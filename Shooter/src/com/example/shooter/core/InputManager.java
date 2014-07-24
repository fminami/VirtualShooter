package com.example.shooter.core;

import android.content.Context;

public abstract class InputManager {

	private final Context context;

	public InputManager(Context context) {
		this.context = context;
	}
	
	public Context getContext(){
		return context;
	}
	
	protected abstract void update();
		
	protected void onResume(){}
	
	protected void onPause(){}

}
