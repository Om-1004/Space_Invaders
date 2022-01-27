package game.main;

import java.awt.Graphics;
import java.awt.Rectangle;

import game.main.classes.EntityA;

public class Bullet extends GameObject implements EntityA {
	
	private Textures tex;
	private Game game;
	
	public Bullet(double x, double y, Textures tex, Game game) {
		super(x,y);
		// Super refers to the parent voice
		this.tex = tex;
		this.game = game;
		// Sets the variables outside the method equal to the methods inside the method
	}
	
	public void tick() {
		y -= 10;
		
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32);
	}
	
	public void render(Graphics g) {
		g.drawImage(tex.missle, (int)x, (int)y, null);
	}
	
	public double getY() {
		return y;
	}
	
	public double getX() {
		return x;
	}
	
	// Setters and getters
	
}
