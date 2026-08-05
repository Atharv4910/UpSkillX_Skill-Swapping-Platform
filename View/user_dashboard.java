package com.upskillx.View;

import com.upskillx.Controller.DashboardController;
import com.upskillx.Controller.MeetingController;
import com.upskillx.dao.UserDAO;
import com.upskillx.model.User;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class user_dashboard {

        String email;
        Scene blogs2Scene;
        UserDAO userDAO = new UserDAO();
        User user;
        VBox connectionVBox;
        int creditPoints;
        public user_dashboard() {

        }

        public void setUser(User user) {
                this.user = user;
        }

        Stage userDashboardStage;
        Scene userDashboardScene;

        public void setUserDashboardStage(Stage userDashboardStage) {
                this.userDashboardStage = userDashboardStage;
        }

        public void setUserDashboardScene(Scene userDashboardScene) {
                this.userDashboardScene = userDashboardScene;
        }

        public StackPane createUserDashboardScene() {

                // if(user == null) {
                // System.out.println("User is null");
                // return null;
                // }

                Image logo = new Image("assets/images/logo.png");
                ImageView logoView = new ImageView(logo);
                logoView.setFitWidth(130);
                logoView.setFitHeight(62);

                Region spwdwTologo = new Region();
                spwdwTologo.setPrefHeight(5);

                String imageUrl = user.getProfileImg(); // Firebase Storage image URL
                Circle circlePic = new Circle(65);
                circlePic.setStyle("-fx-stroke: white;");

                if (imageUrl != null && !imageUrl.isEmpty()) {
                        Image profileImg = new Image(imageUrl, true);

                        profileImg.progressProperty().addListener((obs, oldVal, newVal) -> {
                                if (newVal.doubleValue() >= 1.0) {
                                        Platform.runLater(() -> {
                                                circlePic.setFill(new ImagePattern(profileImg));
                                        });
                                }
                        });

                        profileImg.errorProperty().addListener((obs, oldVal, newVal) -> {
                                if (newVal) {
                                        System.out.println("Error loading profile image.");
                                }
                        });
                } else {
                        System.out.println("Image URL is null or empty.");
                }

                circlePic.setStyle("-fx-stroke: white;");

                Text username = new Text(user.getFirstName() + "  " + user.getLastName());
                username.setStyle("-fx-font-size:20;-fx-font-weight:BOLD");

                Image homeIcon = new Image("assets/images/home_dashboard.png");
                ImageView homeIconView = new ImageView(homeIcon);
                Button dashboardButton = new Button("  Dashboard", homeIconView);
                dashboardButton.setStyle(
                                "-fx-min-width:210px;-fx-min-height:27px;-fx-font-weight:BOLD;-fx-text-fill:rgba(0, 0, 0, 1);-fx-background-color:White;-fx-border-color:WHITE;-fx-border-radius:15;");
                dashboardButton.setAlignment(Pos.TOP_LEFT);

                Text let_exp = new Text("Let's Explore");
                let_exp.setStyle("-fx-font-size:14;-fx-font-weight:BOLD;");

                Image findMImg = new Image("assets/images/FindMentor.png");
                ImageView findMIcon = new ImageView(findMImg);
                findMIcon.setFitWidth(24);
                findMIcon.setFitHeight(24);

                Button findMentorBtn = new Button("   Find Mentor", findMIcon);
                findMentorBtn.setStyle(
                                "-fx-font-weight:BOLD;-fx-text-fill:rgb(0, 0, 0);-fx-background-color:transparent;-fx-border-color:none;");
                findMentorBtn.setAlignment(Pos.CENTER_LEFT);
                findMentorBtn.setMinWidth(180);
                findMentorBtn.setFont(new Font(15));
                findMentorBtn.setOnMouseEntered(e -> findMentorBtn
                                .setStyle("-fx-background-color: linear-gradient(to right, #dab6f5,rgb(177, 191, 234));"
                                                +
                                                "-fx-text-fill: black;" +
                                                "-fx-padding: 10 30;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-background-radius: 30;"));
                findMentorBtn.setOnMouseExited(e -> {
                        findMentorBtn.setStyle(
                                        "-fx-font-weight:BOLD;-fx-text-fill:rgb(2, 2, 2);-fx-background-color:transparent;-fx-border-color:none;");
                });
                findMentorBtn.setOnAction(event -> {
                        FindMentor findMentor = new FindMentor(user);
                        try {
                                findMentor.start(userDashboardStage); // Switch scene
                        } catch (Exception ex) {
                                ex.printStackTrace();
                        }
                });

                Image communityImg = new Image("assets/images/communityIcon.png");
                ImageView communityIcon = new ImageView(communityImg);
                communityIcon.setFitWidth(24);
                communityIcon.setFitHeight(24);
                Button communityBtn = new Button("   Blogs", communityIcon);
                communityBtn.setStyle(
                                "-fx-font-weight:BOLD;-fx-text-fill:rgb(0, 0, 0);-fx-background-color:transparent;-fx-border-color:none;");
                communityBtn.setAlignment(Pos.CENTER_LEFT);
                communityBtn.setFont(new Font(15));
                communityBtn.setMinWidth(180);
                communityBtn.setOnMouseEntered(e -> communityBtn
                                .setStyle("-fx-background-color: linear-gradient(to right, #dab6f5,rgb(177, 191, 234));"
                                                +
                                                "-fx-text-fill: black;" +
                                                "-fx-padding: 10 30;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-background-radius: 30;"));
                communityBtn.setOnMouseExited(e -> {
                        communityBtn.setStyle(
                                        "-fx-font-weight:BOLD;-fx-text-fill:rgb(0, 0, 0);-fx-background-color:transparent;-fx-border-color:none;");
                });
                communityBtn.setOnAction(event -> {
                        initializeBlog();
                        userDashboardStage.setScene(blogs2Scene);
                        
                });

                Image shortsImg = new Image("assets/images/reelsIcon.png");
                ImageView shortsIcon = new ImageView(shortsImg);
                shortsIcon.setFitWidth(24);
                shortsIcon.setFitHeight(24);
                Button shortsBtn = new Button("   Feed", shortsIcon);
                shortsBtn.setMinWidth(180);
                shortsBtn.setFont(new Font(15));
                shortsBtn.setStyle(
                                "-fx-font-weight:BOLD;-fx-text-fill:rgb(0, 0, 0);-fx-background-color:transparent;-fx-border-color:none;");
                shortsBtn.setAlignment(Pos.CENTER_LEFT);
                shortsBtn.setOnMouseEntered(e -> shortsBtn
                                .setStyle("-fx-background-color: linear-gradient(to right, #dab6f5,rgb(177, 191, 234));"
                                                +
                                                "-fx-text-fill: black;" +
                                                "-fx-padding: 10 30;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-background-radius: 30;"));
                shortsBtn.setOnMouseExited(e -> {
                        shortsBtn.setStyle(
                                        "-fx-font-weight:BOLD;-fx-text-fill:rgb(0, 0, 0);-fx-background-color:transparent;-fx-border-color:none;");
                });
                shortsBtn.setOnAction(event -> {
                        Feed2 feed = new Feed2(user);
                        try {
                                feed.start(userDashboardStage);
                        } catch (Exception e1) {
                                e1.printStackTrace();
                        } 
                });

                VBox exploreTabs = new VBox(20, let_exp, findMentorBtn, communityBtn, shortsBtn);
                exploreTabs.setPadding(new Insets(0, 0, 0, 40));

                Text acc_set = new Text("Account Settings");
                acc_set.setStyle("-fx-font-size:14;-fx-font-weight:BOLD;");

                Image perInfoImg = new Image("assets/images/personal Info.png");
                ImageView perInfoIcon = new ImageView(perInfoImg);
                perInfoIcon.setFitWidth(24);
                perInfoIcon.setFitHeight(24);
                Button perInfoBtn = new Button("   Personal Info", perInfoIcon);
                perInfoBtn.setMinWidth(180);
                perInfoBtn.setFont(new Font(15));
                perInfoBtn.setStyle(
                                "-fx-font-weight:BOLD;-fx-text-fill:rgb(0, 0, 0);-fx-background-color:transparent;-fx-border-color:none;");
                perInfoBtn.setAlignment(Pos.CENTER_LEFT);
                perInfoBtn.setOnMouseEntered(e -> perInfoBtn
                                .setStyle("-fx-background-color: linear-gradient(to right, #dab6f5,rgb(177, 191, 234));"
                                                +
                                                "-fx-text-fill: black;" +
                                                "-fx-padding: 10 30;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-background-radius: 30;"));
                perInfoBtn.setOnMouseExited(e -> {
                        perInfoBtn.setStyle(
                                        "-fx-font-weight:BOLD;-fx-text-fill:rgb(0, 0, 0);-fx-background-color:transparent;-fx-border-color:none;");
                });

                Image logoutImg = new Image("assets/images/logout.png");
                ImageView logoutIcon = new ImageView(logoutImg);
                logoutIcon.setFitHeight(24);
                logoutIcon.setFitWidth(24);

                Button logout = new Button("   Logout", logoutIcon);
                logout.setMinWidth(180);
                logout.setAlignment(Pos.CENTER_LEFT);
                logout.setFont(new Font(15));
                logout.setOnMouseEntered(e -> logout
                                .setStyle("-fx-background-color: linear-gradient(to right, #dab6f5,rgb(177, 191, 234));"
                                                +
                                                "-fx-text-fill: black;" +
                                                "-fx-padding: 10 30;" +
                                                "-fx-font-weight: bold;" +
                                                "-fx-background-radius: 30;"));
                logout.setOnMouseExited(e -> {
                        logout.setStyle("-fx-font-weight:BOLD;-fx-text-fill:rgb(0, 0, 0);-fx-background-color:transparent;-fx-border-color:none;");
                });
                logout.setStyle(
                                "-fx-font-weight:BOLD;-fx-text-fill:rgb(0, 0, 0);-fx-background-color:transparent;-fx-border-color:none;");
                perInfoBtn.setAlignment(Pos.CENTER_LEFT);
                logout.setOnAction(event -> {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to logout?",
                                        ButtonType.YES, ButtonType.NO);
                        alert.setHeaderText(null);
                        alert.showAndWait().ifPresent(response -> {
                                if (response == ButtonType.YES) {
                                        // Return to landing page
                                        landing_page landingPage = new landing_page();
                                        try {
                                                landingPage.start(userDashboardStage); // Use the same stage
                                        } catch (Exception e) {
                                                e.printStackTrace();
                                        }
                                }
                        });
                });

                // HBox logoutBar = new HBox(logoutLabel);
                // logoutBar.setAlignment(Pos.CENTER_LEFT);
                // logoutBar.setPadding(new Insets(10, 40, 0, 0));

                VBox account_setTabs = new VBox(20, acc_set, perInfoBtn, logout);
                account_setTabs.setPadding(new Insets(0, 0, 0, 40));

                Region spsrtToact = new Region();
                spsrtToact.setPrefHeight(20);

                VBox buttonsBox = new VBox(20, spwdwTologo, logoView, circlePic, username, dashboardButton, exploreTabs,
                                spsrtToact, account_setTabs);
                buttonsBox.setAlignment(Pos.TOP_CENTER);

                buttonsBox.setPrefWidth(245);
                buttonsBox.setMinWidth(245);
                buttonsBox.setMaxWidth(245);
                // Background gradient
                Stop[] stops = new Stop[] {
                                new Stop(0.5, Color.web("#d6bbf9ff")),
                                new Stop(1, Color.web("#cad0ecff"))
                };
                LinearGradient gradient = new LinearGradient(
                                0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
                BackgroundFill gradientFill = new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY);
                buttonsBox.setBackground(new Background(gradientFill));

                Text dashBoard_text = new Text("Dashboard");
                dashBoard_text.setStyle("-fx-font-size:20; -fx-font-weight:BOLD;");
                creditPoints = user.getTotalCp();
                Text points = new Text(String.valueOf(creditPoints));
                points.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));

                Image cpimage = new Image("assets/images/cpimage.png");
                ImageView cpimageview = new ImageView(cpimage);
                cpimageview.setFitWidth(33); // Slightly smaller for neat top bar
                cpimageview.setPreserveRatio(true);

                HBox cpbox = new HBox(5, points, cpimageview); // 5px spacing between text and icon
                cpbox.setPadding(new Insets(0, 12, 6, 10)); 
                cpbox.setStyle("""
                                    -fx-border-color: #333;
                                    -fx-border-width: 2; /* Thicker border */
                                    -fx-border-radius: 10;
                                    -fx-background-radius: 10;
                                    -fx-background-color: white;
                                    -fx-effect: dropshadow(one-pass-box, rgba(0,0,0,0.1), 3, 0.0, 0, 1);
                                """);
                VBox.setMargin(cpbox, new Insets(10, 0, 0, 0)); // Add margin from top

                cpbox.setAlignment(Pos.CENTER); // center contents vertically
                // top, right, bottom, left

                Image notifications = new Image("assets/icons/notifications.png");
                ImageView notview = new ImageView(notifications);
                notview.setFitWidth(33);
                notview.setPreserveRatio(true);

                // Spacer to push right items to the end
                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                // Wrap right elements in an HBox for spacing
                HBox rightBoxtop = new HBox(10, cpbox, notview);
                rightBoxtop.setAlignment(Pos.CENTER_RIGHT);

                // Main Top HBox
                HBox dashtop = new HBox();
                dashtop.setPadding(new Insets(30, 20, 0, 20)); // Top, Right, Bottom, Left
                dashtop.setAlignment(Pos.CENTER_LEFT);
                dashtop.getChildren().addAll(dashBoard_text, spacer, rightBoxtop);

                Line line = new Line();
                line.setStyle("-fx-stroke: rgba(177, 175, 175, 1);");

                // Create welcome text
                Text welcomeText = new Text("Welcome Back, ");
                welcomeText.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-fill: #000000;");

                Text usernameText = new Text(user.getFirstName());
                usernameText.setStyle("-fx-font-size: 25px; -fx-font-weight: bold; -fx-fill: #f74c4cff;");

                Text subtitle = new Text(
                                "Manage everything from here.\nYour skills, connections, and updates — all in one place.");
                subtitle.setStyle("-fx-font-size: 15px; -fx-fill: #4b4b4b;");

                // Horizontal layout for welcome + name
                HBox titleRow = new HBox(welcomeText, usernameText);
                titleRow.setSpacing(4);

                // VBox for text content
                VBox textBox = new VBox(10, titleRow, subtitle);
                textBox.setAlignment(Pos.CENTER_LEFT);

                // Load image on right side
                Image userImage = new Image("assets/images/wlcUser.png"); // or use uploaded one
                ImageView userImageView = new ImageView(userImage);
                userImageView.setFitWidth(290);
                userImageView.setPreserveRatio(true);

                // HBox to combine text and image
                HBox contentRow = new HBox();
                contentRow.setAlignment(Pos.CENTER_LEFT);
                contentRow.setPadding(new Insets(20, 160, 20, 20));
                contentRow.setSpacing(Region.USE_COMPUTED_SIZE); // optional

                // Add spacer between text and image
                Region spacer2 = new Region();
                HBox.setHgrow(spacer2, Priority.ALWAYS);

                contentRow.getChildren().addAll(textBox, spacer2, userImageView);

                // Rectangle background container
                Rectangle rectangle = new Rectangle();
                rectangle.setHeight(220); // Adjust as needed
                rectangle.setArcWidth(30);
                rectangle.setArcHeight(30);
                rectangle.setFill(gradient); // Light blue background

                // StackPane to hold background and content
                StackPane rectangleWithText = new StackPane(rectangle, contentRow);
                rectangleWithText.setAlignment(Pos.TOP_LEFT);

                Text sessionHsty_text = new Text("Ongoing Courses");
                sessionHsty_text.setStyle("-fx-font-size:16;-fx-font-weight:BOLD;");

                VBox sessionVBox = new VBox(10, sessionHsty_text);
                sessionVBox.setPrefHeight(460);
                sessionVBox.setPrefWidth(750);

                MeetingController controller = new MeetingController(sessionVBox);

                VBox connectionVBox = new VBox(15);
                connectionVBox.setPrefHeight(460);
                connectionVBox.setMinHeight(300);
                connectionVBox.setPrefWidth(120);
                connectionVBox.setStyle(
                                "-fx-border-radius: black; -fx-background-radius: none;");

                HBox.setHgrow(sessionVBox, Priority.ALWAYS);
                HBox.setHgrow(connectionVBox, Priority.ALWAYS);

                sessionVBox.setAlignment(Pos.TOP_LEFT);
                connectionVBox.setAlignment(Pos.TOP_LEFT);

                DashboardController dashcontroller = new DashboardController(connectionVBox);
                connectionVBox = dashcontroller.initialize();

                // ScrollPane connectionScrollPane = new ScrollPane(connectionVBox);
                // connectionScrollPane.setFitToWidth(true);
                // connectionScrollPane.setFitToHeight(true); // Optional: allows VBox height
                // expansion
                // connectionScrollPane.setStyle("-fx-background:transparent;
                // -fx-background-color:transparent;");

                // connectionScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                // connectionScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                // connectionScrollPane.setPannable(true);

                connectionVBox.setStyle(
                                "-fx-border-color : black, -fx-font-weight: 4; -fx-border-style: solid; -fx-background-color: white");
                connectionVBox.setPadding(new Insets(10));
                ScrollPane sp = new ScrollPane(connectionVBox);
                sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                

                Text tx = new Text("Connection Requests");
                tx.setFill(Color.rgb(9, 130, 222));
                tx.setFont(Font.font("", FontWeight.BOLD, 14));
                VBox connRequestBox = new VBox(10, tx, sp);
                connRequestBox.setStyle(
                                "-fx-border-color: black; -fx-border-weight: 4; -fx-border-style: solid; -fx-stroke: white;");
                connRequestBox.setBackground(new Background(gradientFill));
                connRequestBox.setMinHeight(300);
                connRequestBox.setPadding(new Insets(10));

                HBox sessionHtryAndAcceptOrReject = new HBox(20, sessionVBox, connRequestBox);
                controller.loadOngoingSessions();
                sessionHtryAndAcceptOrReject.setPadding(new Insets(0, 26, 0, 0));

                sessionVBox.prefWidthProperty().bind(sessionHtryAndAcceptOrReject.widthProperty().multiply(0.65));
                connectionVBox.prefWidthProperty().bind(sessionHtryAndAcceptOrReject.widthProperty().multiply(0.35));

                VBox btnClickedContent = new VBox(15, dashtop, line, rectangleWithText,
                                sessionHtryAndAcceptOrReject);
                btnClickedContent.setPadding(new Insets(5, 0, 0, 26));

                btnClickedContent.setStyle("-fx-background-color:WHITE");
                line.endXProperty().bind(btnClickedContent.widthProperty().subtract(55));
                rectangle.widthProperty().bind(btnClickedContent.widthProperty().subtract(55));

                MyProfile profile = new MyProfile();
                HBox Personalinfo = profile.personalInfo();

                VBox contentPlaceholder = new VBox(); // This will hold the current view
                contentPlaceholder.getChildren().add(btnClickedContent);
                HBox.setHgrow(contentPlaceholder, Priority.ALWAYS);

                dashboardButton.setOnAction(e -> {
                        contentPlaceholder.getChildren().clear();
                        contentPlaceholder.getChildren().add(btnClickedContent);
                        HBox.setHgrow(btnClickedContent, Priority.ALWAYS);
                });

                perInfoBtn.setOnAction(e -> {
                        contentPlaceholder.getChildren().clear();
                        contentPlaceholder.getChildren().add(Personalinfo);
                        HBox.setHgrow(Personalinfo, Priority.ALWAYS);
                });

                HBox container = new HBox(buttonsBox, contentPlaceholder);
                container.setStyle("-fx-border-redius:30");
                HBox.setHgrow(btnClickedContent, Priority.ALWAYS);

                StackPane stackpane = new StackPane(container);
                return stackpane;

        }

        // public void initialize() {
        // loadConnectionRequests();
        // connectionVBox.setPrefHeight(460);
        // connectionVBox.setPrefWidth(120);
        // connectionVBox.setStyle(
        // "-fx-border-radius: none; -fx-background-radius: none; -fx-background-color:
        // transparent;");
        // }

        // private void loadConnectionRequests() {
        // userDAO.getIncomingRequests(LoginPage.currentUser, incomingUsers -> {
        // connectionVBox.getChildren().clear();

        // for (User sender : incomingUsers) {
        // HBox requestCard = createRequestItem(sender);
        // connectionVBox.getChildren().add(requestCard);
        // }
        // });
        // }

        // private HBox createRequestItem(User requester) {
        // Text name = new Text(requester.getFirstName() + " " +
        // requester.getLastName());
        // name.setStyle("-fx-font-size:17px");

        // Text skill = new Text(requester.getPosition()); // Or any field like skill
        // skill.setStyle("-fx-font-size:12px");

        // VBox userInfo = new VBox(5, name, skill);
        // userInfo.setStyle("-fx-min-width:150");

        // // // Image (optional static or from field)
        // // Circle profileImg = new Circle(25);
        // // Image image = new Image("assets/images/profile1.png"); // Or dynamic if
        // you store URL
        // // profileImg.setFill(new ImagePattern(image));

        // // VBox profileContainer = new VBox(profileImg);

        // Button acceptBtn = new Button("", new ImageView(new
        // Image("assets/images/accept_btn.png")));
        // Button rejectBtn = new Button("", new ImageView(new
        // Image("assets/images/reject_btn.png")));
        // acceptBtn.setStyle("-fx-background-color:WHITE");
        // rejectBtn.setStyle("-fx-background-color:WHITE");

        // acceptBtn.setOnAction(e -> {
        // userDAO.updateRequestStatus(LoginPage.currentUser, requester.getEmail(),
        // "accepted",
        // this::loadConnectionRequests);

        // });

        // rejectBtn.setOnAction(e -> {
        // userDAO.updateRequestStatus(LoginPage.currentUser, requester.getEmail(),
        // "rejected",
        // this::loadConnectionRequests);

        // });

        // Region spacer = new Region();
        // HBox.setHgrow(spacer, Priority.ALWAYS);

        // HBox row = new HBox(10, userInfo, spacer, acceptBtn, rejectBtn);
        // row.setAlignment(Pos.CENTER_LEFT);
        // row.setPadding(new Insets(8));
        // row.setStyle("-fx-background-color: #f5f5f5; -fx-background-radius: 6;");

        // return row;
        // }

        private void initializeBlog() {
                
                Blogs2 blogs2 = new Blogs2(user);
                blogs2.setBlogStage(userDashboardStage);
                blogs2Scene = new Scene(blogs2.createBlogScene(),1200,800);

        }

        private HBox createInfoRow(String label, String value) {
                Label fieldLabel = new Label(label);
                fieldLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 15));
                fieldLabel.setTextFill(Color.web("#4B0082"));
                fieldLabel.setMinWidth(160);

                Label fieldValue = new Label(value);
                fieldValue.setFont(Font.font("Segoe UI", 15));
                fieldValue.setTextFill(Color.web("#333"));

                HBox row = new HBox(10, fieldLabel, fieldValue);
                row.setAlignment(Pos.CENTER_LEFT);
                return row;
        }

        private VBox createSessionCard(String daysLeft, String dateTime, String title) {
                Label daysLeftLabel = new Label(daysLeft);
                daysLeftLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

                Label dateTimeLabel = new Label(dateTime);
                dateTimeLabel.setStyle("-fx-text-fill: #007bff; -fx-font-size: 11px;");

                Label titleLabel = new Label(title);
                titleLabel.setWrapText(true);
                titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

                Label sessionType = new Label("🖥️ Online Session");
                sessionType.setStyle("-fx-font-size: 11px; -fx-text-fill: gray;");

                Button attendBtn = new Button("Attend");
                attendBtn.setStyle("-fx-background-color: white; -fx-border-color: gray; -fx-padding: 4 12;");

                VBox card = new VBox(5, daysLeftLabel, dateTimeLabel, titleLabel, sessionType, attendBtn);
                card.setPadding(new Insets(12));
                card.setStyle(
                                "-fx-border-color: #ddd; -fx-border-radius: 8; -fx-background-color: white; -fx-background-radius: 8;");
                card.setPrefSize(220, 220); // Width x Height
                card.setMaxSize(200, 180);
                card.setMinSize(200, 180);
                return card;
        }

}