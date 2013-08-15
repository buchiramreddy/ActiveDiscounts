/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.openadmin.server.service;

import org.broadleafcommerce.common.exception.ServiceException;

import org.broadleafcommerce.openadmin.dto.Entity;


/**
 * Thrown when an entity fails validation when attempting to populate an actual Hibernate entity based on its DTO
 * representation.
 *
 * @see
 *           
 *           {@link org.broadleafcommerce.openadmin.server.service.persistence.module.RecordHelper#createPopulatedInstance(java.io.Serializable, org.broadleafcommerce.openadmin.dto.Entity, java.util.Map, Boolean)}
 * @author   Phillip Verheyden (phillipuniverse)
 * @version  $Revision$, $Date$
 */
public class ValidationException extends ServiceException {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected Entity entity;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new ValidationException object.
   *
   * @param  entity   DOCUMENT ME!
   * @param  message  DOCUMENT ME!
   */
  public ValidationException(Entity entity, String message) {
    super(message);
    setEntity(entity);
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

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  entity  DOCUMENT ME!
   */
  public void setEntity(Entity entity) {
    this.entity = entity;
  }

} // end class ValidationException
