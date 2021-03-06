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
/**
 *
 */
package org.broadleafcommerce.cms.url.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.broadleafcommerce.cms.url.type.URLRedirectType;

import org.broadleafcommerce.common.admin.domain.AdminMainEntity;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   priyeshpatel
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(
  populateToOneFields = PopulateToOneFieldsEnum.TRUE,
  friendlyName        = "URLHandlerImpl_friendyName"
)
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_URL_HANDLER")
public class URLHandlerImpl implements URLHandler, Serializable, AdminMainEntity {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "URLHandlerImpl_ID",
    order        = 1,
    group        = "URLHandlerImpl_friendyName",
    groupOrder   = 1,
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "URL_HANDLER_ID")
  @GeneratedValue(generator = "URLHandlerID")
  @GenericGenerator(
    name       = "URLHandlerID",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "URLHandlerImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.cms.url.domain.URLHandlerImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "URLHandlerImpl_incomingURL",
    order        = 1,
    group        = "URLHandlerImpl_friendyName",
    prominent    = true,
    groupOrder   = 1
  )
  @Column(
    name     = "INCOMING_URL",
    nullable = false
  )
  @Index(
    name        = "INCOMING_URL_INDEX",
    columnNames = { "INCOMING_URL" }
  )
  protected String incomingURL;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "URLHandlerImpl_newURL",
    order        = 1,
    group        = "URLHandlerImpl_friendyName",
    prominent    = true,
    groupOrder   = 1
  )
  @Column(
    name     = "NEW_URL",
    nullable = false
  )
  protected String newURL;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName         = "URLHandlerImpl_redirectType",
    order                = 4,
    group                = "URLHandlerImpl_friendyName",
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    broadleafEnumeration = "org.broadleafcommerce.cms.url.type.URLRedirectType",
    groupOrder           = 2,
    prominent            = true
  )
  @Column(name = "URL_REDIRECT_TYPE")
  protected String urlRedirectType;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /* (non-Javadoc)
        * @see org.broadleafcommerce.common.url.URLHandler#getId()
        */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* (non-Javadoc)
        * @see org.broadleafcommerce.common.url.URLHandler#getIncomingURL()
        */
  @Override public String getIncomingURL() {
    return incomingURL;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.admin.domain.AdminMainEntity#getMainEntityName()
   */
  @Override public String getMainEntityName() {
    return getIncomingURL();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* (non-Javadoc)
        * @see org.broadleafcommerce.common.url.URLHandler#getNewURL()
        */
  @Override public String getNewURL() {
    return newURL;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.url.domain.URLHandler#getUrlRedirectType()
   */
  @Override public URLRedirectType getUrlRedirectType() {
    return URLRedirectType.getInstance(urlRedirectType);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* (non-Javadoc)
        * @see org.broadleafcommerce.common.url.URLHandler#setId(java.lang.Long)
        */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* (non-Javadoc)
        * @see org.broadleafcommerce.common.url.URLHandler#setIncomingURL(java.lang.String)
        */
  @Override public void setIncomingURL(String incomingURL) {
    this.incomingURL = incomingURL;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* (non-Javadoc)
        * @see org.broadleafcommerce.common.url.URLHandler#setNewURL(java.lang.String)
        */
  @Override public void setNewURL(String newURL) {
    this.newURL = newURL;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.url.domain.URLHandler#setUrlRedirectType(org.broadleafcommerce.cms.url.type.URLRedirectType)
   */
  @Override public void setUrlRedirectType(URLRedirectType redirectType) {
    this.urlRedirectType = redirectType.getType();
  }

} // end class URLHandlerImpl
