package org.llaith.toolkit.core.status;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 *
 */
public interface StatusToken extends StatusControl {

    Object context();

    Date timeStarted();

    long timeElapsed(TimeUnit timeUnit);

    MessageToken postMessage(StatusLevel level);

    MessageToken postMessage(Object id, StatusLevel level);

    ExceptionToken postException(StatusLevel level);

    ExceptionToken postException(Object id, StatusLevel level);

    ProgressToken postProgress(StatusLevel level);

    ProgressToken postProgress(Object id, StatusLevel level);

}
