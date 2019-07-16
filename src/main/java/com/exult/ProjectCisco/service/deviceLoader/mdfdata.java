package com.exult.ProjectCisco.service.deviceLoader;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;

@Service
public class Mdfdata {

    public void getDeviceDetails(String id) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(new File("src\\main\\resources\\sample.xml"));
        // Create XPathFactory for creating XPath Object
        XPathFactory xPathFactory = XPathFactory.newInstance();
        // Create XPath object from XPathFactory
        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();

        // 1) Get book titles written after 2001
        XPathExpression expr = xpath.compile("//SYSOID[@OID='"+id+"']");
        Object result = expr.evaluate(doc, XPathConstants.NODESET);
        NodeList nodes = (NodeList) result;
        System.out.println(nodes.getLength());
        for (int i = 0; i < nodes.getLength(); i++) {
            System.out.println(nodes.item(i).getParentNode().getNodeName());
        }

    }
}
