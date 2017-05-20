package minesweeper;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

import minesweeper.Play.GameStatus;

public class Cell {

	public enum Status {
		HIDDEN, REVEALED, FLAGGED
	}

	private static final Color cellColor = new Color(100, 180, 255);
	private static final Color mineCell = new Color(255, 100, 100);

	private static final Color flaggedCell = new Color(50, 255, 150);
	private static final Color ultralight = cellColor.brighter().brighter();
	private static Color color = cellColor;
	private static Color bottomRight = cellColor.darker();

	private static Color topLeft = cellColor.brighter();
	public static final int SIZE = 30;
	private Status status;

	private boolean hasMine;

	private int adjacentMines;;

	public Cell() {
		status = Status.HIDDEN;
		hasMine = false;
		adjacentMines = 0;
	}

	public void draw(final Graphics graphics, final int x, final int y, final boolean mouseOver,
		final GameStatus gameStatus) {

		if (hasMine && gameStatus == GameStatus.LOST) {
			color = mineCell;
		} else if (status == Status.REVEALED) {
			color = ultralight;
		} else if (status == Status.FLAGGED) {
			color = flaggedCell;
		} else if (status == Status.HIDDEN) {
			color = mouseOver && gameStatus == GameStatus.IN_PROGRESS ? ultralight : cellColor;
		}

		graphics.setColor(color);
		graphics.fillRect(x + 1, y + 1, SIZE - 2, SIZE - 2);

		topLeft = color.brighter();
		graphics.setColor(topLeft);
		graphics.drawLine(x, y + SIZE - 1, x, y);
		graphics.drawLine(x, y, x + SIZE - 1, y);

		bottomRight = color.darker();
		graphics.setColor(bottomRight);
		graphics.drawLine(x + 1, y + SIZE - 1, x + SIZE - 1, y + SIZE - 1);
		graphics.drawLine(x + SIZE - 1, y + SIZE - 1, x + SIZE - 1, y + 1);

		graphics.setColor(Color.black);
		if (adjacentMines != 0 && !hasMine && status == Status.REVEALED) {
			graphics.drawString(Integer.toString(adjacentMines), x + SIZE / 3, y + SIZE / 4);
		}

	}

	public int getAdjacentMineCount() {
		return adjacentMines;
	}

	public Status getStatus() {
		return status;
	}

	public boolean hasMine() {
		return hasMine;
	}

	public void incrementAdjacentMines() {
		adjacentMines++;
	}

	public void setHasMine(final boolean hasMine) {
		this.hasMine = hasMine;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}

}
