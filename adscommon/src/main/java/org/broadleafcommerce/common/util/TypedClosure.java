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
 * A class that provides for a typed closure that will return a the specified type value from the specified type.
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 *
 * @param    <K>  the type of the key to be returned
 * @param    <V>  the type of the value to generate a key for
 */
public interface TypedClosure<K, V> {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   value  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  K getKey(V value);

}
