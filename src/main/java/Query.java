import java.sql.*;
import java.util.ArrayList;

public class Query {
    public static ArrayList dbQuery(String[] fields, String myQuery, boolean resultReturn){
        ArrayList fieldResults = new ArrayList();

        try {
            Class driver = Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost/bigBadBank", "root", "password");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(myQuery);

            while(resultSet.next() && resultReturn) {
                for (int i=0; i<fields.length; i++){
                    fieldResults.add(resultSet.getString(fields[i]));
                }

            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fieldResults;
    }

    public static int dbUpdate(String myQuery){
        int result = 0;
        try {
            Class driver = Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost/bigBadBank", "root", "password");
            Statement statement = connection.createStatement();
            result= statement.executeUpdate(myQuery);
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
