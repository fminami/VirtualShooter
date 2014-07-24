package com.example.shooter.util;

public final class Vector3 {
	
	public static final int SIZE = 3;
	public static final int BYTE_SIZE = SIZE * 4;
	
	public static void zero(float[] result, int offset){
		result[offset + 0] = 0.0f;
		result[offset + 1] = 0.0f;
		result[offset + 2] = 0.0f;
	}
	
	public static void one(float[] result, int offset){
		result[offset + 0] = 1.0f;
		result[offset + 1] = 1.0f;
		result[offset + 2] = 1.0f;
	}
	
	public static void unitX(float[] result, int offset){
		result[offset + 0] = 1.0f;
		result[offset + 1] = 0.0f;
		result[offset + 2] = 0.0f;
	}
	
	public static void unitY(float[] result, int offset){
		result[offset + 0] = 0.0f;
		result[offset + 1] = 1.0f;
		result[offset + 2] = 0.0f;
	}
	
	public static void unitZ(float[] result, int offset){
		result[offset + 0] = 0.0f;
		result[offset + 1] = 0.0f;
		result[offset + 2] = 1.0f;
	}

	public static void negate(float[] result, int offset, float[] v, int vOffset){
		result[offset + 0] = -v[vOffset + 0];
		result[offset + 1] = -v[vOffset + 1];
		result[offset + 2] = -v[vOffset + 2];
	}
	
	public static void add(float[] result, int offset, float[] v1, int v1Offset, float[] v2, int v2Offset){
		result[offset + 0] = v1[v1Offset + 0] + v2[v2Offset + 0];
		result[offset + 1] = v1[v1Offset + 1] + v2[v2Offset + 1];
		result[offset + 2] = v1[v1Offset + 2] + v2[v2Offset + 2];
	}
	
	public static void add(float[] result, int offset, float[] v, int vOffset, float scalar){
		result[offset + 0] = v[vOffset + 0] + scalar;
		result[offset + 1] = v[vOffset + 1] + scalar;
		result[offset + 2] = v[vOffset + 2] + scalar;
	}
	
	public static void sub(float[] result, int offset, float[] v1, int v1Offset, float[] v2, int v2Offset){
		result[offset + 0] = v1[v1Offset + 0] - v2[v2Offset + 0];
		result[offset + 1] = v1[v1Offset + 1] - v2[v2Offset + 1];
		result[offset + 2] = v1[v1Offset + 2] - v2[v2Offset + 2];
	}
	
	public static void sub(float[] result, int offset, float[] v, int vOffset, float scalar){
		result[offset + 0] = v[vOffset + 0] - scalar;
		result[offset + 1] = v[vOffset + 1] - scalar;
		result[offset + 2] = v[vOffset + 2] - scalar;
	}
	
	public static void mul(float[] result, int offset, float[] v1, int v1Offset, float[] v2, int v2Offset){
		result[offset + 0] = v1[v1Offset + 0] * v2[v2Offset + 0];
		result[offset + 1] = v1[v1Offset + 1] * v2[v2Offset + 1];
		result[offset + 2] = v1[v1Offset + 2] * v2[v2Offset + 2];
	}
	
	public static void mul(float[] result, int offset, float[] v, int vOffset, float scalar){
		result[offset + 0] = v[vOffset + 0] * scalar;
		result[offset + 1] = v[vOffset + 1] * scalar;
		result[offset + 2] = v[vOffset + 2] * scalar;
	}

	public static void div(float[] result, int offset, float[] v1, int v1Offset, float[] v2, int v2Offset){
		result[offset + 0] = v1[v1Offset + 0] / v2[v2Offset + 0];
		result[offset + 1] = v1[v1Offset + 1] / v2[v2Offset + 1];
		result[offset + 2] = v1[v1Offset + 2] / v2[v2Offset + 2];
	}
	
	public static void div(float[] result, int offset, float[] v, int vOffset, float scalar){
		result[offset + 0] = v[vOffset + 0] / scalar;
		result[offset + 1] = v[vOffset + 1] / scalar;
		result[offset + 2] = v[vOffset + 2] / scalar;
	}
	
	public static float dot(float[] v1, int v1Offset, float[] v2, int v2Offset){
		return (v1[v1Offset + 0] * v2[v2Offset + 0] + v1[v1Offset + 1] * v2[v2Offset + 1] + v1[v1Offset + 2] * v2[v2Offset + 2]);
	}
	
	public static void cross(float[] result, int offset, float[] v1, int v1Offset, float[] v2, int v2Offset){
		result[offset + 0] = v1[v1Offset + 1] * v2[v2Offset + 2] - v1[v1Offset + 2] * v2[v2Offset + 1];
		result[offset + 1] = v1[v1Offset + 2] * v2[v2Offset + 0] - v1[v1Offset + 0] * v2[v2Offset + 2];
		result[offset + 2] = v1[v1Offset + 0] * v2[v2Offset + 1] - v1[v1Offset + 1] * v2[v2Offset + 0];
	}
	
	public static float min(float[] v, int offset){
		return Math.min(Math.min(v[offset + 0], v[offset + 1]), v[offset + 2]);
	}
	
	public static float max(float[] v, int offset){
		return Math.max(Math.max(v[offset + 0], v[offset + 1]), v[offset + 2]);
	}
	
	public static float length(float[] v, int offset){
		return (float)Math.sqrt(v[offset + 0] * v[offset + 0] + v[offset + 1] * v[offset + 1] + v[offset + 2] * v[offset + 2]);
	}
	
	public static float lengthSquared(float[] v, int offset){
		return (v[offset + 0] * v[offset + 0] + v[offset + 1] * v[offset + 1] + v[offset + 2] * v[offset + 2]);
	}
	
	public static void normalize(float[] result, int offset, float[] v, int vOffset){
		float length = (float)Math.sqrt(v[vOffset + 0] * v[vOffset + 0] + v[vOffset + 1] * v[vOffset + 1] + v[vOffset + 2] * v[vOffset + 2]);
		result[offset + 0] = v[vOffset + 0] / length;
		result[offset + 1] = v[vOffset + 1] / length;
		result[offset + 2] = v[vOffset + 2] / length;
	}
	
	public static void lerp(float[] result, int offset, float[] v1, int v1Offset, float[] v2, int v2Offset, float t){
		result[offset + 0] = v1[v1Offset + 0] * (1.0f - t) + v2[v2Offset + 0] * t;
		result[offset + 1] = v1[v1Offset + 1] * (1.0f - t) + v2[v2Offset + 1] * t;
		result[offset + 2] = v1[v1Offset + 2] * (1.0f - t) + v2[v2Offset + 2] * t;
	}
}
