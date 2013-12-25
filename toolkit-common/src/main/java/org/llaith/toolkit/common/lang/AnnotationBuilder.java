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

import org.llaith.toolkit.common.util.reflection.AnnotationUtil;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class AnnotationBuilder<T extends Annotation> {


    public static <X extends Annotation> AnnotationBuilder<X> buildAnnotation(final Class<X> annClass) {
        return new AnnotationBuilder<>(annClass);
    }

    public static class AnnotationParam<T extends Annotation> {
        private final AnnotationBuilder<T> holder;
        private final String key;

        private AnnotationParam(final AnnotationBuilder<T> holder, final String key) {
            this.holder = holder;
            this.key = key;
        }

        public AnnotationBuilder<T> of(final Object val) {
            this.holder.values.put(this.key,val);
            return this.holder;
        }
    }

    private final Class<T> klass;
    private final Map<String, Object> values;

    private AnnotationBuilder(final Class<T> klass) {
        this(klass,new HashMap<String, Object>());
    }

    private AnnotationBuilder(final Class<T> klass, final Map<String, Object> values) {
        this.klass = klass;
        this.values = values;
    }


    public AnnotationBuilder.AnnotationParam<T> with(final String key) {
        return new AnnotationBuilder.AnnotationParam<>(this,key);
    }


    public T create() {
        return AnnotationUtil.newAnnotation(this.klass,this.values);
    }

}
