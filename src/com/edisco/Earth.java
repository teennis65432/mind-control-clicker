package com.edisco;

import java.awt.FontFormatException;
import java.io.IOException;

import org.lwjgl.util.Timer;
import org.newdawn.slick.Color;
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

public class Earth extends BasicGameState{
	
	long score = 0L; 				//The score of the Overlord
	long population = 7462000000L;	//The number that the Overlord has to decrease to 0
					//^Equal to 7,462,000,000 for clarity
	long ab = 1L;					//"Active Bonus", the amount that a click will give to the score
	long pb = 0L;					//"Passive Bonus", the amount that accumulates passively per frame (locked at 60 frames in a second)
	
	Image clicker;					//The image that the player will click on for points
	
	Image mindRayIcon;
	Image armedSoldierIcon;
	Image neuralEmitterIcon;
	Image sponsorsIcon;
	Image satelliteControlIcon;
	
	Image button;
	
	Image menu;
	
	java.awt.Font awtfont;
	UnicodeFont font;
	
	long mousex = 0, mousey = 0;	//Tracks the mouse's current position
	int clickerX = 200;				//The Clicker's top left X
	int clickerY = 200; 			//The Clicker's top left Y
	float clickerScale= 2f;			//The scale that the clicker is drawn at
	
	long price1 = 10;				//Price of the active upgrade
	long amount1 = 0;				//Amount of times active upgrade was purchased
	long price2 = 50;				//Price of the first passive upgrade (Armed Soldiers)
	long amount2 = 0;				//Amount of times Armed Soldiers was purchased
	long price3 = 1000;				//Price of second passive upgrade (Neural Emitters)
	long amount3 = 0;				//Amount of times Neural Wave Emitters was purchased
	long price4 = 1000000;			//Price of third passive upgrade (Sponsors)
	long amount4 = 0;				//Amoutn of times Sponsors was purchased
	long price5 = 100000000;		//Price of fourth passive upgrade (Satellite Control)
	long amount5 = 0;				//Amount of times Satellite Control was purchased
	
	Rectangle aBuy = new Rectangle(1100, 70, 200, 50);	//Placeholder rectangle for the active upgrade buy button
	Rectangle p1Buy = new Rectangle(1100, 210, 200, 50);
	Rectangle p2Buy = new Rectangle(1100, 350, 200, 50);
	Rectangle p3Buy = new Rectangle(1100, 490, 200, 50);
	Rectangle p4Buy = new Rectangle(1100, 630, 200, 50);
	
	Timer timer = new Timer();		//Timer used to recalculated MPS (Minds-per-second)
	long MPS = 0;					//Minds per second
	long MPSTemp = 0;				//Takes the time to count MPS
	
