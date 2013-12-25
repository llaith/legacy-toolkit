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
package org.llaith.toolkit.core.memo.impl;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import org.llaith.toolkit.common.etc.Counter;
import org.llaith.toolkit.common.etc.FirstFlag;
import org.llaith.toolkit.common.etc.Text;
import org.llaith.toolkit.common.exception.ext.UnexpectedCaseException;
import org.llaith.toolkit.common.util.lang.ExceptionUtil;
import org.llaith.toolkit.common.util.lang.StringUtil;
import org.llaith.toolkit.core.memo.Block;
import org.llaith.toolkit.core.memo.ListBlock;
import org.llaith.toolkit.core.memo.ListItem;
import org.llaith.toolkit.core.memo.Memo;
import org.llaith.toolkit.core.memo.MemoFactory;
import org.llaith.toolkit.core.memo.MemoRenderer;
import org.llaith.toolkit.core.memo.ParagraphBlock;
import org.llaith.toolkit.core.memo.Section;
import org.llaith.toolkit.core.memo.TableBlock;

import java.util.ArrayList;
import java.util.List;

/**
 * Note, can render multiple messages through this if you want.
 */
public class StringMemoRenderer implements MemoRenderer {

    private final int tabSize;

    private final Text text;

    private final FirstFlag messageFlag = new FirstFlag();

    public StringMemoRenderer(final int maxWidth, final int tabSize) {
        this.tabSize = tabSize;

        this.text = new Text(maxWidth);
    }

    public StringMemoRenderer renderAnd(final Memo memo) {
        this.render(memo);
        return this;
    }

    @Override
    public void render(final Memo memo) {

        if (this.messageFlag.notFirst()) this.text.newline(); // extra

        final Counter sectionCounter = new Counter();

        for (final Section section: memo.sections()) {

            if (sectionCounter.notFirst()) this.text.newline(); // extra

            this.renderSection(section);
        }

    }

    private void renderSection(final Section section) {

        if (section.hasHeading()) {

            this.text.newline();

            this.text.newline(
                    this.tabSize * (section.level() * 2),
                    section.heading());

            this.text.newline(
                    this.tabSize * (section.level() * 2),
                    Strings.repeat("~", section.heading().length()));

            this.text.newline(); // extra
        }

        final FirstFlag blockFlag = new FirstFlag();

        if (section.hasBlocks()) {

            for (final Block block : section.blocks()) {

                if (blockFlag.notFirst()) this.text.newline();

                this.renderBlock(
                        section.level() * 2,
                        block);
            }
        }

    }

    private void renderBlock(final int indentLevel, final Block block) {

        if (block.hasTitle()) {

            this.text.newline(
                    this.tabSize * indentLevel,
                    block.title() + ":");

        }

        if (block instanceof ParagraphBlock) this.renderParagraph(indentLevel,(ParagraphBlock)block);
        else if (block instanceof ListBlock) this.renderList(indentLevel,(ListBlock) block);
        else if (block instanceof TableBlock) this.renderTable(indentLevel,(TableBlock) block);
        else throw new UnexpectedCaseException(block.getClass());

    }

    private void renderParagraph(final int indentLevel, final ParagraphBlock para) {

        for (final String text : para.texts()) {

            this.text.newline(
                    this.tabSize * indentLevel,
                    text);

        }

    }

    private void renderList(final int indentLevel, final ListBlock list) {

        final Counter count = new Counter();

        for (final ListItem item : list.items()) {

            this.renderItem(
                    indentLevel,
                    list,
                    count.nextValue(),
                    item);

        }

    }

    private void renderItem(final int indentLevel, final ListBlock list, final int itemCount, final ListItem item) {

        final String s = list.numeric() ?
                ""+(itemCount + 1) :
                "*";

        this.text.newline(
                this.tabSize * indentLevel,
                s + ") " + item.heading());

        if (item.hasBlocks()) {

            for (final Block block : item.blocks()) {
                this.renderBlock(indentLevel+1,block); // increment the indent
            }

        }

    }

    private void renderTable(final int indentLevel, final TableBlock table) {
        throw new UnsupportedOperationException("TODO");
    }

    public String asString() {
        return Joiner.on("\n").join(this.text.lines());
    }

    public List<String> asLines() {
        return new ArrayList<>(this.text.lines());
    }

    public static void main(String[] args) {

        //testBasic();

        //testBasicOverflow();

        //testBasicOverflowWithIndent();

        //testBasicOverflowWithIndentAndLists();

        //testBasicLists();

        testNestedLists();

        try {
            throw new RuntimeException();
        } catch (Exception e) {
            try {
                throw new RuntimeException(e);
            } catch (Exception e1) {
                e1.addSuppressed(new Exception());

                System.out.println(StringUtil.reflowText(ExceptionUtil.stackTraceToString(e1), 80));
            }
        }

    }

