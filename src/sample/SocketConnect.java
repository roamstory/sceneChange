package sample;

import com.sun.org.apache.xpath.internal.operations.Bool;
import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.json.JSONObject;
import sun.applet.Main;

import javax.naming.directory.SearchControls;
import java.net.URISyntaxException;

public class SocketConnect {

    static Socket mSocket;

    CustomerSearchController customerSearchController;
    static DeviceInfoXmlParse deviceInfoXmlParse =  new DeviceInfoXmlParse();

    static Stage stage;

    public void socketConnect() {
        try {
            mSocket = IO.socket("http://localhost:5000");
            //mSocket = IO.socket("http://49.236.137.39:5000");
            mSocket.on(Socket.EVENT_CONNECT, onConnect);
            mSocket.on("onNewMessage", onNewMessage);
            mSocket.on("connectedSuccess", connectedSuccess);
            mSocket.on("customerInfoResponse", customerSearchController.customerInfoResponse);
            System.out.println("connect");
            mSocket.connect();
        } catch (URISyntaxException e) {
            System.out.println("Exception ::" + e);
            throw new RuntimeException(e);
        }
        System.out.println("connected");
    }

    static int interVal2 = 0;

    /*
     * Node Server 연결 시 해당 디바이스 연결
     */
    static Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... objects) {
            System.out.println("Log");
            try {
                StoreVO storeVO = deviceInfoXmlParse.parseXML();
                JSONObject storeInfo = new JSONObject();
                storeInfo.put("deviceId", storeVO.getDeviceId());
                storeInfo.put("mallSocketId", storeVO.getMallSocketId());

                mSocket.emit("join", storeInfo , new Ack() {
                    @Override
                    public void call(Object... args) {
                        System.out.println("join");
                    }
                });
            } catch (Exception e) {

            }


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
