package com.example.shooter.util;

public interface PrimitiveDeque {
	
	public enum Policy{
		NONE,
		RESIZE,
		OVERWRITE,
	}
	
	int size();
	
	int capacity();
	
	boolean isEmpty();
	
	Policy getPolicy();
	
	void clear();
}
