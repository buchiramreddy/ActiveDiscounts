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

/**
 * A null safe media object.
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public class MediaDto implements Media {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String altText = "";

  private long   id;
  private String tags  = "";
  private String title = "";
  private String url   = "";

  //~ Methods ----------------------------------------------------------------------------------------------------------

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
} // end class MediaDto
