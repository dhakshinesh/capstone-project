package bank.customers;
import bank.Data.file_handler;
import java.util.ArrayList;
import java.time.LocalDate;

public class CustomerManager {
    // Create a file handler object to handle file operations
    //file_handler fileHandler = new file_handler();
    // Create ArrayLists to store customers, accounts, and transactions
    ArrayList<Customer> customers = new ArrayList<>();
    ArrayList<BankAccount> accounts = new ArrayList<>();
    ArrayList<Transaction> transactions = new ArrayList<>();

    // Create a file handler object to handle file operations
    public CustomerManager() {
        // Initialize the file handler
        file_handler fileHandler = new file_handler();
        // Read customers from the file and populate the customers ArrayList
        customers = fileHandler.read_customers();
        accounts = fileHandler.read_accounts();
        transactions = fileHandler.read_transactions();
        //Intialize bnkcustomers.accounts
        for (Customer customer : customers) {
            customer.accounts = new ArrayList<>();
            for (BankAccount account : accounts) {
                if (account.bnkCustomer.customerID.equals(customer.customerID)) {
                    customer.accounts.add(account);
                }
            }
        }
    }
    
    // Add a new customer
    public String addCustomer(String name, String email, String phone, String address, String password) {
        //Generate Customer ids 
        String customerID = "CUST" + (customers.size() + 1);
        Customer newCustomer = new Customer();
        newCustomer.change_info(customerID, name, email, phone, address);
        newCustomer.set_password(password);
        customers.add(newCustomer);
        file_handler.writeObjectToFile(newCustomer, "customers.txt"); // Write customer data to file
        return customerID; // Return the generated customer ID
    }
    //Add a new account
    public boolean addAccount(Customer bnkCustomer,String accountNumber, double balance, String accountType, String password) {
        BankAccount newAccount = new BankAccount(bnkCustomer,
        accountNumber, balance, accountType);
        //set password
        if (password.length() >= 8){
            newAccount.set_password(password);
        }
        else{
            System.out.println("Password must be at least 8 characters long!");
            return false; // Password criteria not met
        }

        bnkCustomer.accounts.add(newAccount);
        accounts.add(newAccount);
        file_handler.writeObjectToFile(newAccount, "accounts.txt"); // Write customer data to file
        return true; // Account added successfully
    }

    //Create new transaction : 
    public boolean createtransaction(String FromaccountNumber,String ToaccountNumber, double amount, String pin){
        //Generate transaction ID
        System.out.println("Method accessed");
        String transactionID = "TRANSACT" + (transactions.size() + 1);
        BankAccount from_account = find_Account(FromaccountNumber);
        BankAccount to_Account = find_Account(ToaccountNumber);
        if (from_account != null && to_Account != null){
            System.out.println("accounts established");
            if (pin.equals(from_account.get_password())){
                System.out.println("Pin matched");
                String date = LocalDate.now().toString(); // Get the current date in YYYY-MM-DD 
                Transaction tran = new Transaction(transactionID, FromaccountNumber, ToaccountNumber, amount, date);
                //Change Balance of the accounts
                if (from_account.balance - amount >= 0){
                    from_account.balance -= amount;
                    to_Account.balance += amount;
                    transactions.add(tran);
                    file_handler.writeObjectToFile(tran, "transactions.txt");
                    System.out.println(transactionID+" Was succesfully processed!");
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        return false;

    }

    // Display all customers
    public ArrayList<Customer> displayCustomers() {
        return customers;
    }

    //Login to Customer account
    public Customer login(String CustomerID, String password) {
        //check if customer exists
        for (Customer cus : customers) {
            if (cus.customerID.equals(CustomerID)) {
                //check if password is correct
                    if (cus.get_password().equals(password)) {
                        return cus;
                    }
            }
        }
        // If customer not found or password is incorrect
        System.out.println("Customer not found or password is incorrect!");
        return null;
    }
    //Display customer info
    public String displayCustomerInfo(Customer bnkCustomer) {
        return bnkCustomer.display_info();
    }
    //Display all accounts
    public ArrayList<String> displayAccounts(Customer bnkCustomer) {
        ArrayList<String> Account_info = new ArrayList<>();
        for (BankAccount account : bnkCustomer.accounts){
            Account_info.add(account.display_info());
        }
        return Account_info;
    }
    //Display all Transactions of specific persn
    public ArrayList<Transaction> displayTransactions(String accountNumber) {
        //Display all transactions involving given account number
        ArrayList<Transaction> prev_transactions = new ArrayList<>();
        for(Transaction tran : transactions){
            if (tran.FromaccountNumber.equals(accountNumber) || tran.ToaccountNumber.equals(accountNumber) ){
                prev_transactions.add(tran);
            }
        }
        System.out.println(prev_transactions.size());
        return prev_transactions;
    }

    public ArrayList<Transaction> displayAlltransactions(){
        return transactions;
    }

    //Find Customer
    public Customer find_Customer(String CustomerID) {
        System.out.println("Finding Customer with ID: " + CustomerID);
        // Check if customer exists
        for (Customer cus : customers) {
            System.out.println("Checking Customer ID: " + cus.customerID);
            if (cus.customerID.equals(CustomerID)) {
                return cus;
            }
        }
        System.out.println("Customer not found!");
        return null;
    }

    public BankAccount find_Account(String accountNumber){
        for (BankAccount account : accounts){
            if (account.accountNumber.equals(accountNumber)){
                
                return account;
            }
        }
        return null;
    }

    public static ArrayList<BankAccount> fetch_bankAccounts(Customer cust){
        return cust.accounts;
    }
}
