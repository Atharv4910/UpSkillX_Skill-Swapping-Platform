package com.upskillx.View;

import com.upskillx.Controller.FeedController;
import com.upskillx.model.User;
import com.upskillx.model.VideoModel;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class Feed2 extends Application {
    private FlowPane videoGrid;
    private Stage feedStage, primaryStage;
    Scene feedScene;
    StackPane dashScene;
    User user;

    public Feed2(User user) {
        this.user = user;
    }
 
    public void start(Stage mystage) throws Exception{
      HBox topBar = new HBox(10);
        topBar.setPadding(new Insets(15));
        topBar.setAlignment(Pos.CENTER);
        topBar.setStyle("-fx-background-color: linear-gradient(to right, #d4b8eeff, #e6c8edff);");

        Image backbtn = new Image("assets\\images\\backbutton.png");
        ImageView bkImageView = new ImageView(backbtn);
        bkImageView.setFitWidth(72);
        bkImageView.setPreserveRatio(true);
        Button backbutton = new Button("", bkImageView);
        backbutton.setAlignment(Pos.TOP_LEFT);
        backbutton.setStyle("-fx-background-color: transparent;");
        backbutton.setOnAction(event-> {
            user_dashboard dashboard = new user_dashboard();
            dashboard.setUserDashboardStage(mystage);
            dashboard.setUser(user);
            StackPane dashboardRoot = dashboard.createUserDashboardScene();
            Scene dashboardScene = new Scene(dashboardRoot, 1200, 800);
            mystage.setScene(dashboardScene);
        });


        Label feedTitle = new Label("FEED");
        feedTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        feedTitle.setTextFill(Color.web("#5E35B1"));

        Button postBtn = new Button("📤 Post a Short");
        postBtn.setStyle("-fx-background-color:#8E24AA;-fx-background-radius:5;-fx-font-weight: bold;");
        postBtn.setTextFill(Color.WHITE);
        postBtn.setFocusTraversable(false);

        Region spacer1 = new Region(), spacer2 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);

        topBar.getChildren().addAll(backbutton, spacer1, feedTitle, spacer2, postBtn);

        // Grid Feed Layout
        videoGrid = new FlowPane();
        videoGrid.setPadding(new Insets(20));
        videoGrid.setHgap(800);
        videoGrid.setVgap(10);
        videoGrid.setAlignment(Pos.TOP_CENTER);
        videoGrid.setStyle("-fx-background-color: #f7f1ff;");

        ScrollPane scrollPane = new ScrollPane(videoGrid);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent;");

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(scrollPane);

        // 🎯 POST BUTTON LOGIC
        FeedController controller = new FeedController();
        loadVideos(controller); // show videos at start

        postBtn.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("MP4 Video", "*.mp4"));
            File selectedFile = fileChooser.showOpenDialog(feedStage);

            if (selectedFile != null) {
                TextInputDialog input = new TextInputDialog("My Video");
                input.setTitle("Enter Title");
                input.setHeaderText("Provide a title for your video:");
                input.setContentText("Title:");

                input.showAndWait().ifPresent(title -> {
                    boolean success = controller.uploadVideo(selectedFile, "User1", title);
                    Alert alert = new Alert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
                    alert.setContentText(success ? "Video uploaded successfully!" : "Upload failed.");
                    alert.show();

                    if (success) loadVideos(controller);
                });
            }
        });
        feedStage = mystage;
         Scene sc = new Scene(root,1200,700);
         mystage.setScene(sc);
         mystage.show();
        
    }
    

    private void loadVideos(FeedController controller) {
        videoGrid.getChildren().clear();
        List<VideoModel> videos = controller.fetchVideos();

        for (VideoModel video : videos) {
            VBox card = new VBox(8);
            card.setPrefSize(300, 550);
            card.setPadding(new Insets(10));
            card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: #ddd;");
            card.setAlignment(Pos.TOP_CENTER);

            Label title = new Label(video.getTitle());
            title.setFont(Font.font("Arial", FontWeight.BOLD, 13));
            title.setWrapText(true);
            title.setTextFill(Color.web("#6A1B9A"));

            Label uploader = new Label("by " + video.getUploader());
            uploader.setFont(Font.font("Arial", 11));
            uploader.setTextFill(Color.web("#8e24aa"));

            try {
                Media media = new Media(video.getVideoUrl());
                MediaPlayer player = new MediaPlayer(media);
                MediaView mediaView = new MediaView(player);
                mediaView.setFitWidth(200);
                mediaView.setFitHeight(300);

                Button playBtn = new Button("▶ Play");
                playBtn.setStyle("-fx-background-color:#5E35B1");
                playBtn.setOnAction(ev -> {
                    player.stop();
                    player.play();
                });

                card.getChildren().addAll(mediaView, title, uploader, playBtn);
            } catch (Exception ex) {
                ImageView thumb = new ImageView(new Image("https://img.icons8.com/external-flat-icons-inmotus-design/67/000000/external-video-video-flat-icons-inmotus-design.png"));
                thumb.setFitWidth(100);
                thumb.setFitHeight(100);
                card.getChildren().addAll(thumb, title, uploader, new Label("⛔ Failed to load"));
            }

            videoGrid.getChildren().add(card);
        }
    }
}
   