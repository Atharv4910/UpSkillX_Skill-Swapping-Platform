package com.upskillx.View;

import java.awt.Desktop;
import java.net.URI;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.upskillx.Controller.MeetingController;
import com.upskillx.dao.ChatDAO;
import com.upskillx.model.User;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ChatRoom {

    String receiver;
    Stage chatStage;

    public static String generateChatRoomId(String user1, String user2) {
        return user1.compareTo(user2) < 0 ? user1 + "_" + user2 : user2 + "_" + user1;
    }

    private void listenForMeetingLink(String chatroomId, Button joinMeetingBtn) {
        DatabaseReference meetingRef = FirebaseDatabase.getInstance()
                .getReference("chatrooms")
                .child(chatroomId)
                .child("meetings");

        meetingRef.limitToLast(1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String prev) {
                String meetLink = snapshot.child("meetLink").getValue(String.class);
                if (meetLink != null && !meetLink.isEmpty()) {
                    Platform.runLater(() -> {
                        joinMeetingBtn.setVisible(true);
                        joinMeetingBtn.setOnAction(e -> {
                            try {
                                Desktop.getDesktop().browse(new URI(meetLink));
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        });
                    });
                }
            }

            @Override
            public void onChildChanged(DataSnapshot s, String p) {
            }

            @Override
            public void onChildRemoved(DataSnapshot s) {
            }

            @Override
            public void onChildMoved(DataSnapshot s, String p) {
            }

            @Override
            public void onCancelled(DatabaseError e) {
            }
        });
    }

    User mentor, user;

    public void setMentor(User mentor) {
        this.mentor = mentor;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMyStage(Stage stage) {
        this.chatStage = stage;
    }

    ChatDAO chatDAO;
    MeetingController meetingController;

    public Scene getScene(String reciverEmail) {

        this.receiver = reciverEmail;

        String chat_key = generateChatRoomId(LoginPage.currentUser, receiver);

        chatDAO = new ChatDAO(chat_key.replace("@", "-").replace(".", "_"));

        meetingController = new MeetingController(chatDAO);

        BorderPane chatRoom = new BorderPane();

        String image = mentor.getProfileImg() != null ? mentor.getProfileImg() : "assets/icons/profile.png";
        String userImage = user.getProfileImg() != null ? user.getProfileImg() : "assets/icons/profile.png";
        Image img1 = new Image(userImage);
        ImageView imageView1 = new ImageView(img1);
        imageView1.setFitHeight(100);
        imageView1.setFitWidth(100);

        Image img2 = new Image(image);
        ImageView imageView2 = new ImageView(img2);
        imageView2.setFitHeight(100);
        imageView2.setFitWidth(100);

        Button meeting = new Button("📅 Schedule Meeting");
        meeting.setOnAction(event -> {
            meetingController.openMeetingSchedular(mentor.getFirstName(),user,mentor);
        });
        meeting.setTextFill(Color.WHITE);
        meeting.setStyle(
                "-fx-font-size: 13px; -fx-padding: 10px 20px; -fx-pref-height: 30px; -fx-pref-width: 200px; -fx-background-color : blue");

        meeting.setOnMouseEntered(e -> meeting.setStyle("-fx-background-color: #aad7e6ff"));
        meeting.setOnMouseExited(e -> meeting.setStyle("-fx-backgroun-color:#5A4FF3"));

        meeting.setOnMouseEntered(e -> meeting.setStyle("-fx-background-color: #b7e7d1ff"));
        meeting.setOnMouseExited(e -> meeting.setStyle("-fx-background-color:#44B78B"));

        Button notes = new Button("📝 Request Notes");
        notes.setTextFill(Color.WHITE);
        notes.setStyle(
                "-fx-font-size: 13px; -fx-padding: 10px 20px; -fx-pref-height: 30px; -fx-pref-width: 200px; -fx-background-color : green");

        Button profile = new Button("View Profile");
        profile.setOnAction(event -> {
            MentorProfile mentorProfile = new MentorProfile();
            mentorProfile.setMentor(user);
            mentorProfile.setUser(mentor);
            mentorProfile.setMyStage(chatStage);
            chatStage.setScene(mentorProfile.getScene());

        });
        Button profile1 = new Button("View Profile");
        profile1.setOnAction(event -> {
            MentorProfile mentorProfile = new MentorProfile();
            mentorProfile.setMentor(mentor);
            mentorProfile.setUser(user);
            mentorProfile.setMyStage(chatStage);
            chatStage.setScene(mentorProfile.getScene());
        });

        TextField msg = new TextField();
        Text name1 = new Text(user.getFirstName() + " " + user.getLastName());
        Text name2 = new Text(mentor.getFirstName() + " " + mentor.getLastName());

        VBox leftBox = new VBox(20, imageView1, name1, profile);
        leftBox.setPrefWidth(300);
        leftBox.setAlignment(Pos.CENTER);
        leftBox.setPadding(new Insets(15));
        // leftBox.setStyle("-fx-background-color: white;
        // -fx-border-color:rgb(195,188,188); -fx-border-radius: 4;");
        leftBox.setStyle(
                "-fx-background-color: #c3b2f5ff; -fx-border-color: #DADADA; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5,0,0,4);");

        chatRoom.setLeft(leftBox);

        VBox rightBox = new VBox(20, imageView2, name2, profile1);
        rightBox.setPrefWidth(300);
        rightBox.setAlignment(Pos.CENTER);
        rightBox.setPadding(new Insets(25));
        // rightBox.setStyle("-fx-background-color: white;
        // -fx-border-color:rgb(195,188,188); -fx-border-radius: 4;");
        rightBox.setStyle(
                "-fx-background-color: #c3b2f5ff; -fx-border-color: #DADADA; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5,0,0,4);");

        chatRoom.setRight(rightBox);

        VBox messageBox = new VBox(10);
        messageBox.setPrefWidth(600);
        messageBox.setMinHeight(Region.USE_COMPUTED_SIZE);
        messageBox.setPrefHeight(Region.USE_COMPUTED_SIZE);
        messageBox.setPadding(new Insets(10));
        // messageBox.setStyle("-fx-background-color:rgb(236, 207, 243);");
        messageBox.setStyle(
                "-fx-background-color:rgb(255, 255, 255); -fx-background-radius: 15; -fx-padding: 15; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 4);");

        ScrollPane scrollPane = new ScrollPane(messageBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setStyle("-fx-background-color: transparent;");
        scrollPane.setPrefHeight(800);

        Button joinMeetingBtn = new Button("🔗 Join Meeting");
        joinMeetingBtn.setStyle("-fx-background-color: #0078D4; -fx-text-fill: white; -fx-font-size: 13px;");
        joinMeetingBtn.setVisible(false); 

        msg.setPromptText("Type Your Message...");
        msg.setAlignment(Pos.BASELINE_LEFT);
        msg.setPrefHeight(50);
        msg.setPrefWidth(500);
        msg.setStyle("-fx-border-radius : 10");

        Button send = new Button("Send");
        send.setMaxSize(50, 50);
        send.setOnAction(e -> {
            String text = msg.getText().trim();
            if (!text.isEmpty()) {

                chatDAO.sendMessage(LoginPage.currentUser, text);
                msg.clear();

                // String time = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"));

                // Text messageText = new Text(text);
                // messageText.setWrappingWidth(300);

                // Text timeText = new Text(time);
                // timeText.setStyle("-fx-font-size: 10px; -fx-fill: gray;");

                // VBox sentMsg = new VBox(messageText, timeText);
                // sentMsg.setStyle("-fx-background-color:rgb(230, 70, 251); -fx-padding: 10px;
                // -fx-background-radius: 10px;");
                // sentMsg.setMaxWidth(400);

                // HBox messageContainer = new HBox(sentMsg);
                // messageContainer.setAlignment(Pos.BASELINE_RIGHT);
                // messageContainer.setPadding(new Insets(5));

                // messageBox.getChildren().add(messageContainer);
                // msg.clear();

                // simulateReceiverResponse(messageBox);
            }
        });

        chatDAO.startListening((sender, message) -> {
            Platform.runLater(() -> {
                Text messageText = new Text(message);
                messageText.setWrappingWidth(300);

                String time = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm"));
                Text timeText = new Text(time);
                timeText.setStyle("-fx-font-size: 10px; -fx-fill: gray;");

                VBox sentMsg = new VBox(messageText, timeText);
                sentMsg.setStyle(sender.equals(LoginPage.currentUser)
                        ? "-fx-background-color : lightgreen; -fx-padding: 10; -fx-background-radius: 10"
                        : "-fx-background-color : purple; -fx-padding: 10; -fx-background-radius: 10;");
                sentMsg.setMaxWidth(400);

                HBox messageContainer = new HBox(sentMsg);
                messageContainer
                        .setAlignment(sender.equals(LoginPage.currentUser) ? Pos.BASELINE_LEFT : Pos.BASELINE_RIGHT);
                messageContainer.setPadding(new Insets(5));

                messageBox.getChildren().add(messageContainer);

            });
        });

        HBox inputBox = new HBox(15, msg, send);
        inputBox.setPadding(new Insets(3));
        inputBox.setAlignment(Pos.BASELINE_CENTER);
        inputBox.setStyle("-fx-background-color: white; -fx-border-color:rgb(195, 188, 188); -fx-border-radius: 4;");

        HBox buttons = new HBox(30, meeting, notes);
        buttons.setAlignment(Pos.CENTER);

        VBox centerBox = new VBox(2, scrollPane,joinMeetingBtn, inputBox);
        centerBox.setPadding(new Insets(5));

        BorderPane topBar = new BorderPane();
        topBar.setPadding(new Insets(10, 40, 0, 10));

        Image backbtn = new Image("assets\\images\\backbutton.png");
        ImageView bkImageView = new ImageView(backbtn);
        bkImageView.setFitHeight(50);
        bkImageView.setFitWidth(50);
        Button backbutton = new Button("", bkImageView);
        backbutton.setAlignment(Pos.TOP_LEFT);
        backbutton.setStyle("-fx-background-color: transparent;");
        backbutton.setOnAction(event -> {
            FindMentor obj = new FindMentor(user);
            obj.start(chatStage);

        });

        Image logo = new Image("assets\\images\\logo.png");
        ImageView imageView = new ImageView(logo);
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        topBar.setRight(imageView);

        Label centerLabel = new Label("UpskillX Chatroom - Learn by Sharing");
        centerLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #444;");
        centerLabel.setAlignment(Pos.CENTER);
        BorderPane.setAlignment(centerLabel, Pos.CENTER);
        topBar.setCenter(centerLabel);

        ImageView profileView = new ImageView(img1);
        Circle circle1 = new Circle(25, 25, 25);
        profileView.setClip(circle1);
        profileView.setFitHeight(50);
        profileView.setFitWidth(50);

        Text username = new Text("  " + user.getFirstName() + "" + user.getLastName());
        username.setFont(Font.font("verdana", FontWeight.BOLD, 9));

        VBox userBox = new VBox(3, profileView, username);
        userBox.setStyle(
                "-fx-background-color: linear-gradient(to right,rgb(199, 183, 241), #f0edfbff, -fx-background-radius: 8)");
        userBox.setAlignment(Pos.BASELINE_CENTER);
        userBox.setPrefHeight(Region.USE_COMPUTED_SIZE);
        VBox backBox = new VBox(10, backbutton, userBox);

        topBar.setLeft(backBox);
        topBar.setStyle(
                "-fx-background-color: linear-gradient(to right,rgb(203, 184, 250), #f0edfbff); -fx-background-radius: 8;");
        // topBar.setStyle("-fx-background-color : # #BBBDEF");
        /*
         * Region space = new Region();
         * HBox.setHgrow(space, Priority.ALWAYS);
         * HBox hb = new HBox(10,imageView,space,userBox);
         * hb.setPadding(new Insets(0,25,10,30));
         * hb.setAlignment(Pos.TOP_LEFT);
         * hb.setMinHeight(100);
         * hb.setMaxHeight(100);
         * hb.setPrefHeight(100);
         * hb.setSpacing(10);
         */

        chatRoom.setTop(topBar);
        chatRoom.setCenter(centerBox);
        chatRoom.setBottom(buttons);
        // chatRoom.setBackground(Background.fill(Color.rgb(187, 189, 239)));
        chatRoom.setStyle(
                "-fx-background-color: linear-gradient(to right,rgb(203, 184, 250), #f0edfbff); -fx-background-radius: 8;");

        /*
         * Timer timer = new Timer();
         * timer.schedule(new TimerTask() {
         * public void run() {
         * Platform.runLater(() -> {
         * Alert alert = new Alert(AlertType.INFORMATION);
         * alert.setTitle("Schedule alert");
         * alert.setHeaderText("Times up");
         * alert.setContentText("Time up");
         * alert.showAndWait();
         * });
         * }
         * }, 10000);
         */
        listenForMeetingLink(chat_key.replace("@", "-").replace(".", "_"), joinMeetingBtn);

        Scene sc = new Scene(chatRoom, 1200, 800);
        return sc;

    }

}
