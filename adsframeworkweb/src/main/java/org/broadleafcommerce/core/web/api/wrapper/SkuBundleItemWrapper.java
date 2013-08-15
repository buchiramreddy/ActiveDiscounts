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

package org.broadleafcommerce.core.web.api.wrapper;

import javax.servlet.http.HttpServletRequest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.catalog.domain.SkuBundleItem;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "skuBundleItem")
public class SkuBundleItemWrapper extends BaseWrapper implements APIWrapper<SkuBundleItem> {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @XmlElement protected Boolean active;

  /** DOCUMENT ME! */
  @XmlElement protected Long bundleId;

  /** DOCUMENT ME! */
  @XmlElement protected String description;

  /** DOCUMENT ME! */
  @XmlElement protected Long id;

  /** DOCUMENT ME! */
  @XmlElement protected String longDescription;

  /** DOCUMENT ME! */
  @XmlElement protected String name;

  /** DOCUMENT ME! */
  @XmlElement protected Integer quantity;

  /** DOCUMENT ME! */
  @XmlElement protected Money retailPrice;

  /** DOCUMENT ME! */
  @XmlElement protected Money salePrice;

  /** DOCUMENT ME! */
  @XmlElement protected SkuWrapper sku;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapDetails(org.broadleafcommerce.core.catalog.domain.SkuBundleItem,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapDetails(SkuBundleItem model, HttpServletRequest request) {
    this.id              = model.getId();
    this.quantity        = model.getQuantity();
    this.salePrice       = model.getSalePrice();
    this.retailPrice     = model.getRetailPrice();
    this.bundleId        = model.getBundle().getId();
    this.name            = model.getSku().getName();
    this.description     = model.getSku().getDescription();
    this.longDescription = model.getSku().getLongDescription();
    this.active          = model.getSku().isActive();
    // this.sku = (SkuWrapper)context.getBean(SkuWrapper.class.getName());
    // this.sku.wrap(model.getSku(), request);

  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapSummary(org.broadleafcommerce.core.catalog.domain.SkuBundleItem,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapSummary(SkuBundleItem model, HttpServletRequest request) {
    wrapDetails(model, request);
  }
} // end class SkuBundleItemWrapper
