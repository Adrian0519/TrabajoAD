import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Metodos  {
    private static Connection posSQL=SinglettonPosSQL.getInstance();
    private static Connection mySQL=SinglettonMySQL.getInstance();
    public void crearCategoria(String nombreCategoria) throws SQLException {
        String countSQL="select Count (*) from categorias where id_categoria is not null";
        String senteciaSQL="Insert into categorias (id_categoria, nombre_categoria) values (?, ?)";
        PreparedStatement preparedStatement;
        ResultSet resultSet;
        try {
            preparedStatement= posSQL.prepareStatement(countSQL);
            resultSet=preparedStatement.executeQuery();
            int nuevoID=1;
            if (resultSet.next()){
                int cantidadid=resultSet.getInt(1);
                nuevoID=cantidadid+1;
            }
            preparedStatement.close();
            preparedStatement= posSQL.prepareStatement(senteciaSQL);
            preparedStatement.setInt(1,nuevoID);
            preparedStatement.setString(2,nombreCategoria);
            int comprobacion= preparedStatement.executeUpdate();
            if (comprobacion>0){
                System.out.println("La categoria " + nombreCategoria + " con id " + nuevoID + " se agrego correctamente");
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
public void eliminarProveedor(int id) throws SQLException {
        String senciaSQL="Delete  from proveedores where id_proveedor= ? ";
        PreparedStatement preparedStatement;
        try {
            preparedStatement= posSQL.prepareStatement(senciaSQL);
            preparedStatement.setInt(1,id);
            int comprobar=0;
            if (comprobar>=1){
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
