package model;

import java.util.ArrayList;
import java.util.List;

import static model.User.ALL_CARDS;

public class EnemyPlayer extends Player {

    public static final int ENEMY_MAX_HEALTH = 20;
    public static final int ENEMY_STARTING_ENERGY = 1;
    public static final int ENEMY_STARTING_HAND_AMOUNT = 3;

    private String name;

    // REQUIRES: a User must be instantiated before enemy can be instantiated
    // EFFECTS: Constructs an enemy with a random deck of cards, a random hand from those cards,
    //          a name, and the starting shield, health, and energy.
    public EnemyPlayer() {
        deck = new Deck("Enemy Deck");
        User tempAllCardsUser = new User();
        tempAllCardsUser.setOwnedCards(ALL_CARDS);
        deck.fillRandom(tempAllCardsUser);
        hand = new ArrayList<>();
        drawCard();
        drawCard();
        drawCard();

        setHealth(ENEMY_MAX_HEALTH);
        setEnergy(ENEMY_STARTING_ENERGY);
        setShield(0);
    }




    // Getters
    public String getName() {
        return name;
    }

    public List<Card> getAllCards() {
        return ALL_CARDS;
    }
}
