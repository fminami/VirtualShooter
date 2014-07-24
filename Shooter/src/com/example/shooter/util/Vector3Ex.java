package com.example.shooter.util;


public final class Vector3Ex {

	public static void zero(FloatArray array, int result) {
		float[] buffer = array.getBuffer();
		Vector3.zero(buffer, result);
	}

	public static void one(FloatArray array, int result) {
		float[] buffer = array.getBuffer();
		Vector3.one(buffer, result);
	}

	public static void unitX(FloatArray array, int result) {
		float[] buffer = array.getBuffer();
		Vector3.unitX(buffer, result);
	}

	public static void unitY(FloatArray array, int result) {
		float[] buffer = array.getBuffer();
		Vector3.unitY(buffer, result);
	}

	public static void unitZ(FloatArray array, int result) {
		float[] buffer = array.getBuffer();
		Vector3.unitZ(buffer, result);
	}

	public static void negate(FloatArray array, int result, int v) {
		float[] buffer = array.getBuffer();
		Vector3.negate(buffer, result, buffer, v);
	}

	public static void add(FloatArray array, int result, int v1, int v2) {
		float[] buffer = array.getBuffer();
		Vector3.add(buffer, result, buffer, v1, buffer, v2);
	}

	public static void add(FloatArray array, int result, int v, float scalar) {
		float[] buffer = array.getBuffer();
		Vector3.add(buffer, result, buffer, v, scalar);
	}

	public static void sub(FloatArray array, int result, int v1, int v2) {
		float[] buffer = array.getBuffer();
		Vector3.sub(buffer, result, buffer, v1, buffer, v2);
	}

	public static void sub(FloatArray array, int result, int v, float scalar) {
		float[] buffer = array.getBuffer();
		Vector3.sub(buffer, result, buffer, v, scalar);
	}

	public static void mul(FloatArray array, int result, int v1, int v2) {
		float[] buffer = array.getBuffer();
		Vector3.mul(buffer, result, buffer, v1, buffer, v2);
	}

	public static void mul(FloatArray array, int result, int v, float scalar) {
		float[] buffer = array.getBuffer();
		Vector3.mul(buffer, result, buffer, v, scalar);
	}

	public static void div(FloatArray array, int result, int v1, int v2) {
		float[] buffer = array.getBuffer();
		Vector3.div(buffer, result, buffer, v1, buffer, v2);
	}

	public static void div(FloatArray array, int result, int v, float scalar) {
		float[] buffer = array.getBuffer();
		Vector3.div(buffer, result, buffer, v, scalar);
	}

	public static float dot(FloatArray array, int v1, int v2) {
		float[] buffer = array.getBuffer();
		return Vector3.dot(buffer, v1, buffer, v2);
	}

	public static void cross(FloatArray array, int result, int v1, int v2) {
		float[] buffer = array.getBuffer();
		Vector3.cross(buffer, result, buffer, v1, buffer, v2);
	}
	
	public static float min(FloatArray array, int v){
		float[] buffer = array.getBuffer();
		return Vector3.min(buffer, v);
	}
	
	public static float max(FloatArray array, int v){
		float[] buffer = array.getBuffer();
		return Vector3.max(buffer, v);
	}

	public static float length(FloatArray array, int v) {
		float[] buffer = array.getBuffer();
		return Vector3.length(buffer, v);
	}

	public static float lengthSquared(FloatArray array, int v) {
		float[] buffer = array.getBuffer();
		return Vector3.lengthSquared(buffer, v);
	}

	public static void normalize(FloatArray array, int result, int v) {
		float[] buffer = array.getBuffer();
		Vector3.normalize(buffer, result, buffer, v);
	}

	public static void lerp(FloatArray array, int result, int v1, int v2, float t) {
		float[] buffer = array.getBuffer();
		Vector3.lerp(buffer, result, buffer, v1, buffer, v2, t);
	}
}
