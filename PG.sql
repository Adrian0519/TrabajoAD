create schema objetoContact;

Create type objetoContact.contacto_type as(
nombre_contacto varchar(100),
nif Varchar(20),
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
nombre_categoria varchar(100)
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

INSERT INTO proveedores (nombre_proveedor, contacto)
VALUES
    ('fruterias blancas', ('Paola', '123456789a', '666777999', 'frutas@gmail.com')),
    ('carpinteria blanca', ('Pablo', '987654321b', '111222333', 'carpinterias@gmail.com')),
    ('supermercado blanco', ('Mark', '444555666c', '444555666', 'supermercado@gmail.com'));

INSERT INTO categorias (nombre_categoria)
VALUES
    ('Carpinteria'),
    ('Electricidad'),
    ('Comida');
INSERT INTO almacenes (nombre_almacen, ubicacion)
VALUES
    ('Almacen galactico', 'España'),
    ('Almacen maderero', 'España'),
    ('Almacen italiano', 'Italia');

INSERT INTO productos (id_producto, id_proveedor, id_categoria) VALUES
    (1, 2, 1),
    (2, 2, 1),
    (3, 1, 3);

INSERT INTO almacenes_productos (id_almacen, id_producto, cantidad) VALUES
    (1, 1, 27),
    (2, 2, 50),
    (3, 3, 47);