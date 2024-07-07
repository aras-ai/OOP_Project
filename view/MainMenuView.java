package citywars.view;

import citywars.controller.MainMenuController;

public class MainMenuView extends MainView {
    public void display() {
        System.out.print(MainMenuController.getInstance().getLoggedInUser().getUsername() + ">");
    }
}
