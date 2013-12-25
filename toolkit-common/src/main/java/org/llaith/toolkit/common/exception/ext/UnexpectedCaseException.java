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
package org.llaith.toolkit.common.exception.ext;

import org.llaith.toolkit.common.exception.BaseException;
import org.llaith.toolkit.common.guard.Guard;

/**
 * Created by IntelliJ IDEA.
 * User: nos
 * Date: 11/08/2012
 * Time: 12:47
 *
 * This is very specific exception, to be used in places such as implementing cases based on types
 * that we are sure we will never get to some path. An Oops exception occuring means we need to rethink
 * what we have designed.
 *
 */
public class UnexpectedCaseException extends RuntimeException implements BaseException {

    public UnexpectedCaseException(final Object missed) {
        super("An unexpected case choice was found: "+ Guard.notNull(missed));
    }

    public UnexpectedCaseException(final Class<?> missed) {
        super("An unexpected case in instanceof for : "+Guard.notNull(missed).getName());
    }

}
