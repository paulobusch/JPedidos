use jpedidos;

insert into users(name, email, login, password, role) values 
('Administrador', 'admin@email.com.br', 'admin', 'uIocuPh+W8WuWp0+qSmlfQ==', 'Admin'),
('Funcionário', 'funcionario@email.com.br', 'funcionario', 'uIocuPh+W8WuWp0+qSmlfQ==', 'Funcionário'),
('Gerente', 'gerente@email.com.br', 'gerente', 'uIocuPh+W8WuWp0+qSmlfQ==', 'Gerente');

-- by test
insert into customers (name, phone) values
('Paulo', '(41) 99587-6478'),
('Otávio', '(41) 92145-3215');

insert into products (name, description, price) values
('Teclado', 'Teclado DELL', 50),
('Notebook', 'Notebook Core I7 16GB de Ram', 6000),
('Caixa de som', 'Caixa de som da JBL', 2600);
