package com.example.shooter;

import android.os.SystemClock;

import com.example.shooter.core.Node;
import com.example.shooter.util.FloatArray;

public class Container extends Node {
	
	public static interface Controller{
		void control(Node node, FloatArray array, float t, float s, float angle);
	}
	
	private final FloatArray array = new FloatArray().freeze();
	
	private final long loop;
	
	private final Controller controller;
	
	public Container(long loop, Controller controller){
		this.loop = loop;
		this.controller = controller;
	}
	
	@Override
	protected void onUpdate() {
		long time = SystemClock.uptimeMillis() % loop;
		float t = (float)((double)time / (double)loop);
		float s = 1.0f - Math.abs(t - 0.5f) * 2.0f;
		float angle = (float) (Math.PI * 2.0) * t;
		
		controller.control(this, array, t, s, angle);
		
		super.onUpdate();
	}
}
