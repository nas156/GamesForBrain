package ua.project.games.service.fileStorage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class JsStorageService extends FileSystemStorageService {

    @Autowired
    public JsStorageService(StorageProperties properties) {
        super(properties);
    }

    public void verifyJs(MultipartFile file) {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if(!filename.matches("^.*\\.js$")){
            throw new FileFormatException("invalid js file");
        }
    }

    public void setFolder(String folderName){
        super.rootLocation = Paths.get("src/main/resources/static/js/" + folderName);
    }
}
