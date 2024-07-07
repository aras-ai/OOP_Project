package citywars.controller;

import citywars.Main;
import citywars.view.GameModeView;

public class GameModeController {
    private static GameModeController instance;
    private GameModeView gameModeView;

    private GameModeController() {
        gameModeView = new GameModeView();
    }

    public static GameModeController getInstance() {
        if (instance == null) {
            instance = new GameModeController();
        }
        return instance;
    }

    public GameModeView getView() {
        return gameModeView;
    }

    public void handleCommand(String command) {
        switch (command) {
            case "two player":
                initiateTwoPlayerMode();
                break;
            case "betting":
                initiateBettingMode();
                break;
            case "back":
                Main.setCurrentView(MainMenuController.getInstance().getView());
                break;
            default:
                gameModeView.displayMessage("Unknown command");
                break;
        }
    }

    private void initiateTwoPlayerMode() {
        SecondPlayerLoginController secondPlayerLoginController = SecondPlayerLoginController.getInstance();
        Main.setCurrentView(secondPlayerLoginController.getView());
    }

    private void initiateBettingMode() {
        SecondPlayerLoginController secondPlayerLoginController = SecondPlayerLoginController.getInstance();
        Main.setCurrentView(secondPlayerLoginController.getView());
    }
}
