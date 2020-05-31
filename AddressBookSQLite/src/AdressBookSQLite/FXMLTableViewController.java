package AdressBookSQLite;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class FXMLTableViewController  {

    @FXML private TableView<Person> tableView;
    @FXML private TextField lastNameField;
    @FXML private TextField firstNameField;
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

    @FXML protected void updatePerson() {
        ObservableList<Person> data = tableView.getItems();
        Person p = tableView.getSelectionModel().getSelectedItem();
        String newLastName = (lastNameField.getText().isEmpty()) ? p.getLastName() : lastNameField.getText();
        String newFirstName = (firstNameField.getText().isEmpty()) ? p.getFirstName() : firstNameField.getText();
        String newEmail = (emailField.getText().isEmpty()) ? p.getEmail() : emailField.getText();
        sql.updatePerson(p.getLastName(), p.getFirstName(), p.getEmail(), newLastName, newFirstName, newEmail);
        data.clear(); firstNameField.clear();; lastNameField.clear(); emailField.clear();
        populateTableView();
    }

    @FXML protected void deletePerson() {
        ObservableList<Person> data = tableView.getItems();
        Person p = tableView.getSelectionModel().getSelectedItem();
        sql.deletePerson(p.getLastName(), p.getFirstName(), p.getEmail());
        data.remove(p);
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
                String lastName = resultSet.getString("lastName");
                String firstName = resultSet.getString("firstName");
                String email = resultSet.getString("email");
                data.add(new Person(lastName,firstName,email));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
