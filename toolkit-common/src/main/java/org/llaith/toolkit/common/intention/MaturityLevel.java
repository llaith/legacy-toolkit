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
package org.llaith.toolkit.common.intention;

/**
 *
 */
public enum MaturityLevel {

    EXPERIMENT, // completely untested. An idea I am quickly jotting down for later testing.
    ALPHA, // minimally tested. Not yet used in an application.
    BETA, // used in at least one application where it appears to function. The 'good' case likely has no bugs.
    RELEASE, // properly tested and safe to use. The 'error' cases are expected to be bug free.
    STABLE // safe to use. Has been in use in multiple applications and has evolved to be useful in general cases.

}
