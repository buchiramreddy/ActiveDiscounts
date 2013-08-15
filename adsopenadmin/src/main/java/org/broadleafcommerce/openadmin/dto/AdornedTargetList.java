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

import org.broadleafcommerce.openadmin.dto.visitor.PersistencePerspectiveItemVisitor;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class AdornedTargetList implements PersistencePerspectiveItem {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String adornedTargetEntityClassname;
  private String adornedTargetEntityPolymorphicType;

  private String  collectionFieldName;
  private Boolean inverse          = Boolean.FALSE;
  private String  joinEntityClass;
  private String  linkedIdProperty;
  private String  linkedObjectPath;
  private Boolean mutable          = true;
  private Boolean sortAscending;
  private String  sortField;
  private String  targetIdProperty;
  private String  targetObjectPath;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new AdornedTargetList object.
   */
  public AdornedTargetList() {
    // do nothing
  }

  /**
   * Creates a new AdornedTargetList object.
   *
   * @param  collectionFieldName           DOCUMENT ME!
   * @param  linkedObjectPath              DOCUMENT ME!
   * @param  linkedIdProperty              DOCUMENT ME!
   * @param  targetObjectPath              DOCUMENT ME!
   * @param  targetIdProperty              DOCUMENT ME!
   * @param  adornedTargetEntityClassname  DOCUMENT ME!
   */
  public AdornedTargetList(String collectionFieldName, String linkedObjectPath, String linkedIdProperty,
    String targetObjectPath, String targetIdProperty, String adornedTargetEntityClassname) {
    this(collectionFieldName, linkedObjectPath, linkedIdProperty, targetObjectPath, targetIdProperty,
      adornedTargetEntityClassname, null, null);
  }

  /**
   * Creates a new AdornedTargetList object.
   *
   * @param  collectionFieldName                 DOCUMENT ME!
   * @param  linkedObjectPath                    DOCUMENT ME!
   * @param  linkedIdProperty                    DOCUMENT ME!
   * @param  targetObjectPath                    DOCUMENT ME!
   * @param  targetIdProperty                    DOCUMENT ME!
   * @param  adornedTargetEntityClassname        DOCUMENT ME!
   * @param  adornedTargetEntityPolymorphicType  DOCUMENT ME!
   */
  public AdornedTargetList(String collectionFieldName, String linkedObjectPath, String linkedIdProperty,
    String targetObjectPath, String targetIdProperty, String adornedTargetEntityClassname,
    String adornedTargetEntityPolymorphicType) {
    this(collectionFieldName, linkedObjectPath, linkedIdProperty, targetObjectPath, targetIdProperty,
      adornedTargetEntityClassname, adornedTargetEntityPolymorphicType, null, null);
  }

  /**
   * Creates a new AdornedTargetList object.
   *
   * @param  collectionFieldName           DOCUMENT ME!
   * @param  linkedObjectPath              DOCUMENT ME!
   * @param  linkedIdProperty              DOCUMENT ME!
   * @param  targetObjectPath              DOCUMENT ME!
   * @param  targetIdProperty              DOCUMENT ME!
   * @param  adornedTargetEntityClassname  DOCUMENT ME!
   * @param  sortField                     DOCUMENT ME!
   * @param  sortAscending                 DOCUMENT ME!
   */
  public AdornedTargetList(String collectionFieldName, String linkedObjectPath, String linkedIdProperty,
    String targetObjectPath, String targetIdProperty, String adornedTargetEntityClassname, String sortField,
    Boolean sortAscending) {
    this(collectionFieldName, linkedObjectPath, linkedIdProperty, targetObjectPath, targetIdProperty,
      adornedTargetEntityClassname, null, sortField, sortAscending);
  }

  /**
   * Creates a new AdornedTargetList object.
   *
   * @param  collectionFieldName                 DOCUMENT ME!
   * @param  linkedObjectPath                    DOCUMENT ME!
   * @param  linkedIdProperty                    DOCUMENT ME!
   * @param  targetObjectPath                    DOCUMENT ME!
   * @param  targetIdProperty                    DOCUMENT ME!
   * @param  adornedTargetEntityClassname        DOCUMENT ME!
   * @param  adornedTargetEntityPolymorphicType  DOCUMENT ME!
   * @param  sortField                           DOCUMENT ME!
   * @param  sortAscending                       DOCUMENT ME!
   */
  public AdornedTargetList(String collectionFieldName, String linkedObjectPath, String linkedIdProperty,
    String targetObjectPath, String targetIdProperty, String adornedTargetEntityClassname,
    String adornedTargetEntityPolymorphicType, String sortField, Boolean sortAscending) {
    this.collectionFieldName                = collectionFieldName;
    this.linkedObjectPath                   = linkedObjectPath;
    this.targetObjectPath                   = targetObjectPath;
    this.adornedTargetEntityClassname       = adornedTargetEntityClassname;
    this.adornedTargetEntityPolymorphicType = adornedTargetEntityPolymorphicType;
    this.sortField                          = sortField;
    this.sortAscending                      = sortAscending;
    this.linkedIdProperty                   = linkedIdProperty;
    this.targetIdProperty                   = targetIdProperty;
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
    AdornedTargetList adornedTargetList = new AdornedTargetList();
    adornedTargetList.collectionFieldName                = collectionFieldName;
    adornedTargetList.linkedObjectPath                   = linkedObjectPath;
    adornedTargetList.targetObjectPath                   = targetObjectPath;
    adornedTargetList.adornedTargetEntityClassname       = adornedTargetEntityClassname;
    adornedTargetList.adornedTargetEntityPolymorphicType = adornedTargetEntityPolymorphicType;
    adornedTargetList.sortField                          = sortField;
    adornedTargetList.sortAscending                      = sortAscending;
    adornedTargetList.linkedIdProperty                   = linkedIdProperty;
    adornedTargetList.targetIdProperty                   = targetIdProperty;
    adornedTargetList.inverse                            = inverse;
    adornedTargetList.joinEntityClass                    = joinEntityClass;
    adornedTargetList.mutable                            = mutable;

    return adornedTargetList;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof AdornedTargetList)) {
      return false;
    }

    AdornedTargetList that = (AdornedTargetList) o;

    if ((adornedTargetEntityClassname != null)
          ? (!adornedTargetEntityClassname.equals(that.adornedTargetEntityClassname))
          : (that.adornedTargetEntityClassname != null)) {
      return false;
    }

    if ((adornedTargetEntityPolymorphicType != null)
          ? (!adornedTargetEntityPolymorphicType.equals(that.adornedTargetEntityPolymorphicType))
          : (that.adornedTargetEntityPolymorphicType != null)) {
      return false;
    }

    if ((collectionFieldName != null) ? (!collectionFieldName.equals(that.collectionFieldName))
                                      : (that.collectionFieldName != null)) {
      return false;
    }

    if ((inverse != null) ? (!inverse.equals(that.inverse)) : (that.inverse != null)) {
      return false;
    }

    if ((linkedIdProperty != null) ? (!linkedIdProperty.equals(that.linkedIdProperty))
                                   : (that.linkedIdProperty != null)) {
      return false;
    }

    if ((linkedObjectPath != null) ? (!linkedObjectPath.equals(that.linkedObjectPath))
                                   : (that.linkedObjectPath != null)) {
      return false;
    }

    if ((sortAscending != null) ? (!sortAscending.equals(that.sortAscending)) : (that.sortAscending != null)) {
      return false;
    }

    if ((sortField != null) ? (!sortField.equals(that.sortField)) : (that.sortField != null)) {
      return false;
    }

    if ((targetIdProperty != null) ? (!targetIdProperty.equals(that.targetIdProperty))
                                   : (that.targetIdProperty != null)) {
      return false;
    }

    if ((targetObjectPath != null) ? (!targetObjectPath.equals(that.targetObjectPath))
                                   : (that.targetObjectPath != null)) {
      return false;
    }

    if ((joinEntityClass != null) ? (!joinEntityClass.equals(that.joinEntityClass)) : (that.joinEntityClass != null)) {
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
  public String getAdornedTargetEntityClassname() {
    return adornedTargetEntityClassname;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getAdornedTargetEntityPolymorphicType() {
    return adornedTargetEntityPolymorphicType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getCollectionFieldName() {
    return collectionFieldName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getInverse() {
    return inverse;
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
  public String getLinkedIdProperty() {
    return linkedIdProperty;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getLinkedObjectPath() {
    return linkedObjectPath;
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
  public Boolean getSortAscending() {
    return sortAscending;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getSortField() {
    return sortField;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getTargetIdProperty() {
    return targetIdProperty;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getTargetObjectPath() {
    return targetObjectPath;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    int result = (collectionFieldName != null) ? collectionFieldName.hashCode() : 0;
    result = (31 * result) + ((linkedObjectPath != null) ? linkedObjectPath.hashCode() : 0);
    result = (31 * result) + ((targetObjectPath != null) ? targetObjectPath.hashCode() : 0);
    result = (31 * result) + ((adornedTargetEntityClassname != null) ? adornedTargetEntityClassname.hashCode() : 0);
    result = (31 * result)
      + ((adornedTargetEntityPolymorphicType != null) ? adornedTargetEntityPolymorphicType.hashCode() : 0);
    result = (31 * result) + ((sortField != null) ? sortField.hashCode() : 0);
    result = (31 * result) + ((sortAscending != null) ? sortAscending.hashCode() : 0);
    result = (31 * result) + ((linkedIdProperty != null) ? linkedIdProperty.hashCode() : 0);
    result = (31 * result) + ((targetIdProperty != null) ? targetIdProperty.hashCode() : 0);
    result = (31 * result) + ((inverse != null) ? inverse.hashCode() : 0);
    result = (31 * result) + ((joinEntityClass != null) ? joinEntityClass.hashCode() : 0);
    result = (31 * result) + ((mutable != null) ? mutable.hashCode() : 0);

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  adornedTargetEntityClassname  DOCUMENT ME!
   */
  public void setAdornedTargetEntityClassname(String adornedTargetEntityClassname) {
    this.adornedTargetEntityClassname = adornedTargetEntityClassname;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  adornedTargetEntityPolymorphicType  DOCUMENT ME!
   */
  public void setAdornedTargetEntityPolymorphicType(String adornedTargetEntityPolymorphicType) {
    this.adornedTargetEntityPolymorphicType = adornedTargetEntityPolymorphicType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  manyToField  DOCUMENT ME!
   */
  public void setCollectionFieldName(String manyToField) {
    this.collectionFieldName = manyToField;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  inverse  DOCUMENT ME!
   */
  public void setInverse(Boolean inverse) {
    this.inverse = inverse;
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
   * @param  linkedIdProperty  DOCUMENT ME!
   */
  public void setLinkedIdProperty(String linkedIdProperty) {
    this.linkedIdProperty = linkedIdProperty;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  linkedPropertyPath  DOCUMENT ME!
   */
  public void setLinkedObjectPath(String linkedPropertyPath) {
    this.linkedObjectPath = linkedPropertyPath;
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
   * @param  sortAscending  DOCUMENT ME!
   */
  public void setSortAscending(Boolean sortAscending) {
    this.sortAscending = sortAscending;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  sortField  DOCUMENT ME!
   */
  public void setSortField(String sortField) {
    this.sortField = sortField;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  targetIdProperty  DOCUMENT ME!
   */
  public void setTargetIdProperty(String targetIdProperty) {
    this.targetIdProperty = targetIdProperty;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  targetObjectPath  DOCUMENT ME!
   */
  public void setTargetObjectPath(String targetObjectPath) {
    this.targetObjectPath = targetObjectPath;
  }
} // end class AdornedTargetList
