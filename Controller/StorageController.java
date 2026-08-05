package com.upskillx.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.awt.Desktop;
import java.net.URI;


import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import com.upskillx.keys.Api_key;
import com.upskillx.model.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.application.Platform;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class StorageController {

    private static final String storage_path = "images/demo.png";
    private static final String local_temp = "D:\\Images\\glass.png";
    ImageView imageView = new ImageView();

    public String uploadImage(Stage myStage) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Image");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));

        File file = chooser.showOpenDialog(myStage);
        if (file == null)
            return null;

        try {

            String filename = "images/" + System.currentTimeMillis() + "_" + file.getName();

            String url = "https://firebasestorage.googleapis.com/v0/b/" +
                    new Api_key().getBUCKET() +
                    "/o?uploadType=media&name=" +
                    URLEncoder.encode(filename, "UTF-8");

            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "image/png");

            try (OutputStream os = httpURLConnection.getOutputStream();
                    FileInputStream fis = new FileInputStream(file)) {
                byte[] buffer = new byte[4096];
                int byteRead;
                while ((byteRead = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, byteRead);
                }
            }

            int responseCode = httpURLConnection.getResponseCode();
            System.out.println("Upload response : " + responseCode);

            if (responseCode == 200) {
                // Generate public URL manually
                String publicUrl = "https://firebasestorage.googleapis.com/v0/b/" +
                        new Api_key().getBUCKET() +
                        "/o/" +
                        URLEncoder.encode(filename, "UTF-8") +
                        "?alt=media";

                return publicUrl;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public String uploadVideo(Stage myStage) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Select Video");
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Video Files", "*.mp4", "*.mov", "*.avi", "*.mkv"));

        File file = chooser.showOpenDialog(myStage);
        if (file == null)
            return null;

        try {

            // Upload to Firebase Storage
            Bucket bucket = StorageClient.getInstance().bucket();
            String filename = "videos/" + System.currentTimeMillis() + "_" + file.getName();

            Blob blob = bucket.create(filename, new FileInputStream(file), "video/mp4");

            // Make file public (optional)
            blob.createAcl(com.google.cloud.storage.Acl.of(com.google.cloud.storage.Acl.User.ofAllUsers(),
                    com.google.cloud.storage.Acl.Role.READER));

            // Return public URL
            return String.format("https://storage.googleapis.com/%s/%s", bucket.getName(), filename);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void getImage() {

        try {
            String encodedPath = storage_path.replace("/", "%2F");
            String url = "https://firebasestorage.googleapis.com/v0/b/" + new Api_key().getBUCKET() + "/o/"
                    + encodedPath + "?alt=media";

            try (InputStream in = new URL(url).openStream();
                    FileOutputStream out = new FileOutputStream(local_temp)) {
                byte[] buffer = new byte[4096];
                int n;
                while ((n = in.read(buffer)) != -1)
                    out.write(buffer, 0, n);
            }

            imageView.setImage(new Image(new FileInputStream(local_temp)));
            System.out.println("Image downloaded and Previewed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteImage() {

        try {
            String encodedPath = storage_path.replace("/", "%2F");
            String url = "https://firebasestorage.googleapis.com/v0/b/" + new Api_key().getBUCKET() + "/o/"
                    + encodedPath;

            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setRequestMethod("DELETE");
            System.out.println("Delete Response : " + httpURLConnection.getResponseCode());
            httpURLConnection.disconnect();
            imageView.setImage(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String uploadCertificate(Stage primaryStage, String name) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showOpenDialog(primaryStage);

        if (file == null) {
            System.out.println("No file selected.");
            return null;
        }

        String fileName = "certificates/" + name + "_" + file.getName();
        try {
            StorageClient.getInstance().bucket()
                    .create(fileName, new FileInputStream(file), "application/pdf");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Certificate uploaded: " + fileName);

        String downloadUrl = "https://firebasestorage.googleapis.com/v0/b/"
                + new Api_key().getBUCKET() + "/o/"
                + URLEncoder.encode(fileName, StandardCharsets.UTF_8)
                + "?alt=media";

        System.out.println("Download URL: " + downloadUrl);
        return downloadUrl;

    }


public String uploadPresentation(Stage primaryStage, String name) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        File file = fileChooser.showOpenDialog(primaryStage);

        if (file == null) {
            System.out.println("No file selected.");
            return null;
        }

        String fileName = "presentations/" + name + "_" + file.getName();
        try {
            StorageClient.getInstance().bucket()
                    .create(fileName, new FileInputStream(file), "application/pdf");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Certificate uploaded: " + fileName);

        String downloadUrl = "https://firebasestorage.googleapis.com/v0/b/"
                + new Api_key().getBUCKET() + "/o/"
                + URLEncoder.encode(fileName, StandardCharsets.UTF_8)
                + "?alt=media";

        System.out.println("Download URL: " + downloadUrl);
        return downloadUrl;

    }
}
