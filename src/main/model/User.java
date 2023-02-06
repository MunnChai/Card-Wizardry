package model;

import java.util.ArrayList;
import java.util.List;

import static model.Card.CardType.*;

public class User implements Player {

    public static final int USER_MAX_HEALTH = 20;
    public static final int USER_STARTING_ENERGY = 1;
    public static final int USER_STARTING_HAND_AMOUNT = 3;
    public static final List<Card> ALL_CARDS = new ArrayList<>();
    public static final int ALL_CARD_COUNT = 60;

    // Out of Battle
    private List<Deck> decks;
    private int coins;
    private List<Card> ownedCards; // owned cards, not in deck. Other owned cards are in decks
    private List<Card> notOwnedCards; // not owned cards
    private Deck selectedDeck;

    // In Battle
    private Deck currentDeck;
    private List<Card> hand;
    private int health;
    private int energy;
    private int shield;

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
        ALL_CARDS.addAll(makeFourCards(ATTACK, 1, 1, 2));
        ALL_CARDS.addAll(makeFourCards(ATTACK, 2, 2, 2));
        ALL_CARDS.addAll(makeFourCards(ATTACK, 4, 3, 2));
        ALL_CARDS.addAll(makeFourCards(ATTACK, 6, 4, 3));
        ALL_CARDS.addAll(makeFourCards(ATTACK, 10, 6, 4));
        ALL_CARDS.addAll(makeFourCards(HEAL, 2, 1, 2));
        ALL_CARDS.addAll(makeFourCards(HEAL, 4, 2, 2));
        ALL_CARDS.addAll(makeFourCards(HEAL, 6, 3, 2));
        ALL_CARDS.addAll(makeFourCards(HEAL, 8, 4, 3));
        ALL_CARDS.addAll(makeFourCards(HEAL, 10, 5, 4));
        ALL_CARDS.addAll(makeFourCards(SHIELD, 2, 1, 2));
        ALL_CARDS.addAll(makeFourCards(SHIELD, 4, 2, 2));
        ALL_CARDS.addAll(makeFourCards(SHIELD, 6, 3, 2));
        ALL_CARDS.addAll(makeFourCards(SHIELD, 8, 4, 2));
        ALL_CARDS.addAll(makeFourCards(SHIELD, 10, 5, 2));
    }

    // EFFECTS: return list of 4 different cards with given type, value, energy cost, and coin cost
    public List<Card> makeFourCards(Card.CardType type, int value, int energyCost, int coinCost) {
        List<Card> fourCards = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            fourCards.add(new Card(type, value, energyCost, coinCost));
        }
        return fourCards;
    }

    // MODIFIES: this
    // EFFECTS: at the start of a battle, set health and energy, create a copy of deck, draw a hand from selected deck
    public void startBattle() {
        List<Card> copy = new ArrayList<>(selectedDeck.getCardsInDeck());
        currentDeck = new Deck(selectedDeck.getName());
        currentDeck.setCardsInDeck(copy);
        hand = new ArrayList<>();
        drawCard();
        drawCard();
        drawCard();

        setHealth(USER_MAX_HEALTH);
        setEnergy(USER_STARTING_ENERGY);
        setShield(0);
    }

    // REQUIRES: current deck is not empty
    // MODIFIES: this
    // EFFECTS: removes a card from current deck, adds that card to hand
    public void drawCard() {
        int randomIndex = (int)(Math.random() * currentDeck.getCardsInDeck().size());
        Card randomCard = currentDeck.getCardsInDeck().get(randomIndex);
        currentDeck.removeCard(randomCard);
        hand.add(randomCard);
    }

    // REQUIRES: hand is not empty
    // MODIFIES: this
    // EFFECTS: removes one card from hand, have card effect occur to given player
    public void playCard(int index, Player target) {
        Card card = hand.get(index);
        hand.remove(card);
        card.cardEffect(target);
        energy -= card.getEnergyCost();
    }

    // MODIFIES: this
    // EFFECTS: decrease health by amount - shield, and decrease shield to 0
    public void takeDamage(int amount) {
        if (amount > shield) {
            int remainingAmount = amount - shield;
            shield = 0;
            health -= remainingAmount;
        } else {
            shield -= amount;
        }
    }

    // MODIFIES: this
    // EFFECTS: increase health by amount, cannot increase over max health
    public void heal(int amount) {
        if (health + amount > USER_MAX_HEALTH) {
            health = USER_MAX_HEALTH;
        } else {
            health += amount;
        }
    }

    // MODIFIES: this
    // EFFECTS: increase shield by amount
    public void shield(int amount) {
        shield += amount;
    }



    // Setters
    public void setCoins(int coins) {
        this.coins = coins;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
    }

    public void setCurrentDeck(Deck deck) {
        this.currentDeck = deck;
    }

    public void setSelectedDeck(Deck selectedDeck) {
        this.selectedDeck = selectedDeck;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setShield(int shield) {
        this.shield = shield;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setOwnedCards(List<Card> ownedCards) {
        this.ownedCards = ownedCards;
    }

    public void setNotOwnedCards(List<Card> notOwnedCards) {
        this.notOwnedCards = notOwnedCards;
    }

    // Getters
    public Deck getCurrentDeck() {
        return currentDeck;
    }

    public Deck getSelectedDeck() {
        return selectedDeck;
    }

    public List<Card> getHand() {
        return hand;
    }

    public int getCoins() {
        return coins;
    }

    public int getEnergy() {
        return energy;
    }

    public int getHealth() {
        return health;
    }

    public int getShield() {
        return shield;
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
