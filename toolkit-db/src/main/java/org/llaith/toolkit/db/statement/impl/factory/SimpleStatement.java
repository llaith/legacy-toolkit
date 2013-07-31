/*
 * Copyright (c) 2013 Nos Doughty
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
package org.llaith.toolkit.db.statement.impl.factory;

import org.llaith.toolkit.db.dbmanager.DbConnection;
import org.llaith.toolkit.db.statement.StatementFactory;
import org.llaith.toolkit.util.guard.Guard;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 */
public class SimpleStatement implements StatementFactory {

    private final String sql;

    public SimpleStatement(final String sql) {
        this.sql = Guard.expectArg(sql,"Cannot pass a null sql param.");
    }


    @Override
    public PreparedStatement createStatement(DbConnection connection) throws SQLException {
        return connection.prepareStatement(this.sql);
    }

}
