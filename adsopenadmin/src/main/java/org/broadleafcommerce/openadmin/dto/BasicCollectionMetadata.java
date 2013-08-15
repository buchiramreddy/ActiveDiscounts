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

import org.broadleafcommerce.common.presentation.client.AddMethodType;

import org.broadleafcommerce.openadmin.dto.visitor.MetadataVisitor;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class BasicCollectionMetadata extends CollectionMetadata {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private AddMethodType addMethodType;

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
    BasicCollectionMetadata basicCollectionMetadata = new BasicCollectionMetadata();

    return populate(basicCollectionMetadata);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.dto.CollectionMetadata#equals(java.lang.Object)
   */
  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof BasicCollectionMetadata)) {
      return false;
    }

    if (!super.equals(o)) {
      return false;
    }

    BasicCollectionMetadata that = (BasicCollectionMetadata) o;

    if (addMethodType != that.addMethodType) {
      return false;
    }

    return true;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public AddMethodType getAddMethodType() {
    return addMethodType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.dto.CollectionMetadata#hashCode()
   */
  @Override public int hashCode() {
    int result = super.hashCode();
    result = (31 * result) + ((addMethodType != null) ? addMethodType.hashCode() : 0);

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  addMethodType  DOCUMENT ME!
   */
  public void setAddMethodType(AddMethodType addMethodType) {
    this.addMethodType = addMethodType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.dto.CollectionMetadata#populate(org.broadleafcommerce.openadmin.dto.FieldMetadata)
   */
  @Override protected FieldMetadata populate(FieldMetadata metadata) {
    ((BasicCollectionMetadata) metadata).addMethodType = addMethodType;

    return super.populate(metadata);
  }
} // end class BasicCollectionMetadata
