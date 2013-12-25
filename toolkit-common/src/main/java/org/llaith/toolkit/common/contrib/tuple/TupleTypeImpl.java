package org.llaith.toolkit.common.contrib.tuple;


class TupleTypeImpl implements TupleType {


    final Class<?>[] types;

    TupleTypeImpl(final Class<?>[] types) {
        this.types = (types != null ? types : new Class<?>[0]);
    }

    @Override
    public int size() {
        return this.types.length;
    }

    // WRONG
    // public <T> Class<T> getNthType(int i)

    // RIGHT - thanks Emil
    @Override
    public Class<?> getNthType(final int i) {
        return this.types[i];
    }


    @SuppressWarnings("boxing")
    @Override
    public Tuple createTuple(final Object... values) {
        if ((values == null && this.types.length == 0) ||
                (values != null && values.length != this.types.length)) {
            throw new IllegalArgumentException(
                    "Expected " + this.types.length + " values, not " +
                            (values == null ? "(null)" : values.length) + " values");
        }

        if (values != null) {
            for (int i = 0; i < this.types.length; i++) {
                final Class<?> nthType = this.types[i];
                final Object nthValue = values[i];
                if (nthValue != null && !nthType.isAssignableFrom(nthValue.getClass())) {
                    throw new IllegalArgumentException(
                            "Expected value #" + i + " ('" +
                                    nthValue + "') of new Tuple to be " +
                                    nthType + ", not " +
                                    (nthValue != null ? nthValue.getClass() : "(null type)"));
                }
            }
        }

        return new TupleImpl(this,values);
    }
}
