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
package org.llaith.toolkit.common.util.reflection;

import org.junit.Assert;
import org.junit.Test;
import org.llaith.toolkit.common.lang.AnnotationBuilder;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class AnnotationUtilTest {

    @Retention(RetentionPolicy.RUNTIME)
    private static @interface MyAnnotation {
        String value1() default "one";
        int value2() default 2;
        String value3();
    }

    @Test
    public void basicTest() {

        MyAnnotation ann = AnnotationUtil.newAnnotation(MyAnnotation.class);

        Assert.assertEquals("Created annotation is not a subclass of Proxy.",Proxy.class,ann.getClass().getSuperclass());
        Assert.assertEquals("Created annotation does not implement desired annotation.",MyAnnotation.class,ann.annotationType());
        Assert.assertTrue("Created annotation will not worn with instanceof.",MyAnnotation.class.isAssignableFrom(ann.getClass()));

    }

    @Test
    public void defaultsTest() {

        MyAnnotation ann = AnnotationUtil.newAnnotation(MyAnnotation.class);

        // test defaults
        Assert.assertEquals("Created annotation has bad defaults.","one",ann.value1());
        Assert.assertEquals("Created annotation has bad boxing defaults.",2,ann.value2());

    }

    @Test
    public void fullTest() {

        final Map<String,Object> vals = new HashMap<String,Object>();
        vals.put("value2",3);
        vals.put("value3","three");

        MyAnnotation ann = AnnotationUtil.newAnnotation(MyAnnotation.class,vals);

        Assert.assertEquals("Created annotation has bad overrides of defaults.",3,ann.value2());
        Assert.assertEquals("Created annotation has bad overrides of non-defaults.","three",ann.value3());

    }

    @Test
    public void prettyTest() {

        MyAnnotation ann = AnnotationBuilder.buildAnnotation(MyAnnotation.class)
                .with("value2").of(3)
                .with("value3").of("three")
                .create();

        Assert.assertEquals("[Pretty] Created annotation has bad overrides of defaults.",3,ann.value2());
        Assert.assertEquals("[Pretty] Created annotation has bad overrides of non-defaults.","three",ann.value3());

    }

}
