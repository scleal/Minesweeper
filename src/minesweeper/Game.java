package minesweeper;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {

	private static final String Minesweeper = "Minesweeper";
	static final int PLAY = 1;
	private static int WIDTH, HEIGHT;
	private static Play play;
	private static Game game;
	private static AppGameContainer appgc;

	public static void fixResolution() {
		WIDTH = getWidth();
		HEIGHT = getHeight();
		try {
			appgc.setDisplayMode(WIDTH, HEIGHT, false);
		} catch (final SlickException e) {
			e.printStackTrace();
		}
	}

	public static int getHeight() {
		return (play.getHeight() + 3) * Cell.SIZE;
	} // 3 = north-south borders

	public static int getWidth() {
		return (play.getWidth() + 2) * Cell.SIZE;
	} // 2 = east-west borders

	public static void main(final String[] args) {
		try {
			game = new Game(Minesweeper);
			appgc = new AppGameContainer(game);
			appgc.setShowFPS(false);
			fixResolution();
			appgc.start();
		} catch (final SlickException e) {
			e.printStackTrace();
		}
	}

	public Game(final String name) {
		super(name);
		play = new Play(PLAY);
		addState(play);
	}

	@Override
	public void initStatesList(final GameContainer gc) throws SlickException {
		getState(PLAY).init(gc, this);
		this.enterState(PLAY);
	}

}
