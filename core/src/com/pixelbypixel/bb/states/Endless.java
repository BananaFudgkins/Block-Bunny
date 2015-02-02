package com.pixelbypixel.bb.states;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.pixelbypixel.bb.entities.Player;
import com.pixelbypixel.bb.handlers.BBContactListener;
import com.pixelbypixel.bb.handlers.GameStateManager;


public class Endless extends GameState {
	
	private World world;
	private BBContactListener cl;
	
	private Player player;
	
	private TiledMap tileMap;
	private int tileMapWidth;
	private int tileMapHeight;
	private int tileSize;
	private OrthogonalTiledMapRenderer tmRenderer;
	
	public Endless(GameStateManager gsm) {
		
		super(gsm);
		
	}
	
	public void handleInput() {
		
	}
	
	public void update(float dt) {
		
	}
	
	public void render() {
		
	}
	
	public void dispose() {}

}
