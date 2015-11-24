create table if not exists todo(todo_id varchar(36) primary key, todo_title varchar(30), finished boolean, created_at timestamp);
CREATE TABLE if not exists operation_date(diff bigint NOT NULL);
DELETE FROM operation_date;
INSERT INTO operation_date(diff) VALUES (1440);
