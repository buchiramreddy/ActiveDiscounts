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

package org.broadleafcommerce.common.extensibility.cache.ehcache;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.broadleafcommerce.common.extensibility.context.ResourceInputStream;
import org.broadleafcommerce.common.extensibility.context.merge.MergeXmlConfigResource;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;

import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import org.springframework.core.io.Resource;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class MergeEhCacheManagerFactoryBean extends EhCacheManagerFactoryBean implements ApplicationContextAware {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected List<Resource> configLocations;

  /** DOCUMENT ME! */
  @javax.annotation.Resource(name = "blMergedCacheConfigLocations")
  protected Set<String> mergedCacheConfigLocations;

  private ApplicationContext applicationContext;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @throws  FatalBeanException  DOCUMENT ME!
   */
  @PostConstruct public void configureMergedItems() {
    List<Resource> temp = new ArrayList<Resource>();

    if ((mergedCacheConfigLocations != null) && !mergedCacheConfigLocations.isEmpty()) {
      for (String location : mergedCacheConfigLocations) {
        temp.add(applicationContext.getResource(location));
      }
    }

    if ((configLocations != null) && !configLocations.isEmpty()) {
      for (Resource resource : configLocations) {
        temp.add(resource);
      }
    }

    try {
      MergeXmlConfigResource merge   = new MergeXmlConfigResource();
      ResourceInputStream[]  sources = new ResourceInputStream[temp.size()];
      int                    j       = 0;

      for (Resource resource : temp) {
        sources[j] = new ResourceInputStream(resource.getInputStream(), resource.getURL().toString());
        j++;
      }

      setConfigLocation(merge.getMergedConfigResource(sources));
    } catch (Exception e) {
      throw new FatalBeanException("Unable to merge cache locations", e);
    }
  } // end method configureMergedItems

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
   */
  @Override public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   configLocations  DOCUMENT ME!
   *
   * @throws  BeansException  DOCUMENT ME!
   */
  public void setConfigLocations(List<Resource> configLocations) throws BeansException {
    this.configLocations = configLocations;
  }
} // end class MergeEhCacheManagerFactoryBean
