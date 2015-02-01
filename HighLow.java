package HighLow;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class HighLow extends JFrame implements ActionListener
{
	private Deck deck;
	private StackInterface<Card> stackDeck;
	private StackInterface<Card> stackGame;
	private String guess, answer;
	private Card currentCard, nextCard;
	private int score;
	private JLabel current = new JLabel();
	private JLabel next = new JLabel();
	private JLabel scoreLabel = new JLabel();
	private JLabel gameText = new JLabel();
	private JButton high = new JButton("High");
	private JButton low = new JButton("Low");
	private JButton playagain = new JButton("Play Again");
	private JPanel controls = new JPanel();
	private Container pane = getContentPane();
	private String defaultText = "<html><body><center>"
			+ "Guess whether the next card will be high or low.<br>"
			+ "Make it through the entire deck and you win!<br>"
			+ "All tie results are correct answers and Aces are the highest value.<br>"
			+ "</center></body></html>";
	
	public static void main(String[] args)
	{
		new HighLow();
	}
	
	public HighLow()
	{ 
		deck = new Deck();
		stackGame = new ArrayStack<Card>(52);
		high.addActionListener(this);
		low.addActionListener(this);
		playagain.addActionListener(this);

		game();
		gui();
	}
	
	public void game()
	{
		stackDeck = deck.shuffle();
		nextRound();
	}
	
	public String checkNext()
	{
		if(nextCard.getRank().getRankValue()<currentCard.getRank().getRankValue()){
			return "low";
		}else if(nextCard.getRank().getRankValue()>currentCard.getRank().getRankValue()){
			return "high";
		}else{
			return "tie";
		}
	}
	
	public boolean checkCorrect()
	{
		if(answer.equals("tie")){
			gameText.setText("Tied");
			score++;
			return true;
		}else{
			if(guess.equals(answer)){
				gameText.setText("Correct");
				score++;
				return true;
			}else{
				gameText.setText("<html><body><center>Incorrect<br>");
				return false;
			}
		}
	}
	
	public void nextRound()
	{
		stackGame.push(stackDeck.peek());
		stackDeck.pop();
		
		currentCard = stackGame.peek();
		nextCard = stackDeck.peek();
		
		current.setText(currentCard.toString());
		next.setText("next");
		scoreLabel.setText("Score: "+score);
		
		answer = checkNext();
	}
	
	public boolean isEnd()
	{
		if(stackGame.size()==52){
			victory();
			return true;
		}
		
		return false;
	}
	
	public void victory()
	{
		gameText.setText("You Win!");
	}
	
	public void gameover()
	{
		next.setText(stackDeck.peek().toString());
		gameText.setText(gameText.getText()+"Game Over</center></body></html>");
		
		high.removeActionListener(this);
		low.removeActionListener(this);
		controls.remove(high);
		controls.remove(low);
		controls.setLayout(new GridLayout(1,1));
		controls.add(playagain);
		
		stackGame.clear();
		repaint();
	}
	
	public void gui()
	{
		setTitle("HighLow");
		setLocation(200,200);
		setSize(500,500);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridLayout grid = new GridLayout(3,1,0,0);
		setLayout(grid);
		setPreferredSize(new Dimension(500,400));
		
		
		final JPanel text = new JPanel();
		final JPanel score = new JPanel();
		final JPanel rules = new JPanel();
		text.setLayout(new GridLayout(2,1));
		
		//score.setPreferredSize(new Dimension(500,50));
		
		score.add(scoreLabel);
		gameText.setText(defaultText);
		rules.add(gameText);
		
		text.add(score);
		text.add(rules);
		
		final JPanel game = new JPanel();
		final JPanel current = new JPanel();
		final JPanel next = new JPanel();
		game.setLayout(new GridLayout(1,2));
		
		current.add(this.current);
		current.setBackground(Color.WHITE);
		next.add(this.next);
		game.add(current);
		game.add(next);
		game.setPreferredSize(new Dimension(500,50));
		
		controls.setLayout(new GridLayout(1,2));
		controls.setPreferredSize(new Dimension(500,50));
		
		controls.add(high);
		controls.add(low);
		
		pane.add(text,BorderLayout.NORTH);
		pane.add(game,BorderLayout.CENTER);
		pane.add(controls,BorderLayout.SOUTH);
		
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("High")){
			guess = "high";
		}else if(e.getActionCommand().equals("Low")){
			guess = "low";
		}else if(e.getActionCommand().equals("Play Again")){
			high.addActionListener(this);
			low.addActionListener(this);
			controls.remove(playagain);
			controls.setLayout(new GridLayout(1,2));
			controls.add(high);
			controls.add(low);
			score = 0;
			gameText.setText(defaultText);
			repaint();
			game();
		}
		
		if(checkCorrect()){
			if(!isEnd()){
				nextRound();
			}
		}else{
			gameover();
		}
	}
}
