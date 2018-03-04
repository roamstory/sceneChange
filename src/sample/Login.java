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

    @Override
    public void start(Stage primaryStage) throws Exception{

        this.stage = primaryStage;
        mainWindow();
        parseXML();
    }

    public void mainWindow() {

        try {
            FXMLLoader loader;
            if(!socketConnect.DEVICE_NAME.equals("")) {

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

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void parseXML() throws Exception {

        // [REF] https://msdn.microsoft.com/ko-kr/library/ms762271(v=vs.85).aspx

        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("deviceInfo.xml");



        // XPath 인스턴스 생성

        XPath xpath = XPathFactory.newInstance().newXPath();



        // 대상 노드 지정

        String expression = "/Elinmedia";



        // 지정 노드로 부터 노드목록 획득

        NodeList nl = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);



        // 첫번째 노드의 부모 노드명을 출력

        System.out.println(nl.item(0).getParentNode().getNodeName());



        System.out.println("sub node start!!");

        System.out.println("-----------------------------------------------------");

        // 1번째  book 노드의 자식노드 목록을 획득

        NodeList cnl = nl.item(0).getChildNodes(); // author, title, genre, price, publish_date, description

        System.out.println(cnl.item(0).getParentNode().getNodeName());



        // 확보된 노드 목록을 순서대로 출력

        for(int i=0; i<cnl.getLength(); i++)

        {

            // 노드 이름이 #text로 출력되는 문제로 스킵 하도록 설정하였다.

            // xml파일의 들여쓰기로 인한 문제 인듯 하다.

            if("#text".equals(cnl.item(i).getNodeName()))

                continue;



            // 현재 노드 인덱스 번호 출력

            System.out.println(i);



            // 노드 명 출력

            System.out.println("NODE : " + cnl.item(i).getNodeName());



            // 노드 값 출력

            System.out.println("VALUE : " + cnl.item(i).getTextContent());

        }

        System.out.println("-----------------------------------------------------");

    }

    public static void main(String[] args) {
        SocketConnect socketConnect = new SocketConnect();
        socketConnect.socketConnect();

        launch(args);
    }
}
