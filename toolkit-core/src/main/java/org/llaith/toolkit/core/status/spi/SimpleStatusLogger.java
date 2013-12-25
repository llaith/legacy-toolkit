package org.llaith.toolkit.core.status.spi;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import org.llaith.toolkit.common.util.lang.ExceptionUtil;
import org.llaith.toolkit.common.util.lang.StringUtil;
import org.llaith.toolkit.core.memo.Memo;
import org.llaith.toolkit.core.memo.impl.StringMemoRenderer;
import org.llaith.toolkit.core.status.ProgressToken;
import org.llaith.toolkit.core.status.StatusLevel;
import org.llaith.toolkit.core.status.impl.ElapsedContext;
import org.llaith.toolkit.core.status.impl.ElapsedSnapshot;
import org.llaith.toolkit.core.status.impl.StatusControlImpl;
import org.llaith.toolkit.core.status.impl.StatusLogger;
import org.llaith.toolkit.common.etc.Counter;
import org.llaith.toolkit.common.guard.Guard;
import org.llaith.toolkit.common.ident.Identifier;
import org.llaith.toolkit.common.ident.impl.ClassnameIdentifier;
import org.llaith.toolkit.common.lang.Console;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Simple logger which does not support updating existing messages.
 */
public class SimpleStatusLogger implements StatusLogger {

    private static final DateFormat dateFormat = DateFormat.getTimeInstance();

    private static final DecimalFormat decimalFormat = new DecimalFormat("#.#");

    private static final Joiner joiner = Joiner.on(" | ");

    private final Console console;

    private final StatusLevel threshhold;

    private final Identifier identifier;

    private final String icons;

    private final double scale;

    private Map<Object,Counter> counters = new HashMap<>();

    public SimpleStatusLogger(final StatusLevel threshhold, final Identifier identifier, final int scale, final boolean animate) {
        this.console = new Console();

        this.threshhold = threshhold;
        this.identifier = identifier;
        this.scale = (double) scale;

        this.icons = animate ?
                "|/-\\" :
                ">>>>";
    }

    @Override
    public void reportStart(final ElapsedContext snapshot, final String message) {
        this.counters.put(snapshot.top().context(), new Counter(0));

        this.emitMessage(
                StatusLevel.VERBOSE,
                "[ %s ] %s",
                ident(snapshot),
                message);
    }

    @Override
    public void reportSuccess(final ElapsedContext snapshot, final String message) {
        this.counters.keySet().remove(snapshot.top().context());

        this.emitMessage(
                StatusLevel.VERBOSE,
                "[ %s ] %s (@ %s seconds)",
                ident(snapshot),
                message,
                elapsed(snapshot));
    }

    @Override
    public void reportFailure(final ElapsedContext snapshot, final String message) {
        this.counters.keySet().remove(snapshot.top().context());

        this.emitMessage(
                StatusLevel.VERBOSE,
                "[ %s ] %s (@ %s seconds)",
                ident(snapshot),
                message,
                elapsed(snapshot));
    }

    @Override
    public void reportMessage(final ElapsedContext snapshot, final Object id, final StatusLevel level, final Memo memo) {
        // this basic impl does not support updated messages.

        for (final String line : new StringMemoRenderer(0,4)
                .renderAnd(memo)
                .asLines()) {

            this.emitMessage(
                    true, // memos newline themselves
                    level,
                    "[ %s ] %s (@ %s seconds) ",
                    ident(snapshot),
                    line,
                    elapsed(snapshot));
        }
    }

    @Override
    public void reportException(final ElapsedContext snapshot, final Object id, final StatusLevel level, final Exception exception) {
        // this basic impl does not support updated messages.

        for (final String line : StringUtil.reflowTextToLines(ExceptionUtil.stackTraceToString(exception),0)) {

            this.emitMessage(
                    true, // memos newline themselves
                    level,
                    "[ %s ] %s ",
                    ident(snapshot),
                    line);
        }

    }

    @Override
    public void reportProgress(final ElapsedContext snapshot, final Object id, final StatusLevel level,
                               final String heading, final int total, final int count, final String message) {

        this.emitMessage(
                count == total, // only newline at end
                level,
                "\r[ %s ] %s %s (%d/%d) %s (@ %s seconds)",
                ident(snapshot),
                heading,
                renderProgress(
                        count,
                        total,
                        this.counters.get(snapshot.top().context()).nextValue()),
                count,
                total,
                message,
                elapsed(snapshot));

    }

    private void emitMessage(final StatusLevel level, final String msg, final Object... args) {
        this.emitMessage(true,level,msg,args);
    }

    private void emitMessage(final boolean newline, final StatusLevel level, final String msg, final Object... args) {
        if (level.ordinal() >= this.threshhold.ordinal()) {
            if (newline) this.console.formatLine(msg,args);
            else this.console.format(msg,args);
        }
    }

    private String timestamp(ElapsedContext context) {
        return dateFormat.format(context.top().timeStarted());
    }

    private String ident(ElapsedContext context) {
        return joiner.join(FluentIterable
                .from(context)
                .transform(new Function<ElapsedSnapshot,String>() {
                    @Override
                    public String apply(final ElapsedSnapshot input) {
                        return Guard.notNull(identifier.apply(input.context()));
                    }
                })
                .toList());
    }

    private double elapsed(ElapsedContext context) {
        return ((double) context.top().timeElapsed(TimeUnit.MILLISECONDS) / 1000);
    }

    private String renderProgress(final int done, final int total, final int count) {

        // easier than mucking around with integer casts inline
        final double ddone = (double) done;
        final double dtotal = (double) total;

        final double factor = scale / dtotal;
        final double complete = ddone * factor;

        final StringBuilder bar = new StringBuilder("[");

        for (int i = 0; i < scale; i++) {

            if (i < (int) complete) {
                bar.append("=");
            } else if (i == (int) complete) {
                final int offset = count % this.icons.length();
                bar.append(this.icons.charAt(offset));
                //bar.append(">");
            } else {
                bar.append(" ");
            }
        }

        final double percent = (100 / dtotal) * ddone;

        return bar.append("]   ")
                .append(decimalFormat.format(percent))
                .append("%     ")
                .toString();

    }

    public static void main(String[] args) {

        final int total = 75;

        ProgressToken progress = new StatusControlImpl(
                new SimpleStatusLogger(StatusLevel.VERY_VERBOSE,new ClassnameIdentifier(false),20,true))
                .startNested(SimpleStatusLogger.class)
                .postProgress(StatusLevel.SUMMARY)
                .update("Progress:", total, 0, "");

        for (int i = 0; i < total; i++) {
            progress.update("Progress:", total, i + 1, "Updating #" + (i + 1));
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
        }

    }

}
