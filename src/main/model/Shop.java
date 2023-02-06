package model;

import java.util.ArrayList;
import java.util.List;

public class Shop {

    public static final int SHOP_CARD_STOCK = 4;

    private List<Card> cardsForSale;
    private User user;

    // EFFECTS: Constructs a shop with cards from the User's not owned cards
    public Shop(User user) {
        cardsForSale = new ArrayList<>();
        this.user = user;
        for (int i = 0; i < SHOP_CARD_STOCK; i++) {
            int randomIndex = (int)(Math.random() * user.getNotOwnedCards().size());
            Card randomCard = user.getNotOwnedCards().get(randomIndex);
            cardsForSale.add(randomCard);
        }
    }

    // MODIFIES: Shop, Player
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



    // Getters
    public List<Card> getCardsForSale() {
        return cardsForSale;
    }

    // Setters

    public void setCardsForSale(List<Card> cardsForSale) {
        this.cardsForSale = cardsForSale;
    }
}
