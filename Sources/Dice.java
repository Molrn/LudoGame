import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

// Class representing the dice frame
// Displays the value of the dice roll
// Allows players to interact with the game when they can't move any piece
public class Dice implements ActionListener, WindowListener {
	
	private JButton dice;
	private JFrame f;
	private Color color;
	private int value;
	private boolean ready;
	
//	Constructor
	public Dice() {
		value = 6;
		color = Color.white;
		dice=new JButton(""+value);
		dice.addActionListener(this);
		dice.setBackground(color);
		dice.setFont(new Font("Arial", Font.ITALIC, 100));
	}
	
//	Getters
	public JFrame getFrame() { return f; }
	public int getValue() { return value; }
	public JButton getDice() { return dice; }
	public boolean isReady() { return ready; }

//	Setters
	public void setValue(int v) { value=v; }
	public void setColor(Color c) { 
		color = c;
		dice.setBackground(color);
	}
	
//	Displays the frame containing the dice
	public void print(){
		f = new JFrame("Dice");
		f.setLocationRelativeTo(null);
		f.add(dice);
		f.setSize(200,200);
		f.setVisible(true);
	}
	
//	Rolls the dice
	public void roll() {
		ready = false;
		Random r = new Random();
		value = r.nextInt(6)+1;
		dice.setText(""+value);
	}
	
//	Displays an error message on the button
	public void errorMessage() {
		dice.setFont(new Font("Arial", Font.PLAIN, 14));
		dice.setText("No play to move (press)");
	}
	
//	Action performed by the button 
//	Allows the game to move to another turn
	public void actionPerformed(ActionEvent e) {
		ready = true;
		dice.setFont(new Font("Arial", Font.ITALIC, 100));
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	public void windowOpened(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}
}
