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

package org.broadleafcommerce.openadmin.dto.override;

import java.io.Serializable;

import java.util.Map;

import org.broadleafcommerce.common.presentation.client.AddMethodType;
import org.broadleafcommerce.common.presentation.client.LookupType;
import org.broadleafcommerce.common.presentation.client.OperationType;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.presentation.client.UnspecifiedBooleanType;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;

import org.broadleafcommerce.openadmin.dto.MergedPropertyType;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class FieldMetadataOverride {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected Integer gridOrder;

  /** DOCUMENT ME! */
  protected Boolean isDerived;

  // basic collection fields
  private AddMethodType addMethodType;
  private OperationType addType;
  private String        broadleafEnumeration;
  private String        columnWidth;
  private String        currencyCodeField;

  // collection fields
  private String[]   customCriteria;
  private Boolean    deleteEntityUponRemove;
  private String     enumerationClass;
  private String[][] enumerationValues;

  // fields everyone depends on
  private Boolean            excluded;
  private SupportedFieldType explicitFieldType;
  private OperationType      fetchType;

  // basic fields
  private SupportedFieldType     fieldType;
  private Boolean                forceFreeFormKeys;
  private String                 foreignKeyClass;
  private Boolean                foreignKeyCollection;
  private String                 foreignKeyDisplayValueProperty;
  private String                 foreignKeyProperty;
  private String                 friendlyName;
  private String[]               gridVisibleFields;
  private String                 group;
  private Boolean                groupCollapsed;
  private Integer                groupOrder;
  private String                 helpText;
  private String                 hint;
  private Boolean                ignoreAdornedProperties;
  private OperationType          inspectType;
  private UnspecifiedBooleanType isSimpleValue;
  private String                 joinEntityClass;

  // Map fields
  private String             keyClass;
  private String             keyPropertyFriendlyName;
  private String[][]         keys;
  private Boolean            largeEntry;
  private Integer            length;
  private String             lookupDisplayProperty;
  private LookupType         lookupType;
  private String[]           maintainedAdornedTargetFields;
  private String             manyToField;
  private String             mapFieldValueClass;
  private String             mapKeyOptionEntityClass;
  private String             mapKeyOptionEntityDisplayField;
  private String             mapKeyOptionEntityValueField;
  private String             mediaField;
  private MergedPropertyType mergedPropertyType;

  // @AdminPresentation derived fields
  private String           name;
  private Boolean          optionCanEditValues;
  private String           optionDisplayFieldName;
  private Serializable[][] optionFilterValues;
  private String           optionListEntity;
  private String           optionValueFieldName;
  private Integer          order;
  private String           parentObjectIdProperty;

  // Adorned target fields
  private String        parentObjectProperty;
  private Integer       precision;
  private Boolean       prominent;
  private Boolean       readOnly;
  private OperationType removeType;
  private Boolean       required;
  private Boolean       requiredOverride;
  private String        ruleIdentifier;
  private Integer       scale;

  // @AdminPresentationMapField derived fields
  private Boolean            searchable;
  private SupportedFieldType secondaryType          = SupportedFieldType.INTEGER;
  private String             securityLevel;
  private String             showIfProperty;
  private Boolean            sortAscending;
  private String             sortProperty;
  private String             tab;
  private Integer            tabOrder;
  private String             targetObjectIdProperty;
  private String             targetObjectProperty;
  private String             tooltip;

  // Not a user definable field
  private Boolean                          toOneLookupCreatedViaAnnotation;
  private Boolean                          translatable;
  private Boolean                          unique;
  private OperationType                    updateType;
  private Boolean                          useServerSideInspectionCache;
  private Map<String, Map<String, String>> validationConfigurations;
  private String                           valueClass;
  private String                           valuePropertyFriendlyName;
  private VisibilityEnum                   visibility;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public AddMethodType getAddMethodType() {
    return addMethodType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public OperationType getAddType() {
    return addType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getBroadleafEnumeration() {
    return broadleafEnumeration;
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
  public String getCurrencyCodeField() {
    return currencyCodeField;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String[] getCustomCriteria() {
    return customCriteria;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getEnumerationClass() {
    return enumerationClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String[][] getEnumerationValues() {
    return enumerationValues;
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
  public SupportedFieldType getExplicitFieldType() {
    return explicitFieldType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public OperationType getFetchType() {
    return fetchType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public SupportedFieldType getFieldType() {
    return fieldType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getForceFreeFormKeys() {
    return forceFreeFormKeys;
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
  public Boolean getForeignKeyCollection() {
    return foreignKeyCollection;
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
  public String getForeignKeyProperty() {
    return foreignKeyProperty;
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
  public Integer getGridOrder() {
    return gridOrder;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String[] getGridVisibleFields() {
    return gridVisibleFields;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getGroup() {
    return group;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getGroupCollapsed() {
    return groupCollapsed;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Integer getGroupOrder() {
    return groupOrder;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getHelpText() {
    return helpText;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getHint() {
    return hint;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public OperationType getInspectType() {
    return inspectType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getIsDerived() {
    return isDerived;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getJoinEntityClass() {
    return joinEntityClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getKeyClass() {
    return keyClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getKeyPropertyFriendlyName() {
    return keyPropertyFriendlyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String[][] getKeys() {
    return keys;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Integer getLength() {
    return length;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getLookupDisplayProperty() {
    return lookupDisplayProperty;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public LookupType getLookupType() {
    return lookupType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String[] getMaintainedAdornedTargetFields() {
    return maintainedAdornedTargetFields;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getManyToField() {
    return manyToField;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getMapFieldValueClass() {
    return mapFieldValueClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getMapKeyOptionEntityClass() {
    return mapKeyOptionEntityClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getMapKeyOptionEntityDisplayField() {
    return mapKeyOptionEntityDisplayField;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getMapKeyOptionEntityValueField() {
    return mapKeyOptionEntityValueField;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getMediaField() {
    return mediaField;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public MergedPropertyType getMergedPropertyType() {
    return mergedPropertyType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

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
  public Boolean getOptionCanEditValues() {
    return optionCanEditValues;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getOptionDisplayFieldName() {
    return optionDisplayFieldName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Serializable[][] getOptionFilterValues() {
    return optionFilterValues;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getOptionListEntity() {
    return optionListEntity;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getOptionValueFieldName() {
    return optionValueFieldName;
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
  public String getParentObjectIdProperty() {
    return parentObjectIdProperty;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getParentObjectProperty() {
    return parentObjectProperty;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Integer getPrecision() {
    return precision;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getReadOnly() {
    return readOnly;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public OperationType getRemoveType() {
    return removeType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getRequired() {
    return required;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getRequiredOverride() {
    return requiredOverride;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getRuleIdentifier() {
    return ruleIdentifier;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Integer getScale() {
    return scale;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getSearchable() {
    return searchable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public SupportedFieldType getSecondaryType() {
    return secondaryType;
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
  public UnspecifiedBooleanType getSimpleValue() {
    return isSimpleValue;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getSortProperty() {
    return sortProperty;
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
  public String getTargetObjectIdProperty() {
    return targetObjectIdProperty;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getTargetObjectProperty() {
    return targetObjectProperty;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getTooltip() {
    return tooltip;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getToOneLookupCreatedViaAnnotation() {
    return toOneLookupCreatedViaAnnotation;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getTranslatable() {
    return translatable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getUnique() {
    return unique;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public OperationType getUpdateType() {
    return updateType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getUseServerSideInspectionCache() {
    return useServerSideInspectionCache;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<String, Map<String, String>> getValidationConfigurations() {
    return validationConfigurations;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getValueClass() {
    return valueClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getValuePropertyFriendlyName() {
    return valuePropertyFriendlyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public VisibilityEnum getVisibility() {
    return visibility;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean isDeleteEntityUponRemove() {
    return deleteEntityUponRemove;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean isIgnoreAdornedProperties() {
    return ignoreAdornedProperties;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean isLargeEntry() {
    return largeEntry;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean isProminent() {
    return prominent;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean isSortAscending() {
    return sortAscending;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  addMethodType  DOCUMENT ME!
   */
  public void setAddMethodType(AddMethodType addMethodType) {
    this.addMethodType = addMethodType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  addType  DOCUMENT ME!
   */
  public void setAddType(OperationType addType) {
    this.addType = addType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  broadleafEnumeration  DOCUMENT ME!
   */
  public void setBroadleafEnumeration(String broadleafEnumeration) {
    this.broadleafEnumeration = broadleafEnumeration;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  columnWidth  DOCUMENT ME!
   */
  public void setColumnWidth(String columnWidth) {
    this.columnWidth = columnWidth;
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
   * @param  customCriteria  DOCUMENT ME!
   */
  public void setCustomCriteria(String[] customCriteria) {
    this.customCriteria = customCriteria;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  deleteEntityUponRemove  DOCUMENT ME!
   */
  public void setDeleteEntityUponRemove(Boolean deleteEntityUponRemove) {
    this.deleteEntityUponRemove = deleteEntityUponRemove;
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
   * @param  enumerationClass  DOCUMENT ME!
   */
  public void setEnumerationClass(String enumerationClass) {
    this.enumerationClass = enumerationClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  enumerationValues  DOCUMENT ME!
   */
  public void setEnumerationValues(String[][] enumerationValues) {
    this.enumerationValues = enumerationValues;
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
   * @param  fieldType  DOCUMENT ME!
   */
  public void setExplicitFieldType(SupportedFieldType fieldType) {
    this.explicitFieldType = fieldType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  fetchType  DOCUMENT ME!
   */
  public void setFetchType(OperationType fetchType) {
    this.fetchType = fetchType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  fieldType  DOCUMENT ME!
   */
  public void setFieldType(SupportedFieldType fieldType) {
    this.fieldType = fieldType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  forceFreeFormKeys  DOCUMENT ME!
   */
  public void setForceFreeFormKeys(Boolean forceFreeFormKeys) {
    this.forceFreeFormKeys = forceFreeFormKeys;
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
   * @param  foreignKeyCollection  DOCUMENT ME!
   */
  public void setForeignKeyCollection(Boolean foreignKeyCollection) {
    this.foreignKeyCollection = foreignKeyCollection;
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
   * @param  foreignKeyProperty  DOCUMENT ME!
   */
  public void setForeignKeyProperty(String foreignKeyProperty) {
    this.foreignKeyProperty = foreignKeyProperty;
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
   * @param  gridOrder  DOCUMENT ME!
   */
  public void setGridOrder(Integer gridOrder) {
    this.gridOrder = gridOrder;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  gridVisibleFields  DOCUMENT ME!
   */
  public void setGridVisibleFields(String[] gridVisibleFields) {
    this.gridVisibleFields = gridVisibleFields;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  group  DOCUMENT ME!
   */
  public void setGroup(String group) {
    this.group = group;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  groupCollapsed  DOCUMENT ME!
   */
  public void setGroupCollapsed(Boolean groupCollapsed) {
    this.groupCollapsed = groupCollapsed;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  groupOrder  DOCUMENT ME!
   */
  public void setGroupOrder(Integer groupOrder) {
    this.groupOrder = groupOrder;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  helpText  DOCUMENT ME!
   */
  public void setHelpText(String helpText) {
    this.helpText = helpText;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  hint  DOCUMENT ME!
   */
  public void setHint(String hint) {
    this.hint = hint;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  ignoreAdornedProperties  DOCUMENT ME!
   */
  public void setIgnoreAdornedProperties(Boolean ignoreAdornedProperties) {
    this.ignoreAdornedProperties = ignoreAdornedProperties;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  inspectType  DOCUMENT ME!
   */
  public void setInspectType(OperationType inspectType) {
    this.inspectType = inspectType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  joinEntityClass  DOCUMENT ME!
   */
  public void setJoinEntityClass(String joinEntityClass) {
    this.joinEntityClass = joinEntityClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  keyClass  DOCUMENT ME!
   */
  public void setKeyClass(String keyClass) {
    this.keyClass = keyClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  keyPropertyFriendlyName  DOCUMENT ME!
   */
  public void setKeyPropertyFriendlyName(String keyPropertyFriendlyName) {
    this.keyPropertyFriendlyName = keyPropertyFriendlyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  keys  DOCUMENT ME!
   */
  public void setKeys(String[][] keys) {
    this.keys = keys;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  largeEntry  DOCUMENT ME!
   */
  public void setLargeEntry(Boolean largeEntry) {
    this.largeEntry = largeEntry;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  length  DOCUMENT ME!
   */
  public void setLength(Integer length) {
    this.length = length;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  lookupDisplayProperty  DOCUMENT ME!
   */
  public void setLookupDisplayProperty(String lookupDisplayProperty) {
    this.lookupDisplayProperty = lookupDisplayProperty;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  lookupType  DOCUMENT ME!
   */
  public void setLookupType(LookupType lookupType) {
    this.lookupType = lookupType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  maintainedAdornedTargetFields  DOCUMENT ME!
   */
  public void setMaintainedAdornedTargetFields(String[] maintainedAdornedTargetFields) {
    this.maintainedAdornedTargetFields = maintainedAdornedTargetFields;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  manyToField  DOCUMENT ME!
   */
  public void setManyToField(String manyToField) {
    this.manyToField = manyToField;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  mapFieldValueClass  DOCUMENT ME!
   */
  public void setMapFieldValueClass(String mapFieldValueClass) {
    this.mapFieldValueClass = mapFieldValueClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  mapKeyOptionEntityClass  DOCUMENT ME!
   */
  public void setMapKeyOptionEntityClass(String mapKeyOptionEntityClass) {
    this.mapKeyOptionEntityClass = mapKeyOptionEntityClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  mapKeyOptionEntityDisplayField  DOCUMENT ME!
   */
  public void setMapKeyOptionEntityDisplayField(String mapKeyOptionEntityDisplayField) {
    this.mapKeyOptionEntityDisplayField = mapKeyOptionEntityDisplayField;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  mapKeyOptionEntityValueField  DOCUMENT ME!
   */
  public void setMapKeyOptionEntityValueField(String mapKeyOptionEntityValueField) {
    this.mapKeyOptionEntityValueField = mapKeyOptionEntityValueField;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  mediaField  DOCUMENT ME!
   */
  public void setMediaField(String mediaField) {
    this.mediaField = mediaField;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  mergedPropertyType  DOCUMENT ME!
   */
  public void setMergedPropertyType(MergedPropertyType mergedPropertyType) {
    this.mergedPropertyType = mergedPropertyType;
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
   * @param  optionCanEditValues  DOCUMENT ME!
   */
  public void setOptionCanEditValues(Boolean optionCanEditValues) {
    this.optionCanEditValues = optionCanEditValues;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  optionDisplayFieldName  DOCUMENT ME!
   */
  public void setOptionDisplayFieldName(String optionDisplayFieldName) {
    this.optionDisplayFieldName = optionDisplayFieldName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  optionFilterValues  DOCUMENT ME!
   */
  public void setOptionFilterValues(Serializable[][] optionFilterValues) {
    this.optionFilterValues = optionFilterValues;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  optionListEntity  DOCUMENT ME!
   */
  public void setOptionListEntity(String optionListEntity) {
    this.optionListEntity = optionListEntity;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  optionValueFieldName  DOCUMENT ME!
   */
  public void setOptionValueFieldName(String optionValueFieldName) {
    this.optionValueFieldName = optionValueFieldName;
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
   * @param  parentObjectIdProperty  DOCUMENT ME!
   */
  public void setParentObjectIdProperty(String parentObjectIdProperty) {
    this.parentObjectIdProperty = parentObjectIdProperty;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  parentObjectProperty  DOCUMENT ME!
   */
  public void setParentObjectProperty(String parentObjectProperty) {
    this.parentObjectProperty = parentObjectProperty;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  precision  DOCUMENT ME!
   */
  public void setPrecision(Integer precision) {
    this.precision = precision;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  prominent  DOCUMENT ME!
   */
  public void setProminent(Boolean prominent) {
    this.prominent = prominent;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  readOnly  DOCUMENT ME!
   */
  public void setReadOnly(Boolean readOnly) {
    this.readOnly = readOnly;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  removeType  DOCUMENT ME!
   */
  public void setRemoveType(OperationType removeType) {
    this.removeType = removeType;
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
   * @param  requiredOverride  DOCUMENT ME!
   */
  public void setRequiredOverride(Boolean requiredOverride) {
    this.requiredOverride = requiredOverride;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  ruleIdentifier  DOCUMENT ME!
   */
  public void setRuleIdentifier(String ruleIdentifier) {
    this.ruleIdentifier = ruleIdentifier;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  scale  DOCUMENT ME!
   */
  public void setScale(Integer scale) {
    this.scale = scale;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  searchable  DOCUMENT ME!
   */
  public void setSearchable(Boolean searchable) {
    this.searchable = searchable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  secondaryType  DOCUMENT ME!
   */
  public void setSecondaryType(SupportedFieldType secondaryType) {
    this.secondaryType = secondaryType;
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
   * @param  simpleValue  DOCUMENT ME!
   */
  public void setSimpleValue(UnspecifiedBooleanType simpleValue) {
    isSimpleValue = simpleValue;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  sortAscending  DOCUMENT ME!
   */
  public void setSortAscending(Boolean sortAscending) {
    this.sortAscending = sortAscending;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  sortProperty  DOCUMENT ME!
   */
  public void setSortProperty(String sortProperty) {
    this.sortProperty = sortProperty;
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
   * @param  targetObjectIdProperty  DOCUMENT ME!
   */
  public void setTargetObjectIdProperty(String targetObjectIdProperty) {
    this.targetObjectIdProperty = targetObjectIdProperty;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  targetObjectProperty  DOCUMENT ME!
   */
  public void setTargetObjectProperty(String targetObjectProperty) {
    this.targetObjectProperty = targetObjectProperty;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  tooltip  DOCUMENT ME!
   */
  public void setTooltip(String tooltip) {
    this.tooltip = tooltip;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  toOneLookupCreatedViaAnnotation  DOCUMENT ME!
   */
  public void setToOneLookupCreatedViaAnnotation(Boolean toOneLookupCreatedViaAnnotation) {
    this.toOneLookupCreatedViaAnnotation = toOneLookupCreatedViaAnnotation;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  translatable  DOCUMENT ME!
   */
  public void setTranslatable(Boolean translatable) {
    this.translatable = translatable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  unique  DOCUMENT ME!
   */
  public void setUnique(Boolean unique) {
    this.unique = unique;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  updateType  DOCUMENT ME!
   */
  public void setUpdateType(OperationType updateType) {
    this.updateType = updateType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  useServerSideInspectionCache  DOCUMENT ME!
   */
  public void setUseServerSideInspectionCache(Boolean useServerSideInspectionCache) {
    this.useServerSideInspectionCache = useServerSideInspectionCache;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  validationConfigurations  DOCUMENT ME!
   */
  public void setValidationConfigurations(Map<String, Map<String, String>> validationConfigurations) {
    this.validationConfigurations = validationConfigurations;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  valueClass  DOCUMENT ME!
   */
  public void setValueClass(String valueClass) {
    this.valueClass = valueClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  valuePropertyFriendlyName  DOCUMENT ME!
   */
  public void setValuePropertyFriendlyName(String valuePropertyFriendlyName) {
    this.valuePropertyFriendlyName = valuePropertyFriendlyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  visibility  DOCUMENT ME!
   */
  public void setVisibility(VisibilityEnum visibility) {
    this.visibility = visibility;
  }
} // end class FieldMetadataOverride
