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

package org.broadleafcommerce.core.order.dao;

import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.core.catalog.dao.SkuDao;
import org.broadleafcommerce.core.catalog.domain.Sku;
import org.broadleafcommerce.core.order.FulfillmentGroupDataProvider;
import org.broadleafcommerce.core.order.OrderItemDataProvider;
import org.broadleafcommerce.core.order.domain.DiscreteOrderItem;
import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.FulfillmentGroupService;
import org.broadleafcommerce.core.order.service.call.FulfillmentGroupItemRequest;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;

import org.broadleafcommerce.profile.core.dao.CustomerAddressDao;
import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.service.CustomerService;

import org.broadleafcommerce.test.BaseTest;

import org.springframework.test.annotation.Rollback;

import org.springframework.transaction.annotation.Transactional;

import org.testng.annotations.Test;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class FulfillmentGroupItemDaoTest extends BaseTest {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  @Resource private CustomerAddressDao customerAddressDao;

  @Resource private CustomerService customerService;

  private FulfillmentGroup fulfillmentGroup;

  @Resource private FulfillmentGroupDao fulfillmentGroupDao;

  @Resource private FulfillmentGroupItemDao fulfillmentGroupItemDao;
  private Long                              fulfillmentGroupItemId;

  @Resource private FulfillmentGroupService fulfillmentGroupService;

  @Resource private OrderDao orderDao;

  @Resource private OrderItemDao orderItemDao;
  private Order                  salesOrder;

  @Resource private SkuDao skuDao;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  fulfillmentGroup  DOCUMENT ME!
   */
  @Rollback(false)
  @Test(
    groups            = "createItemFulfillmentGroup",
    dataProvider      = "basicFulfillmentGroup",
    dataProviderClass = FulfillmentGroupDataProvider.class,
    dependsOnGroups   = { "createOrder", "createCustomerAddress" }
  )
  @Transactional public void createDefaultFulfillmentGroup(FulfillmentGroup fulfillmentGroup) {
    String   userName = "customer1";
    Customer customer = customerService.readCustomerByUsername(userName);
    Address  address  = (customerAddressDao.readActiveCustomerAddressesByCustomerId(customer.getId())).get(0)
      .getAddress();
    salesOrder = orderDao.createNewCartForCustomer(customer);

    FulfillmentGroup newFG = fulfillmentGroupDao.createDefault();
    newFG.setAddress(address);
    newFG.setRetailShippingPrice(fulfillmentGroup.getRetailShippingPrice());
    newFG.setMethod(fulfillmentGroup.getMethod());
    newFG.setService(fulfillmentGroup.getService());
    newFG.setOrder(salesOrder);
    newFG.setReferenceNumber(fulfillmentGroup.getReferenceNumber());

    assert newFG.getId() == null;
    this.fulfillmentGroup = fulfillmentGroupService.save(newFG);
    assert this.fulfillmentGroup.getId() != null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   orderItem  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  @Rollback(false)
  @Test(
    groups            = { "createFulfillmentGroupItem" },
    dataProvider      = "basicDiscreteOrderItem",
    dataProviderClass = OrderItemDataProvider.class,
    dependsOnGroups   = { "createOrder", "createSku", "createItemFulfillmentGroup" }
  )
  @Transactional public void createFulfillmentGroupItem(DiscreteOrderItem orderItem) throws PricingException {
    Sku si = skuDao.readFirstSku();
    orderItem.setSku(si);
    orderItem = (DiscreteOrderItem) orderItemDao.save(orderItem);
    orderItem.setOrder(salesOrder);
    salesOrder.addOrderItem(orderItem);
    orderDao.save(salesOrder);

    FulfillmentGroupItemRequest fulfillmentGroupItemRequest = new FulfillmentGroupItemRequest();
    fulfillmentGroupItemRequest.setOrderItem(orderItem);
    fulfillmentGroupItemRequest.setFulfillmentGroup(fulfillmentGroup);
    fulfillmentGroupService.addItemToFulfillmentGroup(fulfillmentGroupItemRequest, true);

    FulfillmentGroupItem fgi = fulfillmentGroup.getFulfillmentGroupItems().get(
        fulfillmentGroup.getFulfillmentGroupItems().size() - 1);
    assert fgi.getId() != null;
    fulfillmentGroupItemId = fgi.getId();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  @Test(
    groups          = { "readFulfillmentGroupItemsById" },
    dependsOnGroups = { "createFulfillmentGroupItem" }
  )
  @Transactional public void readFulfillmentGroupItemsById() {
    FulfillmentGroupItem fgi = fulfillmentGroupItemDao.readFulfillmentGroupItemById(fulfillmentGroupItemId);
    assert fgi != null;
    assert fgi.getId() != null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  @Test(
    groups          = { "readFulfillmentGroupItemsForFulfillmentGroup" },
    dependsOnGroups = { "createFulfillmentGroupItem" }
  )
  @Transactional public void readFulfillmentGroupItemsForFulfillmentGroup() {
    List<FulfillmentGroupItem> fgis = fulfillmentGroupItemDao.readFulfillmentGroupItemsForFulfillmentGroup(
        fulfillmentGroup);
    assert fgis != null;
    assert fgis.size() > 0;
  }
} // end class FulfillmentGroupItemDaoTest
