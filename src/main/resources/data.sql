INSERT INTO customers(id, name, surname, credit_limit, used_credit_limit) VALUES (1, 'Admin', 'Admin', 10000.0, 0.0);
INSERT INTO customers(id, name, surname, credit_limit, used_credit_limit) VALUES (2, 'Ali', 'YILMAZ', 1000.0, 12.5);
INSERT INTO customers(id, name, surname, credit_limit, used_credit_limit) VALUES (3, 'Can', 'YILMAZ', 1000.0, 0.0);

INSERT INTO users (username, password, role, customer_id) VALUES ('admin', '$2a$10$HUFbxXddADjjg2lcPv2T7uWmnm6CKCbynXi4DdR86SoLADG6W/W0G', 'ADMIN', 1);
INSERT INTO users (username, password, role, customer_id) VALUES ('ayilmaz', '$2a$10$HUFbxXddADjjg2lcPv2T7uWmnm6CKCbynXi4DdR86SoLADG6W/W0G', 'CUSTOMER', 2);
INSERT INTO users (username, password, role, customer_id) VALUES ('cyilmaz', '$2a$10$HUFbxXddADjjg2lcPv2T7uWmnm6CKCbynXi4DdR86SoLADG6W/W0G', 'CUSTOMER', 3);

INSERT INTO loans ( create_date, is_paid, loan_amount, number_of_installment, customer_id) VALUES ( '2025-03-11', false, 50.0, 6, 2);

INSERT INTO loan_installment ( amount, due_date, is_paid, paid_amount, payment_date, loan_id) VALUES (12.5, '2025-03-01', true, 12.5, '2025-03-01', 1);
INSERT INTO loan_installment (amount, due_date, is_paid,  loan_id) VALUES (12.5, '2025-04-01', false,  1);
INSERT INTO loan_installment (amount, due_date, is_paid,  loan_id) VALUES (12.5, '2025-05-01', false,  1);
INSERT INTO loan_installment (amount, due_date, is_paid,  loan_id) VALUES (12.5, '2025-06-01', false,  1);
INSERT INTO loan_installment (amount, due_date, is_paid,  loan_id) VALUES (12.5, '2025-07-01', false,  1);
INSERT INTO loan_installment (amount, due_date, is_paid,  loan_id) VALUES (12.5, '2025-08-01', false,  1);
