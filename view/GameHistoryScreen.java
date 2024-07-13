package citywars.view;

import java.util.List;

import citywars.controller.GameHistoryController;
import citywars.controller.MainMenuController;
import citywars.model.GameRecord;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class GameHistoryScreen {
    private VBox view, scoreboard;
    private HBox headerRow, paginateBox;
    private Button prevButton, nextButton, sortDateButton, sortResultButton, sortOpponentButton, sortLevelButton;
    private String currentSort = "date";
    private String currentOrder = "ASC";
    private int currentPage = 1;
    List<GameRecord> gameHistory;

    public GameHistoryScreen(Stage stage) {
        view = new VBox(20);
        view.setAlignment(Pos.CENTER);
        view.setPadding(new Insets(20));
        view.setStyle("-fx-background-color: #121212;");
        scoreboard = new VBox(20);
        scoreboard.setAlignment(Pos.CENTER);
        headerRow = new HBox(10);
        headerRow.setAlignment(Pos.CENTER);
        paginateBox = new HBox(10);
        paginateBox.setAlignment(Pos.CENTER);
        prevButton = new Button("Prev");
        prevButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-size: 14;");
        nextButton = new Button("Next");
        nextButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-size: 14;");

        sortDateButton = new Button("Date");
        sortDateButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-size: 14;");
        sortResultButton = new Button("Result");
        sortResultButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-size: 14;");
        sortOpponentButton = new Button("Opponent");
        sortOpponentButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-size: 14;");
        sortLevelButton = new Button("Level");
        sortLevelButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-size: 14;");

        headerRow.getChildren().addAll(sortDateButton, sortResultButton, sortOpponentButton, sortLevelButton);
        paginateBox.getChildren().addAll(prevButton, nextButton);
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> {
            HomeScreen homeScreen = new HomeScreen(stage);
            stage.setScene(new Scene(homeScreen.getView(), 600, 800));
        });
        backButton.setStyle("-fx-background-color: #1DB954; -fx-text-fill: white; -fx-font-size: 14;");
        view.getChildren().addAll(headerRow, scoreboard, paginateBox, backButton);

        gameHistory = MainMenuController.getInstance().getLoggedInUser().getGameHistory();

        setScoreboard();

        nextButton.setOnAction(e -> {
            currentPage += 1;
            setScoreboard();
        });
        prevButton.setOnAction(e -> {
            currentPage -= 1;
            setScoreboard();
        });
        sortDateButton.setOnAction(e -> {
            if (currentSort.equals("date")) {
                currentOrder = currentOrder.equals("asc") ? "desc" : "asc";
            } else {
                currentSort = "date";
                currentOrder = "asc";
            }
            setScoreboard();
        });
        sortResultButton.setOnAction(e -> {
            if (currentSort.equals("result")) {
                currentOrder = currentOrder.equals("asc") ? "desc" : "asc";
            } else {
                currentSort = "result";
                currentOrder = "asc";
            }
            setScoreboard();
        });
        sortOpponentButton.setOnAction(e -> {
            if (currentSort.equals("opponent")) {
                currentOrder = currentOrder.equals("asc") ? "desc" : "asc";
            } else {
                currentSort = "opponent";
                currentOrder = "asc";
            }
            setScoreboard();
        });
        sortLevelButton.setOnAction(e -> {
            if (currentSort.equals("level")) {
                currentOrder = currentOrder.equals("asc") ? "desc" : "asc";
            } else {
                currentSort = "level";
                currentOrder = "asc";
            }
            setScoreboard();
        });
    }

    public void setScoreboard() {
        if (currentPage < 1) currentPage = 1;
        if ((currentPage - 1)* 10 > gameHistory.size()) currentPage = (int) Math.ceil(gameHistory.size() / 10);
        if (gameHistory.size() == 0) currentPage = 1;
        GameHistoryController.getInstance().sortGameHistory(gameHistory, currentSort, currentOrder);
        scoreboard.getChildren().clear();
        for (int i = (currentPage - 1) * 10; i < currentPage * 10; i++) {
            if (i == gameHistory.size()) break;
            GameRecord record = gameHistory.get(i);
            HBox row = new HBox(5);
            row.getChildren().addAll(
                new Label(record.getDate().toString()),
                new Label(record.getResult()),
                new Label(record.getOpponentName()),
                new Label(record.getOpponentLevel() + "")
            );
            scoreboard.getChildren().add(row);
        }
    }

    public VBox getView() {
        return view;
    }
}
