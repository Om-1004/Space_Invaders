package game.main;

import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;

public class GameOver {

	public void render(Graphics g) {
		Font fnt0 = new Font("arial", Font.BOLD, 50);
		g.setFont(fnt0);
		g.setColor(Color.white);
		g.drawString("GAME OVER", Game.WIDTH/2, Game.HEIGHT/2);
	}
	
}
