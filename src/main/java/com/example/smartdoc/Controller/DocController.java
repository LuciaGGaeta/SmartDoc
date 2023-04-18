package com.example.smartdoc.Controller;


import com.example.smartdoc.Service.DocService;
import com.example.smartdoc.Utils.Folder;
import com.example.smartdoc.Utils.UploadResponse;
import com.google.cloud.storage.Blob;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.concurrent.ExecutionException;


@RestController
    @RequestMapping("/api")
    @CrossOrigin(origins = "*")
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

        @GetMapping ("/getdocs")
        public List<Folder> getCollection() throws ExecutionException, InterruptedException, IOException {
            return storageService.getDocs();
        }

        @PostMapping("/getdoc")
        public Folder  uploadDoc(@RequestBody String folderName) throws IOException, ExecutionException, InterruptedException {
            Folder folder = storageService.getDoc(folderName);
            return folder;
        }

    @PostMapping("/downloadFile")
    public void getFile(@RequestBody String fileName) {
            storageService.downloadObject(fileName);
    }




}






