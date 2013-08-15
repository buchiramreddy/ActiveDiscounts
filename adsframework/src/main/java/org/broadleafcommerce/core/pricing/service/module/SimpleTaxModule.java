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

import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupFee;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.TaxDetail;
import org.broadleafcommerce.core.order.domain.TaxDetailImpl;
import org.broadleafcommerce.core.order.domain.TaxType;
import org.broadleafcommerce.core.pricing.service.exception.TaxException;

import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.domain.Country;
import org.broadleafcommerce.profile.core.domain.State;


/**
 * Simple factor-based tax module that can be configured by adding rates for specific postalCodes, city, state, or
 * country.
 *
 * <p>Through configuration, this module can be used to set a specific tax rate for items and shipping for a given
 * postal code, city, state, or country.</p>
 *
 * <p>Utilizes the fulfillment group's address to determine the tax location.</p>
 *
 * <p>Useful for those with very simple tax needs.</p>
 *
 * @author   jfischer, brian polster
 * @version  $Revision$, $Date$
 */
@Deprecated public class SimpleTaxModule implements TaxModule {
  /** DOCUMENT ME! */
  public static final String MODULENAME = "simpleTaxModule";

  /** DOCUMENT ME! */
  protected String name = MODULENAME;

  /** DOCUMENT ME! */
  protected Map<String, Double> itemPostalCodeTaxRateMap;

  /** DOCUMENT ME! */
  protected Map<String, Double> itemCityTaxRateMap;

  /** DOCUMENT ME! */
  protected Map<String, Double> itemStateTaxRateMap;

  /** DOCUMENT ME! */
  protected Map<String, Double> itemCountryTaxRateMap;

  /** DOCUMENT ME! */
  protected Map<String, Double> fulfillmentGroupPostalCodeTaxRateMap;

  /** DOCUMENT ME! */
  protected Map<String, Double> fulfillmentGroupCityTaxRateMap;

  /** DOCUMENT ME! */
  protected Map<String, Double> fulfillmentGroupStateTaxRateMap;

  /** DOCUMENT ME! */
  protected Map<String, Double> fulfillmentGroupCountryTaxRateMap;


  /** DOCUMENT ME! */
  protected Double defaultItemTaxRate;

  /** DOCUMENT ME! */
  protected Double defaultFulfillmentGroupTaxRate;

  /** DOCUMENT ME! */
  protected boolean taxFees;

  /**
   * @see  org.broadleafcommerce.core.pricing.service.module.TaxModule#calculateTaxForOrder(org.broadleafcommerce.core.order.domain.Order)
   */
  @Override public Order calculateTaxForOrder(Order order) throws TaxException {
    for (FulfillmentGroup fulfillmentGroup : order.getFulfillmentGroups()) {
      // Set taxes on the fulfillment group items
      for (FulfillmentGroupItem fgItem : fulfillmentGroup.getFulfillmentGroupItems()) {
        if (isItemTaxable(fgItem)) {
          Double factor = determineItemTaxRate(fulfillmentGroup.getAddress());

          if ((factor != null) && (factor.compareTo(0d) != 0)) {
            TaxDetail tax;

checkDetail:  {
              for (TaxDetail detail : fgItem.getTaxes()) {
                if (detail.getType().equals(TaxType.COMBINED)) {
                  tax = detail;

                  break checkDetail;
                }
              }

              tax = new TaxDetailImpl();
              tax.setType(TaxType.COMBINED);
              fgItem.getTaxes().add(tax);
            }

            tax.setRate(new BigDecimal(factor));
            tax.setAmount(fgItem.getTotalItemTaxableAmount().multiply(factor));
          }
        }
      } // end for

      for (FulfillmentGroupFee fgFee : fulfillmentGroup.getFulfillmentGroupFees()) {
        if (isFeeTaxable(fgFee)) {
          Double factor = determineItemTaxRate(fulfillmentGroup.getAddress());

          if ((factor != null) && (factor.compareTo(0d) != 0)) {
            TaxDetail tax;

checkDetail:  {
              for (TaxDetail detail : fgFee.getTaxes()) {
                if (detail.getType().equals(TaxType.COMBINED)) {
                  tax = detail;

                  break checkDetail;
                }
              }

              tax = new TaxDetailImpl();
              tax.setType(TaxType.COMBINED);
              fgFee.getTaxes().add(tax);
            }

            tax.setRate(new BigDecimal(factor));
            tax.setAmount(fgFee.getAmount().multiply(factor));
          }
        }
      } // end for

      Double factor = determineTaxRateForFulfillmentGroup(fulfillmentGroup);

      if ((factor != null) && (factor.compareTo(0d) != 0)) {
        TaxDetail tax;

checkDetail:  {
          for (TaxDetail detail : fulfillmentGroup.getTaxes()) {
            if (detail.getType().equals(TaxType.COMBINED)) {
              tax = detail;

              break checkDetail;
            }
          }

          tax = new TaxDetailImpl();
          tax.setType(TaxType.COMBINED);
          fulfillmentGroup.getTaxes().add(tax);
        }

        tax.setRate(new BigDecimal(factor));
        tax.setAmount(fulfillmentGroup.getFulfillmentPrice().multiply(factor));
      }
    } // end for

    return order;
  } // end method calculateTaxForOrder

