package com.example.shooter;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.example.shooter.ActionDetector.Action;
import com.example.shooter.core.InputManager;
import com.example.shooter.util.Vector3;

public class ShooterInputManager extends InputManager implements ShooterInput{
	
	private static final int ORIENTATION;
	private static final int CENTER;
	private static final int GYROSCOPE;
	private static final int TOTAL;
	static{
		int offset = 0;
		ORIENTATION = offset;
		offset += Vector3.SIZE;
		CENTER = offset;
		offset += Vector3.SIZE;
		GYROSCOPE = offset;
		offset += Vector3.SIZE;
		TOTAL = offset;
	}
	
	private final SensorManager sensors;
	private final SensorData data;
	
	private final ActionDetector actions;
	
	private final float[] buffer = new float[TOTAL];

	public ShooterInputManager(Context context) {
		super(context);
		
		this.sensors = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
		this.data = new SensorData(context);
		
		this.actions = new ActionDetector();
	}
	
	@Override
	public void getOrientation(float[] value, int offset) {
		value[offset + 0] = buffer[ORIENTATION + 0] - buffer[CENTER + 0];
		value[offset + 1] = buffer[ORIENTATION + 1] - buffer[CENTER + 1];
		value[offset + 2] = buffer[ORIENTATION + 2] - buffer[CENTER + 2];
	}
	
	@Override
	public void getRawOrientation(float[] value, int offset) {
		value[offset + 0] = buffer[ORIENTATION + 0];
		value[offset + 1] = buffer[ORIENTATION + 1];
		value[offset + 2] = buffer[ORIENTATION + 2];
	}
	
	public void getCenter(float[] value, int offset){
		value[offset + 0] = buffer[CENTER + 0];
		value[offset + 1] = buffer[CENTER + 1];
		value[offset + 2] = buffer[CENTER + 2];
	}
	
	@Override
	public void setCenter(float[] value, int offset) {
		buffer[CENTER + 0] = value[offset + 0];
		buffer[CENTER + 1] = value[offset + 1];
		buffer[CENTER + 2] = value[offset + 2];
	}
	
	public void getGyroscope(float[] value, int offset) {
		value[offset + 0] = buffer[GYROSCOPE + 0];
		value[offset + 1] = buffer[GYROSCOPE + 1];
		value[offset + 2] = buffer[GYROSCOPE + 2];
	}
	
	@Override
	public boolean detectedShootAction(float[] value, int offset){
		for(int i = 0, count = actions.getActionCount(); i < count; i++){
			if(actions.getAction(i) == Action.SHOOT){
				actions.getSnapshotOrientation(value, offset, i);
				Vector3.sub(value, offset, value, offset, buffer, CENTER);
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	protected void update() {
		data.getOrientation(buffer, ORIENTATION);
		data.getGyroscope(buffer, GYROSCOPE);

		actions.update(buffer, ORIENTATION, buffer, GYROSCOPE);
	}

	@Override
	protected void onResume() {
		actions.clear();
		
		sensors.registerListener(data,
				sensors.getDefaultSensor(Sensor.TYPE_GRAVITY),
				SensorManager.SENSOR_DELAY_GAME);
		sensors.registerListener(data,
				sensors.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
				SensorManager.SENSOR_DELAY_GAME);
		sensors.registerListener(data,
				sensors.getDefaultSensor(Sensor.TYPE_GYROSCOPE),
				SensorManager.SENSOR_DELAY_GAME);
	}
	
	@Override
	protected void onPause() {
		sensors.unregisterListener(data);
	}
}
