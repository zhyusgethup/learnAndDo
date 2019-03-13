package main;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("成功加载驱动");

            String url = "jdbc:mysql://127.0.0.1:3306/analysis_log?user=root&password=Ostudio123456Db?&useUnicode=true&characterEncoding=UTF8";
            connection = DriverManager.getConnection(url);
            System.out.println("成功获取连接");

            statement = connection.createStatement();
            String sql = "select * from t_balance";
            resultSet = statement.executeQuery(sql);

            resultSet.beforeFirst();
            while (resultSet.next()) {
                System.out.println(resultSet.getString(1));
            }
            System.out.println("成功操作数据库");
        } catch(Throwable t) {
            // TODO 婢跺嫮鎮婂鍌氱埗
            t.printStackTrace();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
            System.out.println("成功关闭资源");
        }

    }
}
