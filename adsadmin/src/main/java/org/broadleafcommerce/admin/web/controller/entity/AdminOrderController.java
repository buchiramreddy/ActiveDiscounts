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

package org.broadleafcommerce.admin.web.controller.entity;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;

import org.broadleafcommerce.common.exception.ServiceException;

import org.broadleafcommerce.openadmin.web.controller.entity.AdminBasicEntityController;
import org.broadleafcommerce.openadmin.web.form.component.ListGrid;
import org.broadleafcommerce.openadmin.web.form.entity.EntityForm;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;


/**
 * Handles admin operations for the {@link Order} entity.
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
@Controller("blAdminOrderController")
@RequestMapping("/" + AdminOrderController.SECTION_KEY)
public class AdminOrderController extends AdminBasicEntityController {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected static final String SECTION_KEY = "order";

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.web.controller.AdminAbstractController#getSectionKey(java.util.Map)
   */
  @Override protected String getSectionKey(Map<String, String> pathVars) {
    // allow external links to work for ToOne items
    if (super.getSectionKey(pathVars) != null) {
      return super.getSectionKey(pathVars);
    }

    return SECTION_KEY;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.web.controller.entity.AdminBasicEntityController#showViewUpdateCollection(javax.servlet.http.HttpServletRequest,
   *       org.springframework.ui.Model, java.util.Map, java.lang.String, java.lang.String, java.lang.String,
   *       java.lang.String)
   */
  @Override protected String showViewUpdateCollection(HttpServletRequest request, Model model,
    Map<String, String> pathVars,
    String id, String collectionField, String collectionItemId, String modalHeaderType) throws ServiceException {
    String returnPath = super.showViewUpdateCollection(request, model, pathVars, id, collectionField, collectionItemId,
        modalHeaderType);

    if ("orderItems".equals(collectionField)) {
      EntityForm ef = (EntityForm) model.asMap().get("entityForm");

      ListGrid adjustmentsGrid = ef.findListGrid("orderItemAdjustments");

      if ((adjustmentsGrid != null) && CollectionUtils.isEmpty(adjustmentsGrid.getRecords())) {
        ef.removeListGrid("orderItemAdjustments");
      }

      ListGrid priceDetailsGrid = ef.findListGrid("orderItemPriceDetails");

      if ((priceDetailsGrid != null) && CollectionUtils.isEmpty(priceDetailsGrid.getRecords())) {
        ef.removeListGrid("orderItemPriceDetails");
      }
    }

    return returnPath;
  }

} // end class AdminOrderController
