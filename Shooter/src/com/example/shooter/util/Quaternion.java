package com.example.shooter.util;

public final class Quaternion {
	
	public static final int SIZE = 4;
	public static final int BYTE_SIZE = SIZE * 4;

	private static final float[] sTemp = new float[4];

	public static void identity(float[] result, int offset) {
		result[offset + 0] = 0.0f;
		result[offset + 1] = 0.0f;
		result[offset + 2] = 0.0f;
		result[offset + 3] = 1.0f;
	}

	public static void fromAxisAngle(float[] result, int offset, float[] axis, int axisOffset, float angle) {
		double theta = angle * 0.5;
		double cos = Math.cos(theta);
		double sin = Math.sin(theta);
		result[offset + 0] = (float) (axis[axisOffset + 0] * sin);
		result[offset + 1] = (float) (axis[axisOffset + 1] * sin);
		result[offset + 2] = (float) (axis[axisOffset + 2] * sin);
		result[offset + 3] = (float) cos;
	}

	public static void fromYawPitchRoll(float[] result, int offset, float yaw, float pitch, float roll) {
		double theta = yaw * 0.5, phi = pitch * 0.5, psi = roll * 0.5;
		double cosTheta = Math.cos(theta), sinTheta = Math.sin(theta);
		double cosPhi = Math.cos(phi), sinPhi = Math.sin(phi);
		double cosPsi = Math.cos(psi), sinPsi = Math.sin(psi);
		result[offset + 0] = (float) (cosTheta * sinPhi * cosPsi + sinTheta * cosPhi * sinPsi);
		result[offset + 1] = (float) (sinTheta * cosPhi * cosPsi - cosTheta * sinPhi * sinPsi);
		result[offset + 2] = (float) (cosTheta * cosPhi * sinPsi - sinTheta * sinPhi * cosPsi);
		result[offset + 3] = (float) (cosTheta * cosPhi * cosPsi + sinTheta * sinPhi * sinPsi);
	}

	public static void fromRotationMatrix(float[] result, int offset, float[] m, int mOffset) {
		float m00 = m[mOffset + 0];
		float m11 = m[mOffset + 5];
		float m22 = m[mOffset + 10];
		float x = m00 - m11 - m22 + 1.0f;
		float y = -m00 + m11 - m22 + 1.0f;
		float z = -m00 - m11 + m22 + 1.0f;
		float w = m00 + m11 + m22 + 1.0f;

		int maxIndex = 0;
		float maxValue = x;
		if (y > maxValue) {
			maxIndex = 1;
			maxValue = y;
		}
		if(z > maxValue){
			maxIndex = 2;
			maxValue = z;
		}
		if(w > maxIndex){
			maxIndex = 3;
			maxValue = w;
		}
		
		if(maxValue < 0.0f) throw new IllegalArgumentException("'m' must be a rotation matrix.");
		
		float s = (float)(Math.sqrt(maxValue) * 0.5);
		float t = 0.25f / s;
		switch(maxIndex){
		case 0:
			result[offset + 0] = s;
			result[offset + 1] = (m[mOffset + 1] + m[mOffset + 4]) * t;
			result[offset + 2] = (m[mOffset + 8] + m[mOffset + 2]) * t;
			result[offset + 3] = (m[mOffset + 6] - m[mOffset + 9]) * t;
			break;
		case 1:
			result[offset + 0] = (m[mOffset + 1] + m[mOffset + 4]) * t;
			result[offset + 1] = s;
			result[offset + 2] = (m[mOffset + 6] + m[mOffset + 9]) * t;
			result[offset + 3] = (m[mOffset + 8] - m[mOffset + 2]) * t;
			break;
		case 2:
			result[offset + 0] = (m[mOffset + 8] + m[mOffset + 2]) * t;
			result[offset + 1] = (m[mOffset + 6] + m[mOffset + 9]) * t;
			result[offset + 2] = s;
			result[offset + 3] = (m[mOffset + 1] - m[mOffset + 4]) * t;
			break;
		case 3:
			result[offset + 0] = (m[mOffset + 6] - m[mOffset + 9]) * t;
			result[offset + 1] = (m[mOffset + 8] - m[mOffset + 2]) * t;
			result[offset + 2] = (m[mOffset + 1] - m[mOffset + 4]) * t;
			result[offset + 3] = s;
			break;
		}
	}

