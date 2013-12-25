package org.llaith.toolkit.common.util.reflection;

import org.junit.Test;

import java.util.TreeMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

/**
 *
 */
public class ClassStructureUtilTest {

    @Test
    public void testBasics() {
        assertThat(
                InstanceUtil.classNamesFrom(ClassStructureUtil.findSuperTypes(TreeMap.class)),
                containsInAnyOrder(
                        "java.util.NavigableMap","java.util.Map","java.lang.Object",
                        "java.util.SortedMap","java.util.TreeMap","java.util.AbstractMap",
                        "java.io.Serializable","java.lang.Cloneable"));
    }


}
