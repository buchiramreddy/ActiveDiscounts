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

package org.broadleafcommerce.openadmin.server.service.handler;

import java.util.ArrayList;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class DefaultCustomPersistenceHandlerFilter implements CustomPersistenceHandlerFilter {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected List<String> filterCustomPersistenceHandlerClassnames = new ArrayList<String>();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<String> getFilterCustomPersistenceHandlerClassnames() {
    return filterCustomPersistenceHandlerClassnames;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  filterCustomPersistenceHandlerClassnames  DOCUMENT ME!
   */
  public void setFilterCustomPersistenceHandlerClassnames(List<String> filterCustomPersistenceHandlerClassnames) {
    this.filterCustomPersistenceHandlerClassnames = filterCustomPersistenceHandlerClassnames;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandlerFilter#shouldUseHandler(java.lang.String)
   */
  @Override public boolean shouldUseHandler(String handlerClassName) {
    return !filterCustomPersistenceHandlerClassnames.contains(handlerClassName);
  }
} // end class DefaultCustomPersistenceHandlerFilter
