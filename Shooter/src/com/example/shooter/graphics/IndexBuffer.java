package com.example.shooter.graphics;

import java.nio.*;
import java.util.EnumMap;

import com.example.shooter.core.ResourceHolder;
import com.example.shooter.core.ResourceManager;

import android.opengl.GLES20;

public final class IndexBuffer implements ResourceHolder<VBO> {

	public enum Format {
		BYTE, SHORT
	}
	
	private static class FormatInfo{
		public final int type;
		public final int bytes;
		public FormatInfo(int type, int bytes){
			this.type = type;
			this.bytes = bytes;
		}
	}

	private static final EnumMap<Format, FormatInfo> formatInfo;
	static {
		formatInfo = new EnumMap<Format, FormatInfo>(Format.class);
		formatInfo.put(Format.BYTE, new FormatInfo(GLES20.GL_UNSIGNED_BYTE, 1));
		formatInfo.put(Format.SHORT, new FormatInfo(GLES20.GL_UNSIGNED_SHORT, 2));
	}

	private VBO buffer;
	private final Buffer data;
	private final int size;
	private final Format format;

	private boolean dirty = true;

	public IndexBuffer(int size, Format format) {
		if (size <= 0) throw new IllegalArgumentException("'size' must be a positive value.");
		if (!formatInfo.containsKey(format)) throw new IllegalArgumentException("Unsupported format.");

		this.data = allocateBuffer(size, format);
		this.size = size;
		this.format = format;
	}
	
	public int size(){
		return size;
	}
	
	public Format getFormat(){
		return format;
	}
	
	public int getFormatGL(){
		return formatInfo.get(format).type;
	}

	public void setIndices(byte[] indices) {
		if (format != Format.BYTE) throw new UnsupportedOperationException("This method is byte version.");
		if (indices == null) throw new IllegalArgumentException("'indices' is null.");

		ByteBuffer byteData = (ByteBuffer)data;
		byteData.put(indices).position(0);
		
		dirty = true;
	}
	
	public void setIndices(byte[] indices, int offset, int length) {
		if (format != Format.BYTE) throw new UnsupportedOperationException("This method is byte version.");
		if (indices == null) throw new IllegalArgumentException("'indices' is null.");

		ByteBuffer byteData = (ByteBuffer)data;
		byteData.put(indices, offset, length).position(0);
		
		dirty = true;
	}
	
	public void setIndices(short[] indices) {
		if (format != Format.SHORT) throw new UnsupportedOperationException("This method is short version.");
		if (indices == null) throw new IllegalArgumentException("'indices' is null.");

		ShortBuffer shortData = (ShortBuffer)data;
		shortData.put(indices).position(0);
		
		dirty = true;
	}
	
	public void setIndices(short[] indices, int offset, int length) {
		if (format != Format.SHORT) throw new UnsupportedOperationException("This method is short version.");
		if (indices == null) throw new IllegalArgumentException("'indices' is null.");

		ShortBuffer shortData = (ShortBuffer)data;
		shortData.put(indices, offset, length).position(0);
		
		dirty = true;
	}
	
	public void bind(){
		buffer.bind();
		if(dirty){
			buffer.setData(data, 0, size * formatInfo.get(format).bytes);
			dirty = false;
		}
	}
	
	public void unbind(){
		buffer.unbind();
	}

	@Override
	public void loadResource(ResourceManager resources) {
		this.buffer = VBO.createIndexBuffer(size * formatInfo.get(format).bytes, VBO.STATIC_DRAW);

		dirty = true;
	}

	private static Buffer allocateBuffer(int size, Format format) {
		Buffer buffer;
		switch (format) {
		case BYTE:
			buffer = ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());
			break;
		case SHORT:
			buffer = ByteBuffer.allocateDirect(size * 2).order(ByteOrder.nativeOrder()).asShortBuffer();
			break;
		default:
			throw new IllegalArgumentException("Unsupported format.");
		}
		return buffer;
	}

}
