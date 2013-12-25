package org.llaith.toolkit.core.dto;

import org.llaith.toolkit.common.guard.Guard;
import org.llaith.toolkit.common.meta.MetadataContainer;
import org.llaith.toolkit.common.meta.MetadataDelegate;

/**
 * DtoValue holds the set of values and flags that represent the current
 * and certain elements of past state for a field in a DtoObject.
 * <p/>
 * Warning:
 * Due to the use of safeNull values, a getValue == null may not be
 * the same as hasCurrent/hasOriginal() which will check the nullValue.
 * When determining the instances logical state, it makes more sense to
 * avoid the comparison to null. The comparison to null should only be
 * used when determining how to display the value.
 * Note: Could not think of a better way to do this, so forced to do a nasty
 * hack to deal with cycles without passing any objects down the interface
 * (yuck). This means it's a tad fragile.
 */
public class DtoValue<T> implements Dto<DtoValue<T>>, MetadataContainer {

    private final MetadataDelegate metadatas = new MetadataDelegate();

    private final DtoField<T> dtoField;

    private T lastValue;
    private T originalValue;
    private T currentValue;
    private T discardValue;

    private transient boolean checkDirty = false;
    private transient boolean checkStale = false;
    private transient boolean skipDirty = false;
    private transient boolean skipStale = false;
    private transient boolean skipAccept = false;
    private transient boolean skipCancel = false;

    /**
     * Constructs a new DtoValue based on the passed in DtoField definition.
     * The constrained value is defaulted to false.
     *
     * @param dtoField the definition for the field
     */
    public DtoValue(final DtoField<T> dtoField) {
        this(dtoField,dtoField.defaultValue());
    }


    /**
     * Constructs a new DtoValue based on the passed in DtoValue and the
     * constrained flag. The passed in DtoValue's DtoField and current value
     * will be used as this objects DtoField and the current and original values.
     *
     * @param dtoValue another instance of dtoValue to use as a template
     */
    public DtoValue(final DtoValue<T> dtoValue) {
        this(dtoValue.dtoField(),dtoValue.currentValue());
    }


    /**
     * Constructs a new DtoValue based on the passed in DtoField definition,
     * current value, and constrained flag. In constrained mode the DtoValue will
     * enforce checks on the required and constant flags of the DtoField definition.
     *
     * @param dtoField the definition for the field
     * @param value the value to be used as the current and original value
     */
    private DtoValue(final DtoField<T> dtoField, final T value) {
        this.dtoField = Guard.notNull(dtoField);

        this.setOriginal(value);
        this.setCurrent(value);

        this.resetState(value);
    }


    /**
     * @return the id of this instance.
     */
    public String id() {
        // convenience function
        return this.dtoField.id();
    }


    /**
     * @return the DtoField definition used in this instance.
     */
    public DtoField<T> dtoField() {
        return dtoField;
    }


    /**
     * @return true if this instances current value is not null
     * or the nullValue.
     */
    public boolean hasCurrent() {
        return (this.currentValue != null) &&
                this.currentValue != this.dtoField.nullValue();
    }


    /**
     * Returns the current value. The current value is the last value
     * of the field that was set or (in some cases) reset.
     *
     * @return the current value.
     */
    public T currentValue() {
        return this.currentValue;
    }


    /**
     * @return true if this instances original value is not null
     * or the nullValue.
     */
    public boolean hasOriginal() {
        return (this.originalValue != null) &&
                this.originalValue != this.dtoField.nullValue();
    }


    /**
     * Returns the original value. The original value is the last value
     * of the field that was reset.
     *
     * @return the original value.
     */
    public T originalValue() {
        return this.originalValue;
    }


    /**
     * Returns the last value. The last value is the previous value
     * of that was reset if the last reset altered the original value.
     *
     * @return the last (previous original) value.
     */
    public T lastValue() {
        return this.lastValue;
    }


    /**
     * Set a new current value. If the called of this function is
     * operating as a builder and does not want to verify the value
     * then the suppress param should be true.
     *
     * @param value the new current value
     * @param suppress suppress validation on the new value
     */
    public DtoValue<T> setValue(final T value, final boolean suppress) {

        // if its still current, nothing to do.
        if (value == this.currentValue) return this;

        // check we need to verify or not
        if (!suppress) this.verify(value);

        // and safe-set it.
        this.setCurrent(value);

        return this;
    }

    /**
     * Set a new current value as an Object. It will be cast to the correct type
     * for the field.
     *
     * @param value the new current value
     * @param suppress suppress validation on the new value
     */
    public DtoValue<T> setValueAsObject(final Object value, final boolean suppress) {
        this.setValue(this.castValue(value),suppress);

        return this;
    }


    /**
     * Changes the 'original' value, and also the 'current' value
     * if the passed in value is different from the previous value.
     * If the value is not different, it leaves the current value
     * which may be dirty alone. If it did change the value, it
     * will also toggle the field as stale. This may result in the
     * field responding true to isConflicted(). If it
     *
     * @param value the new 'original' value
     * @return the passed in value param
     */
    public DtoValue<T> resetValue(T value) {

        // if it hasn't changed from before, nothing to do.
        if (value == this.originalValue) return this;

        // verify the value
        this.verify(value);

        // save a new last value
        this.lastValue = this.originalValue;

        // then we set it as the new the original value
        this.setOriginal(value);

        // and if it's a new original value, we update the current
        if (this.isStale()) {
            // it's now conflicted
            this.discardValue = this.currentValue;
            // and overwritten
            this.setCurrent(value);
        }

        return this;
    }

