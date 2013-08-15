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

package org.broadleafcommerce.core.order.domain;

import java.io.Serializable;

import java.math.BigDecimal;

import org.broadleafcommerce.common.config.domain.ModuleConfiguration;
import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;
import org.broadleafcommerce.common.money.Money;


/**
 * The Interface TaxDetail. A TaxDetail object stores relevant tax information including a tax type, amount, and rate.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface TaxDetail extends Serializable {
  /**
   * Gets the id.
   *
   * @return  the id
   */
  Long getId();

  /**
   * Sets the id.
   *
   * @param  id  the new id
   */
  void setId(Long id);

  /**
   * Gets the tax type.
   *
   * @return  the tax type
   */
  TaxType getType();

  /**
   * Sets the tax type.
   *
   * @param  type  the tax type
   */
  void setType(TaxType type);

  /**
   * Gets the tax amount.
   *
   * @return  the tax amount
   */
  Money getAmount();

  /**
   * Sets the tax amount.
   *
   * @param  amount  the tax amount
   */
  void setAmount(Money amount);

  /**
   * Gets the tax rate.
   *
   * @return  the rate
   */
  BigDecimal getRate();

  /**
   * Sets the tax rate.
   *
   * @param  rate  name the tax rate
   */
  void setRate(BigDecimal rate);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  BroadleafCurrency getCurrency();

  /**
   * DOCUMENT ME!
   *
   * @param  currency  DOCUMENT ME!
   */
  void setCurrency(BroadleafCurrency currency);

  /**
   * Returns the configuration of the module that was used to calculate taxes. Allows for tracking, especially when more
   * than one module may be used by the system.
   *
   * @return  the configuration of the module that was used to calculate taxes.
   */
  ModuleConfiguration getModuleConfiguration();

  /**
   * Sets the module configuration that was used to calculate taxes. Allows for tracking of which module was used,
   * especially in cases where more than one module is available over time.
   *
   * @param  config  DOCUMENT ME!
   */
  void setModuleConfiguration(ModuleConfiguration config);

  /**
   * Optionally sets the name of the tax jurisdiction.
   *
   * @param  jurisdiction  DOCUMENT ME!
   */
  void setJurisdictionName(String jurisdiction);

  /**
   * Returns the name of the tax jurisdiction. May return null.
   *
   * @return  the name of the tax jurisdiction.
   */
  String getJurisdictionName();

  /**
   * Sets the name of the tax, if applicable.
   *
   * @param  taxName  DOCUMENT ME!
   */
  void setTaxName(String taxName);

  /**
   * Gets the name of the tax. May return null.
   *
   * @return  gets the name of the tax.
   */
  String getTaxName();

  /**
   * Sets the region, as a string. Typically this will be a State, Province, or County.
   *
   * @param  region  DOCUMENT ME!
   */
  void setRegion(String region);

  /**
   * Returns the name of the region used for tax calculation. May return null.
   *
   * @return  the name of the region used for tax calculation.
   */
  String getRegion();

  /**
   * Sets the country used for tax calculation.
   *
   * @param  country  DOCUMENT ME!
   */
  void setCountry(String country);

  /**
   * Returns the country, as a string, used for tax calculation. May return null.
   *
   * @return  the country, as a string, used for tax calculation.
   */
  String getCountry();

} // end interface TaxDetail
