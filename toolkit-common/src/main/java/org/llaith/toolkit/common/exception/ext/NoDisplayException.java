package org.llaith.toolkit.common.exception.ext;

/**
 * Used when an exception has been handled previously, and you
 * just want to break the flow.
 */
public class NoDisplayException extends RuntimeException {

    public NoDisplayException(final Throwable cause) {
        super(cause);
    }

    public NoDisplayException(final String message) {
        super(message);
    }

    public NoDisplayException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
