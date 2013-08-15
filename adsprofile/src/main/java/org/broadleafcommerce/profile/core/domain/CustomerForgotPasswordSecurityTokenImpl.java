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

package org.broadleafcommerce.profile.core.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * Created by bpolster.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_CUSTOMER_PASSWORD_TOKEN")
public class CustomerForgotPasswordSecurityTokenImpl implements CustomerForgotPasswordSecurityToken {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Column(
    name     = "CREATE_DATE",
    nullable = false
  )
  @Temporal(TemporalType.TIMESTAMP)
  protected Date createDate;

  /** DOCUMENT ME! */
  @Column(
    name     = "CUSTOMER_ID",
    nullable = false
  )
  protected Long customerId;

  /** DOCUMENT ME! */
  @Column(
    name     = "PASSWORD_TOKEN",
    nullable = false
  )
  @Id protected String token;

  /** DOCUMENT ME! */
  @Column(name = "TOKEN_USED_DATE")
  @Temporal(TemporalType.TIMESTAMP)
  protected Date tokenUsedDate;

  /** DOCUMENT ME! */
  @Column(
    name     = "TOKEN_USED_FLAG",
    nullable = false
  )
  protected boolean tokenUsedFlag;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if ((o == null) || (getClass() != o.getClass())) {
      return false;
    }

    CustomerForgotPasswordSecurityTokenImpl that = (CustomerForgotPasswordSecurityTokenImpl) o;

    if ((token != null) ? (!token.equals(that.token)) : (that.token != null)) {
      return false;
    }

    return true;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerForgotPasswordSecurityToken#getCreateDate()
   */
  @Override public Date getCreateDate() {
    return createDate;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerForgotPasswordSecurityToken#getCustomerId()
   */
  @Override public Long getCustomerId() {
    return customerId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerForgotPasswordSecurityToken#getToken()
   */
  @Override public String getToken() {
    return token;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerForgotPasswordSecurityToken#getTokenUsedDate()
   */
  @Override public Date getTokenUsedDate() {
    return tokenUsedDate;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    return (token != null) ? token.hashCode() : 0;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerForgotPasswordSecurityToken#isTokenUsedFlag()
   */
  @Override public boolean isTokenUsedFlag() {
    return tokenUsedFlag;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerForgotPasswordSecurityToken#setCreateDate(java.util.Date)
   */
  @Override public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerForgotPasswordSecurityToken#setCustomerId(java.lang.Long)
   */
  @Override public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerForgotPasswordSecurityToken#setToken(java.lang.String)
   */
  @Override public void setToken(String token) {
    this.token = token;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerForgotPasswordSecurityToken#setTokenUsedDate(java.util.Date)
   */
  @Override public void setTokenUsedDate(Date tokenUsedDate) {
    this.tokenUsedDate = tokenUsedDate;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.CustomerForgotPasswordSecurityToken#setTokenUsedFlag(boolean)
   */
  @Override public void setTokenUsedFlag(boolean tokenUsedFlag) {
    this.tokenUsedFlag = tokenUsedFlag;
  }
} // end class CustomerForgotPasswordSecurityTokenImpl
