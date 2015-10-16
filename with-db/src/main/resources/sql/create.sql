create table if not exists todo(todo_id varchar(36) primary key, todo_title varchar(30), finished boolean, created_at timestamp);
CREATE TABLE if not exists operation_date(diff bigint NOT NULL);
DELETE FROM operation_date;
INSERT INTO operation_date(diff) VALUES (1440);
drop table if exists users;
drop table if exists authorities;
create table users(
    username varchar_ignorecase(50) not null primary key,
    password varchar_ignorecase(128) not null,
    enabled boolean not null
);

create table authorities (
    username varchar_ignorecase(50) not null,
    authority varchar_ignorecase(50) not null,
    constraint fk_authorities_users foreign key(username) references users(username)
);
create unique index ix_auth_username on authorities (username,authority);
insert into users values ('admin', 'demo', true);
insert into users values ('user1', 'demo', true);
insert into authorities values('admin', 'ROLE_ADMIN');
insert into authorities values('user1', 'ROLE_USER');

commit;
