package com.example.shooter.graphics;

import java.nio.FloatBuffer;

public class FloatVertexBuffer extends VertexBuffer {
	
	private final FloatBuffer data;
	
	public FloatVertexBuffer(int size, Format[] formats) {
		super(size, formats);
		
		this.data = getByteBuffer().asFloatBuffer();
	}

	public void setVertices(float[] vertices) {
		if(vertices == null) throw new IllegalArgumentException("'vertices' is null");
		
		data.put(vertices).position(0);
		
		update();
	}

	public void setVertices(float[] vertices, int offset, int length) {
		if(vertices == null) throw new IllegalArgumentException("'vertices' is null");
		
		data.put(vertices, offset, length).position(0);
		
		update();
	}
}
