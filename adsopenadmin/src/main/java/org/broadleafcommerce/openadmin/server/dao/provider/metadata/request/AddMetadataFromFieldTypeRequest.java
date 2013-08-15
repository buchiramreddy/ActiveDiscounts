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

import java.util.List;
import java.util.Map;

import org.broadleafcommerce.common.presentation.client.SupportedFieldType;

import org.broadleafcommerce.openadmin.dto.FieldMetadata;
import org.broadleafcommerce.openadmin.dto.ForeignKey;
import org.broadleafcommerce.openadmin.dto.MergedPropertyType;
import org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao;

import org.hibernate.mapping.Property;

import org.hibernate.type.Type;


/**
 * Contains the requested field, property name and support classes.
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class AddMetadataFromFieldTypeRequest {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private final ForeignKey[]               additionalForeignFields;
  private final int                        additionalForeignKeyIndexPosition;
  private final List<Property>             componentProperties;
  private final DynamicEntityDao           dynamicEntityDao;
  private final SupportedFieldType         explicitType;
  private final ForeignKey                 foreignField;
  private final String                     idProperty;
  private final MergedPropertyType         mergedPropertyType;
  private final String                     prefix;
  private final FieldMetadata              presentationAttribute;
  private final Map<String, FieldMetadata> presentationAttributes;
  private final boolean                    propertyForeignKey;

  private final Field    requestedField;
  private final String   requestedPropertyName;
  private final Class<?> returnedClass;
  private final Class<?> targetClass;
  private final Type     type;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new AddMetadataFromFieldTypeRequest object.
   *
   * @param  requestedField                     DOCUMENT ME!
   * @param  targetClass                        DOCUMENT ME!
   * @param  foreignField                       DOCUMENT ME!
   * @param  additionalForeignFields            DOCUMENT ME!
   * @param  mergedPropertyType                 DOCUMENT ME!
   * @param  componentProperties                DOCUMENT ME!
   * @param  idProperty                         DOCUMENT ME!
   * @param  prefix                             DOCUMENT ME!
   * @param  requestedPropertyName              DOCUMENT ME!
   * @param  type                               DOCUMENT ME!
   * @param  propertyForeignKey                 DOCUMENT ME!
   * @param  additionalForeignKeyIndexPosition  DOCUMENT ME!
   * @param  presentationAttributes             DOCUMENT ME!
   * @param  presentationAttribute              DOCUMENT ME!
   * @param  explicitType                       DOCUMENT ME!
   * @param  returnedClass                      DOCUMENT ME!
   * @param  dynamicEntityDao                   DOCUMENT ME!
   */
  public AddMetadataFromFieldTypeRequest(Field requestedField, Class<?> targetClass, ForeignKey foreignField,
    ForeignKey[] additionalForeignFields,
    MergedPropertyType mergedPropertyType, List<Property> componentProperties,
    String idProperty,
    String prefix, String requestedPropertyName, Type type,
    boolean propertyForeignKey, int additionalForeignKeyIndexPosition,
    Map<String, FieldMetadata> presentationAttributes,
    FieldMetadata presentationAttribute, SupportedFieldType explicitType, Class<?> returnedClass,
    DynamicEntityDao dynamicEntityDao) {
    this.requestedField                    = requestedField;
    this.targetClass                       = targetClass;
    this.foreignField                      = foreignField;
    this.additionalForeignFields           = additionalForeignFields;
    this.mergedPropertyType                = mergedPropertyType;
    this.componentProperties               = componentProperties;
    this.idProperty                        = idProperty;
    this.prefix                            = prefix;
    this.requestedPropertyName             = requestedPropertyName;
    this.type                              = type;
    this.propertyForeignKey                = propertyForeignKey;
    this.additionalForeignKeyIndexPosition = additionalForeignKeyIndexPosition;
    this.presentationAttributes            = presentationAttributes;
    this.presentationAttribute             = presentationAttribute;
    this.explicitType                      = explicitType;
    this.returnedClass                     = returnedClass;
    this.dynamicEntityDao                  = dynamicEntityDao;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public ForeignKey[] getAdditionalForeignFields() {
    return additionalForeignFields;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int getAdditionalForeignKeyIndexPosition() {
    return additionalForeignKeyIndexPosition;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

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
  public SupportedFieldType getExplicitType() {
    return explicitType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public ForeignKey getForeignField() {
    return foreignField;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getIdProperty() {
    return idProperty;
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
  public String getPrefix() {
    return prefix;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public FieldMetadata getPresentationAttribute() {
    return presentationAttribute;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<String, FieldMetadata> getPresentationAttributes() {
    return presentationAttributes;
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
  public String getRequestedPropertyName() {
    return requestedPropertyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Class<?> getReturnedClass() {
    return returnedClass;
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

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Type getType() {
    return type;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isPropertyForeignKey() {
    return propertyForeignKey;
  }
} // end class AddMetadataFromFieldTypeRequest
