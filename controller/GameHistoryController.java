package citywars.controller;

import citywars.model.GameRecord;
import java.util.Comparator;
import java.util.List;

public class GameHistoryController {
    private static GameHistoryController instance;

    private GameHistoryController() {
    }

    public static GameHistoryController getInstance() {
        if (instance == null) {
            instance = new GameHistoryController();
        }
        return instance;
    }

    public void sortGameHistory(List<GameRecord> gameHistory, String sortCriteria, String order) {
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
                return;
        }

        if ("desc".equals(order)) {
            comparator = comparator.reversed();
        }

        gameHistory.sort(comparator);
    }
}
