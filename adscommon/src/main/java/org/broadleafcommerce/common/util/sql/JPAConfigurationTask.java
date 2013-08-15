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

package org.broadleafcommerce.common.util.sql;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.spi.PersistenceUnitInfo;

import org.apache.tools.ant.BuildException;

import org.broadleafcommerce.common.extensibility.context.MergeFileSystemAndClassPathXMLApplicationContext;
import org.broadleafcommerce.common.extensibility.jpa.MergePersistenceUnitManager;

import org.hibernate.HibernateException;

import org.hibernate.cfg.Configuration;

import org.hibernate.internal.util.ReflectHelper;

import org.hibernate.tool.ant.ConfigurationTask;

import org.xml.sax.EntityResolver;


/**
 * This is a re-worked version from Hibernate tools.
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class JPAConfigurationTask extends ConfigurationTask {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String dialect;
  private String driverClassName;
  private String password;

  private String persistenceUnit;
  private String url;
  private String userName;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new JPAConfigurationTask object.
   */
  public JPAConfigurationTask() {
    setDescription("JPA Configuration");
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getDialect() {
    return dialect;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getDriverClassName() {
    return driverClassName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getPassword() {
    return password;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getPersistenceUnit() {
    return persistenceUnit;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getUrl() {
    return url;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getUserName() {
    return userName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  dialect  DOCUMENT ME!
   */
  public void setDialect(String dialect) {
    this.dialect = dialect;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  driverClassName  DOCUMENT ME!
   */
  public void setDriverClassName(String driverClassName) {
    this.driverClassName = driverClassName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  password  DOCUMENT ME!
   */
  public void setPassword(String password) {
    this.password = password;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  persistenceUnit  DOCUMENT ME!
   */
  public void setPersistenceUnit(String persistenceUnit) {
    this.persistenceUnit = persistenceUnit;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  url  DOCUMENT ME!
   */
  public void setUrl(String url) {
    this.url = url;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  userName  DOCUMENT ME!
   */
  public void setUserName(String userName) {
    this.userName = userName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   mergeContext  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  BuildException  DOCUMENT ME!
   */
  protected Configuration createConfiguration(MergeFileSystemAndClassPathXMLApplicationContext mergeContext) {
    try {
      PersistenceUnitInfo unitInfo = ((MergePersistenceUnitManager) mergeContext.getBean("blPersistenceUnitManager"))
        .obtainPersistenceUnitInfo(persistenceUnit);

      Map<Object, Object> overrides = new HashMap<Object, Object>();
      Properties          p         = getProperties();

      if (p != null) {
        overrides.putAll(p);
      }

      overrides.put("hibernate.dialect", dialect);

      if ((this.url != null) && !"null".equalsIgnoreCase(this.url)) {
        overrides.put("hibernate.connection.url", this.url);
      }

      if ((this.userName != null) && !"null".equalsIgnoreCase(this.userName)) {
        overrides.put("hibernate.connection.username", this.userName);

        if (this.password == null) {
          // This is for situations like HSQLDB that, by default, use no password
          overrides.put("hibernate.connection.password", "");
        } else if (!"null".equalsIgnoreCase(this.password)) {
          // This allows you to specify a password or the word "null" to not set this property at all
          overrides.put("hibernate.connection.password", this.password);
        }
      }

      if ((this.driverClassName != null) && !"null".equalsIgnoreCase(this.driverClassName)) {
        overrides.put("hibernate.connection.driver_class", this.driverClassName);
      }

      Class<?> clazz   = ReflectHelper.classForName("org.hibernate.ejb.Ejb3Configuration", JPAConfigurationTask.class);
      Object   ejb3cfg = clazz.newInstance();

      if (entityResolver != null) {
        Class<?> resolver = ReflectHelper.classForName(entityResolver, this.getClass());
        Object   object   = resolver.newInstance();
        Method   method   = clazz.getMethod("setEntityResolver", new Class[] { EntityResolver.class });
        method.invoke(ejb3cfg, new Object[] { object });
      }

      Method method = clazz.getMethod("configure", new Class[] { PersistenceUnitInfo.class, Map.class });

      if (method.invoke(ejb3cfg, new Object[] { unitInfo, overrides }) == null) {
        throw new BuildException("Persistence unit not found: '" + persistenceUnit + "'.");
      }

      method = clazz.getMethod("getHibernateConfiguration", new Class[0]);

      return (Configuration) method.invoke(ejb3cfg, (Object[]) null);
    } catch (HibernateException he) {
      throw new BuildException(he);
    } catch (BuildException be) {
      throw be;
    } catch (Exception t) {
      throw new BuildException(
        "Problems in creating a configuration for JPA. Have you remembered to add hibernate EntityManager jars to the classpath ?",
        t);
    } // end try-catch
  } // end method createConfiguration

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.hibernate.tool.ant.ConfigurationTask#doConfiguration(org.hibernate.cfg.Configuration)
   */
  @Override protected void doConfiguration(Configuration configuration) { }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.hibernate.tool.ant.ConfigurationTask#validateParameters()
   */
  @Override protected void validateParameters() throws BuildException { }


} // end class JPAConfigurationTask
