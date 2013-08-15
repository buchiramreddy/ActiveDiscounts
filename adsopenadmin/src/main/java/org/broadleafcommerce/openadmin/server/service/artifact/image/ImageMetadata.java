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

package org.broadleafcommerce.openadmin.server.service.artifact.image;

/**
 * A bean designed to hold general information about an image.
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class ImageMetadata {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private int height;

  private int width;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * The height.
   *
   * @return  the height
   */
  public int getHeight() {
    return height;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The width.
   *
   * @return  the width
   */
  public int getWidth() {
    return width;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  height  the height to set
   */
  public void setHeight(int height) {
    this.height = height;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  width  the width to set
   */
  public void setWidth(int width) {
    this.width = width;
  }

} // end class ImageMetadata
