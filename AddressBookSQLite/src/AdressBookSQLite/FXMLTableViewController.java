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
    @FXML SQLiteJDBC sqlConnector;


    @FXML protected void initialize(){
        connectDatabase();
        populateTableView();
    }

    //TODO: Add remove person functionality - https://www.sqlitetutorial.net/sqlite-delete/

    @FXML protected void removePerson(ActionEvent event) {
        ObservableList<Person> data = tableView.getItems();

        Person person = tableView.getSelectionModel().getSelectedItem();
        data.remove(person);
        sqlConnector.deletePerson(person.getEmail());

        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
    }

    //TODO: Add update person functionality - https://www.sqlitetutorial.net/sqlite-update/

    @FXML protected void updatePerson(ActionEvent event) {
        ObservableList<Person> data = tableView.getItems();

        Person person = tableView.getSelectionModel().getSelectedItem();
        int id = data.indexOf(person);
        String old_email = person.getEmail();
        person.setFirstName(firstNameField.getText());
        person.setLastName(lastNameField.getText());
        person.setEmail(emailField.getText());
        data.set(id,person);
        sqlConnector.updatePerson(lastNameField.getText(), firstNameField.getText(), emailField.getText(), old_email);

        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
    }


    @FXML protected void addPerson(ActionEvent event) {
        ObservableList<Person> data = tableView.getItems();

        data.add(new Person(firstNameField.getText(), lastNameField.getText(), emailField.getText()));
        sqlConnector.insertPerson(lastNameField.getText(), firstNameField.getText(), emailField.getText());

        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
    }



    @FXML protected void connectDatabase() {
        sqlConnector = new SQLiteJDBC();
    }

    @FXML protected void populateTableView() {
        ObservableList<Person> data = tableView.getItems();
        ResultSet resultSet = null;
        try {
            resultSet = sqlConnector.getResultSet();

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
