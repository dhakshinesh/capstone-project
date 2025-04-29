package bank;
import java.util.Scanner;
import bank.employees.Employee_Account;
import bank.employees.EmployeeManager;
import bank.customers.CustomerManager;
import bank.customers.Customer;
import bank.Data.file_handler;
import bank.customers.BankAccount;


public class Bank{
    public static void main(String[] args) {
        
        //Terminal Width
        int terminalWidth = 160;
        //Initialize Scanner object:
        Scanner sc = new Scanner(System.in);

        //Menu Driven System
        //Main Loop
        //Instance of EmployeeManager class.
        EmployeeManager eManager = new EmployeeManager();
        System.out.println("-".repeat(terminalWidth)); 

        while(true){
            System.out.println("Choose your role (1.Bank Employee, 2.Bank Customer)");
            System.out.println("3. Exit");
            System.out.print("->");//Indication for user to enter an input
            int user_input;
            try{
                user_input =  sc.nextInt();
                sc.nextLine(); // Consume the newline character left by nextInt
            }
            catch (Exception x){
                user_input = -1;
                sc.next();
                System.out.println("Error : Enter a Number");
            }

            if (user_input == 1){
                System.out.println("-".repeat(terminalWidth));
                while (true) {
                    /// PAGE : Employee Login/Signup ///

                    //Create an Account
                    System.out.println("1. Create an Account");
                    //Login to an Account
                    System.out.println("2. Login");
                    //Go back to home page
                    System.out.println("3. Go Back");
                    System.out.print("->");//Indication for user to enter an input
                    try{
                        user_input = sc.nextInt();
                        sc.nextLine(); // Consume the newline character left by nextInt
                    }
                    catch (Exception x){
                        user_input = -1;
                        sc.next();
                        System.out.println("Error : Enter a Number");
                    }

                    //Check if user wants to go back
                    if (user_input == 3){break;}
                    //Switch case:
                    switch (user_input){
                        //Create an employee account. parameters(empID,name,designation,salary)
                        case 1:
                            System.out.println("Enter the following details : ");
                            System.out.print("Employee ID : ");
                            System.out.print("Name, Designation, Salary (comma-separated): ");
                            String[] details = sc.nextLine().split(",");
                            String name = details[0].trim();
                            String designation = details[1].trim();
                            double salary = Double.parseDouble(details[2].trim());
                            Employee_Account emp_acc = eManager.addEmployee(name, designation, salary);
                            System.out.println("Account Created Successfully!");
                            //Create credentials to employee account. parameters(username, password)
                            System.out.println("Create username and password");
                            //Set username and password to employee account.
                            while (true) {
                                System.out.print("Username : ");
                                String username = sc.nextLine();
                                System.out.print("Password : ");
                                String password = sc.nextLine();
                                boolean result = eManager.setAccount_credentials(emp_acc, username, password);
                                if (result){
                                    System.out.println("Username and Password set successfully!");
                                    break;
                                }
                                else{
                                    System.out.println("Error : Username or Password not set. Please try again.");
                                }
                            }
                            break;
                        case 2:
                            //Login to Bank employee account. parameters(username, password)
                            System.out.println("Login to an Account : ");
                            System.out.print("Username : ");
                            String username = sc.nextLine();
                            System.out.print("Password : ");
                            String password = sc.nextLine();
                            Employee_Account emp = eManager.login(username, password);
                            if (emp != null) {
                                //Login successful, show employee details
                                emp.display();
                                //SHOW LOGIN PAGE IF LOGIN SUCCESSFUL
                                System.out.println("-".repeat(terminalWidth));
                                //Give access to Customer Mabager
                                CustomerManager cManager = new CustomerManager();
                                while (true) {
                                    //Employee Dashboard
                                    System.out.println("1. View Employee Details");
                                    System.out.println("2. Create a Customer");
                                    System.out.println("3. Create an Account for Customer");
                                    System.out.println("4. Logout");
                                    System.out.print("->");//Indication for user to enter an input
                                    user_input = sc.nextInt();
                                    sc.nextLine(); // Consume the newline character left by nextInt
                                    
                                    //Check if user wants to go back
                                    if (user_input == 4){break;}

                                    //Switch case:
                                    switch (user_input){
                                        case 1:
                                            emp.display();
                                            break;
                                        case 2:
                                            //View customer details
                                            //cManager.createCustomer();
                                            System.out.println("Enter Customer Details (Name, Email, Phone, Address, Password - comma-separated): ");
                                            String[] custDetails = sc.nextLine().split(",");
                                            String custname = custDetails[0].trim(), custemail = custDetails[1].trim(), 
                                                   custphone = custDetails[2].trim(), custaddress = custDetails[3].trim()
                                                    , cust_password = custDetails[4].trim();
                                            String customerID = cManager.addCustomer(custname, custemail, custphone, custaddress, cust_password);
                                            System.out.println("Customer ID: " + customerID);

                                            break;
                                        case 3:
                                            //Create an account for customer
                                            System.out.println("Enter Account Details (Account Number, Customer ID, Account Type, Balance, password - comma-separated): ");
                                            String[] accDetails = sc.nextLine().split(",");
                                            String accountNumber = accDetails[0].trim(), customerID1 = accDetails[1].trim(), 
                                                   accountType = accDetails[2].trim(), acc_password = accDetails[4].trim();
                                            double balance = Double.parseDouble(accDetails[3].trim());
                                            //Get customer from customer ID
                                            Customer cust = cManager.find_Customer(customerID1);
                                            //Create a new account for the customer
                                            boolean accountCreated = cManager.addAccount(cust, accountNumber, balance, accountType, acc_password);
                                            if (accountCreated) {
                                                System.out.println("Account created successfully!");
                                            } else {
                                                System.out.println("Error: Account creation failed.");
                                            }
                                        default:
                                            System.out.println("Given input is invalid.");
                                    }
                                }
                                //Show employee dashboard or perform other actions here
                            } else {
                                System.out.println("Login failed. Please check your credentials.");
                            }
                            break;
                        default:
                            System.out.println("Given input is invalid.");
                    }
                }
            }
            else if (user_input == 2){
                /// PAGE : Customer Login/Signup ///
                System.out.println("-".repeat(terminalWidth));
                while (true) {
                    //Login to an Account
                    System.out.println("1. Login");
                    //Go back to home page
                    System.out.println("2. Go Back");
                    System.out.print("->"); //Indication for user to enter an input
                    user_input = sc.nextInt();
                    sc.nextLine(); // Consume the newline character left by nextInt

                    //Check if user wants to go back
                    if (user_input == 2){break;}
                    //Switch case:
                    switch (user_input){
                        case 1:
                            //Login to Bank customer account. parameters(username, password)
                            System.out.println("Login to an Account : ");
                            System.out.print("CustomerID : ");
                            String cusID = sc.nextLine();
                            System.out.print("Password : ");
                            String password = sc.nextLine();
                            //Login to customer account
                            CustomerManager cManager_cust = new CustomerManager();
                            Customer cust = cManager_cust.login(cusID, password);
                            if (cust != null) {
                                //Login successful, show customer details
                                cManager_cust.displayCustomerInfo(cust);
                                //SHOW LOGIN PAGE IF LOGIN SUCCESSFUL
                                System.out.println("-".repeat(terminalWidth));
                                while (true) {
                                    //Customer Dashboard
                                    System.out.println("1. View Customer Details");
                                    System.out.println("2. View Accounts");
                                    System.out.println("3. Logout");
                                    System.out.print("->");//Indication for user to enter an input
                                    user_input = sc.nextInt();
                                    sc.nextLine(); // Consume the newline character left by nextInt

                                    //Check if user wants to go back
                                    if (user_input == 3){break;}

                                    //Switch case:
                                    switch (user_input){
                                        case 1:
                                        cManager_cust.displayCustomerInfo(cust);
                                            break;
                                        case 2:
                                            //Display all accounts of the customer
                                            System.out.println("Customer Accounts: ");
                                            cManager_cust.displayAccounts(cust);
                                            break;
                                        default:
                                            System.out.println("Given input is invalid.");
                                    }
                                }
                            }
                            else if(user_input == 3){
                                System.out.println("Thank you for using our Bank Services, Come back later!");
                                sc.close();
                                System.exit(0);
                            }
                            else{
                                System.out.println("Given input is invalid.");
                            }
                        }
                }
            }
        }
    }
}