package HighLow;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("serial")
public class HighLow extends JFrame implements ActionListener
{
	private BufferedImage bigImg;
	private BufferedImage[][] sprites;
	private ImageIcon cardIcon;
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
			+ "Guess whether the next card will be high or low. Make it through the entire deck and you win!<br>"
			+ "All tie results are correct answers and Aces are the highest value.<br>"
			+ "</center></body></html>";
	
	public static void main(String[] args) throws IOException
	{
		new HighLow();
	}
	
	public HighLow() throws IOException
	{ 
		bigImg = ImageIO.read(new File("Comp310/HighLow/cards original.png"));
		generateDeckImages(bigImg);
		deck = new Deck();
		stackGame = new ArrayStack<Card>(52);
		high.addActionListener(this);
		low.addActionListener(this);
		playagain.addActionListener(this);

		game();
		gui();
	}
	
	public void generateDeckImages(BufferedImage bigImg)
	{
		int width = 300;
		int height = 400;
		int rows = 4;
		int cols = 14;
		
		//4200 width, 1600 height for big image
		//300 width, 400 height for cards
		sprites = new BufferedImage[rows+1][cols+1];
		
		for(int i=0;i<rows;i++){
			for(int j=0;j<cols;j++){
				sprites[i][j] = bigImg.getSubimage(j*width, i*height, width, height);
			}
		}
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
		gameText.setText(stackDeck.peek().toString());
		if(answer.equals("tie")){
			gameText.setText(gameText.getText()+": Tied");
			score++;
			return true;
		}else{
			if(guess.equals(answer)){
				gameText.setText(gameText.getText()+": Correct");
				score++;
				return true;
			}else{
				gameText.setText("<html><body><center>"+gameText.getText()+": Incorrect <br>");
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
		
		//current.setText(currentCard.toString());
		//next.setText("next");
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
		next.setIcon(new ImageIcon(stackDeck.peek().getCardImage(sprites)));
		//next.setText(stackDeck.peek().toString());
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
	
	public void gui()
	{
		setTitle("HighLow");
		setLocation(200,200);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		GridBagLayout grid = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(grid);
		//setPreferredSize(new Dimension(500,400));
		c.weightx = 0.5;

		scoreLabel.setHorizontalAlignment(JLabel.CENTER);
		
		//score
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipady = 20;
		c.gridx = 0;
		c.gridy = 0;
		pane.add(scoreLabel,c);
		
		gameText.setHorizontalAlignment(JLabel.CENTER);
		gameText.setText(defaultText);
		gameText.setPreferredSize(gameText.getPreferredSize());
		
		//gameText
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 1;
		pane.add(gameText,c);
		
		final JPanel game = new JPanel();
		current.setBackground(Color.WHITE);
		next.setBackground(Color.LIGHT_GRAY);
		game.add(current);
		game.add(next);
		
		cardIcon = new ImageIcon(currentCard.getCardImage(sprites));
		current.setIcon(cardIcon);
		
		next.setIcon((new ImageIcon(sprites[1][0])));
		
		//cards
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		pane.add(game,c);
		
		controls.setLayout(new GridBagLayout());
		
		controls.add(high);
		controls.add(low);
		
		//controls
		c.fill = GridBagConstraints.HORIZONTAL;
		c.ipadx = 0;
		c.ipady = 0;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy = 3;
		pane.add(controls,c);
		
		pack();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e)
	{
		if(e.getActionCommand().equals("High")){
			guess = "high";
			
			if(checkCorrect()){
				if(!isEnd()){
					nextRound();
				}
			}else{
				gameover();
			}
		}else if(e.getActionCommand().equals("Low")){
			guess = "low";
			
			if(checkCorrect()){
				if(!isEnd()){
					nextRound();
				}
			}else{
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
		
		
		//current.add(new JLabel(new ImageIcon(getCardImage())));

		cardIcon.setImage(currentCard.getCardImage(sprites));
		repaint();
		revalidate();
	}
}
