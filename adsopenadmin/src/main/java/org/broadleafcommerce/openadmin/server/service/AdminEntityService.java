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

package org.broadleafcommerce.openadmin.server.service;

import java.util.Map;

import org.broadleafcommerce.common.exception.ServiceException;

import org.broadleafcommerce.openadmin.dto.ClassMetadata;
import org.broadleafcommerce.openadmin.dto.DynamicResultSet;
import org.broadleafcommerce.openadmin.dto.Entity;
import org.broadleafcommerce.openadmin.dto.FilterAndSortCriteria;
import org.broadleafcommerce.openadmin.dto.Property;
import org.broadleafcommerce.openadmin.server.domain.PersistencePackageRequest;
import org.broadleafcommerce.openadmin.web.form.entity.EntityForm;


/**
 * DOCUMENT ME!
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public interface AdminEntityService {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Persists the given entity.
   *
   * @param   entityForm      DOCUMENT ME!
   * @param   customCriteria  DOCUMENT ME!
   *
   * @return  the persisted Entity
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   */
  Entity addEntity(EntityForm entityForm, String[] customCriteria) throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Adds an item into the specified collection.
   *
   * @param   entityForm    DOCUMENT ME!
   * @param   mainMetadata  DOCUMENT ME!
   * @param   field         DOCUMENT ME!
   * @param   parentEntity  DOCUMENT ME!
   *
   * @return  the persisted Entity
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   * @throws  ClassNotFoundException
   */
  Entity addSubCollectionEntity(EntityForm entityForm, ClassMetadata mainMetadata, Property field,
    Entity parentEntity) throws ServiceException, ClassNotFoundException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Gets an Entity representing a specific collection item.
   *
   * @param   containingClassMetadata  DOCUMENT ME!
   * @param   containingEntity         DOCUMENT ME!
   * @param   collectionProperty       DOCUMENT ME!
   * @param   collectionItemId         DOCUMENT ME!
   *
   * @return  the Entity
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   */
  Entity getAdvancedCollectionRecord(ClassMetadata containingClassMetadata, Entity containingEntity,
    Property collectionProperty, String collectionItemId) throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns class metadata for the given request object.
   *
   * @param   request  DOCUMENT ME!
   *
   * @return  ClassMetadata for the given request
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   */
  ClassMetadata getClassMetadata(PersistencePackageRequest request) throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the appropriate id to use for the given entity/metadata and prefix when dealing with collections. For
   * example, on the Product screen, we display associated media. However, this media is actually owned by the Sku
   * entity, which means its property name is "defaultSku.skuMedia". In this case, when wanting to look up media for
   * this product, we cannot use the id of the product. Instead, we need to use the id of the sku.
   *
   * @param   cmd           DOCUMENT ME!
   * @param   entity        DOCUMENT ME!
   * @param   propertyName  DOCUMENT ME!
   *
   * @return  the id to be used for this relationship
   */
  String getContextSpecificRelationshipId(ClassMetadata cmd, Entity entity, String propertyName);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the name of the property in this ClassMetadata that has field type set to
   * {@link org.broadleafcommerce.common.presentation.client.SupportedFieldType#ID}.
   *
   * @param   cmd  DOCUMENT ME!
   *
   * @return  the id property name
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   */
  String getIdProperty(ClassMetadata cmd) throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns a specific record for the given request and primary key id/property.
   *
   * @param   request              DOCUMENT ME!
   * @param   id                   DOCUMENT ME!
   * @param   cmd                  DOCUMENT ME!
   * @param   isCollectionRequest  whether or not this record request was initiated from a collection on a parent entity
   *
   * @return  the Entity
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   */
  Entity getRecord(PersistencePackageRequest request, String id, ClassMetadata cmd, boolean isCollectionRequest)
    throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the DynamicResultSet containing the total records for the query and the currently fetched Entity[].
   *
   * @param   request  DOCUMENT ME!
   *
   * @return  DynamicResultSet
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   */
  DynamicResultSet getRecords(PersistencePackageRequest request) throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns all records for all subcollections of the specified request and its primary key.
   *
   * @param   ppr               DOCUMENT ME!
   * @param   containingEntity  DOCUMENT ME!
   *
   * @return  all Entity[] for all collections for the specified containingClass
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   *
   * @see     #getRecordsForCollection(org.broadleafcommerce.openadmin.dto.ClassMetadata, String,
   *          org.broadleafcommerce.openadmin.dto.Property)
   */
  Map<String, DynamicResultSet> getRecordsForAllSubCollections(PersistencePackageRequest ppr,
    Entity containingEntity) throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the DynamicResultSet representing the records that belong to the specified collectionProperty for the given
   * containingClass and the primary key for the containingClass.
   *
   * @param   containingClassMetadata  DOCUMENT ME!
   * @param   containingEntity         DOCUMENT ME!
   * @param   collectionProperty       DOCUMENT ME!
   * @param   fascs                    DOCUMENT ME!
   * @param   startIndex               DOCUMENT ME!
   * @param   maxIndex                 DOCUMENT ME!
   *
   * @return  the DynamicResultSet
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   */
  DynamicResultSet getRecordsForCollection(ClassMetadata containingClassMetadata, Entity containingEntity,
    Property collectionProperty, FilterAndSortCriteria[] fascs, Integer startIndex, Integer maxIndex)
    throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Removes the given entity.
   *
   * @param   entityForm      DOCUMENT ME!
   * @param   customCriteria  DOCUMENT ME!
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   */
  void removeEntity(EntityForm entityForm, String[] customCriteria) throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Removes the given item from the specified collection.
   *
   * @param   mainMetadata  DOCUMENT ME!
   * @param   field         DOCUMENT ME!
   * @param   parentEntity  parentId
   * @param   itemId        DOCUMENT ME!
   * @param   priorKey      - only needed for Map type collections
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   */
  void removeSubCollectionEntity(ClassMetadata mainMetadata, Property field, Entity parentEntity, String itemId,
    String priorKey) throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Updates the given entity.
   *
   * @param   entityForm      DOCUMENT ME!
   * @param   customCriteria  DOCUMENT ME!
   *
   * @return  the persisted Entity
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   */
  Entity updateEntity(EntityForm entityForm, String[] customCriteria) throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Updates the specified collection item.
   *
   * @param   entityForm        DOCUMENT ME!
   * @param   mainMetadata      DOCUMENT ME!
   * @param   field             DOCUMENT ME!
   * @param   parentEntity      DOCUMENT ME!
   * @param   collectionItemId  DOCUMENT ME!
   *
   * @return  the persisted Entity
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   * @throws  ClassNotFoundException
   */
  Entity updateSubCollectionEntity(EntityForm entityForm, ClassMetadata mainMetadata, Property field,
    Entity parentEntity, String collectionItemId) throws ServiceException, ClassNotFoundException;

} // end interface AdminEntityService
