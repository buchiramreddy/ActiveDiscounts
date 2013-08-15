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

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentationClass;

import org.broadleafcommerce.core.catalog.domain.Sku;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(friendlyName = "DynamicPriceDiscreteOrderItemImpl_dynamicPriceOrderItem")
@Cache(
  usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
  region = "blOrderElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_DYN_DISCRETE_ORDER_ITEM")
public class DynamicPriceDiscreteOrderItemImpl extends DiscreteOrderItemImpl implements DynamicPriceDiscreteOrderItem {
  private static final long serialVersionUID = 1L;

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItem#setSku(org.broadleafcommerce.core.catalog.domain.Sku)
   */
  @Override public void setSku(Sku sku) {
    this.sku  = sku;
    this.name = sku.getName();
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItemImpl#updateSaleAndRetailPrices()
   */
  @Override public boolean updateSaleAndRetailPrices() {
    return false;
  }

}
