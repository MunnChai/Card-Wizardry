package model;

import java.util.ArrayList;
import java.util.List;

import static model.Card.CardType.*;

public class User {

    public static final List<Card> ALL_CARDS = new ArrayList<>();
    public static final int ALL_CARD_COUNT = 60;

    private List<Deck> decks;
    private int coins;
    private List<Card> ownedCards; // owned cards, not in deck. Other owned cards are in decks
    private List<Card> notOwnedCards; // not owned cards
    private Deck selectedDeck;

    // EFFECTS: constructs user with 1 starting deck, 0 coins, all available cards in deck, and rest
    //          of cards not available
    public User() {
        makeCards();

        initializeOwned();
        initializeNotOwned();

        initializeFirstDeck();
        setSelectedDeck(decks.get(0));
    }

    // EFFECTS: create player's decks, fill first deck with random cards from player owned cards
    public void initializeFirstDeck() {
        decks = new ArrayList<>();
        Deck firstDeck = new Deck("Starter Deck");
        firstDeck.fillRandom(this);
        decks.add(firstDeck);
    }

    // EFFECTS: Half of all cards go into owned cards
    public void initializeOwned() {
        ownedCards = new ArrayList<>();
        for (int i = 0; i < ALL_CARD_COUNT / 6; i++) {
            ownedCards.add(ALL_CARDS.get(i));
        }
        for (int i = 2 * ALL_CARD_COUNT / 6; i < 3 * ALL_CARD_COUNT / 6; i++) {
            ownedCards.add(ALL_CARDS.get(i));
        }
        for (int i = 4 * ALL_CARD_COUNT / 6; i < 5 * ALL_CARD_COUNT / 6; i++) {
            ownedCards.add(ALL_CARDS.get(i));
        }
    }

    // EFFECTS: Half of all cards go into not owned cards
    public void initializeNotOwned() {
        notOwnedCards = new ArrayList<>();
        for (int i = ALL_CARD_COUNT / 6; i < 2 * ALL_CARD_COUNT / 6; i++) {
            notOwnedCards.add(ALL_CARDS.get(i));
        }
        for (int i = 3 * ALL_CARD_COUNT / 6; i < 4 * ALL_CARD_COUNT / 6; i++) {
            notOwnedCards.add(ALL_CARDS.get(i));
        }
        for (int i = 5 * ALL_CARD_COUNT / 6; i < 6 * ALL_CARD_COUNT / 6; i++) {
            notOwnedCards.add(ALL_CARDS.get(i));
        }
    }

    // REQUIRES: only implement at the START OF A GAME
    // EFFECTS: Create all playable cards in the game
    public void makeCards() {
        ALL_CARDS.addAll(makeCards(8, ATTACK, 2, 1, 2));
        ALL_CARDS.addAll(makeCards(8, ATTACK, 4, 2, 2));
        ALL_CARDS.addAll(makeCards(8, ATTACK, 6, 3, 2));
        ALL_CARDS.addAll(makeCards(8, ATTACK, 8, 4, 3));
        ALL_CARDS.addAll(makeCards(8, ATTACK, 12, 6, 4));
        ALL_CARDS.addAll(makeCards(2, HEAL, 1, 1, 2));
        ALL_CARDS.addAll(makeCards(2, HEAL, 2, 2, 2));
        ALL_CARDS.addAll(makeCards(2, HEAL, 4, 3, 2));
        ALL_CARDS.addAll(makeCards(2, HEAL, 6, 4, 3));
        ALL_CARDS.addAll(makeCards(2, HEAL, 8, 5, 4));
        ALL_CARDS.addAll(makeCards(2, SHIELD, 1, 1, 2));
        ALL_CARDS.addAll(makeCards(2, SHIELD, 3, 2, 2));
        ALL_CARDS.addAll(makeCards(2, SHIELD, 6, 3, 2));
        ALL_CARDS.addAll(makeCards(2, SHIELD, 8, 4, 2));
        ALL_CARDS.addAll(makeCards(2, SHIELD, 10, 6, 2));
    }

    // EFFECTS: return list of 4 different cards with given type, value, energy cost, and coin cost
    public List<Card> makeCards(int amount, Card.CardType type, int value, int energyCost, int coinCost) {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            cards.add(new Card(type, value, energyCost, coinCost));
        }
        return cards;
    }

    // EFFECTS: Add given deck to list of decks
    public void addDeck(Deck deck) {
        decks.add(deck);
    }

    // EFFECTS: Produce all cards that user owns that are not in user's decks
    public List<Card> getCanSellCards() {
        List<Card> canSellCards = new ArrayList<>();
        for (Card c : ownedCards) {
            for (Deck d : decks) {
                if (!d.getCardsInDeck().contains(c)) {
                    canSellCards.add(c);
                }
            }
        }
        return canSellCards;
    }



    // Setters
    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setSelectedDeck(Deck selectedDeck) {
        this.selectedDeck = selectedDeck;
    }

    public void setOwnedCards(List<Card> ownedCards) {
        this.ownedCards = ownedCards;
    }

    public void setNotOwnedCards(List<Card> notOwnedCards) {
        this.notOwnedCards = notOwnedCards;
    }

    // Getters
    public Deck getSelectedDeck() {
        return selectedDeck;
    }

    public int getCoins() {
        return coins;
    }

    public List<Card> getOwnedCards() {
        return ownedCards;
    }

    public List<Deck> getDecks() {
        return decks;
    }

    public List<Card> getNotOwnedCards() {
        return notOwnedCards;
    }
}
