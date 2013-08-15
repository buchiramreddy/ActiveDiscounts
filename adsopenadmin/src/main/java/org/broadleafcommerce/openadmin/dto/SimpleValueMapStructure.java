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
public class SimpleValueMapStructure extends MapStructure {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String valuePropertyFriendlyName;

  private String valuePropertyName;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new SimpleValueMapStructure object.
   */
  public SimpleValueMapStructure() {
    super();
  }

  /**
   *
   * Creates a new SimpleValueMapStructure object.
   *
   * @param  keyClassName               DOCUMENT ME!
   * @param  keyPropertyName            DOCUMENT ME!
   * @param  keyPropertyFriendlyName    DOCUMENT ME!
   * @param  valueClassName             DOCUMENT ME!
   * @param  valuePropertyName          DOCUMENT ME!
   * @param  valuePropertyFriendlyName  DOCUMENT ME!
   * @param  mapProperty                DOCUMENT ME!
   */
  public SimpleValueMapStructure(String keyClassName, String keyPropertyName, String keyPropertyFriendlyName,
    String valueClassName, String valuePropertyName, String valuePropertyFriendlyName, String mapProperty) {
    super(keyClassName, keyPropertyName, keyPropertyFriendlyName, valueClassName, mapProperty, false);
    this.valuePropertyFriendlyName = valuePropertyFriendlyName;
    this.valuePropertyName         = valuePropertyName;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.dto.MapStructure#accept(org.broadleafcommerce.openadmin.dto.visitor.PersistencePerspectiveItemVisitor)
   */
  @Override public void accept(PersistencePerspectiveItemVisitor visitor) {
    visitor.visit(this);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.dto.MapStructure#clonePersistencePerspectiveItem()
   */
  @Override public PersistencePerspectiveItem clonePersistencePerspectiveItem() {
    SimpleValueMapStructure mapStructure = new SimpleValueMapStructure();
    mapStructure.setKeyClassName(getKeyClassName());
    mapStructure.setKeyPropertyName(getKeyPropertyName());
    mapStructure.setValuePropertyFriendlyName(getKeyPropertyFriendlyName());
    mapStructure.setValueClassName(getValueClassName());
    mapStructure.setMapProperty(getMapProperty());
    mapStructure.setDeleteValueEntity(getDeleteValueEntity());
    mapStructure.valuePropertyName         = valuePropertyName;
    mapStructure.valuePropertyFriendlyName = valuePropertyFriendlyName;

    return mapStructure;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.dto.MapStructure#equals(java.lang.Object)
   */
  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof SimpleValueMapStructure)) {
      return false;
    }

    if (!super.equals(o)) {
      return false;
    }

    SimpleValueMapStructure that = (SimpleValueMapStructure) o;

    if ((valuePropertyFriendlyName != null) ? (!valuePropertyFriendlyName.equals(that.valuePropertyFriendlyName))
                                            : (that.valuePropertyFriendlyName != null)) {
      return false;
    }

    if ((valuePropertyName != null) ? (!valuePropertyName.equals(that.valuePropertyName))
                                    : (that.valuePropertyName != null)) {
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
  public String getValuePropertyFriendlyName() {
    return valuePropertyFriendlyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getValuePropertyName() {
    return valuePropertyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.dto.MapStructure#hashCode()
   */
  @Override public int hashCode() {
    int result = super.hashCode();
    result = (31 * result) + ((valuePropertyName != null) ? valuePropertyName.hashCode() : 0);
    result = (31 * result) + ((valuePropertyFriendlyName != null) ? valuePropertyFriendlyName.hashCode() : 0);

    return result;
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
   * @param  valuePropertyName  DOCUMENT ME!
   */
  public void setValuePropertyName(String valuePropertyName) {
    this.valuePropertyName = valuePropertyName;
  }
} // end class SimpleValueMapStructure
