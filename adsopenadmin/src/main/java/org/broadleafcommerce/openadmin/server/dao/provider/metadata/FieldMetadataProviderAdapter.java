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

package org.broadleafcommerce.openadmin.server.dao.provider.metadata;

import java.util.Map;

import org.broadleafcommerce.openadmin.dto.FieldMetadata;
import org.broadleafcommerce.openadmin.server.dao.provider.metadata.request.AddMetadataFromFieldTypeRequest;
import org.broadleafcommerce.openadmin.server.dao.provider.metadata.request.AddMetadataFromMappingDataRequest;
import org.broadleafcommerce.openadmin.server.dao.provider.metadata.request.AddMetadataRequest;
import org.broadleafcommerce.openadmin.server.dao.provider.metadata.request.LateStageAddMetadataRequest;
import org.broadleafcommerce.openadmin.server.dao.provider.metadata.request.OverrideViaAnnotationRequest;
import org.broadleafcommerce.openadmin.server.dao.provider.metadata.request.OverrideViaXmlRequest;
import org.broadleafcommerce.openadmin.server.service.type.FieldProviderResponse;

import org.springframework.core.Ordered;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class FieldMetadataProviderAdapter extends AbstractFieldMetadataProvider {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.provider.metadata.FieldMetadataProvider#addMetadata(org.broadleafcommerce.openadmin.server.dao.provider.metadata.request.AddMetadataRequest,
   *       java.util.Map)
   */
  @Override public FieldProviderResponse addMetadata(AddMetadataRequest addMetadataRequest,
    Map<String, FieldMetadata> metadata) {
    return FieldProviderResponse.NOT_HANDLED;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.provider.metadata.FieldMetadataProvider#addMetadataFromFieldType(org.broadleafcommerce.openadmin.server.dao.provider.metadata.request.AddMetadataFromFieldTypeRequest,
   *       java.util.Map)
   */
  @Override public FieldProviderResponse addMetadataFromFieldType(
    AddMetadataFromFieldTypeRequest addMetadataFromFieldTypeRequest, Map<String, FieldMetadata> metadata) {
    return FieldProviderResponse.NOT_HANDLED;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.provider.metadata.FieldMetadataProvider#addMetadataFromMappingData(org.broadleafcommerce.openadmin.server.dao.provider.metadata.request.AddMetadataFromMappingDataRequest,
   *       org.broadleafcommerce.openadmin.dto.FieldMetadata)
   */
  @Override public FieldProviderResponse addMetadataFromMappingData(
    AddMetadataFromMappingDataRequest addMetadataFromMappingDataRequest, FieldMetadata metadata) {
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
   * @see  org.broadleafcommerce.openadmin.server.dao.provider.metadata.FieldMetadataProvider#lateStageAddMetadata(org.broadleafcommerce.openadmin.server.dao.provider.metadata.request.LateStageAddMetadataRequest,
   *       java.util.Map)
   */
  @Override public FieldProviderResponse lateStageAddMetadata(LateStageAddMetadataRequest addMetadataRequest,
    Map<String, FieldMetadata> metadata) {
    return FieldProviderResponse.NOT_HANDLED;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.provider.metadata.FieldMetadataProvider#overrideViaAnnotation(org.broadleafcommerce.openadmin.server.dao.provider.metadata.request.OverrideViaAnnotationRequest,
   *       java.util.Map)
   */
  @Override public FieldProviderResponse overrideViaAnnotation(
    OverrideViaAnnotationRequest overrideViaAnnotationRequest, Map<String, FieldMetadata> metadata) {
    return FieldProviderResponse.NOT_HANDLED;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.provider.metadata.FieldMetadataProvider#overrideViaXml(org.broadleafcommerce.openadmin.server.dao.provider.metadata.request.OverrideViaXmlRequest,
   *       java.util.Map)
   */
  @Override public FieldProviderResponse overrideViaXml(OverrideViaXmlRequest overrideViaXmlRequest,
    Map<String, FieldMetadata> metadata) {
    return FieldProviderResponse.NOT_HANDLED;
  }
} // end class FieldMetadataProviderAdapter
