package com.example.shooter.core;

import com.example.shooter.util.*;

public class PerspectiveCamera extends Camera {
	
	private static final int VIEW;
	private static final int PROJECTION;
	private static final int TOTAL;
	static{
		int offset = 0;
		VIEW = offset;
		offset += Matrix.SIZE;
		PROJECTION = offset;
		offset += Matrix.SIZE;
		TOTAL = offset;
	}
	
	private static final int DIRTY_FLAG_VIEW = 0x01;
	private static final int DIRTY_FLAG_PROJ = 0x02;
	private static final int DIRTY_FLAG_ALL = DIRTY_FLAG_VIEW | DIRTY_FLAG_PROJ;
	
	private final float[] buffer = new float[TOTAL];
	{
		Matrix.identity(buffer, VIEW);
		Matrix.identity(buffer, PROJECTION);
	}
	
	private float fovy;
	private float aspect;
	private float nearClipPlane;
	private float farClipPlane;
	
	private int dirtyFlag = DIRTY_FLAG_ALL;
	
	private static final float[] sTemp = new float[32];

	@Override
	public void getView(float[] result, int offset) {
		updateView();
		for(int i = 0; i < Matrix.SIZE; i++){
			result[offset + i] = buffer[VIEW + i];
		}
	}

	@Override
	public void getProjection(float[] result, int offset) {
		updateProjection();
		for(int i = 0; i < Matrix.SIZE; i++){
			result[offset + i] = buffer[PROJECTION + i];
		}
	}
	
	public float getFieldOfViewY(){
		return fovy;
	}
	
	public void setFieldOfViewY(float value){
		if(fovy != value){
			fovy = value;
			dirtyFlag |= DIRTY_FLAG_PROJ;
		}
	}
	
	public float getAspectRatio(){
		return aspect;
	}
	
	public void setAspectRatio(float value){
		if(aspect != value){
			aspect = value;
			dirtyFlag |= DIRTY_FLAG_PROJ;
		}
	}
	
	public float getNearClipPlane(){
		return nearClipPlane;
	}
	
	public void setNearClipPlane(float value){
		if(nearClipPlane != value){
			nearClipPlane = value;
			dirtyFlag |= DIRTY_FLAG_PROJ;
		}
	}
	
	public float getFarClipPlane(){
		return farClipPlane;
	}
	
	public void setFarClipPlane(float value){
		if(farClipPlane != value){
			farClipPlane = value;
			dirtyFlag |= DIRTY_FLAG_PROJ;
		}
	}
	
	@Override
	protected void onModelMatrixUpdated() {
		dirtyFlag |= DIRTY_FLAG_VIEW;
	}
	
	protected void updateView(){
		updateModelMatrix();
		
		if((dirtyFlag & DIRTY_FLAG_VIEW) != 0){
			synchronized(sTemp){
				getModelMatrix(sTemp, 0);
				Vector3.negate(sTemp, 0, sTemp, 12);
				Matrix.translation(sTemp, 16, sTemp, 0);
				
				int total = 4, result = 8;
				getRotation(sTemp, total);
				for(Node node = getParent(); node != null; node = node.getParent()){
					node.getRotation(sTemp, 0);
					Quaternion.mul(sTemp, result, sTemp, 0, sTemp, total);
					total ^= 12;
					result ^= 12;
				}
				Quaternion.inverse(sTemp, 0, sTemp, total);
				Matrix.fromQuaternion(sTemp, 0, sTemp, 0);
				
				Matrix.mul(buffer, VIEW, sTemp, 0, sTemp, 16);
			}
			
			dirtyFlag &= ~DIRTY_FLAG_VIEW;
		}
	}
	
	protected void updateProjection(){
		if((dirtyFlag & DIRTY_FLAG_PROJ) != 0){
			Matrix.perspective(buffer, PROJECTION, fovy, aspect, nearClipPlane, farClipPlane);
			
			dirtyFlag &= ~DIRTY_FLAG_PROJ;
		}
	}

}
