package com.upskillx.View;

import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.upskillx.dao.UserDAO;
import com.upskillx.model.User;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.*;

public class MyProfile {

        UserDAO userDAO = new UserDAO();
        User user = LoginPage.loginUser;
        VBox myconnections = new VBox();
        HBox starRating = new HBox(5);

        public HBox personalInfo() {

                VBox root = new VBox();
                root.setPadding(new Insets(40));
                root.setSpacing(20);
                root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e0c3fc, #8ec5fc);");

                VBox mainContent = new VBox(30);
                mainContent.setAlignment(Pos.TOP_CENTER);

                // ==== LEFT: Profile Card ====
                VBox leftCard = new VBox(15);
                leftCard.setAlignment(Pos.TOP_CENTER);
                leftCard.setPadding(new Insets(30));
                leftCard.setPrefWidth(300);

                String cardStyle = "-fx-background-color: white;" +
                                "-fx-background-radius: 20;" +
                                "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0.3, 0, 5);";
                leftCard.setStyle(cardStyle);

                Label nameLabel = new Label(user.getFirstName() + " " + user.getLastName());
                nameLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));

                Label titleLabel = new Label("Your Trusted Mentor!!!");
                titleLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 15));
                titleLabel.setStyle("-fx-text-fill: #666;");

                Label locationLabel = new Label("📍 " + user.getLocation());
                locationLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 14));
                locationLabel.setStyle("-fx-text-fill: #777;");

                // LEFT SIDE: user info
                VBox leftInfoBox = new VBox(8, nameLabel, titleLabel, locationLabel);
                leftInfoBox.setAlignment(Pos.CENTER_LEFT);

                
                for (int i = 0; i < 5; i++) {
                    Label star = new Label(i < user.getStarCount() ? "★" : "☆");
                    star.setStyle("-fx-font-size: 18px; -fx-text-fill: gold;");
                    starRating.getChildren().add(star);
                }

                
                Text points = new Text("Avail CPs :  "+String.valueOf(user.getCp()+100));
                points.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
                Image cpimage = new Image("assets/images/cpimage.png");
                ImageView cpimageview = new ImageView(cpimage);
                cpimageview.setFitWidth(33); // Slightly smaller for neat top bar
                cpimageview.setPreserveRatio(true);
                HBox cpbox = new HBox(5, points, cpimageview); // 5px spacing between text and icon
                
                cpbox.setStyle("""
                                    -fx-border-color: #333;
                                    -fx-border-width: 2; /* Thicker border */
                                    -fx-border-radius: 10;
                                    -fx-background-radius: 10;
                                    -fx-background-color: white;
                                    -fx-effect: dropshadow(one-pass-box, rgba(0,0,0,0.1), 3, 0.0, 0, 1);
                                """);
                cpbox.setAlignment(Pos.CENTER);

                VBox rightInfoBox = new VBox(15,starRating, cpbox);
                rightInfoBox.setAlignment(Pos.CENTER_RIGHT);

                // Combine both in HBox
                HBox topSection = new HBox(250, leftInfoBox, rightInfoBox);
                topSection.setAlignment(Pos.CENTER_LEFT);
                topSection.setPrefWidth(Region.USE_COMPUTED_SIZE);
                HBox.setHgrow(rightInfoBox, Priority.ALWAYS);
                topSection.setMaxWidth(Double.MAX_VALUE);

                leftCard.setAlignment(Pos.CENTER_LEFT);
                leftCard.getChildren().add(topSection);

                // ==== RIGHT: Profile Details ====
                VBox rightCard = new VBox(20);
                rightCard.setPadding(new Insets(30));
                rightCard.setPrefWidth(650);
                rightCard.setStyle(cardStyle);

                Label detailsTitle = new Label("👤 User Details");
                detailsTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));

                GridPane grid = new GridPane();
                grid.setHgap(20);
                grid.setVgap(15);

                int row = 0;

                String[][] details = {
                                { "👤 Full Name:", user.getFirstName() + " " + user.getLastName() },
                                { "💼 Position:", user.getPosition() },
                                { "🎓 Education:", user.getEducation() },
                                { "✨ Skills:", user.getSkillsToTeach().toString() },
                                { "📄 About:", user.getBio() },
                                { "⏰ Availability:", "SAT,SUN" },
                                { "📁 Preferred Format:", user.getPrefFormat().toString() },
                                { "🔁 Skill Exchange Requirements:", user.getSkillsToLearn().toString() }
                };

                for (String[] pair : details) {
                        Text label = new Text(pair[0]);
                        label.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 15));
                        label.setFill(Color.DARKSLATEBLUE);
                        Label value = new Label(pair[1]);
                        value.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 15));
                        value.setWrapText(true);
                        grid.add(label, 0, row);
                        grid.add(value, 1, row++);
                }

                Button editBtn = new Button("✏️ Edit Profile");
                editBtn.setStyle("-fx-background-color: linear-gradient(to right, #7F00FF, #E100FF);" +
                                "-fx-text-fill: white;" +
                                "-fx-padding: 10 30;" +
                                "-fx-font-weight: bold;" +
                                "-fx-background-radius: 30;");

                editBtn.setOnMouseEntered(e -> editBtn
                                .setStyle("-fx-background-color: linear-gradient(to right, #682AE9, #B700F9);" +
                                                "-fx-text-fill: white;" +
                                                "-fx-padding: 10 30;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-background-radius: 30;"));

                editBtn.setOnMouseExited(e -> editBtn
                                .setStyle("-fx-background-color: linear-gradient(to right, #7F00FF, #E100FF);" +
                                                "-fx-text-fill: white;" +
                                                "-fx-padding: 10 30;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-background-radius: 30;"));

                rightCard.getChildren().addAll(detailsTitle, grid, editBtn);

                mainContent.getChildren().addAll(leftCard, rightCard);
                root.getChildren().add(mainContent);

                // ==== My Connections Card ====
                Text headcon = new Text("👥 My Connections");
                headcon.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
                headcon.setFill(Color.web("#333"));

                myconnections = new VBox(15);
                VBox.setMargin(headcon, new Insets(20, 0, 10, 0));
                myconnections.setPrefHeight(720);
                myconnections.setPrefWidth(400);
                myconnections.setAlignment(Pos.TOP_CENTER);
                myconnections.setStyle("-fx-background-color: linear-gradient(to bottom right, #e0c3fc, #8ec5fc);");
                myconnections.getChildren().add(headcon);
                fetchAcceptedConnections();

                HBox Personalinfo = new HBox(30, root, myconnections);
                Personalinfo.setPadding(new Insets(20));
                return Personalinfo;
        }

        private void fetchAcceptedConnections() {
                Firestore db = FirestoreClient.getFirestore();
                String currentUserEmail = user.getEmail();
                VBox conn = new VBox(10);
                conn.setPadding(new Insets(10));
                try {
                        CollectionReference connectionRef = db.collection("users")
                                        .document(currentUserEmail)
                                        .collection("connectionRequests");

                        for (DocumentSnapshot doc : connectionRef.get().get().getDocuments()) {
                                String status = doc.getString("status");
                                String fromEmail = doc.getString("from");

                                if ("accepted".equalsIgnoreCase(status) && fromEmail != null) {
                                        DocumentSnapshot fromUserDoc = db.collection("users").document(fromEmail).get()
                                                        .get();

                                        if (fromUserDoc.exists()) {
                                                String firstName = fromUserDoc.getString("firstName");
                                                String lastName = fromUserDoc.getString("lastName");
                                                String profileImageUrl = (fromUserDoc.getString("profileImg")) != null
                                                                ? fromUserDoc.getString("profileImg")
                                                                : "assets\\icons\\profile.png";

                                                String fullName = firstName + " " + lastName;

                                                HBox connectionCard = buildConnectionCard(fullName, profileImageUrl);
                                                conn.getChildren().add(connectionCard);
                                        }
                                }
                        }
                        myconnections.getChildren().add(conn);
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        private HBox buildConnectionCard(String fullName, String imageUrl) {
                HBox card = new HBox(10);
                card.setPadding(new Insets(10));
                card.setAlignment(Pos.CENTER_LEFT);
                card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.05), 4, 0, 0, 2);");

                ImageView imageView = new ImageView(imageUrl);
                imageView.setFitWidth(40);
                imageView.setFitHeight(40);

                Circle clip = new Circle(20, 20, 20);
                imageView.setClip(clip);

                Label nameLabel = new Label(fullName);
                nameLabel.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 14));
                nameLabel.setTextFill(Color.web("#333"));

                card.getChildren().addAll(imageView, nameLabel);
                return card;
        }

}
