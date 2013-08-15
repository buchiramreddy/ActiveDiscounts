/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.openadmin.web.processor;

import org.broadleafcommerce.common.web.dialect.AbstractModelVariableModifierProcessor;

import org.broadleafcommerce.openadmin.server.security.domain.AdminMenu;
import org.broadleafcommerce.openadmin.server.security.domain.AdminUser;
import org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService;
import org.broadleafcommerce.openadmin.server.security.service.navigation.AdminNavigationService;

import org.springframework.context.ApplicationContext;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.stereotype.Component;

import org.thymeleaf.Arguments;

import org.thymeleaf.dom.Element;

import org.thymeleaf.spring3.context.SpringWebContext;


/**
 * A Thymeleaf processor that will add the appropriate AdminModules to the model. It does this by iterating through the
 * permissions specified in the SecurityContexts AdminUser object and adding the appropriate section to the model
 * attribute specified by resultVar
 *
 * <p>This is useful in constructing the left navigation menu for the admin console.</p>
 *
 * @author   elbertbautista
 * @version  $Revision$, $Date$
 */
@Component("blAdminModuleProcessor")
public class AdminModuleProcessor extends AbstractModelVariableModifierProcessor {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final String ANONYMOUS_USER_NAME = "anonymousUser";

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private AdminNavigationService adminNavigationService;
  private AdminSecurityService   securityService;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Sets the name of this processor to be used in Thymeleaf template.
   */
  public AdminModuleProcessor() {
    super("admin_module");
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.thymeleaf.processor.AbstractProcessor#getPrecedence()
   */
  @Override public int getPrecedence() {
    return 10001;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected AdminUser getPersistentAdminUser() {
    SecurityContext ctx = SecurityContextHolder.getContext();

    if (ctx != null) {
      Authentication auth = ctx.getAuthentication();

      if ((auth != null) && !auth.getName().equals(ANONYMOUS_USER_NAME)) {
        UserDetails temp = (UserDetails) auth.getPrincipal();

        return securityService.readAdminUserByUserName(temp.getUsername());
      }
    }

    return null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  arguments  DOCUMENT ME!
   */
  protected void initServices(Arguments arguments) {
    if ((adminNavigationService == null) || (securityService == null)) {
      final ApplicationContext applicationContext = ((SpringWebContext) arguments.getContext()).getApplicationContext();
      adminNavigationService = (AdminNavigationService) applicationContext.getBean("blAdminNavigationService");
      securityService        = (AdminSecurityService) applicationContext.getBean("blAdminSecurityService");
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.web.dialect.AbstractModelVariableModifierProcessor#modifyModelAttributes(org.thymeleaf.Arguments,
   *       org.thymeleaf.dom.Element)
   */
  @Override protected void modifyModelAttributes(Arguments arguments, Element element) {
    String resultVar = element.getAttributeValue("resultVar");
    initServices(arguments);

    AdminUser user = getPersistentAdminUser();

    if (user != null) {
      AdminMenu menu = adminNavigationService.buildMenu(user);
      addToModel(arguments, resultVar, menu);
    }

  }
} // end class AdminModuleProcessor
