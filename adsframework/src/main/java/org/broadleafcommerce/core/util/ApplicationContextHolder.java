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

package org.broadleafcommerce.core.util;

import org.springframework.beans.BeansException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class ApplicationContextHolder implements ApplicationContextAware {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static ApplicationContext context;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static ApplicationContext getApplicationContext() {
    return context;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
   */
  @Override public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    context = applicationContext;
  }

} // end class ApplicationContextHolder
