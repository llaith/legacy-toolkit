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
package org.llaith.toolkit.db.dbmanager;

import org.llaith.toolkit.db.connection.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 */
public class DbManager {

    private static final Logger Log = LoggerFactory.getLogger(DbManager.class);

    private final ConnectionManager connectionManager;

    public DbManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public <X extends DbCommand> X execute(X command) throws SQLException {

        try (final Connection connection = this.connectionManager.getConnection()) {

            connection.setAutoCommit(true);

            command.execute(new DbConnection(connection));

            return command;

        }

    }

    public <X extends TransactionalDbCommand> X executeInTransaction(X command) throws SQLException {

        try (final Connection connection = this.connectionManager.getConnection()) {

            try {
                connection.setAutoCommit(false);
                command.executeInTransaction(new TransactionalDbConnection(connection));
                connection.commit();
                connection.setAutoCommit(true);
            } catch (Throwable t) {
                try{
                    connection.rollback();
                    connection.setAutoCommit(true);
                }catch(SQLException e){
                    Log.error("Ignoring exception on rollback.",e);
                }
            }

            return command;

        }
    }

}
