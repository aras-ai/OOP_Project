package citywars.model;

import java.util.ArrayList;
import java.util.List;

import citywars.controller.SecurityQuestion;

public class UserDatabase {
    private List<User> users;

    public UserDatabase() {
        users = new ArrayList<>();

        User admin = new User("admin", "admin123", "Admin", "admin@gmail.com");
        admin.setSecurityQuestion(SecurityQuestion.getQuestion(1));
        admin.setSecurityAnswer("meow");
        admin.setCoins(20000);
        users.add(admin);
    }

    public boolean addUser(User user) {
        for (User u : users) {
            if (u.getUsername().equals(user.getUsername())) {
                return false;
            }
        }
        users.add(user);
        return true;
    }

    public User getUser(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) {
                return u;
            }
        }
        return null;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users);
    }
}
