package citywars.controller;

import citywars.model.Card;
import citywars.model.User;

public class GamePlayController {
    private static GamePlayController instance;
    private User firstPlayer;
    private User secondPlayer;
    private int[][] board; // 2 rows, 16 columns each

    private GamePlayController() {
        board = new int[2][16];
    }

    public static GamePlayController getInstance() {
        if (instance == null) {
            instance = new GamePlayController();
        }
        return instance;
    }

    public void setPlayers(User firstPlayer, User secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        initializeBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 16; col++) {
                board[row][col] = 0; // Empty board initialization
            }
        }
    }

    public void placeCard(Card card, int colIndex, int playerNumber) {
        int rowIndex = playerNumber - 1;
        if (colIndex < 16) {
            board[rowIndex][colIndex] = card.getAttackDefense();
            applyCardEffect(card, rowIndex, colIndex);
        }
    }

    private void applyCardEffect(Card card, int rowIndex, int colIndex) {
        if ("healing".equals(card.getType())) {
            if (rowIndex == 0) {
                firstPlayer.setMaxHP(firstPlayer.getMaxHP() + card.getDamage());
            } else {
                secondPlayer.setMaxHP(secondPlayer.getMaxHP() + card.getDamage());
            }
        } else if ("damage".equals(card.getType())) {
            if (rowIndex == 0) {
                secondPlayer.setMaxHP(secondPlayer.getMaxHP() - card.getDamage());
            } else {
                firstPlayer.setMaxHP(firstPlayer.getMaxHP() - card.getDamage());
            }
        }
    }

    public int calculatePlayerDamage(int playerNumber) {
        int damage = 0;
        int rowIndex = playerNumber - 1;
        for (int col = 0; col < 16; col++) {
            damage += board[rowIndex][col];
        }
        return damage;
    }

    public int[] getBoardState(int playerNumber) {
        int rowIndex = playerNumber - 1;
        return board[rowIndex];
    }

    public boolean isGameOver() {
        return firstPlayer.getMaxHP() <= 0 || secondPlayer.getMaxHP() <= 0;
    }
}
