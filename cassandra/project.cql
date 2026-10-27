CREATE KEYSPACE IF NOT EXISTS mykeyspacename WITH REPLICATION = { 'class' : 'NetworkTopologyStrategy', 'datacenter1' : 1 };

CREATE TABLE IF NOT EXISTS mykeyspacename.user_table (
    userid UUID,
    create_date TIMESTAMP,
    update_date TIMESTAMP,
    status TEXT,
    username TEXT,
    email TEXT,
    password TEXT,
    first_name TEXT,
    last_name TEXT,
    full_name TEXT,
    language_preference TEXT,
    timezone TEXT,
    last_login_date TIMESTAMP,
    account_verification_status TEXT,
    two_factor_authentication BOOLEAN,
    company_id UUID,
    company_name TEXT,
    auth_api_token UUID,

    PRIMARY KEY ( userid )
);

-- Query projections for UserTableRepository/UserTableService. Maintain alongside user_table writes.
-- RecordStatus stores display values (Active/Disabled); verification enums store names (PENDING/VERIFIED/BLOCKED).
CREATE TABLE IF NOT EXISTS mykeyspacename.user_ids_by_email (
    email text,
    status text,
    userid uuid,
    PRIMARY KEY ((email, status), userid)
);

CREATE TABLE IF NOT EXISTS mykeyspacename.user_ids_by_username (
    username text,
    status text,
    userid uuid,
    PRIMARY KEY ((username, status), userid)
);

CREATE TABLE IF NOT EXISTS mykeyspacename.users_by_verification_status (
    userid uuid,
    create_date timestamp,
    update_date timestamp,
    status text,
    username text,
    email text,
    first_name text,
    last_name text,
    full_name text,
    language_preference text,
    timezone text,
    last_login_date timestamp,
    account_verification_status text,
    two_factor_authentication boolean,
    company_id uuid,
    company_name text,
    PRIMARY KEY ((account_verification_status, status), userid)
);

CREATE TABLE IF NOT EXISTS mykeyspacename.roles (
    roleid UUID PRIMARY KEY,
    create_date TIMESTAMP,
    update_date TIMESTAMP,
    status TEXT,
    role_name TEXT,
    description TEXT
);

CREATE TABLE IF NOT EXISTS mykeyspacename.user_role (
     create_date TIMESTAMP,
     update_date TIMESTAMP,
     status TEXT,
     userid UUID,
     roleid UUID,
     role_name TEXT,
     username TEXT,

     PRIMARY KEY ( userid, roleid )
);

CREATE TABLE IF NOT EXISTS mykeyspacename.messages (
    messageid UUID,
    create_date TIMESTAMP,
    update_date TIMESTAMP,
    status TEXT,
    conversation_id UUID,
    from_user_id UUID,
    from_username TEXT,
    to_user_id UUID,
    to_username TEXT,
    content TEXT,

    PRIMARY KEY ( messageid, create_date, conversation_id )
) WITH CLUSTERING ORDER BY ( create_date DESC, conversation_id DESC );

CREATE TABLE IF NOT EXISTS mykeyspacename.conversations (
    conversationid UUID,
    create_date TIMESTAMP,
    update_date TIMESTAMP,
    status TEXT,
    name TEXT,
    users SET<UUID>,

    PRIMARY KEY ( conversationid )
);

CREATE TABLE  IF NOT EXISTS mykeyspacename.event (
    user_id uuid,
    event_id timeuuid,
    create_date TIMESTAMP,
    update_date TIMESTAMP,
    status text,
    event_type text,
    actor_id uuid,
    actor_name text,
    title text,
    message text,
    action_url text,
    metadata map<text, text>,
    is_read boolean,
    is_archived boolean,
    expires_at timestamp,
    PRIMARY KEY (user_id, event_id)
) WITH CLUSTERING ORDER BY (event_id DESC);

CREATE TABLE  IF NOT EXISTS mykeyspacename.events_by_type (
    user_id uuid,
    event_type text,
    event_id timeuuid,
    create_date TIMESTAMP,
    update_date TIMESTAMP,
    status text,
    actor_id uuid,
    actor_name text,
    title text,
    message text,
    action_url text,
    metadata map<text, text>,
    is_read boolean,
    is_archived boolean,
    expires_at timestamp,
    PRIMARY KEY ((user_id, event_type), event_id)
) WITH CLUSTERING ORDER BY (event_id DESC)
   AND comment = 'Events partitioned by user and type for filtered queries'
   AND gc_grace_seconds = 86400
   AND compaction = {
    'class': 'TimeWindowCompactionStrategy',
    'compaction_window_size': 1,
    'compaction_window_unit': 'DAYS'
    };

