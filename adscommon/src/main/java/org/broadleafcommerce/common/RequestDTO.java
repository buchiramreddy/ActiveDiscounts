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

package org.broadleafcommerce.common;


/**
 * Created by bpolster.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface RequestDTO {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Returns the URL and parameters.
   *
   * @return  Returns the URL and parameters.
   */
  String getFullUrLWithQueryString();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the request not including the protocol, domain, or query string.
   *
   * @return  returns the request not including the protocol, domain, or query string
   */
  String getRequestURI();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * True if this request came in through HTTPS.
   *
   * @return  true if this request came in through HTTPS
   */
  Boolean isSecure();
} // end interface RequestDTO