	public static void negate(float[] result, int offset, float[] q, int qOffset) {
		result[offset + 0] = -q[qOffset + 0];
		result[offset + 1] = -q[qOffset + 1];
		result[offset + 2] = -q[qOffset + 2];
		result[offset + 3] = -q[qOffset + 3];
	}

	public static void add(float[] result, int offset, float[] q1, int q1Offset, float[] q2, int q2Offset) {
		result[offset + 0] = q1[q1Offset + 0] + q2[q2Offset + 0];
		result[offset + 1] = q1[q1Offset + 1] + q2[q2Offset + 1];
		result[offset + 2] = q1[q1Offset + 2] + q2[q2Offset + 2];
		result[offset + 3] = q1[q1Offset + 3] + q2[q2Offset + 3];
	}

	public static void sub(float[] result, int offset, float[] q1, int q1Offset, float[] q2, int q2Offset) {
		result[offset + 0] = q1[q1Offset + 0] - q2[q2Offset + 0];
		result[offset + 1] = q1[q1Offset + 1] - q2[q2Offset + 1];
		result[offset + 2] = q1[q1Offset + 2] - q2[q2Offset + 2];
		result[offset + 3] = q1[q1Offset + 3] - q2[q2Offset + 3];
	}

	public static void mul(float[] result, int offset, float[] q1, int q1Offset, float[] q2, int q2Offset) {
		float q1x = q1[q1Offset + 0], q1y = q1[q1Offset + 1], q1z = q1[q1Offset + 2], q1w = q1[q1Offset + 3];
		float q2x = q2[q2Offset + 0], q2y = q2[q2Offset + 1], q2z = q2[q2Offset + 2], q2w = q2[q2Offset + 3];
		result[offset + 0] = q1w * q2x + q2w * q1x + (q1y * q2z - q1z * q2y);
		result[offset + 1] = q1w * q2y + q2w * q1y + (q1z * q2x - q1x * q2z);
		result[offset + 2] = q1w * q2z + q2w * q1z + (q1x * q2y - q1y * q2x);
		result[offset + 3] = q1w * q2w - (q1x * q2x + q1y * q2y + q1z * q2z);
	}

	public static void mul(float[] result, int offset, float[] q, int qOffset, float scalar) {
		result[offset + 0] = q[qOffset + 0] * scalar;
		result[offset + 1] = q[qOffset + 1] * scalar;
		result[offset + 2] = q[qOffset + 2] * scalar;
		result[offset + 3] = q[qOffset + 3] * scalar;
	}

	public static void div(float[] result, int offset, float[] q1, int q1Offset, float[] q2, int q2Offset) {
		float q1x = q1[q1Offset + 0], q1y = q1[q1Offset + 1], q1z = q1[q1Offset + 2], q1w = q1[q1Offset + 3];
		float q2x = -q2[q2Offset + 0], q2y = -q2[q2Offset + 1], q2z = -q2[q2Offset + 2], q2w = q2[q2Offset + 3];
		float lengthSquared = q2x * q2x + q2y * q2y + q2z * q2z + q2w * q2w;
		result[offset + 0] = (q1w * q2x + q2w * q1x + (q1y * q2z - q1z * q2y)) / lengthSquared;
		result[offset + 1] = (q1w * q2y + q2w * q1y + (q1z * q2x - q1x * q2z)) / lengthSquared;
		result[offset + 2] = (q1w * q2z + q2w * q1z + (q1x * q2y - q1y * q2x)) / lengthSquared;
		result[offset + 3] = (q1w * q2w - (q1x * q2x + q1y * q2y + q1z * q2z)) / lengthSquared;
	}

	public static void div(float[] result, int offset, float[] q, int qOffset, float scalar) {
		result[offset + 0] = q[qOffset + 0] / scalar;
		result[offset + 1] = q[qOffset + 1] / scalar;
		result[offset + 2] = q[qOffset + 2] / scalar;
		result[offset + 3] = q[qOffset + 3] / scalar;
	}

	public static void conjugate(float[] result, int offset, float[] q, int qOffset) {
		result[offset + 0] = -q[qOffset + 0];
		result[offset + 1] = -q[qOffset + 1];
		result[offset + 2] = -q[qOffset + 2];
		result[offset + 3] = q[qOffset + 3];
	}

