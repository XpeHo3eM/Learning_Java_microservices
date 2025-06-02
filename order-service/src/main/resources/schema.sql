CREATE TABLE IF NOT EXISTS orders (
    url        VARCHAR   PRIMARY KEY,
    file_name  VARCHAR   NOT NULL,
    created_at TIMESTAMP NOT NULL
);