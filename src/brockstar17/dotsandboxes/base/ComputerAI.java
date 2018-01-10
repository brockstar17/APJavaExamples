package brockstar17.dotsandboxes.base;

import java.awt.Color;

import brockstar17.dotsandboxes.multiplayer.Line;
import brockstar17.dotsandboxes.multiplayer.Utils;

public class ComputerAI
{

	private BoxandDots bd;

	public ComputerAI(BoxandDots bd)
	{
		this.bd = bd;
	}

	public void pickAISpace() {
		do {

			bd.goAgain = false;

			int randBox = 0;
			int randLine = 0;
			while (BoxandDots.board[randBox].isFullBox()) {
				randBox++;

			}

			while (BoxandDots.board[randBox].getLine(randLine).isTaken()) {
				randLine++;
			}

			Line click = BoxandDots.board[randBox].getLine(randLine);
			// System.out.println("Line " + click.getOrdinance());
			click.setColor(Color.RED);
			click.setTaken();

			if (BoxandDots.board[randBox].isFullBox()) {
				bd.goAgain = true;
			}

			int adj = Utils.getAdjId(click.getOrdinance(), BoxandDots.board[randBox].getBoxId());
			if (adj != -1) {
				BoxandDots.board[adj].updateShareLine(click.getOrdinance(), Color.RED);
			}

		} while (bd.goAgain && !bd.isBoardFull());

		bd.endTurn("player2");
	}
}
