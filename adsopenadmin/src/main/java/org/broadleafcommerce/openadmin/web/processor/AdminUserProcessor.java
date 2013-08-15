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

import org.broadleafcommerce.openadmin.server.security.domain.AdminUser;
import org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService;

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
 * A Thymeleaf processor that will add the appropriate AdminUser to the model.
 *
 * @author   elbertbautista
 * @version  $Revision$, $Date$
 */
@Component("blAdminUserProcessor")
public class AdminUserProcessor extends AbstractModelVariableModifierProcessor {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final String ANONYMOUS_USER_NAME = "anonymousUser";

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private AdminSecurityService securityService;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new AdminUserProcessor object.
   */
  public AdminUserProcessor() {
    super("admin_user");
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.thymeleaf.processor.AbstractProcessor#getPrecedence()
   */
  @Override public int getPrecedence() {
    return 10000;
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
    if (securityService == null) {
      final ApplicationContext applicationContext = ((SpringWebContext) arguments.getContext()).getApplicationContext();
      securityService = (AdminSecurityService) applicationContext.getBean("blAdminSecurityService");
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
      addToModel(arguments, resultVar, user);
    }
  }
} // end class AdminUserProcessor
