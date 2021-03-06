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

package org.broadleafcommerce.common.extensibility.context.merge;

import org.springframework.context.ApplicationContext;

import org.springframework.stereotype.Component;


/**
 * This {@link org.broadleafcommerce.common.extensibility.context.merge.MergeBeanStatusProvider} can be utilized by
 * modules that are trying to provide functionality that is only required when MultiTenant is loaded.
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
@Component("blMultiTenantMergeBeanStatusProvider")
public class MultiTenantMergeBeanStatusProvider implements MergeBeanStatusProvider {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.extensibility.context.merge.MergeBeanStatusProvider#isProcessingEnabled(java.lang.Object,
   *       java.lang.String, org.springframework.context.ApplicationContext)
   */
  @Override public boolean isProcessingEnabled(Object bean, String beanName, ApplicationContext appCtx) {
    return appCtx.containsBean("blMultiTenantClassTransformer");
  }

}
