/*
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
 */
package org.llaith.toolkit.common.lang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

/**
 * Quick and dirty wrapper.
 */
public class Console {

    private final java.io.Console console;
    private final BufferedReader reader;
    private final PrintWriter writer;

    public Console() {
        try {
            this.console = System.console();
            this.reader = (this.console == null) ? new BufferedReader(new InputStreamReader(System.in)) : null;
            this.writer = (this.console == null) ? new PrintWriter(new OutputStreamWriter(System.out,"UTF-8")) : null;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public String readLine() {

        if (this.console != null) return this.console.readLine();

        try {
            return reader.readLine();
        } catch (IOException e) {
            return null;
        }

    }

    public String readLine(String fmt, Object... args) {

        if (this.console != null) return this.console.readLine(fmt,args);

        try {
            this.writer.format(fmt,args);
            return reader.readLine();
        } catch (IOException e) {
            return null;
        }

    }

    public Console formatLine(String fmt, Object... args) {
        return this.format(fmt+"\n",args);
    }

    public Console format(String fmt, Object... args) {

        if (this.console != null) {
            this.console.format(fmt,args);

            return this;
        }

        writer.format(fmt,args).flush();

        return this;

    }

    private Reader reader() {
        return this.console != null ? this.console.reader() : this.reader;
    }

    private PrintWriter writer() {
        return this.console != null ? this.console.writer() : this.writer;
    }

}
