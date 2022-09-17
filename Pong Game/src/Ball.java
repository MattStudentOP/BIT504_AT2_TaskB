import java.awt.Color;

public class Ball extends Sprite {
	
	static final Color COLOUR = Color.WHITE;
	static final int WIDTH = 25;
	static final int HEIGHT = 25;

	public Ball(int panelWidth, int panelHeight) {
		
		setColour(COLOUR);
		setWidth(WIDTH);
		setHeight(HEIGHT);
		setInitialPosition(panelWidth / 2 - (getWidth() / 2), panelHeight / 2 - (getHeight() / 2));
		resetToInitialPosition();
	}
}
