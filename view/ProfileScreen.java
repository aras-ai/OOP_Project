package citywars.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import citywars.controller.MainMenuController;
import citywars.controller.UserController;
import citywars.model.User;

public class ProfileScreen {
    private VBox view;
    private TextField usernameField, emailField, nicknameField;
    private PasswordField passwordField, passwordConfirmationField;
    private Button updateProfileButton;
    private Label messageLabel;

    public ProfileScreen(Stage stage) {
        view = new VBox(10);
        usernameField = new TextField();
        passwordField = new PasswordField();
        passwordConfirmationField = new PasswordField();
        emailField = new TextField();
        nicknameField = new TextField();
        updateProfileButton = new Button("Update Profile");
        messageLabel = new Label();

        User loggedInUser = MainMenuController.getInstance().getLoggedInUser();
        usernameField.setPromptText("Username");
        usernameField.setText(loggedInUser.getUsername());
        passwordField.setPromptText("Password");
        passwordField.setText(loggedInUser.getPassword());
        passwordConfirmationField.setPromptText("Password Confirmation");
        passwordConfirmationField.setText(loggedInUser.getPassword());
        emailField.setPromptText("Email");
        emailField.setText(loggedInUser.getEmail());
        nicknameField.setPromptText("Nickname");
        nicknameField.setText(loggedInUser.getNickname());
        
        updateProfileButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-size: 14;");
        messageLabel.setTextFill(Color.RED);

        view.setStyle("-fx-background-color: #121212;");
        usernameField.setStyle("-fx-background-color: #2A2A2A; -fx-text-fill: white;");
        passwordField.setStyle("-fx-background-color: #2A2A2A; -fx-text-fill: white;");
        passwordConfirmationField.setStyle("-fx-background-color: #2A2A2A; -fx-text-fill: white;");
        emailField.setStyle("-fx-background-color: #2A2A2A; -fx-text-fill: white;");
        nicknameField.setStyle("-fx-background-color: #2A2A2A; -fx-text-fill: white;");
        messageLabel.setStyle("-fx-text-fill: #1DB954;");

        view.setPadding(new Insets(20));
        view.setAlignment(Pos.CENTER);

        view.getChildren().addAll(
            createTitle(),
            new Label("Username"), usernameField,
            new Label("Password"), passwordField,
            new Label("Password Confirmation"), passwordConfirmationField,
            new Label("Email"), emailField,
            new Label("Nickname"), nicknameField,
            updateProfileButton,
            messageLabel
        );

        updateProfileButton.setOnAction(e -> handleUpdateProfile(stage));
    }

    private Label createTitle() {
        Label title = new Label("Profile");
        title.setFont(new Font("Arial", 30));
        title.setTextFill(Color.WHITE);
        return title;
    }

    private void handleUpdateProfile(Stage stage) {
        String error = UserController.getInstance().updateProfile(
            MainMenuController.getInstance().getLoggedInUser(),
            usernameField.getText(),
            passwordField.getText(),
            passwordConfirmationField.getText(),
            emailField.getText(),
            nicknameField.getText());
        if (error.equals("")) {
            HomeScreen homeScreen = new HomeScreen(stage);
            stage.setScene(new Scene(homeScreen.getView(), 600, 800));
        } else {
            messageLabel.setText(error);
        }
    }

    public VBox getView() {
        return view;
    }
}
