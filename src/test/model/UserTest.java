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




}
