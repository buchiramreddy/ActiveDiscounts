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

package org.broadleafcommerce.core.order.service.legacy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.broadleafcommerce.core.order.domain.BundleOrderItem;
import org.broadleafcommerce.core.order.domain.DiscreteOrderItem;
import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.service.OrderService;
import org.broadleafcommerce.core.order.service.call.BundleOrderItemRequest;
import org.broadleafcommerce.core.order.service.call.DiscreteOrderItemRequest;
import org.broadleafcommerce.core.order.service.call.FulfillmentGroupRequest;
import org.broadleafcommerce.core.order.service.call.GiftWrapOrderItemRequest;
import org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO;
import org.broadleafcommerce.core.order.service.exception.ItemNotFoundException;
import org.broadleafcommerce.core.payment.domain.PaymentInfo;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;

import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.domain.Customer;


/**
 * This legacy interface should no longer be used as of 2.0.
 *
 * <p>The new interface and implementation are OrderService and OrderServiceImpl</p>
 *
 * @deprecated  DOCUMENT ME!
 * @author      $author$
 * @version     $Revision$, $Date$
 */
@Deprecated public interface LegacyOrderService extends OrderService {
  /**
   * DOCUMENT ME!
   *
   * @param   order  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  FulfillmentGroup findDefaultFulfillmentGroupForOrder(Order order);

  /**
   * Note: This method will automatically associate the given <b>order</b> to the given <b>itemRequest</b> such that
   * then resulting {@link org.broadleafcommerce.core.order.domain.OrderItem} will already have an
   * {@link org.broadleafcommerce.core.order.domain.Order} associated to it.
   *
   * @param   order        DOCUMENT ME!
   * @param   itemRequest  DOCUMENT ME!
   *
   * @return  note:
   *
   * @throws  org.broadleafcommerce.core.pricing.service.exception.PricingException
   */
  OrderItem addGiftWrapItemToOrder(Order order, GiftWrapOrderItemRequest itemRequest) throws PricingException;

  /**
   * Used to create dynamic bundles groupings of order items. Typically not used with ProductBundles which should
   * instead call addProductToOrder.
   *
   * <p>Prices the order after adding the bundle.</p>
   *
   * @param   order        DOCUMENT ME!
   * @param   itemRequest  DOCUMENT ME!
   *
   * @return  used to create dynamic bundles groupings of order items.
   *
   * @throws  org.broadleafcommerce.core.pricing.service.exception.PricingException
   */
  OrderItem addBundleItemToOrder(Order order, BundleOrderItemRequest itemRequest) throws PricingException;

  /**
   * Used to create dynamic bundles groupings of order items. Typically not used with ProductBundles which should
   * instead call addProductToOrder.
   *
   * <p>Prices the order after adding the bundle if priceOrder = true. Clients may wish to perform many cart operations
   * without pricing and then use priceOrder = true on the last operation to avoid exercising the pricing engine in a
   * batch order update mode.</p>
   *
   * <p>NOTE: this will automatically associate the given <b>order</b> to the given <b>itemRequest</b> such that the
   * resulting {@link org.broadleafcommerce.core.order.domain.OrderItem} will already have the
   * {@link org.broadleafcommerce.core.order.domain.Order} associated to it</p>
   *
   * @param   order        DOCUMENT ME!
   * @param   itemRequest  DOCUMENT ME!
   * @param   priceOrder   DOCUMENT ME!
   *
   * @return  used to create dynamic bundles groupings of order items.
   *
   * @throws  org.broadleafcommerce.core.pricing.service.exception.PricingException
   */
  OrderItem addBundleItemToOrder(Order order, BundleOrderItemRequest itemRequest, boolean priceOrder)
    throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   order    DOCUMENT ME!
   * @param   payment  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PaymentInfo addPaymentToOrder(Order order, PaymentInfo payment);

