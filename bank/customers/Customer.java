package bank.customers;
import java.util.ArrayList;
import java.io.Serializable;

public class Customer implements Serializable {
    static final long serialVersionUID = 1L; // For serialization
    String customerID;
    String name;
    String email;
    String phone;
    String address;
    private String password;
    ArrayList<BankAccount> accounts = new ArrayList<>();

    public String display_info(){
        System.out.println("CustomerID : "+customerID);
        System.out.println("Name : "+name);
        System.out.println("Email : "+email);
        System.out.println("Phone : "+phone);
        return String.format(" CustomerID : %s \n Name : %s \n Email : %s\n Phone : %s\n", customerID,name, email,phone );
    }
    void set_password(String password){
        this.password = password;
    }
    String get_password(){
        return password;
    }
    
    void change_info(String customerID, String name,String email, String phone, String address){
        this.customerID = customerID;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }
}