package com.example.shooter.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("unchecked")
public abstract class NodeBase<T extends NodeBase<T>> {

	private T parent;
	
	private List<T> children;
	
	public T getParent(){
		return parent;
	}
	
	public List<T> getChildren(){
		return children != null ? children : Collections.<T>emptyList();
	}
	
	public void addChild(T child){
		if(child == null) throw new IllegalArgumentException("'child' is null.");
		if(child.parent != null) throw new IllegalArgumentException("'child' already has a parent.");
		
		if(children == null) children = Collections.synchronizedList(new ArrayList<T>());
		
		if(children.add(child)){
			child.parent = (T)this;
		}
	}
	
	public void removeChild(T child){
		if(child == null) throw new IllegalArgumentException("'child' is null.");
		if(child.parent != this) throw new IllegalArgumentException("'child' is not a child.");
		if(children == null) throw new IllegalStateException("There is no children.");
		
		if(children.remove(child)){
			child.parent = null;
		}
	}
	
}
