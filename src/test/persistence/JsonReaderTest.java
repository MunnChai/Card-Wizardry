package persistence;

import model.Deck;
import model.Shop;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    @Test
    public void testFileNotFound() {
        JsonReader reader = new JsonReader("./data/fileDoesNotExist");

        try {
            User user = reader.read();
            fail("Expected Exception not thrown");
        } catch (IOException e) {
            // Pass
        }
    }

    @Test
    public void testReadDefaultUser() {
        JsonReader reader = new JsonReader("./data/testJsonReaderDefaultUser");

        try {
            User user = reader.read();

            assertEquals("Default User", user.getName());
            assertEquals(1, user.getDecks().size());
            assertEquals(30, user.getOwnedCards().size());
            assertEquals(30, user.getNotOwnedCards().size());
            assertEquals(0, user.getCoins());
        } catch (IOException e) {
            fail("Unexpected Exception caught");
        }
    }

    @Test
    public void testReadGeneralUser() {
        JsonReader reader = new JsonReader("./data/testJsonReaderGeneralUser");

        try {
            User user = reader.read();

            assertEquals("General User", user.getName());
            assertEquals(2, user.getDecks().size());
            assertEquals("Second Deck", user.getDecks().get(1).getName());
            assertEquals(0, user.getDecks().get(1).getCardsInDeck().size());
            assertEquals(30, user.getOwnedCards().size());
            assertEquals(30, user.getNotOwnedCards().size());
            assertEquals(15, user.getCoins());
        } catch (IOException e) {
            fail("Unexpected Exception caught");
        }
    }

    @Test
    public void testReadDefaultShop() {
        JsonReader reader = new JsonReader("./data/testJsonReaderDefaultShop");

        try {
            Shop shop = reader.readShop();

            assertEquals(4, shop.getCardsForSale().size());
        } catch (IOException e) {
            fail("Unexpected Exception caught");
        }
    }

    @Test
    public void testReadGeneralShop() {
        JsonReader reader = new JsonReader("./data/testJsonReaderGeneralShop");

        try {
            Shop shop = reader.readShop();

            assertEquals(2, shop.getCardsForSale().size());
        } catch (IOException e) {
            fail("Unexpected Exception caught");
        }
    }
}
