package com.exult.ProjectCisco.service.deviceLoader;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;

@Service
public class mdfdata {
    void getDeviceDetails(File file) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(file);

        // Create XPathFactory for creating XPath Object
        XPathFactory xPathFactory = XPathFactory.newInstance();
        // Create XPath object from XPathFactory
        XPath xpath = xPathFactory.newXPath();
        // Compile the XPath expression for getting all brands
        XPathExpression xPathExpr = xpath.compile("/DEVICEGROUP/DEVICE/SYSOID/text()");

        Object result = xPathExpr.evaluate(doc, XPathConstants.NODESET);

        // get all models by xpath expression in java
        xPathExpr = xpath.compile("/smartphones/smartphone/model/text()");
        result = xPathExpr.evaluate(doc, XPathConstants.NODESET);
        System.out.println("Java Xpath text example: All popular smartphone model ");


    }
}
