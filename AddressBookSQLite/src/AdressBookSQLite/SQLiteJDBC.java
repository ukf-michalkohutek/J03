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
            String sql = "CREATE TABLE IF NOT EXISTS person" +
                    " (lastName  TEXT    NOT NULL, " +
                    " firstName  TEXT    NOT NULL, " +
                    " email  TEXT    NOT NULL)";
            stmt.executeUpdate(sql);
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

    public void updatePerson(String lastName, String firstName, String email,
                             String newLastName, String newFirstName, String newEmail) {
        try {
            PreparedStatement preparedStatement;
            Connection connection = this.getConnection();
            String sqlUpdate = "UPDATE person SET lastName=?, firstName=?, email=? " +
                    "WHERE lastName=? AND firstName=? AND email=?";
            preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setString(1, newLastName);
            preparedStatement.setString(2, newFirstName);
            preparedStatement.setString(3, newEmail);
            preparedStatement.setString(4, lastName);
            preparedStatement.setString(5, firstName);
            preparedStatement.setString(6, email);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void deletePerson(String lastName, String firstName, String email) {
        try {
            PreparedStatement preparedStatement;
            Connection connection = this.getConnection();
            String sqlDelete = "DELETE FROM person WHERE lastName=? AND firstName=? AND email=?";
            preparedStatement = connection.prepareStatement(sqlDelete);
            preparedStatement.setString(1, lastName);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, email);
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