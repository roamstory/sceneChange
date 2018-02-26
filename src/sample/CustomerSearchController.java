package sample;

import com.jfoenix.controls.JFXTextField;
import io.socket.client.Ack;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class CustomerSearchController extends SocketConnect {

    @FXML
    private Button logintoMain;

    @FXML
    private Button openPresent;

    @FXML
    private Button customerSearch;

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
                        scene.getStylesheets().addAll(Login.class.getResource("Platform.css").toExternalForm());
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

    @FXML
    void searchCustomerAction(ActionEvent event) {
        String phoneNumber = "01012345678";

        mSocket.emit("searchCustomer", phoneNumber, new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject)args[0];
                String requestVal = "";
                try {
                    requestVal = data.getString("requestVal");
                    System.out.println("-----------------------");
                    System.out.println("Data :::" + data.getString("requestVal"));
                    System.out.println("-----------------------");
                } catch (JSONException e) {

                }

                if(requestVal.equals("1")) {
                    Platform.runLater(()-> {
                                try {
                                    Stage stage = new Stage();
                                    stage = (Stage) customerSearch.getScene().getWindow();
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
                                    Parent root = loader.load();
                                    MainController mainController = loader.<MainController>getController();
                                    mainController.setData(data);
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

}
