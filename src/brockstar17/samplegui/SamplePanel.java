package brockstar17.samplegui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SamplePanel extends JPanel
{
	private SampleFrame sf;
	private boolean isHollow;

	/*
	 * This can be used to pass in frame parameters to the panel to do rendering math based on the
	 * dimensions of the window.
	 */
	public SamplePanel(SampleFrame sf)
	{
		this.sf = sf;
	}

	/*
	 * This method is the method that draws to the panel. It is run whenever the panel is drawn (or
	 * redrawn) It must be overriden to mark that it is different from the one in the JPanel Class
	 */
	@Override
	protected void paintComponent(Graphics g) {
		// ALL CODE in this method must come after the call to the super method in order to be
		// properly rendered on the screen.
		super.paintComponent(g);

		// g.drawRect(sf.getWidth() / 2 - 10, sf.getHeight() / 2 - 10, 20, 20);

		if (!isHollow) {
			for (int i = sf.getHeight() / 20 - 5; i > 0; i--) {

				if (i % 2 == 0) {
					g.setColor(Color.BLACK);
				}
				else {
					g.setColor(Color.BLUE);
				}

				g.fillRect(sf.getWidth() / 2 - (i * 10), sf.getHeight() / 2 - (i * 10), i * 20, i * 20);
			}
		}
		else {
			for (int i = sf.getHeight() / 20 - 5; i > 0; i--) {

				if (i % 2 == 0) {
					g.setColor(Color.BLACK); // Sets the color of the graphics
				}
				else {
					g.setColor(Color.BLUE);
				}

				g.fillOval(sf.getWidth() / 2 - (i * 10), sf.getHeight() / 2 - (i * 10), i * 20, i * 20);
			}
		}

	}

	protected void setIsHollow(boolean isHollow) {
		this.isHollow = isHollow;
	}

	protected boolean getIsHollow() {
		return this.isHollow;
	}
}
