package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

// A randomized shop with a list of cards that can be purchased. Holds given user, to access user's owned and not
// owned cards. Has methods for buying and selling cards from the shop.
public class Shop implements Writable {

    public static final int SHOP_CARD_STOCK = 4;

    private List<Card> cardsForSale;
    private User user;

    // EFFECTS: Constructs a shop with cards from the User's not owned cards
    public Shop(User givenUser) {
        cardsForSale = new ArrayList<>();
        user = givenUser;
        int i = 0;
        while (i < SHOP_CARD_STOCK) {
            int randomIndex = (int)(Math.random() * user.getNotOwnedCards().size());
            Card randomCard = user.getNotOwnedCards().get(randomIndex);
            if (!cardsForSale.contains(randomCard)) {
                cardsForSale.add(randomCard);
                i++;
            }
        }
    }

    // MODIFIES: this, Player
    // EFFECTS: Buy a card from cards for sale, decided using the index, coin value is deducted from player coins
    //          purchased card is removed from not owned cards, and added to owned cards
    public void buyCard(int index) {
        Card purchasedCard = cardsForSale.get(index);
        cardsForSale.remove(purchasedCard);
        user.getOwnedCards().add(purchasedCard);
        user.getNotOwnedCards().remove(purchasedCard);
        user.setCoins(user.getCoins() - purchasedCard.getCoinCost());
    }

    // MODIFIES: Player
    // EFFECTS: card is removed from owned cards, and added to not owned cards, coin value is added to player coins
    public void sellCard(Card card) {
        user.getOwnedCards().remove(card);
        user.getNotOwnedCards().add(card);
        user.setCoins(user.getCoins() + card.getCoinCost());
    }

    // EFFECTS: returns shop as a JSONObject
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("cardsForSale", cardsToJson(cardsForSale));
        return json;
    }

    // EFFECTS: returns list of cards as JSON Array
    public JSONArray cardsToJson(List<Card> cards) {
        JSONArray jsonArray = new JSONArray();
        for (Card card : cards) {
            jsonArray.put(card.toJson());
        }
        return jsonArray;
    }



    // Getters
    public List<Card> getCardsForSale() {
        return cardsForSale;
    }

    // Setters
    public void setCardsForSale(List<Card> cardsForSale) {
        this.cardsForSale = cardsForSale;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
