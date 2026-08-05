package com.upskillx.dao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.upskillx.model.User;

import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class MentorDAO {
    Firestore db = FirestoreClient.getFirestore();

    public List<User> getAllMentors(String currentUserEmail) {
        List<User> mentors = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();

        try {
            ApiFuture<QuerySnapshot> future = db.collection("users").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for (QueryDocumentSnapshot doc : documents) {
                User user = doc.toObject(User.class);
                if (!user.getEmail().equalsIgnoreCase(currentUserEmail)) {
                    mentors.add(user);
                }
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return mentors;
    }

    public User getMentorProfile(String email) {
        Firestore db = FirestoreClient.getFirestore();

        try {
            DocumentReference docRef = db.collection("users").document(email); // if email is used as doc ID
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                return document.toObject(User.class);
            }

        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void getMentorByEmail(String email, Consumer<User> callback) {
        // Get the Firestore instance from the Admin SDK
        Firestore firestore = FirestoreClient.getFirestore();

        // Reference the user document
        DocumentReference docRef = firestore.collection("users").document(email);

        // Fetch the document asynchronously
        docRef.get().addListener(() -> {
            try {
                DocumentSnapshot document = docRef.get().get(); // blocking get inside thread
                if (document.exists()) {
                    User user = document.toObject(User.class);
                    callback.accept(user);
                } else {
                    System.out.println("No mentor found with email: " + email);
                    callback.accept(null);
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
                callback.accept(null);
            }
        }, Runnable::run); // or use an executor for better control
    }

}
