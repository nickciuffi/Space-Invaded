package com.nicolasstudios.entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.nicolasstudios.main.Game;
import com.nicolasstudios.world.Camera;
import com.nicolasstudios.world.FloorTile;
import com.nicolasstudios.world.Tiles;
import com.nicolasstudios.world.WallTile;
import com.nicolasstudios.world.World;

public class BulletShoot extends Entity{
	
	public double dx, dy;
	public double spd = 4;
	private int life = 20, curLife = 0;
	private double angx = 0, angy = 0;
	
	
	public BulletShoot(int x, int y, int width, int height, BufferedImage sprite, double dx, double dy ) {
		super(x, y, width, height, sprite);
		this.dx = dx;
		this.dy = dy;
		//this.angx = angx;
		//this.angy = angy;
		// TODO Auto-generated constructor stub
	}

	public void tick() {
		x+=dx*spd+angx;
		y+=dy*spd+angy;
		curLife++;
		if(curLife == life) {
			Game.bullets.remove(this);
			return;
		}
		ColideTile();
	}
	private void ColideTile(){
		for(int i = 0; i < World.tiles.length; i++) {
			Tiles tile = World.tiles[i];
			if(tile instanceof WallTile) {
				if(Entity.isColidding(this, new Entity(tile.getX(), tile.getY(), 16, 16, null))) {
					Game.bullets.remove(this);
					
					if(!isBorder(i)) {
						if(!tile.protect || Game.player.allowBreakProtect) {
					tile.life-= Game.rand.nextInt(5);
					if(tile.life <= 0) {
						World.tiles[i] = new FloorTile(tile.getX(), tile.getY(), Tiles.TILE_FLOOR);
						Game.player.blocksCollected++;
					}
				}
				}
				}
			}
		}
		
		
	}
	private boolean isBorder(int indexTile) {
		Tiles tile = World.tiles[indexTile];
		if(tile.getX() == 0 || tile.getX()	== (World.WIDTH * World.TILE_SIZE)-World.TILE_SIZE || tile.getY() == 0 || tile.getY() == (World.HEIGHT * World.TILE_SIZE)-World.TILE_SIZE) {
			return true;
			
		}
		return false;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.green);
		g.fillOval(this.getX()- Camera.x, this.getY() - Camera.y, width, height);
		
	}
	
}
