package HighLow;

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
}
