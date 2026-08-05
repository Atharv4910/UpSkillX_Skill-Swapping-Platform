package com.upskillx.View;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.upskillx.model.User;

import javafx.animation.FadeTransition;
import javafx.animation.FillTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FeedBack extends Application {

    User user;
    User mentor;

    public FeedBack(User user, User mentor) {
        this.user = user;
        this.mentor = mentor;
    }

    Firestore db;

    @Override
    public void start(Stage myStage) {
        Stage dialog = new Stage();
        dialog.setTitle("Session Summary");

        // Top Congratulatory Text
        Label congrats = new Label("🚀 Skill leveled up! Great things happen when you keep learning.");
        congrats.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        congrats.setTextFill(Color.web("#2e7d32"));

        // === Course Summary Card ===
        Text summaryTitle = new Text("📘 Course Summary");
        summaryTitle.setFill(Color.DARKSLATEBLUE);
        summaryTitle.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        Text skillEarn = new Text("Skill Earned : Java");
        Text mentorNme = new Text("Mentor : Akshay");
        Text cpEarn = new Text("CP Earned : " + user.getCp());
        int cpFromUser = mentor.getCp(); // user pays this to mentor
        int cpFromMentor = user.getCp(); // mentor pays this to user

        // Update User (learner)
        user.setTotalCp(user.getTotalCp() - cpFromUser); // deduct what user paid
        user.setTotalCp(user.getTotalCp() + cpFromMentor); // add what user earned

        // Update Mentor (also a user)
        mentor.setTotalCp(mentor.getTotalCp() - cpFromMentor); // deduct what mentor paid
        mentor.setTotalCp(mentor.getTotalCp() + cpFromUser);

        db = FirestoreClient.getFirestore();

        db.collection("users").document(user.getEmail())
                .update("totalCp", user.getTotalCp());

        db.collection("users").document(mentor.getEmail())
                .update("totalCp", mentor.getTotalCp());

        VBox sessionSumBox = new VBox(5, summaryTitle, skillEarn, mentorNme, cpEarn);
        sessionSumBox.setPadding(new Insets(15));
        styleCard(sessionSumBox);

        // === Feedback Card ===
        Text feedbackTitle = new Text("📝 Give Feedback");
        feedbackTitle.setFill(Color.DARKSLATEBLUE);
        feedbackTitle.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        // Star Rating System
        Label ratingPrompt = new Label("⭐ Rate your mentor:");
        ratingPrompt.setFont(Font.font("Arial", 13));
        HBox starBox = new HBox(8);
        starBox.setAlignment(Pos.CENTER_LEFT);

        List<Label> stars = new ArrayList<>();
        final int[] selectedRating = { 5 }; // Default 5 stars

        for (int i = 1; i <= 5; i++) {
            Label star = new Label("★");
            star.setStyle("-fx-font-size: 28px; -fx-text-fill: #ffb400; -fx-cursor: hand;");
            int ratingValue = i;

            star.setOnMouseEntered(e -> {
                for (int j = 0; j < 5; j++) {
                    stars.get(j).setText(j < ratingValue ? "★" : "☆");
                }
            });

            star.setOnMouseExited(e -> {
                for (int j = 0; j < 5; j++) {
                    stars.get(j).setText(j < selectedRating[0] ? "★" : "☆");
                }
            });

            star.setOnMouseClicked(e -> selectedRating[0] = ratingValue);

            stars.add(star);
            starBox.getChildren().add(star);
        }

        Label reviewLabel = new Label("😊 Enjoyed your session? Say thanks and share your thoughts in a review.");
        reviewLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));

        TextArea reviewArea = new TextArea();
        reviewArea.setFocusTraversable(false);
        reviewArea.setPromptText("Write your experience...");
        reviewArea.setWrapText(true);
        reviewArea.setPrefRowCount(4);
        reviewArea.setStyle(
                "-fx-background-radius: 8; -fx-border-radius: 8; -fx-border-color: #bdbdbd; -fx-focus-color: #64b5f6;");

        Button submitReview = new Button("✅ Submit Review");
        Button scheduleAgain = new Button("📅 Schedule Another Session");
        styleButton(submitReview, "#4CAF50", "#43a047");
        styleButton(scheduleAgain, "#2196F3", "#1976d2");

        HBox buttonBox = new HBox(15, submitReview, scheduleAgain);
        buttonBox.setAlignment(Pos.CENTER);

        VBox feedbackCard = new VBox(12, feedbackTitle, ratingPrompt, starBox, reviewLabel, reviewArea);
        feedbackCard.setPadding(new Insets(15));
        styleCard(feedbackCard);

        // Motivation Text
        Label motivation = new Label(
                "!!!  Keep going! If your syllabus isn’t done, consider scheduling another session to complete your journey.");
        motivation.setFont(Font.font("", FontWeight.BOLD, 14));
        motivation.setTextFill(Color.RED);
        motivation.setWrapText(true);

        // Main Layout
        VBox layout = new VBox(20, congrats, sessionSumBox, feedbackCard, motivation, buttonBox);
        layout.setPadding(new Insets(25));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom,#e3f2fd,#ffffff);"
                + "-fx-background-radius: 15; -fx-border-radius: 15;");
        layout.setPrefWidth(650);
        layout.setEffect(new DropShadow(15, Color.LIGHTGRAY));

        Scene scene = new Scene(layout);
        dialog.setScene(scene);
        dialog.show();

        // === Card Hover Effect ===
        addCardHoverEffect(sessionSumBox);
        addCardHoverEffect(feedbackCard);

        submitReview.setOnAction(e -> {
            String reviewText = reviewArea.getText().trim();
            int rating = selectedRating[0];

            if (reviewText.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please write a review before submitting.").show();
                return;
            }

            System.out.println("Review: " + reviewText);
            System.out.println("Rating: " + rating);

            String userId = user.getEmail();
            String mentorId = mentor.getEmail();

            Map<String, Object> reviewData = new HashMap<>();
            reviewData.put("review", reviewText);
            reviewData.put("givenBy", user.getFirstName() + " " + user.getLastName());
            reviewData.put("stars", rating);
            reviewData.put("date", LocalDateTime.now().toString());

            db = FirestoreClient.getFirestore();
            db.collection("users").document(mentorId).collection("reviews").document(userId).set(reviewData)
                    .addListener(() -> {
                        Platform.runLater(() -> {
                            new Alert(Alert.AlertType.INFORMATION, "Thanks! Your review has been submitted.").show();

                        });
                    }, Executors.newSingleThreadExecutor());

            Label thankYou = new Label("🎉 Thank you for your feedback!");
            thankYou.setFont(Font.font("Arial", FontWeight.BOLD, 18));
            thankYou.setTextFill(Color.web("#2e7d32"));
            layout.getChildren().setAll(thankYou);

            FadeTransition fade = new FadeTransition(Duration.seconds(1.5), layout);
            fade.setFromValue(0);
            fade.setToValue(1);
            fade.play();

            fade.setOnFinished(ev -> dialog.close());
        });

        scheduleAgain.setOnAction(e -> {
            dialog.close();
            System.out.println("User wants to schedule another session.");
        });
    }

    private void styleCard(VBox card) {
        card.setStyle(
                "-fx-background-color: #ffffff; -fx-background-radius: 12; -fx-border-radius: 12; "
                        + "-fx-border-color: #e0e0e0; -fx-border-width: 1;");
        card.setEffect(new DropShadow(5, Color.rgb(200, 200, 200)));
    }

    /** Adds hover animation for border color */
    private void addCardHoverEffect(VBox card) {
        card.setOnMouseEntered(e -> {
            card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12; -fx-border-radius: 12;"
                    + "-fx-border-color: #42a5f5; -fx-border-width: 2; -fx-cursor: hand;");
        });

        card.setOnMouseExited(e -> {
            card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12; -fx-border-radius: 12;"
                    + "-fx-border-color: #e0e0e0; -fx-border-width: 1;");
        });
    }

    private void styleButton(Button btn, String color, String hoverColor) {
        btn.setStyle("-fx-background-color: " + color
                + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 10; "
                + "-fx-padding: 8 20; -fx-cursor: hand;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: " + hoverColor
                + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 8 20;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: " + color
                + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 8 20;"));
    }
}
