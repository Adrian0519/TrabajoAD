import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Metodos  {
    private static Connection posSQL=SinglettonPosSQL.getInstance();
    private static Connection mySQL=SinglettonMySQL.getInstance();
    public void crearCategoria(String nombreCategoria){
        String countSQL="select Count (*) from categorias where id_categoria is not null";
        String senteciaSQL="Insert into categorias (id_categoria) (nombre_categoria) values (?, ?)";
        int id= Integer.parseInt(countSQL);
        PreparedStatement preparedStatement;
        try {
            preparedStatement= posSQL.prepareStatement(countSQL);
            ResultSet resultSet;
            resultSet=preparedStatement.getResultSet();
            if (resultSet.next()){
                resultSet.getInt(id);
            }
        }catch (SQLException e){
            System.out.println(e.toString());
        }

    }



}
