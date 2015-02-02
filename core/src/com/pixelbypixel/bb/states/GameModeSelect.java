package com.pixelbypixel.bb.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.pixelbypixel.bb.Game;
import com.pixelbypixel.bb.handlers.GameButton;
import com.pixelbypixel.bb.handlers.GameStateManager;

public class GameModeSelect extends GameState {
	
	private World world;
	private BitmapFont font;
	private GameButton[][] levels;
	private GameButton backarrow;
	private GameButton endlessmode;

	@SuppressWarnings("deprecation")
	public GameModeSelect(GameStateManager gsm) {
		
		super(gsm);
		
		TextureRegion leveltile = new TextureRegion(Game.res.getTexture("hud"), 0, 0, 32, 32);
		levels = new GameButton[1][1];
		for(int row = 0; row < levels.length; row++) {
			for(int col = 0; col < levels[0].length; col++) {
				levels[row][col] = new GameButton(leveltile, 100, 130, cam);
				levels[row][col].setText(row * levels[0].length + col + 50 + "");
			}
		}
		
		world = new World(new Vector2(0, -9.8f * 5), true);
		
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ASCII.ttf"));
		font = gen.generateFont(12);
		
		Texture tex = Game.res.getTexture("arrowleft");
		backarrow = new GameButton(new TextureRegion(tex, 0, 0, 50, 50), 29, 135, cam);
		
		tex = Game.res.getTexture("EndlessMode");
		endlessmode = new GameButton(new TextureRegion(tex, 0, 0, 50, 50), 260, 135, cam);
		
	}
	
	public void handleInput() {
		if(backarrow.isClicked()) gsm.setState(GameStateManager.MENU);
	}

	public void update(float dt) {
		
		handleInput();
		
		for(int row = 0; row < levels.length; row++) {
			for(int col = 0; col < levels[0].length; col++) {
				levels[row][col].update(dt);
				if(levels[row][col].isClicked()) {
					Game.res.getSound("crystal").play();
					gsm.setState(GameStateManager.LEVEL_SELECT);
				}
			}
		}
		
		world.step(dt / 5, 8, 3);
		
		Menu.bg.update(dt);
		
		backarrow.update(dt);
		
		endlessmode.update(dt);
		
	}

	public void render() {
		
		sb.setProjectionMatrix(cam.combined);
		
		Menu.bg.render(sb);
		
		backarrow.render(sb);
		endlessmode.render(sb);
		
		sb.begin();
		font.setColor(Color.BLUE);
		font.draw(sb, "Classic", 75, 100);
		font.draw(sb, "Endless (coming soon!)", 165, 100);
		font.draw(sb, "Tap the 50 to play all 50 levels of the game.", 5, 230);
		//font.draw(sb, "Tap the bunny to play endless mode.", 40, 210);
		//font.draw(sb, "Endless", 70, 210);
		font.draw(sb, "Endless mode is coming soon.", 65, 210);
		sb.end();
		
		for(int row = 0; row < levels.length; row++) {
			for(int col = 0; col < levels[0].length; col++) {
				levels[row][col].render(sb);
			}
		}
		
	}

	public void dispose() {
		
	}
	
	

}
