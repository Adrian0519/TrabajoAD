import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    //Menu mediante bucle while que le permitira realizar los metodos que quiera, si pulsa un número incorrecto le dara
    //un error dicho y le pedira que ingrese un caracter de la lista, para finalizar las operaciones pulsara 0,
    // Tambien tiene excepciones para que no rompa el programa y alguna otra (En el metodo de eliminar producto
    // por nombre se permite el espacion en blanco por si el usuario consigue meterlo de alhuna manera se podra borrarlo).
    public static void main(String[] args) throws SQLException {
        Metodos metodos=new Metodos();
        Scanner scanner=new Scanner(System.in);
        int eleccion=99;
        while (eleccion!=0){
            System.out.println("------------------------");
            System.out.println("Menu de acciones ");
            System.out.println("------------------------");
            System.out.println("1.-Crear una nueva categoría");
            System.out.println("------------------------");
            System.out.println("2.-Crear un nuevo proveedor");
            System.out.println("------------------------");
            System.out.println("3.-Eliminar un nuevo proveedor");
            System.out.println("------------------------");
            System.out.println("4.-Crear un nuevo usuario");
            System.out.println("------------------------");
            System.out.println("5.-Eliminar un usuario");
            System.out.println("------------------------");
            System.out.println("6.-Crear nuevo producto");
            System.out.println("------------------------");
            System.out.println("7.-Eliminar un producto por su nombre");
            System.out.println("------------------------");
            System.out.println("8.-Listar los productos con bajo stock (menos de X unidades disponibles)");
            System.out.println("------------------------");
            System.out.println("9.-Obtener el total de pedidos realizados por cada usuario");
            System.out.println("------------------------");
            System.out.println("10.-Obtener la cantidad de productos almacenados por cada almacén");
            System.out.println("------------------------");
            System.out.println("11.-Listar todos los productos con sus respectivas categorías y proveedores");
            System.out.println("------------------------");
            System.out.println("12.-Obtener todos los Usuarios que han comprado algún producto de una categoria dada");
            System.out.println("------------------------");
            System.out.println("0.-Salir del programa");
            try {
                eleccion = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                eleccion = 99;
            }

            switch (eleccion){
                case 1:
                    System.out.println("Dime el nombre de la categoria que deseas crear");
                    String nombreCategoria= scanner.nextLine();
                    if (nombreCategoria.isBlank()){
                        System.out.println("Error: no puede quedar vacio");
                        break;
                    }
                    metodos.crearCategoria(nombreCategoria);
                    break;
                case 2:
                    System.out.println("Dime el nombre del proveedor");
                    String nombre= scanner.nextLine();
                    System.out.println("Inserte el NIF");
                    String nif= scanner.nextLine();
                    if (!nif.matches("^[1-9]\\d{7}[A-Za-z]$")) {
                        System.out.println("Error: El NIF no es correcto.");
                        break;
                    }
                    System.out.println("Necesito el tlf");
                    int tlf;
                    try {
                        tlf = scanner.nextInt();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Error: el telefono debe ser un numero.");
                        scanner.nextLine();
                        break;
                    }
                    System.out.println("Ahora inserte el mail");
                    String mail= scanner.nextLine();
                    if (!mail.matches("^[^@]+@[A-Za-z]+\\.[A-Za-z]+$")) {
                        System.out.println("Error: El correo electrónico no tiene un formato válido.");
                        break;
                    }
                    metodos.crearNuevoProveedor(nombre,nif,tlf,mail);
                    break;
                case 3:
                    System.out.println("Dime el id del provedor");
                    int id;
                    try {
                        id = scanner.nextInt();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Error: el id debe ser un numero.");
                        scanner.nextLine();
                        break;
                    }
                    metodos.eliminarProveedor(id);
                    break;
                case 4:
                    System.out.println("Inserta el nombre del usuario");
                    String nombreInsertar=scanner.nextLine();
                    System.out.println("Insertar el mail del usuario");
                    String mailInsertar= scanner.nextLine();
                    if (!mailInsertar.matches("^[^@]+@[A-Za-z]+\\.[A-Za-z]+$")) {
                        System.out.println("Error: el correo electrónico no tiene un formato valido.");
                        break;
                    }
                    System.out.println("Por ultimo el anho de nacimiento");
                    int insertarNacimiento;
                    try {
                        insertarNacimiento = scanner.nextInt();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Error: el anho tiene que ser un numero .");
                        scanner.nextLine();
                        break;
                    }
                    metodos.crearUsuario(nombreInsertar,mailInsertar,insertarNacimiento);
                    break;
                case 5:
                    System.out.println("Inserta el id del usuario a eliminar");
                    int idEliminar;
                    try {
                        idEliminar = scanner.nextInt();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Error: el id debe ser un numero .");
                        scanner.nextLine();
                        break;
                    }
                    metodos.eliminarUsuario(idEliminar);
                    break;
                case 6:
                    System.out.println("Inserta el nombre del producto a crear");
                    String productoCrear= scanner.nextLine();
                    System.out.println("Insertame su precio");
                    double precio;
                    try {
                        precio = scanner.nextDouble();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Error: el precio debe ser un numero.");
                        scanner.nextLine();
                        break;
                    }
                    System.out.println("Dime el stock");
                    int stock;
                    try {
                        stock = scanner.nextInt();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Error: el stock debe ser un numero entero.");
                        scanner.nextLine();
                        break;
                    }
                    System.out.println("Ahora quiero el nombre del categoria");
                    String categoria=scanner.nextLine();
                    System.out.println("Puedes darme el nif del proveedor");
                    String nifProe= scanner.nextLine();
                    metodos.crearProducto(productoCrear,precio,stock,categoria,nifProe);
                    break;
                case 7:
                    System.out.println("Inserta el producto que deseas eliminar");
                    String productoEliminar=scanner.nextLine();
                    metodos.eliminarProductoPorNombre(productoEliminar);
                    break;
                case 8:
                    System.out.println("Dime el stock y t mostraremos con los productos con menos de dicho stock");
                    int stockMenor;
                    try {
                        stockMenor = scanner.nextInt();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Error: el stock debe ser un numero entero.");
                        scanner.nextLine();
                        break;
                    }
                    metodos.listarProductosBajoStock(stockMenor);
                    break;
                case 9:
                    metodos.obtenerTotalPedidosUsuarios();
                    break;
                case 10:
                    metodos.obtenerCantidadProductosEnCadaAlmacen();
                    break;
                case 11:
                    metodos.listarTodosProductosConCategoriaYProveedor();
                    break;
                case 12:
                    System.out.println("Dame el id de la categoria");
                    int idCategoria;
                    try {
                        idCategoria = scanner.nextInt();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Error: el id debe ser un numero.");
                        scanner.nextLine();
                        break;
                    }
                    metodos.obtenerUsuariosCompraronProductosCategoria(idCategoria);
                    break;
                case 0:
                    System.out.println("Gracias, se cerrara el programa");
                    break;
                default:
                    System.out.println("Dato incorrecto introduzca un numero correcto");
                    break;
            }

    }
    }
}