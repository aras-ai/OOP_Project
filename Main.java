package citywars;

import citywars.controller.AdminController;
import citywars.controller.CharacterSelectionController;
import citywars.controller.GameModeController;
import citywars.controller.GamePlayController;
import citywars.controller.MainMenuController;
import citywars.controller.SecondPlayerLoginController;
import citywars.controller.ShopController;
import citywars.controller.UserController;
import citywars.view.MainView;

import java.util.Scanner;

public class Main {
    public static Scanner scanner = new Scanner(System.in);
    private static MainView currentView;
    private static UserController userController = UserController.getInstance();
    private static MainMenuController mainMenuController = MainMenuController.getInstance();
    private static ShopController shopController = ShopController.getInstance();
    private static AdminController adminController = AdminController.getInstance();
    private static GameModeController gameModeController = GameModeController.getInstance();
    private static SecondPlayerLoginController secondPlayerLoginController = SecondPlayerLoginController.getInstance();
    private static CharacterSelectionController characterSelectionController = CharacterSelectionController.getInstance();
    private static GamePlayController gamePlayController = GamePlayController.getInstance();

    public static void main(String[] args) {
        userController.start();
        while (true) {
            currentView.display();
            String command = scanner.nextLine();
            if (command.equals("exit")) {
                break;
            }
            if (currentView == userController.getView()) {
                userController.handleCommand(command);
            } else if (currentView == mainMenuController.getView()) {
                mainMenuController.handleCommand(command);
            } else if (currentView == shopController.getView()) {
                shopController.handleCommand(command);
            } else if (currentView == adminController.getView()) {
                adminController.handleCommand(command);
            } else if (currentView == gameModeController.getView()) {
                gameModeController.handleCommand(command);
            } else if (currentView == secondPlayerLoginController.getView()) {
                secondPlayerLoginController.handleCommand(command);
            } else if (currentView == characterSelectionController.getView()) {
                characterSelectionController.handleCommand(command);
            } else if (currentView == gamePlayController.getView()) {
                gamePlayController.handleCommand(command);
            }
        }
    }

    public static void setCurrentView(MainView view) {
        currentView = view;
    }
}
