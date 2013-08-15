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


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class Property implements Serializable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected String        displayValue;

  /** DOCUMENT ME! */
  protected boolean       isAdvancedCollection = false;

  /** DOCUMENT ME! */
  protected Boolean       isDirty  = false;

  /** DOCUMENT ME! */
  protected FieldMetadata metadata = new BasicFieldMetadata();

  /** DOCUMENT ME! */
  protected String name;

  /** DOCUMENT ME! */
  protected String rawValue;

  /** DOCUMENT ME! */
  protected String unHtmlEncodedValue;

  /** DOCUMENT ME! */
  protected String value;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    Property other = (Property) obj;

    if ((metadata == null) || (metadata instanceof CollectionMetadata)
          || (((BasicFieldMetadata) metadata).getMergedPropertyType() == null)) {
      if ((other.metadata != null) && (other.metadata instanceof BasicFieldMetadata)
            && (((BasicFieldMetadata) other.metadata).getMergedPropertyType() != null)) {
        return false;
      }
    } else if ((metadata instanceof BasicFieldMetadata) && (other.metadata instanceof BasicFieldMetadata)
          && !((BasicFieldMetadata) metadata).getMergedPropertyType().equals(
            ((BasicFieldMetadata) other.metadata).getMergedPropertyType())) {
      return false;
    }

    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
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
  public String getDisplayValue() {
    return displayValue;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getIsDirty() {
    return isDirty;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public FieldMetadata getMetadata() {
    return metadata;
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
  public String getRawValue() {
    return rawValue;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getUnHtmlEncodedValue() {
    return unHtmlEncodedValue;
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
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result)
      + (((metadata == null) || (metadata instanceof CollectionMetadata)
          || (((BasicFieldMetadata) metadata).getMergedPropertyType() == null))
        ? 0 : ((BasicFieldMetadata) metadata).getMergedPropertyType().hashCode());
    result = (prime * result) + ((name == null) ? 0 : name.hashCode());

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isAdvancedCollection() {
    return isAdvancedCollection;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  advancedCollection  DOCUMENT ME!
   */
  public void setAdvancedCollection(boolean advancedCollection) {
    isAdvancedCollection = advancedCollection;
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
   * @param  isDirty  DOCUMENT ME!
   */
  public void setIsDirty(Boolean isDirty) {
    this.isDirty = isDirty;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  metadata  DOCUMENT ME!
   */
  public void setMetadata(FieldMetadata metadata) {
    this.metadata = metadata;
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
   * @param  rawValue  DOCUMENT ME!
   */
  public void setRawValue(String rawValue) {
    this.rawValue = rawValue;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  unHtmlEncodedValue  DOCUMENT ME!
   */
  public void setUnHtmlEncodedValue(String unHtmlEncodedValue) {
    this.unHtmlEncodedValue = unHtmlEncodedValue;
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
   * @see  java.lang.Object#toString()
   */
  @Override public String toString() {
    return getName() + ": " + getValue();
  }

} // end class Property
