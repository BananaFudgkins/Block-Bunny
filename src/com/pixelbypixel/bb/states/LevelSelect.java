package com.pixelbypixel.bb.states;

import static com.pixelbypixel.bb.handlers.B2DVars.PPM;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.pixelbypixel.bb.Game;
import com.pixelbypixel.bb.handlers.Animation;
import com.pixelbypixel.bb.handlers.B2DVars;
import com.pixelbypixel.bb.handlers.GameButton;
import com.pixelbypixel.bb.handlers.GameStateManager;

public class LevelSelect extends GameState {
	
	private TextureRegion reg;
	
	private GameButton[][] buttons;
	private GameButton rightarrowButton;
	private GameButton leftarrowButton;
	private Animation animation;
	
	private World world;
	
	public LevelSelect(GameStateManager gsm) {
		
		super(gsm);
		
		reg = new TextureRegion(Game.res.getTexture("bgs"), 0, 0, 320, 240);
		
		TextureRegion buttonReg = new TextureRegion(Game.res.getTexture("hud"), 0, 0, 32, 32);
		buttons = new GameButton[5][5];
		for(int row = 0; row < buttons.length; row++) {
			for(int col = 0; col < buttons[0].length; col++) {
				buttons[row][col] = new GameButton(buttonReg, 80 + col * 40, 200 - row * 40 + 15, cam);
				buttons[row][col].setText(row * buttons[0].length + col + 1 + "");
			}
		}
		
		cam.setToOrtho(false, Game.V_WIDTH, Game.V_HEIGHT);
		
		world = new World(new Vector2(0, -9.8f * 5), true);
		
		Texture tex = Game.res.getTexture("arrowright");
		rightarrowButton = new GameButton(new TextureRegion(tex, 0, 0, 50, 50), 290, 132, cam);
		
		tex = Game.res.getTexture("arrowleft");
		leftarrowButton = new GameButton(new TextureRegion(tex, 0, 0, 50, 50), 29, 135, cam);
		
		createTileBodies();
		
	}
	
	private void createTileBodies() {
		// top platform
				BodyDef tpbdef = new BodyDef();
				tpbdef.type = BodyType.StaticBody;
				tpbdef.position.set(160 / PPM, 180 / PPM);
				Body tpbody = world.createBody(tpbdef);
				PolygonShape tpshape = new PolygonShape();
				tpshape.setAsBox(120 / PPM, 1 / PPM);
				FixtureDef tpfdef = new FixtureDef();
				tpfdef.shape = tpshape;
				tpfdef.filter.categoryBits = B2DVars.BIT_TOP_PLATFORM;
				tpfdef.filter.maskBits = B2DVars.BIT_TOP_BLOCK;
				tpbody.createFixture(tpfdef);
				tpshape.dispose();
				
				// bottom platform
				BodyDef bpbdef = new BodyDef();
				bpbdef.type = BodyType.StaticBody;
				bpbdef.position.set(160 / PPM, 130 / PPM);
				Body bpbody = world.createBody(bpbdef);
				PolygonShape bpshape = new PolygonShape();
				bpshape.setAsBox(120 / PPM, 1 / PPM);
				FixtureDef bpfdef = new FixtureDef();
				bpfdef.shape = bpshape;
				bpfdef.filter.categoryBits = B2DVars.BIT_BOTTOM_PLATFORM;
				bpfdef.filter.maskBits = B2DVars.BIT_BOTTOM_BLOCK;
				bpbody.createFixture(bpfdef);
				bpshape.dispose();
	}
	
	public void handleInput() {
		if(rightarrowButton.isClicked()) gsm.setState(GameStateManager.LEVEL_SELECT2);
		if(leftarrowButton.isClicked()) gsm.setState(GameStateManager.MENU);
	}
	
	public void update(float dt) {
		
		handleInput();
		
		for(int row = 0; row < buttons.length; row++) {
			for(int col = 0; col < buttons[0].length; col++) {
				buttons[row][col].update(dt);
				if(buttons[row][col].isClicked()) {
					Play.level = row * buttons[0].length + col + 1;
					Game.res.getSound("levelselect").play();
					gsm.setState(GameStateManager.PLAY);
				}
			}
		}
		
		leftarrowButton.update(dt);
		rightarrowButton.update(dt);
		
		world.step(dt / 5, 8, 3);
		
		Menu.bg.update(dt);
		
	}
	
	public void render() {
		
		sb.setProjectionMatrix(cam.combined);
		
		sb.begin();
		sb.draw(reg, 0, 0);
		sb.end();
		
		Menu.bg.render(sb);
		
		for(int row = 0; row < buttons.length; row++) {
			for(int col = 0; col < buttons[0].length; col++) {
				buttons[row][col].render(sb);
			}
		}
		
		rightarrowButton.render(sb);
		
		leftarrowButton.render(sb);
		
		// draw character selection
		sb.draw(animation.getFrame(), 146, 100);
		
	}
	
	public void dispose() {
		// everything is in the resource manager com.neet.blockbunny.handlers.Content
	}
	
}
