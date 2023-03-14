package container.code.function.account.controller;

import container.code.function.account.service.filestorage.FileStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "https://cleaning-house-service.vercel.app", allowCredentials = "true")
@RequestMapping("/file-storage")
public class FileUploadController {
    @Autowired
    private FileStorage fileStorage;

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadFile(@RequestPart(required = true) MultipartFile file) throws IOException {
        String url = fileStorage.uploadFile(file);
        if (!url.isEmpty()) {
            return new ResponseEntity<>(url, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFile(@RequestParam String url) {

        try {
            fileStorage.deleteFile(url);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e) {
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

}
