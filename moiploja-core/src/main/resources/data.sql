
delete from role_permission;
delete from  user_role;
delete from  permissions;
delete from  roles;
delete from  users;

delete from  order_items;
delete from  orders;
delete from  payments;
delete from  customers;
delete from  addresses;
delete from  products;
delete from  categories;

INSERT INTO permissions (id, name) VALUES 
(1, 'MANAGE_CATEGORIES'),
(2, 'MANAGE_PRODUCTS'),
(3, 'MANAGE_ORDERS'),
(4, 'MANAGE_CUSTOMERS'),
(5, 'MANAGE_PAYMENT_SYSTEMS'),
(6, 'MANAGE_USERS'),
(7, 'MANAGE_ROLES'),
(8, 'MANAGE_PERMISSIONS'),
(9, 'MANAGE_SETTINGS')
;

INSERT INTO roles (id, name) VALUES 
(1, 'ROLE_SUPER_ADMIN'),
(2, 'ROLE_ADMIN'),
(3, 'ROLE_CMS_ADMIN'), 
(4, 'ROLE_USER')
;

--BACKUP: $2a$10$p3PHnpoBAnzZiL8mr3gMieMhVVSb585ajC2ZsBB0kwb4KvZKFSdNi
INSERT INTO users (id, email, password, name) VALUES 
(1, 'superadmin1@gmail.com', '$2a$10$AeLsDCzNoIV3ffMNUTdIVO9qoZxUO2SMMHuBP.GO/OpvJrHdOsGCS', 'Super Admin'),
(2, 'admin1@fake.com', '$2a$10$AeLsDCzNoIV3ffMNUTdIVO9qoZxUO2SMMHuBP.GO/OpvJrHdOsGCS', 'Admin'),
(3, 'marcos1@fake.com', '$2a$10$AeLsDCzNoIV3ffMNUTdIVO9qoZxUO2SMMHuBP.GO/OpvJrHdOsGCS', 'Marcos 1'),
(4, 'marcos2@fake.com', '$2a$10$AeLsDCzNoIV3ffMNUTdIVO9qoZxUO2SMMHuBP.GO/OpvJrHdOsGCS', 'Marcos 2'),
(5, 'marcos3@fake.com', '$2a$10$AeLsDCzNoIV3ffMNUTdIVO9qoZxUO2SMMHuBP.GO/OpvJrHdOsGCS', 'Marcos 3')
;

insert into role_permission(role_id, perm_id) values
(1,1),(1,2),(1,3),(1,4),(1,5),(1,6),(1,7),(1,8),(1,9),
(2,1),(2,2),(2,3),(2,4),(2,5),(2,9),
(3,1),(3,2)
;

insert into user_role(user_id, role_id) values
(1,1),
(2,2),
(3,3),
(4,2),(4,3),
(5,4)
;

insert into categories(id, name, disp_order,disabled) values
(1,'Flores',1,false),
(2,'Brinquedos',2,false),
(3,'Livros',3,false)
;

INSERT INTO products (id,cat_id,sku,name,description,image_url,price,disabled,created_on) VALUES
 (1,1,'P1001','Produto 1','Produto 1','1.jpg','430.00',false,now()),
 (2,2,'P1002','Produto 2','Produto 2','2.jpg','490.00',false,now()),
 (3,3,'P1003','Produto 3','Produto 3','3.jpg','400.00',false,now()),
 (4,1,'P1004','Produto 4','Produto 4','4.jpg','430.00',false,now()),
 (5,2,'P1005','Produto 5','Produto 5','5.jpg','750.00',false,now()),
 (6,3,'P1006','Produto 6','Produto 6','6.jpg','350.00',false,now()),
 (7,1,'P1007','Produto 7','Produto 7','7.jpg','220.00',false,now()),
 (8,2,'P1008','Produto 8','Produto 8','8.jpg','120.00',false,now()),
 (9,3,'P1009','Produto 9','Produto 9','9.jpg','150.00',false,now()),
 (10,1,'P1010','Produto 10','Produto 10','10.jpg','460.00',false,now()),
 (11,2,'P1011','Produto 11','Produto 11','11.jpg','440.00',false,now()),
 (12,3,'P1012','Produto 12','Produto 12','12.jpg','450.00',false,now()),
 (13,1,'P1013','Produto 13','Produto 13','13.jpg','470.00',false,now()),
 (14,2,'P1014','Produto 14','Produto 14','14.jpg','250.00',false,now()),
 (15,3,'P1015','Produto 15','Produto 15','15.jpg','450.00',false,now()),
 (16,1,'P1016','Produto 16','Produto 16','16.jpg','150.00',false,now()),
 (17,2,'P1017','Produto 17','Produto 17','17.jpg','450.00',false,now()),
 (18,3,'P1018','Produto 18','Produto 18','18.jpg','450.00',false,now()),
 (19,1,'P1019','Produto 19','Produto 19','19.jpg','400.00',false,now()),
 (20,2,'P1020','Produto 20','Produto 20','20.jpg','450.00',false,now()),
 (21,3,'P1021','Produto 21','Produto 21','21.jpg','450.00',false,now()),
 (22,1,'P1022','Produto 22','Produto 22','22.jpg','550.00',false,now()),
 (23,2,'P1023','Produto 23','Produto 23','23.jpg','450.00',false,now()),
 (24,3,'P1024','Produto 24','Produto 24','24.jpg','450.00',false,now()),
 (25,1,'P1025','Produto 25','Produto 25','25.jpg','250.00',false,now())
;

INSERT INTO customers (id,firstname,lastname,email,phone,password) 
VALUES
  (1,'customer1','K','customer1@fake.com','999999999','$2a$10$AeLsDCzNoIV3ffMNUTdIVO9qoZxUO2SMMHuBP.GO/OpvJrHdOsGCS'),
  (2,'customer2','P','customer2@fake.com','8888888888','$2a$10$AeLsDCzNoIV3ffMNUTdIVO9qoZxUO2SMMHuBP.GO/OpvJrHdOsGCS')
  ;

insert into addresses (id, address_line1, address_line2, city, state, zip_code, country) values 
(1, 'Av dos Bobos 1455', 'BL 1', 'São Paulo', 'SP', '500088', 'BR');

insert into payments (id, cc_number, cvv, amount) values 
(1, '1111111111111111', '123', 430.00);

insert into orders (id, order_number, cust_id, billing_addr_id, delivery_addr_id, payment_id, status, created_on) values 
(1, '1447737431927', 1, 1, 1, 1, 'NEW', now());

insert into order_items (id, order_id, price, product_id, quantity) values 
(1,1, 430.00, 1, 1);