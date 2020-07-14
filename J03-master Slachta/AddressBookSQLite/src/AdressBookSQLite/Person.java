package AdressBookSQLite;


import javafx.beans.property.SimpleStringProperty;

public class Person {
    private final SimpleStringProperty firstName = new SimpleStringProperty("");
    private final SimpleStringProperty lastName = new SimpleStringProperty("");
    private final SimpleStringProperty email = new SimpleStringProperty("");

    public Person() {
        this("","","");
    }

    public Person (String firstName, String lastName, String email) {
        setFirstName(firstName);
        setLastName(lastName);
        setEmail(email);
    }

    //setters
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public void setEmail(String email){
        this.email.set(email);
    }

    //getters

    public String getFirstName(){
        return this.firstName.get();
    }

    public String getLastName(){
        return this.lastName.get();
    }

    public String getEmail(){
        return this.email.get();
    }


}
