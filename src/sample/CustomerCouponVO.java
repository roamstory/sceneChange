package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class CustomerCouponVO {

    private SimpleStringProperty type;
    private SimpleStringProperty customer_couponproduct_no;
    private SimpleStringProperty coupon_title;
    private SimpleStringProperty product_title;
    private SimpleStringProperty product_image_url;
    private SimpleIntegerProperty customer_couponproduct_use_code;
    private SimpleStringProperty customer_couponproduct_use_date;
    private SimpleStringProperty customer_couponproduct_begin_date;
    private SimpleStringProperty customer_couponproduct_finish_date;
    private SimpleStringProperty couponUseYn;


    public CustomerCouponVO() {
        this.type = new SimpleStringProperty();
        this.customer_couponproduct_no = new SimpleStringProperty();
        this.coupon_title = new SimpleStringProperty();
        this.product_title = new SimpleStringProperty();
        this.product_image_url = new SimpleStringProperty();
        this.customer_couponproduct_use_code = new SimpleIntegerProperty();
        this.customer_couponproduct_use_date = new SimpleStringProperty();
        this.customer_couponproduct_begin_date = new SimpleStringProperty();
        this.customer_couponproduct_finish_date = new SimpleStringProperty();
        this.couponUseYn = new SimpleStringProperty();

    }
    public CustomerCouponVO(String type, String customer_couponproduct_no, String coupon_title,String product_title, String product_image_url
            , int customer_couponproduct_use_code, String customer_couponproduct_use_date,
                            String customer_couponproduct_begin_date, String customer_couponproduct_finish_date, String couponUseYn) {
        this.type = new SimpleStringProperty(type);
        this.customer_couponproduct_no = new SimpleStringProperty(customer_couponproduct_no);
        this.coupon_title = new SimpleStringProperty(coupon_title);
        this.product_title = new SimpleStringProperty(product_title);
        this.product_image_url = new SimpleStringProperty(product_image_url);
        this.customer_couponproduct_use_code = new SimpleIntegerProperty(customer_couponproduct_use_code);
        this.customer_couponproduct_use_date = new SimpleStringProperty(customer_couponproduct_use_date);
        this.customer_couponproduct_begin_date = new SimpleStringProperty(customer_couponproduct_begin_date);
        this.customer_couponproduct_finish_date = new SimpleStringProperty(customer_couponproduct_finish_date);
        this.couponUseYn = new SimpleStringProperty(couponUseYn);

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

    public String getCustomer_couponproduct_no() {
        return customer_couponproduct_no.get();
    }

    public SimpleStringProperty customer_couponproduct_noProperty() {
        return customer_couponproduct_no;
    }

    public void setCustomer_couponproduct_no(String customer_couponproduct_no) {
        this.customer_couponproduct_no.set(customer_couponproduct_no);
    }

    public String getCoupon_title() {
        return coupon_title.get();
    }

    public SimpleStringProperty coupon_titleProperty() {
        return coupon_title;
    }

    public void setCoupon_title(String coupon_title) {
        this.coupon_title.set(coupon_title);
    }

    public String getProduct_title() {
        return product_title.get();
    }

    public SimpleStringProperty product_titleProperty() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title.set(product_title);
    }

    public String getProduct_image_url() {
        return product_image_url.get();
    }

    public SimpleStringProperty product_image_urlProperty() {
        return product_image_url;
    }

    public void setProduct_image_url(String product_image_url) {
        this.product_image_url.set(product_image_url);
    }

    public int getCustomer_couponproduct_use_code() {
        return customer_couponproduct_use_code.get();
    }

    public SimpleIntegerProperty customer_couponproduct_use_codeProperty() {
        return customer_couponproduct_use_code;
    }

    public void setCustomer_couponproduct_use_code(int customer_couponproduct_use_code) {
        this.customer_couponproduct_use_code.set(customer_couponproduct_use_code);
    }

    public String getCustomer_couponproduct_use_date() {
        return customer_couponproduct_use_date.get();
    }

    public SimpleStringProperty customer_couponproduct_use_dateProperty() {
        return customer_couponproduct_use_date;
    }

    public void setCustomer_couponproduct_use_date(String customer_couponproduct_use_date) {
        this.customer_couponproduct_use_date.set(customer_couponproduct_use_date);
    }

    public String getCustomer_couponproduct_begin_date() {
        return customer_couponproduct_begin_date.get();
    }

    public SimpleStringProperty customer_couponproduct_begin_dateProperty() {
        return customer_couponproduct_begin_date;
    }

    public void setCustomer_couponproduct_begin_date(String customer_couponproduct_begin_date) {
        this.customer_couponproduct_begin_date.set(customer_couponproduct_begin_date);
    }

    public String getCustomer_couponproduct_finish_date() {
        return customer_couponproduct_finish_date.get();
    }

    public SimpleStringProperty customer_couponproduct_finish_dateProperty() {
        return customer_couponproduct_finish_date;
    }

    public void setCustomer_couponproduct_finish_date(String customer_couponproduct_finish_date) {
        this.customer_couponproduct_finish_date.set(customer_couponproduct_finish_date);
    }

    public String getCouponUseYn() {
        return couponUseYn.get();
    }

    public SimpleStringProperty couponUseYnProperty() {
        return couponUseYn;
    }

    public void setCouponUseYn(String couponUseYn) {
        this.couponUseYn.set(couponUseYn);
    }
}
