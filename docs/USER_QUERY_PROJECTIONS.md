# User queries and projection maintenance

The canonical entity remains `model.User` in `user_table`. The profile DTO excludes passwords and API tokens.

| Query | Table | Service method |
|---|---|---|
| User details/password verification by ID | `user_table` | `getUserById`, `getUserByIdAndPassword` |
| Active user IDs by email | `user_ids_by_email` | `getUserIdByEmail` |
| Active user IDs by username | `user_ids_by_username` | `getUserIdByUsername` |
| Pending active users across companies | `users_by_verification_status` | `getUnverifiedUsers` |
| All users within a company | `users_by_company` | `getUsersByCompanyId` |
| Active/disabled users within a company | `users_by_company` | `getUsersByCompanyIdAndStatus` |
| Pending/verified/blocked users within a company and record status | `users_by_company_verification_status` | `getUsersByCompanyVerificationStatus` |

## Company directory API

Both new entities use `builder().with...().build()`. Their repositories use composite `MapId` keys.
`UserDTO.from(...)` supports either entity, in addition to the existing models.

Company service reads require a `Pageable` of at most 500 users and return `Slice<UserDTO>`.
Start with `PageRequest.of(0, 50)` and continue with the returned slice's `nextPageable()` while
`hasNext()` is true. Cassandra uses paging state; arbitrary numbered-page/offset pagination and
arbitrary sort fields are not supported. The directory orders by status, then user ID; the
company/verification/status view orders by user ID. Name sorting or prefix search needs a separately
designed query and key, rather than `ALLOW FILTERING`.

These methods take a company ID, but do not authorize the caller to access that company.
Controllers must authorize company membership or a global administrator before invoking them.
No HTTP endpoints or browser calls were added by this change.

## Writes

`createUser` writes both company projections. `registerUser` now carries the form's company ID and
company name into the canonical entity. Profile, email, username, verification and record-status
operations refresh the company snapshots. Key changes delete the old company rows after writing
the new ones. `changeCompany(userId, companyId, companyName)` updates canonical membership,
the global verification view, and both company views, removing rows from the previous company.

`saveCompanyProjections(User)` writes only the two company snapshots. It is suitable for a backfill
or repairing those snapshots after the canonical user has already been saved. It does not save
the canonical entity or delete historical keys. Company-less users, such as global administrators,
have no company-directory rows.

Login dates are deliberately absent from the two directory tables, so `recordLogin` still performs
only its two original partial updates. Directory DTOs leave `lastLoginDate`, roles and conversations
null; load canonical details/relations when required. Passwords and API tokens are never duplicated
into these tables. Profile edits now incur two additional snapshot writes; login does not.

## Applying the schema and existing data

Apply only the new `CREATE TABLE` statements in `cassandra/project.cql` to an existing database.
They are additive; changing the file does not automatically create or populate tables in a running
database. Page through existing canonical users and call `saveCompanyProjections` for users whose
company IDs and statuses are populated. Users previously created without company IDs need their
membership assigned first. Coordinate backfill with live writes to avoid replaying older snapshots.
No database commands or backfill were executed as part of this change.

## Review of the existing design

- Email and username tables deliberately allow multiple IDs per value/status. They do not enforce
  uniqueness; the existing lookup service chooses the first result. Decide whether identity is
  globally unique or company-scoped before changing this behavior or creating tenant-specific logins.
- The global verification table groups all users with the same verification/record status together.
  The company-scoped alternative avoids reading other companies' users for tenant administration.
  Large global queues may eventually need paging and time/hash buckets.
- The company directory is one partition per company. Paging bounds each response, not partition
  size. Very large tenants may need buckets; that requires fan-out reads and a measured partition-size
  requirement. The current tables do not assume an arbitrary bucket count in advance.
- No speculative role, full-text, prefix or activity-search tables were added: their keys depend on
  exact access patterns. Existing role queries remain on `user_role`.
- Multi-table writes remain separate Cassandra operations. Concurrent changes and partial failures
  require retry/reconciliation policies; these service methods are not cross-table transactions.
- Existing stale global verification or identity rows are not automatically repaired by adding the
  new company tables. Keep old-key cleanup on each operation and reconcile historical rows separately.
