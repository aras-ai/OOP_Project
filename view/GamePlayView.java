package citywars.view;

import citywars.model.Card;
import citywars.model.User;

import java.util.List;

public class GamePlayView extends MainView {
    public void display() {
        System.out.print("GAME> ");
    }

    public void displayGameState(User firstPlayer, User secondPlayer, List<Card> firstPlayerCards, List<Card> secondPlayerCards, int[][] board, int currentPlayerTurn) {
        System.out.println("Current Game State:");
        System.out.println("Player 1: " + firstPlayer.getUsername() + " (HP: " + firstPlayer.getMaxHP() + ")");
        System.out.println("Player 2: " + secondPlayer.getUsername() + " (HP: " + secondPlayer.getMaxHP() + ")");
        System.out.println("Board State:");
        displayBoard(board);

        System.out.println("Player 1 Cards:");
        displayCards(firstPlayerCards);

        System.out.println("Player 2 Cards:");
        displayCards(secondPlayerCards);

        System.out.println("Current Turn: Player " + currentPlayerTurn);
    }

    public void displayCardDetails(Card card) {
        System.out.println("Card Details:");
        System.out.println("Name: " + card.getName());
        System.out.println("Type: " + card.getType());
        System.out.println("Duration: " + card.getDuration());
        System.out.println("Player Damage: " + card.getDamage());
        System.out.println("Attack/Defense Points: " + card.getAttackDefense());
    }

    public void displayRoundResult(int[][] board, User firstPlayer, User secondPlayer) {
        System.out.println("Round Result:");
        displayBoard(board);
        System.out.println("Player 1 HP: " + firstPlayer.getMaxHP());
        System.out.println("Player 2 HP: " + secondPlayer.getMaxHP());
    }

    public void displayEndGameResult(User winner, User loser, int xpGain, int coinGain) {
        System.out.println("Game Over!");
        System.out.println("Winner: " + winner.getUsername());
        System.out.println("Loser: " + loser.getUsername());
        System.out.println("XP Gain: " + xpGain);
        System.out.println("Coin Gain: " + coinGain);
    }

    public void displayBoard(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            System.out.print("Player " + (i + 1) + " Board: ");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void displayCards(List<Card> cards) {
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);
            System.out.println(i + ". " + card.getName() + " (Type: " + card.getType() + ", Duration: " + card.getDuration() + ", Damage: " + card.getDamage() + ", Attack/Defense: " + card.getAttackDefense() + ")");
        }
    }
}
