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
package org.llaith.toolkit.common.snapshot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class SnapshotUtil<T extends Snapshotable<T>> {

    private static final Logger log = LoggerFactory.getLogger(SnapshotUtil.class);


    public T snapshotFields(final T cloned) throws IllegalAccessException, SnapshotException {
        for (final Field f : cloned.getClass().getFields()) {
            snapshotField(cloned,f);
        }
        return cloned;
    }

    private void snapshotField(final Object cloned, final Field field) throws IllegalAccessException, SnapshotException {
        if (hasSnapshotMarker(field)) {
            log.debug("Found snapshot-marker on field :[" + field.getName() + "] of class :[" + this.getClass().getName() + "].");
            if (isFieldSnapshotable(field))
                this.snapshotReference(cloned,field);
            else if (Set.class.isAssignableFrom(field.getType()))
                this.snapshotHashSet(cloned,field);
            else
                log.warn("Ignoring snapshot-marker on class :[" + this.getClass().getName() + "] and field :[" + field.getType() + "] because type is not supported.");
        }
    }

    private void snapshotReference(final Object cloned, final Field f) throws IllegalAccessException, SnapshotException {
        // will already have copied the ref, replace with clone where needed.
        if ((f.get(cloned)) != null) {
            f.set(cloned,((Snapshotable<?>) f.get(cloned)).snapshot());
        }
    }

    private void snapshotHashSet(final Object cloned, final Field f) throws IllegalAccessException, SnapshotException {
        // will already have copied refs in it, replace with clones where needed.
        if ((f.get(cloned)) != null) {
            final Set<Object> c = new HashSet<Object>();
            for (final Object obj : (Set<?>) f.get(this)) {
                if (obj instanceof Snapshotable<?>) {
                    c.remove(obj);
                    c.add(((Snapshotable<?>) obj).snapshot());
                }
            }
            f.set(cloned,c);
        }
    }

    private boolean isFieldSnapshotable(final Field field) {
        return Snapshotable.class.isAssignableFrom(field.getType());
    }

    private boolean hasSnapshotMarker(final Field field) {
        return (field.isAnnotationPresent(Snapshot.class)) && (field.getAnnotation(Snapshot.class).value());
    }

}
