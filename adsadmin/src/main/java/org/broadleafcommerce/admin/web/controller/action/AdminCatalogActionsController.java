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

package org.broadleafcommerce.admin.web.controller.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.admin.server.service.AdminCatalogService;

import org.broadleafcommerce.openadmin.web.controller.AdminAbstractController;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Controller that responds to custom catalog actions. These would normally be hooked up in customized controllers like
 * {@link org.broadleafcommerce.admin.web.controller.entity.AdminProductController}
 *
 * @author   Phillip Verheyden (phillipuniverse)
 * @see      {@link org.broadleafcommerce.admin.web.controller.entity.AdminProductController}
 * @version  $Revision$, $Date$
 */
@Controller("blAdminCatalogActionsController")
public class AdminCatalogActionsController extends AdminAbstractController {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blAdminCatalogService")
  protected AdminCatalogService adminCatalogService;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Invokes a separate service to generate a list of Skus for a particular {@link Product} and that {@link Product}'s
   * Product Options.
   *
   * @param   request        DOCUMENT ME!
   * @param   response       DOCUMENT ME!
   * @param   model          DOCUMENT ME!
   * @param   productId      DOCUMENT ME!
   * @param   skusFieldName  DOCUMENT ME!
   *
   * @return  invokes a separate service to generate a list of Skus for a particular {@link Product} and that
   *          {@link Product}'s Product Options.
   */
  @RequestMapping(
    value    = "product/{productId}/{skusFieldName}/generate-skus",
    method   = RequestMethod.GET,
    produces = "application/json"
  )
  @ResponseBody public Map<String, Object> generateSkus(HttpServletRequest request, HttpServletResponse response,
    Model model,
    @PathVariable(value = "productId") Long productId,
    @PathVariable(value = "skusFieldName") String skusFieldName) {
    HashMap<String, Object> result        = new HashMap<String, Object>();
    Integer                 skusGenerated = adminCatalogService.generateSkusFromProduct(productId);

    // TODO: Externalize these messages to property files
    if (skusGenerated == 0) {
      result.put("message",
        "No Skus were generated. It is likely that each product option value permutation "
        + "already has a Sku associated with it");
    } else if (skusGenerated == -1) {
      result.put("message", "This product has no Product Options configured to generate Skus from");
    } else {
      result.put("message", skusGenerated + " Skus have been generated from the configured product options");
    }

    String url = request.getRequestURL().toString();
    url = url.substring(0, url.indexOf("/generate-skus"));

    result.put("skusGenerated", skusGenerated);
    result.put("listGridUrl", url);

    return result;
  }
} // end class AdminCatalogActionsController
