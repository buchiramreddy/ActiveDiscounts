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

package org.broadleafcommerce.common.sandbox.dao;

import org.broadleafcommerce.common.sandbox.domain.SandBox;
import org.broadleafcommerce.common.sandbox.domain.SandBoxType;
import org.broadleafcommerce.common.site.domain.Site;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface SandBoxDao {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   site         DOCUMENT ME!
   * @param   sandBoxName  DOCUMENT ME!
   * @param   sandBoxType  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  SandBox createSandBox(Site site, String sandBoxName, SandBoxType sandBoxType);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   entity  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  SandBox persist(SandBox entity);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   id  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  SandBox retrieve(Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   site         DOCUMENT ME!
   * @param   sandboxType  DOCUMENT ME!
   * @param   sandboxName  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  SandBox retrieveNamedSandBox(Site site, SandBoxType sandboxType, String sandboxName);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   site         DOCUMENT ME!
   * @param   sandboxType  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  SandBox retrieveSandBoxByType(Site site, SandBoxType sandboxType);

} // end interface SandBoxDao
