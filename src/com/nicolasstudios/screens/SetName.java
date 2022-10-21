package com.nicolasstudios.screens;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.nicolasstudios.main.Game;
import com.nicolasstudios.main.Menu;

public class SetName extends JFrame implements ActionListener{
	
	JButton button;
	JTextField textField;
	JLabel label;
	JPanel space1;
	JPanel space2;
	
	public SetName(){
		
		label = new JLabel("Digite o seu nome");
		label.setFont(new Font("arial", Font.PLAIN, 32));
		
		space1 = new JPanel();
		space1.setPreferredSize(new Dimension(300, 50));
		
		button = new JButton("Salvar");
		button.setBackground(new Color(64, 125, 56));
		button.setForeground(Color.white);
		button.setMargin(new Insets(5, 20, 5, 20));
		
		space2 = new JPanel();
		space2.setPreferredSize(new Dimension(300, 5));
		
		textField = new JTextField();
		textField.setPreferredSize(new Dimension(250, 40));
		textField.setFont(new Font("arial", Font.PLAIN, 28));
		textField.setForeground(new Color(64, 125, 56));
		textField.setDocument(new JTextFieldLimit(10));
		
		
		this.add(label);
		this.add(space1);
		this.add(textField);
		this.add(space2);
		this.add(button);
		
		Image icon = null;
		try {
			icon = ImageIO.read(getClass().getResource("/icon.png"));
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		this.setIconImage(icon);
		this.setPreferredSize(new Dimension(300, 250));
		this.setVisible(true);
		this.setResizable(false);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new FlowLayout());
		this.setPreferredSize(getPreferredSize());
		
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String[] correctName = textField.getText().split(":");
				correctName = correctName[0].split("'");
				
				Game.TimeAdd(correctName[0], String.valueOf((Game.min*100)+Game.sec));
				Game.frame.setVisible(true);
				Game.gameState = "MENU";
				Game.disposeSetName = true;
				Menu.won = false;
				
			}
			
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
