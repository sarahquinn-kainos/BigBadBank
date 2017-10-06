import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        //String[] myArray = new String[] {"fname", "sname"};
        //dbQuery(myArray, "SELECT * from customer", true);
        viewFunds();


    }

    public static void viewFunds(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n" +
                "=======================================\n" +
                "  Please enter you account number:   ");
        String accountNum = scanner.nextLine();
        String[] myArray = new String[] {"id","funds"};
        dbQuery(myArray, "SELECT * FROM account where id=1",true);



        //System.out.println(accountNum);
    }

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
                    System.out.println(fields[i]);
                    String temp= resultSet.getString(fields[i]);
                    fieldResult.add(temp);
                    System.out.println(fieldResult);
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fieldResult;
    }


}