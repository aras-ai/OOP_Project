package citywars.model;

import java.util.ArrayList;
import java.util.List;

public class CardDatabase {
    private List<Card> cards;

    public CardDatabase() {
        cards = new ArrayList<>();

        cards.add(new Card("Shield", "spell", 0, 1, 0, 0, 1));
        cards.add(new Card("Heal", "healing", 0, 1, 0, 0, 1));
        cards.add(new Card("Power Booster", "spell", 0, 1, 0, 0, 1));
        cards.add(new Card("Hole Position Changer", "spell", 0, 1, 0, 0, 1));
        cards.add(new Card("Repairer", "spell", 0, 1, 0, 0, 1));
        cards.add(new Card("Round Reducer", "spell", 0, 1, 0, 0, 1));
        cards.add(new Card("Remove Opponent Card", "spell", 0, 1, 0, 0, 1));
        cards.add(new Card("Weaken Opponent Card", "spell", 0, 1, 0, 0, 1));
        cards.add(new Card("Card Copier", "spell", 0, 1, 0, 0, 1));
    }

    public List<Card> getAllCards() {
        return new ArrayList<>(cards);
    }

    public List<Card> getDeck(User player) {
        return new ArrayList<>(cards);
    }

    public List<Card> getUpgradableCards(List<String> ownedCards) {
        List<Card> upgradableCards = new ArrayList<>();
        for (String cardName : ownedCards) {
            for (Card card : cards) {
                if (card.getName().equals(cardName)) {
                    upgradableCards.add(card);
                    break;
                }
            }
        }
        return upgradableCards;
    }

    public Card getCard(String name) {
        for (Card card : cards) {
            if (card.getName().equals(name)) {
                return card;
            }
        }
        return null;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public void deleteCard(String name) {
        cards.removeIf(card -> card.getName().equals(name));
    }
}
