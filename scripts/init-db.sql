create database jpedidos;
create user 'jpedidos'@'%' identified by '321654987';
grant all privileges on *.* to 'jpedidos'@'%' with grant option;
flush privileges;

use jpedidos;

create table users (
  id int not null auto_increment,
  name varchar(75) not null,
  email varchar(80) not null,
  login varchar(35) not null,
  password varchar(80) not null,
  password_temporary bit not null default 1,
  role enum('Admin', 'Gerente', 'Funcionário') not null,
  
  primary key(id),

  unique key uq_login(login),
  unique key uq_email(email)
);

create table customers (
  id int not null auto_increment,
  name varchar(75) not null,
  phone varchar(15) not null,

  primary key(id)
);

create table orders (
  id int not null auto_increment,
  user_id int not null, 
  customer_id int not null,
  status enum('aberto', 'fechado') not null default 'aberto',
  open_date datetime not null default now(),
  close_date datetime,
  
  primary key(id),

  foreign key (user_id) references users(id),
  foreign key (customer_id) references customers(id)
);

create table products (
  id int not null auto_increment,
  name varchar(40) not null,
  description varchar(250) not null,
  price decimal(10, 2) not null,
  active bit(1) not null default 1,
  
  primary key(id),

  unique key uq_name(name)
);

create table orders_products(
  id int not null auto_increment,
  order_id int not null,
  product_id int not null,
  amount int not null,
  
  primary key(id),

  foreign key (order_id) references orders(id),
  foreign key (product_id) references products(id),

  unique key uq_pf(order_id, product_id)
);
