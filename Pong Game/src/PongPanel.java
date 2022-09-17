import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import javax.swing.Timer;
import javax.swing.JPanel;
   
public class PongPanel extends JPanel implements ActionListener, KeyListener {
		
	private final static Color BACKGROUND_COLOR = Color.black;
	private final static int TIMER_DELAY = 5;
	private final static int BALL_MOVEMENT_SPEED = 8;
	private final static int POINTS_TO_WIN = 3;
	private final static int SCORE_TEXT_X = 100;
	private final static int SCORE_TEXT_Y = 100;
	private final static int WIN_TEXT_X = 200;
	private final static int WIN_TEXT_Y = 200;
	private final static int SCORE_FONT_SIZE = 50;
	private final static int WIN_FONT_SIZE = 50;
	private final static String DEFAULT_FONT_FAMILY = "Serif";
	private final static String WINNER_TEXT = "Win!";
	int player1Score = 0, player2Score = 0;
	Player gameWinner;
	GameState gameState = GameState.Initialising;
	Ball ball;
	Paddle paddle1, paddle2;
       
	public PongPanel() {
			
		setBackground(BACKGROUND_COLOR);
		Timer timer = new Timer(TIMER_DELAY, this);
        timer.start();
        addKeyListener(this);
        setFocusable(true);
	}
	
	public void createObjects() {
		
		ball = new Ball(getWidth(), getHeight());
		ball.setXVelocity(BALL_MOVEMENT_SPEED);
		ball.setYVelocity(BALL_MOVEMENT_SPEED);
		paddle1 = new Paddle(Player.One, getWidth(), getHeight());
		paddle2 = new Paddle(Player.Two, getWidth(), getHeight());
	}
	
	private void update() {
		
		switch(gameState) {
			
			case Initialising: {
			
				createObjects();
				gameState = GameState.Playing;
				break;
			}
			
			case Playing: {
				
				moveObject(paddle1);
				moveObject(paddle2);
				moveObject(ball);
				checkWallBounce();
				checkPaddleBounce();
				checkWin();
				break;
			}
			
			case GameOver: {
				
				break;
			}
		}
	}
	
	private void paintSprite(Graphics g, Sprite sprite) {
		
		g.setColor(sprite.getColour());
		g.fillRect(sprite.getXPosition(), sprite.getYPosition(), sprite.getWidth(), sprite.getHeight());
	}
	
	private void paintDottedLine(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g.create();
		Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
		g2d.setStroke(dashed);
		g2d.setPaint(Color.WHITE);
		g2d.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
		g2d.dispose();
	}
	
	private void paintScores(Graphics g) {
		
		Font scoreFont = new Font(DEFAULT_FONT_FAMILY, Font.BOLD, SCORE_FONT_SIZE);
		String leftScore = Integer.toString(player1Score);
		String rightScore = Integer.toString(player2Score);
		g.setFont(scoreFont);
		g.drawString(leftScore, SCORE_TEXT_X, SCORE_TEXT_Y);
		g.drawString(rightScore, getWidth() - SCORE_TEXT_X, SCORE_TEXT_Y);
	}
	
	private void paintWinMessage(Graphics g) {
		
		if (gameWinner != null) {
		
			Font winFont = new Font (DEFAULT_FONT_FAMILY, Font.BOLD, WIN_FONT_SIZE);
			g.setFont(winFont);
			int xCentre = getWidth() / 2;
			
			if (gameWinner == Player.One) {
				
				g.drawString(WINNER_TEXT, xCentre - WIN_TEXT_X, WIN_TEXT_Y);
			}
			else if (gameWinner == Player.Two) {

				g.drawString(WINNER_TEXT, xCentre + WIN_TEXT_X, WIN_TEXT_Y);
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		paintDottedLine(g);
		
		if (gameState != GameState.Initialising) {
			
			paintSprite(g, ball);
			paintSprite(g, paddle1);
			paintSprite(g, paddle2);
			paintScores(g);
			paintWinMessage(g);
		}
	}
	
	public void moveObject(Sprite obj) {
		
		obj.setXPosition(obj.getXPosition() + obj.getXVelocity(), getWidth());
		obj.setYPosition(obj.getYPosition() + obj.getYVelocity(), getHeight());
	}
	
	public void checkWallBounce() {
		
		if (ball.getXPosition() <= 0) {
			
			ball.setXVelocity(-ball.getXVelocity());
			addScore(Player.Two);
			resetBall();
		}
		else if (ball.getXPosition() >= getWidth() - ball.getWidth()) {
			
			ball.setXVelocity(-ball.getXVelocity());
			addScore(Player.One);
			resetBall();
		}
		
		if (ball.getYPosition() <= 0 || ball.getYPosition() >= getHeight() - ball.getHeight()) {
			
			ball.setYVelocity(-ball.getXVelocity());
		}
	}
	
	public void checkPaddleBounce() {
		
		if (ball.getXVelocity() < 0 && ball.getRectangle().intersects(paddle1.getRectangle())) {
			
			ball.setXVelocity(BALL_MOVEMENT_SPEED);
		}
		
		if (ball.getXVelocity() > 0 && ball.getRectangle().intersects(paddle2.getRectangle())) {
			
			ball.setXVelocity(-BALL_MOVEMENT_SPEED);
		}
	}
	
	public void resetBall() {
		
		ball.resetToInitialPosition();
	}
	
	public void addScore(Player p) {
		 
		if (p == Player.One) {
			
			player1Score ++;
		}
		else if (p == Player.Two) {
			
			player2Score ++;
		}
	}
	
	public void checkWin() {
		
		if (player1Score >= POINTS_TO_WIN) {
			
			gameWinner = Player.One;
			gameState = GameState.GameOver;
		}
		
		if (player2Score >= POINTS_TO_WIN) {
			
			gameWinner = Player.Two;
			gameState = GameState.GameOver;
		}
	}
  
	@Override
	public void keyPressed(KeyEvent event) {
    	  
		if (event.getKeyCode() == KeyEvent.VK_W) {
			
			paddle1.setYVelocity(-10);
		}
		else if (event.getKeyCode() == KeyEvent.VK_S) {
			
			paddle1.setYVelocity(10);
		}
		else if (event.getKeyCode() == KeyEvent.VK_UP) {
			
			paddle2.setYVelocity(-10);
		}
		else if (event.getKeyCode() == KeyEvent.VK_DOWN) {
			
			paddle2.setYVelocity(10);
		}
	}
  
	@Override
	public void keyReleased(KeyEvent event) {
     
		if (event.getKeyCode() == KeyEvent.VK_W || event.getKeyCode() == KeyEvent.VK_S) {
			
			paddle1.setYVelocity(0);
		}
		else if (event.getKeyCode() == KeyEvent.VK_UP || event.getKeyCode() == KeyEvent.VK_DOWN) {
			
			paddle2.setYVelocity(0);
		}
	}
  
	@Override
	public void keyTyped(KeyEvent event) {
      
	}
   
	@Override
	public void actionPerformed(ActionEvent event) {
      
		update();
		repaint();
	}
  
 }