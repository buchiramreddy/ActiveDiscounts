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

package org.broadleafcommerce.core.media.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Entity
@Table(name = "BLC_CATEGORY_MEDIA_MAP")
public class CategoryMediaMap implements Serializable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @EmbeddedId CategoryMediaMapPK categoryMediaMapPK;

  @Column(
    name     = "KEY",
    nullable = false
  )
  private String key;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public CategoryMediaMapPK getCategoryMediaMapPK() {
    return categoryMediaMapPK;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getKey() {
    return key;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  key  DOCUMENT ME!
   */
  public void setKey(String key) {
    this.key = key;
  }

  //~ Inner Classes ----------------------------------------------------------------------------------------------------

  public static class CategoryMediaMapPK implements Serializable {
    //~ Static fields/initializers -------------------------------------------------------------------------------------

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    //~ Instance fields ------------------------------------------------------------------------------------------------

    @Column(
      name     = "CATEGORY_ID",
      nullable = false
    )
    private Long categoryId;

    @Column(
      name     = "MEDIA_ID",
      nullable = false
    )
    private Long mediaId;

    //~ Methods --------------------------------------------------------------------------------------------------------

    @Override public boolean equals(Object obj) {
      if (obj == null) {
        return false;
      } else if (!(obj instanceof CategoryMediaMapPK)) {
        return false;
      }

      return categoryId.equals(((CategoryMediaMapPK) obj).getCategoryId())
        && mediaId.equals(((CategoryMediaMapPK) obj).getMediaId());
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    public Long getCategoryId() {
      return categoryId;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    public Long getMediaId() {
      return mediaId;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public int hashCode() {
      return categoryId.hashCode() + mediaId.hashCode();
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    public void setCategoryId(Long categoryId) {
      this.categoryId = categoryId;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    public void setMediaId(Long mediaId) {
      this.mediaId = mediaId;
    }
  } // end class CategoryMediaMapPK
} // end class CategoryMediaMap
