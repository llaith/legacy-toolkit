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
package org.llaith.toolkit.core.stage.builder;

import com.google.common.base.Supplier;
import org.llaith.toolkit.core.memo.MemoFactory;
import org.llaith.toolkit.core.stage.Stage;
import org.llaith.toolkit.core.stage.impl.Series;
import org.llaith.toolkit.core.status.StatusLevel;
import org.llaith.toolkit.core.status.StatusToken;

/**
 *
 */
public class TmpTest {

    public static void main(String[] args) {

        SeriesBuilder series = Series.startsAs("outside")
                .firstConditionallyDo(dummyStage("start")).onlyWhen(true)
                .thenConditionallyDo(dummyStage("continue"))
                .andThenDo(dummyStage("continue2"))
                .onlyWhen(false)
                .thenDo(dummyStage("done"));

        Series.continuesFrom(series)
                .firstDo(dummyStage("a"))
                .thenDo(dummyStage("b"));

    }

    public static Supplier<Stage> dummyStage(final String name) {
        return new Supplier<Stage>() {
            @Override
            public Stage get() {
                return new Stage() {
                    @Override
                    public void execute(StatusToken status) {
                        status.postMessage(StatusLevel.WARN)
                                .update(MemoFactory.newMemo("Performing stage: " + name));
                    }
                };
            }
        };
    }

}
