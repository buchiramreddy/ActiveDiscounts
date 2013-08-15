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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.sandbox.dao.SandBoxDao;
import org.broadleafcommerce.common.sandbox.domain.SandBox;
import org.broadleafcommerce.common.sandbox.domain.SandBoxType;
import org.broadleafcommerce.common.site.domain.Site;

import org.broadleafcommerce.openadmin.server.dao.SandBoxItemDao;
import org.broadleafcommerce.openadmin.server.domain.SandBoxAction;
import org.broadleafcommerce.openadmin.server.domain.SandBoxActionImpl;
import org.broadleafcommerce.openadmin.server.domain.SandBoxActionType;
import org.broadleafcommerce.openadmin.server.domain.SandBoxItem;
import org.broadleafcommerce.openadmin.server.domain.SandBoxItemListener;
import org.broadleafcommerce.openadmin.server.security.domain.AdminUser;
import org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Service(value = "blSandBoxService")
public class SandBoxServiceImpl implements SandBoxService {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log LOG = LogFactory.getLog(SandBoxServiceImpl.class);

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blAdminSecurityService")
  protected AdminSecurityService adminSecurityService;

  /** DOCUMENT ME! */
  @Resource(name = "blSandBoxDao")
  protected SandBoxDao sandBoxDao;

  /** DOCUMENT ME! */
  @Resource(name = "blSandBoxItemDao")
  protected SandBoxItemDao sandBoxItemDao;

