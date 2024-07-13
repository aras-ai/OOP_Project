package citywars.view;

import citywars.controller.MainMenuController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class HomeScreen {
    private VBox view, profileDetails;
    private HBox profileBox;
    private Label nicknameLabel, levelLabel,coinLabel, XPLabel, HPLabel;
    private ImageView profileImageView;
    private Button startGameButton, gameHistoryButton, shopButton, logoutButton;

    public HomeScreen(Stage stage) {
        view = new VBox(20);
        profileDetails = new VBox(10);
        profileBox = new HBox(10);

        nicknameLabel = new Label(MainMenuController.getInstance().getLoggedInUser().getNickname());
        nicknameLabel.setStyle("-fx-text-fill: #1DB954;");
        levelLabel = new Label("LVL: " + MainMenuController.getInstance().getLoggedInUser().getLevel());
        levelLabel.setStyle("-fx-text-fill: #1DB954;");
        coinLabel = new Label("COIN: " + MainMenuController.getInstance().getLoggedInUser().getCoins());
        coinLabel.setStyle("-fx-text-fill: #1DB954;");
        XPLabel = new Label("XP: " + MainMenuController.getInstance().getLoggedInUser().getXp());
        XPLabel.setStyle("-fx-text-fill: #1DB954;");
        HPLabel = new Label("HP: " + MainMenuController.getInstance().getLoggedInUser().getMaxHP());
        HPLabel.setStyle("-fx-text-fill: #1DB954;");

        String uri = getClass().getResource("../assets/profile.jpg").toExternalForm();
        profileImageView = new ImageView(uri);
        profileImageView.setFitWidth(120);
        profileImageView.setFitHeight(120);

        startGameButton = new Button("Start Game");
        startGameButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-size: 14;");
        gameHistoryButton = new Button("Game History");
        gameHistoryButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-size: 14;");
        shopButton = new Button("Shop");
        shopButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-size: 14;");
        logoutButton = new Button("Log Out");
        logoutButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-size: 14;");

        view.setPadding(new Insets(20));
        view.setAlignment(Pos.TOP_CENTER);
        profileBox.setPadding(new Insets(10));
        profileBox.setAlignment(Pos.TOP_LEFT);

        view.setStyle("-fx-background-color: #121212;");

        profileDetails.getChildren().addAll(
            nicknameLabel, levelLabel, coinLabel, XPLabel, HPLabel
        );
        profileBox.getChildren().addAll(profileImageView, profileDetails);

        view.getChildren().addAll(profileBox, startGameButton, gameHistoryButton, shopButton, logoutButton);

        logoutButton.setOnAction(e -> {
            MainMenuController.getInstance().setLoggedInUser(null);
            LoginScreen loginScreen = new LoginScreen(stage);
            stage.setScene(new Scene(loginScreen.getView(), 600, 800));
        });

        profileBox.setOnMouseClicked(e -> {
            ProfileScreen ProfileScreen = new ProfileScreen(stage);
            stage.setScene(new Scene(ProfileScreen.getView(), 600, 800));
        });

        gameHistoryButton.setOnAction(e -> {
            GameHistoryScreen gameHistoryScreen = new GameHistoryScreen(stage);
            stage.setScene(new Scene(gameHistoryScreen.getView(), 600, 800));
        });

        shopButton.setOnAction(e -> {
            ShopScreen shopScreen = new ShopScreen(stage);
            stage.setScene(new Scene(shopScreen.getView(), 600, 800));
        });

        startGameButton.setOnAction(e -> {
            SecondPlayerLoginScreen secondPlayerLoginScreen = new SecondPlayerLoginScreen(stage);
            stage.setScene(new Scene(secondPlayerLoginScreen.getView(), 600, 800));
        });
    }

    public VBox getView() {
        return view;
    }
}
