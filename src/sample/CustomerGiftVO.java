package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CustomerGiftVO {

    private SimpleStringProperty type;
    private SimpleStringProperty giftNo;
    private SimpleStringProperty giftProductName;
    private SimpleStringProperty giftProductImageUrl;
    private SimpleIntegerProperty giftUseStatusCode;
    private SimpleStringProperty giftUseDate;
    private SimpleStringProperty giftBeginDate;
    private SimpleStringProperty giftFinishDate;


    public CustomerGiftVO() {
        this.type = new SimpleStringProperty();
        this.giftNo = new SimpleStringProperty();
        this.giftProductName = new SimpleStringProperty();
        this.giftProductImageUrl = new SimpleStringProperty();
        this.giftUseStatusCode = new SimpleIntegerProperty();
        this.giftUseDate = new SimpleStringProperty();
        this.giftBeginDate = new SimpleStringProperty();
        this.giftFinishDate = new SimpleStringProperty();

    }
    public CustomerGiftVO(String type, String giftNo, String giftProductName, String giftProductImageUrl
            , int giftUseStatusCode, String giftUseDate, String giftBeginDate, String giftFinishDate) {
        this.type = new SimpleStringProperty(type);
        this.giftNo = new SimpleStringProperty(giftNo);
        this.giftProductName = new SimpleStringProperty(giftProductName);
        this.giftProductImageUrl = new SimpleStringProperty(giftProductImageUrl);
        this.giftUseStatusCode = new SimpleIntegerProperty(giftUseStatusCode);
        this.giftUseDate = new SimpleStringProperty(giftUseDate);
        this.giftBeginDate = new SimpleStringProperty(giftBeginDate);
        this.giftFinishDate = new SimpleStringProperty(giftFinishDate);

    }

    public String getType() {
        return type.get();
    }

    public SimpleStringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getGiftNo() {
        return giftNo.get();
    }

    public SimpleStringProperty giftNoProperty() {
        return giftNo;
    }

    public void setGiftNo(String giftNo) {
        this.giftNo.set(giftNo);
    }

    public String getGiftProductName() {
        return giftProductName.get();
    }

    public SimpleStringProperty giftProductNameProperty() {
        return giftProductName;
    }

    public void setGiftProductName(String giftProductName) {
        this.giftProductName.set(giftProductName);
    }

    public String getGiftProductImageUrl() {
        return giftProductImageUrl.get();
    }

    public SimpleStringProperty giftProductImageUrlProperty() {
        return giftProductImageUrl;
    }

    public void setGiftProductImageUrl(String giftProductImageUrl) {
        this.giftProductImageUrl.set(giftProductImageUrl);
    }

    public int getGiftUseStatusCode() {
        return giftUseStatusCode.get();
    }

    public SimpleIntegerProperty giftUseStatusCodeProperty() {
        return giftUseStatusCode;
    }

    public void setGiftUseStatusCode(int giftUseStatusCode) {
        this.giftUseStatusCode.set(giftUseStatusCode);
    }

    public String getGiftUseDate() {
        return giftUseDate.get();
    }

    public SimpleStringProperty giftUseDateProperty() {
        return giftUseDate;
    }

    public void setGiftUseDate(String giftUseDate) {
        this.giftUseDate.set(giftUseDate);
    }

    public String getGiftBeginDate() {
        return giftBeginDate.get();
    }

    public SimpleStringProperty giftBeginDateProperty() {
        return giftBeginDate;
    }

    public void setGiftBeginDate(String giftBeginDate) {
        this.giftBeginDate.set(giftBeginDate);
    }

    public String getGiftFinishDate() {
        return giftFinishDate.get();
    }

    public SimpleStringProperty giftFinishDateProperty() {
        return giftFinishDate;
    }

    public void setGiftFinishDate(String giftFinishDate) {
        this.giftFinishDate.set(giftFinishDate);
    }
}
