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

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;


/**
 * Convenience methods for interacting with collections.
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public class BLCCollectionUtils {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Delegates to
   * {@link org.apache.commons.collections.CollectionUtils#collect(java.util.Collection, org.apache.commons.collections.Transformer)}
   * , but performs the necessary type coercion to allow the returned collection to be correctly casted based on the
   * TypedTransformer.
   *
   * @param   <T>              DOCUMENT ME!
   * @param   inputCollection  DOCUMENT ME!
   * @param   transformer      DOCUMENT ME!
   *
   * @return  the typed, collected Collection
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public static <T> Collection<T> collect(Collection inputCollection, TypedTransformer<T> transformer) {
    return CollectionUtils.collect(inputCollection, transformer);
  }

}
