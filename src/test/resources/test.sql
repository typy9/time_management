CREATE SCHEMA IF NOT EXISTS test_schema AUTHORIZATION sa;

CREATE TABLE test_schema.users

(user_id INTEGER NOT NULL,

 user_name VARCHAR(25) NOT NULL,

 role VARCHAR(25) NOT NULL,

 PRIMARY KEY (user_id))

