package game.main;

import java.awt.image.BufferedImage;

public class Textures {

	public BufferedImage[] player = new BufferedImage[3];
	// Makes a BufferedImage array and stores 3 BufferedImages in there since there are three photos for animation for the player in the spritesheet
	public BufferedImage missle;
	public BufferedImage enemy;
	
	private SpriteSheet ss = null;

	public Textures(Game game) {
		ss = new SpriteSheet(game.getSpriteSheet());
		
		getTextures();
	}
	
	private void getTextures() {
		player[0] = ss.grabImage(1, 1, 32, 32);
		player[1] = ss.grabImage(1,2,32,32);
		player[2] = ss.grabImage(1,3,32,32);
		
		missle = ss.grabImage(2, 1, 32, 32);
		
		enemy = ss.grabImage(3, 1, 32, 32);
		// Crops all the images for everything accordingly
	}
	
}
