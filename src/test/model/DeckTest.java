package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static model.Card.CardType.*;
import static model.Deck.VIABLE_DECK_CARD_COUNT;
import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    Deck deck1;
    Deck deck2;
    Deck deck3;

    Card sampleCard1;
    Card sampleCard2;
    Card sampleCard3;

    @BeforeEach
    public void setup() {
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
        assertEquals(0, deck1.getCards().size());

        assertEquals("Deck 2", deck2.getName());
        assertEquals(0, deck2.getCards().size());
    }

    @Test
    public void testAddCard() {
        for (int i = 0; i < 20; i++) {
            deck1.addCard(sampleCard1);
        }
        for (int i = 0; i < 10; i++) {
            deck2.addCard(sampleCard1);
        }

        assertEquals(20, deck1.getCards().size());
        assertEquals(sampleCard1, deck1.getCards().get(19));

        deck1.addCard(sampleCard2);
        assertEquals(sampleCard2, deck1.getCards().get(20));
        assertEquals(21, deck1.getCards().size());

        deck1.addCard(sampleCard1);
        assertEquals(sampleCard1, deck1.getCards().get(21));
        assertEquals(22, deck1.getCards().size());

        assertEquals(10, deck2.getCards().size());
        assertEquals(sampleCard1, deck2.getCards().get(9));

        deck2.addCard(sampleCard2);
        assertEquals(sampleCard2, deck2.getCards().get(10));
        assertEquals(11, deck2.getCards().size());
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
        assertEquals(2, deck1.getCards().size());
        assertEquals(expected1, deck1.getCards());

        deck2.addCard(sampleCard2);
        deck2.addCard(sampleCard2);
        deck2.addCard(sampleCard3);
        deck2.removeCard(sampleCard2);

        List<Card> expected2 = new ArrayList<>();
        expected2.add(sampleCard2);
        expected2.add(sampleCard3);
        assertEquals(2, deck2.getCards().size());
        assertEquals(expected2, deck2.getCards());
    }

    @Test
    public void testCheckViable() {
        for (int i = 0; i < VIABLE_DECK_CARD_COUNT - 10; i++) {
            deck1.addCard(sampleCard1);
        }
        for (int i = 0; i < VIABLE_DECK_CARD_COUNT; i++) {
            deck2.addCard(sampleCard1);
        }
        for (int i = 0; i < VIABLE_DECK_CARD_COUNT + 10; i++) {
            deck3.addCard(sampleCard1);
        }

        assertFalse(deck1.checkViable());
        assertTrue(deck2.checkViable());
        assertFalse(deck3.checkViable());

        for (int i = 0; i < 10; i++) {
            deck1.addCard(sampleCard1);
        }

        assertTrue(deck1.checkViable());
    }
}
