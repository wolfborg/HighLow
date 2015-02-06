package HighLow;

import java.awt.image.BufferedImage;

public class Card
{
	private Rank rank;
	private Suit suit;
	
	public Card(Rank rank, Suit suit)
	{
		this.rank = rank;
		this.suit = suit;
	}
	
	public Suit getSuit(){ return suit; }
	public Rank getRank(){ return rank; }
	
	public void setRank(Rank newRank){ this.rank = newRank; }
	public void setSuit(Suit newSuit){ this.suit = newSuit; }
	
	public String toString()
	{
		return rank+" of "+suit;
	}
	
	public BufferedImage getCardImage(BufferedImage[][] sprites)
	{
		int row;
		Suit suit = getSuit();
		if(suit.equals(Suit.CLUBS)){
			row = 1;
		}else if(suit.equals(Suit.SPADES)){
			row = 2;
		}else if(suit.equals(Suit.DIAMONDS)){
			row = 3;
		}else{
			row = 4;
		}
		
		int col;
		if(getRank().getRankValue()==14){
			col = 1; 
		}else{
			col = getRank().getRankValue();
		}
		
		return sprites[row-1][col];
	}
}