	public static void inverse(float[] result, int offset, float[] q, int qOffset) {
		float lengthSquared = q[qOffset + 0] * q[qOffset + 0] + q[qOffset + 1] * q[qOffset + 1] + q[qOffset + 2] * q[qOffset + 2] + q[qOffset + 3] * q[qOffset + 3];
		result[offset + 0] = -q[qOffset + 0] / lengthSquared;
		result[offset + 1] = -q[qOffset + 1] / lengthSquared;
		result[offset + 2] = -q[qOffset + 2] / lengthSquared;
		result[offset + 3] = q[qOffset + 3] / lengthSquared;
	}

	public static float dot(float[] q1, int q1Offset, float[] q2, int q2Offset) {
		return (q1[q1Offset + 0] * q2[q2Offset + 0] + q1[q1Offset + 1] * q2[q2Offset + 1] + q1[q1Offset + 2] * q2[q2Offset + 2] + q1[q1Offset + 3] * q2[q2Offset + 3]);
	}

	public static float length(float[] q, int offset) {
		return (float) Math.sqrt(q[offset + 0] * q[offset + 0] + q[offset + 1] * q[offset + 1] + q[offset + 2] * q[offset + 2] + q[offset + 3] * q[offset + 3]);
	}

	public static float lengthSquared(float[] q, int offset) {
		return (q[offset + 0] * q[offset + 0] + q[offset + 1] * q[offset + 1] + q[offset + 2] * q[offset + 2] + q[offset + 3] * q[offset + 3]);
	}

	public static void normalize(float[] result, int offset, float[] q, int qOffset) {
		float length = (float) Math.sqrt(q[qOffset + 0] * q[qOffset + 0] + q[qOffset + 1] * q[qOffset + 1] + q[qOffset + 2] * q[qOffset + 2] + q[qOffset + 3] * q[qOffset + 3]);
		result[offset + 0] = q[qOffset + 0] / length;
		result[offset + 1] = q[qOffset + 1] / length;
		result[offset + 2] = q[qOffset + 2] / length;
		result[offset + 3] = q[qOffset + 3] / length;
	}

	public static void lerp(float[] result, int offset, float[] q1, int q1Offset, float[] q2, int q2Offset, float t) {
		result[offset + 0] = q1[q1Offset + 0] * (1.0f - t) + q2[q2Offset + 0] * t;
		result[offset + 1] = q1[q1Offset + 1] * (1.0f - t) + q2[q2Offset + 1] * t;
		result[offset + 2] = q1[q1Offset + 2] * (1.0f - t) + q2[q2Offset + 2] * t;
		result[offset + 3] = q1[q1Offset + 3] * (1.0f - t) + q2[q2Offset + 3] * t;
	}

	public static void slerp(float[] result, int offset, float[] q1, int q1Offset, float[] q2, int q2Offset, float t) {
		double dot = (q1[q1Offset + 0] * q2[q2Offset + 0] + q1[q1Offset + 1] * q2[q2Offset + 1] + q1[q1Offset + 2] * q2[q2Offset + 2] + q1[q1Offset + 3] * q2[q2Offset + 3]);
		double rsin = 1.0 / Math.sqrt(1.0 - dot * dot);
		
		if(Double.isInfinite(rsin) || Double.isNaN(rsin)){
			result[offset + 0] = q1[q1Offset + 0]; 
			result[offset + 1] = q1[q1Offset + 1]; 
			result[offset + 2] = q1[q1Offset + 2]; 
			result[offset + 3] = q1[q1Offset + 3]; 
		}else{
			double omega = Math.acos(dot);
			float t0 = (float)(Math.sin(omega * (1.0 - t)) * rsin);
			float t1 = (float)(Math.sin(omega * t) * rsin);
			
			result[offset + 0] = q1[q1Offset + 0] * t0 + q2[q2Offset + 0] * t1;
			result[offset + 1] = q1[q1Offset + 1] * t0 + q2[q2Offset + 1] * t1;
			result[offset + 2] = q1[q1Offset + 2] * t0 + q2[q2Offset + 2] * t1;
			result[offset + 3] = q1[q1Offset + 3] * t0 + q2[q2Offset + 3] * t1;
		}
	}

	public static void transform(float[] result, int offset, float[] v, int vOffset, float[] q, int qOffset) {
		conjugate(result, offset, q, qOffset);
		synchronized (sTemp) {
			sTemp[0] = v[vOffset + 0];
			sTemp[1] = v[vOffset + 1];
			sTemp[2] = v[vOffset + 2];
			sTemp[3] = 0.0f;
			mul(result, offset, sTemp, 0, result, offset);
		}
		mul(result, offset, q, qOffset, result, offset);
	}
}
