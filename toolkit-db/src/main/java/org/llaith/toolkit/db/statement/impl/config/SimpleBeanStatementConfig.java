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
package org.llaith.toolkit.db.statement.impl.config;

import org.llaith.toolkit.db.statement.StatementConfigurer;
import org.llaith.toolkit.db.statement.util.PsParamProcessor;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 */
public class SimpleBeanStatementConfig implements StatementConfigurer {

    private final String[] properties;
    private final Object object;

    private final PsParamProcessor helper = new PsParamProcessor();

    public SimpleBeanStatementConfig(String[] properties, Object object) {
        this.properties = properties;
        this.object = object;
    }

    @Override
    public void configureStatement(final PreparedStatement preparedStatement) throws SQLException {
        this.helper.fillStatementWithBean(preparedStatement,this.object,this.properties);
    }

}
