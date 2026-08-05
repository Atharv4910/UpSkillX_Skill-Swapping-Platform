package com.upskillx.Controller;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.upskillx.View.FeedBack;
import com.upskillx.View.LoginPage;
import com.upskillx.dao.ChatDAO;
import com.upskillx.dao.MentorDAO;
import com.upskillx.dao.UserDAO;
import com.upskillx.model.Meeting;
import com.upskillx.model.User;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MeetingController {

    private ChatDAO chatDAO;
    private final UserDAO userDAO = new UserDAO();
    private final MentorDAO mentorDAO = new MentorDAO();
    User user;
    User mentor;
    String endTime;
    public MeetingController(ChatDAO chatDAO) {
        this.chatDAO = chatDAO;
    }

    public void openMeetingSchedular(String currentUserId, User user, User mentor) {
        this.user = user;
        this.mentor = mentor;
        Stage popup = new Stage();
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setTitle("Schedule Meeting");

        DatePicker startdatePicker = new DatePicker();
        DatePicker endDatePicker = new DatePicker();
        ComboBox<String> startTime = new ComboBox<>();
        

        startTime.getItems().addAll("09:00", "10:00", "11:00", "01:00");
        Spinner<Integer> hourSpinner = new Spinner<>(0, 23, 12);
        Spinner<Integer> minuteSpinner = new Spinner<>(0, 59, 0);

        hourSpinner.setEditable(true);
        minuteSpinner.setEditable(true);

        HBox timePicker = new HBox(5, new Label("Hour:"), hourSpinner, new Label("Minute:"), minuteSpinner);

        Button confirmBtn = new Button("Confirm");
        Label result = new Label();

        confirmBtn.setOnAction(event -> {
            int hour = hourSpinner.getValue();
            int minute = minuteSpinner.getValue();
            endTime = String.format("%02d:%02d", hour,minute);

            if ((startdatePicker.getValue() != null && endDatePicker.getValue() != null)
                    && (startTime.getValue() != null && endTime != null)) {

                Meeting meeting = new Meeting(
                        currentUserId,
                        startdatePicker.getValue().toString(),
                        endDatePicker.getValue().toString(),
                        startTime.getValue(),
                        endTime);

                Firestore db = FirestoreClient.getFirestore();
                DocumentReference docRef = db.collection("meetingLinks").document("meetLink");
                try {
                    DocumentSnapshot document = docRef.get().get();
                    if (document.exists()) {
                        String meetLink = document.getString("meetlink");
                        meeting.setMeetLink(meetLink);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Alert error = new Alert(Alert.AlertType.ERROR, "Failed to fetch meeting link.");
                    error.showAndWait();
                }

                chatDAO.scheduleMeeting(meeting);

                // Confirm popup
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Meeting Scheduled");
                alert.setHeaderText("Meeting Confirmed!");
                alert.setContentText("From " + meeting.getStartDate() + " to " + meeting.getEndDate() +
                        "\nTime: " + meeting.getStartTime() + " - " + meeting.getEndTime());
                alert.showAndWait();

                popup.close();

                // Schedule end-of-session alert
                try {
                    String selectedTime = meeting.getEndTime();
                    String timeString = selectedTime.trim(); // always trim before parsing

                    LocalDate endDate = LocalDate.parse(meeting.getEndDate());
                    LocalTime endTime1 = LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));
                    LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime1);

                    long delay = Duration.between(LocalDateTime.now(), endDateTime).toMillis();
                    if (delay > 0) {
                        Timer timer = new Timer();
                        timer.schedule(new TimerTask() {
                            public void run() {
                                Platform.runLater(() -> {
                                    Alert endAlert = new Alert(Alert.AlertType.INFORMATION);
                                    endAlert.setTitle("Meeting Ended");
                                    endAlert.setHeaderText("Scheduled Session Finished");
                                    endAlert.setContentText("The meeting session has ended.");
                                    endAlert.setOnCloseRequest(e -> sessionCompletionBox(user, mentor));
                                    endAlert.showAndWait();

                                });
                            }
                        }, delay);
                    }

                } catch (Exception ex) {
                    ex.printStackTrace(); // handle parse error
                }

            } else {
                result.setText("All fields must be filled");
            }
        });

        VBox vb = new VBox(10, new Label("Start Date : "), startdatePicker,
                new Label("End Date : "), endDatePicker,
                new Label("Start Time : "), startTime,
                new Label("End Time : "), timePicker,
                confirmBtn, result);

        vb.setPadding(new Insets(20));
        vb.setAlignment(Pos.CENTER);
        popup.setScene(new Scene(vb, 300, 400));
        popup.showAndWait();

    }

    private void sessionCompletionBox(User user, User mentor) {
        FeedBack feedBack = new FeedBack(user, mentor);
        feedBack.start(new Stage());
    }

    public void loadOngoingMeetings(User currentUser, HBox sessionCardContainer) {
        System.out.println("Loading ongoing meetings");
        DatabaseReference chatroomsRef = FirebaseDatabase.getInstance().getReference("chatrooms");

        chatroomsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot chatroomSnap : snapshot.getChildren()) {
                    String roomKey = chatroomSnap.getKey();
                    System.out.println(roomKey);

                    if (!roomKey.contains(sanitizeEmail(currentUser.getEmail())))
                        continue;
                    System.out.println("Continued");
                    DataSnapshot meetingsSnap = chatroomSnap.child("meetings");
                    for (DataSnapshot meetingSnap : meetingsSnap.getChildren()) {
                        Meeting meeting = meetingSnap.getValue(Meeting.class);

                        String otherEmail = extractOtherUserEmail(roomKey, currentUser.getEmail());
                        System.out.println(otherEmail);

                        mentorDAO.getMentorByEmail(otherEmail, mentor -> {

                            if (mentor == null) {
                                System.out.println("Mentor not found for email : " + otherEmail);
                                return;
                            }

                            String mentorName = mentor.getFirstName() + " " + mentor.getLastName();
                            System.out.println("getting mentor " + mentorName);
                            String skillSwap = "Skill Swap: " + mentor.getSkillsToTeach().get(0)
                                    + " ↔ " + currentUser.getSkillsToLearn().get(0);
                            String format = "📋 " + mentor.getPrefFormat().get(0);
                            String dateTime = meeting.getStartDate() + " @" + meeting.getStartTime();
                            String timeLeft = calculateDaysLeft(meeting.getStartDate());

                            VBox card = createSessionCard(timeLeft, dateTime, skillSwap, format, mentorName);
                            Platform.runLater(() -> sessionCardContainer.getChildren().add(card));
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                System.out.println("Error: " + error.getMessage());
            }
        });
    }

    private String sanitizeEmail(String email) {
        // System.out.println(email.replace(".", "_").replace("@", "-"));
        return email.replace(".", "_").replace("@", "-");
    }

    private String extractOtherUserEmail(String chatroomKey, String currentEmail) {
        String sanitizedCurrent = sanitizeEmail(currentEmail);
        String[] parts = chatroomKey.split("_" + sanitizedCurrent + "_");

        String otherSanitizedEmail;
        if (parts.length == 2) {
            // sanitizedCurrent is in the middle
            otherSanitizedEmail = parts[0] + parts[1];
        } else if (chatroomKey.startsWith(sanitizedCurrent + "_")) {
            otherSanitizedEmail = chatroomKey.substring(sanitizedCurrent.length() + 1);
        } else if (chatroomKey.endsWith("_" + sanitizedCurrent)) {
            otherSanitizedEmail = chatroomKey.substring(0, chatroomKey.length() - sanitizedCurrent.length() - 1);
        } else {
            // fallback
            String[] fallbackParts = chatroomKey.split("_");
            otherSanitizedEmail = fallbackParts[0].equals(sanitizedCurrent) ? fallbackParts[1] : fallbackParts[0];
        }

        return otherSanitizedEmail.replace("-", "@").replace("_", ".");
    }

    private String calculateDaysLeft(String startDateStr) {
        LocalDate today = LocalDate.now();
        LocalDate meetingDate = LocalDate.parse(startDateStr);
        long days = ChronoUnit.DAYS.between(today, meetingDate);
        return days + " Day" + (days != 1 ? "s" : "") + " Left";
    }

    private VBox createSessionCard(String daysLeft, String dateTime, String skillTitle, String format,
            String mentorName) {
        VBox card = new VBox(5);
        card.setPadding(new Insets(10));
        card.setStyle(
                "-fx-background-color: #fff; -fx-border-color: #ddd; -fx-border-radius: 10; -fx-background-radius: 10;");
        card.setPrefWidth(250);

        Label dayLeftLabel = new Label(daysLeft);
        dayLeftLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");

        Label timeLabel = new Label(dateTime);
        timeLabel.setStyle("-fx-text-fill: blue;");

        Label titleLabel = new Label(skillTitle);
        titleLabel.setWrapText(true);
        titleLabel.setStyle("-fx-font-weight: bold;");

        Label mentorLabel = new Label("With: " + mentorName);
        Label formatLabel = new Label(format);

        Button attendButton = new Button("Attend");
        attendButton.setOnAction(e -> {
            System.out.println("Redirecting to chatroom...");
        });

        card.getChildren().addAll(dayLeftLabel, timeLabel, titleLabel, mentorLabel, formatLabel, attendButton);
        return card;
    }

    private VBox sessionVBox;

    public MeetingController(VBox sessionVBox) {
        this.sessionVBox = sessionVBox;
    }

    public void loadOngoingSessions() {

        System.out.println("Loading onlgoing sessions");
        ScrollPane scrollPane = new ScrollPane();
        HBox sessionCardContainer = new HBox(20);
        sessionCardContainer.setPadding(new Insets(10));
        sessionCardContainer.setAlignment(Pos.CENTER_LEFT);

        scrollPane.setContent(sessionCardContainer);
        scrollPane.setFitToHeight(true);
        scrollPane.setStyle("-fx-background-color:WHITE;");
        sessionVBox.getChildren().add(scrollPane);

        User currentUser = LoginPage.loginUser;
        loadOngoingMeetings(currentUser, sessionCardContainer);
    }

}
