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

package org.broadleafcommerce.openadmin.server.service.persistence.module.provider;

import java.math.BigDecimal;

import java.text.NumberFormat;

import java.util.Currency;
import java.util.Locale;

import org.broadleafcommerce.openadmin.dto.Property;
import org.broadleafcommerce.openadmin.server.service.persistence.PersistenceException;
import org.broadleafcommerce.openadmin.server.service.persistence.module.provider.request.ExtractValueRequest;
import org.broadleafcommerce.openadmin.server.service.type.FieldProviderResponse;


/**
 * Abstract persistence provider that provides a method to actually handle formatting moneys.
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public abstract class AbstractMoneyFieldPersistenceProvider extends FieldPersistenceProviderAdapter {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.persistence.module.provider.FieldPersistenceProviderAdapter#extractValue(org.broadleafcommerce.openadmin.server.service.persistence.module.provider.request.ExtractValueRequest,
   *       org.broadleafcommerce.openadmin.dto.Property)
   */
  @Override public FieldProviderResponse extractValue(ExtractValueRequest extractValueRequest, Property property)
    throws PersistenceException {
    if (!canHandleExtraction(extractValueRequest, property)) {
      return FieldProviderResponse.NOT_HANDLED;
    }

    if (extractValueRequest.getRequestedValue() == null) {
      return FieldProviderResponse.NOT_HANDLED;
    }

    property.setValue(formatValue((BigDecimal) extractValueRequest.getRequestedValue(), extractValueRequest, property));
    property.setDisplayValue(formatDisplayValue((BigDecimal) extractValueRequest.getRequestedValue(),
        extractValueRequest, property));

    return FieldProviderResponse.HANDLED_BREAK;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   extractValueRequest  DOCUMENT ME!
   * @param   property             DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected abstract boolean canHandleExtraction(ExtractValueRequest extractValueRequest, Property property);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   extractValueRequest  DOCUMENT ME!
   * @param   property             DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected abstract Currency getCurrency(ExtractValueRequest extractValueRequest, Property property);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   extractValueRequest  DOCUMENT ME!
   * @param   property             DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected abstract Locale getLocale(ExtractValueRequest extractValueRequest, Property property);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   value                DOCUMENT ME!
   * @param   extractValueRequest  DOCUMENT ME!
   * @param   property             DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected String formatDisplayValue(BigDecimal value, ExtractValueRequest extractValueRequest, Property property) {
    Locale       locale   = getLocale(extractValueRequest, property);
    Currency     currency = getCurrency(extractValueRequest, property);
    NumberFormat format   = NumberFormat.getCurrencyInstance(locale);
    format.setCurrency(currency);

    return format.format(value);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   value                DOCUMENT ME!
   * @param   extractValueRequest  DOCUMENT ME!
   * @param   property             DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected String formatValue(BigDecimal value, ExtractValueRequest extractValueRequest, Property property) {
    NumberFormat format = NumberFormat.getInstance();
    format.setMaximumFractionDigits(2);
    format.setMinimumFractionDigits(2);

    return format.format(value);
  }

} // end class AbstractMoneyFieldPersistenceProvider
