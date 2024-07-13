package citywars.view;

import citywars.controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class LoginScreen {
    private VBox view;
    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton, signUpButton, forgetButton;
    private Label messageLabel;

    public LoginScreen(Stage stage) {
        view = new VBox(10);
        usernameField = new TextField();
        passwordField = new PasswordField();
        loginButton = new Button("Login");
        signUpButton = new Button("Sign Up");
        forgetButton = new Button("Forget Password");
        messageLabel = new Label();
        
        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");
        loginButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-size: 14;");
        signUpButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-size: 14;");
        forgetButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-size: 14;");
        messageLabel.setTextFill(Color.RED);

        view.setStyle("-fx-background-color: #121212;");
        usernameField.setStyle("-fx-background-color: #2A2A2A; -fx-text-fill: white;");
        passwordField.setStyle("-fx-background-color: #2A2A2A; -fx-text-fill: white;");
        messageLabel.setStyle("-fx-text-fill: #1DB954;");
        
        view.setPadding(new Insets(20));
        view.setAlignment(Pos.CENTER);
        
        view.getChildren().addAll(
            createTitle(),
            new Label("Username"), usernameField, 
            new Label("Password"), passwordField, 
            loginButton, signUpButton, forgetButton,
            messageLabel
        );

        loginButton.setOnAction(e -> handleLogin(stage));
        signUpButton.setOnAction(e -> handleSignUp(stage));
        forgetButton.setOnAction(e -> handleForget(stage));
    }

    private Label createTitle() {
        Label title = new Label("City Wars: Tokyo Reign");
        title.setFont(new Font("Arial", 30));
        title.setTextFill(Color.WHITE);
        return title;
    }

    private void handleLogin(Stage stage) {
        String error = UserController.getInstance().loginUser(usernameField.getText(), passwordField.getText());
        if (error.equals("")) {
            HomeScreen homeScreen = new HomeScreen(stage);
            stage.setScene(new Scene(homeScreen.getView(), 600, 800));
        } else {
            messageLabel.setText(error);
        }
    }

    private void handleSignUp(Stage stage) {
        SignUpScreen signUpScreen = new SignUpScreen(stage);
        stage.setScene(new Scene(signUpScreen.getView(), 600, 800));
    }

    private void handleForget(Stage stage) {
        ForgetScreen forgetScreen = new ForgetScreen(stage);
        stage.setScene(new Scene(forgetScreen.getView(), 600, 800));
    }

    public VBox getView() {
        return view;
    }
}
