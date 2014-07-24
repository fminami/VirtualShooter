package com.example.shooter;

import android.opengl.GLES20;

import com.example.shooter.Container.Controller;
import com.example.shooter.core.*;
import com.example.shooter.graphics.Graphics;
import com.example.shooter.util.*;

public class ShooterGame extends Game {

	private final FloatArray array = new FloatArray().freeze();
	
	public ShooterGame(InputManager input, ResourceManager resources){
		super(input , resources);
		
		setScene(new GameScene(this));
	}
	
	@Override
	protected void onScreenChanged(int width, int height) {
		float aspect = (float)width / (float)height;
		
		GameScene scene = (GameScene)getScene();
		for(int i = 0, count = scene.getCameraCount(); i < count; i++){
			Camera camera = scene.getCamera(i);
			if(camera instanceof PerspectiveCamera){
				PerspectiveCamera pcamera = (PerspectiveCamera)camera;
				pcamera.setAspectRatio(aspect);
			}
		}

		super.onScreenChanged(width, height);
	}

	@Override
	protected void onInit() {
		Node root = getScene().getRoot();
		
		generateCubes(root);
		
		root.addChild(new ShooterLight());
		root.addChild(new ShooterCamera());
	}

	@Override
	protected void onLoad() {
		Graphics.setClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		
		Graphics.enableCullFace(true);
		Graphics.setCullFaceMode(GLES20.GL_BACK);
		Graphics.setFrontFace(GLES20.GL_CCW);
		
		Graphics.enableDepthTest(true);
	}

	@Override
	protected void onUnload() {
		
	}

	@Override
	protected void onUpdate() {
		
	}

	@Override
	protected void onDraw() {
		Graphics.clear();
	}
	
	private void generateCubes(Node root){
		Container c1 = new Container(10000L, new Controller(){
			@Override
			public void control(Node node, FloatArray array, float t, float s, float angle) {
				float[] buffer = array.getBuffer();
				array.set(array.t(0), 0.0f, 0.0f, -20.0f);
				node.setPosition(buffer, array.t(0));
				QuaternionEx.fromYawPitchRoll(array, array.t(0), 0.0f, 0.0f, angle);
				node.setRotation(buffer, array.t(0));
				float scale = 1.0f + s * 5.0f;
				array.set(array.t(0), scale, scale, 1.0f);
				node.setScale(buffer, array.t(0));;
			}
		});
		
		for(int i = 0; i < 4; i++){
			Container c2 = new Container(3000L, new Controller(){
				@Override
				public void control(Node node, FloatArray array, float t, float s, float angle) {
					float[] buffer = array.getBuffer();
					QuaternionEx.fromYawPitchRoll(array, array.t(0), 0.0f, (float)((s - 0.5f) * Math.PI), angle);
					node.setRotation(buffer, array.t(0));
				}
			});
			float x = (i % 2) * 10.0f - 5.0f;
			float y = (i / 2) * 10.0f - 5.0f;
			array.set(array.t(0), x, y, 0.0f);
			c2.setPosition(array.getBuffer(), array.t(0));
			
			c2.addChild(newCube(0.0f, 0.0f, 0.0f, 2.0f));
			c2.addChild(newCube(-2.5f, 0.0f, -2.5f, 1.0f));
			c2.addChild(newCube(2.5f, 0.0f, -2.5f, 1.0f));
			c2.addChild(newCube(-2.5f, 0.0f, 2.5f, 1.0f));
			c2.addChild(newCube(2.5f, 0.0f, 2.5f, 1.0f));
			
			c1.addChild(c2);
		}
		
		c1.addChild(newCube(0.0f, 0.0f, 0.0f, 3.0f));
		
		root.addChild(c1);
	}
	
	private Cube newCube(float x, float y, float z, float scale){
		Cube cube = new Cube();
		array.set(array.t(0), x, y, z);
		cube.setPosition(array.getBuffer(), array.t(0));
		array.set(array.t(1), scale, scale, scale);
		cube.setScale(array.getBuffer(), array.t(1));
		return cube;
	}

}
