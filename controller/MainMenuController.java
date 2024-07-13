package citywars.controller;

import citywars.model.User;

public class MainMenuController {
    private static MainMenuController instance;
    private User loggedInUser;

    private MainMenuController() {

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

    public User getLoggedInUser() {
        return loggedInUser;
    }
}
