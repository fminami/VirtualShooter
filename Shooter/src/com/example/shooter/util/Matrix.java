package com.example.shooter.util;

public final class Matrix {
	
	public static final int SIZE = 16;
	public static final int BYTE_SIZE = SIZE * 4;

	private static final float[] sTemp = new float[32];
	
	private static final float toDegrees = (float)(180.0 / Math.PI);

	public static void identity(float[] result, int offset) {
		android.opengl.Matrix.setIdentityM(result, offset);
	}

	public static void fromAxisAngle(float[] result, int offset, float[] axis, int axisOffset, float angle) {
		android.opengl.Matrix.setRotateM(result, offset, angle * toDegrees, axis[axisOffset + 0], axis[axisOffset + 1], axis[axisOffset + 2]);
	}

	public static void fromYawPitchRoll(float[] result, int offset, float yaw, float pitch, float roll) {
		double theta = yaw, phi = pitch, psi = roll;
		double cosTheta = Math.cos(theta), sinTheta = Math.sin(theta);
		double cosPhi = Math.cos(phi), sinPhi = Math.sin(phi);
		double cosPsi = Math.cos(psi), sinPsi = Math.sin(psi);
		result[offset + 0] = (float)(cosTheta * cosPsi);
		result[offset + 1] = (float)(cosTheta * sinPsi);
		result[offset + 2] = (float)(-sinTheta);
		result[offset + 3] = 0.0f;
		result[offset + 4] = (float)(-cosPhi * sinPsi + sinTheta * sinPhi * cosPsi);
		result[offset + 5] = (float)(cosPhi * cosPsi + sinTheta * sinPhi * sinPsi);
		result[offset + 6] = (float)(cosTheta * sinPhi);
		result[offset + 7] = 0.0f;
		result[offset + 8] = (float)(sinPhi * sinPsi + sinTheta * cosPhi * cosPsi);
		result[offset + 9] = (float)(-sinPhi * cosPsi + sinTheta * cosPhi * sinPsi);
		result[offset + 10] = (float)(cosTheta * cosPhi);
		result[offset + 11] = 0.0f;
		result[offset + 12] = 0.0f;
		result[offset + 13] = 0.0f;
		result[offset + 14] = 0.0f;
		result[offset + 15] = 1.0f;
	}

	public static void fromQuaternion(float[] result, int offset, float[] q, int qOffset) {
		float x = q[qOffset + 0], y = q[qOffset + 1], z = q[qOffset + 2], w = q[qOffset + 3];
		result[offset + 0] = 1.0f - 2.0f * (y * y + z * z);
		result[offset + 1] = 2.0f * (x * y + w * z);
		result[offset + 2] = 2.0f * (x * z - w * y);
		result[offset + 3] = 0.0f;
		result[offset + 4] = 2.0f * (x * y - w * z);
		result[offset + 5] = 1.0f - 2.0f * (x * x + z * z);
		result[offset + 6] = 2.0f * (w * x + y * z);
		result[offset + 7] = 0.0f;
		result[offset + 8] = 2.0f * (w * y + x * z);
		result[offset + 9] = 2.0f * (y * z - w * x);
		result[offset + 10] = 1.0f - 2.0f * (x * x + y * y);
		result[offset + 11] = 0.0f;
		result[offset + 12] = 0.0f;
		result[offset + 13] = 0.0f;
		result[offset + 14] = 0.0f;
		result[offset + 15] = 1.0f;
	}
	
	public static void extract(float[] result, int offset, float[] m, int mOffset){
		result[offset + 0] = m[mOffset + 0];
		result[offset + 1] = m[mOffset + 1];
		result[offset + 2] = m[mOffset + 2];
		result[offset + 3] = 0.0f;
		result[offset + 4] = m[mOffset + 4];
		result[offset + 5] = m[mOffset + 5];
		result[offset + 6] = m[mOffset + 6];
		result[offset + 7] = 0.0f;
		result[offset + 8] = m[mOffset + 8];
		result[offset + 9] = m[mOffset + 9];
		result[offset + 10] = m[mOffset + 10];
		result[offset + 11] = 0.0f;
		result[offset + 12] = 0.0f;
		result[offset + 13] = 0.0f;
		result[offset + 14] = 0.0f;
		result[offset + 15] = 1.0f;
	}

