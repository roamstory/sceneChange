package sample;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import io.socket.client.Ack;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends SocketConnect implements Initializable {

    DeviceInfoXmlParse deviceInfoXmlParse = new DeviceInfoXmlParse();

    StoreVO storeVO = new StoreVO();

    static HanKeyToEngKey hanKeyToEngKey = HanKeyToEngKey.getInstance();

    @FXML
    private Button openRegister;

    @FXML
    private  Button openLogin;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField userpassword;

    int interVal1 = 0;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void openLoginAction(ActionEvent event) {
        interVal1++;
        System.out.println("interVal1 >>>> " + interVal1);
        JSONObject loginData = new JSONObject();
            try {
                    storeVO = deviceInfoXmlParse.parseXML();

                    String name = username.getText();
                    System.out.println(name);
                    String password = userpassword.getText();
                    System.out.println(hanKeyToEngKey.getHanKeyToEngKey(password));
                    String deviceId = storeVO.getDeviceId();
                    System.out.println(deviceId);

                    loginData.put("deviceId", deviceId);
                    loginData.put("id", name);
                    loginData.put("password", hanKeyToEngKey.getHanKeyToEngKey(password));
            } catch (Exception e) {
                    e.printStackTrace();
            }


        mSocket.emit("posLoginDeviceInsData", loginData, new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject)args[0];
                String requestVal = "";
                try {
                    requestVal = data.getString("responseCode");
                    System.out.println("-----------------------");
                    System.out.println("Data :::" + data);
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
                            CustomerSearchController customerSearchController = loader.<CustomerSearchController>getController();
                            customerSearchController.setStoreVO(storeVO);
                            Scene scene = new Scene(root);
                            scene.getStylesheets().addAll(Login.class.getResource("Platform.css").toExternalForm());
                            stage.setScene(scene);
                        } catch (Exception e) {
                            System.out.println(e);
                            System.out.println("failed");
                        }
                        Thread.interrupted();
                    });
                } else {
                    System.out.println("fail");
                }
            }
        });

    }

    public void setStoreVO(StoreVO storeVO) {
        this.storeVO = storeVO;
        System.out.println(this.storeVO.toString());
    }

}
