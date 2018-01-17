package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Login extends Application {

//    @Override
//    public void start(Stage primaryStage) {
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//            Scene scene = new Scene(root, 400, 400);
//            scene.getStylesheets().add(getClass().getResource("sample.css").toExternalForm());
//            primaryStage.setTitle("Scene Change");
//            primaryStage.setScene(scene);
//            primaryStage.show();
//        } catch (Exception e) {
//            System.out.println("FXML File Not Found >>" + e);
//            e.printStackTrace();
//        }
//    }

    Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{

        this.stage = primaryStage;
        mainWindow();
    }

    public void mainWindow() {

        try {

            FXMLLoader loader =  new FXMLLoader(Login.class.getResource("Login.fxml"));
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            scene.getStylesheets().addAll(Login.class.getResource("Login.css").toExternalForm());

            stage.setResizable(true);
//          stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("signin");
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
