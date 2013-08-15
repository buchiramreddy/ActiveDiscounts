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

package org.broadleafcommerce.core.search.domain;

/**
 * DOCUMENT ME!
 *
 * @deprecated  Replaced in functionality by {@link org.broadleafcommerce.core.search.redirect.domain.SearchRedirect}
 * @author      $author$
 * @version     $Revision$, $Date$
 */
@Deprecated public interface SearchIntercept {
  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getTerm();

  /**
   * DOCUMENT ME!
   *
   * @param  term  DOCUMENT ME!
   */
  void setTerm(String term);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getRedirect();

  /**
   * DOCUMENT ME!
   *
   * @param  redirect  DOCUMENT ME!
   */
  void setRedirect(String redirect);

} // end interface SearchIntercept
