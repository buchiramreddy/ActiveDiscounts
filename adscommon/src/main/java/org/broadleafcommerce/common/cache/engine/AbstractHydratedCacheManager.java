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

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.ehcache.event.CacheEventListener;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public abstract class AbstractHydratedCacheManager implements CacheEventListener, HydratedCacheManager,
  HydratedAnnotationManager {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log LOG = LogFactory.getLog(AbstractHydratedCacheManager.class);

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Map<String, HydrationDescriptor> hydrationDescriptors = Collections.synchronizedMap(new HashMap(100));

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#clone()
   */
  @Override public Object clone() throws CloneNotSupportedException {
    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  net.sf.ehcache.event.CacheEventListener#dispose()
   */
  @Override public void dispose() {
    if (LOG.isInfoEnabled()) {
      LOG.info("Disposing of all hydrated cache members");
    }

    hydrationDescriptors.clear();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.cache.engine.HydratedAnnotationManager#getHydrationDescriptor(java.lang.Object)
   */
  @Override public HydrationDescriptor getHydrationDescriptor(Object entity) {
    if (hydrationDescriptors.containsKey(entity.getClass().getName())) {
      return hydrationDescriptors.get(entity.getClass().getName());
    }

    HydrationDescriptor descriptor     = new HydrationDescriptor();
    Class<?>            topEntityClass = getTopEntityClass(entity);
    HydrationScanner    scanner        = new HydrationScanner(topEntityClass, entity.getClass());
    scanner.init();
    descriptor.setHydratedMutators(scanner.getCacheMutators());

    Map<String, Method[]> mutators = scanner.getIdMutators();

    if (mutators.size() != 1) {
      throw new RuntimeException(
        "Broadleaf Commerce Hydrated Cache currently only supports entities with a single @Id annotation.");
    }

    Method[] singleMutators = mutators.values().iterator().next();
    descriptor.setIdMutators(singleMutators);

    String cacheRegion = scanner.getCacheRegion();

    if ((cacheRegion == null) || "".equals(cacheRegion)) {
      cacheRegion = topEntityClass.getName();
    }

    descriptor.setCacheRegion(cacheRegion);
    hydrationDescriptors.put(entity.getClass().getName(), descriptor);

    return descriptor;
  } // end method getHydrationDescriptor

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   entity  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected Class<?> getTopEntityClass(Object entity) {
    Class<?> myClass    = entity.getClass();
    Class<?> superClass = entity.getClass().getSuperclass();

    while ((superClass != null) && superClass.getName().startsWith("org.broadleaf")) {
      myClass    = superClass;
      superClass = superClass.getSuperclass();
    }

    return myClass;
  }

} // end class AbstractHydratedCacheManager
