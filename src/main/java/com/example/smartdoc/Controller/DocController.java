package com.example.smartdoc.Controller;


import com.example.smartdoc.Service.DocService;
import com.example.smartdoc.Utils.UploadResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


    @RestController
    @RequestMapping("/api")
    @CrossOrigin(origins = "http://localhost:4200")
    public class  DocController {
        private final DocService storageService;

        public DocController(DocService storageService) {
            this.storageService = storageService;
        }

        @PostMapping("/upload")
        public ResponseEntity<UploadResponse> uploadDoc(@RequestParam("file") MultipartFile file) {
            String fileName = file.getOriginalFilename();
            storageService.uploadFile(file, fileName);
            UploadResponse response = new UploadResponse("File uploaded successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }



    }


