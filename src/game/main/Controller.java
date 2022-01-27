package game.main;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

import game.main.classes.EntityA;
import game.main.classes.EntityB;

public class Controller {

	private LinkedList<EntityA> ea = new LinkedList<EntityA>();
	private LinkedList<EntityB> eb = new LinkedList<EntityB>();
	
	EntityA enta;
	EntityB entb;
	Textures tex;
	Random r = new Random();
	// Random is used to generate a random number
	// Creating a Random variable
	private Game game;
	
	public Controller(Textures tex, Game game) {
		// Constructor for the controller class and takes two parameters of classes in this project
		this.tex = tex;
		this.game = game;
	}
	
	public void createEnemy(int enemy_count) {
		// Method to create enemies
		for(int i=0; i < enemy_count; i++) {
			// For loop that runs as long as i is less than the amount of enemies on the screen
			addEntity(new Enemy(r.nextInt(640), -10, tex, this, game));
			// Calls the addEntity method which adds an entity to the screen
		}
	}
	
	public void tick() {
		// Tick method runs multiple times
		// Variables that need to constantly be checked are usually put in the tick method
		//A CLASS
		for(int i = 0; i < ea.size(); i++) {
			enta = ea.get(i);
			
			enta.tick();
		}
		
		//B CLASS
		for(int i = 0; i < eb.size(); i++) {
			entb = eb.get(i);
					
			entb.tick();
		}
	}
	
	public void render(Graphics g) {
		//A CLASS
		for(int i = 0; i < ea.size(); i++) {
			enta = ea.get(i);
			
			enta.render(g);
			// Calls the render method in the EntityA instance
		}
		
		//B CLASS
		for(int i = 0; i < eb.size(); i++) {
			entb = eb.get(i);
				
			entb.render(g);
		}
	}
	
	public void addEntity(EntityA block) {
		ea.add(block);
	}
	public void removeEntity(EntityA block) {
		ea.remove(block);
	}
	public void addEntity(EntityB block) {
		eb.add(block);
	}
	public void removeEntity(EntityB block) {
		eb.remove(block);
	}
	
	public LinkedList<EntityA> getEntityA(){
		return ea;
	}
	
	public LinkedList<EntityB> getEntityB(){
		return eb;
	}
	// Getter methods for the Entity A and B LinkedLists
	// Allows the LinkedLists to be used outside this class as they are private
	
}
