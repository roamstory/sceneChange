package sample;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

public class DeviceInfoXmlParse {

    static StoreVO storeVO = new StoreVO();

    public static StoreVO parseXML() throws Exception {


        // [REF] https://msdn.microsoft.com/ko-kr/library/ms762271(v=vs.85).aspx

        Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse("deviceInfo.xml");



        // XPath 인스턴스 생성

        XPath xpath = XPathFactory.newInstance().newXPath();



        // 대상 노드 지정

        String expression = "/wishwide";



        // 지정 노드로 부터 노드목록 획득

        NodeList nl = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);



        // 첫번째 노드의 부모 노드명을 출력

        System.out.println(nl.item(0).getParentNode().getNodeName());



        System.out.println("sub node start!!");

        System.out.println("-----------------------------------------------------");

        // 1번째  book 노드의 자식노드 목록을 획득

        NodeList cnl = nl.item(0).getChildNodes(); // author, title, genre, price, publish_date, description

        System.out.println(cnl.item(0).getParentNode().getNodeName());



        // 확보된 노드 목록을 순서대로 출력

        for(int i=0; i<cnl.getLength(); i++)

        {

            // 노드 이름이 #text로 출력되는 문제로 스킵 하도록 설정하였다.

            // xml파일의 들여쓰기로 인한 문제 인듯 하다.

            if("#text".equals(cnl.item(i).getNodeName()))

                continue;



            // 현재 노드 인덱스 번호 출력
            System.out.println(i);



            // 노드 명 출력
            System.out.println("NODE : " + cnl.item(i).getNodeName());


            // 노드 값 출력
            System.out.println("VALUE : " + cnl.item(i).getTextContent());


            switch (cnl.item(i).getNodeName()) {

                case "mallSocketId" :

                    System.out.println("mallSocketId set");
                    storeVO.setMallSocketId(cnl.item(i).getTextContent());
                    break;

                case "deviceId" :

                    System.out.println("deviceId set");
                    storeVO.setDeviceId(cnl.item(i).getTextContent());
                    break;

                case "wideManagerId" :

                    System.out.println("wideManagerId set");
                    storeVO.setWideManagerId(cnl.item(i).getTextContent());
                    break;

                case "wideManagerPassword" :

                    System.out.println("wideManagerPassword set");
                    storeVO.setWideManagerPassword(cnl.item(i).getTextContent());
                    break;

                case "wideManagerName" :

                    System.out.println("wideManagerName set");
                    storeVO.setWideManagerName(cnl.item(i).getTextContent());
                    break;

                case "benefitTypeCode" :

                    System.out.println("benefitTypeCode set");
                    storeVO.setBenefitTypeCode(cnl.item(i).getTextContent());
                    break;

                case "stampGoal" :

                    System.out.println("stampGoal set");
                    storeVO.setStampGoal(Integer.parseInt(cnl.item(i).getTextContent()));
                    break;

                case "stampVipGoal" :

                    System.out.println("stampVipGoal set");
                    storeVO.setStampVipGoal(Integer.parseInt(cnl.item(i).getTextContent()));
                    break;

                case "stampVipDiscountRate" :

                    System.out.println("stampVipDiscountRate set");
                    storeVO.setStampVipDiscountRate(Integer.parseInt(cnl.item(i).getTextContent()));
                    break;

                case "pointRate" :

                    System.out.println("pointRate set");
                    storeVO.setPointRate(Integer.parseInt(cnl.item(i).getTextContent()));
                    break;

                case "pointUseGoal" :

                    System.out.println("pointUseGoal set");
                    storeVO.setPointUseGoal(Integer.parseInt(cnl.item(i).getTextContent()));
                    break;

                case "pointVipGoal" :

                    System.out.println("pointVipGoal set");
                    storeVO.setPointVipGoal(Integer.parseInt(cnl.item(i).getTextContent()));
                    break;

                case "pointVipRate" :

                    System.out.println("pointVipRate set");
                    storeVO.setPointVipRate(Integer.parseInt(cnl.item(i).getTextContent()));
                    break;

                case "pointVipDiscountRate" :

                    System.out.println("pointVipDiscountRate set");
                    storeVO.setPointVipDiscountRate(Integer.parseInt(cnl.item(i).getTextContent()));
                    break;

                case "stampCommonStatusCode" :

                    System.out.println("stampCommonStatusCode set");
                    storeVO.setStampCommonStatusCode(Integer.parseInt(cnl.item(i).getTextContent()));
                    break;


                case "pointCommonStatusCode" :

                    System.out.println("pointCommonStatusCode set");
                    storeVO.setPointCommonStatusCode(Integer.parseInt(cnl.item(i).getTextContent()));
                    break;


                case "wideManagerFinishDate" :

                    System.out.println("wideManagerFinishDate set");
                    storeVO.setWideManagerFinishDate(cnl.item(i).getTextContent());
                    break;

                case "contractStatusCode" :

                    System.out.println("contractStatusCode set");
                    storeVO.setContractStatusCode(cnl.item(i).getTextContent());
                    break;

                case "wideManagerRole" :

                    System.out.println("wideManagerRole set");
                    storeVO.setWideManagerRole(cnl.item(i).getTextContent());
                    break;

                case "benefitCouponNo" :

                    System.out.println("benefitCouponNo set");
                    storeVO.setBenefitCouponNo(Integer.parseInt(cnl.item(i).getTextContent()));
                    break;

            }
        }

        return storeVO;

    }
}
