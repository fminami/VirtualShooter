package com.example.shooter.core;

public interface ResourceHolder<T extends Resource> extends Resource{
	
	void loadResource(ResourceManager resources);
	
}