  /**
   * DOCUMENT ME!
   *
   * @param   fulfillmentGroupRequest  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  FulfillmentGroup addFulfillmentGroupToOrder(FulfillmentGroupRequest fulfillmentGroupRequest) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   fulfillmentGroupRequest  DOCUMENT ME!
   * @param   priceOrder               DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  FulfillmentGroup addFulfillmentGroupToOrder(FulfillmentGroupRequest fulfillmentGroupRequest, boolean priceOrder)
    throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   order             DOCUMENT ME!
   * @param   fulfillmentGroup  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  FulfillmentGroup addFulfillmentGroupToOrder(Order order, FulfillmentGroup fulfillmentGroup) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   order             DOCUMENT ME!
   * @param   fulfillmentGroup  DOCUMENT ME!
   * @param   priceOrder        DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  FulfillmentGroup addFulfillmentGroupToOrder(Order order, FulfillmentGroup fulfillmentGroup, boolean priceOrder)
    throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   item              DOCUMENT ME!
   * @param   fulfillmentGroup  DOCUMENT ME!
   * @param   quantity          DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  FulfillmentGroup addItemToFulfillmentGroup(OrderItem item, FulfillmentGroup fulfillmentGroup, int quantity)
    throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   item              DOCUMENT ME!
   * @param   fulfillmentGroup  DOCUMENT ME!
   * @param   quantity          DOCUMENT ME!
   * @param   priceOrder        DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  FulfillmentGroup addItemToFulfillmentGroup(OrderItem item, FulfillmentGroup fulfillmentGroup, int quantity,
    boolean priceOrder) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   item              DOCUMENT ME!
   * @param   fulfillmentGroup  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  FulfillmentGroup addItemToFulfillmentGroup(OrderItem item, FulfillmentGroup fulfillmentGroup) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   item              DOCUMENT ME!
   * @param   fulfillmentGroup  DOCUMENT ME!
   * @param   priceOrder        DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  FulfillmentGroup addItemToFulfillmentGroup(OrderItem item, FulfillmentGroup fulfillmentGroup, boolean priceOrder)
    throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   order             DOCUMENT ME!
   * @param   item              DOCUMENT ME!
   * @param   fulfillmentGroup  DOCUMENT ME!
   * @param   quantity          DOCUMENT ME!
   * @param   priceOrder        DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  FulfillmentGroup addItemToFulfillmentGroup(Order order, OrderItem item, FulfillmentGroup fulfillmentGroup,
    int quantity, boolean priceOrder) throws PricingException;

  /**
   * Delegates to the fully parametrized method with priceOrder = true.
   *
   * @param   order  DOCUMENT ME!
   * @param   item   DOCUMENT ME!
   *
   * @throws  org.broadleafcommerce.core.order.service.exception.ItemNotFoundException
   * @throws  org.broadleafcommerce.core.pricing.service.exception.PricingException
   */
  void updateItemQuantity(Order order, OrderItem item) throws ItemNotFoundException, PricingException;

  /**
   * Updates the quantity and reprices the order. Removes the orderItem if the quantity is updated to 0 (or less).
   *
   * @param   order       DOCUMENT ME!
   * @param   item        DOCUMENT ME!
   * @param   priceOrder  DOCUMENT ME!
   *
   * @throws  org.broadleafcommerce.core.order.service.exception.ItemNotFoundException
   * @throws  org.broadleafcommerce.core.pricing.service.exception.PricingException
   */
  void updateItemQuantity(Order order, OrderItem item, boolean priceOrder) throws ItemNotFoundException,
    PricingException;

