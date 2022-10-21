package com.nicolasstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.nicolasstudios.main.Game;
import com.nicolasstudios.world.Camera;
import com.nicolasstudios.world.World;

public class WeaponInHand extends Entity{
	
	public static boolean isShooting;
	protected boolean blockShoot = false;
	protected int cadency = 0, shootFrames = 0;

	public WeaponInHand(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		// TODO Auto-generated constructor stub
	}
	
	public void shoot(int px, int py, int dx, int dy) {
		BulletShoot bullet = new BulletShoot(Game.player.getX()+px, Game.player.getY()+py, 3, 3, null, dx, dy);
		Game.bullets.add(bullet);
		Game.player.ammo--;
	}
	
	public void tick(){
		if(isShooting) {
			isShooting = false;
			
		if(Game.player.hasGun && Game.player.ammo > 0 && !blockShoot) {
			blockShoot = true;
			int dx = 0;
			int dy = 0;
			int px = 0;
			int py = 0;
			if(Game.player.dir == Game.player.right_dir) {
				
				shoot(21, 4, 1, 0);
			}
			else if(Game.player.dir == Game.player.left_dir){
				shoot(-10, 4, -1, 0);
			}
			else if(Game.player.dir == Game.player.up_dir) {
				shoot(6, -2, 0, -1);
			}
			else if(Game.player.dir == Game.player.down_dir) {
				
				shoot(4, 14, 0, 1);
			}
			
			
			
		}
		}
		
		shootFrames++;
		if(shootFrames >= cadency) {
			shootFrames = 0;
			blockShoot = false;
		}
	}
	
	public void render(Graphics g) {
		if(!Game.player.isDamaged) {
			if(Game.player.dir == Game.player.right_dir) {
			g.drawImage(Entity.GUN_PISTOL_RIGHT, Game.player.getX() + 10 - Camera.x, Game.player.getY() - (Game.player.alturaCabeca-1) - Camera.y, null);
			
			}
			else if(Game.player.dir == Game.player.left_dir) {
					g.drawImage(Entity.GUN_PISTOL_LEFT, Game.player.getX() - 12 - Camera.x, Game.player.getY() - (Game.player.alturaCabeca -1) - Camera.y, null);
					
			}
			else if(Game.player.dir == Game.player.up_dir) {
					g.drawImage(Entity.GUN_PISTOL_TOP, Game.player.getX() - 1 - Camera.x, Game.player.getY() - (Game.player.alturaCabeca+3) - Camera.y, null);
					
			}
			else if(Game.player.dir == Game.player.down_dir) {
					g.drawImage(Entity.GUN_PISTOL_DOWN, Game.player.getX() - 2 - Camera.x, Game.player.getY() + 2 - Camera.y, null);
					
			}
			}
			else {
				if(Game.player.dir == Game.player.right_dir) {
					g.drawImage(Entity.GUN_PISTOL_RIGHT_DAMAGE, Game.player.getX() + 9 - Camera.x, Game.player.getY() - Camera.y, null);
				}
				if(Game.player.dir == Game.player.left_dir) {
					g.drawImage(Entity.GUN_PISTOL_LEFT_DAMAGE, Game.player.getX() - 12 - Camera.x, Game.player.getY() - Camera.y, null);
				}
				if(Game.player.dir == Game.player.up_dir) {
					g.drawImage(Entity.GUN_PISTOL_TOP_DAMAGE, Game.player.getX() - 1 - Camera.x, Game.player.getY() - 4 - Camera.y, null);
				}
				if(Game.player.dir == Game.player.down_dir) {
					g.drawImage(Entity.GUN_PISTOL_DOWN_DAMAGE, Game.player.getX() - 2 - Camera.x, Game.player.getY() - Camera.y, null);
				}
				}
	}

}
