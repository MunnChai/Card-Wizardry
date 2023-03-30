package model;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// Represents a deck of cards, with a name, and a list of all the cards in the deck
public class Deck implements Writable {

    public static final int VIABLE_DECK_CARD_COUNT = 20;    // A deck must have this many cards to be viable for battle

    private String name;
    private List<Card> cardsInDeck;

    // EFFECTS: Constructs an empty deck
    public Deck(String name) {
        this.name = name;
        cardsInDeck = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: fills list of cards with random cards from the given player's cards until viable number of cards are in
    // the deck.
    public void fillRandom(User user) {
        List<Card> canAddCards = getCanAddCards(user);
        while (cardsInDeck.size() < VIABLE_DECK_CARD_COUNT && canAddCards.size() > 0) {
            int randomInt = (int)(Math.random() * canAddCards.size());
            cardsInDeck.add(canAddCards.get(randomInt));
            canAddCards.remove(randomInt);
        }
    }

    // EFFECTS: Produce all cards that are in user's owned cards, but not in the deck's cards
    public List<Card> getCanAddCards(User user) {
        List<Card> availableCards = new ArrayList<>();
        for (Card c : user.getOwnedCards()) {
            if (!this.cardsInDeck.contains(c)) {
                availableCards.add(c);
            }
        }
        return availableCards;
    }

    // MODIFIES: this
    // EFFECTS: Add given card to deck at index 0
    public void addCard(Card card) {
        cardsInDeck.add(0, card);
    }

    // MODIFIES: this
    // EFFECTS: Remove given card from deck
    public void removeCard(Card card) {
        cardsInDeck.remove(card);
    }

    // EFFECTS: Produce true if deck has viable amount of cards
    public Boolean checkViable() {
        return (cardsInDeck.size() == VIABLE_DECK_CARD_COUNT);
    }

    // EFFECTS: returns deck as JSON Object
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", getName());
        jsonObject.put("cardsInDeck", getCardsInDeck());
        return jsonObject;
    }

    // Setters
    public void setCardsInDeck(List<Card> cardsInDeck) {
        this.cardsInDeck = cardsInDeck;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getters
    public List<Card> getCardsInDeck() {
        return cardsInDeck;
    }

    public String getName() {
        return name;
    }
}
