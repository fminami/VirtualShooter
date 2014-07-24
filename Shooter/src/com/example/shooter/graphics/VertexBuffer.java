package com.example.shooter.graphics;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.EnumMap;

import com.example.shooter.core.ResourceHolder;
import com.example.shooter.core.ResourceManager;

import android.opengl.GLES20;

public abstract class VertexBuffer implements ResourceHolder<VBO> {
	
	public enum Format {
		FLOAT1, FLOAT2, FLOAT3, FLOAT4
	}
	
	private static class FormatInfo{
		public final int type;
		public final boolean normalized;
		public final int size;
		public final int bytes;
		public FormatInfo(int type, boolean normalized, int size, int bytes){
			this.type = type;
			this.normalized = normalized;
			this.size = size;
			this.bytes = bytes;
		}
	}

	private static final EnumMap<Format, FormatInfo> formatInfo;
	static {
		formatInfo = new EnumMap<Format, FormatInfo>(Format.class);
		formatInfo.put(Format.FLOAT1, new FormatInfo(GLES20.GL_FLOAT, false, 1, 4));
		formatInfo.put(Format.FLOAT2, new FormatInfo(GLES20.GL_FLOAT, false, 2, 8));
		formatInfo.put(Format.FLOAT3, new FormatInfo(GLES20.GL_FLOAT, false, 3, 12));
		formatInfo.put(Format.FLOAT4, new FormatInfo(GLES20.GL_FLOAT, false, 4, 16));
	}

	private VBO buffer;
	private final ByteBuffer data;
	private final int size;
	private final int stride;
	private final Format[] formats;
	
	private final int[] bindings;

	private boolean dirty = true;

	public VertexBuffer(int size, Format[] formats) {
		if (size <= 0) throw new IllegalArgumentException("'size' must be a positive value.");
		if(formats == null) throw new IllegalArgumentException("'formats' is null.");
		for(int i = 0; i < formats.length; i++){
			if (!formatInfo.containsKey(formats[i])) throw new IllegalArgumentException("Unsupported format.");
		}

		int stride = calculateStride(formats);
		
		this.data = ByteBuffer.allocateDirect(size * stride).order(ByteOrder.nativeOrder());
		this.size = size;
		this.stride = stride;
		this.formats = formats;
		this.bindings = new int[formats.length];
		Arrays.fill(this.bindings, -1);
	}
	
	public int size(){
		return size;
	}
	
	public int stride(){
		return stride;
	}
	
	protected ByteBuffer getByteBuffer() {
		return data;
	}
	
	public void setAttributeBinding(int stream, int location) {
		if (stream < 0 || stream >= bindings.length) throw new IllegalArgumentException("'stream' is out of bounds.");
	
		bindings[stream] = Math.max(location, -1);
	}
	
	public void bind(){
		buffer.bind();
		if(dirty){
			buffer.setData(data, 0, size * stride);
			dirty = false;
		}
		
		int offset = 0;
		for(int i = 0; i < formats.length; i++){
			FormatInfo info = formatInfo.get(formats[i]);
			if(bindings[i] >= 0){
				GLES20.glEnableVertexAttribArray(bindings[i]);
				GLES20.glVertexAttribPointer(bindings[i], info.size, info.type, info.normalized, stride, offset);
			}
			offset += info.bytes;
		}
	}
	
	public void unbind(){
		for(int i = 0; i < formats.length; i++){
			if(bindings[i] >= 0){
				GLES20.glDisableVertexAttribArray(bindings[i]);
			}
		}
		
		buffer.unbind();
	}

	@Override
	public void loadResource(ResourceManager resources) {
		this.buffer = VBO.createVertexBuffer(size * stride, VBO.STATIC_DRAW);

		dirty = true;
	}
	
	protected void update(){
		dirty = true;
	}
	
	private static int calculateStride(Format[] formats){
		int stride = 0;
		for(int i = 0; i < formats.length; i++){
			stride += formatInfo.get(formats[i]).bytes;
		}
		return stride;
	}

}