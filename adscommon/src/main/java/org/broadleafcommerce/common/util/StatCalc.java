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

/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class StatCalc {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private int    count;                                // Number of numbers that have been entered.
  private double max       = Double.NEGATIVE_INFINITY; // Largest item seen.
  private double min       = Double.POSITIVE_INFINITY; // Smallest item seen.
  private double squareSum;                            // The sum of the squares of all the items.
  private double sum;                                  // The sum of all the items that have been entered.

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  num  DOCUMENT ME!
   */
  public void enter(double num) {
    // Add the number to the dataset.
    count++;
    sum       += num;
    squareSum += num * num;

    if (num > max) {
      max = num;
    }

    if (num < min) {
      min = num;
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int getCount() {
    // Return number of items that have been entered.
    return count;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public double getMax() {
    // Return the largest item that has been entered.
    // Value will be -infinity if no items have been entered.
    return max;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public double getMean() {
    // Return average of all the items that have been entered.
    // Value is Double.NaN if count == 0.
    return sum / count;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public double getMin() {
    // Return the smallest item that has been entered.
    // Value will be infinity if no items have been entered.
    return min;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public double getStandardDeviation() {
    // Return standard deviation of all the items that have been entered.
    // Value will be Double.NaN if count == 0.
    double mean = getMean();

    return Math.sqrt((squareSum / count) - (mean * mean));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public double getSum() {
    // Return the sum of all the items that have been entered.
    return sum;
  }

} // end class StatCalc
