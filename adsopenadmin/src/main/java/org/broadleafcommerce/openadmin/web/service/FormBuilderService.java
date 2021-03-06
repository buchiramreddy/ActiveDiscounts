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

package org.broadleafcommerce.openadmin.web.service;

import java.util.Map;

import org.broadleafcommerce.common.exception.ServiceException;

import org.broadleafcommerce.openadmin.dto.AdornedTargetCollectionMetadata;
import org.broadleafcommerce.openadmin.dto.AdornedTargetList;
import org.broadleafcommerce.openadmin.dto.ClassMetadata;
import org.broadleafcommerce.openadmin.dto.DynamicResultSet;
import org.broadleafcommerce.openadmin.dto.Entity;
import org.broadleafcommerce.openadmin.dto.MapMetadata;
import org.broadleafcommerce.openadmin.dto.MapStructure;
import org.broadleafcommerce.openadmin.dto.Property;
import org.broadleafcommerce.openadmin.web.form.component.ListGrid;
import org.broadleafcommerce.openadmin.web.form.entity.EntityForm;


/**
 * DOCUMENT ME!
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public interface FormBuilderService {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Builds the EntityForm used in modal dialogs when adding items to adorned target collections.
   *
   * @param   adornedMd    DOCUMENT ME!
   * @param   adornedList  DOCUMENT ME!
   * @param   parentId     DOCUMENT ME!
   *
   * @return  the EntityForm
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   */
  EntityForm buildAdornedListForm(AdornedTargetCollectionMetadata adornedMd, AdornedTargetList adornedList,
    String parentId) throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Equivalent to
   * {@link #buildAdornedListForm(org.broadleafcommerce.openadmin.dto.AdornedTargetCollectionMetadata, org.broadleafcommerce.openadmin.dto.AdornedTargetList, String)}
   * except rather than creating a new {@link org.broadleafcommerce.openadmin.web.form.entity.EntityForm} this simply
   * uses the {@link org.broadleafcommerce.openadmin.web.form.entity.EntityForm} that was passed in as <b>ef</b>. Used
   * mainly when rebuilding an {@link org.broadleafcommerce.openadmin.web.form.entity.EntityForm} after it has already
   * been bound by Spring.
   *
   * <p>Before invoking this method, you should invoke
   * {@link org.broadleafcommerce.openadmin.web.form.entity.EntityForm#clearFieldsMap()} to ensure that you have a clean
   * set of field groups and tabs for this method to work with</p>
   *
   * @param   adornedMd    mapMd
   * @param   adornedList  mapStructure
   * @param   parentId     DOCUMENT ME!
   * @param   ef           the form DTO to populate
   *
   * @return  the original {@link org.broadleafcommerce.openadmin.web.form.entity.EntityForm} passed in but fully
   *          populated
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   */
  EntityForm buildAdornedListForm(AdornedTargetCollectionMetadata adornedMd, AdornedTargetList adornedList,
    String parentId, EntityForm ef) throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Builds a list grid that is used to render a collection inline in an entity form.
   *
   * <p>Note that it can also be used in other places that require the same grid provided the type on the returned
   * ListGrid is set appropriately.</p>
   *
   * @param   containingEntityId  DOCUMENT ME!
   * @param   drs                 DOCUMENT ME!
   * @param   field               DOCUMENT ME!
   * @param   sectionKey          DOCUMENT ME!
   *
   * @return  the ListGrid
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   */
  ListGrid buildCollectionListGrid(String containingEntityId, DynamicResultSet drs, Property field, String sectionKey)
    throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Builds a list grid that is typically used at the top entity level to select an entity for modification.
   *
   * <p>Note that it can also be used in other places that require the same grid as the main entity search screen
   * provided the type on the returned ListGrid is set appropriately.</p>
   *
   * @param   drs         DOCUMENT ME!
   * @param   cmd         DOCUMENT ME!
   * @param   sectionKey  DOCUMENT ME!
   *
   * @return  the ListGrid
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   */
  ListGrid buildMainListGrid(DynamicResultSet drs, ClassMetadata cmd, String sectionKey) throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Builds the EntityForm used in modal dialogs when adding items to map collections.
   *
   * @param   mapMd         DOCUMENT ME!
   * @param   mapStructure  DOCUMENT ME!
   * @param   cmd           DOCUMENT ME!
   * @param   parentId      DOCUMENT ME!
   *
   * @return  the EntityForm
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   */
  EntityForm buildMapForm(MapMetadata mapMd, MapStructure mapStructure, ClassMetadata cmd, String parentId)
    throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Equivalent to
   * {@link #buildMapForm(org.broadleafcommerce.openadmin.dto.MapMetadata, org.broadleafcommerce.openadmin.dto.MapStructure, org.broadleafcommerce.openadmin.dto.ClassMetadata, String)}
   * except rather than creating a new {@link org.broadleafcommerce.openadmin.web.form.entity.EntityForm} this simply
   * uses the {@link org.broadleafcommerce.openadmin.web.form.entity.EntityForm} that was passed in as <b>ef</b>. Used
   * mainly when rebuilding an {@link org.broadleafcommerce.openadmin.web.form.entity.EntityForm} after it has already
   * been bound by Spring.
   *
   * <p>Before invoking this method, you should invoke
   * {@link org.broadleafcommerce.openadmin.web.form.entity.EntityForm#clearFieldsMap()} to ensure that you have a clean
   * set of field groups and tabs for this method to work with</p>
   *
   * @param   mapMd         DOCUMENT ME!
   * @param   mapStructure  DOCUMENT ME!
   * @param   cmd           DOCUMENT ME!
   * @param   parentId      DOCUMENT ME!
   * @param   ef            the form DTO to populate
   *
   * @return  the original {@link org.broadleafcommerce.openadmin.web.form.entity.EntityForm} passed in but fully
   *          populated
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   */
  EntityForm buildMapForm(MapMetadata mapMd, final MapStructure mapStructure, ClassMetadata cmd, String parentId,
    EntityForm ef) throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new EntityForm with the a default 'Save' action. This will then delegate to
   * {@link #populateEntityForm(org.broadleafcommerce.openadmin.dto.ClassMetadata, org.broadleafcommerce.openadmin.web.form.entity.EntityForm)}
   * to ensure that the newly created {@link org.broadleafcommerce.openadmin.web.form.entity.EntityForm} has all of the
   * appropriate fields set up without any values based on <b>cmd</b>
   *
   * @param   cmd  DOCUMENT ME!
   *
   * @return  the EntityForm
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   *
   * @see
   *          
   *          {@link #populateEntityForm(org.broadleafcommerce.openadmin.dto.ClassMetadata, org.broadleafcommerce.openadmin.web.form.entity.EntityForm)}
   */
  EntityForm createEntityForm(ClassMetadata cmd) throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new EntityForm that has all of the appropriate fields set up along with the values for those fields from
   * the given Entity. Delegates to {@link #createEntityForm(org.broadleafcommerce.openadmin.dto.ClassMetadata)} for
   * further population
   *
   * @param   cmd     metadata that the created {@link org.broadleafcommerce.openadmin.web.form.entity.EntityForm}
   *                  should use to initialize its fields
   * @param   entity  DOCUMENT ME!
   *
   * @return  the EntityForm
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   *
   * @see     {@link #createEntityForm(org.broadleafcommerce.openadmin.dto.ClassMetadata)}
   */
  EntityForm createEntityForm(ClassMetadata cmd, Entity entity) throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Builds an EntityForm that has all of the appropriate fields set up along with the values for those fields from the
   * given Entity as well as all sub-collections of the given Entity that appear in the collectionRecords map. This
   * method simply delegates to create a standard {@link org.broadleafcommerce.openadmin.web.form.entity.EntityForm}
   * (that has a save action) and then populates that {@link org.broadleafcommerce.openadmin.web.form.entity.EntityForm}
   * using
   * {@link #populateEntityForm(org.broadleafcommerce.openadmin.dto.ClassMetadata, org.broadleafcommerce.openadmin.dto.Entity, java.util.Map, org.broadleafcommerce.openadmin.web.form.entity.EntityForm)}.
   *
   * <p>NOTE: if you are submitting a validation result, you must not call this method and instead invoke the one that
   * has an {@link org.broadleafcommerce.openadmin.web.form.entity.EntityForm} as a parameter. You cannot re-assign the
   * entityForm to the model after it has already been bound to a BindingResult, else the binding result will be
   * removed.</p>
   *
   * @param   cmd                DOCUMENT ME!
   * @param   entity             DOCUMENT ME!
   * @param   collectionRecords  DOCUMENT ME!
   *
   * @return  the EntityForm
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   *
   * @see
   *          
   *          {@link #populateEntityForm(org.broadleafcommerce.openadmin.dto.ClassMetadata, org.broadleafcommerce.openadmin.dto.Entity, java.util.Map, org.broadleafcommerce.openadmin.web.form.entity.EntityForm)}
   */
  EntityForm createEntityForm(ClassMetadata cmd, Entity entity, Map<String, DynamicResultSet> collectionRecords)
    throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets values for the necessary adorned fields on the EntityForm from the specified entity.
   *
   * @param  ef           DOCUMENT ME!
   * @param  entity       DOCUMENT ME!
   * @param  adornedList  DOCUMENT ME!
   */
  void populateAdornedEntityFormFields(EntityForm ef, Entity entity, AdornedTargetList adornedList);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Populates the given <b>ef</b> with all of the fields based on the properties from <b>cmd</b>. For all the fields
   * that are created, no values are set (as <b>cmd</b> usually does not have any). In order to fill out values in the
   * given <b>ef</b>, consider instead calling
   * {@link #populateEntityForm(org.broadleafcommerce.openadmin.dto.ClassMetadata, org.broadleafcommerce.openadmin.dto.Entity, org.broadleafcommerce.openadmin.web.form.entity.EntityForm, boolean)}
   *
   * @param   cmd  DOCUMENT ME!
   * @param   ef   DOCUMENT ME!
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   */
  void populateEntityForm(ClassMetadata cmd, EntityForm ef) throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Populates a given <b>ef</b> based on the given <b>cmd</b> to initially create fields with the necessary metadata
   * and then fills those fields out based on the property values from <b>entity</b>.
   *
   * @param   cmd     DOCUMENT ME!
   * @param   entity  DOCUMENT ME!
   * @param   ef      DOCUMENT ME!
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   *
   * @see
   *          
   *          {@link #populateEntityForm(org.broadleafcommerce.openadmin.dto.ClassMetadata, org.broadleafcommerce.openadmin.web.form.entity.EntityForm)}
   */
  void populateEntityForm(ClassMetadata cmd, Entity entity, EntityForm ef) throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Builds an EntityForm that has all of the appropriate fields set up along with the values for thsoe fields from the
   * given Entity as well as all sub-collections of the given Entity that appear in the collectionRecords map.
   *
   * <p>NOTE: This method is mainly used when coming back from validation. In the case of validation, you cannot re-add
   * a new {@link org.broadleafcommerce.openadmin.web.form.entity.EntityForm} to the model or else you lose the whole
   * {@link org.springframework.validation.BindingResult} and errors will not properly be displayed. In that scenario,
   * you must use this method rather than the one that does not take in an entityForm as it will attempt to instantiate
   * a new object.</p>
   *
   * @param   cmd                DOCUMENT ME!
   * @param   entity             DOCUMENT ME!
   * @param   collectionRecords  DOCUMENT ME!
   * @param   entityForm         rather than instantiate a new EntityForm, this will use this parameter to fill out
   *
   * @throws  org.broadleafcommerce.common.exception.ServiceException
   */
  void populateEntityForm(ClassMetadata cmd, Entity entity, Map<String, DynamicResultSet> collectionRecords,
    EntityForm entityForm) throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Delegates to
   * {@link #populateEntityFormFields(org.broadleafcommerce.openadmin.web.form.entity.EntityForm, org.broadleafcommerce.openadmin.dto.Entity, boolean, boolean)}
   * with true for populating both the id and type.
   *
   * @see
   *         
   *         {@link #populateEntityFormFields(org.broadleafcommerce.openadmin.web.form.entity.EntityForm, org.broadleafcommerce.openadmin.dto.Entity, boolean, boolean)}
   *
   * @param  ef      DOCUMENT ME!
   * @param  entity  DOCUMENT ME!
   */
  void populateEntityFormFields(EntityForm ef, Entity entity);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets values for all fields found on the EntityForm from the specified entity.
   *
   * @param  ef            DOCUMENT ME!
   * @param  entity        DOCUMENT ME!
   * @param  populateType  whether or not to use the type from the given
   *                       {@link org.broadleafcommerce.openadmin.dto.Entity} or keep the current value on the
   *                       {@link org.broadleafcommerce.openadmin.web.form.entity.EntityForm}
   * @param  populateId    whether or not to use the id from the given
   *                       {@link org.broadleafcommerce.openadmin.dto.Entity} or keep the current value on the
   *                       {@link org.broadleafcommerce.openadmin.web.form.entity.EntityForm}
   */
  void populateEntityFormFields(EntityForm ef, Entity entity, boolean populateType, boolean populateId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Populates the given {@link org.broadleafcommerce.openadmin.web.form.entity.EntityForm} with values based on the
   * {@link org.broadleafcommerce.openadmin.dto.Entity} that has been passed in. The
   * {@link org.broadleafcommerce.openadmin.dto.ClassMetadata} is used to determine which properties should be attempted
   * to be populated
   *
   * @param  cmd     'inspect' metadata for the class being populated
   * @param  entity  the {@link org.broadleafcommerce.openadmin.dto.Entity} that should be used to fill out the field
   *                 values in the given {@link org.broadleafcommerce.openadmin.web.form.entity.EntityForm}
   * @param  ef      the {@link org.broadleafcommerce.openadmin.web.form.entity.EntityForm} to populate field values
   *                 from the given {@link org.broadleafcommerce.openadmin.dto.Entity}
   */
  void populateEntityFormFieldValues(ClassMetadata cmd, Entity entity, EntityForm ef);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets values for the necessary map fields on the EntityForm from the specified entity.
   *
   * @param  ef      DOCUMENT ME!
   * @param  entity  DOCUMENT ME!
   */
  void populateMapEntityFormFields(EntityForm ef, Entity entity);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Loops through all of the fields that are specified in given class metadata and removes fields that are not
   * applicable for the given polymorphic entity type from the entity form.
   *
   * @param  cmd         DOCUMENT ME!
   * @param  entityForm  DOCUMENT ME!
   * @param  entityType  DOCUMENT ME!
   */
  void removeNonApplicableFields(ClassMetadata cmd, EntityForm entityForm, String entityType);
} // end interface FormBuilderService
