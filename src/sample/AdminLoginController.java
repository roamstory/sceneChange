package sample;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import io.socket.client.Ack;
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

public class AdminLoginController extends SocketConnect {

    @FXML
    private AnchorPane pane;

    @FXML
    private Button openRegister;

    @FXML
    private  Button openLogin;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField userpassword;

    @FXML
    private JFXTextField userDeviceId;

    int interVal1 = 0;

    @FXML
    void openLoginAction(ActionEvent event) {
        interVal1++;
        System.out.println("interVal1 >>>> " + interVal1);
                    String name = username.getText();
                    System.out.println(name);
                    String password = userpassword.getText();
                    System.out.println(password);

                    JSONObject loginData = new JSONObject();

                    try {
                        loginData.put("deviceId", "log");
                        loginData.put("id", name);
                        loginData.put("password", password);
                        if (DEVICE_NAME.equals("")) {
                            DEVICE_NAME = userDeviceId.getText();
                        }
                        loginData.put("DEVICE_NAME", DEVICE_NAME);
                        System.out.println("DEVICE_NAME ::" + DEVICE_NAME);
                    } catch (JSONException jsonE) {
                        jsonE.printStackTrace();
                    }

                    mSocket.emit("adminLogin", loginData, new Ack() {
                        @Override
                        public void call(Object... args) {
                            JSONObject data = (JSONObject)args[0];
                            String requestVal = "";
                            try {
                                System.out.println(data);
                                requestVal = data.getString("requestVal");
                                System.out.println("-----------------------");
                                System.out.println("Data :::" + data.getString("requestVal"));
                                System.out.println("-----------------------");
                            } catch (JSONException e) {
                                System.out.println(e);
                            }
                            if(requestVal.equals("1")) {
                                Platform.runLater(()-> {
                                            try {
                                                Stage stage = new Stage();
                                                stage = (Stage) openLogin.getScene().getWindow();
                                                FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerSearch.fxml"));
                                                Parent root = loader.load();
                                                Scene scene = new Scene(root);
                                                stage.setScene(scene);
                                            } catch (IOException e) {
                                                System.out.println(e);
                                                System.out.println("failed");
                                            }
                                            Thread.interrupted();
                                        }
                                );
                            } else {
                                System.out.println("fail");
                            }
                        }
                    });

    }

    @FXML
    void openRegisterAction(ActionEvent event) {
        try {
            Stage stage = new Stage();
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
