package com.example.shooter;

import com.example.shooter.core.ResourceManager;
import com.example.shooter.graphics.Effect;
import com.example.shooter.graphics.ShaderProgram;
import com.example.shooter.util.Matrix;
import com.example.shooter.util.Vector3;

public final class SimpleEffect extends Effect{
	
	private static final String RESOURCE_NAME = "SimpleEffect";
	
	private static final String VS_FILE_PATH = "shader/simple.vert";
	private static final String FS_FILE_PATH = "shader/simple.frag";
	
	private static final int MVP_MATRIX;
	private static final int MV_MATRIX;
	private static final int DIFFUSE;
	private static final int SPECULAR;
	private static final int AMBIENT;
	private static final int LIGHT_DIRECTION;
	private static final int LIGHT_DIFFUSE;
	private static final int LIGHT_SPECULAR;
	private static final int LIGHT_AMBIENT;
	private static final int TOTAL;
	static{
		int offset = 0;
		MVP_MATRIX = offset;
		offset += Matrix.SIZE;
		MV_MATRIX = offset;
		offset += Matrix.SIZE;
		DIFFUSE = offset;
		offset += Vector3.SIZE;
		SPECULAR = offset;
		offset += Vector3.SIZE;
		AMBIENT = offset;
		offset += Vector3.SIZE;
		LIGHT_DIRECTION = offset;
		offset += Vector3.SIZE;
		LIGHT_DIFFUSE = offset;
		offset += Vector3.SIZE;
		LIGHT_SPECULAR = offset;
		offset += Vector3.SIZE;
		LIGHT_AMBIENT = offset;
		offset += Vector3.SIZE;
		TOTAL = offset;
	}
	
	private static final int DIRTY_FLAG_NONE = 0x0000;
	private static final int DIRTY_FLAG_MVP_MATRIX = 0x0001;
	private static final int DIRTY_FLAG_MV_MATRIX = 0x0002;
	private static final int DIRTY_FLAG_ALPHA = 0x0004;
	private static final int DIRTY_FLAG_DIFFUSE = 0x0010;
	private static final int DIRTY_FLAG_SPECULAR = 0x0020;
	private static final int DIRTY_FLAG_AMBIENT = 0x0040;
	private static final int DIRTY_FLAG_SHININESS = 0x0080;
	private static final int DIRTY_FLAG_LIGHT_DIRECTION = 0x0100;
	private static final int DIRTY_FLAG_LIGHT_DIFFUSE = 0x0200;
	private static final int DIRTY_FLAG_LIGHT_SPECULAR = 0x0400;
	private static final int DIRTY_FLAG_LIGHT_AMBIENT = 0x0800;
	private static final int DIRTY_FLAG_ALL = 0xffff;
	
	private final float[] buffer = new float[TOTAL];
	{
		Matrix.identity(buffer, MVP_MATRIX);
		Matrix.identity(buffer, MV_MATRIX);
		Vector3.one(buffer, DIFFUSE);
		Vector3.one(buffer, SPECULAR);
		Vector3.one(buffer, AMBIENT);
		Vector3.zero(buffer, LIGHT_DIRECTION);
		Vector3.one(buffer, LIGHT_DIFFUSE);
		Vector3.one(buffer, LIGHT_SPECULAR);
		Vector3.one(buffer, LIGHT_AMBIENT);
	}
	
	private float alpha = 1.0f;
	private float shininess = 10.0f;
	
	private int hPosition;
	private int hNormal;
	
	private int hMvpMatrix;
	private int hMvMatrix;
	private int hAlpha;
	private int hDiffuse;
	private int hSpecular;
	private int hAmbient;
	private int hShininess;
	private int hLightDirection;
	private int hLightDiffuse;
	private int hLightSpecular;
	private int hLightAmbient;
	
	private int dirtyFlag = DIRTY_FLAG_ALL;
	
	public int getPositionLocation(){
		return hPosition;
	}
	
	public int getNormalLocation(){
		return hNormal;
	}
	
	public void getModelViewProjection(float[] value, int offset){
		for(int i = 0; i < Matrix.SIZE; i++){
			value[offset + i] = buffer[MVP_MATRIX + i];
		}
	}
	
