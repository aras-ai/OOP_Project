package citywars.controller;

import citywars.Main;
import citywars.model.User;
import citywars.view.CharacterSelectionView;

public class CharacterSelectionController {
    private static CharacterSelectionController instance;
    private CharacterSelectionView characterSelectionView;
    private User firstPlayer;
    private User secondPlayer;

    private CharacterSelectionController() {
        characterSelectionView = new CharacterSelectionView();
    }

    public static CharacterSelectionController getInstance() {
        if (instance == null) {
            instance = new CharacterSelectionController();
        }
        return instance;
    }

    public CharacterSelectionView getView() {
        return characterSelectionView;
    }

    public void handleCommand(String command) {
        if (command.startsWith("character select")) {
            selectCharacter(command);
        } else {
            characterSelectionView.displayMessage("Unknown command");
        }
    }

    private void selectCharacter(String command) {
        String[] parts = command.split(" ");
        String character = null;
        for (int i = 2; i < parts.length; i++) {
            switch (parts[i]) {
                case "player1":
                    character = parts[++i];
                    firstPlayer.setCharacter(character);
                    characterSelectionView.displayMessage("Player 1 selected character: " + character);
                    break;
                case "player2":
                    character = parts[++i];
                    secondPlayer.setCharacter(character);
                    characterSelectionView.displayMessage("Player 2 selected character: " + character);
                    break;
            }
        }

        if (firstPlayer.getCharacter() != null && secondPlayer.getCharacter() != null) {
            Main.setCurrentView(GamePlayController.getInstance().getView());
        }
    }

    public void setPlayers(User firstPlayer, User secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }
}
