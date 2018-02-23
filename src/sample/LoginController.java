package sample;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;

public class LoginController  extends SocketConnect  {

    @FXML
    private Button openRegister;

    @FXML
    private Button openLogin;

    @FXML
    private Stage stage;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField userpassword;



    @FXML
    void openLoginAction(ActionEvent event) {

                    String name = username.getText();
                    System.out.println(name);
                    String password = userpassword.getText();
                    System.out.println(password);

                    JSONObject loginData = new JSONObject();

                    try {
                        loginData.put("deviceId", "log");
                        loginData.put("id", name);
                        loginData.put("password", password);
                    } catch (JSONException jsonE) {
                        jsonE.printStackTrace();
                    }

                    mSocket.emit("Login", loginData);
                    Emitter.Listener loginVal = new Emitter.Listener() {

                        @Override
                        public void call(final Object... args) {
                            String data = (String)args[0];
                            System.out.println("Data :::" + data);

                            if(data.equals("ok")) {
                                Platform.runLater(()-> {
                                    try {
                                        stage = (Stage) openLogin.getScene().getWindow();
                                        System.out.println(">>>>>stage is " + stage);
                                        if (!stage.equals(null)) {
                                            Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
                                            Scene scene = new Scene(root);
                                            stage.setScene(scene);
                                        }
                                    } catch (IOException e) {
                                        System.out.println("failed");
                                    }
                                });
                            } else {
                                System.out.println("fail");
                            }

                        }
                    };

                    mSocket.on("loginVal", loginVal);



    }

    void mainTogo() {
        System.out.println("Here");

    }
    @FXML
    void openRegisterAction(ActionEvent event) {
        try {
            stage = (Stage) openRegister.getScene().getWindow();
            System.out.println(stage);
            Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (Exception e) {
            System.out.println("File Not Found >>" + e);
            e.printStackTrace();
        }
    }
}
