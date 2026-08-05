package com.upskillx.View;

import java.net.URI;
import java.util.List;
import java.util.Scanner;

import org.checkerframework.checker.units.qual.m;

import com.upskillx.Controller.StorageController;
import com.upskillx.dao.UserDAO;
import com.upskillx.model.User;

import java.awt.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MentorProfile {
    User mentor, user;
    Stage myStage;

    public void setMyStage(Stage myStage) {
        this.myStage = myStage;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMentor(User mentor) {

        this.mentor = mentor;

    }

    public Scene getScene() {

        StackPane root = new StackPane();

        Image backbtn = new Image("assets\\images\\backbutton.png");
        ImageView bkImageView = new ImageView(backbtn);
        bkImageView.setFitWidth(72);
        bkImageView.setPreserveRatio(true);
        Button backbutton = new Button("", bkImageView);
        backbutton.setAlignment(Pos.TOP_LEFT);
        backbutton.setStyle("-fx-background-color: transparent;");
        backbutton.setOnAction(event -> {
            FindMentor findMentor = new FindMentor(mentor);
            findMentor.start(myStage);

        });

        BorderPane bp = new BorderPane();
        bp.setTop(backbutton);

        root.setStyle("-fx-background-color : #rgba(145, 105, 159, 1)");
        String img = mentor.getProfileImg() != null ? mentor.getProfileImg() : "assets\\icons\\profile.png";
        Image profileImg = new Image(img);
        ImageView profilView = new ImageView(profileImg);
        profilView.setFitHeight(150);
        profilView.setFitWidth(150);
        profilView.setPreserveRatio(true);

        Text mentorName = new Text(mentor.getFirstName() + " " + mentor.getLastName());
        mentorName.setFill(Color.BLACK);
        mentorName.setFont(Font.font("", FontWeight.BOLD, 30));
        Text tagLine = new Text("Your trusted tech mentor");
        tagLine.setFont(new Font(15));

        HBox review = new HBox(5);
        review.setPadding(new Insets(10));
        review.setAlignment(Pos.CENTER_LEFT);

        for (int i = 0; i < 4; i++) {

            Text stars = new Text("\u2605");
            stars.setFont(Font.font("Arial", FontWeight.BOLD, 30));
            stars.setFill(Color.GOLD);
            review.getChildren().add(stars);
        }

        Text rating = new Text("4.0");
        rating.setFont(Font.font("", FontWeight.BOLD, 13));
        rating.setFill(Color.WHITE);

        review.getChildren().add(rating);

        Text position = new Text(mentor.getPosition());
        position.setFill(Color.BLACK);
        position.setFont(Font.font("", FontWeight.BOLD, 15));

        Text experience = new Text(mentor.getEducation());
        experience.setFill(Color.BLACK);
        experience.setFont(Font.font("", FontWeight.BOLD, 15));

        VBox info = new VBox(10, mentorName, tagLine, review, position, experience);
        info.setPadding(new Insets(30));

        HBox basicInfo = new HBox(20, profilView, info);
        basicInfo.setPadding(new Insets(20));
        basicInfo.setMinWidth(700);
        basicInfo.setPrefWidth(600);
        // basicInfo.setMaxHeight(300);
        basicInfo.setMinHeight(200);
        // basicInfo.setPrefHeight(300);
        basicInfo.setAlignment(Pos.CENTER);
        // basicInfo.setStyle("-fx-background-color: white");
        basicInfo.setStyle(
                "-fx-background-color: linear-gradient(to right,rgb(135, 91, 165),rgb(226, 180, 232)); -fx-background-radius: 8;");
        basicInfo.setEffect(new DropShadow(10, Color.gray(0.6)));
        basicInfo.setMaxWidth(600);

        HBox tabButtons = new HBox(30);
        tabButtons.setPadding(new Insets(10));
        tabButtons.setAlignment(Pos.CENTER);
        tabButtons.setStyle("-fx-background-color: #f2e9ff;");

        Button btnSkills = new Button("Skills");
        Button btnAbout = new Button("About");
        Button btnAchievements = new Button("Achievements");

        for (Button b : new Button[] { btnSkills, btnAbout, btnAchievements }) {
            b.setStyle("-fx-background-color: transparent; -fx-font-weight: bold; -fx-font-size: 14px;");
        }

        tabButtons.getChildren().addAll(btnSkills, btnAbout, btnAchievements);

        VBox leftVBox = new VBox(15);

        Text title = new Text("SKILLS");
        title.setFill(Color.BLACK);
        title.setFont(Font.font("Arial", 20));

        HBox skills = new HBox(14);
        List<String> skillsList = mentor.getSkillsToTeach();
        if (skillsList != null) {
            for (String skill : skillsList) {
                skills.getChildren().add(createCard(skill));
            }
        }

        Text about = new Text("About");
        about.setFont(Font.font("Arial", 20));
        about.setFill(Color.BLACK);

        Label aboutDesc = new Label(mentor.getBio());
        aboutDesc.setWrapText(true);
        aboutDesc.setStyle("-fx-font-size: 13px; -fx-text-fill: #333333;");

        Text availability = new Text("Availability");
        availability.setFont(Font.font("Arial", 20));
        availability.setFill(Color.BLACK);

        HBox days = new HBox(8);
        days.getChildren().addAll(
                createButton("M"),
                createButton("T"),
                createButton("W"),
                createButton("TH"),
                createButton("F"),
                createButton("SA"),
                createButton("S"));

        Text prefFormat = new Text("Preferred format : " + mentor.getPrefFormat());
        prefFormat.setFont(Font.font("Arial", 20));
        prefFormat.setFill(Color.BLACK);
        Text exchangeLabel = new Text("Skill Exchange Requirements");
        exchangeLabel.setFont(Font.font("Arial",20));
        exchangeLabel.setFill(Color.BLACK);

        HBox hBox = new HBox(14);
        List<String> skillToLearn = mentor.getSkillsToLearn();
        if (skillToLearn != null) {
            for (String skill : skillToLearn) {
                hBox.getChildren().add(createCard(skill));
            }
        }

        Text feeLabel = new Text("Monetary Fee: "+mentor.getCp()+ " CPs");
        feeLabel.setFont(Font.font("Arial", 20));
        feeLabel.setFill(Color.BLACK);

        Button requestButton = new Button("Request Session");
        requestButton.setStyle("-fx-background-color: #5a4ff3; -fx-text-fill: white;");
        requestButton.setMaxWidth(Double.MAX_VALUE);

        Button chaButton = new Button("Chat with Mentor");
        chaButton.setStyle("-fx-background-color: #5a4ff3; -fx-text-fill: white;");
        chaButton.setMaxWidth(Double.MAX_VALUE);
        chaButton.setOnAction(event -> {
            ChatRoom obj = new ChatRoom();
            obj.setMentor(mentor);
            obj.setUser(user);
            obj.setMyStage(myStage);
            myStage.setScene(obj.getScene(mentor.getEmail()));

        });

        VBox.setMargin(requestButton, new Insets(10, 0, 0, 0));
        VBox.setMargin(chaButton, new Insets(10, 0, 0, 0));

        leftVBox.getChildren().addAll(title, skills, about, aboutDesc, availability, days, prefFormat, exchangeLabel,
                hBox, feeLabel, requestButton, chaButton);
        leftVBox.setMaxWidth(500);
        leftVBox.setPrefWidth(500);
        leftVBox.setMinWidth(300);

        VBox rightVBox = new VBox(15);
        rightVBox.setPadding(new Insets(10));

    
        Text reviews = new Text("Reviews");
        reviews.setFont(Font.font("Arial", 20));
        reviews.setFill(Color.BLACK);

        ScrollPane sp = new ScrollPane();
        VBox reviewBox = new VBox(15);
        reviewBox.setMinWidth(300);
        reviewBox.setPadding(new Insets(10));
        reviewBox.setStyle("-fx-background-color: #f9f9f9; -fx-border-radius: 8; -fx-border-color: black; -fx-border-style: solid;");
        sp.setContent(reviewBox);
        sp.setPrefHeight(500);
        sp.setFitToWidth(true);

        new UserDAO().loadMentorReviews(mentor.getEmail(),reviewBox);


        rightVBox.getChildren().addAll(reviews, sp);
        rightVBox.setAlignment(Pos.TOP_LEFT);
        rightVBox.setMinWidth(400);
        rightVBox.setPrefWidth(400);

        HBox mainContent = new HBox(40, leftVBox, rightVBox);
        mainContent.setStyle("-fx-background-color : transparent");
        mainContent.setPadding(new Insets(20));
        mainContent.setMinHeight(Region.USE_COMPUTED_SIZE);
        // mainContent.setPrefHeight(Region.USE_COMPUTED_SIZE);

        VBox profilepane = new VBox(mainContent);
        profilepane.setMaxHeight(Region.USE_COMPUTED_SIZE);
        profilepane.setPrefHeight(Region.USE_COMPUTED_SIZE);

        VBox aboutPane = new VBox(20);
        aboutPane.setPadding(new Insets(20));
        aboutPane.setAlignment(Pos.TOP_LEFT);
        aboutPane.setStyle("-fx-background-color : transparent");

        Text sectionTitle = new Text("👤 Meet Your Mentor");
        sectionTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
        sectionTitle.setFill(Color.DARKSLATEBLUE);
        HBox sectionBox = new HBox(10, sectionTitle);
        sectionBox.setAlignment(Pos.TOP_LEFT);

        Text videoTitle = new Text("🎥 Introduction Video");
        videoTitle.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 18));
        videoTitle.setFill(Color.DIMGRAY);

        MediaView mediaView2 = null;
        String rawUrl2 = mentor.getIntroVdoUrl() != null ? mentor.getIntroVdoUrl() : "assets\\icons\\profile.png";
        String introvideoUrl = rawUrl2.contains("alt=media") ? rawUrl2
                : rawUrl2 + (rawUrl2.contains("?") ? "&" : "?") + "alt=media";

        if (introvideoUrl != null && !introvideoUrl.isEmpty()) {
            try {
                Media media = new Media(introvideoUrl);
                MediaPlayer mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setAutoPlay(false);

                mediaView2 = new MediaView(mediaPlayer);
                mediaView2.setFitWidth(500);
                mediaView2.setFitHeight(280);
                mediaView2.setEffect(new DropShadow(10, Color.GRAY));
                mediaView2.setPreserveRatio(true);

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Failed to load video");
            }
        }

        VBox videoBox = new VBox(10, videoTitle, mediaView2);
        videoBox.setAlignment(Pos.CENTER_LEFT);
        videoBox.setPadding(new Insets(20, 20, 20, 40));

        Text bioTitle = new Text("📝 About Me");
        bioTitle.setFont(Font.font("Arial", FontWeight.SEMI_BOLD, 18));
        bioTitle.setFill(Color.DIMGRAY);

        Text introText = new Text(mentor.getBio());
        introText.setFont(Font.font("Segoe UI", 14));
        introText.setWrappingWidth(500);
        introText.setTextAlignment(TextAlignment.JUSTIFY);
        introText.setFill(Color.BLACK);
        introText.setLineSpacing(5);

        VBox intrBox = new VBox(10, bioTitle, introText);
        intrBox.setAlignment(Pos.CENTER_LEFT);
        intrBox.setPadding(new Insets(20, 20, 20, 40));

        aboutPane.getChildren().addAll(sectionBox, intrBox, videoBox);

        VBox achievementPane = new VBox(20);
        achievementPane.setPadding(new Insets(20));
        achievementPane.setAlignment(Pos.TOP_LEFT);

        Text achieveTitle = new Text("🏆 Achievements & Certificates");
        achieveTitle.setFont(Font.font("verdana", FontWeight.BOLD, 20));
        achieveTitle.setFill(Color.DARKSLATEBLUE);
        HBox titleBox = new HBox(achieveTitle);
        titleBox.setAlignment(Pos.TOP_LEFT);

        Text desc = new Text("Click to View Certification : ");
        desc.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        VBox certList = new VBox(10);
        certList.setPadding(new Insets(10));

        for (String certUrl : mentor.getCertificateUrl()) {
            String filename = certUrl.substring(certUrl.lastIndexOf('/') + 1);
            Button certButton = new Button("📄 " + filename);
            certButton.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
            certButton.setStyle("-fx-background-color:rgb(232, 170, 244); -fx-border-color: #ccc;");
            certButton.setOnAction(e -> {
                try {
                    Desktop.getDesktop().browse(new URI(certUrl));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            certList.getChildren().add(certButton);
        }
        VBox achieveBox = new VBox(15,desc, certList);
        achieveBox.setPadding(new Insets(20,20,20,40));

        Text presntLabel = new Text("Presentations / Articles:");
        presntLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        VBox presntList = new VBox(15);

        for (String presentUrl : mentor.getPresenatationUrl()) {
            String filename = presentUrl.substring(presentUrl.lastIndexOf('/') + 1);
            Button presentButton = new Button("📄 " + filename);
            presentButton.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 13));
            presentButton.setStyle("-fx-background-color: rgb(232, 170, 244); -fx-border-color: #ccc;");
            presentButton.setOnAction(e -> {
                try {
                    Desktop.getDesktop().browse(new URI(presentUrl));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            presntList.getChildren().add(presentButton);
        }
        VBox prsentBox = new VBox(15,presntLabel,presntList);
        prsentBox.setPadding(new Insets(20,20,20,40));

        achievementPane.getChildren().addAll(titleBox,achieveBox, prsentBox);


        for (VBox pane : new VBox[] { profilepane, aboutPane, achievementPane }) {
            pane.setAlignment(Pos.CENTER);
            pane.setPadding(new Insets(30));
        }
        aboutPane.setVisible(false);
        achievementPane.setVisible(false);

        btnSkills.setOnAction(e -> {
            profilepane.setVisible(true);
            profilepane.setManaged(true);

            aboutPane.setVisible(false);
            aboutPane.setManaged(false);

            achievementPane.setVisible(false);
            achievementPane.setManaged(false);
        });

        btnAbout.setOnAction(e -> {
            profilepane.setVisible(false);
            profilepane.setManaged(false);

            aboutPane.setVisible(true);
            aboutPane.setManaged(true);

            achievementPane.setVisible(false);
            achievementPane.setManaged(false);
        });

        btnAchievements.setOnAction(e -> {
            profilepane.setVisible(false);
            profilepane.setManaged(false);

            aboutPane.setVisible(false);
            aboutPane.setManaged(false);

            achievementPane.setVisible(true);
            achievementPane.setManaged(true);
        });

        VBox profile = new VBox(10);
        profile.setStyle(
                "-fx-background-color: #f2e9ff; -fx-background-radius : 20; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 20,10,0,5)");
        profile.setAlignment(Pos.CENTER);
        profile.setPadding(new Insets(20));
        profile.setMaxWidth(800);
        profile.setMinWidth(800);
        profile.setPrefWidth(800);
        profile.getChildren().addAll(basicInfo, tabButtons, profilepane, aboutPane, achievementPane);

        // VBox profile = new VBox(0,basicInfo,mainContent);
        // profile.setAlignment(Pos.TOP_CENTER);
        // profile.setPadding(new Insets(30));
        // profile.setMaxWidth(600);
        // profile.setStyle("-fx-background-color :white; -fx-background-radius : 20;
        // -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 20,10,0,5)");

        VBox centeringContainer = new VBox(profile);
        centeringContainer.setAlignment(Pos.TOP_CENTER); // center horizontally
        centeringContainer.setPadding(new Insets(30)); // optional spacing
        centeringContainer.setStyle("-fx-background-color: transparent;");

        // ScrollPane setup
        ScrollPane scrollPane = new ScrollPane(centeringContainer);
        scrollPane.setFitToWidth(true); // Make profile width responsive
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        StackPane.setAlignment(backbutton, Pos.TOP_LEFT);
        StackPane.setMargin(backbutton, new Insets(10)); // optional: margin from edge
        root.getChildren().addAll(bp, scrollPane);
        // root.setStyle("-fx-background-color: white; -fx-effect:
        // dropshadow(three-pass-box, rgba(184, 14, 231, 0.65), 10,0,0,5)");

        root.setPadding(new Insets(60, 60, 10, 60));
        root.setStyle(
                "-fx-background-color: linear-gradient(to right,rgb(203, 184, 250), #f0edfbff); -fx-background-radius: 8;");

        Scene sc = new Scene(root, 1200, 800);

        return sc;

    }

    static private Node createCard(String skill) {
        Label label = new Label(skill);
        label.setStyle(
                "-fx-background-color :rgb(45, 45, 202); -fx-padding : 5 10; -fx-border-radius : 5; -fx-background-radius : 5;");
        label.setFont(new Font(13));
        return label;

    }

    static private Button createButton(String d) {
        Button daybtn = new Button(d);
        daybtn.setStyle("-fx-background-color:rgb(142, 162, 210); -fx-border-radius: 4;");
        return daybtn;

    }

}
