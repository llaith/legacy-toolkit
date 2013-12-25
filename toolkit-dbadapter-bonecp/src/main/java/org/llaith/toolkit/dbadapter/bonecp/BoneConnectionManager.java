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
package org.llaith.toolkit.dbadapter.bonecp;

import org.llaith.toolkit.common.exception.ext.UncheckedException;
import org.llaith.toolkit.common.pattern.ResourceLoan;
import org.llaith.toolkit.common.pattern.ResourceManager;
import org.llaith.toolkit.common.pattern.impl.AbstractResourceLoan;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.llaith.toolkit.common.pattern.impl.ResourceLoanReleaseWrapper.wrap;

/**
 *
 */
public class BoneConnectionManager implements ResourceManager<Connection> {

    private final ResourceLoan<? extends DataSource> dataSourceResource;

    public BoneConnectionManager(final ResourceLoan<? extends DataSource> dataSourceResource) {
        this.dataSourceResource = dataSourceResource;
    }

    @Override
    public ResourceLoan<Connection> acquire() {
        try {
            return wrap(new AbstractResourceLoan<Connection>(this.dataSourceResource.target().getConnection()) {
                @Override
                public void releaseTarget() {
                    try {
                        // the real pooling is done by the BoneCP pool.
                        this.target().close();
                    } catch (Throwable e) {
                        throw UncheckedException.wrap(e);
                    }
                }
            });
        } catch (SQLException e) {
            throw UncheckedException.wrap(e);
        }
    }

    @Override
    public void close() {
        try {
            this.dataSourceResource.releaseTarget();
        } catch (Exception e) {
            throw UncheckedException.wrap(e);
        }
    }

}
