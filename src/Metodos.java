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
            throw new RuntimeException(e);
            }
    }

    public void  crearNuevoProveedor(String nombreProveedor, String nif, int telefono, String email) throws SQLException {
        String sentenciaSQL="Insert into proveedores ( nombre_proveedor, contacto values (? , ROW (?, ?, ?, ?))" ;
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


}
