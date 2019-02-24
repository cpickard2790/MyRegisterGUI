/**
Handle RegisterTable.fxml
*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 FXML Controller class

 @author cpick_000
 */
public class RegisterTableController implements Initializable
{
    @FXML
    private TableView<CheckBookData> tableView;     // TableView
    @FXML
    private TableColumn<CheckBookData, String> checkNumCol; // check number column
    @FXML
    private TableColumn<CheckBookData, String> dateCol;     // date column
    @FXML
    private TableColumn<CheckBookData, String> transactionCol;  // transaction column
    @FXML
    private TableColumn<CheckBookData, String> withdrawCol; // withdraw column
    @FXML
    private TableColumn<CheckBookData, String> depositCol;  // deposit column
    @FXML
    private TableColumn<CheckBookData, String> balanceCol;  // balance column
    
    
    @FXML
    private TextField transactionField; // text field for transaction
    @FXML
    private TextField withdrawField;    // text field for withdraw
    @FXML
    private TextField depositField;     // text field for deposit
    @FXML
    private TextField dateField;        // test field for date
    @FXML
    private TextField checkNumField;    // text field for check number
    
    // Create Alert object
    Alert alert = new Alert(Alert.AlertType.ERROR);
    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
    TextInputDialog dialog = new TextInputDialog("Starting Balance");

    // Create an observable list to store CheckBookData
    ObservableList<CheckBookData> checkbook = FXCollections.observableArrayList();

    Double balance = 0.0;   // Starting balance

    /**
     Initializes the controller class.
    */
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        alert2.setTitle("Instructions");
        alert2.setHeaderText("My CheckBook instructions:");
        alert2.setContentText("-Fill in Check # field with the check number.\n"
                + "\n-Fill in the date field with date of transactions. EX: 1/17\n"
                + "\n-Fill in the transaction field with a description of the "
                + "transaction.\n"
                + "\n-Fill in either the withdraw or deposit fields with the amount\n"
                + " withdrawn or deposited. One of these fields must have a value.");
        
        alert2.showAndWait();

