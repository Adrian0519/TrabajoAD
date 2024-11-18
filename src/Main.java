import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
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
                    break;
                case 0:
                    System.out.println("Gracias, se cerrara el programa");
            }


    }
    }
}