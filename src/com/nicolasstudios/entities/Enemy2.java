package com.nicolasstudios.entities;

import java.awt.image.BufferedImage;

import com.nicolasstudios.main.Game;

public class Enemy2 extends Enemy{
	
	
	public Enemy2(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.life = 20;
		this.speed = 1;
		this.usesAStar = false;
		dano = 20;
		this.sprites = new BufferedImage[4];
		for(int i = 0; i<this.sprites.length; i++) {
			sprites[i] = Game.spritesheet.getSprite(32 + i*16, 64+16, 16, 16);
			}
	}
	
}
