package com.nicolasstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.nicolasstudios.main.Game;
import com.nicolasstudios.world.Camera;

public class ShotGun extends WeaponInHand{
	public ShotGun(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		cadency = 50;
		// TODO Auto-generated constructor stub
	}
	
	public void shoot(int px, int py, int dx, int dy) {
		
		if(Game.player.dir == Game.player.left_dir || Game.player.dir == Game.player.right_dir) {
		BulletShoot bullet = new BulletShoot(Game.player.getX()+px, Game.player.getY()+py, 3, 3, null, dx, dy);
		Game.bullets.add(bullet);
		bullet = new BulletShoot(Game.player.getX()+px, Game.player.getY()+py, 3, 3, null, dx, dy+0.3);
		Game.bullets.add(bullet);
		bullet = new BulletShoot(Game.player.getX()+px, Game.player.getY()+py, 3, 3, null, dx, dy-0.3);
		Game.bullets.add(bullet);
		}
		else {
			BulletShoot bullet = new BulletShoot(Game.player.getX()+px, Game.player.getY()+py, 3, 3, null, dx, dy);
			Game.bullets.add(bullet);
			bullet = new BulletShoot(Game.player.getX()+px, Game.player.getY()+py, 3, 3, null, dx+0.3, dy);
			Game.bullets.add(bullet);
			bullet = new BulletShoot(Game.player.getX()+px, Game.player.getY()+py, 3, 3, null, dx-0.3, dy);
			Game.bullets.add(bullet);
		}
		Game.player.ammo-=2;
	}
	
	public void render(Graphics g) {
		if(!Game.player.isDamaged) {
			if(Game.player.dir == Game.player.right_dir) {
			g.drawImage(Entity.GUN_SHOTGUN_RIGHT, Game.player.getX() + 10 - Camera.x, Game.player.getY() - (Game.player.alturaCabeca-1) - Camera.y, null);
			
			}
			else if(Game.player.dir == Game.player.left_dir) {
					g.drawImage(Entity.GUN_SHOTGUN_LEFT, Game.player.getX() - 12 - Camera.x, Game.player.getY() - (Game.player.alturaCabeca -1) - Camera.y, null);
					
			}
			else if(Game.player.dir == Game.player.up_dir) {
					g.drawImage(Entity.GUN_SHOTGUN_TOP, Game.player.getX() - 1 - Camera.x, Game.player.getY() - (Game.player.alturaCabeca+3) - Camera.y, null);
					
			}
			else if(Game.player.dir == Game.player.down_dir) {
					g.drawImage(Entity.GUN_SHOTGUN_DOWN, Game.player.getX() - 2 - Camera.x, Game.player.getY() + 2 - Camera.y, null);
					
			}
			}
			else {
				if(Game.player.dir == Game.player.right_dir) {
					g.drawImage(Entity.GUN_SHOTGUN_RIGHT_DAMAGE, Game.player.getX() + 9 - Camera.x, Game.player.getY() - Camera.y, null);
				}
				if(Game.player.dir == Game.player.left_dir) {
					g.drawImage(Entity.GUN_SHOTGUN_LEFT_DAMAGE, Game.player.getX() - 12 - Camera.x, Game.player.getY() - Camera.y, null);
				}
				if(Game.player.dir == Game.player.up_dir) {
					g.drawImage(Entity.GUN_SHOTGUN_TOP_DAMAGE, Game.player.getX() - 1 - Camera.x, Game.player.getY() - 4 - Camera.y, null);
				}
				if(Game.player.dir == Game.player.down_dir) {
					g.drawImage(Entity.GUN_SHOTGUN_DOWN_DAMAGE, Game.player.getX() - 2 - Camera.x, Game.player.getY() - Camera.y, null);
				}
				}
	}
}
