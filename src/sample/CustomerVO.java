package sample;

import javafx.collections.ObservableList;

import java.util.List;

public class CustomerVO {
    private String membershipCustomerNo;
    private String membershipCustomerPhone;
    private String membershipCustomerGrade;
    private String membershipCustomerBenefitType;
    private int membershipCustomerBenefitValue;
    private int membershipCustomerReceiveGiftCnt;
    private String wideCustomerName;
    private String wideCustomerSex;
    private String wideCustomerBirth;
    private int giftCount;

    private List<CustomerGiftVO> customerGiftVOList;

    private ObservableList customerGiftList;

    public String getMembershipCustomerNo() {
        return membershipCustomerNo;
    }

    public void setMembershipCustomerNo(String membershipCustomerNo) {
        this.membershipCustomerNo = membershipCustomerNo;
    }

    public String getMembershipCustomerPhone() {
        return membershipCustomerPhone;
    }

    public void setMembershipCustomerPhone(String membershipCustomerPhone) {
        this.membershipCustomerPhone = membershipCustomerPhone;
    }

    public String getMembershipCustomerGrade() {
        return membershipCustomerGrade;
    }

    public void setMembershipCustomerGrade(String membershipCustomerGrade) {
        this.membershipCustomerGrade = membershipCustomerGrade;
    }

    public String getMembershipCustomerBenefitType() {
        return membershipCustomerBenefitType;
    }

    public void setMembershipCustomerBenefitType(String membershipCustomerBenefitType) {
        this.membershipCustomerBenefitType = membershipCustomerBenefitType;
    }

    public int getMembershipCustomerBenefitValue() {
        return membershipCustomerBenefitValue;
    }

    public void setMembershipCustomerBenefitValue(int membershipCustomerBenefitValue) {
        this.membershipCustomerBenefitValue = membershipCustomerBenefitValue;
    }

    public int getMembershipCustomerReceiveGiftCnt() {
        return membershipCustomerReceiveGiftCnt;
    }

    public void setMembershipCustomerReceiveGiftCnt(int membershipCustomerReceiveGiftCnt) {
        this.membershipCustomerReceiveGiftCnt = membershipCustomerReceiveGiftCnt;
    }

    public String getWideCustomerName() {
        return wideCustomerName;
    }

    public void setWideCustomerName(String wideCustomerName) {
        this.wideCustomerName = wideCustomerName;
    }

    public String getWideCustomerSex() {
        return wideCustomerSex;
    }

    public void setWideCustomerSex(String wideCustomerSex) {
        this.wideCustomerSex = wideCustomerSex;
    }

    public String getWideCustomerBirth() {
        return wideCustomerBirth;
    }

    public void setWideCustomerBirth(String wideCustomerBirth) {
        this.wideCustomerBirth = wideCustomerBirth;
    }

    public int getGiftCount() {
        return giftCount;
    }

    public void setGiftCount(int giftCount) {
        this.giftCount = giftCount;
    }

    public List<CustomerGiftVO> getCustomerGiftVOList() {
        return customerGiftVOList;
    }

    public void setCustomerGiftVOList(List<CustomerGiftVO> customerGiftVOList) {
        this.customerGiftVOList = customerGiftVOList;
    }

    public ObservableList getCustomerGiftList() {
        return customerGiftList;
    }

    public void setCustomerGiftList(ObservableList customerGiftList) {
        this.customerGiftList = customerGiftList;
    }

    @Override
    public String toString() {
        return "CustomerVO{" +
                "membershipCustomerNo='" + membershipCustomerNo + '\'' +
                ", membershipCustomerPhone='" + membershipCustomerPhone + '\'' +
                ", membershipCustomerGrade='" + membershipCustomerGrade + '\'' +
                ", membershipCustomerBenefitType='" + membershipCustomerBenefitType + '\'' +
                ", membershipCustomerBenefitValue=" + membershipCustomerBenefitValue +
                ", membershipCustomerReceiveGiftCnt=" + membershipCustomerReceiveGiftCnt +
                ", wideCustomerName='" + wideCustomerName + '\'' +
                ", wideCustomerSex='" + wideCustomerSex + '\'' +
                ", wideCustomerBirth='" + wideCustomerBirth + '\'' +
                ", giftCount=" + giftCount +
                ", customerGiftVOList=" + customerGiftVOList +
                '}';
    }
}
