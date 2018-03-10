package sample;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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

//    @Override
//    public void start(Stage primaryStage) {
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//            Scene scene = new Scene(root, 400, 400);
//            scene.getStylesheets().add(getClass().getResource("sample.css").toExternalForm());
//            primaryStage.setTitle("Scene Change");
//            primaryStage.setScene(scene);
//            primaryStage.show();
//        } catch (Exception e) {
//            System.out.println("FXML File Not Found >>" + e);
//            e.printStackTrace();
//        }
//    }

    Stage stage;
    SocketConnect socketConnect = new SocketConnect();

    DeviceInfoXmlParse deviceInfoXmlParse = new DeviceInfoXmlParse();

    StoreVO storeVO = new StoreVO();

    @Override
    public void start(Stage primaryStage) throws Exception{

        this.stage = primaryStage;
        mainWindow();
    }

    public void mainWindow() {

        try {
            FXMLLoader loader;
            String deviceId = "";

            File file = new File("deviceInfo.xml");

            if( file.exists() ){
                storeVO = deviceInfoXmlParse.parseXML();
                deviceId = storeVO.getDeviceId();
            }


            if(!deviceId.equals("")) {
                loader =  new FXMLLoader(Login.class.getResource("Login.fxml"));
            } else {
                loader =  new FXMLLoader(Login.class.getResource("AdminLogin.fxml"));
            }
            AnchorPane pane = loader.load();
            Scene scene = new Scene(pane);
            scene.getStylesheets().addAll(Login.class.getResource("Platform.css").toExternalForm());
            stage.setResizable(true);
//          stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("signin");
            stage.setScene(scene);
            stage.show();

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
