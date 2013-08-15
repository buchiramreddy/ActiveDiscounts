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

package org.broadleafcommerce.profile.core.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.email.service.EmailService;
import org.broadleafcommerce.common.email.service.info.EmailInfo;
import org.broadleafcommerce.common.security.util.PasswordChange;
import org.broadleafcommerce.common.security.util.PasswordReset;
import org.broadleafcommerce.common.security.util.PasswordUtils;
import org.broadleafcommerce.common.service.GenericResponse;
import org.broadleafcommerce.common.time.SystemTime;

import org.broadleafcommerce.profile.core.dao.CustomerDao;
import org.broadleafcommerce.profile.core.dao.CustomerForgotPasswordSecurityTokenDao;
import org.broadleafcommerce.profile.core.dao.RoleDao;
import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.CustomerForgotPasswordSecurityToken;
import org.broadleafcommerce.profile.core.domain.CustomerForgotPasswordSecurityTokenImpl;
import org.broadleafcommerce.profile.core.domain.CustomerRole;
import org.broadleafcommerce.profile.core.domain.CustomerRoleImpl;
import org.broadleafcommerce.profile.core.domain.Role;
import org.broadleafcommerce.profile.core.service.handler.PasswordUpdatedHandler;
import org.broadleafcommerce.profile.core.service.listener.PostRegistrationObserver;

import org.springframework.security.authentication.encoding.PasswordEncoder;

import org.springframework.stereotype.Service;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Service("blCustomerService")
public class CustomerServiceImpl implements CustomerService {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log LOG = LogFactory.getLog(CustomerServiceImpl.class);

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blChangePasswordEmailInfo")
  protected EmailInfo changePasswordEmailInfo;

  /** DOCUMENT ME! */
  @Resource(name = "blCustomerDao")
  protected CustomerDao customerDao;

  /** DOCUMENT ME! */
  @Resource(name = "blCustomerForgotPasswordSecurityTokenDao")
  protected CustomerForgotPasswordSecurityTokenDao customerForgotPasswordSecurityTokenDao;

  /** DOCUMENT ME! */
  @Resource(name = "blEmailService")
  protected EmailService emailService;

  /** DOCUMENT ME! */
  @Resource(name = "blForgotPasswordEmailInfo")
  protected EmailInfo forgotPasswordEmailInfo;

  /** DOCUMENT ME! */
  @Resource(name = "blForgotUsernameEmailInfo")
  protected EmailInfo forgotUsernameEmailInfo;

  /** DOCUMENT ME! */
  @Resource(name = "blIdGenerationService")
  protected IdGenerationService          idGenerationService;

  /** DOCUMENT ME! */
  protected List<PasswordUpdatedHandler> passwordChangedHandlers = new ArrayList<PasswordUpdatedHandler>();

  /** DOCUMENT ME! */
  @Resource(name = "blPasswordEncoder")
  protected PasswordEncoder              passwordEncoder;

  /** DOCUMENT ME! */
  protected List<PasswordUpdatedHandler> passwordResetHandlers = new ArrayList<PasswordUpdatedHandler>();

  /** DOCUMENT ME! */
  protected int                          passwordTokenLength = 20;

  /** DOCUMENT ME! */
  protected final List<PostRegistrationObserver> postRegisterListeners = new ArrayList<PostRegistrationObserver>();

  /** DOCUMENT ME! */
  @Resource(name = "blRegistrationEmailInfo")
  protected EmailInfo registrationEmailInfo;

  /** DOCUMENT ME! */
  @Resource(name = "blRoleDao")
  protected RoleDao roleDao;

  /** Optional password salt to be used with the passwordEncoder. */
  protected String salt;

