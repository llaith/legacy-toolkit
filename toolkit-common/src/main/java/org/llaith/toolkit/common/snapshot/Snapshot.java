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
package org.llaith.toolkit.common.snapshot;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by IntelliJ IDEA.
 * User: llaith
 * Date: 18/09/2011
 * Time: 10:17
 *
 * ToSnapshot & Snapshot: I forgot this. The Snapshot mechanism is a way to get 'clones' of objects that are saved as historic. Any *reference* or *collection
 * of references* that should be preserved when the entity being snapshotted is saved should have the @Snapshot marker on them. This would include for example,
 * addresses being saved as part of a person record.
 *
 * Generally CHILD entitiy references will be @Snapshot, and PARENT entity references won't be. With Collections that are always children, they will be
 * snapshotted when they are 'owned', not 'shared' by the entity.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Snapshot {
    boolean value() default true;
}
