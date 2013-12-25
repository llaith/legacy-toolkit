package org.llaith.toolkit.core.status;

/**
 *
 */
public interface StatusControl {

    StatusToken startNested(Object context);

    void markNestedOk(StatusToken token);

    void markNestedFailed(StatusToken token);

    void markStageCompletion(StatusToken token, boolean success);

}
