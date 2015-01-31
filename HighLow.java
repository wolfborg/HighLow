package HighLow;

import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.KeyStroke;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class HighLow extends JFrame implements ActionListener
{
	private Scanner keyboard = new Scanner(System.in);
	private Deck deck;
	private StackInterface<Card> stackDeck;
	private StackInterface<Card> stackGame;
	
	public static void main(String[] args)
	{
		new HighLow();
	}
	
	public HighLow()
	{ 
		deck = new Deck();
		stackGame = new ArrayStack<Card>(52);
		gui();
		//game();
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
			
			
			if(stackDeck.peek().getRank().getRankValue()<stackGame.peek().getRank().getRankValue()){
				answer = "low";
			}else if(stackDeck.peek().getRank().getRankValue()>stackGame.peek().getRank().getRankValue()){
				answer = "high";
			}else{
				answer = "tie";
			}
			
			while(!guess.equals("high")&&!guess.equals("low")){
				System.out.println("Invalid Input");
				System.out.println();
				System.out.print("High or Low: "); guess = keyboard.next();
			}
			
			System.out.println();
			
			if(answer.equals("tie")){
				System.out.println("Tied");
			}else if(guess.equals("high")||guess.equals("low")){
				if(guess.equals(answer)){
					System.out.println("Correct!");
				}else{
					System.out.println("Incorrect!");
					gameover();
					break;
				}
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
		System.out.println("Next card was "+stackDeck.peek());
		
		System.out.println();
		System.out.println("Score: "+stackGame.size());
		System.out.println("Game Over");
		System.out.println();
		System.out.print("Play Again? ");
		String answer = keyboard.next();
		
		while(!answer.equals("yes")&&!answer.equals("no")){
			System.out.println("Invalid Input");
			System.out.println();
			System.out.print("Play Again? ");
			answer = keyboard.next();
		}
		
		if(answer.equals("yes")){
			stackGame.clear();
			System.out.println();
			game();
		}else{
			System.exit(0);
		}
	}
	
	public void gui()
	{
		GridLayout grid = new GridLayout(0,2);
		final Container pane = getContentPane();
		
		setTitle("HighLow");
		setLocation(200,200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final JPanel game = new JPanel();
		game.setLayout(grid);
		JPanel controls = new JPanel();
		controls.setLayout(new GridLayout(2,3));
		
		game.setPreferredSize(new Dimension(100,100));
		
		
		
		
		game.add(new JButton("High"));
		game.add(new JButton("Low"));
		
		pane.add(game,BorderLayout.NORTH);
		
		
		game.getInputMap().put(KeyStroke.getKeyStroke("ESC"),null);
		
		pack();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		
	}
}
