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

import java.util.List;
import java.util.Map;

import org.broadleafcommerce.openadmin.dto.FieldMetadata;
import org.broadleafcommerce.openadmin.dto.Property;
import org.broadleafcommerce.openadmin.server.service.persistence.module.criteria.FilterMapping;
import org.broadleafcommerce.openadmin.server.service.persistence.module.provider.request.AddFilterPropertiesRequest;
import org.broadleafcommerce.openadmin.server.service.persistence.module.provider.request.AddSearchMappingRequest;
import org.broadleafcommerce.openadmin.server.service.persistence.module.provider.request.ExtractValueRequest;
import org.broadleafcommerce.openadmin.server.service.persistence.module.provider.request.PopulateValueRequest;
import org.broadleafcommerce.openadmin.server.service.type.FieldProviderResponse;

import org.springframework.core.Ordered;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class FieldPersistenceProviderAdapter extends AbstractFieldPersistenceProvider {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.persistence.module.provider.FieldPersistenceProvider#addSearchMapping(org.broadleafcommerce.openadmin.server.service.persistence.module.provider.request.AddSearchMappingRequest,
   *       java.util.List)
   */
  @Override public FieldProviderResponse addSearchMapping(AddSearchMappingRequest addSearchMappingRequest,
    List<FilterMapping> filterMappings) {
    return FieldProviderResponse.NOT_HANDLED;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.persistence.module.provider.FieldPersistenceProvider#extractValue(org.broadleafcommerce.openadmin.server.service.persistence.module.provider.request.ExtractValueRequest,
   *       org.broadleafcommerce.openadmin.dto.Property)
   */
  @Override public FieldProviderResponse extractValue(ExtractValueRequest extractValueRequest, Property property) {
    return FieldProviderResponse.NOT_HANDLED;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.persistence.module.provider.FieldPersistenceProvider#filterProperties(org.broadleafcommerce.openadmin.server.service.persistence.module.provider.request.AddFilterPropertiesRequest,
   *       java.util.Map)
   */
  @Override public FieldProviderResponse filterProperties(AddFilterPropertiesRequest addFilterPropertiesRequest,
    Map<String, FieldMetadata> properties) {
    return FieldProviderResponse.NOT_HANDLED;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.core.Ordered#getOrder()
   */
  @Override public int getOrder() {
    return Ordered.LOWEST_PRECEDENCE;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.persistence.module.provider.FieldPersistenceProvider#populateValue(org.broadleafcommerce.openadmin.server.service.persistence.module.provider.request.PopulateValueRequest,
   *       java.io.Serializable)
   */
  @Override public FieldProviderResponse populateValue(PopulateValueRequest populateValueRequest,
    Serializable instance) {
    return FieldProviderResponse.NOT_HANDLED;
  }
} // end class FieldPersistenceProviderAdapter
