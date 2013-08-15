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

package org.broadleafcommerce.common.jmx;

import org.springframework.beans.factory.FactoryBean;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class ExplicitNameFactoryBean implements FactoryBean {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private final String name;
  private final String suffix;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new ExplicitNameFactoryBean object.
   *
   * @param  name    DOCUMENT ME!
   * @param  suffix  DOCUMENT ME!
   */
  public ExplicitNameFactoryBean(String name, String suffix) {
    this.name   = name;
    this.suffix = suffix;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.beans.factory.FactoryBean#getObject()
   */
  @Override public Object getObject() throws Exception {
    return name + "-" + suffix;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.beans.factory.FactoryBean#getObjectType()
   */
  @Override
  @SuppressWarnings("unchecked")
  public Class getObjectType() {
    return String.class;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.beans.factory.FactoryBean#isSingleton()
   */
  @Override public boolean isSingleton() {
    return false;
  }

} // end class ExplicitNameFactoryBean
