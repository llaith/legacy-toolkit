package org.llaith.toolkit.core.memo;

/**
 *
 */
public class MemoFactory {

    public static Memo newMemo(final String message) {

        return new Memo(new Section(0)
                .withBlock(new ParagraphBlock()
                        .withText(message)));
    }

}