	public static void row0(float[] result, int offset, float[] m, int mOffset) {
		result[offset + 0] = m[mOffset + 0];
		result[offset + 1] = m[mOffset + 4];
		result[offset + 2] = m[mOffset + 8];
		result[offset + 3] = m[mOffset + 12];
	}

	public static void row1(float[] result, int offset, float[] m, int mOffset) {
		result[offset + 0] = m[mOffset + 1];
		result[offset + 1] = m[mOffset + 5];
		result[offset + 2] = m[mOffset + 9];
		result[offset + 3] = m[mOffset + 13];
	}

	public static void row2(float[] result, int offset, float[] m, int mOffset) {
		result[offset + 0] = m[mOffset + 2];
		result[offset + 1] = m[mOffset + 6];
		result[offset + 2] = m[mOffset + 10];
		result[offset + 3] = m[mOffset + 14];
	}

	public static void row3(float[] result, int offset, float[] m, int mOffset) {
		result[offset + 0] = m[mOffset + 3];
		result[offset + 1] = m[mOffset + 7];
		result[offset + 2] = m[mOffset + 11];
		result[offset + 3] = m[mOffset + 15];
	}

	public static void col0(float[] result, int offset, float[] m, int mOffset) {
		result[offset + 0] = m[mOffset + 0];
		result[offset + 1] = m[mOffset + 1];
		result[offset + 2] = m[mOffset + 2];
		result[offset + 3] = m[mOffset + 3];
	}

	public static void col1(float[] result, int offset, float[] m, int mOffset) {
		result[offset + 0] = m[mOffset + 4];
		result[offset + 1] = m[mOffset + 5];
		result[offset + 2] = m[mOffset + 6];
		result[offset + 3] = m[mOffset + 7];
	}

	public static void col2(float[] result, int offset, float[] m, int mOffset) {
		result[offset + 0] = m[mOffset + 8];
		result[offset + 1] = m[mOffset + 9];
		result[offset + 2] = m[mOffset + 10];
		result[offset + 3] = m[mOffset + 11];
	}

	public static void col3(float[] result, int offset, float[] m, int mOffset) {
		result[offset + 0] = m[mOffset + 12];
		result[offset + 1] = m[mOffset + 13];
		result[offset + 2] = m[mOffset + 14];
		result[offset + 3] = m[mOffset + 15];
	}

	public static void negate(float[] result, int offset, float[] m, int mOffset) {
		for (int i = 0; i < 16; i++) {
			result[offset + i] = -m[mOffset + i];
		}
	}

	public static void add(float[] result, int offset, float[] m1, int m1Offset, float[] m2, int m2Offset) {
		for (int i = 0; i < 16; i++) {
			result[offset + i] = m1[m1Offset + i] + m2[m2Offset + i];
		}
	}

	public static void sub(float[] result, int offset, float[] m1, int m1Offset, float[] m2, int m2Offset) {
		for (int i = 0; i < 16; i++) {
			result[offset + i] = m1[m1Offset + i] - m2[m2Offset + i];
		}
	}

	public static void mul(float[] result, int offset, float[] m1, int m1Offset, float[] m2, int m2Offset) {
		android.opengl.Matrix.multiplyMM(result, offset, m1, m1Offset, m2, m2Offset);
	}

	public static void mul(float[] result, int offset, float[] m, int mOffset, float scalar) {
		for (int i = 0; i < 16; i++) {
			result[offset + i] = m[mOffset + i] * scalar;
		}
	}

	public static void div(float[] result, int offset, float[] m1, int m1Offset, float[] m2, int m2Offset) {
		synchronized (sTemp) {
			android.opengl.Matrix.invertM(sTemp, 0, m2, m2Offset);
			android.opengl.Matrix.multiplyMM(result, offset, m1, m1Offset, sTemp, 0);
		}
	}

	public static void div(float[] result, int offset, float[] m, int mOffset, float scalar) {
		for (int i = 0; i < 16; i++) {
			result[offset + i] = m[mOffset + i] / scalar;
		}
	}

