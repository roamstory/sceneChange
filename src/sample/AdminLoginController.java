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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.LogManager;

public class AdminLoginController extends SocketConnect implements Initializable {

    DeviceInfoXmlParse deviceInfoXmlParse = new DeviceInfoXmlParse();

    WriteInfoXml writeInfoXml = new WriteInfoXml();

    StoreVO storeVO = new StoreVO();

    static HanKeyToEngKey hanKeyToEngKey = HanKeyToEngKey.getInstance();

    @FXML
    private AnchorPane pane;

    @FXML
    private Button openRegister;

    @FXML
    private  Button openLogin;

    @FXML
    private  Stage stage;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField userpassword;

    @FXML
    private JFXTextField userDeviceId;

    int interVal1 = 0;

    public AdminLoginController() {
        System.out.println("현재 stage는 " +stage);
    }

    public AdminLoginController(Stage stage) {
        this.stage = stage;
        System.out.println("현재 stage는 " +stage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void openLoginAction(ActionEvent event) {
        interVal1++;
        System.out.println("interVal1 >>>> " + interVal1);
                    String name = username.getText();
                    System.out.println(name);
                    String password = userpassword.getText();
                    System.out.println(hanKeyToEngKey.getHanKeyToEngKey(password));
                    String deviceId = userDeviceId.getText();
                    System.out.println(deviceId);

                    JSONObject loginData = new JSONObject();

                    try {
                        loginData.put("deviceId", deviceId);
                        loginData.put("id", name);
                        loginData.put("password", hanKeyToEngKey.getHanKeyToEngKey(password));
                    } catch (JSONException jsonE) {
                        jsonE.printStackTrace();
                    }

                    mSocket.emit("posLoginDeviceInsData", loginData, new Ack() {
                        @Override
                        public void call(Object... args) {
                            JSONObject data = (JSONObject)args[0];
                            String responseVal = "";
                            try {
                                responseVal = data.getString("responseCode");
                                System.out.println("-----------------------");
                                System.out.println("Data :::" + data);
                                System.out.println("-----------------------");
                            } catch (JSONException e) {
                                System.out.println(e);
                            }
                            if(responseVal.equals("1")) {

                                writeInfoXml.writeInfo(data);

                                Platform.runLater(()-> {
                                            try {
                                                Stage stage = new Stage();
                                                stage = (Stage) openLogin.getScene().getWindow();
                                                FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerSearch.fxml"));
                                                Parent root = loader.load();
                                                CustomerSearchController customerSearchController = new CustomerSearchController(stage);
                                                customerSearchController = customerSearchController.getInstance();
                                                customerSearchController.setStage(stage);
                                                storeVO = deviceInfoXmlParse.parseXML();
                                                customerSearchController.setStoreVO(storeVO);
                                                Scene scene = new Scene(root);
                                                stage.setTitle("고객 조회");
                                                scene.getStylesheets().addAll(Login.class.getResource("Platform.css").toExternalForm());
                                                stage.setScene(scene);
                                            } catch (Exception e) {
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



    public void setStoreVO(StoreVO storeVO) {
        this.storeVO = storeVO;
        System.out.println(this.storeVO.toString());
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        System.out.println(this.stage.toString());
    }
}
