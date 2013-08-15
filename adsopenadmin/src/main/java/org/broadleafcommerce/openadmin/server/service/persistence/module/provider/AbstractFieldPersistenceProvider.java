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

package org.broadleafcommerce.openadmin.server.service.persistence.module.provider;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.apache.commons.lang.ArrayUtils;

import org.broadleafcommerce.openadmin.dto.Property;
import org.broadleafcommerce.openadmin.server.dao.FieldInfo;
import org.broadleafcommerce.openadmin.server.service.persistence.PersistenceManager;
import org.broadleafcommerce.openadmin.server.service.persistence.module.FieldManager;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public abstract class AbstractFieldPersistenceProvider implements FieldPersistenceProvider {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   field  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected FieldInfo buildFieldInfo(Field field) {
    FieldInfo info = new FieldInfo();
    info.setName(field.getName());
    info.setGenericType(field.getGenericType());

    ManyToMany manyToMany = field.getAnnotation(ManyToMany.class);

    if (manyToMany != null) {
      info.setManyToManyMappedBy(manyToMany.mappedBy());
      info.setManyToManyTargetEntity(manyToMany.targetEntity().getName());
    }

    OneToMany oneToMany = field.getAnnotation(OneToMany.class);

    if (oneToMany != null) {
      info.setOneToManyMappedBy(oneToMany.mappedBy());
      info.setOneToManyTargetEntity(oneToMany.targetEntity().getName());
    }

    return info;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   instance            DOCUMENT ME!
   * @param   fieldManager        DOCUMENT ME!
   * @param   property            DOCUMENT ME!
   * @param   persistenceManager  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected Class<?> getListFieldType(Serializable instance, FieldManager fieldManager, Property property,
    PersistenceManager persistenceManager) {
    Class<?>               returnType = null;
    Field                  field      = fieldManager.getField(instance.getClass(), property.getName());
    java.lang.reflect.Type type       = field.getGenericType();

    if (type instanceof ParameterizedType) {
      ParameterizedType pType    = (ParameterizedType) type;
      Class<?>          clazz    = (Class<?>) pType.getActualTypeArguments()[0];
      Class<?>[]        entities = persistenceManager.getDynamicEntityDao().getAllPolymorphicEntitiesFromCeiling(clazz);

      if (!ArrayUtils.isEmpty(entities)) {
        returnType = entities[entities.length - 1];
      }
    }

    return returnType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   instance            DOCUMENT ME!
   * @param   fieldManager        DOCUMENT ME!
   * @param   property            DOCUMENT ME!
   * @param   persistenceManager  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected Class<?> getMapFieldType(Serializable instance, FieldManager fieldManager, Property property,
    PersistenceManager persistenceManager) {
    Class<?>               returnType = null;
    Field                  field      = fieldManager.getField(instance.getClass(),
        property.getName().substring(0, property.getName().indexOf(FieldManager.MAPFIELDSEPARATOR)));
    java.lang.reflect.Type type       = field.getGenericType();

    if (type instanceof ParameterizedType) {
      ParameterizedType pType    = (ParameterizedType) type;
      Class<?>          clazz    = (Class<?>) pType.getActualTypeArguments()[1];
      Class<?>[]        entities = persistenceManager.getDynamicEntityDao().getAllPolymorphicEntitiesFromCeiling(clazz);

      if (!ArrayUtils.isEmpty(entities)) {
        returnType = entities[entities.length - 1];
      }
    }

    return returnType;
  }
} // end class AbstractFieldPersistenceProvider
