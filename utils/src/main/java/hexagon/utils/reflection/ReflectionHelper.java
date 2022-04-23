package hexagon.utils.reflection;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.Consumer;

public class ReflectionHelper {

	public static Object instantiate(String className) {
		try {
			return Class.forName(className).getConstructor().newInstance();
		} catch (Exception e) {
			throw new ReflectionException("Could not instantiate " + className, e);
		}
	}

	public static <T> T instantiate(String className, Class<T> cast) {
		return cast.cast(instantiate(className));
	}

	public static void forEachField(Class<?> clazz, Consumer<Field> action) {
		for(Field field : clazz.getDeclaredFields()) {
			action.accept(field);
		}
	}

	public static boolean isFieldAssignable(Field field, Class<?>... types) {
		return Arrays.stream(types).anyMatch(type -> field.getType().isAssignableFrom(type));
	}

	public static void setField(Object object, Field field, Object value) {
		try {
			if(field.canAccess(object)) {
				field.set(object, value);
			} else {
				field.setAccessible(true);
				field.set(object, value);
				field.setAccessible(false);
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new ReflectionException("Could not set field " + field.getName(), e);
		}
	}
}
