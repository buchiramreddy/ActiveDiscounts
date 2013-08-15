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

package org.broadleafcommerce.core.pricing.service.module;

import java.math.BigDecimal;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.currency.util.BroadleafCurrencyUtils;

import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;
import org.broadleafcommerce.core.pricing.domain.ShippingRate;
import org.broadleafcommerce.core.pricing.service.ShippingRateService;
import org.broadleafcommerce.core.pricing.service.workflow.type.ShippingServiceType;

import org.broadleafcommerce.profile.core.domain.Address;


/**
 * DOCUMENT ME!
 *
 * @deprecated  Superceded by functionality given by
 *              {@link org.broadleafcommerce.core.order.fulfillment.domain.BandedPriceFulfillmentOption} and
 *              {@link org.broadleafcommerce.core.pricing.service.fulfillment.provider.BandedFulfillmentPricingProvider}
 * @see         {@link org.broadleafcommerce.core.order.fulfillment.domain.BandedPriceFulfillmentOption},
 *              {@link org.broadleafcommerce.core.order.fulfillment.domain.BandedPriceFulfillmentOption}
 * @author      $author$
 * @version     $Revision$, $Date$
 */
@Deprecated public class BandedShippingModule implements ShippingModule {
  private static final Log LOG = LogFactory.getLog(BandedShippingModule.class);

  /** DOCUMENT ME! */
  public static final String MODULENAME = "bandedShippingModule";

  /** DOCUMENT ME! */
  protected String  name            = MODULENAME;

  /** DOCUMENT ME! */
  protected Boolean isDefaultModule = false;

  @Resource(name = "blShippingRateService")
  private ShippingRateService shippingRateService;

  private Map<String, String> feeTypeMapping;
  private Map<String, String> feeSubTypeMapping;

  // this will need to calculate shipping on each fulfilmentGroup in an order
  /**
   * @see  org.broadleafcommerce.core.pricing.service.module.ShippingModule#calculateShippingForFulfillmentGroup(org.broadleafcommerce.core.order.domain.FulfillmentGroup)
   */
  @Override public FulfillmentGroup calculateShippingForFulfillmentGroup(FulfillmentGroup fulfillmentGroup) {
    calculateShipping(fulfillmentGroup);

    return fulfillmentGroup;
  }

  private void calculateShipping(FulfillmentGroup fulfillmentGroup) {
    if (!isValidModuleForService(fulfillmentGroup.getService()) && !isDefaultModule()) {
      LOG.info("fulfillment group (" + fulfillmentGroup.getId() + ") with a service type of ("
        + fulfillmentGroup.getService() + ") is not valid for this module service type (" + getServiceName() + ")");

      return;
    }

    if (fulfillmentGroup.getFulfillmentGroupItems().size() == 0) {
      LOG.warn("fulfillment group (" + fulfillmentGroup.getId()
        + ") does not contain any fulfillment group items. Unable to price banded shipping");
      fulfillmentGroup.setShippingPrice(BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO,
          fulfillmentGroup.getOrder().getCurrency()));
      fulfillmentGroup.setSaleShippingPrice(BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO,
          fulfillmentGroup.getOrder().getCurrency()));
      fulfillmentGroup.setRetailShippingPrice(BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO,
          fulfillmentGroup.getOrder().getCurrency()));

      return;
    }

    Address    address     = fulfillmentGroup.getAddress();
    String     state       = ((address != null) && (address.getState() != null)) ? address.getState().getAbbreviation()
                                                                                 : null;
    BigDecimal retailTotal = new BigDecimal(0);
    String     feeType     = feeTypeMapping.get(fulfillmentGroup.getMethod());
    String     feeSubType  = ((feeSubTypeMapping.get(state) == null) ? feeSubTypeMapping.get("ALL")
                                                                     : feeSubTypeMapping.get(state));

    for (FulfillmentGroupItem fulfillmentGroupItem : fulfillmentGroup.getFulfillmentGroupItems()) {
      BigDecimal price = (fulfillmentGroupItem.getRetailPrice() != null)
        ? fulfillmentGroupItem.getRetailPrice().getAmount().multiply(BigDecimal.valueOf(
            fulfillmentGroupItem.getQuantity())) : null;

      if (price == null) {
        price = fulfillmentGroupItem.getOrderItem().getRetailPrice().getAmount().multiply(BigDecimal.valueOf(
              fulfillmentGroupItem.getQuantity()));
      }

      retailTotal = retailTotal.add(price);
    }

    ShippingRate sr = shippingRateService.readShippingRateByFeeTypesUnityQty(feeType, feeSubType, retailTotal);

    if (sr == null) {
      throw new NotImplementedException("Shipping rate " + fulfillmentGroup.getMethod() + " not supported");
    }

    BigDecimal shippingPrice = new BigDecimal(0);

    if (sr.getBandResultPercent().compareTo(0) > 0) {
      BigDecimal percent = new BigDecimal(sr.getBandResultPercent() / 100);
      shippingPrice = retailTotal.multiply(percent);
    } else {
      shippingPrice = sr.getBandResultQuantity();
    }

    fulfillmentGroup.setShippingPrice(BroadleafCurrencyUtils.getMoney(shippingPrice,
        fulfillmentGroup.getOrder().getCurrency()));
    fulfillmentGroup.setSaleShippingPrice(fulfillmentGroup.getShippingPrice());
    fulfillmentGroup.setRetailShippingPrice(fulfillmentGroup.getSaleShippingPrice());
  } // end method calculateShipping

  /**
   * @see  org.broadleafcommerce.core.pricing.service.module.ShippingModule#getName()
   */
  @Override public String getName() {
    return name;
  }

  /**
   * @see  org.broadleafcommerce.core.pricing.service.module.ShippingModule#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<String, String> getFeeTypeMapping() {
    return feeTypeMapping;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  feeTypeMapping  DOCUMENT ME!
   */
  public void setFeeTypeMapping(Map<String, String> feeTypeMapping) {
    this.feeTypeMapping = feeTypeMapping;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<String, String> getFeeSubTypeMapping() {
    return feeSubTypeMapping;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  feeSubTypeMapping  DOCUMENT ME!
   */
  public void setFeeSubTypeMapping(Map<String, String> feeSubTypeMapping) {
    this.feeSubTypeMapping = feeSubTypeMapping;
  }

  /**
   * @see  org.broadleafcommerce.core.pricing.service.module.ShippingModule#getServiceName()
   */
  @Override public String getServiceName() {
    return ShippingServiceType.BANDED_SHIPPING.getType();
  }

  /**
   * @see  org.broadleafcommerce.core.pricing.service.module.ShippingModule#isValidModuleForService(java.lang.String)
   */
  @Override public Boolean isValidModuleForService(String serviceName) {
    return getServiceName().equals(serviceName);
  }

  /**
   * @see  org.broadleafcommerce.core.pricing.service.module.ShippingModule#isDefaultModule()
   */
  @Override public Boolean isDefaultModule() {
    return isDefaultModule;
  }

  /**
   * @see  org.broadleafcommerce.core.pricing.service.module.ShippingModule#setDefaultModule(java.lang.Boolean)
   */
  @Override public void setDefaultModule(Boolean isDefaultModule) {
    this.isDefaultModule = isDefaultModule;
  }

} // end class BandedShippingModule
