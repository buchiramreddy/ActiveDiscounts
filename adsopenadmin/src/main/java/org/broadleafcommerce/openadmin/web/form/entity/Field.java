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

import org.apache.commons.lang3.ArrayUtils;

import org.broadleafcommerce.common.presentation.client.SupportedFieldType;


/**
 * DOCUMENT ME!
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public class Field {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  public static final String ALTERNATE_ORDERING = "AlternateOrdering";

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected String  columnWidth;

  /** DOCUMENT ME! */
  protected String  displayValue;

  /** DOCUMENT ME! */
  protected String  fieldType;

  /** DOCUMENT ME! */
  protected String  foreignKeyClass;

  /** DOCUMENT ME! */
  protected String  foreignKeyDisplayValueProperty;

  /** DOCUMENT ME! */
  protected String  friendlyName;

  /** DOCUMENT ME! */
  protected String  idOverride;

  /** DOCUMENT ME! */
  protected Boolean isAlternateOrdering;

  /** DOCUMENT ME! */
  protected Boolean isDerived;

  /** DOCUMENT ME! */
  protected Boolean isFilterSortDisabled;

  /** DOCUMENT ME! */
  protected Boolean isMainEntityLink;

  /** DOCUMENT ME! */
  protected Boolean isReadOnly;

  /** DOCUMENT ME! */
  protected Boolean isTranslatable;

  /** DOCUMENT ME! */
  protected Boolean isVisible;

  /** DOCUMENT ME! */
  protected String  name;

  /** DOCUMENT ME! */
  protected String  onChangeTrigger;

  /** DOCUMENT ME! */
  protected Integer order;

  /** DOCUMENT ME! */
  protected String  owningEntityClass;

  /** DOCUMENT ME! */
  protected Boolean required = false;

  /** DOCUMENT ME! */
  protected String  value;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getAlternateOrdering() {
    return (isAlternateOrdering == null) ? false : isAlternateOrdering;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Used for linking in toOneLookup fields as well as linking to the entity via a 'name' field.
   *
   * @return  used for linking in toOneLookup fields as well as linking to the entity via a 'name' field.
   */
  public boolean getCanLinkToExternalEntity() {
    return SupportedFieldType.ADDITIONAL_FOREIGN_KEY.toString().equals(fieldType);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getColumnWidth() {
    return columnWidth;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getDisplayValue() {
    return (displayValue == null) ? value : displayValue;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Used to build a link for this particular field value to be displayed in a modal. This is used to build the link for
   * a 'to-one-lookup' field.
   *
   * @return  used to build a link for this particular field value to be displayed in a modal.
   */
  public String getEntityViewPath() {
    return getForeignKeyClass() + "/" + getValue();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getFieldType() {
    return fieldType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getFilterSortDisabled() {
    return (isFilterSortDisabled == null) ? false : isFilterSortDisabled;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getForeignKeyClass() {
    return foreignKeyClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getForeignKeyDisplayValueProperty() {
    return foreignKeyDisplayValueProperty;
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
  public String getIdOverride() {
    return idOverride;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getIsDerived() {
    return (isDerived == null) ? false : isDerived;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* ************************ */
  /* CUSTOM GETTERS / SETTERS */
  /* ************************ */

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getIsVisible() {
    String[] invisibleTypes = new String[] {
      SupportedFieldType.ID.toString(),
      SupportedFieldType.HIDDEN.toString(),
      SupportedFieldType.FOREIGN_KEY.toString()
    };

    return (isVisible == null) ? (!ArrayUtils.contains(invisibleTypes, fieldType)) : isVisible;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getMainEntityLink() {
    return (isMainEntityLink == null) ? false : isMainEntityLink;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* ************************** */
  /* STANDARD GETTERS / SETTERS */
  /* ************************** */

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getName() {
    return name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getOnChangeTrigger() {
    return onChangeTrigger;
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
  public String getOwningEntityClass() {
    return owningEntityClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getReadOnly() {
    return (isReadOnly == null) ? false : isReadOnly;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getRequired() {
    return (required == null) ? false : required;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getTranslatable() {
    return (isTranslatable == null) ? false : isTranslatable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getValue() {
    return value;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  alternateOrdering  DOCUMENT ME!
   */
  public void setAlternateOrdering(Boolean alternateOrdering) {
    this.isAlternateOrdering = alternateOrdering;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  columnWidth  DOCUMENT ME!
   */
  public void setColumnWidth(String columnWidth) {
    if ("*".equals(columnWidth)) {
      columnWidth = null;
    }

    this.columnWidth = columnWidth;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  isDerived  DOCUMENT ME!
   */
  public void setDerived(Boolean isDerived) {
    this.isDerived = isDerived;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  displayValue  DOCUMENT ME!
   */
  public void setDisplayValue(String displayValue) {
    this.displayValue = displayValue;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  fieldType  DOCUMENT ME!
   */
  public void setFieldType(String fieldType) {
    this.fieldType = fieldType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  isFilterSortDisabled  DOCUMENT ME!
   */
  public void setFilterSortDisabled(Boolean isFilterSortDisabled) {
    this.isFilterSortDisabled = isFilterSortDisabled;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  foreignKeyClass  DOCUMENT ME!
   */
  public void setForeignKeyClass(String foreignKeyClass) {
    this.foreignKeyClass = foreignKeyClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  foreignKeyDisplayValueProperty  DOCUMENT ME!
   */
  public void setForeignKeyDisplayValueProperty(String foreignKeyDisplayValueProperty) {
    this.foreignKeyDisplayValueProperty = foreignKeyDisplayValueProperty;
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
   * @param  idOverride  DOCUMENT ME!
   */
  public void setIdOverride(String idOverride) {
    this.idOverride = idOverride;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  isVisible  DOCUMENT ME!
   */
  public void setIsVisible(Boolean isVisible) {
    this.isVisible = isVisible;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  isMainEntityLink  DOCUMENT ME!
   */
  public void setMainEntityLink(Boolean isMainEntityLink) {
    this.isMainEntityLink = isMainEntityLink;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  name  DOCUMENT ME!
   */
  public void setName(String name) {
    this.name = name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  onChangeTrigger  DOCUMENT ME!
   */
  public void setOnChangeTrigger(String onChangeTrigger) {
    this.onChangeTrigger = onChangeTrigger;
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
   * @param  owningEntityClass  DOCUMENT ME!
   */
  public void setOwningEntityClass(String owningEntityClass) {
    this.owningEntityClass = owningEntityClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  readOnly  DOCUMENT ME!
   */
  public void setReadOnly(Boolean readOnly) {
    this.isReadOnly = readOnly;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  required  DOCUMENT ME!
   */
  public void setRequired(Boolean required) {
    this.required = required;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  translatable  DOCUMENT ME!
   */
  public void setTranslatable(Boolean translatable) {
    this.isTranslatable = translatable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  value  DOCUMENT ME!
   */
  public void setValue(String value) {
    this.value = value;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   alternateOrdering  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Field withAlternateOrdering(Boolean alternateOrdering) {
    setAlternateOrdering(alternateOrdering);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   columnWidth  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Field withColumnWidth(String columnWidth) {
    setColumnWidth(columnWidth);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   isDerived  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Field withDerived(Boolean isDerived) {
    setDerived(isDerived);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   displayValue  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Field withDisplayValue(String displayValue) {
    setDisplayValue(displayValue);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   fieldType  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Field withFieldType(String fieldType) {
    setFieldType(fieldType);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   isFilterSortDisabled  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Field withFilterSortDisabled(Boolean isFilterSortDisabled) {
    setFilterSortDisabled(isFilterSortDisabled);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   foreignKeyClass  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Field withForeignKeyClass(String foreignKeyClass) {
    setForeignKeyClass(foreignKeyClass);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   foreignKeyDisplayValueProperty  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Field withForeignKeyDisplayValueProperty(String foreignKeyDisplayValueProperty) {
    setForeignKeyDisplayValueProperty(foreignKeyDisplayValueProperty);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   friendlyName  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Field withFriendlyName(String friendlyName) {
    setFriendlyName(friendlyName);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   idOverride  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Field withIdOverride(String idOverride) {
    setIdOverride(idOverride);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   isMainEntityLink  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Field withMainEntityLink(Boolean isMainEntityLink) {
    setMainEntityLink(isMainEntityLink);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* ************ */
  /* WITH METHODS */
  /* ************ */

  /**
   * DOCUMENT ME!
   *
   * @param   name  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Field withName(String name) {
    setName(name);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   order  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Field withOrder(Integer order) {
    setOrder(order);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   owningEntityClass  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Field withOwningEntityClass(String owningEntityClass) {
    setOwningEntityClass(owningEntityClass);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   isReadOnly  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Field withReadOnly(Boolean isReadOnly) {
    setReadOnly(isReadOnly);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   required  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Field withRequired(Boolean required) {
    setRequired(required);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   isTranslatable  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Field withTranslatable(Boolean isTranslatable) {
    setTranslatable(isTranslatable);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   value  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Field withValue(String value) {
    setValue(value);

    return this;
  }

} // end class Field
