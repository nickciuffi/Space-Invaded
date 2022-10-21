package com.nicolasstudios.world;

import java.awt.image.BufferedImage;

public class WallTile extends Tiles{
	
	

	public WallTile(int x, int y, BufferedImage sprite, boolean protect) {
		super(x, y, sprite, protect);
		this.life = 5;
	}
}
