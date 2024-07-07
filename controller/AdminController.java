package citywars.controller;

import citywars.Main;
import citywars.model.Card;
import citywars.model.User;
import citywars.view.AdminView;

import java.util.List;

public class AdminController {
    private static AdminController instance;
    private AdminView adminView;
    private boolean isLoggedIn;

    private AdminController() {
        adminView = new AdminView();
        isLoggedIn = false;
    }

    public static AdminController getInstance() {
        if (instance == null) {
            instance = new AdminController();
        }
        return instance;
    }

    public AdminView getView() {
        return adminView;
    }

    public void handleCommand(String command) {
        if (!isLoggedIn) {
            if (command.startsWith("login admin")) {
                loginAdmin(command);
            } else {
                adminView.displayMessage("Unknown command");
            }
        } else {
            switch (command) {
                case "add card":
                    addCard();
                    break;
                case "edit card":
                    editCard();
                    break;
                case "delete card":
                    deleteCard();
                    break;
                case "view all users":
                    viewAllUsers();
                    break;
                case "logout":
                    isLoggedIn = false;
                    Main.setCurrentView(UserController.getInstance().getView());
                    break;
                default:
                    adminView.displayMessage("Unknown command");
                    break;
            }
        }
    }

    private void loginAdmin(String command) {
        String[] parts = command.split(" ");
        if (parts.length < 3) {
            adminView.displayMessage("Invalid command. Usage: login admin <password>");
            return;
        }
        String password = parts[2];
        if (password.equals("admin123")) {
            isLoggedIn = true;
            Main.setCurrentView(adminView);
            adminView.displayMessage("Admin logged in successfully.");
        } else {
            adminView.displayMessage("Invalid admin password.");
        }
    }

    private void addCard() {
        String name = adminView.getInput("Enter card name: ");
        if (Card.getCardDatabase().getCard(name) != null) {
            adminView.displayMessage("Card with this name already exists.");
            return;
        }
        String type = adminView.getInput("Enter card type: ");
        int attackDefense = Integer.parseInt(adminView.getInput("Enter attack/defense value (10-100): "));
        int duration = Integer.parseInt(adminView.getInput("Enter duration (1-5): "));
        int damage = Integer.parseInt(adminView.getInput("Enter damage to player (10-50): "));
        int upgradeLevel = Integer.parseInt(adminView.getInput("Enter minimum level for upgrade: "));
        int upgradeCost = Integer.parseInt(adminView.getInput("Enter cost for upgrade: "));
        Card.getCardDatabase().addCard(new Card(name, type, attackDefense, duration, damage, upgradeLevel, upgradeCost));
        adminView.displayMessage("Card added successfully.");
    }

    private void editCard() {
        String name = adminView.getInput("Enter the name of the card you want to edit: ");
        Card card = Card.getCardDatabase().getCard(name);
        if (card == null) {
            adminView.displayMessage("Card not found.");
            return;
        }
        adminView.displayMessage("Editing card: " + card);
        String field = adminView.getInput("Enter the field to edit (name, attackDefense, duration, damage, upgradeLevel, upgradeCost): ");
        switch (field) {
            case "name":
                String newName = adminView.getInput("Enter new name: ");
                card.setName(newName);
                break;
            case "attackDefense":
                int newAttackDefense = Integer.parseInt(adminView.getInput("Enter new attack/defense value (10-100): "));
                card.setAttackDefense(newAttackDefense);
                break;
            case "duration":
                int newDuration = Integer.parseInt(adminView.getInput("Enter new duration (1-5): "));
                card.setDuration(newDuration);
                break;
            case "damage":
                int newDamage = Integer.parseInt(adminView.getInput("Enter new damage to player (10-50): "));
                card.setDamage(newDamage);
                break;
            case "upgradeLevel":
                int newUpgradeLevel = Integer.parseInt(adminView.getInput("Enter new minimum level for upgrade: "));
                card.setUpgradeLevel(newUpgradeLevel);
                break;
            case "upgradeCost":
                int newUpgradeCost = Integer.parseInt(adminView.getInput("Enter new cost for upgrade: "));
                card.setUpgradeCost(newUpgradeCost);
                break;
            default:
                adminView.displayMessage("Invalid field.");
                return;
        }
        adminView.displayMessage("Card edited successfully.");
    }

    private void deleteCard() {
        String name = adminView.getInput("Enter the name of the card you want to delete: ");
        Card card = Card.getCardDatabase().getCard(name);
        if (card == null) {
            adminView.displayMessage("Card not found.");
            return;
        }
        Card.getCardDatabase().deleteCard(name);
        adminView.displayMessage("Card deleted successfully.");
    }

    private void viewAllUsers() {
        List<User> users = User.getUserDatabase().getAllUsers();
        adminView.displayUsers(users);
    }
}
