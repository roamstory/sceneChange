package sample;

import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteInfoXml {
    void writeInfo(JSONObject data) {

        File file = new File("deviceInfo.xml");

        if( file.exists() ){
            try {
                FileWriter fw = new FileWriter(file);
                fw.write("");
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println(e);
            }
        }

        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Node root = document.createElement("wishwide");
            document.appendChild(root);
            {
                {
                    Element mallSocketId = document.createElement("mallSocketId");
                    mallSocketId.appendChild(document.createTextNode(data.getString("mallSocketId")));
                    root.appendChild(mallSocketId);
                }
                {
                    Element deviceId = document.createElement("deviceId");
                    deviceId.appendChild(document.createTextNode(data.getString("deviceId")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("wideManagerId");
                    deviceId.appendChild(document.createTextNode(data.getString("wideManagerId")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("wideManagerPassword");
                    deviceId.appendChild(document.createTextNode(data.getString("wideManagerPassword")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("wideManagerName");
                    deviceId.appendChild(document.createTextNode(data.getString("wideManagerName")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("benefitTypeCode");
                    deviceId.appendChild(document.createTextNode(data.getString("benefitTypeCode")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("benefitCouponNo");
                    deviceId.appendChild(document.createTextNode(data.getString("benefitCouponNo")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("stampGoal");
                    deviceId.appendChild(document.createTextNode(data.getString("stampGoal")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("stampVipGoal");
                    deviceId.appendChild(document.createTextNode(data.getString("stampVipGoal")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("stampVipDiscountRate");
                    deviceId.appendChild(document.createTextNode(data.getString("stampVipDiscountRate")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("pointRate");
                    deviceId.appendChild(document.createTextNode(data.getString("pointRate")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("pointUseGoal");
                    deviceId.appendChild(document.createTextNode(data.getString("pointUseGoal")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("pointVipGoal");
                    deviceId.appendChild(document.createTextNode(data.getString("pointVipGoal")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("pointVipRate");
                    deviceId.appendChild(document.createTextNode(data.getString("pointVipRate")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("pointVipDiscountRate");
                    deviceId.appendChild(document.createTextNode(data.getString("pointVipDiscountRate")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("stampCommonStatusCode");
                    deviceId.appendChild(document.createTextNode(data.getString("stampCommonStatusCode")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("pointCommonStatusCode");
                    deviceId.appendChild(document.createTextNode(data.getString("pointCommonStatusCode")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("wideManagerFinishDate");
                    deviceId.appendChild(document.createTextNode(data.getString("wideManagerFinishDate")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("contractStatusCode");
                    deviceId.appendChild(document.createTextNode(data.getString("contractStatusCode")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("wideManagerRole");
                    deviceId.appendChild(document.createTextNode(data.getString("wideManagerRole")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("stampCommonStatusCode");
                    deviceId.appendChild(document.createTextNode(data.getString("stampCommonStatusCode")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("pointCommonStatusCode");
                    deviceId.appendChild(document.createTextNode(data.getString("pointCommonStatusCode")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("partnerId");
                    deviceId.appendChild(document.createTextNode(data.getString("partnerId")));
                    root.appendChild(deviceId);
                }
                {
                    Element deviceId = document.createElement("franchiseId");
                    deviceId.appendChild(document.createTextNode(data.getString("franchiseId")));
                    root.appendChild(deviceId);
                }

            }
            DOMSource xmlDOM = new DOMSource(document);
            StreamResult xmlFile = new StreamResult(new File("deviceInfo.xml"));
            TransformerFactory.newInstance().newTransformer().transform(xmlDOM, xmlFile);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
