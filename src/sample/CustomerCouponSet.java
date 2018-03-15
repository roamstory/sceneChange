package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONObject;

public class CustomerCouponSet {

    public ObservableList<CustomerCouponVO> customerCouponSetVO(JSONObject data) throws Exception {

        ObservableList<CustomerCouponVO> customerCouponList = FXCollections.observableArrayList();

        for (int i = 0; i < data.getInt("couponCount"); i++) {
            JSONObject couponList =  data.getJSONObject("coupon"+i);

            String type = couponList.getString("type");
            String customer_couponproduct_no = couponList.getString("customer_couponproduct_no");
            String coupon_title = couponList.getString("coupon_title");
            String product_title = couponList.getString("product_title");
            String product_image_url = couponList.getString("product_image_url");
            String coupon_image_url = couponList.getString("coupon_image_url");
            int coupon_discount_value = couponList.getInt("coupon_discount_value");
            String coupon_discount_type_code = couponList.getString("coupon_discount_type_code");
            String customer_couponproduct_use_date = couponList.getString("customer_couponproduct_use_date");
            int customer_couponproduct_use_code = couponList.getInt("customer_couponproduct_use_code");
            String customer_couponproduct_begin_date = couponList.getString("customer_couponproduct_begin_date");
            String customer_couponproduct_finish_date = couponList.getString("customer_couponproduct_finish_date");
            String couponUseYn = couponList.getString("couponUseYn");

            customerCouponList.add(new CustomerCouponVO( type,  customer_couponproduct_no,  coupon_title, product_title,  product_image_url, coupon_image_url
                    ,  customer_couponproduct_use_code,  customer_couponproduct_use_date, coupon_discount_type_code, coupon_discount_value,
                     customer_couponproduct_begin_date,  customer_couponproduct_finish_date, couponUseYn));

        }
        System.out.println("customerCouponList>>>>>>>>" + customerCouponList);

        return customerCouponList;
    }

}
