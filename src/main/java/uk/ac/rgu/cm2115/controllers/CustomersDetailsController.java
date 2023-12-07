package uk.ac.rgu.cm2115.controllers;

import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import uk.ac.rgu.cm2115.AppState;
import uk.ac.rgu.cm2115.Controller;
import uk.ac.rgu.cm2115.MainApp;
import uk.ac.rgu.cm2115.models.BankAccount;
import uk.ac.rgu.cm2115.models.BasicAccount;
import uk.ac.rgu.cm2115.models.Customer;
import uk.ac.rgu.cm2115.models.CustomerType;
import uk.ac.rgu.cm2115.models.ISA;
import uk.ac.rgu.cm2115.models.RewardAccount;

public class CustomersDetailsController extends Controller<Customer> {

    @FXML
    private ListView<BankAccount> customerAccounts;

    @FXML
    private Button viewAccountDetailsButton;

    @FXML
    private Button openNewAccountButton;

    @FXML
    private Button returnToAccountManagerButton;

    @FXML
    private TextField customerName;

    @FXML
    private TextField customerAddress;

    @FXML
    private ChoiceBox<CustomerType> customerType;

    @FXML
    public void initialize() {
        // initialize() is called when the scene is loaded
    }

    @FXML
    public void updateCustomerDetails() {
        if (customerName.getText().isEmpty() || customerAddress.getText().isEmpty()
                || customerType.getValue() == null) {
            showAlert("Error", "Missing Fields", "Please enter all fields.");
            return;
        }

        Customer selectedCustomer = (Customer) AppState.getInstance().get("customer");

        selectedCustomer.setName(customerName.getText());
        selectedCustomer.setAddress(customerAddress.getText());
        selectedCustomer.setType(customerType.getValue());

        showAlert("Update Customer Details", "Success", "Customer details updated successfully!");
    }

    @FXML
    public void viewAccountDetails() throws IOException {
        BankAccount selectedAccount = customerAccounts.getSelectionModel().getSelectedItem();

        if (selectedAccount == null) {
            showAlert("Error", "No Account Selected", "Please select an account to view.");
            return;
        }

        AppState.getInstance().put("account", selectedAccount);

        MainApp.setScene("BankDetailsManager", new Customer());

    }

    @FXML
    public void openNewAccount() {
        Customer selectedCustomer = (Customer) AppState.getInstance().get("customer");

        // Create a custom dialog
        Dialog<BankAccount> dialog = new Dialog<>();
        dialog.setTitle("Open New Account");

        // add buttons to the dialog
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        // Create labels and fields
        TextField accountNameField = new TextField();
        TextField accountNumberField = new TextField();
        TextField sortCodeField = new TextField();

        GridPane grid = new GridPane();
        grid.add(new Label("Account Name:"), 0, 0);
        grid.add(accountNameField, 1, 0);
        grid.add(new Label("Account Number:"), 0, 1);
        grid.add(accountNumberField, 1, 1);
        grid.add(new Label("Sort Code:"), 0, 2);
        grid.add(sortCodeField, 1, 2);

        // prevent the user from entering non-numeric values
        accountNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                accountNumberField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        sortCodeField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                sortCodeField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Set the default values
        accountNameField.setText("New Account");
        accountNumberField.setText("123455670");
        sortCodeField.setText("1230");

        dialog.getDialogPane().setContent(grid);

        // Convert the result to an Account
        dialog.setResultConverter(dialogButton -> {
            BankAccount newAccount = null;
            if (dialogButton == ButtonType.OK) {
                if (selectedCustomer.getType() == CustomerType.INDIVIDUAL) {
                    BasicAccount basicAccount = new BasicAccount(accountNameField.getText());
                    basicAccount.setAccountNumber(Integer.parseInt(accountNumberField.getText()));
                    basicAccount.setSortCode(Integer.parseInt(sortCodeField.getText()));
                    basicAccount.setBalance(0);
                    newAccount = basicAccount;
                } else if (selectedCustomer.getType() == CustomerType.BUSINESS) {
                    ISA isaAccount = new ISA(accountNameField.getText());
                    isaAccount.setAccountNumber(Integer.parseInt(accountNumberField.getText()));
                    isaAccount.setSortCode(Integer.parseInt(sortCodeField.getText()));
                    isaAccount.setBalance(0);
                    newAccount = isaAccount;
                } else if (selectedCustomer.getType() == CustomerType.CHARITY) {
                    RewardAccount rewardAccount = new RewardAccount(accountNameField.getText());
                    rewardAccount.setAccountNumber(Integer.parseInt(accountNumberField.getText()));
                    rewardAccount.setSortCode(Integer.parseInt(sortCodeField.getText()));
                    rewardAccount.setBalance(0);
                    newAccount = rewardAccount;

                }
            }
            return newAccount;
        });
        Optional<BankAccount> result = dialog.showAndWait();

        result.ifPresent(account -> {
            selectedCustomer.openAccount(account);
            customerAccounts.getItems().add(account);
        });

    }

    @FXML
    public void returnToAccountManager() throws IOException {
        MainApp.setScene("AccountManager");
    }

    @Override
    public void setModel(Customer model) {
        this.model = model;
        customerAccounts.getItems().clear();
        customerAccounts.getItems().addAll(model.getAccounts());

        Customer selectedCustomer = (Customer) AppState.getInstance().get("customer");

        customerName.setText(selectedCustomer.getName());
        customerAddress.setText(selectedCustomer.getAddress());
        customerType.getItems().addAll(CustomerType.INDIVIDUAL, CustomerType.BUSINESS, CustomerType.CHARITY);
        customerType.setValue(selectedCustomer.getType()); // Set the selected value
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
