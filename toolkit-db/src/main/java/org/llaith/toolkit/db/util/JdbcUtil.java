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
package org.llaith.toolkit.db.util;

import java.sql.SQLException;
import java.util.Arrays;

/**
 *
 */
public class JdbcUtil {

    /**
     * Cannot be instantiated
     */
    private JdbcUtil() {
        super();
    }

    /**
     *
     * This one comes from the AbstractQueryRunner class.
     *
     * Throws a new exception with a more informative error message.
     *
     * @param cause  The original exception that will be chained to the new
     *               exception when it's rethrown.
     * @param sql    The query that was executing when the exception happened.
     * @param params The query replacement parameters; <code>null</code> is a valid
     *               value to pass in.
     * @throws java.sql.SQLException if a database access error occurs
     */
    public static void rethrow(SQLException cause, String sql, Object... params) throws SQLException {

        final String msg =
                (cause.getMessage() != null ? cause.getMessage() : "") +
                        " Query: " +
                        sql +
                        " Parameters: " +
                        (params != null ? Arrays.deepToString(params) : "[]");

        SQLException e = new SQLException(msg,cause.getSQLState(),cause.getErrorCode());
        e.setNextException(cause);

        throw e;

    }

}

