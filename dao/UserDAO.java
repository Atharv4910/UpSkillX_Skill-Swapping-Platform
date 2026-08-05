package com.upskillx.dao;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.FieldValue;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.FirebaseDatabase;
import com.upskillx.keys.Api_key;
// import com.upskillx.model.QuizResult;
import com.upskillx.model.User;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class UserDAO {

    private static boolean initialized = false;

    public static void initFirebase() {
        if (!initialized) {

            try {
                FileInputStream serviceAccount = new FileInputStream(
                        "skill_swapping/src/main/resources/assets/firebase.json");

                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .setDatabaseUrl("https://upskillx-91a69-default-rtdb.firebaseio.com")
                        .setStorageBucket(new Api_key().getBUCKET())
                        .build();

                FirebaseApp.initializeApp(options);
                initialized = true;
                System.out.println("Firebase initialized.");

            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to initialize Firebase", e);
            }
        }
    }

    public static FirebaseDatabase getDatabase() {
        return FirebaseDatabase.getInstance();
    }

    Firestore db = FirestoreClient.getFirestore();

    public void saveUser(User user) {

        Firestore db = FirestoreClient.getFirestore();
        try {
            ApiFuture<?> future = db.collection("users").document(user.getEmail()).set(user);
            future.get();
            System.out.println("User saved to Firestore: " + user.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getUserByEmail(String email) {
        try {
            Firestore db = FirestoreClient.getFirestore();
            DocumentReference docRef = db.collection("users").document(email);
            ApiFuture<DocumentSnapshot> future = docRef.get();
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                return document.toObject(User.class);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendConnectionRequest(String fromEmail, String toEmail, Runnable onSuccess) {
        Map<String, Object> request = new HashMap<>();
        request.put("status", "pending");
        request.put("from", fromEmail);
        request.put("timestamp", FieldValue.serverTimestamp());

        db.collection("users")
                .document(toEmail)
                .collection("connectionRequests")
                .document(fromEmail)
                .set(request)
                .addListener(() -> {
                    Platform.runLater(onSuccess); // UI updates in JavaFX thread
                }, Executors.newSingleThreadExecutor());
    }

    public void getIncomingRequests(String userEmail, OnRequestsFetchedListener listener) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.submit(() -> {
            List<User> incomingUsers = new ArrayList<>();
            try {
                Firestore db = FirestoreClient.getFirestore();

                ApiFuture<QuerySnapshot> future = db.collection("users")
                        .document(userEmail)
                        .collection("connectionRequests")
                        .whereEqualTo("status", "pending")
                        .get();

                QuerySnapshot snapshot = future.get();

                for (QueryDocumentSnapshot doc : snapshot) {
                    String fromEmail = doc.getId();

                    DocumentSnapshot userDoc = db.collection("users").document(fromEmail).get().get();
                    if (userDoc.exists()) {
                        User sender = userDoc.toObject(User.class);
                        incomingUsers.add(sender);
                    }
                }

                Platform.runLater(() -> listener.onFetched(incomingUsers));

            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            } finally {
                executor.shutdown();
            }
        });
    }

    public interface OnRequestsFetchedListener {
        void onFetched(List<User> incomingUsers);
    }

    public void updateRequestStatus(String currentUserEmail, String fromEmail, String status, Runnable onComplete) {
        db.collection("users")
                .document(currentUserEmail)
                .collection("connectionRequests")
                .document(fromEmail)
                .update("status", status)
                .addListener(() -> Platform.runLater(onComplete), Executors.newSingleThreadExecutor());
    }

    public void loadMentorReviews(String mentorId, VBox reviewsContainer) {
        Firestore db = FirestoreClient.getFirestore();

        try {
            ApiFuture<QuerySnapshot> future = db.collection("users")
                    .document(mentorId)
                    .collection("reviews")
                    .get();

            List<QueryDocumentSnapshot> documents = future.get().getDocuments(); // blocks the UI

            reviewsContainer.getChildren().clear();
            if (documents.isEmpty()) {
                reviewsContainer.getChildren().add(new Label("No reviews yet."));
            } else {
                for (QueryDocumentSnapshot doc : documents) {
                    String reviewerId = doc.getString("givenBy");
                    String review = doc.getString("review");
                    int stars = doc.getLong("stars").intValue();
                    String date = doc.getString("date");

                    VBox card = createReviewCard("👤 " + reviewerId, review, stars, date);
                    reviewsContainer.getChildren().add(card);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private VBox createReviewCard(String reviewerNameText, String reviewContent, int starCount, String date) {
        
        HBox starsBox = new HBox(3);
        for (int i = 0; i < 5; i++) {
            Label star = new Label(i < starCount ? "★" : "☆");
            star.setStyle("-fx-font-size: 16px; -fx-text-fill: #FFA500;");
            starsBox.getChildren().add(star);
        }

        Label nameLabel = new Label(reviewerNameText);
        nameLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label dateLabel = new Label("🕒 " + date);
        dateLabel.setStyle("-fx-font-size: 11px; -fx-text-fill: #888888;");

        Label reviewLabel = new Label("\"" + reviewContent + "\"");
        reviewLabel.setWrapText(true);
        reviewLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #333333;");

        VBox card = new VBox(6, nameLabel, starsBox, reviewLabel, dateLabel);
        card.setMinWidth(300);
        card.setMinHeight(200);
        card.setPadding(new Insets(12));
        card.setSpacing(5);
        card.setStyle(
                "-fx-background-color: #ffffff;" +
                        "-fx-border-color: #cccccc;" +
                        "-fx-border-radius: 10;" +
                        "-fx-background-radius: 10;" +
                        "-fx-effect: dropshadow(two-pass-box, rgba(0,0,0,0.1), 4, 0, 0, 2);");
        return card;
    }

}