	public static void transpose(float[] result, int offset, float[] m, int mOffset) {
		android.opengl.Matrix.transposeM(result, offset, m, mOffset);
	}

	public static void invert(float[] result, int offset, float[] m, int mOffset) {
		// android.opengl.Matrix.invertM(result, offset, m, mOffset);
		Matrix.invertM(result, offset, m, mOffset);
	}

	public static void transform(float[] result, int offset, float[] m, int mOffset, float[] v, int vOffset) {
		android.opengl.Matrix.multiplyMV(result, offset, m, mOffset, v, vOffset);
	}

	public static void scale(float[] result, int offset, float[] scale, int scaleOffset) {
		result[offset + 0] = scale[scaleOffset + 0];
		result[offset + 1] = 0.0f;
		result[offset + 2] = 0.0f;
		result[offset + 3] = 0.0f;
		result[offset + 4] = 0.0f;
		result[offset + 5] = scale[scaleOffset + 1];
		result[offset + 6] = 0.0f;
		result[offset + 7] = 0.0f;
		result[offset + 8] = 0.0f;
		result[offset + 9] = 0.0f;
		result[offset + 10] = scale[scaleOffset + 2];
		result[offset + 11] = 0.0f;
		result[offset + 12] = 0.0f;
		result[offset + 13] = 0.0f;
		result[offset + 14] = 0.0f;
		result[offset + 15] = 1.0f;
	}
	
	public static void rotationX(float[] result, int offset, float angle){
		android.opengl.Matrix.setRotateM(result, offset, angle * toDegrees, 1.0f, 0.0f, 0.0f);
	}
	
	public static void rotationY(float[] result, int offset, float angle){
		android.opengl.Matrix.setRotateM(result, offset, angle * toDegrees, 0.0f, 1.0f, 0.0f);
	}
	
	public static void rotationZ(float[] result, int offset, float angle){
		android.opengl.Matrix.setRotateM(result, offset, angle * toDegrees, 0.0f, 0.0f, 1.0f);
	}

	public static void rotationEuler(float[] result, int offset, float[] rotation, int rotationOffset) {
		// android.opengl.Matrix.setRotationEulerM(...);
		Matrix.setRotateEulerM(result, offset,
				rotation[rotationOffset + 0] * toDegrees,
				rotation[rotationOffset + 1] * toDegrees,
				rotation[rotationOffset + 2] * toDegrees);
	}
	
	public static void billboard(float[] result, int offset, float[] forward, int forwardOffset, float[] up, int upOffset) {
		// TODO
		throw new UnsupportedOperationException();
	}

	public static void cylindricalBillboard(float[] result, int offset, float[] axis, int axisOffset, float[] forward, int forwardOffset, float[] up, int upOffset) {
		// TODO
		throw new UnsupportedOperationException();
	}

	public static void translation(float[] result, int offset, float[] translation, int translationOffset) {
		result[offset + 0] = 1.0f;
		result[offset + 1] = 0.0f;
		result[offset + 2] = 0.0f;
		result[offset + 3] = 0.0f;
		result[offset + 4] = 0.0f;
		result[offset + 5] = 1.0f;
		result[offset + 6] = 0.0f;
		result[offset + 7] = 0.0f;
		result[offset + 8] = 0.0f;
		result[offset + 9] = 0.0f;
		result[offset + 10] = 1.0f;
		result[offset + 11] = 0.0f;
		result[offset + 12] = translation[translationOffset + 0];
		result[offset + 13] = translation[translationOffset + 1];
		result[offset + 14] = translation[translationOffset + 2];
		result[offset + 15] = 1.0f;
	}

	public static void lookAt(float[] result, int offset, float[] eye, int eyeOffset, float[] center, int centerOffset, float[] up, int upOffset) {
		android.opengl.Matrix.setLookAtM(result, offset, eye[eyeOffset + 0], eye[eyeOffset + 1], eye[eyeOffset + 2], center[centerOffset + 0], center[centerOffset + 1], center[centerOffset + 2], up[upOffset + 0], up[upOffset + 1], up[upOffset + 2]);
	}

