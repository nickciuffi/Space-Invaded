package com.nicolasstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.nicolasstudios.main.Game;
import com.nicolasstudios.world.Camera;
import com.nicolasstudios.world.DoorTile;
import com.nicolasstudios.world.Tiles;
import com.nicolasstudios.world.World;

public class Player extends Entity{
	
	public boolean right, up, left, down;
	public int right_dir = 0, left_dir = 1, up_dir = 2, down_dir = 3;
	public int dir = right_dir;
	public double speed = 1.4;
	
	private int maskh = 16, maskw = 12;
	
	public double life = 100;
	public static int maxLife = 100;
	
	
	public int frames = 0, maxFrames = 5, index = 0, maxIndex = 3, alturaCabeca = 1;
	private boolean moved = false;
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	private BufferedImage[] upPlayer;
	private BufferedImage[] downPlayer;
	private BufferedImage damagePlayer;
	
	public boolean hasGun = false;
	public int gun = 0;
	
	public int ammo = 0;
	public int blocksCollected = 10;
	public boolean isDamaged = false;
	public int damageFrames = 0;
	
	public boolean mouseShoot = false;
	public int mx, my;
	public boolean venceu = false;
	
	private int sideDropX = 0, sideDropY = 0;
	
	public int sideBlocksX = 1, sideBlocksY = 0;
	public boolean allowBreakProtect = false;
	
