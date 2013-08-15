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

import org.apache.commons.lang3.builder.EqualsBuilder;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class EntityFormAction implements Cloneable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  public static final String ADD    = "ADD";

  /** DOCUMENT ME! */
  public static final String SAVE   = "SAVE";

  /** DOCUMENT ME! */
  public static final String DELETE = "DELETE";

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected String buttonClass = "";

  /** DOCUMENT ME! */
  protected String buttonType  = "button";

  /** DOCUMENT ME! */
  protected String displayText = "";

  /** DOCUMENT ME! */
  protected String iconClass  = "";

  /** DOCUMENT ME! */
  protected String id         = "";

  /** DOCUMENT ME! */
  protected String urlPostfix = "";

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new EntityFormAction object.
   *
   * @param  id  DOCUMENT ME!
   */
  public EntityFormAction(String id) {
    this.id = id;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#clone()
   */
  @Override public EntityFormAction clone() {
    EntityFormAction cloned = new EntityFormAction(id);
    cloned.buttonType  = buttonType;
    cloned.buttonClass = buttonClass;
    cloned.urlPostfix  = urlPostfix;
    cloned.iconClass   = iconClass;
    cloned.displayText = displayText;

    return cloned;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object obj) {
    if (obj instanceof EntityFormAction) {
      EntityFormAction that = (EntityFormAction) obj;

      return new EqualsBuilder().append(buttonClass, that.buttonClass).append(urlPostfix, that.urlPostfix).append(
          iconClass, that.iconClass).append(displayText, that.displayText).build();
    }

    return false;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getButtonClass() {
    return buttonClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getButtonType() {
    return buttonType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getDisplayText() {
    return displayText;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getIconClass() {
    return iconClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getUrlPostfix() {
    return urlPostfix;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  buttonClass  DOCUMENT ME!
   */
  public void setButtonClass(String buttonClass) {
    this.buttonClass = buttonClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  buttonType  DOCUMENT ME!
   */
  public void setButtonType(String buttonType) {
    this.buttonType = buttonType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  displayText  DOCUMENT ME!
   */
  public void setDisplayText(String displayText) {
    this.displayText = displayText;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  iconClass  DOCUMENT ME!
   */
  public void setIconClass(String iconClass) {
    this.iconClass = iconClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  urlPostfix  DOCUMENT ME!
   */
  public void setUrlPostfix(String urlPostfix) {
    this.urlPostfix = urlPostfix;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   buttonClass  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public EntityFormAction withButtonClass(String buttonClass) {
    setButtonClass(buttonClass);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   buttonType  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public EntityFormAction withButtonType(String buttonType) {
    setButtonType(buttonType);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   displayText  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public EntityFormAction withDisplayText(String displayText) {
    setDisplayText(displayText);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   iconClass  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public EntityFormAction withIconClass(String iconClass) {
    setIconClass(iconClass);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   urlPostfix  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public EntityFormAction withUrlPostfix(String urlPostfix) {
    setUrlPostfix(urlPostfix);

    return this;
  }

} // end class EntityFormAction
