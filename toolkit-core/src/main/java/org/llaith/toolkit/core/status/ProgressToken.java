package org.llaith.toolkit.core.status;

/**
 *
 */
public interface ProgressToken {

    ProgressToken update(String heading, int total, int count, String message);

}
