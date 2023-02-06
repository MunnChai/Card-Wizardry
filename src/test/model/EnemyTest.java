package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static model.Card.CardType.*;
import static model.Deck.VIABLE_DECK_CARD_COUNT;
import static model.Enemy.*;
import static model.User.USER_MAX_HEALTH;
import static org.junit.jupiter.api.Assertions.*;

public class EnemyTest {

    private Enemy enemy1;
    private Enemy enemy2;

    private User user1;

    @BeforeEach
    public void setup() {
        enemy1 = new Enemy();
        enemy2 = new Enemy();

        user1 = new User();
        user1.startBattle();
    }

    @Test
    public void testEnemyConstructor() {
        assertEquals(VIABLE_DECK_CARD_COUNT - ENEMY_STARTING_HAND_AMOUNT, enemy1.getCurrentDeck().getCardsInDeck().size());
        assertEquals(ENEMY_STARTING_HAND_AMOUNT, enemy1.getHand().size());
        assertEquals(ENEMY_MAX_HEALTH, enemy1.getHealth());
        assertEquals(ENEMY_STARTING_ENERGY, enemy1.getEnergy());
        assertEquals(0, enemy1.getShield());
    }

    @Test
    public void testDrawCard() {
        enemy1.drawCard();
        assertEquals(VIABLE_DECK_CARD_COUNT - ENEMY_STARTING_HAND_AMOUNT -  1,
                enemy1.getCurrentDeck().getCardsInDeck().size());
        assertEquals(ENEMY_STARTING_HAND_AMOUNT + 1, enemy1.getHand().size());
        enemy1.drawCard();
        assertEquals(VIABLE_DECK_CARD_COUNT - ENEMY_STARTING_HAND_AMOUNT - 2,
                enemy1.getCurrentDeck().getCardsInDeck().size());
        assertEquals(ENEMY_STARTING_HAND_AMOUNT + 2, enemy1.getHand().size());

        enemy2.drawCard();
        assertEquals(VIABLE_DECK_CARD_COUNT - ENEMY_STARTING_HAND_AMOUNT - 1,
                enemy2.getCurrentDeck().getCardsInDeck().size());
        assertEquals(ENEMY_STARTING_HAND_AMOUNT + 1, enemy2.getHand().size());
    }

    @Test
    public void testPlayCard() {

        Card sampleCard1 = new Card(ATTACK, 1, 1, 1);
        Card sampleCard2 = new Card(HEAL, 2, 2, 1);
        List<Card> enemyHand = new ArrayList<>();
        enemyHand.add(sampleCard1);
        enemyHand.add(sampleCard2);

        enemy1.setHand(enemyHand);
        enemy1.playCard(0, user1);
        List<Card> expected1 = new ArrayList<>();
        expected1.add(sampleCard2);
        assertEquals(expected1, enemy1.getHand());
        assertEquals(USER_MAX_HEALTH - 1, user1.getHealth());

        enemy1.playCard(0, enemy1);
        expected1.remove(0);
        assertEquals(expected1, enemy1.getHand());
        assertEquals(ENEMY_MAX_HEALTH, enemy1.getHealth());

        enemyHand.add(sampleCard1);
        enemyHand.add(sampleCard2);
        enemy2.setHand(enemyHand);
        enemy2.playCard(1, enemy2);
        List<Card> expected2 = new ArrayList<>();
        expected2.add(sampleCard1);
        assertEquals(expected2, enemy2.getHand());
        assertEquals(ENEMY_MAX_HEALTH, enemy2.getHealth());
    }

    @Test
    public void testTakeDamage() {
        enemy1.takeDamage(3);
        assertEquals(ENEMY_MAX_HEALTH - 3, enemy1.getHealth());
        enemy1.takeDamage(7);
        assertEquals(ENEMY_MAX_HEALTH - 10, enemy1.getHealth());

        enemy2.takeDamage(25);
        assertEquals(ENEMY_MAX_HEALTH - 25, enemy2.getHealth());
    }

    @Test
    public void testHeal() {
        enemy1.takeDamage(7);
        assertEquals(ENEMY_MAX_HEALTH - 7, enemy1.getHealth());
        enemy1.heal(3);
        assertEquals(ENEMY_MAX_HEALTH - 4, enemy1.getHealth());

        enemy2.heal(ENEMY_MAX_HEALTH);
        assertEquals(ENEMY_MAX_HEALTH, enemy2.getHealth());
    }

    @Test
    public void testShield() {
        enemy1.shield(5);
        assertEquals(5, enemy1.getShield());
        enemy1.takeDamage(3);
        assertEquals(2, enemy1.getShield());
        assertEquals(ENEMY_MAX_HEALTH, enemy1.getHealth());
        enemy1.takeDamage(8);
        assertEquals(0, enemy1.getShield());
        assertEquals(ENEMY_MAX_HEALTH - 6, enemy1.getHealth());
    }
}
