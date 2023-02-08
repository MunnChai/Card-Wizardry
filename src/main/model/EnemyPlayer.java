package model;

import java.util.ArrayList;
import java.util.List;

import static model.User.ALL_CARDS;

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

    private String name;
    private List<String> enemyIdles;

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
        createRandomIdles();
    }

    // EFFECTS: Produce random string name
    public String produceRandomEnemyName() {
        int randInt1 = (int)(Math.random() * Adjectives.values().length);
        int randInt2 = (int)(Math.random() * Nouns.values().length);
        return Adjectives.values()[randInt1].name() + " " + Nouns.values()[randInt2].name();
    }

    public void createRandomIdles() {
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
    }

    // EFFECTS: Produce random string for enemy idle description, reliant on enemy's health and player's health
    public String produceEnemyIdleDescription(UserPlayer user) {
        if (health < 7) {
            int randInt = (int)(Math.random() * 3) + 3;
            return "The " + name + " " + enemyIdles.get(randInt);
        } else if (health >= 7 && user.getHealth() < 7) {
            int randInt = (int)(Math.random() * 3) + 6;
            return "The " + name + " " + enemyIdles.get(randInt);
        } else {
            int randInt = (int)(Math.random() * 3);
            return "The " + name + " " + enemyIdles.get(randInt);
        }
    }

    // Getters
    public String getName() {
        return name;
    }

    public List<Card> getAllCards() {
        return ALL_CARDS;
    }

    // Setters

    public void setName(String name) {
        this.name = name;
    }
}
