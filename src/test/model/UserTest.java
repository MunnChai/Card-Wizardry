package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static model.Card.CardType.*;
import static model.Deck.VIABLE_DECK_CARD_COUNT;
import static model.Enemy.*;
import static model.User.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user1;
    private User user2;

    private Enemy enemy1;

    @BeforeEach
    public void setup() {
        user1 = new User();
        user2 = new User();
        user1.startBattle();
        user2.startBattle();

        enemy1 = new Enemy();
    }

    @Test
    public void testUserConstructor() {
        assertEquals(1, user1.getDecks().size());
        assertEquals(user1.getSelectedDeck(), user1.getDecks().get(0));
        assertEquals(0, user1.getCoins());
        assertEquals(VIABLE_DECK_CARD_COUNT, user1.getDecks().get(0).getCardsInDeck().size());
        assertEquals(ALL_CARD_COUNT - user1.getOwnedCards().size(), user1.getNotOwnedCards().size());
    }

    @Test
    public void testStartBattle() {
        assertEquals(VIABLE_DECK_CARD_COUNT - USER_STARTING_HAND_AMOUNT, user1.getCurrentDeck().getCardsInDeck().size());
        assertEquals(USER_STARTING_HAND_AMOUNT, user1.getHand().size());
        assertEquals(USER_MAX_HEALTH, user1.getHealth());
        assertEquals(USER_STARTING_ENERGY, user1.getEnergy());
        assertEquals(0, user1.getShield());
    }

    @Test
    public void testDrawCard() {
        int userBeforeCount = user1.getCurrentDeck().getCardsInDeck().size();
        user1.drawCard();
        assertEquals(userBeforeCount - 1, user1.getCurrentDeck().getCardsInDeck().size());
        assertEquals(USER_STARTING_HAND_AMOUNT + 1, user1.getHand().size());
        user1.drawCard();
        assertEquals(userBeforeCount - 2, user1.getCurrentDeck().getCardsInDeck().size());
        assertEquals(USER_STARTING_HAND_AMOUNT + 2, user1.getHand().size());

        user2.drawCard();
        assertEquals(userBeforeCount - 1, user2.getCurrentDeck().getCardsInDeck().size());
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
