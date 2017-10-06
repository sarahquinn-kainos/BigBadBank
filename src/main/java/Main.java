import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        System.out.println("Welcome to kbank\n==============");
        Menu();

    }

    public static void Menu(){
        boolean correctInput = false;
        String menuSelection = " ";
        System.out.println("Please select from the following options:");
        System.out.println("1. Open a new account");
        System.out.println("2. Check Balance");
        while (!correctInput){
            Scanner scanner = new Scanner(System.in);
            menuSelection = scanner.nextLine();
            if (menuSelection.equals("1") || menuSelection.equals("2")){
                correctInput = true;
            } else{
                System.out.println("Please enter an acceptable input from the options in the menu");
            }
        }
        int menuSelectionInt = Integer.parseInt(menuSelection);
        switch (menuSelectionInt){
            case 1:
                newAccount();
                break;
            case 2:
                QueryMYSQL.viewFunds();
                break;
        }
    }

    public static void newAccount(){
        String firstName, surname, address, DOB, gender, email;
        int id, telephoneNo;
        double deposit = 0;
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Do you already have an account with us?\n type y or n");
        String response = scanner2.nextLine();
        if (response.toLowerCase().equals("y") || response.toLowerCase().equals("yes")){
            System.out.println("Please enter your Customer Id");
            scanner2 = new Scanner(System.in);
            try {
                String[] columns = new String[]{"id"};
                id = scanner2.nextInt();
                /*if (id < 10000000){
                    System.out.println("Please enter a 8 digit ID");
                    newAccount();
                }*/
                ArrayList<String> result = QueryMYSQL.dbQuery(columns, "SELECT id FROM customer where id = " + id, true);
                if (result.size() < 1){
                    System.out.println("This customer ID does not currently exist");
                    newAccount();
                }
                boolean correctDeposit = false;
                while (!correctDeposit) {
                    System.out.println("Please enter the deposit amount");
                    scanner2 = new Scanner(System.in);
                    deposit = scanner2.nextInt();
                    if (deposit > 1000 || deposit < 0) {
                        System.out.println("Please enter a deposit amount between 0 and 1000");
                    }else{
                        correctDeposit = true;
                    }
                }
                String query = "INSERT INTO account (funds, initialDeposit, customerID) VALUES(" + deposit + ", " + deposit + ", " + id + ");";
                QueryMYSQL.dbUpdate(query);
            }
            catch (InputMismatchException ex){
                System.out.println("Please ensure that you enter a number for your id and deposit amount");
            }

        }else if (response.toLowerCase().equals("n") || response.toLowerCase().equals("no")){

        }else{
            System.out.println("Incorrect selection please try again");
            newAccount();
        }

    }
}