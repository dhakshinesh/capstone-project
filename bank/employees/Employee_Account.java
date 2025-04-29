package bank.employees;

import java.io.Serializable;

//Creating an Employee account automatically creates an Employee
class Employee implements Serializable {
    private static final long serialVersionUID = 1L; // For serialization
    private String empID;
    private String name;
    private String designation;
    private double salary;

    //Set Employee Details
    public void setempID(String empID) {this.empID = empID;}
    public void setname(String name ) {this.name = name;}
    public void setdesignation(String designation) {this.designation = designation;}
    public void setSalary(double salary) {this.salary = salary;}

    //Return Employee Details : 
    public String getEmpID() { return empID; }
    public String getName() { return name; }
    public String getDesignation() { return designation; }
    public double getSalary() { return salary; }

    //View Employee's Details
    public void display() {
        System.out.println("Employee ID: " + empID);
        System.out.println("Name: " + name);
        System.out.println("Designation: " + designation);
        System.out.println("Salary: " + salary);
    }

}
public class Employee_Account extends Employee{
    public String username;
    private String password;

    public Employee_Account(String empID, String name, String designation, double salary) {
        //Initialise Customer details
        setempID(empID);
        setdesignation(designation);
        setname(name);
        setSalary(salary);
    }

    void set_username_password(String username, String password){
        this.username = username;
        this.password = password;
    }

    String get_password(){
        return password;
    }

}
