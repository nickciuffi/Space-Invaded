package com.nicolasstudios.entities;

import java.awt.image.BufferedImage;

import com.nicolasstudios.main.Game;
import com.nicolasstudios.world.World;

public class Enemy3 extends Enemy{
	
	public String dir = "RIGHT";
	private boolean blockShoot = false;
	private int shootFrames = 0, cadency = 30;
	private int distToShoot = 80;
	
	
	public Enemy3(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		this.life = 5;
		alternativeWalk = true;
		distancyFirstSleep = 80;
		speed = 0.6;
		//cria arma
		Game.enemyGun.add(new EnemyGun((int)this.x, (int)this.y, this, 16, 16, Entity.ENEMY_RIGHT_GUN));
		this.sprites = new BufferedImage[4];
		
		for(int i = 0; i<this.sprites.length; i++) {
			sprites[i] = Game.spritesheet.getSprite(32 + i*16, 64+16, 16, 16);
			}
		
	}
	public void tick() {
		
		super.tick();
		if(verifySleep()) {
		if(Game.player.getX() < x) {
			dir = "LEFT";
		}
		else if(Game.player.getX() > x){
			dir = "RIGHT";
		}
		
		if(Game.player.getX() + distToShoot < this.x && World.isFree((int)(this.getX() - speed), getY(), maskw, maskh)) {
			this.x-=speed;
			moved = true;
		}
		else if(Game.player.getX() - distToShoot > this.x && World.isFree((int)(this.getX() + speed), getY(), maskw, maskh)) {
			x+=speed;
			moved = true;
		}
		if(Game.player.getY() - 10 >= y && World.isFree(this.getX(), (int)(getY() + speed), maskw, maskh)) {
			y+=speed;
			moved = true;
		}
		else if(Game.player.getY() + 6 <= y && World.isFree(this.getX(), (int)(getY() - speed), maskw, maskh)) {
			y-=speed;
			moved = true;
		}
		
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index > maxIndex) {
					index = 0;
				}
			}
		
		}
		//detecta dano no humano
		if(isColiddingWithPlayer()) {
		if(Game.rand.nextInt(100) < 30) {
			Game.player.life-= Game.rand.nextInt((int)dano);
			Game.player.isDamaged = true;
			}
		}
		
		if(!blockShoot && !moved) {
			blockShoot = true;
			if(dir == "RIGHT") {
				Game.bullets.add(new BulletShoot((int)(x + 14), (int)(y + 9), 3, 3, null, 1, 0));
			}
			else if(dir == "LEFT"){
				Game.bullets.add(new BulletShoot((int)(x), (int)(y + 9), 3, 3, null, -1, 0));
			}
			
			/*double angle = Math.atan2((Game.player.getX() - Camera.x) - (this.getX() - Camera.x) , (Game.player.getY() - Camera.y) - (this.getY() - Camera.y));
			System.out.println(angle);
			double dx = Math.cos(angle);
			double dy = Math.sin(angle);
			BulletShoot bullet = new BulletShoot((int)(x + 20), (int)(y + 8), 2, 2, null, 1, 1);
			Game.bullets.add(bullet);*/
		}
		else {
		shootFrames++;
		if(shootFrames >= cadency) {
			shootFrames = 0;
			blockShoot = false;
		}
		}
		}
	}
	
}
