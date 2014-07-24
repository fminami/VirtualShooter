package com.example.shooter.graphics;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import com.example.shooter.core.Resource;

import android.content.res.AssetManager;
import android.opengl.GLES20;
import android.util.Log;

public final class ShaderProgram implements Resource{
	
	private static final String TAG = "ShaderProgram";
	
	private final int handle;
	
	private ShaderProgram(int handle){
		this.handle = handle;
	}
	
	public int getUniformLocation(String name){
		return GLES20.glGetUniformLocation(handle, name);
	}
	
	public int getAttributeLocation(String name){
		return GLES20.glGetAttribLocation(handle, name);
	}
	
	public void apply(){
		GLES20.glUseProgram(handle);
	}
	
	public static ShaderProgram create(AssetManager assets, String vsFileName, String fsFileName){
		String vsSource, fsSource;
		try{
			vsSource = readSourceFile(assets, vsFileName);
			fsSource = readSourceFile(assets, fsFileName);
		}catch(IOException e){
			throw new IllegalArgumentException("Failed to read the source files.", e);
		}
		
		return create(vsSource, fsSource);
	}
	
	public static ShaderProgram create(String vsSource, String fsSource, String... bindings){
		int vsHandle, fsHandle, pHandle;
		vsHandle = fsHandle = pHandle = 0;
		try{
			vsHandle = createVertexShader(vsSource);
			fsHandle = createFragmentShader(fsSource);
			pHandle = createProgram(vsHandle, fsHandle, bindings);
		}finally{
			if(vsHandle != 0) GLES20.glDeleteShader(vsHandle);
			if(fsHandle != 0) GLES20.glDeleteShader(fsHandle);
		}
		
		return new ShaderProgram(pHandle);
	}
	
	private static int createVertexShader(String source){
		int handle = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER);
		if(handle == 0) throw new IllegalStateException("Failed to create a vertex shader.");
		
		GLES20.glShaderSource(handle, source);
		GLES20.glCompileShader(handle);
		
        final int[] status = new int[1];
        GLES20.glGetShaderiv(handle, GLES20.GL_COMPILE_STATUS, status, 0);
        
        if (status[0] == GLES20.GL_FALSE) {
            String infoLog = GLES20.glGetShaderInfoLog(handle);
            String message = "Failed to compile the source of a vertex shader:\n" + infoLog;
            Log.e(TAG, message);
            throw new IllegalArgumentException(message);
        }
		
		return handle;
	}
	
	private static int createFragmentShader(String source){
		int handle = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER);
		if(handle == 0) throw new IllegalStateException("Failed to create a fragment shader.");
		
		GLES20.glShaderSource(handle, source);
		GLES20.glCompileShader(handle);
		
        final int[] status = new int[1];
        GLES20.glGetShaderiv(handle, GLES20.GL_COMPILE_STATUS, status, 0);
        
        if (status[0] == GLES20.GL_FALSE) {
            String infoLog = GLES20.glGetShaderInfoLog(handle);
            String message = "Failed to compile the source of a fragment shader:\n" + infoLog;
            Log.e(TAG, message);
            throw new IllegalArgumentException(message);
        }
		
		return handle;
	}
	
	private static int createProgram(int vsHandle, int fsHandle, String[] bindings){
		int handle = GLES20.glCreateProgram();
		if(handle == 0) throw new IllegalStateException("Failed to create a shader program.");
		
		GLES20.glAttachShader(handle, vsHandle);
		GLES20.glAttachShader(handle, fsHandle);
		
		for(int i = 0; i < bindings.length; i++){
			GLES20.glBindAttribLocation(handle, i, bindings[i]);
		}
		
		GLES20.glLinkProgram(handle);
		
		final int[] status = new int[1];
        GLES20.glGetProgramiv(handle, GLES20.GL_LINK_STATUS, status, 0);
        
        if(status[0] == GLES20.GL_FALSE){
        	String infoLog = GLES20.glGetProgramInfoLog(handle);
        	GLES20.glDeleteProgram(handle);
        	String message = "Failed to link the program:\n" + infoLog;
        	Log.e(TAG, message);
        	throw new IllegalArgumentException(message);
        }
        
        return handle;
	}
	
	private static String readSourceFile(AssetManager assets, String fileName) throws IOException{
		String source;
		// AssetFileDescriptor fd = null;
		InputStream stream = null;
		try{
			// fd = manager.openFd(fileName);
			// stream = fd.createInputStream();
			// byte[] buffer = new byte[(int) fd.getLength()];
			stream = assets.open(fileName, AssetManager.ACCESS_STREAMING);
			byte[] buffer = new byte[stream.available()];
			stream.read(buffer);
			source = new String(buffer, StandardCharsets.UTF_8);
		}finally{
			if(stream != null) stream.close();
			// if(fd != null) fd.close();
		}
		
		return source;
	}
	
}
