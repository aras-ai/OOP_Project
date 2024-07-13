package citywars.view;

import citywars.controller.GamePlayController;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import citywars.model.Card;
import citywars.model.User;

public class GameScreen {
    private User firstPlayer;
    private User secondPlayer;
    private VBox layout;
    private GridPane player1Grid;
    private GridPane player2Grid;
    private Label player1HPLabel;
    private Label player2HPLabel;
    private Label player1DamageLabel;
    private Label player2DamageLabel;
    private Label turnLabel;
    private int currentPlayerTurn;
    private GamePlayController gamePlayController;
    private Card selectedCard;

    public GameScreen(Stage stage, User firstPlayer, User secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.currentPlayerTurn = 1; // Start with player 1
        this.gamePlayController = GamePlayController.getInstance();
        gamePlayController.setPlayers(firstPlayer, secondPlayer);

        layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));

        player1HPLabel = new Label("Player 1 HP: " + firstPlayer.getMaxHP());
        player2HPLabel = new Label("Player 2 HP: " + secondPlayer.getMaxHP());
        player1DamageLabel = new Label("Player 1 Damage: 0");
        player2DamageLabel = new Label("Player 2 Damage: 0");
        turnLabel = new Label("Current Turn: Player " + currentPlayerTurn);

        player1Grid = createGrid(1);
        player2Grid = createGrid(2);

        layout.getChildren().addAll(player1HPLabel, player2HPLabel, player1DamageLabel, player2DamageLabel, turnLabel, new Label("Player 1 Board"), player1Grid, new Label("Player 2 Board"), player2Grid);

        updateGameState();
    }

    private GridPane createGrid(int playerNumber) {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(5);
        grid.setVgap(5);
        for (int row = 0; row < 1; row++) {
            for (int col = 0; col < 16; col++) {
                Button cell = new Button();
                cell.setMinSize(50, 50);
                final int rowIndex = row;
                final int colIndex = col;
                cell.setOnAction(e -> handleCellClick(playerNumber, rowIndex, colIndex));
                grid.add(cell, col, row);
            }
        }
        return grid;
    }

    private void handleCellClick(int playerNumber, int rowIndex, int colIndex) {
        if (selectedCard != null && currentPlayerTurn == playerNumber) {
            gamePlayController.placeCard(selectedCard, colIndex, playerNumber);
            selectedCard = null;
            updateGameState();
        }
    }

    private void updateGameState() {
        player1HPLabel.setText("Player 1 HP: " + firstPlayer.getMaxHP());
        player2HPLabel.setText("Player 2 HP: " + secondPlayer.getMaxHP());
        player1DamageLabel.setText("Player 1 Damage: " + gamePlayController.calculatePlayerDamage(1));
        player2DamageLabel.setText("Player 2 Damage: " + gamePlayController.calculatePlayerDamage(2));
        turnLabel.setText("Current Turn: Player " + currentPlayerTurn);

        // Update the board representation
        updateBoard(player1Grid, gamePlayController.getBoardState(1));
        updateBoard(player2Grid, gamePlayController.getBoardState(2));

        if (gamePlayController.isGameOver()) {
            endGame();
        } else {
            currentPlayerTurn = currentPlayerTurn == 1 ? 2 : 1;
        }
    }

    private void updateBoard(GridPane grid, int[] boardState) {
        for (int col = 0; col < 16; col++) {
            Button cell = (Button) getNodeByRowColumnIndex(0, col, grid);
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
    }

    private javafx.scene.Node getNodeByRowColumnIndex(final int row, final int column, GridPane gridPane) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                return node;
            }
        }
        return null;
    }

    public VBox getView() {
        return layout;
    }
}
