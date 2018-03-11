package sample;

import com.jfoenix.controls.JFXButton;
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
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.json.JSONObject;

import javax.print.DocFlavor;
import java.net.URL;
import java.util.ResourceBundle;

public class PresentController extends SocketConnect implements Initializable {

    DeviceInfoXmlParse deviceInfoXmlParse = new DeviceInfoXmlParse();

    StoreVO storeVO = new StoreVO();

    CustomerVO customerVO = new CustomerVO();

    ObservableList<CustomerGiftVO> customerGiftList = FXCollections.observableArrayList();
    Popup popup;

    @FXML private TableView<CustomerGiftVO> tableView;
    @FXML private ImageView imageView;
    @FXML private Button maintoPresent;
    @FXML private Stage stage;
    @FXML private Label presentName;
    @FXML private Label presentPeriod;

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
                    newValue.getType();
                    newValue.getGiftNo();
                    presentName.setText(newValue.getGiftProductName());
                    presentPeriod.setText(newValue.getGiftBeginDate() + " ~ " + newValue.getGiftFinishDate());
                }
            }
        });
    }
}
