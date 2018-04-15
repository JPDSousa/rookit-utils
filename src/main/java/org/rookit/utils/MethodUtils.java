package org.rookit.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@SuppressWarnings("javadoc")
public class MethodUtils {
    
    private MethodUtils() {}
    
    public static Object forceInvokeMethod(final Object invoked, final Method method, final Object... args) throws Throwable {
        final boolean isAccessible = method.isAccessible();
        if (!isAccessible) {
            method.setAccessible(true);
        }
        try {
            return method.invoke(invoked, args);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        } finally {
            if (!isAccessible) {
                method.setAccessible(false);
            }
        }
    }

}
