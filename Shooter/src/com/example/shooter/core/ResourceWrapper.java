package com.example.shooter.core;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public interface ResourceWrapper<T extends Resource> extends Resource {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@interface Factory {
		String value();
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD)
	@interface Loader {
	}

	static class Validation {

		public static boolean factory(Method method) {
			Factory annotation = method.getAnnotation(Factory.class);
			if (annotation != null) {
				if (!Modifier.isStatic(method.getModifiers()))
					throw new IllegalArgumentException("Method is not static.");

				Class<?>[] paramTypes = method.getParameterTypes();
				Class<?> returnType = method.getReturnType();
				if ((paramTypes.length != 1 || !paramTypes[0].isAssignableFrom(ResourceManager.class))
						|| !Resource.class.isAssignableFrom(returnType))
					throw new IllegalArgumentException("Method's signature is invalid.");
				
				return true;
			}
			
			return false;
		}

		public static boolean loader(Method method) {
			Loader annotation = method.getAnnotation(Loader.class);
			if (annotation != null) {
				if (Modifier.isStatic(method.getModifiers()))
					throw new IllegalArgumentException("Method is not instance.");

				Class<?>[] paramTypes = method.getParameterTypes();
				Class<?> returnType = method.getReturnType();
				if ((paramTypes.length != 1 || !Resource.class.isAssignableFrom(paramTypes[0]))
						|| !Void.TYPE.equals(returnType))
					throw new IllegalArgumentException("Method's signature is invalid.");
				
				return true;
			}
			
			return false;
		}

	}
}
