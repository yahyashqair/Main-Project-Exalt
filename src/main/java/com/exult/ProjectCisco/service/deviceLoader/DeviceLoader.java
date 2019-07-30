package com.exult.ProjectCisco.service.deviceLoader;

import com.exult.ProjectCisco.model.*;
import com.exult.ProjectCisco.repository.*;
import com.exult.ProjectCisco.service.ifmDevice.Feature.FeatureService;
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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Service
public class DeviceLoader {

    @Autowired
    CriteriaRepository criteriaRepository;
    @Autowired
    private XdeService xdeService;
    @Autowired
    private FeatureService featureService;
    @Autowired
    private FeatureXdeRepository featureXdeRepository;
    @Autowired
    private FeatureRepository featureRepository;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private MavenRepository mavenRepository;
//    @Autowired
//    private ConfigurationRepository configrationRepository;
    // Store all XmpXde and XmpFeature here For Sorting xml and feature to avoid errors
    private ArrayList<File> xdeFiles = new ArrayList<>();
    private ArrayList<File> featureFiles = new ArrayList<>();
    private ArrayList<File> profileFiles = new ArrayList<>();
    // Array for Solve the dependency between profiles
    private HashMap<String, String> profileMap = new HashMap<String, String>();

    public static <T> ArrayList<T> removeDuplicates(ArrayList<T> list) {

        // Create a new LinkedHashSet
        Set<T> set = new LinkedHashSet<>();

        // Add the elements to set
        set.addAll(list);

        // Clear the list
        list.clear();

        // add the elements of set
        // with no duplicates to the list
        list.addAll(set);

        // return the list
        return list;
    }

    /*
     * Helper function for parse the xml pages and convert it into DOM object
     * */
    private Document parse(File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.parse(file);
        document.getDocumentElement().normalize();
        return document;
    }

    /*
     *   Take file directory and run the loader
     * */
    public void run(File folder) throws IOException, ParserConfigurationException, SAXException {
        // File folder = new File("C:\\Users\\user\\Desktop\\test2");
        listAllFiles(folder);
        storeInOrder();
    }

    // Recurrence function that open all folders and explore files
    private void listAllFiles(File folder) throws IOException, ParserConfigurationException, SAXException {
        File[] fileNames = folder.listFiles();
        if (fileNames != null) {
            for (File file : fileNames) {
                // if directory call the same method again
                if (file.isDirectory()) {
                    listAllFiles(file);
                } else {
                    if (file.getName().equals("xmpxde.xml")) {
                        xdeFiles.add(file);
                    } else if (file.getName().equals("xmpfeature.xml")) {
                        featureFiles.add(file);
                    } else if (file.getName().equals("xmpdevice.xml")) {
                        profileFiles.add(file);
                    }
                }
            }
        }
    }
    /*
     * Function that store Xdes first then store Features then store Profiles
     * */

    private void storeInOrder() throws ParserConfigurationException, SAXException, IOException {
        // Remove Dublicate
        xdeFiles = removeDuplicates(xdeFiles);
        featureFiles = removeDuplicates(featureFiles);
        profileFiles = removeDuplicates(profileFiles);

        for (File xdeFile : xdeFiles) {
            readXde(xdeFile);
        }
        for (int i = 0; i < featureFiles.size(); i++) {
            readFeature(featureFiles.get(i));
        }
        for (int i = 0; i < profileFiles.size(); i++) {
            readProfile(profileFiles.get(i));
        }
        /*
         * Solve profile Dependency !!
         * */
        for (Map.Entry<String, String> entry : profileMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            solveProfileDependency(key, value);
        }
    }

    /*
     * Function Take file "xmpfeature.xml" and added it in data base
     * */

