package citywars.controller;

import citywars.Main;
import citywars.model.User;
import citywars.view.MainMenuView;

import java.util.List;
import java.util.Random;

public class MainMenuController {
    private static MainMenuController instance;
    private MainMenuView mainMenuView;
    private User loggedInUser;
    private boolean starterPackGiven;

    private MainMenuController() {
        mainMenuView = new MainMenuView();
        starterPackGiven = false;
    }

    public static MainMenuController getInstance() {
        if (instance == null) {
            instance = new MainMenuController();
        }
        return instance;
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    public MainMenuView getView() {
        return mainMenuView;
    }

    public void handleCommand(String command) {
        if (loggedInUser != null) {
            if (!starterPackGiven) {
                giveStarterPack();
                starterPackGiven = true;
            }
            switch (command) {
                case "start game":
                    // Navigate to game mode selection menu
                    break;
                case "view cards":
                    viewCards();
                    break;
                case "game history":
                    GameHistoryController gameHistoryController = GameHistoryController.getInstance();
                    gameHistoryController.displayGameHistory(loggedInUser);
                    break;
                case "store":
                    ShopController.getInstance().setLoggedInUser(loggedInUser);
                    Main.setCurrentView(ShopController.getInstance().getView());
                    break;
                case "profile":
                    profileMenu();
                    break;
                case "log out":
                    loggedInUser = null;
                    mainMenuView.displayMessage("User logged out successfully.");
                    Main.setCurrentView(UserController.getInstance().getView());
                    break;
                default:
                    mainMenuView.displayMessage("Unknown command");
                    break;
            }
        }
    }

    private void giveStarterPack() {
        List<String> starterPack = generateStarterPack();
        loggedInUser.setStarterPack(starterPack);
        mainMenuView.displayMessage("Starter pack given: " + starterPack);
    }

    private List<String> generateStarterPack() {
        // Simulate random cards generation
        Random random = new Random();
        return random.ints(20, 1, 100)
                .mapToObj(i -> "Card " + i)
                .toList();
    }

    private void viewCards() {
        List<String> cards = loggedInUser.getCards();
        if (cards.isEmpty()) {
            mainMenuView.displayMessage("You have no cards.");
        } else {
            mainMenuView.displayMessage("Your cards: " + String.join(", ", cards));
        }
    }

    public void profileMenu() {
        while (true) {
            String command = mainMenuView.getInput("PROFILE>");
            if (command.equals("show information")) {
                showInformation();
            } else if (command.startsWith("profile change -u")) {
                changeUsername(command);
            } else if (command.startsWith("profile change -n")) {
                changeNickname(command);
            } else if (command.startsWith("profile change password")) {
                changePassword(command);
            } else if (command.startsWith("profile change -e")) {
                changeEmail(command);
            } else if (command.equals("back")) {
                break;
            } else {
                mainMenuView.displayMessage("Unknown command");
            }
        }
    }

    private void showInformation() {
        mainMenuView.displayMessage("Username: " + loggedInUser.getUsername());
        mainMenuView.displayMessage("Email: " + loggedInUser.getEmail());
        mainMenuView.displayMessage("Nickname: " + loggedInUser.getNickname());
        mainMenuView.displayMessage("Level: " + loggedInUser.getLevel());
        mainMenuView.displayMessage("Max HP: " + loggedInUser.getMaxHP());
        mainMenuView.displayMessage("XP: " + loggedInUser.getXp());
        mainMenuView.displayMessage("Coins: " + loggedInUser.getCoins());
    }

    private void changeUsername(String command) {
        String[] parts = command.split(" ");
        String newUsername = null;
        for (int i = 3; i < parts.length; i++) {
            if (parts[i].equals("-u")) {
                newUsername = parts[++i];
                break;
            }
        }

        if (newUsername == null || !User.isValidUsername(newUsername)) {
            mainMenuView.displayMessage("Invalid username. Username must contain only letters, numbers, and underscores.");
            return;
        }

        if (User.getUserDatabase().getUser(newUsername) != null) {
            mainMenuView.displayMessage("Username already exists. Please choose a different username.");
            return;
        }
        loggedInUser.setUsername(newUsername);
        mainMenuView.displayMessage("Username changed successfully.");
    }

    private void changeNickname(String command) {
        String[] parts = command.split(" ");
        String newNickname = null;
        for (int i = 3; i < parts.length; i++) {
            if (parts[i].equals("-n")) {
                newNickname = parts[++i];
                break;
            }
        }

        if (newNickname == null || newNickname.isEmpty()) {
            mainMenuView.displayMessage("Nickname cannot be empty.");
            return;
        }
        loggedInUser.setNickname(newNickname);
        mainMenuView.displayMessage("Nickname changed successfully.");
    }

    private void changePassword(String command) {
        String[] parts = command.split(" ");
        String oldPassword = null;
        String newPassword = null;
        String newPasswordConfirmation = null;
        for (int i = 3; i < parts.length; i++) {
            if (parts[i].equals("-o")) {
                oldPassword = parts[++i];
            } else if (parts[i].equals("-n")) {
                newPassword = parts[++i];
                newPasswordConfirmation = parts[++i];
            }
        }

        if (oldPassword == null || newPassword == null || newPasswordConfirmation == null) {
            mainMenuView.displayMessage("All password fields are required.");
            return;
        }

        if (!loggedInUser.getPassword().equals(oldPassword)) {
            mainMenuView.displayMessage("Current password is incorrect.");
            return;
        }

        if (newPassword.equals(oldPassword)) {
            mainMenuView.displayMessage("Please enter a new password.");
            return;
        }

        if (!newPassword.equals(newPasswordConfirmation)) {
            mainMenuView.displayMessage("Passwords do not match. Please try again.");
            return;
        }

        if (!User.isValidPassword(newPassword)) {
            mainMenuView.displayMessage(
                    "Weak password. Password must be at least 8 characters long, contain at least one lowercase letter, one uppercase letter, and one special character.");
            return;
        }

        while (true) {
            String captcha = CaptchaGenerator.getInstance().generateCaptcha();
            mainMenuView.displayMessage("Please solve the following captcha: " + captcha);
            int userResult = Integer.parseInt(mainMenuView.getInput("Enter result: "));
            if (CaptchaGenerator.getInstance().validateCaptcha(captcha, userResult)) {
                break;
            } else {
                mainMenuView.displayMessage("Incorrect captcha. Please try again.");
            }
        }
        loggedInUser.setPassword(newPassword);
        mainMenuView.displayMessage("Password changed successfully.");
    }

    private void changeEmail(String command) {
        String[] parts = command.split(" ");
        String newEmail = null;

        for (int i = 3; i < parts.length; i++) {
            if (parts[i].equals("-e")) {
                newEmail = parts[++i];
                break;
            }
        }

        if (newEmail == null || !User.isValidEmail(newEmail)) {
            mainMenuView.displayMessage("Invalid email format. Email must be in the format <email>@<domain>.com.");
            return;
        }
        loggedInUser.setEmail(newEmail);
        mainMenuView.displayMessage("Email changed successfully.");
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
