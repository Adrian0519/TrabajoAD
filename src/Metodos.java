import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Metodos  {
    private static Connection posSQL=SinglettonPosSQL.getInstance();
    private static Connection mySQL=SinglettonMySQL.getInstance();
    public void crearCategoria(String nombreCategoria) throws SQLException {
        String senteciaSQL="Insert into categorias (nombre_categoria) values (?)";
        PreparedStatement preparedStatement;
        try {
            preparedStatement= posSQL.prepareStatement(senteciaSQL);
            preparedStatement.setString(1,nombreCategoria);
            int comprobacion= preparedStatement.executeUpdate();
            if (comprobacion>0){
                System.out.println("La categoria " + nombreCategoria + " se agrego correctamente");
            }else {
                System.out.println("Se produjo un error durante la creacion ");
            }
          } catch (SQLException e) {
            System.out.println(e.toString());

            }
    }

    public void  crearNuevoProveedor(String nombreProveedor, String nif, int telefono, String email) throws SQLException {
        String sentenciaSQL="Insert into proveedores (nombre_proveedor, contacto) values (?, ROW (?, ?, ?, ?))" ;
        PreparedStatement preparedStatement;
        try {
            preparedStatement= posSQL.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1,nombreProveedor);
            preparedStatement.setString(2,nombreProveedor);
            preparedStatement.setString(3,nif);
            preparedStatement.setInt(4,telefono);
            preparedStatement.setString(5,email);
            int comprobacion= preparedStatement.executeUpdate();
            if (comprobacion>0){
                System.out.println("El provedor fue introducido de forma exitosa");
            }else {
                System.out.println("Se produjo un error al crear el provedor");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
public void eliminarProveedor(int id) throws SQLException {
        String sentenciaProducto="Delete from productos where id_proveedor = ?";
        String sentenciaAlmacen="Delete from almacenes_productos where id_producto in (select id_producto from productos where id_proveedor = ?)";
        String senciaProveedorL="Delete from proveedores where id_proveedor = ?";
        PreparedStatement preparedStatement;
        PreparedStatement preparedStatement1;
        PreparedStatement preparedStatement2;
        try {
            preparedStatement= posSQL.prepareStatement(sentenciaAlmacen);
            preparedStatement.setInt(1,id);
            preparedStatement.executeUpdate();
            preparedStatement1= posSQL.prepareStatement(sentenciaProducto);
            preparedStatement1.setInt(1,id);
            preparedStatement1.executeUpdate();
            preparedStatement2= posSQL.prepareStatement(senciaProveedorL);
            preparedStatement2.setInt(1,id);
            preparedStatement2.executeUpdate();
            int comprobar= preparedStatement.executeUpdate();
            if (comprobar>0){
                System.out.println("Se elimino de forma exitosa el proveedor");
            }else {
                System.out.println("No hay ningun proveedor con dicho id");
            }
        }catch (SQLException e){
            System.out.println(e.toString());
        }
}

public void crearUsuario(String nombre, String email, int anho_nacimiento) throws SQLException {
        String sentenciaSQL="Insertr into usuarios (nombre, email, ano_nacimiento) values (?, ?, ?)";
        PreparedStatement preparedStatement;
        try {
            preparedStatement=mySQL.prepareStatement(sentenciaSQL);
            preparedStatement.setString(1,nombre);
            preparedStatement.setString(2,email);
            preparedStatement.setInt(3,anho_nacimiento);
            int comprobacion= preparedStatement.executeUpdate();
            if (comprobacion>=1){
                System.out.println("Datos del usario insertados correctamente");
            }else {
                System.out.println("Error en la inseccion de datos");
            }
        }catch (SQLException e){
            System.out.println(e.toString());
        }
}

}
