package com.example.shooter.graphics;

import com.example.shooter.core.ResourceWrapper;

import android.opengl.GLES20;

public abstract class Effect implements ResourceWrapper<ShaderProgram>{
	
	private ShaderProgram program;
	
	public ShaderProgram getShaderProgram(){
		return program;
	}
	
	protected void setShaderProgram(ShaderProgram program){
		if(program == null) throw new IllegalArgumentException("'program' is null.");
		
		this.program = program;
	}
	
	public void apply(){
		if(program == null) throw new IllegalStateException("Shader program is still not set.");
		
		program.apply();
		
		onApply();
	}
	
	protected void setFloat(int location, float value){
		GLES20.glUniform1f(location, value);
	}
	
	protected void setVector1(int location, int count, float[] value, int offset){
		GLES20.glUniform1fv(location, count, value, offset);
	}
	
	protected void setVector2(int location, int count, float[] value, int offset){
		GLES20.glUniform2fv(location, count, value, offset);
	}
	
	protected void setVector3(int location, int count, float[] value, int offset){
		GLES20.glUniform3fv(location, count, value, offset);
	}
	
	protected void setVector4(int location, int count, float[] value, int offset){
		GLES20.glUniform4fv(location, count, value, offset);
	}
	
	protected void setInteger(int location, int value){
		GLES20.glUniform1i(location, value);
	}
	
	protected void setIVector1(int location, int count, int[] value, int offset){
		GLES20.glUniform1iv(location, count, value, offset);
	}
	
	protected void setIVector2(int location, int count, int[] value, int offset){
		GLES20.glUniform2iv(location, count, value, offset);
	}
	
	protected void setIVector3(int location, int count, int[] value, int offset){
		GLES20.glUniform3iv(location, count, value, offset);
	}
	
	protected void setIVector4(int location, int count, int[] value, int offset){
		GLES20.glUniform4iv(location, count, value, offset);
	}
	
	protected void setMatrix2(int location, int count, boolean transpose, float[] value, int offset){
		GLES20.glUniformMatrix2fv(location, count, transpose, value, offset);
	}
	
	protected void setMatrix3(int location, int count, boolean transpose, float[] value, int offset){
		GLES20.glUniformMatrix3fv(location, count, transpose, value, offset);
	}
	
	protected void setMatrix4(int location, int count, boolean transpose, float[] value, int offset){
		GLES20.glUniformMatrix4fv(location, count, transpose, value, offset);
	}
	
	protected abstract void loadShaderProgram(ShaderProgram program);
	protected abstract void onApply();
}
