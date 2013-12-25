/*******************************************************************************

  * Copyright (c) 2005 - 2013 Nos Doughty
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *     http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.

 ******************************************************************************/
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
