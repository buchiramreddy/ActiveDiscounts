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

package org.broadleafcommerce.core.search.redirect.domain;

import java.io.Serializable;

import java.util.Date;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface SearchRedirect extends Serializable {
  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Long getId();

  /**
   * DOCUMENT ME!
   *
   * @param  id  DOCUMENT ME!
   */
  void setId(Long id);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getSearchTerm();

  /**
   * DOCUMENT ME!
   *
   * @param  searchTerm  DOCUMENT ME!
   */
  void setSearchTerm(String searchTerm);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getUrl();

  /**
   * DOCUMENT ME!
   *
   * @param  url  DOCUMENT ME!
   */
  void setUrl(String url);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Integer getSearchPriority();

  /**
   * DOCUMENT ME!
   *
   * @param  searchPriority  DOCUMENT ME!
   */
  void setSearchPriority(Integer searchPriority);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Date getActiveStartDate();

  /**
   * DOCUMENT ME!
   *
   * @param  activeStartDate  DOCUMENT ME!
   */
  void setActiveStartDate(Date activeStartDate);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Date getActiveEndDate();

  /**
   * DOCUMENT ME!
   *
   * @param  activeEndDate  DOCUMENT ME!
   */
  void setActiveEndDate(Date activeEndDate);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  boolean isActive();
} // end interface SearchRedirect
