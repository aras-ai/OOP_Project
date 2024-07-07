package citywars.controller;

import citywars.Main;
import citywars.model.Card;
import citywars.model.User;
import citywars.model.CardDatabase;
import citywars.view.GamePlayView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GamePlayController {
    private static GamePlayController instance;
    private GamePlayView gamePlayView;
    private User firstPlayer;
    private User secondPlayer;
    private List<Card> firstPlayerCards;
    private List<Card> secondPlayerCards;
    private CardDatabase cardDatabase;

    private int[][] board; // Board state representation
    private int currentPlayerTurn; // 1 for first player, 2 for second player
    private int remainingRounds;

    private GamePlayController() {
        gamePlayView = new GamePlayView();
        cardDatabase = new CardDatabase();
        board = new int[2][21]; // Assuming a board with 21 blocks per player
        remainingRounds = 4;
    }

    public static GamePlayController getInstance() {
        if (instance == null) {
            instance = new GamePlayController();
        }
        return instance;
    }

    public GamePlayView getView() {
        return gamePlayView;
    }

    public void setPlayers(User firstPlayer, User secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        initializeGame();
    }

    private void initializeGame() {
        // Initialize game state, distribute initial cards, etc.
        firstPlayerCards = drawInitialCards(firstPlayer);
        secondPlayerCards = drawInitialCards(secondPlayer);
        // Randomly select starting player
        currentPlayerTurn = new Random().nextBoolean() ? 1 : 2;
        gamePlayView.displayGameState(firstPlayer, secondPlayer, firstPlayerCards, secondPlayerCards, board,
                currentPlayerTurn);
    }

    private List<Card> drawInitialCards(User player) {
        // Draw 5 random cards from the player's deck
        List<Card> deck = cardDatabase.getDeck(player);
        Collections.shuffle(deck);
        return new ArrayList<>(deck.subList(0, 5));
    }

    public void handleCommand(String command) {
        if (command.startsWith("select card number")) {
            selectCard(command);
        } else if (command.startsWith("placing card number")) {
            placeCard(command);
        } else {
            gamePlayView.displayMessage("Unknown command");
        }
    }

    private void selectCard(String command) {
        String[] parts = command.split(" ");
        int cardNumber = Integer.parseInt(parts[3]);
        int playerNumber = Integer.parseInt(parts[5]);

        Card selectedCard;
        if (playerNumber == 1) {
            selectedCard = firstPlayerCards.get(cardNumber);
        } else {
            selectedCard = secondPlayerCards.get(cardNumber);
        }

        gamePlayView.displayCardDetails(selectedCard);
    }

    private void placeCard(String command) {
        String[] parts = command.split(" ");
        int cardNumber = Integer.parseInt(parts[3]);
        int blockNumber = Integer.parseInt(parts[6]);

        Card selectedCard;
        if (currentPlayerTurn == 1) {
            selectedCard = firstPlayerCards.get(cardNumber);
            firstPlayerCards.remove(cardNumber);
            firstPlayerCards.add(drawNewCard(firstPlayer));
        } else {
            selectedCard = secondPlayerCards.get(cardNumber);
            secondPlayerCards.remove(cardNumber);
            secondPlayerCards.add(drawNewCard(secondPlayer));
        }

        placeCardOnBoard(selectedCard, blockNumber);

        // Handle special effects of spell cards
        if ("spell".equals(selectedCard.getType())) {
            applySpellEffect(selectedCard);
        }

        gamePlayView.displayMessage("Card placed successfully.");
        gamePlayView.displayGameState(firstPlayer, secondPlayer, firstPlayerCards, secondPlayerCards, board,
                currentPlayerTurn);

        currentPlayerTurn = currentPlayerTurn == 1 ? 2 : 1;
        remainingRounds--;

        if (remainingRounds == 0) {
            endRound();
        }
    }

    private Card drawNewCard(User player) {
        List<Card> deck = cardDatabase.getDeck(player);
        Collections.shuffle(deck);
        return deck.get(0);
    }

    private void placeCardOnBoard(Card card, int startBlock) {
        // Place card on the board and update game state
        int endBlock = Math.min(startBlock + card.getDuration() - 1, 20);
        for (int i = startBlock; i <= endBlock; i++) {
            board[currentPlayerTurn - 1][i] = card.getAttackDefense();
        }
    }

    private void applySpellEffect(Card card) {
        switch (card.getName()) {
            case "Shield":
                // Shield effect logic
                // Blocks any attack on the same block
                break;
            case "Heal":
                // Heal effect logic
                // Adds health to the current player
                if (currentPlayerTurn == 1) {
                    firstPlayer.setMaxHP(firstPlayer.getMaxHP() + card.getDamage());
                } else {
                    secondPlayer.setMaxHP(secondPlayer.getMaxHP() + card.getDamage());
                }
                break;
            case "Power Booster":
                // Power Booster effect logic
                // Boosts one random card's attack/defense points
                boostRandomCard();
                break;
            case "Hole Position Changer":
                // Hole Position Changer effect logic
                // Changes the position of a destroyed block
                changeHolePosition();
                break;
            case "Repairer":
                // Repairer effect logic
                // Repairs a hole in the player's board
                repairHole();
                break;
            case "Round Reducer":
                // Round Reducer effect logic
                // Reduces the number of rounds
                remainingRounds = Math.max(0, remainingRounds - 1);
                break;
            case "Remove Opponent Card":
                // Remove Opponent Card effect logic
                // Removes a random card from the opponent's hand
                removeOpponentCard();
                break;
            case "Weaken Opponent Card":
                // Weaken Opponent Card effect logic
                // Weakens two random cards of the opponent
                weakenOpponentCards();
                break;
            case "Card Copier":
                // Card Copier effect logic
                // Copies a card from the player's hand
                copyCard();
                break;
        }
    }

    private void boostRandomCard() {
        List<Card> currentHand = currentPlayerTurn == 1 ? firstPlayerCards : secondPlayerCards;
        if (!currentHand.isEmpty()) {
            Card randomCard = currentHand.get(new Random().nextInt(currentHand.size()));
            randomCard.setAttackDefense(randomCard.getAttackDefense() + 10); // Example boost value
        }
    }

    private void changeHolePosition() {
        // Logic to change the position of a hole on the board
    }

    private void repairHole() {
        // Logic to repair a hole on the player's board
    }

    private void removeOpponentCard() {
        List<Card> opponentHand = currentPlayerTurn == 1 ? secondPlayerCards : firstPlayerCards;
        if (!opponentHand.isEmpty()) {
            opponentHand.remove(new Random().nextInt(opponentHand.size()));
        }
    }

    private void weakenOpponentCards() {
        List<Card> opponentHand = currentPlayerTurn == 1 ? secondPlayerCards : firstPlayerCards;
        if (!opponentHand.isEmpty()) {
            Card card1 = opponentHand.get(new Random().nextInt(opponentHand.size()));
            Card card2 = opponentHand.get(new Random().nextInt(opponentHand.size()));
            card1.setAttackDefense(Math.max(0, card1.getAttackDefense() - 5)); // Example weaken value
            card2.setDamage(Math.max(0, card2.getDamage() - 5)); // Example weaken value
        }
    }

    private void copyCard() {
        List<Card> currentHand = currentPlayerTurn == 1 ? firstPlayerCards : secondPlayerCards;
        if (!currentHand.isEmpty()) {
            Card randomCard = currentHand.get(new Random().nextInt(currentHand.size()));
            currentHand.add(new Card(randomCard.getName(), randomCard.getType(), randomCard.getAttackDefense(),
                    randomCard.getDuration(), randomCard.getDamage(), randomCard.getUpgradeLevel(),
                    randomCard.getUpgradeCost()));
        }
    }

    private void endRound() {
        // Implement end of round logic, calculate damage, update HP, etc.
        int firstPlayerDamage = calculatePlayerDamage(1);
        int secondPlayerDamage = calculatePlayerDamage(2);

        firstPlayer.setMaxHP(firstPlayer.getMaxHP() - secondPlayerDamage);
        secondPlayer.setMaxHP(secondPlayer.getMaxHP() - firstPlayerDamage);

        gamePlayView.displayRoundResult(board, firstPlayer, secondPlayer);

        if (firstPlayer.getMaxHP() <= 0 || secondPlayer.getMaxHP() <= 0) {
            endGame();
        } else {
            remainingRounds = 4;
            currentPlayerTurn = new Random().nextBoolean() ? 1 : 2;
            gamePlayView.displayGameState(firstPlayer, secondPlayer, firstPlayerCards, secondPlayerCards, board,
                    currentPlayerTurn);
        }
    }

    private int calculatePlayerDamage(int playerNumber) {
        int damage = 0;
        for (int i = 0; i < 21; i++) {
            if (board[playerNumber - 1][i] > board[2 - playerNumber][i]) {
                damage += board[playerNumber - 1][i] - board[2 - playerNumber][i];
            }
        }
        return damage;
    }

    private void endGame() {
        // Implement end game logic, calculate XP, coins, etc.
        User winner = firstPlayer.getMaxHP() > secondPlayer.getMaxHP() ? firstPlayer : secondPlayer;
        User loser = winner == firstPlayer ? secondPlayer : firstPlayer;

        int xpGain = calculateXPGain(winner, loser);
        int coinGain = calculateCoinGain(winner, loser);

        winner.setXp(winner.getXp() + xpGain);
        winner.setCoins(winner.getCoins() + coinGain);

        gamePlayView.displayEndGameResult(winner, loser, xpGain, coinGain);

        if (winner.getXp() >= 100) { // Example XP threshold for leveling up
            winner.setLevel(winner.getLevel() + 1);
            winner.setXp(0);
            winner.setCoins(winner.getCoins() + 50); // Example coin reward for leveling up
            gamePlayView.displayMessage("Congratulations! You have leveled up!");
        }

        Main.setCurrentView(UserController.getInstance().getView());
    }

    private int calculateXPGain(User winner, User loser) {
        return (loser.getMaxHP() + winner.getMaxHP()) / 10; // Example XP calculation
    }

    private int calculateCoinGain(User winner, User loser) {
        return (loser.getMaxHP() + winner.getMaxHP()) / 5; // Example coin calculation
    }
}
