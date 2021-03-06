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
/**
 *
 */
package org.broadleafcommerce.cms.url.domain;

import org.broadleafcommerce.cms.url.type.URLRedirectType;


/**
 * A Null instance of a URLHandler. Used by the default URLHandlerServiceImpl implementation to cache misses (e.g. urls
 * that are not being handled by forwards and redirects.
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public class NullURLHandler implements URLHandler, java.io.Serializable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.url.domain.URLHandler#getId()
   */
  @Override public Long getId() {
    return null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.url.domain.URLHandler#getIncomingURL()
   */
  @Override public String getIncomingURL() {
    return null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.url.domain.URLHandler#getNewURL()
   */
  @Override public String getNewURL() {
    return null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.url.domain.URLHandler#getUrlRedirectType()
   */
  @Override public URLRedirectType getUrlRedirectType() {
    return null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.url.domain.URLHandler#setId(java.lang.Long)
   */
  @Override public void setId(Long id) { }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.url.domain.URLHandler#setIncomingURL(java.lang.String)
   */
  @Override public void setIncomingURL(String incomingURL) { }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.url.domain.URLHandler#setNewURL(java.lang.String)
   */
  @Override public void setNewURL(String newURL) { }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.url.domain.URLHandler#setUrlRedirectType(org.broadleafcommerce.cms.url.type.URLRedirectType)
   */
  @Override public void setUrlRedirectType(URLRedirectType redirectType) { }


} // end class NullURLHandler
