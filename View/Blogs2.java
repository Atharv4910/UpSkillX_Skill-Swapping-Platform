package com.upskillx.View;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import com.upskillx.Controller.ApiController;
import com.upskillx.model.User;

public class Blogs2 {
    private int currentNewsIndex = 0;
    private Label newsContentLabel;
    User user;

    public Blogs2(User user) {
        this.user = user;
    }

    private GridPane blogCardGrid;

    Stage blogStage;
    Scene blogScene;

    public void setBlogStage(Stage blogStage) {
        this.blogStage = blogStage;
    }

    public void setBlogScene(Scene blogScene) {
        this.blogScene = blogScene;
    }

    StackPane mainRoot;

    public StackPane createBlogScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #ece9f2ff;");

        HBox topBar = new HBox();
        topBar.setPadding(new Insets(10));
        Image backbtn = new Image("assets\\images\\backbutton.png");
        ImageView bkImageView = new ImageView(backbtn);
        bkImageView.setFitWidth(72);
        bkImageView.setPreserveRatio(true);
        Button backbutton = new Button("", bkImageView);
        backbutton.setAlignment(Pos.TOP_LEFT);
        backbutton.setStyle("-fx-background-color: transparent;");
        backbutton.setOnAction(e -> {

            user_dashboard dashboard = new user_dashboard();
            dashboard.setUserDashboardStage(blogStage);
            dashboard.setUser(user);
            StackPane dashboardRoot = dashboard.createUserDashboardScene();
            Scene dashboardScene = new Scene(dashboardRoot, 1200, 800);
            blogStage.setScene(dashboardScene);
        });
        topBar.getChildren().add(backbutton);

        VBox centerContent = new VBox(25);
        centerContent.setPadding(new Insets(20));

        Label trendingLabel = new Label("🔥 Trending News");
        trendingLabel.setFont(Font.font("Arial", 20));
        trendingLabel.setTextFill(Color.web("#6c539eff"));

        newsContentLabel = new Label("Loading trending news...");
        newsContentLabel.setWrapText(true);
        newsContentLabel.setFont(Font.font(16));
        newsContentLabel.setTextFill(Color.web("#333333"));

        fetchTrendingNews();

        VBox newsBox = new VBox(newsContentLabel);
        newsBox.setPadding(new Insets(25));
        newsBox.setStyle("-fx-background-color: #cfbafaff; -fx-background-radius: 10;");
        newsBox.setMinHeight(160);
        newsBox.setMinWidth(650);
        newsBox.setMaxWidth(650);

        HBox newsRow = new HBox(10, newsBox);
        newsRow.setAlignment(Pos.CENTER);

        VBox newsSection = new VBox(10, trendingLabel, newsRow);

        // Blog Grid View
        Label blogLabel = new Label("📝 Blogs");
        blogLabel.setFont(Font.font("Arial", 20));
        blogLabel.setTextFill(Color.web("#5C3D99"));

        blogCardGrid = new GridPane();
        blogCardGrid.setHgap(20);
        blogCardGrid.setVgap(20);
        blogCardGrid.setPadding(new Insets(20));
        blogCardGrid.setAlignment(Pos.TOP_CENTER);

        ScrollPane blogScrollPane = new ScrollPane(blogCardGrid);
        blogScrollPane.setFitToWidth(true);
        blogScrollPane.setPannable(true);
        blogScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        blogScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        blogScrollPane.setStyle("-fx-background-color:transparent; -fx-background:transparent;");

        // Write Blog Button
        Button writeBlogBtn = new Button("✍ Write Blog");
        writeBlogBtn.setFont(Font.font(15));
        writeBlogBtn.setStyle("-fx-background-color:#593b90ff; -fx-text-fill: white; -fx-background-radius: 8;");
        writeBlogBtn.setOnAction(e -> {
            showBlogOverlay();
        });

        centerContent.getChildren().addAll(newsSection, blogLabel, blogScrollPane, writeBlogBtn);
        root.setTop(topBar);
        root.setCenter(centerContent);

