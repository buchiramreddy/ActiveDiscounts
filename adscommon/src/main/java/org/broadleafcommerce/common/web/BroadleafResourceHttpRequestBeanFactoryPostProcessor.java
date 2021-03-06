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

package org.broadleafcommerce.common.web;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class BroadleafResourceHttpRequestBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.beans.factory.config.BeanFactoryPostProcessor#postProcessBeanFactory(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
   */
  @Override public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
    String[] names = factory.getBeanNamesForType(ResourceHttpRequestHandler.class);

    for (String name : names) {
      BeanDefinition bd = factory.getBeanDefinition(name);
      bd.setBeanClassName(BroadleafGWTModuleURLMappingResourceHttpRequestHandler.class.getName());
    }
  }
}
