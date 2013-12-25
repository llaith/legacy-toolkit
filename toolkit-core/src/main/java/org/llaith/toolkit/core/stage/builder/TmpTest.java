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
