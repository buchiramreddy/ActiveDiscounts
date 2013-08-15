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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.order.domain.BundleOrderItem;
import org.broadleafcommerce.core.order.domain.DiscreteOrderItem;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.domain.OrderItemAttribute;
import org.broadleafcommerce.core.order.domain.OrderItemPriceDetail;
import org.broadleafcommerce.core.order.domain.OrderItemQualifier;


/**
 * This is a JAXB wrapper around OrderItem. For simplicity and most use cases, this wrapper only serializes attributes
 * of <code>DiscreteOrderItem</code> This wrapper should be extended for BundledOrderItems etc...
 *
 * <p>User: Elbert Bautista Date: 4/10/12</p>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "orderItem")
public class OrderItemWrapper extends BaseWrapper implements APIWrapper<OrderItem> {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  // This will only be poulated if this is a BundleOrderItem
  /** DOCUMENT ME! */
  @XmlElement(name = "bundleItem")
  @XmlElementWrapper(name = "bundleItems")
  protected List<OrderItemWrapper> bundleItems;
  //

  /** DOCUMENT ME! */
  @XmlElement protected Long categoryId;

  /** DOCUMENT ME! */
  @XmlElement protected Long id;

  /** DOCUMENT ME! */
  protected Boolean isBundle = Boolean.FALSE;

  /** DOCUMENT ME! */
  @XmlElement protected String name;

  /** DOCUMENT ME! */
  @XmlElement protected Long orderId;

  /** DOCUMENT ME! */
  @XmlElement(name = "orderItemAttribute")
  @XmlElementWrapper(name = "orderItemAttributes")
  protected List<OrderItemAttributeWrapper> orderItemAttributes;

  /** DOCUMENT ME! */
  @XmlElement(name = "orderItemPriceDetails")
  @XmlElementWrapper(name = "orderItemPriceDetails")
  protected List<OrderItemPriceDetailWrapper> orderItemPriceDetails;

  /** DOCUMENT ME! */
  @XmlElement protected Long productId;

  /** DOCUMENT ME! */
  @XmlElement(name = "qualifier")
  @XmlElementWrapper(name = "qualifiers")
  protected List<OrderItemQualifierWrapper> qualifiers;

  /** DOCUMENT ME! */
  @XmlElement protected Integer quantity;

  /** DOCUMENT ME! */
  @XmlElement protected Money retailPrice;

  /** DOCUMENT ME! */
  @XmlElement protected Money salePrice;

  /** DOCUMENT ME! */
  @XmlElement protected Long skuId;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapDetails(org.broadleafcommerce.core.order.domain.OrderItem,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapDetails(OrderItem model, HttpServletRequest request) {
    this.id          = model.getId();
    this.name        = model.getName();
    this.quantity    = model.getQuantity();
    this.orderId     = model.getOrder().getId();
    this.retailPrice = model.getRetailPrice();
    this.salePrice   = model.getSalePrice();

    if (model.getCategory() != null) {
      this.categoryId = model.getCategory().getId();
    }

    if ((model.getOrderItemAttributes() != null) && !model.getOrderItemAttributes().isEmpty()) {
      Map<String, OrderItemAttribute> itemAttributes = model.getOrderItemAttributes();
      this.orderItemAttributes = new ArrayList<OrderItemAttributeWrapper>();

      Set<String> keys = itemAttributes.keySet();

      for (String key : keys) {
        OrderItemAttributeWrapper orderItemAttributeWrapper = (OrderItemAttributeWrapper) context.getBean(
            OrderItemAttributeWrapper.class.getName());
        orderItemAttributeWrapper.wrapSummary(itemAttributes.get(key), request);
        this.orderItemAttributes.add(orderItemAttributeWrapper);
      }
    }

    if ((model.getOrderItemPriceDetails() != null) && !model.getOrderItemPriceDetails().isEmpty()) {
      this.orderItemPriceDetails = new ArrayList<OrderItemPriceDetailWrapper>();

      for (OrderItemPriceDetail orderItemPriceDetail : model.getOrderItemPriceDetails()) {
        OrderItemPriceDetailWrapper orderItemPriceDetailWrapper = (OrderItemPriceDetailWrapper) context.getBean(
            OrderItemPriceDetailWrapper.class.getName());
        orderItemPriceDetailWrapper.wrapSummary(orderItemPriceDetail, request);
        this.orderItemPriceDetails.add(orderItemPriceDetailWrapper);
      }
    }

    if (model instanceof DiscreteOrderItem) {
      DiscreteOrderItem doi = (DiscreteOrderItem) model;
      this.skuId     = doi.getSku().getId();
      this.productId = doi.getProduct().getId();
      this.isBundle  = false;
    } else if (model instanceof BundleOrderItem) {
      BundleOrderItem boi = (BundleOrderItem) model;
      this.skuId     = boi.getSku().getId();
      this.productId = boi.getProduct().getId();
      this.isBundle  = true;

      // Wrap up all the discrete order items for this bundle order item
      List<DiscreteOrderItem> discreteItems = boi.getDiscreteOrderItems();

      if ((discreteItems != null) && !discreteItems.isEmpty()) {
        this.bundleItems = new ArrayList<OrderItemWrapper>();

        for (DiscreteOrderItem doi : discreteItems) {
          OrderItemWrapper doiWrapper = (OrderItemWrapper) context.getBean(OrderItemWrapper.class.getName());
          doiWrapper.wrapSummary(doi, request);
          this.bundleItems.add(doiWrapper);
        }
      }

    } // end if-else

    if ((model.getOrderItemQualifiers() != null) && !model.getOrderItemQualifiers().isEmpty()) {
      this.qualifiers = new ArrayList<OrderItemQualifierWrapper>();

      for (OrderItemQualifier qualifier : model.getOrderItemQualifiers()) {
        OrderItemQualifierWrapper qualifierWrapper = (OrderItemQualifierWrapper) context.getBean(
            OrderItemQualifierWrapper.class.getName());
        qualifierWrapper.wrapSummary(qualifier, request);
        this.qualifiers.add(qualifierWrapper);
      }
    }
  } // end method wrapDetails

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapSummary(org.broadleafcommerce.core.order.domain.OrderItem,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapSummary(OrderItem model, HttpServletRequest request) {
    wrapDetails(model, request);
  }
} // end class OrderItemWrapper
