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

package org.broadleafcommerce.common.cache.engine;

import java.lang.reflect.Method;

import java.util.Properties;

import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.event.CacheEventListenerFactory;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class HydratedCacheEventListenerFactory extends CacheEventListenerFactory {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static HydratedCacheManager manager = null;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static HydratedCacheManager getConfiguredManager() {
    return manager;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  net.sf.ehcache.event.CacheEventListenerFactory#createCacheEventListener(java.util.Properties)
   */
  @Override public CacheEventListener createCacheEventListener(Properties props) {
    try {
      if ((props == null) || props.isEmpty()) {
        manager = EhcacheHydratedCacheManagerImpl.getInstance();
      } else {
        String   managerClass = props.getProperty("managerClass");
        Class<?> clazz        = Class.forName(managerClass);
        Method   method       = clazz.getDeclaredMethod("getInstance");
        manager = (HydratedCacheManager) method.invoke(null);
      }
    } catch (Exception e) {
      throw new RuntimeException("Unable to create a CacheEventListener instance", e);
    }

    return (CacheEventListener) manager;
  }
} // end class HydratedCacheEventListenerFactory
