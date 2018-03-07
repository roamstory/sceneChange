package sample;

import com.sun.org.apache.xpath.internal.operations.Bool;
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
            mSocket = IO.socket("http://localhost:5000");
            mSocket.on(Socket.EVENT_CONNECT, onConnect);
            mSocket.on("onNewMessage", onNewMessage);
            mSocket.on("connectedSuccess", connectedSuccess);
            System.out.println("connect");
            mSocket.connect();
        } catch (URISyntaxException e) {
            System.out.println("Exception ::" + e);
            throw new RuntimeException(e);
        }
        System.out.println("connected");
    }

    static int interVal2 = 0;


    static Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... objects) {
            System.out.println("Log");
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
