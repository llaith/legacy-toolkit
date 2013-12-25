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
package org.llaith.toolkit.dbadapter.ormlite;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import org.llaith.toolkit.common.exception.ext.UncheckedException;
import org.llaith.toolkit.common.exception.ext.WhoopsException;
import org.llaith.toolkit.common.intention.Maturity;
import org.llaith.toolkit.common.intention.MaturityLevel;
import org.llaith.toolkit.core.pump.Chunk;
import org.llaith.toolkit.core.pump.Sink;

import java.sql.SQLException;

import static org.llaith.toolkit.common.guard.Guard.notNull;

/**
 *
 */
@Maturity(MaturityLevel.BETA)
public class OrmLiteSink<T,ID> implements Sink<T> {

    public enum Mode {
        CREATE, UPDATE, DELETE
    }

    private final Mode mode;
    private final Dao<T,ID> dao;

    public OrmLiteSink(final ConnectionSource source, final Class<T> dtoClass, final Mode mode) {
        this.mode = mode;
        try {
            this.dao =  DaoManager.createDao(notNull(source),notNull(dtoClass));
        } catch (SQLException e) {
            throw new UncheckedException(String.format(
                    "Cannot create an auto-generated DAO for the DTO class: '%s'. Is it configured for ORMLite correctly?",
                    dtoClass.getName()),e);
        }
    }

    @Override
    public void put(final Chunk<T> chunk) {
        try {
            for (T t : chunk) {
                if (Mode.CREATE == mode) dao.create(t);
                else if (Mode.UPDATE == mode) dao.update(t);
                else if (Mode.DELETE == mode) dao.delete(t);
                else throw new WhoopsException("Bad enum: "+mode);
            }
        } catch (SQLException e) {
            throw UncheckedException.wrap(e);
        }
    }

    @Override
    public void close() {
        // nothing to do
    }

}
