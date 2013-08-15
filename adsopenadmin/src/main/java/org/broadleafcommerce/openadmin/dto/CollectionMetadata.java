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


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public abstract class CollectionMetadata extends FieldMetadata {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String   collectionCeilingEntity;
  private String[] customCriteria;
  private boolean  mutable = true;

  private PersistencePerspective persistencePerspective;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.dto.FieldMetadata#equals(java.lang.Object)
   */
  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof CollectionMetadata)) {
      return false;
    }

    CollectionMetadata metadata = (CollectionMetadata) o;

    if (mutable != metadata.mutable) {
      return false;
    }

    if ((collectionCeilingEntity != null) ? (!collectionCeilingEntity.equals(metadata.collectionCeilingEntity))
                                          : (metadata.collectionCeilingEntity != null)) {
      return false;
    }

    if (!Arrays.equals(customCriteria, metadata.customCriteria)) {
      return false;
    }

    if ((persistencePerspective != null) ? (!persistencePerspective.equals(metadata.persistencePerspective))
                                         : (metadata.persistencePerspective != null)) {
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
  public String getCollectionCeilingEntity() {
    return collectionCeilingEntity;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String[] getCustomCriteria() {
    return customCriteria;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PersistencePerspective getPersistencePerspective() {
    return persistencePerspective;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.dto.FieldMetadata#hashCode()
   */
  @Override public int hashCode() {
    int result = (persistencePerspective != null) ? persistencePerspective.hashCode() : 0;
    result = (31 * result) + ((collectionCeilingEntity != null) ? collectionCeilingEntity.hashCode() : 0);
    result = (31 * result) + (mutable ? 1 : 0);
    result = (31 * result) + ((customCriteria != null) ? Arrays.hashCode(customCriteria) : 0);

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isMutable() {
    return mutable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  collectionCeilingEntity  DOCUMENT ME!
   */
  public void setCollectionCeilingEntity(String collectionCeilingEntity) {
    this.collectionCeilingEntity = collectionCeilingEntity;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  customCriteria  DOCUMENT ME!
   */
  public void setCustomCriteria(String[] customCriteria) {
    this.customCriteria = customCriteria;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  mutable  DOCUMENT ME!
   */
  public void setMutable(boolean mutable) {
    this.mutable = mutable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  persistencePerspective  DOCUMENT ME!
   */
  public void setPersistencePerspective(PersistencePerspective persistencePerspective) {
    this.persistencePerspective = persistencePerspective;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.dto.FieldMetadata#populate(org.broadleafcommerce.openadmin.dto.FieldMetadata)
   */
  @Override protected FieldMetadata populate(FieldMetadata metadata) {
    super.populate(metadata);
    ((CollectionMetadata) metadata).setPersistencePerspective(persistencePerspective.clonePersistencePerspective());
    ((CollectionMetadata) metadata).setCollectionCeilingEntity(collectionCeilingEntity);
    ((CollectionMetadata) metadata).setMutable(mutable);
    ((CollectionMetadata) metadata).setCustomCriteria(customCriteria);
    ((CollectionMetadata) metadata).setTab(getTab());
    ((CollectionMetadata) metadata).setTabOrder(getTabOrder());

    return metadata;
  }
} // end class CollectionMetadata
