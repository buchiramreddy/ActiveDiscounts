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

package org.broadleafcommerce.core.web.social;


import javax.annotation.Resource;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;

import org.springframework.stereotype.Component;

import org.springframework.web.context.request.NativeWebRequest;


/**
 * The SignInAdapter is exclusively used for provider sign in so a SignInAdapter bean will need to be added to the
 * Spring Social configuration.
 *
 * <p>The signIn() method takes the local application user's user ID normalized as a String. No other credentials are
 * necessary here because by the time this method is called the user will have signed into the provider and their
 * connection with that provider has been used to prove the user's identity. This adapter will then authenticate
 * manually against Spring Security</p>
 *
 * <p>To use: this will automatically be injected into ProviderSignInController, as long as this package is scanned
 * (make sure the following is in applicationContext-servlet.xml) <context:component-scan
 * base-package="org.broadleafcommerce.core.web"/></p>
 *
 * @see      org.springframework.social.connect.web.ProviderSignInController
 * @author   elbertbautista
 * @version  $Revision$, $Date$
 */
@Component("blSocialSignInAdapter")
public class BroadleafSocialSignInAdapter implements SignInAdapter {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  @Resource(name = "blUserDetailsService")
  private UserDetailsService userDetailsService;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.social.connect.web.SignInAdapter#signIn(java.lang.String,org.springframework.social.connect.Connection,
   *       org.springframework.web.context.request.NativeWebRequest)
   */
  @Override public String signIn(String username, Connection<?> connection, NativeWebRequest request) {
    UserDetails                         principal = userDetailsService.loadUserByUsername(username);
    UsernamePasswordAuthenticationToken token     = new UsernamePasswordAuthenticationToken(principal, null,
        principal.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(token);

    return null;
  }

}
