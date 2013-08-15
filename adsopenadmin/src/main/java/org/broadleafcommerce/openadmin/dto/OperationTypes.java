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

import org.broadleafcommerce.common.presentation.client.OperationType;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class OperationTypes implements Serializable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private OperationType addType = OperationType.BASIC;

  private OperationType fetchType   = OperationType.BASIC;
  private OperationType inspectType = OperationType.BASIC;
  private OperationType removeType  = OperationType.BASIC;
  private OperationType updateType  = OperationType.BASIC;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new OperationTypes object.
   */
  public OperationTypes() {
    // do nothing
  }

  /**
   * Creates a new OperationTypes object.
   *
   * @param  fetchType    DOCUMENT ME!
   * @param  removeType   DOCUMENT ME!
   * @param  addType      DOCUMENT ME!
   * @param  updateType   DOCUMENT ME!
   * @param  inspectType  DOCUMENT ME!
   */
  public OperationTypes(OperationType fetchType, OperationType removeType, OperationType addType,
    OperationType updateType, OperationType inspectType) {
    this.removeType  = removeType;
    this.addType     = addType;
    this.updateType  = updateType;
    this.fetchType   = fetchType;
    this.inspectType = inspectType;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public OperationTypes cloneOperationTypes() {
    OperationTypes operationTypes = new OperationTypes();
    operationTypes.setAddType(addType);
    operationTypes.setFetchType(fetchType);
    operationTypes.setInspectType(inspectType);
    operationTypes.setRemoveType(removeType);
    operationTypes.setUpdateType(updateType);

    return operationTypes;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof OperationTypes)) {
      return false;
    }

    OperationTypes that = (OperationTypes) o;

    if (addType != that.addType) {
      return false;
    }

    if (fetchType != that.fetchType) {
      return false;
    }

    if (inspectType != that.inspectType) {
      return false;
    }

    if (removeType != that.removeType) {
      return false;
    }

    if (updateType != that.updateType) {
      return false;
    }

    return true;
  } // end method equals

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * How should the system execute an addition for this item.
   *
   * <p>OperationType BASIC will result in the item being inserted OperationType NONDESTRUCTIVEREMOVE is not supported
   * and will result in the same behavior as BASIC. Note, any foreign key associations in the persistence perspective
   * (@see PersistencePerspective) will be honored during the BASIC based add. OperationType ADORNEDTARGETLIST will
   * result in a join structure entity being added (not either of the associated entities).
   * org.broadleafcommerce.core.catalog.domain.CategoryProductXrefImpl is an example of a join structure entity.
   * OperationType MAP will result in the item being added to the requisite map in the containing entity.</p>
   *
   * @return  the type of the add operation
   */
  public OperationType getAddType() {
    return addType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * How should the system execute a fetch.
   *
   * <p>OperationType BASIC will result in a search for items having one or more basic properties matches OperationType
   * FOREINKEY is not support and will result in the same behavior as BASIC. Note, any foreign key associations will be
   * included as part of the query. OperationType ADORNEDTARGETLIST will result in search for items that match one of
   * the associations in a join structure. For example, CategoryProductXrefImpl is used in a AdornedTargetList fetch to
   * retrieve all products for a particular category. OperationType MAP will result retrieval of all map entries for the
   * requisite map in the containing entity.</p>
   *
   * @return  the type of the fetch operation
   */
  public OperationType getFetchType() {
    return fetchType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * OperationType values are generally ignored for inspect and should be defined as BASIC for consistency in most
   * circumstances. This API is meant to support future persistence modules where specialized inspect phase management
   * may be required.
   *
   * @return  the type of the inspect operation
   */
  public OperationType getInspectType() {
    return inspectType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * How should the system execute a removal of this item.
   *
   * <p>OperationType BASIC will result in the item being removed based on its primary key OperationType
   * NONDESTRUCTIVEREMOVE will result in the item being removed from the containing list in the containing entity. This
   * is useful when you don't want the item to actually be deleted, but simply removed from the parent collection.
   * OperationType ADORNEDTARGETLIST will result in a join structure being deleted (not either of the associated
   * entities). org.broadleafcommerce.core.catalog.domain.CategoryProductXrefImpl is an example of a join structure
   * entity. OperationType MAP will result in the item being removed from the requisite map in the containing entity.
   * </p>
   *
   * @return  the type of remove operation
   */
  public OperationType getRemoveType() {
    return removeType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * How should the system execute an update for this item.
   *
   * <p>OperationType BASIC will result in the item being updated based on it's primary key OperationType
   * NONDESTRUCTIVEREMOVE is not supported and will result in the same behavior as BASIC. Note, any foreign key
   * associations in the persistence perspective (@see PersistencePerspective) will be honored during the BASIC based
   * update. OperationType ADORNEDTARGETLIST will result in a join structure entity being updated (not either of the
   * associated entities). org.broadleafcommerce.core.catalog.domain.CategoryProductXrefImpl is an example of a join
   * structure entity. OperationType MAP will result in the item being updated to the requisite map in the containing
   * entity.</p>
   *
   * @return  the type of the update operation
   */
  public OperationType getUpdateType() {
    return updateType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    int result = (fetchType != null) ? fetchType.hashCode() : 0;
    result = (31 * result) + ((removeType != null) ? removeType.hashCode() : 0);
    result = (31 * result) + ((addType != null) ? addType.hashCode() : 0);
    result = (31 * result) + ((updateType != null) ? updateType.hashCode() : 0);
    result = (31 * result) + ((inspectType != null) ? inspectType.hashCode() : 0);

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * How should the system execute an addition for this item.
   *
   * <p>OperationType BASIC will result in the item being inserted OperationType NONDESTRUCTIVEREMOVE is not supported
   * and will result in the same behavior as BASIC. Note, any foreign key associations in the persistence perspective
   * (@see PersistencePerspective) will be honored during the BASIC based add. OperationType ADORNEDTARGETLIST will
   * result in a join structure entity being added (not either of the associated entities).
   * org.broadleafcommerce.core.catalog.domain.CategoryProductXrefImpl is an example of a join structure entity.
   * OperationType MAP will result in the item being added to the requisite map in the containing entity.</p>
   *
   * @param  addType  DOCUMENT ME!
   */
  public void setAddType(OperationType addType) {
    this.addType = addType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * How should the system execute a fetch.
   *
   * <p>OperationType BASIC will result in a search for items having one or more basic properties matches OperationType
   * FOREINKEY is not support and will result in the same behavior as BASIC. Note, any foreign key associations will be
   * included as part of the query. OperationType ADORNEDTARGETLIST will result in search for items that match one of
   * the associations in a join structure. For example, CategoryProductXrefImpl is used in a AdornedTargetList fetch to
   * retrieve all products for a particular category. OperationType MAP will result retrieval of all map entries for the
   * requisite map in the containing entity.</p>
   *
   * @param  fetchType  DOCUMENT ME!
   */
  public void setFetchType(OperationType fetchType) {
    this.fetchType = fetchType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * OperationType values are generally ignored for inspect and should be defined as BASIC for consistency in most
   * circumstances. This API is meant to support future persistence modules where specialized inspect phase management
   * may be required.
   *
   * @param  inspectType  DOCUMENT ME!
   */
  public void setInspectType(OperationType inspectType) {
    this.inspectType = inspectType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * How should the system execute a removal of this item.
   *
   * <p>OperationType BASIC will result in the item being removed based on its primary key OperationType
   * NONDESTRUCTIVEREMOVE will result in the item being removed from the containing list in the containing entity. This
   * is useful when you don't want the item to be removed to actually be deleted, but simply removed from the parent
   * collection. OperationType ADORNEDTARGETLIST will result in a join structure being deleted (not either of the
   * associated entities). org.broadleafcommerce.core.catalog.domain.CategoryProductXrefImpl is an example of a join
   * structure entity. OperationType MAP will result in the item being removed from the requisite map in the containing
   * entity.</p>
   *
   * @param  removeType  DOCUMENT ME!
   */
  public void setRemoveType(OperationType removeType) {
    this.removeType = removeType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * How should the system execute an update for this item.
   *
   * <p>OperationType BASIC will result in the item being updated based on it's primary key OperationType
   * NONDESTRUCTIVEREMOVE is not supported and will result in the same behavior as BASIC. Note, any foreign key
   * associations in the persistence perspective (@see PersistencePerspective) will be honored during the BASIC based
   * update. OperationType ADORNEDTARGETLIST will result in a join structure entity being updated (not either of the
   * associated entities). org.broadleafcommerce.core.catalog.domain.CategoryProductXrefImpl is an example of a join
   * structure entity. OperationType MAP will result in the item being updated to the requisite map in the containing
   * entity.</p>
   *
   * @param  updateType  DOCUMENT ME!
   */
  public void setUpdateType(OperationType updateType) {
    this.updateType = updateType;
  }
} // end class OperationTypes
