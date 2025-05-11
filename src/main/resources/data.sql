INSERT INTO customers(id, name, surname, credit_limit, used_credit_limit) VALUES (1, 'Admin', 'Admin', 10000.0, 0.0);
INSERT INTO customers(id, name, surname, credit_limit, used_credit_limit) VALUES (2, 'Ali', 'YILMAZ', 1000.0, 0.0);
INSERT INTO customers(id, name, surname, credit_limit, used_credit_limit) VALUES (3, 'Can', 'YILMAZ', 1000.0, 0.0);

INSERT INTO users (username, password, role, customer_id) VALUES ('admin', '$2a$10$HUFbxXddADjjg2lcPv2T7uWmnm6CKCbynXi4DdR86SoLADG6W/W0G', 'ADMIN', 1);

INSERT INTO users (username, password, role, customer_id) VALUES ('ayilmaz', '$2a$10$HUFbxXddADjjg2lcPv2T7uWmnm6CKCbynXi4DdR86SoLADG6W/W0G', 'CUSTOMER', 2);
INSERT INTO users (username, password, role, customer_id) VALUES ('cyilmaz', '$2a$10$HUFbxXddADjjg2lcPv2T7uWmnm6CKCbynXi4DdR86SoLADG6W/W0G', 'CUSTOMER', 3);


