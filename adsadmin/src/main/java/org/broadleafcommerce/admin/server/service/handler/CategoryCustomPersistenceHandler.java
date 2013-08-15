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

import java.lang.reflect.InvocationTargetException;

import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.exception.ServiceException;

import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.catalog.domain.CategoryXref;
import org.broadleafcommerce.core.catalog.domain.CategoryXrefImpl;

import org.broadleafcommerce.openadmin.dto.Entity;
import org.broadleafcommerce.openadmin.dto.FieldMetadata;
import org.broadleafcommerce.openadmin.dto.ForeignKey;
import org.broadleafcommerce.openadmin.dto.MergedPropertyType;
import org.broadleafcommerce.openadmin.dto.PersistencePackage;
import org.broadleafcommerce.openadmin.dto.PersistencePerspective;
import org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao;
import org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter;
import org.broadleafcommerce.openadmin.server.service.persistence.module.RecordHelper;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class CategoryCustomPersistenceHandler extends CustomPersistenceHandlerAdapter {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log LOG = LogFactory.getLog(CategoryCustomPersistenceHandler.class);

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter#add(org.broadleafcommerce.openadmin.dto.PersistencePackage,
   *       org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao,
   *       org.broadleafcommerce.openadmin.server.service.persistence.module.RecordHelper)
   */
  @Override public Entity add(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao,
    RecordHelper helper) throws ServiceException {
    Entity entity = persistencePackage.getEntity();

    try {
      PersistencePerspective     persistencePerspective = persistencePackage.getPersistencePerspective();
      Category                   adminInstance          = (Category) Class.forName(entity.getType()[0]).newInstance();
      Map<String, FieldMetadata> adminProperties        = helper.getSimpleMergedProperties(Category.class.getName(),
          persistencePerspective);
      adminInstance = (Category) helper.createPopulatedInstance(adminInstance, entity, adminProperties, false);

      CategoryXref categoryXref = new CategoryXrefImpl();
      categoryXref.setCategory(adminInstance.getDefaultParentCategory());
      categoryXref.setSubCategory(adminInstance);

      if ((adminInstance.getDefaultParentCategory() != null)
            && !adminInstance.getAllParentCategoryXrefs().contains(categoryXref)) {
        adminInstance.getAllParentCategoryXrefs().add(categoryXref);
      }

      adminInstance = (Category) dynamicEntityDao.merge(adminInstance);

      return helper.getRecord(adminProperties, adminInstance, null, null);
    } catch (Exception e) {
      throw new ServiceException("Unable to add entity for " + entity.getType()[0], e);
    }
  } // end method add

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter#canHandleAdd(org.broadleafcommerce.openadmin.dto.PersistencePackage)
   */
  @Override public Boolean canHandleAdd(PersistencePackage persistencePackage) {
    String   ceilingEntityFullyQualifiedClassname = persistencePackage.getCeilingEntityFullyQualifiedClassname();
    String[] customCriteria                       = persistencePackage.getCustomCriteria();

    return !ArrayUtils.isEmpty(customCriteria) && "addNewCategory".equals(customCriteria[0])
      && Category.class.getName().equals(ceilingEntityFullyQualifiedClassname);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   ceilingEntityFullyQualifiedClass  DOCUMENT ME!
   * @param   dynamicEntityDao                  DOCUMENT ME!
   * @param   populateManyToOneFields           DOCUMENT ME!
   * @param   includeManyToOneFields            DOCUMENT ME!
   * @param   excludeManyToOneFields            DOCUMENT ME!
   * @param   configurationKey                  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  ClassNotFoundException     DOCUMENT ME!
   * @throws  SecurityException          DOCUMENT ME!
   * @throws  IllegalArgumentException   DOCUMENT ME!
   * @throws  NoSuchMethodException      DOCUMENT ME!
   * @throws  IllegalAccessException     DOCUMENT ME!
   * @throws  InvocationTargetException  DOCUMENT ME!
   * @throws  NoSuchFieldException       DOCUMENT ME!
   */
  protected Map<String, FieldMetadata> getMergedProperties(Class<?> ceilingEntityFullyQualifiedClass,
    DynamicEntityDao dynamicEntityDao, Boolean populateManyToOneFields, String[] includeManyToOneFields,
    String[] excludeManyToOneFields, String configurationKey) throws ClassNotFoundException, SecurityException,
    IllegalArgumentException, NoSuchMethodException, IllegalAccessException, InvocationTargetException,
    NoSuchFieldException {
    Class<?>[] entities = dynamicEntityDao.getAllPolymorphicEntitiesFromCeiling(ceilingEntityFullyQualifiedClass);

    return dynamicEntityDao.getMergedProperties(
        ceilingEntityFullyQualifiedClass.getName(),
        entities,
        null,
        new String[] {},
        new ForeignKey[] {},
        MergedPropertyType.PRIMARY,
        populateManyToOneFields,
        includeManyToOneFields,
        excludeManyToOneFields,
        configurationKey,
        "");
  }
} // end class CategoryCustomPersistenceHandler
