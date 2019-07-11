package com.exult.ProjectCisco.service.deviceLoader;

import com.exult.ProjectCisco.model.Feature;
import com.exult.ProjectCisco.model.FeatureXde;
import com.exult.ProjectCisco.model.Maven;
import com.exult.ProjectCisco.model.Xde;
import com.exult.ProjectCisco.repository.FeatureRepository;
import com.exult.ProjectCisco.repository.FeatureXdeRepository;
import com.exult.ProjectCisco.repository.MavenRepository;
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
import java.io.*;
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

    @Autowired
    MavenRepository mavenRepository;


    // Store all XmpXde and XmpFeature here For Sorting xml and feature to avoid errors
    ArrayList<File> xdeFiles = new ArrayList<>();
    ArrayList<File> featureFiles = new ArrayList<>();

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
        File folder = new File("C:\\Users\\user\\Desktop\\device_packages_ifm");
        listAllFiles(folder);
        storeInOrder();
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
                    readXde(file);
                } else if (file.getName().equals("xmpfeature.xml")) {
                    featureFiles.add(file);
                }
            }
        }
    }

    /*
     * Function that store Xdes first then store Features
     * */
    public void storeInOrder() throws ParserConfigurationException, SAXException, IOException {
//        for (File xdeFile: xdeFiles) {
//            readXde(xdeFile);
//        }
        for (int i = 0; i < featureFiles.size(); i++) {
            readFeature(featureFiles.get(i));
        }
    }

    // Tested
    public void readXde(File file) throws IOException, ParserConfigurationException, SAXException {
        Document document = parse(file);
        System.err.println(file.getPath());
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
        feature.setMaven(maven);
        mavenRepository.save(maven);
        featureRepository.save(feature);
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
                    if (eElement.getNodeName().equals("groupId")) {
                        groupId = eElement.getTextContent();
                    } else if (eElement.getNodeName().equals("artifactId")) {
                        artifactId = eElement.getTextContent();
                    }
                }
            }
            Xde xde = xdeService.findXde(groupId + artifactId);
            System.out.println(xde);
            FeatureXde featureXde = new FeatureXde();
            featureXde.setFeature(feature);
            featureXde.setXde(xde);
            Set<FeatureXde> featureXdeSet = feature.getXdeSet();
            featureXde.setTypeOfRelation(findRelationType(file,xde));
            featureXdeSet.add(featureXde);
            feature.setXdeSet(featureXdeSet);
            feature.setName(maven.getGroupId() + maven.getArtifactId());
            /*
             * Find Relation type
             * */
            featureXdeRepository.save(featureXde);
        }
        featureRepository.save(feature);
    }

    public String findRelationType(File file,Xde xde) {
        try {
            String newfile = file.getParent() + "\\src\\main\\resources\\META-INF\\MANIFEST.MF";
            System.out.println(file.getPath());
            BufferedReader br = new BufferedReader(new FileReader(newfile));
            String st;
            while ((st = br.readLine()) != null){
                if(st.contains(xde.getMaven().getGroupId()+":"+xde.getMaven().getArtifactId())){
                    st = br.readLine();
                    return st.substring(18);
                }
            }
        } catch (Exception e) {
            System.out.println("File Not Found "+file.getPath());
        }
        return null;
    }

}