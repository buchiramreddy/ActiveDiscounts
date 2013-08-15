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
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.broadleafcommerce.common.presentation.client.PersistencePerspectiveItemType;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class PersistencePerspective implements Serializable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected ForeignKey[] additionalForeignKeys = new ForeignKey[] {};

  /** DOCUMENT ME! */
  protected String[]                                                        additionalNonPersistentProperties =
    new String[] {};

  /** DOCUMENT ME! */
  protected String                                                          configurationKey;

  /** DOCUMENT ME! */
  protected String[]                                                        excludeFields                =
    new String[] {};

  /** DOCUMENT ME! */
  protected String[]                                                        includeFields                =
    new String[] {};

  /** DOCUMENT ME! */
  protected OperationTypes                                                  operationTypes               =
    new OperationTypes();

  /** DOCUMENT ME! */
  protected Map<PersistencePerspectiveItemType, PersistencePerspectiveItem> persistencePerspectiveItems  =
    new HashMap<PersistencePerspectiveItemType, PersistencePerspectiveItem>();

  /** DOCUMENT ME! */
  protected Boolean                                                         populateToOneFields          = false;

  /** DOCUMENT ME! */
  protected Boolean                                                         showArchivedFields           = false;

  /** DOCUMENT ME! */
  protected Boolean                                                         useServerSideInspectionCache = true;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new PersistencePerspective object.
   */
  public PersistencePerspective() { }

  /**
   * Creates a new PersistencePerspective object.
   *
   * @param  operationTypes                     DOCUMENT ME!
   * @param  additionalNonPersistentProperties  DOCUMENT ME!
   * @param  additionalForeignKeys              DOCUMENT ME!
   */
  public PersistencePerspective(OperationTypes operationTypes, String[] additionalNonPersistentProperties,
    ForeignKey[] additionalForeignKeys) {
    setAdditionalNonPersistentProperties(additionalNonPersistentProperties);
    setAdditionalForeignKeys(additionalForeignKeys);
    this.operationTypes = operationTypes;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  type  DOCUMENT ME!
   * @param  item  DOCUMENT ME!
   */
  public void addPersistencePerspectiveItem(PersistencePerspectiveItemType type, PersistencePerspectiveItem item) {
    persistencePerspectiveItems.put(type, item);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PersistencePerspective clonePersistencePerspective() {
    PersistencePerspective persistencePerspective = new PersistencePerspective();
    persistencePerspective.operationTypes = operationTypes.cloneOperationTypes();

    if (additionalNonPersistentProperties != null) {
      persistencePerspective.additionalNonPersistentProperties = new String[additionalNonPersistentProperties.length];
      System.arraycopy(additionalNonPersistentProperties, 0, persistencePerspective.additionalNonPersistentProperties,
        0, additionalNonPersistentProperties.length);
    }

    if (additionalForeignKeys != null) {
      persistencePerspective.additionalForeignKeys = new ForeignKey[additionalForeignKeys.length];

      for (int j = 0; j < additionalForeignKeys.length; j++) {
        persistencePerspective.additionalForeignKeys[j] = additionalForeignKeys[j].cloneForeignKey();
      }
    }

    if (this.persistencePerspectiveItems != null) {
      Map<PersistencePerspectiveItemType, PersistencePerspectiveItem> persistencePerspectiveItems =
        new HashMap<PersistencePerspectiveItemType, PersistencePerspectiveItem>(this.persistencePerspectiveItems
          .size());

      for (Map.Entry<PersistencePerspectiveItemType, PersistencePerspectiveItem> entry
        : this.persistencePerspectiveItems.entrySet()) {
        persistencePerspectiveItems.put(entry.getKey(), entry.getValue().clonePersistencePerspectiveItem());
      }

      persistencePerspective.persistencePerspectiveItems = persistencePerspectiveItems;
    }

    persistencePerspective.populateToOneFields          = populateToOneFields;
    persistencePerspective.configurationKey             = configurationKey;
    persistencePerspective.showArchivedFields           = showArchivedFields;
    persistencePerspective.useServerSideInspectionCache = useServerSideInspectionCache;

    if (excludeFields != null) {
      persistencePerspective.excludeFields = new String[excludeFields.length];
      System.arraycopy(excludeFields, 0, persistencePerspective.excludeFields, 0, excludeFields.length);
    }

    if (includeFields != null) {
      persistencePerspective.includeFields = new String[includeFields.length];
      System.arraycopy(includeFields, 0, persistencePerspective.includeFields, 0, includeFields.length);
    }

    return persistencePerspective;
  } // end method clonePersistencePerspective

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof PersistencePerspective)) {
      return false;
    }

    PersistencePerspective that = (PersistencePerspective) o;

    if (!Arrays.equals(additionalForeignKeys, that.additionalForeignKeys)) {
      return false;
    }

    if (!Arrays.equals(additionalNonPersistentProperties, that.additionalNonPersistentProperties)) {
      return false;
    }

    if ((configurationKey != null) ? (!configurationKey.equals(that.configurationKey))
                                   : (that.configurationKey != null)) {
      return false;
    }

    if (!Arrays.equals(excludeFields, that.excludeFields)) {
      return false;
    }

    if (!Arrays.equals(includeFields, that.includeFields)) {
      return false;
    }

    if ((operationTypes != null) ? (!operationTypes.equals(that.operationTypes)) : (that.operationTypes != null)) {
      return false;
    }

    if ((persistencePerspectiveItems != null) ? (!persistencePerspectiveItems.equals(
              that.persistencePerspectiveItems)) : (that.persistencePerspectiveItems != null)) {
      return false;
    }

    if ((populateToOneFields != null) ? (!populateToOneFields.equals(that.populateToOneFields))
                                      : (that.populateToOneFields != null)) {
      return false;
    }

    if ((showArchivedFields != null) ? (!showArchivedFields.equals(that.showArchivedFields))
                                     : (that.showArchivedFields != null)) {
      return false;
    }

    if ((useServerSideInspectionCache != null)
          ? (!useServerSideInspectionCache.equals(that.useServerSideInspectionCache))
          : (that.useServerSideInspectionCache != null)) {
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
  public ForeignKey[] getAdditionalForeignKeys() {
    return additionalForeignKeys;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String[] getAdditionalNonPersistentProperties() {
    return additionalNonPersistentProperties;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getConfigurationKey() {
    return configurationKey;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Retrieve the list of fields to exclude from the admin presentation. Implementations should use the excluded
   * property of the AdminPresentation annotation instead, or use an AdminPresentationOverride if re-enabling a
   * Broadleaf field is desired. If multiple datasources point to the same entity, but different exclusion behavior is
   * required, a custom persistence handler may be employed with different inspect method implementations to account for
   * the variations.
   *
   * @return  list of fields to exclude from the admin
   */
  @Deprecated public String[] getExcludeFields() {
    return excludeFields;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Get the list of fields to include in the admin presentation. Implementations should use excludeFields instead.
   *
   * @return  list of fields to include in the admin
   */
  @Deprecated public String[] getIncludeFields() {
    return includeFields;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public OperationTypes getOperationTypes() {
    return operationTypes;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<PersistencePerspectiveItemType, PersistencePerspectiveItem> getPersistencePerspectiveItems() {
    return persistencePerspectiveItems;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Retrieves whether or not ManyToOne and OneToOne field boundaries will be traversed when retrieving and populating
   * entity fields. Implementation should use the @AdminPresentationClass annotation instead.
   *
   * @return  Whether or not ManyToOne and OneToOne field boundaries will be crossed.
   */
  @Deprecated public Boolean getPopulateToOneFields() {
    return populateToOneFields;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getShowArchivedFields() {
    return showArchivedFields;
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
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    int result = (additionalNonPersistentProperties != null) ? Arrays.hashCode(additionalNonPersistentProperties) : 0;
    result = (31 * result) + ((additionalForeignKeys != null) ? Arrays.hashCode(additionalForeignKeys) : 0);
    result = (31 * result) + ((persistencePerspectiveItems != null) ? persistencePerspectiveItems.hashCode() : 0);
    result = (31 * result) + ((operationTypes != null) ? operationTypes.hashCode() : 0);
    result = (31 * result) + ((populateToOneFields != null) ? populateToOneFields.hashCode() : 0);
    result = (31 * result) + ((excludeFields != null) ? Arrays.hashCode(excludeFields) : 0);
    result = (31 * result) + ((includeFields != null) ? Arrays.hashCode(includeFields) : 0);
    result = (31 * result) + ((configurationKey != null) ? configurationKey.hashCode() : 0);
    result = (31 * result) + ((showArchivedFields != null) ? showArchivedFields.hashCode() : 0);
    result = (31 * result) + ((useServerSideInspectionCache != null) ? useServerSideInspectionCache.hashCode() : 0);

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  additionalForeignKeys  DOCUMENT ME!
   */
  public void setAdditionalForeignKeys(ForeignKey[] additionalForeignKeys) {
    this.additionalForeignKeys = additionalForeignKeys;
    Arrays.sort(this.additionalForeignKeys, new Comparator<ForeignKey>() {
        @Override public int compare(ForeignKey o1, ForeignKey o2) {
          return o1.getManyToField().compareTo(o2.getManyToField());
        }
      });
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  additionalNonPersistentProperties  DOCUMENT ME!
   */
  public void setAdditionalNonPersistentProperties(String[] additionalNonPersistentProperties) {
    this.additionalNonPersistentProperties = additionalNonPersistentProperties;
    Arrays.sort(this.additionalNonPersistentProperties);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  configurationKey  DOCUMENT ME!
   */
  public void setConfigurationKey(String configurationKey) {
    this.configurationKey = configurationKey;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Set the list of fields to exclude from the admin presentation. Implementations should use the excluded property of
   * the AdminPresentation annotation instead, or use an AdminPresentationOverride if re-enabling a Broadleaf field is
   * desired. If multiple datasources point to the same entity, but different exclusion behavior is required, a custom
   * persistence handler may be employed with different inspect method implementations to account for the variations.
   *
   * @param  excludeManyToOneFields  DOCUMENT ME!
   */
  @Deprecated public void setExcludeFields(String[] excludeManyToOneFields) {
    this.excludeFields = excludeManyToOneFields;
    Arrays.sort(this.excludeFields);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Set the list of fields to include in the admin presentation. Implementations should use excludeFields instead.
   *
   * @param  includeManyToOneFields  DOCUMENT ME!
   */
  @Deprecated public void setIncludeFields(String[] includeManyToOneFields) {
    this.includeFields = includeManyToOneFields;
    Arrays.sort(this.includeFields);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  operationTypes  DOCUMENT ME!
   */
  public void setOperationTypes(OperationTypes operationTypes) {
    this.operationTypes = operationTypes;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  persistencePerspectiveItems  DOCUMENT ME!
   */
  public void setPersistencePerspectiveItems(
    Map<PersistencePerspectiveItemType, PersistencePerspectiveItem> persistencePerspectiveItems) {
    this.persistencePerspectiveItems = persistencePerspectiveItems;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets whether or not ManyToOne and OneToOne field boundaries will be traversed when retrieving and populating entity
   * fields. Implementation should use the @AdminPresentationClass annotation instead.
   *
   * @param  populateToOneFields  DOCUMENT ME!
   */
  @Deprecated public void setPopulateToOneFields(Boolean populateToOneFields) {
    this.populateToOneFields = populateToOneFields;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  showArchivedFields  DOCUMENT ME!
   */
  public void setShowArchivedFields(Boolean showArchivedFields) {
    this.showArchivedFields = showArchivedFields;
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
} // end class PersistencePerspective
