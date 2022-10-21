package com.nicolasstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.nicolasstudios.main.Game;
import com.nicolasstudios.world.Camera;

public class SubMachine extends WeaponInHand{

	public SubMachine(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		cadency = 10;
		// TODO Auto-generated constructor stub
	}
	
	public void render(Graphics g) {
	if(!Game.player.isDamaged) {
		if(Game.player.dir == Game.player.right_dir) {
		g.drawImage(Entity.GUN_SUBMACHINE_RIGHT, Game.player.getX() + 10 - Camera.x, Game.player.getY() - (Game.player.alturaCabeca-1) - Camera.y, null);
		
		}
		else if(Game.player.dir == Game.player.left_dir) {
				g.drawImage(Entity.GUN_SUBMACHINE_LEFT, Game.player.getX() - 12 - Camera.x, Game.player.getY() - (Game.player.alturaCabeca -1) - Camera.y, null);
				
		}
		else if(Game.player.dir == Game.player.up_dir) {
				g.drawImage(Entity.GUN_SUBMACHINE_TOP, Game.player.getX() - 1 - Camera.x, Game.player.getY() - (Game.player.alturaCabeca+3) - Camera.y, null);
				
		}
		else if(Game.player.dir == Game.player.down_dir) {
				g.drawImage(Entity.GUN_SUBMACHINE_DOWN, Game.player.getX() - 2 - Camera.x, Game.player.getY() + 2 - Camera.y, null);
				
		}
		}
		else {
			if(Game.player.dir == Game.player.right_dir) {
				g.drawImage(Entity.GUN_SUBMACHINE_RIGHT_DAMAGE, Game.player.getX() + 9 - Camera.x, Game.player.getY() - Camera.y, null);
			}
			if(Game.player.dir == Game.player.left_dir) {
				g.drawImage(Entity.GUN_SUBMACHINE_LEFT_DAMAGE, Game.player.getX() - 12 - Camera.x, Game.player.getY() - Camera.y, null);
			}
			if(Game.player.dir == Game.player.up_dir) {
				g.drawImage(Entity.GUN_SUBMACHINE_TOP_DAMAGE, Game.player.getX() - 1 - Camera.x, Game.player.getY() - 4 - Camera.y, null);
			}
			if(Game.player.dir == Game.player.down_dir) {
				g.drawImage(Entity.GUN_SUBMACHINE_DOWN_DAMAGE, Game.player.getX() - 2 - Camera.x, Game.player.getY() - Camera.y, null);
			}
			}
	
}
}
