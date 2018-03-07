package sample;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CustomerSet {

    CustomerVO customerVO = new CustomerVO();

    CustomerGiftVO customerGiftVO = new CustomerGiftVO();

    public CustomerVO customerSetVO(JSONObject data) throws Exception {

        customerVO.setMembershipCustomerNo(data.getString("membershipCustomerNo"));
        customerVO.setMembershipCustomerPhone(data.getString("membershipCustomerPhone"));
        customerVO.setMembershipCustomerBenefitType(data.getString("membershipCustomerBenefitType"));
        customerVO.setMembershipCustomerGrade(data.getString("membershipCustomerGrade"));
        customerVO.setMembershipCustomerBenefitValue(data.getInt("membershipCustomerBenefitValue"));
        customerVO.setMembershipCustomerReceiveGiftCnt(data.getInt("membershipCustomerReceiveGiftCnt"));
        customerVO.setWideCustomerName(data.getString("wideCustomerName"));
        customerVO.setWideCustomerSex(data.getString("wideCustomerSex"));
        customerVO.setWideCustomerBirth(data.getString("wideCustomerBirth"));


        return customerVO;
    }
}
