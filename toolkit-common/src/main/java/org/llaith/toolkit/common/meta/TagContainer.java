package org.llaith.toolkit.common.meta;

/**
 *
 */
public interface TagContainer {

    boolean containsTag(String tag);

    String addTag(String tag);

    String removeTag(String tag);

}
