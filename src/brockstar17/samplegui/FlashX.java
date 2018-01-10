package brockstar17.samplegui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class FlashX extends JFrame implements ActionListener
{
	private Paint p;
	private float clock;

	private int sub = 60;
	private int sy = 30;
	private int x1 = sub;
	private int x2;
	private int y1 = sy;
	private int y2;

	private Color cx;

	private boolean rup;
	private boolean doGreen;
	// 17, 9, 130

	private int red, green, blue;

	public FlashX()
	{
		this.setVisible(true);
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		Container c = this.getContentPane();
		this.p = new Paint(this);
		c.add(p);

		clock = 0;
		rup = true;
		doGreen = true;

		Timer t = new Timer(10, this);
		t.start();

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		y2 = this.getHeight() - 90;
		x2 = this.getWidth() - sub;

		cx = Color.BLACK;
		red = 17;
		green = 9;
		blue = 130;

	}

	public static void main(String[] args) {
		new FlashX();
	}

	private class Paint extends JPanel
	{

		protected Paint(FlashX xo)
		{
		}

		@Override
		protected void paintComponent(Graphics g) {

			super.paintComponent(g);
			drawX(g);
		}

		private void drawX(Graphics g) {

			int offT = 6;
			int outT = 22;
			int inT = 10;

			drawLine(g, Color.BLACK, x1, x2, y1, y2, outT, false);
			drawLine(g, Color.BLACK, x1, x2, y1, y2, outT, true);

			drawLine(g, cx, x1 + 2 * offT, x2, y1 + offT, y2 - offT, inT, false);
			drawLine(g, cx, x1 - 1, x2 - 2 * offT - 1, y1 + offT, y2 - offT, inT, true);

		}

		private void drawLine(Graphics g, Color c, int x1, int x2, int y1, int y2, int thick, boolean reverse) {

			g.setColor(c);

			if (!reverse) {
				for (int i = 0; i < thick; i++) {
					g.drawLine((x1 + i), y1, (x2 + i), y2);
				}
			}
			else {
				for (int i = 0; i < thick; i++) {
					g.drawLine((x2 - i), y1, (x1 - i), y2);
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		clock += 0.05;
		// System.out.println(green + (int) (clock));

		if (red + clock > 250) {
			rup = false;

		}
		if (red - clock < 9) {
			rup = true;
			doGreen = !doGreen;
		}

		if (rup) {
			red += clock;
		}
		else {
			red -= clock;
		}

		cx = new Color(red, green, blue);

		if (clock > 30) {
			clock = 0;
		}

		repaint();
	}
}
