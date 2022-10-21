package com.nicolasstudios.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import com.nicolasstudios.main.Game;
import com.nicolasstudios.world.Camera;
import com.nicolasstudios.world.Node;
import com.nicolasstudios.world.Vector2i;

public class Entity {
	
	public static BufferedImage LIFEPACK_EN = Game.spritesheet.getSprite(96, 0, 16, 16);
	public static BufferedImage ABLEBLOCKS_EN = Game.spritesheet.getSprite(16, 32, 16, 16);
	public static BufferedImage WEAPON_PISTOL_EN = Game.spritesheet.getSprite(112, 0, 16, 16);
	public static BufferedImage WEAPON_SUBMACHINE_EN = Game.spritesheet.getSprite(96, 128, 16, 16);
	public static BufferedImage WEAPON_SHOTGUN_EN = Game.spritesheet.getSprite(96, 96, 16, 16);
	public static BufferedImage BULLET_EN = Game.spritesheet.getSprite(128, 0, 16, 16);
	public static BufferedImage ENEMY_EN = Game.spritesheet.getSprite(32, 64, 16, 16);
	public static BufferedImage ENEMY2_EN = Game.spritesheet.getSprite(32, 64+16, 16, 16);
	public static BufferedImage ENEMY_FEEFBACK = Game.spritesheet.getSprite(0, 32, 16, 16);
	public static BufferedImage BLOCK_INDICATOR = Game.spritesheet.getSprite(144, 0, 16, 16);
	public static BufferedImage ENEMY_RIGHT_GUN = Game.spritesheet.getSprite(96, 48, 16, 16);
	public static BufferedImage ENEMY_LEFT_GUN = Game.spritesheet.getSprite(96+16, 48, 16, 16);
	//guns
	//shotgun
	public static BufferedImage GUN_SHOTGUN_LEFT = Game.spritesheet.getSprite(128, 96, 16, 16);
	public static BufferedImage GUN_SHOTGUN_RIGHT = Game.spritesheet.getSprite(128-16, 96, 16, 16);
	public static BufferedImage GUN_SHOTGUN_LEFT_DAMAGE = Game.spritesheet.getSprite(128, 96+16, 16, 16);
	public static BufferedImage GUN_SHOTGUN_RIGHT_DAMAGE = Game.spritesheet.getSprite(128-16, 96+16, 16, 16);
	public static BufferedImage GUN_SHOTGUN_TOP = Game.spritesheet.getSprite(128+16, 96, 16, 16);
	public static BufferedImage GUN_SHOTGUN_DOWN = Game.spritesheet.getSprite(96, 112, 16, 16);
	public static BufferedImage GUN_SHOTGUN_TOP_DAMAGE = Game.spritesheet.getSprite(112+32, 16+16, 16, 16);
	public static BufferedImage GUN_SHOTGUN_DOWN_DAMAGE = Game.spritesheet.getSprite(96+32, 16+16, 16, 16);
	//pistol
	public static BufferedImage GUN_PISTOL_LEFT = Game.spritesheet.getSprite(112, 16, 16, 16);
	public static BufferedImage GUN_PISTOL_RIGHT = Game.spritesheet.getSprite(96, 16, 16, 16);
	public static BufferedImage GUN_PISTOL_LEFT_DAMAGE = Game.spritesheet.getSprite(112, 16+16, 16, 16);
	public static BufferedImage GUN_PISTOL_RIGHT_DAMAGE = Game.spritesheet.getSprite(96, 16+16, 16, 16);
	public static BufferedImage GUN_PISTOL_TOP = Game.spritesheet.getSprite(112+32, 16, 16, 16);
	public static BufferedImage GUN_PISTOL_DOWN = Game.spritesheet.getSprite(96+32, 16, 16, 16);
	public static BufferedImage GUN_PISTOL_TOP_DAMAGE = Game.spritesheet.getSprite(112+32, 16+16, 16, 16);
	public static BufferedImage GUN_PISTOL_DOWN_DAMAGE = Game.spritesheet.getSprite(96+32, 16+16, 16, 16);
	//submachine
	public static BufferedImage GUN_SUBMACHINE_LEFT = Game.spritesheet.getSprite(128, 128, 16, 16);
	public static BufferedImage GUN_SUBMACHINE_RIGHT = Game.spritesheet.getSprite(112, 128, 16, 16);
	public static BufferedImage GUN_SUBMACHINE_TOP = Game.spritesheet.getSprite(112+32, 128, 16, 16);
	public static BufferedImage GUN_SUBMACHINE_DOWN = Game.spritesheet.getSprite(96, 144, 16, 16);
	public static BufferedImage GUN_SUBMACHINE_LEFT_DAMAGE = Game.spritesheet.getSprite(128, 144, 16, 16);
	public static BufferedImage GUN_SUBMACHINE_RIGHT_DAMAGE = Game.spritesheet.getSprite(128-16, 144, 16, 16);
	public static BufferedImage GUN_SUBMACHINE_TOP_DAMAGE = Game.spritesheet.getSprite(112+32, 16+16, 16, 16);
	public static BufferedImage GUN_SUBMACHINE_DOWN_DAMAGE = Game.spritesheet.getSprite(96+32, 16+16, 16, 16);

