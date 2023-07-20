package com.edisco;

import java.awt.FontFormatException;
import java.io.IOException;

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

public class Aliens extends BasicGameState{
	
	long score = 0L; 			//The score of the Overlord
	long population = 3200000L;		//The number that the Overlord has to decrease to 0
					//^Equal to 3,200,000 for clarity
	long ab = 1L;					//"Active Bonus", the amount that a click will give to the score
	long ctc = 10L;					//"Clicks-Until-Clicked", the amount of times that the clicker will have to be clicked until a bonus is applied
	long maxctc = 10L;				//Maximum Clicks til clicked
	
	Image clicker;					//The image that the player will click on for points
	
	Image sabotageIcon;
	Image UFOIcon;
	Image warshipsIcon;
	Image mothershipIcon;
	Image deathRayIcon;
	
	Image button;
	
	Image menu;
	
	java.awt.Font awtfont;
	UnicodeFont font;
	
	long mousex = 0, mousey = 0;	//Tracks the mouse's current position
	int clickerX = 200;				//The Clicker's top left X
	int clickerY = 200; 			//The Clicker's top left Y
	float clickerScale= 2f;			//The scale that the clicker is drawn at
	
	long price1 = 25;				//Price of the first active (Sabotage)
	long amount1 = 0;				//Amount of times purchased
	long bonus1 = 1;				//Reduces ctc
	long price2 = 100;				//Price of second active (UFOs)
	long amount2 = 0;				//Amount of UFOs
	long bonus2 = 5;				//Bonus of UFOs
	long price3 = 1000;				//Price of third active (Warships)
	long amount3 = 0;				//Amount of Warships owned
	long bonus3 = 100;				//Bonus of Warships
	long price4 = 10000;			//Price of fourth active (Mothership)
	long amount4 = 0;				//Amount of Motherships called in
	long bonus4 = 1000;				//Bonus from Motherships
	long price5 = 100000;			//Price of fifth active (Death Ray)
	long amount5 = 0;				//Amount of Death Rays aimed at X-314
	long bonus5 = 50000;			//Bonus from Death Rays
	
	Rectangle aBuy = new Rectangle(1100, 70, 200, 50);	//Placeholder rectangle for the active upgrade buy button
	Rectangle p1Buy = new Rectangle(1100, 210, 200, 50);
	Rectangle p2Buy = new Rectangle(1100, 350, 200, 50);
	Rectangle p3Buy = new Rectangle(1100, 490, 200, 50);
	Rectangle p4Buy = new Rectangle(1100, 630, 200, 50);
	
	@Override	//The Initialization
	public void init(GameContainer app, StateBasedGame sbg) throws SlickException {
		try{
			clicker = new Image("assets/ayylmao.png");	//The sprite of the clicker
			
			sabotageIcon = new Image("assets/sabotageicon.png");
			UFOIcon = new Image("assets/UFOicon.png");
			warshipsIcon = new Image("assets/warshipsicon.png");
			mothershipIcon = new Image("assets/mothershipicon.png");
			deathRayIcon = new Image("assets/deathrayicon.png");
			
			button = new Image("assets/exitbutton.png");
			
			menu = new Image("assets/upgrademenual.png");
			
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
		} catch (FontFormatException e){
			e.printStackTrace();
		}
	}

