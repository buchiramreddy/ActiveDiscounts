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

package org.broadleafcommerce.common.web.extensibility;

import org.springframework.web.context.ContextLoaderListener;


/**
 * Bootstrap listener to start up BroadleafCommerce's rootId {@link MergeWebApplicationContext}. Simply delegates to
 * {@link org.broadleafcommerce.common.web.extensibility.MergeContextLoader}.
 *
 * <p>This listener should be registered after {@link org.springframework.web.util.Log4jConfigListener} in <code>
 * web.xml</code>, if the latter is used.</p>
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class MergeContextLoaderListener extends ContextLoaderListener {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Create the ContextLoader to use. Can be overridden in subclasses.
   *
   * @return  the new ContextLoader
   */
  @Override protected MergeContextLoader createContextLoader() {
    return new MergeContextLoader();
  }

}