	public static void orthographic(float[] result, int offset, float left, float right, float bottom, float top, float near, float far) {
		android.opengl.Matrix.orthoM(result, offset, left, right, bottom, top, near, far);
	}

	public static void perspective(float[] result, int offset, float fovy, float aspect, float near, float far) {
		android.opengl.Matrix.perspectiveM(result, offset, fovy * toDegrees, aspect, near, far);
	}

	public static void frustum(float[] result, int offset, float left, float right, float bottom, float top, float near, float far) {
		android.opengl.Matrix.frustumM(result, offset, left, right, bottom, top, near, far);
	}

	private static boolean invertM(float[] mInv, int mInvOffset, float[] m, int mOffset) {
		synchronized (sTemp) {
			android.opengl.Matrix.transposeM(sTemp, 0, m, mOffset);

			float tmp0, tmp1, tmp2, tmp3, tmp4, tmp5, tmp6, tmp7, tmp8, tmp9, tmp10, tmp11;

			tmp0 = sTemp[10] * sTemp[15];
			tmp1 = sTemp[11] * sTemp[14];
			tmp2 = sTemp[9] * sTemp[15];
			tmp3 = sTemp[11] * sTemp[13];
			tmp4 = sTemp[9] * sTemp[14];
			tmp5 = sTemp[10] * sTemp[13];
			tmp6 = sTemp[8] * sTemp[15];
			tmp7 = sTemp[11] * sTemp[12];
			tmp8 = sTemp[8] * sTemp[14];
			tmp9 = sTemp[10] * sTemp[12];
			tmp10 = sTemp[8] * sTemp[13];
			tmp11 = sTemp[9] * sTemp[12];

			sTemp[16] = tmp0 * sTemp[5] + tmp3 * sTemp[6] + tmp4 * sTemp[7];
			sTemp[16] -= tmp1 * sTemp[5] + tmp2 * sTemp[6] + tmp5 * sTemp[7];
			sTemp[17] = tmp1 * sTemp[4] + tmp6 * sTemp[6] + tmp9 * sTemp[7];
			sTemp[17] -= tmp0 * sTemp[4] + tmp7 * sTemp[6] + tmp8 * sTemp[7];
			sTemp[18] = tmp2 * sTemp[4] + tmp7 * sTemp[5] + tmp10 * sTemp[7];
			sTemp[18] -= tmp3 * sTemp[4] + tmp6 * sTemp[5] + tmp11 * sTemp[7];
			sTemp[19] = tmp5 * sTemp[4] + tmp8 * sTemp[5] + tmp11 * sTemp[6];
			sTemp[19] -= tmp4 * sTemp[4] + tmp9 * sTemp[5] + tmp10 * sTemp[6];
			sTemp[20] = tmp1 * sTemp[1] + tmp2 * sTemp[2] + tmp5 * sTemp[3];
			sTemp[20] -= tmp0 * sTemp[1] + tmp3 * sTemp[2] + tmp4 * sTemp[3];
			sTemp[21] = tmp0 * sTemp[0] + tmp7 * sTemp[2] + tmp8 * sTemp[3];
			sTemp[21] -= tmp1 * sTemp[0] + tmp6 * sTemp[2] + tmp9 * sTemp[3];
			sTemp[22] = tmp3 * sTemp[0] + tmp6 * sTemp[1] + tmp11 * sTemp[3];
			sTemp[22] -= tmp2 * sTemp[0] + tmp7 * sTemp[1] + tmp10 * sTemp[3];
			sTemp[23] = tmp4 * sTemp[0] + tmp9 * sTemp[1] + tmp10 * sTemp[2];
			sTemp[23] -= tmp5 * sTemp[0] + tmp8 * sTemp[1] + tmp11 * sTemp[2];

			tmp0 = sTemp[2] * sTemp[7];
			tmp1 = sTemp[3] * sTemp[6];
			tmp2 = sTemp[1] * sTemp[7];
			tmp3 = sTemp[3] * sTemp[5];
			tmp4 = sTemp[1] * sTemp[6];
			tmp5 = sTemp[2] * sTemp[5];
			tmp6 = sTemp[0] * sTemp[7];
			tmp7 = sTemp[3] * sTemp[4];
			tmp8 = sTemp[0] * sTemp[6];
			tmp9 = sTemp[2] * sTemp[4];
			tmp10 = sTemp[0] * sTemp[5];
			tmp11 = sTemp[1] * sTemp[4];

			sTemp[24] = tmp0 * sTemp[13] + tmp3 * sTemp[14] + tmp4 * sTemp[15];
			sTemp[24] -= tmp1 * sTemp[13] + tmp2 * sTemp[14] + tmp5 * sTemp[15];
			sTemp[25] = tmp1 * sTemp[12] + tmp6 * sTemp[14] + tmp9 * sTemp[15];
			sTemp[25] -= tmp0 * sTemp[12] + tmp7 * sTemp[14] + tmp8 * sTemp[15];
			sTemp[26] = tmp2 * sTemp[12] + tmp7 * sTemp[13] + tmp10 * sTemp[15];
			sTemp[26] -= tmp3 * sTemp[12] + tmp6 * sTemp[13] + tmp11 * sTemp[15];
			sTemp[27] = tmp5 * sTemp[12] + tmp8 * sTemp[13] + tmp11 * sTemp[14];
			sTemp[27] -= tmp4 * sTemp[12] + tmp9 * sTemp[13] + tmp10 * sTemp[14];
			sTemp[28] = tmp2 * sTemp[10] + tmp5 * sTemp[11] + tmp1 * sTemp[9];
			sTemp[28] -= tmp4 * sTemp[11] + tmp0 * sTemp[9] + tmp3 * sTemp[10];
			sTemp[29] = tmp8 * sTemp[11] + tmp0 * sTemp[8] + tmp7 * sTemp[10];
			sTemp[29] -= tmp6 * sTemp[10] + tmp9 * sTemp[11] + tmp1 * sTemp[8];
			sTemp[30] = tmp6 * sTemp[9] + tmp11 * sTemp[11] + tmp3 * sTemp[8];
			sTemp[30] -= tmp10 * sTemp[11] + tmp2 * sTemp[8] + tmp7 * sTemp[9];
			sTemp[31] = tmp10 * sTemp[10] + tmp4 * sTemp[8] + tmp9 * sTemp[9];
			sTemp[31] -= tmp8 * sTemp[9] + tmp11 * sTemp[10] + tmp5 * sTemp[8];

			float det = sTemp[0] * sTemp[16] + sTemp[1] * sTemp[17] + sTemp[2] * sTemp[18] + sTemp[3] * sTemp[19];
			// if (det == 0.0f) { }

			det = 1 / det;
			for (int j = 16; j < 32; j++) {
				mInv[j + mInvOffset] = sTemp[j] * det;
			}
		}

		return true;
	}

