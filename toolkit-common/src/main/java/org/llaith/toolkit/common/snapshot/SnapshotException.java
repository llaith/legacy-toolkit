package org.llaith.toolkit.common.snapshot;

import org.llaith.toolkit.common.exception.BaseException;

/**
 *
 */
public class SnapshotException extends Exception implements BaseException {

    public SnapshotException(final Throwable cause) {
        super(cause);
    }

}
