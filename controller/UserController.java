package citywars.controller;

import citywars.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class UserController {
    private static UserController instance;
    private Map<String, Long> loginAttemptsTime;
    private Map<String, Integer> loginAttemptsCount;

    private UserController() {
        loginAttemptsTime = new HashMap<>();
        loginAttemptsCount = new HashMap<>();
    }

    public static UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }

    public String createUser(String username, String password, String passwordConfirmation, String email, String nickname, String question, String answer) {
        if (username == null || !User.isValidUsername(username)) {
            return "Invalid username. Username must contain only letters, numbers, and underscores.";
        }

        if (User.getUserDatabase().getUser(username) != null) {
            return "Username already exists. Please choose a different username.";
        }

        if (password == null || !User.isValidPassword(password)) {
            return "Weak password. Password must be at least 8 characters long, contain at least one lowercase letter, one uppercase letter, and one special character.";
        }

        if (passwordConfirmation == null || !password.equals(passwordConfirmation)) {
            return "Passwords do not match. Please try again.";
        }

        if (email == null || !User.isValidEmail(email)) {
            return "Invalid email format. Email must be in the format <email>@<domain>.com.";
        }

        if (nickname == null || nickname.isEmpty()) {
            return "Nickname cannot be empty.";
        }

        User user = new User(username, password, nickname, email);

        user.setSecurityQuestion(question);
        user.setSecurityAnswer(answer);

        User.getUserDatabase().addUser(user);
        return "";
    }

    public String loginUser(String username, String password) {
        if (username == null || password == null) {
            return "Username and password cannot be empty.";
        }

        User user = User.getUserDatabase().getUser(username);
        if (user == null) {
            return "Username doesn’t exist!";
        }

        long currentTime = System.currentTimeMillis();
        long lastAttemptTime = loginAttemptsTime.getOrDefault(username, System.currentTimeMillis());
        int attempts = loginAttemptsCount.getOrDefault(username, 0);

        if ((currentTime - lastAttemptTime) < attempts * 5000) {
            return "too soon to try again";
        }

        if (!user.getPassword().equals(password)) {
            loginAttemptsCount.put(username, attempts + 1);
            loginAttemptsTime.put(username, currentTime);

            return "Password and Username don’t match! Try again in " + (5 * attempts) + " seconds.";
        }

        MainMenuController.getInstance().setLoggedInUser(user);
        return "";
    }

    public String getSecurityQuestion(String username) {
        if (username == null) {
            return "";
        }

        User user = User.getUserDatabase().getUser(username);
        if (user == null) {
            return "";
        }

        return user.getSecurityQuestion();
    }

    public String forgetPassword(String username, String answer, String newPassword, String newPasswordConfirmation) {
        if (username == null) {
            return "Username cannot be empty.";
        }

        User user = User.getUserDatabase().getUser(username);
        if (user == null) {
            return "Username doesn’t exist!";
        }

        if (!user.getSecurityAnswer().equals(answer)) {
            return "Incorrect answer!";
        }

        if (!newPassword.equals(newPasswordConfirmation)) {
            return "Passwords do not match. Please try again.";
        }

        if (!User.isValidPassword(newPassword)) {
            return "Weak password. Password must be at least 8 characters long, contain at least one lowercase letter, one uppercase letter, and one special character.";
        }

        user.setPassword(newPassword);
        return "";
    }

    public String updateProfile(User user, String username, String password, String passwordConfirmation, String email, String nickname) {
        if (username == null || !User.isValidUsername(username)) {
            return "Invalid username. Username must contain only letters, numbers, and underscores.";
        }

        if (User.getUserDatabase().getUser(username) != null && !username.equals(user.getUsername())) {
            return "Username already exists. Please choose a different username.";
        }

        if (password == null || !User.isValidPassword(password)) {
            return "Weak password. Password must be at least 8 characters long, contain at least one lowercase letter, one uppercase letter, and one special character.";
        }

        if (passwordConfirmation == null || !password.equals(passwordConfirmation)) {
            return "Passwords do not match. Please try again.";
        }

        if (email == null || !User.isValidEmail(email)) {
            return "Invalid email format. Email must be in the format <email>@<domain>.com.";
        }

        if (nickname == null || nickname.isEmpty()) {
            return "Nickname cannot be empty.";
        }

        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setNickname(nickname);

        return "";
    }

    public String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }
}
