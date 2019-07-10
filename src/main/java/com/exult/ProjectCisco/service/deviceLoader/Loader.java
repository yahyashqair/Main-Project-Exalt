package com.exult.ProjectCisco.service.deviceLoader;

import com.exult.ProjectCisco.model.Feature;
import com.exult.ProjectCisco.model.FeatureXde;
import com.exult.ProjectCisco.model.Maven;
import com.exult.ProjectCisco.model.Xde;
import com.exult.ProjectCisco.repository.FeatureRepository;
import com.exult.ProjectCisco.repository.FeatureXdeRepository;
import com.exult.ProjectCisco.service.ifmDevice.Xde.XdeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.transaction.Transactional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

@Service
public class Loader {

    @Autowired
    XdeService xdeService;

    @Autowired
    FeatureXdeRepository featureXdeRepository;

    @Autowired
    FeatureRepository featureRepository;

    // Store all XmpXde and XmpFeature here For Sorting xml and feature to avoid errors
    ArrayList<File> XdeFiles = new ArrayList<>();
    ArrayList<File> FeatureFiles = new ArrayList<>();

    /*
     * Helper function for parse the xml pages and convert it into DOM object
     * */
    Document parse(File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.parse(file);
        document.getDocumentElement().normalize();
        return document;
    }

    /*
     *   Take file directory and run the loader
     * */
    public void run() throws IOException, ParserConfigurationException, SAXException {
        File folder = new File("C:\\Users\\user\\Desktop\\device_packages_ifm\\test");
        //System.out.println(Arrays.toString(folder.list()));
        listAllFiles(folder);
    }

    // Recurrence function that open all folders and explore files
    public void listAllFiles(File folder) throws IOException, ParserConfigurationException, SAXException {
        File[] fileNames = folder.listFiles();
        for (File file : fileNames) {
            // if directory call the same method again
            if (file.isDirectory()) {
                listAllFiles(file);
            } else {
                if (file.getName().equals("xmpxde.xml")) {
                    XdeFiles.add(file);
                    return;
                } else if (file.getName().equals("xmpfeature.xml")) {
                    FeatureFiles.add(file);
                }
            }
        }
    }

    /*
    * Function that store Xdes first then store Features
    * */
    public void storeInOrder(){
        
    }

    // Tested
    public void readXde(File file) throws IOException, ParserConfigurationException, SAXException {
        Document document = parse(file);
        Element element = document.getDocumentElement();
        NodeList nodeList = element.getChildNodes();
        Maven maven = new Maven();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeName() == "groupId") {
                maven.setGroupId(node.getTextContent());
            } else if (node.getNodeName() == "artifactId") {
                maven.setArtifactId(node.getTextContent());
            } else if (node.getNodeName() == "version") {
                maven.setVersion(node.getTextContent());
            }
        }
        xdeService.insertXde(maven.getGroupId() + maven.getArtifactId(), maven);
    }

    //Tested
    @Transactional
    public void readFeature(File file) throws IOException, SAXException, ParserConfigurationException {
        Document document = parse(file);
        Element element = document.getDocumentElement();
        Feature feature = new Feature();

        /*
         * Loop to extract maven information
         * */
        NodeList nodeList1 = element.getChildNodes();
        Maven maven = new Maven();
        for (int i = 0; i < nodeList1.getLength(); i++) {
            Node node = nodeList1.item(i);
            if (node.getNodeName() == "groupId") {
                maven.setGroupId(node.getTextContent());
            } else if (node.getNodeName() == "artifactId") {
                maven.setArtifactId(node.getTextContent());
            } else if (node.getNodeName() == "version") {
                maven.setVersion(node.getTextContent());
            }
        }
        //System.out.println(maven);
        /*
         * Loop to extract Dependencies ' Xde '
         * */
        NodeList nList = document.getElementsByTagName("dependency");
        for (int i = 0; i < nList.getLength(); i++) {

            Node node = nList.item(i);
            System.out.println("namee" + node.getTextContent());
            NodeList nodeList = node.getChildNodes();
            String groupId = null, artifactId = null;
            /*
             * Find Xde Information
             * */
            for (int j = 0; j < nodeList.getLength(); j++) {
                Node nNode = nodeList.item(j);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (eElement.getNodeName() == "groupId") {
                        groupId = eElement.getTextContent();
                    } else if (eElement.getNodeName() == "artifactId") {
                        artifactId = eElement.getTextContent();
                    }
                }
            }
            Xde xde = xdeService.findXde(groupId + artifactId);
            System.out.println(xde);
            FeatureXde featureXde = new FeatureXde();
            featureXde.setFeature(feature);
            featureXde.setXde(xde);
            feature.setMaven(maven);
            Set<FeatureXde> featureXdeSet = feature.getXdeSet();
            featureXdeSet.add(featureXde);
            feature.setXdeSet(featureXdeSet);
            feature.setName(maven.getGroupId() + maven.getArtifactId());
            featureXdeRepository.save(featureXde);
        }
        featureRepository.save(feature);
    }

}