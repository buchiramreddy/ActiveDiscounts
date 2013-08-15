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

package org.broadleafcommerce.common.currency.domain;

/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class NullBroadleafCurrency implements BroadleafCurrency {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 7926395625817119455L;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.currency.domain.BroadleafCurrency#getCurrencyCode()
   */
  @Override public String getCurrencyCode() {
    return null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.currency.domain.BroadleafCurrency#getDefaultFlag()
   */
  @Override public boolean getDefaultFlag() {
    return false;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.currency.domain.BroadleafCurrency#getFriendlyName()
   */
  @Override public String getFriendlyName() {
    return null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.currency.domain.BroadleafCurrency#setCurrencyCode(java.lang.String)
   */
  @Override public void setCurrencyCode(String code) {
    // Do nothing
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.currency.domain.BroadleafCurrency#setDefaultFlag(boolean)
   */
  @Override public void setDefaultFlag(boolean defaultFlag) {
    // Do nothing
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.currency.domain.BroadleafCurrency#setFriendlyName(java.lang.String)
   */
  @Override public void setFriendlyName(String friendlyName) {
    // Do nothing
  }

} // end class NullBroadleafCurrency
