INSERT INTO account(username, password, authorities, first_name, last_name) VALUES('admin', '$2a$10$oxSJl.keBwxmsMLkcT9lPeAIxfNTPNQxpeywMrF7A3kVszwUTqfTK', 'ROLE_ADMIN', 'Taro', 'Yamada');
INSERT INTO account(username, password, authorities, first_name, last_name) VALUES('user1', '$2a$10$oxSJl.keBwxmsMLkcT9lPeAIxfNTPNQxpeywMrF7A3kVszwUTqfTK', 'ROLE_USER', 'Ichiro', 'Suzuki');
COMMIT;