	public void setModelViewProjection(float[] value, int offset){
		for(int i = 0; i < Matrix.SIZE; i++){
			buffer[MVP_MATRIX + i] = value[offset + i];
		}
		
		dirtyFlag |= DIRTY_FLAG_MVP_MATRIX;
	}
	
	public void getModelView(float[] value, int offset){
		for(int i = 0; i < Matrix.SIZE; i++){
			value[offset + i] = buffer[MV_MATRIX + i];
		}
	}
	
	public void setModelView(float[] value, int offset){
		for(int i = 0; i < Matrix.SIZE; i++){
			buffer[MV_MATRIX + i] = value[offset + i];
		}
		
		dirtyFlag |= DIRTY_FLAG_MV_MATRIX;
	}
	
	public float getAlpha(){
		return alpha;
	}
	
	public void setAlpha(float value){
		if(alpha != value){
			this.alpha = value;
			dirtyFlag |= DIRTY_FLAG_ALPHA;
		}
	}
	
	public void getDiffuse(float[] value, int offset){
		for(int i = 0; i < Vector3.SIZE; i++){
			value[offset + i] = buffer[DIFFUSE + i];
		}
	}
	
	public void setDiffuse(float[] value, int offset){
		for(int i = 0; i < Vector3.SIZE; i++){
			if(buffer[DIFFUSE + i] != value[offset + i]){
				buffer[DIFFUSE + i] = value[offset + i];
				dirtyFlag |= DIRTY_FLAG_DIFFUSE;
			}
		}
	}
	
	public void getSpecular(float[] value, int offset){
		for(int i = 0; i < Vector3.SIZE; i++){
			value[offset + i] = buffer[SPECULAR + i];
		}
	}
	
	public void setSpecular(float[] value, int offset){
		for(int i = 0; i < Vector3.SIZE; i++){
			if(buffer[SPECULAR + i] != value[offset + i]){
				buffer[SPECULAR + i] = value[offset + i];
				dirtyFlag |= DIRTY_FLAG_SPECULAR;
			}
		}
	}
	
	public void getAmbient(float[] value, int offset){
		for(int i = 0; i < Vector3.SIZE; i++){
			value[offset + i] = buffer[AMBIENT + i];
		}
	}
	
	public void setAmbient(float[] value, int offset){
		for(int i = 0; i < Vector3.SIZE; i++){
			if(buffer[AMBIENT + i] != value[offset + i]){
				buffer[AMBIENT + i] = value[offset + i];
				dirtyFlag |= DIRTY_FLAG_AMBIENT;
			}
		}
	}
	
	public float getShininess(){
		return shininess;
	}
	
	public void setShininess(float value){
		if(shininess != value){
			shininess = value;
			dirtyFlag |= DIRTY_FLAG_SHININESS;
		}
	}
	
	public void getLightDirection(float[] value, int offset){
		for(int i = 0; i < Vector3.SIZE; i++){
			value[offset + i] = buffer[LIGHT_DIRECTION + i];
		}
	}
	
	public void setLightDirection(float[] value, int offset){
		for(int i = 0; i < Vector3.SIZE; i++){
			if(buffer[LIGHT_DIRECTION + i] != value[offset + i]){
				buffer[LIGHT_DIRECTION + i] = value[offset + i];
				dirtyFlag |= DIRTY_FLAG_LIGHT_DIRECTION;
			}
		}
	}
	
	public void getLightDiffuse(float[] value, int offset){
		for(int i = 0; i < Vector3.SIZE; i++){
			value[offset + i] = buffer[LIGHT_DIFFUSE + i];
		}
	}
	
	public void setLightDiffuse(float[] value, int offset){
		for(int i = 0; i < Vector3.SIZE; i++){
			if(buffer[LIGHT_DIFFUSE + i] != value[offset + i]){
				buffer[LIGHT_DIFFUSE + i] = value[offset + i];
				dirtyFlag |= DIRTY_FLAG_LIGHT_DIFFUSE;
			}
		}
	}
	
	public void getLightSpecular(float[] value, int offset){
		for(int i = 0; i < Vector3.SIZE; i++){
			value[offset + i] = buffer[LIGHT_SPECULAR + i];
		}
	}
	
