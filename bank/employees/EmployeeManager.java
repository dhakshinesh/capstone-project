package bank.employees;
import java.util.ArrayList;

import bank.Data.file_handler;

public class EmployeeManager {
    private ArrayList<Employee_Account> employees = new ArrayList<>();
    private ArrayList<String> empIDs = new ArrayList<>();

    public EmployeeManager() {
        // Initialize the employee list from a file or database if needed
        file_handler fileHandler = new file_handler();
        employees = fileHandler.read_employees(); // Read employees from the file
        System.out.println("Employees size: " + employees.size());
        empIDs = fileHandler.read_empIDs(); // Read employee IDs from the file
        // For now, it's empty
    }

    //Function for checking if a string contains only alphabets
    //Accessible only in EmployeeManager File
    static boolean check_subset(String username){
        String alphabets = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMOPQRSTUVWXYZ";
        for(int i = 0 ; i< username.length(); i++){
            if (alphabets.indexOf(username.charAt(i)) < 0){
                return false;
            }
        }
        return true;
    }

    // Method to Create and add an Employee Account
    //Accessible by everyone
    public Employee_Account addEmployee(String name, String designation, double salary) {
        //Generate Employee ID
        String empID = "EMP" + (employees.size() + 1); // Simple ID generation logic
        empIDs.add(empID); // Add the new ID to the list of employee IDs
        Employee_Account newEmployee = new Employee_Account(empID, name, designation, salary);
        employees.add(newEmployee);
        //file_handler.writeObjectToFile(newEmployee, "employees.txt"); // Write employee data to file
        return newEmployee; // Return the newly created employee account
    }

    //Method to set username and password of Employee 
    //Accessible by Employee
    public boolean setAccount_credentials(Employee_Account emp_acc, String username, String password){
        try{
            //Check if username exists
            System.out.println(employees.size());
            if (employees != null) {
                for (Employee_Account emp : employees) {
                    if (emp.username != null){
                        if (emp.username.equals(username)) {
                            System.out.println("Username already exists!");
                            return false; //Username already exists
                        }
                    }
                }
            }
            if (username.length() >= 8 && check_subset(username) ){
                if (password.length() >= 8){
                    emp_acc.set_username_password(username, password);
                    //update employee account in the file
                    file_handler.rewriteFile(employees); // Write employee data to file
                    return true;
                }
                else{
                    System.out.println("Password must be at least 8 characters long!");
                }
            }
            else{
                System.out.println("Username must be at least 8 characters long and contain only alphabets!");
            }
            return false; //If username and password are not creaated due to criteria voilation
        }
        catch (Exception e){
            System.err.println("Internal Error Occured : "+e.getMessage());
            return false; //If Error occurs
        }
    } 

    public Employee_Account login(String username, String password){
        for (Employee_Account emp : employees) {
            if (emp.username.equals(username) && emp.get_password().equals(password)) {
                System.out.println("Login successful!");
                emp.display(); // Display employee details on successful login
                return emp;
            }
        }
        System.out.println("Invalid username or password!");
        return null; // Explicitly return null if no match is found
    }

    // Method to display all employees
    public void displayEmployees() {
        System.out.println("Bank Employees:");
        for (Employee_Account emp : employees) {
            emp.display();
        }
    }

    // Method to find an employee by ID
    public Employee_Account findEmployee(String empID) {
        for (Employee_Account emp : employees) {
            if (emp.getEmpID().equals(empID)) {
                return emp;
            }
        }
        return null;
    }
}
