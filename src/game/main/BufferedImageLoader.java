package game.main;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BufferedImageLoader {

	private BufferedImage image;
	// Initializing BufferedImage variable
	
	public BufferedImage loadImage(String path) throws IOException {
		// BufferedImage method which takes a string as a parameter
		image = ImageIO.read(getClass().getResource(path));
		// Reads the image at the path entered in the parameter of the method
		return image;
		// Returns the image it has read
	}
	
}
