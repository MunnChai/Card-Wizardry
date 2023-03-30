package model;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static model.Card.CardType.*;
import static model.EnemyPlayer.*;
import static model.UserPlayer.*;
import static org.junit.jupiter.api.Assertions.*;


class CardTest {

    Card attackCard1;
    Card healCard1;
    Card shieldCard1;
    Card attackCard2;
    Card healCard2;
    Card shieldCard2;

    User user;
    UserPlayer userPlayer;
    EnemyPlayer enemy;

    @BeforeEach
    public void setup() {
        attackCard1 = new Card(ATTACK, 2, 1, 1, 0);
        healCard1 = new Card(HEAL, 1, 1, 1, 1);
        shieldCard1 = new Card(SHIELD, 3, 1, 1, 2);

        attackCard2 = new Card(ATTACK, 6, 1, 1, 3);
        healCard2 = new Card(HEAL, 2, 1, 1, 4);
        shieldCard2 = new Card(SHIELD, 3, 1, 1, 5);

        user = new User("Name");
        userPlayer = new UserPlayer(user.getSelectedDeck());
        enemy = new EnemyPlayer();
    }

    @Test
    public void testCardConstructor() {
        assertEquals(ATTACK, attackCard1.getType());
        assertEquals(2, attackCard1.getValue());
        assertEquals(1, attackCard1.getEnergyCost());
        assertEquals(1, attackCard1.getCoinCost());
        assertEquals(0, attackCard1.getId());

        assertEquals(HEAL, healCard1.getType());
        assertEquals(1, healCard1.getValue());
        assertEquals(1, healCard1.getEnergyCost());
        assertEquals(1, healCard1.getCoinCost());
        assertEquals(1, healCard1.getId());

        assertEquals(SHIELD, shieldCard1.getType());
        assertEquals(3, shieldCard1.getValue());
        assertEquals(1, shieldCard1.getEnergyCost());
        assertEquals(1, shieldCard1.getCoinCost());
        assertEquals(2, shieldCard1.getId());
    }

    @Test
    public void testRandomAttackAdjectives() {
        List<String> testList = new ArrayList<>();
        for (int i = 0; i < 800; i++) {
            testList.add(attackCard1.pickRandomAdj(ATTACK));
        }

        List<String> attackAdjectives = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            attackAdjectives.add(Card.Adjectives.values()[i].name());
        }

