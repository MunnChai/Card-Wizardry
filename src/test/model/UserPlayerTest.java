package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Deck.VIABLE_DECK_CARD_COUNT;
import static model.UserPlayer.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserPlayerTest {

    User user;
    UserPlayer userPlayer;

    @BeforeEach
    public void setup() {
        user = new User("Name");
        userPlayer = new UserPlayer(user.getSelectedDeck());
    }

    @Test
    public void testUserPlayerConstructor() {
        assertEquals(VIABLE_DECK_CARD_COUNT - USER_STARTING_HAND_AMOUNT, userPlayer.getDeck().getCardsInDeck().size());
        assertEquals(USER_STARTING_HAND_AMOUNT, userPlayer.getHand().size());
        assertEquals(USER_MAX_HEALTH, userPlayer.getHealth());
        assertEquals(USER_STARTING_ENERGY, userPlayer.getEnergy());
        assertEquals(0, userPlayer.getShield());
    }
}
