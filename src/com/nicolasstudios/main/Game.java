package com.nicolasstudios.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.image.DataBufferInt;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.nicolasstudios.entities.BulletShoot;
import com.nicolasstudios.entities.Enemy;
import com.nicolasstudios.entities.EnemyGun;
import com.nicolasstudios.entities.Entity;
import com.nicolasstudios.entities.Player;
import com.nicolasstudios.entities.WeaponInHand;
import com.nicolasstudios.graficos.Spritesheet;
import com.nicolasstudios.graficos.UI;
import com.nicolasstudios.world.Camera;
import com.nicolasstudios.world.World;

import com.nicolasstudios.screens.SetName;

public class Game extends Canvas implements Runnable, KeyListener, MouseListener{
	
	private static final long serialVersionUID = 1L;
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 240, HEIGHT = 160, SCALE = 3;
	public static boolean blocksDisable = false;
	
	private BufferedImage image;
	
	public static List<Entity>  entities;
	public static List<Enemy> enemies;
	public static List<BulletShoot> bullets;
	public static List<EnemyGun> enemyGun;
	
	public static Spritesheet spritesheet;
	
	public static World world;
	
	public static Player player;
	
	public static Random rand;
	
	public static Menu menu;
	
	public static Sound sound;
	
	public static boolean askKey = false;
	private int askKeyFrames = 3, askKeyMaxFrames = 300;
	
	public static int curLevel = 1, maxLevel = 4;
	
	public static String gameState = "MENU"; 
	private boolean showMessageGameOver = true;
	private int framesGameOver = 0;
	
	private boolean restartGame = false;
	
	public static boolean showLevel = true;
	public static int levelFrames = 0, levelMaxFrames = 300;
	
	public static int lastSong = 0, MusicQtd = 5;
	
	public static boolean hasMusic = false, hasSounds = false;
	
	public static int min = 0, sec = 0, secFrames = 0;
	public static String secCompleter = "0", minCompleter = "0";
	
	public static boolean showScoreBoard = false;
	
	public static boolean disposeSetName = false;
	
	public UI ui;
	
	public int[] pixels;
	public BufferedImage lightmap;
	public int[] lightmapPixels;
	
	public static BufferedImage minimapa;
	public static int[] minimapaPixels;
	
	public static boolean isDark = false;
 	
