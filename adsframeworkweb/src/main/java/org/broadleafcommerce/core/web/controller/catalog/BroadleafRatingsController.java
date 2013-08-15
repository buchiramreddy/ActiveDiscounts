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

package org.broadleafcommerce.core.web.controller.catalog;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletRequest;

import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.service.CatalogService;
import org.broadleafcommerce.core.rating.domain.ReviewDetail;
import org.broadleafcommerce.core.rating.service.RatingService;
import org.broadleafcommerce.core.rating.service.type.RatingType;

import org.broadleafcommerce.profile.web.core.CustomerState;

import org.springframework.ui.Model;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class BroadleafRatingsController {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blCatalogService")
  protected CatalogService catalogService;

  /** DOCUMENT ME! */
  protected String formView = "catalog/partials/review";

  /** DOCUMENT ME! */
  @Resource(name = "blRatingService")
  protected RatingService ratingService;

  /** DOCUMENT ME! */
  protected String        successView = "catalog/partials/reviewSuccessful";

  //~ Methods ----------------------------------------------------------------------------------------------------------


  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getFormView() {
    return formView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getSuccessView() {
    return successView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request  DOCUMENT ME!
   * @param   model    DOCUMENT ME!
   * @param   form     DOCUMENT ME!
   * @param   itemId   DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String reviewItem(HttpServletRequest request, Model model, ReviewForm form, String itemId) {
    ratingService.reviewItem(itemId, RatingType.PRODUCT, CustomerState.getCustomer(), form.getRating(),
      form.getReviewText());
    model.addAttribute("reviewForm", form);

    return getSuccessView();
  }

  //~ ------------------------------------------------------------------------------------------------------------------


  /**
   * DOCUMENT ME!
   *
   * @param   request  DOCUMENT ME!
   * @param   model    DOCUMENT ME!
   * @param   form     DOCUMENT ME!
   * @param   itemId   DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String viewReviewForm(HttpServletRequest request, Model model, ReviewForm form, String itemId) {
    Product product = catalogService.findProductById(Long.valueOf(itemId));
    form.setProduct(product);

    ReviewDetail reviewDetail = ratingService.readReviewByCustomerAndItem(CustomerState.getCustomer(), itemId);

    if (reviewDetail != null) {
      form.setReviewText(reviewDetail.getReviewText());
      form.setRating(reviewDetail.getRatingDetail().getRating());
    }

    model.addAttribute("reviewForm", form);

    return getFormView();
  }

} // end class BroadleafRatingsController
