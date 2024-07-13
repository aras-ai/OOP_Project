package citywars.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import citywars.controller.MainMenuController;
import citywars.model.User;

public class SecondPlayerLoginScreen {
    private VBox layout;
    public SecondPlayerLoginScreen(Stage stage) {
        layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setStyle("-fx-background-color: #121212;");

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-size: 14;");
        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: #1DB954;");

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            User user = citywars.model.User.getUserDatabase().getUser(username);
            if (user != null && user.getPassword().equals(password)) {
                CharacterSelectionScreen characterSelectionScreen = new CharacterSelectionScreen(stage);
                characterSelectionScreen.setPlayers(MainMenuController.getInstance().getLoggedInUser(), user);
                stage.setScene(new Scene(characterSelectionScreen.getView(), 600, 800));
            } else {
                messageLabel.setText("Invalid username or password.");
            }
        });

        layout.getChildren().addAll(usernameLabel, usernameField, passwordLabel, passwordField, loginButton, messageLabel);
    }

    public VBox getView() {
        return layout;
    }
}