        loadAllBlogs();
        mainRoot = new StackPane(root);
        return mainRoot;

    }

    private void fetchTrendingNews() {
        ApiController controller = new ApiController();
        JSONArray news = controller.getNews("technology"); // or "technology", "education", etc.

        if (news != null && news.length() > 0) {
            List<String> headlines = new java.util.ArrayList<>();
            for (int i = 0; i < Math.min(5, news.length()); i++) {
                JSONObject article = news.getJSONObject(i);
                String title = article.getString("title");
                headlines.add(title);
            }

            // Update your dummy list and label
            javafx.application.Platform.runLater(() -> {
                startNewsCarousel(headlines);
            });
        }
    }

    private void startNewsCarousel(List<String> newsList) {
        currentNewsIndex = 0;

        if (newsList == null || newsList.isEmpty()) {
            newsContentLabel.setText("No trending news available.");
            return;
        }

        // Show first headline immediately
        newsContentLabel.setText(newsList.get(0));

        Timeline newsTimeline = new Timeline(new KeyFrame(Duration.seconds(4), e -> {
            currentNewsIndex = (currentNewsIndex + 1) % newsList.size();
            newsContentLabel.setText(newsList.get(currentNewsIndex));
        }));
        newsTimeline.setCycleCount(Timeline.INDEFINITE);
        newsTimeline.play();
    }

    private void addBlogCard(String title, String snippet, String author) {
        int count = blogCardGrid.getChildren().size();
        int col = count % 3;
        int row = count / 3;

        VBox card = new VBox(15);
        card.setPadding(new Insets(10));
        card.setAlignment(Pos.TOP_LEFT);
        card.setStyle(
                "-fx-background-color: #FFFFFF; " +
                        "-fx-border-color: #D7C1FF; " +
                        "-fx-border-radius: 10; " +
                        "-fx-background-radius: 10; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5,0,0,2);");
        card.setPrefSize(220, 180);

        Text blogTitle = new Text(title);
        blogTitle.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        blogTitle.setFill(Color.BLACK);

        Label blogSnippet =new Label(snippet);
        blogSnippet.setFont(new Font(14));
        blogSnippet.setStyle("-fx-text-fill: black;");
        blogSnippet.setWrapText(true);
        Text blogAuthor = new Text(author);
        blogAuthor.setFill(Color.BLACK);
        blogAuthor.setFont(Font.font(14));

        card.getChildren().addAll(blogTitle, blogSnippet, blogAuthor);

        if (!title.isEmpty()) {
            card.setOnMouseClicked(e -> openFullBlog(title, snippet, author));
            card.setCursor(Cursor.HAND);
        }

        blogCardGrid.add(card, col, row);
    }

    private void openFullBlog(String title, String content, String author) {
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle(title);

        VBox box = new VBox(10);
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background-color:#EDE3FF");

        Label blogTitle = new Label(title);
        blogTitle.setFont(Font.font("Arial", 18));
        Label blogContent = new Label(content);
        blogContent.setWrapText(true);
        Label blogAuthor = new Label("Written by: " + author);
        blogAuthor.setTextFill(Color.GRAY);

        box.getChildren().addAll(blogTitle, blogContent, blogAuthor);

        Scene scene = new Scene(box, 400, 300);
        popup.setScene(scene);
        popup.show();
    }

    private void showBlogOverlay() {

        Rectangle overlay = new Rectangle();
        overlay.widthProperty().bind(mainRoot.widthProperty());
        overlay.heightProperty().bind(mainRoot.heightProperty());
        overlay.setFill(Color.rgb(0, 0, 0, 0.5));

        VBox form = new VBox(20);
        form.setPadding(new Insets(20));
        form.setAlignment(Pos.CENTER_LEFT);
        form.setMaxWidth(400);
        form.setMinHeight(500);
        form.setMaxHeight(500);
        form.setPrefHeight(500);
        form.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, #e0c3fc, #8ec5fc);" +
                        "-fx-border-radius: 15;" +
                        "-fx-background-radius: 15;" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 20, 0, 0, 5);" // subtle shadow
        );

        Text title = new Text("Share your Thoughts here...");
        title.setFill(Color.DARKSLATEBLUE);
        title.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        TextField titleField = new TextField();
        titleField.setPromptText("Blog Title");

        TextArea contentArea = new TextArea();
        contentArea.setPromptText("Write your blog here...");
        contentArea.setWrapText(true);
        contentArea.setPrefRowCount(8);

        TextField authorField = new TextField();
        authorField.setPromptText("Author Name");

        Label warningLabel = new Label();
        warningLabel.setTextFill(Color.RED);

        Button submitBtn = new Button("Submit");
        submitBtn.setStyle("-fx-background-color:rgb(135, 9, 160); -fx-text-fill: white;");

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle("-fx-background-color: gray; -fx-text-fill: white;");

        HBox btnBox = new HBox(10, submitBtn, cancelBtn);
        btnBox.setAlignment(Pos.CENTER);

        form.getChildren().addAll(title, titleField, contentArea, authorField, warningLabel, btnBox);

        StackPane overlayPane = new StackPane(overlay, form);
        overlayPane.setMinHeight(400);
        overlayPane.setPrefHeight(400);
        StackPane.setAlignment(form, Pos.CENTER);

        overlay.setOnMouseClicked(e -> mainRoot.getChildren().remove(overlayPane));
        cancelBtn.setOnAction(e -> mainRoot.getChildren().remove(overlayPane));

        submitBtn.setOnAction(e -> {
            if (titleField.getText().isEmpty() || contentArea.getText().isEmpty() || authorField.getText().isEmpty()) {
                warningLabel.setText("Please fill all fields.");
            } else {
                Map<String, Object> blogData = new HashMap<>();
                blogData.put("blogTitle", titleField.getText());
                blogData.put("content", contentArea.getText());
                blogData.put("author", user.getFirstName() + " " + user.getLastName());
                blogData.put("userId", user.getEmail());

                Firestore db = FirestoreClient.getFirestore();
                db.collection("users").document(user.getEmail()).collection("blogs").add(blogData)
                        .addListener(() -> {
                            Platform.runLater(() -> {
                                System.out.println("Blog Added");
                            });
                        }, Executors.newSingleThreadExecutor());
                db.collection("userBlogs")
                        .add(blogData)
                        .addListener(() -> {
                            Platform.runLater(() -> {
                                System.out.println("Blog Added");
                            });
                        }, Executors.newSingleThreadExecutor());

                addBlogCard(titleField.getText(), contentArea.getText(), authorField.getText());
                mainRoot.getChildren().remove(overlayPane);
            }
        });

        mainRoot.getChildren().add(overlayPane);

        form.setTranslateY(-mainRoot.getHeight() / 2); // Start above center
        form.setOpacity(0);

        TranslateTransition slideDown = new TranslateTransition(Duration.millis(400), form);
        slideDown.setFromY(-mainRoot.getHeight() / 2);
        slideDown.setToY(0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(400), form);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        ParallelTransition animation = new ParallelTransition(slideDown, fadeIn);
        animation.play();

    }

    private void loadAllBlogs() {
        blogCardGrid.getChildren().clear(); // Clear existing cards
        addBlogCard("Why Skill Swap Matters", "Collaboration over competition. Let's grow together.", "Atharv");
        addBlogCard("How I learned JavaFX", "Sharing my journey of JavaFX.", "Dhanashri");
        addBlogCard("Tips for Better Coding", "Write clean code, follow design patterns, and test often!", "Abhay");
        Firestore db = FirestoreClient.getFirestore();

        new Thread(() -> {
            try {
                ApiFuture<QuerySnapshot> futureBlogs = db.collection("userBlogs").get();
                List<QueryDocumentSnapshot> blogDocs = futureBlogs.get().getDocuments();

                System.out.println("Total blogs found: " + blogDocs.size());

                for (QueryDocumentSnapshot blogDoc : blogDocs) {
                    Map<String, Object> blogData = blogDoc.getData();

                    String title = blogData.getOrDefault("blogTitle", "").toString();
                    String content = blogData.getOrDefault("content", "").toString();
                    String author = blogData.getOrDefault("author", "").toString();

                    Platform.runLater(() -> addBlogCard(title, content, author));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


}