  /**
   * From the given OrderItemRequestDTO object, this will look through the order's DiscreteOrderItems to find the item
   * with the matching orderItemId and update this item's quantity with the value of the quantity field in the
   * OrderItemRequestDTO.
   *
   * @param   order                DOCUMENT ME!
   * @param   orderItemRequestDTO  DOCUMENT ME!
   *
   * @throws  org.broadleafcommerce.core.order.service.exception.ItemNotFoundException
   * @throws  org.broadleafcommerce.core.pricing.service.exception.PricingException
   */
  void updateItemQuantity(Order order, OrderItemRequestDTO orderItemRequestDTO) throws ItemNotFoundException,
    PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   order             DOCUMENT ME!
   * @param   fulfillmentGroup  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  void removeFulfillmentGroupFromOrder(Order order, FulfillmentGroup fulfillmentGroup) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   order             DOCUMENT ME!
   * @param   fulfillmentGroup  DOCUMENT ME!
   * @param   priceOrder        DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  void removeFulfillmentGroupFromOrder(Order order, FulfillmentGroup fulfillmentGroup, boolean priceOrder)
    throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   order  DOCUMENT ME!
   * @param   item   DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  Order removeItemFromOrder(Order order, OrderItem item) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   order       DOCUMENT ME!
   * @param   item        DOCUMENT ME!
   * @param   priceOrder  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  Order removeItemFromOrder(Order order, OrderItem item, boolean priceOrder) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param  name      DOCUMENT ME!
   * @param  customer  DOCUMENT ME!
   */
  void removeNamedOrderForCustomer(String name, Customer customer);

  /**
   * DOCUMENT ME!
   *
   * @param   order  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  void removeAllFulfillmentGroupsFromOrder(Order order) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   order       DOCUMENT ME!
   * @param   priceOrder  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  void removeAllFulfillmentGroupsFromOrder(Order order, boolean priceOrder) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   order  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<PaymentInfo> readPaymentInfosForOrder(Order order);

  /**
   * DOCUMENT ME!
   *
   * @param   orderId  DOCUMENT ME!
   * @param   itemId   DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  Order removeItemFromOrder(Long orderId, Long itemId) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   orderId     DOCUMENT ME!
   * @param   itemId      DOCUMENT ME!
   * @param   priceOrder  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  Order removeItemFromOrder(Long orderId, Long itemId, boolean priceOrder) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   order    DOCUMENT ME!
   * @param   address  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  FulfillmentGroup createDefaultFulfillmentGroup(Order order, Address address);

  /**
   * Adds an item to the passed in order.
   *
   * <p>The orderItemRequest can be sparsely populated.</p>
   *
   * <p>When priceOrder is false, the system will not reprice the order. This is more performant in cases such as bulk
   * adds where the repricing could be done for the last item only.</p>
   *
   * @param   orderId              DOCUMENT ME!
   *
   * @see     org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO
   *
   * @param   orderItemRequestDTO  DOCUMENT ME!
   * @param   priceOrder           DOCUMENT ME!
   *
   * @return  adds an item to the passed in order.
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  Order addItemToOrder(Long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder)
    throws PricingException;


  /**
   * Not typically used in versions since 1.7. See:
   * {@link #addItemToOrder(Long, org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO, boolean)}
   *
   * @param   orderId     DOCUMENT ME!
   * @param   skuId       DOCUMENT ME!
   * @param   productId   DOCUMENT ME!
   * @param   categoryId  DOCUMENT ME!
   * @param   quantity    DOCUMENT ME!
   *
   * @return  not typically used in versions since 1.7.
   */
  DiscreteOrderItemRequest createDiscreteOrderItemRequest(Long orderId, Long skuId, Long productId, Long categoryId,
    Integer quantity);

  /**
   * Adds the passed in name/value pair to the order-item. If the attribute already exists, then it is updated with the
   * new value.
   *
   * <p>If the value passed in is null or empty string and the attribute exists, it is removed from the order item.</p>
   *
   * <p>You may wish to set priceOrder to false if performing set of cart operations to avoid the expense of exercising
   * the pricing engine until you are ready to finalize pricing after adding the last item.</p>
   *
   * @param   order            DOCUMENT ME!
   * @param   item             DOCUMENT ME!
   * @param   attributeValues  DOCUMENT ME!
   * @param   priceOrder       DOCUMENT ME!
   *
   * @return  adds the passed in name/value pair to the order-item.
   *
   * @throws  ItemNotFoundException  DOCUMENT ME!
   * @throws  PricingException       DOCUMENT ME!
   */
  Order addOrUpdateOrderItemAttributes(Order order, OrderItem item, Map<String, String> attributeValues,
    boolean priceOrder) throws ItemNotFoundException, PricingException;

