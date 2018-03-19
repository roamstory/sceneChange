package sample;

import com.jfoenix.controls.JFXButton;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.socket.client.Ack;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class CouponController extends SocketConnect implements Initializable {

    DeviceInfoXmlParse deviceInfoXmlParse = new DeviceInfoXmlParse();

    StoreVO storeVO = new StoreVO();

    CustomerVO customerVO = new CustomerVO();

    CustomerCouponSet customerCouponSet = new CustomerCouponSet();

    ObservableList<CustomerCouponVO> customerCouponList = FXCollections.observableArrayList();

    @FXML private TableView<CustomerCouponVO> tableView;
    @FXML private ImageView imageView;
    @FXML private Button maintoPresent;
    @FXML private Stage stage;
    @FXML private Label couponName;
    @FXML private Label couponPeriod;
    @FXML private Label couponProduct;
    @FXML private Label couponNo;
    @FXML private Label type;
    @FXML private Label couponUseYn;
    @FXML private Label productImageUrl;
    @FXML private Button insertUse;
    private String typeHide;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void maintoPresentAction(ActionEvent event) {
        try {
            Platform.runLater(()-> {
                try {
                    Stage stage = new Stage();
                    stage = (Stage) maintoPresent.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
                    Parent root = loader.load();
                    MainController mainController = loader.<MainController>getController();
                    storeVO = deviceInfoXmlParse.parseXML();
                    mainController.setData(customerVO);
                    mainController.setStoreVO(storeVO);
                    System.out.println("customerVO는" + customerVO.toString());
                    mainController.setCustomerVO(customerVO);
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                } catch(Exception e) {
                    System.out.println(">>>" + e);
                }
            });
        } catch (Exception e) {
            System.out.println("File Not Found >>" + e);
            e.printStackTrace();
        }
    }

    @FXML
    void insertDataAction(ActionEvent ev) {
        if (!couponUseYn.getText().equals("사용")) {
            Platform.runLater(() -> {
                try {
                    String productImage = productImageUrl.getText();
                    Stage dialog = new Stage(StageStyle.UTILITY);
                    dialog.initModality(Modality.WINDOW_MODAL);
                    dialog.initOwner(stage);
                    dialog.setTitle("쿠폰 사용여부 확인");
                    Parent parent = FXMLLoader.load(getClass().getResource("coupon_dialog.fxml"));
                    System.out.println("productImage >" + productImage);
                    typeHide = type.getText();
                    ImageView productImageView = (ImageView) parent.lookup("#productImageView");
                    productImageView.setImage(new Image(productImage));
                    Label productName = (Label) parent.lookup("#productName");
                    productName.setText(couponName.getText());
                    Label productPeriod = (Label) parent.lookup("#productPeriod");
                    productPeriod.setText(couponPeriod.getText());
                    Button btnCancel = (Button) parent.lookup("#btnCancel");
                    btnCancel.setOnAction(event->dialog.close());
                    Button btnOk = (Button) parent.lookup("#btnOk");
                    btnOk.setOnAction((event) -> {
                        // Button was clicked, do something...
                        goToCouponUse();
                        dialog.close();
                    });
                    Scene scene = new Scene(parent);

                    dialog.setScene(scene);
                    dialog.setResizable(false);
                    dialog.show();
                }
                catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e);
                }
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
                    txtTitle.setText("사용이 되어 사용이 불가합니다.");
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

    @FXML
    void couponUse(ActionEvent ev) {
        System.out.println("쿠폰 사용 하기");
        JSONObject benefitInsertInfo = new JSONObject();
        try {
            System.out.println(typeHide);
            benefitInsertInfo.put("membershipCustomerNo", customerVO.getMembershipCustomerNo());
            benefitInsertInfo.put("customer_couponproduct_no", couponNo.getText());
            benefitInsertInfo.put("wideManagerId", storeVO.getWideManagerId());
            benefitInsertInfo.put("phoneNumber", customerVO.getMembershipCustomerPhone());
            benefitInsertInfo.put("benefitType", storeVO.getBenefitTypeCode());
            benefitInsertInfo.put("mallSocketId", storeVO.getMallSocketId());
        } catch (JSONException jsonEx) {
            jsonEx.printStackTrace();
        }

        System.out.println(benefitInsertInfo);
        mSocket.emit("couponUpdate", benefitInsertInfo, new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                String responseVal = "";
                Boolean useOk = false;
                try {
                    JSONObject couponList = new JSONObject();
                    responseVal = data.getString("responseCode");
                    System.out.println("-----------------------");
                    System.out.println("Data 1:::" + data.getString("couponList"));
                    System.out.println("Data 2:::" + data.getString("responseCode"));
                    System.out.println("-----------------------");
                    couponList = data.getJSONObject("couponList");
                    couponList.put("couponCount" , data.getString("couponCount"));
                    customerCouponList = customerCouponSet.customerCouponSetVO(couponList);
                    System.out.println(">>>>>>" + customerCouponList);
                } catch (Exception e) {
                    System.out.println(e);
                }

                if (responseVal.equals("3") ) {
                    Platform.runLater(() -> {
                        try {
                            Stage stage = new Stage();
                            stage = (Stage) insertUse.getScene().getWindow();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("Coupon.fxml"));
                            Parent root = loader.load();
                            CouponController couponController = loader.<CouponController>getController();
                            storeVO = deviceInfoXmlParse.parseXML();
                            couponController.setStoreVO(storeVO);
                            couponController.setCustomerVO(customerVO);
                            couponController.setCustomerCouponList(customerCouponList);
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                        } catch (Exception e) {
                            System.out.println("File Not Found >>" + e);
                            e.printStackTrace();
                        }
                    });
                    useOk = true;

                } else {
                    Platform.runLater(() -> {
                        try {
                            Stage dialog = new Stage(StageStyle.UTILITY);
                            dialog.initModality(Modality.WINDOW_MODAL);
                            dialog.initOwner(stage);
                            dialog.setTitle("확인");

                            Parent parent = FXMLLoader.load(getClass().getResource("custom_dialog.fxml"));
                            Label txtTitle = (Label) parent.lookup("#txtTitle");
                            txtTitle.setText("쿠폰 사용 시 오류가 발생했습니다.");
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

                if (useOk == true) {
                    Platform.runLater(() -> {
                        try {
                            Stage dialog = new Stage(StageStyle.UTILITY);
                            dialog.initModality(Modality.WINDOW_MODAL);
                            dialog.initOwner(stage);
                            dialog.setTitle("확인");

                            Parent parent = FXMLLoader.load(getClass().getResource("custom_dialog.fxml"));
                            Label txtTitle = (Label) parent.lookup("#txtTitle");
                            txtTitle.setText("쿠폰이 사용되었습니다.");
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

    public void goToCouponUse() {
        System.out.println("쿠폰 사용 하기");
        JSONObject benefitInsertInfo = new JSONObject();
        try {
            System.out.println(type.getText());
            benefitInsertInfo.put("membershipCustomerNo", customerVO.getMembershipCustomerNo());
            benefitInsertInfo.put("customer_couponproduct_no", couponNo.getText());
            benefitInsertInfo.put("wideManagerId", storeVO.getWideManagerId());
            benefitInsertInfo.put("phoneNumber", customerVO.getMembershipCustomerPhone());
            benefitInsertInfo.put("benefitType", storeVO.getBenefitTypeCode());
            benefitInsertInfo.put("mallSocketId", storeVO.getMallSocketId());
        } catch (JSONException jsonEx) {
            jsonEx.printStackTrace();
        }

        System.out.println(benefitInsertInfo);
        mSocket.emit("couponUpdate", benefitInsertInfo, new Ack() {
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                String responseVal = "";
                Boolean useOk = false;
                try {
                    JSONObject couponList = new JSONObject();
                    responseVal = data.getString("responseCode");
                    System.out.println("-----------------------");
                    System.out.println("Data 1:::" + data.getString("couponList"));
                    System.out.println("Data 2:::" + data.getString("responseCode"));
                    System.out.println("-----------------------");
                    couponList = data.getJSONObject("couponList");
                    couponList.put("couponCount" , data.getString("couponCount"));
                    customerCouponList = customerCouponSet.customerCouponSetVO(couponList);
                    System.out.println(">>>>>>" + customerCouponList);
                } catch (Exception e) {
                    System.out.println(e);
                }

                if (responseVal.equals("3")) {
                    Platform.runLater(() -> {
                        try {
                            Stage stage = new Stage();
                            stage = (Stage) insertUse.getScene().getWindow();
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("Coupon.fxml"));
                            Parent root = loader.load();
                            CouponController couponController = loader.<CouponController>getController();
                            storeVO = deviceInfoXmlParse.parseXML();
                            couponController.setStoreVO(storeVO);
                            couponController.setCustomerVO(customerVO);
                            couponController.setCustomerCouponList(customerCouponList);
                            Scene scene = new Scene(root);
                            stage.setScene(scene);
                        } catch (Exception e) {
                            System.out.println("File Not Found >>" + e);
                            e.printStackTrace();
                        }
                    });
                    useOk = true;

                } else {
                    Platform.runLater(() -> {
                        try {
                            Stage dialog = new Stage(StageStyle.UTILITY);
                            dialog.initModality(Modality.WINDOW_MODAL);
                            dialog.initOwner(stage);
                            dialog.setTitle("확인");

                            Parent parent = FXMLLoader.load(getClass().getResource("custom_dialog.fxml"));
                            Label txtTitle = (Label) parent.lookup("#txtTitle");
                            txtTitle.setText("쿠폰 사용 시 오류가 발생했습니다.");
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

                if (useOk == true) {
                    Platform.runLater(() -> {
                        try {
                            Stage dialog = new Stage(StageStyle.UTILITY);
                            dialog.initModality(Modality.WINDOW_MODAL);
                            dialog.initOwner(stage);
                            dialog.setTitle("확인");

                            Parent parent = FXMLLoader.load(getClass().getResource("custom_dialog.fxml"));
                            Label txtTitle = (Label) parent.lookup("#txtTitle");
                            txtTitle.setText("쿠폰이 사용되었습니다.");
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

    public void setStoreVO(StoreVO storeVO) {
        this.storeVO = storeVO;
        System.out.println(this.storeVO.toString());
    }

    public void setCustomerVO(CustomerVO customerVO) {
        this.customerVO = customerVO;
        System.out.println(this.customerVO.toString());
    }

    public void setCustomerCouponList(ObservableList<CustomerCouponVO> customerCouponList) {
        this.customerCouponList = customerCouponList;
        System.out.println("customerCouponList>????>" + customerCouponList);
        TableColumn tcSmartPhone = tableView.getColumns().get(0);
        tcSmartPhone.setCellValueFactory(
                new PropertyValueFactory("coupon_title")
        );
        tcSmartPhone.setStyle("-fx-alignment: CENTER;");

        TableColumn tcImage = tableView.getColumns().get(1);
        tcImage.setCellValueFactory(
                new PropertyValueFactory("couponUseYn")
        );
        tcImage.setStyle("-fx-alignment: CENTER;");

        tableView.setItems(customerCouponList);
        System.out.println("customerCouponList>>>>" + customerCouponList);

        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerCouponVO>() {
            @Override
            public void changed(ObservableValue<? extends CustomerCouponVO> observable, CustomerCouponVO oldValue, CustomerCouponVO newValue) {
                if(newValue!=null) {

                    couponNo.setText(newValue.getCustomer_couponproduct_no());
                    type.setText(newValue.getType());
                    couponUseYn.setText(newValue.getCouponUseYn());
                    System.out.println(newValue.getProduct_image_url()+">>>");
                    productImageUrl.setText(newValue.getCoupon_image_url());
                    couponNo.setVisible(false);
                    type.setVisible(false);
                    couponUseYn.setVisible(false);
                    productImageUrl.setVisible(false);
                    couponName.setText(newValue.getCoupon_title());
                    String typeName = "";
                    if (newValue.getCoupon_discount_type_code() == "DCP") {
                        typeName = "원";
                    } else {
                        typeName = "%";
                    }
                    couponProduct.setText(newValue.getProduct_title() + " " + newValue.getCoupon_discount_value() + typeName + "할인");
                    couponPeriod.setText(newValue.getCustomer_couponproduct_begin_date() + " ~ " + newValue.getCustomer_couponproduct_finish_date());

                }
            }
        });
    }
}