	public double x, y;
	protected int width, height;
	protected double speed = 0.8;
	
	
	protected List<Node> path;
	
	private BufferedImage sprite;
	
	public int maskx, masky, mheight, mwidth;
	
	
	public Entity(int x, int y, int width, int height, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		
		this.maskx = 0;
		this.masky = 0;
		this.mwidth = width;
		this.mheight = height;
	}
	
	public void setMask(int maskx, int masky, int mwidth, int mheight) {
		this.maskx = maskx;
		this.masky = masky;
		this.mwidth = mwidth;
		this.mheight = mheight;
	}
	
	public int getX() {
		return (int)this.x;
	}
	public int getY() {
		return (int)this.y;
	}
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	public void setY(int newY) {
		this.y = newY;
	}
	
	public void tick() {
		
	}
	public boolean isColidding(int xnext, int ynext) {
		Rectangle enemyCurrent = new Rectangle(xnext + maskx, ynext + masky, mwidth, mheight);
		
	for(int i = 0; i < Game.enemies.size(); i++) {
			Enemy e = Game.enemies.get(i);
			if(e == this) {
				continue;
			}
			Rectangle targetEnemy = new Rectangle(e.getX() + maskx, e.getY() + masky, mwidth, mheight);
			if(enemyCurrent.intersects(targetEnemy)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void followPath(List<Node> path) {
		if(path != null) {
			if(path.size() > 0) {
				Vector2i target = path.get(path.size() - 1).tile;
				if(x < target.x * 16 && !isColidding(this.getX() + (int)this.speed, getY())) {
					x+=speed;
				}
				else if(x > target.x * 16 && !isColidding(this.getX() - (int)this.speed, getY())) {
					x-=speed;
				}
				if(y < target.y * 16 && !isColidding(this.getX(), getY() + (int)this.speed)) {
					y+=speed;
				}
				else if(y > target.y * 16 && !isColidding(this.getX(), getY() - (int)this.speed)) {
					y-=speed;
				}
				if(x == target.x * 16 && y == target.y * 16) {
					path.remove(path.size()-1);
					
				}
			}
		}
	}
	
	public static boolean isColidding(Entity e1, Entity e2) {
		Rectangle e1Mask = new Rectangle(e1.getX() + e1.maskx, e1.getY() + e1.masky, e1.mwidth, e1.mheight);
		Rectangle e2Mask = new Rectangle(e2.getX() + e2.maskx, e2.getY() + e2.masky, e2.mwidth, e2.mheight);
		
		return e1Mask.intersects(e2Mask);
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
		//g.setColor(Color.blue);
		//g.fillRect(this.getX() + maskx - Camera.x,this.getY() + masky - Camera.y, mwidth, mheight);
	}
}
