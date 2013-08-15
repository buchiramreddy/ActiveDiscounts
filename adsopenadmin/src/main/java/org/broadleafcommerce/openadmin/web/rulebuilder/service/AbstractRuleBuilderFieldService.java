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

package org.broadleafcommerce.openadmin.web.rulebuilder.service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.web.BroadleafRequestContext;

import org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao;
import org.broadleafcommerce.openadmin.server.service.DynamicEntityRemoteService;
import org.broadleafcommerce.openadmin.server.service.persistence.PersistenceManager;
import org.broadleafcommerce.openadmin.server.service.persistence.TargetModeType;
import org.broadleafcommerce.openadmin.web.rulebuilder.dto.FieldDTO;
import org.broadleafcommerce.openadmin.web.rulebuilder.dto.FieldData;
import org.broadleafcommerce.openadmin.web.rulebuilder.dto.FieldWrapper;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.MessageSource;


/**
 * DOCUMENT ME!
 *
 * @author   Elbert Bautista (elbertbautista)
 * @version  $Revision$, $Date$
 */
public abstract class AbstractRuleBuilderFieldService implements RuleBuilderFieldService, ApplicationContextAware,
  InitializingBean {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected ApplicationContext applicationContext;

  /** DOCUMENT ME! */
  protected DynamicEntityDao dynamicEntityDao;

  /** DOCUMENT ME! */
  protected List<FieldData>  fields = new ArrayList<FieldData>();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public abstract String getDtoClassName();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  public abstract void init();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
   */
  @Override public void afterPropertiesSet() throws Exception {
    // This bean only is valid when the following bean is active. (admin)
    if (applicationContext.containsBean(DynamicEntityRemoteService.DEFAULTPERSISTENCEMANAGERREF)) {
      PersistenceManager persistenceManager = (PersistenceManager) applicationContext.getBean(
          DynamicEntityRemoteService.DEFAULTPERSISTENCEMANAGERREF);
      persistenceManager.setTargetMode(TargetModeType.SANDBOX);
      dynamicEntityDao = persistenceManager.getDynamicEntityDao();
      setFields(new ArrayList<FieldData>());

      // This cannot be null during startup as we do not want to remove the null safety checks in a multi-tenant env.
      if (BroadleafRequestContext.getBroadleafRequestContext() == null) {
        BroadleafRequestContext brc = new BroadleafRequestContext();
        brc.setIgnoreSite(true);
        BroadleafRequestContext.setBroadleafRequestContext(brc);
      }

      init();
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.web.rulebuilder.service.RuleBuilderFieldService#buildFields()
   */
  @Override public FieldWrapper buildFields() {
    FieldWrapper wrapper = new FieldWrapper();

    for (FieldData field : getFields()) {
      FieldDTO fieldDTO = new FieldDTO();
      fieldDTO.setLabel(field.getFieldLabel());

      // translate the label to display
      String                  label    = field.getFieldLabel();
      BroadleafRequestContext context  = BroadleafRequestContext.getBroadleafRequestContext();
      MessageSource           messages = context.getMessageSource();
      label = messages.getMessage(label, null, label, context.getJavaLocale());
      fieldDTO.setLabel(label);

      fieldDTO.setName(field.getFieldName());
      fieldDTO.setOperators(field.getOperators());
      fieldDTO.setOptions(field.getOptions());
      wrapper.getFields().add(fieldDTO);
    }

    return wrapper;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#clone()
   */
  @Override public RuleBuilderFieldService clone() throws CloneNotSupportedException {
    try {
      RuleBuilderFieldService clone = this.getClass().newInstance();
      clone.setFields(this.fields);

      return clone;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.web.rulebuilder.service.RuleBuilderFieldService#getField(java.lang.String)
   */
  @Override public FieldDTO getField(String fieldName) {
    for (FieldData field : getFields()) {
      if (field.getFieldName().equals(fieldName)) {
        FieldDTO fieldDTO = new FieldDTO();
        fieldDTO.setLabel(field.getFieldLabel());
        fieldDTO.setName(field.getFieldName());
        fieldDTO.setOperators(field.getOperators());
        fieldDTO.setOptions(field.getOptions());

        return fieldDTO;
      }
    }

    return null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.web.rulebuilder.service.RuleBuilderFieldService#getFields()
   */
  @Override public List<FieldData> getFields() {
    return fields;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.web.rulebuilder.service.RuleBuilderFieldService#getSecondaryFieldType(java.lang.String)
   */
  @Override public SupportedFieldType getSecondaryFieldType(String fieldName) {
    SupportedFieldType type = null;

    if (fieldName != null) {
      for (FieldData field : getFields()) {
        if (fieldName.equals(field.getFieldName())) {
          return field.getSecondaryFieldType();
        }
      }
    }

    return type;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.web.rulebuilder.service.RuleBuilderFieldService#getSupportedFieldType(java.lang.String)
   */
  @Override public SupportedFieldType getSupportedFieldType(String fieldName) {
    SupportedFieldType type = null;

    if (fieldName != null) {
      for (FieldData field : getFields()) {
        if (fieldName.equals(field.getFieldName())) {
          return field.getFieldType();
        }
      }
    }

    return type;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
   */
  @Override public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.web.rulebuilder.service.RuleBuilderFieldService#setFields(java.util.List)
   */
  @Override
  @SuppressWarnings("unchecked")
  public void setFields(final List<FieldData> fields) {
    List<FieldData> proxyFields = (List<FieldData>) Proxy.newProxyInstance(getClass().getClassLoader(),
        new Class<?>[] { List.class }, new InvocationHandler() {
          @Override public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.getName().equals("add")) {
              FieldData fieldData = (FieldData) args[0];
              testFieldName(fieldData);
            }

            if (method.getName().equals("addAll")) {
              Collection<FieldData> addCollection = (Collection<FieldData>) args[0];
              Iterator<FieldData>   itr           = addCollection.iterator();

              while (itr.hasNext()) {
                FieldData fieldData = itr.next();
                testFieldName(fieldData);
              }
            }

            return method.invoke(fields, args);
          }

          private void testFieldName(FieldData fieldData) throws ClassNotFoundException {
            if (!StringUtils.isEmpty(fieldData.getFieldName()) && (dynamicEntityDao != null)) {
              Class<?>[] dtos = dynamicEntityDao.getAllPolymorphicEntitiesFromCeiling(Class.forName(getDtoClassName()));

              if (ArrayUtils.isEmpty(dtos)) {
                dtos = new Class<?>[] { Class.forName(getDtoClassName()) };
              }

              Field field = null;

              for (Class<?> dto : dtos) {
                field = dynamicEntityDao.getFieldManager().getField(dto, fieldData.getFieldName());

                if (field != null) {
                  break;
                }
              }

              if (field == null) {
                throw new IllegalArgumentException(
                  "Unable to find the field declared in FieldData (" + fieldData.getFieldName()
                  + ") on the target class (" + getDtoClassName()
                  + "), or any registered entity class that derives from it.");
              }
            }
          } // end method testFieldName
        });
    this.fields = proxyFields;
  } // end method setFields

} // end class AbstractRuleBuilderFieldService
