package AdressBookSQLite;
import java.sql.*;
import java.util.ArrayList;

public class SQLiteJDBC {

    public SQLiteJDBC(){
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

    public void selectPerson(String student3) {
        try {
            ArrayList<String> firstnames = new ArrayList();
            PreparedStatement preparedStatement;
            Connection connection =  this.getConnection();
            String sqlInsert = "SELECT * FROM person WHERE firstname = (?)";
            preparedStatement = connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1,student3);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()) firstnames.add(rs.getString("firstname"));
            for (int i = 0; i < firstnames.size(); i++) deletePerson(firstnames.get(i));
        } catch (SQLException e) {
            System.err.println( e.getMessage() );
        }
    }

    public void insertPerson(String lastName, String firstName, String email) {
        try {
            PreparedStatement preparedStatement;
            Connection connection =  this.getConnection();
            String sqlInsert = "INSERT INTO person (lastName, firstName, email)" +
                    "VALUES (?,?,?)";
            preparedStatement = connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1,lastName);
            preparedStatement.setString(2,firstName);
            preparedStatement.setString(3,email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println( e.getMessage() );
        }
    }

    public void updatePerson(String lastName, String firstName, String email) {
        try {
            PreparedStatement preparedStatement;
            Connection connection =  this.getConnection();
            String sqlInsert = "UPDATE person SET firstName = (?), email = (?) WHERE lastName like (?)";
            preparedStatement = connection.prepareStatement(sqlInsert);
            preparedStatement.setString(3,lastName);
            preparedStatement.setString(1,firstName);
            preparedStatement.setString(2,email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println( e.getMessage() );
        }
    }

    public void deletePerson(String firstname) {
        try {
            PreparedStatement preparedStatement;
            Connection connection =  this.getConnection();
            String sqlInsert = "DELETE FROM person WHERE firstname = (?) ";
            preparedStatement = connection.prepareStatement(sqlInsert);
            preparedStatement.setString(1, firstname);
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