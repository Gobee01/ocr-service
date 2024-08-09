package com.senz.ocr.service.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)  //TODO: Need To Specify Domain
@RequestMapping("/api")
public class FileUploadApi {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${vm.upload-dir}")
    private String vmUploadDir;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();

        try {
            String uniqueDirName = generateUniqueFileName(file.getOriginalFilename());
            File uniqueDir = new File(vmUploadDir + "/" + uniqueDirName);
            if (!uniqueDir.exists()) {
                uniqueDir.mkdirs();
            }

            String filePath = uniqueDir.getAbsolutePath() + "/" + file.getOriginalFilename();
            Files.copy(file.getInputStream(), Paths.get(filePath));

            response.put("fileName", file.getOriginalFilename());
            response.put("filePath", filePath);

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            e.printStackTrace();
            response.put("error", "File upload failed");
            return ResponseEntity.status(500).body(response);
        }
    }

    private String generateUniqueFileName(String originalFileName) {
        long timestamp = System.currentTimeMillis();
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String fileNameWithoutExtension = originalFileName.replace(fileExtension, "");
        return fileNameWithoutExtension + "_" + timestamp + fileExtension;
    }
}


