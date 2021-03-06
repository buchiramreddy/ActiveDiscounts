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

package org.broadleafcommerce.core.web.processor.extension;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import org.thymeleaf.Arguments;

import org.thymeleaf.dom.Element;


/**
 * DOCUMENT ME!
 *
 * @author   Jerry Ocanas (jocanas)
 * @version  $Revision$, $Date$
 */
@Service("blHeadProcessorExtensionManager")
public class HeadProcessorExtensionManager implements HeadProcessorExtensionListener {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected List<HeadProcessorExtensionListener> listeners;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<HeadProcessorExtensionListener> getListeners() {
    return listeners;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.processor.extension.HeadProcessorExtensionListener#processAttributeValues(org.thymeleaf.Arguments,
   *       org.thymeleaf.dom.Element)
   */
  @Override public void processAttributeValues(Arguments arguments, Element element) {
    if (listeners == null) {
      listeners = new ArrayList<HeadProcessorExtensionListener>();
    }

    for (HeadProcessorExtensionListener listener : listeners) {
      listener.processAttributeValues(arguments, element);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  listeners  DOCUMENT ME!
   */
  public void setListeners(List<HeadProcessorExtensionListener> listeners) {
    this.listeners = listeners;
  }
} // end class HeadProcessorExtensionManager