    private void readXde(File file) throws IOException, ParserConfigurationException, SAXException {
        Document document = parse(file);
        System.err.println(file.getPath());
        Element element = document.getDocumentElement();
        NodeList nodeList = element.getChildNodes();
        Maven maven = new Maven();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeName().equals("groupId")) {
                maven.setGroupId(node.getTextContent());
            } else if (node.getNodeName().equals("artifactId")) {
                maven.setArtifactId(node.getTextContent());
            } else if (node.getNodeName().equals("version")) {
                maven.setVersion(node.getTextContent());
            }
        }
        if (mavenRepository.findByArtifactIdAndAndGroupId(maven.getArtifactId(), maven.getGroupId()) == null) {
            mavenRepository.save(maven);
            xdeService.insertXde(maven.getGroupId() + "." + maven.getArtifactId(), maven);
        }
    }

    /*
     * Function Take file "xmpfeature.xml" and added it in data base
     * Helper Function findRelationType
     * */
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
            if (node.getNodeName().equals("groupId")) {
                maven.setGroupId(node.getTextContent());
            } else if (node.getNodeName().equals("artifactId")) {
                maven.setArtifactId(node.getTextContent());
            } else if (node.getNodeName().equals("version")) {
                maven.setVersion(node.getTextContent());
            }
        }

        if (mavenRepository.findByArtifactIdAndAndGroupId(maven.getArtifactId(), maven.getGroupId()) == null) {
            mavenRepository.save(maven);
        } else {
            return;
        }
        feature.setMaven(maven);
        featureRepository.save(feature);
        //System.out.println(maven);
        /*
         * Loop to extract Dependencies ' Xde '
         * */
        NodeList nList = document.getElementsByTagName("dependency");
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            // System.out.println("namee" + node.getTextContent());
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
            Xde xde = xdeService.findXde(groupId + "." + artifactId);
            //System.out.println(xde);
            FeatureXde featureXde = new FeatureXde();
            featureXde.setFeature(feature);
            featureXde.setXde(xde);
            Set<FeatureXde> featureXdeSet = feature.getXdeSet();
            featureXde.setTypeOfRelation(findRelationType(file, xde));
            featureXdeSet.add(featureXde);
            feature.setXdeSet(featureXdeSet);
            feature.setName(maven.getGroupId() + "." + maven.getArtifactId());
            /*
             * Find Relation type
             * */
            featureXdeRepository.save(featureXde);
        }
        featureRepository.save(feature);
    }

    /*
     * Helper Function for readFeature
     * Take xmpfeature.xml and xde and return type of its relation
     * */
    private String findRelationType(File file, Xde xde) {
        try {
            String newfile = file.getParent() + "\\src\\main\\resources\\META-INF\\MANIFEST.MF";
            //  System.out.println(file.getPath());
            BufferedReader br = new BufferedReader(new FileReader(newfile));
            String st;
            while ((st = br.readLine()) != null) {
                if (st.contains(xde.getMaven().getGroupId() + ":" + xde.getMaven().getArtifactId())) {
                    st = br.readLine();
                    return st.substring(18);
                }
            }
        } catch (Exception e) {
            System.out.println("File Not Found " + file.getPath());
        }
        return null;
    }
    // End of Feature Functionality.

    /*
     * Take xmpdevice.xml , and add it in database ,
     * Helper Function solveProfileDependency
     * */
    @Transactional
    public void readProfile(File file) throws IOException, SAXException, ParserConfigurationException {
        Document document = parse(file);
        Element element = document.getDocumentElement();
        Profile profile = new Profile();
        boolean hasParent = false;
        Set<Feature> featureSet = new HashSet<>();
        Set<Feature> exfeatureSet = new HashSet<>();
        /*
         * Loop to extract maven information
         * */
        NodeList nodeList1 = element.getChildNodes();
        Maven maven = new Maven();
        for (int i = 0; i < nodeList1.getLength(); i++) {
            Node node = nodeList1.item(i);
            if (node.getNodeName().equals("groupId")) {
                maven.setGroupId(node.getTextContent());
            } else if (node.getNodeName().equals("artifactId")) {
                maven.setArtifactId(node.getTextContent());
            } else if (node.getNodeName().equals("version")) {
                maven.setVersion(node.getTextContent());
            }
        }
        profile.setName(maven.getGroupId() + "." + maven.getArtifactId());
        profile.setMaven(maven);
        if (mavenRepository.findByArtifactIdAndAndGroupId(maven.getArtifactId(), maven.getGroupId()) == null) {
            mavenRepository.save(maven);
        } else {
            return;
        }
        profileRepository.save(profile);
        /*
         * Find Features , parent  ;
         * */
        NodeList nList = document.getElementsByTagName("dependency");
        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);
            NodeList nodeList = node.getChildNodes();
            String groupId = null, artifactId = null, type = null;
            /*
             * Find dependency Information
             * */
            for (int j = 0; j < nodeList.getLength(); j++) {
                Node nNode = nodeList.item(j);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    if (eElement.getNodeName().equals("groupId")) {
                        groupId = eElement.getTextContent();
                    } else if (eElement.getNodeName().equals("artifactId")) {
                        artifactId = eElement.getTextContent();
                    } else if (eElement.getNodeName().equals("type")) {
                        type = eElement.getTextContent();
                        hasParent = true;
                    }
                }
            }
            if (type != null && type.contains("dar")) {
                profileMap.put(maven.getGroupId() + "." + maven.getArtifactId(), groupId + "." + artifactId);
            } else {
                Feature feature = featureService.findFeature(groupId + "." + artifactId);
                if (feature != null) {
                    featureSet.add(feature);
                } else {
                    System.err.println("Feature Not Found");
                }
            }
        }
        profile.setFeatures(featureSet);
        /*
         * Find Excluded feature
         * */
        NodeList execludingSet = document.getElementsByTagName("exclusion");
        for (int i = 0; i < execludingSet.getLength(); i++) {
            Node node = execludingSet.item(i);
            NodeList nodeList = node.getChildNodes();
            String groupId = null, artifactId = null, type = null;
            /*
             * Find feature Information
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
            Feature feature = featureService.findFeature(groupId + "." + artifactId);
            if (feature != null) {
                exfeatureSet.add(feature);
            }
        }
        profile.setExcludeFeature(exfeatureSet);
        /*
         * Find Configration
         * */
        profile.setCriteriaSet(findConfigurationsSet(file));
        /*
         * Save profile ,
         * */
        profileRepository.save(profile);
    }
    /*
     * After adding profile , there is some feature that Child profile inherit  from his parent
     * this function solve this dependency
     * */

    private void solveProfileDependency(String child, String parent) {
        if (parent == null) return;
        try {
            // Solve Parent Dependency
            solveProfileDependency(parent, profileMap.get(parent));
            // Solved Parent

            Profile p1 = profileRepository.findByName(child);
            Profile p2 = profileRepository.findByName(parent);

            // Add parent Feature to Child and exclude "ExFeature"
            Set<Feature> featureSet = p1.getFeatures();
            featureSet.addAll(p2.getFeatures());
            p1.setFeatures(featureSet);
            featureSet.removeAll(p1.getExcludeFeature());

            //Set Parent to Child
            p1.setParent(p2);

        } catch (Exception e) {
            System.err.println("Error" + e.getMessage());
        }
        // Set Chlid independent of his parent ,'Null'
        profileMap.put(child, null);
    }

    @Transactional
    public Set<Criteria> findConfigurationsSet(File file) throws IOException, SAXException, ParserConfigurationException {
        try {
            String newfile = file.getParent() + "\\src\\main\\resources\\.orderedFeatures";
            Document document = parse(new File(newfile));
            NodeList nodeList = document.getElementsByTagName("configuration");
            Node node1 = nodeList.item(0);
            NodeList nList = node1.getChildNodes();
            Set<Criteria> criteriaSet = new HashSet<>();
            for (int i = 0; i < nList.getLength(); i++) {
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    String name, value;
                    name = eElement.getNodeName();
                    if (name.contains("param")) {
                        Criteria criteria = new Criteria();
                        Set<Configuration> configurationSet = new HashSet<>();
                        criteria.setName(findString(name, "name"));
                        // Has more than 1 configuration
                        if (!eElement.getAttribute("param:operator").isEmpty()) {
                            criteria.setOperator(eElement.getAttribute("param:operator"));
                            NodeList nList2 = nList.item(i).getChildNodes();
                            for (int j = 0; j < nList2.getLength(); j++) {
                                Node node2 = nList.item(j);
                                Element eElement2 = (Element) node2;
                                if (node2.getNodeType() == Node.ELEMENT_NODE) {
                                    Configuration configuration = new Configuration();
                                    if(!eElement2.getAttribute("param:operation").equals("")) {
                                        configuration.setOperation(eElement2.getAttribute("param:operation"));
                                    }else{
                                        configuration.setOperation("equal");
                                    }configuration.setValue(node2.getTextContent());
                                    configurationSet.add(configuration);
                                }
                            }
                        } else {
                            criteria.setOperator("or");
                            Configuration configuration = new Configuration();
                            configuration.setValue(eElement.getTextContent());
                            if (!eElement.getAttribute("param:operation").equals("")) {
                                configuration.setOperation(eElement.getAttribute("param:operation"));
                            }else{
                                configuration.setOperation("equal");
                            }
                            configurationSet.add(configuration);
                        }
                        criteria.setConfigurationSet(configurationSet);
                        criteriaSet.add(criteria);
                    }
                }
            }
            criteriaRepository.saveAll(criteriaSet);
            System.out.println(criteriaSet);
            return criteriaSet;
        } catch (Exception e) {
            System.err.println("Error" + e.getMessage());
        }
        return null;
    }

    private String findString(String tagName, String delimiter) {
        if (delimiter.equals("name")) {
            if (tagName.contains("param")) {
                int x = tagName.indexOf(":");
                return tagName.substring(x+1);
            }
        }
        //return tagName.split(delimiter)[1];
        return null;
    }

}

