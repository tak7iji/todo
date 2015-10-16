drop table operation_date;
drop table account;
drop table todo;
CREATE TABLE account(
    username varchar(128),
    password varchar(60),
    authorities varchar(32),
    first_name varchar(128),
    last_name varchar(128),
    constraint pk_tbl_account primary key (username)
);
create table todo(todo_id varchar(36) primary key, todo_title varchar(30), finished boolean, created_at timestamp);
CREATE TABLE operation_date(diff bigint NOT NULL);
