package citywars.controller;

import citywars.Main;
import citywars.model.GameRecord;
import citywars.model.User;
import citywars.view.GameHistoryView;

import java.util.Comparator;
import java.util.List;

public class GameHistoryController {
    private static GameHistoryController instance;
    private GameHistoryView gameHistoryView;

    private GameHistoryController() {
        gameHistoryView = new GameHistoryView();
    }

    public static GameHistoryController getInstance() {
        if (instance == null) {
            instance = new GameHistoryController();
        }
        return instance;
    }

    public void displayGameHistory(User loggedInUser) {
        List<GameRecord> gameHistory = loggedInUser.getGameHistory();
        gameHistoryView.displayGameHistory(gameHistory);

        while (true) {
            String command = gameHistoryView.getInput("Enter command (back, sort, page): ");
            switch (command) {
                case "back":
                    Main.setCurrentView(MainMenuController.getInstance().getView());
                    return;
                case "sort":
                    sortGameHistory(gameHistory);
                    break;
                case "page":
                    paginateGameHistory(gameHistory);
                    break;
                default:
                    gameHistoryView.displayMessage("Unknown command");
                    break;
            }
        }
    }

    private void sortGameHistory(List<GameRecord> gameHistory) {
        String sortCriteria = gameHistoryView.getInput("Enter sort criteria (date, result, opponent, level): ");
        String order = gameHistoryView.getInput("Enter order (asc, desc): ");

        Comparator<GameRecord> comparator;
        switch (sortCriteria) {
            case "date":
                comparator = Comparator.comparing(GameRecord::getDate);
                break;
            case "result":
                comparator = Comparator.comparing(GameRecord::getResult);
                break;
            case "opponent":
                comparator = Comparator.comparing(GameRecord::getOpponentName);
                break;
            case "level":
                comparator = Comparator.comparing(GameRecord::getOpponentLevel);
                break;
            default:
                gameHistoryView.displayMessage("Invalid sort criteria");
                return;
        }

        if ("desc".equals(order)) {
            comparator = comparator.reversed();
        }

        gameHistory.sort(comparator);
        gameHistoryView.displayGameHistory(gameHistory);
    }

    private void paginateGameHistory(List<GameRecord> gameHistory) {
        int itemsPerPage = 10;
        int totalPages = (int) Math.ceil(gameHistory.size() / (double) itemsPerPage);
        int currentPage = 1;

        while (true) {
            gameHistoryView.displayPage(gameHistory, currentPage, itemsPerPage);

            String command = gameHistoryView.getInput("Enter page command (next, previous, page no, back): ");
            switch (command) {
                case "next":
                    if (currentPage < totalPages) {
                        currentPage++;
                    } else {
                        gameHistoryView.displayMessage("Already on the last page");
                    }
                    break;
                case "previous":
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        gameHistoryView.displayMessage("Already on the first page");
                    }
                    break;
                case "back":
                    return;
                default:
                    try {
                        int pageNo = Integer.parseInt(command);
                        if (pageNo >= 1 && pageNo <= totalPages) {
                            currentPage = pageNo;
                        } else {
                            gameHistoryView.displayMessage("Invalid page number");
                        }
                    } catch (NumberFormatException e) {
                        gameHistoryView.displayMessage("Invalid command");
                    }
                    break;
            }
        }
    }
}
