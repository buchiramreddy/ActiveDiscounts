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

package org.broadleafcommerce.cms.common;


/**
 * Exception thrown by the {@link org.broadleafcommerce.cms.file.service.StaticAssetService} indicating that the asset
 * requested does not exist.
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public class AssetNotFoundException extends RuntimeException {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = -6349160176427682630L;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new AssetNotFoundException object.
   */
  public AssetNotFoundException() {
    // do nothing
  }

  /**
   * Creates a new AssetNotFoundException object.
   *
   * @param  cause  DOCUMENT ME!
   */
  public AssetNotFoundException(Throwable cause) {
    super(cause);
  }

  /**
   * Creates a new AssetNotFoundException object.
   *
   * @param  message  DOCUMENT ME!
   */
  public AssetNotFoundException(String message) {
    super(message);
  }

  /**
   * Creates a new AssetNotFoundException object.
   *
   * @param  message  DOCUMENT ME!
   * @param  cause    DOCUMENT ME!
   */
  public AssetNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

} // end class AssetNotFoundException
