create table users(username varchar(50) not null primary key,password varchar(500) not null,enabled boolean not null);
create table authorities (username varchar(50) not null,authority varchar(50) not null,constraint fk_authorities_users foreign key(username) references users(username));
create unique index ix_auth_username on authorities (username,authority);

INSERT IGNORE INTO users VALUES ('user', '{noop}1212', '1');
INSERT IGNORE INTO `authorities` VALUES ('user','read');

INSERT IGNORE INTO `users` VALUES ('admin', '{bcrypt}$2a$12$KTFzTpiHvl/UoCaUqbF3QOETfXZQpe2iZfb4h6jrdXV5nzF0iyvX.', '1');
INSERT IGNORE INTO `authorities` VALUES ('admin', 'admin');

