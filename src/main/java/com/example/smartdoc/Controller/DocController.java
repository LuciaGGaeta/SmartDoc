package com.example.smartdoc.Controller;


import com.example.smartdoc.Service.DocService;
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
        public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
            String fileName = file.getOriginalFilename();
            storageService.uploadFile(file, fileName);
            return new ResponseEntity<>("File uploaded successfully", HttpStatus.OK);
        }

        @GetMapping("/hello")
        public  ResponseEntity<String> hello() {
            return new ResponseEntity<>("Hello", HttpStatus.OK);
        }


    }


