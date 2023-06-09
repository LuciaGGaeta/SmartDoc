package com.example.smartdoc.Service;


import com.example.smartdoc.Utils.Folder;
import com.google.api.client.http.GenericUrl;
import com.google.api.core.ApiFuture;
import com.google.api.services.storage.model.StorageObject;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.cloud.storage.*;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import com.google.cloud.storage.Blob;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import io.github.cdimascio.dotenv.Dotenv;



@Service
public class DocService {

    Dotenv dotenv = Dotenv.configure().load();
    private final Storage storage = StorageOptions.getDefaultInstance().getService();

    private final String bucketName = dotenv.get("MY_BUCKET_NAME");
    private final String projectId = dotenv.get("MY_PROJECTID");

    FirestoreOptions firestoreOptions =
            FirestoreOptions.getDefaultInstance().toBuilder()
                    .setProjectId(projectId)
                    .setCredentials(GoogleCredentials.getApplicationDefault())
                    .build();
    Firestore db = firestoreOptions.getService();


    public DocService() throws IOException {
    }

    public boolean uploadFile(MultipartFile file, String fileName) {
        UUID fileId = UUID.randomUUID();
        String name = fileName.replace(".pdf", "");
        fileName =  name + "_" + fileId.toString() + ".pdf";
        try {
            BlobId blobId = BlobId.of(bucketName, fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
            storage.create(blobInfo, file.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    public List<Folder> getDocs() throws ExecutionException, InterruptedException, IOException {

        ApiFuture<QuerySnapshot> future = db.collection("cartellefile_smartdoc").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        List<Folder> folders = new ArrayList<>();
        for (QueryDocumentSnapshot document : documents) {
            folders.add(document.toObject(Folder.class));
        }
        return  folders;
    }

    public Folder getDoc(String folderName) throws ExecutionException, InterruptedException, IOException {
        DocumentReference docRef = db.collection("cartellefile_smartdoc").document(folderName);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            // convert document to POJO
           return document.toObject(Folder.class);
        } else {
            System.out.println("No such document!");
            return  null;
        }
    }

    public Blob downloadObject(String objectName) {
        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        return storage.get(BlobId.of(bucketName, objectName));
    }
}

