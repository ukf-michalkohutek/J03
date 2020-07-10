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
                    " (lastname  TEXT    NOT NULL, " +
                    " firstname  TEXT    NOT NULL, " +
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
            String sqlInsert = "INSERT INTO person (lastName, firstName, email) VALUES (?,?,?)";
            executeAsPreparedStatement(sqlInsert, new Person(lastName, firstName, email));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public ResultSet getResultSet() throws SQLException {
        String query = "SELECT * FROM person";
        ResultSet resultSet = this.getConnection().createStatement().executeQuery(query);
        return resultSet;
    }

    public void removePerson(Person p) {
        try {
            String query = "DELETE FROM person WHERE lastName = ? AND firstName = ? AND email = ?";
            executeAsPreparedStatement(query, p);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    private void executeAsPreparedStatement(String query, Person p) throws SQLException {
        Connection connection = this.getConnection();
        PreparedStatement preparedStatement;
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, p.getLastName());
        preparedStatement.setString(2, p.getFirstName());
        preparedStatement.setString(3, p.getEmail());
        preparedStatement.executeUpdate();
    }

    public void updatePerson(Person nova, Person stara) {
        try {
            String query = "UPDATE person SET lastName = ?, firstName = ?, email = ? " +
                    "WHERE lastName = \"" + stara.getLastName() + "\" AND firstName = \"" + stara.getFirstName() + "\"" +
                    " AND email = \"" + stara.getEmail() + "\"";
            executeAsPreparedStatement(query, nova);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

}