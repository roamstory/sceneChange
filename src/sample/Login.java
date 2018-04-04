package sample;

import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

public class Login extends Application  {

    Stage stage;

    SocketConnect socketConnect = new SocketConnect();

    DeviceInfoXmlParse deviceInfoXmlParse = new DeviceInfoXmlParse();

    WriteInfoXml writeInfoXml = new WriteInfoXml();

    StoreVO storeVO = new StoreVO();

    @Override
    public void start(Stage primaryStage) throws Exception{

        this.stage = primaryStage;
        mainWindow();
        //parseXML();
    }

    public void mainWindow() {

        try {

            String deviceId = "";

            File file = new File("deviceInfo.xml");

            if( file.exists() ){
                storeVO = deviceInfoXmlParse.parseXML();
                deviceId = storeVO.getDeviceId();
            }

            if(!deviceId.equals("")) {


                JSONObject loginData = new JSONObject();
                try {
                    storeVO = deviceInfoXmlParse.parseXML();

                    String name = storeVO.getWideManagerId();
                    String password = storeVO.getWideManagerPassword();
                    System.out.println(deviceId);

                    loginData.put("deviceId", deviceId);
                    loginData.put("id", name);
                    loginData.put("password", password);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                socketConnect.mSocket.emit("posLoginDeviceInsData", loginData, new Ack() {
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

                            writeInfoXml.writeInfo(data);

                            Platform.runLater(()-> {
                                try {
                                    FXMLLoader loader;
                                    loader =  new FXMLLoader(Login.class.getResource("CustomerSearch.fxml"));
                                    AnchorPane pane = loader.load();
                                    CustomerSearchController customerSearchController = new CustomerSearchController(stage);
                                    customerSearchController = customerSearchController.getInstance();
                                    customerSearchController.setStoreVO(storeVO);
                                    customerSearchController.setStage(stage);
                                    Scene scene = new Scene(pane);
                                    scene.getStylesheets().addAll(Login.class.getResource("Platform.css").toExternalForm());
                                    stage.setResizable(true);
                                    stage.setTitle("고객조회");
                                    stage.setScene(scene);
                                    stage.show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    System.out.println(">>>failed");
                                }
                                Thread.interrupted();
                            });
                        } else {
                            System.out.println("fail");
                        }
                    }
                });

            } else {
                FXMLLoader loader;
                loader =  new FXMLLoader(Login.class.getResource("AdminLogin.fxml"));
                AnchorPane pane = loader.load();
                AdminLoginController adminLoginController = new AdminLoginController(stage);
                adminLoginController.setStage(stage);
                Scene scene = new Scene(pane);
                scene.getStylesheets().addAll(Login.class.getResource("Platform.css").toExternalForm());
                stage.setResizable(true);
//          stage.initStyle(StageStyle.UNDECORATED);
                stage.setTitle("매장 로그인");
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        SocketConnect socketConnect = new SocketConnect();
        socketConnect.socketConnect();

        launch(args);
    }
}
