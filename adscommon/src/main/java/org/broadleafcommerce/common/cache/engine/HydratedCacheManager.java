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


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public interface HydratedCacheManager {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  cacheRegion      DOCUMENT ME!
   * @param  cacheName        DOCUMENT ME!
   * @param  elementKey       DOCUMENT ME!
   * @param  elementItemName  DOCUMENT ME!
   * @param  elementValue     DOCUMENT ME!
   */
  void addHydratedCacheElementItem(String cacheRegion, String cacheName, Serializable elementKey,
    String elementItemName, Object elementValue);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   cacheRegion      DOCUMENT ME!
   * @param   cacheName        DOCUMENT ME!
   * @param   elementKey       DOCUMENT ME!
   * @param   elementItemName  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Object getHydratedCacheElementItem(String cacheRegion, String cacheName, Serializable elementKey,
    String elementItemName);

} // end interface HydratedCacheManager
