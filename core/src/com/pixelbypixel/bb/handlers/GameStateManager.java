package com.pixelbypixel.bb.handlers;

import java.util.Stack;

import com.pixelbypixel.bb.Game;
import com.pixelbypixel.bb.states.*;

public class GameStateManager {
	
	private Game game;
	
	private Stack<GameState> gameStates;
	
	public static final int MENU = 83774392;
	public static final int PLAY = 388031654;
	public static final int LEVEL_SELECT = -9238732;
	public static final int LEVEL_SELECT2 = 90;
	public static final int GAMEMODE_SELECT = 1000;
	public static final int CREDITS = 76453;
	public static final int ENDLESS = 010101010;
	public static final int RESTART = 99999999;
	
	public GameStateManager(Game game) {
		this.game = game;
		gameStates = new Stack<GameState>();
		pushState(MENU);
	}
	
	public void update(float dt) {
		gameStates.peek().update(dt);
	}
	
	public void render() {
		gameStates.peek().render();
	}
	
	public Game game() { return game; }
	
	private GameState getState(int state) {
		if(state == MENU) return new Menu(this);
		if(state == PLAY) return new Play(this);
		if(state == LEVEL_SELECT) return new LevelSelect(this);
		if(state == LEVEL_SELECT2) return new LevelSelect2(this);
		if(state == GAMEMODE_SELECT) return new GameModeSelect(this);
		if(state == CREDITS) return new Credits(this);
		if(state == RESTART) return new RestartScreen(this);
		return null;
	}
	
	public void setState(int state) {
		popState();
		pushState(state);
	}
	
	public void pushState(int state) {
		gameStates.push(getState(state));
	}
	
	public void popState() {
		GameState g = gameStates.pop();
		g.dispose();
	}
	
}
