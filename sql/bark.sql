CREATE TABLE bark_target
(
    id      INTEGER PRIMARY KEY AUTOINCREMENT,
    name    TEXT,
    url     TEXT,
    enabled BOOLEAN
);

CREATE TABLE bark_message
(
    id           INTEGER PRIMARY KEY AUTOINCREMENT,
    payload      TEXT,
    ack_time     TEXT,
    create_time  TEXT,
    notice_count INTEGER
);