  /**
   * @see  org.broadleafcommerce.core.pricing.service.module.TaxModule#getName()
   */
  @Override public String getName() {
    return name;
  }

  /**
   * @see  org.broadleafcommerce.core.pricing.service.module.TaxModule#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
  }


  /**
   * Returns the taxAmount for the passed in postal code or null if no match is found.
   *
   * @param   postalCodeTaxRateMap  DOCUMENT ME!
   * @param   postalCode            DOCUMENT ME!
   *
   * @return  the taxAmount for the passed in postal code or null if no match is found.
   */
  public Double lookupPostalCodeRate(Map<String, Double> postalCodeTaxRateMap, String postalCode) {
    if ((postalCodeTaxRateMap != null) && (postalCode != null)) {
      return postalCodeTaxRateMap.get(postalCode);
    }

    return null;
  }

  /**
   * Changes the city to upper case before checking the configuration.
   *
   * <p>Return null if no match is found.</p>
   *
   * @param   cityTaxRateMap  city
   * @param   city            DOCUMENT ME!
   *
   * @return  changes the city to upper case before checking the configuration.
   */
  public Double lookupCityRate(Map<String, Double> cityTaxRateMap, String city) {
    if ((cityTaxRateMap != null) && (city != null)) {
      city = city.toUpperCase();

      return cityTaxRateMap.get(city);
    }

    return null;
  }

  /**
   * Returns the taxAmount for the passed in state or null if no match is found.
   *
   * <p>First checks the abbreviation (uppercase) followed by the name (uppercase).</p>
   *
   * @param   stateTaxRateMap  state
   * @param   state            DOCUMENT ME!
   *
   * @return  the taxAmount for the passed in state or null if no match is found.
   */
  public Double lookupStateRate(Map<String, Double> stateTaxRateMap, State state) {
    if ((stateTaxRateMap != null) && (state != null) && (state.getAbbreviation() != null)) {
      String stateAbbr = state.getAbbreviation().toUpperCase();
      Double rate      = stateTaxRateMap.get(stateAbbr);

      if ((rate == null) && (state.getName() != null)) {
        String stateName = state.getName().toUpperCase();

        return stateTaxRateMap.get(stateName);
      } else {
        return rate;
      }
    }

    return null;
  }

  /**
   * Returns the taxAmount for the passed in country or null if no match is found.
   *
   * <p>First checks the abbreviation (uppercase) followed by the name (uppercase).</p>
   *
   * @param   countryTaxRateMap  state
   * @param   country            DOCUMENT ME!
   *
   * @return  the taxAmount for the passed in country or null if no match is found.
   */
  public Double lookupCountryRate(Map<String, Double> countryTaxRateMap, Country country) {
    if ((countryTaxRateMap != null) && (country != null) && (country.getAbbreviation() != null)) {
      String cntryAbbr = country.getAbbreviation().toUpperCase();
      Double rate      = countryTaxRateMap.get(cntryAbbr);

      if ((rate == null) && (country.getName() != null)) {
        String countryName = country.getName().toUpperCase();

        return countryTaxRateMap.get(countryName);
      } else {
        return rate;
      }
    }

    return null;
  }

