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

package org.broadleafcommerce.openadmin.audit;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.broadleafcommerce.common.presentation.AdminPresentation;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Embeddable public class AdminAuditable implements Serializable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Column(
    name      = "CREATED_BY",
    updatable = false
  )
  protected Long createdBy;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AdminAuditable_Date_Created",
    group        = "AdminAuditable_Audit",
    readOnly     = true
  )
  @Column(
    name      = "DATE_CREATED",
    updatable = false
  )
  @Temporal(TemporalType.TIMESTAMP)
  protected Date dateCreated;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AdminAuditable_Date_Updated",
    group        = "AdminAuditable_Audit",
    readOnly     = true
  )
  @Column(name = "DATE_UPDATED")
  @Temporal(TemporalType.TIMESTAMP)
  protected Date dateUpdated;

  /** DOCUMENT ME! */
  @Column(name = "UPDATED_BY")
  protected Long updatedBy;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Long getCreatedBy() {
    return createdBy;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Date getDateCreated() {
    return dateCreated;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Date getDateUpdated() {
    return dateUpdated;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Long getUpdatedBy() {
    return updatedBy;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  createdBy  DOCUMENT ME!
   */
  public void setCreatedBy(Long createdBy) {
    this.createdBy = createdBy;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  dateCreated  DOCUMENT ME!
   */
  public void setDateCreated(Date dateCreated) {
    this.dateCreated = dateCreated;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  dateUpdated  DOCUMENT ME!
   */
  public void setDateUpdated(Date dateUpdated) {
    this.dateUpdated = dateUpdated;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  updatedBy  DOCUMENT ME!
   */
  public void setUpdatedBy(Long updatedBy) {
    this.updatedBy = updatedBy;
  }
} // end class AdminAuditable
