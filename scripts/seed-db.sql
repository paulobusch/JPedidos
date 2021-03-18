use jpedidos;

insert into users(name, email, login, password, role) 
values ('Administrador', 'admin@email.com.br', 'admin', 'uIocuPh+W8WuWp0+qSmlfQ==', 'Admin');

-- by test
insert into customers (name, phone) values
('Paulo', '41896542531'),
('Otávio', '41896548562');

insert into products (name, description, price) values
('Teclado', 'Teclado DELL', 50),
('Notebook', 'Notebook Core I7 16GB de Ram', 50),
('Caixa de som', 'Caixa de som da JBL', 2600);