  /** DOCUMENT ME! */
  protected int tokenExpiredMinutes = 30;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerService#addPostRegisterListener(org.broadleafcommerce.profile.core.service.listener.PostRegistrationObserver)
   */
  @Override public void addPostRegisterListener(PostRegistrationObserver postRegisterListeners) {
    this.postRegisterListeners.add(postRegisterListeners);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerService#changePassword(org.broadleafcommerce.common.security.util.PasswordChange)
   */
  @Override public Customer changePassword(PasswordChange passwordChange) {
    Customer customer = readCustomerByUsername(passwordChange.getUsername());
    customer.setUnencodedPassword(passwordChange.getNewPassword());
    customer.setPasswordChangeRequired(passwordChange.getPasswordChangeRequired());
    customer = saveCustomer(customer);

    for (PasswordUpdatedHandler handler : passwordChangedHandlers) {
      handler.passwordChanged(passwordChange, customer, passwordChange.getNewPassword());
    }

    return customer;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerService#checkPasswordResetToken(java.lang.String)
   */
  @Override public GenericResponse checkPasswordResetToken(String token) {
    GenericResponse response = new GenericResponse();
    checkPasswordResetToken(token, response);

    return response;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerService#createCustomer()
   */
  @Override public Customer createCustomer() {
    return createCustomerFromId(null);
  }

  //~ ------------------------------------------------------------------------------------------------------------------


  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerService#createCustomerFromId(java.lang.Long)
   */
  @Override public Customer createCustomerFromId(Long customerId) {
    Customer customer = (customerId != null) ? readCustomerById(customerId) : null;

    if (customer == null) {
      customer = customerDao.create();

      if (customerId != null) {
        customer.setId(customerId);
      } else {
        customer.setId(findNextCustomerId());
      }
    }

    return customer;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerService#createNewCustomer()
   */
  @Override public Customer createNewCustomer() {
    return createCustomerFromId(null);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Allow customers to call from subclassed service.
   *
   * @return  allow customers to call from subclassed service.
   */
  @Override public Long findNextCustomerId() {
    return idGenerationService.findNextId("org.broadleafcommerce.profile.core.domain.Customer");
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public EmailInfo getChangePasswordEmailInfo() {
    return changePasswordEmailInfo;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public EmailInfo getForgotPasswordEmailInfo() {
    return forgotPasswordEmailInfo;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public EmailInfo getForgotUsernameEmailInfo() {
    return forgotUsernameEmailInfo;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerService#getPasswordChangedHandlers()
   */
  @Override public List<PasswordUpdatedHandler> getPasswordChangedHandlers() {
    return passwordChangedHandlers;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerService#getPasswordResetHandlers()
   */
  @Override public List<PasswordUpdatedHandler> getPasswordResetHandlers() {
    return passwordResetHandlers;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int getPasswordTokenLength() {
    return passwordTokenLength;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public EmailInfo getRegistrationEmailInfo() {
    return registrationEmailInfo;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getSalt() {
    return salt;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Optionally provide a salt based on a customer. By default, this returns the salt property
   *
   * @param   customer  DOCUMENT ME!
   *
   * @return  optionally provide a salt based on a customer.
   *
   * @see     {@link org.broadleafcommerce.profile.core.service.CustomerServiceImpl#getSalt()}
   */
  public String getSalt(Customer customer) {
    return getSalt();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int getTokenExpiredMinutes() {
    return tokenExpiredMinutes;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerService#readCustomerByEmail(java.lang.String)
   */
  @Override public Customer readCustomerByEmail(String emailAddress) {
    return customerDao.readCustomerByEmail(emailAddress);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerService#readCustomerById(java.lang.Long)
   */
  @Override public Customer readCustomerById(Long id) {
    return customerDao.readCustomerById(id);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerService#readCustomerByUsername(java.lang.String)
   */
  @Override public Customer readCustomerByUsername(String username) {
    return customerDao.readCustomerByUsername(username);
  }

  //~ ------------------------------------------------------------------------------------------------------------------


  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerService#registerCustomer(org.broadleafcommerce.profile.core.domain.Customer,
   *       java.lang.String, java.lang.String)
   */
  @Override public Customer registerCustomer(Customer customer, String password, String passwordConfirm) {
    customer.setRegistered(true);

    // When unencodedPassword is set the save() will encode it
    if (customer.getId() == null) {
      customer.setId(findNextCustomerId());
    }

    customer.setUnencodedPassword(password);

    Customer retCustomer = saveCustomer(customer);
    createRegisteredCustomerRoles(retCustomer);

    HashMap<String, Object> vars = new HashMap<String, Object>();
    vars.put("customer", retCustomer);

    emailService.sendTemplateEmail(customer.getEmailAddress(), getRegistrationEmailInfo(), vars);
    notifyPostRegisterListeners(retCustomer);

    return retCustomer;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerService#removePostRegisterListener(org.broadleafcommerce.profile.core.service.listener.PostRegistrationObserver)
   */
  @Override public void removePostRegisterListener(PostRegistrationObserver postRegisterListeners) {
    if (this.postRegisterListeners.contains(postRegisterListeners)) {
      this.postRegisterListeners.remove(postRegisterListeners);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerService#resetPassword(org.broadleafcommerce.common.security.util.PasswordReset)
   */
  @Override public Customer resetPassword(PasswordReset passwordReset) {
    Customer customer    = readCustomerByUsername(passwordReset.getUsername());
    String   newPassword = PasswordUtils.generateTemporaryPassword(passwordReset.getPasswordLength());
    customer.setUnencodedPassword(newPassword);
    customer.setPasswordChangeRequired(passwordReset.getPasswordChangeRequired());
    customer = saveCustomer(customer);

    for (PasswordUpdatedHandler handler : passwordResetHandlers) {
      handler.passwordChanged(passwordReset, customer, newPassword);
    }

    return customer;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerService#resetPasswordUsingToken(java.lang.String,java.lang.String,
   *       java.lang.String, java.lang.String)
   */
  @Override public GenericResponse resetPasswordUsingToken(String username, String token, String password,
    String confirmPassword) {
    GenericResponse response = new GenericResponse();
    Customer        customer = null;

    if (username != null) {
      customer = customerDao.readCustomerByUsername(username);
    }

    checkCustomer(customer, response);
    checkPassword(password, confirmPassword, response);

    CustomerForgotPasswordSecurityToken fpst = checkPasswordResetToken(token, response);

    if (!response.getHasErrors()) {
      if (!customer.getId().equals(fpst.getCustomerId())) {
        if (LOG.isWarnEnabled()) {
          LOG.warn("Password reset attempt tried with mismatched customer and token " + customer.getId() + ", "
            + token);
        }

        response.addErrorCode("invalidToken");
      }
    }

    if (!response.getHasErrors()) {
      customer.setUnencodedPassword(password);
      saveCustomer(customer);
      fpst.setTokenUsedFlag(true);
      customerForgotPasswordSecurityTokenDao.saveToken(fpst);
    }

    return response;
  } // end method resetPasswordUsingToken

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerService#saveCustomer(org.broadleafcommerce.profile.core.domain.Customer)
   */
  @Override public Customer saveCustomer(Customer customer) {
    return saveCustomer(customer, true);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerService#saveCustomer(org.broadleafcommerce.profile.core.domain.Customer,
   *       boolean)
   */
  @Override public Customer saveCustomer(Customer customer, boolean register) {
    if (register && !customer.isRegistered()) {
      customer.setRegistered(true);
    }

    if (customer.getUnencodedPassword() != null) {
      customer.setPassword(passwordEncoder.encodePassword(customer.getUnencodedPassword(), getSalt(customer)));
    }

    // let's make sure they entered a new challenge answer (we will populate
    // the password field with hashed values so check that they have changed
    // id
    if ((customer.getUnencodedChallengeAnswer() != null)
          && !customer.getUnencodedChallengeAnswer().equals(customer.getChallengeAnswer())) {
      customer.setChallengeAnswer(passwordEncoder.encodePassword(customer.getUnencodedChallengeAnswer(),
          getSalt(customer)));
    }

    return customerDao.save(customer);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerService#sendForgotPasswordNotification(java.lang.String, java.lang.String)
   */
  @Override public GenericResponse sendForgotPasswordNotification(String username, String resetPasswordUrl) {
    GenericResponse response = new GenericResponse();
    Customer        customer = null;

    if (username != null) {
      customer = customerDao.readCustomerByUsername(username);
    }

    checkCustomer(customer, response);

    if (!response.getHasErrors()) {
      String token = PasswordUtils.generateTemporaryPassword(getPasswordTokenLength());
      token = token.toLowerCase();

      CustomerForgotPasswordSecurityToken fpst = new CustomerForgotPasswordSecurityTokenImpl();
      fpst.setCustomerId(customer.getId());
      fpst.setToken(passwordEncoder.encodePassword(token, null));
      fpst.setCreateDate(SystemTime.asDate());
      customerForgotPasswordSecurityTokenDao.saveToken(fpst);

      HashMap<String, Object> vars = new HashMap<String, Object>();
      vars.put("token", token);

      if (!StringUtils.isEmpty(resetPasswordUrl)) {
        if (resetPasswordUrl.contains("?")) {
          resetPasswordUrl = resetPasswordUrl + "&token=" + token;
        } else {
          resetPasswordUrl = resetPasswordUrl + "?token=" + token;
        }
      }

      vars.put("resetPasswordUrl", resetPasswordUrl);
      emailService.sendTemplateEmail(customer.getEmailAddress(), getForgotPasswordEmailInfo(), vars);
    }

    return response;
  } // end method sendForgotPasswordNotification

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerService#sendForgotUsernameNotification(java.lang.String)
   */
  @Override public GenericResponse sendForgotUsernameNotification(String emailAddress) {
    GenericResponse response  = new GenericResponse();
    List<Customer>  customers = null;

    if (emailAddress != null) {
      customers = customerDao.readCustomersByEmail(emailAddress);
    }

    if ((customers == null) || customers.isEmpty()) {
      response.addErrorCode("notFound");
    } else {
      List<String> activeUsernames = new ArrayList<String>();

      for (Customer customer : customers) {
        if (!customer.isDeactivated()) {
          activeUsernames.add(customer.getUsername());
        }
      }

      if (activeUsernames.size() > 0) {
        HashMap<String, Object> vars = new HashMap<String, Object>();
        vars.put("userNames", activeUsernames);
        emailService.sendTemplateEmail(emailAddress, getForgotUsernameEmailInfo(), vars);
      } else {
        // send inactive username found email.
        response.addErrorCode("inactiveUser");
      }
    }

    return response;
  } // end method sendForgotUsernameNotification

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  changePasswordEmailInfo  DOCUMENT ME!
   */
  public void setChangePasswordEmailInfo(EmailInfo changePasswordEmailInfo) {
    this.changePasswordEmailInfo = changePasswordEmailInfo;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  customerDao  DOCUMENT ME!
   */
  public void setCustomerDao(CustomerDao customerDao) {
    this.customerDao = customerDao;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  forgotPasswordEmailInfo  DOCUMENT ME!
   */
  public void setForgotPasswordEmailInfo(EmailInfo forgotPasswordEmailInfo) {
    this.forgotPasswordEmailInfo = forgotPasswordEmailInfo;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  forgotUsernameEmailInfo  DOCUMENT ME!
   */
  public void setForgotUsernameEmailInfo(EmailInfo forgotUsernameEmailInfo) {
    this.forgotUsernameEmailInfo = forgotUsernameEmailInfo;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerService#setPasswordChangedHandlers(java.util.List)
   */
  @Override public void setPasswordChangedHandlers(List<PasswordUpdatedHandler> passwordChangedHandlers) {
    this.passwordChangedHandlers = passwordChangedHandlers;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  passwordEncoder  DOCUMENT ME!
   */
  public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.CustomerService#setPasswordResetHandlers(java.util.List)
   */
  @Override public void setPasswordResetHandlers(List<PasswordUpdatedHandler> passwordResetHandlers) {
    this.passwordResetHandlers = passwordResetHandlers;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  passwordTokenLength  DOCUMENT ME!
   */
  public void setPasswordTokenLength(int passwordTokenLength) {
    this.passwordTokenLength = passwordTokenLength;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  registrationEmailInfo  DOCUMENT ME!
   */
  public void setRegistrationEmailInfo(EmailInfo registrationEmailInfo) {
    this.registrationEmailInfo = registrationEmailInfo;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  salt  DOCUMENT ME!
   */
  public void setSalt(String salt) {
    this.salt = salt;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  tokenExpiredMinutes  DOCUMENT ME!
   */
  public void setTokenExpiredMinutes(int tokenExpiredMinutes) {
    this.tokenExpiredMinutes = tokenExpiredMinutes;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  customer  DOCUMENT ME!
   * @param  response  DOCUMENT ME!
   */
  protected void checkCustomer(Customer customer, GenericResponse response) {
    if (customer == null) {
      response.addErrorCode("invalidCustomer");
    } else if ((customer.getEmailAddress() == null) || "".equals(customer.getEmailAddress())) {
      response.addErrorCode("emailNotFound");
    } else if (customer.isDeactivated()) {
      response.addErrorCode("inactiveUser");
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  password         DOCUMENT ME!
   * @param  confirmPassword  DOCUMENT ME!
   * @param  response         DOCUMENT ME!
   */
  protected void checkPassword(String password, String confirmPassword, GenericResponse response) {
    if ((password == null) || (confirmPassword == null) || "".equals(password) || "".equals(confirmPassword)) {
      response.addErrorCode("invalidPassword");
    } else if (!password.equals(confirmPassword)) {
      response.addErrorCode("passwordMismatch");
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Subclassed implementations can assign unique roles for various customer types.
   *
   * @param  customer  DOCUMENT ME!
   */
  protected void createRegisteredCustomerRoles(Customer customer) {
    Role         role         = roleDao.readRoleByName("ROLE_USER");
    CustomerRole customerRole = new CustomerRoleImpl();
    customerRole.setRole(role);
    customerRole.setCustomer(customer);
    roleDao.addRoleToCustomer(customerRole);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   fpst  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected boolean isTokenExpired(CustomerForgotPasswordSecurityToken fpst) {
    Date now                   = SystemTime.asDate();
    long currentTimeInMillis   = now.getTime();
    long tokenSaveTimeInMillis = fpst.getCreateDate().getTime();
    long minutesSinceSave      = (currentTimeInMillis - tokenSaveTimeInMillis) / 60000;

    return minutesSinceSave > tokenExpiredMinutes;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  customer  DOCUMENT ME!
   */
  protected void notifyPostRegisterListeners(Customer customer) {
    for (Iterator<PostRegistrationObserver> iter = postRegisterListeners.iterator(); iter.hasNext();) {
      PostRegistrationObserver listener = iter.next();
      listener.processRegistrationEvent(customer);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  private CustomerForgotPasswordSecurityToken checkPasswordResetToken(String token, GenericResponse response) {
    if ((token == null) || "".equals(token)) {
      response.addErrorCode("invalidToken");
    }

    CustomerForgotPasswordSecurityToken fpst = null;

    if (!response.getHasErrors()) {
      token = token.toLowerCase();
      fpst  = customerForgotPasswordSecurityTokenDao.readToken(passwordEncoder.encodePassword(token, null));

      if (fpst == null) {
        response.addErrorCode("invalidToken");
      } else if (fpst.isTokenUsedFlag()) {
        response.addErrorCode("tokenUsed");
      } else if (isTokenExpired(fpst)) {
        response.addErrorCode("tokenExpired");
      }
    }

    return fpst;
  }
} // end class CustomerServiceImpl
