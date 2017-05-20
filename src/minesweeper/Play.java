package minesweeper;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import minesweeper.Cell.Status;

public class Play extends BasicGameState {

	public enum GameStatus {
		IN_PROGRESS, WON, LOST
	}

	private final Field field;
	private int x, y;
	private boolean mouseLeft, mouseRight;;
	private GameStatus gameStatus;

	Play(final int state) {
		field = new Field(Mode.BeginnerMode());
		gameStatus = GameStatus.IN_PROGRESS;
	}

	public int getHeight() {
		return field.getHeight();
	}

	@Override
	public int getID() {
		return Game.PLAY;
	}

	public int getWidth() {
		return field.getWidth();
	}

	@Override
	public void init(final GameContainer gc, final StateBasedGame sbg) throws SlickException {
	}

	public boolean isClear() {
		return field.isClear();
	}

	@Override
	public void render(final GameContainer gc, final StateBasedGame sbg, final Graphics graphics)
		throws SlickException {
		graphics.setColor(Color.lightGray);
		graphics.fillRect(0, 0, Game.getWidth(), Game.getHeight());
		field.draw(graphics, x, y, gameStatus);
	}

	public void setMode(final Mode m) {
		gameStatus = GameStatus.IN_PROGRESS;
		field.setMode(m);
	}

	@Override
	public void update(final GameContainer gc, final StateBasedGame sbg, final int delta) throws SlickException {

		final Input input = gc.getInput();
		x = input.getMouseX() / Cell.SIZE - 1; // border size of 30 pixels 1*Cell.SIZE
		y = input.getMouseY() / Cell.SIZE - 2; // border size of 60 pixels 2*Cell.SIZE

		if (gameStatus == GameStatus.IN_PROGRESS) {
			if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
				mouseLeft = true;
			} else if (mouseLeft) {
				field.generateMines(x, y);
				field.revealCells(x, y);
				final Cell cell = field.getCell(x, y);
				if (cell != null && cell.hasMine()) {
					gameStatus = GameStatus.LOST;
				} else if (isClear()) {
					gameStatus = GameStatus.WON;
				}
				mouseLeft = false;
			} else if (input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON)) {
				mouseRight = true;
			} else if (mouseRight || input.isKeyPressed(Input.KEY_F)) {
				final Cell cell = field.getCell(x, y);
				switch (cell.getStatus()) {
				case FLAGGED:
					cell.setStatus(Status.HIDDEN);
					break;
				case HIDDEN:
					cell.setStatus(Status.FLAGGED);
				default:
					break;
				}
				if (isClear()) {
					gameStatus = GameStatus.WON;
				}
				mouseRight = false;
			}
		}

		if (input.isKeyPressed(Input.KEY_E)) {
			setMode(Mode.ExpertMode());
		} else if (input.isKeyPressed(Input.KEY_I)) {
			setMode(Mode.IntermediateMode());
		} else if (input.isKeyPressed(Input.KEY_B)) {
			setMode(Mode.BeginnerMode());
		} else if (input.isKeyPressed(Input.KEY_R)) {
			setMode(field.getMode());
		}

	}

}