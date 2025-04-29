package bank.Data;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import bank.customers.Customer;
import bank.customers.Transaction;
import bank.customers.BankAccount;
import bank.employees.Employee_Account;

public class file_handler {
    //Create a file handler class to handle file operations
    //This class will contain methods to read and write data to files
    //The files include:
    //1. Customers.txt
    //2. Accounts.txt
    //3. Transactions.txt
    //4. Employees.txt
    //Method to create all files if not exists
    public file_handler() {
        try {
            //Create Customers.txt file
            File customersFile = new File("bank/Data/customers.txt");
            if (customersFile.createNewFile()) {
                System.out.println("Customers file created: " + customersFile.getName());
            } else {
                System.out.println("Customers file already exists.");
            }
            
            //Create Accounts.txt file
            File accountsFile = new File("bank/Data/accounts.txt");
            if (accountsFile.createNewFile()) {
                System.out.println("Accounts file created: " + accountsFile.getName());
            } else {
                System.out.println("Accounts file already exists.");
            }
            
            //Create Transactions.txt file
            File transactionsFile = new File("bank/Data/transactions.txt");
            if (transactionsFile.createNewFile()) {
                System.out.println("Transactions file created: " + transactionsFile.getName());
            } else {
                System.out.println("Transactions file already exists.");
            }
            
            //Create Employees.txt file
            File employeesFile = new File("bank/Data/employees.txt");
            if (employeesFile.createNewFile()) {
                System.out.println("Employees file created: " + employeesFile.getName());
            } else {
                System.out.println("Employees file already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating files.");
            e.printStackTrace();
        }
    }
    //Method to write object data to file (directly store objects using writeObject)
    public static void writeObjectToFile(Object obj, String filePath) {
        try {
            // Read existing objects from the file
            ArrayList<Object> objects = new ArrayList<>();
            String path = String.format("bank/Data/%s", filePath);
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path))) {
            while (true) {
                objects.add(ois.readObject());
            }
            } catch (EOFException e) {
            // End of file reached
            } catch (IOException | ClassNotFoundException e) {
            System.out.println("An error occurred while reading existing objects.");
            }

            // Add the new object to the list
            objects.add(obj);

            // Rewrite the file with the updated list
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path))) {
            for (Object object : objects) {
                oos.writeObject(object);
            }
            }
            System.out.println("Object written to file successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while writing object to file.");
            e.printStackTrace();
        }
    }
    public ArrayList<Customer> read_customers(){
        ArrayList<Customer> customers = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("bank/Data/customers.txt"))) {
            while (true) {
                Customer customer = (Customer) ois.readObject();
                customers.add(customer);
                }
        } catch (EOFException e) {
                // End of file reached
        } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
        }
        return customers;
    }

    public ArrayList<BankAccount> read_accounts(){
        ArrayList<BankAccount> accounts = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("bank/Data/accounts.txt"))) {
            while (true) {
                BankAccount account = (BankAccount) ois.readObject();
                accounts.add(account);
                }
        } catch (EOFException e) {
                // End of file reached
        } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
        }
        return accounts;
    }

    public ArrayList<String> read_empIDs(){
        ArrayList<String> empIDs = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("bank/Data/employees.txt"))) {
            while (true) {
                Employee_Account emp = (Employee_Account) ois.readObject();
                empIDs.add(emp.getEmpID());
                }
        } catch (EOFException e) {
                // End of file reached
        } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
        }
        return empIDs;
    }

    //methode to read employees from file
    public ArrayList<Employee_Account> read_employees(){
        ArrayList<Employee_Account> employees = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("bank/Data/employees.txt"))) {
            while (true) {
                Employee_Account employee = (Employee_Account) ois.readObject();
                employees.add(employee);
                }
        } catch (EOFException e) {
                // End of file reached
        } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
        }
        return employees;
    }

    //methode to read transactions from file
    public ArrayList<Transaction> read_transactions(){
        ArrayList<Transaction> transactions = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("bank/Data/transactions.txt"))) {
            while (true) {
                Transaction transaction = (Transaction) ois.readObject();
                transactions.add(transaction);
                }
        } catch (EOFException e) {
                // End of file reached
        } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
        }
        return transactions;
    }

    //rewrite the file with the updated list of objects
    public static void rewriteFile(ArrayList<Employee_Account> objects) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("bank/Data/employees.txt"))) {
            for (Employee_Account object : objects) {
                System.out.println(object.getEmpID());
                oos.writeObject(object);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while rewriting the file.");
            e.printStackTrace();
        }
    }


    //Method to read object data and create arrayList of Objects
    //This method will be used to read data from files and create arrayList of objects



}
