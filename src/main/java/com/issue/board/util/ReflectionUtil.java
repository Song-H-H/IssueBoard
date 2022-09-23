package com.issue.board.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReflectionUtil {

    private ReflectionUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static List<Field> getAllFields(Class<?> clazz) {
		List<Field> fields = new ArrayList<>();
		for (Class<?> clazzInClasses : getAllClassesIncludingSuperClasses(clazz)) {
			fields.addAll(Arrays.asList(clazzInClasses.getDeclaredFields()));
		}
		return fields;
	}

    public static Field getField(Class<?> clazz, String name) throws NoSuchFieldException {
		for (Class<?> clazzInClasses : getAllClassesIncludingSuperClasses(clazz)) {
			for (Field field : clazzInClasses.getDeclaredFields()) {
				if (field.getName().equals(name)) {
					return clazzInClasses.getDeclaredField(name);
				}
			}
		}
		throw new NoSuchFieldException();
	}

	private static List<Class<?>> getAllClassesIncludingSuperClasses(Class<?> clazz) {
		List<Class<?>> classes = new ArrayList<>();
		while (clazz != null) {
			classes.add(clazz);
			clazz = clazz.getSuperclass();
		}
		return classes;
	}
}
