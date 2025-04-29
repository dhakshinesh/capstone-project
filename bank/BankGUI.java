package bank;

import bank.customers.BankAccount;
import bank.customers.Customer;
import bank.customers.CustomerManager;
import bank.customers.Transaction;
import bank.employees.EmployeeManager;
import bank.employees.Employee_Account;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BankGUI {
    private static CustomerManager customerManager = new CustomerManager();
    private static EmployeeManager employeeManager = new EmployeeManager();

    public static void main(String[] args) {
        // Apply a consistent look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        UIManager.put("Label.font", new Font("Arial", Font.BOLD, 14)); // For JLabel
        UIManager.put("Button.font", new Font("Arial", Font.PLAIN, 14)); // For JButton
        UIManager.put("TextPane.font", new Font("Monospaced", Font.PLAIN, 14)); // For JTextPane
        UIManager.put("ComboBox.font", new Font("Arial", Font.PLAIN, 14)); // For JComboBox

        // Create the main application frame
        JFrame frame = new JFrame("Bank Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);

        // Add a menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Options");
        JMenuItem logoutMenuItem = new JMenuItem("Logout");
        JMenuItem exitMenuItem = new JMenuItem("Exit");

        logoutMenuItem.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                frame.getContentPane().removeAll();
                frame.add(createLoginPanel(frame));
                frame.revalidate();
                frame.repaint();
            }
        });

        exitMenuItem.addActionListener(e -> System.exit(0));

        menu.add(logoutMenuItem);
        menu.add(exitMenuItem);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        // Show the login panel first
        JPanel loginPanel = createLoginPanel(frame);
        frame.add(loginPanel);

        // Set the frame visibility to true
        frame.setVisible(true);
    }

    private static JPanel createLoginPanel(JFrame frame) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel roleLabel = new JLabel("Select Role:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(roleLabel, gbc);

        String[] roles = {"Customer", "Employee"};
        JComboBox<String> roleComboBox = new JComboBox<>(roles);
        gbc.gridx = 1;
        panel.add(roleComboBox, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(15);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(loginButton, gbc);

        JLabel messageLabel = new JLabel("");
        gbc.gridy = 4;
        panel.add(messageLabel, gbc);

        // Add action listener for the login button
        loginButton.addActionListener(e -> {
            String role = (String) roleComboBox.getSelectedItem();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (role.equals("Customer")) {
                Customer customer = customerManager.login(username, password);
                if (customer != null) {
                    messageLabel.setText("Login successful!");
                    switchToPanel(frame, createCustomerPanel(customer));
                } else {
                    messageLabel.setText("Invalid customer credentials.");
                }
            } else if (role.equals("Employee")) {
                Employee_Account employeeAccount = employeeManager.login(username, password);
                if (employeeAccount != null) {
                    messageLabel.setText("Login successful!");
                    switchToPanel(frame, createEmployeePanel());
                } else {
                    messageLabel.setText("Invalid employee credentials.");
                }
            }
        });

        return panel;
    }

    private static JPanel createCustomerPanel(Customer customer) {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel customerLabel = new JLabel("Customer Portal", SwingConstants.CENTER);
        customerLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(customerLabel, BorderLayout.NORTH);

        JTextPane displayPane = new JTextPane();
        displayPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayPane);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton viewInfoButton = new JButton("View Info");
        JButton viewAccountsButton = new JButton("View Accounts");
        JButton createTransactionButton = new JButton("Create Transaction");
        JButton viewTransactionsButton = new JButton("View Transactions");
        JButton logoutButton = new JButton("Logout");

        buttonPanel.add(viewInfoButton);
        buttonPanel.add(viewAccountsButton);
        buttonPanel.add(createTransactionButton);
        buttonPanel.add(viewTransactionsButton);
        buttonPanel.add(logoutButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners for buttons
        viewInfoButton.addActionListener(e -> {
            clearAndAppendText(displayPane, "Customer Information:\n", Color.BLUE, true);
            appendText(displayPane, customerManager.displayCustomerInfo(customer), Color.BLACK, false);
        });

        viewAccountsButton.addActionListener(e -> {
            clearAndAppendText(displayPane, "Customer Accounts:\n", Color.BLUE, true);
            for (String accountInfo : customerManager.displayAccounts(customer)) {
                appendText(displayPane, accountInfo + "\n", Color.BLACK, false);
            }
        });

        createTransactionButton.addActionListener(e -> {
            String fromAccountNumber = JOptionPane.showInputDialog("Enter From Account Number:");
            String toAccountNumber = JOptionPane.showInputDialog("Enter To Account Number:");
            String amountStr = JOptionPane.showInputDialog("Enter Amount:");
            String pin = JOptionPane.showInputDialog("Enter PIN:");

            try {
                double amount = Double.parseDouble(amountStr);

                if (customerManager.createtransaction(fromAccountNumber, toAccountNumber, amount, pin)) {
                    clearAndAppendText(displayPane, "Transaction Successful!\n", Color.GREEN, true);
                    appendText(displayPane, "From Account: " + fromAccountNumber + "\n", Color.BLACK, false);
                    appendText(displayPane, "To Account: " + toAccountNumber + "\n", Color.BLACK, false);
                    appendText(displayPane, "Amount: " + amount + "\n", Color.BLACK, false);
                } else {
                    clearAndAppendText(displayPane, "Failed to create transaction!", Color.RED, true);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(panel, "Invalid amount entered. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        viewTransactionsButton.addActionListener(e -> {
            JComboBox<String> accountDropdown = new JComboBox<>();
            for (BankAccount account : CustomerManager.fetch_bankAccounts(customer)) {
                accountDropdown.addItem(account.accountNumber);
            }

            int result = JOptionPane.showConfirmDialog(panel, accountDropdown, "Select Account", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                String selectedAccount = (String) accountDropdown.getSelectedItem();
                if (selectedAccount != null) {
                    clearAndAppendText(displayPane, "Transactions for Account: " + selectedAccount + "\n", Color.BLUE, true);
                    ArrayList<Transaction> transactions = customerManager.displayTransactions(selectedAccount);
                    if (transactions.isEmpty()) {
                        appendText(displayPane, "No transactions found for this account.\n", Color.RED, false);
                    } else {
                        System.out.println("YES!");
                        for (Transaction transaction : transactions) {
                            System.out.println(selectedAccount+transaction.FromaccountNumber);
                            if(transaction.FromaccountNumber.equals(selectedAccount)){
                                appendText(displayPane, " Transaction ID: "+transaction.transactionID + "\n", Color.RED, true);
                                appendText(displayPane, " From account number: "+transaction.FromaccountNumber + "\n", Color.RED, false);
                                appendText(displayPane, " To account number: "+transaction.ToaccountNumber + "\n", Color.RED, false);
                                appendText(displayPane," Amount: "+ transaction.amount + "\n", Color.RED, true);
                                appendText(displayPane," Date: "+ transaction.date + "\n", Color.RED, false);
                                appendText(displayPane, "\n", Color.BLACK, false);
                            }
                            else{
                                appendText(displayPane," Transaction ID: "+ transaction.transactionID + "\n", Color.BLACK, true);
                                appendText(displayPane," From account number: "+transaction.FromaccountNumber + "\n", Color.BLACK, false);
                                appendText(displayPane, " To account number: "+transaction.ToaccountNumber + "\n", Color.BLACK, true);
                                appendText(displayPane," Amount: "+ transaction.amount + "\n", Color.BLACK, false);
                                appendText(displayPane," Date: "+ transaction.date + "\n", Color.BLACK, false);
                                appendText(displayPane, "\n", Color.BLACK, false);
                            }
                            
                        }
                    }
                }
            }
        });

        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(panel, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                switchToPanel((JFrame) SwingUtilities.getWindowAncestor(panel), createLoginPanel((JFrame) SwingUtilities.getWindowAncestor(panel)));
            }
        });

        return panel;
    }

    private static JPanel createEmployeePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JLabel employeeLabel = new JLabel("Employee Portal", SwingConstants.CENTER);
        employeeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(employeeLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton addCustomerButton = new JButton("Add Customer");
        JButton addAccountButton = new JButton("Add Account");
        JButton viewCustomersButton = new JButton("View Customers");
        JButton viewTransactionsButton = new JButton("View Transactions");
        JButton logoutButton = new JButton("Logout");

        buttonPanel.add(addCustomerButton);
        buttonPanel.add(addAccountButton);
        buttonPanel.add(viewCustomersButton);
        buttonPanel.add(viewTransactionsButton);
        buttonPanel.add(logoutButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        JTextPane displayPane = new JTextPane();
        displayPane.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayPane);
        panel.add(scrollPane, BorderLayout.CENTER);

        addCustomerButton.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("Enter Customer Name:");
            String email = JOptionPane.showInputDialog("Enter Customer Email:");
            String phone = JOptionPane.showInputDialog("Enter Customer Phone:");
            String address = JOptionPane.showInputDialog("Enter Customer Address:");
            String password = JOptionPane.showInputDialog("Enter Customer Password:");
            String customerID = customerManager.addCustomer(name, email, phone, address, password);
            JOptionPane.showMessageDialog(panel, "Customer added with ID: " + customerID);
        });

        addAccountButton.addActionListener(e -> {
            String customerID = JOptionPane.showInputDialog("Enter Customer ID:");
            Customer customer = customerManager.find_Customer(customerID);
            if (customer != null) {
                String accountNumber = JOptionPane.showInputDialog("Enter Account Number:");
                double balance = Double.parseDouble(JOptionPane.showInputDialog("Enter Initial Balance:"));
                String accountType = JOptionPane.showInputDialog("Enter Account Type:");
                String password = JOptionPane.showInputDialog("Enter Account Password:");
                boolean success = customerManager.addAccount(customer, accountNumber, balance, accountType, password);
                if (success) {
                    JOptionPane.showMessageDialog(panel, "Account added successfully.");
                } else {
                    JOptionPane.showMessageDialog(panel, "Failed to add account.");
                }
            } else {
                JOptionPane.showMessageDialog(panel, "Customer not found.");
            }
        });

        viewCustomersButton.addActionListener(e -> {
            clearAndAppendText(displayPane, "All Customers:\n", Color.BLUE, true);
            ArrayList<Customer> customers = customerManager.displayCustomers();
            for (Customer customer : customers) {
                appendText(displayPane, customer.display_info() + "\n", Color.BLACK, false);
            }
        });

        viewTransactionsButton.addActionListener(e -> {
            clearAndAppendText(displayPane, "All Transactions:\n", Color.BLUE, true);
            ArrayList<Transaction> transactions = customerManager.displayAlltransactions();
            for (Transaction transaction : transactions) {
                appendText(displayPane, transaction.display() + "\n", Color.BLACK, false);
            }
        });

        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(panel, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                switchToPanel((JFrame) SwingUtilities.getWindowAncestor(panel), createLoginPanel((JFrame) SwingUtilities.getWindowAncestor(panel)));
            }
        });

        return panel;
    }

    private static void switchToPanel(JFrame frame, JPanel panel) {
        frame.getContentPane().removeAll();
        frame.add(panel);
        frame.revalidate();
        frame.repaint();
    }

    private static void clearAndAppendText(JTextPane textPane, String text, Color color, boolean bold) {
        textPane.setText("");
        appendText(textPane, text, color, bold);
    }

    private static void appendText(JTextPane textPane, String text, Color color, boolean bold) {
        StyledDocument doc = textPane.getStyledDocument();
        Style style = textPane.addStyle("Style", null);
        StyleConstants.setForeground(style, color);
        StyleConstants.setBold(style, bold);
        try {
            doc.insertString(doc.getLength(), text, style);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}