package model;

import java.util.List;

import static model.UserPlayer.*;

public abstract class Player {

    protected Deck deck;
    protected List<Card> hand;
    protected int health;
    protected int energy;
    protected int shield;
    protected Card cardPlayed;
    protected boolean drawnCard;

    // REQUIRES: current deck is not empty
    // MODIFIES: this
    // EFFECTS: removes a card from current deck, adds that card to hand
    public void drawCard() {
        int randomIndex = (int)(Math.random() * deck.getCardsInDeck().size());
        Card randomCard = deck.getCardsInDeck().get(randomIndex);
        deck.removeCard(randomCard);
        hand.add(randomCard);
        setDrawnCard(true);
    }

    // REQUIRES: hand is not empty
    // MODIFIES: this
    // EFFECTS: removes one card from hand, have card effect occur to given player
    public void playCard(Card card, Player target) {
        hand.remove(card);
        card.cardEffect(target);
        energy -= card.getEnergyCost();
        setCardPlayed(card);
        setDrawnCard(false);
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

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void setCardPlayed(Card cardPlayed) {
        this.cardPlayed = cardPlayed;
    }

    public void setDrawnCard(boolean drawnCard) {
        this.drawnCard = drawnCard;
    }

    // Getters
    public List<Card> getHand() {
        return hand;
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

    public Deck getDeck() {
        return deck;
    }

    public Card getCardPlayed() {
        return cardPlayed;
    }

    public boolean getDrawnCard() {
        return drawnCard;
    }
}
