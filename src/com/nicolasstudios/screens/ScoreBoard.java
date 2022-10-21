package com.nicolasstudios.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import com.nicolasstudios.main.Game;
import com.nicolasstudios.main.Menu;

public class ScoreBoard {
	
	public static boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        double d = Double.parseDouble(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	public static void render(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(10, 150, 10, 250));
		g.fillRect((Game.WIDTH*Game.SCALE)/2 - 130, (Game.HEIGHT*Game.SCALE)/2 - 150, 2*150, 150*2);
		//titulo
		g.setColor(Color.white);
		g.setFont(new Font("arial", Font.BOLD, 30));
		g.drawString("Melhores Tempos", (Game.WIDTH*Game.SCALE)/2 - 108, (Game.HEIGHT*Game.SCALE)/2 - 100);
		g.setFont(new Font("arial", Font.BOLD, 25));
		int min,sec;
		String minCompleter, secCompleter;
		//1
		String[] top1 = new String[2];
		top1 = Game.getTop3()[0].split(":");
		
		if(isNumeric(top1[1])) {
		min = Integer.valueOf(top1[1])/100;
		if(min<10) {
			minCompleter = "0";
		}else {minCompleter = "";}
		sec = Integer.valueOf(top1[1])-min*100;
		if(sec<10) {
			secCompleter = "0";
		}else {secCompleter = "";}
		g.drawString(minCompleter + min + ":" + secCompleter + sec, (Game.WIDTH*Game.SCALE)/2 + 70, (Game.HEIGHT*Game.SCALE)/2 - 20);
		
		}
		else {
			g.drawString(top1[1], (Game.WIDTH*Game.SCALE)/2 + 85, (Game.HEIGHT*Game.SCALE)/2 - 20);
		}
		g.drawString(top1[0], (Game.WIDTH*Game.SCALE)/2 - 94, (Game.HEIGHT*Game.SCALE)/2 - 20);
		
		
		//2
		String[] top2 = new String[2];
		top2 = Game.getTop3()[1].split(":");
		if(isNumeric(top2[1])) {
		min = Integer.valueOf(top2[1])/100;
		if(min<10) {
			minCompleter = "0";
		}else {minCompleter = "";}
		sec = Integer.valueOf(top2[1])-min*100;
		if(sec<10) {
			secCompleter = "0";
		}else {secCompleter = "";}
		g.drawString(minCompleter + min + ":" + secCompleter + sec, (Game.WIDTH*Game.SCALE)/2 + 70, (Game.HEIGHT*Game.SCALE)/2 + 30);
		
		}
		else {
			g.drawString(top2[1], (Game.WIDTH*Game.SCALE)/2 + 85, (Game.HEIGHT*Game.SCALE)/2 + 30);
		}
		g.drawString(top2[0], (Game.WIDTH*Game.SCALE)/2 - 94, (Game.HEIGHT*Game.SCALE)/2 + 30);
		
		//3
		String[] top3 = new String[2];
		top3 = Game.getTop3()[2].split(":");
		if(isNumeric(top3[1])) {
		min = Integer.valueOf(top3[1])/100;
		if(min<10) {
			minCompleter = "0";
		}else {minCompleter = "";}
		sec = Integer.valueOf(top3[1])-min*100;
		if(sec<10) {
			secCompleter = "0";
		}else {secCompleter = "";}
		g.drawString(minCompleter + min + ":" + secCompleter + sec, (Game.WIDTH*Game.SCALE)/2 + 70, (Game.HEIGHT*Game.SCALE)/2 + 80);
		}
		else {
			g.drawString(top3[1], (Game.WIDTH*Game.SCALE)/2 + 85, (Game.HEIGHT*Game.SCALE)/2 + 80);
		}
		g.drawString(top3[0], (Game.WIDTH*Game.SCALE)/2 - 94, (Game.HEIGHT*Game.SCALE)/2 + 80);
		
		
	}
}
