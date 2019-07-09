package com.exult.ProjectCisco.service.deviceLoader;
import com.exult.ProjectCisco.model.Xde;
import org.springframework.stereotype.Service;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

@Service
public class Loader {


    Document parse(File file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document document = dBuilder.parse(file);
        document.getDocumentElement().normalize();
        return document;

    }

    public  void run() throws IOException, ParserConfigurationException, SAXException {
        File folder = new File("C:\\Users\\user\\Desktop\\device_packages_ifm\\test");
        //System.out.println(Arrays.toString(folder.list()));
        listAllFiles(folder);
    }
    // Uses listFiles method
    public void listAllFiles(File folder) throws IOException, ParserConfigurationException, SAXException {
        File[] fileNames = folder.listFiles();
        for(File file : fileNames){
            // if directory call the same method again
            if(file.isDirectory()){
                listAllFiles(file);
            }else{
                if (file.getName().equals("xmpxde.xml")) {
                    System.out.println("Yes");
                    readXde(file);
                    return;
                }
            }
        }
    }

    public void readContent(File file) throws IOException{
        System.out.println("read file " + file.getCanonicalPath() );
        try(BufferedReader br  = new BufferedReader(new FileReader(file))){
            String strLine;
            // Read lines from the file, returns null when end of stream
            // is reached
            while((strLine = br.readLine()) != null){
                System.out.println("Line is - " + strLine);
            }
        }
    }

    public void readXde(File file) throws IOException, ParserConfigurationException, SAXException {
        Document document = parse(file);
        Element element = document.getDocumentElement();


        NodeList namedNodeMap2 = document.getChildNodes();
        Node node= namedNodeMap2.item(0);
        NodeList namedNodeMap = node.getChildNodes();
        Xde xde = new Xde();
        

    }
}