import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class QueryMYSQL {

    public static ArrayList dbQuery(String[] fields, String myQuery, boolean resultReturn){
        ArrayList fieldResult = new ArrayList();
        try {
            Class driver = Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/bigBadBank", "root", "password");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(myQuery);
            while(resultSet.next() && resultReturn) {
                for (int i=0; i<fields.length; i++){
                    String temp= resultSet.getString(fields[i]);
                    fieldResult.add(temp);
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fieldResult;
    }

    public static void viewFunds() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n" +
                "=======================================\n" +
                "  Please enter you account number:   ");
        String accountNum = scanner.nextLine();
        String[] myArray = new String[]{"id", "funds"};
        ArrayList accountFunds = new ArrayList();
        accountFunds = dbQuery(myArray, "SELECT * FROM account where id=" + accountNum, true);
        System.out.println("" +
                "\n============= View Funds ==============\n" +
                "Account_ID:         " + (accountFunds.get(0)) + "\n" +
                "Available Funds:    " + (accountFunds.get(1)) + "\n" +
                "=======================================\n");
    }
}