        // Initialize the columns
        transactionCol.setCellValueFactory(new PropertyValueFactory<>("transaction"));
        withdrawCol.setCellValueFactory(new PropertyValueFactory<>("withdraw"));
        depositCol.setCellValueFactory(new PropertyValueFactory<>("deposit"));
        balanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));
        checkNumCol.setCellValueFactory(new PropertyValueFactory<>("checkNumber"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        
        tableView.setItems(checkbook); 
    }    
    
    
    @FXML
    private void addTransaction(ActionEvent event)
    {   
        // Error if withdraw and deposit fields are empty
        if (withdrawField.getText().isEmpty() && 
                depositField.getText().isEmpty())   
        {
            alert.setTitle("ERROR");
            alert.setContentText("Please enter a number in either the deposit"
                    + " or the withdraw field.");
            alert.showAndWait();
        }
        // Error if withdraw and deposit fields both have values
        else if (!depositField.getText().isEmpty() && 
                !withdrawField.getText().isEmpty())
        {
            alert.setTitle("ERROR");
            alert.setContentText("Both deposit and withdraw fields can not"
                    + " have a value.");
            
            alert.showAndWait();
            
            withdrawField.clear();
            depositField.clear();
        }
        // if deposit field is empty & withdraw not empty add withdraw transaction
        else if (depositField.getText().isEmpty())
        {
            String deposit = "";    // give deposit empty string
            
            // Get text in transactionField
            String transaction = transactionField.getText();
            
            // Get value in withdrawField
            Double withdraw = Double.parseDouble(withdrawField.getText());
            
            // Set withdraw to a String
            String stringWithdraw = Double.toString(withdraw);
            
            // Get text from checkNumField
            String checkNumber = checkNumField.getText();
            
            // Get text from dateField
            String date = dateField.getText();
            
            // Subtract withdraw from balance
            balance = balance - withdraw;
            
            // Format balance
            String addBalance = String.format("$" + "%.2f", balance);

            // Add values to checkbook
            checkbook.add(new CheckBookData(transaction, stringWithdraw, 
                    deposit, addBalance, checkNumber, date));
        
            // Clear the TextFields
            transactionField.clear();
            withdrawField.clear();
            depositField.clear();
            checkNumField.clear();
            dateField.clear();
        }
        else if (withdrawField.getText().isEmpty())
        {
            String withdraw = "";   // give withdraw empty string
            
            // Get text in transactionField
            String transaction = transactionField.getText();
            
            // Get the value in depositField
            Double deposit = Double.parseDouble(depositField.getText());
            
            // Set deposit to String
            String stringDeposit = "$" + Double.toString(deposit);
            
            // Get text from checkNumField
            String checkNumber = checkNumField.getText();
            
            // Get text from dateField
            String date = dateField.getText();
            
            // Add deposit to current balance
            balance = balance + deposit;
            
            // Format balance 
            String addBalance = String.format("$" + "%.2f", balance);
        
            // Add values to checkbook
            checkbook.add(new CheckBookData(transaction, withdraw, 
                    stringDeposit, addBalance, checkNumber, date));
        
            // Clear the TextFields
            transactionField.clear();
            withdrawField.clear();
            depositField.clear();
            checkNumField.clear();
            dateField.clear();
        }    
    }

    /**
    handleCloseApp closes the application
    @param event 
    */
    
    @FXML
    private void handleCloseApp(ActionEvent event)
    {
        Platform.exit(); 
    }
    
    /**
    handleSave save calls the writeExcel method to write
    the TableView data to csv file
    @param event
    @throws Exception 
    */

    @FXML
    private void handleSave(ActionEvent event) throws Exception
    {
        writeExcel();
    }
    
    /**
    writeExcel writes the TableView data to a csv(Excel) file
    @throws Exception 
    */
    
    public void writeExcel() throws Exception 
    {
        Writer writer = null;
        PrintWriter write = new PrintWriter(new FileWriter("balance.txt"));
        
        String bal = Double.toString(balance);
        try 
        {
            write.println(bal);
            File file = new File("data.csv");
            writer = new BufferedWriter(new FileWriter(file));
            
            
            for (CheckBookData data : checkbook) 
            {
                String text = data.getCheckNumber() + "," + data.getDate() + 
                        "," + data.getTransaction() + "," + data.getWithdraw() +
                        "," + data.getDeposit() + "," + data.getBalance() + "\n";

                writer.write(text);
            }
        } 
        catch (Exception ex) 
        {
            Logger.getLogger(RegisterTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally 
        {
            write.close();
            writer.flush();
            writer.close();
        }
    }
    
    /**
    handleOpen event loads the saved csv file into 
    the TableView
    @param event 
    */

    @FXML
    private void handleOpen(ActionEvent event)
    {
        String CsvFile = "data.csv";
        String FieldDelimiter = ",";
        BufferedReader br;
 
        try 
        {
            br = new BufferedReader(new FileReader(CsvFile));
 
            String line;
            
            while ((line = br.readLine()) != null) 
            {
                String[] data = line.split(FieldDelimiter, -1);
 
                CheckBookData check = new CheckBookData(data[2], data[3], data[4],
                        data[5], data[0], data[1]);
                checkbook.add(check);
            }
        }
        catch (IOException ex)
        {
            Logger.getLogger(RegisterTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        getBalance();
    
    }
    
    /**
    handleAbout gives a description about My Checkbook
    @param event 
    */

    @FXML
    private void handleAbout(ActionEvent event)
    {
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.setTitle(("About"));
        alert.setHeaderText("My Checkbook");
        alert.setContentText("My Checkbook is a desktop check register\n"
                + "application. As you enter transactions, My Checkbook\n"
                + "will calculate all of your deposits and withdraws.");
        
        alert.showAndWait();
    }
    
    /**
    getBalance method loads that balance.txt file to store 
    the previous balance in the balance field.
    */
    
    public void getBalance()
    {
        File file = new File("balance.txt");
        try
        {
            Scanner sc = new Scanner(file);
            
            while (sc.hasNextLine())
            {
                String bal = sc.nextLine();
                balance = Double.parseDouble(bal);
            }
        } 
        catch (FileNotFoundException ex)
        {
            Logger.getLogger(RegisterTableController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}