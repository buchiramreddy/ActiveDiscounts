/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.openadmin.server.service.persistence.datasource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executor;

import org.apache.commons.pool.impl.GenericObjectPool;

import org.springframework.util.Assert;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class SandBoxConnection implements Connection {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private GenericObjectPool connectionPool;

  private Connection delegate;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new SandBoxConnection object.
   *
   * @param  delegate        DOCUMENT ME!
   * @param  connectionPool  DOCUMENT ME!
   */
  public SandBoxConnection(Connection delegate, GenericObjectPool connectionPool) {
    this.delegate       = delegate;
    this.connectionPool = connectionPool;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   executor  DOCUMENT ME!
   *
   * @throws  SQLException  DOCUMENT ME!
   */
  public void abort(Executor executor) throws SQLException {
    try {
      Class<? extends Connection>   delegateClass = delegate.getClass();
      Class[]                       partypes      = new Class[1];
      partypes[0] = Executor.class;

      Object[] args = new Object[1];
      args[0] = executor;

      Method method = delegateClass.getMethod("abort", partypes);
      method.invoke(delegate, args);
    } catch (SecurityException e) {
      // ignore exceptions
    } catch (NoSuchMethodException e) {
      // ignore exceptions
    } catch (IllegalArgumentException e) {
      // ignore exceptions
    } catch (IllegalAccessException e) {
      // ignore exceptions
    } catch (InvocationTargetException e) {
      // ignore exceptions
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#clearWarnings()
   */
  @Override public void clearWarnings() throws SQLException {
    delegate.clearWarnings();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#close()
   */
  @Override public void close() throws SQLException {
    try {
      connectionPool.returnObject(this);
    } catch (Exception e) {
      throw new SQLException(e);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#commit()
   */
  @Override public void commit() throws SQLException {
    delegate.commit();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#createArrayOf(java.lang.String, java.lang.Object[])
   */
  @Override public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
    return delegate.createArrayOf(typeName, elements);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#createBlob()
   */
  @Override public Blob createBlob() throws SQLException {
    return delegate.createBlob();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#createClob()
   */
  @Override public Clob createClob() throws SQLException {
    return delegate.createClob();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#createNClob()
   */
  @Override public NClob createNClob() throws SQLException {
    return delegate.createNClob();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#createSQLXML()
   */
  @Override public SQLXML createSQLXML() throws SQLException {
    return delegate.createSQLXML();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#createStatement()
   */
  @Override public Statement createStatement() throws SQLException {
    return delegate.createStatement();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#createStatement(int, int)
   */
  @Override public Statement createStatement(int resultSetType, int resultSetConcurrency) throws SQLException {
    return delegate.createStatement(resultSetType, resultSetConcurrency);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#createStatement(int, int, int)
   */
  @Override public Statement createStatement(int resultSetType,
    int resultSetConcurrency, int resultSetHoldability) throws SQLException {
    return delegate.createStatement(resultSetType, resultSetConcurrency,
        resultSetHoldability);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#createStruct(java.lang.String, java.lang.Object[])
   */
  @Override public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
    return delegate.createStruct(typeName, attributes);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#getAutoCommit()
   */
  @Override public boolean getAutoCommit() throws SQLException {
    return delegate.getAutoCommit();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#getCatalog()
   */
  @Override public String getCatalog() throws SQLException {
    return delegate.getCatalog();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#getClientInfo()
   */
  @Override public Properties getClientInfo() throws SQLException {
    return delegate.getClientInfo();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#getClientInfo(java.lang.String)
   */
  @Override public String getClientInfo(String name) throws SQLException {
    return delegate.getClientInfo(name);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#getHoldability()
   */
  @Override public int getHoldability() throws SQLException {
    return delegate.getHoldability();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#getMetaData()
   */
  @Override public DatabaseMetaData getMetaData() throws SQLException {
    return delegate.getMetaData();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  SQLException  DOCUMENT ME!
   */
  public int getNetworkTimeout() throws SQLException {
    int returnValue = 0;

    try {
      Class<? extends Connection> delegateClass = delegate.getClass();
      Method                      method        = delegateClass.getMethod("getNetworkTimeout");
      returnValue = Integer.parseInt(method.invoke(delegate).toString());
    } catch (SecurityException e) {
      // ignore exceptions
    } catch (NoSuchMethodException e) {
      // ignore exceptions
    } catch (IllegalArgumentException e) {
      // ignore exceptions
    } catch (IllegalAccessException e) {
      // ignore exceptions
    } catch (InvocationTargetException e) {
      // ignore exceptions
    }

    return returnValue;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  SQLException  DOCUMENT ME!
   */
  public String getSchema() throws SQLException {
    String returnValue = null;

    try {
      Class<? extends Connection> delegateClass = delegate.getClass();
      Method                      method        = delegateClass.getMethod("getSchema");
      returnValue = method.invoke(delegate).toString();
    } catch (SecurityException e) {
      // ignore exceptions
    } catch (NoSuchMethodException e) {
      // ignore exceptions
    } catch (IllegalArgumentException e) {
      // ignore exceptions
    } catch (IllegalAccessException e) {
      // ignore exceptions
    } catch (InvocationTargetException e) {
      // ignore exceptions
    }

    return returnValue;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#getTransactionIsolation()
   */
  @Override public int getTransactionIsolation() throws SQLException {
    return delegate.getTransactionIsolation();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#getTypeMap()
   */
  @Override public Map<String, Class<?>> getTypeMap() throws SQLException {
    return delegate.getTypeMap();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#getWarnings()
   */
  @Override public SQLWarning getWarnings() throws SQLException {
    return delegate.getWarnings();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#isClosed()
   */
  @Override public boolean isClosed() throws SQLException {
    return delegate.isClosed();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#isReadOnly()
   */
  @Override public boolean isReadOnly() throws SQLException {
    return delegate.isReadOnly();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#isValid(int)
   */
  @Override public boolean isValid(int timeout) throws SQLException {
    return delegate.isValid(timeout);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Wrapper#isWrapperFor(java.lang.Class)
   */
  @Override public boolean isWrapperFor(Class<?> iface) throws SQLException {
    return Connection.class.equals(iface);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#nativeSQL(java.lang.String)
   */
  @Override public String nativeSQL(String sql) throws SQLException {
    return delegate.nativeSQL(sql);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#prepareCall(java.lang.String)
   */
  @Override public CallableStatement prepareCall(String sql) throws SQLException {
    return delegate.prepareCall(sql);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#prepareCall(java.lang.String, int, int)
   */
  @Override public CallableStatement prepareCall(String sql, int resultSetType,
    int resultSetConcurrency) throws SQLException {
    return delegate.prepareCall(sql, resultSetType, resultSetConcurrency);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#prepareCall(java.lang.String, int, int, int)
   */
  @Override public CallableStatement prepareCall(String sql, int resultSetType,
    int resultSetConcurrency, int resultSetHoldability) throws SQLException {
    return delegate.prepareCall(sql, resultSetType, resultSetConcurrency,
        resultSetHoldability);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#prepareStatement(java.lang.String)
   */
  @Override public PreparedStatement prepareStatement(String sql) throws SQLException {
    return delegate.prepareStatement(sql);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#prepareStatement(java.lang.String, int)
   */
  @Override public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) throws SQLException {
    return delegate.prepareStatement(sql, autoGeneratedKeys);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#prepareStatement(java.lang.String, int[])
   */
  @Override public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException {
    return delegate.prepareStatement(sql, columnIndexes);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#prepareStatement(java.lang.String, java.lang.String[])
   */
  @Override public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException {
    return delegate.prepareStatement(sql, columnNames);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#prepareStatement(java.lang.String, int, int)
   */
  @Override public PreparedStatement prepareStatement(String sql, int resultSetType,
    int resultSetConcurrency) throws SQLException {
    return delegate.prepareStatement(sql, resultSetType,
        resultSetConcurrency);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#prepareStatement(java.lang.String, int, int, int)
   */
  @Override public PreparedStatement prepareStatement(String sql, int resultSetType,
    int resultSetConcurrency, int resultSetHoldability) throws SQLException {
    return delegate.prepareStatement(sql, resultSetType,
        resultSetConcurrency, resultSetHoldability);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#releaseSavepoint(java.sql.Savepoint)
   */
  @Override public void releaseSavepoint(Savepoint savepoint) throws SQLException {
    delegate.releaseSavepoint(savepoint);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#rollback()
   */
  @Override public void rollback() throws SQLException {
    delegate.rollback();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#rollback(java.sql.Savepoint)
   */
  @Override public void rollback(Savepoint savepoint) throws SQLException {
    delegate.rollback(savepoint);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#setAutoCommit(boolean)
   */
  @Override public void setAutoCommit(boolean autoCommit) throws SQLException {
    delegate.setAutoCommit(autoCommit);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#setCatalog(java.lang.String)
   */
  @Override public void setCatalog(String catalog) throws SQLException {
    delegate.setCatalog(catalog);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#setClientInfo(java.util.Properties)
   */
  @Override public void setClientInfo(Properties properties) throws SQLClientInfoException {
    delegate.setClientInfo(properties);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#setClientInfo(java.lang.String, java.lang.String)
   */
  @Override public void setClientInfo(String name, String value) throws SQLClientInfoException {
    delegate.setClientInfo(name, value);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#setHoldability(int)
   */
  @Override public void setHoldability(int holdability) throws SQLException {
    delegate.setHoldability(holdability);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   executor      DOCUMENT ME!
   * @param   milliseconds  DOCUMENT ME!
   *
   * @throws  SQLException  DOCUMENT ME!
   */
  public void setNetworkTimeout(Executor executor, int milliseconds) throws SQLException {
    try {
      Class<? extends Connection>   delegateClass = delegate.getClass();
      Class[]                       partypes      = new Class[2];
      partypes[0] = Executor.class;
      partypes[1] = int.class;

      Object[] args = new Object[2];
      args[0] = executor;
      args[1] = milliseconds;

      Method method = delegateClass.getMethod("setNetworkTimeout", partypes);
      method.invoke(delegate, args);
    } catch (SecurityException e) {
      // ignore exceptions
    } catch (NoSuchMethodException e) {
      // ignore exceptions
    } catch (IllegalArgumentException e) {
      // ignore exceptions
    } catch (IllegalAccessException e) {
      // ignore exceptions
    } catch (InvocationTargetException e) {
      // ignore exceptions
    }

  } // end method setNetworkTimeout

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#setReadOnly(boolean)
   */
  @Override public void setReadOnly(boolean readOnly) throws SQLException {
    delegate.setReadOnly(readOnly);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#setSavepoint()
   */
  @Override public Savepoint setSavepoint() throws SQLException {
    return delegate.setSavepoint();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#setSavepoint(java.lang.String)
   */
  @Override public Savepoint setSavepoint(String name) throws SQLException {
    return delegate.setSavepoint(name);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   schema  DOCUMENT ME!
   *
   * @throws  SQLException  DOCUMENT ME!
   */
  public void setSchema(String schema) throws SQLException {
    try {
      Class<? extends Connection>   delegateClass = delegate.getClass();
      Class[]                       partypes      = new Class[1];
      partypes[0] = String.class;

      Object[] args = new Object[1];
      args[0] = schema;

      Method method;
      method = delegateClass.getMethod("setSchema", partypes);
      method.invoke(delegate, args);
    } catch (SecurityException e) {
      // ignore exceptions
    } catch (NoSuchMethodException e) {
      // ignore exceptions
    } catch (IllegalArgumentException e) {
      // ignore exceptions
    } catch (IllegalAccessException e) {
      // ignore exceptions
    } catch (InvocationTargetException e) {
      // ignore exceptions
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#setTransactionIsolation(int)
   */
  @Override public void setTransactionIsolation(int level) throws SQLException {
    delegate.setTransactionIsolation(level);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Connection#setTypeMap(java.util.Map)
   */
  @Override public void setTypeMap(Map<String, Class<?>> map) throws SQLException {
    delegate.setTypeMap(map);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.sql.Wrapper#unwrap(java.lang.Class)
   */
  @Override public <T> T unwrap(Class<T> iface) throws SQLException {
    Assert.notNull(iface, "Interface argument must not be null");

    if (!Connection.class.equals(iface)) {
      throw new SQLException("Connection of type [" + getClass().getName()
        + "] can only be unwrapped as [java.sql.Connection], not as [" + iface.getName());
    }

    return (T) delegate;
  }
} // end class SandBoxConnection
