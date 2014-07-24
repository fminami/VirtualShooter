package com.example.shooter;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.example.shooter.util.*;
import com.example.shooter.util.PrimitiveDeque.Policy;

public class ActionDetector {

	public enum Action {
		SHOOT, DROP, LEFT_CLICK, RIGHT_CLICK, LEFT_PUSH, RIGHT_PUSH,
	}

	private enum State {
		READY, FIRE, OVERHEAT, COOLDOWN, RELOAD,
	}

	private static final int MAX_DATA_SIZE = 100;

	private static final int ACTION_SIZE = Action.values().length;

	private static final float SHOOT_SENSITIVITY = 5.0f;
	private static final float SHOOT_THRESHOLD = 0.01f;
	private static final float DROP_SENSITIVITY = 1.0f;
	private static final float DROP_THRESHOLD = 0.1f;
	private static final float CLICK_SENSITIVITY = 1.0f;
	private static final float CLICK_THRESHOLD = 0.1f;
	private static final float PUSH_SENSITIVITY = 1.0f;
	private static final float PUSH_THRESHOLD = 0.1f;

	private static final int V0 = Vector3.SIZE * 0;
	private static final int V1 = Vector3.SIZE * 1;
	private static final int V2 = Vector3.SIZE * 2;
	private static final int V3 = Vector3.SIZE * 3;

	private final List<Action> actions = new ArrayList<Action>();
	private final int[] time = new int[ACTION_SIZE];

	private final FloatVectorDeque orientations = new FloatVectorDeque(Vector3.SIZE, MAX_DATA_SIZE, Policy.OVERWRITE);
	private final FloatVectorDeque gyroscopes = new FloatVectorDeque(Vector3.SIZE, MAX_DATA_SIZE, Policy.OVERWRITE);
	private final FloatVectorDeque radius = new FloatVectorDeque(Vector3.SIZE, MAX_DATA_SIZE, Policy.OVERWRITE);

	private final State[] state = new State[ACTION_SIZE];
	private final int[] duration = new int[ACTION_SIZE];

	private final float[] buffer = new float[Vector3.SIZE * 4];

	public void clear() {
		orientations.clear();
		gyroscopes.clear();
		radius.clear();

		actions.clear();
	}

	public int getActionCount() {
		return actions.size();
	}

	public Action getAction(int index) {
		return actions.get(index);
	}
	
	public void getSnapshotOrientation(float[] value, int offset, int index){
		orientations.get(value, offset, time[actions.get(index).ordinal()]);
	}

	public void update(float[] orientation, int orientationOffset, float[] gyroscope, int gyroscopeOffset) {
		actions.clear();

		orientations.addLast(orientation, orientationOffset);
		gyroscopes.addLast(gyroscope, gyroscopeOffset);

		calcCurvatureRadius();
		radius.addLast(buffer, Vector3.SIZE * 3);

		detect();
	}

	private void calcCurvatureRadius() {
		int size = gyroscopes.size();
		if (size >= 3) {
			gyroscopes.get(buffer, V0, size - 3);
			gyroscopes.get(buffer, V1, size - 2);
			gyroscopes.get(buffer, V2, size - 1);

			for (int i = 0; i < Vector3.SIZE; i++) {
				int offset = i * Vector3.SIZE;
				float v0 = buffer[offset + 0];
				float v1 = buffer[offset + 1];
				float v2 = buffer[offset + 2];
				float dydx = (v2 - v0) / 2.0f;
				float d2ydx2 = (v2 - v1 * 2.0f + v0);
				float r = ((float) Math.pow((1.0f + dydx * dydx), 1.5f)) / Math.abs(d2ydx2);

				buffer[V3 + i] = r;
			}
		} else {
			buffer[V3 + 0] = Float.POSITIVE_INFINITY;
			buffer[V3 + 1] = Float.POSITIVE_INFINITY;
			buffer[V3 + 2] = Float.POSITIVE_INFINITY;
		}
	}

	private void detect() {
		gyroscopes.getLast(buffer, V0);

		detectShoot(-buffer[V0 + 1]);
		detectDrop(buffer[V0 + 1]);
		detectPush(buffer[V0 + 0]);
		detectClick(buffer[V0 + 2]);
	}

	private void detectShoot(float angle) {
		int index = Action.SHOOT.ordinal();

		duration[index]++;
		if (state[index] == State.FIRE) state[index] = State.OVERHEAT;
		if (state[index] == State.RELOAD) state[index] = State.READY;

		if (angle > SHOOT_SENSITIVITY) {
			if (state[index] == State.READY) {
				for(int i = radius.size() - 1; i > 0; i--){
					radius.get(buffer, V1, i - 1);
					radius.get(buffer, V2, i);
					float delta = buffer[V1 + 1] - buffer[V2 + 1];
					if(delta > SHOOT_THRESHOLD){
						state[index] = State.FIRE;
						time[index] = i;
						actions.add(Action.SHOOT);
						break;
					}
				}
			}
		}else{
			if(state[index] != State.READY){
				state[index] = State.RELOAD;
			}
		}
	}

	private void detectDrop(float angle) {

	}

	private void detectPush(float angle) {

	}

	private void detectClick(float angle) {

	}
}
