import java.sql.*;

/**
 * 测试jdbc链接
 */

public class test {

    private static Connection conn;

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://mini1:3306/test2?useSSL=false&serverTimezone=UTC", "root", "123456");
            Statement statement = conn.createStatement();
            ResultSet show_tables = statement.executeQuery("show tables");
            while (show_tables.next()) {
                System.out.println(show_tables.getString(1));

            }
            show_tables.close();
            conn.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