    private static void testBasic() {
        // test basic message
        System.out.println("****************************************************************************************************");
        System.out.println(new StringMemoRenderer(80,4)
                .renderAnd(MemoFactory.newMemo("Hello There!"))
                .renderAnd(MemoFactory.newMemo("How are you?"))
                .asString());
    }

    private static void testBasicOverflow() {
        // test basic overflow
        System.out.println("****************************************************************************************************");
        System.out.println(new StringMemoRenderer(80,4)
                .renderAnd(MemoFactory.newMemo("...................................................................................................."))
                .renderAnd(MemoFactory.newMemo("...................................................................................................."))
                .asString());
    }

    private static void testBasicOverflowWithIndent() {
        // test basic indent with overflow
        System.out.println("****************************************************************************************************");
        System.out.println(new StringMemoRenderer(80,4)
                .renderAnd(new Memo(
                        new Section(0,"Heading 1",
                                new ParagraphBlock("Title 1","...................................................................................................."),
                                new ParagraphBlock("Title 2","....................................................................................................")),
                        new Section(1,"Sub Heading 1",
                                new ParagraphBlock("Title 3","...................................................................................................."),
                                new ParagraphBlock("Title 4","....................................................................................................")),
                        new Section(0,"Heading 2",
                                new ParagraphBlock("Title 5","...................................................................................................."),
                                new ParagraphBlock("Title 6","...................................................................................................."))
                ))
                .asString());
    }

    private static void testBasicLists() {
        System.out.println("****************************************************************************************************");
        System.out.println(new StringMemoRenderer(80,4)
                .renderAnd(new Memo(
                        new Section(1,"Heading 1",
                                new ParagraphBlock("Title 1","...................................................................................................."),
                                new ListBlock(true,
                                        "Some List 1",
                                        new ListItem("Item 1"),
                                        new ListItem("Item 2")))
                ))
                .asString());
    }

    private static void testNestedLists() {
        System.out.println("****************************************************************************************************");
        System.out.println(new StringMemoRenderer(80,4)
                .renderAnd(new Memo(
                        new Section(1,"Heading 1",
                                new ParagraphBlock("Title 1","...................................................................................................."),
                                new ListBlock(true,
                                        lorum(),
                                        new ListItem("Item 1",
                                                new ParagraphBlock("Heading","This is some text.","This is some more text.")),
                                        new ListItem("Item 2",
                                                new ListBlock(false,"Sublist",
                                                        new ListItem("Item A"),
                                                        new ListItem("Item B")))))
                ))
                .asString());
    }

    private static String lorum() {
        return "Didn't need no welfare states. Everybody pulled his weight. Gee our old Lasalle ran great. Those were the days. And when the odds are against him and their dangers work to do. You bet your life Speed Racer he will see it through. Movin' on up to the east side. We finally got a piece of the pie. Movin' on up to the east side. We finally got a piece of the pie." +
                "Well we're movin' on up to the east side. To a deluxe apartment in the sky. Movin' on up to the east side. We finally got a piece of the pie. The first mate and his Skipper too will do their very best to make the others comfortable in their tropic island nest. The first mate and his Skipper too will do their very best to make the others comfortable in their tropic island nest. Sunny Days sweepin' the clouds away. On my way to where the air is sweet. Can you tell me how to get how to get to Sesame Street." +
                "\nAnd when the odds are against him and their dangers work to do. You bet your life Speed Racer he will see it through. He's gainin' on you so you better look alive. He busy revin' up his Powerful Mach 5. The ship set ground on the shore of this uncharted desert isle with Gilligan the Skipper too the millionaire and his wife. So this is the tale of our castaways they're here for a long long time. They'll have to make the best of things its an uphill climb. Straightnin' the curves. Flatnin' the hills Someday the mountain might get ‘em but the law never will. It's time to play the music. It's time to light the lights. It's time to meet the Muppets on the Muppet Show tonight." +
                "Just two good ol' boys Wouldn't change if they could. Fightin' the system like a true modern day Robin Hood. So lets make the most of this beautiful day. Since we're together! Movin' on up to the east side. We finally got a piece of the pie. Maybe you and me were never meant to be. But baby think of me once in awhile. I'm at WKRP in Cincinnati. Sunny Days sweepin' the clouds away. On my way to where the air is sweet. Can you tell me how to get how to get to Sesame Street." +
                "All of them had hair of gold like their mother the youngest one in curls. Its mission - to explore strange new worlds to seek out new life and new civilizations to boldly go where no man has gone before. Come and knock on our door. We've been waiting for you. Where the kisses are hers and hers and his. Three's company too. Here he comes Here comes Speed Racer. He's a demon on wheels? Here's the story of a man named Brady who was busy with three boys of his own. And when the odds are against him and their dangers work to do. You bet your life Speed Racer he will see it through. Just sit right back and you'll hear a tale a tale of a fateful trip that started from this tropic port aboard this tiny ship";
    }

}
