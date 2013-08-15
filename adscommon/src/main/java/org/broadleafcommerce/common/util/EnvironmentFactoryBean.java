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

package org.broadleafcommerce.common.util;

import org.springframework.beans.factory.FactoryBean;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class EnvironmentFactoryBean implements FactoryBean {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String className;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new EnvironmentFactoryBean object.
   *
   * @param  className  DOCUMENT ME!
   */
  public EnvironmentFactoryBean(String className) {
    this.className = className;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.beans.factory.FactoryBean#getObject()
   */
  @Override public Object getObject() throws Exception {
    return Class.forName(className).newInstance();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.beans.factory.FactoryBean#getObjectType()
   */
  @Override
  @SuppressWarnings("unchecked")
  public Class getObjectType() {
    try {
      return Class.forName(className);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.beans.factory.FactoryBean#isSingleton()
   */
  @Override public boolean isSingleton() {
    return false;
  }

} // end class EnvironmentFactoryBean
