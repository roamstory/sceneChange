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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends SocketConnect implements Initializable {

    DeviceInfoXmlParse deviceInfoXmlParse = new DeviceInfoXmlParse();

    StoreVO storeVO = new StoreVO();

    CustomerVO customerVO = new CustomerVO();

    CustomerGiftSet customerGiftSet = new CustomerGiftSet();

    CustomerCouponSet customerCouponSet = new CustomerCouponSet();

    ObservableList<CustomerGiftVO> customerGiftList = FXCollections.observableArrayList();

    ObservableList<CustomerCouponVO> customerCouponList = FXCollections.observableArrayList();

    @FXML
    private Button searchCustomertoMain;

    @FXML
    private Button openPresent;

    @FXML
    private Button openCoupon;

    @FXML
    private Stage stage;

    @FXML
    private Label name;

    @FXML
    private Label birthday;

    @FXML
    private Label phone;

    @FXML
    private Label sex;

    @FXML
    private Label grade;

    @FXML
    private Label benefitType;

    @FXML
    private Label benefitVal;

    @FXML
    private Label benefitTypeName;

    private JSONObject CustomerData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    void setData(CustomerVO customerVO) {
        System.out.println("customerVO" + customerVO);
        System.out.println("Main" + storeVO.toString());
        birthday.setText(customerVO.getWideCustomerBirth());
        benefitVal.setText(customerVO.getMembershipCustomerBenefitValue()+"개");
        phone.setText(customerVO.getMembershipCustomerPhone());
        name.setText(customerVO.getWideCustomerName());
        grade.setText(customerVO.getMembershipCustomerGrade());

        if (customerVO.getWideCustomerSex().equals(0)) {
            sex.setText("(여)");
        } else {
            sex.setText("(남)");
        }

        if (customerVO.getMembershipCustomerBenefitType() == "S") {
            benefitTypeName.setText("개");
            benefitType.setText("현재 쿠폰 도장 개수 :");
            benefitVal.setText(customerVO.getMembershipCustomerBenefitValue()+" 개");
        } else {
            benefitTypeName.setText("포인트");
            benefitType.setText("현재 적립 포인트 :");
            benefitVal.setText(customerVO.getMembershipCustomerBenefitValue()+" 포인트");
        }
    }

    @FXML
    void searchCustomertoMainAction(ActionEvent event) {

        try {
            Platform.runLater(()-> {
                try {
                    Stage stage = new Stage();
                    stage = (Stage) searchCustomertoMain.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("CustomerSearch.fxml"));
                    Parent root = loader.load();
                    CustomerSearchController customerSearchController = loader.<CustomerSearchController>getController();
                    storeVO = deviceInfoXmlParse.parseXML();
                    customerSearchController.setStoreVO(storeVO);
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
    void openPresentAction(ActionEvent event) {
        JSONObject searchCustomerInfo = new JSONObject();
        try {
            storeVO = deviceInfoXmlParse.parseXML();

            String phoneNumber = customerVO.getMembershipCustomerPhone();
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

        mSocket.emit("searchCustomerPresent", searchCustomerInfo, new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                String responseVal = "";
                try {
                    responseVal = data.getString("responseCode");
                    System.out.println("-----------------------");
                    System.out.println("Data :::" + data.getString("responseCode"));
                    System.out.println("-----------------------");

                    customerGiftList = customerGiftSet.customerGiftSetVO(data);

                } catch (Exception e) {
                    System.out.println(e);
                }

                if (responseVal.equals("1")) {


                    Platform.runLater(() -> {
                        try {
                            Stage stage = new Stage();
                            stage = (Stage) openPresent.getScene().getWindow();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("Present.fxml"));
                            Parent root = loader.load();
                            PresentController presentController = loader.<PresentController>getController();

                            storeVO = deviceInfoXmlParse.parseXML();
                            presentController.setStoreVO(storeVO);
                            presentController.setCustomerVO(customerVO);
                            presentController.setCustomerGiftList(customerGiftList);
                            Scene scene = new Scene(root);
                            scene.getStylesheets().addAll(Login.class.getResource("Platform.css").toExternalForm());
                            stage.setScene(scene);
                        } catch (Exception e) {
                            System.out.println("File Not Found >>" + e);
                            e.printStackTrace();
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
    void openCouponAction(ActionEvent event) {
        JSONObject searchCustomerInfo = new JSONObject();
        try {
            storeVO = deviceInfoXmlParse.parseXML();

            String phoneNumber = customerVO.getMembershipCustomerPhone();
            String wideManagerId = storeVO.getWideManagerId();
            String mallSocketId = storeVO.getMallSocketId();
            String deviceId = storeVO.getDeviceId();

            System.out.println(">>>" + phoneNumber);
            searchCustomerInfo.put("phoneNumber", phoneNumber);
            searchCustomerInfo.put("wideManagerId", wideManagerId);
            searchCustomerInfo.put("mallSocketId", mallSocketId);
            searchCustomerInfo.put("deviceId", deviceId);

        } catch (Exception e) {
            System.out.println(e);
        }

        mSocket.emit("searchCustomerCoupon", searchCustomerInfo, new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                String responseVal = "";
                try {
                    responseVal = data.getString("responseCode");
                    System.out.println("-----------------------");
                    System.out.println("Data :::" + data.getString("responseCode"));
                    System.out.println("-----------------------");

                    customerCouponList = customerCouponSet.customerCouponSetVO(data);

                } catch (Exception e) {
                    System.out.println(e);
                }

                if (responseVal.equals("1")) {


                    Platform.runLater(() -> {
                        try {
                            Stage stage = new Stage();
                            stage = (Stage) openCoupon.getScene().getWindow();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("Coupon.fxml"));
                            Parent root = loader.load();
                            CouponController couponController = loader.<CouponController>getController();

                            storeVO = deviceInfoXmlParse.parseXML();
                            couponController.setStoreVO(storeVO);
                            couponController.setCustomerVO(customerVO);
                            couponController.setCustomerCouponList(customerCouponList);
                            Scene scene = new Scene(root);
                            scene.getStylesheets().addAll(Login.class.getResource("Platform.css").toExternalForm());
                            stage.setScene(scene);
                        } catch (Exception e) {
                            System.out.println("File Not Found >>" + e);
                            e.printStackTrace();
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

    public void setCustomerVO(CustomerVO customerVO) {
        this.customerVO = customerVO;
        System.out.println("Main>>>>" + this.customerVO.toString());
    }

    public void setCustomerGiftList(ObservableList<CustomerGiftVO> customerGiftList) {
        this.customerGiftList = customerGiftList;
        System.out.println("customerGiftList>>>>" + customerGiftList);
    }

    public void setCustomerCouponList(ObservableList<CustomerCouponVO> customerCouponList) {
        this.customerCouponList = customerCouponList;
        System.out.println("customerCouponList>>>>" + customerCouponList);
    }

}
