package AdressBookSQLite;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;


public class FXMLTableViewController  {

    @FXML private TableView<Person> tableView;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML SQLiteJDBC sql;


    @FXML protected void initialize(){
        connectDatabase();
        populateTableView();
    }


    @FXML protected void addPerson(ActionEvent event) {
        ObservableList<Person> data = tableView.getItems();
        data.add(new Person(firstNameField.getText(), lastNameField.getText(), emailField.getText()));
        sql.insertPerson(lastNameField.getText(), firstNameField.getText(), emailField.getText());
        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
    }



    @FXML protected void connectDatabase() {
        sql = new SQLiteJDBC();
    }

    @FXML protected void populateTableView() {
        ObservableList<Person> data = tableView.getItems();
        ResultSet resultSet = null;
        try {
            resultSet = sql.getResultSet();

        while(resultSet.next()) {
            String lastName = resultSet.getString("lastname");
            String firstName = resultSet.getString("firstname");
            String email = resultSet.getString("email");
            data.add(new Person(lastName,firstName,email));
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void deletePerson(ActionEvent event) {
        ObservableList<Person> data = tableView.getItems();
        sql.deletePerson(tableView.getSelectionModel().getSelectedItem().getEmail());
        data.remove(tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItem()));
    }

    @FXML protected void Update(ActionEvent event) {
        ObservableList<Person> data = tableView.getItems();
        sql.Update(tableView.getSelectionModel().getSelectedItem().getEmail());
    }

}
