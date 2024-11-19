import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SinglettonMySQL {

    private static Connection connection;
    private final String usuario = "root";
    private final String clave = "abc123.";
    private final String url ="jdbc:mysql://localhost:3306/proyecto";

    private SinglettonMySQL(){
        try {
            this.connection = DriverManager.getConnection(url, usuario, clave);
        }catch(SQLException e) {
            System.out.println("Error al abrir la conexión");
        }
    }

    public static Connection getInstance(){
        if (SinglettonMySQL.connection == null)
            new SinglettonMySQL();
        return SinglettonMySQL.connection;
    }

}
