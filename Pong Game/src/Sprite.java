import java.awt.Color;
import java.awt.Rectangle;

public class Sprite {
	
	private int initialXPosition, initialYPosition;
	private int xPosition, yPosition;
	private int xVelocity, yVelocity;
	private int width, height;
	private Color colour;
	
	public void setInitialPosition(int initialX, int initialY) {
		
		initialXPosition = initialX;
		initialYPosition = initialY;
	}
	
	public void resetToInitialPosition() {
		
		setXPosition(initialXPosition);
		setYPosition(initialYPosition);
	}

	public int getXPosition() {
		
		return xPosition;
	}
	
	public void setXPosition(int xPosition) {
		
		this.xPosition = xPosition;
	}
	
	public void setXPosition(int newX, int panelWidth) {
		
		xPosition = newX;
		
		if (xPosition < 0) {
			
			xPosition = 0;
		}
		else if (xPosition + width > panelWidth) {
			
			xPosition = panelWidth - width;
		}
	}
	
	public int getYPosition() {
		
		return yPosition;
	}
	
	public void setYPosition(int yPosition) {
		
		this.yPosition = yPosition;
	}
	
	public void setYPosition(int newY, int panelHeight) {
		
		yPosition = newY;
		
		if (yPosition < 0) {
			
			yPosition = 0;
		}
		else if (yPosition + height > panelHeight) {
			
			yPosition = panelHeight - height;
		}
	}
	
	public int getXVelocity() {
		
		return xVelocity;
	}
	
	public void setXVelocity(int xVelocity) {
		
		this.xVelocity = xVelocity;
	}
	
	public int getYVelocity() {
		
		return yVelocity;
	}
	
	public void setYVelocity(int yVelocity) {
		
		this.yVelocity = yVelocity;
	}
	
	public int getWidth() {
		
		return width;
	}
	
	public void setWidth(int width) {
		
		this.width = width;
	}
	
	public int getHeight() {
		
		return height;
	}
	
	public void setHeight(int height) {
		
		this.height = height;
	}
	
	public Color getColour() {
		
		return colour;
	}
	
	public void setColour(Color colour) {
		
		this.colour = colour;
	}
	
	public Rectangle getRectangle() {
		
		return new Rectangle(getXPosition(), getYPosition(), getWidth(), getHeight());
	}
}
