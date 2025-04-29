package bank.customers;

import java.io.Serializable;

//Classes
//Customer Class

//BankAccount Class
public class BankAccount implements Serializable{
    //Serialization
    static final long serialVersionUID = 2L; // For serialization
    public final String accountNumber;
    double balance;
    String accountType;
    private String password;
    final Customer bnkCustomer;
    
    BankAccount(Customer bnkCustomer,String accountNumber,double balance, String accountType){
        //Customer details
        this.bnkCustomer = bnkCustomer;
        //Account details
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountType = accountType;
    }

    String display_info(){
        System.out.println("Account Number :"+ accountNumber);
        System.out.println("Customer ID : "+ bnkCustomer.customerID);
        System.out.println("Balance : "+ balance);
        System.out.println("Account Type : "+ accountType);
        String info = String.format(" Account Number : %s\n Customer ID : %s \n Balance %f \n Account Type : %s \n", accountNumber, bnkCustomer.customerID, balance, accountType);
        return info;
    }
    void set_password(String password){
        this.password = password;
    }
    String get_password(){
        return password;
    }
    void forgot_password(String email, String password){
        if (bnkCustomer.email == email){
            this.password = password;
            System.out.println("Successfully updated password!");
        }
        else{
            System.out.println("Email Does not Match!");
        }
    }
    void change_info(){

    }
}




