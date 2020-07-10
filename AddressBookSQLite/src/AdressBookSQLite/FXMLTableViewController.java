package AdressBookSQLite;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.sql.ResultSet;
import java.sql.SQLException;


public class FXMLTableViewController  {

    @FXML private TableView<Person> tableView;
    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML SQLiteJDBC jdbc;


    @FXML protected void initialize(){
        connectDatabase();
        populateTableView();
    }

    //TODO: Add remove person functionality - https://www.sqlitetutorial.net/sqlite-delete/
    //TODO: Add update person functionality - https://www.sqlitetutorial.net/sqlite-update/

    @FXML protected void addPerson(ActionEvent event) {
        ObservableList<Person> data = tableView.getItems();

        data.add(new Person(firstNameField.getText(), lastNameField.getText(), emailField.getText()));
        jdbc.insertPerson(lastNameField.getText(), firstNameField.getText(), emailField.getText());

        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
    }

    @FXML protected void removePerson(){
        Person p = tableView.getItems().get(tableView.getSelectionModel().getSelectedIndex());
        tableView.getItems().remove(p);
        jdbc.removePerson(p);
    }

    @FXML protected void updatePerson(){
        Person nova = new Person(lastNameField.getText(), firstNameField.getText(), emailField.getText());
        Person stara = tableView.getSelectionModel().getSelectedItem();

        int i = tableView.getSelectionModel().getSelectedIndex();
        tableView.getItems().set(i, nova);
        jdbc.updatePerson(nova, stara);

        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
    }

    @FXML protected void connectDatabase() {
        jdbc = new SQLiteJDBC();
    }

    @FXML protected void populateTableView() {
        ObservableList<Person> data = tableView.getItems();
        ResultSet resultSet = null;
        try {
            resultSet = jdbc.getResultSet();

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

}
