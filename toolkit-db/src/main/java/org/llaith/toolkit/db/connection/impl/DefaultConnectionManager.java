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
package org.llaith.toolkit.db.connection.impl;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.Statistics;
import org.llaith.toolkit.db.connection.ConnectionManager;
import org.llaith.toolkit.exception.base.WrappedException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class DefaultConnectionManager implements ConnectionManager {

    public static class Config {
        private final String url;
        private final String username;
        private final String password;
        private final int minConnections;
        private final int maxConnections;
        private final boolean trackStatistics;
        private final boolean debugMode;
        public Config(String url, String username, String password, int minConnections, int maxConnections, boolean trackStatistics, boolean debugMode) {
            this.url = url;
            this.username = username;
            this.password = password;
            this.minConnections = minConnections;
            this.maxConnections = maxConnections;
            this.trackStatistics = trackStatistics;
            this.debugMode = debugMode;
        }
    }

    private BoneCP pool;

    public DefaultConnectionManager(final Config config) {
        try {
            this.pool = poolFrom(configFrom(config));
        } catch (SQLException e) {
            throw WrappedException.wrap(e);
        }
    }

    private BoneCPConfig configFrom(final Config config) {
        final BoneCPConfig cfg = new BoneCPConfig();
        // user params
        cfg.setJdbcUrl(config.url);
        if (config.username != null) cfg.setUsername(config.username);
        if (config.password != null) cfg.setPassword(config.password);
        cfg.setMinConnectionsPerPartition(config.minConnections);
        cfg.setMaxConnectionsPerPartition(config.maxConnections);
        cfg.setStatisticsEnabled(config.trackStatistics);
        cfg.setLogStatementsEnabled(config.debugMode);
        // other constants
        cfg.setPartitionCount(1);
        return cfg;
    }

    private BoneCP poolFrom(final BoneCPConfig config) throws SQLException {
        return addShutdownHook(new BoneCP(config));
    }

    private BoneCP addShutdownHook(final BoneCP pool) {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

            @Override
            public void run() {
                pool.shutdown();
            }

        }));
        return pool;
    }

    @Override
    public Connection getConnection() {
        try {
            return this.pool.getConnection();
        } catch (SQLException e) {
            throw WrappedException.wrap(e);
        }
    }

    @Override
    public void close() {
        this.pool.close();
    }


    public Map<String,String> getStatistics() {
        final Map<String,String> stats = new HashMap<>();

        final Statistics boneStats = this.pool.getStatistics();

        stats.put("connections total",""+boneStats.getTotalFree()+boneStats.getTotalLeased());
        stats.put("connections free",""+boneStats.getTotalFree());
        stats.put("connections used",""+boneStats.getTotalLeased());
        stats.put("connections requested","" + boneStats.getConnectionsRequested());
        stats.put("connection cache hit ratio","" + boneStats.getCacheHitRatio());

        stats.put("total connection wait time",""+boneStats.getCumulativeConnectionWaitTime());
        stats.put("total statement prepare time","" + boneStats.getCumulativeStatementPrepareTime());
        stats.put("total statement execute time","" + boneStats.getCumulativeStatementExecutionTime());

        stats.put("average connection wait time","" + boneStats.getConnectionWaitTimeAvg());
        stats.put("average statement prepare time",""+boneStats.getStatementPrepareTimeAvg());
        stats.put("average statement execute time",""+boneStats.getStatementExecuteTimeAvg());

        return stats;
    }

}
