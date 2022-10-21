package com.nicolasstudios.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.nicolasstudios.main.Game;

public class Tiles {

	public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprite(0, 0, 16, 16);
	public static BufferedImage TILE_WALL = Game.spritesheet.getSprite(16, 0, 16, 16);
	public static BufferedImage TILE_DOOR = Game.spritesheet.getSprite(0, 48, 16, 16);
	public static BufferedImage TILE_SPACE = Game.spritesheet.getSprite(16, 16, 16, 16);
	public static BufferedImage TILE_BLACK = Game.spritesheet.getSprite(16, 32, 16, 16);
	
	private BufferedImage sprite;
	private int x, y;
	public boolean protect = false;
	
	public int life;
	
	public Tiles(int x, int y, BufferedImage sprite, boolean protect) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		this.protect = protect;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y,null);
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
}
