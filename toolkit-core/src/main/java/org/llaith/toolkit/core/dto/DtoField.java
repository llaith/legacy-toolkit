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
package org.llaith.toolkit.core.dto;

import com.google.common.primitives.Primitives;
import com.google.common.reflect.TypeToken;
import org.llaith.toolkit.common.exception.ext.UncheckedException;
import org.llaith.toolkit.common.etc.IdToken;
import org.llaith.toolkit.common.guard.Guard;
import org.llaith.toolkit.common.lang.Primitive;
import org.llaith.toolkit.common.util.lang.StringUtil;

/**
 * These five properties define how we actually think of objects in OOP. The
 * nullable is enforced with assertions/IllegalArgumentExceptions, the constant
 * with the lack of a getter, and identity fields are used in the equals and
 * should ideally be constant. Currently it's not enforced that the identity
 * fields are constant. Also, for the purposes of Dto's, constant fields can
 * only be 'set' via the 'authorative' update routines.
 * <p/>
 * Validation and conversion are NOT included in Dtos. These require extra
 * infrastructure to display the failures. At this *lower* level, problems are
 * essentially programming errors and will be thrown as RuntimeExceptions.
 * <p/>
 * Be careful about setting the optional flag. It's for use in cases where an
 * equivalent real object would throw an exception on null eg: Guard.notNull(),
 * not for simple validation cases. Validation to notNull is done with the
 * Forms. That means very few cases should have a this set.
 * <p/>
 * NOTE: Division of labour. If it's a check for the vale *class*, it probably
 * belongs in here. If it's for the value instance, it's probably a check that belongs
 * in the DtoField instead.
 */
public class DtoField<T> extends IdToken<String,T> {

    // primitive special case. Always has a nullvalue, and always is required. Therefore, doesn't need a defaultvalue.
    public static <T> DtoField<T> newPrimitive(final String id, final Primitive<T> primitive) {
        return new DtoField<>(
                id,
                Guard.notNull(primitive).wrapperClass(),
                true,
                false,
                false,
                primitive.nullValue(),
                null);
    }

    public static <T> DtoField<T> newPrimitive(final String id, final Primitive<T> primitive,
                                                final boolean constant, final boolean identity) {
        return new DtoField<>(
                id,
                Guard.notNull(primitive).wrapperClass(),
                true,
                constant,
                identity,
                primitive.nullValue(),
                null);
    }

    public static <T> DtoField<T> newReference(final String id, final Class<T> target) {

        if (Primitives.isWrapperType(target)) throw new IllegalArgumentException(
                "newReference() cannot be used for primitive types.");

        if (Dto.class.isAssignableFrom(target)) throw new IllegalArgumentException(
                "newReference() cannot be used for Dto types.");

        return new DtoField<>(
                id,
                target);
    }

    public static <T> DtoField<T> newReference(final String id, final Class<T> target,
                                                final boolean required, final boolean constant, final boolean identity) {

        if (Primitives.isWrapperType(target)) throw new IllegalArgumentException(
                "newReference() cannot be used for primitive types.");

        if (Dto.class.isAssignableFrom(target)) throw new IllegalArgumentException(
                "newReference() cannot be used for Dto types.");

        return new DtoField<>(
                id,
                target,
                required,
                constant,
                identity);
    }

    public static <T> DtoField<T> newReference(final String id, final Class<T> target,
                                                final boolean required, final boolean constant, final boolean identity,
                                                final T nullValue, final T defaultValue) {

        if (Primitives.isWrapperType(target)) throw new IllegalArgumentException(
                "newReference() cannot be used for primitive types.");

        if (Dto.class.isAssignableFrom(target)) throw new IllegalArgumentException(
                "newReference() cannot be used for Dto types.");

        return new DtoField<>(
                id,
                target,
                required,
                constant,
                identity,
                nullValue,
                defaultValue);
    }

