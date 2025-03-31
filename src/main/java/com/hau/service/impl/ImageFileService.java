package com.hau.service.impl;

import com.hau.service.FileService;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImageFileService implements FileService {
    private static final String ROOT_DIR = "/template/web/img/";

    private final ServletContext servletContext;

    public ImageFileService(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Override
    public String saveFile(MultipartFile file, String uploadDir) throws IOException {
        String imageName = StringUtils.cleanPath(file.getOriginalFilename());
        String realPath = servletContext.getRealPath(ROOT_DIR);
        Path imageUploadPath = Paths.get(realPath + uploadDir + "/");

        if (!Files.exists(imageUploadPath)) {
            Files.createDirectories(imageUploadPath);
        }

        InputStream inputStream = file.getInputStream();
        Path imagePath = imageUploadPath.resolve(imageName);
        System.out.println(imagePath.toFile().getAbsolutePath());

        Files.copy(inputStream, imagePath, StandardCopyOption.REPLACE_EXISTING);
        return imageName;
    }

    @Override
    public void deleteFile(String fileName, String uploadDir) throws IOException {
        String realPath = servletContext.getRealPath(ROOT_DIR);
        Path filePath = Paths.get(realPath + uploadDir + "/" + fileName);
        Files.deleteIfExists(filePath);
    }

    @Override
    public MultipartFile handleDefaultFile(String fileName, String uploadDir) {
        String realPath = servletContext.getRealPath(ROOT_DIR + uploadDir + "/" + fileName);
        File file = new File(realPath);
        if (!file.exists()) {
            throw new RuntimeException("File không tồn tại: " + realPath);
        }
        DiskFileItem fileItem = new DiskFileItem("file", "image/png", false, file.getName(), (int) file.length(), file.getParentFile());
        try (FileInputStream input = new FileInputStream(file)) {
            fileItem.getOutputStream().write(input.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi đọc file", e);
        }
        return new CommonsMultipartFile(fileItem);
    }

}
