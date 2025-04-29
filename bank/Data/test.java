package bank.Data;
import bank.customers.Customer;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.EOFException;
import java.util.ArrayList;


public class test {
    //Read objects in customers.txt file and store them in an ArrayList of Customer objects
    //Method to read objects from file and return an ArrayList of Customer objects
            //Read customers.txt
            static ArrayList<Customer> read_customers(){
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
    public static void main(String[] args) {
        //Display details of customers
        read_customers().get(0).display_info();

        //Loop through all customers and display their details
        
    }
}
