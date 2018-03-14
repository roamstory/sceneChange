package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;
import org.json.JSONException;
import org.json.JSONObject;

import javax.print.DocFlavor;
import java.net.URL;
import java.util.ResourceBundle;

public class PresentController extends SocketConnect implements Initializable {

    DeviceInfoXmlParse deviceInfoXmlParse = new DeviceInfoXmlParse();

    StoreVO storeVO = new StoreVO();

    CustomerVO customerVO = new CustomerVO();

    CustomerGiftSet customerGiftSet = new CustomerGiftSet();

    ObservableList<CustomerGiftVO> customerGiftList = FXCollections.observableArrayList();

    @FXML private TableView<CustomerGiftVO> tableView;
    @FXML private ImageView imageView;
    @FXML private Button maintoPresent;
    @FXML private Stage stage;
    @FXML private Label presentName;
    @FXML private Label presentPeriod;
    @FXML private Label giftNo;
    @FXML private Label type;
    @FXML private Label giftUseYn;
    @FXML private Button insertUse;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void handleBtnOkAction(ActionEvent e) {

        CustomerGiftVO customerGiftVO = tableView.getSelectionModel().getSelectedItem();
        System.out.println("TableView 스마트폰: " + customerGiftVO.getGiftProductName());
        System.out.println("TableView 이미지: " + customerGiftVO.getGiftFinishDate());
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
    public void setStoreVO(StoreVO storeVO) {
        this.storeVO = storeVO;
        System.out.println(this.storeVO.toString());
    }

    public void setCustomerVO(CustomerVO customerVO) {
        this.customerVO = customerVO;
        System.out.println(this.customerVO.toString());
    }

    @FXML
    void insertDataAction(ActionEvent ev) {
        if (!giftUseYn.getText().equals("사용")) {
            Platform.runLater(() -> {
                try {
                    Stage dialog = new Stage(StageStyle.UTILITY);
                    dialog.initModality(Modality.WINDOW_MODAL);
                    dialog.initOwner(stage);
                    dialog.setTitle("확인");

                    Parent parent = FXMLLoader.load(getClass().getResource("present_dialog.fxml"));
                    Label productName = (Label) parent.lookup("#productName");
                    productName.setText(presentName.getText());
                    Label productPeriod = (Label) parent.lookup("#productPeriod");
                    productPeriod.setText(presentPeriod.getText());
                    Button btnCancel = (Button) parent.lookup("#btnCancel");
                    btnCancel.setOnAction(event->dialog.close());
                    Button btnOk = (Button) parent.lookup("#btnOk");
                    btnOk.setOnAction((event) -> {
                        // Button was clicked, do something...
                        goToPresnetUse();
                        dialog.close();
                    });
                    Scene scene = new Scene(parent);

                    dialog.setScene(scene);
                    dialog.setResizable(false);
                    dialog.show();
                }
                catch (Exception e) {
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

    public void goToPresnetUse() {
        System.out.println("선물 사용 하기");
            JSONObject benefitInsertInfo = new JSONObject();
            try {
                System.out.println(type.getText());
                benefitInsertInfo.put("membershipCustomerNo", customerVO.getMembershipCustomerNo());
                benefitInsertInfo.put("giftNo", giftNo.getText());
                benefitInsertInfo.put("wideManagerId", storeVO.getWideManagerId());
                benefitInsertInfo.put("phoneNumber", customerVO.getMembershipCustomerPhone());
                benefitInsertInfo.put("benefitType", storeVO.getBenefitTypeCode());
                benefitInsertInfo.put("mallSocketId", storeVO.getMallSocketId());
            } catch (JSONException jsonEx) {
                jsonEx.printStackTrace();
            }

            System.out.println(benefitInsertInfo);
            mSocket.emit("giftUpdate", benefitInsertInfo, new Ack() {
                @Override
                public void call(Object... args) {
                    JSONObject data = (JSONObject) args[0];
                    String responseVal = "";
                    try {
                        JSONObject giftList = new JSONObject();
                        responseVal = data.getString("responseCode");
                        System.out.println("-----------------------");
                        System.out.println("Data 1:::" + data.getString("giftList"));
                        System.out.println("Data 2:::" + data.getString("responseCode"));
                        System.out.println("-----------------------");
                        giftList = data.getJSONObject("giftList");
                        giftList.put("giftCount" , data.getString("giftCount"));
                        customerGiftList = customerGiftSet.customerGiftSetVO(giftList);
                        System.out.println(">>>>>>" + customerGiftList);
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                    if (responseVal.equals("1") || responseVal.equals("2")) {
                        Platform.runLater(() -> {
                            try {
                                Stage stage = new Stage();
                                stage = (Stage) insertUse.getScene().getWindow();
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("Present.fxml"));
                                Parent root = loader.load();
                                PresentController presentController = loader.<PresentController>getController();
                                storeVO = deviceInfoXmlParse.parseXML();
                                presentController.setStoreVO(storeVO);
                                presentController.setCustomerVO(customerVO);
                                presentController.setCustomerGiftList(customerGiftList);
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
                                txtTitle.setText("선물 사용 시 오류가 발생했습니다.");
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

    public void setCustomerGiftList(ObservableList<CustomerGiftVO> customerGiftList) {
        this.customerGiftList = customerGiftList;
        System.out.println("customerGiftList>????>" + customerGiftList);
        TableColumn tcSmartPhone = tableView.getColumns().get(0);
        tcSmartPhone.setCellValueFactory(
                new PropertyValueFactory("giftProductName")
        );
        tcSmartPhone.setStyle("-fx-alignment: CENTER;");

        TableColumn tcImage = tableView.getColumns().get(1);
        tcImage.setCellValueFactory(
                new PropertyValueFactory("giftUseYn")
        );
        tcImage.setStyle("-fx-alignment: CENTER;");

        tableView.setItems(customerGiftList);
        System.out.println("customerGiftList>>>>" + customerGiftList);

        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerGiftVO>() {
            @Override
            public void changed(ObservableValue<? extends CustomerGiftVO> observable, CustomerGiftVO oldValue, CustomerGiftVO newValue) {
                if(newValue!=null) {
                    customerVO.getMembershipCustomerNo();
                    giftNo.setText(newValue.getGiftNo());
                    type.setText(newValue.getType());
                    giftUseYn.setText(newValue.getGiftUseYn());
                    giftNo.setVisible(false);
                    type.setVisible(false);
                    giftUseYn.setVisible(false);
                    presentName.setText(newValue.getGiftProductName());
                    presentPeriod.setText(newValue.getGiftBeginDate() + " ~ " + newValue.getGiftFinishDate());
                }
            }
        });
    }
}
