package com.edisco;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Main extends StateBasedGame{

	public Main(String title) {	//Constructor for the game object
		super(title);			//returns the title of the game
	}
	
	public static Main game = new Main("Mind Control Clicker");
	
	public static void main(String[] args) {	//For those that don't know Java, this is the first method run by the runtime in any and every case
		try {
			AppGameContainer app = new AppGameContainer(game);
			app.setDisplayMode(1360, 768, true);	//making container fit to a 1360x768 res (so everything fits and looks nice)
			app.setShowFPS(false);					//Removes the annoying fps string
			app.setVSync(false);					//Puts in VSync
			app.setTargetFrameRate(60);				//Syncs game to 60fps, otherwise the game moves at hyperspeed
			app.start();							//Starts the game
		} catch (SlickException e) {	//Catch the exception
			e.printStackTrace();		//Print the exception
		}
	}

	@Override
	public void initStatesList(GameContainer arg0) throws SlickException {	//The list of states in this game
		addState(new Title()); //The Title Screen (ID: 0)
		addState(new Earth()); //The Earth Gamemode (ID: 1)
		addState(new Jungle());//The Jungle Gamemode (ID: 2)
		addState(new Aliens());//The Aliens Gamemode (ID: 3)
		addState(new Victory()); //The Victory Screen (ID: 4)
	}

}
