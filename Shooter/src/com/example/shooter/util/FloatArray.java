package com.example.shooter.util;

public class FloatArray {

	public static final int UNIT_SIZE = 4;

	private static final int DEFAULT_TEMP_BUFFER_SIZE = 16;
	
	private float[] buffer;

	private int fixedBufferSize;

	private int tempBufferSize;

	public FloatArray() {
		this(DEFAULT_TEMP_BUFFER_SIZE);
	}

	public FloatArray(int size) {
		if (size < 0) throw new IndexOutOfBoundsException("size");

		this.tempBufferSize = size;
	}
	
	public float[] getBuffer(){
		return buffer;
	}

	public int getVectorHandle() {
		return getHandle(1);
	}

	public int getMatrixHandle() {
		return getHandle(4);
	}

	public int getQuaternionHandle() {
		return getHandle(1);
	}

	public FloatArray freeze() {
		if (isFreezed()) throw new IllegalStateException("Already freezed.");

		int size = (fixedBufferSize + tempBufferSize) * UNIT_SIZE;
		buffer = new float[size];
		
		return this;
	}
	
	public boolean isFreezed(){
		return (buffer != null);
	}
	
	public int size(){
		return (fixedBufferSize + tempBufferSize);
	}

	public int t(int n) {
		return ((n + fixedBufferSize) * UNIT_SIZE);
	}
	
	public void set(int offset, float x){
		buffer[offset + 0] = x;
	}
	
	public void set(int offset, float x, float y){
		buffer[offset + 0] = x;
		buffer[offset + 1] = y;
	}
	
	public void set(int offset, float x, float y, float z){
		buffer[offset + 0] = x;
		buffer[offset + 1] = y;
		buffer[offset + 2] = z;
	}
	
	public void set(int offset, float x, float y, float z, float w){
		buffer[offset + 0] = x;
		buffer[offset + 1] = y;
		buffer[offset + 2] = z;
		buffer[offset + 3] = w;
	}
	
	public static void copy(FloatArray src, int srcOffset, float[] dst, int dstOffset, int length){
		System.arraycopy(src.buffer, srcOffset, dst, dstOffset, length * UNIT_SIZE);
	}
	
	public static void copy(float[] src, int srcOffset, FloatArray dst, int dstOffset, int length){
		System.arraycopy(src, srcOffset, dst.buffer, dstOffset, length * UNIT_SIZE);
	}
	
	public static void copy(FloatArray src, int srcOffset, FloatArray dst, int dstOffset, int length){
		System.arraycopy(src.buffer, srcOffset, dst.buffer, dstOffset, length * UNIT_SIZE);
	}

	private int getHandle(int size) {
		if (isFreezed()) throw new IllegalStateException("Already freezed.");

		int handle = fixedBufferSize * UNIT_SIZE;
		fixedBufferSize += size;
		return handle;
	}
}
