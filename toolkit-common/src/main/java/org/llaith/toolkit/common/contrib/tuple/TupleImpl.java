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
package org.llaith.toolkit.common.contrib.tuple;


import java.util.Arrays;


class TupleImpl implements Tuple {

    private final TupleType type;

    private final Object[] values;

    TupleImpl(final TupleType type, final Object[] values) {
        this.type = type;
        if (values == null || values.length == 0) {
            this.values = new Object[0];
        } else {
            this.values = new Object[values.length];
            System.arraycopy(values,0,this.values,0,values.length);
        }
    }

    @Override
    public TupleType getType() {
        return this.type;
    }

    @Override
    public int size() {
        return this.values.length;
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T> T getNthValue(final int i) {
        return (T) this.values[i];
    }

    @Override
    public boolean equals(final Object object) {
        if (object == null) return false;
        if (this == object) return true;

        if (!(object instanceof Tuple)) return false;

        final Tuple other = (Tuple) object;
        if (other.size() != size()) return false;

        final int size = size();
        for (int i = 0; i < size; i++) {
            final Object thisNthValue = getNthValue(i);
            final Object otherNthValue = other.getNthValue(i);
            if ((thisNthValue == null && otherNthValue != null) ||
                    (thisNthValue != null && !thisNthValue.equals(otherNthValue))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 17;
        for (final Object value : this.values) {
            if (value != null) {
                hash = hash * 37 + value.hashCode();
            }
        }
        return hash;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.values);
    }
}
