package org.llaith.toolkit.core.status;

/**
 * Not the same as code loggers! Suited to runtime use. Verboses and summaries
 * are specialisations of infos, and fatals are a specialisation of error.
 */
public enum StatusLevel {

    VERY_VERBOSE,VERBOSE,SUMMARY,WARN,ERROR,FATAL

}
