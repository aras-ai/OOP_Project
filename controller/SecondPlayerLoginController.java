package citywars.controller;

import citywars.Main;
import citywars.model.User;
import citywars.model.UserDatabase;
import citywars.view.SecondPlayerLoginView;

public class SecondPlayerLoginController {
    private static SecondPlayerLoginController instance;
    private SecondPlayerLoginView secondPlayerLoginView;
    private UserDatabase userDatabase;
    private User secondPlayer;

    private SecondPlayerLoginController() {
        secondPlayerLoginView = new SecondPlayerLoginView();
        userDatabase = new UserDatabase();
    }

    public static SecondPlayerLoginController getInstance() {
        if (instance == null) {
            instance = new SecondPlayerLoginController();
        }
        return instance;
    }

    public SecondPlayerLoginView getView() {
        return secondPlayerLoginView;
    }

    public void handleCommand(String command) {
        if (command.startsWith("login -u")) {
            loginUser(command);
        } else {
            secondPlayerLoginView.displayMessage("Unknown command");
        }
    }

    private void loginUser(String command) {
        String[] parts = command.split(" ");
        String username = null;
        String password = null;

        for (int i = 2; i < parts.length; i++) {
            switch (parts[i]) {
                case "-u":
                    username = parts[++i];
                    break;
                case "-p":
                    password = parts[++i];
                    break;
            }
        }

        if (username == null || password == null) {
            secondPlayerLoginView.displayMessage("Username and password cannot be empty.");
            return;
        }

        User user = userDatabase.getUser(username);
        if (user == null) {
            secondPlayerLoginView.displayMessage("Username doesn’t exist!");
            return;
        }

        if (!user.getPassword().equals(password)) {
            secondPlayerLoginView.displayMessage("Password and Username don’t match!");
            return;
        }

        secondPlayer = user;
        secondPlayerLoginView.displayMessage("Second player logged in successfully!");
        Main.setCurrentView(CharacterSelectionController.getInstance().getView());
    }

    public User getSecondPlayer() {
        return secondPlayer;
    }
}
