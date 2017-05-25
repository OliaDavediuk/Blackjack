package ua.com.blackjack;

import java.util.Random;
import ua.com.blackjack.card.*;

public class Deck {
    private Card[] cards;
    private final int DECK_CAPACITY = 52;
    int currentCard = 0;

    public Deck() {
        cards = new Card[DECK_CAPACITY];
        int currentCard = 0;
        for (int suitId = 0; suitId < CardSuit.values().length; suitId++) {
            for (int cardId = 0; cardId < CardValue.values().length; cardId++) {
                cards[currentCard++] = new Card(cardId, suitId);
            }
        }
    }

    public void shuffle(){
        Random random = new Random();
        int temp;
        for(int i=0; i<DECK_CAPACITY; i++){
            temp = random.nextInt(DECK_CAPACITY);
            swap(i, temp);
        }
        currentCard = 0;
    }

    private void swap(int i, int temp){
        Card card = cards[i];
        cards[i] = cards[temp];
        cards[temp]=card;
    }

    public Card getCard(){
        return cards[currentCard++];
    }
}
