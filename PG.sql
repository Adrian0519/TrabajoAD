create schema objetoContact;

Create type objetoContact.contacto_type as(
nombre_contacto varchar(100),
nif int,
telefono int,
email varchar(100)
);


create table almacenes(
id_almacen serial primary key,
nombre_almacen Varchar(100),
ubicacion varchar(100)
);

create table categorias(
id_categoria serial primary key,
nombre_categorias varchar(100)
);

create table proveedores(
id_proveedor serial primary key,
nombre_proveedor varchar(100),
contacto objetoContact.contacto_type 
);

create table productos(
id_producto int primary key,
id_proveedor int not null,
id_categoria int not null,
foreign key(id_categoria) references categorias (id_categoria) on update cascade,
foreign key(id_proveedor) references proveedores (id_proveedor) on update cascade
);

create table almacenes_productos(
id_almacen int,
id_producto int ,
cantidad int not null,
foreign key(id_almacen) references almacenes (id_almacen) on update cascade,
foreign key(id_producto) references productos (id_producto) on update cascade,
primary key(id_almacen , id_producto)
);


INSERT INTO proveedores VALUES ('1','fruterias blancas',('Paola','123456789','666777999','frutas@gmail.com'));
INSERT INTO proveedores VALUES ('2','carpinteria blanca',('Pablo','987654321','111222333','carpinterias@gmail.com'));
INSERT INTO proveedores VALUES ('3','supermercado blanco',('Mark','444555666','444555666','supermercado@gmail.com'));
INSERT INTO categorias VALUES ('1','Carpinteria');
INSERT INTO categorias VALUES ('2','Electricidad');
INSERT INTO categorias VALUES ('3','Comida');
INSERT INTO almacenes VALUES ('1','Almacen galactico','España');
INSERT INTO almacenes VALUES ('2','Almacen maderero','España');
INSERT INTO almacenes VALUES ('3','Almacen italiano', 'Italia');
INSERT INTO productos VALUES ('1','2','1');
INSERT INTO productos VALUES ('2','2','1');
INSERT INTO productos VALUES ('3','1','3');
INSERT INTO almacenes_productos VALUES ('1','1','27');
INSERT INTO almacenes_productos VALUES ('2','2','50');
INSERT INTO almacenes_productos VALUES ('3','3','47');
