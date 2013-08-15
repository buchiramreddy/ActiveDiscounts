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

package org.broadleafcommerce.common.money;

import java.math.BigDecimal;

import java.util.Currency;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public final class BankersRounding {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  public static final int DEFAULT_SCALE = 2;

  /** DOCUMENT ME! */
  public static final BigDecimal ZERO = setScale(0);

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   dividend  DOCUMENT ME!
   * @param   divisor   DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static BigDecimal divide(BigDecimal dividend, BigDecimal divisor) {
    return divide(DEFAULT_SCALE, dividend, divisor);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   scale     DOCUMENT ME!
   * @param   dividend  DOCUMENT ME!
   * @param   divisor   DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static BigDecimal divide(int scale, BigDecimal dividend, BigDecimal divisor) {
    return dividend.divide(divisor, scale, BigDecimal.ROUND_HALF_EVEN);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   scale     DOCUMENT ME!
   * @param   dividend  DOCUMENT ME!
   * @param   divisor   DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static double divide(int scale, double dividend, double divisor) {
    return divide(setScale(scale, dividend), setScale(scale, divisor)).doubleValue();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   currency  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static int getScaleForCurrency(Currency currency) {
    if (currency != null) {
      return currency.getDefaultFractionDigits();
    } else {
      return DEFAULT_SCALE;
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   scale         DOCUMENT ME!
   * @param   multiplicand  DOCUMENT ME!
   * @param   multiplier    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static double multiply(int scale, double multiplicand, double multiplier) {
    return setScale(scale, multiplicand).multiply(setScale(scale, multiplier)).doubleValue();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   amount  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static BigDecimal setScale(BigDecimal amount) {
    return setScale(DEFAULT_SCALE, amount);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   amount  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static BigDecimal setScale(double amount) {
    return setScale(DEFAULT_SCALE, new BigDecimal(amount));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   scale   DOCUMENT ME!
   * @param   amount  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static BigDecimal setScale(int scale, BigDecimal amount) {
    return amount.setScale(scale, BigDecimal.ROUND_HALF_EVEN);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   scale   DOCUMENT ME!
   * @param   amount  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static BigDecimal setScale(int scale, double amount) {
    return setScale(scale, new BigDecimal(amount));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   amount  DOCUMENT ME!
   * @param   scale   DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static BigDecimal setScale(BigDecimal amount, int scale) {
    return setScale(scale, amount);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static BigDecimal zeroAmount() {
    return ZERO;
  }
} // end class BankersRounding
