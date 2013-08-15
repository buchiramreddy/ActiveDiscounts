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

package org.broadleafcommerce.common.web.resource;

import java.util.List;

import org.broadleafcommerce.common.resource.GeneratedResource;

import org.springframework.core.io.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;


/**
 * An abstract GeneratedResourceHandler that is capable of responding to a single specified filename and generate
 * contents for that filename. This abstract parent will handle caching of the generated resource.
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public abstract class AbstractGeneratedResourceHandler {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected Cache generatedResourceCache;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Booelean determining whether or not this handler is able to handle the given request.
   *
   * @param   path  DOCUMENT ME!
   *
   * @return  booelean determining whether or not this handler is able to handle the given request
   */
  public abstract boolean canHandle(String path);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The Resource representing this file.
   *
   * @param   path       DOCUMENT ME!
   * @param   locations  DOCUMENT ME!
   *
   * @return  the Resource representing this file
   */
  public abstract Resource getFileContents(String path, List<Resource> locations);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Whether or not the given cachedResource needs to be regenerated.
   *
   * @param   cachedResource  DOCUMENT ME!
   * @param   path            DOCUMENT ME!
   * @param   locations       DOCUMENT ME!
   *
   * @return  whether or not the given cachedResource needs to be regenerated
   */
  public abstract boolean isCachedResourceExpired(GeneratedResource cachedResource, String path,
    List<Resource> locations);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Attempts to retrive the requested resource from cache. If not cached, generates the resource, caches it, and then
   * returns it
   *
   * @param   path       request
   * @param   locations  DOCUMENT ME!
   *
   * @return  the generated resource
   */
  public Resource getResource(String path, List<Resource> locations) {
    Element  e = getGeneratedResourceCache().get(path);
    Resource r = null;

    boolean shouldGenerate = false;

    if ((e == null) || (e.getObjectValue() == null)) {
      shouldGenerate = true;
    } else if ((e.getObjectValue() instanceof GeneratedResource)
          && isCachedResourceExpired((GeneratedResource) e.getObjectValue(), path, locations)) {
      shouldGenerate = true;
    } else {
      r = (Resource) e.getObjectValue();
    }

    if (shouldGenerate) {
      r = getFileContents(path, locations);
      e = new Element(path, r);
      getGeneratedResourceCache().put(e);
    }

    return r;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected Cache getGeneratedResourceCache() {
    if (generatedResourceCache == null) {
      generatedResourceCache = CacheManager.getInstance().getCache("generatedResourceCache");
    }

    return generatedResourceCache;
  }

} // end class AbstractGeneratedResourceHandler
