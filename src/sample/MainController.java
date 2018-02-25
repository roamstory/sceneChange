package sample;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MainController extends SocketConnect {

    @FXML
    private Button logintoMain;

    @FXML
    private Button openPresent;

    @FXML
    private Stage stage;

    @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField birthday;

    @FXML
    private JFXTextField phone;

    @FXML
    private JFXTextField stampCount;

    @FXML
    private JFXTextField stampAllCount;

    private JSONObject CustomeData;

    void setData(JSONObject CustomeData) {
        System.out.println("CustomeData" + CustomeData);

        try {
            birthday.setText(CustomeData.getString("birthday"));
            stampCount.setText(CustomeData.getString("stampCount")+"개");
            stampAllCount.setText(CustomeData.getString("stampAllCount")+"개");
            phone.setText(CustomeData.getString("phone"));
            name.setText(CustomeData.getString("name"));
        } catch (JSONException e) {

        }

    }
    @FXML
    void logintoMainAction(ActionEvent event) {

        try {
                Platform.runLater(()-> {
                    try {
                        stage = (Stage) logintoMain.getScene().getWindow();
                        System.out.println("mainStage ::" + stage);
                        System.out.println(DEVICE_NAME);
                        Parent root;
                        if(!DEVICE_NAME.equals("")) {
                            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
                        } else {
                            root = FXMLLoader.load(getClass().getResource("AdminLogin.fxml"));
                        }
                        Scene scene = new Scene(root);
                        scene.getStylesheets().addAll(Login.class.getResource("Login.css").toExternalForm());
                        stage.setScene(scene);
                    } catch(Exception e) {
                        System.out.println(e);
                    }
                });
            } catch (Exception e) {
                System.out.println("File Not Found >>" + e);
                e.printStackTrace();
            }
    }

    @FXML
    void openPresentAction(ActionEvent event) {
        try {
            stage = (Stage) openPresent.getScene().getWindow();
            System.out.println(stage);
            Parent root = FXMLLoader.load(getClass().getResource("Present.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (Exception e) {
            System.out.println("File Not Found >>" + e);
            e.printStackTrace();
        }
    }

}
