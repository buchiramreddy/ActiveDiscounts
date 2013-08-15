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

package org.broadleafcommerce.core.pricing.service;

import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.common.config.domain.ModuleConfiguration;
import org.broadleafcommerce.common.config.service.ModuleConfigurationService;
import org.broadleafcommerce.common.config.service.type.ModuleConfigurationType;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.pricing.service.exception.TaxException;
import org.broadleafcommerce.core.pricing.service.tax.provider.TaxProvider;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class TaxServiceImpl implements TaxService {
  /** DOCUMENT ME! */
  protected boolean mustCalculate = false;

  /** DOCUMENT ME! */
  @Resource(name = "blTaxProviders")
  protected List<TaxProvider> providers;

  /** DOCUMENT ME! */
  @Resource(name = "blModuleConfigurationService")
  protected ModuleConfigurationService moduleConfigService;

  /**
   * @see  org.broadleafcommerce.core.pricing.service.TaxService#calculateTaxForOrder(org.broadleafcommerce.core.order.domain.Order)
   */
  @Override public Order calculateTaxForOrder(Order order) throws TaxException {
    List<ModuleConfiguration> configurations = moduleConfigService.findActiveConfigurationsByType(
        ModuleConfigurationType.TAX_CALCULATION);

    if ((configurations != null) && !configurations.isEmpty()) {
      // Try to find a default configuration
      ModuleConfiguration config = null;

      for (ModuleConfiguration configuration : configurations) {
        if (configuration.getIsDefault()) {
          config = configuration;

          break;
        }
      }

      if (config == null) {
        // if there wasn't a default one, use the first active one...
        config = configurations.get(0);
      }

      if ((providers != null) && !providers.isEmpty()) {
        for (TaxProvider provider : providers) {
          if (provider.canRespond(config)) {
            return provider.calculateTaxForOrder(order, config);
          }
        }
      }
    } // end if

    if (!mustCalculate) {
      return order;
    }

    throw new TaxException("No eligible tax providers were configured.");
  } // end method calculateTaxForOrder

  /**
   * @see  org.broadleafcommerce.core.pricing.service.TaxService#commitTaxForOrder(org.broadleafcommerce.core.order.domain.Order)
   */
  @Override public Order commitTaxForOrder(Order order) throws TaxException {
    List<ModuleConfiguration> configurations = moduleConfigService.findActiveConfigurationsByType(
        ModuleConfigurationType.TAX_CALCULATION);

    if ((configurations != null) && !configurations.isEmpty()) {
      // Try to find a default configuration
      ModuleConfiguration config = null;

      for (ModuleConfiguration configuration : configurations) {
        if (configuration.getIsDefault()) {
          config = configuration;

          break;
        }
      }

      if (config == null) {
        // if there wasn't a default one, use the first active one...
        config = configurations.get(0);
      }

      if ((providers != null) && !providers.isEmpty()) {
        for (TaxProvider provider : providers) {
          if (provider.canRespond(config)) {
            return provider.commitTaxForOrder(order, config);
          }
        }
      }
    } // end if

    if (!mustCalculate) {
      return order;
    }

    throw new TaxException("No eligible tax providers were configured.");
  } // end method commitTaxForOrder

  /**
   * @see  org.broadleafcommerce.core.pricing.service.TaxService#cancelTax(org.broadleafcommerce.core.order.domain.Order)
   */
  @Override public void cancelTax(Order order) throws TaxException {
    List<ModuleConfiguration> configurations = moduleConfigService.findActiveConfigurationsByType(
        ModuleConfigurationType.TAX_CALCULATION);

    if ((configurations != null) && !configurations.isEmpty()) {
      // Try to find a default configuration
      ModuleConfiguration config = null;

      for (ModuleConfiguration configuration : configurations) {
        if (configuration.getIsDefault()) {
          config = configuration;

          break;
        }
      }

      if (config == null) {
        // if there wasn't a default one, use the first active one...
        config = configurations.get(0);
      }

      if ((providers != null) && !providers.isEmpty()) {
        for (TaxProvider provider : providers) {
          if (provider.canRespond(config)) {
            provider.cancelTax(order, config);

            return;
          }
        }
      }
    } // end if

    if (mustCalculate) {
      throw new TaxException("No eligible tax providers were configured.");
    }
  } // end method cancelTax

  /**
   * Sets a list of <code>TaxProvider</code> implementations.
   *
   * @param  providers  DOCUMENT ME!
   */
  public void setTaxProviders(List<TaxProvider> providers) {
    this.providers = providers;
  }

  /**
   * Sets whether or not this service is required to delegate to a tax provider. Setting this value to true will cause
   * an exception if no tax providers are configured, or if none are eligible.
   *
   * @param  mustCalculate  DOCUMENT ME!
   */
  public void setMustCalculate(boolean mustCalculate) {
    this.mustCalculate = mustCalculate;
  }
} // end class TaxServiceImpl
