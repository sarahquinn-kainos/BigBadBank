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
        System.out.println("\n" +
                "+======================== MAIN MENU ========================+\n" +
                "| Please select from the following options:                 |\n" +
                "| - - - - - - - - - - - - - - - - - - - - - - - - - - - - - |\n" +
                "| (1) Open a new account                                    |\n" +
                "| (2) Check Balance                                         |\n" +
                "| (0) EXIT                                                  |\n" +
                "+===========================================================+\n" );
        while (!correctInput){
            Scanner scanner = new Scanner(System.in);
            menuSelection = scanner.nextLine();
            if (menuSelection.equals("1") || menuSelection.equals("2")|| menuSelection.equals("0")){
                correctInput = true;
            } else{
                System.out.println("\nPlease enter an acceptable input from the options in the menu\n");
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
            case 0:
                System.out.println("\n Good Bye!\n");
                System.exit(0);
        }
    }

    public static void newAccount(){
        String firstName, surname, address, DOB, gender, email;
        int id, telephoneNo;
        double deposit = 0;
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("\nDo you already have an account with us? (y/n)");
        String response = scanner2.nextLine();
        if (response.toLowerCase().equals("y") || response.toLowerCase().equals("yes")){
            System.out.println("\nPlease enter your Customer Id");
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
                    System.out.println("\n This customer ID does not currently exist\n Exiting to Main Menu ......\n");
                    Menu();
                }
                boolean correctDeposit = false;
                while (!correctDeposit) {
                    System.out.println("\nPlease enter the deposit amount");
                    scanner2 = new Scanner(System.in);
                    deposit = scanner2.nextInt();
                    if (deposit > 1000 || deposit < 0) {
                        System.out.println("\nPlease enter a deposit amount between 0 and 1000");
                    }else{
                        correctDeposit = true;
                    }
                }
                String query = "INSERT INTO account (funds, initialDeposit, customerID) VALUES(" + deposit + ", " + deposit + ", " + id + ");";
                QueryMYSQL.dbUpdate(query);
                System.out.println("\n ACCOUNT CREATED SUCCESSFULLY!\n");
                Menu();
            }
            catch (InputMismatchException ex){
                System.out.println("\nPlease ensure that you enter a number for your id and deposit amount\n" +
                        "ACCOUNT NOT CREATED DUE TO INPUT ERROR - TRY AGAIN IF NEEDED");
                Menu();

            }

        }else if (response.toLowerCase().equals("n") || response.toLowerCase().equals("no")){

        }else{
            System.out.println("\nIncorrect selection please try again");
            newAccount();
        }

    }
}