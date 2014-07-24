package com.example.shooter.core;

import java.util.*;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.example.shooter.graphics.Graphics;

import android.opengl.GLSurfaceView;

public abstract class Game implements GLSurfaceView.Renderer, ServiceProvider {

	private final InputManager input;
	private final ResourceManager resources;
	
	private final Map<Class<?>, Object> services =
			Collections.synchronizedMap(new HashMap<Class<?>, Object>());
	
	private volatile GameScene scene;
	
	private volatile boolean running;

	public Game(InputManager input, ResourceManager resources){
		this.input = input;
		this.resources = resources;
		
		addService(InputManager.class, input);
		addService(ResourceManager.class, resources);
	}
	
	public InputManager getInputManager(){
		return input;
	}
	
	public ResourceManager getResourceManager(){
		return resources;
	}
	
	public GameScene getScene(){
		return scene;
	}
	
	public void setScene(GameScene scene){
		this.scene = scene;
	}
	
	public boolean isRunning(){
		return running;
	}
	
	public void run(){
		if(running) throw new IllegalStateException("Game has already been running.");
		
		init();
		
		running = true;
	}
	
	public void addService(Object service){
		addService(service.getClass(), service);
	}
	
	@Override
	public void addService(Class<?> clazz, Object service){
		if(!clazz.isAssignableFrom(service.getClass())) throw new IllegalArgumentException("Type mismatch.");
		
		services.put(clazz, service);
	}
	
	@Override
	public boolean removeService(Class<?> clazz){
		return (services.remove(clazz) != null);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getService(Class<T> clazz){
		return (T)services.get(clazz);
	}
	
	@Override
	public final void onSurfaceCreated(GL10 gl, EGLConfig config) {
		if(!running) throw new IllegalStateException("Call run() method before onSurfaceCreated.");
		
		load();
	}

	@Override
	public final void onSurfaceChanged(GL10 gl, int width, int height) {
		onScreenChanged(width, height);
	}

	@Override
	public final void onDrawFrame(GL10 gl) {
		frame();
	}
	
	public final void onSurfaceDestroyed(){
		unload();
	}
	
	protected void frame(){
		update();
		draw();
	}
	
	protected void onScreenChanged(int width, int height){
		Graphics.setViewport(0, 0, width, height);
	}
	
	protected void onResume(){
		if(input != null) input.onResume();
	}
	
	protected void onPause(){
		if(input != null) input.onPause();
	}
	
	protected abstract void onInit();
	protected abstract void onLoad();
	protected abstract void onUnload();
	protected abstract void onUpdate();
	protected abstract void onDraw();
	
	private void init(){
		onInit();
		if(scene != null) scene.init();
	}
	
	private void load(){
		onLoad();
		if(scene != null) scene.load();
	}
	
	private void unload(){
		if(scene != null) scene.unload();
		onUnload();
		if(resources != null) resources.unloadAll();
	}
	
	private void update(){
		if(input != null) input.update();
		onUpdate();
		if(scene != null) scene.update();
	}
	
	private void draw(){
		onDraw();
		if(scene != null) scene.draw();
	}
}
