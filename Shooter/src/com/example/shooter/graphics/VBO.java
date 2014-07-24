package com.example.shooter.graphics;

import java.nio.Buffer;

import com.example.shooter.core.Resource;

import android.opengl.GLES20;

public final class VBO implements Resource {
	
	public static final int STREAM_DRAW = GLES20.GL_STREAM_DRAW;
	public static final int STATIC_DRAW = GLES20.GL_STATIC_DRAW;
	public static final int DYNAMIC_DRAW = GLES20.GL_DYNAMIC_DRAW;
	
	private final int handle;
	
	private final int target;

	private VBO(int handle, int target){
		this.handle = handle;
		this.target = target;
	}
	
	public void bind(){
		GLES20.glBindBuffer(target, handle);
	}
	
	public void unbind(){
		GLES20.glBindBuffer(target, 0);
	}
	
	public void setData(Buffer data, int offset, int size){
		GLES20.glBufferSubData(target, offset, size, data);
	}
	
	public static VBO createVertexBuffer(int size, int usage){
		return create(size, usage, false);
	}
	
	public static VBO createIndexBuffer(int size, int usage){
		return create(size, usage, true);
	}
	
	private static VBO create(int size, int usage, boolean isIndexBuffer){
		int[] handles = new int[1];
		int target = (isIndexBuffer ? GLES20.GL_ELEMENT_ARRAY_BUFFER : GLES20.GL_ARRAY_BUFFER);
		
		GLES20.glGenBuffers(1, handles, 0);
		
		GLES20.glBindBuffer(target, handles[0]);
		GLES20.glBufferData(target, size, null, usage);
		GLES20.glBindBuffer(target, 0);
		
		return new VBO(handles[0], target);
	}
}
