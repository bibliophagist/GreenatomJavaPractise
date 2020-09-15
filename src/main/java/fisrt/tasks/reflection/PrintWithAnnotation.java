package fisrt.tasks.reflection;

import java.lang.reflect.Method;

public class PrintWithAnnotation {

    public static Object printHelloWorld(Object o) {
        Class<?> objectClass = o.getClass();
        for (Method method : objectClass.getMethods()) {
            if (method.isAnnotationPresent(PrintHelloWorld.class)) {
                PrintHelloWorld test = method.getAnnotation(PrintHelloWorld.class);
                System.out.println(test.printHelloWorld() + " to method " + method.getName() +
                        " of object " + o.getClass().getName());
            }
        }
        return o;
    }
}
