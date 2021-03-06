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

package org.broadleafcommerce.core.web.checkout.model;

import java.io.Serializable;

import java.util.List;

import org.broadleafcommerce.core.order.service.call.OrderMultishipOptionDTO;


/**
 * This form is used to bind multiship options in a way that doesn't require the actual objects to be instantiated -- we
 * handle that at the controller level.
 *
 * @see      org.broadleafcommerce.core.order.service.call.OrderMultishipOptionDTO
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public class OrderMultishipOptionForm implements Serializable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = -5989681894142759293L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected List<OrderMultishipOptionDTO> options;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<OrderMultishipOptionDTO> getOptions() {
    return options;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  options  DOCUMENT ME!
   */
  public void setOptions(List<OrderMultishipOptionDTO> options) {
    this.options = options;
  }

} // end class OrderMultishipOptionForm
