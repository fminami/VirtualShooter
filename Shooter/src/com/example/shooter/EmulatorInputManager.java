package com.example.shooter;

import android.content.Context;
import android.view.*;
import android.view.View.OnKeyListener;

import com.example.shooter.core.InputManager;

public class EmulatorInputManager extends InputManager implements ShooterInput, OnKeyListener {

	private static final float ANGLE = (float)(Math.PI / 180.0);
	
	private final float[] orientation = new float[3];
	
	private boolean shoot;
	private volatile boolean nextShoot;

	public EmulatorInputManager(Context context) {
		super(context);
	}

	@Override
	public void getOrientation(float[] value, int offset) {
		synchronized (orientation) {
			value[offset + 0] = orientation[0];
			value[offset + 1] = orientation[1];
			value[offset + 2] = orientation[2];
		}
	}
	
	@Override
	public boolean detectedShootAction(float[] value, int offset) {
		if(shoot){
			getOrientation(value, offset);
			return true;
		}
		
		return false;
	}

	@Override
	protected void update() {
		shoot = nextShoot;
		nextShoot = false;
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		synchronized (orientation) {
			if (event.getAction() == KeyEvent.ACTION_DOWN) {
				switch (keyCode) {
				case KeyEvent.KEYCODE_A: // left
					orientation[0] -= ANGLE;
					break;
				case KeyEvent.KEYCODE_D: // right
					orientation[0] += ANGLE;
					break;
				case KeyEvent.KEYCODE_W: // up
					orientation[1] -= ANGLE;
					break;
				case KeyEvent.KEYCODE_S: // down
					orientation[1] += ANGLE;
					break;
				case KeyEvent.KEYCODE_Q: // L
					orientation[2] -= ANGLE;
					break;
				case KeyEvent.KEYCODE_E: // R
					orientation[2] += ANGLE;
					break;
				case KeyEvent.KEYCODE_Z:
					orientation[0] = 0.0f;
					orientation[1] = 0.0f;
					orientation[2] = 0.0f;
					break;
				case KeyEvent.KEYCODE_X:
					nextShoot = true;
					break;
				}
			}
		}
		
		return false;
	}

}
