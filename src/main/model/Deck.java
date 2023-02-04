package model;

import java.util.ArrayList;
import java.util.List;

/* A deck of cards, which you can add or remove cards from. To use a deck, the deck must have the exact number of
 required cards. */
public class Deck {

    public static final int VIABLE_DECK_CARD_COUNT = 20;

    private String name;
    private List<Card> cards;

    // EFFECTS: Constructs an empty deck
    public Deck(String name) {
        this.name = name;
        cards = new ArrayList<Card>();
    }

    // MODIFIES: this
    // EFFECTS: fills list of cards with random cards until viable number of cards are in the deck
    public void fillRandom() {

    }

    // EFFECTS: Add given card to deck, remove from owned cards
    public void addCard(Card card) {
        cards.add(card);
    }

    // REQUIRES: given card must be in deck
    // EFFECTS: Remove given card from deck, add to owned cards
    public void removeCard(Card card) {
        cards.remove(card);
    }

    // EFFECTS: Produce true if deck has viable amount of cards
    public Boolean checkViable() {
        return false; // stub
    }

    public List<Card> getCards() {
        return cards;
    }

    public String getName() {
        return name;
    }
}
