package citywars.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import citywars.controller.CaptchaGenerator;
import citywars.controller.SecurityQuestion;
import citywars.controller.UserController;

public class SignUpScreen {
    private VBox view;
    private TextField usernameField, emailField, nicknameField, answerField, captchaAnswer;
    private PasswordField passwordField, passwordConfirmationField;
    private ComboBox<String> questionComboBox;
    private HBox captchaBox;
    private Button createAccountButton, generateRandomPasswordButton;
    private Label messageLabel, captchaText;
    private Alert passwordDialog;

    public SignUpScreen(Stage stage) {
        view = new VBox(10);
        captchaBox = new HBox(10);
        usernameField = new TextField();
        passwordField = new PasswordField();
        passwordConfirmationField = new PasswordField();
        emailField = new TextField();
        nicknameField = new TextField();
        questionComboBox = new ComboBox<>();
        answerField = new TextField();
        captchaAnswer = new TextField();
        createAccountButton = new Button("Create Account");
        generateRandomPasswordButton = new Button("Generate Random Password");
        messageLabel = new Label();
        captchaText = new Label();
        passwordDialog = new Alert(AlertType.INFORMATION);

        usernameField.setPromptText("Username");
        passwordField.setPromptText("Password");
        passwordConfirmationField.setPromptText("Password Confirmation");
        emailField.setPromptText("Email");
        nicknameField.setPromptText("Nickname");
        answerField.setPromptText("Security Answer");
        
        createAccountButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-size: 14;");
        generateRandomPasswordButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-size: 14;");
        messageLabel.setTextFill(Color.RED);

        questionComboBox.getItems().addAll(SecurityQuestion.questions);

        view.setStyle("-fx-background-color: #121212;");
        captchaBox.setStyle("-fx-background-color: #121212;");
        usernameField.setStyle("-fx-background-color: #2A2A2A; -fx-text-fill: white;");
        passwordField.setStyle("-fx-background-color: #2A2A2A; -fx-text-fill: white;");
        passwordConfirmationField.setStyle("-fx-background-color: #2A2A2A; -fx-text-fill: white;");
        emailField.setStyle("-fx-background-color: #2A2A2A; -fx-text-fill: white;");
        nicknameField.setStyle("-fx-background-color: #2A2A2A; -fx-text-fill: white;");
        answerField.setStyle("-fx-background-color: #2A2A2A; -fx-text-fill: white;");
        questionComboBox.setStyle("-fx-background-color: #2A2A2A; -fx-text-fill: white;");
        captchaAnswer.setStyle("-fx-background-color: #2A2A2A; -fx-text-fill: white;");
        messageLabel.setStyle("-fx-text-fill: #1DB954;");
        captchaText.setStyle("-fx-text-fill: #1DB954;");

        view.setPadding(new Insets(20));
        view.setAlignment(Pos.CENTER);

        captchaBox.setPadding(new Insets(20));
        captchaBox.setAlignment(Pos.CENTER);

        captchaBox.getChildren().addAll(captchaText, captchaAnswer);

        view.getChildren().addAll(
            createTitle(),
            new Label("Username"), usernameField,
            new Label("Password"), passwordField,
            new Label("Password Confirmation"), passwordConfirmationField,
            new Label("Email"), emailField,
            new Label("Nickname"), nicknameField,
            new Label("Security Question"), questionComboBox,
            new Label("Security Answer"), answerField,
            new Label("Captcha"), captchaBox,
            createAccountButton,
            generateRandomPasswordButton,
            messageLabel
        );

        setCaptcha();

        createAccountButton.setOnAction(e -> handleCreateAccount(stage));
        generateRandomPasswordButton.setOnAction(e -> {
            String password = UserController.getInstance().generateRandomPassword();
            passwordDialog.setHeaderText("Generated Password");
            passwordDialog.setContentText(password);
            passwordDialog.show();
            passwordField.setText(password);
        });
    }

    private Label createTitle() {
        Label title = new Label("Sign Up");
        title.setFont(new Font("Arial", 30));
        title.setTextFill(Color.WHITE);
        return title;
    }

    private void handleCreateAccount(Stage stage) {
        int result = 1000;
        try {
            result = Integer.parseInt(captchaAnswer.getText());
        } catch (NumberFormatException e) {
            setCaptcha();
            messageLabel.setText("wrong captcha");
        }
        if (!CaptchaGenerator.getInstance().validateCaptcha(captchaText.getText(), result)) {
            setCaptcha();
            messageLabel.setText("wrong captcha");
            return;
        }

        String error = UserController.getInstance().createUser(
            usernameField.getText(),
            passwordField.getText(),
            passwordConfirmationField.getText(),
            emailField.getText(),
            nicknameField.getText(),
            questionComboBox.getValue(),
            answerField.getText());
        if (error.equals("")) {
            LoginScreen loginScreen = new LoginScreen(stage);
            stage.setScene(new Scene(loginScreen.getView(), 600, 800));
        } else {
            messageLabel.setText(error);
        }
    }

    public VBox getView() {
        return view;
    }

    private void setCaptcha() {
        String captcha = CaptchaGenerator.getInstance().generateCaptcha();
        captchaText.setText(captcha);
    }
}
