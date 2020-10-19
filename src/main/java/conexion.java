import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexion {
    public Connection conectarbd() throws ClassNotFoundException, SQLException {
        Connection cn = null;
        String url= "jdbc:postgresql://localhost:5432/sistema_distribuido";
        String user= "postgres";
        String password= "1234";
        Class.forName("org.postgresql.Driver");
        cn = DriverManager.getConnection(url,user,password);
        System.out.println("CONEXIÓN ESTABLECIDA");
        return cn;
    }
}
