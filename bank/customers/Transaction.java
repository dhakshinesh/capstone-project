package bank.customers;

import java.io.Serializable;

//Transaction 
public class Transaction implements Serializable{
    static final long serialVersionUID = 4L;
    public String transactionID;
    public String FromaccountNumber;
    public String ToaccountNumber;
    public double amount;
    public String date;

    Transaction(String transactionID, String FromaccountNumber,String ToaccountNumber , double amount, String date) {
        this.transactionID = transactionID;
        this.FromaccountNumber = FromaccountNumber;
        this.ToaccountNumber = ToaccountNumber;
        this.amount = amount;
        this.date = date;
    }

    public String display() {
        return String.format(" Transaction ID : %s \n From account number : %s\n To account number : %s\n amount : %f \n date : %s \n", transactionID,FromaccountNumber,ToaccountNumber,amount,date);
    }
}