package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static model.Card.CardType.*;
import static model.Shop.SHOP_CARD_STOCK;
import static org.junit.jupiter.api.Assertions.*;

public class ShopTest {

    User user;

    Shop shop1;
    Shop shop2;

    Card sampleCard1;
    Card sampleCard2;
    Card sampleCard3;

    @BeforeEach
    public void setup() {
        user = new User();
        shop1 = new Shop(user);
        shop2 = new Shop(user);

        sampleCard1 = new Card(ATTACK, 3, 2, 1);
        sampleCard2 = new Card(HEAL, 1, 1, 1);
        sampleCard3 = new Card(SHIELD, 6, 4, 3);
    }

    @Test
    public void testShopConstructor() {
        assertEquals(SHOP_CARD_STOCK, shop1.getCardsForSale().size());
        assertEquals(SHOP_CARD_STOCK, shop2.getCardsForSale().size());
    }

    // MODIFIES: Shop, Player
    // EFFECTS: Buy a card from cards for sale, decided using the index, coin value is deducted from player coins
    //          purchased card is removed from not owned cards, and added to owned cards
    public void buyCard(int index) {

    }

    @Test
    public void testBuyCard() {

        user.setCoins(10);

        List<Card> shop1Cards = new ArrayList<>();
        shop1Cards.add(sampleCard1);
        shop1Cards.add(sampleCard2);
        shop1Cards.add(sampleCard3);
        shop1.setCardsForSale(shop1Cards);

        List<Card> expectedOwnedCards = new ArrayList<>(user.getOwnedCards());
        expectedOwnedCards.add(sampleCard1);
        List<Card> expectedNotOwnedCards = new ArrayList<>(user.getNotOwnedCards());
        expectedNotOwnedCards.remove(sampleCard1);

        shop1.buyCard(0);
        assertEquals(2, shop1.getCardsForSale().size());

        List<Card> expected1 = new ArrayList<>();
        expected1.add(sampleCard2);
        expected1.add(sampleCard3);
        assertEquals(expected1, shop1.getCardsForSale());

        assertEquals(expectedOwnedCards, user.getOwnedCards());
        assertEquals(expectedNotOwnedCards, user.getNotOwnedCards());

        int cost = sampleCard1.getCoinCost();
        assertEquals(10 - cost, user.getCoins());
    }

    @Test
    public void testSellCard() {
        user.setCoins(10);
        List<Card> expectedOwnedCards = new ArrayList<>(user.getOwnedCards());
        expectedOwnedCards.remove(sampleCard1);
        List<Card> expectedNotOwnedCards = new ArrayList<>(user.getNotOwnedCards());
        expectedNotOwnedCards.add(sampleCard1);

        shop1.sellCard(sampleCard1);
        assertEquals(expectedOwnedCards, user.getOwnedCards());
        assertEquals(expectedNotOwnedCards, user.getNotOwnedCards());

        int cost = sampleCard1.getCoinCost();
        assertEquals(10 + cost, user.getCoins());
    }
}