	public Player(int x, int y, int width, int height, BufferedImage sprite) {
		super(x, y, width, height, sprite);
		
		rightPlayer = new BufferedImage[4];
		leftPlayer = new BufferedImage[4];
		upPlayer = new BufferedImage[4];
		downPlayer = new BufferedImage[4];
		damagePlayer = Game.spritesheet.getSprite(0, 16, 16, 16);
		
		for(int i = 0; i < 4; i++) {
		rightPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 0, 16, 16);
		}
		for(int i = 0; i < 4; i++) {
			leftPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 16, 16, 16);
			}
		for(int i = 0; i < 4; i++) {
			upPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 32, 16, 16);
			}
		for(int i = 0; i < 4; i++) {
			downPlayer[i] = Game.spritesheet.getSprite(32 + (i*16), 48, 16, 16);
			}
	}
	public void tick() {
		moved = false;
		if(right) {
			if(World.isFree((int)(x+speed), this.getY(), maskw, maskh-1)) {
			moved = true;
			dir = right_dir;
			sideDropX = -1;
			sideDropY = 0;
			sideBlocksX = 1;
			sideBlocksY = 0;
			x+=speed;
			}
			else if(this.getX() == (((int)(x+speed))/16)*16){
				this.setX(((((int)(x+speed))/16)*16) + World.TILE_SIZE - maskw);
			}
		}
		else if(left) {
			if(World.isFree((int)(x-speed), this.getY(), maskw, maskh-1)) {
			moved = true;
			dir = left_dir;
			sideDropX = 1;
			sideDropY = 0;
			sideBlocksX = -1;
			sideBlocksY = 0;
			x-=speed;
			}
			else {
				//this.setX((int)((((int)(x-speed))/16)*16+16));
			}
		}
		if(up) {
			if(World.isFree(this.getX(), (int)(y-speed), maskw, maskh)) {
			moved = true;
			dir = up_dir;
			sideDropX = 0;
			sideDropY = 1;
			sideBlocksX = 0;
			sideBlocksY = -1;
			y-=speed;
			}
			else {
				this.setY((int)((((int)(y-speed))/16)*16+16));
			}
		}
		else if(down) {
			if(World.isFree(this.getX(), (int)(y + speed), maskw, maskh)) {
			moved = true;
			dir = down_dir;
			sideDropX = 0;
			sideDropY = -1;
			sideBlocksX = 0;
			sideBlocksY = 1;
			y+=speed;
		}
		else {
			this.setY((int)((((int)(y+speed))/16)*16));
		}
		}
		if(moved) {
			frames++;
			if(frames == maxFrames) {
				frames = 0;
				index++;
				if(index == 2) {
					alturaCabeca = 0;
				}
				else {
					alturaCabeca = 1;
				}
				if(index > maxIndex) {
					index = 0;
				}
			}
		}
		else {
			index = 0;
			alturaCabeca = 1;
		}
		this.checkCollisionGun();
		checkCollisionDoor();
		checkCollisionBullet();
		checkCollisionEntity();
		if(isDamaged) {
			this.damageFrames++;
			if(this.damageFrames == 8) {
			this.damageFrames = 0;
			isDamaged = false;
			}
		}
		
		
		
		if(mouseShoot) {
			mouseShoot = false;
			int px = 8;
			int py = 8;
			if(hasGun && ammo > 0) {
				if(dir == right_dir) {
					px = 21;
					py = 4;
				}
				else if(dir == left_dir){
					px = -10;
					py = 4;
				}
				else if(dir == up_dir) {
					py = -2;
					px = 6;
				}
				else if(dir == down_dir) {
					px = 4;
					py = 14;
				}
				double angle = Math.atan2(my - (this.getY()+py - Camera.y) , mx - (this.getX()+px - Camera.x));
				
				double dx = Math.cos(angle);
				double dy = Math.sin(angle);
				
				
				BulletShoot bullet = new BulletShoot(this.getX()+px, this.getY()+py, 2, 2, null, dx, dy);
				Game.bullets.add(bullet);
				ammo--;
			}
		}
		
		if(life <= 0 ) {
			//Game over
			life = 0;
			Game.gameState = "GAME_OVER";
		}
		
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH*16 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT*16 - Game.HEIGHT);
		
		
	}
	
	public void checkCollisionEntity() {
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Bullet) {
				if(Entity.isColidding(this, atual)) {
					ammo+=10;
					
					Game.entities.remove(i);
				}
			}
			else if(atual instanceof AbleBlocks) {
				if(Entity.isColidding(this, atual)) {
					Game.blocksDisable = false;
					Game.player.blocksCollected+= 5;
					Game.entities.remove(i);
					allowBreakProtect = true;
				}
			}
			else if(atual instanceof LifePack) {
				if(Entity.isColidding(this, atual)) {
					life+=30;
					if(life >= 100) {
						life = 100;
						
					}
					Game.entities.remove(i);
				}
			}
		}
	}
	
	public void checkCollisionDoor() {
		for(int i = 0; i < World.tiles.length; i++) {
			Tiles tile = World.tiles[i];
			if(tile instanceof DoorTile) {
				if(Entity.isColidding(this, new Entity(tile.getX(), tile.getY(), 16, 16, null))) {
					if(venceu) {
						Game.nextLevel();
					}
					else {
						Game.askKey = true;
					}
				}
			}
		}
	}
	
	public void checkCollisionBullet() {
		for(int i = 0; i < Game.bullets.size(); i++) {
			Entity e = Game.bullets.get(i);
			if(e instanceof BulletShoot) {
				if(Entity.isColidding(this, e)) {
					Game.bullets.remove(i);
					isDamaged = true;
					life-=5;
					//Sound.ai1.play();
					return;
				}
			}
		}
	}
	
	
	public void checkCollisionGun() {
		
		for(int i = 0; i < Game.entities.size(); i++) {
			Entity atual = Game.entities.get(i);
			if(atual instanceof Weapon) {
				
				if(Entity.isColidding(this, atual)) {
					
					
					if(atual instanceof PistolFloor) {
						if(Game.player.hasGun) {
							for(int x = 0; x < Game.entities.size(); x++) {
								Entity gunHand = Game.entities.get(x);
								if(gunHand instanceof WeaponInHand) {
									
									Game.entities.remove(x);
									if(gun == 2) {
									Game.entities.add(new SubMachineFloor(getX() + (20*sideDropX), getY() + (20*sideDropY), 16, 16, Entity.WEAPON_SUBMACHINE_EN));
									}
									else if(gun == 3) {
										Game.entities.add(new ShotGunFloor(getX() + (20*sideDropX), getY() + (20*sideDropY), 16, 16, Entity.WEAPON_SHOTGUN_EN));
										}
									
								}
							}
						}
						gun = 1;
						Game.entities.add(new Pistol(this.getX() + Camera.x, this.getY() + Camera.y, 16, 16, null));
						}
					
					else if(atual instanceof SubMachineFloor) {
						if(Game.player.hasGun) {
							for(int x = 0; x < Game.entities.size(); x++) {
								Entity gunHand = Game.entities.get(x);
								if(gunHand instanceof WeaponInHand) {
									Game.entities.remove(x);
									if(gun == 1) {
									Game.entities.add(new PistolFloor(getX() + (20*sideDropX), getY() + (20*sideDropY), 16, 16, Entity.WEAPON_PISTOL_EN));
									}
									else if(gun == 3) {
										Game.entities.add(new ShotGunFloor(getX() + (20*sideDropX), getY() + (20*sideDropY), 16, 16, Entity.WEAPON_SHOTGUN_EN));
										}
									
								}
							}
						}
						gun = 2;
						Game.entities.add(new SubMachine(this.getX() + Camera.x, this.getY() + Camera.y, 16, 16, null));
						
					}
					else if(atual instanceof ShotGunFloor) {
						if(Game.player.hasGun) {
							for(int x = 0; x < Game.entities.size(); x++) {
								Entity gunHand = Game.entities.get(x);
								if(gunHand instanceof WeaponInHand) {
									Game.entities.remove(x);
									if(gun == 1) {
									Game.entities.add(new PistolFloor(getX() + (20*sideDropX), getY() + (20*sideDropY), 16, 16, Entity.WEAPON_PISTOL_EN));
									}
									else if(gun == 2) {
										Game.entities.add(new SubMachineFloor(getX() + (20*sideDropX), getY() + (20*sideDropY), 16, 16, Entity.WEAPON_SUBMACHINE_EN));
										}
									
								}
							}
						}
						gun = 3;
						Game.entities.add(new ShotGun(this.getX() + Camera.x, this.getY() + Camera.y, 16, 16, null));
						
					}
					Game.entities.remove(i);
					hasGun = true;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		if(!isDamaged) {
		if(dir == right_dir) {
		g.drawImage(rightPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
		
		}
		else if(dir == left_dir) {
			g.drawImage(leftPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			
		}
		else if(dir == up_dir) {
			g.drawImage(upPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			
		}
		else if(dir == down_dir) {
			g.drawImage(downPlayer[index], this.getX() - Camera.x, this.getY() - Camera.y, null);
			
		}
		}
		else {
			g.drawImage(damagePlayer, this.getX() - Camera.x, this.getY() - Camera.y, null);
			
		}
	}


}
