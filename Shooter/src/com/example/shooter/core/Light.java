package com.example.shooter.core;

import com.example.shooter.util.Vector3;

public abstract class Light extends Node {

	private static final int DIFFUSE;
	private static final int SPECULAR;
	private static final int AMBIENT;
	private static final int TOTAL;
	static {
		int offset = 0;
		DIFFUSE = offset;
		offset += Vector3.SIZE;
		SPECULAR = offset;
		offset += Vector3.SIZE;
		AMBIENT = offset;
		offset += Vector3.SIZE;
		TOTAL = offset;
	}

	private final float[] buffer = new float[TOTAL];
	{
		Vector3.one(buffer, DIFFUSE);
		Vector3.one(buffer, SPECULAR);
		Vector3.one(buffer, AMBIENT);
	}
	
	public void getDiffuse(float[] value, int offset){
		for(int i = 0; i < Vector3.SIZE; i++){
			value[offset + i] = buffer[DIFFUSE + i];
		}
	}
	
	public void setDiffuse(float[] value, int offset){
		for(int i = 0; i < Vector3.SIZE; i++){
			buffer[DIFFUSE + i] = value[offset + i];
		}
	}
	
	public void getSpecular(float[] value, int offset){
		for(int i = 0; i < Vector3.SIZE; i++){
			value[offset + i] = buffer[SPECULAR + i];
		}
	}
	
	public void setSpecular(float[] value, int offset){
		for(int i = 0; i < Vector3.SIZE; i++){
			buffer[SPECULAR + i] = value[offset + i];
		}
	}
	
	public void getAmbient(float[] value, int offset){
		for(int i = 0; i < Vector3.SIZE; i++){
			value[offset + i] = buffer[AMBIENT + i];
		}
	}
	
	public void setAmbient(float[] value, int offset){
		for(int i = 0; i < Vector3.SIZE; i++){
			buffer[AMBIENT + i] = value[offset + i];
		}
	}
	
}
