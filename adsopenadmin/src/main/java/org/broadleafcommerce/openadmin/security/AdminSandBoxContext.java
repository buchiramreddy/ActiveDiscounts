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

package org.broadleafcommerce.openadmin.security;

import org.broadleafcommerce.common.web.SandBoxContext;

import org.broadleafcommerce.openadmin.server.security.domain.AdminUser;
import org.broadleafcommerce.openadmin.server.service.SandBoxMode;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class AdminSandBoxContext extends SandBoxContext {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected AdminUser   adminUser;

  /** DOCUMENT ME! */
  protected boolean     isReplay       = false;

  /** DOCUMENT ME! */
  protected boolean     rebuildSandBox = false;

  /** DOCUMENT ME! */
  protected boolean     resetData   = false;

  /** DOCUMENT ME! */
  protected SandBoxMode sandBoxMode;

  /** DOCUMENT ME! */
  protected String      sandBoxName;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.web.SandBoxContext#clone()
   */
  @Override public SandBoxContext clone() {
    AdminSandBoxContext myContext = new AdminSandBoxContext();
    myContext.setResetData(isResetData());
    myContext.setAdminUser(getAdminUser());
    myContext.setSandBoxId(getSandBoxId());
    myContext.setPreviewMode(getPreviewMode());
    myContext.setSandBoxMode(getSandBoxMode());
    myContext.setSandBoxName(getSandBoxName());
    myContext.setReplay(isReplay());
    myContext.setRebuildSandBox(isRebuildSandBox());


    return myContext;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public AdminUser getAdminUser() {
    return adminUser;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public SandBoxMode getSandBoxMode() {
    return sandBoxMode;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getSandBoxName() {
    return sandBoxName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isRebuildSandBox() {
    return rebuildSandBox;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isReplay() {
    return isReplay;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isResetData() {
    return resetData;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  adminUser  DOCUMENT ME!
   */
  public void setAdminUser(AdminUser adminUser) {
    this.adminUser = adminUser;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  rebuildSandBox  DOCUMENT ME!
   */
  public void setRebuildSandBox(boolean rebuildSandBox) {
    this.rebuildSandBox = rebuildSandBox;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  replay  DOCUMENT ME!
   */
  public void setReplay(boolean replay) {
    isReplay = replay;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  resetData  DOCUMENT ME!
   */
  public void setResetData(boolean resetData) {
    this.resetData = resetData;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  sandBoxMode  DOCUMENT ME!
   */
  public void setSandBoxMode(SandBoxMode sandBoxMode) {
    this.sandBoxMode = sandBoxMode;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  sandBoxName  DOCUMENT ME!
   */
  public void setSandBoxName(String sandBoxName) {
    this.sandBoxName = sandBoxName;
  }
} // end class AdminSandBoxContext
