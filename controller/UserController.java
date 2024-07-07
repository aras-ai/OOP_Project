package citywars.controller;

import citywars.Main;
import citywars.model.User;
import citywars.view.UserView;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class UserController {
    private static UserController instance;
    private UserView userView;
    private Map<String, Long> loginAttempts;

    private UserController() {
        userView = new UserView();
        loginAttempts = new HashMap<>();
    }

    public static UserController getInstance() {
        if (instance == null) {
            instance = new UserController();
        }
        return instance;
    }

    public void start() {
        Main.setCurrentView(userView);
    }

    public UserView getView() {
        return userView;
    }

    public void handleCommand(String command) {
        if (command.startsWith("user create")) {
            createUser(command);
        } else if (command.startsWith("user login")) {
            loginUser(command);
        } else if (command.startsWith("forget password")) {
            forgotPassword(command);
        } else if (command.startsWith("login admin")) {
            AdminController.getInstance().handleCommand(command);
        } else {
            userView.displayMessage("Unknown command");
        }
    }

    private void createUser(String command) {
        String[] parts = command.split(" ");
        String username = null;
        String password = null;
        String passwordConfirmation = null;
        String email = null;
        String nickname = null;

        for (int i = 2; i < parts.length; i++) {
            switch (parts[i]) {
                case "-u":
                    username = parts[++i];
                    break;
                case "-p":
                    password = parts[++i];
                    if (!password.equals("random")) {
                        passwordConfirmation = parts[++i];
                    }
                    break;
                case "--email":
                    email = parts[++i];
                    break;
                case "-n":
                    nickname = parts[++i];
                    break;
            }
        }

        if (username == null || !User.isValidUsername(username)) {
            userView.displayMessage("Invalid username. Username must contain only letters, numbers, and underscores.");
            return;
        }

        if (User.getUserDatabase().getUser(username) != null) {
            userView.displayMessage("Username already exists. Please choose a different username.");
            return;
        }

        if ("random".equals(password)) {
            password = generateRandomPassword();
            userView.displayMessage("Your random password: " + password);
            String enteredPassword = userView.getInput("Please enter your password: ");
            if (!password.equals(enteredPassword)) {
                userView.displayMessage("Passwords do not match. Please try again.");
                return;
            }
        } else {
            if (password == null || !User.isValidPassword(password)) {
                userView.displayMessage(
                        "Weak password. Password must be at least 8 characters long, contain at least one lowercase letter, one uppercase letter, and one special character.");
                return;
            }

            if (passwordConfirmation == null || !password.equals(passwordConfirmation)) {
                userView.displayMessage("Passwords do not match. Please try again.");
                return;
            }
        }

        if (email == null || !User.isValidEmail(email)) {
            userView.displayMessage("Invalid email format. Email must be in the format <email>@<domain>.com.");
            return;
        }

        if (nickname == null || nickname.isEmpty()) {
            userView.displayMessage("Nickname cannot be empty.");
            return;
        }

        User user = new User(username, password, nickname, email);

        userView.displayMessage("User created successfully. Please choose a security question:");
        for (int i = 0; i < SecurityQuestion.questions.length; i++) {
            userView.displayMessage((i + 1) + ". " + SecurityQuestion.questions[i]);
        }

        int questionNumber = Integer.parseInt(userView.getInput("Enter question number: "));
        String question = SecurityQuestion.getQuestion(questionNumber);
        if (question == null) {
            userView.displayMessage("Invalid question number.");
            return;
        }

        String answer = userView.getInput("Enter answer: ");
        String answerConfirmation = userView.getInput("Confirm answer: ");
        if (!answer.equals(answerConfirmation)) {
            userView.displayMessage("Answers do not match. Please try again.");
            return;
        }
        user.setSecurityQuestion(question);
        user.setSecurityAnswer(answer);

        while (true) {
            String captcha = CaptchaGenerator.getInstance().generateCaptcha();
            userView.displayMessage("Please solve the following captcha: " + captcha);
            int userResult = Integer.parseInt(userView.getInput("Enter result: "));
            if (CaptchaGenerator.getInstance().validateCaptcha(captcha, userResult)) {
                break;
            } else {
                userView.displayMessage("Incorrect captcha. Please try again.");
            }
        }

        User.getUserDatabase().addUser(user);
        userView.displayMessage("User registered successfully!");
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
            userView.displayMessage("Username and password cannot be empty.");
            return;
        }

        User user = User.getUserDatabase().getUser(username);
        if (user == null) {
            userView.displayMessage("Username doesn’t exist!");
            return;
        }

        if (!user.getPassword().equals(password)) {
            long currentTime = System.currentTimeMillis();
            long lastAttemptTime = loginAttempts.getOrDefault(username, 0L);
            int attempts = (int) ((currentTime - lastAttemptTime) / 5000) + 1;

            loginAttempts.put(username, currentTime);
            userView.displayMessage("Password and Username don’t match! Try again in " + (5 * attempts) + " seconds.");
            return;
        }

        MainMenuController.getInstance().setLoggedInUser(user);
        userView.displayMessage("User logged in successfully!");
        Main.setCurrentView(MainMenuController.getInstance().getView());
    }

    private void forgotPassword(String command) {
        String[] parts = command.split(" ");
        String username = null;

        for (int i = 2; i < parts.length; i++) {
            if (parts[i].equals("-u")) {
                username = parts[++i];
                break;
            }
        }

        if (username == null) {
            userView.displayMessage("Username cannot be empty.");
            return;
        }

        User user = User.getUserDatabase().getUser(username);
        if (user == null) {
            userView.displayMessage("Username doesn’t exist!");
            return;
        }

        userView.displayMessage("Answer the security question to reset your password:");
        userView.displayMessage(user.getSecurityQuestion());
        String answer = userView.getInput("Answer: ");
        if (!user.getSecurityAnswer().equals(answer)) {
            userView.displayMessage("Incorrect answer!");
            return;
        }

        String newPassword = userView.getInput("Enter new password: ");
        String newPasswordConfirmation = userView.getInput("Confirm new password: ");
        if (!newPassword.equals(newPasswordConfirmation)) {
            userView.displayMessage("Passwords do not match. Please try again.");
            return;
        }

        if (!User.isValidPassword(newPassword)) {
            userView.displayMessage(
                    "Weak password. Password must be at least 8 characters long, contain at least one lowercase letter, one uppercase letter, and one special character.");
            return;
        }

        user.setPassword(newPassword);
        userView.displayMessage("Password reset successfully!");
    }

    private String generateRandomPassword() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++)
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        return sb.toString();
    }
}
