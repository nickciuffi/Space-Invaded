package com.nicolasstudios.world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.IndexColorModel;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.nicolasstudios.entities.AbleBlocks;
import com.nicolasstudios.entities.Bullet;
import com.nicolasstudios.entities.Enemy;
import com.nicolasstudios.entities.Enemy2;
import com.nicolasstudios.entities.Enemy3;
import com.nicolasstudios.entities.Enemy4;
import com.nicolasstudios.entities.EnemyGun;
import com.nicolasstudios.entities.Entity;
import com.nicolasstudios.entities.LifePack;
import com.nicolasstudios.entities.PistolFloor;
import com.nicolasstudios.entities.Player;
import com.nicolasstudios.entities.ShotGunFloor;
import com.nicolasstudios.entities.SubMachineFloor;
import com.nicolasstudios.graficos.Spritesheet;
import com.nicolasstudios.main.Game;

public class World {
	
	public static Tiles[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 16;
	public static int largura;
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			int[] pixels = new int[map.getWidth() * map.getHeight()];
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			tiles = new Tiles[map.getWidth() * map.getHeight()];
			map.getRGB(0, 0, map.getWidth(), map.getHeight(), pixels, 0, map.getWidth());
			largura = map.getWidth();
			
			for(int xx = 0; xx < map.getWidth(); xx++) {
				for(int yy = 0; yy < map.getHeight(); yy++) {
					int pixelAtual = pixels[xx + (yy * map.getWidth())];
					tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tiles.TILE_FLOOR);
					
					if(pixelAtual == 0xFFFCFCFC) {
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tiles.TILE_WALL, false);
					}
					else if(pixelAtual == 0xFFE560E1) {
						//ARMA
						tiles[xx + (yy * WIDTH)] = new DoorTile(xx*16, yy*16, Tiles.TILE_DOOR);
					}
					else if(pixelAtual == 0xFFEAF7C3) {
						//BULLET
						tiles[xx + (yy * WIDTH)] = new WallTile(xx*16, yy*16, Tiles.TILE_WALL, true);
					}
					else if(pixelAtual == 0xFFF200FF) {
						//SPACE
						tiles[xx + (yy * WIDTH)] = new Tiles(xx*16, yy*16, Tiles.TILE_BLACK, false);
						tiles[xx + (yy * WIDTH)] = new SpaceTile(xx*16, yy*16, World.rotate(Tiles.TILE_SPACE, Game.rand.nextInt(360)));
					}
					else if(pixelAtual == 0xFFF90074) {
						//BLOCKDESABLE
						Game.blocksDisable = true;
						tiles[xx + (yy * WIDTH)] = new SpaceTile(xx*16, yy*16, World.rotate(Tiles.TILE_SPACE, Game.rand.nextInt(360)));
					}
					else if(pixelAtual == 0xFF0915FC) {
						//PLAYER
						Game.player.setX(xx*16);
						Game.player.setY(yy*16);
						//tiles[xx + (yy * WIDTH)] = new FloorTile(xx*16, yy*16, Tiles.TILE_FLOOR);
					}
					else if(pixelAtual == 0xFFFCD500) {
						//BULLET
						Game.entities.add(new Bullet(xx*16, yy*16,16, 16, Entity.BULLET_EN));	
					}
					
					else if(pixelAtual == 0xFFFC0000) {
						//VIDA
						LifePack life = new LifePack(xx*16, yy*16,16, 16, Entity.LIFEPACK_EN);
						life.setMask(3, 2, 11, 12);
						Game.entities.add(life);
						}
					else if(pixelAtual == 0xFF9200FC) {
						//PISTOL
						Game.entities.add(new PistolFloor(xx*16, yy*16, 16, 16, Entity.WEAPON_PISTOL_EN));
					}
					else if(pixelAtual == 0xFFF900E0) {
						//ShotGun
						Game.entities.add(new ShotGunFloor(xx*16, yy*16, 16, 16, Entity.WEAPON_SHOTGUN_EN));
					}
					else if(pixelAtual == 0xFFFF732D) {
						//Submachine
						Game.entities.add(new SubMachineFloor(xx*16, yy*16, 16, 16, Entity.WEAPON_SUBMACHINE_EN));
					}
					else if(pixelAtual == 0xFFB558AE) {
						//Able blocks
						Game.entities.add(new AbleBlocks(xx*16, yy*16, 16, 16, Entity.ABLEBLOCKS_EN));
					}
					else if(pixelAtual == 0xFF11FC09) {
						//INIMIGO
						Enemy en = new Enemy(xx*16, yy*16,16, 16, Entity.ENEMY_EN);
						Game.entities.add(en);
						Game.enemies.add(en);
					}
					else if(pixelAtual == 0xFF2CF9F6) {
						//ENEMY2
						Enemy2 en2 = new Enemy2(xx*16, yy*16, 16, 16, Entity.ENEMY2_EN);
						Game.entities.add(en2);
						Game.enemies.add(en2);
					}
					else if(pixelAtual == 0xFFB5FFFF) {
						//ENEMY3
						Enemy3 en3 = new Enemy3(xx*16, yy*16, 16, 16, Entity.ENEMY2_EN);
								Game.entities.add(en3);
								Game.enemies.add(en3);
					}
					else if(pixelAtual == 0xFFFFEA75) {
						//ENEMY3
						Enemy4 en4 = new Enemy4(xx*16, yy*16, 16, 16, Entity.ENEMY2_EN);
								Game.entities.add(en4);
								Game.enemies.add(en4);
					}
					
					
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static boolean isFree(int xnext, int ynext, double sizeX, double sizeY) {

		int x1 = (xnext / World.TILE_SIZE);
		int y1 = (ynext / World.TILE_SIZE);
		
		int x2 = (int)((xnext + sizeX) / World.TILE_SIZE);
		int y2 = (ynext / World.TILE_SIZE);
		
		int x3 = (xnext / World.TILE_SIZE);
		int y3 = (int)((ynext + sizeY) / World.TILE_SIZE);
		
		int x4 = (int)((xnext + sizeX) / World.TILE_SIZE);
		int y4 = (int)((ynext + sizeY) / World.TILE_SIZE);
		if(sizeX == 13) {
		System.out.println(x4);
		}
	
		return !((tiles[x1 + (y1*World.WIDTH)] instanceof WallTile)||
				(tiles[x2 + (y2*World.WIDTH)] instanceof WallTile) ||
				(tiles[x3 + (y3*World.WIDTH)] instanceof WallTile) ||
				(tiles[x4 + (y4*World.WIDTH)] instanceof WallTile));
		
	}
	
	public static GraphicsConfiguration getDefaultConfiguration() {
	    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice gd = ge.getDefaultScreenDevice();
	    return gd.getDefaultConfiguration();
	}
	
	public static BufferedImage rotate(BufferedImage image, double angle) {
	    int w = image.getWidth(), h = image.getHeight();
	    GraphicsConfiguration gc = getDefaultConfiguration();
	    BufferedImage result = gc.createCompatibleImage(w, h);
	    Graphics2D g = result.createGraphics();
	    g.rotate(Math.toRadians(angle), w / 2, h / 2);
	    g.drawRenderedImage(image, null);
	    g.dispose();
	    return result;
	}
	
	public static void createWall(double mx, double my) {
		
		if(Game.player.blocksCollected > 0) {
		int bx = (int)((mx)/16);
		int by = (int)((my)/16);
		if(by<0 || bx<0 || bx>largura || my > World.HEIGHT*16) {
			return;
		}
		int indexTile = (largura*by)+bx;
		if(tiles[indexTile] instanceof FloorTile) {
			if(!ColideEnemy(indexTile) && !ColideEntity(indexTile)) {
		tiles[indexTile] = new WallTile(bx*16, by*16, Tiles.TILE_WALL, false);
		Game.player.blocksCollected--;
		
		}
		}
		}
	}
	
	private static boolean ColideEnemy(int indexTile){
		Tiles tile = World.tiles[indexTile];
		for(int i = 0; i < Game.enemies.size(); i++) {
			
			if(Entity.isColidding(Game.enemies.get(i), new Entity(tile.getX(), tile.getY(), 16, 16, null))) {
				return true;
				}
			}
		
		
		return false;
	}
	
	private static boolean ColideEntity(int indexTile){
		Tiles tile = World.tiles[indexTile];
		for(int i = 0; i < Game.entities.size(); i++) {
			
			if(Entity.isColidding(Game.entities.get(i), new Entity(tile.getX(), tile.getY(), 16, 16, null))) {
				return true;
				}
			}
		
		
		return false;
	}
	
	public static void restartGame(String level) {
		Game.blocksDisable = false;
		Game.levelFrames = 0;
		Game.showLevel = true;
		Game.entities = new ArrayList<Entity>();
		Game.enemies = new ArrayList<Enemy>();
		Game.enemyGun = new ArrayList<EnemyGun>();
		Game.spritesheet = new Spritesheet("/spritesheet.png");
		
		Game.player = new Player(0, 0, 16, 16, Game.spritesheet.getSprite(32, 0, 16, 16));
		Game.world = new World(level);
		Game.entities.add(Game.player);
		
	}
	
	public static void renderMiniMap() {
		if(WIDTH*HEIGHT != Game.minimapaPixels.length) {
			Game.minimapa = new BufferedImage(World.WIDTH, World.HEIGHT, BufferedImage.TYPE_INT_RGB);
			Game.minimapaPixels = ((DataBufferInt)Game.minimapa.getRaster().getDataBuffer()).getData();
			
		}
		for(int i = 0; i < Game.minimapaPixels.length; i++) {
			Game.minimapaPixels[i] = 0;
		}
		for(int xx = 0; xx < WIDTH; xx++) {
			for(int yy = 0; yy < HEIGHT; yy++) {
				if(tiles[xx + (yy*WIDTH)] instanceof WallTile) {
					Game.minimapaPixels[xx + (yy*HEIGHT)] = 0Xffffff;
				}
				else if(tiles[xx + (yy*WIDTH)] instanceof DoorTile) {
					Game.minimapaPixels[xx + (yy*HEIGHT)] = 0X8e05ff;
				}
				Game.minimapaPixels[Game.player.getX()/16 + ((Game.player.getY()/16)*HEIGHT)] = 0x00ff00;
			}
		}
	}
	
	public void render(Graphics g) {
		int xstart = Camera.x/16;
		int ystart = Camera.y/16;
		int xfinal = xstart + (Game.WIDTH / 16);
		int yfinal = ystart + (Game.HEIGHT / 16);
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) {
					continue;
				}
				Tiles tile = tiles[xx + (yy * WIDTH)];
				tile.render(g);
			}
		}
	}
}
