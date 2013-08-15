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

package org.broadleafcommerce.common.extensibility.jpa.convert.inheritance;

import javax.persistence.DiscriminatorType;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class SingleTableInheritanceInfo {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected String            className;

  /** DOCUMENT ME! */
  protected int               discriminatorLength;

  /** DOCUMENT ME! */
  protected String            discriminatorName;

  /** DOCUMENT ME! */
  protected DiscriminatorType discriminatorType;

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

    SingleTableInheritanceInfo other = (SingleTableInheritanceInfo) obj;

    if (className == null) {
      if (other.className != null) {
        return false;
      }
    } else if (!className.equals(other.className)) {
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
  public String getClassName() {
    return className;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int getDiscriminatorLength() {
    return discriminatorLength;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getDiscriminatorName() {
    return discriminatorName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public DiscriminatorType getDiscriminatorType() {
    return discriminatorType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((className == null) ? 0 : className.hashCode());

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  className  DOCUMENT ME!
   */
  public void setClassName(String className) {
    this.className = className;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  discriminatorLength  DOCUMENT ME!
   */
  public void setDiscriminatorLength(int discriminatorLength) {
    this.discriminatorLength = discriminatorLength;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  discriminatorName  DOCUMENT ME!
   */
  public void setDiscriminatorName(String discriminatorName) {
    this.discriminatorName = discriminatorName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  discriminatorType  DOCUMENT ME!
   */
  public void setDiscriminatorType(DiscriminatorType discriminatorType) {
    this.discriminatorType = discriminatorType;
  }

} // end class SingleTableInheritanceInfo
