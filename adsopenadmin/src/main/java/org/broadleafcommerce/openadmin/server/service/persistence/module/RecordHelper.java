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

import java.io.Serializable;

import java.lang.reflect.InvocationTargetException;

import java.util.List;
import java.util.Map;

import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.common.presentation.client.OperationType;

import org.broadleafcommerce.openadmin.dto.CriteriaTransferObject;
import org.broadleafcommerce.openadmin.dto.Entity;
import org.broadleafcommerce.openadmin.dto.EntityResult;
import org.broadleafcommerce.openadmin.dto.FieldMetadata;
import org.broadleafcommerce.openadmin.dto.PersistencePackage;
import org.broadleafcommerce.openadmin.dto.PersistencePerspective;
import org.broadleafcommerce.openadmin.server.service.ValidationException;
import org.broadleafcommerce.openadmin.server.service.persistence.module.criteria.FilterMapping;
import org.broadleafcommerce.openadmin.server.service.persistence.module.criteria.RestrictionFactory;


/**
 * Helper interface for serializing/deserializing the generic {@link org.broadleafcommerce.openadmin.dto.Entity} DTO
 * to/from its actual domain object representation.
 *
 * @author   jfischer
 * @see      {@link org.broadleafcommerce.openadmin.server.service.persistence.module.BasicPersistenceModule}
 * @see      {@link org.broadleafcommerce.openadmin.server.service.persistence.module.MapStructurePersistenceModule}
 * @see
 *           
 *           {@link org.broadleafcommerce.openadmin.server.service.persistence.module.AdornedTargetListPersistenceModule}
 * @version  $Revision$, $Date$
 */
