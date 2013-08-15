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

package org.broadleafcommerce.openadmin.web.form;

/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class TranslationForm {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected String  ceilingEntity;

  /** DOCUMENT ME! */
  protected String  entityId;

  /** DOCUMENT ME! */
  protected Boolean isRte;

  /** DOCUMENT ME! */
  protected String  localeCode;

  /** DOCUMENT ME! */
  protected String  propertyName;

  /** DOCUMENT ME! */
  protected String  translatedValue;

  /** DOCUMENT ME! */
  protected Long    translationId;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getCeilingEntity() {
    return ceilingEntity;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getEntityId() {
    return entityId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getIsRte() {
    return (isRte == null) ? false : isRte;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getLocaleCode() {
    return localeCode;
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
  public String getTranslatedValue() {
    return translatedValue;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Long getTranslationId() {
    return translationId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  ceilingEntity  DOCUMENT ME!
   */
  public void setCeilingEntity(String ceilingEntity) {
    this.ceilingEntity = ceilingEntity;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  entityId  DOCUMENT ME!
   */
  public void setEntityId(String entityId) {
    this.entityId = entityId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  isRte  DOCUMENT ME!
   */
  public void setIsRte(Boolean isRte) {
    this.isRte = isRte;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  localeCode  DOCUMENT ME!
   */
  public void setLocaleCode(String localeCode) {
    this.localeCode = localeCode;
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
   * @param  translatedValue  DOCUMENT ME!
   */
  public void setTranslatedValue(String translatedValue) {
    this.translatedValue = translatedValue;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  translationId  DOCUMENT ME!
   */
  public void setTranslationId(Long translationId) {
    this.translationId = translationId;
  }

} // end class TranslationForm
