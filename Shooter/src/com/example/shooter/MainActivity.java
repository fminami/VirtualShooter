package com.example.shooter;

import com.example.shooter.core.*;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.KeyEvent;

public class MainActivity extends Activity {

	private GLSurfaceView view;
	
	private EmulatorInputManager emulator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		final ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		final ConfigurationInfo cinfo = manager.getDeviceConfigurationInfo();

		if (cinfo.reqGlEsVersion >= 0x20000 || Utils.isEmulator()) {
			view = new GameView(this, newGame());
			setContentView(view);
		} else {
			setContentView(R.layout.activity_main);
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		if (view != null) view.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();

		if (view != null) view.onPause();
	}
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if(emulator != null){
			emulator.onKey(view, event.getKeyCode(), event);
		}
		
		return super.dispatchKeyEvent(event);
	}

	private Game newGame() {
		InputManager input;
		ResourceManager resources;
		if(Utils.isEmulator()){
			input = emulator = new EmulatorInputManager(this);
		}else{
			input = new ShooterInputManager(this);
		}
		resources = new ResourceManager(getAssets());
		return new ShooterGame(input, resources);
	}

}
