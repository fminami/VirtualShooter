package com.example.shooter.util;

public class FloatVectorDeque implements PrimitiveDeque{
	
	public final int block;
	
	public final Policy policy;
	
	private float[] buffer;
	
	private int start;
	
	private int end;
	
	private static final int DEFAULT_CAPACITY = 8;
	
	private static final int SCALING_RATIO = 2;
	
	public FloatVectorDeque(int block){
		this(block, DEFAULT_CAPACITY, Policy.RESIZE);
	}
	
	public FloatVectorDeque(int block, int capacity){
		this(block, capacity, Policy.RESIZE);
	}
	
	public FloatVectorDeque(int block, int capacity, Policy policy){
		if(block <= 0) throw new IllegalArgumentException("block");
		if(capacity <= 0) throw new IllegalArgumentException("capacity");
		
		this.buffer = new float[block * capacity];
		this.block = block;
		this.policy = policy;
	}
	
	public int size() {
		return ((end - start) / block);
	}
	
	public int capacity(){
		return (buffer.length / block);
	}
	
	public boolean isEmpty() {
		return (start == end);
	}
	
	public Policy getPolicy(){
		return policy;
	}

	public void clear() {
		start = end = 0;
	}

	public void addFirst(float[] e, int offset) {
		if(!checkSize()){
			end -= block;
		}
		
		start -= block;
		if(start < 0){
			start += buffer.length;
			end += buffer.length;
		}
		
		for(int i = 0; i < block; i++){
			buffer[start + i] = e[offset + i];
		}
	}

	public void addLast(float[] e, int offset) {
		if(!checkSize()){
			start += block;
			if(start >= buffer.length){
				start -= buffer.length;
				end -= buffer.length;
			}
		}
		
		int last = wrap(end, buffer.length);
		for(int i = 0; i < block; i++){
			buffer[last + i] = e[offset + i];
		}
		
		end += block;
	}
	
	public void removeFirst() {
		if(isEmpty()) throw new IllegalStateException("The deque is empty.");
		
		start += block;
		if(start >= buffer.length){
			start -= buffer.length;
			end -= buffer.length;
		}
	}

	public void removeLast() {
		if(isEmpty()) throw new IllegalStateException("The deque is empty.");
		
		end -= block;
	}

	public void pollFirst(float[] e, int offset) {
		if(isEmpty()) throw new IllegalStateException("The deque is empty.");
		
		for(int i = 0; i < block; i++){
			e[offset + i] = buffer[start + i];
		}
		
		start += block;
		if(start >= buffer.length){
			start -= buffer.length;
			end -= buffer.length;
		}
	}

	public void pollLast(float[] e, int offset) {
		if(isEmpty()) throw new IllegalStateException("The deque is empty.");
		
		end -= block;
		
		int last = wrap(end, buffer.length);
		for(int i = 0; i < block; i++){
			e[offset + i] = buffer[last + i];
		}
	}
	
	public void get(float[] e, int offset, int index) {
		if(index < 0 || index >= size()) throw new IndexOutOfBoundsException("index");
		
		int top = wrap(start + index * block, buffer.length);
		for(int i = 0; i < block; i++){
			e[offset + i] = buffer[top + i];
		}
	}

	public void getFirst(float[] e, int offset) {
		if(isEmpty()) throw new IllegalStateException("The deque is empty.");
		
		for(int i = 0; i < block; i++){
			e[offset + i] = buffer[start + i];
		}
	}

	public void getLast(float[] e, int offset) {
		if(isEmpty()) throw new IllegalStateException("The deque is empty.");
		
		int last = wrap(end - block, buffer.length);
		for(int i = 0; i < block; i++){
			e[offset + i] = buffer[last + i];
		}
	}
	
	public void copy(float[] e, int offset){
		copy(e, offset, 0, size());
	}
	
	public void copy(float[] e, int offset, int index, int length){
		if(index < 0 || index + length > size()) throw new IndexOutOfBoundsException("index");
		
		int first = wrap(start + index * block, buffer.length);
		int last = wrap(start + (index + length) * block, buffer.length);
		if(first <= last){
			System.arraycopy(buffer, first, e, offset, last - first);
		}else{
			int count = buffer.length - first;
			System.arraycopy(buffer, first, e, offset, count);
			System.arraycopy(buffer, 0, e, offset + count, last);
		}
	}
	
	private boolean checkSize(){
		if(start != end && start == wrap(end, buffer.length)){
			switch(policy){
			case NONE:
				throw new IllegalStateException("The deque is full.");
			case OVERWRITE:
				return false;
			case RESIZE:
				resize();
				break;
			default:
				throw new IllegalStateException("Unknown policy.");
			}
		}
		
		return true;
	}
	
	private void resize(){
		int newSize = buffer.length * SCALING_RATIO;
		float[] newBuffer = new float[newSize];
		
		int count = buffer.length - start;
		System.arraycopy(buffer, start, newBuffer, 0, count);
		System.arraycopy(buffer, 0, newBuffer, count, start);
		
		start = 0;
		end = buffer.length;
		
		buffer = newBuffer;
	}
	
	private static int wrap(int n, int max){
		return (n < 0 ? (max - (-n % max)) : n % max);
	}

}
