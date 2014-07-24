package com.example.shooter.core;

import java.util.List;

import com.example.shooter.util.*;

public class Node extends NodeBase<Node> {

	private static final int MODEL;
	private static final int LOCAL;
	private static final int POSITION;
	private static final int ROTATION;
	private static final int SCALE;
	private static final int TOTAL;
	static {
		int offset = 0;
		MODEL = offset;
		offset += Matrix.SIZE;
		LOCAL = offset;
		offset += Matrix.SIZE;
		POSITION = offset;
		offset += Vector3.SIZE;
		ROTATION = offset;
		offset += Quaternion.SIZE;
		SCALE = offset;
		offset += Vector3.SIZE;
		TOTAL = offset;
	}
	
	private static final int DIRTY_FLAG_NONE = 0x00;
	private static final int DIRTY_FLAG_MODEL = 0x01;
	private static final int DIRTY_FLAG_LOCAL = 0x02;

	private Scene scene;
	
	private boolean initialized;

	private boolean visible = true;

	private boolean enabled = true;

	private final float[] buffer = new float[TOTAL];
	{
		Matrix.identity(buffer, MODEL);
		Matrix.identity(buffer, LOCAL);
		Vector3.zero(buffer, POSITION);
		Quaternion.identity(buffer, ROTATION);
		Vector3.one(buffer, SCALE);
	}
	
	private int dirtyFlag = DIRTY_FLAG_NONE;

	private static float[] sTemp = new float[48];

	public Scene getScene() {
		return scene;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void enabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void getModelMatrix(float[] value, int offset) {
		updateModelMatrix();
		
		for (int i = 0; i < Matrix.SIZE; i++) {
			value[offset + i] = buffer[MODEL + i];
		}
	}

	public void getLocalMatrix(float[] value, int offset) {
		updateLocalMatrix();
		
		for (int i = 0; i < Matrix.SIZE; i++) {
			value[offset + i] = buffer[LOCAL + i];
		}
	}

	public void getPosition(float[] value, int offset) {
		for (int i = 0; i < Vector3.SIZE; i++) {
			value[offset + i] = buffer[POSITION + i];
		}
	}

	public void setPosition(float[] value, int offset) {
		for (int i = 0; i < Vector3.SIZE; i++) {
			buffer[POSITION + i] = value[offset + i];
		}
		
		dirtyLocalMatrix();
	}

	public void getRotation(float[] value, int offset) {
		for (int i = 0; i < Quaternion.SIZE; i++) {
			value[offset + i] = buffer[ROTATION + i];
		}
	}

	public void setRotation(float[] value, int offset) {
		for (int i = 0; i < Quaternion.SIZE; i++) {
			buffer[ROTATION + i] = value[offset + i];
		}
		
		dirtyLocalMatrix();
	}

	public void getScale(float[] value, int offset) {
		for (int i = 0; i < Vector3.SIZE; i++) {
			value[offset + i] = buffer[SCALE + i];
		}
	}

	public void setScale(float[] value, int offset) {
		for (int i = 0; i < Vector3.SIZE; i++) {
			buffer[SCALE + i] = value[offset + i];
		}
		
		dirtyLocalMatrix();
	}

	@Override
	public void addChild(Node child) {
		super.addChild(child);

		if (scene != null) scene.attach(child);
	}

	@Override
	public void removeChild(Node child) {
		super.removeChild(child);

		if(child.scene != null) child.scene.detach(child);
	}

	protected void onInit() {}
	protected void onLoad() {}
	protected void onUnload() {}
	protected void onAttached() {}
	protected void onDetached() {}
	protected void onUpdate() {}
	protected void onDraw() {}
	protected void onModelMatrixUpdated() {}
	protected void onLocalMatrixUpdated() {}

	final void init() {
		if(!initialized){
			onInit();
			
			initialized = true;
		}
		
		List<Node> children = getChildren();
		for(int i = 0, size = children.size(); i < size; i++){
			children.get(i).init();
		}
	}
	
	final void load(){
		onLoad();
		
		List<Node> children = getChildren();
		for(int i = 0, size = children.size(); i < size; i++){
			children.get(i).load();
		}
	}
	
	final void unload(){
		List<Node> children = getChildren();
		for(int i = 0, size = children.size(); i < size; i++){
			children.get(i).unload();
		}
		
		onUnload();
	}
	
	final void attach(Scene scene){
		this.scene = scene;
		
		onAttached();
	}
	
	final void detach(){
		this.scene = null;
		
		onDetached();
	}

	final void update() {
		if (enabled) {
			onUpdate();
			
			List<Node> children = getChildren();
			for(int i = 0, size = children.size(); i < size; i++){
				children.get(i).update();
			}
		}
	}

	final void draw() {
		if (visible) {
			onDraw();
			
			List<Node> children = getChildren();
			for(int i = 0, size = children.size(); i < size; i++){
				children.get(i).draw();
			}
		}
	}
	
	private void dirtyModelMatrix(){
		if((dirtyFlag & DIRTY_FLAG_MODEL) == 0){
			dirtyFlag |= DIRTY_FLAG_MODEL;
			
			List<Node> children = getChildren();
			for(int i = 0, size = children.size(); i < size; i++){
				children.get(i).dirtyModelMatrix();
			}
		}
	}

	private void dirtyLocalMatrix(){
		dirtyFlag |= DIRTY_FLAG_LOCAL;
		
		dirtyModelMatrix();
	}
	
	protected void updateModelMatrix(){
		updateLocalMatrix();
		
		if((dirtyFlag & DIRTY_FLAG_MODEL) != 0){
			Node parent = getParent();
			if (parent != null) {
				parent.updateModelMatrix();
				
				Matrix.mul(buffer, MODEL, parent.buffer, MODEL, buffer, LOCAL);
			}
			
			onModelMatrixUpdated();
			
			dirtyFlag &= ~DIRTY_FLAG_MODEL;
		}
	}
	
	protected void updateLocalMatrix(){
		if((dirtyFlag & DIRTY_FLAG_LOCAL) != 0){
			synchronized (sTemp) {
				Matrix.scale(sTemp, 0, buffer, SCALE);
				Matrix.fromQuaternion(sTemp, 16, buffer, ROTATION);
				Matrix.mul(sTemp, 32, sTemp, 16, sTemp, 0);
				Matrix.translation(sTemp, 0, buffer, POSITION);
				Matrix.mul(buffer, LOCAL, sTemp, 0, sTemp, 32);
			}
			
			onLocalMatrixUpdated();
			
			dirtyFlag &= ~DIRTY_FLAG_LOCAL;
		}
	}
}
