package model;

import java.util.List;

import static model.UserPlayer.*;

// A class for any player in a battle. The player has a deck, hand, health, energy, and shield. It can play or draw a
// card, take or heal damage.
public class Player {

    protected Deck deck;            // player's selected deck for battle
    protected List<Card> hand;      // player's current hand of cards
    protected int health;           // player's current health
    protected int energy;           // player's current energy
    protected int shield;           // player's current shield
    protected Card lastCardPlayed;      // tracks the last card played by the player
    protected boolean ifDrewCard;   // tracks if the last action done by player is drawing a card

    // REQUIRES: current deck is not empty
    // MODIFIES: this
    // EFFECTS: removes a card from current deck, adds that card to hand
    public void drawCard() {
        int randomIndex = (int)(Math.random() * deck.getCardsInDeck().size());
        Card randomCard = deck.getCardsInDeck().get(randomIndex);
        deck.removeCard(randomCard);
        hand.add(randomCard);
        ifDrewCard = true;
    }

    // REQUIRES: hand is not empty
    // MODIFIES: this
    // EFFECTS: removes one card from hand, have card effect occur to given player
    public void playCard(Card card, Player target) {
        hand.remove(card);
        card.cardEffect(target);
        energy -= card.getEnergyCost();
        lastCardPlayed = card;
        ifDrewCard = false;
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

    // EFFECTS: return true if player's energy is greater or equal to the energy cost of the card, false otherwise.
    public boolean canPlayCard(Card c) {
        return energy >= c.getEnergyCost();
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


    public void setIfDrewCard(boolean ifDrewCard) {
        this.ifDrewCard = ifDrewCard;
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

    public Card getLastCardPlayed() {
        return lastCardPlayed;
    }

    public boolean getIfDrewCard() {
        return ifDrewCard;
    }
}
