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

package org.broadleafcommerce.openadmin.server.service.persistence;

import java.util.Calendar;
import java.util.List;

import org.broadleafcommerce.common.sandbox.domain.SandBox;
import org.broadleafcommerce.common.sandbox.domain.SandBoxType;
import org.broadleafcommerce.common.site.domain.Site;

import org.broadleafcommerce.openadmin.server.domain.SandBoxItem;
import org.broadleafcommerce.openadmin.server.security.domain.AdminUser;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface SandBoxService {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   site         DOCUMENT ME!
   * @param   sandBoxName  DOCUMENT ME!
   * @param   sandBoxType  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  Exception  DOCUMENT ME!
   */
  SandBox createSandBox(Site site, String sandBoxName, SandBoxType sandBoxType) throws Exception;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  sandBox  DOCUMENT ME!
   * @param  comment  DOCUMENT ME!
   */
  void promoteAllSandBoxItems(SandBox sandBox, String comment);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  sandBox       DOCUMENT ME!
   * @param  comment       DOCUMENT ME!
   * @param  sandBoxItems  DOCUMENT ME!
   */
  void promoteSelectedItems(SandBox sandBox, String comment, List<SandBoxItem> sandBoxItems);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  originalSandBox  DOCUMENT ME!
   * @param  sandBox          DOCUMENT ME!
   * @param  comment          DOCUMENT ME!
   */
  void rejectAllSandBoxItems(SandBox originalSandBox, SandBox sandBox, String comment);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  sandBox       DOCUMENT ME!
   * @param  comment       DOCUMENT ME!
   * @param  sandBoxItems  DOCUMENT ME!
   */
  void rejectSelectedSandBoxItems(SandBox sandBox, String comment, List<SandBoxItem> sandBoxItems);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   sandBox  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  SandBox retrieveApprovalSandBox(SandBox sandBox);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   site         DOCUMENT ME!
   * @param   sandBoxName  DOCUMENT ME!
   * @param   sandBoxType  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  SandBox retrieveSandBox(Site site, String sandBoxName, SandBoxType sandBoxType);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   id  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  SandBox retrieveSandboxById(Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the sandbox currently associated with the passed in userId. If one is not associated, it uses (or creates)
   * a default user sandbox with the name: user:username.
   *
   * @param   site       DOCUMENT ME!
   * @param   adminUser  DOCUMENT ME!
   *
   * @return  the sandbox currently associated with the passed in userId.
   */
  SandBox retrieveUserSandBox(Site site, AdminUser adminUser);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  originalSandBox  DOCUMENT ME!
   * @param  sandBox          DOCUMENT ME!
   */
  void revertAllSandBoxItems(SandBox originalSandBox, SandBox sandBox);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  sandBox       DOCUMENT ME!
   * @param  sandBoxItems  DOCUMENT ME!
   */
  void revertSelectedSandBoxItems(SandBox sandBox, List<SandBoxItem> sandBoxItems);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  sandBox   DOCUMENT ME!
   * @param  calendar  DOCUMENT ME!
   */
  void schedulePromotionForSandBox(SandBox sandBox, Calendar calendar);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  sandBoxItems  DOCUMENT ME!
   * @param  calendar      DOCUMENT ME!
   */
  void schedulePromotionForSandBoxItems(List<SandBoxItem> sandBoxItems, Calendar calendar);

} // end interface SandBoxService
