package brockstar17.dotsandboxes.utils;

import java.awt.Color;

public class ServerMessage
{
	private String message;
	private Color msgColor;
	private boolean alwaysShow;
	private int showTime;

	/**
	 * Creates a new client message.
	 * 
	 * @param message
	 *            The content of the message.
	 * @param color
	 *            The color to display the message in.
	 * @param showTime
	 *            The amount of time in seconds that this message should be displayed for. If zero
	 *            this message will not display.
	 * @param alwaysShow
	 *            Pass in true to this to always show the message.
	 */
	public ServerMessage(String message, Color color, int showTime, boolean alwaysShow)
	{
		this.message = message;
		this.msgColor = color;
		this.alwaysShow = alwaysShow;
		this.showTime = showTime;
	}

	/**
	 * Sets the content of this message.
	 * 
	 * @param msg
	 *            The message to set.
	 */
	public void setMessage(String msg) {
		this.message = msg;
	}

	/**
	 * Set the color to draw this message.
	 * 
	 * @param c
	 *            The color of this message.
	 */
	public void setColor(Color c) {
		this.msgColor = c;
	}

	/**
	 * Set a time for this message to be shown. If zero is passed in the message will be flagged to
	 * stop displaying.
	 * 
	 * @param time
	 *            The time (in seconds) to display this message for.
	 */
	public void setShowTime(int time) {
		this.showTime = time;
	}

	/**
	 * Get the message.
	 * 
	 * @return The message to display.
	 */
	public String getMessage() {
		return this.message;
	}

	/**
	 * Get the color of this message.
	 * 
	 * @return The color that this message should be displayed in.
	 */
	public Color getColor() {
		return this.msgColor;
	}

	/**
	 * Return if this message should stop being displayed.
	 * 
	 * @return True if this message should stop being displayed.
	 */
	public boolean stopShowing() {
		if (this.showTime <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * Decrease the show timer counter by one.
	 */
	public void decreaseShowTimer() {
		if (!this.alwaysShow) {
			this.showTime--;
		}
	}

}
