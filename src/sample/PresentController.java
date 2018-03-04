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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.print.DocFlavor;
import java.net.URL;
import java.util.ResourceBundle;

public class PresentController implements Initializable {

    @FXML private TableView<Phone> tableView;
    @FXML private ImageView imageView;
    @FXML private Button customerToMain;
    @FXML
    private Stage stage;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList phoneList = FXCollections.observableArrayList(
                new Phone("갤럭시S1", "phone01.png"),
                new Phone("갤럭시S2", "phone02.png"),
                new Phone("갤럭시S3", "phone03.png"),
                new Phone("갤럭시S4", "phone04.png"),
                new Phone("갤럭시S5", "phone05.png"),
                new Phone("갤럭시S6", "phone06.png"),
                new Phone("갤럭시S1", "phone01.png"),
                new Phone("갤럭시S2", "phone02.png"),
                new Phone("갤럭시S3", "phone03.png"),
                new Phone("갤럭시S4", "phone04.png"),
                new Phone("갤럭시S5", "phone05.png"),
                new Phone("갤럭시S6", "phone06.png"),
                new Phone("갤럭시S1", "phone01.png"),
                new Phone("갤럭시S2", "phone02.png"),
                new Phone("갤럭시S3", "phone03.png"),
                new Phone("갤럭시S4", "phone04.png"),
                new Phone("갤럭시S5", "phone05.png"),
                new Phone("갤럭시S6", "phone06.png"),
                new Phone("갤럭시S7", "phone07.png")
        );

        TableColumn tcSmartPhone = tableView.getColumns().get(0);
        tcSmartPhone.setCellValueFactory(
                new PropertyValueFactory("smartPhone")
        );
        tcSmartPhone.setStyle("-fx-alignment: CENTER;");

        TableColumn tcImage = tableView.getColumns().get(1);
        tcImage.setCellValueFactory(
                new PropertyValueFactory("image")
        );
        tcImage.setStyle("-fx-alignment: CENTER;");

        tableView.setItems(phoneList);

    }

    public void handleBtnOkAction(ActionEvent e) {

        Phone phone = tableView.getSelectionModel().getSelectedItem();
        System.out.println("TableView 스마트폰: " + phone.getSmartPhone());
        System.out.println("TableView 이미지: " + phone.getImage());
    }

    @FXML
    void customertoMainAction(ActionEvent event) {

        try {
            Platform.runLater(()-> {
                try {
                    stage = (Stage) customerToMain.getScene().getWindow();
                    System.out.println("mainStage ::" + stage);
                    Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
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
}
