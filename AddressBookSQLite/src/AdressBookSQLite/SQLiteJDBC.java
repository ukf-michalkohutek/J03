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

    protected void deletePerson(String lName, String fName, String mail){
        try {
            PreparedStatement preparedStatement;
            Connection connection = this.getConnection();
            String sqlDelete = "DELETE FROM person" +
                    "WHERE lastName = ? and firstName = ? and email = ?";
            preparedStatement = connection.prepareStatement(sqlDelete);
            preparedStatement.setString(1,lName);
            preparedStatement.setString(2,fName);
            preparedStatement.setString(3,mail);
            preparedStatement.executeUpdate();

        } catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }

    public void updatePerson(String lastName, String firstName, String email,String lName, String fName, String mail) {
        try {
            PreparedStatement preparedStatement;
            Connection connection =  this.getConnection();
            String sqlUpdate = "UPDATE person " +
                    "SET lastName = ?,firstName = ?,email = ?" +
                    "WHERE" +
                    " lastName = ? and firstName = ? and email = ?" +
                    "ORDER BY lastName" +
                    "LIMIT 1";
            preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setString(1,lastName);
            preparedStatement.setString(2,firstName);
            preparedStatement.setString(3,email);
            preparedStatement.setString(4,lName);
            preparedStatement.setString(5,fName);
            preparedStatement.setString(6,mail);
            preparedStatement.executeUpdate();

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

    public ResultSet getResultSet() throws SQLException {
            String query = "SELECT * FROM person";
            ResultSet resultSet = this.getConnection().createStatement().executeQuery(query);
            return resultSet;

    }

}