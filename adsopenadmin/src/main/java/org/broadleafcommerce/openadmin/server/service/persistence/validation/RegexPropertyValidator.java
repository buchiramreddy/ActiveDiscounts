/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
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

import org.broadleafcommerce.openadmin.dto.BasicFieldMetadata;
import org.broadleafcommerce.openadmin.dto.Entity;
import org.broadleafcommerce.openadmin.dto.FieldMetadata;

import org.springframework.stereotype.Component;


/**
 * Validates a field against a configured 'regularExpression' item.
 *
 * @author   Phillip Verheyden
 * @version  $Revision$, $Date$
 */
@Component("blRegexPropertyValidator")
public class RegexPropertyValidator extends ValidationConfigurationBasedPropertyValidator {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.persistence.validation.ValidationConfigurationBasedPropertyValidator#validateInternal(org.broadleafcommerce.openadmin.dto.Entity,
   *       java.io.Serializable, java.util.Map, java.util.Map, org.broadleafcommerce.openadmin.dto.BasicFieldMetadata,
   *       java.lang.String, java.lang.String)
   */
  @Override public boolean validateInternal(Entity entity,
    Serializable instance, Map<String, FieldMetadata> entityFieldMetadata, Map<String, String> validationConfiguration,
    BasicFieldMetadata propertyMetadata,
    String propertyName,
    String value) {
    String expression = validationConfiguration.get("regularExpression");

    return (value == null) ? true : value.matches(expression);
  }

}
