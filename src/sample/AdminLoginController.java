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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
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
                        loginData.put("deviceId", "WW_DI4");
                        loginData.put("id", name);
                        loginData.put("password", password);
                        if (DEVICE_NAME.equals("")) {
                            DEVICE_NAME = userDeviceId.getText();
                        }
                        loginData.put("DEVICE_NAME", DEVICE_NAME);
                    } catch (JSONException jsonE) {
                        jsonE.printStackTrace();
                    }

                    mSocket.emit("loginDeviceInsData", loginData, new Ack() {
                        @Override
                        public void call(Object... args) {
                            JSONObject data = (JSONObject)args[0];
                            String requestVal = "";
                            try {
                                System.out.println(data);
                                requestVal = data.getString("responseCode");
                                System.out.println("-----------------------");
                                System.out.println("Data :::" + data.getString("responseCode"));
                                System.out.println("-----------------------");
                            } catch (JSONException e) {
                                System.out.println(e);
                            }
                            if(requestVal.equals("1")) {
                                try {
                                    Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
                                    Node root = document.createElement("Elinmedia");
                                    document.appendChild(root);
                                    {
                                        Element device = document.createElement("wishwide");
                                        root.appendChild(device);
                                        {
                                            Element mallSocketId = document.createElement("mallSocketId");
                                            mallSocketId.appendChild(document.createTextNode(data.getString("mallSocketId")));
                                            device.appendChild(mallSocketId);
                                        }
                                        {
                                            Element deviceId = document.createElement("deviceId");
                                            deviceId.appendChild(document.createTextNode(data.getString("deviceId")));
                                            device.appendChild(deviceId);
                                        }
                                        {
                                            Element deviceId = document.createElement("wideManagerId");
                                            deviceId.appendChild(document.createTextNode(data.getString("wideManagerId")));
                                            device.appendChild(deviceId);
                                        }
                                        {
                                            Element deviceId = document.createElement("wideManagerPassword");
                                            deviceId.appendChild(document.createTextNode(password));
                                            device.appendChild(deviceId);
                                        }
                                        {
                                            Element deviceId = document.createElement("wideDeviceType");
                                            deviceId.appendChild(document.createTextNode("POS"));
                                            device.appendChild(deviceId);
                                        }
                                    }
                                    DOMSource xmlDOM = new DOMSource(document);
                                    StreamResult xmlFile = new StreamResult(new File("deviceInfo.xml"));
                                    TransformerFactory.newInstance().newTransformer().transform(xmlDOM, xmlFile);

                                } catch (Exception e) {
                                    System.out.println(e);
                                }


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
