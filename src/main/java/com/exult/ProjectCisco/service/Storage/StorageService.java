package com.exult.ProjectCisco.service.Storage;
import net.lingala.zip4j.exception.ZipException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    String store(MultipartFile file);

    Path load(String filename);
    public String unzipedFileAndLoaded(String filename) throws ZipException, ParserConfigurationException, SAXException, IOException;
}