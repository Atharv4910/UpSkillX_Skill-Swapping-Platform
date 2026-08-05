package com.upskillx.Controller;

import com.upskillx.View.LoginPage;
import com.upskillx.dao.UserDAO;
import com.upskillx.model.User;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DashboardController {

    VBox connectionVBox;
    private final UserDAO userDAO = new UserDAO();

    public DashboardController(VBox connectionVBox) {
        this.connectionVBox = connectionVBox;
    }

    public VBox initialize() {
        VBox returnBox = loadConnectionRequests();
        connectionVBox.setPrefHeight(460);
        connectionVBox.setPrefWidth(120);
        connectionVBox
                .setStyle("-fx-border-radius: none; -fx-background-radius: none; -fx-background-color: transparent;");
        return returnBox;
    }

    private VBox loadConnectionRequests() {
        userDAO.getIncomingRequests(LoginPage.currentUser, incomingUsers -> {
            System.out.println("Loading requests for: " + LoginPage.currentUser);
            System.out.println("Fetched users: " + incomingUsers.size());
            connectionVBox.getChildren().clear();
            for (User sender : incomingUsers) {
                if (incomingUsers.isEmpty()) {
                    connectionVBox.getChildren().add(new Label("No incoming requests."));
                }
                HBox requestCard = createRequestCard(sender);
                connectionVBox.getChildren().add(requestCard);
            }
        });
        return connectionVBox;
    }

    private HBox createRequestCard(User requester) {
        Text name = new Text(requester.getFirstName() + " " + requester.getLastName());
                name.setStyle("-fx-font-size:17px");

                Text skill = new Text(requester.getPosition()); // Or any field like skill
                skill.setStyle("-fx-font-size:12px");

                VBox userInfo = new VBox(5, name, skill);
                userInfo.setStyle("-fx-min-width:150");

                // // Image (optional static or from field)
                // Circle profileImg = new Circle(25);
                // Image image = new Image("assets/images/profile1.png"); // Or dynamic if you store URL
                // profileImg.setFill(new ImagePattern(image));

                // VBox profileContainer = new VBox(profileImg);
                Image accept = new Image("assets\\images\\accept_btn.png");
                Image reject = new Image("assets\\images\\reject_btn.png");

                Button acceptBtn = new Button("", new ImageView(accept));
                Button rejectBtn = new Button("", new ImageView(reject));
                acceptBtn.setStyle("-fx-background-color:WHITE");
                rejectBtn.setStyle("-fx-background-color:WHITE");

                acceptBtn.setOnAction(e -> {
                        userDAO.updateRequestStatus(LoginPage.currentUser, requester.getEmail(), "accepted",
                                        this::loadConnectionRequests);

                });

                rejectBtn.setOnAction(e -> {
                        userDAO.updateRequestStatus(LoginPage.currentUser, requester.getEmail(), "rejected",
                                        this::loadConnectionRequests);

                });

                Region spacer = new Region();
                HBox.setHgrow(spacer, Priority.ALWAYS);

                HBox row = new HBox(10, userInfo, spacer, acceptBtn, rejectBtn);
                row.setAlignment(Pos.CENTER_LEFT);
                row.setPadding(new Insets(8));
                row.setStyle("-fx-background-color:rgb(238, 185, 185); -fx-background-radius: 6;");

                return row;
        }
}