	@Override	//The Initialization
	public void init(GameContainer app, StateBasedGame sbg) throws SlickException {
		try{
			clicker = new Image("assets/earthclicker.png");		//The sprite of the clicker
			
			//Icons
			mindRayIcon = new Image("assets/mindrayicon.png");
			armedSoldierIcon = new Image("assets/armedsoldiersicon.png");
			neuralEmitterIcon = new Image("assets/neuralemittersicon.png");
			sponsorsIcon = new Image("assets/sponsorsicon.png");
			satelliteControlIcon = new Image("assets/satellitecontrolicon.png");
			
			button = new Image("assets/exitbutton.png");
			
			menu = new Image("assets/upgrademenu.png");
			
			awtfont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, org.newdawn.slick.util.ResourceLoader.getResourceAsStream("/Fonts/slkscr.ttf"));
			awtfont = awtfont.deriveFont(java.awt.Font.PLAIN, 14.f);
			
			font = new UnicodeFont(awtfont);		//Font for what's not selected
			font.addAsciiGlyphs();
			font.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
			font.addAsciiGlyphs();
			font.loadGlyphs();
		} catch (SlickException e){
			e.printStackTrace();
		} catch (IOException e){
			e.printStackTrace();
		}  catch (FontFormatException e){
			e.printStackTrace();
		}
	}

	@Override	//The Graphics Loop
	public void render(GameContainer app, StateBasedGame sbg, Graphics g) throws SlickException {
		Input input = app.getInput();	//Gets the input
		
		g.setBackground(new Color(50, 100, 150));	//Sets the background of the game
		
		font.drawString(200, 100, "Minds Controlled: "+score);			//Shows the current score
		font.drawString(200, 120, "Population Left: "+population);		//Shows the population left
		font.drawString(200, 140, "Minds-per-second: "+MPS);
		
		g.setFont(font);
		
		//The Investment Menu
		menu.draw(690, 10);
		g.drawString("Upgrades", 975, 15);
		
		//The first upgrade (called the active upgrade)
		mindRayIcon.draw(720, 40);							//Draws an image for the upgrade
		g.drawString("Mind Control Ray", 850, 40);		//Name of the upgrade
		g.drawString("1 Mind-Per-Click", 850, 60);		//Tells what the upgrade will give
		g.drawString("Current Bonus: "+ab, 850, 80);	//Tells how much the upgrade is currently giving
		button.draw(1100, 70);									//A button will go here
		g.drawString("Buy for "+price1, 1125, 80);		//Names the price of the upgrade
		
		//The second upgrade (called Armed Soldiers)
		armedSoldierIcon.draw(720, 180);
		g.drawString("Armed Soldiers", 850, 180);
		g.drawString("60 Minds-per-second", 850, 200);
		g.drawString("Current Bonus: "+(amount2 * 60), 850, 220);
		button.draw(1100, 210);
		g.drawString("Buy for "+price2, 1125, 220);
		
		//The third upgrade (called Neural Wave Emitters)
		neuralEmitterIcon.draw(720, 320);
		g.drawString("Neural Wave Emitters", 850, 320);
		g.drawString("300 Minds-per-second", 850, 340);
		g.drawString("Current Bonus: "+(amount3 * 300), 850, 360);
		button.draw(1100, 350);
		g.drawString("Buy for "+price3, 1125, 360);
		
		//The fourth upgrade (called Sponsors)
		sponsorsIcon.draw(720, 460);
		g.drawString("Sponsors", 850, 460);
		g.drawString("7200 Minds-per-second", 850, 480);
		g.drawString("Current Bonus: "+(amount4 * 7200), 850, 500);
		button.draw(1100, 490);
		g.drawString("Buy for "+price4, 1125, 500);
		
		//The fifth upgrade (called Satellite Control)
		satelliteControlIcon.draw(720, 600);
		g.drawString("Satellite Control", 850, 600);
		g.drawString("60,000 Minds-per-second", 850, 620);
		g.drawString("Current Bonus: "+(amount5 * 60000), 850, 640);
		button.draw(1100, 630);
		g.drawString("Buy for "+price5, 1125, 640);
		
		//Slightly decreases the clicker's size when clicked on
		if(input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)){
			int x = input.getMouseX();
			int y = input.getMouseY();
			
			//The clicker
			if(x > clickerX && x < clickerX+(clicker.getWidth()*clickerScale) && y > clickerY && y < clickerY+(clicker.getHeight()*clickerScale)){
				clicker.draw(clickerX+10, clickerY+10, (clickerScale-.2f));
			} else {
				clicker.draw(clickerX, clickerY, clickerScale);
			}
			
		} else {
			clicker.draw(clickerX, clickerY, clickerScale);
		}
	}

	@Override	//The Update Loop
	public void update(GameContainer app, StateBasedGame sbg, int delta) throws SlickException {
		Input input = app.getInput();	//Allows input
		Timer.tick();
		
		if(timer.getTime() > 1){
			MPS = MPSTemp;
			MPSTemp = 0;
			timer.set(0);
		}
		
		//Gives us the coordinates of the mouse so we can track it, purely for debugging. The game never actually uses these.
		mousex = input.getMouseX();
		mousey = input.getMouseY();
		
		//Kills the game if escape is pressed
		if(input.isKeyPressed(Input.KEY_ESCAPE)) {
			app.exit();
	    }
		
		//This entire block tells the game what to do when the left mouse button is clicked, depending based on where the mouse is at the time
		if(input.isMousePressed(Input.MOUSE_LEFT_BUTTON)){
			//Naming variables for the mouse's current position
			int x = input.getMouseX();
			int y = input.getMouseY();
			
			//When the clicker is clicked on, it gives score to the player and removes it from the population
			if(x > clickerX && x < clickerX+(clicker.getWidth()*clickerScale) && y > clickerY && y < clickerY+(clicker.getHeight()*clickerScale)){
				//Scoring stuff
				score += ab;
				population -= ab;
			}
			
			//The first upgrade's button (the active upgrade)
			if(x > aBuy.getX() && x < (aBuy.getX() + aBuy.getWidth()) && y > aBuy.getY() && y < (aBuy.getY() + aBuy.getHeight())){
				if(price1 <= score){
					score -= price1;		//Removes the score from the player
					population += price1;	//Re-adds the score to the population
					
					price1 += (price1*.2);	//Increments the price of the upgrade
					ab += 1;				//Adds to the active bonus
				}
			}
			
			//The second upgrade's button (Armed Soldiers)
			if(x > p1Buy.getX() && x < (p1Buy.getX() + p1Buy.getWidth()) && y > p1Buy.getY() && y < (p1Buy.getY() + p1Buy.getHeight())){
				if(price2 <= score){
					score -= price2;		//Removes the score from the player
					population += price2;	//Re-adds the score to the population
					
					price2 += (price2*.2);	//Increments the price of the upgrade
					pb += 1;				//Adds to the passive bonus
				}
			}
			
			//The third upgrade's button (Neural Wave Emitters)
			if(x > p2Buy.getX() && x < (p2Buy.getX() + p2Buy.getWidth()) && y > p2Buy.getY() && y < (p2Buy.getY() + p2Buy.getHeight())){
				if(price3 <= score){
					score -= price3;		//Removes the score from the player
					population += price3;	//Re-adds the score to the population
					
					price3 += (price3*.2);	//Increments the price of the upgrade
					pb += 5;				//Adds to the passive bonus
				}
			}
			
			//The fourth upgrade's button (Sponsors)
			if(x > p3Buy.getX() && x < (p3Buy.getX() + p3Buy.getWidth()) && y > p3Buy.getY() && y < (p3Buy.getY() + p3Buy.getHeight())){
				if(price4 <= score){
					score -= price4;		//Removes the score from the player
					population += price4;	//Re-adds the score to the population
					
					price4 += (price4*.2);	//Increments the price of the upgrade
					pb += 120;				//Adds to the passive bonus
				}
			}
			
			//The fifth upgrade's button (Satellite Control)
			if(x > p4Buy.getX() && x < (p4Buy.getX() + p4Buy.getWidth()) && y > p4Buy.getY() && y < (p4Buy.getY() + p4Buy.getHeight())){
				if(price5 <= score){
					score -= price5;		//Removes the score from the player
					population += price5;	//Re-adds the score to the population
					
					price5 += (price5*.2);	//Increments the price of the upgrade
					pb += 1000;				//Adds to the passive bonus
				}
			}
			
		}
		
		//Passively gives you extra humans
		score += pb;
		population -= pb;
		MPSTemp += pb;
		
		//The win condition
		if(population <= 0){
			if(Title.jungUnlocked == false){
				Title.jungUnlocked = true;
				Main.game.enterState(4);
			}
		}
		
	}

	@Override
	public int getID() {	//Returns the ID of this state, which is 1
		return 1;
	}

}
