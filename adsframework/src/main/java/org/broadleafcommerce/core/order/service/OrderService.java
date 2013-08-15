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

package org.broadleafcommerce.core.order.service;

import java.util.List;

import org.broadleafcommerce.core.offer.domain.OfferCode;
import org.broadleafcommerce.core.offer.service.exception.OfferMaxUseExceededException;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.service.call.GiftWrapOrderItemRequest;
import org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO;
import org.broadleafcommerce.core.order.service.exception.AddToCartException;
import org.broadleafcommerce.core.order.service.exception.RemoveFromCartException;
import org.broadleafcommerce.core.order.service.exception.UpdateCartException;
import org.broadleafcommerce.core.order.service.type.OrderStatus;
import org.broadleafcommerce.core.payment.domain.PaymentInfo;
import org.broadleafcommerce.core.payment.domain.Referenced;
import org.broadleafcommerce.core.payment.service.type.PaymentInfoType;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;

import org.broadleafcommerce.profile.core.domain.Customer;


/**
 * The general interface for interacting with shopping carts and completed Orders. In Broadleaf Commerce, a Cart and an
 * Order are the same thing. A "cart" becomes an order after it has been submitted.
 *
 * <p>Most of the methods in this order are used to modify the cart. However, it is also common to use this service for
 * "named" orders (aka wishlists).</p>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface OrderService {
  /**
   * Creates a new Order for the given customer. Generally, you will want to use the customer that is on the current
   * request, which can be grabbed by utilizing the CustomerState utility class.
   *
   * <p>The default Broadleaf implementation of this method will provision a new Order in the database and set the
   * current customer as the owner of the order. If the customer has an email address associated with their profile,
   * that will be copied as well. If the customer is a new, anonymous customer, his username will be set to his database
   * id.</p>
   *
   * @see     org.broadleafcommerce.profile.web.core.CustomerState#getCustomer()
   *
   * @param   customer  DOCUMENT ME!
   *
   * @return  the newly created order
   */
  Order createNewCartForCustomer(Customer customer);

  /**
   * Creates a new Order for the given customer with the given name. Typically, this represents a "wishlist" order that
   * the customer can save but not check out with.
   *
   * @param   name      the wishlist name
   * @param   customer  DOCUMENT ME!
   *
   * @return  the newly created named order
   */
  Order createNamedOrderForCustomer(String name, Customer customer);

  /**
   * Looks up an Order by the given customer and a specified order name.
   *
   * <p>This is typically used to retrieve a "wishlist" order.</p>
   *
   * @see     #createNamedOrderForCustomer(String name, org.broadleafcommerce.profile.core.domain.Customer customer)
   *
   * @param   name      DOCUMENT ME!
   * @param   customer  DOCUMENT ME!
   *
   * @return  the named order requested
   */
  Order findNamedOrderForCustomer(String name, Customer customer);

  /**
   * Looks up an Order by its database id.
   *
   * @param   orderId  DOCUMENT ME!
   *
   * @return  the requested Order
   */
  Order findOrderById(Long orderId);

  /**
   * Looks up the current shopping cart for the customer. Note that a shopping cart is simply an Order with OrderStatus
   * = IN_PROCESS. If for some reason the given customer has more than one current IN_PROCESS Order, the default
   * Broadleaf implementation will return the first match found. Furthermore, also note that the current shopping cart
   * for a customer must never be named -- an Order with a non-null "name" property indicates that it is a wishlist and
   * not a shopping cart.
   *
   * @param   customer  DOCUMENT ME!
   *
   * @return  the current shopping cart for the customer
   */
  Order findCartForCustomer(Customer customer);

  /**
   * Looks up all Orders for the specified customer, regardless of current OrderStatus.
   *
   * @param   customer  DOCUMENT ME!
   *
   * @return  the requested Orders
   */
  List<Order> findOrdersForCustomer(Customer customer);

  /**
   * Looks up all Orders for the specified customer that are in the specified OrderStatus.
   *
   * @param   customer  DOCUMENT ME!
   * @param   status    DOCUMENT ME!
   *
   * @return  the requested Orders
   */
  List<Order> findOrdersForCustomer(Customer customer, OrderStatus status);

  /**
   * Looks up Orders and returns the order matching the given orderNumber.
   *
   * @param   orderNumber  DOCUMENT ME!
   *
   * @return  the requested Order
   */
  Order findOrderByOrderNumber(String orderNumber);

  /**
   * Returns all PaymentInfo objects that are associated with the given order.
   *
   * @param   order  DOCUMENT ME!
   *
   * @return  the list of all PaymentInfo objects
   */
  List<PaymentInfo> findPaymentInfosForOrder(Order order);

  /**
   * Associates a given PaymentInfo with an Order. Note that it is acceptable for the securePaymentInfo to be null. For
   * example, if the secure credit card details are handled by a third party, a given application may never have
   * associated securePaymentInfos
   *
   * @param   order              DOCUMENT ME!
   * @param   payment            DOCUMENT ME!
   * @param   securePaymentInfo  - null if it doesn't exist
   *
   * @return  the persisted version of the PaymentInfo
   */
  PaymentInfo addPaymentToOrder(Order order, PaymentInfo payment, Referenced securePaymentInfo);

  /**
   * Persists the given order to the database. If the priceOrder flag is set to true, the pricing workflow will execute
   * before the order is written to the database. Generally, you will want to price the order in every request scope
   * once, and preferrably on the last call to save() for performance reasons.
   *
   * <p>However, if you have logic that depends on the Order being priced, there are no issues with saving as many times
   * as necessary.</p>
   *
   * @param   order       DOCUMENT ME!
   * @param   priceOrder  DOCUMENT ME!
   *
   * @return  the persisted Order, which will be a different instance than the Order passed in
   *
   * @throws  org.broadleafcommerce.core.pricing.service.exception.PricingException
   */
  Order save(Order order, Boolean priceOrder) throws PricingException;

  /**
   * Deletes the given order. Note that the default Broadleaf implementation in OrderServiceImpl will actually remove
   * the Order instance from the database.
   *
   * @param  order  DOCUMENT ME!
   */
  void cancelOrder(Order order);

  /**
   * Adds the given OfferCode to the order. Optionally prices the order as well
   *
   * @param   order       DOCUMENT ME!
   * @param   offerCode   DOCUMENT ME!
   * @param   priceOrder  DOCUMENT ME!
   *
   * @return  the modified Order
   *
   * @throws  org.broadleafcommerce.core.pricing.service.exception.PricingException
   * @throws  org.broadleafcommerce.core.offer.service.exception.OfferMaxUseExceededException
   */
  Order addOfferCode(Order order, OfferCode offerCode, boolean priceOrder) throws PricingException,
    OfferMaxUseExceededException;

  /**
   * Remove the given OfferCode for the order. Optionally prices the order as well.
   *
   * @param   order       DOCUMENT ME!
   * @param   offerCode   DOCUMENT ME!
   * @param   priceOrder  DOCUMENT ME!
   *
   * @return  the modified Order
   *
   * @throws  org.broadleafcommerce.core.pricing.service.exception.PricingException
   */
  Order removeOfferCode(Order order, OfferCode offerCode, boolean priceOrder) throws PricingException;

  /**
   * Removes all offer codes for the given order. Optionally prices the order as well.
   *
   * @param   order       DOCUMENT ME!
   * @param   priceOrder  DOCUMENT ME!
   *
   * @return  the modified Order
   *
   * @throws  org.broadleafcommerce.core.pricing.service.exception.PricingException
   */
  Order removeAllOfferCodes(Order order, boolean priceOrder) throws PricingException;

  /**
   * The null order is the default order for all customers when they initially enter the site. Upon the first addition
   * of a product to a cart, a non-null order will be provisioned for the user.
   *
   * @see     org.broadleafcommerce.core.order.domain.NullOrderImpl for more information
   *
   * @return  a shared, static, unmodifiable NullOrder
   */
  Order getNullOrder();

  /**
   * Whether or not like-items will be automatically merged.
   *
   * @see     #setAutomaticallyMergeLikeItems(boolean)
   *
   * @return  whether or not like-items will be automatically merged
   */
  boolean getAutomaticallyMergeLikeItems();

  /**
   * When set to true, the system when items are added to the cart, they will automatically be merged. For example, when
   * a user adds an item to the cart and then adds the item again, the item will have its quantity changed to 2 instead
   * of the cart containing two separate items.
   *
   * <p>If this logic needs to be more complex, it is possible to extend the behavior by overriding
   * OrderOfferProcessor.buildIdentifier().</p>
   *
   * @param  automaticallyMergeLikeItems  DOCUMENT ME!
   */
  void setAutomaticallyMergeLikeItems(boolean automaticallyMergeLikeItems);

  /**
   * Changes the OrderStatus to SUBMITTED.
   *
   * @param   order  to confirm
   *
   * @return  the order that was confirmed
   */
  Order confirmOrder(Order order);

  /**
   * Looks through the given order and returns the latest added OrderItem that matches on the skuId and productId.
   * Generally, this is used to retrieve the OrderItem that was just added to the cart. The default Broadleaf
   * implementation will attempt to match on skuId first, and failing that, it will look at the productId.
   *
   * <p>Note that the behavior is slightly undeterministic in the case that {@link setAutomaticallyMergeLikeItems} is
   * set to true and the last added sku matches on a previously added sku. In this case, the sku that has the merged
   * items would be returned, so the total quantity of the OrderItem might not match exactly what was just added.</p>
   *
   * @param   order      DOCUMENT ME!
   * @param   skuId      DOCUMENT ME!
   * @param   productId  DOCUMENT ME!
   *
   * @return  the best matching OrderItem with highest index in the list of OrderItems in the order
   */
  OrderItem findLastMatchingItem(Order order, Long skuId, Long productId);

  /**
   * Adds a GiftWrapItem to the order based on the itemRequest. A GiftWrapItem is a product (for example, a "Gift Box
   * with Red Ribbon") that contains a list of OrderItems that should be wrapped by this GiftWrapItem.
   *
   * <p>The OrderItems must already exist and belong to an order before they are able to be wrapped by the GiftWrapItem
   * </p>
   *
   * @param   order        DOCUMENT ME!
   * @param   itemRequest  DOCUMENT ME!
   * @param   priceOrder   DOCUMENT ME!
   *
   * @return  the GiftWrapItem instance that was created and attached to the order
   *
   * @throws  org.broadleafcommerce.core.pricing.service.exception.PricingException
   */
  OrderItem addGiftWrapItemToOrder(Order order, GiftWrapOrderItemRequest itemRequest, boolean priceOrder)
    throws PricingException;

  /**
   * Initiates the addItem workflow that will attempt to add the given quantity of the specified item to the Order. The
   * item to be added can be determined in a few different ways. For example, the SKU can be specified directly or it
   * can be determine based on a Product and potentially some specified ProductOptions for that given product.
   *
   * <p>The minimum required parameters for OrderItemRequest are: productId and quantity or alternatively, skuId and
   * quantity</p>
   *
   * <p>When priceOrder is false, the system will not reprice the order. This is more performant in cases such as bulk
   * adds where the repricing could be done for the last item only.</p>
   *
   * <p>This method differs from the
   * {@link #addItemWithPriceOverrides(Long, org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO, boolean)}
   * in that it will clear any values set on the
   * {@link org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO} for the overrideSalePrice or
   * overrideRetailPrice.</p>
   *
   * <p>This design is intended to ensure that override pricing is not called by mistake. Implementors should use this
   * method when no manual price overrides are allowed.</p>
   *
   * @see     org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO
   *
   * @param   orderId              DOCUMENT ME!
   * @param   orderItemRequestDTO  DOCUMENT ME!
   * @param   priceOrder           DOCUMENT ME!
   *
   * @return  the order the item was added to
   *
   * @throws  org.broadleafcommerce.core.workflow.WorkflowException
   * @throws  Throwable
   * @throws  AddToCartException                                     DOCUMENT ME!
   */
  Order addItem(Long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder) throws AddToCartException;

  /**
   * Initiates the addItem workflow that will attempt to add the given quantity of the specified item to the Order. The
   * item to be added can be determined in a few different ways. For example, the SKU can be specified directly or it
   * can be determine based on a Product and potentially some specified ProductOptions for that given product.
   *
   * <p>The minimum required parameters for OrderItemRequest are: productId and quantity or alternatively, skuId and
   * quantity</p>
   *
   * <p>When priceOrder is false, the system will not reprice the order. This is more performant in cases such as bulk
   * adds where the repricing could be done for the last item only.</p>
   *
   * <p>As opposed to the
   * {@link #addItem(Long, org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO, boolean)} method, this
   * method allows the passed in {@link org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO} to contain
   * values for the overrideSale or overrideRetail price fields.</p>
   *
   * <p>This design is intended to ensure that override pricing is not called by mistake. Implementors should use this
   * method when manual price overrides are allowed.</p>
   *
   * @see     org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO
   *
   * @param   orderId              DOCUMENT ME!
   * @param   orderItemRequestDTO  DOCUMENT ME!
   * @param   priceOrder           DOCUMENT ME!
   *
   * @return  the order the item was added to
   *
   * @throws  org.broadleafcommerce.core.workflow.WorkflowException
   * @throws  Throwable
   * @throws  AddToCartException                                     DOCUMENT ME!
   */
  Order addItemWithPriceOverrides(Long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder)
    throws AddToCartException;

  /**
   * Initiates the updateItem workflow that will attempt to update the item quantity for the specified OrderItem in the
   * given Order. The new quantity is specified in the OrderItemRequestDTO
   *
   * <p>Minimum required parameters for OrderItemRequest: orderItemId, quantity</p>
   *
   * @see     org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO
   *
   * @param   orderId              DOCUMENT ME!
   * @param   orderItemRequestDTO  DOCUMENT ME!
   * @param   priceOrder           DOCUMENT ME!
   *
   * @return  the order the item was added to
   *
   * @throws  org.broadleafcommerce.core.order.service.exception.UpdateCartException
   * @throws  org.broadleafcommerce.core.order.service.exception.RemoveFromCartException
   */
  Order updateItemQuantity(Long orderId, OrderItemRequestDTO orderItemRequestDTO, boolean priceOrder)
    throws UpdateCartException, RemoveFromCartException;

  /**
   * Initiates the removeItem workflow that will attempt to remove the specified OrderItem from the given Order.
   *
   * @see     org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO
   *
   * @param   orderId      DOCUMENT ME!
   * @param   orderItemId  DOCUMENT ME!
   * @param   priceOrder   DOCUMENT ME!
   *
   * @return  the order the item was added to
   *
   * @throws  org.broadleafcommerce.core.order.service.exception.RemoveFromCartException
   */
  Order removeItem(Long orderId, Long orderItemId, boolean priceOrder) throws RemoveFromCartException;

  /**
   * Whether items will be removed from the wishlist when added to the cart.
   *
   * @see     #setMoveNamedOrderItems(boolean)
   *
   * @return  whether items will be removed from the wishlist when added to the cart
   */
  boolean isMoveNamedOrderItems();

  /**
   * Determines whether or not items will be removed from the named order (wishlist) when they are moved to the
   * Customer's current cart.
   *
   * @param  moveNamedOrderItems  DOCUMENT ME!
   */
  void setMoveNamedOrderItems(boolean moveNamedOrderItems);

  /**
   * Whether empty wishlists will be deleted automatically.
   *
   * @see     #setDeleteEmptyNamedOrders(boolean)
   *
   * @return  whether empty wishlists will be deleted automatically
   */
  boolean isDeleteEmptyNamedOrders();

  /**
   * Sets whether or not to delete named orders once all items have been removed.
   *
   * @param  deleteEmptyNamedOrders  DOCUMENT ME!
   */
  void setDeleteEmptyNamedOrders(boolean deleteEmptyNamedOrders);

  /**
   * Adds the passed in orderItem to the current cart for the same Customer that owns the named order. This method will
   * remove the item from the wishlist based on whether the {@link setMoveNamedOrderItems} flag is set.
   *
   * <p>Note that if an item was in a wishlist and is no longer able to be added to the cart, the item will still be
   * removed from the wishlist.</p>
   *
   * <p>Note that this method does not change the association of the OrderItems to the new order -- instead, those
   * OrderItems is completely removed and a new OrderItem that mirrors it is created.</p>
   *
   * @param   namedOrder  DOCUMENT ME!
   * @param   orderItem   DOCUMENT ME!
   * @param   priceOrder  DOCUMENT ME!
   *
   * @return  the cart with the requested orderItem added to it
   *
   * @throws  org.broadleafcommerce.core.order.service.exception.RemoveFromCartException
   * @throws  org.broadleafcommerce.core.order.service.exception.AddToCartException
   */
  Order addItemFromNamedOrder(Order namedOrder, OrderItem orderItem, boolean priceOrder) throws RemoveFromCartException,
    AddToCartException;

  /**
   * This method performs the same function as addItemFromNamedOrder(Order, OrderItem, boolean) except that instead of
   * adding all of the quantity from the named order to the cart, it will only add/move the specific quantity requested.
   *
   * @see     #addItemFromNamedOrder(org.broadleafcommerce.core.order.domain.Order,org.broadleafcommerce.core.order.domain.OrderItem,
   *          boolean)
   *
   * @param   namedOrder  DOCUMENT ME!
   * @param   orderItem   DOCUMENT ME!
   * @param   quantity    DOCUMENT ME!
   * @param   priceOrder  DOCUMENT ME!
   *
   * @return  the cart with the requested orderItem added to it
   *
   * @throws  org.broadleafcommerce.core.order.service.exception.RemoveFromCartException
   * @throws  org.broadleafcommerce.core.order.service.exception.AddToCartException
   * @throws  org.broadleafcommerce.core.order.service.exception.UpdateCartException
   */
  Order addItemFromNamedOrder(Order namedOrder, OrderItem orderItem, int quantity, boolean priceOrder)
    throws RemoveFromCartException, AddToCartException, UpdateCartException;

  /**
   * Adds all orderItems to the current cart from the same Customer that owns the named order. This method will remove
   * the item from the wishlist based on whether the {@link setMoveNamedOrderItems} flag is set.
   *
   * <p>Note that any items that are in the wishlist but are no longer able to be added to a cart will still be removed
   * from the wishlist.</p>
   *
   * <p>Note that this method does not change the association of the OrderItems to the new order -- instead, those
   * OrderItems is completely removed and a new OrderItem that mirrors it is created.</p>
   *
   * @param   namedOrder  DOCUMENT ME!
   * @param   priceOrder  DOCUMENT ME!
   *
   * @return  adds all orderItems to the current cart from the same Customer that owns the named order.
   *
   * @throws  org.broadleafcommerce.core.order.service.exception.RemoveFromCartException
   * @throws  org.broadleafcommerce.core.order.service.exception.AddToCartException
   */
  Order addAllItemsFromNamedOrder(Order namedOrder, boolean priceOrder) throws RemoveFromCartException,
    AddToCartException;

  /**
   * Deletes all the Payment Info's on the order.
   *
   * @param  order  DOCUMENT ME!
   */
  void removeAllPaymentsFromOrder(Order order);

  /**
   * Deletes the Payment Info of the passed in type from the order Note that this method will also delete any associated
   * Secure Payment Infos if necessary.
   *
   * @param  order            DOCUMENT ME!
   * @param  paymentInfoType  DOCUMENT ME!
   */
  void removePaymentsFromOrder(Order order, PaymentInfoType paymentInfoType);

  /**
   * Deletes the Payment Info from the order. Note that this method will also delete any associated Secure Payment Infos
   * if necessary.
   *
   * @param  order        DOCUMENT ME!
   * @param  paymentInfo  DOCUMENT ME!
   */
  void removePaymentFromOrder(Order order, PaymentInfo paymentInfo);

  /**
   * DOCUMENT ME!
   *
   * @param  cart  DOCUMENT ME!
   */
  void deleteOrder(Order cart);

  /**
   * DOCUMENT ME!
   *
   * @param   orderId     DOCUMENT ME!
   * @param   priceOrder  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  RemoveFromCartException  DOCUMENT ME!
   */
  Order removeInactiveItems(Long orderId, boolean priceOrder) throws RemoveFromCartException;

} // end interface OrderService
