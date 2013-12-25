package org.llaith.toolkit.common.exception.ext;

import org.llaith.toolkit.common.exception.BaseException;
import org.llaith.toolkit.common.guard.Guard;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 11/08/2012
 * Time: 12:47
 *
 * This is very specific exception, to be used in places such as implementing cases based on types
 * that we are sure we will never get to some path. An Oops exception occuring means we need to rethink
 * what we have designed.
 *
 */
public class UnexpectedCaseException extends RuntimeException implements BaseException {

    public UnexpectedCaseException(final Object missed) {
        super("An unexpected case choice was found: "+ Guard.notNull(missed));
    }

    public UnexpectedCaseException(final Class<?> missed) {
        super("An unexpected case in instanceof for : "+Guard.notNull(missed).getName());
    }

}
