package Server;

import java.sql.*;

public class JDBC {

    private String username = "valera";

    private String password = "1234";
    private String dBaseName = "userscloudstore";

    Connection con;
    PreparedStatement preparedStatement;
    Statement statement;

    public void main(String[] args) {
        JDBC jdbc = new JDBC();
        try {
//            con = DriverManager.getConnection(
//                    "jdbc:myDriver:myDatabase",
//                    username,
//                    password);

            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            jdbc.con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/userscloudstore", "root", "1234"
            );

//            String selectUser = "Select username," +" userpassword" + "From " + "users " +
//                    "Where username =  '" + jdbc.username + "' and userpassword = '" + jdbc.password+"';";
            String selectUser = "Select username From users  Where username =  'valera'  and userpassword =  '1234';";
            jdbc.statement = jdbc.con.createStatement();
            ResultSet rs = jdbc.statement.executeQuery(selectUser);
            if (!rs.next()) {
                System.out.println("Unknow user");
            }
            while (rs.next()) {
                String str = rs.getString("username");
                System.out.println(str);
                if (str.equals(username)) {
                    System.out.format("%s  авторизован", username);
                } else System.out.println("неверное имя  пользователя  или пароль");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
