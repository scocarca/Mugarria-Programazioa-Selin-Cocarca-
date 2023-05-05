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
            
        } catch (ClassNotFoundException | SQLException e) {


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