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
 * Convenience methods for interacting with arrays.
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public class BLCArrayUtils {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Given an array and a typed predicate, determines if the array has an object that matches the condition of the
   * predicate. The predicate should evaluate to true when a match occurs.
   *
   * @param   <T>        DOCUMENT ME!
   * @param   array      DOCUMENT ME!
   * @param   predicate  DOCUMENT ME!
   *
   * @return  whether or not the array contains an element that matches the predicate
   */
  public static <T> boolean contains(T[] array, TypedPredicate<T> predicate) {
    for (T o : array) {
      if (predicate.evaluate(o)) {
        return true;
      }
    }

    return false;
  }


} // end class BLCArrayUtils
