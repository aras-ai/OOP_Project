package citywars.view;

import citywars.model.GameRecord;

import java.util.List;

public class GameHistoryView extends MainView {
    public void display() {
        System.out.print("HISTORY> ");
    }

    public void displayGameHistory(List<GameRecord> gameHistory) {
        System.out.println("Game History:");
        for (GameRecord record : gameHistory) {
            System.out.println(record);
        }
    }

    public void displayPage(List<GameRecord> gameHistory, int page, int itemsPerPage) {
        int start = (page - 1) * itemsPerPage;
        int end = Math.min(start + itemsPerPage, gameHistory.size());
        System.out.println("Page " + page);
        for (int i = start; i < end; i++) {
            System.out.println(gameHistory.get(i));
        }
    }
}
