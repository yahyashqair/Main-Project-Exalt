package com.exult.ProjectCisco.service.deviceLoader;
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


    void parse(String file) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory1 = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory1.newDocumentBuilder();
        Document document = builder.parse(new File( file ));
        Schema schema = null;
        try {
            String language = XMLConstants.W3C_XML_SCHEMA_NS_URI;
            SchemaFactory factory = SchemaFactory.newInstance(language);
            schema = factory.newSchema(new File(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Validator validator = schema.newValidator();
        validator.validate(new DOMSource(document));
        Element root = document.getDocumentElement();

    }

    public  void run() {
        File folder = new File("C:\\Users\\user\\Desktop\\device_packages_ifm");
        System.out.println(Arrays.toString(folder.list()));
        //listAllFiles(folder);
    }
    // Uses listFiles method
    public void listAllFiles(File folder){
        System.out.println("In listAllfiles(File) method");
        File[] fileNames = folder.listFiles();
        for(File file : fileNames){
            // if directory call the same method again
            if(file.isDirectory()){
                listAllFiles(file);
            }else{
                try {
                    readContent(file);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
    }
    // Uses Files.walk method
    public void listAllFiles(String path){
        System.out.println("In listAllfiles(String path) method");
        try(Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    try {
                        readContent(filePath);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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

    public void readContent(Path filePath) throws IOException{
        System.out.println("read file " + filePath);
        List<String> fileList = Files.readAllLines(filePath);
        System.out.println("" + fileList);
    }




}
