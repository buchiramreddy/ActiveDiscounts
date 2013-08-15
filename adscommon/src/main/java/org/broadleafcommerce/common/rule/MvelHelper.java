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

package org.broadleafcommerce.common.rule;

import java.math.BigDecimal;

import java.text.ParseException;

import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.util.FormatUtil;


/**
 * Helper class for some common rule functions that can be called from mvel. An instance of this class is available to
 * the mvel runtime under the variable name mvelHelper.
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class MvelHelper {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   type        DOCUMENT ME!
   * @param   fieldValue  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  RuntimeException          DOCUMENT ME!
   * @throws  IllegalArgumentException  DOCUMENT ME!
   */
  public static Object convertField(String type, String fieldValue) {
    if (fieldValue == null) {
      return null;
    }

    try {
      if (type.equals(SupportedFieldType.BOOLEAN.toString())) {
        return Boolean.parseBoolean(fieldValue);
      } else if (type.equals(SupportedFieldType.DATE.toString())) {
        return FormatUtil.getTimeZoneFormat().parse(fieldValue);
      } else if (type.equals(SupportedFieldType.INTEGER.toString())) {
        return Integer.parseInt(fieldValue);
      } else if (type.equals(SupportedFieldType.MONEY.toString())
            || type.equals(SupportedFieldType.DECIMAL.toString())) {
        return new BigDecimal(fieldValue);
      }
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }

    throw new IllegalArgumentException("Unrecognized type(" + type + ") for map field conversion.");
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   value  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static Object toUpperCase(String value) {
    if (value == null) {
      return null;
    }

    return value.toUpperCase();
  }
} // end class MvelHelper
