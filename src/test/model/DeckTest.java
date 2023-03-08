package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static model.Card.CardType.*;
import static model.Deck.VIABLE_DECK_CARD_COUNT;
import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    User user1;

    Deck deck1;
    Deck deck2;
    Deck deck3;

    Card sampleCard1;
    Card sampleCard2;
    Card sampleCard3;

    @BeforeEach
    public void setup() {
        user1 = new User("Name");
        deck1 = new Deck("Deck 1");
        deck2 = new Deck("Deck 2");
        deck3 = new Deck("Deck 3");
        sampleCard1 = new Card(ATTACK, 3, 2, 1);
        sampleCard2 = new Card(HEAL, 1, 1, 1);
        sampleCard3 = new Card(SHIELD, 6, 4, 3);
    }

    @Test
    public void testDeckConstructor() {
        assertEquals("Deck 1", deck1.getName());
        assertEquals(0, deck1.getCardsInDeck().size());

        assertEquals("Deck 2", deck2.getName());
        assertEquals(0, deck2.getCardsInDeck().size());
    }

    @Test
    public void testFillRandom() {
        deck1.fillRandom(user1);
        assertEquals(20, deck1.getCardsInDeck().size());
        assertEquals(30, user1.getOwnedCards().size());
        assertEquals(10, deck1.getAvailableCards(user1).size());
    }

    @Test
    public void testFillRandomFull() {
        deck1.fillRandom(user1);
        assertEquals(20, deck1.getCardsInDeck().size());
        assertEquals(30, user1.getOwnedCards().size());
        assertEquals(10, deck1.getAvailableCards(user1).size());
        deck1.fillRandom(user1);
        assertEquals(20, deck1.getCardsInDeck().size());
    }

    @Test
    public void testGetAvailableCards() {
        List<Card> ownedCards = new ArrayList<>();
        ownedCards.add(sampleCard1);
        ownedCards.add(sampleCard2);
        ownedCards.add(sampleCard3);
        user1.setOwnedCards(ownedCards);

        deck1.addCard(sampleCard1);
        deck1.addCard(sampleCard2);
        user1.setSelectedDeck(deck1);

        List<Card> expected = new ArrayList<>();
        expected.add(sampleCard3);

        assertEquals(1, deck1.getAvailableCards(user1).size());
        assertEquals(expected, deck1.getAvailableCards(user1));
    }

    @Test
    public void testAddCard() {
        for (int i = 0; i < 10; i++) {
            deck2.addCard(sampleCard1);
        }

        assertEquals(10, deck2.getCardsInDeck().size());
        assertEquals(sampleCard1, deck2.getCardsInDeck().get(9));

        deck2.addCard(sampleCard2);
        assertEquals(sampleCard2, deck2.getCardsInDeck().get(10));
        assertEquals(11, deck2.getCardsInDeck().size());
    }

    @Test
    // Note: Decks can hold more cards than the viable amount, but you cannot start a battle using a deck
    //       that does not have the viable amount of cards.
    public void testAddCardMoreThanViable() {
        for (int i = 0; i < VIABLE_DECK_CARD_COUNT; i++) {
            deck1.addCard(sampleCard1);
        }

        assertEquals(VIABLE_DECK_CARD_COUNT, deck1.getCardsInDeck().size());
        assertEquals(sampleCard1, deck1.getCardsInDeck().get(19));

        deck1.addCard(sampleCard2);
        assertEquals(sampleCard2, deck1.getCardsInDeck().get(20));
        assertEquals(VIABLE_DECK_CARD_COUNT + 1, deck1.getCardsInDeck().size());

        deck1.addCard(sampleCard1);
        assertEquals(sampleCard1, deck1.getCardsInDeck().get(21));
        assertEquals(VIABLE_DECK_CARD_COUNT + 2, deck1.getCardsInDeck().size());
    }

    @Test
    public void testRemoveCard() {
        deck1.addCard(sampleCard1);
        deck1.addCard(sampleCard2);
        deck1.addCard(sampleCard3);
        deck1.removeCard(sampleCard2);

        List<Card> expected1 = new ArrayList<>();
        expected1.add(sampleCard1);
        expected1.add(sampleCard3);
        assertEquals(2, deck1.getCardsInDeck().size());
        assertEquals(expected1, deck1.getCardsInDeck());

        deck2.addCard(sampleCard2);
        deck2.addCard(sampleCard2);
        deck2.addCard(sampleCard3);
        deck2.removeCard(sampleCard2);

        List<Card> expected2 = new ArrayList<>();
        expected2.add(sampleCard2);
        expected2.add(sampleCard3);
        assertEquals(2, deck2.getCardsInDeck().size());
        assertEquals(expected2, deck2.getCardsInDeck());
    }

    @Test
    public void testCheckViableEdgeCases() {
        for (int i = 0; i < VIABLE_DECK_CARD_COUNT - 1; i++) {
            deck1.addCard(sampleCard1);
        }
        for (int i = 0; i < VIABLE_DECK_CARD_COUNT; i++) {
            deck2.addCard(sampleCard1);
        }
        for (int i = 0; i < VIABLE_DECK_CARD_COUNT + 1; i++) {
            deck3.addCard(sampleCard1);
        }

        assertFalse(deck1.checkViable());
        assertTrue(deck2.checkViable());
        assertFalse(deck3.checkViable());
    }

    @Test
    public void testCheckViableNormal() {
        for (int i = 0; i < VIABLE_DECK_CARD_COUNT * 2; i++) {
            deck1.addCard(sampleCard1);
        }

        assertFalse(deck1.checkViable());
    }

    @Test
    public void testSetName() {
        Deck newDeck = new Deck("Starting name");
        assertEquals("Starting name", newDeck.getName());
        newDeck.setName("New name");
        assertEquals("New name", newDeck.getName());
    }

    @Test
    public void testToJson() {
        // TODO
    }
}
