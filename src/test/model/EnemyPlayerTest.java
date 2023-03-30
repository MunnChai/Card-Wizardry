package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static model.Card.CardType.*;
import static model.Deck.VIABLE_DECK_CARD_COUNT;
import static model.EnemyPlayer.*;
import static org.junit.jupiter.api.Assertions.*;

public class EnemyPlayerTest {

    private EnemyPlayer enemy1;

    private User user;
    private UserPlayer user1;

    @BeforeEach
    public void setup() {
        user = new User("Name"); // User must be instantiated for ALL_CARDS
        enemy1 = new EnemyPlayer();
        Deck testDeck = new Deck("Test Deck");
        testDeck.fillRandom(user);
        user1 = new UserPlayer(testDeck);
    }

    @Test
    public void testEnemyConstructor() {
        assertEquals(VIABLE_DECK_CARD_COUNT - ENEMY_STARTING_HAND_AMOUNT, enemy1.getDeck().getCardsInDeck().size());
        assertEquals(ENEMY_STARTING_HAND_AMOUNT, enemy1.getHand().size());
        assertEquals(ENEMY_MAX_HEALTH, enemy1.getHealth());
        assertEquals(ENEMY_STARTING_ENERGY, enemy1.getEnergy());
        assertEquals(0, enemy1.getShield());
    }

    @Test
    public void testProduceRandomName() {
        List<String> randomNames = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {
            randomNames.add(enemy1.produceRandomEnemyName());
        }

        // With so many names being generated, the chance that one of the following names is not generated
        // is slim, so these tests will USUALLY pass

        // If it fails, go buy a lottery ticket :)

        assertTrue(randomNames.contains("NEFARIOUS GOBLIN"));
        assertTrue(randomNames.contains("SHADY CYCLOPS"));
        assertTrue(randomNames.contains("VILE OGRE"));
        assertTrue(randomNames.contains("FELONIOUS PILE_OF_ROCKS"));
    }

    @Test
    // Tests that a set few idle strings are in the enemy idles.
    public void testCreateIdles() {
        List<String> enemyIdles = enemy1.createIdles();

        assertEquals(9, enemyIdles.size());

        assertTrue(enemyIdles.contains("flips through its hand."));
        assertTrue(enemyIdles.contains("yawns, and spits on the ground."));
        assertTrue(enemyIdles.contains("scratches its head."));
        assertTrue(enemyIdles.contains("bites its nails."));
        assertTrue(enemyIdles.contains("glances around angrily, perhaps looking for a rock to throw"
                + " at you?"));
        assertTrue(enemyIdles.contains("starts to tear up."));
        assertTrue(enemyIdles.contains("does a backflip."));
        assertTrue(enemyIdles.contains("smirks at you."));
        assertTrue(enemyIdles.contains("starts doing pushups."));
    }

    @Test
    public void testProduceEnemyIdleDescriptionNormal() {
        enemy1.setHealth(20);
        user1.setHealth(20);

        List<String> expectedStrings = new ArrayList<>();
        expectedStrings.add("The " + enemy1.getName() + " flips through its hand.");
        expectedStrings.add("The " + enemy1.getName() + " yawns, and spits on the ground.");
        expectedStrings.add("The " + enemy1.getName() + " scratches its head.");

        // Test randomness a large amount of times
        for (int i = 0; i < 1000; i++) {
            String idleDescription = enemy1.produceEnemyIdleDescription(user1);
            assertTrue(expectedStrings.contains(idleDescription));
        }
    }

    @Test
    public void testProduceEnemyIdleDescriptionCocky() {
        enemy1.setHealth(20);
        user1.setHealth(4);

        List<String> expectedStrings = new ArrayList<>();
        expectedStrings.add("The " + enemy1.getName() + " does a backflip.");
        expectedStrings.add("The " + enemy1.getName() + " smirks at you.");
        expectedStrings.add("The " + enemy1.getName() + " starts doing pushups.");

        // Test randomness a large amount of times
        for (int i = 0; i < 1000; i++) {
            String idleDescription = enemy1.produceEnemyIdleDescription(user1);
            assertTrue(expectedStrings.contains(idleDescription));
        }
    }

    @Test
    public void testProduceEnemyIdleDescriptionWorried() {
        enemy1.setHealth(4);
        user1.setHealth(20);

        List<String> expectedStrings = new ArrayList<>();
        expectedStrings.add("The " + enemy1.getName() + " bites its nails.");
        expectedStrings.add("The " + enemy1.getName() + " glances around angrily, perhaps looking for a rock to throw "
                + "at you?");
        expectedStrings.add("The " + enemy1.getName() + " starts to tear up.");

        // Test randomness a large amount of times
        for (int i = 0; i < 1000; i++) {
            String idleDescription = enemy1.produceEnemyIdleDescription(user1);
            assertTrue(expectedStrings.contains(idleDescription));
        }
    }

    @Test
    public void testEnemyDecisionMakingEmptyHand() {
        List<Card> emptyHand = new ArrayList<>();
        enemy1.setHand(emptyHand);
        enemy1.enemyDecisionMaking(user1);
        assertTrue(enemy1.getIfDrewCard());
        assertEquals(1, enemy1.getHand().size());
    }

    @Test
    public void testEnemyDecisionMakingNonEmptyHand() {
        List<Card> notEmptyHand = new ArrayList<>();
        Card testCard = new Card(ATTACK, 1, 1, 1, 0);
        notEmptyHand.add(testCard);
        notEmptyHand.add(testCard);
        notEmptyHand.add(testCard);
        enemy1.setHand(notEmptyHand);

        enemy1.enemyDecisionMaking(user1);
        assertFalse(enemy1.getIfDrewCard());
        assertEquals(testCard, enemy1.getLastCardPlayed());
        assertEquals(2, enemy1.getHand().size());
    }

    @Test
    public void testPlayRandomCardHasEnergyAttackCard() {
        List<Card> attackCards = new ArrayList<>();
        Card attackCard = new Card(ATTACK, 1, 1, 1, 1);
        attackCards.add(attackCard);
        attackCards.add(attackCard);
        attackCards.add(attackCard);
        enemy1.setHand(attackCards);
        int enemyInitialHandSize = enemy1.getHand().size();
        enemy1.setEnergy(200);
        enemy1.playRandomCard(user1);
        assertEquals(enemyInitialHandSize - 1, enemy1.getHand().size());
    }

    @Test
    public void testPlayRandomCardHasEnergyHealCard() {
        List<Card> healCards = new ArrayList<>();
        Card healCard = new Card(HEAL, 1, 1, 1, 2);
        healCards.add(healCard);
        healCards.add(healCard);
        healCards.add(healCard);
        enemy1.setHand(healCards);
        int enemyInitialHandSize = enemy1.getHand().size();
        enemy1.setEnergy(200);
        enemy1.playRandomCard(user1);
        assertEquals(enemyInitialHandSize - 1, enemy1.getHand().size());
    }

    @Test
    public void testPlayRandomCardNoEnergy() {
        int enemyInitialHandSize = enemy1.getHand().size();
        enemy1.setEnergy(0);
        enemy1.playRandomCard(user1);
        assertEquals(enemyInitialHandSize + 1, enemy1.getHand().size());
    }
}
