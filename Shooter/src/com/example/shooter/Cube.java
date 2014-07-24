package com.example.shooter;

import com.example.shooter.core.*;
import com.example.shooter.graphics.*;
import com.example.shooter.util.*;

import android.opengl.GLES20;
import android.os.SystemClock;

public class Cube extends Model {
	
	private static final String VERTEX_BUFFER_NAME = "CubeVertexBuffer";
	private static final String INDEX_BUFFER_NAME = "CubeIndexBuffer";

	private static final int VERTICES_PER_FACE = 4;
	private static final int INDICES_PER_FACE = 6;
	private static final int FACE_COUNT = 6;
	private static final int VERTEX_COUNT = VERTICES_PER_FACE * FACE_COUNT;
	private static final int INDEX_COUNT = INDICES_PER_FACE * FACE_COUNT;
	
	private static final int POSITION_SIZE = 3;
	private static final int NORMAL_SIZE = 3;
	private static final int UV_SIZE = 2;
	private static final int COLOR_SIZE = 4;

	private static final float R = 0.5f;
	private static final float[] POSITIONS = {
			-R, -R, -R,
			+R, -R, -R,
			-R, +R, -R,
			+R, +R, -R,
			-R, -R, +R,
			+R, -R, +R,
			-R, +R, +R,
			+R, +R, +R,
	};
	private static final float[] NORMALS = {
			-1, +0, +0,
			+1, +0, +0,
			+0, -1, +0,
			+0, +1, +0,
			+0, +0, -1,
			+0, +0, +1,
	};
	private static final float[] UVS = {
			0, 0,
			1, 0,
			0, 1,
			1, 1,
	};
	private static final int[] POINTS = {
			0, 2, 4, 6,
			7, 3, 5, 1,
			0, 4, 1, 5,
			7, 6, 3, 2,
			0, 1, 2, 3,
			7, 5, 6, 4,
	};
	private static final short[] INDICES = {
			0, 2, 1, 1, 2, 3,
	};

	private VertexBuffer vbuffer;
	private IndexBuffer ibuffer;
	private SimpleEffect effect;

	private FloatArray fa = new FloatArray();
	private int hMvp = fa.getMatrixHandle();
	private int hMv = fa.getMatrixHandle();
	private int hModel = fa.getMatrixHandle();
	private int hView = fa.getMatrixHandle();
	private int hProj = fa.getMatrixHandle();
	{
		fa.freeze();
	}
	
	int color = 0;
	private static final float[] diffuse = {
		1.0f, 1.0f, 1.0f, 
		1.0f, 0.0f, 0.0f, 
		0.0f, 1.0f, 0.0f, 
		0.0f, 0.0f, 1.0f, 
	};

	@Override
	protected void onInit() {
		ServiceProvider services = getScene().getServiceProvider();
		ResourceManager resources = services.getService(ResourceManager.class);
		
		if(!resources.isRegistered(VERTEX_BUFFER_NAME)){
			resources.register(VERTEX_BUFFER_NAME, new ResourceFactory<VertexBuffer>(){
				@Override
				public VertexBuffer createResource(ResourceManager manager) {
					float[] data = getVertexData(null);
					VertexBuffer.Format[] formats = {
							VertexBuffer.Format.FLOAT3,
							VertexBuffer.Format.FLOAT3,
							VertexBuffer.Format.FLOAT2,
					};
					FloatVertexBuffer vb = new FloatVertexBuffer(VERTEX_COUNT, formats);
					vb.setVertices(data);
					return vb;
				}
			});	
		}
		if(!resources.isRegistered(INDEX_BUFFER_NAME)){
			resources.register(INDEX_BUFFER_NAME, new ResourceFactory<IndexBuffer>() {
				@Override
				public IndexBuffer createResource(ResourceManager manager) {
					short[] data = getIndexData();
					IndexBuffer ib = new IndexBuffer(INDEX_COUNT, IndexBuffer.Format.SHORT);
					ib.setIndices(data);
					return ib;
				}
			});
		}
		if(!resources.isRegistered(SimpleEffect.class)){
			resources.register(SimpleEffect.class);
		}
		
		fa.set(fa.t(0), 1.0f, 1.0f, 1.0f);
		fa.set(fa.t(1), 1.0f, 1.0f, 1.0f);
		fa.set(fa.t(2), 1.0f, 1.0f, 1.0f);
		float[] buffer = fa.getBuffer();
		
		effect = new SimpleEffect();
		effect.setAlpha(1.0f);
		effect.setDiffuse(buffer, fa.t(0));
		effect.setSpecular(buffer, fa.t(1));
		effect.setAmbient(buffer, fa.t(2));
		effect.setShininess(10.0f);
	}
	
	@Override
	protected void onLoad() {
		ServiceProvider services = getScene().getServiceProvider();
		ResourceManager resources = services.getService(ResourceManager.class);
		resources.load(effect);
		
		vbuffer = (VertexBuffer)resources.load(VERTEX_BUFFER_NAME);
		ibuffer = (IndexBuffer)resources.load(INDEX_BUFFER_NAME);
		resources.load(vbuffer);
		resources.load(ibuffer);
		vbuffer.setAttributeBinding(0, effect.getPositionLocation());
		vbuffer.setAttributeBinding(1, effect.getNormalLocation());
		
		super.onLoad();
	}

