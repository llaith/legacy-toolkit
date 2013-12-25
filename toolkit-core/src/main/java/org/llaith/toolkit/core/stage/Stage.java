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
package org.llaith.toolkit.core.stage;

import org.llaith.toolkit.common.pattern.Command;
import org.llaith.toolkit.core.status.StatusToken;

/**
 * Like a command, in that it's a another example of a command-pattern, but not
 * the toolkit.Command from the toolkit-misc because *that* command is a pattern
 * based around using a command manager, and this one is based around a 'flow'
 * of stages reporting status through a reporting interface.
 */
public interface Stage extends Command<StatusToken> {

    void execute(StatusToken status);

}
