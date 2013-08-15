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

package org.broadleafcommerce.core.web.processor;

import javax.annotation.Resource;

import org.broadleafcommerce.common.web.dialect.AbstractModelVariableModifierProcessor;

import org.broadleafcommerce.core.order.domain.NullOrderImpl;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.OrderService;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.web.core.CustomerState;

import org.springframework.stereotype.Component;

import org.thymeleaf.Arguments;

import org.thymeleaf.dom.Element;


/**
 * A Thymeleaf processor that will add the desired named order to the model.
 *
 * @author   elbertbautista
 * @version  $Revision$, $Date$
 */
@Component("blNamedOrderProcessor")
public class NamedOrderProcessor extends AbstractModelVariableModifierProcessor {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blOrderService")
  protected OrderService orderService;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Sets the name of this processor to be used in Thymeleaf template.
   *
   * <p>NOTE: thymeleaf normalizes the attribute names by converting all to lower-case we will use the underscore
   * instead of camel case to avoid confusion</p>
   */
  public NamedOrderProcessor() {
    super("named_order");
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.thymeleaf.processor.AbstractProcessor#getPrecedence()
   */
  @Override public int getPrecedence() {
    return 10000;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.web.dialect.AbstractModelVariableModifierProcessor#modifyModelAttributes(org.thymeleaf.Arguments,
   *       org.thymeleaf.dom.Element)
   */
  @Override protected void modifyModelAttributes(Arguments arguments, Element element) {
    Customer customer = CustomerState.getCustomer();

    String orderVar  = element.getAttributeValue("orderVar");
    String orderName = element.getAttributeValue("orderName");

    Order order = orderService.findNamedOrderForCustomer(orderName, customer);

    if (order != null) {
      addToModel(arguments, orderVar, order);
    } else {
      addToModel(arguments, orderVar, new NullOrderImpl());
    }
  }
} // end class NamedOrderProcessor
