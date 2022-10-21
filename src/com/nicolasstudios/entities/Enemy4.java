package com.nicolasstudios.entities;

import java.awt.image.BufferedImage;

import com.nicolasstudios.main.Game;

public class Enemy4 extends Enemy{
	public Enemy4(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.life = 10;
		this.speed = 1;
		this.usesAStar = true;
		this.distancySleep = 110;
		dano = 15;
		this.sprites = new BufferedImage[4];
		for(int i = 0; i<this.sprites.length; i++) {
			sprites[i] = Game.spritesheet.getSprite(32 + i*16, 96, 16, 16);
			}
	}
}
