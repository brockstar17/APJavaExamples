package brockstar17.samples;

import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * This class 
 * @author Brock
 *
 */
@SuppressWarnings("serial")
public class Panel extends JPanel{

	private int num;
	
	public Panel(int num) {
		this.num = num;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString("" + Example1.testInt + " + " + num, 200, 50);
		
	}

	
}
