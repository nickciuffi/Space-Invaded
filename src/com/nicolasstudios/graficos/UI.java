package com.nicolasstudios.graficos;

import java.awt.Color;
import java.awt.Graphics;

import com.nicolasstudios.entities.Entity;
import com.nicolasstudios.entities.Player;
import com.nicolasstudios.main.Game;
import com.nicolasstudios.world.Camera;

public class UI {

	public void render(Graphics g) {
		g.setColor(Color.red);
		g.fillRect((int)(Game.player.x-2-Camera.x), (int)(Game.player.y+18-Camera.y), 17, 5);

		g.setColor(Color.green);
		g.fillRect((int)(Game.player.x-2-Camera.x), (int)(Game.player.y+18-Camera.y), (int)((Game.player.life/Player.maxLife)*17), 5);
		
		g.drawImage(Entity.BLOCK_INDICATOR, Game.player.getX() + (50*Game.player.sideBlocksX) - Camera.x, Game.player.getY() + (50*Game.player.sideBlocksY) - Camera.y, null);
		
	}
}
