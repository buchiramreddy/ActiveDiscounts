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

package org.broadleafcommerce.profile.core.service.handler;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.email.service.EmailService;
import org.broadleafcommerce.common.email.service.info.EmailInfo;
import org.broadleafcommerce.common.security.util.PasswordReset;

import org.broadleafcommerce.profile.core.domain.Customer;


/**
 * DOCUMENT ME!
 *
 * @deprecated  - This email approach is no longer recommended. See documentation for BroadleafCommerce
 * @author      bpolster
 * @version     $Revision$, $Date$
 */
public class EmailNotificationPasswordUpdatedHandler implements PasswordUpdatedHandler {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log   LOG                                 = LogFactory.getLog(
      EmailNotificationPasswordUpdatedHandler.class);

  /** DOCUMENT ME! */
  public static final String CUSTOMER_PASSWORD_TEMPLATE_VARIABLE = "customerPasswordTemplateVariable";

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blEmailService")
  protected EmailService emailService;

  /** DOCUMENT ME! */
  protected Locale       passwordResetEmailDefaultLocale = Locale.US;

  /** DOCUMENT ME! */
  protected String       passwordResetEmailFromAddress;

  /** DOCUMENT ME! */
  protected Map<Locale, String> passwordResetEmailSubject  = new HashMap<Locale, String>();

  /** DOCUMENT ME! */
  protected Map<Locale, String> passwordResetEmailTemplate = new HashMap<Locale, String>();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Locale getPasswordResetEmailDefaultLocale() {
    return passwordResetEmailDefaultLocale;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getPasswordResetEmailFromAddress() {
    return passwordResetEmailFromAddress;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<Locale, String> getPasswordResetEmailSubject() {
    return passwordResetEmailSubject;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<Locale, String> getPasswordResetEmailTemplate() {
    return passwordResetEmailTemplate;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.handler.PasswordUpdatedHandler#passwordChanged(org.broadleafcommerce.common.security.util.PasswordReset,
   *       org.broadleafcommerce.profile.core.domain.Customer, java.lang.String)
   */
  @Override
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public void passwordChanged(PasswordReset passwordReset, Customer customer, String newPassword) {
    Locale                                            localeToUse = null;
    org.broadleafcommerce.common.locale.domain.Locale blLocale    = customer.getCustomerLocale();

    if (blLocale != null) {
      String[] splitLocale = blLocale.getLocaleCode().split("_");

      if (splitLocale.length > 1) {
        localeToUse = new Locale(splitLocale[0], splitLocale[1]);
      } else {
        localeToUse = new Locale(splitLocale[0]);
      }
    }

    if (localeToUse == null) {
      localeToUse = getPasswordResetEmailDefaultLocale();
    }

    String subject = getPasswordResetEmailSubject().get(localeToUse);

    if (subject == null) {
      LOG.warn("Unable to find an email subject for customer locale: " + localeToUse.toString()
        + ". Using default locale instead.");
      subject = getPasswordResetEmailSubject().get(getPasswordResetEmailDefaultLocale());
    }

    String template = getPasswordResetEmailTemplate().get(localeToUse);

    if (template == null) {
      LOG.warn("Unable to find an email template for customer locale: " + localeToUse.toString()
        + ". Using default locale instead.");
      template = getPasswordResetEmailTemplate().get(getPasswordResetEmailDefaultLocale());
    }

    EmailInfo info = new EmailInfo();
    info.setFromAddress(getPasswordResetEmailFromAddress());
    info.setSubject(subject);
    info.setEmailTemplate(template);
    info.setSendEmailReliableAsync(String.valueOf(passwordReset.isSendResetEmailReliableAsync()));

    HashMap vars = constructPasswordChangeEmailTemplateVariables(customer, newPassword);

    emailService.sendTemplateEmail(passwordReset.getEmail(), info, vars);
  } // end method passwordChanged

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  passwordResetEmailDefaultLocale  DOCUMENT ME!
   */
  public void setPasswordResetEmailDefaultLocale(Locale passwordResetEmailDefaultLocale) {
    this.passwordResetEmailDefaultLocale = passwordResetEmailDefaultLocale;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  passwordResetEmailFromAddress  DOCUMENT ME!
   */
  public void setPasswordResetEmailFromAddress(String passwordResetEmailFromAddress) {
    this.passwordResetEmailFromAddress = passwordResetEmailFromAddress;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  passwordResetEmailSubject  DOCUMENT ME!
   */
  public void setPasswordResetEmailSubject(Map<Locale, String> passwordResetEmailSubject) {
    this.passwordResetEmailSubject = passwordResetEmailSubject;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  passwordResetEmailTemplate  DOCUMENT ME!
   */
  public void setPasswordResetEmailTemplate(Map<Locale, String> passwordResetEmailTemplate) {
    this.passwordResetEmailTemplate = passwordResetEmailTemplate;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   customer     DOCUMENT ME!
   * @param   newPassword  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @SuppressWarnings("rawtypes")
  /**
   * Override this method to add in whatever variables your custom template may require.
   */
  protected HashMap constructPasswordChangeEmailTemplateVariables(Customer customer, String newPassword) {
    HashMap<String, String> vars = new HashMap<String, String>();
    vars.put(CUSTOMER_PASSWORD_TEMPLATE_VARIABLE, newPassword);

    return vars;
  }
} // end class EmailNotificationPasswordUpdatedHandler
