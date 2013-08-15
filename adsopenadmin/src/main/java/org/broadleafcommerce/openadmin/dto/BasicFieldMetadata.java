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

import java.util.HashMap;
import java.util.Map;

import org.broadleafcommerce.common.presentation.client.LookupType;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;

import org.broadleafcommerce.openadmin.dto.visitor.MetadataVisitor;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class BasicFieldMetadata extends FieldMetadata {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected String             broadleafEnumeration;

  /** DOCUMENT ME! */
  protected String             columnWidth;

  /** DOCUMENT ME! */
  protected String[]           customCriteria;

  /** DOCUMENT ME! */
  protected String             enumerationClass;

  /** DOCUMENT ME! */
  protected String[][]         enumerationValues;

  /** DOCUMENT ME! */
  protected SupportedFieldType explicitFieldType;

  /** DOCUMENT ME! */
  protected SupportedFieldType fieldType;

  /** DOCUMENT ME! */
  protected String             foreignKeyClass;

  /** DOCUMENT ME! */
  protected Boolean            foreignKeyCollection;

  /** DOCUMENT ME! */
  protected String             foreignKeyDisplayValueProperty;

  /** DOCUMENT ME! */
  protected String             foreignKeyProperty;

  /** DOCUMENT ME! */
  protected Integer            gridOrder;

  /** DOCUMENT ME! */
  protected String             group;

  /** DOCUMENT ME! */
  protected Boolean            groupCollapsed;

  /** DOCUMENT ME! */
  protected Integer            groupOrder;

  /** DOCUMENT ME! */
  protected String             helpText;

  /** DOCUMENT ME! */
  protected String             hint;

  /** DOCUMENT ME! */
  protected Boolean            isDerived;

  /** DOCUMENT ME! */
  protected Boolean            largeEntry;

  /** DOCUMENT ME! */
  protected Integer            length;

  /** DOCUMENT ME! */
  protected String             lookupDisplayProperty;

  /** DOCUMENT ME! */
  protected LookupType         lookupType;

  /** DOCUMENT ME! */
  protected String             manyToField;

  // for MapFields
  /** DOCUMENT ME! */
  protected String             mapFieldValueClass;

  /** DOCUMENT ME! */
  protected MergedPropertyType mergedPropertyType;

  /** DOCUMENT ME! */
  protected Boolean            mutable;

  // @AdminPresentation derived fields
  /** DOCUMENT ME! */
  protected String                           name;

  /** DOCUMENT ME! */
  protected Boolean                          optionCanEditValues;

  /** DOCUMENT ME! */
  protected String                           optionDisplayFieldName;

  /** DOCUMENT ME! */
  protected String[][]                       optionFilterParams;

  /** DOCUMENT ME! */
  protected String                           optionListEntity;

  /** DOCUMENT ME! */
  protected String                           optionValueFieldName;

  /** DOCUMENT ME! */
  protected Integer                          precision;

  /** DOCUMENT ME! */
  protected Boolean                          prominent;

  /** DOCUMENT ME! */
  protected Boolean                          readOnly;

  /** DOCUMENT ME! */
  protected Boolean                          required;

  /** DOCUMENT ME! */
  protected Boolean                          requiredOverride;

  /** DOCUMENT ME! */
  protected String                           ruleIdentifier;

  /** DOCUMENT ME! */
  protected Integer                          scale;

  /** DOCUMENT ME! */
  protected Boolean                          searchable;

  /** DOCUMENT ME! */
  protected SupportedFieldType               secondaryType                   = SupportedFieldType.INTEGER;

  /** DOCUMENT ME! */
  protected String                           tooltip;

  /** DOCUMENT ME! */
  protected Boolean                          toOneLookupCreatedViaAnnotation;

  /** DOCUMENT ME! */
  protected Boolean                          translatable;

  /** DOCUMENT ME! */
  protected Boolean                          unique;

  /** DOCUMENT ME! */
  protected Boolean                          useServerSideInspectionCache;

  /** DOCUMENT ME! */
  protected Map<String, Map<String, String>> validationConfigurations = new HashMap<String, Map<String, String>>(5);

  /** DOCUMENT ME! */
  protected VisibilityEnum                   visibility;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.dto.FieldMetadata#accept(org.broadleafcommerce.openadmin.dto.visitor.MetadataVisitor)
   */
  @Override public void accept(MetadataVisitor visitor) {
    visitor.visit(this);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.dto.FieldMetadata#cloneFieldMetadata()
   */
  @Override public FieldMetadata cloneFieldMetadata() {
    BasicFieldMetadata metadata = new BasicFieldMetadata();
    metadata.fieldType                      = fieldType;
    metadata.secondaryType                  = secondaryType;
    metadata.length                         = length;
    metadata.required                       = required;
    metadata.unique                         = unique;
    metadata.scale                          = scale;
    metadata.precision                      = precision;
    metadata.mutable                        = mutable;
    metadata.foreignKeyProperty             = foreignKeyProperty;
    metadata.foreignKeyClass                = foreignKeyClass;
    metadata.foreignKeyDisplayValueProperty = foreignKeyDisplayValueProperty;
    metadata.foreignKeyCollection           = foreignKeyCollection;
    metadata.mergedPropertyType             = mergedPropertyType;
    metadata.enumerationClass               = enumerationClass;

    if (enumerationValues != null) {
      metadata.enumerationValues = new String[enumerationValues.length][];

      for (int j = 0; j < enumerationValues.length; j++) {
        metadata.enumerationValues[j] = new String[enumerationValues[j].length];
        System.arraycopy(enumerationValues[j], 0, metadata.enumerationValues[j], 0, enumerationValues[j].length);
      }
    }

    metadata.name           = name;
    metadata.visibility     = visibility;
    metadata.group          = group;
    metadata.groupOrder     = groupOrder;
    metadata.groupCollapsed = groupCollapsed;
    metadata.setTab(getTab());
    metadata.setTabOrder(getTabOrder());
    metadata.explicitFieldType    = explicitFieldType;
    metadata.largeEntry           = largeEntry;
    metadata.prominent            = prominent;
    metadata.gridOrder            = gridOrder;
    metadata.columnWidth          = columnWidth;
    metadata.broadleafEnumeration = broadleafEnumeration;
    metadata.readOnly             = readOnly;
    metadata.requiredOverride     = requiredOverride;
    metadata.tooltip              = tooltip;
    metadata.helpText             = helpText;
    metadata.hint                 = hint;

    for (Map.Entry<String, Map<String, String>> entry : validationConfigurations.entrySet()) {
      Map<String, String> clone = new HashMap<String, String>(entry.getValue().size());

      for (Map.Entry<String, String> entry2 : entry.getValue().entrySet()) {
        clone.put(entry2.getKey(), entry2.getValue());
      }

      metadata.validationConfigurations.put(entry.getKey(), clone);
    }

    metadata.lookupDisplayProperty  = lookupDisplayProperty;
    metadata.optionListEntity       = optionListEntity;
    metadata.optionCanEditValues    = optionCanEditValues;
    metadata.optionDisplayFieldName = optionDisplayFieldName;
    metadata.optionValueFieldName   = optionValueFieldName;

    if (optionFilterParams != null) {
      metadata.optionFilterParams = new String[optionFilterParams.length][];

      for (int j = 0; j < optionFilterParams.length; j++) {
        metadata.optionFilterParams[j] = new String[optionFilterParams[j].length];
        System.arraycopy(optionFilterParams[j], 0, metadata.optionFilterParams[j], 0, optionFilterParams[j].length);
      }
    }

    metadata.customCriteria                  = customCriteria;
    metadata.useServerSideInspectionCache    = useServerSideInspectionCache;
    metadata.toOneLookupCreatedViaAnnotation = toOneLookupCreatedViaAnnotation;
    metadata.ruleIdentifier                  = ruleIdentifier;
    metadata.mapFieldValueClass              = mapFieldValueClass;
    metadata.searchable                      = searchable;
    metadata.manyToField                     = manyToField;
    metadata.lookupType                      = lookupType;
    metadata.translatable                    = translatable;
    metadata.isDerived                       = isDerived;

    metadata = (BasicFieldMetadata) populate(metadata);

    return metadata;
  } // end method cloneFieldMetadata

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.dto.FieldMetadata#equals(java.lang.Object)
   */
  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof BasicFieldMetadata)) {
      return false;
    }

    if (!super.equals(o)) {
      return false;
    }

    BasicFieldMetadata metadata = (BasicFieldMetadata) o;

    if ((broadleafEnumeration != null) ? (!broadleafEnumeration.equals(metadata.broadleafEnumeration))
                                       : (metadata.broadleafEnumeration != null)) {
      return false;
    }

    if ((columnWidth != null) ? (!columnWidth.equals(metadata.columnWidth)) : (metadata.columnWidth != null)) {
      return false;
    }

    if ((enumerationClass != null) ? (!enumerationClass.equals(metadata.enumerationClass))
                                   : (metadata.enumerationClass != null)) {
      return false;
    }

    if (explicitFieldType != metadata.explicitFieldType) {
      return false;
    }

    if (fieldType != metadata.fieldType) {
      return false;
    }

    if ((foreignKeyClass != null) ? (!foreignKeyClass.equals(metadata.foreignKeyClass))
                                  : (metadata.foreignKeyClass != null)) {
      return false;
    }

    if ((foreignKeyCollection != null) ? (!foreignKeyCollection.equals(metadata.foreignKeyCollection))
                                       : (metadata.foreignKeyCollection != null)) {
      return false;
    }

    if ((foreignKeyDisplayValueProperty != null)
          ? (!foreignKeyDisplayValueProperty.equals(metadata.foreignKeyDisplayValueProperty))
          : (metadata.foreignKeyDisplayValueProperty != null)) {
      return false;
    }

    if ((foreignKeyProperty != null) ? (!foreignKeyProperty.equals(metadata.foreignKeyProperty))
                                     : (metadata.foreignKeyProperty != null)) {
      return false;
    }

    if ((group != null) ? (!group.equals(metadata.group)) : (metadata.group != null)) {
      return false;
    }

    if ((groupCollapsed != null) ? (!groupCollapsed.equals(metadata.groupCollapsed))
                                 : (metadata.groupCollapsed != null)) {
      return false;
    }

    if ((groupOrder != null) ? (!groupOrder.equals(metadata.groupOrder)) : (metadata.groupOrder != null)) {
      return false;
    }

    if ((helpText != null) ? (!helpText.equals(metadata.helpText)) : (metadata.helpText != null)) {
      return false;
    }

    if ((hint != null) ? (!hint.equals(metadata.hint)) : (metadata.hint != null)) {
      return false;
    }

    if ((largeEntry != null) ? (!largeEntry.equals(metadata.largeEntry)) : (metadata.largeEntry != null)) {
      return false;
    }

    if ((length != null) ? (!length.equals(metadata.length)) : (metadata.length != null)) {
      return false;
    }

    if ((lookupDisplayProperty != null) ? (!lookupDisplayProperty.equals(metadata.lookupDisplayProperty))
                                        : (metadata.lookupDisplayProperty != null)) {
      return false;
    }

    if (mergedPropertyType != metadata.mergedPropertyType) {
      return false;
    }

    if ((mutable != null) ? (!mutable.equals(metadata.mutable)) : (metadata.mutable != null)) {
      return false;
    }

    if ((name != null) ? (!name.equals(metadata.name)) : (metadata.name != null)) {
      return false;
    }

    if ((optionCanEditValues != null) ? (!optionCanEditValues.equals(metadata.optionCanEditValues))
                                      : (metadata.optionCanEditValues != null)) {
      return false;
    }

    if ((optionDisplayFieldName != null) ? (!optionDisplayFieldName.equals(metadata.optionDisplayFieldName))
                                         : (metadata.optionDisplayFieldName != null)) {
      return false;
    }

    if ((optionListEntity != null) ? (!optionListEntity.equals(metadata.optionListEntity))
                                   : (metadata.optionListEntity != null)) {
      return false;
    }

    if ((optionValueFieldName != null) ? (!optionValueFieldName.equals(metadata.optionValueFieldName))
                                       : (metadata.optionValueFieldName != null)) {
      return false;
    }

    if ((precision != null) ? (!precision.equals(metadata.precision)) : (metadata.precision != null)) {
      return false;
    }

    if ((prominent != null) ? (!prominent.equals(metadata.prominent)) : (metadata.prominent != null)) {
      return false;
    }

    if ((gridOrder != null) ? (!gridOrder.equals(metadata.gridOrder)) : (metadata.gridOrder != null)) {
      return false;
    }

    if ((readOnly != null) ? (!readOnly.equals(metadata.readOnly)) : (metadata.readOnly != null)) {
      return false;
    }

    if ((required != null) ? (!required.equals(metadata.required)) : (metadata.required != null)) {
      return false;
    }

    if ((requiredOverride != null) ? (!requiredOverride.equals(metadata.requiredOverride))
                                   : (metadata.requiredOverride != null)) {
      return false;
    }

    if ((scale != null) ? (!scale.equals(metadata.scale)) : (metadata.scale != null)) {
      return false;
    }

    if (secondaryType != metadata.secondaryType) {
      return false;
    }

    if ((tooltip != null) ? (!tooltip.equals(metadata.tooltip)) : (metadata.tooltip != null)) {
      return false;
    }

    if ((unique != null) ? (!unique.equals(metadata.unique)) : (metadata.unique != null)) {
      return false;
    }

    if ((validationConfigurations != null) ? (!validationConfigurations.equals(metadata.validationConfigurations))
                                           : (metadata.validationConfigurations != null)) {
      return false;
    }

    if (visibility != metadata.visibility) {
      return false;
    }

    if ((ruleIdentifier != null) ? (!ruleIdentifier.equals(metadata.ruleIdentifier))
                                 : (metadata.ruleIdentifier != null)) {
      return false;
    }

    if ((mapFieldValueClass != null) ? (!mapFieldValueClass.equals(metadata.mapFieldValueClass))
                                     : (metadata.mapFieldValueClass != null)) {
      return false;
    }

    if ((searchable != null) ? (!searchable.equals(metadata.searchable)) : (metadata.searchable != null)) {
      return false;
    }

    if ((manyToField != null) ? (!manyToField.equals(metadata.manyToField)) : (metadata.manyToField != null)) {
      return false;
    }

    if ((lookupType != null) ? (!lookupType.equals(metadata.lookupType)) : (metadata.lookupType != null)) {
      return false;
    }

    if ((isDerived != null) ? (!isDerived.equals(metadata.isDerived)) : (metadata.isDerived != null)) {
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
  public SupportedFieldType getExplicitFieldType() {
    return explicitFieldType;
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
  public Integer getGridOrder() {
    return gridOrder;
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
  public Boolean getIsDerived() {
    return isDerived;
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
  public MergedPropertyType getMergedPropertyType() {
    return mergedPropertyType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getMutable() {
    return mutable;
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
  public String[][] getOptionFilterParams() {
    return optionFilterParams;
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
  public Boolean getUseServerSideInspectionCache() {
    return useServerSideInspectionCache;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The validation configurations for this property keyed by the fully-qualified name of the
   * {@link org.broadleafcommerce.openadmin.server.service.persistence.validation.PropertyValidator} implementation.
   *
   * @return  the validation configurations for this property keyed by the fully-qualified name of the
   *          {@link org.broadleafcommerce.openadmin.server.service.persistence.validation.PropertyValidator}
   *          implementation
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
  public VisibilityEnum getVisibility() {
    return visibility;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.dto.FieldMetadata#hashCode()
   */
  @Override public int hashCode() {
    int result = super.hashCode();
    result = (31 * result) + ((fieldType != null) ? fieldType.hashCode() : 0);
    result = (31 * result) + ((secondaryType != null) ? secondaryType.hashCode() : 0);
    result = (31 * result) + ((length != null) ? length.hashCode() : 0);
    result = (31 * result) + ((required != null) ? required.hashCode() : 0);
    result = (31 * result) + ((unique != null) ? unique.hashCode() : 0);
    result = (31 * result) + ((scale != null) ? scale.hashCode() : 0);
    result = (31 * result) + ((precision != null) ? precision.hashCode() : 0);
    result = (31 * result) + ((mutable != null) ? mutable.hashCode() : 0);
    result = (31 * result) + ((foreignKeyProperty != null) ? foreignKeyProperty.hashCode() : 0);
    result = (31 * result) + ((foreignKeyClass != null) ? foreignKeyClass.hashCode() : 0);
    result = (31 * result) + ((foreignKeyDisplayValueProperty != null) ? foreignKeyDisplayValueProperty.hashCode() : 0);
    result = (31 * result) + ((foreignKeyCollection != null) ? foreignKeyCollection.hashCode() : 0);
    result = (31 * result) + ((mergedPropertyType != null) ? mergedPropertyType.hashCode() : 0);
    result = (31 * result) + ((enumerationClass != null) ? enumerationClass.hashCode() : 0);
    result = (31 * result) + ((name != null) ? name.hashCode() : 0);
    result = (31 * result) + ((visibility != null) ? visibility.hashCode() : 0);
    result = (31 * result) + ((group != null) ? group.hashCode() : 0);
    result = (31 * result) + ((groupOrder != null) ? groupOrder.hashCode() : 0);
    result = (31 * result) + ((groupCollapsed != null) ? groupCollapsed.hashCode() : 0);
    result = (31 * result) + ((explicitFieldType != null) ? explicitFieldType.hashCode() : 0);
    result = (31 * result) + ((largeEntry != null) ? largeEntry.hashCode() : 0);
    result = (31 * result) + ((prominent != null) ? prominent.hashCode() : 0);
    result = (31 * result) + ((gridOrder != null) ? gridOrder.hashCode() : 0);
    result = (31 * result) + ((columnWidth != null) ? columnWidth.hashCode() : 0);
    result = (31 * result) + ((broadleafEnumeration != null) ? broadleafEnumeration.hashCode() : 0);
    result = (31 * result) + ((readOnly != null) ? readOnly.hashCode() : 0);
    result = (31 * result) + ((validationConfigurations != null) ? validationConfigurations.hashCode() : 0);
    result = (31 * result) + ((requiredOverride != null) ? requiredOverride.hashCode() : 0);
    result = (31 * result) + ((tooltip != null) ? tooltip.hashCode() : 0);
    result = (31 * result) + ((helpText != null) ? helpText.hashCode() : 0);
    result = (31 * result) + ((hint != null) ? hint.hashCode() : 0);
    result = (31 * result) + ((lookupDisplayProperty != null) ? lookupDisplayProperty.hashCode() : 0);
    result = (31 * result) + ((optionListEntity != null) ? optionListEntity.hashCode() : 0);
    result = (31 * result) + ((optionValueFieldName != null) ? optionValueFieldName.hashCode() : 0);
    result = (31 * result) + ((optionDisplayFieldName != null) ? optionDisplayFieldName.hashCode() : 0);
    result = (31 * result) + ((optionCanEditValues != null) ? optionCanEditValues.hashCode() : 0);
    result = (31 * result) + ((ruleIdentifier != null) ? ruleIdentifier.hashCode() : 0);
    result = (31 * result) + ((mapFieldValueClass != null) ? mapFieldValueClass.hashCode() : 0);
    result = (31 * result) + ((searchable != null) ? searchable.hashCode() : 0);
    result = (31 * result) + ((manyToField != null) ? manyToField.hashCode() : 0);
    result = (31 * result) + ((lookupType != null) ? lookupType.hashCode() : 0);
    result = (31 * result) + ((isDerived != null) ? isDerived.hashCode() : 0);

    return result;
  } // end method hashCode

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
   * @param  customCriteria  DOCUMENT ME!
   */
  public void setCustomCriteria(String[] customCriteria) {
    this.customCriteria = customCriteria;
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
   * @param  fieldType  DOCUMENT ME!
   */
  public void setExplicitFieldType(SupportedFieldType fieldType) {
    this.explicitFieldType = fieldType;
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
   * @param  gridOrder  DOCUMENT ME!
   */
  public void setGridOrder(Integer gridOrder) {
    this.gridOrder = gridOrder;
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
   * @param  mergedPropertyType  DOCUMENT ME!
   */
  public void setMergedPropertyType(MergedPropertyType mergedPropertyType) {
    this.mergedPropertyType = mergedPropertyType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  mutable  DOCUMENT ME!
   */
  public void setMutable(Boolean mutable) {
    this.mutable = mutable;
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
   * @param  optionFilterParams  DOCUMENT ME!
   */
  public void setOptionFilterParams(String[][] optionFilterParams) {
    this.optionFilterParams = optionFilterParams;
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
   * @param  visibility  DOCUMENT ME!
   */
  public void setVisibility(VisibilityEnum visibility) {
    this.visibility = visibility;
  }


} // end class BasicFieldMetadata
