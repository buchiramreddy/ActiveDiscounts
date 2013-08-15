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

import java.util.Arrays;

import org.broadleafcommerce.openadmin.dto.visitor.MetadataVisitor;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class AdornedTargetCollectionMetadata extends CollectionMetadata {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String[] gridVisibleFields = {};

  private boolean  ignoreAdornedProperties;
  private String[] maintainedAdornedTargetFields = {};
  private String   parentObjectClass;

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
    AdornedTargetCollectionMetadata metadata = new AdornedTargetCollectionMetadata();

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

    if (!(o instanceof AdornedTargetCollectionMetadata)) {
      return false;
    }

    if (!super.equals(o)) {
      return false;
    }

    AdornedTargetCollectionMetadata metadata = (AdornedTargetCollectionMetadata) o;

    if (ignoreAdornedProperties != metadata.ignoreAdornedProperties) {
      return false;
    }

    if (!Arrays.equals(gridVisibleFields, metadata.gridVisibleFields)) {
      return false;
    }

    if (!Arrays.equals(maintainedAdornedTargetFields, metadata.maintainedAdornedTargetFields)) {
      return false;
    }

    if ((parentObjectClass != null) ? (!parentObjectClass.equals(metadata.parentObjectClass))
                                    : (metadata.parentObjectClass != null)) {
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
  public String[] getGridVisibleFields() {
    return gridVisibleFields;
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
  public String getParentObjectClass() {
    return parentObjectClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.dto.CollectionMetadata#hashCode()
   */
  @Override public int hashCode() {
    int result = super.hashCode();
    result = (31 * result) + (ignoreAdornedProperties ? 1 : 0);
    result = (31 * result) + ((parentObjectClass != null) ? parentObjectClass.hashCode() : 0);
    result = (31 * result)
      + ((maintainedAdornedTargetFields != null) ? Arrays.hashCode(maintainedAdornedTargetFields) : 0);
    result = (31 * result) + ((gridVisibleFields != null) ? Arrays.hashCode(gridVisibleFields) : 0);

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isIgnoreAdornedProperties() {
    return ignoreAdornedProperties;
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
   * @param  ignoreAdornedProperties  DOCUMENT ME!
   */
  public void setIgnoreAdornedProperties(boolean ignoreAdornedProperties) {
    this.ignoreAdornedProperties = ignoreAdornedProperties;
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
   * @param  parentObjectClass  DOCUMENT ME!
   */
  public void setParentObjectClass(String parentObjectClass) {
    this.parentObjectClass = parentObjectClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.dto.CollectionMetadata#populate(org.broadleafcommerce.openadmin.dto.FieldMetadata)
   */
  @Override protected FieldMetadata populate(FieldMetadata metadata) {
    ((AdornedTargetCollectionMetadata) metadata).ignoreAdornedProperties       = ignoreAdornedProperties;
    ((AdornedTargetCollectionMetadata) metadata).parentObjectClass             = parentObjectClass;
    ((AdornedTargetCollectionMetadata) metadata).maintainedAdornedTargetFields = maintainedAdornedTargetFields;
    ((AdornedTargetCollectionMetadata) metadata).gridVisibleFields             = gridVisibleFields;

    return super.populate(metadata);
  }
} // end class AdornedTargetCollectionMetadata
