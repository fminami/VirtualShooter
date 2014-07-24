package com.example.shooter.core;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.SurfaceHolder;

public class GameView extends GLSurfaceView {

	private final Game game;
	
	public GameView(Context context, Game game) {
		super(context);
		
		this.game = game;
		game.run();
		
		// setDebugFlags(DEBUG_CHECK_GL_ERROR);
		// setDebugFlags(DEBUG_LOG_GL_CALLS);
		setEGLContextClientVersion(2);
		
		setRenderer(game);
	}
	
	public Game getGame(){
		return game;
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		queueEvent(new Runnable(){
			@Override
			public void run() {
				game.onResume();
			}
		});
	}
	
	@Override
	public void onPause() {
		queueEvent(new Runnable(){
			@Override
			public void run() {
				game.onPause();
			}
		});
		
		super.onPause();		
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder holder){
		queueEvent(new Runnable(){
			@Override
			public void run() {
				game.onSurfaceDestroyed();
			}
		});
		
		super.surfaceDestroyed(holder);
	}

}
