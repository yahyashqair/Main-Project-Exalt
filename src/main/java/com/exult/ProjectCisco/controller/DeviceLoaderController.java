package com.exult.ProjectCisco.controller;

import com.exult.ProjectCisco.dto.FileResponse;
import com.exult.ProjectCisco.service.Storage.StorageService;
import com.exult.ProjectCisco.service.deviceLoader.DeviceLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
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
@CrossOrigin(origins = "http://localhost:4200")
public class DeviceLoaderController {


    @Autowired
    private DeviceLoader deviceLoader;
    @Autowired
    private StorageService storageService;

    @PostMapping("/zip")
    @ResponseBody
    public FileResponse uploadFile(@RequestParam("file") MultipartFile file) throws ParserConfigurationException, ZipException, SAXException, IOException {
        String name = storageService.store(file);
        storageService.unzipedFileAndLoaded(name);
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(name)
                .toUriString();
        return new FileResponse(name, uri, file.getContentType(), file.getSize());
    }

}
