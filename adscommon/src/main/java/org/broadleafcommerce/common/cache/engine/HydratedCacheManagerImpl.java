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

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.Hashtable;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.hibernate.cache.spi.CacheKey;

import net.sf.ehcache.CacheException;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.event.CacheEventListener;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
@Deprecated public class HydratedCacheManagerImpl implements CacheEventListener, HydratedCacheManager,
  HydratedAnnotationManager {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log                      LOG     = LogFactory.getLog(HydratedCacheManagerImpl.class);
  private static final HydratedCacheManagerImpl MANAGER = new HydratedCacheManagerImpl();

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Map<String, HydratedCache>       hydratedCacheContainer = new Hashtable<String, HydratedCache>(100);
  private Map<String, HydrationDescriptor> hydrationDescriptors   = new Hashtable<String, HydrationDescriptor>(100);

  //~ Constructors -----------------------------------------------------------------------------------------------------

  private HydratedCacheManagerImpl() { }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static HydratedCacheManagerImpl getInstance() {
    return MANAGER;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  cache  DOCUMENT ME!
   */
  public void addHydratedCache(final HydratedCache cache) {
    hydratedCacheContainer.put(cache.getCacheRegion() + "_" + cache.getCacheName(), cache);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.cache.engine.HydratedCacheManager#addHydratedCacheElementItem(java.lang.String,java.lang.String,
   *       java.io.Serializable, java.lang.String, java.lang.Object)
   */
  @Override public void addHydratedCacheElementItem(String cacheRegion, String cacheName, Serializable elementKey,
    String elementItemName, Object elementValue) {
    HydratedCache        hydratedCache = getHydratedCache(cacheRegion, cacheName);
    HydratedCacheElement element       = hydratedCache.getCacheElement(cacheRegion, cacheName, elementKey);

    if (element == null) {
      element = new HydratedCacheElement();
      hydratedCache.addCacheElement(cacheRegion, cacheName, elementKey, element);
    }

    element.putCacheElementItem(elementItemName, elementKey, elementValue);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#clone()
   */
  @Override public Object clone() throws CloneNotSupportedException {
    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   cacheRegion  DOCUMENT ME!
   * @param   cacheName    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean containsCache(String cacheRegion, String cacheName) {
    return hydratedCacheContainer.containsKey(cacheRegion + "_" + cacheName);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  net.sf.ehcache.event.CacheEventListener#dispose()
   */
  @Override public void dispose() {
    if (LOG.isInfoEnabled()) {
      LOG.info("Disposing of all hydrated cache members");
    }

    hydratedCacheContainer.clear();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   cacheRegion  DOCUMENT ME!
   * @param   cacheName    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public HydratedCache getHydratedCache(final String cacheRegion, final String cacheName) {
    if (!containsCache(cacheRegion, cacheName)) {
      HydratedCache cache = new HydratedCache(cacheRegion, cacheName);
      addHydratedCache(cache);
    }

    return hydratedCacheContainer.get(cacheRegion + "_" + cacheName);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.cache.engine.HydratedCacheManager#getHydratedCacheElementItem(java.lang.String,java.lang.String,
   *       java.io.Serializable, java.lang.String)
   */
  @Override public Object getHydratedCacheElementItem(String cacheRegion, String cacheName, Serializable elementKey,
    String elementItemName) {
    Object               response      = null;
    HydratedCache        hydratedCache = getHydratedCache(cacheRegion, cacheName);
    HydratedCacheElement element       = hydratedCache.getCacheElement(cacheRegion, cacheName, elementKey);

    if (element != null) {
      response = element.getCacheElementItem(elementItemName, elementKey);
    }

    return response;
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
  public Class<?> getTopEntityClass(Object entity) {
    Class<?> myClass    = entity.getClass();
    Class<?> superClass = entity.getClass().getSuperclass();

    while ((superClass != null) && superClass.getName().startsWith("org.broadleaf")) {
      myClass    = superClass;
      superClass = superClass.getSuperclass();
    }

    return myClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  net.sf.ehcache.event.CacheEventListener#notifyElementEvicted(net.sf.ehcache.Ehcache, net.sf.ehcache.Element)
   */
  @Override public void notifyElementEvicted(Ehcache arg0, Element arg1) {
    removeCache(arg0.getName(), arg1.getKey());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  net.sf.ehcache.event.CacheEventListener#notifyElementExpired(net.sf.ehcache.Ehcache, net.sf.ehcache.Element)
   */
  @Override public void notifyElementExpired(Ehcache arg0, Element arg1) {
    removeCache(arg0.getName(), arg1.getKey());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  net.sf.ehcache.event.CacheEventListener#notifyElementPut(net.sf.ehcache.Ehcache, net.sf.ehcache.Element)
   */
  @Override public void notifyElementPut(Ehcache arg0, Element arg1) throws CacheException {
    // do nothing
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  net.sf.ehcache.event.CacheEventListener#notifyElementRemoved(net.sf.ehcache.Ehcache, net.sf.ehcache.Element)
   */
  @Override public void notifyElementRemoved(Ehcache arg0, Element arg1) throws CacheException {
    removeCache(arg0.getName(), arg1.getKey());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  net.sf.ehcache.event.CacheEventListener#notifyElementUpdated(net.sf.ehcache.Ehcache, net.sf.ehcache.Element)
   */
  @Override public void notifyElementUpdated(Ehcache arg0, Element arg1) throws CacheException {
    removeCache(arg0.getName(), arg1.getKey());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  net.sf.ehcache.event.CacheEventListener#notifyRemoveAll(net.sf.ehcache.Ehcache)
   */
  @Override public void notifyRemoveAll(Ehcache arg0) {
    removeAll(arg0.getName());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   cacheRegion  DOCUMENT ME!
   * @param   cacheName    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public HydratedCache removeHydratedCache(final String cacheRegion, final String cacheName) {
    return hydratedCacheContainer.remove(cacheRegion + "_" + cacheName);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  private void removeAll(String cacheName) {
    if (hydratedCacheContainer.containsKey(cacheName)) {
      if (LOG.isInfoEnabled()) {
        LOG.info("Clearing all hydrated caches for cache name: " + cacheName);
      }

      hydratedCacheContainer.remove(cacheName);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  private void removeCache(String cacheRegion, Serializable key) {
    String cacheName = cacheRegion;

    if (key instanceof CacheKey) {
      cacheName = ((CacheKey) key).getEntityOrRoleName();
      key       = ((CacheKey) key).getKey();
    }

    if (containsCache(cacheRegion, cacheName)) {
      HydratedCache cache = hydratedCacheContainer.get(cacheRegion + "_" + cacheName);
      String        myKey = cacheRegion + "_" + cacheName + "_" + key;

      if (cache.containsKey(myKey)) {
        if (LOG.isInfoEnabled()) {
          LOG.info("Clearing hydrated cache for cache name: " + cacheRegion + "_" + cacheName + "_" + key);
        }

        cache.removeCacheElement(cacheRegion, cacheName, key);
      }
    }
  }

} // end class HydratedCacheManagerImpl
