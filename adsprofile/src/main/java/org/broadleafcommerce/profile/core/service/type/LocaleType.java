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

package org.broadleafcommerce.profile.core.service.type;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import org.broadleafcommerce.common.BroadleafEnumerationType;


/**
 * An extendible enumeration of locale types.
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class LocaleType implements Serializable, BroadleafEnumerationType {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  private static final Map<String, LocaleType> TYPES = new LinkedHashMap<String, LocaleType>();

  /** DOCUMENT ME! */
  public static final LocaleType US_ENGLISH      = new LocaleType("en_US", "US English", Locale.US);

  /** DOCUMENT ME! */
  public static final LocaleType CANADA          = new LocaleType("en_CA", "Canada English", Locale.CANADA);

  /** DOCUMENT ME! */
  public static final LocaleType CANADA_FRENCH   = new LocaleType("fr_CA", "Canada French", Locale.CANADA_FRENCH);

  /** DOCUMENT ME! */
  public static final LocaleType CHINA           = new LocaleType("zh_CN", "China", Locale.CHINA);

  /** DOCUMENT ME! */
  public static final LocaleType CHINA_ENGLISH   = new LocaleType("en_CN", "China English", new Locale("CN", "en"));

  /** DOCUMENT ME! */
  public static final LocaleType FRANCE          = new LocaleType("fr_FR", "France", Locale.FRANCE);

  /** DOCUMENT ME! */
  public static final LocaleType FRANCE_ENGLISH  = new LocaleType("en_FR", "France English", new Locale("FR", "en"));

  /** DOCUMENT ME! */
  public static final LocaleType GERMANY         = new LocaleType("de_DE", "Germany", Locale.GERMANY);

  /** DOCUMENT ME! */
  public static final LocaleType GERMANY_ENGLISH = new LocaleType("en_DE", "Germany English", new Locale("DE", "en"));

  /** DOCUMENT ME! */
  public static final LocaleType ITALY          = new LocaleType("it_IT", "Italy", Locale.ITALY);

  /** DOCUMENT ME! */
  public static final LocaleType ITALY_ENGLISH  = new LocaleType("en_IT", "Italy English", new Locale("IT", "en"));

  /** DOCUMENT ME! */
  public static final LocaleType JAPAN          = new LocaleType("ja_JP", "Japan", Locale.JAPAN);

  /** DOCUMENT ME! */
  public static final LocaleType JAPAN_ENGLISH  = new LocaleType("en_JP", "Japan English", new Locale("JP", "en"));

  /** DOCUMENT ME! */
  public static final LocaleType KOREA          = new LocaleType("ko_KR", "Korea", Locale.KOREA);

  /** DOCUMENT ME! */
  public static final LocaleType KOREA_ENGLISH  = new LocaleType("en_KR", "Korea English", new Locale("KR", "en"));

  /** DOCUMENT ME! */
  public static final LocaleType INDIA_HINDI    = new LocaleType("hi_IN", "India Hindi", new Locale("IN", "hi"));

  /** DOCUMENT ME! */
  public static final LocaleType INDIA_ENGLISH  = new LocaleType("en_IN", "India English", new Locale("IN", "en"));

  /** DOCUMENT ME! */
  public static final LocaleType UK_ENGLISH     = new LocaleType("en_UK", "UK English", Locale.UK);

  /** DOCUMENT ME! */
  public static final LocaleType TAIWAN         = new LocaleType("zh_TW", "Taiwan", Locale.TAIWAN);

  /** DOCUMENT ME! */
  public static final LocaleType TAIWAN_ENGLISH = new LocaleType("en_TW", "Taiwan English", new Locale("TW", "en"));

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String friendlyType;
  private Locale locale;

  private String type;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new LocaleType object.
   */
  public LocaleType() {
    // do nothing
  }

  /**
   * Creates a new LocaleType object.
   *
   * @param  type          DOCUMENT ME!
   * @param  friendlyType  DOCUMENT ME!
   * @param  locale        DOCUMENT ME!
   */
  public LocaleType(final String type, final String friendlyType, final Locale locale) {
    this.friendlyType = friendlyType;
    this.locale       = locale;
    setType(type);
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   type  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static LocaleType getInstance(final String type) {
    return TYPES.get(type);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  items  DOCUMENT ME!
   */
  public static void main(String[] items) {
    System.out.println(Locale.TAIWAN.toString());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    LocaleType other = (LocaleType) obj;

    if (type == null) {
      if (other.type != null) {
        return false;
      }
    } else if (!type.equals(other.type)) {
      return false;
    }

    return true;
  } // end method equals

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.BroadleafEnumerationType#getFriendlyType()
   */
  @Override public String getFriendlyType() {
    return friendlyType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Locale getLocale() {
    return locale;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.BroadleafEnumerationType#getType()
   */
  @Override public String getType() {
    return type;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((type == null) ? 0 : type.hashCode());

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  private void setType(final String type) {
    this.type = type;

    if (!TYPES.containsKey(type)) {
      TYPES.put(type, this);
    }
  }
} // end class LocaleType
