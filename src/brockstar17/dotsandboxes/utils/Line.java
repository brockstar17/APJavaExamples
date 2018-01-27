package brockstar17.dotsandboxes.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Line implements Cerealizer
{

	private Color color;
	private Point p1, p2;

	private boolean isTaken;
	private String ord;

	public Line(Point p1, Point p2, String ord)
	{
		this.color = Color.GRAY;
		this.p1 = p1;
		this.p2 = p2;
		this.ord = ord;
		this.isTaken = false;
	}

	public Line(int x1, int x2, int y1, int y2, String ord)
	{
		this.p1 = new Point(x1, y1);
		this.p2 = new Point(x2, y2);
		this.color = Color.GRAY;
		this.ord = ord;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Point getCenterPoint() {
		return new Point((this.p1.x + this.p2.x) / 2, (this.p1.y + this.p2.y) / 2);
	}

	public Point getP1() {
		return this.p1;
	}

	public Point getP2() {
		return this.p2;
	}

	public void setTaken() {
		this.isTaken = true;
	}

	public boolean isTaken() {
		return this.isTaken;
	}

	public void draw(Graphics g) {
		g.setColor(color);
		int x1 = p1.x, x2 = p2.x, y1 = p1.y, y2 = p2.y;
		switch (ord) {
		case "N":
			for (int i = 0; i < 10; i++) {

				g.drawLine(x1, y1, x2, y2);
				y1++;
				y2++;
			}
			return;

		case "E":
			for (int i = 0; i < 10; i++) {
				g.drawLine(x1, y1, x2, y2);
				x1++;
				x2++;
			}
			return;

		case "S":
			for (int i = 0; i < 10; i++) {
				g.drawLine(x1, y1, x2, y2);
				y1++;
				y2++;
			}
			return;

		case "W":
			for (int i = 0; i < 10; i++) {
				g.drawLine(x1, y1, x2, y2);
				x1++;
				x2++;
			}
			return;

		default:

			return;
		}
	}

	public boolean isLineClicked(int mx, int my) {

		if (mx > this.p1.x - 20 && mx < this.p2.x + 20 && my > this.p1.y - 20 && my < this.p2.y + 20) {
			return true;
		}

		return false;
	}

	public String getOrdinance() {
		return this.ord;
	}

	@Override
	public void sendSerialized(ObjectOutputStream oos) throws IOException {
		oos.writeObject(color);
		oos.writeObject(p1);
		oos.writeObject(p2);
		oos.writeBoolean(isTaken);
		oos.writeUTF(ord);
	}

	@Override
	public void deserialize(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		this.color = (Color) ois.readObject();
		this.p1 = (Point) ois.readObject();
		this.p2 = (Point) ois.readObject();
		this.isTaken = ois.readBoolean();
		this.ord = ois.readUTF();
	}
}
