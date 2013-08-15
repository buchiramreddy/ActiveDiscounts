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

package org.broadleafcommerce.openadmin.server.security.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.email.service.EmailService;
import org.broadleafcommerce.common.email.service.info.EmailInfo;
import org.broadleafcommerce.common.security.util.PasswordChange;
import org.broadleafcommerce.common.security.util.PasswordUtils;
import org.broadleafcommerce.common.service.GenericResponse;
import org.broadleafcommerce.common.time.SystemTime;

import org.broadleafcommerce.openadmin.server.security.dao.AdminPermissionDao;
import org.broadleafcommerce.openadmin.server.security.dao.AdminRoleDao;
import org.broadleafcommerce.openadmin.server.security.dao.AdminUserDao;
import org.broadleafcommerce.openadmin.server.security.dao.ForgotPasswordSecurityTokenDao;
import org.broadleafcommerce.openadmin.server.security.domain.AdminPermission;
import org.broadleafcommerce.openadmin.server.security.domain.AdminRole;
import org.broadleafcommerce.openadmin.server.security.domain.AdminUser;
import org.broadleafcommerce.openadmin.server.security.domain.ForgotPasswordSecurityToken;
import org.broadleafcommerce.openadmin.server.security.domain.ForgotPasswordSecurityTokenImpl;
import org.broadleafcommerce.openadmin.server.security.service.type.PermissionType;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
@Service("blAdminSecurityService")
public class AdminSecurityServiceImpl implements AdminSecurityService {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log LOG = LogFactory.getLog(AdminSecurityServiceImpl.class);

  private static int PASSWORD_TOKEN_LENGTH = 12;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blAdminRoleDao")
  protected AdminRoleDao adminRoleDao;

  /** DOCUMENT ME! */
  @Resource(name = "blAdminUserDao")
  protected AdminUserDao adminUserDao;

  /** DOCUMENT ME! */
  @Resource(name = "blEmailService")
  protected EmailService emailService;

  /** DOCUMENT ME! */
  @Resource(name = "blForgotPasswordSecurityTokenDao")
  protected ForgotPasswordSecurityTokenDao forgotPasswordSecurityTokenDao;

  /** DOCUMENT ME! */
  @Resource(name = "blPasswordEncoder")
  protected PasswordEncoder passwordEncoder;

  /** DOCUMENT ME! */
  @Resource(name = "blSendAdminResetPasswordEmail")
  protected EmailInfo resetPasswordEmailInfo;

  /** DOCUMENT ME! */
  @Value("${resetPasswordURL}")
  protected String resetPasswordURL;

  /** Optional password salt to be used with the passwordEncoder. */
  protected String salt;

  /** DOCUMENT ME! */
  @Resource(name = "blSendAdminUsernameEmailInfo")
  protected EmailInfo sendUsernameEmailInfo;

  // Variables to set via external configuration.
  /** DOCUMENT ME! */
  @Value("${tokenExpiredMinutes}")
  protected int tokenExpiredMinutes = 30;