	@Override	//The Graphics Loop
	public void render(GameContainer app, StateBasedGame sbg, Graphics g) throws SlickException {
		Input input = app.getInput();	//Gets the input
		
		g.setFont(font);
		
		g.setBackground(new Color(100, 50, 100));	//Sets the background of the game
		
		g.drawString("Aliens controlled: "+score, 200, 100);			//Shows the current score
		g.drawString("Population left: "+population, 200, 120);		//Shows the population left
		g.drawString("Clicks Needed: "+ctc, 200, 140);
		
		//The Investment Menu (Right now a placeholder for it)
		menu.draw(690, 10);
		g.drawString("Upgrades", 975, 20);
		
		//The first upgrade (Sabotage)
		sabotageIcon.draw(720, 40);							//Draws an image for the upgrade
		g.drawString("Sabotage", 850, 40);				//Name of the upgrade
		g.drawString("-1 Required Click", 850, 60);		//Tells what the upgrade will give
		g.drawString("Current Decrease: "+(amount1*bonus1), 850, 80);	//Tells how much the upgrade is currently giving
		button.draw(1100, 70);									//A button will go here
		g.drawString("Buy for "+price1, 1125, 80);		//Names the price of the upgrade
		
		//The second upgrade (UFOs)
		UFOIcon.draw(720, 180);
		g.drawString("UFOs", 850, 180);
		g.drawString(bonus2+" Minds-per-click", 850, 200);
		g.drawString("Current Bonus: "+(amount2 * bonus2), 850, 220);
		button.draw(1100, 210);
		g.drawString("Buy for "+price2, 1125, 220);
		
		//The third upgrade (Warships)
		warshipsIcon.draw(720, 320);
		g.drawString("Warships", 850, 320);
		g.drawString(bonus3+" Minds-per-click", 850, 340);
		g.drawString("Current Bonus: "+(amount3 * bonus3), 850, 360);
		button.draw(1100, 350);
		g.drawString("Buy for "+price3, 1125, 360);
		
		//The fourth upgrade (Mothership)
		mothershipIcon.draw(720, 460);
		g.drawString("Mothership", 850, 460);
		g.drawString(bonus4+" Minds-per-click", 850, 480);
		g.drawString("Current Bonus: "+(amount4 * bonus4), 850, 500);
		button.draw(1100, 490);
		g.drawString("Buy for "+price4, 1125, 500);
		
		//The fifth upgrade (Death Ray)
		deathRayIcon.draw(720, 600);
		g.drawString("Death Ray", 850, 600);
		g.drawString(bonus5+" Minds-per-click", 850, 620);
		g.drawString("Current Bonus: "+(amount5 * bonus5), 850, 640);
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
				ctc--;
				if(ctc == 0){
					ctc = maxctc;
					score += ab;
					population -= ab;
				}
			}
			
			//The first upgrade's button (Sabotage)
			if(x > aBuy.getX() && x < (aBuy.getX() + aBuy.getWidth()) && y > aBuy.getY() && y < (aBuy.getY() + aBuy.getHeight()) && maxctc > 1){
				if(price1 <= score){
					score -= price1;		//Removes the score from the player
					population += price1;	//Re-adds the score to the population
					
					price1 += (price1*.2);	//Increments the price of the upgrade
					maxctc -= bonus1;
					ctc -= bonus1;
				}
			}
			
			//The second upgrade's button (UFOs)
			if(x > p1Buy.getX() && x < (p1Buy.getX() + p1Buy.getWidth()) && y > p1Buy.getY() && y < (p1Buy.getY() + p1Buy.getHeight())){
				if(price2 <= score){
					score -= price2;
					population += price2;
					
					price2 += (price2*.2);
					ab += bonus2;				
				}
			}
			
			//The third upgrade's button (Warships)
			if(x > p2Buy.getX() && x < (p2Buy.getX() + p2Buy.getWidth()) && y > p2Buy.getY() && y < (p2Buy.getY() + p2Buy.getHeight())){
				if(price3 <= score){
					score -= price3;		
					population += price3;	
					
					price3 += (price3*.2);	
					ab += bonus3;				
				}
			}
			
			//The fourth upgrade's button (Mothership)
			if(x > p3Buy.getX() && x < (p3Buy.getX() + p3Buy.getWidth()) && y > p3Buy.getY() && y < (p3Buy.getY() + p3Buy.getHeight())){
				if(price4 <= score){
					score -= price4;
					population += price4;
					
					price4 += (price4*.2);
					ab += bonus4;
				}
			}
			
			//The fifth upgrade's button (Death Ray)
			if(x > p4Buy.getX() && x < (p4Buy.getX() + p4Buy.getWidth()) && y > p4Buy.getY() && y < (p4Buy.getY() + p4Buy.getHeight())){
				if(price5 <= score){
					score -= price5;
					population += price5;
					
					price5 += (price5*.2);
					ab += bonus5;
				}
			}
			
		}
		
		if(population <= 0){
			Main.game.enterState(4);
		}
		
	}

	@Override
	public int getID() {	//Returns the ID of this state, which is 3
		return 3;
	}

}
