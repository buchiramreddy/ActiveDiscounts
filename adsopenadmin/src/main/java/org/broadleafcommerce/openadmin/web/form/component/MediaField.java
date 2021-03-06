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

package org.broadleafcommerce.openadmin.web.form.component;

import org.broadleafcommerce.common.media.domain.Media;

import org.broadleafcommerce.openadmin.web.form.entity.Field;


/**
 * DOCUMENT ME!
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public class MediaField extends Field {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected String height;

  /** DOCUMENT ME! */
  protected Media media;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getHeight() {
    return height;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Media getMedia() {
    return media;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  height  DOCUMENT ME!
   */
  public void setHeight(String height) {
    this.height = height;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  media  DOCUMENT ME!
   */
  public void setMedia(Media media) {
    this.media = media;
  }

} // end class MediaField
