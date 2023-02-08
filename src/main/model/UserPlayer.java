package model;

import java.util.ArrayList;
import java.util.List;

public class UserPlayer extends Player {

    public static final int USER_MAX_HEALTH = 20;
    public static final int USER_STARTING_ENERGY = 1;
    public static final int USER_STARTING_HAND_AMOUNT = 3;

    public UserPlayer(Deck selectedDeck) {
        List<Card> copy = new ArrayList<>(selectedDeck.getCardsInDeck());
        deck = new Deck(selectedDeck.getName());
        deck.setCardsInDeck(copy);
        hand = new ArrayList<>();
        drawCard();
        drawCard();
        drawCard();

        setHealth(USER_MAX_HEALTH);
        setEnergy(USER_STARTING_ENERGY);
        setShield(0);
    }
}
