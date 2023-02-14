package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Deck.VIABLE_DECK_CARD_COUNT;
import static model.EnemyPlayer.*;
import static org.junit.jupiter.api.Assertions.*;

public class EnemyPlayerTest {

    private EnemyPlayer enemy1;

    private User user;

    @BeforeEach
    public void setup() {
        user = new User(); // User must be instantiated for ALL_CARDS
        enemy1 = new EnemyPlayer();
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

    }

    @Test
    public void testCreateRandomIdles() {

    }

    @Test
    public void testProduceEnemyIdleDescription() {

    }

    @Test
    public void testEnemyDecisionMaking() {

    }

    @Test
    public void testPlayRandomCard() {

    }
}
