package HighLow;

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
	private StackInterface<String> stack;
	private String[] cards;
	
	private enum Rank{DEUCE,THREE,FOUR,FIVE,SIX,SEVEN,EIGHT,NINE,TEN,JACK,QUEEN,KING,ACE};
	private enum Suit{SPADES,CLUBS,HEARTS,DIAMONDS};
	
	public static void main(String[] args)
	{
		new HighLow();
	}
	
	public HighLow()
	{
		stack = new ArrayStack<String>(52);
	}
	
	public void gui()
	{
		Container pane = getContentPane();
		FlowLayout flo = new FlowLayout();
		
		setTitle("Maze");
		setLocation(200,200);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel title = new JPanel();
		JPanel game = new JPanel();
		
		JLabel level = new JLabel("Level0");
		
		pane.setLayout(flo);
		title.add(level);
		
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
