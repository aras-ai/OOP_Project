package citywars.view;

import citywars.controller.GamePlayController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import citywars.model.Card;
import citywars.model.User;

import java.util.List;

public class GameScreen {
    private User firstPlayer;
    private User secondPlayer;
    private GridPane gameBoard;
    private VBox player1HandBox;
    private VBox player2HandBox;
    private Label player1InfoLabel;
    private Label player2InfoLabel;
    private Label turnLabel;
    private int currentPlayerTurn;
    private int[][] board;
    private GamePlayController gamePlayController;
    private Card selectedCard;
    private BorderPane layout;

    public GameScreen(Stage stage, User firstPlayer, User secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.board = new int[2][16];
        this.currentPlayerTurn = 1; // Start with player 1
        this.gamePlayController = GamePlayController.getInstance();
        gamePlayController.setPlayers(firstPlayer, secondPlayer);

        layout = new BorderPane();
        layout.setPadding(new Insets(10, 10, 10, 10));

        HBox topArea = new HBox(10);
        player1HandBox = new VBox(10);
        player1HandBox.setPadding(new Insets(10));
        player1InfoLabel = new Label("Player 1\nHP: " + firstPlayer.getMaxHP() + "\nDamage: 0");
        turnLabel = new Label("Current Turn: Player " + currentPlayerTurn);
        topArea.getChildren().addAll(player1HandBox, turnLabel, player1InfoLabel);
        topArea.setAlignment(Pos.CENTER);

        // Center area
        gameBoard = createGameBoard();

        // Bottom area
        HBox bottomArea = new HBox(10);
        player2HandBox = new VBox(10);
        player2HandBox.setPadding(new Insets(10));
        player2InfoLabel = new Label("Player 2\nHP: " + secondPlayer.getMaxHP() + "\nDamage: 0");
        bottomArea.getChildren().addAll(player2InfoLabel, player2HandBox);
        bottomArea.setAlignment(Pos.CENTER);

        // Set positions in layout
        layout.setTop(topArea);
        layout.setCenter(gameBoard);
        layout.setBottom(bottomArea);

        updateCardDisplays();
        updateGameState();
    }

    private GridPane createGameBoard() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(5);
        grid.setVgap(5);
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 16; col++) {
                Button cell = new Button();
                cell.setMinSize(50, 50);
                final int rowIndex = row;
                final int colIndex = col;
                cell.setOnAction(e -> handleCellClick(rowIndex, colIndex));
                grid.add(cell, col, row);
            }
        }
        return grid;
    }

    private void handleCellClick(int rowIndex, int colIndex) {
        if (selectedCard != null && currentPlayerTurn == (rowIndex + 1)) {
            gamePlayController.placeCard(selectedCard, colIndex, currentPlayerTurn);
            selectedCard = null;
            updateGameState();
        }
    }

    private void updateGameState() {
        player1InfoLabel.setText("Player 1\nHP: " + firstPlayer.getMaxHP() + "\nDamage: " + gamePlayController.calculatePlayerDamage(1));
        player2InfoLabel.setText("Player 2\nHP: " + secondPlayer.getMaxHP() + "\nDamage: " + gamePlayController.calculatePlayerDamage(2));
        turnLabel.setText("Current Turn: Player " + currentPlayerTurn);

        // Update the board representation
        updateBoard(gamePlayController.getBoardState(1), 0);
        updateBoard(gamePlayController.getBoardState(2), 1);

        if (gamePlayController.isGameOver()) {
            endGame();
        } else {
            currentPlayerTurn = currentPlayerTurn == 1 ? 2 : 1;
            updateCardDisplays();
        }
    }

    private void updateBoard(int[] boardState, int rowIndex) {
        for (int col = 0; col < 16; col++) {
            Button cell = (Button) getNodeByRowColumnIndex(rowIndex, col, gameBoard);
            cell.setText(boardState[col] > 0 ? "X" : "");
        }
    }

    private void endGame() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        if (firstPlayer.getMaxHP() <= 0) {
            alert.setContentText("Player 2 Wins!");
        } else if (secondPlayer.getMaxHP() <= 0) {
            alert.setContentText("Player 1 Wins!");
        } else {
            alert.setContentText("It's a Draw!");
        }
        alert.showAndWait();
        // GameController.getInstance().endGame();
    }

    private void updateCardDisplays() {
        updateCardDisplay(player1HandBox, gamePlayController.getHand(1));
        updateCardDisplay(player2HandBox, gamePlayController.getHand(2));
    }

    private void updateCardDisplay(VBox cardBox, List<Card> hand) {
        cardBox.getChildren().clear();
        for (Card card : hand) {
            Button cardButton = new Button(card.getName() + " - " + card.getAttackDefense());
            cardButton.setOnAction(e -> selectedCard = card);
            cardBox.getChildren().add(cardButton);
        }
    }

    private javafx.scene.Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                return node;
            }
        }
        return null;
    }

    public BorderPane getView() {
        return layout;
    }
}
