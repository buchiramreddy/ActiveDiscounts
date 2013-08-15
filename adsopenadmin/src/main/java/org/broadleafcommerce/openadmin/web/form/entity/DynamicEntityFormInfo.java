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

package org.broadleafcommerce.openadmin.web.form.entity;

/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class DynamicEntityFormInfo {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected String ceilingClassName;

  /** DOCUMENT ME! */
  protected String criteriaName;

  /** DOCUMENT ME! */
  protected String propertyName;

  /** DOCUMENT ME! */
  protected String propertyValue;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getCeilingClassName() {
    return ceilingClassName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getCriteriaName() {
    return criteriaName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getPropertyName() {
    return propertyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getPropertyValue() {
    return propertyValue;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  ceilingClassName  DOCUMENT ME!
   */
  public void setCeilingClassName(String ceilingClassName) {
    this.ceilingClassName = ceilingClassName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  criteriaName  DOCUMENT ME!
   */
  public void setCriteriaName(String criteriaName) {
    this.criteriaName = criteriaName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  propertyName  DOCUMENT ME!
   */
  public void setPropertyName(String propertyName) {
    this.propertyName = propertyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  propertyValue  DOCUMENT ME!
   */
  public void setPropertyValue(String propertyValue) {
    this.propertyValue = propertyValue;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   ceilingClassName  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public DynamicEntityFormInfo withCeilingClassName(String ceilingClassName) {
    setCeilingClassName(ceilingClassName);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   criteriaName  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public DynamicEntityFormInfo withCriteriaName(String criteriaName) {
    setCriteriaName(criteriaName);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   propertyName  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public DynamicEntityFormInfo withPropertyName(String propertyName) {
    setPropertyName(propertyName);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   propertyValue  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public DynamicEntityFormInfo withPropertyValue(String propertyValue) {
    setPropertyValue(propertyValue);

    return this;
  }

} // end class DynamicEntityFormInfo
