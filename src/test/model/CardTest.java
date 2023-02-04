package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static model.Card.CardType.*;
import static model.Enemy.*;
import static model.User.*;
import static org.junit.jupiter.api.Assertions.*;


class CardTest {

    Card attackCard1;
    Card healCard1;
    Card shieldCard1;
    Card attackCard2;
    Card healCard2;
    Card shieldCard2;

    Deck userDeck;
    User user;
    Enemy enemy;

    @BeforeEach
    public void setup() {
        attackCard1 = new Card(ATTACK, 2, 1, 1);
        healCard1 = new Card(HEAL, 1, 1, 1);
        shieldCard1 = new Card(SHIELD, 3, 1, 1);

        attackCard2 = new Card(ATTACK, 6, 1, 1);
        healCard2 = new Card(HEAL, 2, 1, 1);
        shieldCard2 = new Card(SHIELD, 3, 1, 1);

        userDeck = new Deck("Deck");
        user = new User();
        enemy = new Enemy();
        user.startBattle();
    }

    @Test
    public void testCardConstructor() {
        assertEquals(ATTACK, attackCard1.getType());
        assertEquals(2, attackCard1.getValue());
        assertEquals(1, attackCard1.getEnergyCost());
        assertEquals(1, attackCard1.getCoinCost());

        assertEquals(HEAL, healCard1.getType());
        assertEquals(1, healCard1.getValue());
        assertEquals(1, healCard1.getEnergyCost());
        assertEquals(1, healCard1.getCoinCost());

        assertEquals(SHIELD, shieldCard1.getType());
        assertEquals(3, shieldCard1.getValue());
        assertEquals(1, shieldCard1.getEnergyCost());
        assertEquals(1, shieldCard1.getCoinCost());
    }

    @Test
    public void testCardEffect() {
        attackCard1.cardEffect(enemy);
        assertEquals(ENEMY_MAX_HEALTH - 2, enemy.getHealth());
        healCard1.cardEffect(enemy);
        assertEquals(ENEMY_MAX_HEALTH - 1, enemy.getHealth());
        shieldCard1.cardEffect(enemy);
        assertEquals(3, enemy.getShield());

        shieldCard2.cardEffect(user);
        assertEquals(3, user.getShield());
        attackCard2.cardEffect(user);
        assertEquals(USER_MAX_HEALTH - 3, user.getHealth());
        healCard2.cardEffect(user);
        assertEquals(USER_MAX_HEALTH - 1, user.getHealth());
    }
}