package com.example.shooter.core;

import java.lang.reflect.*;
import java.util.*;

import android.content.res.AssetManager;

public class ResourceManager {

	private final AssetManager assets;

	private final Map<Class<?>, ResourceWrapperInfo> wrappers = new HashMap<Class<?>, ResourceWrapperInfo>();

	private final Map<String, ResourceData> resources = new HashMap<String, ResourceData>();
	
	private static final Object[] args = new Object[1];

	public ResourceManager(AssetManager assets) {
		this.assets = assets;
	}

	public AssetManager getAssets() {
		return assets;
	}

	public <T extends Resource> void register(String name, ResourceFactory<T> factory) {
		if (name == null) throw new IllegalArgumentException("'name' is null.");
		if (factory == null) throw new IllegalArgumentException("'factory' is null.");

		synchronized (resources) {
			if (resources.containsKey(name)) throw new IllegalArgumentException("'" + name + "' already exists.");

			resources.put(name, new ResourceData(factory));
		}
	}

	public <T extends Resource> void register(Class<? extends ResourceWrapper<T>> clazz) {
		if (clazz == null) throw new IllegalArgumentException("'clazz' is null.");

		ResourceWrapperInfo info = getResourceWrapperInfo(clazz, true);

		final Method method = info.factory;
		ResourceFactory<T> factory = new ResourceFactory<T>() {
			@Override
			public T createResource(ResourceManager manager) {
				return ResourceManager.<T>invokeMethod(method, null, manager);
			}
		};

		register(info.getFactoryAnnotation().value(), factory);
	}

	public void unregister(String name) {
		if (name == null) throw new IllegalArgumentException("'name' is null.");

		synchronized (resources) {
			resources.remove(name);
		}
	}

	public void unregister(Class<? extends ResourceWrapper<?>> clazz) {
		if (clazz == null) throw new IllegalArgumentException("'clazz' is null.");

		ResourceWrapperInfo info = getResourceWrapperInfo(clazz, false);
		if(info == null) throw new IllegalArgumentException("Not registered.");

		unregister(info.getFactoryAnnotation().value());
	}

	public void unregisterAll() {
		synchronized (resources) {
			resources.clear();
		}
	}
	
	public boolean isRegistered(String name){
		if (name == null) throw new IllegalArgumentException("'name' is null.");

		boolean registered;
		synchronized(resources){
			registered = resources.containsKey(name);
		}
		
		return registered;
	}
	
	public boolean isRegistered(Class<? extends ResourceWrapper<?>> clazz){
		if (clazz == null) throw new IllegalArgumentException("'clazz' is null.");

		ResourceWrapperInfo info = getResourceWrapperInfo(clazz, false);
		
		return (info != null);
	}

	@SuppressWarnings("unchecked")
	public <T extends Resource> T load(String name) {
		ResourceData info = getResourceInfo(name);
		Object resource = info.cache;
		if (resource == null) {
			resource = info.factory.createResource(this);

			if (resource == null) throw new IllegalStateException("Factory returned null.");
		}

		return (T) resource;
	}
	
	public <T extends Resource> void load(ResourceHolder<T> holder){
		if(holder == null) throw new IllegalArgumentException("'holder' is null.");
		
		holder.loadResource(this);
	}

	public <T extends Resource> void load(ResourceWrapper<T> wrapper) {
		if (wrapper == null) throw new IllegalArgumentException("'wrapper' is null.");

		ResourceWrapperInfo info = getResourceWrapperInfo(wrapper.getClass(), false);
		if(info == null) throw new IllegalArgumentException("Not registered.");
		
		T resource = load(info.getFactoryAnnotation().value());
		ResourceManager.<Void>invokeMethod(info.loader, wrapper, resource);
	}

	public void unload(String name) {
		ResourceData info = getResourceInfo(name);
		info.cache = null;
	}

	public void unloadAll() {
		synchronized (resources) {
			for (ResourceData info : resources.values()) {
				info.cache = null;
			}
		}
	}

	private ResourceWrapperInfo getResourceWrapperInfo(Class<?> clazz, boolean force) {
		ResourceWrapperInfo info;
		synchronized (wrappers) {
			info = wrappers.get(clazz);
			if (info == null && force) {
				info = new ResourceWrapperInfo();

				Method[] methods = clazz.getDeclaredMethods();
				for (int i = 0; i < methods.length; i++) {
					final Method method = methods[i];
					if (ResourceWrapper.Validation.factory(method)) {
						info.factory = method;
					} else if (ResourceWrapper.Validation.loader(method)) {
						info.loader = method;
					}
				}

				if (info.factory == null) throw new IllegalArgumentException("Factory method is not found.");
				if (info.loader == null) throw new IllegalArgumentException("Loader method is not found.");

				wrappers.put(clazz, info);
			}
		}

		return info;
	}
	
	private ResourceData getResourceInfo(String name) {
		if (name == null) throw new IllegalArgumentException("'name' is null.");

		ResourceData resource;
		synchronized (resources) {
			resource = resources.get(name);
		}
		if (resource == null) throw new IllegalArgumentException("'" + name + "' is not found.");

		return resource;
	}

	@SuppressWarnings("unchecked")
	private static <T> T invokeMethod(Method method, Object receiver, Object arg){
		T result;
		method.setAccessible(true);
		try {
			synchronized(args){
				args[0] = arg;
				result = (T) method.invoke(receiver, args);
			}
		} catch (Exception e) {
			throw new IllegalStateException("Failed to invoke the method.", e);
		}
		method.setAccessible(false);
		
		return result;
	}

	private class ResourceWrapperInfo {
		public Method factory;
		public Method loader;
		public ResourceWrapper.Factory getFactoryAnnotation(){
			return factory.getAnnotation(ResourceWrapper.Factory.class);
		}
//		public ResourceWrapper.Loader getLoaderAnnotation(){
//			return factory.getAnnotation(ResourceWrapper.Loader.class);
//		}
	}

	private class ResourceData {
		public ResourceFactory<?> factory;
		public Object cache;

		public ResourceData(ResourceFactory<?> factory) {
			this.factory = factory;
		}
	}
}
