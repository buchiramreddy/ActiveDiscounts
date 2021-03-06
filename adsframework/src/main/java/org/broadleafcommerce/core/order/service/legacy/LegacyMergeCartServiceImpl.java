/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.core.order.service.legacy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import org.broadleafcommerce.core.offer.dao.OfferDao;
import org.broadleafcommerce.core.offer.domain.OfferCode;
import org.broadleafcommerce.core.offer.domain.OfferInfo;
import org.broadleafcommerce.core.offer.service.OfferService;
import org.broadleafcommerce.core.order.domain.BundleOrderItem;
import org.broadleafcommerce.core.order.domain.DiscreteOrderItem;
import org.broadleafcommerce.core.order.domain.GiftWrapOrderItem;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.service.FulfillmentGroupService;
import org.broadleafcommerce.core.order.service.MergeCartService;
import org.broadleafcommerce.core.order.service.OrderItemService;
import org.broadleafcommerce.core.order.service.OrderService;
import org.broadleafcommerce.core.order.service.call.MergeCartResponse;
import org.broadleafcommerce.core.order.service.call.ReconstructCartResponse;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;

import org.broadleafcommerce.profile.core.domain.Customer;


/**
 * These methods used to exist in LegacyCartService.
 *
 * @author   apazzolini
 * @version  $Revision$, $Date$
 */
@Deprecated public class LegacyMergeCartServiceImpl implements MergeCartService {
  @Resource(name = "blOfferDao")
  private OfferDao offerDao;

  @Resource(name = "blOrderService")
  private OrderService orderService;

  @Resource(name = "blOrderItemService")
  private OrderItemService orderItemService;

  @Resource(name = "blOfferService")
  private OfferService offerService;

  /** DOCUMENT ME! */
  @Resource(name = "blFulfillmentGroupService")
  protected FulfillmentGroupService fulfillmentGroupService;

  /**
   * @see  org.broadleafcommerce.core.order.service.MergeCartService#mergeCart(org.broadleafcommerce.profile.core.domain.Customer,
   *       org.broadleafcommerce.core.order.domain.Order)
   */
  @Override public MergeCartResponse mergeCart(Customer customer, Order anonymousCart) throws PricingException {
    return mergeCart(customer, anonymousCart, true);
  }

  /**
   * @see  org.broadleafcommerce.core.order.service.MergeCartService#reconstructCart(org.broadleafcommerce.profile.core.domain.Customer)
   */
  @Override public ReconstructCartResponse reconstructCart(Customer customer) throws PricingException {
    return reconstructCart(customer, true);
  }

  /**
   * @see  org.broadleafcommerce.core.order.service.MergeCartService#mergeCart(org.broadleafcommerce.profile.core.domain.Customer,
   *       org.broadleafcommerce.core.order.domain.Order, boolean)
   */
  @Override public MergeCartResponse mergeCart(Customer customer, Order anonymousCart, boolean priceOrder)
    throws PricingException {
    MergeCartResponse mergeCartResponse = new MergeCartResponse();

    // reconstruct cart items (make sure they are valid)
    ReconstructCartResponse reconstructCartResponse = reconstructCart(customer, false);
    mergeCartResponse.setRemovedItems(reconstructCartResponse.getRemovedItems());

    Order customerCart = reconstructCartResponse.getOrder();

    if ((anonymousCart != null) && (customerCart != null) && anonymousCart.getId().equals(customerCart.getId())) {
      /*
       * Set merged to false if the cart ids are equal (cookied customer
       * logs in).
       */
      mergeCartResponse.setMerged(false);
    } else {
      /*
       * Set the response to merged if the saved cart has any items
       * available to merge in.
       */
      mergeCartResponse.setMerged((customerCart != null) && (customerCart.getOrderItems().size() > 0));
    }


    // add anonymous cart items (make sure they are valid)
    if ((anonymousCart != null) && ((customerCart == null) || !customerCart.getId().equals(anonymousCart.getId()))) {
      if ((anonymousCart != null) && (anonymousCart.getOrderItems() != null)
            && !anonymousCart.getOrderItems().isEmpty()) {
        if (customerCart == null) {
          customerCart = orderService.createNewCartForCustomer(customer);
        }

        Map<OrderItem, OrderItem> oldNewItemMap = new HashMap<OrderItem, OrderItem>();
        customerCart = mergeRegularOrderItems(anonymousCart, mergeCartResponse, customerCart, oldNewItemMap);
        customerCart = mergeOfferCodes(anonymousCart, customerCart);
        customerCart = removeExpiredGiftWrapOrderItems(mergeCartResponse, customerCart, oldNewItemMap);
        customerCart = mergeGiftWrapOrderItems(mergeCartResponse, customerCart, oldNewItemMap);

        orderService.cancelOrder(anonymousCart);
      }
    }

    // copy the customer's email to this order, overriding any previously set email
    if ((customerCart != null) && StringUtils.isNotBlank(customer.getEmailAddress())) {
      customerCart.setEmailAddress(customer.getEmailAddress());
      customerCart = orderService.save(customerCart, priceOrder);
    }

    mergeCartResponse.setOrder(customerCart);

    return mergeCartResponse;
  } // end method mergeCart

