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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.broadleafcommerce.openadmin.dto.visitor.MetadataVisitor;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public abstract class FieldMetadata implements Serializable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  // Additional metadata not supported as first class
  private Map<String, Object> additionalMetadata = new HashMap<String, Object>();
  private String[]            availableToTypes;

  // temporary fields
  private Boolean childrenExcluded;
  private String  currencyCodeField;
  private Boolean excluded;
  private String  fieldName;
  private String  friendlyName;

  private String  inheritedFromType;
  private Integer order;
  private String  owningClass;
  private String  owningClassFriendlyName;
  private String  prefix;
  private String  securityLevel;
  private String  showIfProperty;

  private String  tab;
  private Integer tabOrder;
  private String  targetClass;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  visitor  DOCUMENT ME!
   */
  public abstract void accept(MetadataVisitor visitor);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public abstract FieldMetadata cloneFieldMetadata();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof FieldMetadata)) {
      return false;
    }

    FieldMetadata that = (FieldMetadata) o;

    if ((additionalMetadata != null) ? (!additionalMetadata.equals(that.additionalMetadata))
                                     : (that.additionalMetadata != null)) {
      return false;
    }

    if (!Arrays.equals(availableToTypes, that.availableToTypes)) {
      return false;
    }

    if ((childrenExcluded != null) ? (!childrenExcluded.equals(that.childrenExcluded))
                                   : (that.childrenExcluded != null)) {
      return false;
    }

    if ((currencyCodeField != null) ? (!currencyCodeField.equals(that.currencyCodeField))
                                    : (that.currencyCodeField
            != null)) {
      return false;
    }

    if ((excluded != null) ? (!excluded.equals(that.excluded)) : (that.excluded != null)) {
      return false;
    }

    if ((fieldName != null) ? (!fieldName.equals(that.fieldName)) : (that.fieldName != null)) {
      return false;
    }

    if ((friendlyName != null) ? (!friendlyName.equals(that.friendlyName)) : (that.friendlyName != null)) {
      return false;
    }

    if ((inheritedFromType != null) ? (!inheritedFromType.equals(that.inheritedFromType))
                                    : (that.inheritedFromType
            != null)) {
      return false;
    }

    if ((order != null) ? (!order.equals(that.order)) : (that.order != null)) {
      return false;
    }

    if ((owningClass != null) ? (!owningClass.equals(that.owningClass)) : (that.owningClass != null)) {
      return false;
    }

    if ((owningClassFriendlyName != null) ? (!owningClassFriendlyName.equals(that.owningClassFriendlyName))
                                          : (that.owningClassFriendlyName != null)) {
      return false;
    }

    if ((prefix != null) ? (!prefix.equals(that.prefix)) : (that.prefix != null)) {
      return false;
    }

    if ((securityLevel != null) ? (!securityLevel.equals(that.securityLevel)) : (that.securityLevel != null)) {
      return false;
    }

    if ((showIfProperty != null) ? (!showIfProperty.equals(that.showIfProperty)) : (that.showIfProperty != null)) {
      return false;
    }

    if ((tab != null) ? (!tab.equals(that.tab)) : (that.tab != null)) {
      return false;
    }

    if ((tabOrder != null) ? (!tabOrder.equals(that.tabOrder)) : (that.tabOrder != null)) {
      return false;
    }

    if ((targetClass != null) ? (!targetClass.equals(that.targetClass)) : (that.targetClass != null)) {
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
  public Map<String, Object> getAdditionalMetadata() {
    return additionalMetadata;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String[] getAvailableToTypes() {
    return availableToTypes;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getChildrenExcluded() {
    return childrenExcluded;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getCurrencyCodeField() {
    return currencyCodeField;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getExcluded() {
    return excluded;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getFieldName() {
    return fieldName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getFriendlyName() {
    return friendlyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getInheritedFromType() {
    return inheritedFromType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Integer getOrder() {
    return order;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getOwningClass() {
    return owningClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getOwningClassFriendlyName() {
    return owningClassFriendlyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getPrefix() {
    return prefix;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getSecurityLevel() {
    return securityLevel;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getShowIfProperty() {
    return showIfProperty;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getTab() {
    return tab;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Integer getTabOrder() {
    return tabOrder;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getTargetClass() {
    return targetClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    int result = (inheritedFromType != null) ? inheritedFromType.hashCode() : 0;
    result = (31 * result) + ((availableToTypes != null) ? Arrays.hashCode(availableToTypes) : 0);
    result = (31 * result) + ((excluded != null) ? excluded.hashCode() : 0);
    result = (31 * result) + ((friendlyName != null) ? friendlyName.hashCode() : 0);
    result = (31 * result) + ((securityLevel != null) ? securityLevel.hashCode() : 0);
    result = (31 * result) + ((order != null) ? order.hashCode() : 0);
    result = (31 * result) + ((owningClassFriendlyName != null) ? owningClassFriendlyName.hashCode() : 0);
    result = (31 * result) + ((tab != null) ? tab.hashCode() : 0);
    result = (31 * result) + ((tabOrder != null) ? tabOrder.hashCode() : 0);
    result = (31 * result) + ((childrenExcluded != null) ? childrenExcluded.hashCode() : 0);
    result = (31 * result) + ((targetClass != null) ? targetClass.hashCode() : 0);
    result = (31 * result) + ((owningClass != null) ? owningClass.hashCode() : 0);
    result = (31 * result) + ((prefix != null) ? prefix.hashCode() : 0);
    result = (31 * result) + ((fieldName != null) ? fieldName.hashCode() : 0);
    result = (31 * result) + ((showIfProperty != null) ? showIfProperty.hashCode() : 0);
    result = (31 * result) + ((currencyCodeField != null) ? currencyCodeField.hashCode() : 0);
    result = (31 * result) + ((additionalMetadata != null) ? additionalMetadata.hashCode() : 0);

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  additionalMetadata  DOCUMENT ME!
   */
  public void setAdditionalMetadata(Map<String, Object> additionalMetadata) {
    this.additionalMetadata = additionalMetadata;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  availableToTypes  DOCUMENT ME!
   */
  public void setAvailableToTypes(String[] availableToTypes) {
    Arrays.sort(availableToTypes);
    this.availableToTypes = availableToTypes;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  childrenExcluded  DOCUMENT ME!
   */
  public void setChildrenExcluded(Boolean childrenExcluded) {
    this.childrenExcluded = childrenExcluded;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  currencyCodeField  DOCUMENT ME!
   */
  public void setCurrencyCodeField(String currencyCodeField) {
    this.currencyCodeField = currencyCodeField;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  excluded  DOCUMENT ME!
   */
  public void setExcluded(Boolean excluded) {
    this.excluded = excluded;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  fieldName  DOCUMENT ME!
   */
  public void setFieldName(String fieldName) {
    this.fieldName = fieldName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  friendlyName  DOCUMENT ME!
   */
  public void setFriendlyName(String friendlyName) {
    this.friendlyName = friendlyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  inheritedFromType  DOCUMENT ME!
   */
  public void setInheritedFromType(String inheritedFromType) {
    this.inheritedFromType = inheritedFromType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  order  DOCUMENT ME!
   */
  public void setOrder(Integer order) {
    this.order = order;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  owningClass  DOCUMENT ME!
   */
  public void setOwningClass(String owningClass) {
    this.owningClass = owningClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  owningClassFriendlyName  DOCUMENT ME!
   */
  public void setOwningClassFriendlyName(String owningClassFriendlyName) {
    this.owningClassFriendlyName = owningClassFriendlyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  prefix  DOCUMENT ME!
   */
  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  securityLevel  DOCUMENT ME!
   */
  public void setSecurityLevel(String securityLevel) {
    this.securityLevel = securityLevel;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  showIfProperty  DOCUMENT ME!
   */
  public void setShowIfProperty(String showIfProperty) {
    this.showIfProperty = showIfProperty;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  tab  DOCUMENT ME!
   */
  public void setTab(String tab) {
    this.tab = tab;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  tabOrder  DOCUMENT ME!
   */
  public void setTabOrder(Integer tabOrder) {
    this.tabOrder = tabOrder;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  targetClass  DOCUMENT ME!
   */
  public void setTargetClass(String targetClass) {
    this.targetClass = targetClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   metadata  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected FieldMetadata populate(FieldMetadata metadata) {
    metadata.inheritedFromType = inheritedFromType;

    if (availableToTypes != null) {
      metadata.availableToTypes = new String[availableToTypes.length];
      System.arraycopy(availableToTypes, 0, metadata.availableToTypes, 0, availableToTypes.length);
    }

    metadata.excluded                = excluded;
    metadata.friendlyName            = friendlyName;
    metadata.owningClassFriendlyName = owningClassFriendlyName;
    metadata.securityLevel           = securityLevel;
    metadata.order                   = order;
    metadata.targetClass             = targetClass;
    metadata.owningClass             = owningClass;
    metadata.prefix                  = prefix;
    metadata.childrenExcluded        = childrenExcluded;
    metadata.fieldName               = fieldName;
    metadata.showIfProperty          = showIfProperty;
    metadata.currencyCodeField       = currencyCodeField;

    for (Map.Entry<String, Object> entry : additionalMetadata.entrySet()) {
      metadata.additionalMetadata.put(entry.getKey(), entry.getValue());
    }

    return metadata;
  } // end method populate
} // end class FieldMetadata
