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
package org.llaith.toolkit.db.dbmanager.command;

import org.llaith.toolkit.db.dbmanager.DbCommand;
import org.llaith.toolkit.db.dbmanager.DbConnection;
import org.llaith.toolkit.db.dbmanager.TransactionalDbCommand;
import org.llaith.toolkit.db.dbmanager.TransactionalDbConnection;
import org.llaith.toolkit.db.statement.StatementConfigurer;
import org.llaith.toolkit.db.statement.StatementFactory;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 */
public class UpdateCommand implements DbCommand, TransactionalDbCommand {

    private final StatementFactory statementFactory;
    private final StatementConfigurer statementConfigurer;

    private int count = 0;

    public UpdateCommand(final StatementFactory statementFactory) {
        this(statementFactory,StatementConfigurer.NO_PARAMS);
    }

    public UpdateCommand(final StatementFactory statementFactory, final StatementConfigurer statementConfigurer) {
        this.statementFactory = statementFactory;
        this.statementConfigurer = statementConfigurer;
    }

    public int getCount() {
        return count;
    }

    @Override
    public void execute(DbConnection connection) throws SQLException {

        try (final PreparedStatement statement = this.statementFactory.createStatement(connection)) {

            this.statementConfigurer.configureStatement(statement);

            this.count = statement.executeUpdate();

        }

    }

    @Override
    public void executeInTransaction(TransactionalDbConnection connection) throws SQLException {
        this.execute(connection);
    }

}
