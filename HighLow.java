package HighLow;

import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@SuppressWarnings("serial")
public class HighLow extends JFrame implements ActionListener, KeyListener
{
	private Scanner keyboard = new Scanner(System.in);
	private Deck deck;
	private StackInterface<Card> stackDeck;
	private StackInterface<Card> stackGame;
	
	public static void main(String[] args)
	{
		HighLow highlow = new HighLow();
		
		highlow.stackDeck.peek();
	}
	
	public HighLow()
	{ 
		deck = new Deck();
		stackGame = new ArrayStack<Card>(52);
		game();
	}
	
	public void game()
	{
		stackDeck = deck.shuffle();
		
		String guess, answer;
		
		while(!isEnd()){
			stackGame.push(stackDeck.peek());
			stackDeck.pop();
			
			System.out.println("Card"+stackGame.size()+" is the "+stackGame.peek().toString());
			System.out.print("High or Low: "); guess = keyboard.next();
			
			if(stackDeck.peek().getRank().getRankValue()<=stackGame.peek().getRank().getRankValue()){
				answer = "low";
			}else{
				answer = "high";
			}
			
			if(guess.equals(answer)){
				System.out.println("Correct!");
			}else{
				System.out.println("Incorrect!");
				gameover();
				break;
			}
			
			System.out.println();
		}
	}
	
	public boolean isEnd()
	{
		if(stackGame.size()==52){
			victory();
		}
		
		return false;
	}
	
	public void victory()
	{
		System.out.println("You Win!");
	}
	
	public void gameover()
	{
		System.out.println();
		System.out.println("Next card was "+stackDeck.peek());
		
		System.out.println();
		System.out.println("Score: "+stackGame.size());
		System.out.println("Game Over");
		System.out.println("Play Again?");
	}
	
	public void gui()
	{
		Container pane = getContentPane();
		FlowLayout flo = new FlowLayout();
		
		setTitle("HighLow");
		setLocation(200,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel title = new JPanel();
		JPanel game = new JPanel();
		
		JLabel label0 = new JLabel("Level0");
		
		pane.setLayout(flo);
		title.add(label0);
		
		pane.add(title);
		pane.add(game);
		
		addKeyListener(this);
		
		pack();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		
	}

	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode()==KeyEvent.VK_ESCAPE){
			System.exit(0);
		}
	}

	public void keyReleased(KeyEvent e)
	{
		
	}

	public void keyTyped(KeyEvent e)
	{
		
	}
}