	public Game() {
		rand = new Random();
		addKeyListener(this);
		addMouseListener(this);
		this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
		
		ui = new UI();
		image = new BufferedImage(240,160,BufferedImage.TYPE_INT_RGB);
		try {
			lightmap = ImageIO.read(getClass().getResource("/lightmap.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		lightmapPixels = new int[lightmap.getWidth() * lightmap.getHeight()];
		lightmap.getRGB(0, 0, lightmap.getWidth(), lightmap.getHeight(), lightmapPixels, 0, lightmap.getWidth());
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		entities = new ArrayList<Entity>();
		enemies = new ArrayList<Enemy>();
		bullets = new ArrayList<BulletShoot>();
		enemyGun = new ArrayList<EnemyGun>();
		
		spritesheet = new Spritesheet("/spritesheet.png");
		
		player = new Player(0, 0, 16, 13, spritesheet.getSprite(32, 0, 16, 13));
		world = new World("/level"+ curLevel +".png");
		
		
		entities.add(player);
		menu = new Menu();
		sound = new Sound();
		
		if(hasSounds) {
			sound.carregaSons();
		}
		minimapa = new BufferedImage(World.WIDTH, World.HEIGHT, BufferedImage.TYPE_INT_RGB);
		minimapaPixels = ((DataBufferInt)minimapa.getRaster().getDataBuffer()).getData();
	}
	public void initFrame() {
		frame = new JFrame("Space Invaded");
		
		Image icon = null;
		try {
			icon = ImageIO.read(getClass().getResource("/icon.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
		frame.setIconImage(icon);
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
	
	public void tick() {
		if(gameState == "NORMAL") {
			
		for(int i = 0; i< entities.size(); i++){
			Entity e = entities.get(i);
			e.tick();
		}
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).tick();
		}
		for(int i = 0; i < enemyGun.size(); i++) {
			enemyGun.get(i).tick();
		}
		if(enemies.size()<=0) {
			player.venceu = true;
		}
		
		if(secFrames == 60) {
			secFrames = 0;
			minCompleter = "0";
			secCompleter = "0";
			sec++;
			if(sec == 60) {
				min++;
				sec = 0;
				
			}
			else if(sec > 9) {
				secCompleter = "";
			}
			if(min > 9) {
				minCompleter = "";
			}
			
		}
		secFrames++;
		}
		else if(gameState == "GAME_OVER") {
			framesGameOver++;
			if(framesGameOver == 30) {
				framesGameOver = 0;
				if(showMessageGameOver) {
					showMessageGameOver = false;
				}else {
					showMessageGameOver = true;
				}
			}
		}
		
		if(restartGame) {
			restartGame = false;
			gameState = "NORMAL";
			curLevel = 1;
			Game.min = 0;
			Game.sec = 0;
			
			musicChange();
			String newWorld = "/level" + curLevel + ".png";
			World.restartGame(newWorld);
		}
		else if(gameState == "MENU") {
			menu.tick();
		}
		
		if(askKey) {
			askKeyFrames++;
			if(askKeyFrames >= askKeyMaxFrames) {
				askKey = false;
				askKeyFrames = 0;
			}
		}
		if(showLevel) {
			levelFrames++;
			if(levelFrames >= levelMaxFrames) {
				showLevel = false;
				levelFrames = 0;
			}
		}
		if(disposeSetName) {
			disposeSetName = false;
			Menu.setName.dispose();
		}
		
	}
	
	public static void nextLevel() {
		curLevel++;
		if(curLevel > maxLevel) {
			curLevel = 1;
			menu.won = true;
			gameState = "MENU";
			Menu.paused = false;
		}
		musicChange();
		
		String newWorld = "/level" + curLevel + ".png";
		World.restartGame(newWorld);
	}
	
	public static void musicChange() {
		if(!hasMusic) {
			return;
		}
		else {
		int nextSong = rand.nextInt(MusicQtd)+1;
		if(nextSong == lastSong) {
			musicChange();
		}
		else {
			
		lastSong = nextSong;
		if(sound.music1 != null) {
			stopMusic();
		}
		
		
		switch(nextSong) {
		case 1:
			sound.music1 = Sound.load("/music1.wav", 1);
			sound.music1.loop();
			break;
		case 2:
			sound.music1 = Sound.load("/music2.wav", 1);
			sound.music1.loop();
			break;
		case 3:
			sound.music1 = Sound.load("/music3.wav", 1);
			sound.music1.loop();
			break;
		case 4:
			sound.music1 = Sound.load("/music4.wav", 1);
			sound.music1.loop();
			break;
		case 5:
			sound.music1 = Sound.load("/music5.wav", 1);
			sound.music1.loop();
			break;
		case 6:
			sound.music1 = Sound.load("/music6.wav", 1);
			sound.music1.loop();
			break;
		case 7:
			sound.music1 = Sound.load("/music7.wav", 1);
			sound.music1.loop();
			break;
		}
		}
	}
	}
	public static void stopMusic() {
		sound.music1.stop();
		
		
	}
	
	public static void applySave(String str) {
		String[] spl = str.split("/");
		for(int i = 0; i < spl.length; i++) {
			String[] spl2 = spl[i].split(":");
			switch(spl2[0])
			{
			case "tempo":
				break;
			case "nome":
				break;
		
			}
		}
	}
	
	public static String loadGame(int encode) {
		String line = "";
		File file = new File("save.txt");
		if(file.exists()) {
			try {
				String singleLine = null;
				BufferedReader reader = new BufferedReader(new FileReader("save.txt"));
				try {
					while((singleLine = reader.readLine()) != null) {
						String[] trans = singleLine.split(":");
						
						line+=trans[0];
						line+=":";
						line+=trans[1];
						line+="/";
					}
				}catch(IOException e) {}
			}catch(FileNotFoundException e) {}
		}
		return line;
	}
	
	public static void saveGame(String[] val1, String[] val2, int encode) {
		BufferedWriter write = null;
		
		try {
			write = new BufferedWriter(new FileWriter("save.txt"));
		}catch(IOException e) {}
		
		for(int i = 0; i < val1.length; i++) {
			String current = val1[i];
			current+=":";
			current+=val2[i];
		
			try {
				write.write(current);
				if(i < val1.length - 1) {
					write.newLine();
				}
			}catch(IOException e) {}
		}
		try {
			write.flush();
			write.close();
		}catch(IOException e) {}
	}
	
public static void TimeAdd(String name, String time) {
		
		String saver = loadGame(10);
		
		saver+= name + ":" + time + "/";
		
		String[] spl = saver.split("/");
		
		String[] opt1 = new String[spl.length];
		String[] opt2 = new String[spl.length];
		
		for(int i = 0; i < spl.length; i++) {
			String[] spl2 = spl[i].split(":");
			for(int x = 0; x < spl.length; x++) {
				String[] splCompare = spl[x].split(":");
				if(Integer.valueOf(spl2[1]) <= Integer.valueOf(splCompare[1]) && i>x) {
					String splInter = spl[x];
					spl[x] = spl[i];
					spl[i] = splInter;
				}
				else {
					spl[i] = spl[i];
				}
			}
			
		}
		
		for(int i = 0; i < spl.length; i++) {
			String[] spl2 = spl[i].split(":");
			//System.out.println(spl2[0]);
			opt1[i] = spl2[0];
			opt2[i] = spl2[1];
		
		}
		saveGame(opt1, opt2, 10);
	}
	public static String[] getTop3() {
		String saver = loadGame(10);
		String[] spl = saver.split("/");
		String[] splReturn = {"Indefinido:?", "Indefinido:?", "Indefinido:?"};
		
		for(int i = 0; i < 3; i++) {
			if(spl.length > i && spl[0] != "") {
			splReturn[i] = spl[i];
			}
		}
		return splReturn;
	}
	
	private void infoScreen(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 22));
		
		g.drawString("Munição: " + player.ammo, WIDTH*SCALE - 150, 50);
		if(!blocksDisable) {
		g.drawString("Blocos: " + player.blocksCollected, WIDTH*SCALE - 150, 90);
		}
		else {
		g.drawString("Blocos: X", WIDTH*SCALE - 150, 90);
		}
		g.drawString("Inimigos: " + enemies.size(), WIDTH*SCALE - 150, 130);
		g.setFont(new Font("arial", Font.BOLD, 35));
		//tempo
		g.drawString(minCompleter + min + ":" + secCompleter + sec, (WIDTH*SCALE/2)-43 , 50);
		
		if(askKey) {
			g.setColor(Color.black);
			g.fillRect((player.getX() - Camera.x + 10) * SCALE, (player.getY() - Camera.y -10) * SCALE, 200, 50);
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 15));
			g.drawString("Você precisa matar todos", (player.getX() - Camera.x + 12) * SCALE, (player.getY() - Camera.y -4) * SCALE);
			g.drawString("os inimigos primeiro!", (player.getX() - Camera.x + 12) * SCALE, (player.getY() - Camera.y +4) * SCALE);
		}
		
		if(showLevel) {
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 55));
			g.drawString("Nivel " + curLevel, (Game.WIDTH*Game.SCALE)/2 - 80, (Game.HEIGHT*Game.SCALE)/2 - 140);
			}
		
