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

package org.broadleafcommerce.openadmin.server.service.persistence.module.provider.request;

import java.util.Map;

import org.broadleafcommerce.openadmin.dto.CriteriaTransferObject;
import org.broadleafcommerce.openadmin.dto.FieldMetadata;
import org.broadleafcommerce.openadmin.dto.PersistencePerspective;
import org.broadleafcommerce.openadmin.server.service.persistence.module.DataFormatProvider;
import org.broadleafcommerce.openadmin.server.service.persistence.module.FieldManager;
import org.broadleafcommerce.openadmin.server.service.persistence.module.criteria.RestrictionFactory;


/**
 * Contains the requested ctoConverter, cto and support classes.
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class AddSearchMappingRequest {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private final String                     ceilingEntityFullyQualifiedClassname;
  private final DataFormatProvider         dataFormatProvider;
  private final FieldManager               fieldManager;
  private final Map<String, FieldMetadata> mergedProperties;

  private final PersistencePerspective persistencePerspective;
  private final String                 propertyName;
  private final CriteriaTransferObject requestedCto;
  private final RestrictionFactory     restrictionFactory;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new AddSearchMappingRequest object.
   *
   * @param  persistencePerspective                DOCUMENT ME!
   * @param  requestedCto                          DOCUMENT ME!
   * @param  ceilingEntityFullyQualifiedClassname  DOCUMENT ME!
   * @param  mergedProperties                      DOCUMENT ME!
   * @param  propertyName                          DOCUMENT ME!
   * @param  fieldManager                          DOCUMENT ME!
   * @param  dataFormatProvider                    DOCUMENT ME!
   * @param  restrictionFactory                    DOCUMENT ME!
   */
  public AddSearchMappingRequest(PersistencePerspective persistencePerspective, CriteriaTransferObject requestedCto,
    String ceilingEntityFullyQualifiedClassname, Map<String, FieldMetadata> mergedProperties,
    String propertyName, FieldManager fieldManager,
    DataFormatProvider dataFormatProvider, RestrictionFactory restrictionFactory) {
    this.persistencePerspective               = persistencePerspective;
    this.requestedCto                         = requestedCto;
    this.ceilingEntityFullyQualifiedClassname = ceilingEntityFullyQualifiedClassname;
    this.mergedProperties                     = mergedProperties;
    this.propertyName                         = propertyName;
    this.fieldManager                         = fieldManager;
    this.dataFormatProvider                   = dataFormatProvider;
    this.restrictionFactory                   = restrictionFactory;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getCeilingEntityFullyQualifiedClassname() {
    return ceilingEntityFullyQualifiedClassname;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public DataFormatProvider getDataFormatProvider() {
    return dataFormatProvider;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public FieldManager getFieldManager() {
    return fieldManager;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<String, FieldMetadata> getMergedProperties() {
    return mergedProperties;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PersistencePerspective getPersistencePerspective() {
    return persistencePerspective;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getPropertyName() {
    return propertyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public CriteriaTransferObject getRequestedCto() {
    return requestedCto;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public RestrictionFactory getRestrictionFactory() {
    return restrictionFactory;
  }
} // end class AddSearchMappingRequest
