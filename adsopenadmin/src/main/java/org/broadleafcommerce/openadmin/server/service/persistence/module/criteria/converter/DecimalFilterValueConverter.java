/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.openadmin.server.service.persistence.module.criteria.converter;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import org.springframework.stereotype.Component;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
@Component("blDecimalFilterValueConverter")
public class DecimalFilterValueConverter implements FilterValueConverter<BigDecimal> {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.persistence.module.criteria.converter.FilterValueConverter#convert(java.lang.String)
   */
  @Override public BigDecimal convert(String stringValue) {
    if (StringUtils.isEmpty(stringValue)) {
      return null;
    }

    try {
      return new BigDecimal(stringValue);
    } catch (NumberFormatException e) {
      return null;
    }
  }
}
