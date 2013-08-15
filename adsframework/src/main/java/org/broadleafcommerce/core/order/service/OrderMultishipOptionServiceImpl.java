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

package org.broadleafcommerce.core.order.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.broadleafcommerce.core.order.dao.OrderMultishipOptionDao;
import org.broadleafcommerce.core.order.domain.DiscreteOrderItem;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderMultishipOption;
import org.broadleafcommerce.core.order.domain.OrderMultishipOptionImpl;
import org.broadleafcommerce.core.order.service.call.OrderMultishipOptionDTO;

import org.broadleafcommerce.profile.core.service.AddressService;

import org.springframework.stereotype.Service;


/**
 * DOCUMENT ME!
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
@Service("blOrderMultishipOptionService")
public class OrderMultishipOptionServiceImpl implements OrderMultishipOptionService {
  /** DOCUMENT ME! */
  @Resource(name = "blOrderMultishipOptionDao")
  OrderMultishipOptionDao orderMultishipOptionDao;

  /** DOCUMENT ME! */
  @Resource(name = "blAddressService")
  protected AddressService addressService;

  /** DOCUMENT ME! */
  @Resource(name = "blOrderItemService")
  protected OrderItemService orderItemService;

  /** DOCUMENT ME! */
  @Resource(name = "blFulfillmentOptionService")
  protected FulfillmentOptionService fulfillmentOptionService;

  /**
   * @see  org.broadleafcommerce.core.order.service.OrderMultishipOptionService#save(org.broadleafcommerce.core.order.domain.OrderMultishipOption)
   */
  @Override public OrderMultishipOption save(OrderMultishipOption orderMultishipOption) {
    return orderMultishipOptionDao.save(orderMultishipOption);
  }

  /**
   * @see  org.broadleafcommerce.core.order.service.OrderMultishipOptionService#findOrderMultishipOptions(java.lang.Long)
   */
  @Override public List<OrderMultishipOption> findOrderMultishipOptions(Long orderId) {
    return orderMultishipOptionDao.readOrderMultishipOptions(orderId);
  }

  /**
   * @see  org.broadleafcommerce.core.order.service.OrderMultishipOptionService#findOrderItemOrderMultishipOptions(java.lang.Long)
   */
  @Override public List<OrderMultishipOption> findOrderItemOrderMultishipOptions(Long orderItemId) {
    return orderMultishipOptionDao.readOrderItemOrderMultishipOptions(orderItemId);
  }

  /**
   * @see  org.broadleafcommerce.core.order.service.OrderMultishipOptionService#create()
   */
  @Override public OrderMultishipOption create() {
    return orderMultishipOptionDao.create();
  }

  /**
   * @see  org.broadleafcommerce.core.order.service.OrderMultishipOptionService#deleteOrderItemOrderMultishipOptions(java.lang.Long)
   */
  @Override public void deleteOrderItemOrderMultishipOptions(Long orderItemId) {
    List<OrderMultishipOption> options = findOrderItemOrderMultishipOptions(orderItemId);
    orderMultishipOptionDao.deleteAll(options);
  }

  /**
   * @see  org.broadleafcommerce.core.order.service.OrderMultishipOptionService#deleteOrderItemOrderMultishipOptions(java.lang.Long,
   *       int)
   */
  @Override public void deleteOrderItemOrderMultishipOptions(Long orderItemId, int numToDelete) {
    List<OrderMultishipOption> options = findOrderItemOrderMultishipOptions(orderItemId);
    numToDelete = (numToDelete > options.size()) ? options.size() : numToDelete;
    options     = options.subList(0, numToDelete);
    orderMultishipOptionDao.deleteAll(options);
  }

  /**
   * @see  org.broadleafcommerce.core.order.service.OrderMultishipOptionService#deleteAllOrderMultishipOptions(org.broadleafcommerce.core.order.domain.Order)
   */
  @Override public void deleteAllOrderMultishipOptions(Order order) {
    List<OrderMultishipOption> options = findOrderMultishipOptions(order.getId());
    orderMultishipOptionDao.deleteAll(options);
  }

  /**
   * @see  org.broadleafcommerce.core.order.service.OrderMultishipOptionService#saveOrderMultishipOptions(org.broadleafcommerce.core.order.domain.Order,
   *       java.util.List)
   */
  @Override public void saveOrderMultishipOptions(Order order, List<OrderMultishipOptionDTO> optionDTOs) {
    Map<Long, OrderMultishipOption> currentOptions = new HashMap<Long, OrderMultishipOption>();

    for (OrderMultishipOption option : findOrderMultishipOptions(order.getId())) {
      currentOptions.put(option.getId(), option);
    }

    List<OrderMultishipOption> orderMultishipOptions = new ArrayList<OrderMultishipOption>();

    for (OrderMultishipOptionDTO dto : optionDTOs) {
      OrderMultishipOption option = currentOptions.get(dto.getId());

      if (option == null) {
        option = orderMultishipOptionDao.create();
      }

      option.setOrder(order);
      option.setOrderItem(orderItemService.readOrderItemById(dto.getOrderItemId()));

      if (dto.getAddressId() != null) {
        option.setAddress(addressService.readAddressById(dto.getAddressId()));
      } else {
        option.setAddress(null);
      }

      if (dto.getFulfillmentOptionId() != null) {
        option.setFulfillmentOption(fulfillmentOptionService.readFulfillmentOptionById(dto.getFulfillmentOptionId()));
      } else {
        option.setFulfillmentOption(null);
      }

      orderMultishipOptions.add(option);
    }

    for (OrderMultishipOption option : orderMultishipOptions) {
      save(option);
    }
  } // end method saveOrderMultishipOptions

  /**
   * @see  org.broadleafcommerce.core.order.service.OrderMultishipOptionService#getOrGenerateOrderMultishipOptions(org.broadleafcommerce.core.order.domain.Order)
   */
  @Override public List<OrderMultishipOption> getOrGenerateOrderMultishipOptions(Order order) {
    List<OrderMultishipOption> orderMultishipOptions = findOrderMultishipOptions(order.getId());

    if ((orderMultishipOptions == null) || (orderMultishipOptions.size() == 0)) {
      orderMultishipOptions = generateOrderMultishipOptions(order);
    }

    // Create a map representing the current discrete order item counts for the order
    Map<Long, Integer> orderDiscreteOrderItemCounts = new HashMap<Long, Integer>();

    for (DiscreteOrderItem item : order.getDiscreteOrderItems()) {
      orderDiscreteOrderItemCounts.put(item.getId(), item.getQuantity());
    }

    List<OrderMultishipOption> optionsToRemove = new ArrayList<OrderMultishipOption>();

    for (OrderMultishipOption option : orderMultishipOptions) {
      Integer count = orderDiscreteOrderItemCounts.get(option.getOrderItem().getId());

      if ((count == null) || (count == 0)) {
        optionsToRemove.add(option);
      } else {
        count--;
        orderDiscreteOrderItemCounts.put(option.getOrderItem().getId(), count);
      }
    }

    for (Entry<Long, Integer> entry : orderDiscreteOrderItemCounts.entrySet()) {
      DiscreteOrderItem item = (DiscreteOrderItem) orderItemService.readOrderItemById(entry.getKey());
      orderMultishipOptions.addAll(createPopulatedOrderMultishipOption(order, item, entry.getValue()));
    }

    orderMultishipOptions.removeAll(optionsToRemove);
    orderMultishipOptionDao.deleteAll(optionsToRemove);

    return orderMultishipOptions;
  } // end method getOrGenerateOrderMultishipOptions

  /**
   * @see  org.broadleafcommerce.core.order.service.OrderMultishipOptionService#getOrderMultishipOptionsFromDTOs(org.broadleafcommerce.core.order.domain.Order,
   *       java.util.List)
   */
  @Override public List<OrderMultishipOption> getOrderMultishipOptionsFromDTOs(Order order,
    List<OrderMultishipOptionDTO> optionDtos) {
    List<OrderMultishipOption> orderMultishipOptions = new ArrayList<OrderMultishipOption>();

    for (OrderMultishipOptionDTO optionDto : optionDtos) {
      OrderMultishipOption option = new OrderMultishipOptionImpl();

      if (optionDto.getAddressId() != null) {
        option.setAddress(addressService.readAddressById(optionDto.getAddressId()));
      }

      if (optionDto.getFulfillmentOptionId() != null) {
        option.setFulfillmentOption(fulfillmentOptionService.readFulfillmentOptionById(
            optionDto.getFulfillmentOptionId()));
      }

      option.setId(optionDto.getId());
      option.setOrder(order);
      option.setOrderItem(orderItemService.readOrderItemById(optionDto.getOrderItemId()));
      orderMultishipOptions.add(option);
    }

    return orderMultishipOptions;
  }

  /**
   * @see  org.broadleafcommerce.core.order.service.OrderMultishipOptionService#generateOrderMultishipOptions(org.broadleafcommerce.core.order.domain.Order)
   */
  @Override public List<OrderMultishipOption> generateOrderMultishipOptions(Order order) {
    List<OrderMultishipOption> orderMultishipOptions = new ArrayList<OrderMultishipOption>();

    for (DiscreteOrderItem discreteOrderItem : order.getDiscreteOrderItems()) {
      orderMultishipOptions.addAll(createPopulatedOrderMultishipOption(order, discreteOrderItem,
          discreteOrderItem.getQuantity()));
    }

    return orderMultishipOptions;
  }

  /**
   * DOCUMENT ME!
   *
   * @param   order     DOCUMENT ME!
   * @param   item      DOCUMENT ME!
   * @param   quantity  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected List<OrderMultishipOption> createPopulatedOrderMultishipOption(Order order, DiscreteOrderItem item,
    Integer quantity) {
    List<OrderMultishipOption> orderMultishipOptions = new ArrayList<OrderMultishipOption>();

    for (int i = 0; i < quantity; i++) {
      OrderMultishipOption orderMultishipOption = new OrderMultishipOptionImpl();
      orderMultishipOption.setOrder(order);
      orderMultishipOption.setOrderItem(item);
      orderMultishipOptions.add(orderMultishipOption);
    }

    return orderMultishipOptions;
  }
} // end class OrderMultishipOptionServiceImpl
