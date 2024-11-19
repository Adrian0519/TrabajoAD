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



}
