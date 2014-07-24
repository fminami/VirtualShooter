package com.example.shooter.core;

import java.util.ArrayList;

public class GameScene extends Scene {

	private final Game game;
	
	private final ArrayList<Camera> cameras = new ArrayList<Camera>();
	private final ArrayList<Light> lights = new ArrayList<Light>();
	
	public GameScene(Game game){
		super(game);
		
		this.game = game;
	}
	
	public Game getGame(){
		return game;
	}
	
	public int getCameraCount(){
		return cameras.size();
	}
	
	public Camera getCamera(int index){
		return cameras.get(index);
	}
	
	public int getLightCount(){
		return lights.size();
	}
	
	public Light getLight(int index){
		return lights.get(index);
	}
	
	@Override
	protected void onNodeAttached(Node node) {
		if(node instanceof Camera){
			cameras.add((Camera)node);
		}else if(node instanceof Light){
			lights.add((Light)node);
		}
	}
	
	@Override
	protected void onNodeDetached(Node node) {
		if(node instanceof Camera){
			cameras.remove((Camera)node);
		}else if(node instanceof Light){
			lights.remove((Light)node);
		}
	}
}
