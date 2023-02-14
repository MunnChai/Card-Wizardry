package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static model.Card.CardType.ATTACK;
import static model.Card.CardType.HEAL;
import static model.EnemyPlayer.ENEMY_MAX_HEALTH;
import static model.EnemyPlayer.ENEMY_STARTING_HAND_AMOUNT;
import static model.UserPlayer.USER_MAX_HEALTH;
import static model.UserPlayer.USER_STARTING_HAND_AMOUNT;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    User user;
    Player user1;
    Player enemy1;

    Card sampleCard1;
    Card sampleCard2;

    @BeforeEach
    public void setup() {
        user = new User();
        user1 = new UserPlayer(user.getSelectedDeck());
        enemy1 = new EnemyPlayer();

        sampleCard1 = new Card(ATTACK, 4, 3, 1);
        sampleCard2 = new Card(HEAL, 2, 5, 1);
    }

    @Test
    public void testUserDrawCard() {
        int userDeckCount = user1.getDeck().getCardsInDeck().size();
        user1.drawCard();
        assertEquals(userDeckCount - 1, user1.getDeck().getCardsInDeck().size());
        assertEquals(USER_STARTING_HAND_AMOUNT + 1, user1.getHand().size());
        user1.drawCard();
        assertEquals(userDeckCount - 2, user1.getDeck().getCardsInDeck().size());
        assertEquals(USER_STARTING_HAND_AMOUNT + 2, user1.getHand().size());
    }

    @Test
    public void testEnemyDrawCard() {
        int enemyDeckCount = enemy1.getDeck().getCardsInDeck().size();
        enemy1.drawCard();
        assertEquals(enemyDeckCount - 1, enemy1.getDeck().getCardsInDeck().size());
        assertEquals(ENEMY_STARTING_HAND_AMOUNT + 1, enemy1.getHand().size());
        enemy1.drawCard();
        assertEquals(enemyDeckCount - 2, enemy1.getDeck().getCardsInDeck().size());
        assertEquals(ENEMY_STARTING_HAND_AMOUNT + 2, enemy1.getHand().size());
    }

    @Test
    public void testUserPlayCard() {
        List<Card> userHand = new ArrayList<>();
        userHand.add(sampleCard1);
        userHand.add(sampleCard2);

        user1.setHand(userHand);
        user1.setEnergy(10);
        user1.playCard(sampleCard1, enemy1);
        List<Card> expected1 = new ArrayList<>();
        expected1.add(sampleCard2);
        assertEquals(expected1, user1.getHand());
        assertEquals(ENEMY_MAX_HEALTH - 4, enemy1.getHealth());
        assertEquals(7, user1.getEnergy());

        user1.playCard(sampleCard2, user1);
        expected1.remove(sampleCard2);
        assertEquals(expected1, user1.getHand());
        assertEquals(USER_MAX_HEALTH, user1.getHealth());
        assertEquals(2, user1.getEnergy());
    }

    @Test
    public void testEnemyPlayCard() {
        List<Card> enemyHand = new ArrayList<>();
        enemyHand.add(sampleCard1);
        enemyHand.add(sampleCard2);

        enemy1.setHand(enemyHand);
        enemy1.setEnergy(10);
        enemy1.playCard(sampleCard1, user1);
        List<Card> expected1 = new ArrayList<>();
        expected1.add(sampleCard2);
        assertEquals(expected1, enemy1.getHand());
        assertEquals(USER_MAX_HEALTH - 4, user1.getHealth());
        assertEquals(7, enemy1.getEnergy());

        enemy1.playCard(sampleCard2, enemy1);
        expected1.remove(sampleCard2);
        assertEquals(expected1, enemy1.getHand());
        assertEquals(ENEMY_MAX_HEALTH, enemy1.getHealth());
        assertEquals(2, enemy1.getEnergy());
    }

    @Test
    public void testUserTakeDamage() {
        user1.takeDamage(3);
        assertEquals(USER_MAX_HEALTH - 3, user1.getHealth());
        user1.takeDamage(7);
        assertEquals(USER_MAX_HEALTH - 10, user1.getHealth());
    }

    @Test
    public void testEnemyTakeDamage() {
        enemy1.takeDamage(5);
        assertEquals(ENEMY_MAX_HEALTH - 5, enemy1.getHealth());
        enemy1.takeDamage(9);
        assertEquals(ENEMY_MAX_HEALTH - 14, enemy1.getHealth());
    }

    @Test
    public void testUserHeal() {
        user1.takeDamage(7);
        assertEquals(USER_MAX_HEALTH - 7, user1.getHealth());
        user1.heal(3);
        assertEquals(USER_MAX_HEALTH - 4, user1.getHealth());
    }

    @Test
    public void testEnemyHeal() {
        enemy1.takeDamage(4);
        assertEquals(ENEMY_MAX_HEALTH - 4, enemy1.getHealth());
        enemy1.heal(10);
        assertEquals(ENEMY_MAX_HEALTH, enemy1.getHealth());
    }

    @Test
    public void testUserShield() {
        user1.shield(5);
        user1.takeDamage(3);
        assertEquals(2, user1.getShield());
        assertEquals(USER_MAX_HEALTH, user1.getHealth());
        user1.takeDamage(8);
        assertEquals(0, user1.getShield());
        assertEquals(USER_MAX_HEALTH - 6, user1.getHealth());
    }

    @Test
    public void testEnemyShield() {
        enemy1.shield(10);
        enemy1.takeDamage(3);
        assertEquals(7, enemy1.getShield());
        assertEquals(ENEMY_MAX_HEALTH, enemy1.getHealth());
        enemy1.takeDamage(17);
        assertEquals(0, enemy1.getShield());
        assertEquals(ENEMY_MAX_HEALTH - 10, enemy1.getHealth());
    }
}
