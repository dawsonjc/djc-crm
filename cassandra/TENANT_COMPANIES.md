# Tenant companies and customer organizations

A **company** is a business using DJC CRM. Its employees have user identities and
company memberships. A **customer organization** is a customer/prospect record
that the company manages inside the CRM. These are separate data models.

## Stored tenant data

- `companies_by_id`: company ID, display name, legal name, billing email, phone,
  website, optional primary administrator user ID, billing address, record status,
  creation/update timestamps and optional actor user IDs.
- `company_users_by_company`: company ID + user ID, company-scoped role, record
  status, creation/update timestamps. This supports listing employees of a company
  and looking up a specific membership by both IDs.

Company and membership status use the existing `RecordStatus` values:
`Active` and `Disabled`. Status is independent of registration and email verification.
The membership role is a company-scoped label; it does not grant the global
platform Admin role. Role validation and access enforcement belong to future
membership workflows.

Credentials, profile details and verification remain on the existing user model.
They are not duplicated on company or membership rows. The Kotlin `companyUsers`
property is a transient convenience only; it is not embedded in the company table.
Membership storage is defined here but is not yet wired into provisioning or login.

A company's `primary_admin_user_id` is an optional reference, not an automatic
membership or permission grant. Billing contact information does not imply that
billing or payment processing has been implemented. Audit actor IDs remain optional
until an authenticated platform-operator identity is propagated to company writes;
the service secret is not an end-user identity.

## Customer data

The existing `crm_organizations_*` and `crm_organization_ids_*` tables remain
customer-organization storage, including sales ownership, industry, revenue,
parent organization and shipping address. Their Java projection classes now live
under `model.organization`.

Existing CRM tables retain the column name `workspace_id` to avoid rewriting
their partition keys. The intended mapping for new CRM workflows is
`workspace_id = company_id`: one tenant per subscribing company. No separate
workspace hierarchy is introduced. Future CRM APIs must enforce company membership;
the presence of an ID in a row does not enforce tenant isolation by itself.

## Applying the additive schema

`crm-schema.cql` creates the two tenant tables after the existing address type.
The existing Compose initializer already applies that file. For an existing local
database, run from the repository root:

```sh
docker compose run --rm cassandra-init
```

This creates missing tables without deleting or reinterpreting existing rows.
Back up retained data before any deliberate data migration.

Rows previously entered through the company screen were stored in
`crm_organizations_by_id`. They are **not** automatically moved: the new company
screen reads `companies_by_id`, so it starts empty until tenant records are created
or deliberately migrated. Review each old row to decide whether it is a tenant or
a customer organization. A migration must explicitly map IDs, retain necessary
references, and discard or translate customer-only fields.

Registration, invitation delivery, email verification, profile editing, and company
access enforcement are unchanged by this data-model change. Cassandra remains the
database; no H2 migration is included.
