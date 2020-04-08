package AdressBookSQLite;
import java.sql.*;

public class SQLiteJDBC {


    public SQLiteJDBC(){
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

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );

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
            Connection connection =  this.getConnection();
            String sqlInsert = "INSERT INTO person (lastName, firstName, email)" +
                    "VALUES (?,?,?)";
            preparedStatement = connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1,lastName);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3,email);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println( e.getMessage() );
        }
    }

    public void removePerson(String lastName, String firstName, String email) {
        try {
            PreparedStatement preparedStatement;
            Connection connection =  this.getConnection();
            String sqlRemove = "DELETE FROM person" +
                    "WHERE lastName = ?" +
                    "AND firstName = ?" +
                    "AND email = ?";
            preparedStatement = connection.prepareStatement(sqlRemove);
            preparedStatement.setString(1,lastName);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3,email);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println( e.getMessage() );
        }
    }

    public void updatePerson(String lastName, String firstName, String email, String lastNameOld, String firstNameOld, String emailOld) {
        try {
            PreparedStatement preparedStatement;
            Connection connection =  this.getConnection();
            String sqlInsert = "UPDATE person " +
                    "SET lastName = ?, firstName = ?, email = ? " +
                    "WHERE lastName = ? " +
                    "AND firstName = ? " +
                    "AND email = ?";
            preparedStatement = connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1,lastName);
            preparedStatement.setString(2,firstName);
            preparedStatement.setString(3,email);
            preparedStatement.setString(4,lastNameOld);
            preparedStatement.setString(5,firstNameOld);
            preparedStatement.setString(6,emailOld);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.err.println( e.getMessage() );
        }
    }

    public ResultSet getResultSet() throws SQLException {
            String query = "SELECT * FROM person";
            ResultSet resultSet = this.getConnection().createStatement().executeQuery(query);
            return resultSet;

    }

}