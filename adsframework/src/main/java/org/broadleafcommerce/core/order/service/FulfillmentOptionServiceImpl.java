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

import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.core.order.dao.FulfillmentOptionDao;
import org.broadleafcommerce.core.order.domain.FulfillmentOption;
import org.broadleafcommerce.core.order.service.type.FulfillmentType;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


/**
 * DOCUMENT ME!
 *
 * @author   Phillip Verheyden
 * @version  $Revision$, $Date$
 */
@Service("blFulfillmentOptionService")
@Transactional("blTransactionManager")
public class FulfillmentOptionServiceImpl implements FulfillmentOptionService {
  /** DOCUMENT ME! */
  @Resource(name = "blFulfillmentOptionDao")
  FulfillmentOptionDao fulfillmentOptionDao;

  /**
   * @see  org.broadleafcommerce.core.order.service.FulfillmentOptionService#readFulfillmentOptionById(java.lang.Long)
   */
  @Override public FulfillmentOption readFulfillmentOptionById(Long fulfillmentOptionId) {
    return fulfillmentOptionDao.readFulfillmentOptionById(fulfillmentOptionId);
  }

  /**
   * @see  org.broadleafcommerce.core.order.service.FulfillmentOptionService#save(org.broadleafcommerce.core.order.domain.FulfillmentOption)
   */
  @Override public FulfillmentOption save(FulfillmentOption option) {
    return fulfillmentOptionDao.save(option);
  }

  /**
   * @see  org.broadleafcommerce.core.order.service.FulfillmentOptionService#readAllFulfillmentOptions()
   */
  @Override public List<FulfillmentOption> readAllFulfillmentOptions() {
    return fulfillmentOptionDao.readAllFulfillmentOptions();
  }

  /**
   * @see  org.broadleafcommerce.core.order.service.FulfillmentOptionService#readAllFulfillmentOptionsByFulfillmentType(org.broadleafcommerce.core.order.service.type.FulfillmentType)
   */
  @Override public List<FulfillmentOption> readAllFulfillmentOptionsByFulfillmentType(FulfillmentType type) {
    return fulfillmentOptionDao.readAllFulfillmentOptionsByFulfillmentType(type);
  }
} // end class FulfillmentOptionServiceImpl
