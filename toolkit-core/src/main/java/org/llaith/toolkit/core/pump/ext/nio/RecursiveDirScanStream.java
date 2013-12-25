package org.llaith.toolkit.core.pump.ext.nio;

import org.llaith.toolkit.common.exception.ext.UncheckedException;
import org.llaith.toolkit.core.pump.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Stack;

/**
 *
 */
public class RecursiveDirScanStream implements Stream<Path>, AutoCloseable {

    private static final Logger Log = LoggerFactory.getLogger(RecursiveDirScanStream.class);

    private final String startDir;
    private final boolean returnFiles;
    private final boolean returnDirs;

    private Stack<Path> paths = null;
    private DirectoryStream<Path> ds = null;
    private Iterator<Path> iterable = null;

    public RecursiveDirScanStream(final String startDir, final boolean returnFiles, final boolean returnDirs) {
        this.startDir = startDir;
        this.returnFiles = returnFiles;
        this.returnDirs = returnDirs;
    }

    @Override
    public Path read() {

        if (this.paths == null) this.firstPath();

        while (this.iterable != null) {

            while (this.iterable.hasNext()) {
                final Path next = this.iterable.next();
                if (Files.isRegularFile(next)) {
                    if (this.returnFiles) return next;
                }
                else if (Files.isDirectory(next)) {
                    paths.push(next);
                    if (this.returnDirs) return next;
                }
            }

            // we dropped out, get a new iterator
            this.nextPath();

        }

        return null;
    }

    @Override
    public void close() {
        try {
            if (this.ds != null) this.ds.close();
        } catch (IOException e) {
            throw new UncheckedException("Cannot get close dir-stream.",e);
        }
    }

    private void firstPath() {
        try {
            this.paths = new Stack<>();
            final Path path = FileSystems.getDefault().getPath(this.startDir);
            if (!Files.isDirectory(path)) throw new UncheckedException("The path '"+this.startDir+"' is not a directory.");
            this.ds = Files.newDirectoryStream(path);
            this.iterable = this.ds.iterator();
        } catch (IOException e) {
            throw new UncheckedException("Cannot get first path.",e);
        }
    }

    private void nextPath() {
        try {
            this.ds.close();
            if (!this.paths.isEmpty()) {
                Log.warn("Scanning path: "+this.paths.peek());
                this.ds = Files.newDirectoryStream(this.paths.pop());
                this.iterable = this.ds.iterator();
            } else {
                this.ds = null;
                this.iterable = null;
            }
        } catch (IOException e) {
            throw new UncheckedException("Cannot get next path.",e);
        }
    }

}
