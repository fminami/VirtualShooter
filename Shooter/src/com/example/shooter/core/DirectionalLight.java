package com.example.shooter.core;

import com.example.shooter.util.Vector3;

public class DirectionalLight extends Light {

	private static final int DIRECTION;
	private static final int TOTAL;
	static {
		int offset = 0;
		DIRECTION = offset;
		offset += Vector3.SIZE;
		TOTAL = offset;
	}
	
	private final float[] buffer = new float[TOTAL];
	{
		Vector3.zero(buffer, DIRECTION);
	}
	
	public void getDirection(float[] value, int offset){
		for(int i = 0; i < Vector3.SIZE; i++){
			value[offset + i] = buffer[DIRECTION + i];
		}
	}
	
	public void setDirection(float[] value, int offset){
		for(int i = 0; i < Vector3.SIZE; i++){
			buffer[DIRECTION + i] = value[offset + i];
		}
	}
	
}