CREATE TABLE IF NOT EXISTS  mykeyspacename.unread_events (
       user_id uuid,
       event_id timeuuid,
       create_date TIMESTAMP,
       update_date TIMESTAMP,
       status text,
       event_type text,
       actor_id uuid,
       actor_name text,
       title text,
       message text,
       action_url text,
       metadata map<text, text>,
       expires_at timestamp,
       PRIMARY KEY (user_id, event_id)
) WITH CLUSTERING ORDER BY (event_id DESC)
   AND comment = 'Unread events only - deleted when marked as read'
   AND gc_grace_seconds = 86400
   AND compaction = {
            'class': 'TimeWindowCompactionStrategy',
            'compaction_window_size': 1,
            'compaction_window_unit': 'DAYS'
            };

CREATE INDEX IF NOT EXISTS ON mykeyspacename.event (is_read);
CREATE INDEX IF NOT EXISTS ON mykeyspacename.event (event_type);

CREATE INDEX IF NOT EXISTS ON mykeyspacename.conversations (users);

CREATE MATERIALIZED VIEW IF NOT EXISTS mykeyspacename.messages_by_conversation_id AS
    SELECT * FROM mykeyspacename.messages WHERE conversation_id IS NOT NULL AND create_date IS NOT NULL AND messageid IS NOT NULL
    PRIMARY KEY (conversation_id, create_date, messageid) WITH CLUSTERING ORDER BY (create_date DESC, messageid ASC);

INSERT INTO mykeyspacename.conversations(conversationid, create_date, update_date, status, name) VALUES(00000000-0000-0000-0000-000000000001, totimestamp(now()), totimestamp(now()), 'Active', 'Global Chat');
INSERT INTO mykeyspacename.roles(roleid, create_date, update_date, status, role_name, description) VALUES (00000000-0000-0000-0000-000000000101, totimestamp(now()), totimestamp(now()), 'Active', 'Super Admin', 'Super Admin');
INSERT INTO mykeyspacename.roles(roleid, create_date, update_date, status, role_name, description) VALUES (00000000-0000-0000-0000-000000000102, totimestamp(now()), totimestamp(now()), 'Active', 'Company Admin', 'Company Admin');
INSERT INTO mykeyspacename.roles(roleid, create_date, update_date, status, role_name, description) VALUES (00000000-0000-0000-0000-000000000103, totimestamp(now()), totimestamp(now()), 'Active', 'Admin', 'Admin');
INSERT INTO mykeyspacename.roles(roleid, create_date, update_date, status, role_name, description) VALUES (00000000-0000-0000-0000-000000000104, totimestamp(now()), totimestamp(now()), 'Active', 'Moderator', 'Moderator');
INSERT INTO mykeyspacename.roles(roleid, create_date, update_date, status, role_name, description) VALUES (00000000-0000-0000-0000-000000000105, totimestamp(now()), totimestamp(now()), 'Active', 'User', 'User');

-- INSERT INTO mykeyspacename.user_table(userid, create_date, update_date, status, username, email, password, first_name, last_name, full_name, language_preference, timezone, last_login_date, account_verification_status, two_factor_authentication, company_id, company_name, auth_api_token) VALUES(00000000-0000-0000-0000-000000000001, totimestamp(now()), totimestamp(now()), 'Active', 'admin', 'admin@admin.com', 'INSERT PASSWORD HERE', 'Admin', 'User', 'Admin User', 'en', 'America/Phoenix', totimestamp(now()), 'VERIFIED', true, null, null, null);
-- INSERT INTO mykeyspacename.user_ids_by_email(email, status, userid) VALUES('admin@admin.com', 'Active', 00000000-0000-0000-0000-000000000001);
-- INSERT INTO mykeyspacename.user_ids_by_username(username, status, userid) VALUES('admin', 'Active', 00000000-0000-0000-0000-000000000001);
-- INSERT INTO mykeyspacename.users_by_verification_status(userid, create_date, update_date, status, username, email, first_name, last_name, full_name, language_preference, timezone, last_login_date, account_verification_status, two_factor_authentication, company_id, company_name) VALUES(00000000-0000-0000-0000-000000000001, totimestamp(now()), totimestamp(now()), 'Active', 'admin', 'admin@admin.com', 'Admin', 'User', 'Admin User', 'en', 'America/Phoenix', totimestamp(now()), 'VERIFIED', true, null, null);
-- INSERT INTO mykeyspacename.user_role(create_date, update_date, status, userid, roleid, role_name) VALUES(totimestamp(now()), totimestamp(now()), 'Active', , 00000000-0000-0000-0000-000000000101, 'Super Admin');
