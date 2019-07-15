package com.exult.ProjectCisco.controller;

import com.exult.ProjectCisco.service.Storage.StorageService;
import com.exult.ProjectCisco.service.deviceLoader.DeviceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.core.ZipFile;

@Controller
@RestController
@RequestMapping("loader")
public class DeviceLoaderController {


    @Autowired
    private DeviceLoader deviceLoader;
    @Autowired
    private StorageService storageService;

    @GetMapping(value = "/{filename:.+}")
    String fun(@PathVariable() String filename) throws ParserConfigurationException, SAXException, IOException, ZipException {
        Path path = storageService.load(filename);
        File file = path.toFile();
        ZipFile zipFile = new ZipFile(file);
        zipFile.extractAll(file.getParent()+"/test");
        File file1= new File(file.getParent()+"/test");
        System.out.println(file1);
        deviceLoader.run(file1);
        return file1.getPath();
    }

}
