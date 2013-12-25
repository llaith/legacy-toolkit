package org.llaith.toolkit.common.snapshot;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by IntelliJ IDEA.
 * User: llaith
 * Date: 18/09/2011
 * Time: 10:17
 *
 * ToSnapshot & Snapshot: I forgot this. The Snapshot mechanism is a way to get 'clones' of objects that are saved as historic. Any *reference* or *collection
 * of references* that should be preserved when the entity being snapshotted is saved should have the @Snapshot marker on them. This would include for example,
 * addresses being saved as part of a person record.
 *
 * Generally CHILD entitiy references will be @Snapshot, and PARENT entity references won't be. With Collections that are always children, they will be
 * snapshotted when they are 'owned', not 'shared' by the entity.
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface Snapshot {
    boolean value() default true;
}
