package HighLow;

import java.util.ArrayList;
import java.util.Collections;

public class Deck
{
	private ArrayList<Card> deck;
	private StackInterface<Card> stackDeck = new ArrayStack<Card>(52);
	
	public Deck()
	{
		deck = new ArrayList<Card>();
		for(int i=0;i<13;i++){
			Rank rank = Rank.values()[i];
			for(int j=0;j<4;j++){
				Card card = new Card(rank, Suit.values()[j]);
				deck.add(card);
			}
		}
	}
	
	public StackInterface<Card> shuffle()
	{
		Collections.shuffle(deck);
		
		Object[] arrayDeck = deck.toArray();
		stackDeck.clear();
		
		for(int i=0;i<arrayDeck.length;i++){
			stackDeck.push((Card)arrayDeck[i]);
		}
		
		return stackDeck;
	}
}
