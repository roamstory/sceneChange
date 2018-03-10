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

    StoreVO storeVO = new StoreVO();

    static HanKeyToEngKey hanKeyToEngKey = HanKeyToEngKey.getInstance();

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

                                writeInfoXml(data);

                                Platform.runLater(()-> {
                                            try {
                                                Stage stage = new Stage();
                                                stage = (Stage) openLogin.getScene().getWindow();
                                                FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerSearch.fxml"));
                                                Parent root = loader.load();
                                                CustomerSearchController customerSearchController = loader.<CustomerSearchController>getController();
                                                storeVO = deviceInfoXmlParse.parseXML();
                                                customerSearchController.setStoreVO(storeVO);
                                                Scene scene = new Scene(root);
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

    void writeInfoXml(JSONObject data) {

        File file = new File("deviceInfo.xml");

        if( file.exists() ){
            if(file.delete()){
                System.out.println("파일삭제 성공");
            }else{
                System.out.println("파일삭제 실패");
            }
        }else{
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println(e);
            }
        }

        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Node root = document.createElement("wishwide");
            document.appendChild(root);
            {
                {
                    Element mallSocketId = document.createElement("mallSocketId");
                    mallSocketId.appendChild(document.createTextNode(data.getString("mallSocketId")));
                    root.appendChild(mallSocketId);
                }
                {
                    Element deviceId = document.createElement("deviceId");
                    deviceId.appendChild(document.createTextNode(data.getString("deviceId")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("wideManagerId");
                    deviceId.appendChild(document.createTextNode(data.getString("wideManagerId")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("wideManagerPassword");
                    deviceId.appendChild(document.createTextNode(data.getString("wideManagerPassword")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("wideManagerName");
                    deviceId.appendChild(document.createTextNode(data.getString("wideManagerName")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("benefitTypeCode");
                    deviceId.appendChild(document.createTextNode(data.getString("benefitTypeCode")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("stampGoal");
                    deviceId.appendChild(document.createTextNode(data.getString("stampGoal")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("stampVipGoal");
                    deviceId.appendChild(document.createTextNode(data.getString("stampVipGoal")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("stampVipDiscountRate");
                    deviceId.appendChild(document.createTextNode(data.getString("stampVipDiscountRate")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("pointRate");
                    deviceId.appendChild(document.createTextNode(data.getString("pointRate")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("pointUseGoal");
                    deviceId.appendChild(document.createTextNode(data.getString("pointUseGoal")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("pointVipGoal");
                    deviceId.appendChild(document.createTextNode(data.getString("pointVipGoal")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("pointVipRate");
                    deviceId.appendChild(document.createTextNode(data.getString("pointVipRate")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("pointVipDiscountRate");
                    deviceId.appendChild(document.createTextNode(data.getString("pointVipDiscountRate")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("stampCommonStatusCode");
                    deviceId.appendChild(document.createTextNode(data.getString("stampCommonStatusCode")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("pointCommonStatusCode");
                    deviceId.appendChild(document.createTextNode(data.getString("pointCommonStatusCode")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("wideManagerFinishDate");
                    deviceId.appendChild(document.createTextNode(data.getString("wideManagerFinishDate")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("contractStatusCode");
                    deviceId.appendChild(document.createTextNode(data.getString("contractStatusCode")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("wideManagerRole");
                    deviceId.appendChild(document.createTextNode(data.getString("wideManagerRole")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("stampCommonStatusCode");
                    deviceId.appendChild(document.createTextNode(data.getString("stampCommonStatusCode")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("pointCommonStatusCode");
                    deviceId.appendChild(document.createTextNode(data.getString("pointCommonStatusCode")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("partnerId");
                    deviceId.appendChild(document.createTextNode(data.getString("partnerId")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("franchiseId");
                    deviceId.appendChild(document.createTextNode(data.getString("franchiseId")));
                    root.appendChild(deviceId);
                }

            }
            DOMSource xmlDOM = new DOMSource(document);
            StreamResult xmlFile = new StreamResult(new File("deviceInfo.xml"));
            TransformerFactory.newInstance().newTransformer().transform(xmlDOM, xmlFile);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void setStoreVO(StoreVO storeVO) {
        this.storeVO = storeVO;
        System.out.println(this.storeVO.toString());
    }

}
