package minesweeper;

public class Mode {

	private static final Mode BeginnerMode = new Mode(9, 9, 10);
	private static final Mode IntermediateMode = new Mode(16, 16, 40);
	private static final Mode ExpertMode = new Mode(30, 16, 99);

	public static Mode BeginnerMode() {
		return BeginnerMode;
	}

	public static Mode ExpertMode() {
		return ExpertMode;
	}

	public static Mode IntermediateMode() {
		return IntermediateMode;
	}

	private final int width, height, mines;

	public Mode(final int width, final int height, final int mines) {
		this.width = width;
		this.height = height;
		this.mines = mines;
	}

	public int getHeight() {
		return height;
	}

	public int getMines() {
		return mines;
	}

	public int getWidth() {
		return width;
	}

}
