drop database if exists proyecto;
create database proyecto;
use proyecto;

create table productos(
id_producto int auto_increment key,
nombre_producto varchar(100) not null,
precio int not null,
stock int not null
);

create table usuarios(
id_usuario int auto_increment primary key,
nombre varchar (100) not null,
email varchar(100) not null,
ano_nacimiento int not null
);

create table pedidos(
id_pedido int auto_increment primary key,
id_usuario int not null,
fecha_pedido date not null,
foreign key(id_usuario) references usuarios (id_usuario) on update cascade
);

create table pedidos_productos(
id_pedido int,
id_producto int,
cantidad int not null,
primary key(id_pedido,id_producto),
foreign key (id_producto) references productos (id_producto) on update cascade,
foreign key (id_pedido) references pedidos (id_pedido) on update cascade
);

INSERT INTO productos VALUES ('1','Puerta','100',23);
INSERT INTO productos VALUES ('2','Mesa','75',19);
INSERT INTO productos VALUES ('3','Patatas','40',530);
INSERT INTO usuarios VALUES ('1','Paco','paco@mail.com',1993);
INSERT INTO usuarios VALUES ('2','Paola','paola@mail.com',1990);
INSERT INTO usuarios VALUES ('3','Jorge,','jorge@mail.com',1999);
INSERT INTO pedidos VALUES ('1','1','2023-7-11');
INSERT INTO pedidos VALUES ('2','1','2023-7-11');
INSERT INTO pedidos VALUES ('3','3','2023-8-9');
INSERT INTO pedidos VALUES ('4','2','2023-5-9');
INSERT INTO pedidos_productos VALUES ('1','1','3');
INSERT INTO pedidos_productos VALUES ('2','3','9');
INSERT INTO pedidos_productos VALUES ('3','1','4');
INSERT INTO pedidos_productos VALUES ('4','2','14');
