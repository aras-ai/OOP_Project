package citywars.controller;

import citywars.model.Card;
import citywars.model.User;
import java.util.List;

public class ShopController {
    private static ShopController instance;

    private ShopController() {
    }

    public static ShopController getInstance() {
        if (instance == null) {
            instance = new ShopController();
        }
        return instance;
    }

    public List<Card> getAllCards() {
        return Card.getCardDatabase().getAllCards();
    }

    public String buyCard(String cardName) {
        Card card = Card.getCardDatabase().getCard(cardName);
        if (card == null) {
            return "Card not found.";
        }
        User user = MainMenuController.getInstance().getLoggedInUser();
        if (user.getCoins() < card.getUpgradeCost()) {
            return "Not enough coins to buy this card.";
        }
        user.getCards().add(card.getName());
        user.setCoins(user.getCoins() - card.getUpgradeCost());
        return "";
    }

    public List<Card> getUpgradableCards() {
        return Card.getCardDatabase().getUpgradableCards(MainMenuController.getInstance().getLoggedInUser().getCards());
    }

    public String upgradeCard(String cardName) {
        User user = MainMenuController.getInstance().getLoggedInUser();
        Card card = Card.getCardDatabase().getCard(cardName);
        if (card == null || !user.getCards().contains(cardName)) {
            return "Card not found or you don't own this card.";
        }
        if (user.getLevel() < card.getUpgradeLevel()) {
            return "Your level is not high enough to upgrade this card.";
        }
        if (user.getCoins() < card.getUpgradeCost()) {
            return "Not enough coins to upgrade this card.";
        }
        user.setCoins(user.getCoins() - card.getUpgradeCost());
        card.upgrade();
        return "";
    }
}
