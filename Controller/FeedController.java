package com.upskillx.Controller;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.cloud.StorageClient;
import com.upskillx.model.VideoModel;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class FeedController {
    private final Firestore db;

    public FeedController() {
        db = FirestoreClient.getFirestore();
    }

    public boolean uploadVideo(File file, String uploader, String title) {
        try {
            String uniqueName = UUID.randomUUID().toString() + ".mp4";
            StorageClient.getInstance().bucket().create("videos/" + uniqueName, new FileInputStream(file), "video/mp4");
            String downloadUrl = "https://firebasestorage.googleapis.com/v0/b/upskillx-91a69.firebasestorage.app/o/videos%2F" + uniqueName + "?alt=media";

            DocumentReference docRef = db.collection("videos").document();
            docRef.set(new VideoModel(title, uploader, downloadUrl));

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<VideoModel> fetchVideos() {
        List<VideoModel> videos = new ArrayList<>();
        try {
            ApiFuture<QuerySnapshot> future = db.collection("videos").get();
            List<QueryDocumentSnapshot> docs = future.get().getDocuments();

            for (QueryDocumentSnapshot doc : docs) {
                String title = doc.getString("title");
                String uploader = doc.getString("uploader");
                String url = doc.getString("videoUrl");
                videos.add(new VideoModel(title, uploader, url));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return videos;
    }

    public List<Map<String, Object>> getAllVideos() {
    List<Map<String, Object>> videoList = new ArrayList<>();
    try {
        Firestore db = FirestoreClient.getFirestore(); // Ensure FirebaseInit.initialize() is called in main
        ApiFuture<QuerySnapshot> future = db.collection("videos").get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot doc : documents) {
            videoList.add(doc.getData());
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return videoList;
}

}
