package com.exult.ProjectCisco.service.Storage;

import com.exult.ProjectCisco.StorageProperties;
import com.exult.ProjectCisco.service.deviceLoader.DeviceLoader;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.annotation.PostConstruct;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    @Autowired
    private StorageService storageService;

    @Autowired
    private DeviceLoader deviceLoader;

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            System.out.println(("Could not initialize storage location"+e.getMessage()));
        }
    }

    @Override
    public String store(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                System.out.println("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                System.out.println(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, this.rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            System.out.println(("Failed to store file " + filename+e));
        }

        return filename;
    }


    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    public String unzipedFileAndLoaded(String filename) throws ZipException, ParserConfigurationException, SAXException, IOException {
                    Path path = storageService.load(filename);
            File file = path.toFile();
            ZipFile zipFile = new ZipFile(file);
            if(filename.contains(".")){
                int x = filename.indexOf(".");
                filename=filename.substring(0,x);
            }
            zipFile.extractAll(file.getParent()+"/"+filename);
            File file1= new File(file.getParent()+"/"+filename);
            //System.out.println(file1);
            deviceLoader.run(file1);
            return file1.getPath();
        }
    }