    public static <T extends DtoObject<T>> DtoField<T> newDtoObject(final String id, final Class<T> target) {

        if (!DtoObject.class.isAssignableFrom(target)) throw new IllegalArgumentException(
                "newDtoObject() can only be used for DtoObject types."); // for nice error if forced in with cast

        return new DtoField<>(
                id,
                target);
    }

    public static <T extends DtoObject<T>> DtoField<T> newDtoObject(final String id, final Class<T> target, final boolean required) {

        if (!DtoObject.class.isAssignableFrom(target)) throw new IllegalArgumentException(
                "newDtoObject() can only be used for DtoObject types.");

        return new DtoField<>(
                id,
                target,
                required,
                false,
                false,
                null,
                null);
    }

    public static <T extends DtoObject<T>> DtoField<T> newDtoObject(final String id, final TypeToken<T> target, final boolean required) {

        if (!DtoObject.class.isAssignableFrom(target.getRawType())) throw new IllegalArgumentException(
                "newDtoObject() can only be used for DtoObject types.");

        return new DtoField<>(
                id,
                target,
                required,
                false,
                false,
                null,
                null);
    }

    public static <T extends DtoObject<T>> DtoField<DtoCollection<T>> newDtoCollection(final String id, final TypeToken<DtoCollection<T>> target) {

        if (!DtoCollection.class.isAssignableFrom(target.getRawType())) throw new IllegalArgumentException(
                "newDtoCollection() can only be used for DtoCollection types.");

        return new DtoField<>(
                id,
                target,
                false,
                false,
                false,
                new DtoCollection<T>(),
                new DtoCollection<T>());
    }

    private final boolean required;         // whether Guard.notNull() is on the value.
    private final boolean constant;         // whether it's a final param.
    private final boolean identity;         // whether it's use in equals/hashcode.
    private final T defaultValue;           // what it's initialised to in the ctor.
    private final T nullValue;              // if we need a null-object-pattern for null.

    private DtoField(final String id, final Class<T> target) {
        this(id,target,false,false,false);
    }

    private DtoField(final String id, final Class<T> target,
                     final boolean required, final boolean constant, final boolean identity) {
        this(id,target,required,constant,identity,null,null);
    }

    private DtoField(final String id, final Class<T> target,
                     final boolean required, final boolean constant, final boolean identity,
                     final T nullValue, final T defaultValue) {
        this(id,TypeToken.of(Guard.notNull(target)),required,constant,identity,nullValue,defaultValue);
    }

    private DtoField(final String id, final TypeToken<T> target) {
        this(id,target,false,false,false);
    }

    private DtoField(final String id, final TypeToken<T> target,
                     final boolean required, final boolean constant, final boolean identity) {
        this(id,target,required,constant,identity,null,null);
    }

    private DtoField(final String id, final TypeToken<T> target,
                     final boolean required, final boolean constant, final boolean identity,
                     final T nullValue, final T defaultValue) {

        super(id,target);

        if (!StringUtil.isValidJavaIdentifier(id)) throw new IllegalArgumentException(String.format(
                "The name '%s' is not a valid java identifier.",
                id));

        this.required = required;
        this.constant = constant;
        this.identity = identity;

        this.nullValue = nullValue;
        this.defaultValue = defaultValue;

        this.verify();

    }

    private DtoField<T> verify() {

        // we need this to stop weirdness around the event listeners
        if (this.identity && !this.constant) throw new UncheckedException(String.format(
                "Field: %s is an identity field but it is not constant as it is required to be.",
                this.id()));

        return this;
    }

    public DtoValue<T> newDtoValue() {
        // this is here because i don't know why new DtoValue<>(dtofield) works
        // when dtofield is DtoField<?>! Basically, I don't believe the compiler.
        return new DtoValue<>(this);
    }

    public boolean isRequired() {
        return this.required;
    }

    public boolean isConstant() {
        return this.constant;
    }

    public boolean isIdentity() {
        return this.identity;
    }

    public T nullValue() {
        return this.nullValue;
    }

    public T defaultValue() {
        return this.defaultValue;
    }

}
