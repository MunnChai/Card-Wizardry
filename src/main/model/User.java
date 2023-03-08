package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static model.Card.CardType.*;

// Represents the user's overall profile. Has a list of decks, a list of all the user's owned cards, a list of all the
// user's not owned cards, and the user's current selected deck. Instantiating a user also instantiates all the cards
// in the game, so User MUST be instantiated for any cards to exist.
public class User implements Writable {

    public static final List<Card> ALL_CARDS = new ArrayList<>();
    public static final int ALL_CARD_COUNT = 60;

    private String name; // This user's name
    private List<Deck> decks;
    private int coins;
    private List<Card> ownedCards; // owned cards, not in deck. Other owned cards are in decks
    private List<Card> notOwnedCards; // not owned cards
    private Deck selectedDeck;

    // EFFECTS: constructs user with 1 starting deck, 0 coins, all available cards in deck, and rest
    //          of cards not available
    public User(String name) {
        this.name = name;

        makeAllCards();

        initializeOwned();
        initializeNotOwned();

        initializeFirstDeck();
        setSelectedDeck(decks.get(0));
    }

    // REQUIRES: user has enough owned cards to fill a deck
    // MODIFIES: this
    // EFFECTS: create player's decks, fill first deck with random cards from player owned cards
    public void initializeFirstDeck() {
        decks = new ArrayList<>();
        Deck firstDeck = new Deck("Starter Deck");
        firstDeck.fillRandom(this);
        decks.add(firstDeck);
    }

    // REQUIRES: ALL_CARDS has been initialized
    // MODIFIES: this
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

    // REQUIRES: ALL_CARDS has been initialized
    // MODIFIES: this
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

    // EFFECTS: Create all playable cards in the game
    public List<Card> makeAllCards() {
        List<Card> allCards = new ArrayList<>();
        allCards.addAll(makeCards(8, ATTACK, 2, 1, 2));
        allCards.addAll(makeCards(8, ATTACK, 4, 2, 2));
        allCards.addAll(makeCards(8, ATTACK, 6, 3, 2));
        allCards.addAll(makeCards(8, ATTACK, 8, 4, 3));
        allCards.addAll(makeCards(8, ATTACK, 12, 6, 4));
        allCards.addAll(makeCards(2, HEAL, 1, 1, 2));
        allCards.addAll(makeCards(2, HEAL, 2, 2, 2));
        allCards.addAll(makeCards(2, HEAL, 4, 3, 2));
        allCards.addAll(makeCards(2, HEAL, 6, 4, 3));
        allCards.addAll(makeCards(2, HEAL, 8, 5, 4));
        allCards.addAll(makeCards(2, SHIELD, 1, 1, 2));
        allCards.addAll(makeCards(2, SHIELD, 3, 2, 2));
        allCards.addAll(makeCards(2, SHIELD, 6, 3, 2));
        allCards.addAll(makeCards(2, SHIELD, 8, 4, 2));
        allCards.addAll(makeCards(2, SHIELD, 10, 6, 2));

        ALL_CARDS.addAll(allCards);
        return allCards;
    }

    // EFFECTS: return list of n cards with given type, value, energy cost, and coin cost
    public List<Card> makeCards(int amount, Card.CardType type, int value, int energyCost, int coinCost) {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            cards.add(new Card(type, value, energyCost, coinCost));
        }
        return cards;
    }

    // MODIFIES: this
    // EFFECTS: Add given deck to list of decks
    public void addDeck(Deck deck) {
        decks.add(deck);
    }

    // REQUIRES: User must have at least 1 deck
    // EFFECTS: Produce all cards that are not in user's decks
    public List<Card> getCanSellCards() {
        List<Card> canSellCards = new ArrayList<>();
        List<Card> cardsInDecks = new ArrayList<>();

        for (Deck d : decks) {
            cardsInDecks.addAll(d.getCardsInDeck());
        }

        for (Card c : ownedCards) {
            if (!cardsInDecks.contains(c)) {
                canSellCards.add(c);
            }
        }
        return canSellCards;
    }

    // EFFECTS: returns this as JSON object
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("coins", coins);
        json.put("decks", decksToJson(decks));
        json.put("ownedCards", cardsToJson(ownedCards));
        json.put("notOwnedCards", cardsToJson(notOwnedCards));
        json.put("selectedDeck", selectedDeck.toJson());
        return json;
    }

    // EFFECTS: returns decks as JSON Array
    public JSONArray decksToJson(List<Deck> decks) {
        JSONArray jsonArray = new JSONArray();
        for (Deck deck : decks) {
            jsonArray.put(deck.toJson());
        }
        return jsonArray;
    }

    // EFFECTS: returns owned cards as JSON Array
    public JSONArray cardsToJson(List<Card> cards) {
        JSONArray jsonArray = new JSONArray();
        for (Card card : cards) {
            jsonArray.put(card.toJson());
        }
        return jsonArray;
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

    public void setDecks(List<Deck> decks) {
        this.decks = decks;
    }

    // Getters
    public String getName() {
        return name;
    }

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
