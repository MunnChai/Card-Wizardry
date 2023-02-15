package model;

import java.util.ArrayList;
import java.util.List;

import static model.Card.CardType.*;
import static model.User.ALL_CARDS;
import static model.UserPlayer.USER_MAX_HEALTH;

// Represents an enemy in battle, with a name, list of idle descriptions, and inherits all the traits from the Player
// superclass
public class EnemyPlayer extends Player {

    enum Adjectives {
        NEFARIOUS, SHADY, VILE, WICKED, ATROCIOUS, CORRUPT, CROOKED, CRUEL, DEPLORABLE, DIABOLICAL, FELONIOUS
    }

    enum Nouns {
        GOBLIN, CYCLOPS, OGRE, GNOME, WEREWOLF, SERPENT, SPIDER, GORGON, TROLL, PILE_OF_ROCKS
    }

    public static final int ENEMY_MAX_HEALTH = 20;
    public static final int ENEMY_STARTING_ENERGY = 1;
    public static final int ENEMY_STARTING_HAND_AMOUNT = 3;

    private String name;                    // enemy name
    private List<String> enemyIdles;        // list of enemy's idle descriptions


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

        setName(produceRandomEnemyName());
        setHealth(ENEMY_MAX_HEALTH);
        setEnergy(ENEMY_STARTING_ENERGY);
        setShield(0);
        createIdles();
    }

    // EFFECTS: Produce random string name
    public String produceRandomEnemyName() {
        int randInt1 = (int)(Math.random() * Adjectives.values().length);
        int randInt2 = (int)(Math.random() * Nouns.values().length);
        return Adjectives.values()[randInt1].name() + " " + Nouns.values()[randInt2].name();
    }

    public List<String> createIdles() {
        List<String> possibleIdles = new ArrayList<>();
        possibleIdles.add("flips through its hand.");
        possibleIdles.add("yawns, and spits on the ground.");
        possibleIdles.add("scratches its head.");

        possibleIdles.add("bites its nails.");
        possibleIdles.add("glances around angrily, perhaps looking for a rock to throw at you?");
        possibleIdles.add("starts to tear up.");

        possibleIdles.add("does a backflip.");
        possibleIdles.add("smirks at you.");
        possibleIdles.add("starts doing pushups.");

        enemyIdles = possibleIdles;
        return possibleIdles;
    }

    // EFFECTS: Produce random string for enemy idle description, dependent on enemy's health and player's health
    public String produceEnemyIdleDescription(UserPlayer user) {
        int randInt;

        if (health < ENEMY_MAX_HEALTH / 3) {
            randInt = (int)(Math.random() * 3) + 3;
            return "The " + name + " " + enemyIdles.get(randInt);
        } else if (health >= ENEMY_MAX_HEALTH / 3 && user.getHealth() < USER_MAX_HEALTH / 3) {
            randInt = (int)(Math.random() * 3) + 6;
            return "The " + name + " " + enemyIdles.get(randInt);
        } else {
            randInt = (int)(Math.random() * 3);
            return "The " + name + " " + enemyIdles.get(randInt);
        }
    }

    // EFFECTS: If hand is empty, draw a card. Otherwise, play a random card.
    public void enemyDecisionMaking(UserPlayer userPlayer) {
        if (getHand().size() == 0) {
            setIfDrewCard(true);
            drawCard();
        } else {
            setIfDrewCard(false);
            playRandomCard(userPlayer);
        }
    }

    // EFFECTS: Picks a random card to play. If enemy has enough energy to play the card, it plays the card. Otherwise,
    //          it draws a card.
    public void playRandomCard(UserPlayer userPlayer) {
        int randInt = (int)(Math.random() * hand.size());
        Card randCard = hand.get(randInt);
        if (canPlayCard(randCard)) {
            if (randCard.getType().isCardType(ATTACK)) {
                playCard(randCard, userPlayer);
            } else {
                playCard(randCard, this);
            }
        } else {
            drawCard();
        }
    }



    // Getters
    public String getName() {
        return name;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }
}
