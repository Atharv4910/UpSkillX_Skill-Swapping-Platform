package com.upskillx.View;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Callback;

public class AdminDashboard extends Application {

    private StackPane mainContent = new StackPane();

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        VBox sidebar = createSidebar();
        mainContent.getChildren().add(getDashboardView());

        root.setLeft(sidebar);
        root.setCenter(mainContent);

        Scene scene = new Scene(root, 1300, 650);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Admin Dashboard - Skill Swap");
        primaryStage.show();
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox();
        sidebar.setStyle("-fx-background-color: linear-gradient(to bottom right, #ae77caff, #b76ae7ff);");
        sidebar.setPrefWidth(200);
        sidebar.setPadding(new Insets(30, 10, 30, 10));
        sidebar.setSpacing(20);

        ImageView avatar = new ImageView(new Image("https://img.icons8.com/ios-glyphs/90/ffffff/user--v1.png"));
        avatar.setFitWidth(50);
        avatar.setFitHeight(50);

        VBox headerBox = new VBox(avatar);
        headerBox.setAlignment(Pos.CENTER);
        headerBox.setPadding(new Insets(10));

        Button dashboardBtn = createSidebarButton("🧑‍💻Dashboard");
        Button usersBtn = createSidebarButton("👥Users");
        Button reportsBtn = createSidebarButton("📑Reports");
        Button analyticsBtn = createSidebarButton("📈Analytics");

        dashboardBtn.setOnAction(e -> switchTo(getDashboardView()));
        usersBtn.setOnAction(e -> switchTo(getUsersView()));
        reportsBtn.setOnAction(e -> switchTo(getReportsView()));
        analyticsBtn.setOnAction(e -> switchTo(getAnalyticsView()));

