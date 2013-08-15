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

import java.lang.reflect.Field;

import org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao;


/**
 * Contains the requested field, metadata and support classes.
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class AddMetadataRequest {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private final DynamicEntityDao dynamicEntityDao;
  private final Class<?>         parentClass;
  private final String           prefix;

  private final Field    requestedField;
  private final Class<?> targetClass;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new AddMetadataRequest object.
   *
   * @param  requestedField    DOCUMENT ME!
   * @param  parentClass       DOCUMENT ME!
   * @param  targetClass       DOCUMENT ME!
   * @param  dynamicEntityDao  DOCUMENT ME!
   * @param  prefix            DOCUMENT ME!
   */
  public AddMetadataRequest(Field requestedField, Class<?> parentClass, Class<?> targetClass,
    DynamicEntityDao dynamicEntityDao, String prefix) {
    this.requestedField   = requestedField;
    this.parentClass      = parentClass;
    this.targetClass      = targetClass;
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
  public Class<?> getParentClass() {
    return parentClass;
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
  public Field getRequestedField() {
    return requestedField;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Class<?> getTargetClass() {
    return targetClass;
  }
} // end class AddMetadataRequest
