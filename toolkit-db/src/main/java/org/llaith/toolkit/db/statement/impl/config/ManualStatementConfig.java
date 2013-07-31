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

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLXML;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 *
 */
public abstract class ManualStatementConfig implements StatementConfigurer {

    public static class StatementParameters {

        protected final PreparedStatement ps;

        public StatementParameters(PreparedStatement ps) {
            this.ps = ps;
        }

        public ParameterMetaData getParameterMetaData() throws SQLException {
            return ps.getParameterMetaData();
        }

        public void clearParameters() throws SQLException {
            ps.clearParameters();
        }

        public void setArray(int parameterIndex, Array x) throws SQLException {
            ps.setArray(parameterIndex,x);
        }

        public void setAsciiStream(int parameterIndex, InputStream x) throws SQLException {
            ps.setAsciiStream(parameterIndex,x);
        }

        public void setAsciiStream(int parameterIndex, InputStream x, int length) throws SQLException {
            ps.setAsciiStream(parameterIndex,x,length);
        }

        public void setAsciiStream(int parameterIndex, InputStream x, long length) throws SQLException {
            ps.setAsciiStream(parameterIndex,x,length);
        }

        public void setBigDecimal(int parameterIndex, BigDecimal x) throws SQLException {
            ps.setBigDecimal(parameterIndex,x);
        }

        public void setBinaryStream(int parameterIndex, InputStream x) throws SQLException {
            ps.setBinaryStream(parameterIndex,x);
        }

        public void setBinaryStream(int parameterIndex, InputStream x, int length) throws SQLException {
            ps.setBinaryStream(parameterIndex,x,length);
        }

        public void setBinaryStream(int parameterIndex, InputStream x, long length) throws SQLException {
            ps.setBinaryStream(parameterIndex,x,length);
        }

        public void setBlob(int parameterIndex, Blob x) throws SQLException {
            ps.setBlob(parameterIndex,x);
        }

        public void setBlob(int parameterIndex, InputStream inputStream) throws SQLException {
            ps.setBlob(parameterIndex,inputStream);
        }

        public void setBlob(int parameterIndex, InputStream inputStream, long length) throws SQLException {
            ps.setBlob(parameterIndex,inputStream,length);
        }

        public void setBoolean(int parameterIndex, boolean x) throws SQLException {
            ps.setBoolean(parameterIndex,x);
        }

        public void setByte(int parameterIndex, byte x) throws SQLException {
            ps.setByte(parameterIndex,x);
        }

        public void setBytes(int parameterIndex, byte[] x) throws SQLException {
            ps.setBytes(parameterIndex,x);
        }

        public void setCharacterStream(int parameterIndex, Reader reader) throws SQLException {
            ps.setCharacterStream(parameterIndex,reader);
        }

        public void setCharacterStream(int parameterIndex, Reader reader, int length) throws SQLException {
            ps.setCharacterStream(parameterIndex,reader,length);
        }

        public void setCharacterStream(int parameterIndex, Reader reader, long length) throws SQLException {
            ps.setCharacterStream(parameterIndex,reader,length);
        }

        public void setClob(int parameterIndex, Clob x) throws SQLException {
            ps.setClob(parameterIndex,x);
        }

        public void setClob(int parameterIndex, Reader reader) throws SQLException {
            ps.setClob(parameterIndex,reader);
        }

        public void setClob(int parameterIndex, Reader reader, long length) throws SQLException {
            ps.setClob(parameterIndex,reader,length);
        }

        public void setDate(int parameterIndex, Date x) throws SQLException {
            ps.setDate(parameterIndex,x);
        }

        public void setDate(int parameterIndex, Date x, Calendar cal) throws SQLException {
            ps.setDate(parameterIndex,x,cal);
        }

        public void setDouble(int parameterIndex, double x) throws SQLException {
            ps.setDouble(parameterIndex,x);
        }

        public void setFloat(int parameterIndex, float x) throws SQLException {
            ps.setFloat(parameterIndex,x);
        }

        public void setInt(int parameterIndex, int x) throws SQLException {
            ps.setInt(parameterIndex,x);
        }

        public void setLong(int parameterIndex, long x) throws SQLException {
            ps.setLong(parameterIndex,x);
        }

        public void setNCharacterStream(int parameterIndex, Reader value) throws SQLException {
            ps.setNCharacterStream(parameterIndex,value);
        }

        public void setNCharacterStream(int parameterIndex, Reader value, long length) throws SQLException {
            ps.setNCharacterStream(parameterIndex,value,length);
        }

        public void setNClob(int parameterIndex, NClob value) throws SQLException {
            ps.setNClob(parameterIndex,value);
        }

        public void setNClob(int parameterIndex, Reader reader) throws SQLException {
            ps.setNClob(parameterIndex,reader);
        }

        public void setNClob(int parameterIndex, Reader reader, long length) throws SQLException {
            ps.setNClob(parameterIndex,reader,length);
        }

        public void setNString(int parameterIndex, String value) throws SQLException {
            ps.setNString(parameterIndex,value);
        }

        public void setNull(int parameterIndex, int sqlType) throws SQLException {
            ps.setNull(parameterIndex,sqlType);
        }

        public void setNull(int parameterIndex, int sqlType, String typeName) throws SQLException {
            ps.setNull(parameterIndex,sqlType,typeName);
        }

        public void setObject(int parameterIndex, Object x) throws SQLException {
            ps.setObject(parameterIndex,x);
        }

        public void setObject(int parameterIndex, Object x, int targetSqlType) throws SQLException {
            ps.setObject(parameterIndex,x,targetSqlType);
        }

        public void setObject(int parameterIndex, Object x, int targetSqlType, int scaleOrLength) throws SQLException {
            ps.setObject(parameterIndex,x,targetSqlType,scaleOrLength);
        }

        public void setRef(int parameterIndex, Ref x) throws SQLException {
            ps.setRef(parameterIndex,x);
        }

        public void setRowId(int parameterIndex, RowId x) throws SQLException {
            ps.setRowId(parameterIndex,x);
        }

        public void setShort(int parameterIndex, short x) throws SQLException {
            ps.setShort(parameterIndex,x);
        }

        public void setString(int parameterIndex, String x) throws SQLException {
            ps.setString(parameterIndex,x);
        }

        public void setSQLXML(int parameterIndex, SQLXML xmlObject) throws SQLException {
            ps.setSQLXML(parameterIndex,xmlObject);
        }

        public void setTime(int parameterIndex, Time x) throws SQLException {
            ps.setTime(parameterIndex,x);
        }

        public void setTime(int parameterIndex, Time x, Calendar cal) throws SQLException {
            ps.setTime(parameterIndex,x,cal);
        }

        public void setTimestamp(int parameterIndex, Timestamp x) throws SQLException {
            ps.setTimestamp(parameterIndex,x);
        }

        public void setTimestamp(int parameterIndex, Timestamp x, Calendar cal) throws SQLException {
            ps.setTimestamp(parameterIndex,x,cal);
        }

        public void setURL(int parameterIndex, URL x) throws SQLException {
            ps.setURL(parameterIndex,x);
        }

    }

    @Override
    public final void configureStatement(final PreparedStatement preparedStatement) throws SQLException {
        this.configureParameters(new StatementParameters(preparedStatement));
    }

    protected abstract void configureParameters(StatementParameters parameters);

}
