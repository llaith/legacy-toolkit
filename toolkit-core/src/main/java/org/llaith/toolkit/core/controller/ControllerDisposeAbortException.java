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
package org.llaith.toolkit.core.controller;

/**
 * A controller that sends this exception is stopping the current
 * unwinding of the stack. The thrower will receive an additional
 * activate() immediately after this exception is processed. The
 * sender should use that activate() as a trigger (set a flag before
 * throwing the exception) to display (possibly involving the push()
 * of a controller that can resolve the condition that caused the
 * dispose() to be aborted. This delayed processing is to allow the
 * controllerStack to be reset before receiving any other pushes
 * to help resolve the condition.
 */
public class ControllerDisposeAbortException extends Exception {

    /**
     * Create a new exception that will abort the current requested pop.
     */
    public ControllerDisposeAbortException() {
        super("Controller pop has been aborted");
    }

}
