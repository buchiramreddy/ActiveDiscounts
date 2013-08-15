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

package org.broadleafcommerce.profile.web.core.service;

import javax.annotation.Resource;

import org.broadleafcommerce.common.web.BroadleafRequestContext;
import org.broadleafcommerce.common.web.BroadleafWebRequestProcessor;

import org.broadleafcommerce.profile.core.domain.Customer;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.stereotype.Service;

import org.springframework.web.context.request.WebRequest;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Service("blLoginService")
public class LoginServiceImpl implements LoginService {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blCartStateRequestProcessor")
  protected BroadleafWebRequestProcessor cartStateRequestProcessor;

  @Resource(name = "blAuthenticationManager")
  private AuthenticationManager authenticationManager;

  @Resource(name = "blCustomerStateRequestProcessor")
  private BroadleafWebRequestProcessor customerStateRequestProcessor;

  @Resource(name = "blUserDetailsService")
  private UserDetailsService userDetailsService;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.web.core.service.LoginService#loginCustomer(org.broadleafcommerce.profile.core.domain.Customer)
   */
  @Override public Authentication loginCustomer(Customer customer) {
    return loginCustomer(customer.getUsername(), customer.getUnencodedPassword());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.web.core.service.LoginService#loginCustomer(java.lang.String, java.lang.String)
   */
  @Override public Authentication loginCustomer(String username, String clearTextPassword) {
    UserDetails                         principal      = userDetailsService.loadUserByUsername(username);
    UsernamePasswordAuthenticationToken token          = new UsernamePasswordAuthenticationToken(principal,
        clearTextPassword, principal.getAuthorities());
    Authentication                      authentication = authenticationManager.authenticate(token);
    SecurityContextHolder.getContext().setAuthentication(authentication);
    customerStateRequestProcessor.process(getWebRequest());
    cartStateRequestProcessor.process(getWebRequest());

    return authentication;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.web.core.service.LoginService#logoutCustomer()
   */
  @Override public void logoutCustomer() {
    SecurityContextHolder.getContext().setAuthentication(null);
    customerStateRequestProcessor.process(getWebRequest());
    cartStateRequestProcessor.process(getWebRequest());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected WebRequest getWebRequest() {
    return BroadleafRequestContext.getBroadleafRequestContext().getWebRequest();
  }

} // end class LoginServiceImpl
