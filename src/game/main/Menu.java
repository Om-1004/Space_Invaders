package game.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Graphics2D;

public class Menu {
	
	public Rectangle playButton = new Rectangle(Game.WIDTH/2 + 120, 150, 100, 50);
	public Rectangle quitButton = new Rectangle(Game.WIDTH/2 + 120, 250, 100, 50);
	// Creates a rectangle to represent the buttons in the menu

	public void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		// Allows us to draw 2D graphics
		
		Font fnt0 = new Font("arial", Font.BOLD, 50);
		// Makes a font that is Arial, bold, and sets the size to 50
		g.setFont(fnt0);
		// Sets that to the current font
		g.setColor(Color.white);
		// Sets the color of the text to white
		g.drawString("SPACE INVADERS", Game.WIDTH/2-50, 100);
	
		Font fnt1 = new Font("arial", Font.BOLD, 30);
		g.setFont(fnt1);
		g.drawString("Play", playButton.x + 19, playButton.y + 30);
		g2d.draw(playButton);
		g.drawString("Quit", quitButton.x + 19, quitButton.y + 30);
		g2d.draw(quitButton);
	}
	
}
