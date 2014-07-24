package com.example.shooter.core;

import java.util.ArrayDeque;
import java.util.List;

public class Scene {

	private enum Task {
		NONE, ATTACH, DETACH,
	}

	private final Node root;
	
	private final ServiceProvider services;

	private boolean initialized;

	private boolean tracing;

	private ArrayDeque<Task> tasks = new ArrayDeque<Task>();
	private ArrayDeque<Node> targets = new ArrayDeque<Node>();
	
	public Scene(ServiceProvider services){
		this.root = new Node();
		this.services = services;
		
		attach(root);
	}

	public Node getRoot() {
		return root;
	}
	
	public ServiceProvider getServiceProvider(){
		return services;
	}
	
	public final void init(){
		tracing = true;
		
		if (!initialized) {
			root.init();
			initialized = true;
		}
		
		tracing = false;
	}
	
	public final void load(){
		if(!initialized) throw new IllegalStateException("Scene hasn't been initialized yet.");
		
		tracing = true;
		
		root.load();
		
		tracing = false;
	}
	
	public final void unload(){
		tracing = true;
		
		root.unload();
		
		tracing = false;
	}

	public final void update() {
		tracing = true;

		if (!tasks.isEmpty()) processTasks();

		root.update();

		tracing = false;
	}
	
	public final void draw() {
		tracing = true;

		root.draw();

		tracing = false;
	}
	
	protected void onNodeAttached(Node node) {}
	
	protected void onNodeDetached(Node node) {}

	final void attach(Node node) {
		assert node != null && node.getScene() == null;

		if (tracing) {
			tasks.add(Task.ATTACH);
			targets.add(node);
		} else {
			node.attach(this);
			onNodeAttached(node);
			
			if (initialized) node.init();

			List<Node> children = node.getChildren();
			for (int i = 0, size = children.size(); i < size; i++) {
				attach(children.get(i));
			}
		}
	}

	final void detach(Node node) {
		assert node != null && node.getScene() == this;

		if (tracing) {
			tasks.add(Task.DETACH);
			targets.add(node);
		} else {
			node.detach();
			onNodeDetached(node);

			List<Node> children = node.getChildren();
			for (int i = 0, size = children.size(); i < size; i++) {
				detach(children.get(i));
			}
		}
	}

	private void processTasks() {
		for (int i = 0, size = tasks.size(); i < size; i++) {
			Task task = tasks.poll();
			Node target = targets.poll();
			switch (task) {
			case ATTACH:
				attach(target);
				break;
			case DETACH:
				detach(target);
				break;
			default:
				throw new IllegalStateException("Unknown task.");
			}
		}
	}
}
