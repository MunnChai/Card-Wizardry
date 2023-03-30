package persistence;

import model.Deck;
import model.Shop;
import model.User;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {

    @Test
    public void testFileNotFound() {
        try {
            User user = new User("Name");
            JsonWriter writer = new JsonWriter("./data/illegal\0file.name:");
            writer.open();
            fail("Expected Exception was not thrown");
        } catch (FileNotFoundException e) {
            // pass
        }
    }

    @Test
    public void testWriteDefaultUser() {
        try {
            User user = new User("Default User");
            JsonWriter writer = new JsonWriter("./data/testJsonWriterDefaultUser");
            writer.open();
            writer.write(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testJsonWriterDefaultUser");
            User loadedUser = reader.read();
            assertEquals("Default User", loadedUser.getName());
            assertEquals(1, loadedUser.getDecks().size());
            assertEquals(30, loadedUser.getOwnedCards().size());
            assertEquals(30, loadedUser.getNotOwnedCards().size());
            assertEquals(0, loadedUser.getCoins());
        } catch (IOException e) {
            fail("Unexpected exception thrown");
        }
    }

    @Test
    public void testWriteGeneralUser() {
        try {
            User user = new User("General User");
            Deck newDeck = new Deck("Second Deck");
            user.addDeck(newDeck);
            user.setCoins(15);
            JsonWriter writer = new JsonWriter("./data/testJsonWriterGeneralUser");
            writer.open();
            writer.write(user);
            writer.close();

            JsonReader reader = new JsonReader("./data/testJsonWriterGeneralUser");
            User loadedUser = reader.read();
            assertEquals("General User", loadedUser.getName());
            assertEquals(2, loadedUser.getDecks().size());
            assertEquals("Second Deck", loadedUser.getDecks().get(1).getName());
            assertEquals(0, loadedUser.getDecks().get(1).getCardsInDeck().size());
            assertEquals(30, loadedUser.getOwnedCards().size());
            assertEquals(30, loadedUser.getNotOwnedCards().size());
            assertEquals(15, loadedUser.getCoins());
        } catch (IOException e) {
            fail("Unexpected exception thrown");
        }
    }

    @Test
    public void testWriteDefaultShop() {
        try {
            User user = new User("Default User");
            Shop shop = new Shop(user);
            JsonWriter writer = new JsonWriter("./data/testJsonWriterDefaultShop");
            writer.open();
            writer.write(shop);
            writer.close();

            JsonReader reader = new JsonReader("./data/testJsonWriterDefaultShop");
            Shop loadedShop = reader.readShop();
            assertEquals(4, loadedShop.getCardsForSale().size());
        } catch (IOException e) {
            fail("Unexpected exception thrown");
        }
    }

    @Test
    public void testWriteGeneralShop() {
        try {
            User user = new User("General User");
            Shop shop = new Shop(user);
            shop.buyCard(0);
            shop.buyCard(2);
            JsonWriter writer = new JsonWriter("./data/testJsonWriterGeneralShop");
            writer.open();
            writer.write(shop);
            writer.close();

            JsonReader reader = new JsonReader("./data/testJsonWriterGeneralShop");
            Shop loadedShop = reader.readShop();
            assertEquals(2, loadedShop.getCardsForSale().size());
        } catch (IOException e) {
            fail("Unexpected exception thrown");
        }
    }
}
