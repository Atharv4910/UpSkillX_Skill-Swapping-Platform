// package com.upskillx.View;
// import javafx.event.ActionEvent;
// import javafx.event.EventHandler;
// import javafx.geometry.Insets;
// import javafx.geometry.Pos;
// import javafx.scene.Scene;
// import javafx.scene.control.Button;
// import javafx.scene.control.PasswordField;
// import javafx.scene.control.TextField;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;
// import javafx.scene.layout.*;
// import javafx.scene.text.Text;
// import javafx.stage.Stage;

// public class login_page {
//     Scene loginScene,user_dashboardScene;
//     Stage loginStage;

//     public void setLoginScene(Scene loginScene) {
//         this.loginScene = loginScene;
//     }

//     public void setLoginStage(Stage loginStage) {
//         this.loginStage = loginStage;
//     }

//     public StackPane createLoginScene( Runnable back) {

//         Image backbtn = new Image("assets\\images\\backbutton.png");
//         ImageView bkImageView = new ImageView(backbtn);
//         Button backbutton = new Button("",bkImageView);
//         backbutton.setAlignment(Pos.TOP_LEFT);
//         backbutton.setStyle("-fx-background-color: transparent;");
//         backbutton.setOnAction(new EventHandler<ActionEvent>() {

//             @Override
//             public void handle(ActionEvent arg0) {
//                 back.run();
//             }
            
//         });

//         Text lines = new Text("    T r a d e   S k i l l s,\n" + //
//                         "            B u i l d   C o n n e c t i o n s, \n" + //
//                         "                              G r o w   T o g e t h e r              ");

//         lines.setStyle("-fx-font-size:25px;-fx-font-weight:BOLD;-fx-fill: rgba(255, 255, 255, 1);");             
        
//         Image art = new Image("assets\\images\\login.jpeg");
//         ImageView imageView = new ImageView(art);
//         imageView.setFitWidth(560);
//         imageView.setFitHeight(400);

       
//         Region spTopAndImg = new Region();
//         spTopAndImg.setPrefHeight(40);
//         VBox leftBox = new VBox(backbutton,lines,spTopAndImg,imageView);
//         leftBox.setAlignment(Pos.CENTER);
//         leftBox.setPrefWidth(340);



        
//         Text createAcTitle = new Text("Sign In To Your Account");
//         createAcTitle.setStyle("-fx-font-size:25px;-fx-font-weight:BOLD;-fx-fill: rgba(0, 0, 0, 1);"); 

//         Image google = new Image("assets\\images\\google.png");
//         ImageView googleimg = new ImageView(google);
//         googleimg.setFitWidth(20);
//         googleimg.setFitHeight(20);

//         Image facebook = new Image("assets\\images\\facebook.png");
//         ImageView facebookImageView = new ImageView(facebook);
//         facebookImageView.setFitWidth(20);
//         facebookImageView.setFitHeight(20);

//         Button signinWithGoogle = new Button("   Sign In With Google",googleimg);
//         signinWithGoogle.setStyle("-fx-background-color:White;-fx-border-color: rgba(140, 138, 138, 1);-fx-border-radius:10;-fx-min-height:32");

//         Button signinWithFacebook = new Button("   Sign In With Facebook",facebookImageView);
//         signinWithFacebook.setStyle("-fx-background-color:White;-fx-border-color: rgba(140, 138, 138, 1);-fx-border-radius:10;-fx-min-height:32");
        
//         HBox signinWithButtons = new HBox(25,signinWithGoogle,signinWithFacebook);
//         signinWithButtons.setAlignment(Pos.CENTER);

//         Text or = new Text("- OR -");
//         or.setStyle("-fx-font-size:17px;-fx-font-weight:BOLD;-fx-fill: rgba(94, 92, 92, 1);");

//         TextField name = new TextField();
//         name.setStyle(
//             "-fx-background-color: transparent;" +
//             "-fx-border-color: transparent transparent #cccccc transparent;" +
//             "-fx-border-width: 0 0 1 0;" +
//             "-fx-font-size: 14px;" +
//             "-fx-max-width:350"
//         );
//         name.setPromptText("Usename");
//         TextField emailId = new TextField();
//         emailId.setStyle(
//             "-fx-background-color: transparent;" +
//             "-fx-border-color: transparent transparent #cccccc transparent;" +
//             "-fx-border-width: 0 0 1 0;" +
//             "-fx-font-size: 14px;" +
//             "-fx-max-width:350"
//         );
//         emailId.setPromptText("Email Id");

       
//         PasswordField passwordField = new PasswordField();
//         TextField visiblePasswordField = new TextField();
//         visiblePasswordField.setManaged(false);
//         visiblePasswordField.setVisible(false);

      
//         String inputStyle = "-fx-background-color: transparent;" +
//         "-fx-border-color: transparent transparent #cccccc transparent;" +
//         "-fx-border-width: 0 0 1 0;" +
//         "-fx-font-size: 14px;" +
//         "-fx-max-width:350";

