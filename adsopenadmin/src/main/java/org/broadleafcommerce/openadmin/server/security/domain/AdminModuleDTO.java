/*
 * Copyright 2008-2009 the original author or authors.
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

package org.broadleafcommerce.openadmin.server.security.domain;

import java.util.ArrayList;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public class AdminModuleDTO implements AdminModule {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected Integer displayOrder;

  /** DOCUMENT ME! */
  protected String  icon;

  /** DOCUMENT ME! */
  protected Long               id;

  /** DOCUMENT ME! */
  protected String             moduleKey;

  /** DOCUMENT ME! */
  protected String             name;

  /** DOCUMENT ME! */
  protected List<AdminSection> sections = new ArrayList<AdminSection>();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminModule#getDisplayOrder()
   */
  @Override public Integer getDisplayOrder() {
    return displayOrder;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminModule#getIcon()
   */
  @Override public String getIcon() {
    return icon;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminModule#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminModule#getModuleKey()
   */
  @Override public String getModuleKey() {
    return moduleKey;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminModule#getName()
   */
  @Override public String getName() {
    return name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminModule#getSections()
   */
  @Override public List<AdminSection> getSections() {
    return sections;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminModule#setDisplayOrder(java.lang.Integer)
   */
  @Override public void setDisplayOrder(Integer displayOrder) {
    this.displayOrder = displayOrder;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminModule#setIcon(java.lang.String)
   */
  @Override public void setIcon(String icon) {
    this.icon = icon;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  id  DOCUMENT ME!
   */
  public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminModule#setModuleKey(java.lang.String)
   */
  @Override public void setModuleKey(String moduleKey) {
    this.moduleKey = moduleKey;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminModule#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminModule#setSections(java.util.List)
   */
  @Override public void setSections(List<AdminSection> sections) {
    this.sections = sections;
  }
} // end class AdminModuleDTO
