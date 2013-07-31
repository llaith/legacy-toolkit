/*
 * Copyright (c) 2013 Nos Doughty
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
 */
package org.llaith.toolkit.toolkit.pipeline;

import org.llaith.toolkit.exception.ErrorHandler;
import org.llaith.toolkit.session.ResourceCloseException;
import org.llaith.toolkit.session.Session;
import org.llaith.toolkit.session.SessionOpenException;
import org.llaith.toolkit.session.SessionTracker;
import org.llaith.toolkit.util.guard.Guard;

/**
 *
 */
public class Pipeline<I,O> implements Session {

    private final Source<I> source;
    private final Filter<I> filter;
    private final Adapter<I,O> adapter;
    private final Transformer<O> transformer;
    private final Sink<O> sink;
    private final Callback<I> callback;
    private final ErrorHandler errors;

    private final SessionTracker tracker = new SessionTracker();

    public Pipeline(Source<I> source, Filter<I> filter, Adapter<I, O> adapter, Transformer<O> transformer, Sink<O> sink, Callback<I> callback, ErrorHandler errors) {
        this.source = track(Guard.expect(source,"Source is required"));
        this.filter = track(Guard.expect(filter,"Filter is required"));
        this.adapter = track(Guard.expect(adapter,"Adapter is required"));
        this.transformer = track(Guard.expect(transformer,"Transformer is required"));
        this.sink = track(Guard.expect(sink,"Sink is required"));
        this.callback = track(Guard.expect(callback,"Callback is required"));
        this.errors = track(Guard.expect(errors,"ErrorHandler is required"));
    }

    private <X> X track(X o) {
        if (o == null) return null;
        if (o instanceof Session) this.tracker.track((Session) o);
        return o ;
    }

    @Override
    public void open() throws SessionOpenException {
        this.tracker.open();
    }

    @Override
    public void close() throws ResourceCloseException {
        this.tracker.close();
    }

    public void pump() throws SessionOpenException, ResourceCloseException {

        try {

            this.open();

            I i = source.read();

            while (i != null) {

                boolean accepted = this.filter.accept(i);
                if (accepted) {
                    final O o = this.adapter.adapt(i);
                    this.transformer.transform(o);
                    this.sink.write(o);
                    this.callback.onProcess(i);
                } else {
                    this.callback.onSkip(i);
                }

                i = source.read();

            }

        } catch (SourceException | FilterException | AdapterException | TransformerException | SinkException e) {

            this.errors.onError(e);

        } finally {

            this.close();

        }

    }

}
