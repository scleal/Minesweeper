package minesweeper;

import java.util.Random;

import org.newdawn.slick.Graphics;

import minesweeper.Cell.Status;
import minesweeper.Play.GameStatus;

public class Field {

	private static Random random = new Random();
	private static final String won = " YOU WON!";
	private static final String lost = " YOU LOST :(";
	private Cell[][] field;
	private int minesGenerated;
	private Mode mode;

	public Field(final Mode mode) {
		init(mode);
	}

	private void generateAdjacentMineCount(final int x, final int y) {
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {
				final Cell cell = getCell(i, j);
				if (cell != null) {
					cell.incrementAdjacentMines();
				}
			}
		}
	}

	private void init(final Mode mode) {
		this.mode = mode;
		field = new Cell[getWidth()][getHeight()];
		minesGenerated = 0;
		generateField();
	}

	public void draw(final Graphics graphics, final int mouseX, final int mouseY, final GameStatus gameStatus) {
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				final Cell cell = field[i][j];
				cell.draw(graphics, (i + 1) * Cell.SIZE, (j + 2) * Cell.SIZE,
					(mouseX == i && mouseY == j) ? true : false, gameStatus);
			}
		}
		if (gameStatus != GameStatus.IN_PROGRESS) {
			graphics.drawString(gameStatus == GameStatus.WON ? won : lost, Game.getWidth() / 2 - Cell.SIZE * 2,
				Cell.SIZE);
		}
	}

	public void generateField() {
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				field[i][j] = new Cell();
			}
		}
	}

	public void generateMines(final int x, final int y) {
		while (minesGenerated < getMines()) {
			final int xx = random.nextInt(getWidth());
			final int yy = random.nextInt(getHeight());
			final Cell cell = getCell(xx, yy);
			if (!cell.hasMine() && (x != xx || y != yy)) {
				cell.setHasMine(true);
				minesGenerated++;
				generateAdjacentMineCount(xx, yy);
			}
		}
	}

	public Cell getCell(final int x, final int y) {
		try {
			return field[x][y];
		} catch (final ArrayIndexOutOfBoundsException e) { // hmm, bad practice?
			return null;
		}
	}

	public int getHeight() {
		return mode.getHeight();
	}

	public int getMines() {
		return mode.getMines();
	}

	public Mode getMode() {
		return mode;
	}

	public int getWidth() {
		return mode.getWidth();
	}

	public boolean isClear() {
		for (int i = 0; i < getWidth(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				final Cell cell = field[i][j];
				final Status status = cell.getStatus();
				if ((cell.hasMine() && status != Status.FLAGGED) || (!cell.hasMine() && status != Status.REVEALED)) {
					return false;
				}
			}
		}
		return true;
	}

	public void revealCells(final int x, final int y) {
		final Cell cell = getCell(x, y);
		if (cell != null && cell.getStatus() == Status.HIDDEN) {
			cell.setStatus(Status.REVEALED);
			// cells with mines in them also have an "adjacent" mine count of at least 1
			// so recursive revealCells calls are not made if a cell with a mine is clicked
			if (cell.getAdjacentMineCount() == 0) {
				revealCells(x - 1, y);
				revealCells(x + 1, y);
				revealCells(x, y - 1);
				revealCells(x, y + 1);
				revealCells(x - 1, y - 1);
				revealCells(x - 1, y + 1);
				revealCells(x + 1, y - 1);
				revealCells(x + 1, y + 1);
			}
		}
	}

	public void setMode(final Mode mode) {
		init(mode);
		Game.fixResolution();
	}

}
