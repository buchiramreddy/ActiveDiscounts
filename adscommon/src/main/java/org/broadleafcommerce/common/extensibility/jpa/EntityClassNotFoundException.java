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

package org.broadleafcommerce.common.extensibility.jpa;

/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class EntityClassNotFoundException extends RuntimeException {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new EntityClassNotFoundException object.
   */
  public EntityClassNotFoundException() {
    super();
  }

  /**
   * Creates a new EntityClassNotFoundException object.
   *
   * @param  arg0  DOCUMENT ME!
   */
  public EntityClassNotFoundException(String arg0) {
    super(arg0);
  }

  /**
   * Creates a new EntityClassNotFoundException object.
   *
   * @param  arg0  DOCUMENT ME!
   */
  public EntityClassNotFoundException(Throwable arg0) {
    super(arg0);
  }

  /**
   * Creates a new EntityClassNotFoundException object.
   *
   * @param  arg0  DOCUMENT ME!
   * @param  arg1  DOCUMENT ME!
   */
  public EntityClassNotFoundException(String arg0, Throwable arg1) {
    super(arg0, arg1);
  }

} // end class EntityClassNotFoundException
