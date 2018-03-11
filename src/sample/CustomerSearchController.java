package sample;

import com.jfoenix.controls.JFXTextField;
import io.socket.client.Ack;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Popup;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerSearchController extends SocketConnect implements Initializable {

    DeviceInfoXmlParse deviceInfoXmlParse = new DeviceInfoXmlParse();

    StoreVO storeVO = new StoreVO();

    CustomerSet customerSet = new CustomerSet();

    CustomerVO customerVO = new CustomerVO();

    @FXML
    private Button logintoMain;

    @FXML
    private Button customerSearch;

    @FXML
    private Stage stage;

    @FXML
    private JFXTextField customerNumber;


    static Emitter.Listener customerInfoResponse = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            System.out.println(">>>>HEAE");
            String phoneNumber = "";
            try {
                JSONObject data = (JSONObject)args[0];
                phoneNumber = data.getString("phoneNumber");

            } catch (Exception e) {

            }
        }
    };

    static void action2(String phoneNumber) {
        try {
            FXMLLoader loader = new FXMLLoader(CustomerSearchController.class.getResource("Main.fxml"));
            Parent root = loader.load();
            CustomerSearchController customerSearchController = loader.<CustomerSearchController>getController();
            customerSearchController.searchCustomerAction2(phoneNumber);
        }catch (Exception e) {

        }

    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void logintoMainAction(ActionEvent event) {
        try {
                Platform.runLater(()-> {
                    try {
                        Stage stage = new Stage();
                        stage = (Stage) logintoMain.getScene().getWindow();
                        String deviceId = storeVO.getDeviceId();
                        FXMLLoader loader;
                        Parent root;
                        if(!deviceId.equals("") || deviceId == null) {
                            loader = new FXMLLoader(getClass().getResource("Login.fxml"));
                            root = loader.load();
                            LoginController loginController = loader.<LoginController>getController();
                            storeVO = deviceInfoXmlParse.parseXML();
                            loginController.setStoreVO(storeVO);
                        } else {
                            loader = new FXMLLoader(getClass().getResource("AdminLogin.fxml"));
                            root = loader.load();
                            AdminLoginController adminLoginController = loader.<AdminLoginController>getController();
                            storeVO = deviceInfoXmlParse.parseXML();
                            adminLoginController.setStoreVO(storeVO);
                        }

                        Scene scene = new Scene(root);
                        scene.getStylesheets().addAll(Login.class.getResource("Platform.css").toExternalForm());
                        stage.setScene(scene);

                    } catch(Exception e) {
                        System.out.println(e);
                    }
                });
            } catch (Exception e) {
                System.out.println("File Not Found >>" + e);
                e.printStackTrace();
            }
    }

    @FXML
    void searchCustomerAction() {
        JSONObject searchCustomerInfo = new JSONObject();

        try {
            storeVO = deviceInfoXmlParse.parseXML();

            String phoneNumber = customerNumber.getText();
            String wideManagerId = storeVO.getWideManagerId();
            String mallSocketId = storeVO.getMallSocketId();
            String deviceId = storeVO.getDeviceId();

            searchCustomerInfo.put("phoneNumber", phoneNumber);
            searchCustomerInfo.put("wideManagerId", wideManagerId);
            searchCustomerInfo.put("mallSocketId", mallSocketId);
            searchCustomerInfo.put("deviceId", deviceId);

        } catch (Exception e) {
            System.out.println(e);
        }


        mSocket.emit("searchCustomer", searchCustomerInfo, new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject)args[0];
                String responseVal = "";
                try {
                    responseVal = data.getString("responseCode");
                    System.out.println("-----------------------");
                    System.out.println("Data :::" + data.getString("responseCode"));
                    System.out.println("-----------------------");

                    customerVO = customerSet.customerSetVO(data);
                    System.out.println("customersearch" + customerVO.toString());
                } catch (Exception e) {
                    System.out.println(e);
                }
                if(responseVal.equals("1")) {
                    Platform.runLater(()-> {
                        try {
                            Stage stage = new Stage();
                            stage = (Stage) customerSearch.getScene().getWindow();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
                            Parent root = loader.load();
                            MainController mainController = loader.<MainController>getController();
                            mainController.setStoreVO(storeVO);
                            mainController.setData(customerVO);
                            mainController.setCustomerVO(customerVO);
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                        } catch (IOException e) {
                            e.printStackTrace();
                            System.out.println(e.toString());
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

    @FXML
    void searchCustomerAction2(String phoneNumberInfo) {
        System.out.println("1번?");
        JSONObject searchCustomerInfo = new JSONObject();

        try {
            storeVO = deviceInfoXmlParse.parseXML();
            System.out.println(phoneNumberInfo + "전화번호는");
            String phoneNumber = phoneNumberInfo;
            String wideManagerId = storeVO.getWideManagerId();
            String mallSocketId = storeVO.getMallSocketId();
            String deviceId = storeVO.getDeviceId();

            searchCustomerInfo.put("phoneNumber", phoneNumber);
            searchCustomerInfo.put("wideManagerId", wideManagerId);
            searchCustomerInfo.put("mallSocketId", mallSocketId);
            searchCustomerInfo.put("deviceId", deviceId);

        } catch (Exception e) {
            System.out.println(e);
        }


        mSocket.emit("searchCustomer", searchCustomerInfo, new Ack() {

            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject)args[0];

                String responseVal = "";
                try {
                    responseVal = data.getString("responseCode");
                    System.out.println("-----------------------");
                    System.out.println("Data :::" + data.getString("responseCode"));
                    System.out.println("-----------------------");

                    customerVO = customerSet.customerSetVO(data);
                    System.out.println("customersearch" + customerVO.toString());
                } catch (Exception e) {
                    System.out.println(e);
                }

                if(responseVal.equals("1")) {
                    Platform.runLater(()-> {
                        try {
                            stage = (Stage) customerSearch.getScene().getWindow();
                            System.out.println(stage + ">>>>>>>>");
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
                            Parent root = loader.load();
                            MainController mainController = loader.<MainController>getController();
                            mainController.setStoreVO(storeVO);
                            mainController.setData(customerVO);
                            mainController.setCustomerVO(customerVO);
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println(e.toString());
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
