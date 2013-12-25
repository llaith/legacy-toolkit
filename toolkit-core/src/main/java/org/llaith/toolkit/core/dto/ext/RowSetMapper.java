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
package org.llaith.toolkit.core.dto.ext;

import com.google.common.base.Supplier;
import org.llaith.toolkit.core.dto.DtoObject;
import org.llaith.toolkit.core.dto.DtoValue;
import org.llaith.toolkit.common.meta.MetadataContainer;
import org.llaith.toolkit.common.guard.Guard;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @param <T>
 */
public class RowSetMapper<T extends DtoObject<T>> {

    public interface ColumnAttribute {
        String columnFrom(MetadataContainer container);
    }

    private final Supplier<T> dtoFactory; // new instances only
    private final ColumnAttribute attribute;

    public RowSetMapper(final Supplier<T> dtoFactory, final ColumnAttribute attribute) {
        this.dtoFactory = Guard.notNull(dtoFactory);
        this.attribute = Guard.notNull(attribute);
    }

    public T nextResult(final CachedRowSet results) throws SQLException {
        final T dto = this.dtoFactory.get();

        for (final DtoValue<?> field : dto.values().values()) {

            final String column = this.attribute.columnFrom(field);

            if (column != null) dto.set(field.id(),this.valueFrom(
                    results,
                    field,
                    column));

        }

        return dto;
    }

    private Object valueFrom(final RowSet results, final DtoValue<?> field, final String column) throws SQLException {
        // Be *very* careful extending this - make sure the wasNull() comes after a *single* getXXX() call.

        Object ret = null;

        if (field.currentValue() instanceof Boolean) {
            ret = results.getBoolean(column);
        } else if (field.currentValue() instanceof Integer) {
            ret = results.getInt(column);
        } else if (field.currentValue() instanceof BigDecimal) {
            ret = results.getBigDecimal(column);
        } else if (field.currentValue() instanceof String) {
            ret = results.getString(column);
        } else if (field.currentValue() instanceof Date) {
            final java.sql.Date d = results.getDate(column);
            if (d != null) return new Date(d.getTime());
            ret = null;
        } else {
            throw new RuntimeException("Unknown result set type: "+field
                    .dtoField()
                    .target()
                    .getRawType()
                    .getName());
        }

        // logic looks weird but getBoolean return false on NULL, and we may want NULL. The Dto may also be
        // using a nullValue of false for null in any case, depends on the use case. We won't loose any
        // information this way.
        if (results.wasNull()) return null;
        else return ret;

    }

}