  /** DOCUMENT ME! */
  @Resource(name = "blAdminPermissionDao")
  AdminPermissionDao adminPermissionDao;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static int getPASSWORD_TOKEN_LENGTH() {
    return PASSWORD_TOKEN_LENGTH;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  PASSWORD_TOKEN_LENGTH  DOCUMENT ME!
   */
  public static void setPASSWORD_TOKEN_LENGTH(int PASSWORD_TOKEN_LENGTH) {
    AdminSecurityServiceImpl.PASSWORD_TOKEN_LENGTH = PASSWORD_TOKEN_LENGTH;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService#changePassword(org.broadleafcommerce.common.security.util.PasswordChange)
   */
  @Override
  @Transactional("blTransactionManager")
  public AdminUser changePassword(PasswordChange passwordChange) {
    AdminUser user = readAdminUserByUserName(passwordChange.getUsername());
    user.setUnencodedPassword(passwordChange.getNewPassword());
    user = saveAdminUser(user);

    Authentication                      auth        = SecurityContextHolder.getContext().getAuthentication();
    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
        passwordChange.getUsername(), passwordChange.getNewPassword(), auth.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authRequest);
    auth.setAuthenticated(false);

    return user;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService#changePassword(java.lang.String,
   *       java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  @Transactional("blTransactionManager")
  public GenericResponse changePassword(String username,
    String oldPassword, String password, String confirmPassword) {
    GenericResponse response = new GenericResponse();
    AdminUser       user     = null;

    if (username != null) {
      user = adminUserDao.readAdminUserByUserName(username);
    }

    checkUser(user, response);
    checkPassword(password, confirmPassword, response);

    if (!response.getHasErrors()) {
      checkExistingPassword(oldPassword, user, response);
    }

    if (!response.getHasErrors()) {
      user.setUnencodedPassword(password);
      saveAdminUser(user);

    }

    return response;

  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService#deleteAdminPermission(org.broadleafcommerce.openadmin.server.security.domain.AdminPermission)
   */
  @Override
  @Transactional("blTransactionManager")
  public void deleteAdminPermission(AdminPermission permission) {
    adminPermissionDao.deleteAdminPermission(permission);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService#deleteAdminRole(org.broadleafcommerce.openadmin.server.security.domain.AdminRole)
   */
  @Override
  @Transactional("blTransactionManager")
  public void deleteAdminRole(AdminRole role) {
    adminRoleDao.deleteAdminRole(role);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService#deleteAdminUser(org.broadleafcommerce.openadmin.server.security.domain.AdminUser)
   */
  @Override
  @Transactional("blTransactionManager")
  public void deleteAdminUser(AdminUser user) {
    adminUserDao.deleteAdminUser(user);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService#doesOperationExistForCeilingEntity(org.broadleafcommerce.openadmin.server.security.service.type.PermissionType,
   *       java.lang.String)
   */
  @Override public boolean doesOperationExistForCeilingEntity(PermissionType permissionType,
    String ceilingEntityFullyQualifiedName) {
    return adminPermissionDao.doesOperationExistForCeilingEntity(permissionType, ceilingEntityFullyQualifiedName);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public EmailInfo getResetPasswordEmailInfo() {
    return resetPasswordEmailInfo;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getResetPasswordURL() {
    return resetPasswordURL;
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
   * Optionally provide a salt based on a a specific AdminUser. By default, this returns the salt property of this class
   *
   * @param   user  DOCUMENT ME!
   *
   * @return  optionally provide a salt based on a a specific AdminUser.
   *
   * @see     {@link org.broadleafcommerce.openadmin.server.security.service.AdminSecurityServiceImpl#getSalt()}
   */
  public String getSalt(AdminUser user) {
    return getSalt();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public EmailInfo getSendUsernameEmailInfo() {
    return sendUsernameEmailInfo;
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
   * @see  org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService#isUserQualifiedForOperationOnCeilingEntity(org.broadleafcommerce.openadmin.server.security.domain.AdminUser,
   *       org.broadleafcommerce.openadmin.server.security.service.type.PermissionType, java.lang.String)
   */
  @Override public boolean isUserQualifiedForOperationOnCeilingEntity(AdminUser adminUser,
    PermissionType permissionType, String ceilingEntityFullyQualifiedName) {
    return adminPermissionDao.isUserQualifiedForOperationOnCeilingEntity(adminUser, permissionType,
        ceilingEntityFullyQualifiedName);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService#readAdminPermissionById(java.lang.Long)
   */
  @Override public AdminPermission readAdminPermissionById(Long id) {
    return adminPermissionDao.readAdminPermissionById(id);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService#readAdminRoleById(java.lang.Long)
   */
  @Override public AdminRole readAdminRoleById(Long id) {
    return adminRoleDao.readAdminRoleById(id);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService#readAdminUserById(java.lang.Long)
   */
  @Override public AdminUser readAdminUserById(Long id) {
    return adminUserDao.readAdminUserById(id);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService#readAdminUserByUserName(java.lang.String)
   */
  @Override public AdminUser readAdminUserByUserName(String userName) {
    return adminUserDao.readAdminUserByUserName(userName);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService#readAllAdminPermissions()
   */
  @Override public List<AdminPermission> readAllAdminPermissions() {
    return adminPermissionDao.readAllAdminPermissions();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService#readAllAdminRoles()
   */
  @Override public List<AdminRole> readAllAdminRoles() {
    return adminRoleDao.readAllAdminRoles();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService#readAllAdminUsers()
   */
  @Override public List<AdminUser> readAllAdminUsers() {
    return adminUserDao.readAllAdminUsers();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService#resetPasswordUsingToken(java.lang.String,
   *       java.lang.String, java.lang.String, java.lang.String)
   */
  @Override
  @Transactional("blTransactionManager")
  public GenericResponse resetPasswordUsingToken(String username, String token, String password,
    String confirmPassword) {
    GenericResponse response = new GenericResponse();
    AdminUser       user     = null;

    if (username != null) {
      user = adminUserDao.readAdminUserByUserName(username);
    }

    checkUser(user, response);
    checkPassword(password, confirmPassword, response);

    if ((token == null) || "".equals(token)) {
      response.addErrorCode("invalidToken");
    }

    ForgotPasswordSecurityToken fpst = null;

    if (!response.getHasErrors()) {
      token = token.toLowerCase();
      fpst  = forgotPasswordSecurityTokenDao.readToken(passwordEncoder.encodePassword(token, null));

      if (fpst == null) {
        response.addErrorCode("invalidToken");
      } else if (fpst.isTokenUsedFlag()) {
        response.addErrorCode("tokenUsed");
      } else if (isTokenExpired(fpst)) {
        response.addErrorCode("tokenExpired");
      }
    }

    if (!response.getHasErrors()) {
      user.setUnencodedPassword(password);
      saveAdminUser(user);
      fpst.setTokenUsedFlag(true);
      forgotPasswordSecurityTokenDao.saveToken(fpst);
    }

    return response;
  } // end method resetPasswordUsingToken

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService#saveAdminPermission(org.broadleafcommerce.openadmin.server.security.domain.AdminPermission)
   */
  @Override
  @Transactional("blTransactionManager")
  public AdminPermission saveAdminPermission(AdminPermission permission) {
    return adminPermissionDao.saveAdminPermission(permission);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService#saveAdminRole(org.broadleafcommerce.openadmin.server.security.domain.AdminRole)
   */
  @Override
  @Transactional("blTransactionManager")
  public AdminRole saveAdminRole(AdminRole role) {
    return adminRoleDao.saveAdminRole(role);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService#saveAdminUser(org.broadleafcommerce.openadmin.server.security.domain.AdminUser)
   */
  @Override
  @Transactional("blTransactionManager")
  public AdminUser saveAdminUser(AdminUser user) {
    if (user.getUnencodedPassword() != null) {
      user.setPassword(passwordEncoder.encodePassword(user.getUnencodedPassword(), getSalt(user)));
    }

    return adminUserDao.saveAdminUser(user);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService#sendForgotUsernameNotification(java.lang.String)
   */
  @Override
  @Transactional("blTransactionManager")
  public GenericResponse sendForgotUsernameNotification(String emailAddress) {
    GenericResponse response = new GenericResponse();
    List<AdminUser> users    = null;

    if (emailAddress != null) {
      users = adminUserDao.readAdminUserByEmail(emailAddress);
    }

    if ((users == null) || users.isEmpty()) {
      response.addErrorCode("notFound");
    } else {
      List<String> activeUsernames = new ArrayList<String>();

      for (AdminUser user : users) {
        if (user.getActiveStatusFlag()) {
          activeUsernames.add(user.getLogin());
        }
      }

      if (activeUsernames.size() > 0) {
        HashMap<String, Object> vars = new HashMap<String, Object>();
        vars.put("accountNames", activeUsernames);
        emailService.sendTemplateEmail(emailAddress, getSendUsernameEmailInfo(), vars);
      } else {
        // send inactive username found email.
        response.addErrorCode("inactiveUser");
      }
    }

    return response;
  } // end method sendForgotUsernameNotification

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService#sendResetPasswordNotification(java.lang.String)
   */
  @Override
  @Transactional("blTransactionManager")
  public GenericResponse sendResetPasswordNotification(String username) {
    GenericResponse response = new GenericResponse();
    AdminUser       user     = null;

    if (username != null) {
      user = adminUserDao.readAdminUserByUserName(username);
    }

    checkUser(user, response);

    if (!response.getHasErrors()) {
      String token = PasswordUtils.generateTemporaryPassword(PASSWORD_TOKEN_LENGTH);
      token = token.toLowerCase();

      ForgotPasswordSecurityToken fpst = new ForgotPasswordSecurityTokenImpl();
      fpst.setAdminUserId(user.getId());
      fpst.setToken(passwordEncoder.encodePassword(token, null));
      fpst.setCreateDate(SystemTime.asDate());
      forgotPasswordSecurityTokenDao.saveToken(fpst);

      HashMap<String, Object> vars = new HashMap<String, Object>();
      vars.put("token", token);

      String resetPasswordUrl = getResetPasswordURL();

      if (!StringUtils.isEmpty(resetPasswordUrl)) {
        if (resetPasswordUrl.contains("?")) {
          resetPasswordUrl = resetPasswordUrl + "&token=" + token;
        } else {
          resetPasswordUrl = resetPasswordUrl + "?token=" + token;
        }
      }

      vars.put("resetPasswordUrl", resetPasswordUrl);
      emailService.sendTemplateEmail(user.getEmail(), getResetPasswordEmailInfo(), vars);

    } // end if

    return response;
  } // end method sendResetPasswordNotification

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  resetPasswordEmailInfo  DOCUMENT ME!
   */
  public void setResetPasswordEmailInfo(EmailInfo resetPasswordEmailInfo) {
    this.resetPasswordEmailInfo = resetPasswordEmailInfo;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  resetPasswordURL  DOCUMENT ME!
   */
  public void setResetPasswordURL(String resetPasswordURL) {
    this.resetPasswordURL = resetPasswordURL;
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
   * @param  sendUsernameEmailInfo  DOCUMENT ME!
   */
  public void setSendUsernameEmailInfo(EmailInfo sendUsernameEmailInfo) {
    this.sendUsernameEmailInfo = sendUsernameEmailInfo;
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
   * @param  password  DOCUMENT ME!
   * @param  user      DOCUMENT ME!
   * @param  response  DOCUMENT ME!
   */
  protected void checkExistingPassword(String password, AdminUser user, GenericResponse response) {
    if (!passwordEncoder.isPasswordValid(user.getPassword(), password, getSalt(user))) {
      response.addErrorCode("invalidPassword");
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
   * DOCUMENT ME!
   *
   * @param  user      DOCUMENT ME!
   * @param  response  DOCUMENT ME!
   */
  protected void checkUser(AdminUser user, GenericResponse response) {
    if (user == null) {
      response.addErrorCode("invalidUser");
    } else if ((user.getEmail() == null) || "".equals(user.getEmail())) {
      response.addErrorCode("emailNotFound");
    } else if ((user.getActiveStatusFlag() == null) || !user.getActiveStatusFlag()) {
      response.addErrorCode("inactiveUser");
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   fpst  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected boolean isTokenExpired(ForgotPasswordSecurityToken fpst) {
    Date now                   = SystemTime.asDate();
    long currentTimeInMillis   = now.getTime();
    long tokenSaveTimeInMillis = fpst.getCreateDate().getTime();
    long minutesSinceSave      = (currentTimeInMillis - tokenSaveTimeInMillis) / 60000;

    return minutesSinceSave > tokenExpiredMinutes;
  }
} // end class AdminSecurityServiceImpl
