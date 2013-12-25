/*
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
 */
package org.llaith.toolkit.dbadapter.ormlite;

import com.j256.ormlite.db.DatabaseType;
import com.j256.ormlite.db.DatabaseTypeUtils;
import com.j256.ormlite.jdbc.JdbcDatabaseConnection;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.BaseConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.jolbox.bonecp.BoneCPDataSource;
import org.llaith.toolkit.common.intention.Maturity;
import org.llaith.toolkit.common.intention.MaturityLevel;
import org.llaith.toolkit.common.pattern.ResourceLoan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Adapted from the com.j256.ormlite.db.DataSourceConnectionSource.
 * The idea is to use the same CP for both the OrmLite layer and
 * freeform SQL.
 */
@Maturity(MaturityLevel.BETA)
public class BoneConnectionSource extends BaseConnectionSource implements ConnectionSource {

    private static Logger logger = LoggerFactory.getLogger(BoneConnectionSource.class);

    private final ResourceLoan<BoneCPDataSource> poolResource;

    private final DatabaseType databaseType;

    /**
     * Create a data source wrapper for a DataSource.
     *
     * @throws java.sql.SQLException
     *             If the driver associated with the database URL is not found in the classpath.
     */
    public BoneConnectionSource(final ResourceLoan<BoneCPDataSource> poolResource) throws SQLException {
        this.poolResource = poolResource;
        this.databaseType = initialize();
    }

    private DatabaseType initialize() throws SQLException {
        final String databaseUrl = this.poolResource.target().getJdbcUrl();
        final DatabaseType databaseType = DatabaseTypeUtils.createDatabaseType(databaseUrl);
        databaseType.loadDriver();
        databaseType.setDriver(DriverManager.getDriver(databaseUrl));
        return databaseType;
    }

    @Override
    public DatabaseConnection getReadOnlyConnection() throws SQLException {
        return getReadWriteConnection();
    }

    @Override
    public DatabaseConnection getReadWriteConnection() throws SQLException {
        final DatabaseConnection saved = getSavedConnection();
        if (saved != null) {
            return saved;
        }
        return new JdbcDatabaseConnection(getConnection());
    }

    @Override
    public void releaseConnection(DatabaseConnection connection) throws SQLException {
        if (isSavedConnection(connection)) {
            // ignore the release because we will close it at the end of the connection
        } else {
            connection.close();
        }
    }

    @Override
    public boolean saveSpecialConnection(DatabaseConnection connection) throws SQLException {
		/*
		 * This is fine to not be synchronized since it is only this thread we care about. Other threads will set this
		 * or have it synchronized in over time.
		 */
        return saveSpecial(connection);
    }

    @Override
    public void clearSpecialConnection(DatabaseConnection connection) {
        clearSpecial(connection, logger);
    }

    /**
     * We just let go, we're managing the BoneCP separately.
     * Btw, this is why I hate when existing exceptions are re-used to avoid making your own
     * exception hierarchy. As you can see, with this interface could end up wrapping an pool
     * or datasource and the possible exceptions are more varied than SQLException! As tempting
     * as it us to return an unchecked exception, it's a little harder to guess looking at the
     * interface, so I will keep it like this.
     */
    @Override
    public void close() throws SQLException {
        try {
            this.poolResource.releaseTarget();
        } catch (Exception e) {
            throw new SQLException("Forced wrapping of exception!",e);
        }
    }

    @Override
    public void closeQuietly() {
        try {
            close();
        } catch (SQLException e) {
            // ignored
        }
    }

    @Override
    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    @Override
    public boolean isOpen() {
        return this.poolResource.target() != null;
    }

    /**
     * @deprecated No longer supported and can be removed.
     */
    @Deprecated
    public void setUsesTransactions(boolean usesTransactions) {
        // do nothing
    }

    private Connection getConnection() throws SQLException {
        return this.poolResource.target().getConnection();
    }

}
