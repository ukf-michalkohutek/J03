package AdressBookSQLite;

import java.sql.*;

public class SQLiteJDBC {

    public SQLiteJDBC() {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = getConnection();
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String createTable = "CREATE TABLE IF NOT EXISTS person" +
                    " (lastname  TEXT    NOT NULL, " +
                    " firstname  TEXT    NOT NULL, " +
                    " email  TEXT    NOT NULL)";
            stmt.executeUpdate(createTable);
            stmt.close();
            c.close();

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println("Table created successfully");
    }

    public Connection getConnection() {
        String url = "jdbc:sqlite:AddressBook.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void insertPerson(String lastName, String firstName, String email) {
        try {
            PreparedStatement preparedStatement;
            Connection connection = this.getConnection();
            String sqlInsert = "INSERT INTO person (lastName, firstName, email)" +
                    "VALUES (?,?,?)";
            preparedStatement = connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, lastName);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, email);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void removePerson(Person p) {
        try {
            PreparedStatement preparedStatement;
            Connection connection = this.getConnection();
            String sqlRemove = "delete from person where lastName = ? and firstName = ? and email = ?";

            preparedStatement = connection.prepareStatement(sqlRemove);
            preparedStatement.setString(1, p.getLastName());
            preparedStatement.setString(2, p.getFirstName());
            preparedStatement.setString(3, p.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updatePerson(Person old, Person updated) {
        try {
            PreparedStatement preparedStatement;
            Connection connection = this.getConnection();
            String sqlRemove = "update person set lastName = ?, firstName = ?, email = ? " +
                    "where lastName = ? and firstName = ? and email = ?";
            preparedStatement = connection.prepareStatement(sqlRemove);
            preparedStatement.setString(1, updated.getLastName());
            preparedStatement.setString(2, updated.getFirstName());
            preparedStatement.setString(3, updated.getEmail());
            preparedStatement.setString(4, old.getLastName());
            preparedStatement.setString(5, old.getFirstName());
            preparedStatement.setString(6, old.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public ResultSet getResultSet() throws SQLException {
        String query = "SELECT * FROM person";
        ResultSet resultSet = this.getConnection().createStatement().executeQuery(query);
        return resultSet;

    }

}