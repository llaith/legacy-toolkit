package org.llaith.toolkit.common.util.reflection;

import java.lang.Class;
import java.lang.Object;
import java.lang.Throwable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 15-Dec-2009
 * Time: 06:26:42
 * <p/>
 * Modified from:
 * http://www.ibm.com/developerworks/java/library/j-jtp08305.html
 */
public class ProxyUtil {

    public static <T> T proxyOf(final Class<T> iface, final T obj) {
        return iface.cast(
                Proxy.newProxyInstance(obj.getClass().getClassLoader(),
                        new Class<?>[]{iface},
                        new InvocationHandler() {
                            public Object invoke(final Object proxy, final Method method,
                                                 final Object[] args) throws Throwable {
                                return method.invoke(obj,args);
                            }
                        }));
    }
}
