package com.example.shooter.core;


public abstract class Camera extends Node {

	public abstract void getView(float[] result, int offset);
	public abstract void getProjection(float[] result, int offset);

}
