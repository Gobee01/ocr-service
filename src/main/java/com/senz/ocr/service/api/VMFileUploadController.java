package com.senz.ocr.service.api;

import com.senz.ocr.service.util.FileTransferUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)  //TODO: Need To Specify Domain
@RequestMapping("/api")
public class VMFileUploadController {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${vm.username}")
    private String vmUsername;

    @Value("${vm.host}")
    private String vmHost;

    @Value("${vm.ssh-key-file}")
    private String vmSshKeyFile;

    @Value("${vm.upload-dir}")
    private String vmUploadDir;

    private static final Logger LOGGER = LoggerFactory.getLogger(VMFileUploadController.class);

    @PostMapping("/uploadVM")
    public ResponseEntity<Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, String> response = new HashMap<>();

        try {
            // Generate a unique file name
            String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());
            File destinationFile = new File(uploadDir + "/" + uniqueFileName);
            LOGGER.debug("upload file request" + ":" + destinationFile.getAbsolutePath());

            // Ensure the upload directory exists
            File uploadDirectory = new File(uploadDir);
            if (!uploadDirectory.exists()) {
                uploadDirectory.mkdirs();
            }

            // Copy the file to the destination
            Files.copy(file.getInputStream(), destinationFile.toPath());

            // Transfer the file to the Ubuntu VM
            String remoteFilePath = vmUploadDir + "/" + uniqueFileName;
            FileTransferUtil.transferFileToVM(destinationFile.getAbsolutePath(), remoteFilePath, vmHost, vmUsername, vmSshKeyFile);

            response.put("fileName", file.getOriginalFilename());
            response.put("filePath", remoteFilePath);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
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

