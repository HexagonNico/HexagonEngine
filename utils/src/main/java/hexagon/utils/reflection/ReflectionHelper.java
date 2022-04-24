package hexagon.utils.reflection;

import java.lang.reflect.InvocationTargetException;

public class ReflectionHelper {

	public static Object instantiate(String className) throws ReflectionException {
		try {
			return Class.forName(className).getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new ReflectionException("Cannot instantiate " + className, e);
		} catch (NoSuchMethodException | SecurityException e) {
			throw new ReflectionException("Cannot find a no-args constructor for " + className, e);
		} catch (ClassNotFoundException | LinkageError e) {
			throw new ReflectionException("Cannot find class " + className, e);
		}
	}

	public static <T> T instantiate(String className, Class<T> cast) throws ReflectionException {
		Object object = instantiate(className);
		try {
			return cast.cast(object);
		} catch (ClassCastException e) {
			throw new ReflectionException("Class " + object.getClass() + " cannot be cast to " + cast, e);
		}
	}
}
