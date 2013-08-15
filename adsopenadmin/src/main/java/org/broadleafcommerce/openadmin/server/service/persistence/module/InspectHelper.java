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

package org.broadleafcommerce.openadmin.server.service.persistence.module;

import java.util.Map;

import org.broadleafcommerce.common.presentation.client.OperationType;

import org.broadleafcommerce.openadmin.dto.ClassMetadata;
import org.broadleafcommerce.openadmin.dto.FieldMetadata;
import org.broadleafcommerce.openadmin.dto.MergedPropertyType;
import org.broadleafcommerce.openadmin.dto.PersistencePerspective;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public interface InspectHelper {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   operationType  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PersistenceModule getCompatibleModule(OperationType operationType);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   entities          DOCUMENT ME!
   * @param   mergedProperties  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  ClassMetadata getMergedClassMetadata(Class<?>[] entities,
    Map<MergedPropertyType, Map<String, FieldMetadata>> mergedProperties);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   entityName              DOCUMENT ME!
   * @param   persistencePerspective  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Map<String, FieldMetadata> getSimpleMergedProperties(String entityName,
    PersistencePerspective persistencePerspective);

} // end interface InspectHelper
