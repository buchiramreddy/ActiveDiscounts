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

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.pricing.service.exception.TaxException;


/**
 * Generic service to calculate taxes. Those implementing tax calculation logic should, more likely, use the default
 * Broadleaf TaxService implementation, and implement TaxProvider.
 *
 * @author   Kelly Tisdell
 * @version  $Revision$, $Date$
 */
public interface TaxService {
  /**
   * Calculates tax for the order.
   *
   * @param   order  DOCUMENT ME!
   *
   * @return  calculates tax for the order.
   *
   * @throws  org.broadleafcommerce.core.pricing.service.exception.TaxException
   */
  Order calculateTaxForOrder(Order order) throws TaxException;

  /**
   * Commits tax for the order. Some implemenations may do nothing. Others may delegate to a tax provider that stores
   * taxes in another system for reporting or reconcilliation.
   *
   * @param   order  DOCUMENT ME!
   *
   * @return  commits tax for the order.
   *
   * @throws  org.broadleafcommerce.core.pricing.service.exception.TaxException
   */
  Order commitTaxForOrder(Order order) throws TaxException;

  /**
   * Some tax providers store tax details from an order on an external system for reporting and tax reconcilliation.
   * This allows one to cancel or undo tax recording in an external system. Typically, this will be called to offset a
   * call to commitTaxForOrder. This might be called, for example, in a rollback handler for a checkout workflow
   * activity that calls commitTaxForOrder.
   *
   * @param   order  DOCUMENT ME!
   *
   * @throws  org.broadleafcommerce.core.pricing.service.exception.TaxException
   */
  void cancelTax(Order order) throws TaxException;

} // end interface TaxService
