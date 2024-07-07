package citywars.view;

import citywars.model.User;

import java.util.List;

public class AdminView extends MainView {
    public void display() {
        System.out.print("ADMIN> ");
    }

    public void displayUsers(List<User> users) {
        System.out.println("All Users:");
        for (User user : users) {
            System.out.println("Username: " + user.getUsername() + ", Level: " + user.getLevel() + ", Coins: " + user.getCoins());
        }
    }
}
