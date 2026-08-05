package com.upskillx.View;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.cloud.firestore.Firestore;
import com.upskillx.Controller.AuthController;
import com.upskillx.Controller.QuizGenerator;
import com.upskillx.Controller.RegistrationController;
import com.upskillx.Controller.StorageController;
import com.upskillx.model.User;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Registration extends Application {

    final FlowPane skillTagsPane = new FlowPane(10, 10);
    final FlowPane skillsTagPane2 = new FlowPane(10, 10);
    private TextField emailField, nameField, surnamField, numberField, bioField, locationField, educationField,
            ageField, workLocation, positionField;
    private TextField teachField, learnField, linkedInField, portfolioField;
    private CheckBox chat, video, fileShare;
    private ComboBox<String> genderComboBox, skillLevel;
    List<String> uploadedCertificateUrls = new ArrayList<>();
    List<String> uploadedPresentation = new ArrayList<>();
    private PasswordField passwordField;
    String imageUrl, videoUrl, certUrl;
    int stars, credits, score;
    User user = new User();

    AuthController controller = new AuthController();
    BorderPane root;
    Stage primaryStage, loginStage;
    Scene loginScene, landingPagScene;

    public void setLandingPagScene(Scene landingPagScene) {
        this.landingPagScene = landingPagScene;
    }

    Text title = new Text("Register Today. Teach One, Learn One.");

    Text registerTitle = new Text("UPSKILLX REGISTRATION");

    public void start(Stage myStage) {

        this.primaryStage = myStage;

        // Image background = new Image("assets\\images\\bgfinal.png");
        // * ImageView bgView = new ImageView(background);
        // * bgView.setFitWidth(1200);
        // * bgView.setFitHeight(800);
        // * bgView.setPreserveRatio(false);
        // */

        registerTitle.setFont(Font.font("verdana", FontWeight.BOLD, 30));

        // BackgroundImage bg = new BackgroundImage(background,
        // BackgroundRepeat.NO_REPEAT,
        // BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
        // new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false,
        // true, true));

        root = new BorderPane();
        root.setStyle(
                "-fx-background-color: linear-gradient(from 0% 0% to 100% 100%, "
                        + "#f3e9ff 0%, #d3c2f7 50%, #b59cf5 100%);");
        // root.setBackground(new Background(backgroundImage));

        ImageView logo = new ImageView(new Image("assets\\images\\logo.png"));
        logo.setFitHeight(80);
        logo.setPreserveRatio(true);

        HBox logoBox = new HBox(200, logo, registerTitle);
        HBox.setMargin(registerTitle, new Insets(80, 0, 0, 0));
        logoBox.setAlignment(Pos.TOP_LEFT);
        logoBox.setPadding(new Insets(25, 15, 60, 20));

        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 22));

        Text emailText = new Text("Enter Email");
        emailText.setFont(new Font(15));
        emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setStyle("-fx-padding: 8 10; -fx-background-radius: 5; -fx-border-color: #ccc;");

        Text passText = new Text("Enter Password");
        passText.setFont(new Font(15));
        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-padding: 8 10; -fx-background-radius: 5; -fx-border-color: #ccc;");

        VBox credential = new VBox(15, emailText, emailField, passText, passwordField);
        credential.setAlignment(Pos.CENTER_LEFT);

        CheckBox msgtText = new CheckBox(
                "By clicking Agree & Join or Continue, you agree to the UpSkillX User Agreement, Privacy Policy, and Cookie Policy.");
        msgtText.setWrapText(true);
        msgtText.setStyle("-fx-text-fill : black; -fx-font-size : 13");

        Button agree = new Button("Agree & Join");
        agree.setMaxWidth(Double.MAX_VALUE);
        agree.setOnAction(event -> {
            System.out.println("Button clicked");
            String msg = AuthController.signUp(emailField.getText(), passwordField.getText());
            String tx = "Please Complete Further Registration process to start with UpSkillX";
            // tx.setFont(new Font(15));
            // tx.setStyle("-fx-font-weight : bold");
            if (msg == null) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registration");
                alert.setHeaderText("User Registered Successfully");
                alert.setContentText(tx);
                alert.setOnHidden(e -> {
                    nextBox(primaryStage);
                });
                alert.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Sign-Up Error");
                alert.setHeaderText(null);
                alert.setContentText(msg);
                alert.showAndWait();
            }
        });

        agree.setStyle(
                "-fx-background-color: #0a66c2; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25; -fx-padding: 10 0;");
        // agree.setOnAction(event -> {
        // nextBox();
        // });
        Text msgText2 = new Text("Already on UpSkillX?");
        msgText2.setFont(new Font(15));
        Button loginBtn = new Button("Sign In");
        loginBtn.setOnAction(event -> {
            initializeLoginPage();
            primaryStage.setScene(loginScene);
        });
        loginBtn.setTextFill(Color.BLUE);
        loginBtn.setFont(new Font(15));
        loginBtn.setStyle("-fx-background-color : transparent; ");

        HBox loginNav = new HBox(10, msgText2, loginBtn);
        loginNav.setAlignment(Pos.CENTER);

        VBox card = new VBox(15);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(30));
        card.setMaxWidth(400);
        card.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.35);"
                        + "-fx-background-radius: 20;"
                        + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0, 0, 4);");
        card.getChildren().addAll(credential, msgtText, agree, loginNav);

        VBox centerBox = new VBox(15);
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerBox.setPadding(new Insets(30));
        centerBox.setStyle("-fx-background-color: transparent;");
        centerBox.getChildren().addAll(title, card);

        root.setTop(logoBox);
        root.setCenter(centerBox);

        StackPane stack = new StackPane(root);
        stack.setStyle("-fx-background-color : rgb(193, 195, 243)");
        Scene sc = new Scene(stack, 1200, 800);

        myStage.setScene(sc);
        myStage.setTitle("Registration");
        myStage.show();
    }

    private void nextBox(Stage stage) {

        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 30));

        Text firstName = new Text("First Name ");
        firstName.setFont(new Font(15));
        nameField = new TextField();
        nameField.setStyle("-fx-padding: 8 10; -fx-background-radius: 5; -fx-border-color: #ccc;");

        Text surname = new Text("Last Name");
        surname.setFont(new Font(15));
        surnamField = new TextField();
        surnamField.setStyle("-fx-padding: 8 10; -fx-background-radius: 5; -fx-border-color: #ccc;");

        Text phone = new Text("Phone Number");
        phone.setFont(new Font(15));
        numberField = new TextField("+91 ");
        numberField.setStyle("-fx-padding: 8 10; -fx-background-radius: 5; -fx-border-color: #ccc;");

        VBox basicInfo = new VBox(15, firstName, nameField, surname, surnamField, phone, numberField);
        basicInfo.setAlignment(Pos.CENTER_LEFT);

        Button continueBtn = new Button("Continue");
        continueBtn.setOnAction(event -> {
            createStep1(stage);
        });
        continueBtn.setMaxWidth(Double.MAX_VALUE);
        continueBtn.setStyle(
                "-fx-background-color: #0a66c2; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25; -fx-padding: 10 0;");

        VBox vb = new VBox(15, basicInfo, continueBtn);
        vb.setPadding(new Insets(30));
        vb.setMaxWidth(400);
        vb.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.35);"
                        + "-fx-background-radius: 20;"
                        + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0, 0, 4);");
        vb.setAlignment(Pos.TOP_CENTER);

        VBox centerBox = new VBox(15);
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerBox.setPadding(new Insets(30));
        // centerBox.setStyle("-fx-background-color: #f3f2ef;");
        centerBox.getChildren().addAll(title, vb);

        root.setCenter(centerBox);

    }

    private VBox createCard(String titleText) {
        VBox box = new VBox(15);
        box.setPadding(new Insets(30));
        box.setAlignment(Pos.TOP_CENTER);
        box.setPrefWidth(400);
        box.setStyle(
                "-fx-background-color: #f3f2ef; -fx-background-radius: 10; -fx-border-color: #ddd; -fx-border-radius: 10;");

        Label title = new Label(titleText);
        title.setFont(new Font("Arial", 16));
        title.setStyle("-fx-font-weight: bold;");

        box.getChildren().add(title);
        return box;
    }

    private void createStep1(Stage stage) {
        VBox step = createCard("Create Your Account – Step 1 of 4");

        Image profileImg = new Image("assets\\images\\profile1.png");
        ImageView profilView = new ImageView(profileImg);
        profilView.setFitHeight(150);
        profilView.setFitWidth(150);
        profilView.setPreserveRatio(true);
        profilView.setOnMouseClicked(event -> {

            StorageController controller = new StorageController();
            imageUrl = controller.uploadImage(stage);
            if (imageUrl != null) {
                profilView.setImage(new Image(imageUrl));
                user.setProfileImg(imageUrl);
            }

        });

        Text bio = new Text("Add Short bio about yourself ");
        bioField = new TextField();
        bioField.setPromptText("Add short bio here...");
        bioField.setMaxHeight(100);
        bioField.setMinHeight(100);
        bioField.setMinWidth(100);

        VBox bioBox = new VBox(10, bio, bioField);

        ageField = new TextField();
        ageField.setPromptText("Enter Your age");

        genderComboBox = new ComboBox<>();
        genderComboBox.getItems().addAll("Male", "Female", "Non-binary", "Prefer not to say", "Other");
        genderComboBox.setPromptText("Select Gender");

        genderComboBox
                .setStyle("-fx-background-radius: 6; -fx-border-radius: 6; -fx-border-color: #ccc; -fx-padding: 6;");

        locationField = new TextField();
        locationField.setPromptText("Enter your location");

        educationField = new TextField();
        educationField.setPromptText("Enter your degree");

        workLocation = new TextField();
        workLocation.setPromptText("Enter where you work");

        positionField = new TextField();
        positionField.setPromptText("Enter you position at Work Location");

        Button next = new Button("Next");
        next.setStyle(
                "-fx-min-width: 100; -fx-background-color: #0a66c2; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 20; -fx-padding: 10 0;");
        next.setOnAction(e -> {
            createStep2(stage);
        });

        HBox nav = new HBox(next);
        nav.setAlignment(Pos.CENTER_RIGHT);

        HBox profilePicBox = new HBox(20, profilView, bioBox);

        step.getChildren().addAll(profilePicBox, ageField, genderComboBox, locationField, educationField, workLocation,
                positionField, nav);
        step.setPadding(new Insets(30));
        step.setMaxWidth(400);
        step.setPrefHeight(Region.USE_COMPUTED_SIZE);
        step.setMinHeight(Region.USE_COMPUTED_SIZE);
        step.setMaxHeight(Region.USE_COMPUTED_SIZE);

        step.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.35);"
                        + "-fx-background-radius: 20;"
                        + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0, 0, 4);");
        step.setAlignment(Pos.TOP_LEFT);

        VBox centerBox = new VBox(15);
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerBox.setPadding(new Insets(30));
        // centerBox.setStyle("-fx-background-color: #f3f2ef;");
        centerBox.getChildren().addAll(step);

        root.setCenter(centerBox);
    }

    private void createStep2(Stage stage) {

        Text title = new Text("Showcase Your Skills – You're Almost Ready to Connect.");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));

        final ObservableList<String> suggestedSkills = FXCollections.observableArrayList(
                "Engineering", "Project Management", "English", "Research Skills",
                "Marketing", "Training", "Communication", "Strategy", "Analytical Skills", "Finance");

        VBox step = createCard("Add Your Skills – Step 2 of 4");

        teachField = new TextField();
        teachField.setPromptText("Skills You Can Teach");

        teachField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && !teachField.getText().isEmpty()) {
                HBox tagBox = addSkillTag(teachField.getText());
                skillTagsPane.getChildren().add(tagBox);
                teachField.clear();
            }
        });

        // Default Suggested Skill Tags (click to add)
        FlowPane suggestedPane = new FlowPane(10, 10);
        suggestedPane.setPadding(new Insets(1));
        for (String skill : suggestedSkills) {
            Button skillBtn = new Button(skill);
            skillBtn.setStyle(
                    "-fx-background-color: transparent; -fx-border-color: #aaa; -fx-border-radius: 15; -fx-padding: 2;");
            skillBtn.setOnAction(event -> {
                HBox tagBox = addSkillTag(skill);
                skillTagsPane.getChildren().add(tagBox);
            });
            suggestedPane.getChildren().add(skillBtn);
        }

        learnField = new TextField();
        learnField.setPromptText("Skills You Want to Learn");

        learnField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER && !learnField.getText().isEmpty()) {
                HBox tagbox = addSkillTag(learnField.getText());
                skillsTagPane2.getChildren().add(tagbox);
                learnField.clear();
            }
        });

        Label tip = new Label("Tip: Choose at least one skill to teach and one to learn.");
        tip.setStyle("-fx-text-fill: gray; -fx-font-size: 10px;");

        Button back = new Button("Back");
        back.setStyle(
                "-fx-min-width: 100; -fx-background-color: #0a66c2; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25; -fx-padding: 10 0;");
        back.setOnAction(e -> {
            createStep1(stage);
        });

        Button next = new Button("Next");
        next.setStyle(
                "-fx-min-width: 100; -fx-background-color: #0a66c2; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25; -fx-padding: 10 0;");
        next.setOnAction(e -> {

            user.setSkillsToLearn(getSkillsFromPane(skillsTagPane2));
            user.setSkillsToTeach(getSkillsFromPane(skillTagsPane));
            createStep3();
        });

        HBox nav = new HBox(10, back, next);
        nav.setAlignment(Pos.CENTER_RIGHT);

        step.getChildren().addAll(teachField, suggestedPane, new Label("Your Skills : "), skillTagsPane, learnField,
                skillsTagPane2, tip, nav);
        step.setPadding(new Insets(30));
        step.setMaxWidth(400);
        step.setPrefHeight(Region.USE_COMPUTED_SIZE);
        step.setMinHeight(Region.USE_COMPUTED_SIZE);
        step.setMaxHeight(Region.USE_COMPUTED_SIZE);

        step.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.35);"
                        + "-fx-background-radius: 20;"
                        + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0, 0, 4);");
        step.setAlignment(Pos.TOP_LEFT);

        VBox centerBox = new VBox(15);
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerBox.setPadding(new Insets(30));
        // centerBox.setStyle("-fx-background-color: #f3f2ef;");
        centerBox.getChildren().addAll(title, step);

        root.setCenter(centerBox);

    }

    private HBox addSkillTag(String skill) {
        // Avoid duplicates
        // boolean exists = skillTagsPane.getChildren().stream()
        // .anyMatch(node -> ((Label) ((HBox)
        // node).getChildren().get(0)).getText().equalsIgnoreCase(skill));
        // if (exists) return;

        Label tag = new Label(skill);
        tag.setFont(Font.font("Arial", 10));
        tag.setPadding(new Insets(3));
        tag.setTextFill(Color.WHITE);
        tag.setStyle(
                "-fx-border-color: #999; -fx-background-color:rgb(45, 52, 181); -fx-background-radius: 10; -fx-border-radius: 10;");

        Button remove = new Button("x");
        remove.setOnAction(e -> skillTagsPane.getChildren().remove(tag.getParent()));
        remove.setStyle("-fx-font-size: 8; -fx-background-color: transparent; -fx-text-fill: red;");

        HBox tagBox = new HBox(tag, remove);
        tagBox.setAlignment(Pos.CENTER);
        tagBox.setSpacing(1);
        tagBox.setPadding(new Insets(2));
        tagBox.setStyle("-fx-background-radius: 15;");
        return tagBox;
    }

    private void createStep3() {

        Text title = new Text("You’re Almost There – Build a Profile That Speaks for You.");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));

        VBox step = createCard("Build Your Profile – Step 3 of 4");

        Label recommendedLabel = new Label("Recommended:");
        recommendedLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

        Label featuredLabel = new Label("Add Featured (Articles/Posts/Presentations):");
        Button uploadFeaturedBtn = new Button("Upload File");
        uploadFeaturedBtn.setOnAction(event -> {
            StorageController controller = new StorageController();
            String presentUrl = controller.uploadPresentation(primaryStage, nameField.getText());

            if (presentUrl != null) {
                System.out.println("Presentation uploaded");
                uploadedPresentation.add(presentUrl);
            }
        });

        Label linkedInLabel = new Label("LinkedIn Profile:");
        linkedInField = new TextField();
        linkedInField.setPromptText("https://linkedin.com/in/your-profile");

        Label portfolioLabel = new Label("Portfolio Link:");
        portfolioField = new TextField();
        portfolioField.setPromptText("portfolio link");

        Label certLabel = new Label("Certifications:");
        Button uploadCertBtn = new Button("Upload Certificate");
        uploadCertBtn.setOnAction(event -> {
            StorageController storageController = new StorageController();
            certUrl = storageController.uploadCertificate(primaryStage, nameField.getText());

            if (certUrl != null) {
                System.out.println("Certificate Uploaded : " + certUrl);
                uploadedCertificateUrls.add(certUrl);

            }
        });

        Label courseLabel = new Label("Courses:");
        TextArea courseArea = new TextArea();
        courseArea.setPromptText("Mention any completed courses...");
        courseArea.setPrefRowCount(2);

        Button back = new Button("Back");
        back.setStyle(
                "-fx-min-width: 100; -fx-background-color: #0a66c2; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25; -fx-padding: 10 0;");
        back.setOnAction(e -> createStep2(primaryStage));

        Button next = new Button("Next");
        next.setStyle(
                "-fx-min-width: 100; -fx-background-color: #0a66c2; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 25; -fx-padding: 10 0;");
        next.setOnAction(e -> createStep4());

        HBox nav = new HBox(10, back, next);
        nav.setAlignment(Pos.CENTER_RIGHT);

        // Final Layout
        step.getChildren().addAll(
                recommendedLabel,
                featuredLabel, uploadFeaturedBtn,
                linkedInLabel, linkedInField,
                portfolioLabel, portfolioField,
                certLabel, uploadCertBtn,
                courseLabel, courseArea,
                nav);

        step.setPadding(new Insets(30));
        step.setMaxWidth(400);
        step.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.35);"
                        + "-fx-background-radius: 20;"
                        + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0, 0, 4);");
         step.setAlignment(Pos.TOP_LEFT);

        VBox centerBox = new VBox(15);
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerBox.setPadding(new Insets(30));
        // centerBox.setStyle("-fx-background-color: #dfd6fa;");

        Image background2 = new Image("assets\\images\\bgfinal.png");

        BackgroundImage bg2 = new BackgroundImage(background2, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true));

        centerBox.setBackground(new Background(bg2));

        centerBox.getChildren().addAll(title, step);

        root.setCenter(centerBox);
    }

    private void createStep4() {

        Text title = new Text("Finish Up – Your Skill Journey Starts Here!");
        title.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));

        VBox step = createCard("Verify & Finish – Step 4 of 4");

        Button upload = new Button("Upload Intro Video");
        upload.setOnAction(event -> {
            StorageController storageController = new StorageController();
            videoUrl = storageController.uploadVideo(primaryStage);

            if (videoUrl != null) {
                System.out.println("Video Uploaded : " + videoUrl);
                user.setIntroVdoUrl(videoUrl);

            }
        });

        Text prefFormat = new Text("Preferred Format : ");
        prefFormat.setFont(Font.font("verdana", FontWeight.BOLD, 14));
        chat = new CheckBox("In-app Chat");
        video = new CheckBox("Video Call");
        fileShare = new CheckBox("File Sharing");

        HBox weekDays = new HBox(5);
        for (String day : new String[] { "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" }) {
            Button btn = new Button(day);
            btn.setStyle("-fx-background-color: #eee;");
            weekDays.getChildren().add(btn);
        }

        Text prefTeach = new Text("Select Preferred Skill to Teach");
        prefTeach.setFont(Font.font("verdana", FontWeight.BOLD, 14));
        ComboBox<String> skillSelection = new ComboBox<>();
        skillSelection.getItems().addAll(user.getSkillsToTeach());
        skillSelection.setPromptText("Select Skill to Highlight...");

        // --- Skill level selection ---
        Text skillLabel = new Text("Skill Level:");
        skillLabel.setFont(Font.font("verdana", FontWeight.BOLD, 14));
        skillLevel = new ComboBox<>();
        skillLevel.getItems().addAll("Moderate", "Intermediate", "Expert");
        skillLevel.setPromptText("Select your skill level");

        // --- Test button and result display ---
        Button takeTest = new Button("Take Skill Test");
        Label testResult = new Label();
        HBox starRating = new HBox(5);
        starRating.setAlignment(Pos.CENTER_LEFT);

        takeTest.setOnAction(e -> {
            String level = skillLevel.getValue();
            if (level == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select your skill level.");
                alert.show();
                return;
            }

            String category = skillSelection.getValue();
            String skilllevel = skillLevel.getValue();

            String quizJson = QuizGenerator.generateQuiz(category, skilllevel);
            JSONArray quizArray = new JSONArray(quizJson);
            List<JSONObject> quizQuestions = new ArrayList<>();
            List<ToggleGroup> answerGroups = new ArrayList<>();

            for (int i = 0; i < quizArray.length(); i++) {
                quizQuestions.add(quizArray.getJSONObject(i));
            }

            VBox quizPane = new VBox(15);
            quizPane.setPadding(new Insets(20));
            quizPane.setPrefWidth(600);

            Label title1 = new Label("Skill Test: " + category);
            title1.setFont(Font.font("Arial", FontWeight.BOLD, 18));
            quizPane.getChildren().add(title1);

            for (JSONObject q : quizQuestions) {
                VBox questionBox = new VBox(5);
                Label questionLabel = new Label(q.getString("question"));
                questionLabel.setStyle("-fx-font-weight: bold;");
                ToggleGroup group = new ToggleGroup();
                answerGroups.add(group);

                JSONArray options = q.getJSONArray("options");
                for (int i = 0; i < options.length(); i++) {
                    RadioButton rb = new RadioButton(options.getString(i));
                    rb.setToggleGroup(group);
                    questionBox.getChildren().add(rb);
                }

                questionBox.getChildren().add(0, questionLabel);
                quizPane.getChildren().add(questionBox);
            }

            Button submitBtn = new Button("Submit Quiz");
            Label quizResult = new Label();
            quizPane.getChildren().addAll(submitBtn, quizResult);

            Stage quizStage = new Stage();
            quizStage.setTitle("Take Skill Quiz");
            quizStage.setScene(new Scene(new ScrollPane(quizPane), 650, 500));
            quizStage.show();

            submitBtn.setOnAction(ev -> {
                score = 0;
                for (int i = 0; i < quizQuestions.size(); i++) {
                    Toggle selected = answerGroups.get(i).getSelectedToggle();
                    if (selected != null) {
                        String selectedAnswer = ((RadioButton) selected).getText();
                        String correct = quizQuestions.get(i).getString("answer");
                        if (selectedAnswer.equalsIgnoreCase(correct)) {
                            score++;
                        }
                    }
                }

                int total = quizQuestions.size();
                double percent = (double) score / total;
                stars = percent >= 0.9 ? 5 : percent >= 0.7 ? 4 : percent >= 0.5 ? 3 : 2;
                credits = stars * 10;

                // Update stars and credit label on original pane
                starRating.getChildren().clear();
                for (int i = 0; i < 5; i++) {
                    Label star = new Label(i < stars ? "★" : "☆");
                    star.setStyle("-fx-font-size: 18px; -fx-text-fill: gold;");
                    starRating.getChildren().add(star);
                }

                testResult
                        .setText("✅ You scored " + score + "/" + total + ". You earned " + credits + " credit points.");
                testResult.setFont(Font.font("verdana", FontWeight.NORMAL, 13));
                testResult.setTextFill(Color.GREEN);

                quizStage.close();
            });
        });

        CheckBox agreement = new CheckBox("I will respect others' time and skills.");

        Button back = new Button("Back");
        back.setStyle(
                "-fx-min-width: 100; -fx-background-color: #0a66c2; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 0;");
        back.setOnAction(e -> {
            createStep3();
        });

        Button submit = new Button("Submit");
        submit.setStyle(
                "-fx-min-width : 100; -fx-background-color: #0a66c2; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 0;");
        submit.setOnAction(e -> {

            List<String> prefList = new ArrayList<>();
            if (chat.isSelected())
                prefList.add(chat.getText());
            if (video.isSelected()) {
                prefList.add(video.getText());
            }
            if (fileShare.isSelected())
                prefList.add(fileShare.getText());

            User user = new User();
            user.setIntroVdoUrl(videoUrl);
            if (!uploadedCertificateUrls.isEmpty()) {
                user.setCertificateUrl(uploadedCertificateUrls);
            }
            if (!uploadedPresentation.isEmpty()) {
                user.setPresenatationUrl(uploadedPresentation);
            }
            user.setProfileImg(imageUrl);
            user.setEmail(emailField.getText());
            user.setPassword(passwordField.getText());
            user.setAge(Integer.parseInt(ageField.getText()));
            user.setBio(bioField.getText());
            user.setFirstName(nameField.getText());
            user.setLastName(surnamField.getText());
            user.setPhone(numberField.getText());
            user.setEducation(educationField.getText());
            user.setLocation(locationField.getText());
            user.setGender(genderComboBox.getValue());
            user.setLinkedIn(linkedInField.getText());
            user.setPortfolio(portfolioField.getText());
            user.setPosition(positionField.getText());
            user.setSkillLevel(skillLevel.getValue());
            user.setPrefFormat(prefList);
            user.setSkillsToLearn(getSkillsFromPane(skillsTagPane2));
            user.setSkillsToTeach(getSkillsFromPane(skillTagsPane));
            user.setWorkLocation(workLocation.getText());
            user.setPrefToTeach(skillSelection.getValue());
            user.setCp(credits);
            user.setTotalCp(100 + credits);
            user.setTestScore(score);
            user.setStarCount(stars);

            RegistrationController controller = new RegistrationController();
            controller.registerUser(user);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Submitted!");
            alert.setOnHidden(event -> {
                initializeLoginPage();
                primaryStage.setScene(loginScene);

            });
            alert.show();
        });

        HBox nav = new HBox(10, back, submit);
        nav.setAlignment(Pos.CENTER_RIGHT);

        // Add all elements to step
        step.getChildren().addAll(
                upload,
                prefFormat, chat, video, fileShare,
                weekDays,
                prefTeach,
                skillSelection,
                skillLabel,
                skillLevel,
                takeTest,
                starRating,
                testResult,
                agreement,
                nav);

        step.setPadding(new Insets(30));
        step.setMaxWidth(400);
        step.setPrefHeight(Region.USE_COMPUTED_SIZE);
        step.setMinHeight(Region.USE_COMPUTED_SIZE);
        step.setMaxHeight(Region.USE_COMPUTED_SIZE);

        step.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.35);"
                        + "-fx-background-radius: 20;"
                        + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10, 0, 0, 4);");
        step.setAlignment(Pos.TOP_LEFT);

        VBox centerBox = new VBox(15);
        centerBox.setAlignment(Pos.TOP_CENTER);
        centerBox.setPadding(new Insets(30));
        // centerBox.setStyle("-fx-background-color: #f3f2ef;");
        centerBox.getChildren().addAll(title, step);

        root.setCenter(centerBox);
    }

    private void initializeLoginPage() {
        LoginPage login = new LoginPage();
        login.setLoginStage(primaryStage);
        loginScene = new Scene(login.createLoginScene(this::handleBackbtnfromLogin), 1200, 800);
    }

    public void handleBackbtnfromLogin() {
        primaryStage.setScene(landingPagScene);
    }

    private List<String> getSkillsFromPane(FlowPane pane) {
        List<String> skills = new ArrayList<>();

        for (Node node : pane.getChildren()) {
            HBox skillBox = (HBox) node; // Assume it's always an HBox
            Label skillLabel = (Label) skillBox.getChildren().get(0); // First child is a Label
            skills.add(skillLabel.getText());
        }
        return skills;
    }

}
