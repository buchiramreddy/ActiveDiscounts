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

import org.broadleafcommerce.openadmin.dto.visitor.PersistencePerspectiveItemVisitor;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class MapStructure implements Serializable, PersistencePerspectiveItem {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Boolean deleteValueEntity = Boolean.FALSE;

  private String  keyClassName;
  private String  keyPropertyFriendlyName;
  private String  keyPropertyName;
  private String  manyToField;
  private String  mapProperty;
  private Boolean mutable        = true;
  private String  valueClassName;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new MapStructure object.
   */
  public MapStructure() {
    // do nothing - support serialization requirements
  }

  /**
   * Creates a new MapStructure object.
   *
   * @param   keyClassName             DOCUMENT ME!
   * @param   keyPropertyName          DOCUMENT ME!
   * @param   keyPropertyFriendlyName  DOCUMENT ME!
   * @param   valueClassName           DOCUMENT ME!
   * @param   mapProperty              DOCUMENT ME!
   * @param   deleteValueEntity        DOCUMENT ME!
   *
   * @throws  RuntimeException  DOCUMENT ME!
   */
  public MapStructure(String keyClassName, String keyPropertyName, String keyPropertyFriendlyName,
    String valueClassName, String mapProperty, Boolean deleteValueEntity) {
    if (!keyClassName.equals(String.class.getName())) {
      throw new RuntimeException("keyClass of java.lang.String is currently the only type supported");
    }

    this.keyClassName            = keyClassName;
    this.valueClassName          = valueClassName;
    this.mapProperty             = mapProperty;
    this.keyPropertyName         = keyPropertyName;
    this.keyPropertyFriendlyName = keyPropertyFriendlyName;
    this.deleteValueEntity       = deleteValueEntity;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.dto.PersistencePerspectiveItem#accept(org.broadleafcommerce.openadmin.dto.visitor.PersistencePerspectiveItemVisitor)
   */
  @Override public void accept(PersistencePerspectiveItemVisitor visitor) {
    visitor.visit(this);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.dto.PersistencePerspectiveItem#clonePersistencePerspectiveItem()
   */
  @Override public PersistencePerspectiveItem clonePersistencePerspectiveItem() {
    MapStructure mapStructure = new MapStructure();
    mapStructure.keyClassName            = keyClassName;
    mapStructure.keyPropertyName         = keyPropertyName;
    mapStructure.keyPropertyFriendlyName = keyPropertyFriendlyName;
    mapStructure.valueClassName          = valueClassName;
    mapStructure.mapProperty             = mapProperty;
    mapStructure.deleteValueEntity       = deleteValueEntity;
    mapStructure.manyToField             = manyToField;
    mapStructure.mutable                 = mutable;

    return mapStructure;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof MapStructure)) {
      return false;
    }

    MapStructure that = (MapStructure) o;

    if ((deleteValueEntity != null) ? (!deleteValueEntity.equals(that.deleteValueEntity))
                                    : (that.deleteValueEntity != null)) {
      return false;
    }

    if ((keyClassName != null) ? (!keyClassName.equals(that.keyClassName)) : (that.keyClassName != null)) {
      return false;
    }

    if ((keyPropertyFriendlyName != null) ? (!keyPropertyFriendlyName.equals(that.keyPropertyFriendlyName))
                                          : (that.keyPropertyFriendlyName != null)) {
      return false;
    }

    if ((keyPropertyName != null) ? (!keyPropertyName.equals(that.keyPropertyName)) : (that.keyPropertyName != null)) {
      return false;
    }

    if ((mapProperty != null) ? (!mapProperty.equals(that.mapProperty)) : (that.mapProperty != null)) {
      return false;
    }

    if ((valueClassName != null) ? (!valueClassName.equals(that.valueClassName)) : (that.valueClassName != null)) {
      return false;
    }

    if ((manyToField != null) ? (!manyToField.equals(that.manyToField)) : (that.manyToField != null)) {
      return false;
    }

    if ((mutable != null) ? (!mutable.equals(that.mutable)) : (that.mutable != null)) {
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
  public Boolean getDeleteValueEntity() {
    return deleteValueEntity;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getKeyClassName() {
    return keyClassName;
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
  public String getKeyPropertyName() {
    return keyPropertyName;
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
  public String getMapProperty() {
    return mapProperty;
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
  public String getValueClassName() {
    return valueClassName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    int result = (keyClassName != null) ? keyClassName.hashCode() : 0;
    result = (31 * result) + ((keyPropertyName != null) ? keyPropertyName.hashCode() : 0);
    result = (31 * result) + ((keyPropertyFriendlyName != null) ? keyPropertyFriendlyName.hashCode() : 0);
    result = (31 * result) + ((valueClassName != null) ? valueClassName.hashCode() : 0);
    result = (31 * result) + ((mapProperty != null) ? mapProperty.hashCode() : 0);
    result = (31 * result) + ((deleteValueEntity != null) ? deleteValueEntity.hashCode() : 0);
    result = (31 * result) + ((manyToField != null) ? manyToField.hashCode() : 0);
    result = (31 * result) + ((mutable != null) ? mutable.hashCode() : 0);

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  deleteValueEntity  DOCUMENT ME!
   */
  public void setDeleteValueEntity(Boolean deleteValueEntity) {
    this.deleteValueEntity = deleteValueEntity;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   keyClassName  DOCUMENT ME!
   *
   * @throws  RuntimeException  DOCUMENT ME!
   */
  public void setKeyClassName(String keyClassName) {
    if (!keyClassName.equals(String.class.getName())) {
      throw new RuntimeException("keyClass of java.lang.String is currently the only type supported");
    }

    this.keyClassName = keyClassName;
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
   * @param  keyPropertyName  DOCUMENT ME!
   */
  public void setKeyPropertyName(String keyPropertyName) {
    this.keyPropertyName = keyPropertyName;
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
   * @param  mapProperty  DOCUMENT ME!
   */
  public void setMapProperty(String mapProperty) {
    this.mapProperty = mapProperty;
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
   * @param  valueClassName  DOCUMENT ME!
   */
  public void setValueClassName(String valueClassName) {
    this.valueClassName = valueClassName;
  }
} // end class MapStructure
