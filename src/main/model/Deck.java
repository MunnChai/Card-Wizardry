package model;

import java.util.ArrayList;
import java.util.List;

/* A deck of cards, which you can add or remove cards from. To use a deck, the deck must have the exact number of
 required cards. */
public class Deck {

    public static final int VIABLE_DECK_CARD_COUNT = 20;

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
        while (cardsInDeck.size() != VIABLE_DECK_CARD_COUNT) {
            int randomInt = (int)(Math.random() * getAvailableCards(user).size());
            cardsInDeck.add(getAvailableCards(user).get(randomInt));
        }
    }

    // EFFECTS: Produce all cards that are in user's owned cards, but not in the deck's cards
    public List<Card> getAvailableCards(User user) {
        List<Card> availableCards = new ArrayList<>();
        for (Card c : user.getOwnedCards()) {
            if (!this.cardsInDeck.contains(c)) {
                availableCards.add(c);
            }
        }
        return availableCards;
    }

    // REQUIRED:
    // EFFECTS: Add given card to deck, remove from owned cards
    public void addCard(Card card) {
        cardsInDeck.add(card);
    }

    // REQUIRES: given card must be in deck
    // EFFECTS: Remove given card from deck, add to owned cards
    public void removeCard(Card card) {
        cardsInDeck.remove(card);
    }

    // EFFECTS: Produce true if deck has viable amount of cards
    public Boolean checkViable() {
        return (cardsInDeck.size() == VIABLE_DECK_CARD_COUNT);
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
