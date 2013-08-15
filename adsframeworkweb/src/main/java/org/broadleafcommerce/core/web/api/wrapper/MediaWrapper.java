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


package org.broadleafcommerce.core.web.api.wrapper;

import javax.servlet.http.HttpServletRequest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.broadleafcommerce.common.media.domain.Media;


/**
 * JAXB wrapper class for Media.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "media")
public class MediaWrapper extends BaseWrapper implements APIWrapper<Media> {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** This allows us to control whether the URL should / can be overwritten, for example by the static asset service. */
  @XmlTransient protected boolean allowOverrideUrl = true;

  /** DOCUMENT ME! */
  @XmlElement protected String altText;

  /** DOCUMENT ME! */
  @XmlElement protected Long id;

  /** DOCUMENT ME! */
  @XmlElement protected String tags;

  /** DOCUMENT ME! */
  @XmlElement protected String title;

  /** DOCUMENT ME! */
  @XmlElement protected String url;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isAllowOverrideUrl() {
    return allowOverrideUrl;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  allow  DOCUMENT ME!
   */
  public void setAllowOverrideUrl(boolean allow) {
    this.allowOverrideUrl = allow;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Call this only if allowOverrideUrl is true, and only AFTER you call wrap.
   *
   * @param  url  DOCUMENT ME!
   */
  public void setUrl(String url) {
    if (allowOverrideUrl) {
      this.url = url;
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapDetails(org.broadleafcommerce.common.media.domain.Media,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapDetails(Media media, HttpServletRequest request) {
    this.id      = media.getId();
    this.title   = media.getTitle();
    this.altText = media.getAltText();
    this.tags    = media.getTags();
    this.url     = media.getUrl();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapSummary(org.broadleafcommerce.common.media.domain.Media,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapSummary(Media media, HttpServletRequest request) {
    wrapDetails(media, request);
  }
} // end class MediaWrapper
