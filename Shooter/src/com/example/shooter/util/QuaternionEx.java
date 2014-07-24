package com.example.shooter.util;


public final class QuaternionEx {
	
	public static void identity(FloatArray array, int result) {
		float[] buffer = array.getBuffer();
		Quaternion.identity(buffer, result);
	}

	public static void fromAxisAngle(FloatArray array, int result, int axis, float angle) {
		float[] buffer = array.getBuffer();
		Quaternion.fromAxisAngle(buffer, result, buffer, axis, angle);
	}

	public static void fromYawPitchRoll(FloatArray array, int result, float yaw, float pitch, float roll) {
		float[] buffer = array.getBuffer();
		Quaternion.fromYawPitchRoll(buffer, result, yaw, pitch, roll);
	}
	
	public static void fromRotationMatrix(FloatArray array, int result, int m) {
		float[] buffer = array.getBuffer();
		Quaternion.fromRotationMatrix(buffer, result, buffer, m);
	}

	public static void negate(FloatArray array, int result, int q) {
		float[] buffer = array.getBuffer();
		Quaternion.negate(buffer, result, buffer, q);
	}

	public static void add(FloatArray array, int result, int q1, int q2) {
		float[] buffer = array.getBuffer();
		Quaternion.add(buffer, result, buffer, q1, buffer, q2);
	}

	public static void sub(FloatArray array, int result, int q1, int q2) {
		float[] buffer = array.getBuffer();
		Quaternion.sub(buffer, result, buffer, q1, buffer, q2);
	}

	public static void mul(FloatArray array, int result, int q1, int q2) {
		float[] buffer = array.getBuffer();
		Quaternion.mul(buffer, result, buffer, q1, buffer, q2);
	}

	public static void mul(FloatArray array, int result, int q, float scalar) {
		float[] buffer = array.getBuffer();
		Quaternion.mul(buffer, result, buffer, q, scalar);
	}

	public static void div(FloatArray array, int result, int q1, int q2) {
		float[] buffer = array.getBuffer();
		Quaternion.div(buffer, result, buffer, q1, buffer, q2);
	}

	public static void div(FloatArray array, int result, int q, float scalar) {
		float[] buffer = array.getBuffer();
		Quaternion.div(buffer, result, buffer, q, scalar);
	}

	public static void conjugate(FloatArray array, int result, int q) {
		float[] buffer = array.getBuffer();
		Quaternion.conjugate(buffer, result, buffer, q);
	}

	public static void inverse(FloatArray array, int result, int q) {
		float[] buffer = array.getBuffer();
		Quaternion.inverse(buffer, result, buffer, q);
	}

	public static float dot(FloatArray array, int q1, int q2) {
		float[] buffer = array.getBuffer();
		return Quaternion.dot(buffer, q1, buffer, q2);
	}

	public static float length(FloatArray array, int q) {
		float[] buffer = array.getBuffer();
		return Quaternion.length(buffer, q);
	}

	public static float lengthSquared(FloatArray array, int q) {
		float[] buffer = array.getBuffer();
		return Quaternion.lengthSquared(buffer, q);
	}

	public static void normalize(FloatArray array, int result, int q) {
		float[] buffer = array.getBuffer();
		Quaternion.normalize(buffer, result, buffer, q);
	}

	public static void lerp(FloatArray array, int result, int q1, int q2, float t) {
		float[] buffer = array.getBuffer();
		Quaternion.lerp(buffer, result, buffer, q1, buffer, q2, t);
	}

	public static void slerp(FloatArray array, int result, int q1, int q2, float t) {
		float[] buffer = array.getBuffer();
		Quaternion.slerp(buffer, result, buffer, q1, buffer, q2, t);
	}

	public static void transform(FloatArray array, int result, int v, int q) {
		float[] buffer = array.getBuffer();
		Quaternion.transform(buffer, result, buffer, v, buffer, q);
	}
}
