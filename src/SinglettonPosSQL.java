import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SinglettonPosSQL {
    private static Connection connection;
    private final String usuario = "postgres";
    private final String clave = "abc123.";
    private final String url ="jdbc:postgresql://localhost:5432/proyecto";

    private SinglettonPosSQL(){
        try {
            this.connection = DriverManager.getConnection(url, usuario, clave);
        }catch(SQLException sqle) {
            System.out.println("Error al abrir la conexión");
        }
    }

    public static Connection getInstance(){
        if (SinglettonPosSQL.connection == null)
            new SinglettonPosSQL();
        return SinglettonPosSQL.connection;
    }
}