  /**
   * Adds the passed in name/value pair to the order-item. If the attribute already exists, then it is updated with the
   * new value.
   *
   * <p>If the value passed in is null and the attribute exists, it is removed from the order item.</p>
   *
   * <p>You may wish to set priceOrder to false if performing set of cart operations to avoid the expense of exercising
   * the pricing engine until you are ready to finalize pricing after adding the last item.</p>
   *
   * @param   order          DOCUMENT ME!
   * @param   item           DOCUMENT ME!
   * @param   attributeName  DOCUMENT ME!
   * @param   priceOrder     DOCUMENT ME!
   *
   * @return  adds the passed in name/value pair to the order-item.
   *
   * @throws  ItemNotFoundException  DOCUMENT ME!
   * @throws  PricingException       DOCUMENT ME!
   */
  Order removeOrderItemAttribute(Order order, OrderItem item, String attributeName, boolean priceOrder)
    throws ItemNotFoundException, PricingException;

  /**
   * DOCUMENT ME!
   *
   * @deprecated  Call addItemToOrder(Long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder)
   *
   * @param       order        DOCUMENT ME!
   * @param       itemRequest  DOCUMENT ME!
   *
   * @return      DOCUMENT ME!
   *
   * @throws      org.broadleafcommerce.core.pricing.service.exception.PricingException
   */
  @Deprecated OrderItem addDiscreteItemToOrder(Order order, DiscreteOrderItemRequest itemRequest)
    throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @deprecated  Call addItemToOrder(Long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder)
   *
   *              <p>Due to cart merging and gathering requirements, the item returned is not an actual cart item.</p>
   *
   *              <p>NOTE: this will automatically associate the given <b>order</b> to the given <b>itemRequest</b> such
   *              that the resulting {@link org.broadleafcommerce.core.order.domain.OrderItem} will already have the
   *              {@link org.broadleafcommerce.core.order.domain.Order} associated to it</p>
   *
   * @param       order        DOCUMENT ME!
   * @param       itemRequest  DOCUMENT ME!
   * @param       priceOrder   DOCUMENT ME!
   *
   * @return      DOCUMENT ME!
   *
   * @throws      org.broadleafcommerce.core.pricing.service.exception.PricingException
   */
  @Deprecated OrderItem addDiscreteItemToOrder(Order order, DiscreteOrderItemRequest itemRequest, boolean priceOrder)
    throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @deprecated  Call addItemToOrder(Long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder)
   *
   * @param       orderId     DOCUMENT ME!
   * @param       skuId       DOCUMENT ME!
   * @param       productId   DOCUMENT ME!
   * @param       categoryId  DOCUMENT ME!
   * @param       quantity    DOCUMENT ME!
   *
   * @return      DOCUMENT ME!
   *
   * @throws      org.broadleafcommerce.core.pricing.service.exception.PricingException
   */
  @Deprecated OrderItem addSkuToOrder(Long orderId, Long skuId, Long productId, Long categoryId, Integer quantity)
    throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @deprecated  Call addItemToOrder(Long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder)
   *
   * @param       orderId              DOCUMENT ME!
   * @param       skuId                DOCUMENT ME!
   * @param       productId            DOCUMENT ME!
   * @param       categoryId           DOCUMENT ME!
   * @param       quantity             DOCUMENT ME!
   * @param       orderItemAttributes  DOCUMENT ME!
   *
   * @return      DOCUMENT ME!
   *
   * @throws      org.broadleafcommerce.core.pricing.service.exception.PricingException
   */
  @Deprecated OrderItem addSkuToOrder(Long orderId, Long skuId, Long productId, Long categoryId, Integer quantity,
    Map<String, String> orderItemAttributes) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @deprecated  Call addItemToOrder(Long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder)
   *
   * @param       orderId     DOCUMENT ME!
   * @param       skuId       DOCUMENT ME!
   * @param       productId   DOCUMENT ME!
   * @param       categoryId  DOCUMENT ME!
   * @param       quantity    DOCUMENT ME!
   * @param       priceOrder  DOCUMENT ME!
   *
   * @return      DOCUMENT ME!
   *
   * @throws      org.broadleafcommerce.core.pricing.service.exception.PricingException
   */
  @Deprecated OrderItem addSkuToOrder(Long orderId, Long skuId, Long productId, Long categoryId, Integer quantity,
    boolean priceOrder) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @deprecated  Call addItemToOrder(Long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder)
   *
   * @param       orderId              DOCUMENT ME!
   * @param       skuId                DOCUMENT ME!
   * @param       productId            DOCUMENT ME!
   * @param       categoryId           DOCUMENT ME!
   * @param       quantity             DOCUMENT ME!
   * @param       priceOrder           DOCUMENT ME!
   * @param       orderItemAttributes  DOCUMENT ME!
   *
   * @return      DOCUMENT ME!
   *
   * @throws      org.broadleafcommerce.core.pricing.service.exception.PricingException
   */
  @Deprecated OrderItem addSkuToOrder(Long orderId, Long skuId, Long productId, Long categoryId, Integer quantity,
    boolean priceOrder, Map<String, String> orderItemAttributes) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @deprecated  Call addItemToOrder(Long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder)
   *
   * @param       order         DOCUMENT ME!
   * @param       newOrderItem  DOCUMENT ME!
   *
   * @return      DOCUMENT ME!
   *
   * @throws      org.broadleafcommerce.core.pricing.service.exception.PricingException
   */
  @Deprecated OrderItem addOrderItemToOrder(Order order, OrderItem newOrderItem) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @deprecated  Call addItemToOrder(Long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder)
   *
   * @param       order         DOCUMENT ME!
   * @param       newOrderItem  DOCUMENT ME!
   * @param       priceOrder    DOCUMENT ME!
   *
   * @return      DOCUMENT ME!
   *
   * @throws      org.broadleafcommerce.core.pricing.service.exception.PricingException
   */
  @Deprecated OrderItem addOrderItemToOrder(Order order, OrderItem newOrderItem, boolean priceOrder)
    throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @deprecated  Call addItemToOrder(Long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder)
   *
   * @param       order                     DOCUMENT ME!
   * @param       itemRequest               DOCUMENT ME!
   * @param       skuPricingConsiderations  DOCUMENT ME!
   *
   * @return      DOCUMENT ME!
   *
   * @throws      org.broadleafcommerce.core.pricing.service.exception.PricingException
   */
  @Deprecated OrderItem addDynamicPriceDiscreteItemToOrder(Order order, DiscreteOrderItemRequest itemRequest,
    @SuppressWarnings("rawtypes") HashMap skuPricingConsiderations) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @deprecated  Call addItemToOrder(Long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder)
   *
   * @param       order                     DOCUMENT ME!
   * @param       itemRequest               DOCUMENT ME!
   * @param       skuPricingConsiderations  DOCUMENT ME!
   * @param       priceOrder                DOCUMENT ME!
   *
   * @return      DOCUMENT ME!
   *
   * @throws      org.broadleafcommerce.core.pricing.service.exception.PricingException
   */
  @Deprecated OrderItem addDynamicPriceDiscreteItemToOrder(Order order, DiscreteOrderItemRequest itemRequest,
    @SuppressWarnings("rawtypes") HashMap skuPricingConsiderations, boolean priceOrder) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   order         DOCUMENT ME!
   * @param   bundle        DOCUMENT ME!
   * @param   newOrderItem  DOCUMENT ME!
   * @param   priceOrder    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  OrderItem addOrderItemToBundle(Order order, BundleOrderItem bundle, DiscreteOrderItem newOrderItem,
    boolean priceOrder) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   order       DOCUMENT ME!
   * @param   bundle      DOCUMENT ME!
   * @param   item        DOCUMENT ME!
   * @param   priceOrder  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  Order removeItemFromBundle(Order order, BundleOrderItem bundle, OrderItem item, boolean priceOrder)
    throws PricingException;

} // end interface LegacyOrderService
