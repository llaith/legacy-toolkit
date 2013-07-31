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
package org.llaith.toolkit.db.statement.util;

import org.llaith.toolkit.util.guard.Guard;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 * Ripped from inside AbstractRunner in DBUtils, and a bit refactored.
 *
 */
public class PsParamProcessor {

    private volatile boolean pmdKnownBroken = false;

    public PsParamProcessor() {
        super();
    }

    public PsParamProcessor(boolean pmdKnownBroken) {
        this.pmdKnownBroken = pmdKnownBroken;
    }

    /**
     * Fill the <code>PreparedStatement</code> replacement parameters with the
     * given object's bean property values.
     *
     * @param stmt          PreparedStatement to fill
     * @param bean          A JavaBean object
     * @param propertyNames An ordered array of property names (these should match the
     *                      getters/setters); this gives the order to insert values in the
     *                      statement
     * @throws java.sql.SQLException If a database access error occurs
     */
    public void fillStatementWithBean(final PreparedStatement stmt, final Object bean, final String... propertyNames) throws SQLException {
        try {

            final PropertyDescriptor[] descriptors = Introspector.getBeanInfo(bean.getClass()).getPropertyDescriptors();
            final PropertyDescriptor[] sorted = new PropertyDescriptor[propertyNames.length];

            for (int i = 0; i < propertyNames.length; i++) {

                String propertyName = Guard.expect(propertyNames[i],"propertyName can't be null: " + i);

                boolean found = false;
                for (PropertyDescriptor descriptor : descriptors) {
                    if (propertyName.equals(descriptor.getName())) {
                        sorted[i] = descriptor;
                        found = true;
                        break;
                    }
                }

                if (!found) throw new RuntimeException("Couldn't find bean property: " + bean.getClass() + " " + propertyName);
            }

            this.fillStatementWithBean(stmt,bean,sorted);

        } catch (IntrospectionException e) {
            throw new RuntimeException("Couldn't introspect bean " + bean.getClass().toString(),e);
        }
    }

    /**
     * Fill the <code>PreparedStatement</code> replacement parameters with the
     * given object's bean property values.
     *
     * @param stmt       PreparedStatement to fill
     * @param bean       a JavaBean object
     * @param properties an ordered array of properties; this gives the order to insert
     *                   values in the statement
     * @throws java.sql.SQLException if a database access error occurs
     */
    public void fillStatementWithBean(final PreparedStatement stmt, final Object bean, final PropertyDescriptor[] properties) throws SQLException {

        final Object[] params = new Object[properties.length];

        for (int i = 0; i < properties.length; i++) {

            final PropertyDescriptor property = properties[i];

            Method method = property.getReadMethod();
            if (method == null) throw new RuntimeException("No read method for bean property " + bean.getClass() + " " + property.getName());

            try {
                params[i] = method.invoke(bean);
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException("Couldn't invoke method: " + method,e);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Couldn't invoke method with 0 arguments: " + method,e);
            }
        }

        this.fillStatement(stmt,params);
    }

    /**
     * Fill the <code>PreparedStatement</code> replacement parameters with the
     * given objects.
     *
     * @param stmt   PreparedStatement to fill
     * @param params Query replacement parameters; <code>null</code> is a valid
     *               value to pass in.
     * @throws java.sql.SQLException if a database access error occurs
     */
    public void fillStatement(final PreparedStatement stmt, final Object... params) throws SQLException {

        final ParameterMetaData pmd = this.checkPmdAndReturn(stmt,params);

        if (params != null) {

            for (int i = 0; i < params.length; i++) {
                if (params[i] != null) stmt.setObject(i + 1,params[i]);
                else {
                    // VARCHAR works with many drivers regardless of the actual column type.
                    // Oddly, NULL and OTHER don't work with Oracle's drivers.
                    int sqlType = Types.VARCHAR;
                    if (!pmdKnownBroken) {
                        try {
                            /*
                             * It's not possible for pmdKnownBroken to change from
                             * true to false, (once true, always true) so pmd cannot
                             * be null here.
                             */
                            sqlType = pmd.getParameterType(i + 1);
                        } catch (SQLException e) {
                            pmdKnownBroken = true;
                        }
                    }
                    stmt.setNull(i + 1,sqlType);
                }
            }
        }

    }

    private ParameterMetaData checkPmdAndReturn(final PreparedStatement stmt, final Object... params) throws SQLException {

        ParameterMetaData pmd = null;

        if (!this.pmdKnownBroken) {
            pmd = stmt.getParameterMetaData();
            int stmtCount = pmd.getParameterCount();
            int paramsCount = params == null ? 0 : params.length;

            if (stmtCount != paramsCount) throw new RuntimeException("Wrong number of parameters: expected " + stmtCount + ", was given " + paramsCount);
        }

        return pmd;

    }

}

