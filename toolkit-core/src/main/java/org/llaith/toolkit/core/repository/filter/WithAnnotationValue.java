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
package org.llaith.toolkit.core.repository.filter;

import com.google.common.base.Predicate;
import net.vidageek.mirror.dsl.Mirror;
import net.vidageek.mirror.invoke.dsl.MethodHandler;
import org.llaith.toolkit.common.guard.Guard;
import org.llaith.toolkit.common.meta.MetadataContainer;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Objects;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

/**
 *
 */
public class WithAnnotationValue<T> implements Predicate<T> {

    public interface AnnotationFinder<T> {
        Annotation find(Class<? extends Annotation> annClass, T t);
    }

    public static class ClassFinder implements WithAnnotationValue.AnnotationFinder<Class<?>> {
        @Override
        public Annotation find(final Class<? extends Annotation> annClass, final Class<?> target) {
            return target.getAnnotation(annClass);
        }
    }

    public static class ModelFinder<T extends MetadataContainer> implements WithAnnotationValue.AnnotationFinder<T> {
        @Override
        public Annotation find(final Class<? extends Annotation> annClass, final T target) {
            return target.metadata(annClass);
        }
    }

    @SafeVarargs
    public static <X> WithAnnotationValue<X> withMetadata(
            final WithAnnotationValue.AnnotationFinder<X> finder,
            final String param,
            final Object value,
            final Class<? extends Annotation>... annClass) {
        return new WithAnnotationValue<>(finder,param,value,annClass);
    }

    private final List<Class<? extends Annotation>> annClasses;
    private final String param;
    private final Object value;
    private final WithAnnotationValue.AnnotationFinder<T> finder;

    @SafeVarargs
    public WithAnnotationValue(final WithAnnotationValue.AnnotationFinder<T> finder, final String param, final Object value, final Class<? extends Annotation>... annClasses) {
        this(finder,param,value,asList(annClasses));
    }

    public WithAnnotationValue(final WithAnnotationValue.AnnotationFinder<T> finder, final String param, final Object value, final List<Class<? extends Annotation>> annClasses) {
        this.param = param;
        this.value = value;
        this.finder = finder;
        this.annClasses = Guard.checkArg(annClasses).is(not(empty())).thenReturnExpected();
    }

    @Override
    public boolean apply(@Nullable final T input) {
        for (final Class<? extends Annotation> annClass : this.annClasses) {
            final Annotation ann = this.finder.find(annClass,input);
            if (ann != null) {
                final MethodHandler method = new Mirror().on(ann).invoke().method(this.param);
                if (method != null) {
                    final Object o = method.withoutArgs();
                    return Objects.equals(o,this.value);
                }
            }
        }
        return false;
    }

}