	public void setLightSpecular(float[] value, int offset){
		for(int i = 0; i < Vector3.SIZE; i++){
			if(buffer[LIGHT_SPECULAR + i] != value[offset + i]){
				buffer[LIGHT_SPECULAR + i] = value[offset + i];
				dirtyFlag |= DIRTY_FLAG_LIGHT_SPECULAR;
			}
		}
	}
	
	public void getLightAmbient(float[] value, int offset){
		for(int i = 0; i < Vector3.SIZE; i++){
			value[offset + i] = buffer[LIGHT_AMBIENT + i];
		}
	}
	
	public void setLightAmbient(float[] value, int offset){
		for(int i = 0; i < Vector3.SIZE; i++){
			if(buffer[LIGHT_AMBIENT + i] != value[offset + i]){
				buffer[LIGHT_AMBIENT + i] = value[offset + i];
				dirtyFlag |= DIRTY_FLAG_LIGHT_AMBIENT;
			}
		}
	}

	@Override
	@Loader
	protected void loadShaderProgram(ShaderProgram program) {
		hPosition = program.getAttributeLocation("a_Position");
		hNormal = program.getAttributeLocation("a_Normal");
		
		hMvpMatrix = program.getUniformLocation("u_MVPMatrix");
		hMvMatrix = program.getUniformLocation("u_MVMatrix");
		hAlpha = program.getUniformLocation("u_Alpha");
		hDiffuse = program.getUniformLocation("u_Diffuse");
		hSpecular = program.getUniformLocation("u_Specular");
		hAmbient = program.getUniformLocation("u_Ambient");
		hShininess = program.getUniformLocation("u_Shininess");
		hLightDirection = program.getUniformLocation("u_LightDirection");
		hLightDiffuse = program.getUniformLocation("u_LightDiffuse");
		hLightSpecular = program.getUniformLocation("u_LightSpecular");
		hLightAmbient = program.getUniformLocation("u_LightAmbient");
		
		setShaderProgram(program);
		
		dirtyFlag = DIRTY_FLAG_ALL;
	}

	@Override
	protected void onApply() {
		if((dirtyFlag & DIRTY_FLAG_MVP_MATRIX) != 0){
			setMatrix4(hMvpMatrix, 1, false, buffer, MVP_MATRIX);
		}
		if((dirtyFlag & DIRTY_FLAG_MV_MATRIX) != 0){
			setMatrix4(hMvMatrix, 1, false, buffer, MV_MATRIX);
		}
		if((dirtyFlag & DIRTY_FLAG_ALPHA) != 0){
			setFloat(hAlpha, alpha);
		}
		if((dirtyFlag & DIRTY_FLAG_DIFFUSE) != 0){
			setVector3(hDiffuse, 1, buffer, DIFFUSE);
		}
		if((dirtyFlag & DIRTY_FLAG_SPECULAR) != 0){
			setVector3(hSpecular, 1, buffer, SPECULAR);
		}
		if((dirtyFlag & DIRTY_FLAG_AMBIENT) != 0){
			setVector3(hAmbient, 1, buffer, AMBIENT);
		}
		if((dirtyFlag & DIRTY_FLAG_SHININESS) != 0){
			setFloat(hShininess, shininess);
		}
		if((dirtyFlag & DIRTY_FLAG_LIGHT_DIRECTION) != 0){
			setVector3(hLightDirection, 1, buffer, LIGHT_DIRECTION);
		}
		if((dirtyFlag & DIRTY_FLAG_LIGHT_DIFFUSE) != 0){
			setVector3(hLightDiffuse, 1, buffer, LIGHT_DIFFUSE);
		}
		if((dirtyFlag & DIRTY_FLAG_LIGHT_SPECULAR) != 0){
			setVector3(hLightSpecular, 1, buffer, LIGHT_SPECULAR);
		}
		if((dirtyFlag & DIRTY_FLAG_LIGHT_AMBIENT) != 0){
			setVector3(hLightAmbient, 1, buffer, LIGHT_AMBIENT);
		}
		
		dirtyFlag = DIRTY_FLAG_NONE;
	}

	@Factory(RESOURCE_NAME)
	static final ShaderProgram createShaderProgram(ResourceManager resources){
		return ShaderProgram.create(resources.getAssets(), VS_FILE_PATH, FS_FILE_PATH);
	}
}
