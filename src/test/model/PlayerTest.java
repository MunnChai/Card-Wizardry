package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static model.Card.CardType.ATTACK;
import static model.Card.CardType.HEAL;
import static model.EnemyPlayer.ENEMY_MAX_HEALTH;
import static model.UserPlayer.USER_MAX_HEALTH;
import static model.UserPlayer.USER_STARTING_HAND_AMOUNT;
import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    User user;
    Player user1;
    Player user2;

    Player enemy1;

    @BeforeEach
    public void setup() {
        user = new User();
        user1 = new UserPlayer(user.getSelectedDeck());
        user2 = new UserPlayer(user.getSelectedDeck());

        enemy1 = new EnemyPlayer();
    }

    @Test
    public void testDrawCard() {
        int userBeforeCount = user1.getDeck().getCardsInDeck().size();
        user1.drawCard();
        assertEquals(userBeforeCount - 1, user1.getDeck().getCardsInDeck().size());
        assertEquals(USER_STARTING_HAND_AMOUNT + 1, user1.getHand().size());
        user1.drawCard();
        assertEquals(userBeforeCount - 2, user1.getDeck().getCardsInDeck().size());
        assertEquals(USER_STARTING_HAND_AMOUNT + 2, user1.getHand().size());

        user2.drawCard();
        assertEquals(userBeforeCount - 1, user2.getDeck().getCardsInDeck().size());
        assertEquals(USER_STARTING_HAND_AMOUNT + 1, user2.getHand().size());
    }

    @Test
    public void testPlayCard() {

        Card sampleCard1 = new Card(ATTACK, 4, 3, 1);
        Card sampleCard2 = new Card(HEAL, 2, 5, 1);
        List<Card> userHand = new ArrayList<>();
        userHand.add(sampleCard1);
        userHand.add(sampleCard2);

        user1.setHand(userHand);
        user1.setEnergy(10);
        user1.playCard(0, enemy1);
        List<Card> expected1 = new ArrayList<>();
        expected1.add(sampleCard2);
        assertEquals(expected1, user1.getHand());
        assertEquals(ENEMY_MAX_HEALTH - 4, enemy1.getHealth());
        assertEquals(7, user1.getEnergy());

        user1.playCard(0, user1);
        expected1.remove(0);
        assertEquals(expected1, user1.getHand());
        assertEquals(USER_MAX_HEALTH, user1.getHealth());
        assertEquals(2, user1.getEnergy());

        userHand.add(sampleCard1);
        userHand.add(sampleCard2);
        user2.setHand(userHand);
        user2.setEnergy(10);
        user2.playCard(1, user2);
        List<Card> expected2 = new ArrayList<>();
        expected2.add(sampleCard1);
        assertEquals(expected2, user2.getHand());
        assertEquals(USER_MAX_HEALTH, user2.getHealth());
        assertEquals(5, user2.getEnergy());
    }

    @Test
    public void testTakeDamage() {
        user1.takeDamage(3);
        assertEquals(USER_MAX_HEALTH - 3, user1.getHealth());
        user1.takeDamage(7);
        assertEquals(USER_MAX_HEALTH - 10, user1.getHealth());

        user2.takeDamage(25);
        assertEquals(USER_MAX_HEALTH - 25, user2.getHealth());
    }

    @Test
    public void testHeal() {
        user1.takeDamage(7);
        assertEquals(USER_MAX_HEALTH - 7, user1.getHealth());
        user1.heal(3);
        assertEquals(USER_MAX_HEALTH - 4, user1.getHealth());

        user2.heal(USER_MAX_HEALTH);
        assertEquals(USER_MAX_HEALTH, user2.getHealth());
    }

    @Test
    public void testShield() {
        user1.shield(5);
        assertEquals(5, user1.getShield());
        user1.takeDamage(3);
        assertEquals(2, user1.getShield());
        assertEquals(USER_MAX_HEALTH, user1.getHealth());
        user1.takeDamage(8);
        assertEquals(0, user1.getShield());
        assertEquals(USER_MAX_HEALTH - 6, user1.getHealth());
    }
}
