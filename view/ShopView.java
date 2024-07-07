package citywars.view;

import citywars.model.Card;

import java.util.List;

public class ShopView extends MainView {
    public void display() {
        System.out.print("SHOP> ");
    }

    public void displayAllCards(List<Card> allCards) {
        System.out.println("All Cards:");
        for (Card card : allCards) {
            System.out.println(card);
        }
    }

    public void displayUpgradableCards(List<Card> upgradableCards) {
        System.out.println("Upgradable Cards:");
        for (Card card : upgradableCards) {
            System.out.println(card);
        }
    }
}
