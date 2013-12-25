package org.llaith.toolkit.common.util.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class RuntimeUtil {

    private static final Logger Log = LoggerFactory.getLogger(RuntimeUtil.class);

    public static <X extends AutoCloseable> X onExitClose(final X closeable) {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    closeable.close();
                } catch (Exception e) {
                    Log.error("Ingoring error on shutdown of closeable:" + closeable.toString(),e);
                }
            }

        }));
        return closeable;
    }

}
