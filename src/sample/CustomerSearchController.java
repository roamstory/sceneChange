package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import io.socket.client.Ack;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
            String phoneNumber = "";
            JSONObject data = (JSONObject)args[0];
            System.out.println("여기여기여기" + data);
            try {
                System.out.println("여기여기여기");
                phoneNumber = data.getString("membershipCustomerPhone");
                System.out.println(phoneNumber);
            } catch (Exception e) {
                System.out.println(e);
            }
            System.out.println("여기여기11여기");
            action2(phoneNumber);
            System.out.println("22222");
        }
    };


    static void action2(String phoneNumber) {
        try {
            System.out.println(">>>>1111");
            FXMLLoader loader = new FXMLLoader(CustomerSearchController.class.getResource("CustomerSearch.fxml"));
            CustomerSearchController customerSearchController = new CustomerSearchController();
            customerSearchController.searchCustomerAction2(phoneNumber);
        }catch (Exception e) {

        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void handleBtnAction(ActionEvent e) {
        if (customerNumber.getText().length() < 13) {
            String buttonText = ((JFXButton)e.getSource()).getText();
            if (customerNumber.getText().length() == 2) {
                customerNumber.setText(customerNumber.getText() + buttonText + '-');
            } else if (customerNumber.getText().length() == 7) {
                customerNumber.setText(customerNumber.getText() + buttonText + '-');
            } else {
                customerNumber.setText(customerNumber.getText() + buttonText);
            }
        }
    }


    public void handleBtnOkAction(ActionEvent e) {
        System.out.println(customerNumber.getText());
    }

    public void handleBtnDelAction(ActionEvent e) {
        String phoneNumber = "";
        if(customerNumber.getText().length() > 0) {
            phoneNumber = customerNumber.getText().substring(0, customerNumber.getText().length()-1 );
        }
        customerNumber.setText(phoneNumber);
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
                        stage.setTitle("매장 로그인");
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

            String phoneNumber = customerNumber.getText().replace("-","");
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
                            stage = (Stage)customerSearch.getScene().getWindow();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
                            Parent root = loader.load();
                            MainController mainController = loader.<MainController>getController();
                            mainController.setStoreVO(storeVO);
                            mainController.setData(customerVO);
                            mainController.setCustomerVO(customerVO);
                            stage.setTitle("고객 정보");
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
                    Platform.runLater(() -> {
                        try {
                            Stage dialog = new Stage(StageStyle.UTILITY);
                            dialog.initModality(Modality.WINDOW_MODAL);
                            dialog.initOwner(stage);
                            dialog.setTitle("확인");

                            Parent parent = FXMLLoader.load(getClass().getResource("custom_dialog.fxml"));
                            Label txtTitle = (Label) parent.lookup("#txtTitle");
                            txtTitle.setText("가입된 멤버쉽 회원이 아닙니다.");
                            Button btnOk = (Button) parent.lookup("#btnOk");
                            btnOk.setOnAction(event->dialog.close());
                            Scene scene = new Scene(parent);

                            dialog.setScene(scene);
                            dialog.setResizable(false);
                            dialog.show();
                        }
                        catch (Exception e) {
                            System.out.println(e);
                        }
                    });
                }
            }
        });

    }

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
                            Stage stage = new Stage();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
                            Parent root = loader.load();
                            MainController mainController = loader.<MainController>getController();
                            mainController.setStoreVO(storeVO);
                            mainController.setData(customerVO);
                            mainController.setCustomerVO(customerVO);
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                            stage.setTitle("고객 정보");
                            stage.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            System.out.println(e.toString());
                            System.out.println("failed");
                        }
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
