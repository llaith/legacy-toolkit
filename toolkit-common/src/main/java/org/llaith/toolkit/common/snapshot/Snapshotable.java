package org.llaith.toolkit.common.snapshot;

public interface Snapshotable<T extends Snapshotable<T>> {

    public T snapshot() throws SnapshotException;

}
