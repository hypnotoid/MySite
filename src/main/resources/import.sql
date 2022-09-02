insert into mysite.items (amount, name, orders, price) values(20,'The Tartar Sauce',42,89);
insert into mysite.items (amount, name, orders, price) values(89,'STAPLER',7,85);
insert into mysite.items (amount, name, orders, price) values(14,'Book',3,75);

insert into mysite.users (username, enabled, first_name, last_name, password) values ( 'admin', true,'Admin', 'Admin','$2a$10$dEv.pFKmpF2Ok/NxNS4iwecjiGHwrxrzGn0uVX72NEK7w01k68tQK');
insert into mysite.users (username, enabled, first_name, last_name, password) values ('test',true,'TEST', 'TEST','$2a$10$qYPXUwwi5EOu4HDgTGiU7OlYQjqYu6APrRubImP4tfeqfCJzaSPBi');
insert into mysite.users (username, enabled, first_name, last_name, password) values ( 'user', true,'User', 'User','$2a$10$dEv.pFKmpF2Ok/NxNS4iwecjiGHwrxrzGn0uVX72NEK7w01k68tQK');
insert into mysite.users (username, enabled, first_name, last_name, password) values ( 'editor', true,'User', 'User','$2a$10$dEv.pFKmpF2Ok/NxNS4iwecjiGHwrxrzGn0uVX72NEK7w01k68tQK');
insert into mysite.users (username, enabled, first_name, last_name, password) values ( 'user2', true,'User', 'User','$2a$10$dEv.pFKmpF2Ok/NxNS4iwecjiGHwrxrzGn0uVX72NEK7w01k68tQK');
insert into mysite.users (username, enabled, first_name, last_name, password) values ( 'editor3', true,'User', 'User','$2a$10$dEv.pFKmpF2Ok/NxNS4iwecjiGHwrxrzGn0uVX72NEK7w01k68tQK');
insert into mysite.roles (name) values ('ROLE_ADMIN'); -- 1 is ADMIN
insert into mysite.roles (name) values ('ROLE_USER');  -- 2 is USER
insert into mysite.roles (name) values ('ROLE_EDITOR');  -- 3 is EDITOR

insert into mysite.users_roles (user_id, role_id) VALUES (1, 1); -- user admin has role admin
insert into mysite.users_roles (user_id, role_id) VALUES (1, 3); -- user admin has role editor

insert into mysite.users_roles (user_id, role_id) VALUES (2, 1); -- user test has role admin
insert into mysite.users_roles (user_id, role_id) VALUES (2, 2); -- user test has role user
insert into mysite.users_roles (user_id, role_id) VALUES (2, 3); -- user test has role editor

insert into mysite.users_roles (user_id, role_id) VALUES (3,2); -- user user has role user

insert into mysite.users_roles (user_id, role_id) values (4,3);
insert into mysite.users_roles (user_id, role_id) values (5,2);
insert into mysite.users_roles (user_id, role_id) values (6,3);

insert into mysite.orders (amount, order_date) VALUES (14, now());
insert into mysite.orders (amount,  order_date) values (24, now());
insert into mysite.orders_products (order_id, product_id) values (1,2);
insert into mysite.orders_products (order_id, product_id) values (2,2);
-- insert into mysite.orders_products (order_id, product_id) values (1,3); -- error
insert into mysite.orders_users (user_id, order_id) values (3,1);
-- insert into mysite.orders_users (user_id, order_id) values (4,1); -- error
insert into mysite.orders_users (user_id, order_id) values (5,2);