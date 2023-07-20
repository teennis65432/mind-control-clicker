package com.edisco;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Title extends BasicGameState{
	
	//Checks what gamemodes are unlocked
	static boolean jungUnlocked = false;
	static boolean alienUnlocked = false;
	
	static int currentState = 0;
	
	//Rectangle earth = new Rectangle(560, 300, 200, 50);
	//Rectangle jung = new Rectangle(560, 400, 200, 50);
	//Rectangle alien = new Rectangle(560, 500, 200, 50);
	//Rectangle exit = new Rectangle(560, 600, 200, 50);
	
	Image logo;
	Image earth;
	Image jung;
	Image alien;
	Image exit;
	
	int earthX = 560;
	int earthY = 300;
	int jungY = 400;
	int alienY = 500;
	int exitY = 600;
	
	java.awt.Font awtfont;
	UnicodeFont font;
	
	@Override
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		try{
			logo = new Image("assets/logo.png");
			earth = new Image("assets/earthbutton.png");
			jung = new Image("assets/jungbutton.png");
			alien = new Image("assets/alienbutton.png");
			exit = new Image("assets/exitbutton.png");
		} catch (SlickException e){
			e.printStackTrace();
		}
		
		try {
			awtfont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, org.newdawn.slick.util.ResourceLoader.getResourceAsStream("/Fonts/slkscr.ttf"));
			awtfont = awtfont.deriveFont(java.awt.Font.PLAIN, 18.f);
		
			font = new UnicodeFont(awtfont);		//Font for what's not selected
			font.addAsciiGlyphs();
			font.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
			font.addAsciiGlyphs();
			font.loadGlyphs();
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(GameContainer container, StateBasedGame sbg, Graphics g) throws SlickException {
		g.drawImage(logo, 350, 50);	//Title at top of screen
		
		g.setFont(font);
		
		//Drawing buttons
		g.drawImage(earth, 560, 300);
		g.drawImage(jung, earthX, jungY);
		g.drawImage(alien, earthX, alienY);
		g.drawImage(exit, earthX, exitY);
		
		g.drawString("Earth", 630, 310);
		g.drawString("Jungle", 620, 410);
		g.drawString("Aliens", 620, 510);
		g.drawString("Exit", 630, 610);
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		Input input = container.getInput();	//Getting input
		
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			int x = input.getMouseX();
			int y = input.getMouseY();
			
			if(x > earthX && x < (earthX + 200)  && y > earthY && y < (earthY + 50)){
				Main.game.enterState(1);	//Enters the Earth Gamemode (Earth.java)
				currentState = 1;
			}
			if(x > earthX && x < (earthX + 200)  && y > jungY && y < (jungY + 50)){
				Main.game.enterState(2);	//Enters the Earth Gamemode (Earth.java)
				currentState = 2;
			}
			if(x > earthX && x < (earthX + 200)  && y > alienY && y < (alienY + 50)){
				Main.game.enterState(3);	//Enters the Earth Gamemode (Earth.java)
				currentState = 3;
			}
			if(x > earthX && x < (earthX + 200)  && y > exitY && y < (exitY + 50)){
				container.exit();
			}
		}
	}

	@Override
	public int getID() {	//Returns the ID, which is 0
		return 0;
	}
	
}
