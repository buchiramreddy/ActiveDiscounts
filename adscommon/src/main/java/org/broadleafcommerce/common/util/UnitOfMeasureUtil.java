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

package org.broadleafcommerce.common.util;

import java.math.BigDecimal;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class UnitOfMeasureUtil {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   feet  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static BigDecimal convertFeetToInches(BigDecimal feet) {
    return feet.multiply(BigDecimal.valueOf(12));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   feet  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static BigDecimal convertFeetToMeters(BigDecimal feet) {
    return feet.multiply(BigDecimal.valueOf(0.3048));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   inches  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static BigDecimal convertInchesToFeet(BigDecimal inches) {
    return inches.multiply(BigDecimal.valueOf(0.083333));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   kilograms  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static BigDecimal convertKilogramsToPounds(BigDecimal kilograms) {
    return kilograms.multiply(BigDecimal.valueOf(0.45359237));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   meters  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static BigDecimal convertMetersToFeet(BigDecimal meters) {
    return meters.multiply(BigDecimal.valueOf(3.28084));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   ounces  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static BigDecimal convertOuncesToPounds(BigDecimal ounces) {
    return ounces.multiply(BigDecimal.valueOf(0.0625));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   pounds  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static BigDecimal convertPoundsToKilograms(BigDecimal pounds) {
    return pounds.multiply(BigDecimal.valueOf(2.20462262185));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   pounds  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static BigDecimal convertPoundsToOunces(BigDecimal pounds) {
    return pounds.multiply(BigDecimal.valueOf(16));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   dimension  DOCUMENT ME!
   * @param   type       DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static BigDecimal findInches(BigDecimal dimension, DimensionUnitOfMeasureType type) {
    if (type.equals(DimensionUnitOfMeasureType.CENTIMETERS)) {
      dimension = UnitOfMeasureUtil.convertFeetToInches(UnitOfMeasureUtil.convertMetersToFeet(
            dimension.multiply(BigDecimal.valueOf(0.01))));
    }

    if (type.equals(DimensionUnitOfMeasureType.METERS)) {
      dimension = UnitOfMeasureUtil.convertFeetToInches(UnitOfMeasureUtil.convertMetersToFeet(dimension));
    }

    if (type.equals(DimensionUnitOfMeasureType.FEET)) {
      dimension = UnitOfMeasureUtil.convertFeetToInches(dimension);
    }

    return dimension;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   weight  DOCUMENT ME!
   * @param   type    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static BigDecimal findOunces(BigDecimal weight, WeightUnitOfMeasureType type) {
    if (type.equals(WeightUnitOfMeasureType.KILOGRAMS)) {
      weight = UnitOfMeasureUtil.convertKilogramsToPounds(weight);
    }

    BigDecimal ounces = UnitOfMeasureUtil.convertPoundsToOunces(weight);

    return ounces;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   weight  DOCUMENT ME!
   * @param   type    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static BigDecimal findPounds(BigDecimal weight, WeightUnitOfMeasureType type) {
    if (type.equals(WeightUnitOfMeasureType.KILOGRAMS)) {
      weight = UnitOfMeasureUtil.convertKilogramsToPounds(weight);
    }

    return weight;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   weight  DOCUMENT ME!
   * @param   type    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static BigDecimal findRemainingOunces(BigDecimal weight, WeightUnitOfMeasureType type) {
    if (type.equals(WeightUnitOfMeasureType.KILOGRAMS)) {
      weight = UnitOfMeasureUtil.convertKilogramsToPounds(weight);
    }

    double     fractionalPounds = weight.doubleValue() - Math.floor(weight.doubleValue());
    BigDecimal ounces           = UnitOfMeasureUtil.convertPoundsToOunces(BigDecimal.valueOf(fractionalPounds));

    return ounces;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   weight  DOCUMENT ME!
   * @param   type    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static int findWholePounds(BigDecimal weight, WeightUnitOfMeasureType type) {
    weight = findPounds(weight, type);

    int pounds = Double.valueOf(Math.floor(weight.doubleValue())).intValue();

    return pounds;
  }
} // end class UnitOfMeasureUtil
