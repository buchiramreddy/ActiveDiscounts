/*
 * Copyright 2008-2012 the original author or authors.
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

package com.activediscounts.controller.catalog;

import javax.servlet.http.HttpServletRequest;

import org.broadleafcommerce.core.web.controller.catalog.BroadleafRatingsController;
import org.broadleafcommerce.core.web.controller.catalog.ReviewForm;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Controller public class RatingsController extends BroadleafRatingsController {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request  DOCUMENT ME!
   * @param   model    DOCUMENT ME!
   * @param   itemId   DOCUMENT ME!
   * @param   form     DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @RequestMapping(
    value  = "/reviews/product/{itemId}",
    method = RequestMethod.POST
  )
  public String reviewItem(HttpServletRequest request, Model model,
    @PathVariable("itemId") String itemId,
    @ModelAttribute("reviewForm") ReviewForm form) {
    return super.reviewItem(request, model, form, itemId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request  DOCUMENT ME!
   * @param   model    DOCUMENT ME!
   * @param   itemId   DOCUMENT ME!
   * @param   form     DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @RequestMapping(
    value  = "/reviews/product/{itemId}",
    method = RequestMethod.GET
  )
  public String viewReviewForm(HttpServletRequest request, Model model,
    @PathVariable("itemId") String itemId,
    @ModelAttribute("reviewForm") ReviewForm form) {
    return super.viewReviewForm(request, model, form, itemId);
  }


} // end class RatingsController
