package com.nicolasstudios.entities;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import com.nicolasstudios.main.Game;
import com.nicolasstudios.main.Sound;
import com.nicolasstudios.world.AStar;
import com.nicolasstudios.world.Camera;
import com.nicolasstudios.world.Vector2i;
import com.nicolasstudios.world.World;

public class Enemy extends Entity{
	
	protected double dano = 5;
	
	protected int distancySleep = 95;
	protected int distancyFirstSleep = 95;
	
	protected boolean firstAwake = false;
	
	protected boolean usesAStar = false;
	protected int maskx = 0, masky = 0, maskh = 16, maskw = 11;
	
	protected int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	protected boolean moved = false;
	
	private boolean isDamaged = false;
	private int damageFrames = 10, damageCurrent = 0;
	public boolean alternativeWalk = false;
	
	public double distX, distY, distFinal;
	
	
	public int life = 5;
	
	private boolean soundSleep = false;
	private int sleepFrames = 0, sleepFramesMax = 80;
	
	protected BufferedImage[] sprites;
	
	public Enemy(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, null);
		sprites = new BufferedImage[4];
		for(int i = 0; i<sprites.length; i++) {
		sprites[i] = Game.spritesheet.getSprite(32 + i*16, 64, 16, 16);
		}
		
		// TODO Auto-generated constructor stub
	}
	public void tick() {
		
		if(verifySleep()) {
		moved = false;
		if(!isColiddingWithPlayer()) {
		if(!usesAStar && !alternativeWalk) {
		if((int)x < Game.player.getX() && World.isFree((int)(x + speed), this.getY(), maskw, maskh-1)
				&& !isColidding((int)(x+speed), this.getY())) {
			//right
			x+=speed;
			moved = true;
		}
		else if((int)x > Game.player.getX() && World.isFree((int)(x-speed), this.getY(), maskw, maskh-1) 
				&& !isColidding((int)(x-speed), this.getY())) {
			//left
			x-=speed;
			moved = true;
		}
		if((int)y > Game.player.y && !isColidding(this.getX(), (int)(y-speed))) {
			if(World.isFree(this.getX(), (int)(y-speed), maskw, maskh)) {
			//up
			y-=speed;
			moved = true;
			}
			else {
			//	this.setY((int)((((int)(y-speed))/16)*16+maskh));
			}
		}
		else if((int)y < Game.player.y && !isColidding(this.getX(), (int)(y+speed))) {
			if(World.isFree(this.getX(), (int)(y+speed), maskw, maskh)) {
			//down
			y+=speed;
			moved = true;
			}
			else {
				this.setY((int)((((int)(y+speed))/16)*16));
			}
		}
		}
		else if(!alternativeWalk){
			if(path == null || path.size() == 0) {
				Vector2i start = new Vector2i((int)(x/16), (int)(y/16));
				Vector2i end = new Vector2i((int)(Game.player.x/16), (int)(Game.player.y/16));
				path = AStar.findPath(Game.world, start, end);
			}
			followPath(path);
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
			
		}
		
		else {
			
		if(!soundSleep && Game.hasSounds) {
			
			int aiNum = Game.rand.nextInt(3);
		if(aiNum == 0) {
			Game.sound.ai1.play();
			}
		else if(aiNum == 1) {
			Game.sound.ai2.play();
		}
		else if(aiNum == 2) {
			Game.sound.ai3.play();
		}
			soundSleep = true;
			}
			
		if(Game.rand.nextInt(100) < 30) {
			Game.player.life-= Game.rand.nextInt((int)dano);
			Game.player.isDamaged = true;
		}
			
			
			}
		if(soundSleep) {
		sleepFrames++;
		if(sleepFrames >= sleepFramesMax) {
			soundSleep = false;
			sleepFrames = 0;
		}
		}
		}
		
		
		collidingBullet();
		
		if(life <= 0) {
			destroySelf();
		}
		if(isDamaged) {
			this.damageCurrent++;
			if(damageCurrent == damageFrames) {
				damageCurrent = 0;
				isDamaged = false;
			}
		}
		}
		
		
//}
	
	public boolean verifySleep() {
		if(Game.player.getX() > getX()) {
			distX = Game.player.getX() - this.getX();
			}
		else {
			distX =	this.getX() - Game.player.getX();
			}
		if(Game.player.getY() > getY()) {
			distX = Game.player.getY() - this.getY();
			}
		else {
			distX =	this.getY() - Game.player.getY();
			}
		
		distFinal = (Math.pow(distX, 2)) + (Math.pow(distY, 2));
		distFinal = Math.sqrt(distFinal);
		if(!firstAwake) {
			if((int)distFinal < distancyFirstSleep) {
				firstAwake = true;
				return true;
				
			}
		}
		else {
		if((int)distFinal < distancySleep) {
			return true;
		}
		}
		
		return false;
	}
	
	public void destroySelf() {
		Game.entities.remove(this);
		Game.enemies.remove(this);
		if(this instanceof Enemy3) {
			for(int i = 0; i < Game.enemyGun.size(); i++) {
				EnemyGun gun = Game.enemyGun.get(i);
				if(gun.en == this) {
					Game.enemyGun.remove(i);
				}
			}
			
		}
		return;
		}
	
	public void collidingBullet(){
		for(int i = 0; i < Game.bullets.size(); i++) {
			Entity e = Game.bullets.get(i);
			if(e instanceof BulletShoot) {
				if(Entity.isColidding(this, e)) {
					Game.bullets.remove(i);
					isDamaged = true;
					
					life--;
					Sound.pimbada.play();
					return;
				}
			}
		}
	}
		
	public boolean isColiddingWithPlayer() {
		Rectangle enemyCurrent = new Rectangle(this.getX() + maskx+3, this.getY() + masky, maskw, maskh);
		Rectangle player = new Rectangle(Game.player.getX()-2, Game.player.getY()-1, 16, 17);
		return enemyCurrent.intersects(player);
	}
	
	
	
	public void render(Graphics g) {
		if(!isDamaged) {
			g.drawImage(sprites[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		}
		else {
			g.drawImage(Entity.ENEMY_FEEFBACK, this.getX() - Camera.x, this.getY() - Camera.y, null);
			
		}
		//g.setColor(Color.blue);
		//g.fillRect(this.getX() + maskx - Camera.x, this.getY() + masky - Camera.y, maskw, maskh);
	}

}