//         passwordField.setPromptText("Password");
//         visiblePasswordField.setPromptText("Password");

//         passwordField.setStyle(inputStyle);
//         visiblePasswordField.setStyle(inputStyle);

        
//         Image eyeOpen = new Image("assets\\images\\show.png");
//         Image eyeClosed = new Image("assets\\images\\hide.png");
//         ImageView eyeIcon = new ImageView(eyeClosed);
//         eyeIcon.setFitWidth(20);
//         eyeIcon.setFitHeight(20);
//         eyeIcon.setPickOnBounds(true); // Make it clickable

//         // Align eye to right inside a StackPane
//         StackPane passwordStack = new StackPane();
//         passwordStack.setMaxWidth(350);
//         passwordStack.getChildren().addAll(passwordField, visiblePasswordField, eyeIcon);
//         StackPane.setAlignment(eyeIcon, Pos.CENTER_RIGHT);
//         StackPane.setMargin(eyeIcon, new Insets(0, 8, 0, 0)); // Optional padding


//         eyeIcon.setOnMouseClicked(e -> {
//           if (passwordField.isVisible()) {
//               visiblePasswordField.setText(passwordField.getText());
//               passwordField.setVisible(false);
//               passwordField.setManaged(false);
//               visiblePasswordField.setVisible(true);
//               visiblePasswordField.setManaged(true);
//               eyeIcon.setImage(eyeOpen);
//         } else {
//               passwordField.setText(visiblePasswordField.getText());
//               visiblePasswordField.setVisible(false);
//               visiblePasswordField.setManaged(false);
//               passwordField.setVisible(true);
//               passwordField.setManaged(true);
//               eyeIcon.setImage(eyeClosed);
//           }
//        });

//         VBox textBox = new VBox(18,name,emailId,passwordStack);
//         textBox.setAlignment(Pos.CENTER);

//         Button signinButton = new Button("Logged In Account");
//         signinButton.setStyle("-fx-min-width:360;-fx-min-height:40;-fx-font-size:16px;-fx-font-weight:BOLD;-fx-background-radius:20px;-fx-text-fill:rgba(253, 253, 253, 1);-fx-background-color:rgba(207, 178, 220, 1)");

//        signinButton.setOnAction(event ->{
//             initializeUserDashboardPage();
//             loginStage.setScene(user_dashboardScene);
//        });

        
//         Region sptextFtoBtn = new Region();
//         sptextFtoBtn.setPrefHeight(25);

//         Text afterbtnClick = new Text("Login Sucessfully");
//         afterbtnClick.setStyle("-fx-text-fill::rgba(207, 178, 220, 1)");
        
//         VBox rightBox = new VBox(35,createAcTitle,signinWithButtons,or,textBox,sptextFtoBtn,signinButton,afterbtnClick);
//         rightBox.setPadding(new Insets(150,0,0,80));
//         VBox.setMargin(or, new Insets(0,0,0,-15));



//         rightBox.setAlignment(Pos.TOP_CENTER);
//         rightBox.setPrefWidth(1000);
//         rightBox.setStyle(
//                 "-fx-background-color: white;" +
//                 "-fx-background-radius: 40 0 0 40;" +
//                 "-fx-border-radius: 40 0 0 40;"
//         );

        
//         HBox container = new HBox(leftBox, rightBox);
//         container.setStyle("-fx-background-color:rgba(207, 178, 220, 1)");

        
//         StackPane stackPane = new StackPane(container);
//         stackPane.setAlignment(Pos.CENTER);

//         return stackPane;
//     }

// //     private void initializeUserDashboardPage(){
// //         // user_dashboard uDashboard = new user_dashboard();
// //         uDashboard.setUserDashboardStage(loginStage);
// //         user_dashboardScene = new Scene(uDashboard.createUserDashboardScene(),1330,790);
// //     }
// // }
