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

package org.broadleafcommerce.openadmin.dto;

import java.io.Serializable;

import java.util.Map;

import org.broadleafcommerce.common.util.BLCMapUtils;
import org.broadleafcommerce.common.util.TypedClosure;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class ClassMetadata implements Serializable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String ceilingType;
  private String currencyCode = "USD";

  private Map<String, Property> pMap                = null;
  private ClassTree             polymorphicEntities;
  private Property[]            properties;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getCeilingType() {
    return ceilingType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getCurrencyCode() {
    return currencyCode;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<String, Property> getPMap() {
    if (pMap == null) {
      pMap = BLCMapUtils.keyedMap(properties, new TypedClosure<String, Property>() {
            @Override public String getKey(Property value) {
              return value.getName();
            }
          });
    }

    return pMap;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public ClassTree getPolymorphicEntities() {
    return polymorphicEntities;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Property[] getProperties() {
    return properties;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  type  DOCUMENT ME!
   */
  public void setCeilingType(String type) {
    this.ceilingType = type;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  currencyCode  DOCUMENT ME!
   */
  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  polymorphicEntities  DOCUMENT ME!
   */
  public void setPolymorphicEntities(ClassTree polymorphicEntities) {
    this.polymorphicEntities = polymorphicEntities;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  property  DOCUMENT ME!
   */
  public void setProperties(Property[] property) {
    this.properties = property;
  }
} // end class ClassMetadata
