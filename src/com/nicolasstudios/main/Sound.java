package com.nicolasstudios.main;
import java.io.*;

import javax.sound.sampled.*;


public class Sound {
	public static boolean secondPlay = false;

	public static class Clips{
		
		public Clip[] clips;
		private int p;
		public int count;
		
		
		public Clips(byte[]  buffer, int count) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
			if(buffer == null) {
				return;
			}
			clips = new Clip[count];
			this.count = count;
			for(int i = 0; i < count; i++) {
				clips[i] = AudioSystem.getClip();
				clips[i].open(AudioSystem.getAudioInputStream(new ByteArrayInputStream(buffer)));
			}
			
			
		}
		
		public void play() {
		if(!Game.hasSounds) {
			return;
		}
		else {
			if(clips == null) {
				return;
			}
			clips[p].stop();
			clips[p].setFramePosition(0);
			clips[p].start();
			p++;
			if(p>=count) {
				p = 0;
			}
			secondPlay = true;
		}
		}
		public void loop() {
			if(clips ==null) {
				return;
			}
			clips[p].loop(300);
			
		}
		public void stop() {
			clips[p].stop();
		}
		
		
	}
	public Clips music1;
	public Clips ai1;
	public Clips ai2;
	public Clips ai3;
	
	public void carregaSons() {
		
		if(ai1 == null) {
			
		ai1 = load("/ai1.wav", 2);
		ai2 = load("/ai2.wav", 3);
		ai3 = load("/ai3.wav", 4);
		}
	}
	
	public static Clips pimbada = load("/pimbada.wav", 11);
	public static Clips load(String name, int count) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataInputStream dis = new DataInputStream(Sound.class.getResourceAsStream(name));
			
			byte[] buffer = new byte[1024];
			int read = 0;
			while((read = dis.read(buffer)) >= 0) {
				baos.write(buffer, 0, read);
			}
			dis.close();
			byte[] data = baos.toByteArray();
			return new Clips(data, count);
		}catch(Exception e) {
			try {
				return new Clips(null, 0);
			}catch(Exception ee) {
				return null;
			}
		}
	}
	
	
}
