// 
package com.upskillx.View;

import javafx.util.Duration;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class landing_page extends Application {

    Scene landing_pageScene,loginpageScene,aboutPageScene,blogPageScene;
    Stage primaryStage;

    @Override
    public void start(Stage myStage) throws Exception {

        primaryStage = myStage;
        // code for logo
        Image logo = new Image("assets\\images\\logo.png");
        ImageView logoView = new ImageView(logo);
        logoView.setFitWidth(140);
        logoView.setFitHeight(72);

        // code for Navigation bar and login button

        Text home = new Text("Home");
        home.setStyle("-fx-font-size:18px;-fx-font-weight:BOLD;-fx-fill:rgba(37, 49, 102, 1);");
       
        Text about = new Text("About");
        about.setStyle("-fx-font-size:18px;-fx-font-weight:BOLD;-fx-fill:rgba(37, 49, 102, 1);");
        about.setOnMouseClicked(e ->{
            initializeAboutPage();
            primaryStage.setScene(aboutPageScene);
        });
        Text blog = new Text("Blog");
        blog.setStyle("-fx-font-size:18px;-fx-font-weight:BOLD;-fx-fill:rgba(37, 49, 102, 1);");
        blog.setOnMouseClicked(e ->{
            initializeBlogPage();
            primaryStage.setScene(blogPageScene);
        });
        Text feed = new Text("Feed");
        feed.setStyle("-fx-font-size:18px;-fx-font-weight:BOLD;-fx-fill:rgba(37, 49, 102, 1);");
        feed.setOnMouseClicked(e -> {
          Feed feedPage = new Feed();
          feedPage.setFeedStage(primaryStage);
            try {
                feedPage.start(primaryStage);
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
       //   Scene feedScene = feedPage.createFeedScene(() -> primaryStage.setScene(landing_pageScene));
           // primaryStage.setScene(feedScene);
        });
       

        Button login = new Button("Login");
        login.setStyle("-fx-min-width:100;-fx-min-height:40;-fx-font-size:16px;-fx-font-weight:BOLD;-fx-background-radius:20px;-fx-text-fill:rgba(253, 253, 253, 1);-fx-background-color:rgba(156,140,203,1)");

        login.setOnAction(e ->{
            initializeLoginPage();
            primaryStage.setScene(loginpageScene);
        });



        Image search_icon = new Image("assets\\images\\search_icon.png");
        ImageView si = new ImageView(search_icon);
        si.setFitWidth(35);
        si.setFitHeight(35);

        Region spaceBtlogoAndHome = new Region();
        spaceBtlogoAndHome.setPrefWidth(230);

        Region spBtEpAndLogin = new Region();
        spBtEpAndLogin.setPrefWidth(180);

        Region spBtLoginAndsrh = new Region();
        spBtLoginAndsrh.setPrefWidth(15);

        HBox navBar = new HBox(15,logoView,spaceBtlogoAndHome,home,about,blog,feed,spBtEpAndLogin,login,spBtLoginAndsrh,si);
        navBar.setPadding(new Insets(30));
        navBar.setSpacing(65);
        navBar.setAlignment(Pos.CENTER_LEFT);

        // code for tags lines on landing page
        Text preTitle = new Text("T r a d e   W h a t   Y o u   K n o w,");
        preTitle.setStyle("-fx-font-size:19px;");

        Text animatedText = new Text();
        animatedText.setStyle("-fx-font-size:35px;-fx-font-weight:BOLD;-fx-fill: rgba(0, 0, 0, 1);");

        Text cursor = new Text("|");
        cursor.setStyle("-fx-font-size:35px;-fx-fill: white;");

        TextFlow titleFlow = new TextFlow(animatedText, cursor);
        titleFlow.setPrefHeight(100);

        Text postTitle = new Text("Learn swap and level up\n" +"Your Skills, Their Needs – A Perfect Match");
        postTitle.setStyle("-fx-font-size:16px;-fx-font-weight:LIGHT");

        Button register = new Button("REGISTER");
        register.setStyle("-fx-min-width:120;-fx-min-height:50;-fx-font-size:16px;-fx-font-weight:BOLD;-fx-background-radius:20px;-fx-text-fill:rgba(253, 253, 253, 1);-fx-background-color:rgba(215,108,239,1)");
        register.setOnAction(event -> {
            Registration registration = new Registration();
            try {
                registration.start(primaryStage);
            }catch(Exception e){
                e.printStackTrace();
            }
        });

        VBox.setVgrow(register, Priority.ALWAYS);

        Region spBtptAndRes = new Region();
        spBtptAndRes.setPrefHeight(20);

        VBox intro = new VBox(preTitle,titleFlow,postTitle,spBtptAndRes,register);
        intro.setPadding(new Insets(120,60,60,60));
        intro.setAlignment(Pos.TOP_LEFT);
        intro.setSpacing(15);

        VBox vb = new VBox(navBar,intro);
        


        // Background image code
        Image BackgroundImage = new Image("assets\\images\\landing_page_upskillx.jpg");
        ImageView bImageView = new ImageView(BackgroundImage);

        
        StackPane stackPane = new StackPane(bImageView,vb);
          bImageView.fitWidthProperty().bind(stackPane.widthProperty());
          bImageView.fitHeightProperty().bind(stackPane.heightProperty());
        Scene scene = new Scene(stackPane,1230,790);
        startTypewriterAnimation(animatedText,cursor, "L E A R N  W H A T   \n    Y O U  N E E D");
        myStage.setScene(scene);
        myStage.setTitle("UpskillX : Skill swapping platform");
        myStage.getIcons().add(logo);

        landing_pageScene = scene;
        new Registration().setLandingPagScene(scene);
        
        myStage.show();
    }

    private void startTypewriterAnimation(Text animatedText, Text cursor, String fullText) {
    final int[] index = {0};
    final boolean[] erasing = {false};
    final String[] current = {""};  // holds current visible string

    // Main typing/erasing timeline
    Timeline typingTimeline = new Timeline();
    typingTimeline.setCycleCount(Timeline.INDEFINITE);

    KeyFrame typingKeyFrame = new KeyFrame(Duration.millis(150), event -> {
        if (!erasing[0]) {
            index[0]++;
            current[0] = fullText.substring(0, index[0]);
            animatedText.setText(current[0]);
            if (index[0] == fullText.length()) {
                erasing[0] = true;
                typingTimeline.pause();
                PauseTransition pause = new PauseTransition(Duration.seconds(2));
                pause.setOnFinished(e -> typingTimeline.play());
                pause.play();
            }
        } else {
            index[0]--;
            current[0] = fullText.substring(0, index[0]);
            animatedText.setText(current[0]);
            if (index[0] == 0) {
                erasing[0] = false;
            }
        }
    });

    typingTimeline.getKeyFrames().add(typingKeyFrame);
    typingTimeline.play();

    // Cursor blinking timeline (toggle between "|" and "")
    Timeline blink = new Timeline(new KeyFrame(Duration.seconds(0.5), e -> {
        cursor.setText(cursor.getText().isEmpty() ? "|" : "");
    }));
    blink.setCycleCount(Timeline.INDEFINITE);
    blink.play();
}

private void initializeLoginPage(){
    LoginPage loginPage = new LoginPage();
    loginPage.setLoginStage(primaryStage);
    loginpageScene = new Scene(loginPage.createLoginScene(this::handleBackbtnfromLogin),1230,790);
}

public void handleBackbtnfromLogin(){
    primaryStage.setScene(landing_pageScene);
}

private void initializeAboutPage(){
    About about = new About();
    about.setAboutStage(primaryStage);
    aboutPageScene = new Scene(about.createAboutPageScene(this::handleBackbtnfromAbout),1230,790);
}

public void handleBackbtnfromAbout(){
    primaryStage.setScene(landing_pageScene);
}

private void initializeBlogPage(){
    Blogs blogs = new Blogs();
    blogs.setBlogStage(primaryStage);
    blogPageScene = new Scene(blogs.createBlogScene(this::handleBackbtnfromBlog),1230,790);
}

public void handleBackbtnfromBlog(){
    primaryStage.setScene(landing_pageScene);
}

    
}