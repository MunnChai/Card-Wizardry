package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static model.Card.CardType.*;
import static model.Deck.VIABLE_DECK_CARD_COUNT;
import static model.EnemyPlayer.*;
import static model.User.*;
import static model.UserPlayer.*;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private User user1;

    @BeforeEach
    public void setup() {
        user1 = new User();
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
    public void testInitializeOwned() {
        assertEquals(30, user1.getOwnedCards().size());
    }

    @Test
    public void testInitializeNotOwned() {
        assertEquals(30, user1.getNotOwnedCards().size());
    }

    @Test
    public void testInitializeFirstDeck() {
        Deck firstDeck = user1.getDecks().get(0);

        assertEquals(1, user1.getDecks().size());
        assertEquals(20, firstDeck.getCardsInDeck().size());
        assertEquals("Starter Deck", firstDeck.getName());

        for (Card c : firstDeck.getCardsInDeck()) {
            assertTrue(user1.getOwnedCards().contains(c));
        }
    }

    @Test
    public void testMakeAllCards() {
        assertEquals(60, user1.makeAllCards().size());
    }

    @Test
    public void testMakeCards() {
        List<Card> testList = new ArrayList<>(user1.makeCards(4, ATTACK, 3, 2, 1));
        assertEquals(4, testList.size());
    }

    @Test
    public void testAddDeck() {
        Deck testDeck1 = new Deck("Test Deck 1");
        Deck testDeck2 = new Deck("Test Deck 2");

        user1.addDeck(testDeck1);
        assertEquals(2, user1.getDecks().size());
        assertEquals(testDeck1, user1.getDecks().get(1));

        user1.addDeck(testDeck2);
        assertEquals(3, user1.getDecks().size());
        assertEquals(testDeck2, user1.getDecks().get(2));
    }

    @Test
    public void testCanSellCards() {
        Deck starterDeck = user1.getDecks().get(0);

        assertEquals(1, user1.getDecks().size());
        assertTrue(starterDeck.checkViable());
        assertEquals(20, starterDeck.getCardsInDeck().size());

        assertEquals(10, user1.getCanSellCards().size());
    }

    @Test
    public void testCanSellAllCards() {
        user1.getDecks().remove(0);
        Deck testDeck = new Deck("Test Deck");
        user1.addDeck(testDeck);
        assertEquals(1, user1.getDecks().size());
        assertEquals(30, user1.getOwnedCards().size());
        assertEquals(30, user1.getCanSellCards().size());
    }

    @Test
    public void testCanSellNoCards() {
        user1.getDecks().remove(0);
        Deck testDeckWithAllCards = new Deck("Test Deck");
        for (Card c : user1.getOwnedCards()) {
            testDeckWithAllCards.addCard(c);
        }
        user1.addDeck(testDeckWithAllCards);

        assertEquals(30, testDeckWithAllCards.getCardsInDeck().size());
        assertEquals(30, user1.getOwnedCards().size());

        assertEquals(0, user1.getCanSellCards().size());
    }
}
