package com.example.shooter.util;


public final class Vector4Ex {

	public static void zero(FloatArray array, int result) {
		float[] buffer = array.getBuffer();
		Vector4.zero(buffer, result);
	}

	public static void one(FloatArray array, int result) {
		float[] buffer = array.getBuffer();
		Vector4.one(buffer, result);
	}

	public static void unitX(FloatArray array, int result) {
		float[] buffer = array.getBuffer();
		Vector4.unitX(buffer, result);
	}

	public static void unitY(FloatArray array, int result) {
		float[] buffer = array.getBuffer();
		Vector4.unitY(buffer, result);
	}

	public static void unitZ(FloatArray array, int result) {
		float[] buffer = array.getBuffer();
		Vector4.unitZ(buffer, result);
	}

	public static void unitW(FloatArray array, int result) {
		float[] buffer = array.getBuffer();
		Vector4.unitW(buffer, result);
	}

	public static void extend(FloatArray array, int result, float w) {
		float[] buffer = array.getBuffer();
		Vector4.extend(buffer, result, w);
	}

	public static void negate(FloatArray array, int result, int v) {
		float[] buffer = array.getBuffer();
		Vector4.negate(buffer, result, buffer, v);
	}

	public static void add(FloatArray array, int result, int v1, int v2) {
		float[] buffer = array.getBuffer();
		Vector4.add(buffer, result, buffer, v1, buffer, v2);
	}

	public static void add(FloatArray array, int result, int v, float scalar) {
		float[] buffer = array.getBuffer();
		Vector4.add(buffer, result, buffer, v, scalar);
	}

	public static void sub(FloatArray array, int result, int v1, int v2) {
		float[] buffer = array.getBuffer();
		Vector4.sub(buffer, result, buffer, v1, buffer, v2);
	}

	public static void sub(FloatArray array, int result, int v, float scalar) {
		float[] buffer = array.getBuffer();
		Vector4.sub(buffer, result, buffer, v, scalar);
	}

	public static void mul(FloatArray array, int result, int v1, int v2) {
		float[] buffer = array.getBuffer();
		Vector4.mul(buffer, result, buffer, v1, buffer, v2);
	}

	public static void mul(FloatArray array, int result, int v, float scalar) {
		float[] buffer = array.getBuffer();
		Vector4.mul(buffer, result, buffer, v, scalar);
	}

	public static void div(FloatArray array, int result, int v1, int v2) {
		float[] buffer = array.getBuffer();
		Vector4.div(buffer, result, buffer, v1, buffer, v2);
	}

	public static void div(FloatArray array, int result, int v, float scalar) {
		float[] buffer = array.getBuffer();
		Vector4.div(buffer, result, buffer, v, scalar);
	}

	public static float dot(FloatArray array, int v1, int v2) {
		float[] buffer = array.getBuffer();
		return Vector4.dot(buffer, v1, buffer, v2);
	}
	
	public static float min(FloatArray array, int v){
		float[] buffer = array.getBuffer();
		return Vector4.min(buffer, v);
	}
	
	public static float max(FloatArray array, int v){
		float[] buffer = array.getBuffer();
		return Vector4.max(buffer, v);
	}

	public static float length(FloatArray array, int v) {
		float[] buffer = array.getBuffer();
		return Vector4.length(buffer, v);
	}

	public static float lengthSquared(FloatArray array, int v) {
		float[] buffer = array.getBuffer();
		return Vector4.lengthSquared(buffer, v);
	}

	public static void normalize(FloatArray array, int result, int v) {
		float[] buffer = array.getBuffer();
		Vector4.normalize(buffer, result, buffer, v);
	}

	public static void lerp(FloatArray array, int result, int v1, int v2, float t) {
		float[] buffer = array.getBuffer();
		Vector4.lerp(buffer, result, buffer, v1, buffer, v2, t);
	}
}
