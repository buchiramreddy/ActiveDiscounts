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

import java.io.Serializable;

import java.util.List;

import org.broadleafcommerce.openadmin.dto.BasicFieldMetadata;
import org.broadleafcommerce.openadmin.dto.Property;
import org.broadleafcommerce.openadmin.server.service.persistence.PersistenceManager;
import org.broadleafcommerce.openadmin.server.service.persistence.module.DataFormatProvider;
import org.broadleafcommerce.openadmin.server.service.persistence.module.FieldManager;
import org.broadleafcommerce.openadmin.server.service.persistence.module.RecordHelper;


/**
 * Contains the requested value, property and support classes.
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class ExtractValueRequest {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected String                   displayVal;

  /** DOCUMENT ME! */
  protected final Serializable       entity;

  /** DOCUMENT ME! */
  protected final FieldManager       fieldManager;

  /** DOCUMENT ME! */
  protected final BasicFieldMetadata metadata;

  /** DOCUMENT ME! */
  protected final PersistenceManager persistenceManager;

  /** DOCUMENT ME! */
  protected final List<Property> props;

  /** DOCUMENT ME! */
  protected final RecordHelper   recordHelper;

  /** DOCUMENT ME! */
  protected final Object         requestedValue;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new ExtractValueRequest object.
   *
   * @param  props               DOCUMENT ME!
   * @param  fieldManager        DOCUMENT ME!
   * @param  metadata            DOCUMENT ME!
   * @param  requestedValue      DOCUMENT ME!
   * @param  displayVal          DOCUMENT ME!
   * @param  persistenceManager  DOCUMENT ME!
   * @param  recordHelper        DOCUMENT ME!
   * @param  entity              DOCUMENT ME!
   */
  public ExtractValueRequest(List<Property> props, FieldManager fieldManager, BasicFieldMetadata metadata,
    Object requestedValue, String displayVal, PersistenceManager persistenceManager,
    RecordHelper recordHelper, Serializable entity) {
    this.props              = props;
    this.fieldManager       = fieldManager;
    this.metadata           = metadata;
    this.requestedValue     = requestedValue;
    this.displayVal         = displayVal;
    this.persistenceManager = persistenceManager;
    this.recordHelper       = recordHelper;
    this.entity             = entity;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public DataFormatProvider getDataFormatProvider() {
    return recordHelper;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getDisplayVal() {
    return displayVal;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Serializable getEntity() {
    return entity;
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
  public BasicFieldMetadata getMetadata() {
    return metadata;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PersistenceManager getPersistenceManager() {
    return persistenceManager;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<Property> getProps() {
    return props;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public RecordHelper getRecordHelper() {
    return recordHelper;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Object getRequestedValue() {
    return requestedValue;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  displayVal  DOCUMENT ME!
   */
  public void setDisplayVal(String displayVal) {
    this.displayVal = displayVal;
  }

} // end class ExtractValueRequest
