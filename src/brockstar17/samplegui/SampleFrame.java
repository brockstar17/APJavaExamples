package brockstar17.samplegui;

import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class SampleFrame extends JFrame implements KeyListener
{
	private SamplePanel panel;

	public SampleFrame()
	{
		super("Sample");

		this.setVisible(true);
		this.setSize(550, 500);
		this.setLocationRelativeTo(null);

		Container c = this.getContentPane();
		panel = new SamplePanel(this);
		c.add(panel);

		this.addKeyListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new SampleFrame();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			this.panel.setIsHollow(!this.panel.getIsHollow());
			repaint();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}
