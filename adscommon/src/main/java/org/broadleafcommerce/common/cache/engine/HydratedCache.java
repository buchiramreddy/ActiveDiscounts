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

import java.util.Hashtable;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
@Deprecated public class HydratedCache extends Hashtable<String, Object> {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String cacheName;
  private String cacheRegion;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new HydratedCache object.
   *
   * @param  cacheRegion  DOCUMENT ME!
   * @param  cacheName    DOCUMENT ME!
   */
  public HydratedCache(String cacheRegion, String cacheName) {
    this.cacheRegion = cacheRegion;
    this.cacheName   = cacheName;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  cacheRegion  DOCUMENT ME!
   * @param  cacheName    DOCUMENT ME!
   * @param  key          DOCUMENT ME!
   * @param  value        DOCUMENT ME!
   */
  public void addCacheElement(String cacheRegion, String cacheName, Serializable key, Object value) {
    String myKey = cacheRegion + "_" + cacheName + "_" + key;
    put(myKey, value);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   cacheRegion  DOCUMENT ME!
   * @param   cacheName    DOCUMENT ME!
   * @param   key          DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public HydratedCacheElement getCacheElement(String cacheRegion, String cacheName, Serializable key) {
    return (HydratedCacheElement) get(cacheRegion + "_" + cacheName + "_" + key);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getCacheName() {
    return cacheName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getCacheRegion() {
    return cacheRegion;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   cacheRegion  DOCUMENT ME!
   * @param   cacheName    DOCUMENT ME!
   * @param   key          DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public HydratedCacheElement removeCacheElement(String cacheRegion, String cacheName, Serializable key) {
    String myKey = cacheRegion + "_" + cacheName + "_" + key;

    return (HydratedCacheElement) remove(myKey);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  cacheRegion  DOCUMENT ME!
   */
  public void setCacheRegion(String cacheRegion) {
    this.cacheRegion = cacheRegion;
  }
} // end class HydratedCache
