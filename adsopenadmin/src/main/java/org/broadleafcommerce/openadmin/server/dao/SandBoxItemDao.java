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

package org.broadleafcommerce.openadmin.server.dao;

import java.util.List;

import org.broadleafcommerce.openadmin.server.domain.SandBoxItem;
import org.broadleafcommerce.openadmin.server.domain.SandBoxItemType;
import org.broadleafcommerce.openadmin.server.domain.SandBoxOperationType;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface SandBoxItemDao {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   sandBox        DOCUMENT ME!
   * @param   operationType  DOCUMENT ME!
   * @param   itemType       DOCUMENT ME!
   * @param   description    DOCUMENT ME!
   * @param   temporaryId    DOCUMENT ME!
   * @param   originalId     DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  SandBoxItem addSandBoxItem(Long sandBox, SandBoxOperationType operationType, SandBoxItemType itemType,
    String description, Long temporaryId, Long originalId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   sbox              DOCUMENT ME!
   * @param   operationType     DOCUMENT ME!
   * @param   itemType          DOCUMENT ME!
   * @param   description       DOCUMENT ME!
   * @param   groupDescription  DOCUMENT ME!
   * @param   temporaryId       DOCUMENT ME!
   * @param   originalId        DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  SandBoxItem addSandBoxItem(Long sbox, SandBoxOperationType operationType, SandBoxItemType itemType,
    String description, String groupDescription, Long temporaryId, Long originalId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  sandBoxItem  DOCUMENT ME!
   */
  void delete(SandBoxItem sandBoxItem);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   sandBoxId  DOCUMENT ME!
   * @param   groupName  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<SandBoxItem> retrieveByGroupName(Long sandBoxId, String groupName);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   id  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  SandBoxItem retrieveById(Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   sandBoxId   DOCUMENT ME!
   * @param   type        DOCUMENT ME!
   * @param   tempItemId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  SandBoxItem retrieveBySandboxAndTemporaryItemId(Long sandBoxId, SandBoxItemType type, Long tempItemId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   sandBox   DOCUMENT ME!
   * @param   itemType  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<SandBoxItem> retrieveSandBoxItemsByTypeForSandbox(Long sandBox, SandBoxItemType itemType);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   sandBox           DOCUMENT ME!
   * @param   sandBoxItemTypes  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<SandBoxItem> retrieveSandBoxItemsByTypesForSandbox(Long sandBox, List<SandBoxItemType> sandBoxItemTypes);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   sandBox  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<SandBoxItem> retrieveSandBoxItemsForSandbox(Long sandBox);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   sandBoxItem  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  SandBoxItem updateSandBoxItem(SandBoxItem sandBoxItem);

} // end interface SandBoxItemDao
