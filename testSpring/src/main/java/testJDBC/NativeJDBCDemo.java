package testJDBC;

import java.sql.*;

public class NativeJDBCDemo {
    private String url;
    private String username;
    private String password;


    /**
     * create table user(
     * id int NOT NULL PRIMARY KEY auto_increment,
     * name VARCHAR(50) ,
     * age int
     * ) DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
     *
     * @param args
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        NativeJDBCDemo nativeJDBCDemo = new NativeJDBCDemo();
        nativeJDBCDemo.setUrl("jdbc:mysql://localhost:3307/test?serverTimezone=Asia/Shanghai&characterEncoding=utf8");
        nativeJDBCDemo.setUsername("root");
        nativeJDBCDemo.setPassword("admin");
        //1. 加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        //2. 获得数据库连接
        Connection conn = DriverManager.getConnection(nativeJDBCDemo.getUrl(), nativeJDBCDemo.getUsername(), nativeJDBCDemo.getPassword());
        //3.操作数据库，实现增删改查
        //预编译SQL，减少sql执行
        String sql = "INSERT INTO user (name, age) VALUES ('qiwen', 22)";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.executeUpdate();

        ResultSet rs2 = stmt.executeQuery("SELECT * FROM user");
        //如果有数据，rs.next()返回true
        while (rs2.next()) {
            System.out.println(rs2.getString("name") + " 年龄：" + rs2.getInt("age"));
        }
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
