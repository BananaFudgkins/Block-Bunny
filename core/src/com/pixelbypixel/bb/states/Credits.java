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

public class Credits extends GameState {
	
	private World world;
	private GameButton backbutton;
	private BitmapFont font;
	private BitmapFont title;
	
	@SuppressWarnings("deprecation")
	public Credits(GameStateManager gsm) {
		
		super(gsm);
		
		world = new World(new Vector2(0, -9.8f * 5), true);
		
		Texture tex = Game.res.getTexture("arrowleft");
		backbutton = new GameButton(new TextureRegion(tex, 0, 0, 50, 50), 29, 135, cam);
		
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/ASCII.ttf"));
		font = gen.generateFont(12);
		title = gen.generateFont(20);
		
	}
	
	public void handleInput() {
		if(backbutton.isClicked()) gsm.setState(GameStateManager.MENU);
	}
	
	public void update(float dt) {
		
		handleInput();
		
		world.step(dt / 5, 8, 3);
		
		Menu.bg.update(dt);
		
		backbutton.update(dt);
		
	}
	
	public void render() {
		
		sb.setProjectionMatrix(cam.combined);
		
		Menu.bg.render(sb);
		
		backbutton.render(sb);
		
		sb.begin();
		font.setColor(Color.BLUE);
		font.draw(sb, "Levels 1-5", 120, 200);
		font.draw(sb, "Mike Sia", 127, 180);
		font.draw(sb, "Sprites", 130, 150);
		font.draw(sb, "Mike Sia", 126, 130);
		font.draw(sb, "Original source code", 80, 100);
		font.draw(sb, "Mike Sia", 125, 80);
		font.draw(sb, "Modified source code", 80, 60);
		font.draw(sb, "Deja Jackson", 115, 45);
		sb.end();
		
		sb.begin();
		title.setColor(Color.BLUE);
		title.draw(sb, "Credits", 113, 230);
		sb.end();
		
	}
	
	public void dispose() {
		
	}

}
