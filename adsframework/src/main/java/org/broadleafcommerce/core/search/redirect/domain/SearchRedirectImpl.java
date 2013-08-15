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
package org.broadleafcommerce.core.search.redirect.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;
import org.broadleafcommerce.common.util.DateUtil;

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
  friendlyName        = "SearchRedirectImpl_friendyName"
)
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_SEARCH_INTERCEPT")
public class SearchRedirectImpl implements SearchRedirect, java.io.Serializable {
  private static final long serialVersionUID = 1L;

  @Transient private static final Log LOG = LogFactory.getLog(SearchRedirectImpl.class);

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "SearchRedirectImpl_ID",
    order        = 1,
    group        = "SearchRedirectImpl_description",
    groupOrder   = 1,
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "SEARCH_REDIRECT_ID")
  @GeneratedValue(generator = "SearchRedirectID")
  @GenericGenerator(
    name       = "SearchRedirectID",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "SearchRedirectImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.search.redirect.domain.SearchRedirectImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "SearchRedirectImpl_searchPriority",
    order        = 1,
    group        = "SearchRedirectImpl_description",
    groupOrder   = 1,
    prominent    = true
  )
  @Column(name = "PRIORITY")
  @Index(
    name        = "SEARCH_PRIORITY_INDEX",
    columnNames = { "PRIORITY" }
  )
  protected Integer searchPriority;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "SearchRedirectImpl_searchTerm",
    order        = 2,
    group        = "SearchRedirectImpl_description",
    prominent    = true,
    groupOrder   = 1
  )
  @Column(
    name     = "SEARCH_TERM",
    nullable = false
  )
  protected String searchTerm;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "SearchRedirectImpl_url",
    order        = 3,
    group        = "SearchRedirectImpl_description",
    prominent    = true,
    groupOrder   = 1
  )
  @Column(
    name     = "URL",
    nullable = false
  )
  protected String url;

  /** The active start date. */
  @AdminPresentation(
    friendlyName = "SkuImpl_Sku_Start_Date",
    order        = 7,
    group        = "SearchRedirectImpl_description",
    tooltip      = "skuStartDateTooltip",
    groupOrder   = 1
  )
  @Column(name = "ACTIVE_START_DATE")
  protected Date activeStartDate;

  /** The active end date. */
  @AdminPresentation(
    friendlyName = "SkuImpl_Sku_End_Date",
    order        = 8,
    group        = "SearchRedirectImpl_description",
    tooltip      = "skuEndDateTooltip",
    groupOrder   = 1
  )
  @Column(name = "ACTIVE_END_DATE")
  @Index(
    name        = "SEARCH_ACTIVE_INDEX",
    columnNames = { "ACTIVE_START_DATE", "ACTIVE_END_DATE" }
  )
  protected Date activeEndDate;

  /**
   * @see  org.broadleafcommerce.core.search.redirect.domain.SearchRedirect#getActiveStartDate()
   */
  @Override public Date getActiveStartDate() {
    return activeStartDate;
  }

  /**
   * @see  org.broadleafcommerce.core.search.redirect.domain.SearchRedirect#setActiveStartDate(java.util.Date)
   */
  @Override public void setActiveStartDate(Date activeStartDate) {
    this.activeStartDate = activeStartDate;
  }

  /**
   * @see  org.broadleafcommerce.core.search.redirect.domain.SearchRedirect#getActiveEndDate()
   */
  @Override public Date getActiveEndDate() {
    return activeEndDate;
  }

  /**
   * @see  org.broadleafcommerce.core.search.redirect.domain.SearchRedirect#setActiveEndDate(java.util.Date)
   */
  @Override public void setActiveEndDate(Date activeEndDate) {
    this.activeEndDate = activeEndDate;
  }

  /**
   * @see  org.broadleafcommerce.core.search.redirect.domain.SearchRedirect#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.search.redirect.domain.SearchRedirect#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.search.redirect.domain.SearchRedirect#getSearchTerm()
   */
  @Override public String getSearchTerm() {
    return searchTerm;
  }

  /**
   * @see  org.broadleafcommerce.core.search.redirect.domain.SearchRedirect#setSearchTerm(java.lang.String)
   */
  @Override public void setSearchTerm(String searchTerm) {
    this.searchTerm = searchTerm;
  }

  /**
   * @see  org.broadleafcommerce.core.search.redirect.domain.SearchRedirect#getUrl()
   */
  @Override public String getUrl() {
    return url;
  }

  /**
   * @see  org.broadleafcommerce.core.search.redirect.domain.SearchRedirect#setUrl(java.lang.String)
   */
  @Override public void setUrl(String url) {
    this.url = url;
  }

  /**
   * @see  org.broadleafcommerce.core.search.redirect.domain.SearchRedirect#getSearchPriority()
   */
  @Override public Integer getSearchPriority() {
    return searchPriority;
  }

  /**
   * @see  org.broadleafcommerce.core.search.redirect.domain.SearchRedirect#setSearchPriority(java.lang.Integer)
   */
  @Override public void setSearchPriority(Integer searchPriority) {
    this.searchPriority = searchPriority;
  }

  /**
   * @see  org.broadleafcommerce.core.search.redirect.domain.SearchRedirect#isActive()
   */
  @Override public boolean isActive() {
    if (LOG.isDebugEnabled()) {
      if (!DateUtil.isActive(getActiveStartDate(), getActiveEndDate(), true)) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("product, " + id + ", inactive due to date");
        }
      }
    }

    return DateUtil.isActive(getActiveStartDate(), getActiveEndDate(), true);
  }

} // end class SearchRedirectImpl
