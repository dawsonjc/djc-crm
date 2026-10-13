# Focused Cassandra CRM schema

`project.cql` continues to own users, roles, conversations, messages, and events. `crm-schema.cql` adds tenant companies and memberships, plus customer organizations, contacts, leads, deals, pipelines, tasks, notes, activities, and teams. Docker Compose applies the two schema files in that order. `crm-query-examples.cql` is documentation only.

Tenant businesses are stored in `companies_by_id`, with employee memberships in `company_users_by_company`. Both use the existing `RecordStatus` values. Customer organizations remain separate CRM records. See [tenant company model and migration notes](TENANT_COMPANIES.md).

Existing CRM tables retain `workspace_id`; for new company-scoped workflows it represents the tenant's `company_id`, not a separate workspace hierarchy. User references point to `user_table.userid`; global roles remain in `roles` and `user_role`, while company membership and team roles are scoped separately.

Complete records live in `*_by_id`. The other tables are query projections for CRM screens. The service must write the canonical record and its projections. If an owner, status, stage, company, contact, email, or other primary-key field changes, delete the old projection row and insert its replacement.

Normalize names, emails, and domains in application code. Use prepared statements and driver paging state, store `due_month` as `YYYY-MM`, calculate `activity_day` in the workspace timezone, and avoid `ALLOW FILTERING`. Cassandra is not suitable for fuzzy full-text search or arbitrary analytics; add purpose-built search or reporting storage if needed.
