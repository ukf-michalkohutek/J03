package AdressBookSQLite;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FXMLTableView extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("fxml_tableview.fxml"));
        primaryStage.setTitle("Hello World");
        System.out.println(primaryStage.getStyle());
        primaryStage.setScene(new Scene(root, 900, 350));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
