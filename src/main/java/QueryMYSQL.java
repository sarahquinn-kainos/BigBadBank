import com.mysql.jdbc.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class QueryMYSQL {

    public static ArrayList dbQuery(String[] fields, String myQuery, boolean resultReturn) {
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
            while (resultSet.next() && resultReturn) {
                for (int i = 0; i < fields.length; i++) {
                    fieldResult.add(resultSet.getString(fields[i]));
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fieldResult;
    }

    public static int dbUpdate(String myQuery) {
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
            result = statement.executeUpdate(myQuery);
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void viewFunds() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n" +
                "=======================================\n" +
                "  Please enter you account number:   ");
        String accountNum = scanner.nextLine();
        if (StringUtils.isStrictlyNumeric(accountNum) != true) {
            System.out.println("\n" +
                    "+============ERROR============+\n" +
                    "| not a valid account ID, try |\n" +
                    "| again or press 0 to exit to |\n" +
                    "| the main menu.              |\n" +
                    "+=============================+\n");
            viewFunds();
        } else if (accountNum.equals("0")) {
            Main.Menu();
        } else {
            String[] myArray = new String[]{"id", "funds"};
            ArrayList accountFunds = new ArrayList();
            accountFunds = dbQuery(myArray, "SELECT * FROM account where id=" + accountNum, true);
            int validResult = accountFunds.size();
            if (validResult == 2) {
                System.out.println("" +
                        "\n============= View Funds ==============\n" +
                        "Account_ID:         " + (accountFunds.get(0)) + "\n" +
                        "Available Funds:    " + (accountFunds.get(1)) + "\n" +
                        "=======================================\n" +
                        "\n Returning to Main Menu .......\n");
                Main.Menu();

            } else {
                System.out.println("\n" +
                        "+============ERROR============+\n" +
                        "| not a valid account ID, try |\n" +
                        "| again or press 0 to exit to |\n" +
                        "| the main menu.              |\n" +
                        "+=============================+\n");
                viewFunds();
            }
        }
    }
}