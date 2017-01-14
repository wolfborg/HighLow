package HighLow;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import java.io.File;
import java.io.IOException;

/*****************************************************************************
 * HighLow
 * 
 * A card game in which the goal is to guess whether the next card will be 
 * higher or lower than the current card. If the user reaches the end of the 
 * deck, the game is won. All ties are considered round wins and Aces are the
 * highest value.
 * 
 * @version 1.0
 * @author Derek Chaplin
 *****************************************************************************/
public class HighLow extends JFrame implements ActionListener
{
	private static final long serialVersionUID = 6388741306888480015L;
	private BufferedImage[][] sprites;
	private Deck deck = new Deck();
	private StackInterface<Card> stackDeck;
	private StackInterface<Card> stackGame = new ArrayStack<Card>(52);
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
	private String defaultText = "<html><body><center>"
			+ "Guess whether the next card will be high or low. Make it through the entire deck and you win!<br>"
			+ "All tie results are correct answers and Aces are the highest value.<br>"
			+ "</center></body></html>";
	
	public static void main(String[] args) throws IOException
	{
		//Deck image: 4200 width, 1600 height 
		//Each card: 300 width, 400 height
		String fileName = "cardsoriginal.png";
		int width = 300;
		int height = 400;
		
		new HighLow(fileName,width,height);
	}
	
	/*****************************************************************************
	 * Creates HighLow game in which the goal is to guess whether the next card
	 * is high or low until they either guess incorrectly or reach the end of
	 * the card deck, which has the standard 52 cards in it.
	 * @param fileName	Name of the deck image file.
	 * @param width		Desired width of each card.
	 * @param height	Desired height of each card.
	 *****************************************************************************/
	public HighLow(String fileName, int width, int height) throws IOException
	{ 
		sprites = generateDeckImages(ImageIO.read(new File(fileName)),width,height);
		
		high.addActionListener(this);
		low.addActionListener(this);
		playagain.addActionListener(this);

		game();
		gui();
	}
	
	
	/*****************************************************************************
	 * Splits a card deck BufferedImage sprite sheet into equally sized subimages.
	 * @param bigImg	BufferedImage of deck to split.
	 * @param width		Desired width of each card.
	 * @param height	Desired height of each card.
	 * @return 			2D BufferedImage array
	 ******************************************************************************/
	public BufferedImage[][] generateDeckImages(BufferedImage bigImg, int width, int height)
	{
		int cols = bigImg.getWidth() / width;
		int rows = bigImg.getHeight() / height;
		
		BufferedImage[][] sprites = new BufferedImage[rows+1][cols+1];
		
		for(int i=0;i<rows;i++)
		{
			for(int j=0;j<cols;j++)
			{
				sprites[i][j] = bigImg.getSubimage(j*width, i*height, width, height);
			}
		}
		
		return sprites;
	}
	
	/*****************************************************************************
	 * Starts a new game.
	 *****************************************************************************/
	public void game()
	{
		score = 0;
		stackDeck = deck.shuffle();
		gameRound();
	}
	
	/*****************************************************************************
	 * Shows current card and asks for high or low guess.
	 *****************************************************************************/
	public void gameRound()
	{
		stackGame.push(stackDeck.peek());
		stackDeck.pop();
		
		scoreLabel.setText("Score: "+score);
		currentCard = stackGame.peek();
		nextCard = stackDeck.peek();
		
		int currentCardValue = currentCard.getRank().getRankValue();
		int nextCardValue = nextCard.getRank().getRankValue();
		
		if(nextCardValue<currentCardValue)
		{
			answer = "low";
		}
		else if(nextCardValue>currentCardValue)
		{
			answer = "high";
		}
		else
		{
			answer = "tie";
		}
	}
	
	/*****************************************************************************
	 * @return	Boolean for if guess is equal to the answer.
	 *****************************************************************************/
	public boolean isCorrect()
	{
		gameText.setText(stackDeck.peek().toString());
		if(answer.equals("tie"))
		{
			gameText.setText(gameText.getText()+": Tied");
			score++;
			return true;
		}
		else
		{
			if(guess.equals(answer))
			{
				gameText.setText(gameText.getText()+": Correct");
				score++;
				
				if(score==51)
				{
					gameText.setText("<html><body><center>You Win!<br>");
					next.setPreferredSize(next.getPreferredSize());
					next.setIcon(null);
					next.setHorizontalAlignment(JLabel.CENTER);
					next.setText("Deck Empty");
					gameover();
				}
				
				return true;
			}
			else
			{
				gameText.setText("<html><body><center>"+gameText.getText()+": Incorrect <br>");
				return false;
			}
		}
		
		
	}
	
	/*****************************************************************************
	 * Declares the game is over and enables the playagain button.
	 *****************************************************************************/
	public void gameover()
	{
		gameText.setText(gameText.getText()+"Game Over</center></body></html>");
		
		high.setEnabled(false);
		low.setEnabled(false);
		controls.remove(high);
		controls.remove(low);
		controls.setLayout(new GridBagLayout());
		playagain.setEnabled(true);
		controls.add(playagain);
		
		stackGame.clear();
		repaint();
	}
	
	/*****************************************************************************
	 * Creates the GUI for the HighLow game.
	 *****************************************************************************/
	public void gui()
	{
		Container pane = getContentPane();
		setTitle("HighLow");
		setLocation(200,200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//gridbag
		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(grid);
		c.weightx = 0.5;

		//score
		scoreLabel.setHorizontalAlignment(JLabel.CENTER);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 20;
		c.gridx = 0;
		c.gridy = 0;
		pane.add(scoreLabel,c);
		
		//gameText
		gameText.setText(defaultText);
		gameText.setHorizontalAlignment(JLabel.CENTER);
		gameText.setPreferredSize(gameText.getPreferredSize());
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		pane.add(gameText,c);
		
		//cards
		final JPanel game = new JPanel();
		current.setIcon(new ImageIcon(currentCard.getCardImage(sprites)));
		next.setIcon((new ImageIcon(sprites[1][0])));
		game.add(current);
		game.add(next);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		pane.add(game,c);
		
		//controls
		controls.setLayout(new GridBagLayout());
		controls.add(high);
		controls.add(low);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 0;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 3;
		pane.add(controls,c);
		
		pack();
		setVisible(true);
	}
	
	/*****************************************************************************
	 * Performs actions based on events sent from the GUI.
	 * @param e	Action caused by the GUI.
	 *****************************************************************************/
	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("High")||e.getActionCommand().equals("Low")){
			guess = e.getActionCommand().toLowerCase();
			
			if(isCorrect()){
				gameRound();
			}else{
				next.setIcon(new ImageIcon(stackDeck.peek().getCardImage(sprites)));
				gameover();
			}
		}else if(e.getActionCommand().equals("Play Again")){
			next.setIcon((new ImageIcon(sprites[1][0])));
			playagain.setEnabled(false);
			controls.remove(playagain);
			controls.setLayout(new GridBagLayout());
			high.setEnabled(true);
			low.setEnabled(true);
			controls.add(high);
			controls.add(low);
			score = 0;
			gameText.setText(defaultText);
			repaint();
			game();
		}

		((ImageIcon)current.getIcon()).setImage(currentCard.getCardImage(sprites));
		repaint();
	}
}
