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

package org.broadleafcommerce.openadmin.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.openadmin.web.form.TranslationForm;

import org.springframework.stereotype.Component;


/**
 * DOCUMENT ME!
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
@Component("blAdminTranslationControllerExtensionManager")
public class AdminTranslationControllerExtensionManager implements AdminTranslationControllerExtensionListener {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blAdminTranslationControllerExtensionListeners")
  protected List<AdminTranslationControllerExtensionListener> listeners =
    new ArrayList<AdminTranslationControllerExtensionListener>();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.web.controller.AdminTranslationControllerExtensionListener#applyTransformation(org.broadleafcommerce.openadmin.web.form.TranslationForm)
   */
  @Override public boolean applyTransformation(TranslationForm form) {
    boolean applied = false;

    for (AdminTranslationControllerExtensionListener listener : listeners) {
      if (listener.applyTransformation(form)) {
        applied = true;

        break;
      }
    }

    return applied;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<AdminTranslationControllerExtensionListener> getListeners() {
    return listeners;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  listeners  DOCUMENT ME!
   */
  public void setListeners(List<AdminTranslationControllerExtensionListener> listeners) {
    this.listeners = listeners;
  }

} // end class AdminTranslationControllerExtensionManager
