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

package org.broadleafcommerce.common.value;

import java.io.Serializable;


/**
 * Describes simple classes that can be assigned a name and value.
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public interface ValueAssignable<T extends Serializable> extends Serializable {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * The name.
   *
   * @return  The name
   */
  String getName();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The value.
   *
   * @return  The value
   */
  T getValue();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The name.
   *
   * @param  name  The name
   */
  void setName(String name);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The value.
   *
   * @param  value  The value
   */
  void setValue(T value);
} // end interface ValueAssignable
