package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import io.socket.client.Ack;
import io.socket.emitter.Emitter;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController extends SocketConnect implements Initializable {

    DeviceInfoXmlParse deviceInfoXmlParse = new DeviceInfoXmlParse();

    StoreVO storeVO = new StoreVO();

    CustomerVO customerVO = new CustomerVO();

    CustomerSet customerSet = new CustomerSet();

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
    private Button btnInsertBenefitData;

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
    private ImageView gradeImage;

    @FXML
    private Label benefitType;

    @FXML
    private Label benefitVal;

    @FXML
    private Label benefitTypeName;

    @FXML
    private JFXTextField benefitData;

    private JSONObject CustomerData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        benefitData.setDisable(true);
    }

    // 고객정보 세팅
    void setData(CustomerVO customerVO) {

        Image vipImage =  new Image(getClass().getResourceAsStream("img/vip.png"));
        Image basicImage =  new Image(getClass().getResourceAsStream("img/basic.png"));
        System.out.println("customerVO" + customerVO);
        System.out.println("Main" + storeVO.toString());
        birthday.setText(customerVO.getWideCustomerBirth());
        benefitVal.setText(customerVO.getMembershipCustomerBenefitValue()+"개");
        String phoneSubstring1 = customerVO.getMembershipCustomerPhone().substring(0,3);
        String phoneSubstring2 = customerVO.getMembershipCustomerPhone().substring(3,7);
        String phoneSubstring3 = customerVO.getMembershipCustomerPhone().substring(7);
        phone.setText(phoneSubstring1 + "-" + phoneSubstring2 + "-" + phoneSubstring3);
        name.setText(customerVO.getWideCustomerName());
        if (customerVO.getMembershipCustomerGrade().equals("VIP")) {
            gradeImage.setImage(vipImage);
        } else {
            gradeImage.setImage(basicImage);
        }

        if (customerVO.getWideCustomerSex().equals(0)) {
            sex.setText("(남)");
        } else {
            sex.setText("(여)");
        }

        if (customerVO.getMembershipCustomerBenefitType().equals("S")) {
            benefitTypeName.setText("개");
            benefitType.setText("현재 쿠폰 도장 개수 :");
            benefitVal.setText(customerVO.getMembershipCustomerBenefitValue()+" 개");
        } else {
            benefitTypeName.setText("포인트");
            benefitType.setText("현재 적립 포인트 :");
            benefitVal.setText(customerVO.getMembershipCustomerBenefitValue()+" 포인트");
        }
    }

    // 고객조회화면 이동
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
                    stage.setTitle("고객 조회");
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

    // 선물함 이동
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
                            stage.setTitle("선물함");
                            scene.getStylesheets().addAll(Login.class.getResource("Platform.css").toExternalForm());
                            stage.setScene(scene);
                        } catch (Exception e) {
                            System.out.println("File Not Found >>" + e);
                            e.printStackTrace();
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
                            txtTitle.setText("선물이 없습니다.");
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

    // 쿠폰함 이동
    @FXML
    void openCouponAction(ActionEvent e) {
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

        } catch (Exception ex) {
            System.out.println(ex);
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
                            stage.setTitle("쿠폰함");
                            scene.getStylesheets().addAll(Login.class.getResource("Platform.css").toExternalForm());
                            stage.setScene(scene);
                        } catch (Exception e) {
                            System.out.println("File Not Found >>" + e);
                            e.printStackTrace();
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
                            txtTitle.setText("쿠폰이 없습니다.");
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

    public void handleBtnAction(ActionEvent e) {
        if (storeVO.getBenefitTypeCode().equals("S")) {
            if (benefitData.getText().length() < 2) {
                String buttonText = ((JFXButton)e.getSource()).getText();
                String banefitVal = benefitData.getText().replaceFirst("^0+(?!$)", "");
                benefitData.setText(banefitVal + buttonText);
            }
        } else {
            if (benefitData.getText().length() < 3) {
                String buttonText = ((JFXButton)e.getSource()).getText();
                String banefitVal = benefitData.getText().replaceFirst("^0+(?!$)", "");
                benefitData.setText(banefitVal + buttonText);
            }
        }
    }

    public void handleBtnOkAction(ActionEvent e) {
        System.out.println(benefitData.getText());
    }

    public void handleBtnDelAction(ActionEvent e) {
        String benefitVal = "";
        if(benefitData.getText().length() > 0) {
            benefitVal = benefitData.getText().substring(0, benefitData.getText().length()-1 );
        }
        benefitData.setText(benefitVal);
    }

    @FXML
    void insertBenefitDataAction(ActionEvent e) {
        if (storeVO.getBenefitTypeCode().equals("S")) {
            System.out.println("benefitData.getText() :::" + benefitData.getText());
            System.out.println("benefitData.getText() :::" + benefitData.getText().equals("0"));
            System.out.println("benefitData.getText() :::" + benefitData.getText().equals(0));

            if(!benefitData.getText().equals("") && !benefitData.getText().equals("0")) {
                JSONObject benefitInsertInfo = new JSONObject();
                try {
                    benefitInsertInfo.put("membershipCustomerNo", customerVO.getMembershipCustomerNo());
                    benefitInsertInfo.put("stampSavingCnt", benefitData.getText().replaceFirst("^0+(?!$)", ""));
                    benefitInsertInfo.put("stampGoal", storeVO.getStampGoal());
                    benefitInsertInfo.put("wideManagerId", storeVO.getWideManagerId());
                    benefitInsertInfo.put("phoneNumber", customerVO.getMembershipCustomerPhone());
                    benefitInsertInfo.put("couponNo", storeVO.getBenefitCouponNo());
                    benefitInsertInfo.put("mallSocketId", storeVO.getMallSocketId());
                } catch (JSONException jsonEx) {
                    jsonEx.printStackTrace();
                }

                System.out.println(benefitInsertInfo);
                mSocket.emit("benefitStampUpdate", benefitInsertInfo, new Ack() {
                    @Override
                    public void call(Object... args) {
                        JSONObject data = (JSONObject) args[0];
                        String responseVal = "";
                        try {
                            responseVal = data.getString("responseCode");
                            System.out.println("-----------------------");
                            System.out.println("Data :::" + data);
                            System.out.println("Data :::" + data.getString("responseCode"));
                            System.out.println("-----------------------");
                            customerVO.setMembershipCustomerBenefitValue(Integer.parseInt(data.getString("stampNowCnt")));
                            System.out.println(customerVO);
                        } catch (Exception e) {
                            System.out.println(e);
                        }

                        if (responseVal.equals("1") || responseVal.equals("2")) {
                            Platform.runLater(() -> {
                                try {
                                    Stage stage = new Stage();
                                    stage = (Stage) btnInsertBenefitData.getScene().getWindow();
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
                                    Parent root = loader.load();
                                    MainController mainController = loader.<MainController>getController();
                                    storeVO = deviceInfoXmlParse.parseXML();
                                    mainController.setStoreVO(storeVO);
                                    mainController.setCustomerVO(customerVO);
                                    mainController.setCustomerCouponList(customerCouponList);
                                    mainController.setData(customerVO);
                                    Scene scene = new Scene(root);
                                    stage.setScene(scene);
                                } catch (Exception e) {
                                    System.out.println("File Not Found >>" + e);
                                    e.printStackTrace();
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
                                    txtTitle.setText("적립 시 오류가 발생했습니다.");
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
            } else {
                Platform.runLater(() -> {
                    try {
                        Stage dialog = new Stage(StageStyle.UTILITY);
                        dialog.initModality(Modality.WINDOW_MODAL);
                        dialog.initOwner(stage);
                        dialog.setTitle("확인");

                        Parent parent = FXMLLoader.load(getClass().getResource("custom_dialog.fxml"));
                        Label txtTitle = (Label) parent.lookup("#txtTitle");
                        txtTitle.setText("정확히 입력해 주세요.");
                        Button btnOk = (Button) parent.lookup("#btnOk");
                        btnOk.setOnAction(event->dialog.close());
                        Scene scene = new Scene(parent);

                        dialog.setScene(scene);
                        dialog.setResizable(false);
                        dialog.show();
                    }
                    catch (Exception ex) {
                        System.out.println(ex);
                    }
                });
            }
        } else {
            if(!benefitData.getText().equals("") && !benefitData.getText().equals("0")) {
                JSONObject benefitInsertInfo = new JSONObject();
                try {
                    benefitInsertInfo.put("membershipCustomerNo", customerVO.getMembershipCustomerNo());
                    benefitInsertInfo.put("pointSavingCnt", benefitData.getText());
                    benefitInsertInfo.put("wideManagerId", storeVO.getWideManagerId());
                    benefitInsertInfo.put("phoneNumber", customerVO.getMembershipCustomerPhone());
                    benefitInsertInfo.put("mallSocketId", storeVO.getMallSocketId());
                } catch (JSONException jsonEx) {
                    jsonEx.printStackTrace();
                }

                System.out.println(benefitInsertInfo);
                mSocket.emit("benefitPointUpdate", benefitInsertInfo, new Ack() {
                    @Override
                    public void call(Object... args) {
                        JSONObject data = (JSONObject) args[0];
                        String responseVal = "";
                        try {
                            responseVal = data.getString("responseCode");
                            System.out.println("-----------------------");
                            System.out.println("Data :::" + data);
                            System.out.println("Data :::" + data.getString("responseCode"));
                            System.out.println("-----------------------");
                            customerVO.setMembershipCustomerBenefitValue(Integer.parseInt(data.getString("pointAllCnt")));
                            System.out.println(customerVO);
                        } catch (Exception e) {
                            System.out.println(e);
                        }

                        if (responseVal.equals("1") || responseVal.equals("2")) {
                            Platform.runLater(() -> {
                                try {
                                    Stage stage = new Stage();
                                    stage = (Stage) btnInsertBenefitData.getScene().getWindow();
                                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
                                    Parent root = loader.load();
                                    MainController mainController = loader.<MainController>getController();
                                    storeVO = deviceInfoXmlParse.parseXML();
                                    mainController.setStoreVO(storeVO);
                                    mainController.setCustomerVO(customerVO);
                                    mainController.setCustomerCouponList(customerCouponList);
                                    mainController.setData(customerVO);
                                    Scene scene = new Scene(root);
                                    stage.setScene(scene);
                                } catch (Exception e) {
                                    System.out.println("File Not Found >>" + e);
                                    e.printStackTrace();
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
                                    txtTitle.setText("적립 시 오류가 발생했습니다.");
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
            } else {
                Platform.runLater(() -> {
                    try {
                        Stage dialog = new Stage(StageStyle.UTILITY);
                        dialog.initModality(Modality.WINDOW_MODAL);
                        dialog.initOwner(stage);
                        dialog.setTitle("확인");

                        Parent parent = FXMLLoader.load(getClass().getResource("custom_dialog.fxml"));
                        Label txtTitle = (Label) parent.lookup("#txtTitle");
                        txtTitle.setText("정확히 입력해 주세요.");
                        Button btnOk = (Button) parent.lookup("#btnOk");
                        btnOk.setOnAction(event->dialog.close());
                        Scene scene = new Scene(parent);

                        dialog.setScene(scene);
                        dialog.setResizable(false);
                        dialog.show();
                    }
                    catch (Exception ex) {
                        System.out.println(ex);
                    }
                });
            }
        }
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
