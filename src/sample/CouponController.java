package sample;

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
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CouponController extends SocketConnect implements Initializable {

    DeviceInfoXmlParse deviceInfoXmlParse = new DeviceInfoXmlParse();

    StoreVO storeVO = new StoreVO();

    CustomerVO customerVO = new CustomerVO();

    ObservableList<CustomerCouponVO> customerCouponList = FXCollections.observableArrayList();

    @FXML private TableView<CustomerCouponVO> tableView;
    @FXML private ImageView imageView;
    @FXML private Button maintoPresent;
    @FXML private Stage stage;
    @FXML private Label couponName;
    @FXML private Label couponPeriod;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void handleBtnOkAction(ActionEvent e) {

        CustomerCouponVO customerCouponVO = tableView.getSelectionModel().getSelectedItem();
        System.out.println("TableView 스마트폰: " + customerCouponVO.getCoupon_title());
        System.out.println("TableView 이미지: " + customerCouponVO.getCustomer_couponproduct_use_date());
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

    public void setCustomerCouponList(ObservableList<CustomerCouponVO> customerCouponList) {
        this.customerCouponList = customerCouponList;
        System.out.println("customerCouponList>????>" + customerCouponList);
        TableColumn tcSmartPhone = tableView.getColumns().get(0);
        tcSmartPhone.setCellValueFactory(
                new PropertyValueFactory("type")
        );
        tcSmartPhone.setStyle("-fx-alignment: CENTER;");

        TableColumn tcImage = tableView.getColumns().get(1);
        tcImage.setCellValueFactory(
                new PropertyValueFactory("giftProductName")
        );
        tcImage.setStyle("-fx-alignment: CENTER;");

        tableView.setItems(customerCouponList);
        System.out.println("customerCouponList>>>>" + customerCouponList);

        tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<CustomerCouponVO>() {
            @Override
            public void changed(ObservableValue<? extends CustomerCouponVO> observable, CustomerCouponVO oldValue, CustomerCouponVO newValue) {
                if(newValue!=null) {
                    couponName.setText(newValue.getCoupon_title());
                    couponPeriod.setText(newValue.getCustomer_couponproduct_begin_date() + " ~ " + newValue.getCustomer_couponproduct_finish_date());
                }
            }
        });
    }
}