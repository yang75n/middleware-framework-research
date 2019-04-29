package testJDBC;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import testJDBC.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class SpringJDBCDemo {

    public static void main(String[] args) {
        SpringJDBCDemo springJDBCDemo = new SpringJDBCDemo();
        springJDBCDemo.springJDBCTest();
    }


    public void springJDBCTest() {
        //初始化DataSource
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3307/test?serverTimezone=Asia/Shanghai&characterEncoding=utf8");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("admin");
        //初始化jdbcTemplate
        JdbcTemplate jdbcTemplate = new JdbcTemplate(driverManagerDataSource);
        // 查询一条数据
        User user1 = jdbcTemplate.queryForObject("select * from user where id = ?", new UserRowMapper(), 1);
        System.out.println(user1);

        // 删除
//         jdbcTemplate.update("delete from user where id = ?", 1);

        // 修改
        jdbcTemplate.update("update user set name = ? where id = ?", "春花", 1);

        // 插入
        jdbcTemplate.update("insert into user(name,age) values( '花花', 10)");

        // 方式2：查询所有的数据
        List<User> list2 = jdbcTemplate.query("select * from user", new UserRowMapper());

        for (User user : list2) {
            System.out.println(user);
        }
        // 方式1： 查询所有的数据
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from user");
        for (Map<String, Object> map : list) {
            //  map.keySet() 用于获取所有的 key
            for (String key : map.keySet()) {
                // 通过 key 获取到对应的 value 值
                System.out.print(map.get(key));
            }
            // 相当于换行效果
            System.out.println();
        }

    }

    private class UserRowMapper implements RowMapper<User> {
        // 查询的时候，有可能会返回多个数据，所有的数据都会放在 rs 结果集中
        // rounum 代表的是记录的下表值
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            System.out.println("mapRow i=" + i);
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            user.setAge(resultSet.getInt("age"));
            return user;
        }
    }
}
