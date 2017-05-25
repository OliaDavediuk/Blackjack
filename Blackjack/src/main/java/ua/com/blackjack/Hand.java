package ua.com.blackjack;

import java.util.ArrayList;
import java.util.List;

import ua.com.blackjack.card.*;

public class Hand {

    private List<Card> cards;
    private final int BLACKJACK = 21;

    public Hand() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public int countPoints() {
        int result = 0;
        int aceCount = 0;

        for (Card card : cards) {
            result += card.getScore();
            if (card.isAce()) {
                aceCount++;
            }
        }

        while (aceCount > 0) {
            if (result > BLACKJACK) {
                result = result - 10;
                aceCount--;
            } else {
                break;
            }
        }
        return result;
    }

    public boolean isBusted() {
        return this.countPoints() > BLACKJACK;
    }

    public boolean isBlackjack() {
        return this.countPoints() == BLACKJACK;
    }

    public Card getCard(int index) {
        return cards.get(index);
    }

    public int getNumberOfCards() {
        return cards.size();
    }

    @Override
    public String toString() {
        return cards.toString() + ". Total points: " + countPoints();
    }

}