  /** DOCUMENT ME! */
  @Resource(name = "blSandboxItemListeners")
  protected List<SandBoxItemListener> sandboxItemListeners = new ArrayList<SandBoxItemListener>();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.persistence.SandBoxService#createSandBox(org.broadleafcommerce.common.site.domain.Site,
   *       java.lang.String, org.broadleafcommerce.common.sandbox.domain.SandBoxType)
   */
  @Override public synchronized SandBox createSandBox(Site site, String sandBoxName, SandBoxType sandBoxType) {
    return sandBoxDao.createSandBox(site, sandBoxName, sandBoxType);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<SandBoxItemListener> getSandboxItemListeners() {
    return sandboxItemListeners;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.persistence.SandBoxService#promoteAllSandBoxItems(org.broadleafcommerce.common.sandbox.domain.SandBox,
   *       java.lang.String)
   */
  @Override
  @Transactional("blTransactionManager")
  public void promoteAllSandBoxItems(SandBox fromSandBox, String comment) {
    promoteSelectedItems(fromSandBox, comment,
      new ArrayList<SandBoxItem>(sandBoxItemDao.retrieveSandBoxItemsForSandbox(fromSandBox.getId())));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.persistence.SandBoxService#promoteSelectedItems(org.broadleafcommerce.common.sandbox.domain.SandBox,
   *       java.lang.String, java.util.List)
   */
  @Override
  @Transactional("blTransactionManager")
  public void promoteSelectedItems(SandBox fromSandBox, String comment, List<SandBoxItem> sandBoxItems) {
    SandBox       destinationSandBox = determineNextSandBox(fromSandBox);
    SandBoxAction action             = createSandBoxAction(SandBoxActionType.PROMOTE, comment);

    for (SandBoxItem sandBoxItem : sandBoxItems) {
      action.addSandBoxItem(sandBoxItem);

      if ((destinationSandBox == null) || SandBoxType.PRODUCTION.equals(destinationSandBox)) {
        sandBoxItem.setArchivedFlag(true);
      }

      if (destinationSandBox != null) {
        sandBoxItem.setSandBoxId(destinationSandBox.getId());
      } else {
        sandBoxItem.setSandBoxId(null);
      }

      if (sandBoxItem.getOriginalSandBoxId() == null) {
        sandBoxItem.setOriginalSandBoxId(fromSandBox.getId());
      }

      sandBoxItem.addSandBoxAction(action);

      for (SandBoxItemListener listener : sandboxItemListeners) {
        listener.itemPromoted(sandBoxItem, destinationSandBox);
      }
    }
  } // end method promoteSelectedItems

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.persistence.SandBoxService#rejectAllSandBoxItems(org.broadleafcommerce.common.sandbox.domain.SandBox,
   *       org.broadleafcommerce.common.sandbox.domain.SandBox, java.lang.String)
   */
  @Override
  @Transactional("blTransactionManager")
  public void rejectAllSandBoxItems(SandBox originalSandBox, SandBox sandBox, String comment) {
    List<SandBoxItem> items        = new ArrayList<SandBoxItem>();
    List<SandBoxItem> currentItems = sandBoxItemDao.retrieveSandBoxItemsForSandbox(sandBox.getId());

    for (SandBoxItem item : currentItems) {
      if (item.getOriginalSandBoxId().equals(originalSandBox.getId())) {
        items.add(item);
      }
    }

    rejectSelectedSandBoxItems(sandBox, comment, items);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.persistence.SandBoxService#rejectSelectedSandBoxItems(org.broadleafcommerce.common.sandbox.domain.SandBox,
   *       java.lang.String, java.util.List)
   */
  @Override
  @Transactional("blTransactionManager")
  public void rejectSelectedSandBoxItems(SandBox fromSandBox, String comment, List<SandBoxItem> sandBoxItems) {
    for (SandBoxItem item : sandBoxItems) {
      if (item.getOriginalSandBoxId() == null) {
        throw new IllegalArgumentException("Cannot reject a SandBoxItem whose originalSandBox member is null");
      }
    }

    SandBoxAction action = createSandBoxAction(SandBoxActionType.REJECT, comment);

    SandBox originalSandBox = null;

    for (SandBoxItem sandBoxItem : sandBoxItems) {
      action.addSandBoxItem(sandBoxItem);

      if (sandBoxItem.getOriginalSandBoxId() != null) {
        if ((originalSandBox != null) && !originalSandBox.getId().equals(sandBoxItem.getOriginalItemId())) {
          originalSandBox = sandBoxDao.retrieve(sandBoxItem.getOriginalItemId());
        }
      } else {
        originalSandBox = null;
      }

      for (SandBoxItemListener listener : sandboxItemListeners) {
        listener.itemRejected(sandBoxItem, originalSandBox);
      }

      sandBoxItem.addSandBoxAction(action);
      sandBoxItem.setSandBoxId(sandBoxItem.getOriginalSandBoxId());
      sandBoxItem.setOriginalSandBoxId(null);


    }
  } // end method rejectSelectedSandBoxItems

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.persistence.SandBoxService#retrieveApprovalSandBox(org.broadleafcommerce.common.sandbox.domain.SandBox)
   */
  @Override public SandBox retrieveApprovalSandBox(SandBox sandBox) {
    final String APPROVAL_SANDBOX_NAME = "Approval";
    SandBox      approvalSandbox       = retrieveSandBox(sandBox.getSite(), APPROVAL_SANDBOX_NAME,
        SandBoxType.APPROVAL);

    // If the approval sandbox doesn't exist, create it.
    if (approvalSandbox == null) {
      approvalSandbox = createSandBox(sandBox.getSite(), APPROVAL_SANDBOX_NAME, SandBoxType.APPROVAL);
    }

    return approvalSandbox;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.persistence.SandBoxService#retrieveSandBox(org.broadleafcommerce.common.site.domain.Site,
   *       java.lang.String, org.broadleafcommerce.common.sandbox.domain.SandBoxType)
   */
  @Override public SandBox retrieveSandBox(Site site, String sandBoxName, SandBoxType sandBoxType) {
    return sandBoxDao.retrieveNamedSandBox(site, sandBoxType, sandBoxName);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.persistence.SandBoxService#retrieveSandboxById(java.lang.Long)
   */
  @Override public SandBox retrieveSandboxById(Long sandboxId) {
    return sandBoxDao.retrieve(sandboxId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.persistence.SandBoxService#retrieveUserSandBox(org.broadleafcommerce.common.site.domain.Site,
   *       org.broadleafcommerce.openadmin.server.security.domain.AdminUser)
   */
  @Override public SandBox retrieveUserSandBox(Site site, AdminUser adminUser) {
    SandBox userSandbox;

    if (adminUser.getOverrideSandBox() != null) {
      userSandbox = adminUser.getOverrideSandBox();
    } else {
      userSandbox = retrieveSandBox(site, adminUser.getLogin(), SandBoxType.USER);

      if (userSandbox == null) {
        userSandbox = createSandBox(site, adminUser.getLogin(), SandBoxType.USER);
      }
    }

    return userSandbox;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.persistence.SandBoxService#revertAllSandBoxItems(org.broadleafcommerce.common.sandbox.domain.SandBox,
   *       org.broadleafcommerce.common.sandbox.domain.SandBox)
   */
  @Override
  @Transactional("blTransactionManager")
  public void revertAllSandBoxItems(SandBox originalSandBox, SandBox sandBox) {
    List<SandBoxItem> items        = new ArrayList<SandBoxItem>();
    List<SandBoxItem> sandBoxItems = sandBoxItemDao.retrieveSandBoxItemsForSandbox(sandBox.getId());

    for (SandBoxItem item : sandBoxItems) {
      if (originalSandBox.equals(sandBox)
            || ((item.getOriginalSandBoxId() != null) && (originalSandBox != null)
              && item.getOriginalSandBoxId().equals(originalSandBox.getId()))) {
        items.add(item);
      }
    }

    revertSelectedSandBoxItems(sandBox, items);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.persistence.SandBoxService#revertSelectedSandBoxItems(org.broadleafcommerce.common.sandbox.domain.SandBox,
   *       java.util.List)
   */
  @Override
  @Transactional("blTransactionManager")
  public void revertSelectedSandBoxItems(SandBox fromSandBox, List<SandBoxItem> sandBoxItems) {
    for (SandBoxItem item : sandBoxItems) {
      if (item.getArchivedFlag()) {
        throw new IllegalArgumentException("Cannot revert an archived SandBoxItem");
      }
    }

    SandBoxAction action = createSandBoxAction(SandBoxActionType.REVERT, null);

    for (SandBoxItem sandBoxItem : sandBoxItems) {
      action.addSandBoxItem(sandBoxItem);

      for (SandBoxItemListener listener : sandboxItemListeners) {
        listener.itemReverted(sandBoxItem);
      }

      // We're done with this sandBoxItem
      sandBoxItem.setArchivedFlag(true);
      sandBoxItem.addSandBoxAction(action);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------


  /**
   * @see  org.broadleafcommerce.openadmin.server.service.persistence.SandBoxService#schedulePromotionForSandBox(org.broadleafcommerce.common.sandbox.domain.SandBox,
   *       java.util.Calendar)
   */
  @Override public void schedulePromotionForSandBox(SandBox sandBox, Calendar calendar) { }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.persistence.SandBoxService#schedulePromotionForSandBoxItems(java.util.List,
   *       java.util.Calendar)
   */
  @Override public void schedulePromotionForSandBoxItems(List<SandBoxItem> sandBoxItems, Calendar calendar) { }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  sandboxItemListeners  DOCUMENT ME!
   */
  public void setSandboxItemListeners(List<SandBoxItemListener> sandboxItemListeners) {
    this.sandboxItemListeners = sandboxItemListeners;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   type     DOCUMENT ME!
   * @param   comment  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected SandBoxAction createSandBoxAction(SandBoxActionType type, String comment) {
    SandBoxAction action = new SandBoxActionImpl();
    action.setActionType(type);
    action.setComment(comment);

    return action;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   sandBox  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  IllegalArgumentException  DOCUMENT ME!
   */
  protected SandBox determineNextSandBox(SandBox sandBox) {
    if (SandBoxType.USER.equals(sandBox.getSandBoxType())) {
      return retrieveApprovalSandBox(sandBox);
    } else if (SandBoxType.APPROVAL.equals(sandBox.getSandBoxType())) {
      if (sandBox.getSite() != null) {
        return sandBox.getSite().getProductionSandbox();
      } else {
        // null is the production sandbox for a single tenant application
        return null;
      }
    }

    throw new IllegalArgumentException("Unable to determine next sandbox for " + sandBox);
  }
} // end class SandBoxServiceImpl
