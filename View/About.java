package com.upskillx.View;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class About {

    Stage aboutStage;
    Scene aboutScene;

    public void setAboutStage(Stage aboutStage) {
        this.aboutStage = aboutStage;
    }

    public void setAboutScene(Scene aboutScene) {
        this.aboutScene = aboutScene;
    }

    public ScrollPane createAboutPageScene(Runnable back) {

        Image im = new Image("assets\\images\\bg.jpg");
        ImageView imv = new ImageView(im);
        imv.setFitWidth(1350);
        imv.setFitHeight(400);

        Image backbtn = new Image("assets\\images\\backbutton.png");
        ImageView bkImageView = new ImageView(backbtn);
        bkImageView.setFitWidth(72);
        bkImageView.setPreserveRatio(true);
        Button backbutton = new Button("", bkImageView);
        backbutton.setAlignment(Pos.TOP_LEFT);
        backbutton.setStyle("-fx-background-color: transparent;");
        backbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                back.run();
            }
        });

        HBox backBtnBox = new HBox(backbutton);
        backBtnBox.setAlignment(Pos.TOP_LEFT);
        backBtnBox.setPadding(new Insets(0, 10, 0, 10));

        Text title = new Text("UpSkillX\nLearn,Swap & Level Up");
        title.setFont(Font.font("Verdana", 50));
        title.setFill(Color.WHITE);
        title.setStyle("-fx-font-weight: bold;-fx-font-size: 50px;");
        title.setTextAlignment(TextAlignment.CENTER);

        DropShadow shadow = new DropShadow();
        shadow.setOffsetY(3.0);
        shadow.setColor(Color.web("#25004dff"));
        title.setEffect(shadow);

        FadeTransition fadeInOut = new FadeTransition(Duration.seconds(1.2), title);
        fadeInOut.setFromValue(0.2);
        fadeInOut.setToValue(1.0);
        fadeInOut.setCycleCount(FadeTransition.INDEFINITE);
        fadeInOut.setAutoReverse(true);
        fadeInOut.play();

        StackPane root = new StackPane(imv, title);
        root.setStyle("-fx-alignment: center;");
        root.setPrefHeight(400);

        VBox vb = new VBox(backBtnBox, root);

        Image imglogo = new Image("assets\\images\\c2wlogoooo.png");
        ImageView logoview = new ImageView(imglogo);
        logoview.setFitHeight(60);
        logoview.setPreserveRatio(true);

        Text coachingTitle = new Text(" Mentorship That Fuels Us!!!");
        coachingTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 32));
        coachingTitle.setFill(Color.web("#7828b1ff"));
        HBox logobox = new HBox(2, logoview, coachingTitle);

        Text coachingInfo = new Text(
                "We are proud students of Core2Web, located in Narhe,Pune. " +
                        "The coaching class is extremely supportive and encourages innovation at every step. " +
                        "A special thanks to Shashi Sir, who is truly the best teacher. His deep knowledge, clear explanations, and passion for teaching "
                        +
                        "make every session meaningful and engaging. He teaches in-depth and ensures every student understands the core concepts thoroughly.\n\n"
                        +
                        "Also Our mentors are always available to guide us, resolve doubts, and push us to achieve our best."
                        +
                        "Their dedication, structured teaching methods, and hands-on approach have played a vital role in shaping this platform.\n\n"
                        +
                        "We really want to thank our instructors Sachin Sir,Pramod Sir and Akshay Sir for teaching and guiding us."
                        +
                        "We also want to thank our mentors Shiv Sir and Subodh Sir for showing us right direction." +
                        "And very special thank you to our team leader Samruddhi Didi for helping and guiding us at every step. For inspiring us throughout our SuperX journey.\n"
                        +
                        "Thank you so much to the entire team of Core2Web.");

        coachingInfo.setFont(Font.font("Segoe UI", 16));
        coachingInfo.setFill(Color.BLACK);
        coachingInfo.setTextAlignment(TextAlignment.LEFT);
        coachingInfo.setWrappingWidth(700);

        Button visitClassButton = new Button("Visit Core2Web Website");
        visitClassButton.setStyle("-fx-background-color: violet; -fx-text-fill: white; -fx-font-weight: bold;");
        visitClassButton.setOnMouseEntered(e -> visitClassButton.setStyle(
                "-fx-background-color: #9f4dfb; -fx-text-fill: white; -fx-font-weight: bold; -fx-scale-x: 1.05; -fx-scale-y: 1.05;"));
        visitClassButton.setOnMouseExited(e -> visitClassButton
                .setStyle("-fx-background-color: violet; -fx-text-fill: white; -fx-font-weight: bold;"));
        visitClassButton.setOnAction(e -> {
            try {
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler https://www.core2web.in/");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        VBox coachingClassVBox = new VBox(15, logobox, coachingInfo, visitClassButton);
        coachingClassVBox.setPadding(new Insets(30));
        coachingClassVBox.setStyle("-fx-background-color: #f2e9fd; -fx-background-radius: 20;");
        coachingClassVBox.setAlignment(Pos.CENTER_LEFT);
        coachingClassVBox.setMaxWidth(750);
        coachingClassVBox.setEffect(new DropShadow(10, Color.LIGHTGRAY));
        coachingClassVBox.setOnMouseEntered(e -> coachingClassVBox.setStyle(
                "-fx-background-color: #f0dfff; -fx-background-radius: 20; -fx-scale-x:1.02; -fx-scale-y:1.02;"));
        coachingClassVBox.setOnMouseExited(
                e -> coachingClassVBox.setStyle("-fx-background-color: #f2e9fd; -fx-background-radius: 20;"));
        VBox.setMargin(coachingClassVBox, new Insets(70, 0, 70, 30));

        Image image = new Image("assets\\images\\ShashiSir.png");
        ImageView c2wimv2 = new ImageView(image);
        c2wimv2.setPreserveRatio(true);
        c2wimv2.setFitWidth(350);
        VBox imageVBox2 = new VBox(c2wimv2);
        imageVBox2.setAlignment(Pos.CENTER);
        imageVBox2.setPadding(new Insets(10));
        imageVBox2.setEffect(new DropShadow(10, Color.LIGHTGRAY));

        HBox mainHBox1 = new HBox(40, coachingClassVBox, imageVBox2);
        mainHBox1.setPadding(new Insets(10));
        mainHBox1.setStyle("-fx-background-color: linear-gradient(to right, #ebe4ff, #f6f4ff);");
        mainHBox1.setAlignment(Pos.CENTER);

        Text heading = new Text("Why Choose UpSkillX?");
        heading.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
        heading.setFill(Color.web("#7828b1ff"));

        Text para = new Text(
                "Our Skill Swapping Platform was developed to bridge the gap between learners and"
                        + " teaching enthusiasts by creating a collaborative"
                        + " space where users can share their expertise, learn from others, and connect with mentors. It is designed to"
                        + " connect people who want to learn new skills by exchanging knowledge with one another. Users can list the skills"
                        + " they can teach and specify the skills they want to learn, making it easy to find matching partners for peer-to-peer learning."
                        + " With this project, we aim to revolutionize skill development by creating an inclusive, accessible space where people"
                        + " can share knowledge, build connections, and grow their abilities — all through collaborative learning and mutual skill exchange.");
        para.setFont(Font.font("Segoe UI", 18));
        para.setFill(Color.web("#000000ff"));
        para.setTextAlignment(TextAlignment.LEFT);
        para.setWrappingWidth(500);

        VBox textVBox = new VBox(20, heading, para);
        textVBox.setPadding(new Insets(30));
        textVBox.setStyle("-fx-background-color: #e5dbf8ff; -fx-background-radius: 20;");
        textVBox.setAlignment(Pos.CENTER_LEFT);
        textVBox.setMaxWidth(750);
        textVBox.setEffect(new DropShadow(10, Color.LIGHTGRAY));
        textVBox.setOnMouseEntered(e -> textVBox.setStyle(
                "-fx-background-color: #d3c3f3; -fx-background-radius: 20; -fx-scale-x:1.02; -fx-scale-y:1.02;"));
        textVBox.setOnMouseExited(
                e -> textVBox.setStyle("-fx-background-color: #e5dbf8ff; -fx-background-radius: 20;"));

        Image image2 = new Image("assets\\images\\swapping.jpg");
        ImageView imv2 = new ImageView(image2);
        imv2.setPreserveRatio(true);
        imv2.setFitWidth(350);

        VBox imageVBox = new VBox(imv2);
        imageVBox.setAlignment(Pos.CENTER);
        imageVBox.setPadding(new Insets(50));
        imageVBox.setEffect(new DropShadow(10, Color.LIGHTGRAY));

        HBox mainHBox = new HBox(40, textVBox, imageVBox);
        mainHBox.setPadding(new Insets(10));
        mainHBox.setStyle("-fx-background-color: linear-gradient(to right, #ebe4ff, #f6f4ff);");
        mainHBox.setAlignment(Pos.CENTER);

        HBox cardsContainer = new HBox(20);
        cardsContainer.setPadding(new Insets(30));
        cardsContainer.setAlignment(Pos.CENTER);

        VBox howItWorksCard = createCard("\uD83D\uDCA1 How It Works?", """
                Skill swapping is simple! Just follow these steps:
                • Join the Platform: Create your profile with offered & wanted skills.
                • Find a Match: Browse/post swap requests to connect.
                • Make an Exchange: Discuss terms and swap skills fairly.
                • Grow & Connect: Learn, share, and expand your network freely!
                """);

        VBox visionCard = createCard("\uD83C\uDF0D Our Vision",
                "To create a world where learning and growth are accessible"
                        + " to everyone through a community-driven skill exchange."
                        + " Everyone has something valuable to offer.");

        VBox missionCard = createCard("\uD83D\uDE80 Our Mission",
                "To build a vibrant, inclusive platform where people share "
                        + " skills and connect with like-minded individuals."
                        + " We remove financial barriers and empower everyone to grow.");

        VBox teamCard = createCard("\uD83D\uDC65 Meet Our Team",
                "1.Atharv Gaikwad\n"
                        + "2.Abhay Chavan\n"
                        + "3.Dhanashri Gadade\n");

        cardsContainer.getChildren().addAll(howItWorksCard, visionCard, missionCard, teamCard);

        VBox mainvb = new VBox(vb, mainHBox1, mainHBox, cardsContainer);
        mainvb.setFillWidth(true);
        mainvb.setPadding(Insets.EMPTY);
        mainvb.setStyle("-fx-background-color:rgba(255, 255, 255, 1)");

        ScrollPane scroll = new ScrollPane(mainvb);

        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        scroll.setPannable(true);

        scroll.setStyle(
                "-fx-background-color: transparent;" +
                        "-fx-padding: 0;" +
                        " -fx-focus-color: transparent;" +
                        "-fx-faint-focus-color: transparent;");

        scroll.setFitToWidth(true);
        scroll.setFitToHeight(false);
        scroll.setPrefSize(1300, 800);

        return scroll;
    }

    private VBox createCard(String titleText, String bodyText) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setPrefWidth(280);
        card.setStyle("""
                -fx-background-color: #eae3f7ff;
                -fx-background-radius: 15;
                -fx-border-radius: 15;
                -fx-border-color: #d2a9faff;
                -fx-border-width: 2;
                """);
        card.setEffect(new DropShadow(10, Color.LIGHTBLUE));

        Label title = new Label(titleText);
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        title.setTextFill(Color.web("#46116fff"));

        Label content = new Label(bodyText);
        content.setFont(Font.font("Segoe UI", 13));
        content.setStyle("-fx-font-weight:bold");
        content.setWrapText(true);
        content.setTextFill(Color.web("#444"));

        card.setOnMouseEntered(e -> {
            card.setStyle("""
                    -fx-background-color: #ddd0f7ff;
                    -fx-background-radius: 15;
                    -fx-border-radius: 15;
                    -fx-border-color: #c292fbff;
                    -fx-border-width: 2;
                    -fx-scale-x: 1.05;
                    -fx-scale-y: 1.05;
                    """);
        });
        card.setOnMouseExited(e -> {
            card.setStyle("""
                    -fx-background-color: #eae3f7ff;
                    -fx-background-radius: 15;
                    -fx-border-radius: 15;
                    -fx-border-color: #d2a9faff;
                    -fx-border-width: 2;
                    """);
        });

        card.getChildren().addAll(title, content);
        return card;
    }
}
