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
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.openadmin.web.controller.entity.AdminBasicEntityController;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Handles admin operations for the {@link org.broadleafcommerce.core.offer.domain.Offer} entity. Certain Offer fields
 * should only render when specific values are set for other fields; we provide the support for that in this controller.
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
@Controller("blAdminOfferController")
@RequestMapping("/" + AdminOfferController.SECTION_KEY)
public class AdminOfferController extends AdminBasicEntityController {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected static final String SECTION_KEY = "offer";

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.web.controller.entity.AdminBasicEntityController#viewAddEntityForm(javax.servlet.http.HttpServletRequest,
   *       javax.servlet.http.HttpServletResponse, org.springframework.ui.Model, java.util.Map, java.lang.String)
   */
  @Override
  @RequestMapping(
    value  = "/add",
    method = RequestMethod.GET
  )
  public String viewAddEntityForm(HttpServletRequest request, HttpServletResponse response, Model model,
    @PathVariable Map<String, String> pathVars,
    @RequestParam(defaultValue = "") String entityType) throws Exception {
    String view = super.viewAddEntityForm(request, response, model, pathVars, entityType);
    model.addAttribute("additionalControllerClasses", "offer-form");

    return view;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Offer field visibility is dependent on other fields in the entity. Mark the form with the appropriate class so that
   * the Javascript will know to handle this form.
   *
   * @param   request   DOCUMENT ME!
   * @param   response  DOCUMENT ME!
   * @param   model     DOCUMENT ME!
   * @param   pathVars  DOCUMENT ME!
   * @param   id        DOCUMENT ME!
   *
   * @return  offer field visibility is dependent on other fields in the entity.
   *
   * @throws  Exception  DOCUMENT ME!
   */
  @Override
  @RequestMapping(
    value  = "/{id}",
    method = RequestMethod.GET
  )
  public String viewEntityForm(HttpServletRequest request, HttpServletResponse response, Model model,
    @PathVariable Map<String, String> pathVars,
    @PathVariable(value = "id") String id) throws Exception {
    String view = super.viewEntityForm(request, response, model, pathVars, id);
    model.addAttribute("additionalControllerClasses", "offer-form");

    return view;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

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

} // end class AdminOfferController
