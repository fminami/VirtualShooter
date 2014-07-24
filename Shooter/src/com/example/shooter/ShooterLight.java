package com.example.shooter;

import com.example.shooter.core.DirectionalLight;
import com.example.shooter.util.FloatArray;
import com.example.shooter.util.Vector3Ex;

public class ShooterLight extends DirectionalLight {

	private FloatArray array = new FloatArray();
	private int hDirection = array.getVectorHandle();
	private int hDiffuse = array.getVectorHandle();
	private int hSpecular = array.getVectorHandle();
	private int hAmbient = array.getVectorHandle();
	{array.freeze();}
	
	@Override
	protected void onInit() {
		array.set(hDirection, -1.0f, -1.0f, -1.0f);
		array.set(hDiffuse, 1.0f, 1.0f, 1.0f);
		array.set(hSpecular, 0.75f, 0.75f, 0.75f);
		array.set(hAmbient, 0.15f, 0.15f, 0.15f);
		Vector3Ex.normalize(array, hDirection, hDirection);
		
		float[] buffer = array.getBuffer();
		setDirection(buffer, hDirection);
		setDiffuse(buffer, hDiffuse);
		setSpecular(buffer, hSpecular);
		setAmbient(buffer, hAmbient);
		
		super.onInit();
	}
	
}
