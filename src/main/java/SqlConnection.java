import java.sql.*;

public class SqlConnection {

    private static final String URL = "jdbc:sqlserver://178.128.246.22:1433;databaseName=sustainability;";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "D24admin";

    public static Connection getConnection() throws Exception
    {
        Connection con = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return con;
    }
}
