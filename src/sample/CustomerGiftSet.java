package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONObject;

public class CustomerGiftSet {

    public ObservableList<CustomerGiftVO> customerGiftSetVO(JSONObject data) throws Exception {

        ObservableList<CustomerGiftVO> customerGiftList = FXCollections.observableArrayList();

        for (int i = 0; i < data.getInt("giftCount"); i++) {
            JSONObject giftList =  data.getJSONObject("giftList"+i);

            String type = giftList.getString("type");
            String giftNo = giftList.getString("giftNo");
            String giftProductName = giftList.getString("giftProductName");
            String giftProductImageUrl = giftList.getString("giftProductImageUrl");
            String giftUseDate = giftList.getString("giftUseDate");
            int giftUseStatusCode = giftList.getInt("giftUseStatusCode");
            String giftFinishDate = giftList.getString("giftFinishDate");
            String giftBeginDate = giftList.getString("giftBeginDate");
            String giftUseYn = giftList.getString("giftUseYn");

            customerGiftList.add(new CustomerGiftVO(type, giftNo, giftProductName, giftProductImageUrl, giftUseStatusCode, giftUseDate, giftBeginDate, giftFinishDate,giftUseYn));

        }
        System.out.println("customerGiftList>>>>>>>>" + customerGiftList);

        return customerGiftList;
    }

}
