package com.exult.ProjectCisco.service.deviceLoader;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Service
public class Mdfdata {

    public HashMap<String, String> getDeviceDetails(String id) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        String filePath = System.getenv().get("mdfdata");
        System.out.println(filePath);
        Document doc = builder.parse(new File(filePath));
        // Create XPathFactory for creating XPath Object
        XPathFactory xPathFactory = XPathFactory.newInstance();
        // Create XPath object from XPathFactory
        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();

        // 1) Get book titles written after 2001
        XPathExpression expr = xpath.compile("//SYSOID[@OID='"+id+"']");
        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        Node node = nodes.item(0);
        node=node.getParentNode();
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("productType",node.getAttributes().getNamedItem("MDFID").getNodeValue());
        node=node.getParentNode();
        hashMap.put("productSeries",node.getAttributes().getNamedItem("MDFID").getNodeValue());
        node=node.getParentNode();
        hashMap.put("productFamily",node.getAttributes().getNamedItem("MDFID").getNodeValue());
        System.out.println(hashMap);
        return hashMap;
    }
}
