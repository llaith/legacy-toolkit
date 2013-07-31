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
package org.llaith.toolkit.db.named;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 *
 */
public class PreparedStatementNamedParamBuilder {

    private final PreparedStatement statement;
    private final NamedParamIndexer namedParamIndexer;

    public PreparedStatementNamedParamBuilder(final PreparedStatement statement, final NamedParamIndexer namedParamIndexer) {
        this.statement = statement;
        this.namedParamIndexer = namedParamIndexer;
    }


    /**
     * Sets a parameter.
     *
     * @param name  parameter name
     * @param value parameter value
     * @throws java.sql.SQLException             if an error occurred
     * @throws IllegalArgumentException if the parameter does not exist
     * @see java.sql.PreparedStatement#setObject(int,Object)
     */
    public void setObject(String name, Object value) throws SQLException {
        int[] indexes = this.namedParamIndexer.getIndexes(name);
        for (int i : indexes) {
            statement.setObject(i,value);
        }
    }


    /**
     * Sets a parameter.
     *
     * @param name  parameter name
     * @param value parameter value
     * @throws java.sql.SQLException             if an error occurred
     * @throws IllegalArgumentException if the parameter does not exist
     * @see java.sql.PreparedStatement#setString(int,String)
     */
    public void setString(String name, String value) throws SQLException {
        int[] indexes = this.namedParamIndexer.getIndexes(name);
        for (int i : indexes) {
            statement.setString(i,value);
        }
    }


    /**
     * Sets a parameter.
     *
     * @param name  parameter name
     * @param value parameter value
     * @throws java.sql.SQLException             if an error occurred
     * @throws IllegalArgumentException if the parameter does not exist
     * @see java.sql.PreparedStatement#setInt(int,int)
     */
    public void setInt(String name, int value) throws SQLException {
        int[] indexes = this.namedParamIndexer.getIndexes(name);
        for (int i : indexes) {
            statement.setInt(i,value);
        }
    }


    /**
     * Sets a parameter.
     *
     * @param name  parameter name
     * @param value parameter value
     * @throws java.sql.SQLException             if an error occurred
     * @throws IllegalArgumentException if the parameter does not exist
     * @see java.sql.PreparedStatement#setInt(int,int)
     */
    public void setLong(String name, long value) throws SQLException {
        int[] indexes = this.namedParamIndexer.getIndexes(name);
        for (int i : indexes) {
            statement.setLong(i,value);
        }
    }


    /**
     * Sets a parameter.
     *
     * @param name  parameter name
     * @param value parameter value
     * @throws java.sql.SQLException             if an error occurred
     * @throws IllegalArgumentException if the parameter does not exist
     * @see java.sql.PreparedStatement#setTimestamp(int,java.sql.Timestamp)
     */
    public void setTimestamp(String name, Timestamp value) throws SQLException {
        int[] indexes = this.namedParamIndexer.getIndexes(name);
        for (int i : indexes) {
            statement.setTimestamp(i,value);
        }
    }

}
