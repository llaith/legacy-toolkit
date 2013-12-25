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
package org.llaith.toolkit.dbadapter.bonecp;

import com.jolbox.bonecp.BoneCPConfig;
import com.jolbox.bonecp.BoneCPDataSource;
import org.llaith.toolkit.common.exception.ext.UncheckedException;
import org.llaith.toolkit.common.pattern.ResourceLoan;
import org.llaith.toolkit.common.pattern.ResourceManager;
import org.llaith.toolkit.common.pattern.impl.NonReturnResourceLoan;

import java.sql.SQLException;

/**
 * Can log statistics from BoneCP via JMX.
 * http://stackoverflow.com/questions/11833215/monitoring-bone-cp-connection-pool
 */
public class BoneDatasourceManager implements ResourceManager<BoneCPDataSource> {

    public static class Config {
        private final String url;
        private final String username;
        private final String password;
        private final int minConnections;
        private final int maxConnections;
        private final boolean trackStatistics;
        private final boolean debugMode;

        public Config(final String url, final String username, final String password, final int minConnections, final int maxConnections, final boolean trackStatistics, final boolean debugMode) {
            this.url = url;
            this.username = username;
            this.password = password;
            this.minConnections = minConnections;
            this.maxConnections = maxConnections;
            this.trackStatistics = trackStatistics;
            this.debugMode = debugMode;
        }
    }

    private final BoneCPDataSource bone;

    public BoneDatasourceManager(final Config config) {
        try {
            this.bone = this.poolFrom(this.configFrom(config));
        } catch (SQLException e) {
            throw UncheckedException.wrap(e);
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
        cfg.setCloseConnectionWatch(config.debugMode);
        // other constants
        cfg.setPartitionCount(1);
        return cfg;
    }

    private BoneCPDataSource poolFrom(final BoneCPConfig config) throws SQLException {
        return this.addShutdownHook(new BoneCPDataSource(config));
    }

    private BoneCPDataSource addShutdownHook(final BoneCPDataSource pool) {
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {

            @Override
            public void run() {
                pool.close();
            }

        }));
        return pool;
    }

    @Override
    public ResourceLoan<BoneCPDataSource> acquire() {
        return new NonReturnResourceLoan<>(this.bone);
    }

    @Override
    public void close() {
        this.bone.close();
    }

}
