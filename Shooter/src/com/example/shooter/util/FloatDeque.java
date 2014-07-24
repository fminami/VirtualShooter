package com.example.shooter.util;

public class FloatDeque implements PrimitiveDeque{
	
	public final Policy policy;
	
	private float[] buffer;
	
	private int start;
	
	private int end;
	
	private static final int DEFAULT_CAPACITY = 8;
	
	private static final int SCALING_RATIO = 2;
	
	public FloatDeque(){
		this(DEFAULT_CAPACITY, Policy.RESIZE);
	}
	
	public FloatDeque(int capacity){
		this(capacity, Policy.RESIZE);
	}
	
	public FloatDeque(int capacity, Policy policy){
		if(capacity <= 0) throw new IllegalArgumentException("capacity");
		
		this.buffer = new float[capacity];
		this.policy = policy;
	}
	
	public int size() {
		return (end - start);
	}
	
	public int capacity(){
		return buffer.length;
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

	public void addFirst(float value) {
		if(!checkSize()){
			end--;
		}
		
		start--;
		if(start < 0){
			start += buffer.length;
			end += buffer.length;
		}
		
		buffer[start] = value;
	}

	public void addLast(float value) {
		if(!checkSize()){
			start++;
			if(start >= buffer.length){
				start -= buffer.length;
				end -= buffer.length;
			}
		}
		
		int last = wrap(end, buffer.length);
		buffer[last] = value;
		
		end++;
	}
	
	public void removeFirst() {
		if(isEmpty()) throw new IllegalStateException("The deque is empty.");
		
		start++;
		if(start >= buffer.length){
			start -= buffer.length;
			end -= buffer.length;
		}
	}

	public void removeLast() {
		if(isEmpty()) throw new IllegalStateException("The deque is empty.");
		
		end--;
	}

	public float pollFirst() {
		if(isEmpty()) throw new IllegalStateException("The deque is empty.");
		
		float value = buffer[start];
		
		start++;
		if(start >= buffer.length){
			start -= buffer.length;
			end -= buffer.length;
		}
		
		return value;
	}

	public float pollLast() {
		if(isEmpty()) throw new IllegalStateException("The deque is empty.");
		
		end--;
		
		int last = wrap(end, buffer.length);
		float value = buffer[last];
		
		return value;
	}
	
	public float get(int index) {
		if(index < 0 || index >= size()) throw new IndexOutOfBoundsException("index");
		
		return buffer[wrap(start + index, buffer.length)];
	}

	public float getFirst() {
		if(isEmpty()) throw new IllegalStateException("The deque is empty.");
		
		return buffer[start];
	}

	public float getLast() {
		if(isEmpty()) throw new IllegalStateException("The deque is empty.");
		
		return buffer[wrap(end - 1, buffer.length)];
	}
	
	public void copy(float[] e, int offset){
		copy(e, offset, 0, size());
	}
	
	public void copy(float[] e, int offset, int index, int length){
		if(index < 0 || index + length > size()) throw new IndexOutOfBoundsException("index");
		
		int first = wrap(start + index, buffer.length);
		int last = wrap(start + (index + length), buffer.length);
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
