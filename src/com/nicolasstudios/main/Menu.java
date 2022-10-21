package com.nicolasstudios.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JTextField;

import com.nicolasstudios.world.World;

import com.nicolasstudios.screens.ScoreBoard;
import com.nicolasstudios.screens.SetName;

public class Menu {

	
	public String[] options = {"Novo jogo", "Música", "Efeitos Sonoros", "Sair", null};
	
	public int currentOption = 0;
	public int maxOption = 5;
	
	private BufferedImage banner;
	
	public boolean up = false, down = false, enter = false;
	
	public static boolean paused = false;
	public static boolean won = false;
	
	public static SetName setName;
	
	public Menu() {
		if(!paused) {
		try {
		   banner = ImageIO.read(getClass().getResource("/banner.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	
	
	
	public void tick() {
		
		
		if(won) {
			options[0] = "Novo jogo";
			options[1] = "Salvar Tempo";
			options[2] = "Música";
			options[3] = "Efeitos Sonoros";
			options[4] = "Sair";
			maxOption = 5;
		}
		else if(paused) {
			options[0] = "Continuar";
			options[1] = "Reiniciar fase";
			options[2] = "Música";
			options[3] = "Efeitos Sonoros";
			options[4] = "Sair";
			maxOption = 5;
		}
		else{
			options[0] = "Novo jogo";
			options[1] = "Música";
			options[2] = "Efeitos Sonoros";
			options[3] = "Melhores Tempos";
			options[4] = "Sair";
			maxOption = 5;
		}
		
		
		
		if(up && !Game.showScoreBoard) {
			up = false;
			currentOption--;
			if(currentOption < 0) {
				currentOption = maxOption-1;
			}
		}
			if(down && !Game.showScoreBoard) {
				
				down = false;
				currentOption++;
				if(currentOption >= maxOption) {
					currentOption = 0;
				}
			}
			
			if(enter && !Game.showScoreBoard) {
				enter = false;
				if(options[currentOption] == "Continuar") {
					Game.gameState = "NORMAL";
					paused = false;
				}
				else if(options[currentOption] == "Sair") {
					if(paused) {
						paused = false;
						currentOption = 0;
					}
					else {
					System.exit(1);
					}
				}
				else if(options[currentOption] == "Novo jogo") {
					Game.min = 0;
					Game.sec = 0;
					Game.musicChange();
					World.restartGame("/level1.png");
					Game.gameState = "NORMAL";
					won = false;
					Game.curLevel = 1;
				}
				else if(options[currentOption] == "Reiniciar fase") {
					Game.musicChange();
					World.restartGame("/level" + Game.curLevel + ".png");
					Game.gameState = "NORMAL";
					
				}
				else if(options[currentOption] == "Música") {
					if(Game.hasMusic) {
						Game.hasMusic = false;
						Game.stopMusic();
					}
					else {
						Game.hasMusic = true;
						Game.musicChange();
					}
				}
				else if(options[currentOption] == "Efeitos Sonoros") {
					if(Game.hasSounds) {
						Game.hasSounds = false;
					}
					else {
						Game.hasSounds = true;
						Game.sound.carregaSons();
					}
				}
				else if(options[currentOption] == "Salvar Tempo") {
					setName = new SetName();
					Game.frame.setVisible(false);
					
					/*String[] opt1 = {"Nicolas", "Isabela"};
					String[] opt2 = {"3", "5"};
					saveGame(opt1, opt2, 10);*/
				}
				else if(options[currentOption] == "Melhores Tempos") {
					Game.showScoreBoard = true;
				}
			}
	}
	
	
	public void render(Graphics g) {
		
		if(!paused) {
			g.drawImage(banner, 0, 0, null);
		}
		else {
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(0, 0, 0, 150));
			g2.fillRect(0, 0, Game.WIDTH*Game.SCALE, Game.HEIGHT*Game.SCALE);
			
		}
		
		if(won) {
			paused = false;
			g.setColor(Color.green);
			g.setFont(new Font("arial", Font.BOLD, 36));
			g.drawString("Você venceu!", (Game.WIDTH*Game.SCALE)/2 - 107, (Game.HEIGHT*Game.SCALE)/2 - 180);
			g.drawString("Tempo: " + Game.minCompleter + Game.min + ":" + Game.secCompleter + Game.sec, (Game.WIDTH*Game.SCALE)/2 - 111, (Game.HEIGHT*Game.SCALE)/2 - 130);
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 24));
			
			g.drawString("Novo jogo", (Game.WIDTH*Game.SCALE)/2 - 58, (Game.HEIGHT*Game.SCALE)/2 - 70);
			g.drawString("Salvar Tempo", (Game.WIDTH*Game.SCALE)/2 - 73, (Game.HEIGHT*Game.SCALE)/2 - 15);
			g.drawString("Música " + musicRepresentation(), (Game.WIDTH*Game.SCALE)/2 - 43, (Game.HEIGHT*Game.SCALE)/2 + 40);
			
			g.drawString("Efeitos Sonoros " + soundRepresentation(), (Game.WIDTH*Game.SCALE)/2 - 90, (Game.HEIGHT*Game.SCALE)/2 + 95);
			
			g.drawString("Sair", (Game.WIDTH*Game.SCALE)/2 - 25, (Game.HEIGHT*Game.SCALE)/2 + 150);
			
			if(currentOption == 0) {
				g.drawString(">", (Game.WIDTH*Game.SCALE)/2 - 75, (Game.HEIGHT*Game.SCALE)/2 - 70);
			}
			
			else if(currentOption == 1) {
				g.drawString(">", (Game.WIDTH*Game.SCALE)/2 - 90, (Game.HEIGHT*Game.SCALE)/2 -15);
				
			}
			else if(currentOption == 2) {
				g.drawString(">", (Game.WIDTH*Game.SCALE)/2 - 60, (Game.HEIGHT*Game.SCALE)/2 + 40);
				
			}
			else if(currentOption == 3) {
				g.drawString(">", (Game.WIDTH*Game.SCALE)/2 - 107, (Game.HEIGHT*Game.SCALE)/2 + 95);
				
			}
			else if(currentOption == 4) {
				g.drawString(">", (Game.WIDTH*Game.SCALE)/2 - 42, (Game.HEIGHT*Game.SCALE)/2 + 150);
				
			}
		}
		else {
			g.setColor(Color.green);
			g.setFont(new Font("arial", Font.BOLD, 36));
			g.drawString("Space Invaded", (Game.WIDTH*Game.SCALE)/2 - 115, (Game.HEIGHT*Game.SCALE)/2 - 180);
			g.setColor(Color.white);
			g.setFont(new Font("arial", Font.BOLD, 24));
			
			if(!paused) {
			g.drawString("Novo jogo", (Game.WIDTH*Game.SCALE)/2 - 58, (Game.HEIGHT*Game.SCALE)/2 - 90);
			g.drawString("Música " + musicRepresentation(), (Game.WIDTH*Game.SCALE)/2 - 43, (Game.HEIGHT*Game.SCALE)/2 - 30);
			g.drawString("Efeitos Sonoros " + soundRepresentation(), (Game.WIDTH*Game.SCALE)/2 - 84, (Game.HEIGHT*Game.SCALE)/2 + 30);
			g.drawString("Melhores Tempos", (Game.WIDTH*Game.SCALE)/2 - 90, (Game.HEIGHT*Game.SCALE)/2 + 90);
			
			g.drawString("Sair", (Game.WIDTH*Game.SCALE)/2 - 25, (Game.HEIGHT*Game.SCALE)/2 + 150);
			
			if(currentOption == 0) {
				g.drawString(">", (Game.WIDTH*Game.SCALE)/2 - 75, (Game.HEIGHT*Game.SCALE)/2 - 90);
					
				}
				
			else if(currentOption == 1) {
				g.drawString(">", (Game.WIDTH*Game.SCALE)/2 - 60, (Game.HEIGHT*Game.SCALE)/2 -30);
				
			}
			else if(currentOption == 2) {
				g.drawString(">", (Game.WIDTH*Game.SCALE)/2 - 101, (Game.HEIGHT*Game.SCALE)/2 + 30);
				
			}
			else if(currentOption == 3) {
				g.drawString(">", (Game.WIDTH*Game.SCALE)/2 - 107, (Game.HEIGHT*Game.SCALE)/2 + 90);
				
			}
			else if(currentOption == 4) {
				g.drawString(">", (Game.WIDTH*Game.SCALE)/2 - 42, (Game.HEIGHT*Game.SCALE)/2 + 150);
				
			}
			}
			else {
				g.drawString("Continuar", (Game.WIDTH*Game.SCALE)/2 - 53, (Game.HEIGHT*Game.SCALE)/2 - 90);
				g.drawString("Reiniciar fase", (Game.WIDTH*Game.SCALE)/2 - 76, (Game.HEIGHT*Game.SCALE)/2 -30);
				g.drawString("Música " + musicRepresentation(), (Game.WIDTH*Game.SCALE)/2 - 44, (Game.HEIGHT*Game.SCALE)/2 + 30);
				g.drawString("Efeitos Sonoros " + soundRepresentation(), (Game.WIDTH*Game.SCALE)/2 - 88, (Game.HEIGHT*Game.SCALE)/2 + 90);
				g.drawString("Sair", (Game.WIDTH*Game.SCALE)/2 - 22, (Game.HEIGHT*Game.SCALE)/2 + 150);
				
			if(currentOption == 0) {
				g.drawString(">", (Game.WIDTH*Game.SCALE)/2 - 70, (Game.HEIGHT*Game.SCALE)/2 - 90);
					
				}
				
			else if(currentOption == 1) {
				g.drawString(">", (Game.WIDTH*Game.SCALE)/2 - 93, (Game.HEIGHT*Game.SCALE)/2 -30);
				
			}
			else if(currentOption == 2) {
				g.drawString(">", (Game.WIDTH*Game.SCALE)/2 - 61, (Game.HEIGHT*Game.SCALE)/2 + 30);
				
			}
			else if(currentOption == 3) {
				g.drawString(">", (Game.WIDTH*Game.SCALE)/2 - 105, (Game.HEIGHT*Game.SCALE)/2 + 90);
				
			}
			else if(currentOption == 4) {
				g.drawString(">", (Game.WIDTH*Game.SCALE)/2 - 39, (Game.HEIGHT*Game.SCALE)/2 + 150);
				
			}
			}
			
		}
		
		if(Game.showScoreBoard) {
			ScoreBoard.render(g);
		}
	}
	private String musicRepresentation(){
		if(Game.hasMusic) {
			return "";
		}
		return "X";
		}
	private String soundRepresentation() {
		if(Game.hasSounds) {
			return "";
			
		}
		return "X";
		
	}
}
