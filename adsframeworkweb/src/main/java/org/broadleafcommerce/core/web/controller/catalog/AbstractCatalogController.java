/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.core.web.controller.catalog;

import javax.annotation.Resource;

import org.broadleafcommerce.common.web.controller.BroadleafAbstractController;

import org.broadleafcommerce.core.catalog.service.CatalogService;


/**
 * An abstract controller that provides convenience methods and resource declarations for its children. Operations that
 * are shared between controllers that deal with customer accounts belong here
 *
 * @author   apazzolini
 * @version  $Revision$, $Date$
 */
public abstract class AbstractCatalogController extends BroadleafAbstractController {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blCatalogService")
  protected CatalogService catalogService;


}
