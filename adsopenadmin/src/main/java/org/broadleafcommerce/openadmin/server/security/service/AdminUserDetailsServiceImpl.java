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
import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.openadmin.server.security.domain.AdminPermission;
import org.broadleafcommerce.openadmin.server.security.domain.AdminRole;
import org.broadleafcommerce.openadmin.server.security.domain.AdminUser;

import org.springframework.dao.DataAccessException;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class AdminUserDetailsServiceImpl implements UserDetailsService {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blAdminSecurityService")
  protected AdminSecurityService adminSecurityService;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
   */
  @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException,
    DataAccessException {
    AdminUser adminUser = adminSecurityService.readAdminUserByUserName(username);

    if ((adminUser == null) || (adminUser.getActiveStatusFlag() == null) || !adminUser.getActiveStatusFlag()) {
      throw new UsernameNotFoundException("The user was not found");
    }

    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

    for (AdminRole role : adminUser.getAllRoles()) {
      for (AdminPermission permission : role.getAllPermissions()) {
        authorities.add(new GrantedAuthorityImpl(permission.getName()));
      }
    }

    for (AdminPermission permission : adminUser.getAllPermissions()) {
      authorities.add(new GrantedAuthorityImpl(permission.getName()));
    }

    return new User(username, adminUser.getPassword(), true, true, true, true, authorities);
  }
} // end class AdminUserDetailsServiceImpl
