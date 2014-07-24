package com.example.shooter.util;

public final class MatrixEx {

	public static void identity(FloatArray array, int result) {
		float[] buffer = array.getBuffer();
		Matrix.identity(buffer, result);
	}

	public static void fromAxisAngle(FloatArray array, int result, int axis, float angle) {
		float[] buffer = array.getBuffer();
		Matrix.fromAxisAngle(buffer, result, buffer, axis, angle);
	}

	public static void fromYawPitchRoll(FloatArray array, int result, float yaw, float pitch, float roll) {
		float[] buffer = array.getBuffer();
		Matrix.fromYawPitchRoll(buffer, result, yaw, pitch, roll);
	}

	public static void fromQuaternion(FloatArray array, int result, int q) {
		float[] buffer = array.getBuffer();
		Matrix.fromQuaternion(buffer, result, buffer, q);
	}

	public static void extract(FloatArray array, int result, int m) {
		float[] buffer = array.getBuffer();
		Matrix.extract(buffer, result, buffer, m);
	}

	public static void row0(FloatArray array, int result, int m) {
		float[] buffer = array.getBuffer();
		Matrix.row0(buffer, result, buffer, m);
	}

	public static void row1(FloatArray array, int result, int m) {
		float[] buffer = array.getBuffer();
		Matrix.row1(buffer, result, buffer, m);
	}

	public static void row2(FloatArray array, int result, int m) {
		float[] buffer = array.getBuffer();
		Matrix.row2(buffer, result, buffer, m);
	}

	public static void row3(FloatArray array, int result, int m) {
		float[] buffer = array.getBuffer();
		Matrix.row3(buffer, result, buffer, m);
	}

	public static void col0(FloatArray array, int result, int m) {
		float[] buffer = array.getBuffer();
		Matrix.col0(buffer, result, buffer, m);
	}

	public static void col1(FloatArray array, int result, int m) {
		float[] buffer = array.getBuffer();
		Matrix.col1(buffer, result, buffer, m);
	}

	public static void col2(FloatArray array, int result, int m) {
		float[] buffer = array.getBuffer();
		Matrix.col2(buffer, result, buffer, m);
	}

	public static void col3(FloatArray array, int result, int m) {
		float[] buffer = array.getBuffer();
		Matrix.col3(buffer, result, buffer, m);
	}

	public static void negate(FloatArray array, int result, int m) {
		float[] buffer = array.getBuffer();
		Matrix.negate(buffer, result, buffer, m);
	}

	public static void add(FloatArray array, int result, int m1, int m2) {
		float[] buffer = array.getBuffer();
		Matrix.add(buffer, result, buffer, m1, buffer, m2);
	}

	public static void sub(FloatArray array, int result, int m1, int m2) {
		float[] buffer = array.getBuffer();
		Matrix.sub(buffer, result, buffer, m1, buffer, m2);
	}

	public static void mul(FloatArray array, int result, int m1, int m2) {
		float[] buffer = array.getBuffer();
		Matrix.mul(buffer, result, buffer, m1, buffer, m2);
	}

	public static void mul(FloatArray array, int result, int m, float scalar) {
		float[] buffer = array.getBuffer();
		Matrix.mul(buffer, result, buffer, m, scalar);
	}

	public static void div(FloatArray array, int result, int m1, int m2) {
		float[] buffer = array.getBuffer();
		Matrix.div(buffer, result, buffer, m1, buffer, m2);
	}

	public static void div(FloatArray array, int result, int m, float scalar) {
		float[] buffer = array.getBuffer();
		Matrix.div(buffer, result, buffer, m, scalar);
	}

	public static void transpose(FloatArray array, int result, int m) {
		float[] buffer = array.getBuffer();
		Matrix.transpose(buffer, result, buffer, m);
	}

	public static void invert(FloatArray array, int result, int m) {
		float[] buffer = array.getBuffer();
		Matrix.invert(buffer, result, buffer, m);
	}

	public static void transform(FloatArray array, int result, int m, int v) {
		float[] buffer = array.getBuffer();
		Matrix.transform(buffer, result, buffer, m, buffer, v);
	}

	public static void scale(FloatArray array, int result, int scale) {
		float[] buffer = array.getBuffer();
		Matrix.scale(buffer, result, buffer, scale);
	}

	public static void rotationX(FloatArray array, int result, float angle) {
		float[] buffer = array.getBuffer();
		Matrix.rotationX(buffer, result, angle);
	}

	public static void rotationY(FloatArray array, int result, float angle) {
		float[] buffer = array.getBuffer();
		Matrix.rotationY(buffer, result, angle);
	}

	public static void rotationZ(FloatArray array, int result, float angle) {
		float[] buffer = array.getBuffer();
		Matrix.rotationZ(buffer, result, angle);
	}

	public static void rotationEuler(FloatArray array, int result, int rotation) {
		float[] buffer = array.getBuffer();
		Matrix.rotationEuler(buffer, result, buffer, rotation);
	}

	public static void billboard(FloatArray array, int result, int forward, int up) {
		float[] buffer = array.getBuffer();
		Matrix.billboard(buffer, result, buffer, forward, buffer, up);
	}

	public static void cylindricalBillboard(FloatArray array, int result, int axis, int forward, int up) {
		float[] buffer = array.getBuffer();
		Matrix.cylindricalBillboard(buffer, result, buffer, axis, buffer, forward, buffer, up);
	}

	public static void translation(FloatArray array, int result, int translation) {
		float[] buffer = array.getBuffer();
		Matrix.translation(buffer, result, buffer, translation);
	}

	public static void lookAt(FloatArray array, int result, int eye, int center, int up) {
		float[] buffer = array.getBuffer();
		Matrix.lookAt(buffer, result, buffer, eye, buffer, center, buffer, up);
	}

	public static void orthographic(FloatArray array, int result, float left, float right, float bottom, float top, float near, float far) {
		float[] buffer = array.getBuffer();
		Matrix.orthographic(buffer, result, left, right, bottom, top, near, far);
	}

	public static void perspective(FloatArray array, int result, float fovy, float aspect, float near, float far) {
		float[] buffer = array.getBuffer();
		Matrix.perspective(buffer, result, fovy, aspect, near, far);
	}

	public static void frustum(FloatArray array, int result, float left, float right, float bottom, float top, float near, float far) {
		float[] buffer = array.getBuffer();
		Matrix.frustum(buffer, result, left, right, bottom, top, near, far);
	}
}
