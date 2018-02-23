package sample;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.scene.Parent;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class SocketConnect {


    static Socket mSocket;

    public void socketConnect() {
        try {
            mSocket = IO.socket("http://localhost:3000");
            mSocket.on(Socket.EVENT_CONNECT, onConnect);
            mSocket.on("onNewMessage", onNewMessage);
            mSocket.on("connectedSuccess", connectedSuccess);
            mSocket.on("loginVal", loginVal);

            System.out.println("connect");
            mSocket.connect();
        } catch (URISyntaxException e) {
            System.out.println("Exception ::" + e);
            throw new RuntimeException(e);
        }
        System.out.println("connected");
    }

    void loginConnect(JSONObject loginData) {
        mSocket.emit("Login", loginData);
    }

    static int interVal2 = 0;

    static Emitter.Listener loginVal = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            System.out.println("-----------------------");
            interVal2++;
            System.out.println("interVal2 >>>> " + interVal2);
            String data = (String)args[0];
            if(data.equals("ok")) {
                System.out.println("ok");
            } else {
                System.out.println("fail");
            }
//            String data = (String)args[0];
//            System.out.println("-----------------------");
//            System.out.println("Data :::" + data);
//            System.out.println("-----------------------");
//            if(data.equals("ok")) {
//
//                Platform.runLater(()-> {
//                    try {
//                        System.out.println(">>>>>stage is2 " + stage);
//                        Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
//                        Scene scene = new Scene(root);
//                        scene.windowProperty();
//                        stage.setScene(scene);
//                    } catch (IOException e) {
//                        System.out.println(e);
//                        System.out.println("failed");
//                    }
//                });
//            } else {
//                System.out.println("fail");
//            }
        }

    };

    static Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... objects) {
            System.out.println("Log");
            mSocket.emit("join" , "log");
        }
    };

    static Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            String data = (String)args[0];
            System.out.println("Data :::" + data);
        }
    };

    static Emitter.Listener connectedSuccess = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            String data = (String)args[0];
            System.out.println("connectedSuccess :::" + data);
        }
    };

}
