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

import org.broadleafcommerce.openadmin.dto.visitor.MetadataVisitor;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class MapMetadata extends CollectionMetadata {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Boolean    forceFreeFormKeys;
  private boolean    isSimpleValue;
  private String[][] keys;
  private String     mapKeyOptionEntityClass;
  private String     mapKeyOptionEntityDisplayField;
  private String     mapKeyOptionEntityValueField;
  private String     mediaField;

  private String valueClassName;

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
    MapMetadata metadata = new MapMetadata();

    return populate(metadata);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.dto.CollectionMetadata#equals(java.lang.Object)
   */
  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof MapMetadata)) {
      return false;
    }

    if (!super.equals(o)) {
      return false;
    }

    MapMetadata metadata = (MapMetadata) o;

    if (isSimpleValue != metadata.isSimpleValue) {
      return false;
    }

    if ((mapKeyOptionEntityClass != null) ? (!mapKeyOptionEntityClass.equals(metadata.mapKeyOptionEntityClass))
                                          : (metadata.mapKeyOptionEntityClass != null)) {
      return false;
    }

    if ((mapKeyOptionEntityDisplayField != null)
          ? (!mapKeyOptionEntityDisplayField.equals(metadata.mapKeyOptionEntityDisplayField))
          : (metadata.mapKeyOptionEntityDisplayField != null)) {
      return false;
    }

    if ((mapKeyOptionEntityValueField != null)
          ? (!mapKeyOptionEntityValueField.equals(metadata.mapKeyOptionEntityValueField))
          : (metadata.mapKeyOptionEntityValueField != null)) {
      return false;
    }

    if ((mediaField != null) ? (!mediaField.equals(metadata.mediaField)) : (metadata.mediaField != null)) {
      return false;
    }

    if ((valueClassName != null) ? (!valueClassName.equals(metadata.valueClassName))
                                 : (metadata.valueClassName != null)) {
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
  public Boolean getForceFreeFormKeys() {
    return forceFreeFormKeys;
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
  public String getValueClassName() {
    return valueClassName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.dto.CollectionMetadata#hashCode()
   */
  @Override public int hashCode() {
    int result = super.hashCode();
    result = (31 * result) + ((valueClassName != null) ? valueClassName.hashCode() : 0);
    result = (31 * result) + (isSimpleValue ? 1 : 0);
    result = (31 * result) + ((mediaField != null) ? mediaField.hashCode() : 0);
    result = (31 * result) + ((mapKeyOptionEntityClass != null) ? mapKeyOptionEntityClass.hashCode() : 0);
    result = (31 * result) + ((mapKeyOptionEntityDisplayField != null) ? mapKeyOptionEntityDisplayField.hashCode() : 0);
    result = (31 * result) + ((mapKeyOptionEntityValueField != null) ? mapKeyOptionEntityValueField.hashCode() : 0);

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isSimpleValue() {
    return isSimpleValue;
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
   * @param  keys  DOCUMENT ME!
   */
  public void setKeys(String[][] keys) {
    this.keys = keys;
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
   * @param  simpleValue  DOCUMENT ME!
   */
  public void setSimpleValue(boolean simpleValue) {
    isSimpleValue = simpleValue;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  valueClassName  DOCUMENT ME!
   */
  public void setValueClassName(String valueClassName) {
    this.valueClassName = valueClassName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.dto.CollectionMetadata#populate(org.broadleafcommerce.openadmin.dto.FieldMetadata)
   */
  @Override protected FieldMetadata populate(FieldMetadata metadata) {
    ((MapMetadata) metadata).valueClassName                 = valueClassName;
    ((MapMetadata) metadata).isSimpleValue                  = isSimpleValue;
    ((MapMetadata) metadata).mediaField                     = mediaField;
    ((MapMetadata) metadata).keys                           = keys;
    ((MapMetadata) metadata).mapKeyOptionEntityClass        = mapKeyOptionEntityClass;
    ((MapMetadata) metadata).mapKeyOptionEntityDisplayField = mapKeyOptionEntityDisplayField;
    ((MapMetadata) metadata).mapKeyOptionEntityValueField   = mapKeyOptionEntityValueField;
    ((MapMetadata) metadata).forceFreeFormKeys              = forceFreeFormKeys;

    return super.populate(metadata);
  }
} // end class MapMetadata
