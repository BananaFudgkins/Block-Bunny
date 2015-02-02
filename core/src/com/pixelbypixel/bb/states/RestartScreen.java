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
import com.pixelbypixel.bb.handlers.Background;
import com.pixelbypixel.bb.handlers.GameButton;
import com.pixelbypixel.bb.handlers.GameStateManager;

public class RestartScreen extends GameState {
	
	private World world;
	private Background bg;
	private GameButton restart;
	private GameButton levelselect;
	private BitmapFont font;
	private BitmapFont font2;
	
	@SuppressWarnings("deprecation")
	public RestartScreen(GameStateManager gsm) {
		
		super(gsm);
		
		Texture tex = Game.res.getTexture("menu");
		bg = new Background(new TextureRegion(tex), cam, 1f);
		bg.setVector(-20, 0);
		
		cam.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);
		
		world = new World(new Vector2(0, -9.8f * 5), true);
		
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ASCII.ttf"));
		font = gen.generateFont(20);
		font2 = gen.generateFont(12);
		
		tex = Game.res.getTexture("arrowleft");
		levelselect = new GameButton(new TextureRegion(tex, 0, 0, 50, 50), 29, 135, cam);
		
		tex = Game.res.getTexture("restart");
		restart = new GameButton(new TextureRegion(tex, 0, 0, 30, 30), 200, 135, cam);
		
	}
	
	public void handleInput() {
		if(levelselect.isClicked() && Play.level <= 25) {
			gsm.setState(GameStateManager.LEVEL_SELECT);
		} else if(levelselect.isClicked() && Play.level >= 26) {
			gsm.setState(GameStateManager.LEVEL_SELECT2);
		}
		if(restart.isClicked()) {
			Game.res.getSound("levelselect").play();
			gsm.setState(GameStateManager.PLAY);
			// restart the player on the current level
			for(int i = 0; i < Play.level; i++) {
				if(Play.level == i) Play.level = i;
			}
		}
	}
	
	public void update(float dt) {
		
		handleInput();
		
		world.step(dt / 5, 8, 3);
		
		bg.update(dt);
		
		levelselect.update(dt);
		
		restart.update(dt);
		
	}
	
	public void render() {
		
		sb.setProjectionMatrix(cam.combined);
		
		bg.render(sb);
		
		levelselect.render(sb);
		
		restart.render(sb);
		
		sb.begin();
		font.setColor(Color.BLUE);
		font.draw(sb, "You died!", 100, 200);
		font.draw(sb, "Restart?", 80, 135);
		sb.end();
		
		sb.begin();
		font2.setColor(Color.BLUE);
		font2.draw(sb, "Tap the left arrow to go back", 50, 80);
		font2.draw(sb, "to level selection.", 90, 60);
		font2.draw(sb, "Tap this", 240, 150);
		font2.draw(sb, "button to", 237, 130);
		font2.draw(sb, "restart", 240, 110);
		sb.end();
		
	}
	
	public void dispose() {}

}
