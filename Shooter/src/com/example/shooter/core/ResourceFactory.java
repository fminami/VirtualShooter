package com.example.shooter.core;

public interface ResourceFactory<T extends Resource> {

	public T createResource(ResourceManager manager);
	
}
