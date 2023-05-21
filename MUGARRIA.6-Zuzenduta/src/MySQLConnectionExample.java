import java.sql.*;

public class MySQLConnectionExample {

    private Connection conexion;
    private String url = "jdbc:mysql://localhost/programazioa";
    private final static String usuario = "selin";
    private final static String contraseña = "1234";

    public MySQLConnectionExample() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, usuario, contraseña);

            if (conexion != null && !conexion.isClosed()) {
                System.out.println("Conexión exitosa a la base de datos");
            } else {
                System.out.println("No se pudo establecer la conexión a la base de datos");
            }

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al establecer la conexión: " + e.getMessage());
        }
    }

    public ResultSet select(String consulta) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = this.conexion.createStatement();
            resultSet = statement.executeQuery(consulta);
        } catch (SQLException e) {
            throw e;
        }
        return resultSet;
    }
}
