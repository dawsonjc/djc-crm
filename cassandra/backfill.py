"""Backfill explicitly registered Cassandra projections from canonical rows."""
import argparse

USER_COLUMNS = (
    "userid create_date update_date status username email first_name last_name "
    "full_name language_preference timezone account_verification_status "
    "two_factor_authentication company_id company_name"
).split()

PROJECTIONS = {
    name: ("user_table", USER_COLUMNS)
    for name in ("users_by_company", "users_by_company_verification_status")
}


def backfill(session, target, apply, page_size):
    from cassandra.query import SimpleStatement

    source, columns = PROJECTIONS[target]
    tables = session.cluster.metadata.keyspaces[session.keyspace].tables
    destination = tables[target]
    keys = [column.name for column in destination.primary_key]
    if not set(keys) <= set(columns) <= (tables[source].columns.keys() & destination.columns.keys()):
        raise ValueError(f"Invalid column mapping for {target}")
    fields = ", ".join(columns)
    statement = None
    if apply:
        placeholders = ", ".join("?" for _ in columns)
        statement = session.prepare(f"INSERT INTO {target} ({fields}) VALUES ({placeholders})")
    rows = session.execute(SimpleStatement(f"SELECT {fields} FROM {source}", fetch_size=page_size))
    eligible = skipped = 0
    for row in rows:
        if any(getattr(row, key) is None for key in keys):
            skipped += 1
            continue
        if statement is not None:
            session.execute(statement, tuple(getattr(row, column) for column in columns))
        eligible += 1
    print(f"{target}: {eligible} rows {'upserted' if apply else 'eligible'}, "
          f"{skipped} skipped (missing primary-key values)", flush=True)


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("targets", nargs="+", choices=sorted(PROJECTIONS))
    parser.add_argument("--host", default="127.0.0.1")
    parser.add_argument("--port", type=int, default=9042)
    parser.add_argument("--keyspace", default="mykeyspacename")
    parser.add_argument("--page-size", type=int, default=500)
    parser.add_argument("--apply", action="store_true", help="Write rows. Default is a read-only preview.")
    args = parser.parse_args()
    if args.page_size < 1:
        parser.error("--page-size must be positive")
    from cassandra.cluster import Cluster

    with Cluster([args.host], port=args.port) as cluster:
        session = cluster.connect(args.keyspace)
        for target in dict.fromkeys(args.targets):
            backfill(session, target, args.apply, args.page_size)


if __name__ == "__main__":
    main()
