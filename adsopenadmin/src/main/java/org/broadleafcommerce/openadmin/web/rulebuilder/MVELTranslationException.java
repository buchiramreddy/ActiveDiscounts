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

package org.broadleafcommerce.openadmin.web.rulebuilder;

import org.broadleafcommerce.common.exception.TranslatableException;


/**
 * DOCUMENT ME!
 *
 * @author   Elbert Bautista (elbertbautista)
 * @version  $Revision$, $Date$
 */
public class MVELTranslationException extends TranslatableException {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  public static final int SPECIFIED_FIELD_NOT_FOUND  = 100;

  /** DOCUMENT ME! */
  public static final int NO_FIELD_FOUND_IN_RULE     = 101;

  /** DOCUMENT ME! */
  public static final int INCOMPATIBLE_DATE_VALUE    = 102;

  /** DOCUMENT ME! */
  public static final int UNRECOGNIZABLE_RULE        = 103;

  /** DOCUMENT ME! */
  public static final int OPERATOR_NOT_FOUND         = 104;

  /** DOCUMENT ME! */
  public static final int INCOMPATIBLE_DECIMAL_VALUE = 105;

  /** DOCUMENT ME! */
  public static final int INCOMPATIBLE_INTEGER_VALUE = 106;

  /** DOCUMENT ME! */
  public static final int INCOMPATIBLE_RULE = 107;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new MVELTranslationException object.
   *
   * @param  code     DOCUMENT ME!
   * @param  message  DOCUMENT ME!
   */
  public MVELTranslationException(int code, String message) {
    super(code, message);
  }
} // end class MVELTranslationException
