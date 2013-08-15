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

package org.broadleafcommerce.openadmin.web.service;

import org.broadleafcommerce.openadmin.web.form.entity.EntityForm;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface FormBuilderExtensionManager {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  ef  DOCUMENT ME!
   */
  void addFormExtensions(EntityForm ef);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   ef  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  boolean canHandle(EntityForm ef);

}
