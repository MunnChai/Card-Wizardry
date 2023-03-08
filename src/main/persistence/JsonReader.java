package persistence;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import model.Card;
import model.Deck;
import model.User;
import org.json.*;

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads user from file and returns it;
    // throws IOException if an error occurs reading data from file
    public User read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseUser(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses user from JSON object and returns it
    private User parseUser(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int coins = jsonObject.getInt("coins");
        Deck selectedDeck = parseDeck(jsonObject.getJSONObject("selectedDeck"));
        User user = new User(name);
        user.setCoins(coins);
        user.setSelectedDeck(selectedDeck);
        addDecks(user, jsonObject);
        addOwnedCards(user, jsonObject);
        addNotOwnedCards(user, jsonObject);
        return user;
    }

    // MODIFIES: user
    // EFFECTS: parses decks from JSON object and adds them to user
    private void addDecks(User user, JSONObject jsonObject) {
        JSONArray decks = jsonObject.getJSONArray("decks");
        List<Deck> decksToAdd = new ArrayList<>();
        for (Object deck : decks) {
            JSONObject nextDeck = (JSONObject) deck;
            decksToAdd.add(parseDeck(nextDeck));
        }
        user.setDecks(decksToAdd);
    }

    // MODIFIES: user
    // EFFECTS: parses cards from JSON Object and adds them to user's owned cards
    private void addOwnedCards(User user, JSONObject jsonObject) {
        JSONArray ownedCards = jsonObject.getJSONArray("ownedCards");
        List<Card> toBeAdded = new ArrayList<>();
        for (Object card : ownedCards) {
            JSONObject nextCard = (JSONObject) card;
            toBeAdded.add(parseCard(nextCard));
        }
        user.setOwnedCards(toBeAdded);
    }

    // MODIFIES: user
    // EFFECTS: parses cards from JSON Object and adds them to user's not owned cards
    private void addNotOwnedCards(User user, JSONObject jsonObject) {
        JSONArray notOwnedCards = jsonObject.getJSONArray("notOwnedCards");
        List<Card> toBeAdded = new ArrayList<>();
        for (Object card : notOwnedCards) {
            JSONObject nextCard = (JSONObject) card;
            toBeAdded.add(parseCard(nextCard));
        }
        user.setNotOwnedCards(toBeAdded);
    }

    // EFFECTS: Parses card from JSON Object and returns it
    private Card parseCard(JSONObject card) {
        String cardName = card.getString("name");
        Card.CardType cardType = Card.CardType.valueOf(card.getString("type"));
        int cardValue = card.getInt("value");
        int cardCoinCost = card.getInt("coinCost");
        int cardEnergyCost = card.getInt("energyCost");
        Card c = new Card(cardType, cardValue, cardEnergyCost, cardCoinCost);
        c.setName(cardName);
        return c;
    }

    // EFFECTS: Parses deck from JSON Object and returns it
    private Deck parseDeck(JSONObject jsonObject) {
        String deckName = jsonObject.getString("name");
        Deck deck = new Deck(deckName);
        JSONArray cards = jsonObject.getJSONArray("cardsInDeck");
        for (Object card : cards) {
            JSONObject nextCard = (JSONObject) card;
            deck.addCard(parseCard(nextCard));
        }
        return deck;
    }
}
