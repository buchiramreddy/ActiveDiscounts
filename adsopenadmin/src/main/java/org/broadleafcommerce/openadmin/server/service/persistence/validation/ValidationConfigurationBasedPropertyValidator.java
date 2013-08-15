/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.openadmin.server.service.persistence.validation;

import java.io.Serializable;

import java.util.Map;

import org.broadleafcommerce.common.presentation.ConfigurationItem;

import org.broadleafcommerce.openadmin.dto.BasicFieldMetadata;
import org.broadleafcommerce.openadmin.dto.Entity;
import org.broadleafcommerce.openadmin.dto.FieldMetadata;


/**
 * Provides a default validate method that uses the validation configuration map to pull out the error key and
 * pre-populate the
 * {@link org.broadleafcommerce.openadmin.server.service.persistence.validation.PropertyValidationResult} based on
 * {@link org.broadleafcommerce.common.presentation.ConfigurationItem#ERROR_MESSAGE}.
 *
 * <p>This class should be used as your base if you are writing a validator based on a
 * {@link org.broadleafcommerce.common.presentation.ValidationConfiguration}</p>
 *
 * @author   Phillip Verheyden (phillipuniverse)
 * @version  $Revision$, $Date$
 */
public abstract class ValidationConfigurationBasedPropertyValidator implements PropertyValidator {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.persistence.validation.PropertyValidator#validate(org.broadleafcommerce.openadmin.dto.Entity,
   *       java.io.Serializable, java.util.Map, java.util.Map, org.broadleafcommerce.openadmin.dto.BasicFieldMetadata,
   *       java.lang.String, java.lang.String)
   */
  @Override public PropertyValidationResult validate(Entity entity, Serializable instance,
    Map<String, FieldMetadata> entityFieldMetadata, Map<String, String> validationConfiguration,
    BasicFieldMetadata propertyMetadata,
    String propertyName,
    String value) {
    return new PropertyValidationResult(validateInternal(entity,
          instance,
          entityFieldMetadata,
          validationConfiguration,
          propertyMetadata,
          propertyName,
          value), validationConfiguration.get(ConfigurationItem.ERROR_MESSAGE));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Delegate method for {@link org.broadleafcommerce.common.presentation.ValidationConfiguration}-based processors that
   * don't need to return an error message.
   *
   * @param   entity                   DOCUMENT ME!
   * @param   instance                 DOCUMENT ME!
   * @param   entityFieldMetadata      DOCUMENT ME!
   * @param   validationConfiguration  DOCUMENT ME!
   * @param   propertyMetadata         DOCUMENT ME!
   * @param   propertyName             DOCUMENT ME!
   * @param   value                    DOCUMENT ME!
   *
   * @return  delegate method for {@link org.broadleafcommerce.common.presentation.ValidationConfiguration}-based
   *          processors that don't need to return an error message.
   */
  public boolean validateInternal(Entity entity,
    Serializable instance, Map<String, FieldMetadata> entityFieldMetadata, Map<String, String> validationConfiguration,
    BasicFieldMetadata propertyMetadata,
    String propertyName,
    String value) {
    return false;
  }


} // end class ValidationConfigurationBasedPropertyValidator
