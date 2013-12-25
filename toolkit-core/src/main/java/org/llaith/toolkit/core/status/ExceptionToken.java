package org.llaith.toolkit.core.status;

import org.llaith.toolkit.core.memo.Memo;

/**
 *
 */
public interface ExceptionToken {

    ExceptionToken update(Memo memo, Exception exception);

}
