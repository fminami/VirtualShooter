package com.example.shooter;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.*;

import com.example.shooter.util.Matrix;
import com.example.shooter.util.Vector3;

public class SensorData implements SensorEventListener {
	
	private final float[] gravity = new float[Vector3.SIZE];
	private final float[] magnetic = new float[Vector3.SIZE];
	private final float[] gyroscope = new float[Vector3.SIZE];
	private final float[] orientation = new float[Vector3.SIZE];

	private final float[] buffer1 = new float[Matrix.SIZE];
	private final float[] buffer2 = new float[Matrix.SIZE];
	
	private final Context context;
	
	public SensorData(Context context){
		this.context = context;
	}
	
	public void getGyroscope(float[] value, int offset){
		synchronized(gyroscope){
			value[offset + 0] = gyroscope[0];
			value[offset + 1] = gyroscope[1];
			value[offset + 2] = gyroscope[2];
		}
	}
	
	public void getOrientation(float[] value, int offset){
		synchronized(orientation){
			value[offset + 0] = orientation[0];
			value[offset + 1] = orientation[1];
			value[offset + 2] = orientation[2];
		}
	}
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		// if(event.accuracy == SensorManager.SENSOR_STATUS_UNRELIABLE) return;

		switch (event.sensor.getType()) {
		case Sensor.TYPE_GRAVITY:
			System.arraycopy(event.values, 0, gravity, 0, gravity.length);
			onOrientationChanged();
			break;
		case Sensor.TYPE_MAGNETIC_FIELD:
			System.arraycopy(event.values, 0, magnetic, 0, magnetic.length);
			onOrientationChanged();
			break;
		case Sensor.TYPE_GYROSCOPE:
			synchronized(gyroscope){
				System.arraycopy(event.values, 0, gyroscope, 0, gyroscope.length);
			}
			break;
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {}

	private void onOrientationChanged() {
		Resources resources = context.getResources();
		Configuration config = resources.getConfiguration();

		SensorManager.getRotationMatrix(buffer1, null, gravity, magnetic);
		if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
			SensorManager.remapCoordinateSystem(buffer1, SensorManager.AXIS_X, SensorManager.AXIS_Z, buffer2);
		} else if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			SensorManager.remapCoordinateSystem(buffer1, SensorManager.AXIS_Z, SensorManager.AXIS_MINUS_X, buffer2);
		} else {
			throw new IllegalStateException("Unknown orientation.");
		}
		SensorManager.getOrientation(buffer2, buffer1);
		
		float azimuth = buffer1[0];
		float pitch = buffer1[1];
		float roll = buffer1[2];
		
		synchronized(orientation){
			orientation[0] = azimuth;
			orientation[1] = pitch;
			orientation[2] = roll;
		}
	}
}
