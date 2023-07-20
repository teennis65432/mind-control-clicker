package com.edisco;

import java.awt.FontFormatException;
import java.io.IOException;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Victory extends BasicGameState{
	
	java.awt.Font awtfont;
	UnicodeFont font;
	
	@Override
	public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
		try {
			awtfont = java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, org.newdawn.slick.util.ResourceLoader.getResourceAsStream("/Fonts/slkscr.ttf"));
			awtfont = awtfont.deriveFont(java.awt.Font.PLAIN, 28.f);
		
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
		g.setFont(font);
		
		if(Title.currentState == 1){
			g.setBackground(new Color(50, 100, 150));
			g.drawString("Congratulations, you've conquered everyone on Earth!", 200, 300);
		} else if(Title.currentState == 2){
			g.setBackground(new Color(50, 100, 50));
			g.drawString("Congratulations, you've conquered every animal on Earth!", 200, 300);
		} else if(Title.currentState == 3){
			g.setBackground(new Color(100, 50, 100));
			g.drawString("Congratulations, you've conquered everyone on X-314!", 200, 300);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame sbg, int delta) throws SlickException {
		
	}

	@Override
	public int getID() {
		return 4;
	}
	
}