  /**
   * @see  org.broadleafcommerce.core.order.service.MergeCartService#reconstructCart(org.broadleafcommerce.profile.core.domain.Customer,
   *       boolean)
   */
  @Override public ReconstructCartResponse reconstructCart(Customer customer, boolean priceOrder)
    throws PricingException {
    ReconstructCartResponse reconstructCartResponse = new ReconstructCartResponse();
    Order                   customerCart            = orderService.findCartForCustomer(customer);

    if (customerCart != null) {
      List<OrderItem> itemsToRemove = new ArrayList<OrderItem>();

      for (OrderItem orderItem : customerCart.getOrderItems()) {
        if (orderItem instanceof DiscreteOrderItem) {
          DiscreteOrderItem discreteOrderItem = (DiscreteOrderItem) orderItem;

          if (discreteOrderItem.getSku().getActiveStartDate() != null) {
            if (!discreteOrderItem.getSku().isActive(
                    discreteOrderItem.getProduct(),
                    orderItem.getCategory())) {
              itemsToRemove.add(orderItem);
            }
          } else {
            if (!discreteOrderItem.getProduct().isActive() || !orderItem.getCategory().isActive()) {
              itemsToRemove.add(orderItem);
            }
          }
        } else if (orderItem instanceof BundleOrderItem) {
          BundleOrderItem bundleOrderItem = (BundleOrderItem) orderItem;
          boolean         removeBundle    = false;

          for (DiscreteOrderItem discreteOrderItem : bundleOrderItem.getDiscreteOrderItems()) {
            if (discreteOrderItem.getSku().getActiveStartDate() != null) {
              if (!discreteOrderItem.getSku().isActive(
                      discreteOrderItem.getProduct(),
                      orderItem.getCategory())) {
                /*
                 * Bundle has an inactive item in it -- remove the
                 * whole bundle
                 */
                removeBundle = true;

                break;
              }
            } else {
              if (!discreteOrderItem.getProduct().isActive() || !orderItem.getCategory().isActive()) {
                removeBundle = true;

                break;
              }
            }
          }

          if (removeBundle) {
            itemsToRemove.add(orderItem);
          }
        } // end if-else
      } // end for

      // Remove any giftwrap items who have one or more wrapped item members that have been removed
      for (OrderItem orderItem : customerCart.getOrderItems()) {
        if (orderItem instanceof GiftWrapOrderItem) {
          for (OrderItem wrappedItem : ((GiftWrapOrderItem) orderItem).getWrappedItems()) {
            if (itemsToRemove.contains(wrappedItem)) {
              itemsToRemove.add(orderItem);

              break;
            }
          }
        }
      }

      for (OrderItem item : itemsToRemove) {
        removeItemFromOrder(customerCart, item, priceOrder);
      }

      reconstructCartResponse.setRemovedItems(itemsToRemove);
    } // end if

    reconstructCartResponse.setOrder(customerCart);

    return reconstructCartResponse;
  } // end method reconstructCart

