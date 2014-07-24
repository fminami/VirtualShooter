package com.example.shooter.core;

public interface ServiceProvider {

	public void addService(Class<?> clazz, Object service);

	public boolean removeService(Class<?> clazz);

	public <T> T getService(Class<T> clazz);

}