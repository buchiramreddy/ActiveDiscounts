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

import java.util.List;

import org.broadleafcommerce.common.presentation.client.SupportedFieldType;

import org.broadleafcommerce.openadmin.dto.MergedPropertyType;
import org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao;

import org.hibernate.mapping.Property;

import org.hibernate.type.Type;


/**
 * Contains the requested Hibernate type, metadata and support classes.
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class AddMetadataFromMappingDataRequest {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private final List<Property>     componentProperties;
  private final DynamicEntityDao   dynamicEntityDao;
  private final MergedPropertyType mergedPropertyType;
  private final String             propertyName;
  private final Type               requestedEntityType;
  private final SupportedFieldType secondaryType;
  private final SupportedFieldType type;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new AddMetadataFromMappingDataRequest object.
   *
   * @param  componentProperties  DOCUMENT ME!
   * @param  type                 DOCUMENT ME!
   * @param  secondaryType        DOCUMENT ME!
   * @param  requestedEntityType  DOCUMENT ME!
   * @param  propertyName         DOCUMENT ME!
   * @param  mergedPropertyType   DOCUMENT ME!
   * @param  dynamicEntityDao     DOCUMENT ME!
   */
  public AddMetadataFromMappingDataRequest(List<Property> componentProperties, SupportedFieldType type,
    SupportedFieldType secondaryType, Type requestedEntityType, String propertyName,
    MergedPropertyType mergedPropertyType, DynamicEntityDao dynamicEntityDao) {
    this.componentProperties = componentProperties;
    this.type                = type;
    this.secondaryType       = secondaryType;
    this.requestedEntityType = requestedEntityType;
    this.propertyName        = propertyName;
    this.mergedPropertyType  = mergedPropertyType;
    this.dynamicEntityDao    = dynamicEntityDao;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<Property> getComponentProperties() {
    return componentProperties;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

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
  public MergedPropertyType getMergedPropertyType() {
    return mergedPropertyType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getPropertyName() {
    return propertyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Type getRequestedEntityType() {
    return requestedEntityType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public SupportedFieldType getSecondaryType() {
    return secondaryType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public SupportedFieldType getType() {
    return type;
  }
} // end class AddMetadataFromMappingDataRequest
