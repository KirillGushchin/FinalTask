package Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.stream.Collectors;

public class DBUtils {
    Connection conn = null;
    Statement st = null;

    public DBUtils(String url, String user, String password){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            System.err.println("Something go wrong with DBDriver due to following error :" +e.getMessage());
        } catch (SQLException throwables) {
            System.err.println("Cannot to connect to DB due to following error: " + throwables.getSQLState());
        }
    }

    public ResultSet runQUERY (String scriptPath) {
        ResultSet rs = null;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(scriptPath));
            String qwery = reader.lines().collect(Collectors.joining());
            st = conn.createStatement();
            rs = st.executeQuery(qwery);
        } catch (FileNotFoundException | SQLException e) {
            System.err.println("Cannot read execute sql script due to following error : " + e.getMessage());
        }
        return rs;
    }
}