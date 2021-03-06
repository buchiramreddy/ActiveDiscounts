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

package org.broadleafcommerce.admin.server.service.handler;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.common.presentation.client.OperationType;
import org.broadleafcommerce.common.presentation.client.PersistencePerspectiveItemType;

import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.catalog.domain.CategoryImpl;
import org.broadleafcommerce.core.catalog.domain.CategoryXref;
import org.broadleafcommerce.core.catalog.domain.CategoryXrefImpl;

import org.broadleafcommerce.openadmin.dto.AdornedTargetList;
import org.broadleafcommerce.openadmin.dto.Entity;
import org.broadleafcommerce.openadmin.dto.PersistencePackage;
import org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao;
import org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter;
import org.broadleafcommerce.openadmin.server.service.persistence.module.RecordHelper;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class ChildCategoriesCustomPersistenceHandler extends CustomPersistenceHandlerAdapter {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter#add(org.broadleafcommerce.openadmin.dto.PersistencePackage,
   *       org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao,
   *       org.broadleafcommerce.openadmin.server.service.persistence.module.RecordHelper)
   */
  @Override public Entity add(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao,
    RecordHelper helper) throws ServiceException {
    AdornedTargetList adornedTargetList = (AdornedTargetList) persistencePackage.getPersistencePerspective()
      .getPersistencePerspectiveItems().get(PersistencePerspectiveItemType.ADORNEDTARGETLIST);
    String            targetPath        = adornedTargetList.getTargetObjectPath() + "."
      + adornedTargetList.getTargetIdProperty();
    String            linkedPath        = adornedTargetList.getLinkedObjectPath() + "."
      + adornedTargetList.getLinkedIdProperty();

    Long parentId = Long.parseLong(persistencePackage.getEntity().findProperty(linkedPath).getValue());
    Long childId  = Long.parseLong(persistencePackage.getEntity().findProperty(targetPath).getValue());

    Category parent = (Category) dynamicEntityDao.retrieve(CategoryImpl.class, parentId);
    Category child  = (Category) dynamicEntityDao.retrieve(CategoryImpl.class, childId);

    CategoryXref categoryXref = new CategoryXrefImpl();
    categoryXref.setSubCategory(child);
    categoryXref.setCategory(parent);

    if (parent.getAllChildCategoryXrefs().contains(categoryXref)) {
      throw new ServiceException("Add unsuccessful. Cannot add a duplicate child category.");
    }

    checkParents(child, parent);

    return helper.getCompatibleModule(OperationType.ADORNEDTARGETLIST).add(persistencePackage);
  } // end method add

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter#canHandleAdd(org.broadleafcommerce.openadmin.dto.PersistencePackage)
   */
  @Override public Boolean canHandleAdd(PersistencePackage persistencePackage) {
    return (!ArrayUtils.isEmpty(persistencePackage.getCustomCriteria())
        && persistencePackage.getCustomCriteria()[0].equals("blcAllParentCategories"));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   child   DOCUMENT ME!
   * @param   parent  DOCUMENT ME!
   *
   * @throws  ServiceException  DOCUMENT ME!
   */
  protected void checkParents(Category child, Category parent) throws ServiceException {
    if (child.getId().equals(parent.getId())) {
      throw new ServiceException("Add unsuccessful. Cannot add a category to itself.");
    }

    for (CategoryXref category : parent.getAllParentCategoryXrefs()) {
      if (!CollectionUtils.isEmpty(category.getCategory().getAllParentCategoryXrefs())) {
        checkParents(child, category.getCategory());
      }
    }
  }
} // end class ChildCategoriesCustomPersistenceHandler
