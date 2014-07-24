package com.example.shooter;

import com.example.shooter.core.*;
import com.example.shooter.util.*;

public class ShooterCamera extends PerspectiveCamera {

	private static final float INIT_FIELD_OF_VIEW_Y = (float)(Math.PI / 2.0);
	private static final float NEAR_CLIP_PLANE = 1.0e-2f;
	private static final float FAR_CLIP_PLANE = 1.0e2f;
	
	private FloatArray array = new FloatArray().freeze();
	
	@Override
	protected void onInit() {
		setFieldOfViewY(INIT_FIELD_OF_VIEW_Y);
		setNearClipPlane(NEAR_CLIP_PLANE);
		setFarClipPlane(FAR_CLIP_PLANE);
		
		super.onInit();
	}
	
	@Override
	protected void onUpdate() {
		ServiceProvider services = getScene().getServiceProvider();
		ShooterInput input = (ShooterInput)services.getService(InputManager.class);
		
		float[] buffer = array.getBuffer();
		int offset = array.t(0);
		input.getOrientation(buffer, offset);
		float azimuth = buffer[offset + 0];
		float pitch = buffer[offset + 1];
		float roll = buffer[offset + 2];
		
		array.set(array.t(0), 0.0f, 1.0f, 0.0f);
		QuaternionEx.fromAxisAngle(array, array.t(1), array.t(0), (float)Math.PI);
		QuaternionEx.fromYawPitchRoll(array, array.t(2), -azimuth, pitch, roll);
		QuaternionEx.mul(array, array.t(3), array.t(2), array.t(1));
		QuaternionEx.mul(array, array.t(4), array.t(1), array.t(3));
		setRotation(array.getBuffer(), array.t(4));
		
		super.onUpdate();
	}
}
