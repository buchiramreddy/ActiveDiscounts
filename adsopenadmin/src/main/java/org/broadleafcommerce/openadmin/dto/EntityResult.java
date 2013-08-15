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

package org.broadleafcommerce.openadmin.dto;

import java.io.Serializable;


/**
 * The DynamicEntityDao infrastructure provides a generic representation of an entity in the system. Some utilities and
 * services want both the generic representation and the entity as it was persisted (e.g. the result of the <code>
 * merge</code> call.
 *
 * <p>This object returns both properties.</p>
 *
 * @author   bpolster
 * @see      {@link org.broadleafcommerce.openadmin.dto.Entity}
 * @see      {@link org.broadleafcommerce.openadmin.dto.Property}
 * @version  $Revision$, $Date$
 */
public class EntityResult implements Serializable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Entity entity;
  private Object entityBackingObject;

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
   * @return  DOCUMENT ME!
   */
  public Object getEntityBackingObject() {
    return entityBackingObject;
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

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  entityBackingObject  DOCUMENT ME!
   */
  public void setEntityBackingObject(Object entityBackingObject) {
    this.entityBackingObject = entityBackingObject;
  }
} // end class EntityResult
