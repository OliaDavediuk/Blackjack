package ua.com.blackjack.card;

public class Card {

    private CardValue cardValue;
    private CardSuit cardSuit;

    public Card(int cardId, int suitId) {
        this.cardValue = CardValue.getCardValue(cardId) ;
        this.cardSuit = CardSuit.getCardSuit(suitId);
    }

    public int getScore(){
        return cardValue.getScore();
    }

    public boolean isAce(){
        return cardValue == CardValue.ACE;
    }

    @Override
    public String toString() {
        return cardValue.name() + " " + cardSuit.name();
    }
}
