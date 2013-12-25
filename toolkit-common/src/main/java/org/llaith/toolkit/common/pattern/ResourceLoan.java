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
package org.llaith.toolkit.common.pattern;

/**
 * When calling releaseTarget(), a subsequent call to target() must return null! This is the safest way to avoid
 * re-using released resources. A nullcheck on the target() is simple enough to avoid needing a isReleased() method,
 * especially as the whole concept of the ResourceLoan means you shouldn't need to check. It's yours until you are
 * done. If you want another, take the resourceManager as a param instead.
 */
public interface ResourceLoan<T> {

    T target();

    void releaseTarget();

}
