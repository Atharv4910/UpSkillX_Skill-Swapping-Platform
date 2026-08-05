package com.upskillx.View;

import java.util.List;

import com.upskillx.dao.MentorDAO;
import com.upskillx.dao.UserDAO;
import com.upskillx.model.User;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FindMentor extends Application {

    Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    User user;
    public FindMentor(User user) {
        this.user = user;
    }

    Scene searchScene, mentorScene;
    MentorDAO mentorDAO;
    UserDAO userDAO;

    public void start(Stage myStage) {

        StackPane root = new StackPane();

        BorderPane layout = new BorderPane();

        VBox mainBox = new VBox(20);

        
        //  Image background = new Image("assets\\images\\mentorbg.png", 1200,
        //  800, false, true);
        //  BackgroundImage backgroundImage = new BackgroundImage(background,
        //  BackgroundRepeat.NO_REPEAT,
        //  BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
        //  new BackgroundSize(100, 100, true, true, false, true));
         
        root.setStyle("-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, #f1e4ff, #dfc7ff, #c9abff, #b899f7);");
         
        Image backbtn = new Image("assets\\images\\backbutton.png");
        ImageView bkImageView = new ImageView(backbtn);
        bkImageView.setFitWidth(72);
        bkImageView.setPreserveRatio(true);
        Button backbutton = new Button("", bkImageView);
        backbutton.setAlignment(Pos.TOP_LEFT);
        backbutton.setStyle("-fx-background-color: transparent;");
        backbutton.setOnAction(event -> {
            user_dashboard dashboard = new user_dashboard();
            dashboard.setUserDashboardStage(primaryStage);
            dashboard.setUser(user);
            StackPane dashboardRoot = dashboard.createUserDashboardScene();
            Scene dashboardScene = new Scene(dashboardRoot, 1200, 800);
            primaryStage.setScene(dashboardScene);
        });
        layout.setTop(new VBox(backbutton));
        Text title = new Text("Search Mentor");
        title.setFont(Font.font("verdana", FontWeight.BOLD, 40));
        title.setFill(Color.BLACK);

        Text subtitle = new Text("Let's Grow Toghether");
        subtitle.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 20));
        subtitle.setStyle("-fx-text-fill :rgb(95, 1, 142)");

        TextField search = new TextField();
        search.setPromptText("Search by...");
        search.setFocusTraversable(false);
        search.setAlignment(Pos.CENTER_LEFT);
        search.setPrefWidth(400);
        search.setStyle("-fx-background-radius: 20; -fx-padding: 10; -fx-border-color: transparent");

        Button filter = new Button("➕ Add Filters");
        filter.setStyle("-fx-background-color : white; -fx-border-radius: 10; -fx-background-radius: 10;");

        HBox serachBar = new HBox(10, search, filter);
        search.setAlignment(Pos.CENTER);

        VBox resultBox = new VBox(10);
        resultBox.setPadding(new Insets(30));
        resultBox.setAlignment(Pos.CENTER_LEFT);
        resultBox.setMaxWidth(600);
        resultBox.setStyle("-fx-background-color : rgba(255,255,255,0.8); -fx-background-radius: 10;");

        Text resultTitle = new Text("Search Results ");
        resultTitle.setFont(Font.font("Arial", 14));
        resultTitle.setFill(Color.BLACK);

        resultBox.getChildren().add(resultTitle);
        resultBox.setFillWidth(true);
        resultBox.setMaxWidth(Double.MAX_VALUE);
        resultBox.setPrefWidth(500);
        resultBox.setMinWidth(500);
        MentorDAO mentorDAO = new MentorDAO();
        List<User> mentorList = mentorDAO.getAllMentors(user.getEmail());

        for (User mentor : mentorList) {
            System.out.println(mentor.getEmail() + " is added");
            resultBox.getChildren().add(
                    createResultItem(mentor));
        }



        ScrollPane sp = new ScrollPane(resultBox);
        sp.setHbarPolicy(ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollBarPolicy.NEVER);
        sp.setFitToHeight(true);
        sp.setFitToWidth(true);
        sp.setMaxWidth(Region.USE_PREF_SIZE);
        sp.prefWidthProperty().bind(resultBox.widthProperty());


        mainBox.getChildren().addAll(title, subtitle, serachBar, sp);
        mainBox.setStyle(
                "-fx-background-color : rgba(255,255,255,0.3); -fx-border-color: white; -fx-border-radius: 20");
        mainBox.setPadding(new Insets(20));

        layout.setPadding(new Insets(60, 0, 60, 60));
        layout.setCenter(mainBox);

        VBox sideIconBar = new VBox(20);
        sideIconBar.setAlignment(Pos.CENTER);
        sideIconBar.setPadding(new Insets(20));
        sideIconBar.setStyle("-fx-background-color : transparent");
        sideIconBar.setPrefWidth(80);

        sideIconBar.getChildren().addAll(
                createIconButton("assets\\icons\\Search.png"),
                createIconButton("assets\\icons\\community.png"),
                createIconButton("assets\\icons\\reels.png"),
                createIconButton("assets\\icons\\profile.png"));

        layout.setRight(sideIconBar);
        root.getChildren().add(layout);

        Scene sc = new Scene(root, 1200, 800);
        myStage.setScene(sc);
        myStage.setTitle("Find Mentor");
        myStage.show();

        this.primaryStage = myStage;
        MentorProfile profile = new MentorProfile();
        profile.setMyStage(myStage);

    }

    private Button createIconButton(String imagePath) {

        Image icon = new Image(imagePath, 24, 24, true, true);
        ImageView iconView = new ImageView(icon);

        Button button = new Button();
        button.setGraphic(iconView);
        button.setStyle("-fx-background-color: transparent; -fx-background-radius: 50%;");
        button.setPrefHeight(50);
        button.setPrefWidth(50);
        return button;
    }

    private HBox createResultItem(User mentor) {
        userDAO = new UserDAO();
        Text nameText = new Text(mentor.getFirstName() + "" + mentor.getLastName());
        nameText.setFont(Font.font("Arial", 16));
        nameText.setFill(Color.BLACK);

        Text roleText = new Text(mentor.getPosition());
        roleText.setFont(Font.font("Arial", 12));
        roleText.setStyle("-fx-text-fill: #666;");

        VBox info = new VBox(2, nameText, roleText);

        Button addButton = new Button("➕");
        addButton.setPrefSize(45, 45);
        addButton.setStyle(
                "-fx-background-color: #dddddd; -fx-background-radius: 50%; -fx-font-size: 18px; -fx-text-fill: black");
        addButton.setOnAction(event -> {

            String fromEmail = LoginPage.currentUser;
            String toEmail = mentor.getEmail();

            userDAO.sendConnectionRequest(fromEmail, toEmail, () -> {
                addButton.setText("✅ Sent");
                addButton.setDisable(true);
            });
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox item = new HBox(10, info, spacer, addButton);
        item.setPrefWidth(400);
        item.setMinWidth(400);
        item.setAlignment(Pos.CENTER_LEFT);
        item.setPadding(new Insets(10));
        item.setStyle("-fx-background-color: #f2f2f2; -fx-background-radius: 6;");

        nameText.setOnMouseClicked(event -> {
            MentorProfile profile = new MentorProfile();
            profile.setMyStage(primaryStage);
            profile.setUser(user);
            profile.setMentor(mentor); 
            Scene profileScene = profile.getScene();
            primaryStage.setScene(profileScene);
        });

        return item;
    }

}