public interface RecordHelper extends DataFormatProvider {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   persistencePackage       DOCUMENT ME!
   * @param   includeRealEntityObject  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  ServiceException  DOCUMENT ME!
   */
  EntityResult add(PersistencePackage persistencePackage, boolean includeRealEntityObject) throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * <p>Populates a Hibernate entity <b>instance</b> based on the values from <b>entity</b> (the DTO representation of
   * <b>instance</b>) and the metadata from <b>mergedProperties</b>.</p>
   *
   * <p>While populating <b>instance</b>, validation is also performed using the
   * {@link org.broadleafcommerce.openadmin.server.service.persistence.validation.EntityValidatorService}. If this
   * validation fails, then the instance is left unchanged and a {@link ValidationExcpetion} is thrown. In the common
   * case, this exception bubbles up to the {@link DynamicRemoteService} which catches the exception and communicates
   * appropriately to the invoker</p>
   *
   * @param   instance          DOCUMENT ME!
   * @param   entity            DOCUMENT ME!
   * @param   mergedProperties  DOCUMENT ME!
   * @param   setId             DOCUMENT ME!
   *
   * @throws  org.broadleafcommerce.openadmin.server.service.ValidationException  if after populating <b>instance</b>
   *                                                                              via the values in <b>entity</b> then
   *                                                                              {@link org.broadleafcommerce.openadmin.server.service.persistence.validation.EntityValidatorService#validate(org.broadleafcommerce.openadmin.dto.Entity, java.io.Serializable, java.util.Map)}
   *                                                                              returns false
   *
   * @return  <b>instance</b> populated with the property values from <b>entity</b> according to the metadata specified
   *          in <b>mergedProperties</b>
   *
   * @see     {@link org.broadleafcommerce.openadmin.server.service.persistence.validation.EntityValidatorService}
   */
  Serializable createPopulatedInstance(Serializable instance, Entity entity,
    Map<String, FieldMetadata> mergedProperties, Boolean setId) throws ValidationException;

  //~ ------------------------------------------------------------------------------------------------------------------

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
   * @return  DOCUMENT ME!
   */
  FieldManager getFieldManager();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   persistencePerspective                DOCUMENT ME!
   * @param   cto                                   DOCUMENT ME!
   * @param   ceilingEntityFullyQualifiedClassname  DOCUMENT ME!
   * @param   mergedProperties                      DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<FilterMapping> getFilterMappings(PersistencePerspective persistencePerspective, CriteriaTransferObject cto,
    String ceilingEntityFullyQualifiedClassname, Map<String, FieldMetadata> mergedProperties);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   persistencePerspective                DOCUMENT ME!
   * @param   cto                                   DOCUMENT ME!
   * @param   ceilingEntityFullyQualifiedClassname  DOCUMENT ME!
   * @param   mergedUnfilteredProperties            DOCUMENT ME!
   * @param   customRestrictionFactory              DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<FilterMapping> getFilterMappings(PersistencePerspective persistencePerspective, CriteriaTransferObject cto,
    String ceilingEntityFullyQualifiedClassname, Map<String, FieldMetadata> mergedUnfilteredProperties,
    RestrictionFactory customRestrictionFactory);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   ceilingEntity   DOCUMENT ME!
   * @param   filterMappings  DOCUMENT ME!
   * @param   firstResult     DOCUMENT ME!
   * @param   maxResults      DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<Serializable> getPersistentRecords(String ceilingEntity, List<FilterMapping> filterMappings, Integer firstResult,
    Integer maxResults);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   entity            DOCUMENT ME!
   * @param   mergedProperties  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Object getPrimaryKey(Entity entity, Map<String, FieldMetadata> mergedProperties);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   ceilingEntityClass      DOCUMENT ME!
   * @param   persistencePerspective  DOCUMENT ME!
   * @param   record                  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Entity getRecord(Class<?> ceilingEntityClass, PersistencePerspective persistencePerspective, Serializable record);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   primaryMergedProperties    DOCUMENT ME!
   * @param   record                     DOCUMENT ME!
   * @param   alternateMergedProperties  DOCUMENT ME!
   * @param   pathToTargetObject         DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Entity getRecord(Map<String, FieldMetadata> primaryMergedProperties, Serializable record,
    Map<String, FieldMetadata> alternateMergedProperties, String pathToTargetObject);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   primaryMergedProperties  DOCUMENT ME!
   * @param   records                  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Entity[] getRecords(Map<String, FieldMetadata> primaryMergedProperties, List<? extends Serializable> records);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   ceilingEntityClass      DOCUMENT ME!
   * @param   persistencePerspective  DOCUMENT ME!
   * @param   records                 DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Entity[] getRecords(Class<?> ceilingEntityClass, PersistencePerspective persistencePerspective,
    List<? extends Serializable> records);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   primaryMergedProperties    DOCUMENT ME!
   * @param   records                    DOCUMENT ME!
   * @param   alternateMergedProperties  DOCUMENT ME!
   * @param   pathToTargetObject         DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Entity[] getRecords(Map<String, FieldMetadata> primaryMergedProperties, List<? extends Serializable> records,
    Map<String, FieldMetadata> alternateMergedProperties, String pathToTargetObject);

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

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns a string representation of the field on the given instance specified by the property name. The propertyName
   * should start from the root of the given instance
   *
   * @param   instance      DOCUMENT ME!
   * @param   propertyName  DOCUMENT ME!
   *
   * @return  a string representation of the field on the given instance specified by the property name.
   *
   * @throws  IllegalAccessException     DOCUMENT ME!
   * @throws  InvocationTargetException  DOCUMENT ME!
   * @throws  NoSuchMethodException      DOCUMENT ME!
   */
  String getStringValueFromGetter(Serializable instance, String propertyName) throws IllegalAccessException,
    InvocationTargetException, NoSuchMethodException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   ceilingEntity   DOCUMENT ME!
   * @param   filterMappings  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Integer getTotalRecords(String ceilingEntity, List<FilterMapping> filterMappings);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   persistencePackage       DOCUMENT ME!
   * @param   includeRealEntityObject  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  ServiceException  DOCUMENT ME!
   */
  EntityResult update(PersistencePackage persistencePackage, boolean includeRealEntityObject) throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Validates the {@link org.broadleafcommerce.openadmin.dto.Entity} based on the validators associated with each
   * property.
   *
   * @param   entity             the instance that is attempted to be saved from. Implementers should set
   *                             {@link org.broadleafcommerce.openadmin.dto.Entity#isValidationFailure()} accordingly as
   *                             a result of the validation
   * @param   populatedInstance  DOCUMENT ME!
   * @param   mergedProperties   TODO
   *
   * @return  whether or not the entity passed validation. This yields the same result as calling
   *          !{@link org.broadleafcommerce.openadmin.dto.Entity#isValidationFailure()} after invoking this method
   */
  boolean validate(Entity entity, Serializable populatedInstance, Map<String, FieldMetadata> mergedProperties);

} // end interface RecordHelper