  /**
   * DOCUMENT ME!
   *
   * @param   item  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected boolean isItemTaxable(FulfillmentGroupItem item) {
    return item.getOrderItem().isTaxable();
  }

  /**
   * DOCUMENT ME!
   *
   * @param   fee  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected boolean isFeeTaxable(FulfillmentGroupFee fee) {
    return fee.isTaxable();
  }


  /**
   * Uses the passed in address to determine if the item is taxable.
   *
   * <p>Checks the configured maps in order - (postal code, city, state, country)</p>
   *
   * @param   address  DOCUMENT ME!
   *
   * @return  uses the passed in address to determine if the item is taxable.
   */
  public Double determineItemTaxRate(Address address) {
    if (address != null) {
      Double postalCodeRate = lookupPostalCodeRate(itemPostalCodeTaxRateMap, address.getPostalCode());

      if (postalCodeRate != null) {
        return postalCodeRate;
      }

      Double cityCodeRate = lookupCityRate(itemCityTaxRateMap, address.getCity());

      if (cityCodeRate != null) {
        return cityCodeRate;
      }

      Double stateCodeRate = lookupStateRate(itemStateTaxRateMap, address.getState());

      if (stateCodeRate != null) {
        return stateCodeRate;
      }

      Double countryCodeRate = lookupCountryRate(itemCountryTaxRateMap, address.getCountry());

      if (countryCodeRate != null) {
        return countryCodeRate;
      }
    } // end if

    if (defaultItemTaxRate != null) {
      return defaultItemTaxRate;
    } else {
      return 0d;
    }
  } // end method determineItemTaxRate

