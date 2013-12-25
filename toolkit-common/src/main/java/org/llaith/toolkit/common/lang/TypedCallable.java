package org.llaith.toolkit.common.lang;

import java.util.concurrent.Callable;

/**
 *
 */
public interface TypedCallable<V,E extends Exception> extends Callable<V> {

    V call() throws E;

}
