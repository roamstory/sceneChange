package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private Button logintoMain;

    @FXML
    private Stage stage;

    @FXML
    void logintoMainAction(ActionEvent event) {
        try {
            stage = (Stage) logintoMain.getScene().getWindow();
            System.out.println(stage);
            Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().addAll(Login.class.getResource("Login.css").toExternalForm());
            stage.setScene(scene);
        } catch (Exception e) {
            System.out.println("File Not Found >>" + e);
            e.printStackTrace();
        }
    }
}