	@Override
	protected void onUpdate() {
		long time = SystemClock.uptimeMillis() % 10000L;
		float angle = (float) ((Math.PI * 2.0 / 10000.0) * time);
		fa.set(fa.t(0), 1.0f, 1.0f, 1.0f);
		Vector3Ex.normalize(fa, fa.t(0), fa.t(0));
		QuaternionEx.fromAxisAngle(fa, fa.t(1), fa.t(0), angle);
		fa.set(fa.t(0), 1.0f, -1.0f, 0.0f);
		Vector3Ex.normalize(fa, fa.t(0), fa.t(0));
		QuaternionEx.fromAxisAngle(fa, fa.t(2), fa.t(0), angle * -2.0f);
		QuaternionEx.mul(fa, fa.t(3), fa.t(1), fa.t(2));
		setRotation(fa.getBuffer(), fa.t(3));
			
		ServiceProvider services = getScene().getServiceProvider();
		ShooterInput input = (ShooterInput)services.getService(InputManager.class);
		float[] buffer = fa.getBuffer();
		int offset = fa.t(0);
		if(input.detectedShootAction(fa.getBuffer(), offset)){
			float azimuth = buffer[offset + 0];
			float pitch = buffer[offset + 1];
			float roll = buffer[offset + 2];
			fa.set(fa.t(0), 0.0f, 1.0f, 0.0f);
			QuaternionEx.fromAxisAngle(fa, fa.t(1), fa.t(0), (float)Math.PI);
			QuaternionEx.fromYawPitchRoll(fa, fa.t(2), -azimuth, pitch, roll);
			QuaternionEx.mul(fa, fa.t(3), fa.t(2), fa.t(1));
			QuaternionEx.mul(fa, fa.t(4), fa.t(1), fa.t(3));
			fa.set(fa.t(0), 0.0f, 0.0f, -1.0f);
			QuaternionEx.transform(fa, fa.t(1), fa.t(0), fa.t(4));
			fa.set(fa.t(2), 0.0f, 0.0f, 0.0f);
			getModelMatrix(buffer, fa.t(4));
			MatrixEx.transform(fa, fa.t(0), fa.t(4), fa.t(2));
			getScale(buffer, fa.t(2));
			float dot_cv = Vector3Ex.dot(fa, fa.t(0), fa.t(1));
			float sqc = Vector3Ex.lengthSquared(fa, fa.t(0));
			float sqv = Vector3Ex.lengthSquared(fa, fa.t(1));
			float r = Vector3Ex.max(fa, fa.t(2)) * (float)(Math.sqrt(3.0) * 0.5) * 2.0f;
			float d = dot_cv * dot_cv - sqv * (sqc - r * r);
			if(d > 0){
				color = (color + 3) % 12;
				effect.setDiffuse(diffuse, color);
			}
		}
	}

	@Override
	protected void onDraw() {
		GameScene scene = (GameScene)getScene();
		DirectionalLight light = (DirectionalLight)scene.getLight(0);
		PerspectiveCamera camera = (PerspectiveCamera)scene.getCamera(0);
		
		float[] buffer = fa.getBuffer();
		getModelMatrix(buffer, hModel);
		camera.getView(buffer, hView);
		camera.getProjection(buffer, hProj);
		MatrixEx.mul(fa, hMv, hView, hModel);
		MatrixEx.mul(fa, hMvp, hProj, hMv);
		
		light.getDirection(buffer, fa.t(0));
		light.getDiffuse(buffer, fa.t(1));
		light.getSpecular(buffer, fa.t(2));
		light.getAmbient(buffer, fa.t(3));

		effect.setModelViewProjection(buffer, hMvp);
		effect.setModelView(buffer, hMv);
		
		effect.setLightDirection(buffer, fa.t(0));
		effect.setLightDiffuse(buffer, fa.t(1));
		effect.setLightSpecular(buffer, fa.t(2));
		effect.setLightAmbient(buffer, fa.t(3));
		
		effect.apply();
		Graphics.drawElements(vbuffer, ibuffer, GLES20.GL_TRIANGLES);
	}

	private static float[] getVertexData(float[] colors) {
		if (colors != null && colors.length != 8 * 4) throw new IllegalArgumentException("'color' length must be 8 x 4.");

		int vSize = POSITION_SIZE + NORMAL_SIZE + UV_SIZE;
		if (colors != null) vSize += COLOR_SIZE;

		float[] data = new float[vSize * VERTEX_COUNT];
		for (int i = 0; i < VERTEX_COUNT; i++) {
			int offset = vSize * i;
			int point = POINTS[i];
			int face = i / VERTICES_PER_FACE;
			int corner = i % VERTICES_PER_FACE;
			data[offset + 0] = POSITIONS[point * POSITION_SIZE + 0];
			data[offset + 1] = POSITIONS[point * POSITION_SIZE + 1];
			data[offset + 2] = POSITIONS[point * POSITION_SIZE + 2];
			data[offset + 3] = NORMALS[face * NORMAL_SIZE + 0];
			data[offset + 4] = NORMALS[face * NORMAL_SIZE + 1];
			data[offset + 5] = NORMALS[face * NORMAL_SIZE + 2];
			data[offset + 6] = UVS[corner * UV_SIZE + 0];
			data[offset + 7] = UVS[corner * UV_SIZE + 1];
			if(colors != null){
				data[offset + 8] = colors[point * POSITION_SIZE + 0];
				data[offset + 9] = colors[point * POSITION_SIZE + 1];
				data[offset + 10] = colors[point * POSITION_SIZE + 2];
				data[offset + 11] = colors[point * POSITION_SIZE + 3];
			}
		}

		return data;
	}
	
	public static short[] getIndexData(){
		short[] idata = new short[INDEX_COUNT];
		for(int i = 0; i < INDEX_COUNT; i++){
			int face = i / INDICES_PER_FACE;
			int corner = i % INDICES_PER_FACE;
			idata[i] = (short)(INDICES[corner] + face * VERTICES_PER_FACE);
		}
		return idata;
	}
}
