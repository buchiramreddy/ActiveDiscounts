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

package org.broadleafcommerce.openadmin.server.service.persistence.module.provider.request;

import org.broadleafcommerce.openadmin.dto.Entity;


/**
 * Contains the {@link org.broadleafcommerce.openadmin.dto.Entity} instance and unfiltered property list.
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class AddFilterPropertiesRequest {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private final Entity entity;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new AddFilterPropertiesRequest object.
   *
   * @param  entity  DOCUMENT ME!
   */
  public AddFilterPropertiesRequest(Entity entity) {
    this.entity = entity;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Entity getEntity() {
    return entity;
  }

} // end class AddFilterPropertiesRequest
