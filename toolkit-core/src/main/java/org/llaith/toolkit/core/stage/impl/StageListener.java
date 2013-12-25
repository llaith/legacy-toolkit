package org.llaith.toolkit.core.stage.impl;

import org.llaith.toolkit.core.status.StatusToken;

/**
* Intended to be used to implement things like transaction control. Stage is passed in case
 * the one listener is tracking multiple stages.
*/
public interface StageListener {

    void onStart(Object source, StatusToken status);

    void onComplete(Object source, StatusToken status);

    // deliberately do not pass exception! Use exceptionHandler elsewhere if
    // needed. This is purly a notification of failure and need to 'rollback'.
    void onFailure(Object source, StatusToken status);

}
