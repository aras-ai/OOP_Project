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

public class ForgetScreen {
    private VBox view;
    private TextField usernameField, answerField;
    private PasswordField passwordField, passwordConfirmationField;
    private Button getQuestionButton, forgetPasswordButton;
    private Label messageLabel, questionLabel;

    public ForgetScreen(Stage stage) {
        view = new VBox(10);
        usernameField = new TextField();
        answerField = new TextField();
        passwordField = new PasswordField();
        passwordConfirmationField = new PasswordField();
        getQuestionButton = new Button("Get Question");
        forgetPasswordButton = new Button("Forget Password");
        messageLabel = new Label();
        questionLabel = new Label();
        
        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");
        passwordConfirmationField.setPromptText("Password Confirmation");
        answerField.setPromptText("Security Answer");
        forgetPasswordButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-size: 14;");
        getQuestionButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-size: 14;");
        questionLabel.setTextFill(Color.WHITE);
        messageLabel.setTextFill(Color.RED);

        view.setStyle("-fx-background-color: #121212;");
        usernameField.setStyle("-fx-background-color: #2A2A2A; -fx-text-fill: white;");
        passwordField.setStyle("-fx-background-color: #2A2A2A; -fx-text-fill: white;");
        passwordConfirmationField.setStyle("-fx-background-color: #2A2A2A; -fx-text-fill: white;");
        answerField.setStyle("-fx-background-color: #2A2A2A; -fx-text-fill: white;");
        messageLabel.setStyle("-fx-text-fill: #1DB954;");
        questionLabel.setStyle("-fx-text-fill: #1DB954;");
        
        view.setPadding(new Insets(20));
        view.setAlignment(Pos.CENTER);
        
        view.getChildren().addAll(
            createTitle(),
            new Label("Username"), usernameField, 
            new Label("Password"), passwordField,
            new Label("Password Confirmation"), passwordConfirmationField,
            new Label("Security Question"), questionLabel,
            new Label("Security Answer"), answerField,
            getQuestionButton, forgetPasswordButton, 
            messageLabel
        );

        getQuestionButton.setOnAction(e -> handleQuestion(stage));
        forgetPasswordButton.setOnAction(e -> handleForget(stage));
    }

    private Label createTitle() {
        Label title = new Label("Forget Password");
        title.setFont(new Font("Arial", 30));
        title.setTextFill(Color.WHITE);
        return title;
    }

    private void handleForget(Stage stage) {
        String error = UserController.getInstance().forgetPassword(usernameField.getText(), answerField.getText(), passwordField.getText(), passwordConfirmationField.getText());
        if (error.equals("")) {
            LoginScreen loginScreen = new LoginScreen(stage);
            stage.setScene(new Scene(loginScreen.getView(), 600, 800));
        } else {
            messageLabel.setText(error);
        }
    }

    private void handleQuestion(Stage stage) {
        String question = UserController.getInstance().getSecurityQuestion(usernameField.getText());
        questionLabel.setText(question);
    }

    public VBox getView() {
        return view;
    }
}