        sidebar.getChildren().addAll(headerBox, dashboardBtn, usersBtn, reportsBtn, analyticsBtn);
        return sidebar;
    }

    private Button createSidebarButton(String text) {
        Button btn = new Button(text);
        btn.setPrefWidth(180);
        btn.setFont(Font.font("Segoe UI", 14));
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #c1b4cdff; -fx-text-fill: white;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white;"));
        return btn;
    }

    private void switchTo(Node view) {
        mainContent.getChildren().clear();
        mainContent.getChildren().add(view);
    }

    private VBox getDashboardView() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #f4f2ff;");

        Label welcomeLabel = new Label("Welcome back, Admin!");
        welcomeLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        Label subLabel = new Label("Here’s what’s happening today 👇");
        subLabel.setFont(Font.font("Segoe UI", FontWeight.NORMAL, 16));

        HBox stats = new HBox(20);
        stats.getChildren().addAll(
                createStatCard("👥", "Total Users", "1452"),
                createStatCard("🔁", "Ongoing Sessions", "58"),
                createStatCard("📢", "Reports", "12"),
                createStatCard("🆕", "New Users", "34"));

        VBox trendingBox = new VBox(15);
        trendingBox.setPrefWidth(300);
        trendingBox.setPadding(new Insets(20));
        trendingBox.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #cfcdfd, 8, 0.3, 0, 3);");

        Label trendingHeader = new Label("🔥 Trending Skills");
        trendingHeader.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));

        VBox trendingList = new VBox(8);
        trendingList.getChildren().addAll(
                new Label("• Python Development"),
                new Label("• UI/UX Design"),
                new Label("• Digital Marketing"));

        trendingBox.getChildren().addAll(trendingHeader, trendingList);

        VBox shortcutBox = new VBox(15);
        shortcutBox.setPrefWidth(300);
        shortcutBox.setPadding(new Insets(20));
        shortcutBox.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #cfcdfd, 8, 0.3, 0, 3);");

        Label shortcutHeader = new Label("⚙️ Quick Admin Actions");
        shortcutHeader.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));

        VBox shortcutButtons = new VBox(10);
        Button homeBtn = new Button("📋 Home");
        Button feedBtn = new Button("🎥 Feed");
        Button blogsBtn = new Button("✍️ Blogs");

        for (Button btn : new Button[] { homeBtn, feedBtn, blogsBtn }) {
            btn.setPrefWidth(250);
            btn.setFont(Font.font("Segoe UI", 14));
            btn.setStyle("-fx-background-color: #ededff; -fx-background-radius: 8;");
        }

        shortcutButtons.getChildren().addAll(homeBtn, feedBtn, blogsBtn);
        shortcutBox.getChildren().addAll(shortcutHeader, shortcutButtons);

        root.getChildren().addAll(welcomeLabel, subLabel, stats, trendingBox, shortcutBox);
        return root;
    }

    private VBox createStatCard(String icon, String title, String value) {
        VBox card = new VBox(5);
        card.setPadding(new Insets(15));
        card.setPrefSize(150, 100);
        card.setStyle("-fx-background-color: #ffffff; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, #cfcdfd, 8, 0.5, 0, 3);");

        Label iconLabel = new Label(icon);
        iconLabel.setFont(Font.font(24));

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Segoe UI", 12));

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));

        card.getChildren().addAll(iconLabel, titleLabel, valueLabel);
        return card;
    }

    private VBox getUsersView() {
        VBox view = new VBox(20);
        view.setPadding(new Insets(30));
        view.setStyle("-fx-background-color: #f5f4fd;");

        Label label = new Label("👥 Manage Users");
        label.setFont(Font.font("Segoe UI", 28));

        TableView<User> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<User, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<User, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<User, String> skillsCol = new TableColumn<>("Skills");
        skillsCol.setCellValueFactory(new PropertyValueFactory<>("skills"));

        table.getColumns().addAll(nameCol, emailCol, skillsCol);

        table.getItems().addAll(
                new User("Amit Sharma", "amit@example.com", "Java, Firebase"),
                new User("Sneha Patel", "sneha@example.com", "Flutter, Dart"),
                new User("Raj Verma", "raj@example.com", "Python, AI")
        );

        view.getChildren().addAll(label, table);
        return view;
    }

    private VBox getReportsView() {
        VBox view = new VBox(20);
        view.setPadding(new Insets(30));
        view.setStyle("-fx-background-color: #f5f4fd;");

        Label label = new Label("📑 View Reports");
        label.setFont(Font.font("Segoe UI", 28));

        TableView<Report> reportTable = new TableView<>();
        reportTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Report, String> fromCol = new TableColumn<>("Reported By");
        fromCol.setCellValueFactory(new PropertyValueFactory<>("reportedBy"));

        TableColumn<Report, String> toCol = new TableColumn<>("Reported User");
        toCol.setCellValueFactory(new PropertyValueFactory<>("reportedTo"));

        TableColumn<Report, Void> actionCol = new TableColumn<>("Action");
        actionCol.setCellFactory(getBlockCellFactory());

        reportTable.getColumns().addAll(fromCol, toCol, actionCol);
        reportTable.getItems().addAll(
                new Report("Meera", "Rohit"),
                new Report("Karan", "Priya"),
                new Report("Anjali", "Dev")
        );

        view.getChildren().addAll(label, reportTable);
        return view;
    }

    private Callback<TableColumn<Report, Void>, TableCell<Report, Void>> getBlockCellFactory() {
        return param -> new TableCell<>() {
            private final Button btn = new Button("Block");
            {
                btn.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white;");
                btn.setOnAction(event -> {
                    Report data = getTableView().getItems().get(getIndex());
                    System.out.println("Blocked user: " + data.getReportedTo());
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btn);
                }
            }
        };
    }

    private VBox getAnalyticsView() {
        VBox view = new VBox(20);
        view.setPadding(new Insets(30));
        view.setStyle("-fx-background-color: #f5f4fd;");

        Label label = new Label("📊 Analytics");
        label.setFont(Font.font("Segoe UI", 28));

        PieChart pieChart = new PieChart();
        pieChart.getData().addAll(
                new PieChart.Data("Total Users", 1452),
                new PieChart.Data("New Users", 342),
                new PieChart.Data("Skill Posts", 245),
                new PieChart.Data("Reports", 12)
        );
        pieChart.setLabelsVisible(true);
        pieChart.setLegendVisible(true);
        pieChart.setClockwise(true);
        pieChart.setStartAngle(90);

        view.getChildren().addAll(label, pieChart);
        return view;
    }

    public static class User {
        private String name, email, skills;

        public User(String name, String email, String skills) {
            this.name = name;
            this.email = email;
            this.skills = skills;
        }

        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getSkills() { return skills; }
    }

    public static class Report {
        private String reportedBy, reportedTo;

        public Report(String reportedBy, String reportedTo) {
            this.reportedBy = reportedBy;
            this.reportedTo = reportedTo;
        }

        public String getReportedBy() { return reportedBy; }
        public String getReportedTo() { return reportedTo; }
    }
}
