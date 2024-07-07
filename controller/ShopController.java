package citywars.controller;

import citywars.Main;
import citywars.model.Card;
import citywars.model.User;
import citywars.view.ShopView;

import java.util.List;

public class ShopController {
    private static ShopController instance;
    private ShopView shopView;
    private User loggedInUser;

    private ShopController() {
        shopView = new ShopView();
    }

    public static ShopController getInstance() {
        if (instance == null) {
            instance = new ShopController();
        }
        return instance;
    }

    public void setLoggedInUser(User user) {
        this.loggedInUser = user;
    }

    public ShopView getView() {
        return shopView;
    }

    public void handleCommand(String command) {
        if (loggedInUser != null) {
            switch (command) {
                case "view all cards":
                    viewAllCards();
                    break;
                case "buy card":
                    buyCard();
                    break;
                case "view upgradable cards":
                    viewUpgradableCards();
                    break;
                case "upgrade card":
                    upgradeCard();
                    break;
                case "back":
                    Main.setCurrentView(MainMenuController.getInstance().getView());
                    break;
                default:
                    shopView.displayMessage("Unknown command");
                    break;
            }
        }
    }

    private void viewAllCards() {
        List<Card> allCards = Card.getCardDatabase().getAllCards();
        shopView.displayAllCards(allCards);
    }

    private void buyCard() {
        String cardName = shopView.getInput("Enter the name of the card you want to buy: ");
        Card card = Card.getCardDatabase().getCard(cardName);
        if (card == null) {
            shopView.displayMessage("Card not found.");
            return;
        }
        if (loggedInUser.getCoins() < card.getUpgradeCost()) {
            shopView.displayMessage("Not enough coins to buy this card.");
            return;
        }
        loggedInUser.getCards().add(card.getName());
        loggedInUser.setCoins(loggedInUser.getCoins() - card.getUpgradeCost());
        shopView.displayMessage("Card bought successfully.");
    }

    private void viewUpgradableCards() {
        List<Card> upgradableCards = Card.getCardDatabase().getUpgradableCards(loggedInUser.getCards());
        shopView.displayUpgradableCards(upgradableCards);
    }

    private void upgradeCard() {
        String cardName = shopView.getInput("Enter the name of the card you want to upgrade: ");
        Card card = Card.getCardDatabase().getCard(cardName);
        if (card == null || !loggedInUser.getCards().contains(cardName)) {
            shopView.displayMessage("Card not found or you don't own this card.");
            return;
        }
        if (loggedInUser.getLevel() < card.getUpgradeLevel()) {
            shopView.displayMessage("Your level is not high enough to upgrade this card.");
            return;
        }
        if (loggedInUser.getCoins() < card.getUpgradeCost()) {
            shopView.displayMessage("Not enough coins to upgrade this card.");
            return;
        }
        loggedInUser.setCoins(loggedInUser.getCoins() - card.getUpgradeCost());
        card.upgrade();
        shopView.displayMessage("Card upgraded successfully.");
    }
}
