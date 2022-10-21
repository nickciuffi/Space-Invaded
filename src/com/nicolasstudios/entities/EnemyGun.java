package com.nicolasstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.nicolasstudios.main.Game;
import com.nicolasstudios.world.Camera;



public class EnemyGun extends Entity{
	
	private String dir = "RIGHT";
	Enemy3 en;
	
	public EnemyGun(int x, int y, Enemy3 en, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.en = en;
		// TODO Auto-generated constructor stub
	}
	
	public void tick() {
		this.x = en.getX();
		this.y = en.getY();
		this.dir = en.dir;
		
		
		
	}
	public void render(Graphics g) {
		if(dir == "RIGHT") {
		g.drawImage(Entity.ENEMY_RIGHT_GUN, (int)(x)-Camera.x, (int)y-Camera.y, null);
		}
		else {
			g.drawImage(Entity.ENEMY_LEFT_GUN, (int)(x-Camera.x - 5), (int)y-Camera.y, null);
		}
	}
	
	


}