		if(gameState == "GAME_OVER") {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 0, 0, 150));
			g2.fillRect(0,  0,  WIDTH*SCALE, HEIGHT*SCALE);
			
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 36));
			g.drawString("GAME OVER", (WIDTH*SCALE)/2 - 105, (HEIGHT*SCALE)/2 - 30);
			g.setFont(new Font("arial", Font.BOLD, 28));
			if(showMessageGameOver) {
			g.drawString(">Pressione ENTER para reiniciar<", (WIDTH*SCALE)/2 - 220, (HEIGHT*SCALE)/2 + 30);
			}
			}
		World.renderMiniMap();
		g.drawImage(minimapa, 580, 340, (int)(World.WIDTH*getScaleWidthMap()), (int)(World.HEIGHT*getScaleHeigthMap()), null);
	}
	
	public void drawRectangleExample() {
		for(int xx = 0; xx < 32; xx++) {
			for(int yy = 0; yy < 32; yy++) {
				pixels[xx + (yy*WIDTH)] = 0xff0000;
			}
		}
	}
	
	public double getScaleWidthMap() {
		return 120/World.WIDTH;
	}
	public double getScaleHeigthMap() {
		return 120/World.HEIGHT;
	}
	
	public void applyLight() {
		for(int xx = 0; xx < WIDTH; xx++) {
			for(int yy = 0; yy < HEIGHT; yy++) {
				if(lightmapPixels[xx+(yy*Game.WIDTH)] == 0xffffffff) {
					pixels[xx+(yy*Game.WIDTH)] = 0;
				}
			}
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		world.render(g);
		
		for(int i = 0; i< entities.size(); i++){
			Entity e = entities.get(i);
			e.render(g);
		}
		for(int i = 0; i < bullets.size(); i++) {
			bullets.get(i).render(g);
		}
		for(int i = 0; i < enemyGun.size(); i++) {
			enemyGun.get(i).render(g);
		}
		if(isDark) {
		applyLight();
		}
		
		ui.render(g);
		
		g.dispose();
		g = bs.getDrawGraphics();
		
		
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		infoScreen(g);
		
		if(gameState == "MENU") {
			menu.render(g);
		}
		
		bs.show();
	}
	

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		requestFocus();
		while(isRunning) {
			long now = System.nanoTime();
			delta+= (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				delta--;
			}
			
		}
		stop();
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = true;
			if(gameState == "MENU") {
				
				menu.up = true;
			}
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = true;
			if(gameState == "MENU") {
				menu.down = true;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_P) {
			WeaponInHand.isShooting = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
		
			if(Game.gameState == "GAME_OVER") {
			restartGame = true;
			}
			menu.enter = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if(Game.gameState == "NORMAL") {
			Menu.paused = true;
			Game.gameState = "MENU";
			}
			if(showScoreBoard) {
				showScoreBoard = false;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_O) {
			if(!blocksDisable) {
			World.createWall(player.getX() + 8 + (50*player.sideBlocksX), player.getY() + 8 + (50*player.sideBlocksY));
			}
		}
	
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		}else if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			player.up = false;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
			player.down = false;
		}
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		
		/*if(e.getButton() == MouseEvent.BUTTON1) {
		player.mouseShoot = true;
		player.mx = (e.getX()/3);
		player.my = (e.getY()/3);
		}
		*/
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
