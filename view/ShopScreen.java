package citywars.view;

import citywars.controller.MainMenuController;
import citywars.controller.ShopController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import citywars.model.Card;

import java.util.List;

public class ShopScreen {
    private Label coinsLabel;
    private VBox cardsBox, view;

    public ShopScreen(Stage stage) {
        view = new VBox(20);
        view.setPadding(new Insets(20));
        view.setAlignment(Pos.TOP_CENTER);
        view.setStyle("-fx-background-color: #121212;");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        coinsLabel = new Label("Coins: " + MainMenuController.getInstance().getLoggedInUser().getCoins());
        coinsLabel.setStyle("-fx-text-fill: #1DB954;");
        GridPane.setConstraints(coinsLabel, 0, 0);

        cardsBox = new VBox();
        cardsBox.setSpacing(10);
        GridPane.setConstraints(cardsBox, 0, 1);

        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(e -> refreshCards());
        refreshButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-size: 14;");
        GridPane.setConstraints(refreshButton, 0, 2);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            HomeScreen homeScreen = new HomeScreen(stage);
            stage.setScene(new Scene(homeScreen.getView(), 600, 800));
        });
        backButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-size: 14;");
        GridPane.setConstraints(backButton, 0, 3);

        grid.getChildren().addAll(coinsLabel, cardsBox, refreshButton, backButton);
        view.getChildren().add(grid);

        refreshCards();
    }

    private void refreshCards() {
        cardsBox.getChildren().clear();
        List<Card> allCards = ShopController.getInstance().getAllCards();

        for (Card card : allCards) {
            Label inStockLabel = new Label(
                MainMenuController.getInstance().getLoggedInUser().getCards()
                .stream().filter(e -> e.equals(card.getName())).count() + " in stock"
            );
            Button cardButton = new Button(card.getName() + " - Price: " + card.getUpgradeCost() + " coins");
            cardButton.setOnAction(e -> handleBuyCard(card));
            cardButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-size: 14;");
            cardsBox.getChildren().addAll(cardButton, inStockLabel);
        }

        coinsLabel.setText("Coins: " + MainMenuController.getInstance().getLoggedInUser().getCoins());
    }

    private void handleBuyCard(Card card) {
        if (MainMenuController.getInstance().getLoggedInUser().getCoins() >= card.getUpgradeCost()) {
            ShopController.getInstance().buyCard(card.getName());
            refreshCards();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Insufficient Funds");
            alert.setHeaderText(null);
            alert.setContentText("You don't have enough coins to buy this card.");
            alert.showAndWait();
        }
    }

    public VBox getView() {
        return view;
    }
}
