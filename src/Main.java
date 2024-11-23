import java.sql.SQLException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {
        Metodos metodos=new Metodos();
        Scanner scanner=new Scanner(System.in);
        int eleccion=99;
        while (eleccion!=0){
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
            eleccion= scanner.nextInt();
            scanner.nextLine();
            switch (eleccion){
                case 1:
                    System.out.println("Dime el nombre de la categoria que deseas crear");
                    String nombreCategoria= scanner.next();
                    scanner.nextLine();
                    metodos.crearCategoria(nombreCategoria);
                    break;
                case 2:
                    System.out.println("Dime el nombre del proveedor");
                    String nombre= scanner.next();
                    scanner.nextLine();
                    System.out.println("Inserte el NIF");
                    String nif= scanner.next();
                    scanner.nextLine();
                    System.out.println("Necesito el tlf");
                    int tlf= scanner.nextInt();
                    scanner.nextLine();
                    System.out.println("Ahora inserte el mail");
                    String mail= scanner.next();
                    scanner.nextLine();
                    metodos.crearNuevoProveedor(nombre,nif,tlf,mail);
                    break;
                case 3:
                    System.out.println("Dime el id del provedor");
                    int id= scanner.nextInt();
                    scanner.nextLine();
                    metodos.eliminarProveedor(id);
                    break;
                case 4:
                    System.out.println("Inserta el nombre del usuario");
                    String nombreInsertar=scanner.next();
                    scanner.nextLine();
                    System.out.println("Insertar el mail del usuario");
                    String mailInsertar= scanner.next();
                    scanner.nextLine();
                    System.out.println("Por ultimo el anho de nacimiento");
                    int insertarNacimiento=scanner.nextInt();
                    scanner.nextLine();
                    metodos.crearUsuario(nombreInsertar,mailInsertar,insertarNacimiento);
                    break;
                case 5:
                    System.out.println("Inserta el id del usuario a eliminar");
                    int idEliminar=scanner.nextInt();
                    scanner.nextLine();
                    metodos.eliminarUsuario(idEliminar);
                    break;
                case 0:
                    System.out.println("Gracias, se cerrara el programa");
                    break;
                default:
                    System.out.println("Dato incorrecto introduzca de nuevo el numero");
                    break;
            }


    }
    }
}