  /**
   * Uses the passed in address to determine if the item is taxable.
   *
   * <p>Checks the configured maps in order - (postal code, city, state, country)</p>
   *
   * @param   fulfillmentGroup  DOCUMENT ME!
   *
   * @return  uses the passed in address to determine if the item is taxable.
   */
  public Double determineTaxRateForFulfillmentGroup(FulfillmentGroup fulfillmentGroup) {
    boolean isTaxable = true;

    if (fulfillmentGroup.isShippingPriceTaxable() != null) {
      isTaxable = fulfillmentGroup.isShippingPriceTaxable();
    }

    if (isTaxable) {
      Address address = fulfillmentGroup.getAddress();

      if (address != null) {
        Double postalCodeRate = lookupPostalCodeRate(fulfillmentGroupPostalCodeTaxRateMap, address.getPostalCode());

        if (postalCodeRate != null) {
          return postalCodeRate;
        }

        Double cityCodeRate = lookupCityRate(fulfillmentGroupCityTaxRateMap, address.getCity());

        if (cityCodeRate != null) {
          return cityCodeRate;
        }

        Double stateCodeRate = lookupStateRate(fulfillmentGroupStateTaxRateMap, address.getState());

        if (stateCodeRate != null) {
          return stateCodeRate;
        }

        Double countryCodeRate = lookupCountryRate(fulfillmentGroupCountryTaxRateMap, address.getCountry());

        if (countryCodeRate != null) {
          return countryCodeRate;
        }
      } // end if

      if (defaultFulfillmentGroupTaxRate != null) {
        return defaultFulfillmentGroupTaxRate;
      }
    } // end if

    return 0d;
  } // end method determineTaxRateForFulfillmentGroup

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<String, Double> getItemPostalCodeTaxRateMap() {
    return itemPostalCodeTaxRateMap;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  itemPostalCodeTaxRateMap  DOCUMENT ME!
   */
  public void setItemPostalCodeTaxRateMap(Map<String, Double> itemPostalCodeTaxRateMap) {
    this.itemPostalCodeTaxRateMap = itemPostalCodeTaxRateMap;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<String, Double> getItemCityTaxRateMap() {
    return itemCityTaxRateMap;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  itemCityTaxRateMap  DOCUMENT ME!
   */
  public void setItemCityTaxRateMap(Map<String, Double> itemCityTaxRateMap) {
    this.itemCityTaxRateMap = itemCityTaxRateMap;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<String, Double> getItemStateTaxRateMap() {
    return itemStateTaxRateMap;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  itemStateTaxRateMap  DOCUMENT ME!
   */
  public void setItemStateTaxRateMap(Map<String, Double> itemStateTaxRateMap) {
    this.itemStateTaxRateMap = itemStateTaxRateMap;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<String, Double> getItemCountryTaxRateMap() {
    return itemCountryTaxRateMap;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  itemCountryTaxRateMap  DOCUMENT ME!
   */
  public void setItemCountryTaxRateMap(Map<String, Double> itemCountryTaxRateMap) {
    this.itemCountryTaxRateMap = itemCountryTaxRateMap;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<String, Double> getFulfillmentGroupPostalCodeTaxRateMap() {
    return fulfillmentGroupPostalCodeTaxRateMap;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  fulfillmentGroupPostalCodeTaxRateMap  DOCUMENT ME!
   */
  public void setFulfillmentGroupPostalCodeTaxRateMap(Map<String, Double> fulfillmentGroupPostalCodeTaxRateMap) {
    this.fulfillmentGroupPostalCodeTaxRateMap = fulfillmentGroupPostalCodeTaxRateMap;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<String, Double> getFulfillmentGroupCityTaxRateMap() {
    return fulfillmentGroupCityTaxRateMap;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  fulfillmentGroupCityTaxRateMap  DOCUMENT ME!
   */
  public void setFulfillmentGroupCityTaxRateMap(Map<String, Double> fulfillmentGroupCityTaxRateMap) {
    this.fulfillmentGroupCityTaxRateMap = fulfillmentGroupCityTaxRateMap;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<String, Double> getFulfillmentGroupStateTaxRateMap() {
    return fulfillmentGroupStateTaxRateMap;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  fulfillmentGroupStateTaxRateMap  DOCUMENT ME!
   */
  public void setFulfillmentGroupStateTaxRateMap(Map<String, Double> fulfillmentGroupStateTaxRateMap) {
    this.fulfillmentGroupStateTaxRateMap = fulfillmentGroupStateTaxRateMap;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<String, Double> getFulfillmentGroupCountryTaxRateMap() {
    return fulfillmentGroupCountryTaxRateMap;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  fulfillmentGroupCountryTaxRateMap  DOCUMENT ME!
   */
  public void setFulfillmentGroupCountryTaxRateMap(Map<String, Double> fulfillmentGroupCountryTaxRateMap) {
    this.fulfillmentGroupCountryTaxRateMap = fulfillmentGroupCountryTaxRateMap;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Double getDefaultItemTaxRate() {
    return defaultItemTaxRate;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  defaultItemTaxRate  DOCUMENT ME!
   */
  public void setDefaultItemTaxRate(Double defaultItemTaxRate) {
    this.defaultItemTaxRate = defaultItemTaxRate;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Double getDefaultFulfillmentGroupTaxRate() {
    return defaultFulfillmentGroupTaxRate;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  defaultFulfillmentGroupTaxRate  DOCUMENT ME!
   */
  public void setDefaultFulfillmentGroupTaxRate(Double defaultFulfillmentGroupTaxRate) {
    this.defaultFulfillmentGroupTaxRate = defaultFulfillmentGroupTaxRate;
  }

  /**
   * Use getDefaultItemTaxRate instead.
   *
   * @deprecated  DOCUMENT ME!
   *
   * @return      use getDefaultItemTaxRate instead.
   */
  @Deprecated public Double getFactor() {
    return getDefaultItemTaxRate();
  }

  /**
   * Use setDefaultItemTaxRate instead.
   *
   * @param       factor  DOCUMENT ME!
   *
   * @deprecated  DOCUMENT ME!
   */
  @Deprecated public void setFactor(Double factor) {
    setDefaultItemTaxRate(factor);
  }

} // end class SimpleTaxModule
