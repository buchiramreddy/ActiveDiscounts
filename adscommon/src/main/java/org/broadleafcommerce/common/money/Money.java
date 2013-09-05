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

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.Currency;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;
import org.broadleafcommerce.common.money.util.CurrencyAdapter;
import org.broadleafcommerce.common.util.xml.BigDecimalRoundingAdapter;
import org.broadleafcommerce.common.web.BroadleafRequestContext;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Money implements Serializable, Cloneable, Comparable<Money>, Externalizable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  public static final Money ZERO = new Money(BigDecimal.ZERO);

  //~ Instance fields --------------------------------------------------------------------------------------------------

  @XmlElement
  @XmlJavaTypeAdapter(value = BigDecimalRoundingAdapter.class)
  private BigDecimal             amount;

  @XmlElement
  @XmlJavaTypeAdapter(CurrencyAdapter.class)
  private final Currency currency;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new Money object.
   */
  public Money() {
    this(BankersRounding.zeroAmount(), defaultCurrency());
  }

  /**
   * Creates a new Money object.
   *
   * @param  currency  DOCUMENT ME!
   */
  public Money(Currency currency) {
    this(BankersRounding.zeroAmount(), currency);
  }

  /**
   * Creates a new Money object.
   *
   * @param  blCurrency  DOCUMENT ME!
   */
  public Money(BroadleafCurrency blCurrency) {
    this(0, Currency.getInstance(getCurrencyCode(blCurrency)));
  }

  /**
   * Creates a new Money object.
   *
   * @param  amount  DOCUMENT ME!
   */
  public Money(BigDecimal amount) {
    this(amount, defaultCurrency());
  }

  /**
   * Creates a new Money object.
   *
   * @param  amount  DOCUMENT ME!
   */
  public Money(double amount) {
    this(valueOf(amount), defaultCurrency());
  }

  /**
   * Creates a new Money object.
   *
   * @param  amount  DOCUMENT ME!
   */
  public Money(int amount) {
    this(BigDecimal.valueOf(amount).setScale(BankersRounding.getScaleForCurrency(defaultCurrency()),
        RoundingMode.HALF_EVEN), defaultCurrency());
  }

  /**
   * Creates a new Money object.
   *
   * @param  amount  DOCUMENT ME!
   */
  public Money(long amount) {
    this(BigDecimal.valueOf(amount).setScale(BankersRounding.getScaleForCurrency(defaultCurrency()),
        RoundingMode.HALF_EVEN), defaultCurrency());
  }

  /**
   * Creates a new Money object.
   *
   * @param  amount  DOCUMENT ME!
   */
  public Money(String amount) {
    this(valueOf(amount), defaultCurrency());
  }

  /**
   * Creates a new Money object.
   *
   * @param  amount      DOCUMENT ME!
   * @param  blCurrency  DOCUMENT ME!
   */
  public Money(BigDecimal amount, BroadleafCurrency blCurrency) {
    this(amount, Currency.getInstance(getCurrencyCode(blCurrency)));
  }

  /**
   * Creates a new Money object.
   *
   * @param  amount        DOCUMENT ME!
   * @param  currencyCode  DOCUMENT ME!
   */
  public Money(BigDecimal amount, String currencyCode) {
    this(amount, Currency.getInstance(currencyCode));
  }

  /**
   * Creates a new Money object.
   *
   * @param  amount    DOCUMENT ME!
   * @param  currency  DOCUMENT ME!
   */
  public Money(double amount, Currency currency) {
    this(valueOf(amount), currency);
  }

  /**
   * Creates a new Money object.
   *
   * @param  amount        DOCUMENT ME!
   * @param  currencyCode  DOCUMENT ME!
   */
  public Money(double amount, String currencyCode) {
    this(valueOf(amount), Currency.getInstance(currencyCode));
  }

  /**
   * Creates a new Money object.
   *
   * @param  amount    DOCUMENT ME!
   * @param  currency  DOCUMENT ME!
   */
  public Money(int amount, Currency currency) {
    this(BigDecimal.valueOf(amount).setScale(BankersRounding.getScaleForCurrency(currency), RoundingMode.HALF_EVEN),
      currency);
  }

  /**
   * Creates a new Money object.
   *
   * @param  amount        DOCUMENT ME!
   * @param  currencyCode  DOCUMENT ME!
   */
  public Money(int amount, String currencyCode) {
    this(BigDecimal.valueOf(amount).setScale(BankersRounding.getScaleForCurrency(Currency.getInstance(currencyCode)),
        RoundingMode.HALF_EVEN), Currency.getInstance(currencyCode));
  }

  /**
   * Creates a new Money object.
   *
   * @param  amount    DOCUMENT ME!
   * @param  currency  DOCUMENT ME!
   */
  public Money(long amount, Currency currency) {
    this(BigDecimal.valueOf(amount).setScale(BankersRounding.getScaleForCurrency(currency), RoundingMode.HALF_EVEN),
      currency);
  }

  /**
   * Creates a new Money object.
   *
   * @param  amount        DOCUMENT ME!
   * @param  currencyCode  DOCUMENT ME!
   */
  public Money(long amount, String currencyCode) {
    this(BigDecimal.valueOf(amount).setScale(BankersRounding.getScaleForCurrency(Currency.getInstance(currencyCode)),
        RoundingMode.HALF_EVEN), Currency.getInstance(currencyCode));
  }

  /**
   * Creates a new Money object.
   *
   * @param  amount    DOCUMENT ME!
   * @param  currency  DOCUMENT ME!
   */
  public Money(String amount, Currency currency) {
    this(valueOf(amount), currency);
  }

  /**
   * Creates a new Money object.
   *
   * @param  amount        DOCUMENT ME!
   * @param  currencyCode  DOCUMENT ME!
   */
  public Money(String amount, String currencyCode) {
    this(valueOf(amount), Currency.getInstance(currencyCode));
  }

  /**
   * Creates a new Money object.
   *
   * @param   amount    DOCUMENT ME!
   * @param   currency  DOCUMENT ME!
   *
   * @throws  IllegalArgumentException  DOCUMENT ME!
   */
  public Money(BigDecimal amount, Currency currency) {
    if (currency == null) {
      throw new IllegalArgumentException("currency cannot be null");
    }

    this.currency = currency;

    if (amount.compareTo(new BigDecimal(".01")) > -1) {
      this.amount = BankersRounding.setScale(amount);
    } else {
      this.amount = amount;
    }
  }

  /**
   * Creates a new Money object.
   *
   * @param  amount      DOCUMENT ME!
   * @param  blCurrency  DOCUMENT ME!
   * @param  scale       DOCUMENT ME!
   */
  public Money(BigDecimal amount, BroadleafCurrency blCurrency, int scale) {
    this(amount, Currency.getInstance(getCurrencyCode(blCurrency)), scale);
  }

  /**
   * Creates a new Money object.
   *
   * @param   amount    DOCUMENT ME!
   * @param   currency  DOCUMENT ME!
   * @param   scale     DOCUMENT ME!
   *
   * @throws  IllegalArgumentException  DOCUMENT ME!
   */
  public Money(BigDecimal amount, Currency currency, int scale) {
    if (currency == null) {
      throw new IllegalArgumentException("currency cannot be null");
    }

    this.currency = currency;
    this.amount   = BankersRounding.setScale(amount, scale);
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   money  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static Money abs(Money money) {
    return new Money(money.amount.abs(), money.currency);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Attempts to load a default currency by using the default locale.
   * {@link java.util.Currency#getInstance(java.util.Locale)} uses the country component of the locale to resolve the
   * currency. In some instances, the locale may not have a country component, in which case the default currency can be
   * controlled with a system property.
   *
   * @return  The default currency to use when none is specified
   */
  public static Currency defaultCurrency() {
    if ((CurrencyConsiderationContext.getCurrencyConsiderationContext() != null)
          && (CurrencyConsiderationContext.getCurrencyConsiderationContext().size() > 0)
          && (CurrencyConsiderationContext.getCurrencyDeterminationService() != null)) {
      return Currency.getInstance(CurrencyConsiderationContext.getCurrencyDeterminationService().getCurrencyCode(
            CurrencyConsiderationContext.getCurrencyConsiderationContext()));
    }

    // Check the BLC Thread
    BroadleafRequestContext brc = BroadleafRequestContext.getBroadleafRequestContext();

    if ((brc != null) && (brc.getBroadleafCurrency() != null)) {
      assert brc.getBroadleafCurrency().getCurrencyCode() != null;

      return Currency.getInstance(brc.getBroadleafCurrency().getCurrencyCode());
    }

    /*if (System.getProperty("currency.default") != null) {
      return Currency.getInstance(System.getProperty("currency.default"));
    }

    Locale locale = Locale.getDefault();

    if ((locale.getCountry() != null) && (locale.getCountry().length() == 2)) {
      return Currency.getInstance(locale);
    }*/

    return Currency.getInstance("INR");
  } // end method defaultCurrency

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   left   DOCUMENT ME!
   * @param   right  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static Money max(Money left, Money right) {
    return left.max(right);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   left   DOCUMENT ME!
   * @param   right  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static Money min(Money left, Money right) {
    return left.min(right);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   money  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static BigDecimal toAmount(Money money) {
    return ((money == null) ? null : money.amount);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   money  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static Currency toCurrency(Money money) {
    return ((money == null) ? null : money.currency);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   currencyCode  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static Money zero(String currencyCode) {
    return zero(Currency.getInstance(currencyCode));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   currency  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static Money zero(Currency currency) {
    return new Money(BankersRounding.zeroAmount(), currency);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money abs() {
    return new Money(amount.abs(), currency);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   other  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  UnsupportedOperationException  DOCUMENT ME!
   */
  public Money add(Money other) {
    if (!other.getCurrency().equals(getCurrency())) {
      if ((CurrencyConversionContext.getCurrencyConversionContext() != null)
            && (CurrencyConversionContext.getCurrencyConversionContext().size() > 0)
            && (CurrencyConversionContext.getCurrencyConversionService() != null)) {
        other = CurrencyConversionContext.getCurrencyConversionService().convertCurrency(other, getCurrency(),
            amount.scale());
      } else {
        throw new UnsupportedOperationException(
          "No currency conversion service is registered, cannot add different currency "
          + "types together (" + getCurrency().getCurrencyCode() + " " + other.getCurrency().getCurrencyCode() + ")");
      }
    }

    return new Money(amount.add(other.amount), currency,
        (amount.scale() == 0) ? BankersRounding.getScaleForCurrency(currency) : amount.scale());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#clone()
   */
  @Override public Object clone() {
    return new Money(amount, currency);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Comparable#compareTo(org.broadleafcommerce.common.money.Money)
   */
  @Override public int compareTo(Money other) {
    return amount.compareTo(other.amount);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   value  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int compareTo(BigDecimal value) {
    return amount.compareTo(value);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   amount  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money divide(double amount) {
    return this.divide(amount, RoundingMode.HALF_EVEN);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   amount  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money divide(int amount) {
    return this.divide(amount, RoundingMode.HALF_EVEN);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   divisor  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money divide(BigDecimal divisor) {
    return this.divide(divisor, RoundingMode.HALF_EVEN);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   amount        DOCUMENT ME!
   * @param   roundingMode  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money divide(double amount, RoundingMode roundingMode) {
    return divide(valueOf(amount), roundingMode);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   amount        DOCUMENT ME!
   * @param   roundingMode  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money divide(int amount, RoundingMode roundingMode) {
    BigDecimal value = BigDecimal.valueOf(amount);
    value = value.setScale(BankersRounding.getScaleForCurrency(currency), RoundingMode.HALF_EVEN);

    return divide(value, roundingMode);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   divisor       DOCUMENT ME!
   * @param   roundingMode  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money divide(BigDecimal divisor, RoundingMode roundingMode) {
    return new Money(amount.divide(divisor, amount.precision(), roundingMode), currency,
        (amount.scale() == 0) ? BankersRounding.getScaleForCurrency(currency) : amount.scale());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public double doubleValue() {
    try {
      return amount.doubleValue();
    } catch (NumberFormatException e) {
      // HotSpot bug in JVM < 1.4.2_06.
      if (e.getMessage().equals("For input string: \"0.00null\"")) {
        return amount.doubleValue();
      } else {
        throw e;
      }
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Money)) {
      return false;
    }

    Money money = (Money) o;

    if ((amount != null) ? (!amount.equals(money.amount)) : (money.amount != null)) {
      return false;
    }

    if (isZero()) {
      return true;
    }

    if ((currency != null) ? (!currency.equals(money.currency)) : (money.currency != null)) {
      return false;
    }

    return true;
  } // end method equals

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public BigDecimal getAmount() {
    return amount;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Currency getCurrency() {
    return currency;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   other  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean greaterThan(Money other) {
    return compareTo(other) > 0;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   value  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean greaterThan(BigDecimal value) {
    return amount.compareTo(value) > 0;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   other  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean greaterThanOrEqual(Money other) {
    return compareTo(other) >= 0;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   value  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean greaterThanOrEqual(BigDecimal value) {
    return amount.compareTo(value) >= 0;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    int result = (amount != null) ? amount.hashCode() : 0;
    result = (31 * result) + ((currency != null) ? currency.hashCode() : 0);

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isZero() {
    return amount.compareTo(BankersRounding.zeroAmount()) == 0;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   other  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean lessThan(Money other) {
    return compareTo(other) < 0;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   value  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean lessThan(BigDecimal value) {
    return amount.compareTo(value) < 0;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   other  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean lessThanOrEqual(Money other) {
    return compareTo(other) <= 0;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   value  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean lessThanOrEqual(BigDecimal value) {
    return amount.compareTo(value) <= 0;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   other  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money max(Money other) {
    if (other == null) {
      return this;
    }

    return greaterThan(other) ? this : other;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   other  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money min(Money other) {
    if (other == null) {
      return this;
    }

    return lessThan(other) ? this : other;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   amount  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money multiply(double amount) {
    return multiply(valueOf(amount));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   amount  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money multiply(int amount) {
    BigDecimal value = BigDecimal.valueOf(amount);
    value = value.setScale(BankersRounding.getScaleForCurrency(currency), RoundingMode.HALF_EVEN);

    return multiply(value);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   multiplier  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money multiply(BigDecimal multiplier) {
    return new Money(amount.multiply(multiplier), currency,
        (amount.scale() == 0) ? BankersRounding.getScaleForCurrency(currency) : amount.scale());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money negate() {
    return new Money(amount.negate(), currency);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.io.Externalizable#readExternal(java.io.ObjectInput)
   */
  @Override public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    // Read in the server properties from the client representation.
    amount = new BigDecimal(in.readFloat());

  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String stringValue() {
    return amount.toString() + " " + currency.getCurrencyCode();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   other  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  UnsupportedOperationException  DOCUMENT ME!
   */
  public Money subtract(Money other) {
    if (!other.getCurrency().equals(getCurrency())) {
      if ((CurrencyConversionContext.getCurrencyConversionContext() != null)
            && (CurrencyConversionContext.getCurrencyConversionContext().size() > 0)
            && (CurrencyConversionContext.getCurrencyConversionService() != null)) {
        other = CurrencyConversionContext.getCurrencyConversionService().convertCurrency(other, getCurrency(),
            amount.scale());
      } else {
        throw new UnsupportedOperationException(
          "No currency conversion service is registered, cannot subtract different currency "
          + "types (" + getCurrency().getCurrencyCode() + ", " + other.getCurrency().getCurrencyCode() + ")");
      }
    }

    return new Money(amount.subtract(other.amount), currency,
        (amount.scale() == 0) ? BankersRounding.getScaleForCurrency(currency) : amount.scale());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#toString()
   */
  @Override public String toString() {
    return amount.toString();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.io.Externalizable#writeExternal(java.io.ObjectOutput)
   */
  @Override public void writeExternal(ObjectOutput out) throws IOException {
    // Write out the client properties from the server representation.
    out.writeFloat(amount.floatValue());
    // out.writeObject(currency);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money zero() {
    return Money.zero(currency);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   blCurrency  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected static String getCurrencyCode(BroadleafCurrency blCurrency) {
    if (blCurrency != null) {
      return blCurrency.getCurrencyCode();
    } else {
      return defaultCurrency().getCurrencyCode();
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Ensures predictable results by converting the double into a string then calling the BigDecimal string constructor.
   *
   * @param   amount  The amount
   *
   * @return  BigDecimal a big decimal with a predictable value
   */
  private static BigDecimal valueOf(double amount) {
    return valueOf(String.valueOf(amount));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  private static BigDecimal valueOf(String amount) {
    BigDecimal value = new BigDecimal(amount);

    if (value.scale() < 2) {
      value = value.setScale(BankersRounding.getScaleForCurrency(defaultCurrency()), RoundingMode.HALF_EVEN);
    }

    return value;
  }

} // end class Money