	private static void setRotateEulerM(float[] rm, int rmOffset, float x, float y, float z) {
		x *= (float) (Math.PI / 180.0f);
		y *= (float) (Math.PI / 180.0f);
		z *= (float) (Math.PI / 180.0f);
		float sx = (float) Math.sin(x);
		float sy = (float) Math.sin(y);
		float sz = (float) Math.sin(z);
		float cx = (float) Math.cos(x);
		float cy = (float) Math.cos(y);
		float cz = (float) Math.cos(z);
		float cxsy = cx * sy;
		float sxsy = sx * sy;

		rm[rmOffset + 0] = cy * cz;
		rm[rmOffset + 1] = -cy * sz;
		rm[rmOffset + 2] = sy;
		rm[rmOffset + 3] = 0.0f;

		rm[rmOffset + 4] = sxsy * cz + cx * sz;
		rm[rmOffset + 5] = -sxsy * sz + cx * cz;
		rm[rmOffset + 6] = -sx * cy;
		rm[rmOffset + 7] = 0.0f;

		rm[rmOffset + 8] = -cxsy * cz + sx * sz;
		rm[rmOffset + 9] = cxsy * sz + sx * cz;
		rm[rmOffset + 10] = cx * cy;
		rm[rmOffset + 11] = 0.0f;

		rm[rmOffset + 12] = 0.0f;
		rm[rmOffset + 13] = 0.0f;
		rm[rmOffset + 14] = 0.0f;
		rm[rmOffset + 15] = 1.0f;
	}
}