  /**
   * DOCUMENT ME!
   *
   * @param   mergeCartResponse  DOCUMENT ME!
   * @param   customerCart       DOCUMENT ME!
   * @param   oldNewItemMap      DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  protected Order mergeGiftWrapOrderItems(MergeCartResponse mergeCartResponse, Order customerCart,
    Map<OrderItem, OrderItem> oldNewItemMap) throws PricingException {
    // update any remaining gift wrap items with their cloned wrapped item values, instead of the originals
    Iterator<OrderItem> addedItems = mergeCartResponse.getAddedItems().iterator();

    while (addedItems.hasNext()) {
      OrderItem addedItem = addedItems.next();

      if (addedItem instanceof GiftWrapOrderItem) {
        GiftWrapOrderItem   giftItem     = (GiftWrapOrderItem) addedItem;
        List<OrderItem>     itemsToAdd   = new ArrayList<OrderItem>();
        Iterator<OrderItem> wrappedItems = giftItem.getWrappedItems().iterator();

        while (wrappedItems.hasNext()) {
          OrderItem wrappedItem = wrappedItems.next();

          if (oldNewItemMap.containsKey(wrappedItem)) {
            OrderItem newItem = oldNewItemMap.get(wrappedItem);
            newItem.setGiftWrapOrderItem(giftItem);
            itemsToAdd.add(newItem);
            wrappedItem.setGiftWrapOrderItem(null);
            wrappedItems.remove();
          }
        }

        giftItem.getWrappedItems().addAll(itemsToAdd);
      } else if (addedItem instanceof BundleOrderItem) {
        // a GiftWrapOrderItem inside a BundleOrderItem can only wrap other members of that bundle
        // or root members of the order - not members of an entirely different bundle
        boolean isValidBundle = true;

        Map<String, DiscreteOrderItem> newItemsMap = new HashMap<String, DiscreteOrderItem>();

        for (DiscreteOrderItem newItem : ((BundleOrderItem) addedItem).getDiscreteOrderItems()) {
          newItemsMap.put(newItem.getSku().getId() + "_" + newItem.getPrice(), newItem);
        }

checkBundle:  {
          for (DiscreteOrderItem itemFromBundle : ((BundleOrderItem) addedItem).getDiscreteOrderItems()) {
            if (itemFromBundle instanceof GiftWrapOrderItem) {
              GiftWrapOrderItem   giftItem     = (GiftWrapOrderItem) itemFromBundle;
              List<OrderItem>     itemsToAdd   = new ArrayList<OrderItem>();
              Iterator<OrderItem> wrappedItems = giftItem.getWrappedItems().iterator();

              while (wrappedItems.hasNext()) {
                OrderItem wrappedItem = wrappedItems.next();

                if (oldNewItemMap.containsKey(wrappedItem)) {
                  OrderItem newItem = oldNewItemMap.get(wrappedItem);
                  newItem.setGiftWrapOrderItem(giftItem);
                  itemsToAdd.add(newItem);
                  wrappedItem.setGiftWrapOrderItem(null);
                  wrappedItems.remove();
                } else if (wrappedItem instanceof DiscreteOrderItem) {
                  DiscreteOrderItem discreteWrappedItem = (DiscreteOrderItem) wrappedItem;
                  String            itemKey             = discreteWrappedItem.getSku().getId() + "_"
                    + discreteWrappedItem.getPrice();

                  if (newItemsMap.containsKey(itemKey)) {
                    OrderItem newItem = newItemsMap.get(itemKey);
                    newItem.setGiftWrapOrderItem(giftItem);
                    itemsToAdd.add(newItem);
                    discreteWrappedItem.setGiftWrapOrderItem(null);
                    wrappedItems.remove();
                  } else {
                    isValidBundle = false;

                    break checkBundle;
                  }
                } else {
                  isValidBundle = false;

                  break checkBundle;
                } // end if-else
              } // end while

              giftItem.getWrappedItems().addAll(itemsToAdd);
            } // end if
          } // end for
        }

        if (!isValidBundle) {
          customerCart = removeItemFromOrder(customerCart, addedItem, false);
          addedItems.remove();
          mergeCartResponse.getRemovedItems().add(addedItem);
        }
      } // end if-else
    } // end while

    // Go through any remaining bundles and check their DiscreteOrderItem instances for GiftWrapOrderItem references without a local GiftWrapOrderItem
    // If found, remove, because this is invalid. A GiftWrapOrderItem cannot wrap OrderItems located in an entirely different bundle.
    for (OrderItem addedItem : mergeCartResponse.getAddedItems()) {
      if (addedItem instanceof BundleOrderItem) {
        boolean containsGiftWrap = false;

        for (DiscreteOrderItem discreteOrderItem : ((BundleOrderItem) addedItem).getDiscreteOrderItems()) {
          if (discreteOrderItem instanceof GiftWrapOrderItem) {
            containsGiftWrap = true;

            break;
          }
        }

        if (!containsGiftWrap) {
          for (DiscreteOrderItem discreteOrderItem : ((BundleOrderItem) addedItem).getDiscreteOrderItems()) {
            discreteOrderItem.setGiftWrapOrderItem(null);
          }
        }
      }
    }

    return customerCart;
  } // end method mergeGiftWrapOrderItems

  /**
   * DOCUMENT ME!
   *
   * @param   mergeCartResponse  DOCUMENT ME!
   * @param   customerCart       DOCUMENT ME!
   * @param   oldNewItemMap      DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  protected Order removeExpiredGiftWrapOrderItems(MergeCartResponse mergeCartResponse, Order customerCart,
    Map<OrderItem, OrderItem> oldNewItemMap) throws PricingException {
    // clear out any Gift Wrap items that contain one or more removed wrapped items
    Iterator<OrderItem> addedItems = mergeCartResponse.getAddedItems().iterator();

    while (addedItems.hasNext()) {
      OrderItem addedItem = addedItems.next();

      if (addedItem instanceof GiftWrapOrderItem) {
        GiftWrapOrderItem giftWrapOrderItem = (GiftWrapOrderItem) addedItem;
        boolean           removeItem        = false;

        for (OrderItem wrappedItem : giftWrapOrderItem.getWrappedItems()) {
          if (mergeCartResponse.getRemovedItems().contains(wrappedItem)) {
            removeItem = true;

            break;
          }
        }

        if (removeItem) {
          for (OrderItem wrappedItem : giftWrapOrderItem.getWrappedItems()) {
            wrappedItem.setGiftWrapOrderItem(null);
          }

          giftWrapOrderItem.getWrappedItems().clear();

          for (OrderItem cartItem : customerCart.getOrderItems()) {
            if ((cartItem.getGiftWrapOrderItem() != null)
                  && oldNewItemMap.containsKey(cartItem.getGiftWrapOrderItem())) {
              cartItem.setGiftWrapOrderItem(null);
            }
          }

          customerCart = removeItemFromOrder(customerCart, giftWrapOrderItem, false);
          addedItems.remove();
          mergeCartResponse.getRemovedItems().add(giftWrapOrderItem);
        }
      } // end if
    } // end while

    return customerCart;
  } // end method removeExpiredGiftWrapOrderItems

  /**
   * DOCUMENT ME!
   *
   * @param   anonymousCart  DOCUMENT ME!
   * @param   customerCart   DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected Order mergeOfferCodes(Order anonymousCart, Order customerCart) {
    // add all offers from anonymous order
    Map<String, OfferCode> customerOffersMap = new HashMap<String, OfferCode>();

    for (OfferCode customerOffer : customerCart.getAddedOfferCodes()) {
      customerOffersMap.put(customerOffer.getOfferCode(), customerOffer);
    }

    for (OfferCode anonymousOffer : anonymousCart.getAddedOfferCodes()) {
      if (!customerOffersMap.containsKey(anonymousOffer.getOfferCode())) {
        OfferCode transferredCode = offerService.lookupOfferCodeByCode(anonymousOffer.getOfferCode());
        OfferInfo info            = anonymousCart.getAdditionalOfferInformation().get(anonymousOffer.getOffer());
        OfferInfo offerInfo       = offerDao.createOfferInfo();

        for (String key : info.getFieldValues().keySet()) {
          offerInfo.getFieldValues().put(key, info.getFieldValues().get(key));
        }

        customerCart.getAdditionalOfferInformation().put(transferredCode.getOffer(), offerInfo);
        customerCart.addOfferCode(transferredCode);
      }
    }

    return customerCart;
  } // end method mergeOfferCodes

  /**
   * DOCUMENT ME!
   *
   * @param   anonymousCart      DOCUMENT ME!
   * @param   mergeCartResponse  DOCUMENT ME!
   * @param   customerCart       DOCUMENT ME!
   * @param   oldNewItemMap      DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  protected Order mergeRegularOrderItems(Order anonymousCart, MergeCartResponse mergeCartResponse, Order customerCart,
    Map<OrderItem, OrderItem> oldNewItemMap) throws PricingException {
    // currently we'll just add items
    for (OrderItem orderItem : anonymousCart.getOrderItems()) {
      if (orderItem instanceof DiscreteOrderItem) {
        orderItem.removeAllAdjustments();
        orderItem.removeAllCandidateItemOffers();

        DiscreteOrderItem discreteOrderItem = (DiscreteOrderItem) orderItem;

        if (discreteOrderItem.getSku().getActiveStartDate() != null) {
          if (discreteOrderItem.getSku().isActive(discreteOrderItem.getProduct(), orderItem.getCategory())) {
            OrderItem newItem = addOrderItemToOrder(customerCart, discreteOrderItem.clone(), false);
            mergeCartResponse.getAddedItems().add(newItem);
            oldNewItemMap.put(orderItem, newItem);
          } else {
            mergeCartResponse.getRemovedItems().add(orderItem);
          }
        } else {
          if (discreteOrderItem.getProduct().isActive() && orderItem.getCategory().isActive()) {
            OrderItem newItem = addOrderItemToOrder(customerCart, discreteOrderItem.clone(), false);
            mergeCartResponse.getAddedItems().add(newItem);
            oldNewItemMap.put(orderItem, newItem);
          } else {
            mergeCartResponse.getRemovedItems().add(orderItem);
          }
        }
      } else if (orderItem instanceof BundleOrderItem) {
        BundleOrderItem bundleOrderItem = (BundleOrderItem) orderItem;
        orderItem.removeAllAdjustments();
        orderItem.removeAllCandidateItemOffers();

        boolean removeBundle = false;

        for (DiscreteOrderItem discreteOrderItem : bundleOrderItem.getDiscreteOrderItems()) {
          discreteOrderItem.removeAllAdjustments();
          discreteOrderItem.removeAllCandidateItemOffers();

          if (discreteOrderItem.getSku().getActiveStartDate() != null) {
            if (!discreteOrderItem.getSku().isActive(discreteOrderItem.getProduct(), orderItem.getCategory())) {
              /*
               * Bundle has an inactive item in it -- remove the whole bundle
               */
              removeBundle = true;
            }
          } else {
            if (!discreteOrderItem.getProduct().isActive() || !orderItem.getCategory().isActive()) {
              removeBundle = true;
            }
          }
        }

        if (!removeBundle) {
          OrderItem newItem = addOrderItemToOrder(customerCart, bundleOrderItem.clone(), false);
          mergeCartResponse.getAddedItems().add(newItem);
          oldNewItemMap.put(orderItem, newItem);
        } else {
          mergeCartResponse.getRemovedItems().add(orderItem);
        }
      } // end if-else
    } // end for

    return customerCart;
  } // end method mergeRegularOrderItems

  /**
   * DOCUMENT ME!
   *
   * @param   order         DOCUMENT ME!
   * @param   newOrderItem  DOCUMENT ME!
   * @param   priceOrder    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  protected OrderItem addOrderItemToOrder(Order order, OrderItem newOrderItem, Boolean priceOrder)
    throws PricingException {
    List<OrderItem> orderItems = order.getOrderItems();
    newOrderItem.setOrder(order);
    newOrderItem = orderItemService.saveOrderItem(newOrderItem);
    orderItems.add(newOrderItem);

    order = orderService.save(order, priceOrder);

    return newOrderItem;
  }

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
  protected Order removeItemFromOrder(Order order, OrderItem item, boolean priceOrder) throws PricingException {
    fulfillmentGroupService.removeOrderItemFromFullfillmentGroups(order, item);

    OrderItem itemFromOrder = order.getOrderItems().remove(order.getOrderItems().indexOf(item));
    itemFromOrder.setOrder(null);
    orderItemService.delete(itemFromOrder);
    order = orderService.save(order, priceOrder);

    return order;
  }

} // end class LegacyMergeCartServiceImpl
