package game.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.JFrame;
import game.main.classes.EntityA;
import game.main.classes.EntityB;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 320;
	public static final int HEIGHT = WIDTH / 12 * 9;
	public static final int SCALE = 2;
	// Variables for the dimensions of the window for the game
	public final String TITLE = "Space Invaders";
	// Variable to store the title of the window
	
	private boolean running = false;
	// Variable to check whether the thread is running
	private Thread thread;
	// Initializing the thread
	// A thread allows a class to be run on its own mini program seperate from everything else
	
	private BufferedImage image = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
	private BufferedImage spriteSheet = null;
	// The sprite sheet is an image that holds everything in the game aside from the background including players, enemies, and bullets
	private BufferedImage background = null;
	// BufferedImage variables hold images
	
	private boolean shooting = false;
	// Variable to check whether the spaceship is shooting
	
	private int enemy_count = 5;
	// Variable to check the amount of enemies on the screen
	private int enemy_killed = 0;
	// Variable to check the amount of enemies killed
	
	private Player p;
	private Controller c;
	private Textures tex;
	private Menu menu;
	private GameOver go;
	// Initializations of different classes in the java project
	
	public LinkedList<EntityA> ea;
	public LinkedList<EntityB> eb;
	// LinkedList is a collection of objects
	// These lists will store data from the Entity A and B interfaces
	// Entities are grouped into A and B by looking at what should happen if the objects make contact
	
	public static int HEALTH = 200;
	// Variable to store the player health and control the health bar
	
	public static enum STATE{
		MENU,
		GAME,
		GAMEOVER
	};
	// Enum/enumerations are used to define variables
	// Variables for different screens/states of the game are being defined
	
	public static STATE State = STATE.MENU;
	// Variable of the enumeration STATE is made and set to MENU
	
	public void init() {
		// Method where everything is initialized
		requestFocus();
		// Bring focus to the JFrame without clicking it
		BufferedImageLoader loader = new BufferedImageLoader();
		// Object made for the BufferedImageLoader class
		try {
			// Try catch statement checks to see if the code in the body of try can be run and if it cannot, it runs whatever is in the body of the catch
			spriteSheet = loader.loadImage("/spritesheet.png");
			background = loader.loadImage("/background.png");
			// Calls the loadImage method from the BufferedImageLoader class which loads images
		}catch(IOException e) {
			e.printStackTrace();
		} 
		
		tex = new Textures(this);
		c = new Controller(tex, this);
		p = new Player(200, 200, tex, this, c);
		// The constructors of these classes take parameters
		// The keyword "this" refers to this class
		menu = new Menu();
		go = new GameOver();
		// Finishing setting up the objects that were initialized earlier
		
		ea = c.getEntityA();
		eb = c.getEntityB();
		// Calls the getEntityA and B methods in the controller class
		
		this.addKeyListener(new KeyInput(this));
		// Adds a KeyListener to the class which allows for keyboard input in the JFrame
		this.addMouseListener(new MouseInput());
		// Adds a MouseListener to the class which allows for mouse input in the JFrame
		
		c.createEnemy(enemy_count);
		
		
	}
	
	private synchronized void start() {
		// Method to start the thread
		// Synchronized allows the access of multiple threads
		if(running)
			return;
		// Exits the method if the thread is already running
		
		running = true;
		thread = new Thread(this);
		// Finishes setting up the Thread that was initialized earlier
		thread.start();
		// Starts the thread
	}
	
	private synchronized void stop() {
		// Method to stop the thread
		if(!running)
			return;
		// If the thread is already not running there's no need to stop it again so it will exit out
	
		running = false;
		try {
			thread.join();
			// Joins all the threads together and waits for them to die
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
		
	}
	
	public void run() {
		// This method is where the game loop is held
		// This method is run over and over again as it is part of the Runnable class
		init();
		// Calls the init method to run over and over again
		
		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		
		while(running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			
			if(delta >= 1) {
				tick();
				render();
				ticks++;
				delta--;
			}
			
			if(timer >= 1000000000) {
				System.out.println("Ticks and Frames: " + ticks);
				ticks = 0;
				timer = 0;
			}
			// Game loop ^^
			}
		stop();
		// Stops the thread
	}
	
	private void tick() {
		// Method that holds variables which need to be checked and updated over and over again
		if(State == STATE.GAME) {
			// Checks if the State of the game is the actual gameplay
		p.tick();
		c.tick();
		// Calls the tick methods in the player and controller class
		}
		
		if(enemy_killed >= enemy_count)
			// Checks to see if there are more enemies killed than there are on the screen
		{
			enemy_count += 2;
			// Adds two to the enemy count
			enemy_killed = 0;
			// Sets enemy killed to 0
			c.createEnemy(enemy_count);
		}
		
		if(HEALTH <= 0)
			State = STATE.GAMEOVER;
		// When the health is less than 0, the state is switched to the game over state which displays game over on the screen
		
	}
	
	private void render() {
		// Render method draws everything on the screen
		BufferStrategy bs = this.getBufferStrategy();
		// BufferStrategy creates buffers behind an image in which the image is loaded before displaying on the screen
		
		if(bs == null) {
			createBufferStrategy(3);
			// Creates 3 buffers behind the actual image
			return;	
		}
		
		Graphics g = bs.getDrawGraphics();
		// Creates a graphics variable that will draw graphics to the screen
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
		// Draws an image to the screen
		
		g.drawImage(background,0,0,null);
		
		if(State == STATE.GAME) {
			// Checks if the state is currently the game state
		p.render(g);
		c.render(g);
		// Renders the player and the controller class if the state is in the game state
		
		g.setColor(Color.gray);
		// Sets the color of the rectangle that will be drawn to gray
		g.fillRect(5, 5, 200, 50);
		// Makes a rectangle that's filled in gray
		
		g.setColor(Color.green);
		g.fillRect(5, 5, HEALTH, 50);
		
		g.setColor(Color.white);
		g.drawRect(5, 5, 200, 50);
		// Makes a rectangle that's outlined white
		// All these drawings make the health bar
		
		}else if(State == STATE.MENU) {
			menu.render(g);
			// Renders the menu class if the state is menu
		}else if(State == STATE.GAMEOVER) {
			go.render(g);
			// Renders the game over class if the state is gameover
		}
		
		g.dispose();
		// Disposes of the graphics object after it has been used
		bs.show();
		// Shows the buffers on the screen
	}
	
	public void keyPressed(KeyEvent e) {
		// Method for the KeyListener class which checks to see if the key is pressed down
		int key = e.getKeyCode();
		// The key integer will get the key code of the key being pressed
		
		if(State == STATE.GAME) {
			// Will only run this if the state is the game state
		if(key == KeyEvent.VK_RIGHT) {
			p.setVelX(5);
			// Checks if the right button is being pressed and if it is, the x velocity is added by 5 which moves the player right
		} else if (key == KeyEvent.VK_LEFT) {
			p.setVelX(-5);
			// Checks if the left button is being pressed and if it is, the x velocity is subtracted by 5 which moves the player left
		} else if (key == KeyEvent.VK_DOWN) {
			p.setVelY(5);
			// Checks if the up button is being pressed and if it is, the y velocity is subtracted by 5 which moves the player down
		} else if (key == KeyEvent.VK_UP) {
			p.setVelY(-5);
			// Checks if the up button is being pressed and if it is, the y velocity is added by 5 which moves the player up
		} else if(key == KeyEvent.VK_SPACE && !shooting) {
			shooting = true;
			c.addEntity(new Bullet(p.getX(), p.getY(), tex, this));
			// Checks if the space bar is being pressed and if it is, shooting is set to true and a bullet is spawned on the screen
		}
		}
	}
	public void keyReleased(KeyEvent e) {
		// Key Released checks to see what happens when the key is released
int key = e.getKeyCode();
		
		if(key == KeyEvent.VK_RIGHT) {
			p.setVelX(0);
		} else if (key == KeyEvent.VK_LEFT) {
			p.setVelX(0);
		} else if (key == KeyEvent.VK_DOWN) {
			p.setVelY(0);
		} else if (key == KeyEvent.VK_UP) {
			p.setVelY(0);
		}else if(key == KeyEvent.VK_SPACE) {
			shooting = false;
		}
	}
	
	public static void main(String args[]) {
		Game game = new Game();
		
		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		// Sets the preferred size of the JFrame
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		// Sets the maximum size of the JFrame
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		// Sets the minimum size of the JFrame
	
		JFrame frame = new JFrame(game.TITLE);
		// Creates the JFrame and makes the title the title variable
		frame.add(game);
		// Adds the game object to the JFrame
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Closes the JFrame when the x button is clicked
		frame.setResizable(false);
		// Doesn't allow the window to be resized
		frame.setVisible(true);
		// Makes the window visible
		
		game.start();
	}
	
	public BufferedImage getSpriteSheet() {
		return spriteSheet;
	}

	public int getEnemy_count() {
		return enemy_count;
	}

	public void setEnemy_count(int enemy_count) {
		this.enemy_count = enemy_count;
	}

	public int getEnemy_killed() {
		return enemy_killed;
	}

	public void setEnemy_killed(int enemy_killed) {
		this.enemy_killed = enemy_killed;
	}
	
	// Setters and Getters to use private variables in other classes
	
}
