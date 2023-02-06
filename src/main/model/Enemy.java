package model;

import java.util.ArrayList;
import java.util.List;

import static model.User.ALL_CARDS;

public class Enemy implements Player {

    public static final int ENEMY_MAX_HEALTH = 20;
    public static final int ENEMY_STARTING_ENERGY = 1;
    public static final int ENEMY_STARTING_HAND_AMOUNT = 3;

    private Deck deck;
    private List<Card> hand;

    private String name;
    private int health;
    private int energy;
    private int shield;

    // REQUIRES: a User must be instantiated before enemy can be instantiated
    // EFFECTS: Constructs an enemy with a random deck of cards, a random hand from those cards,
    //          a name, and the starting shield, health, and energy.
    public Enemy() {
        deck = new Deck("Enemy Deck");
        deck.fillRandom(this);
        hand = new ArrayList<>();
        drawCard();
        drawCard();
        drawCard();

        setHealth(ENEMY_MAX_HEALTH);
        setEnergy(ENEMY_STARTING_ENERGY);
        setShield(0);
    }


    // REQUIRES: deck is not empty
    // MODIFIES: this
    // EFFECTS: removes a card from deck, adds that card to hand
    public void drawCard() {
        int randomIndex = (int)(Math.random() * deck.getCardsInDeck().size());
        Card randomCard = deck.getCardsInDeck().get(randomIndex);
        deck.removeCard(randomCard);
        hand.add(randomCard);
    }

    // REQUIRES: hand is not empty
    // MODIFIES: this
    // EFFECTS: removes one card from hand, subtract energy cost from player energy, have card effect occur
    public void playCard(int index, Player target) {
        Card card = hand.get(index);
        hand.remove(card);
        card.cardEffect(target);
        energy -= card.getEnergyCost();
    }

    // MODIFIES: this
    // EFFECTS: decrease health by amount
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
        if (health + amount > ENEMY_MAX_HEALTH) {
            health = ENEMY_MAX_HEALTH;
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
    public void setHand(List<Card> hand) {
        this.hand = hand;
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

    // Getters
    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getEnergy() {
        return energy;
    }

    public List<Card> getHand() {
        return hand;
    }

    public Deck getCurrentDeck() {
        return deck;
    }

    public int getShield() {
        return shield;
    }

    public List<Card> getOwnedCards() {
        return ALL_CARDS;
    }
}