        int inAdjectivesCount = 0;
        for (String s : testList) {
            if (attackAdjectives.contains(s)) {
                inAdjectivesCount++;
            }
        }
        assertEquals(800, inAdjectivesCount);
    }

    @Test
    public void testRandomHealAdjectives() {
        List<String> testList = new ArrayList<>();
        for (int i = 0; i < 800; i++) {
            testList.add(attackCard1.pickRandomAdj(HEAL));
        }

        List<String> healAdjectives = new ArrayList<>();
        for (int i = 8; i < 16; i++) {
            healAdjectives.add(Card.Adjectives.values()[i].name());
        }

        int inAdjectivesCount = 0;
        for (String s : testList) {
            if (healAdjectives.contains(s)) {
                inAdjectivesCount++;
            }
        }
        assertEquals(800, inAdjectivesCount);
    }

    @Test
    public void testRandomShieldAdjectives() {
        List<String> testList = new ArrayList<>();
        for (int i = 0; i < 800; i++) {
            testList.add(attackCard1.pickRandomAdj(SHIELD));
        }

        List<String> shieldAdjectives = new ArrayList<>();
        for (int i = 16; i < 24; i++) {
            shieldAdjectives.add(Card.Adjectives.values()[i].name());
        }

        int inAdjectivesCount = 0;
        for (String s : testList) {
            if (shieldAdjectives.contains(s)) {
                inAdjectivesCount++;
            }
        }
        assertEquals(800, inAdjectivesCount);
    }

    @Test
    public void testRandomAttackNoun() {
        List<String> testList = new ArrayList<>();
        for (int i = 0; i < 800; i++) {
            testList.add(attackCard1.pickRandomNoun(ATTACK));
        }

        List<String> attackNouns = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            attackNouns.add(Card.Nouns.values()[i].name());
        }

        int inNounsCount = 0;
        for (String s : testList) {
            if (attackNouns.contains(s)) {
                inNounsCount++;
            }
        }
        assertEquals(800, inNounsCount);
    }

    @Test
    public void testRandomHealNouns() {
        List<String> testList = new ArrayList<>();
        for (int i = 0; i < 800; i++) {
            testList.add(attackCard1.pickRandomNoun(HEAL));
        }

        List<String> healNouns = new ArrayList<>();
        for (int i = 8; i < 16; i++) {
            healNouns.add(Card.Nouns.values()[i].name());
        }

        int inNounsCount = 0;
        for (String s : testList) {
            if (healNouns.contains(s)) {
                inNounsCount++;
            }
        }
        assertEquals(800, inNounsCount);
    }

    @Test
    public void testRandomShieldNouns() {
        List<String> testList = new ArrayList<>();
        for (int i = 0; i < 800; i++) {
            testList.add(attackCard1.pickRandomNoun(SHIELD));
        }

        List<String> shieldNouns = new ArrayList<>();
        for (int i = 16; i < 24; i++) {
            shieldNouns.add(Card.Nouns.values()[i].name());
        }

        int inNounsCount = 0;
        for (String s : testList) {
            if (shieldNouns.contains(s)) {
                inNounsCount++;
            }
        }
        assertEquals(800, inNounsCount);
    }

    @Test
    public void testAttackCardEffect() {
        attackCard1.cardEffect(enemy);
        assertEquals(ENEMY_MAX_HEALTH - 2, enemy.getHealth());
        attackCard2.cardEffect(enemy);
        assertEquals(ENEMY_MAX_HEALTH - 8, enemy.getHealth());
    }

    @Test
    public void testHealCardEffect() {
        attackCard1.cardEffect(enemy);
        healCard1.cardEffect(enemy);
        assertEquals(ENEMY_MAX_HEALTH - 1, enemy.getHealth());
    }

    @Test
    public void testHealCardMaxHealthEffect() {
        healCard1.cardEffect(enemy);
        healCard1.cardEffect(enemy);
        healCard1.cardEffect(enemy);
        assertEquals(ENEMY_MAX_HEALTH, enemy.getHealth());
    }

    @Test
    public void testShieldCardEffect() {
        shieldCard2.cardEffect(userPlayer);
        assertEquals(3, userPlayer.getShield());
        attackCard2.cardEffect(userPlayer);
        assertEquals(USER_MAX_HEALTH - 3, userPlayer.getHealth());
    }

    @Test
    public void testMoreShieldCardEffect() {
        shieldCard2.cardEffect(userPlayer);
        attackCard1.cardEffect(userPlayer);
        assertEquals(USER_MAX_HEALTH, userPlayer.getHealth());
        assertEquals(1, userPlayer.getShield());
    }

    @Test
    public void testIsCardTypeTrue() {
        Card.CardType attackType = ATTACK;
        assertTrue(attackType.isCardType(ATTACK));
    }

    @Test
    public void testIsCardTypeFalse() {
        Card.CardType attackType = SHIELD;
        assertFalse(attackType.isCardType(HEAL));
    }

    @Test
    public void testSetName() {
        Card card = new Card(ATTACK, 1, 1, 1, 6);
        String initialName = card.getName();
        card.setName("New name");
        assertEquals("New name", card.getName());
        assertNotSame(card.getName(), initialName);
    }

    @Test
    public void testCardToJson() {
        attackCard1.setName("Attack Card");
        JSONObject jsonObject = attackCard1.toJson();

        assertEquals("Attack Card", jsonObject.get("name"));
        assertEquals(ATTACK, jsonObject.get("type"));
        assertEquals(2, jsonObject.get("value"));
        assertEquals(1, jsonObject.get("coinCost"));
        assertEquals(1, jsonObject.get("energyCost"));
    }

    @Test
    public void testEqualsDifferentClass() {
        assertFalse(attackCard1.equals(new Deck("New deck")));
    }

    @Test
    public void testDifferentValue() {
        Card card1 = new Card(ATTACK, 2, 1, 1, 0);
        Card card2 = new Card(ATTACK, 3, 1, 1, 0);

        card1.setName("Bob");
        card2.setName("Bob");

        assertNotEquals(card1, card2);
    }

    @Test
    public void testDifferentEnergyCost() {
        Card card1 = new Card(ATTACK, 2, 1, 1, 0);
        Card card2 = new Card(ATTACK, 2, 2, 1, 0);

        card1.setName("Bob");
        card2.setName("Bob");

        assertNotEquals(card1, card2);
    }

    @Test
    public void testDifferentCoinCost() {
        Card card1 = new Card(ATTACK, 2, 1, 1, 0);
        Card card2 = new Card(ATTACK, 2, 1, 2, 0);

        card1.setName("Bob");
        card2.setName("Bob");

        assertNotEquals(card1, card2);
    }

    @Test
    public void testEqualsDifferentId() {
        Card card1 = new Card(ATTACK, 2, 1, 1, 0);
        Card card2 = new Card(ATTACK, 2, 1, 1, 3);

        card1.setName("Bob");
        card2.setName("Bob");

        assertNotEquals(card1, card2);
    }

    @Test
    public void testDifferentCardType() {
        Card card1 = new Card(ATTACK, 2, 1, 1, 0);
        Card card2 = new Card(SHIELD, 2, 1, 1, 0);

        card1.setName("Bob");
        card2.setName("Bob");

        assertNotEquals(card1, card2);
    }

    @Test
    public void testSetId() {
        Card card1 = new Card(ATTACK, 2, 1, 1, 0);
        Card card2 = new Card(ATTACK, 2, 1, 1, 2);

        card1.setName("Bob");
        card2.setName("Bob");

        card2.setId(0);

        assertEquals(card1, card2);
    }

    @Test
    public void testDifferentCardName() {
        Card card1 = new Card(ATTACK, 2, 1, 1, 0);
        Card card2 = new Card(ATTACK, 2, 1, 1, 0);

        card1.setName("Bob");
        card2.setName("Joe");

        assertNotEquals(card1, card2);
    }

    @Test
    public void testHashCode() {
        assertNotEquals(attackCard1.hashCode(), attackCard2.hashCode());
    }
}