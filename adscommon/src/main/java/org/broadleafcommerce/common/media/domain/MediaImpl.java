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

package org.broadleafcommerce.common.media.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_MEDIA")
public class MediaImpl implements Media {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "MediaImpl_Media_Alt_Text",
    order        = 3,
    prominent    = true
  )
  @Column(name = "ALT_TEXT")
  protected String altText;

  /** DOCUMENT ME! */
  @Column(name = "MEDIA_ID")
  @GeneratedValue(generator = "MediaId")
  @GenericGenerator(
    name       = "MediaId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "MediaImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.common.media.domain.MediaImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(friendlyName = "MediaImpl_Media_Tags")
  @Column(name = "TAGS")
  protected String tags;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "MediaImpl_Media_Title",
    order        = 2,
    prominent    = true
  )
  @Column(name = "TITLE")
  @Index(
    name        = "MEDIA_TITLE_INDEX",
    columnNames = { "TITLE" }
  )
  protected String title;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "MediaImpl_Media_Url",
    order        = 1,
    prominent    = true,
    fieldType    = SupportedFieldType.ASSET_LOOKUP
  )
  @Column(
    name     = "URL",
    nullable = false
  )
  @Index(
    name        = "MEDIA_URL_INDEX",
    columnNames = { "URL" }
  )
  protected String url;

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

    MediaImpl other = (MediaImpl) obj;

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (title == null) {
      if (other.title != null) {
        return false;
      }
    } else if (!title.equals(other.title)) {
      return false;
    }

    if (altText == null) {
      if (other.altText != null) {
        return false;
      }
    } else if (!altText.equals(other.altText)) {
      return false;
    }

    if (tags == null) {
      if (other.tags != null) {
        return false;
      }
    } else if (!tags.equals(other.tags)) {
      return false;
    }

    if (url == null) {
      if (other.url != null) {
        return false;
      }
    } else if (!url.equals(other.url)) {
      return false;
    }

    return true;
  } // end method equals

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.media.domain.Media#getAltText()
   */
  @Override public String getAltText() {
    return altText;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.media.domain.Media#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.media.domain.Media#getTags()
   */
  @Override public String getTags() {
    return tags;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.media.domain.Media#getTitle()
   */
  @Override public String getTitle() {
    return title;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.media.domain.Media#getUrl()
   */
  @Override public String getUrl() {
    return url;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((title == null) ? 0 : title.hashCode());
    result = (prime * result) + ((altText == null) ? 0 : altText.hashCode());
    result = (prime * result) + ((tags == null) ? 0 : tags.hashCode());
    result = (prime * result) + ((url == null) ? 0 : url.hashCode());

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.media.domain.Media#setAltText(java.lang.String)
   */
  @Override public void setAltText(String altText) {
    this.altText = altText;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.media.domain.Media#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.media.domain.Media#setTags(java.lang.String)
   */
  @Override public void setTags(String tags) {
    this.tags = tags;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.media.domain.Media#setTitle(java.lang.String)
   */
  @Override public void setTitle(String title) {
    this.title = title;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.media.domain.Media#setUrl(java.lang.String)
   */
  @Override public void setUrl(String url) {
    this.url = url;
  }

} // end class MediaImpl
