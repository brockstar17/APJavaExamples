package brockstar17.dotsandboxes.utils;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import brockstar17.dotsandboxes.DBServer;

public class ServerMessageBoard implements ActionListener
{
	private DBServer server;

	private ArrayList<ServerMessage> messages;
	private Timer timer;

	private int limit;

	private int x = 0, y = 0;
	private int width = 100;

	/**
	 * Creates a new Client Message Board with a default message limit of 10 messages.
	 */
	public ServerMessageBoard(DBServer server)
	{
		this.server = server;

		this.limit = 10;
		this.messages = new ArrayList<ServerMessage>();

		timer = new Timer(1000, this);
		timer.start();
	}

	/**
	 * Creates a new Client Message Board with a defined message limit.
	 * 
	 * @param limit
	 *            The message limit.
	 */
	public ServerMessageBoard(DBServer server, int limit)
	{
		this.server = server;

		this.limit = limit;
		this.messages = new ArrayList<ServerMessage>();

		timer = new Timer(1000, this);
		timer.start();
	}

	/**
	 * Returns the client messages.
	 * 
	 * @return The array list containing the client messages.
	 */
	public ArrayList<ServerMessage> getMessages() {
		return this.messages;
	}

	/**
	 * Set the x and y coordinates of the top left corner of this message board.
	 * 
	 * @param x
	 *            The x coordinate of the top left corner.
	 * @param y
	 *            The y coordinate of the top left corner.
	 */
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Return the x coordinate of the top left corner of this message board.
	 * 
	 * @return The x coordinate of the top left corner.
	 */
	public int getXCoord() {
		return this.x;
	}

	/**
	 * Return the y coordinate of the top left corner of this message board.
	 * 
	 * @return The y coordinate of the top left corner.
	 */
	public int getYCoord() {
		return this.y;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Sets the message limit.
	 * 
	 * @param limit
	 *            The message limit.
	 */
	public void setMessageLimit(int limit) {
		this.limit = limit;
	}

	/**
	 * Gets the message limit.
	 * 
	 * @return The message limit.
	 */
	public int getMessageLimit() {
		return this.limit;
	}

	/**
	 * Adds a default Client Message.
	 * 
	 * @param msg
	 *            The message to display.
	 */
	public void addMessage(String msg) {
		if (!messageLimitReached()) {
			messages.add(new ServerMessage(msg, Color.BLACK, 10, false));
			server.repaint();
		}
		else {
			// Removes the oldest message.
			removeMessage(0);
		}

	}

	/**
	 * Adds a Client Message with a specified color.
	 * 
	 * @param msg
	 *            The message to display.
	 * @param color
	 *            The color to display it in.
	 */
	public void addMessage(String msg, Color color) {
		if (!messageLimitReached()) {
			messages.add(new ServerMessage(msg, color, 10, false));
			server.repaint();
		}
		else {
			// Removes the oldest message.
			removeMessage(0);
		}

	}

	/**
	 * Adds a Client Message that may always show with a specified color
	 * 
	 * @param msg
	 * @param color
	 * @param alwaysShow
	 */
	public void addMessage(String msg, Color color, boolean alwaysShow) {
		if (!messageLimitReached()) {
			messages.add(new ServerMessage(msg, color, 10, alwaysShow));
			server.repaint();
		}
		else {
			// Removes the oldest message.
			removeMessage(0);
		}
	}

	/**
	 * Adds a Client Message that will display for a specified time in a specified color.
	 * 
	 * @param msg
	 *            The message to display.
	 * @param color
	 *            The color to display it in.
	 * @param time
	 *            The number of seconds to display it for.
	 */
	public void addMessage(String msg, Color color, int time) {
		if (!messageLimitReached()) {
			messages.add(new ServerMessage(msg, color, time, false));
			server.repaint();
		}
		else {
			// Removes the oldest message.
			removeMessage(0);
		}
	}

	/**
	 * Return if the message limit has been reached.
	 * 
	 * @return True if the message limit has been reached and no more messages can be added.
	 */
	private boolean messageLimitReached() {
		if (messages.size() == limit) {
			return true;
		}
		return false;
	}

	/**
	 * Removes the message at the specified index and trims the array list to size.
	 * 
	 * @param index
	 *            The index to remove the message from.
	 */
	private void removeMessage(int index) {
		messages.remove(index); // Removes the message.
		messages.trimToSize(); // Trims the array list.
		server.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (messages.size() > 0) {
			for (int i = 0; i < messages.size(); i++) {
				messages.get(i).decreaseShowTimer();
				if (messages.get(i).stopShowing()) {
					removeMessage(i);
				}

			}
		}
	}

	public void drawMessages(Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		int stringY = fm.getHeight();
		int ty = y;

		for (int i = 0; i < messages.size(); i++) {

			ServerMessage msg = messages.get(i);
			g.setColor(msg.getColor());
			ty += stringY * i;
			if (fm.stringWidth(msg.getMessage()) >= this.width) {
				// System.out.println(fm.stringWidth(msg.getMessage()));
				ArrayList<String> lines = wrapLines(fm, msg.getMessage());

				for (String s : lines) {
					if (s != null) {
						ty += stringY;
						g.drawString(s, x + 5, ty);

					}
				}
			}
			else {

				g.drawString(msg.getMessage(), x + 5, ty);
			}

			// g.drawString(msg.getMessage(), x + 5, y + stringY * i);
		}
	}

	private ArrayList<String> wrapLines(FontMetrics fm, String toWrap) {
		ArrayList<String> lines = new ArrayList<String>();
		String temp = "";
		int i = 1;

		while (fm.stringWidth(toWrap) > this.width) {
			temp = toWrap.substring(0, i);
			if (fm.stringWidth(temp) >= this.width) {
				lines.add(temp);
				toWrap = " -" + toWrap.substring(i);
				i = 1;
			}
			i++;
		}

		lines.add(toWrap);

		return lines;
	}
}
