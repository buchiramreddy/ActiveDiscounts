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

import org.broadleafcommerce.common.presentation.client.ForeignKeyRestrictionType;

import org.broadleafcommerce.openadmin.dto.visitor.PersistencePerspectiveItemVisitor;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class ForeignKey implements Serializable, PersistencePerspectiveItem {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String currentValue;
  private String dataSourceName;
  private String displayValueProperty = "name";
  private String foreignKeyClass;

  private String                    manyToField;
  private Boolean                   mutable = true;
  private ForeignKeyRestrictionType restrictionType = ForeignKeyRestrictionType.ID_EQ;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new ForeignKey object.
   */
  public ForeignKey() {
    // do nothing
  }

  /**
   * Creates a new ForeignKey object.
   *
   * @param  manyToField      DOCUMENT ME!
   * @param  foreignKeyClass  DOCUMENT ME!
   */
  public ForeignKey(String manyToField, String foreignKeyClass) {
    this(manyToField, foreignKeyClass, null);
  }

  /**
   * Creates a new ForeignKey object.
   *
   * @param  manyToField      DOCUMENT ME!
   * @param  foreignKeyClass  DOCUMENT ME!
   * @param  dataSourceName   DOCUMENT ME!
   */
  public ForeignKey(String manyToField, String foreignKeyClass, String dataSourceName) {
    this(manyToField, foreignKeyClass, dataSourceName, ForeignKeyRestrictionType.ID_EQ);
  }

  /**
   * Creates a new ForeignKey object.
   *
   * @param  manyToField      DOCUMENT ME!
   * @param  foreignKeyClass  DOCUMENT ME!
   * @param  dataSourceName   DOCUMENT ME!
   * @param  restrictionType  DOCUMENT ME!
   */
  public ForeignKey(String manyToField, String foreignKeyClass, String dataSourceName,
    ForeignKeyRestrictionType restrictionType) {
    this(manyToField, foreignKeyClass, dataSourceName, restrictionType, "name");
  }

  /**
   * Creates a new ForeignKey object.
   *
   * @param  manyToField           DOCUMENT ME!
   * @param  foreignKeyClass       DOCUMENT ME!
   * @param  dataSourceName        DOCUMENT ME!
   * @param  restrictionType       DOCUMENT ME!
   * @param  displayValueProperty  DOCUMENT ME!
   */
  public ForeignKey(String manyToField, String foreignKeyClass, String dataSourceName,
    ForeignKeyRestrictionType restrictionType, String displayValueProperty) {
    this.manyToField          = manyToField;
    this.foreignKeyClass      = foreignKeyClass;
    this.dataSourceName       = dataSourceName;
    this.restrictionType      = restrictionType;
    this.displayValueProperty = displayValueProperty;
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
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public ForeignKey cloneForeignKey() {
    ForeignKey foreignKey = new ForeignKey();
    foreignKey.manyToField          = manyToField;
    foreignKey.foreignKeyClass      = foreignKeyClass;
    foreignKey.currentValue         = currentValue;
    foreignKey.dataSourceName       = dataSourceName;
    foreignKey.restrictionType      = restrictionType;
    foreignKey.displayValueProperty = displayValueProperty;
    foreignKey.mutable              = mutable;

    return foreignKey;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.dto.PersistencePerspectiveItem#clonePersistencePerspectiveItem()
   */
  @Override public PersistencePerspectiveItem clonePersistencePerspectiveItem() {
    return cloneForeignKey();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof ForeignKey)) {
      return false;
    }

    ForeignKey that = (ForeignKey) o;

    if ((currentValue != null) ? (!currentValue.equals(that.currentValue)) : (that.currentValue != null)) {
      return false;
    }

    if ((dataSourceName != null) ? (!dataSourceName.equals(that.dataSourceName)) : (that.dataSourceName != null)) {
      return false;
    }

    if ((displayValueProperty != null) ? (!displayValueProperty.equals(that.displayValueProperty))
                                       : (that.displayValueProperty != null)) {
      return false;
    }

    if ((foreignKeyClass != null) ? (!foreignKeyClass.equals(that.foreignKeyClass)) : (that.foreignKeyClass != null)) {
      return false;
    }

    if ((manyToField != null) ? (!manyToField.equals(that.manyToField)) : (that.manyToField != null)) {
      return false;
    }

    if (restrictionType != that.restrictionType) {
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
  public String getCurrentValue() {
    return currentValue;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getDataSourceName() {
    return dataSourceName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getDisplayValueProperty() {
    return displayValueProperty;
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
  public String getManyToField() {
    return manyToField;
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
  public ForeignKeyRestrictionType getRestrictionType() {
    return restrictionType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    int result = (manyToField != null) ? manyToField.hashCode() : 0;
    result = (31 * result) + ((foreignKeyClass != null) ? foreignKeyClass.hashCode() : 0);
    result = (31 * result) + ((currentValue != null) ? currentValue.hashCode() : 0);
    result = (31 * result) + ((dataSourceName != null) ? dataSourceName.hashCode() : 0);
    result = (31 * result) + ((restrictionType != null) ? restrictionType.hashCode() : 0);
    result = (31 * result) + ((displayValueProperty != null) ? displayValueProperty.hashCode() : 0);
    result = (31 * result) + ((mutable != null) ? mutable.hashCode() : 0);

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  currentValue  DOCUMENT ME!
   */
  public void setCurrentValue(String currentValue) {
    this.currentValue = currentValue;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  dataSourceName  DOCUMENT ME!
   */
  public void setDataSourceName(String dataSourceName) {
    this.dataSourceName = dataSourceName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  displayValueProperty  DOCUMENT ME!
   */
  public void setDisplayValueProperty(String displayValueProperty) {
    this.displayValueProperty = displayValueProperty;
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
   * @param  manyToField  DOCUMENT ME!
   */
  public void setManyToField(String manyToField) {
    this.manyToField = manyToField;
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
   * @param  restrictionType  DOCUMENT ME!
   */
  public void setRestrictionType(ForeignKeyRestrictionType restrictionType) {
    this.restrictionType = restrictionType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#toString()
   */
  @Override public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(manyToField);
    sb.append(foreignKeyClass);
    sb.append(currentValue);
    sb.append(dataSourceName);
    sb.append(restrictionType);
    sb.append(displayValueProperty);

    return sb.toString();
  }
} // end class ForeignKey
