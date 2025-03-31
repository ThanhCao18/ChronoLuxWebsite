use chronoluxweb;

insert into role(code,name) values('ROLE_ADMIN','Quản trị');
insert into role(code,name) values('ROLE_USER','Người dùng');

insert into user(username,password,fullname,status)
values('admin','$2a$10$/RUbuT9KIqk6f8enaTQiLOXzhnUkiwEJRdtzdrMXXwU7dgnLKTCYG','Đàm Đức Huy',1);
insert into user(username,password,fullname,status)
values('nguyenvana','$2a$10$/RUbuT9KIqk6f8enaTQiLOXzhnUkiwEJRdtzdrMXXwU7dgnLKTCYG','nguyễn văn A',1);
insert into user(username,password,fullname,status)
values('nguyenvanb','$2a$10$/RUbuT9KIqk6f8enaTQiLOXzhnUkiwEJRdtzdrMXXwU7dgnLKTCYG','nguyễn văn B',1);

INSERT INTO role_detail(user_id,role_id) VALUES (1,1);
INSERT INTO role_detail(user_id,role_id) VALUES (2,2);
INSERT INTO role_detail(user_id,role_id) VALUES (3,2);

ALTER TABLE `chronoluxweb`.`user`
ADD COLUMN `email` VARCHAR(255) NULL AFTER `password`;

ALTER TABLE `chronoluxweb`.`user`
ADD COLUMN `reset_password_token` VARCHAR(45) NULL AFTER `username`;

ALTER TABLE `chronoluxweb`.`product`
ADD COLUMN `name` VARCHAR(255) NULL AFTER `product_line_id`;

insert into user(username,password,fullname,status)
values('ChronoLuxAdmin','$2a$12$6YhCULCfFAHUweBK0cfjMOqbjctFuJ1/nrlm2pTCFVpDXvQnYDRBy','Hoàng Lâm',1);
insert into user(username,password,fullname,status)
values('ChronoLuxUser','$2a$12$6YhCULCfFAHUweBK0cfjMOqbjctFuJ1/nrlm2pTCFVpDXvQnYDRBy','Tester',1);