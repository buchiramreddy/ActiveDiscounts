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

package org.broadleafcommerce.openadmin.server.dao;

import java.util.HashMap;

import javax.persistence.spi.PersistenceUnitInfo;

import org.hibernate.ejb.Ejb3Configuration;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class EJB3ConfigurationDaoImpl implements EJB3ConfigurationDao {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected PersistenceUnitInfo persistenceUnitInfo;

  private Ejb3Configuration configuration = null;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.EJB3ConfigurationDao#getConfiguration()
   */
  @Override public Ejb3Configuration getConfiguration() {
    synchronized (this) {
      if (configuration == null) {
        Ejb3Configuration temp          = new Ejb3Configuration();
        String            previousValue = persistenceUnitInfo.getProperties().getProperty("hibernate.hbm2ddl.auto");
        persistenceUnitInfo.getProperties().setProperty("hibernate.hbm2ddl.auto", "none");
        configuration = temp.configure(persistenceUnitInfo, new HashMap());
        configuration.getHibernateConfiguration().buildSessionFactory();
        persistenceUnitInfo.getProperties().setProperty("hibernate.hbm2ddl.auto", previousValue);
      }
    }

    return configuration;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PersistenceUnitInfo getPersistenceUnitInfo() {
    return persistenceUnitInfo;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  persistenceUnitInfo  DOCUMENT ME!
   */
  public void setPersistenceUnitInfo(PersistenceUnitInfo persistenceUnitInfo) {
    this.persistenceUnitInfo = persistenceUnitInfo;
  }

} // end class EJB3ConfigurationDaoImpl
