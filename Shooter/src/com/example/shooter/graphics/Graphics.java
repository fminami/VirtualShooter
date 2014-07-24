package com.example.shooter.graphics;

import android.opengl.GLES20;

public final class Graphics {
	
	public static final boolean[] vBool = new boolean[1];
	public static final int[] vInt = new int[4];
	public static final float[] vFloat = new float[4];
	
	public static boolean cullFaceEnabled(){
		synchronized(vBool){
			GLES20.glGetBooleanv(GLES20.GL_CULL_FACE, vBool, 0);
			return vBool[0];
		}
	}
	
	public static void enableCullFace(boolean status){
		if(status){
			GLES20.glEnable(GLES20.GL_CULL_FACE);
		}else{
			GLES20.glDisable(GLES20.GL_CULL_FACE);
		}
	}
	
	public static boolean depthTestEnabled(){
		synchronized(vBool){
			GLES20.glGetBooleanv(GLES20.GL_DEPTH_TEST, vBool, 0);
			return vBool[0];
		}
	}
	
	public static void enableDepthTest(boolean status){
		if(status){
			GLES20.glEnable(GLES20.GL_DEPTH_TEST);
		}else{
			GLES20.glDisable(GLES20.GL_DEPTH_TEST);
		}
	}
	
	public static int getCullFaceMode(){
		synchronized(vInt){
			GLES20.glGetIntegerv(GLES20.GL_CULL_FACE_MODE, vInt, 0);
			return vInt[0];
		}
	}
	
	public static void setCullFaceMode(int mode){
		GLES20.glCullFace(mode);
	}
	
	public static int getFrontFace(){
		synchronized(vInt){
			GLES20.glGetIntegerv(GLES20.GL_FRONT_FACE, vInt, 0);
			return vInt[0];
		}
	}
	
	public static void setFrontFace(int mode){
		GLES20.glFrontFace(mode);
	}
	
	public static void getViewport(){
		GLES20.glGetIntegerv(GLES20.GL_VIEWPORT, vInt, 0);
	}
	
	public static void setViewport(int x, int y, int width, int height){
		GLES20.glViewport(x, y, width, height);
	}
	
	public static void getClearColor(){
		GLES20.glGetFloatv(GLES20.GL_COLOR_CLEAR_VALUE, vFloat, 0);
	}
	
	public static void setClearColor(float red, float green, float blue, float alpha){
		GLES20.glClearColor(red, green, blue, alpha);
	}
	
	public static float getClearDepth(){
		synchronized(vFloat){
			GLES20.glGetFloatv(GLES20.GL_DEPTH_CLEAR_VALUE, vFloat, 0);
			return vFloat[0];
		}
	}
	
	public static void setClearDepth(float depth){
		GLES20.glClearDepthf(depth);
	}
	
	public static int getClearStencil(){
		synchronized(vInt){
			GLES20.glGetIntegerv(GLES20.GL_STENCIL_CLEAR_VALUE, vInt, 0);
			return vInt[0];
		}
	}
	
	public static void setClearStencil(int s){
		GLES20.glClearStencil(s);
	}
	
	public static void clear(){
		clear(true, true, true);
	}
	
	public static void clear(boolean color, boolean depth, boolean stencil){
		int mask = 0;
		mask |= color ? GLES20.GL_COLOR_BUFFER_BIT : 0;
		mask |= depth ? GLES20.GL_DEPTH_BUFFER_BIT : 0;
		mask |= stencil ? GLES20.GL_STENCIL_BUFFER_BIT : 0;
		GLES20.glClear(mask);
	}
	
	public static void drawArrays(VertexBuffer buffer, int mode){
		buffer.bind();
		GLES20.glDrawArrays(mode, 0, buffer.size());
		buffer.unbind();
	}
	
	public static void drawElements(VertexBuffer vertexBuffer, IndexBuffer indexBuffer, int mode){
		vertexBuffer.bind();
		indexBuffer.bind();
		GLES20.glDrawElements(mode, indexBuffer.size(), indexBuffer.getFormatGL(), 0);
		vertexBuffer.unbind();
		indexBuffer.unbind();
	}
}
