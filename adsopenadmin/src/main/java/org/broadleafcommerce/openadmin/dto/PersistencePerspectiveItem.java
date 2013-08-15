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

import org.broadleafcommerce.openadmin.dto.visitor.PersistencePerspectiveItemVisitor;


/**
 * Simple marker interface for persistence perspective members.
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public interface PersistencePerspectiveItem extends Serializable {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  visitor  DOCUMENT ME!
   */
  void accept(PersistencePerspectiveItemVisitor visitor);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PersistencePerspectiveItem clonePersistencePerspectiveItem();

}
