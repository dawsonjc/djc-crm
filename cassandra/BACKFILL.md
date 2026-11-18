# Backfill Cassandra query tables

Apply the new table's CREATE TABLE migration first. From the repository root,
with Python 3 and local Cassandra exposed on port 9042, install the driver with
`python3 -m pip install cassandra-driver` and preview the initial projections:

```bash
python3 cassandra/backfill.py users_by_company users_by_company_verification_status
```

Pause application writes to the affected canonical and projection tables during
the backfill. Run the same command with `--apply` to copy existing rows. Resume
writes once finished, with the projection write paths enabled. The script pages
through canonical data and uses prepared upserts. Rows missing a target key
(such as users without a company) are counted and skipped. It can be rerun after
a partial failure. It does not delete stale destination keys or preserve source
TTLs or timestamps. Use it for new tables with ordinary snapshot columns.

For a new query table, register its canonical table and explicit column allowlist
in `PROJECTIONS` in `backfill.py`. The runner checks the mapping against Cassandra
metadata and discovers which columns form the target key. Derived columns,
custom filters, or TTL rules need an explicit migration instead. Run this step
from your schema migration workflow after table creation, once per new projection.
The repository does not run it automatically at every startup.

