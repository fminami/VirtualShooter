package com.example.shooter;

public interface ShooterInput {

	void getOrientation(float[] value, int offset);
	
	void getRawOrientation(float[] value, int offset);
	
	void setCenter(float[] value, int offset);
	
	boolean detectedShootAction(float[] value, int offset);
}