    /**
     * Resets the original value as an object. The passed in value
     * will be cast to the correct type.
     *
     * @param value the value to reset the original value to.
     */
    public void resetValueAsObject(Object value) {
        this.resetValue(this.castValue(value));
    }


    /*
     *  Inherited.
     */
    @Override
    public boolean isDirty() {
        return (this.currentValue != this.originalValue) || (this.hasNesting() && this.nestedDto().isDirty());
    }


    /*
     *  Inherited.
     */
    @Override
    public boolean isStale() {
        return (this.originalValue != this.lastValue) || (this.hasNesting() && this.nestedDto().isStale());
    }


    /*
     *  Inherited.
     */
    @Override
    public boolean isConflicted() {
        return ((this.discardValue != null) && (this.discardValue != this.currentValue)) ||
                (this.hasNesting() && this.nestedDto().isConflicted());
    }


    /*
     *  Inherited.
     */
    @Override
    public DtoValue<T> acceptChanges() {
        this.originalValue = this.resetState(this.currentValue);

        if (this.hasNesting()) this.nestedDto().acceptChanges();

        return this;
    }


    /*
     *  Inherited.
     */
    @Override
    public DtoValue<T> cancelChanges() {
        this.currentValue = this.resetState(this.originalValue);

        if (this.hasNesting()) this.nestedDto().cancelChanges();

        return this;
    }


    /*
     *  Inherited.
     */
    @Override
    public <X> X addMetadata(X metadata) {
        return this.metadatas.addMetadata(metadata);
    }


    /*
     *  Inherited.
     */
    @Override
    public <X> X replaceMetadata(X metadata) {
        return this.metadatas.replaceMetadata(metadata);
    }


    /*
     *  Inherited.
     */
    @Override
    public <X> X metadata(Class<X> metadataClass) {
        return this.metadatas.metadata(metadataClass);
    }


    /*
     *  Inherited.
     */
    @Override
    public boolean hasMetadata(Class<?> metadataClass) {
        return this.metadatas.hasMetadata(metadataClass);
    }


    /*
     *  Inherited.
     */
    @Override
    public <X> X removeMetadata(Class<X> metadataClass) {
        return this.metadatas.removeMetadata(metadataClass);
    }


    /*
     *  Inherited.
     */
    @Override
    public final int hashCode() {
        // don't override
        return super.hashCode();
    }


    /*
     *  Inherited.
     */
    @Override
    public final boolean equals(Object obj) {
        // don't override
        return super.equals(obj);
    }


    /*
     *  Check the field is storable in the associated slot if added via
     *  the object methods. Tries casting via the generic type.
     */
    private T castValue(Object value) {
        if (value == null) return null;

        // NOTE: allows subclasses
        if (!this.dtoField.isTargetAssignableFrom(value.getClass()))
            throw new ClassCastException(String.format(
                    "Field: %s accepts: %s but received: %s.",
                    this.dtoField.id(),
                    this.dtoField.target().getRawType().getName(),
                    value.getClass().getName()));

        return this.dtoField.castTarget(value);
    }


    /*
     *  Check the field is storable in the associated slot if added via
     *  the object methods. Tries casting via the generic type.
     */
    public void verify(T value) {

        if ((value == null) && this.dtoField.isRequired())
            throw new IllegalStateException(String.format(
                    "Field: %s is not optional.",
                    this.dtoField.id()));

        if (this.dtoField.isConstant() && (value != this.originalValue))
            throw new IllegalStateException(String.format(
                    "Field: %s is constant.",
                    this.dtoField.id()));

    }


    /*
     * Sets the 'current' value using the nullValue if required.
     * Note the 'discard' is exclusively set from this value so
     * needs no special handling for nullValues.
     */
    private void setCurrent(T value) {
        // fire off change listeners
        if (value != null) this.currentValue = value;
        else this.currentValue = this.dtoField().nullValue();
    }


    /*
     * Sets the 'original' value using the nullValue if required.
     * Note the 'lost' is exclusively set from this value so
     * needs no special handling for nullValues.
     */
    private void setOriginal(final T value) {
        // fire off change listeners
        if (value != null) this.originalValue = value;
        else this.originalValue = this.dtoField().nullValue();
    }


    /**
     * Resets the internal state common to acceptChanges and
     * cancelChanges.
     *
     * @param value the current or original value as required.
     */
    private T resetState(final T value) {
        this.lastValue = value;
        this.discardValue = null;

        return value;
    }

    /*
     * Nesting is true if the current value is a Dto.
     */
    private boolean hasNesting() {
        return this.currentValue instanceof Dto;
    }


    /*
     * If the current value is a nested Dto, return it cast to Dto.
     */
    private Dto<?> nestedDto() {
        if (this.currentValue instanceof Dto) return Dto.class.cast(this.currentValue);
        return null;
    }

}
