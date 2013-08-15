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

package org.broadleafcommerce.openadmin.server.dao.provider.metadata.request;

import org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao;


/**
 * Contains the requested entity, metadata and support classes.
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class OverrideViaAnnotationRequest {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private final DynamicEntityDao dynamicEntityDao;
  private final Boolean          parentExcluded;
  private final String           prefix;

  private final Class<?> requestedEntity;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new OverrideViaAnnotationRequest object.
   *
   * @param  requestedEntity   DOCUMENT ME!
   * @param  parentExcluded    DOCUMENT ME!
   * @param  dynamicEntityDao  DOCUMENT ME!
   * @param  prefix            DOCUMENT ME!
   */
  public OverrideViaAnnotationRequest(Class<?> requestedEntity, Boolean parentExcluded,
    DynamicEntityDao dynamicEntityDao, String prefix) {
    this.requestedEntity  = requestedEntity;
    this.parentExcluded   = parentExcluded;
    this.dynamicEntityDao = dynamicEntityDao;
    this.prefix           = prefix;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public DynamicEntityDao getDynamicEntityDao() {
    return dynamicEntityDao;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getParentExcluded() {
    return parentExcluded;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getPrefix() {
    return prefix;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Class<?> getRequestedEntity() {
    return requestedEntity;
  }
} // end class OverrideViaAnnotationRequest
