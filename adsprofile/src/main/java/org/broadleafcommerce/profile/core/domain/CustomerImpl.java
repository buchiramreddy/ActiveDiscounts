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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.StringUtils;

import org.broadleafcommerce.common.admin.domain.AdminMainEntity;
import org.broadleafcommerce.common.audit.Auditable;
import org.broadleafcommerce.common.audit.AuditableListener;
import org.broadleafcommerce.common.locale.domain.Locale;
import org.broadleafcommerce.common.locale.domain.LocaleImpl;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.AdminPresentationCollection;
import org.broadleafcommerce.common.presentation.AdminPresentationMap;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.broadleafcommerce.common.presentation.client.AddMethodType;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Index;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(
  populateToOneFields = PopulateToOneFieldsEnum.TRUE,
  friendlyName        = "CustomerImpl_baseCustomer"
)
@Cache(
  usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
  region = "blCustomerElements"
)
@Entity
@EntityListeners(value = { AuditableListener.class })
@Inheritance(strategy = InheritanceType.JOINED)
@Table(
  name              = "BLC_CUSTOMER",
  uniqueConstraints = @UniqueConstraint(columnNames = { "USER_NAME" })
)
public class CustomerImpl implements Customer, AdminMainEntity {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Transient protected boolean anonymous;

  /** DOCUMENT ME! */
  @Embedded protected Auditable auditable = new Auditable();

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "CustomerImpl_Challenge_Answer",
    order        = 5000,
    tab          = Presentation.Tab.Name.Advanced,
    tabOrder     = Presentation.Tab.Order.Advanced,
    excluded     = true
  )
  @Column(name = "CHALLENGE_ANSWER")
  protected String challengeAnswer;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "CustomerImpl_Challenge_Question",
    order        = 4000,
    tab          = Presentation.Tab.Name.Advanced,
    tabOrder     = Presentation.Tab.Order.Advanced,
    excluded     = true
  )
  @Index(
    name        = "CUSTOMER_CHALLENGE_INDEX",
    columnNames = { "CHALLENGE_QUESTION_ID" }
  )
  @JoinColumn(name = "CHALLENGE_QUESTION_ID")
  @ManyToOne(targetEntity = ChallengeQuestionImpl.class)
  protected ChallengeQuestion challengeQuestion;

  /** DOCUMENT ME! */
  @Transient protected boolean cookied;

  /** DOCUMENT ME! */
  @AdminPresentationCollection(
    friendlyName = "CustomerImpl_Customer_Addresses",
    order        = 1000,
    addType      = AddMethodType.PERSIST,
    tab          = Presentation.Tab.Name.Contact,
    tabOrder     = Presentation.Tab.Order.Contact
  )
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @Cascade(value = { org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
  @OneToMany(
    mappedBy     = "customer",
    targetEntity = CustomerAddressImpl.class,
    cascade      = { CascadeType.ALL }
  )
  protected List<CustomerAddress> customerAddresses = new ArrayList<CustomerAddress>();

  /** DOCUMENT ME! */
  @AdminPresentationMap(
    friendlyName            = "CustomerAttributeImpl_Attribute_Name",
    deleteEntityUponRemove  = true,
    forceFreeFormKeys       = true,
    keyPropertyFriendlyName = "ProductAttributeImpl_Attribute_Name",
    tab                     = Presentation.Tab.Name.Advanced,
    tabOrder                = Presentation.Tab.Order.Advanced
  )
  @BatchSize(size = 50)
  @Cache(
    usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
    region = "blStandardElements"
  )
  @MapKey(name = "name")
  @OneToMany(
    mappedBy      = "customer",
    targetEntity  = CustomerAttributeImpl.class,
    cascade       = { CascadeType.ALL },
    orphanRemoval = true
  )
  protected Map<String, CustomerAttribute> customerAttributes = new HashMap<String, CustomerAttribute>();

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "CustomerImpl_Customer_Locale",
    order        = 4000,
    tab          = Presentation.Tab.Name.Advanced,
    tabOrder     = Presentation.Tab.Order.Advanced,
    excluded     = true,
    visibility   = VisibilityEnum.GRID_HIDDEN
  )
  @JoinColumn(name = "LOCALE_CODE")
  @ManyToOne(targetEntity = LocaleImpl.class)
  protected Locale customerLocale;

  /** DOCUMENT ME! */
  @AdminPresentationCollection(
    friendlyName = "CustomerImpl_Customer_Payments",
    order        = 3000,
    addType      = AddMethodType.PERSIST,
    tab          = Presentation.Tab.Name.Contact,
    tabOrder     = Presentation.Tab.Order.Contact
  )
  @BatchSize(size = 50)
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @Cascade(value = { org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
  @OneToMany(
    mappedBy     = "customer",
    targetEntity = CustomerPaymentImpl.class,
    cascade      = { CascadeType.ALL }
  )
  protected List<CustomerPayment> customerPayments = new ArrayList<CustomerPayment>();

  /** DOCUMENT ME! */
  @AdminPresentationCollection(
    friendlyName = "CustomerImpl_Customer_Phones",
    order        = 2000,
    addType      = AddMethodType.PERSIST,
    tab          = Presentation.Tab.Name.Contact,
    tabOrder     = Presentation.Tab.Order.Contact
  )
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @Cascade(value = { org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
  @OneToMany(
    mappedBy     = "customer",
    targetEntity = CustomerPhoneImpl.class,
    cascade      = { CascadeType.ALL }
  )
  protected List<CustomerPhone> customerPhones = new ArrayList<CustomerPhone>();

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "CustomerImpl_Customer_Deactivated",
    order        = 3000,
    tab          = Presentation.Tab.Name.Advanced,
    tabOrder     = Presentation.Tab.Order.Advanced
  )
  @Column(name = "DEACTIVATED")
  protected Boolean deactivated = false;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "CustomerImpl_Email_Address",
    order        = 1000,
    group        = "CustomerImpl_Customer",
    prominent    = true,
    gridOrder    = 1000
  )
  @Column(name = "EMAIL_ADDRESS")
  @Index(
    name        = "CUSTOMER_EMAIL_INDEX",
    columnNames = { "EMAIL_ADDRESS" }
  )
  protected String emailAddress;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "CustomerImpl_First_Name",
    order        = 2000,
    group        = "CustomerImpl_Customer",
    prominent    = true,
    gridOrder    = 2000
  )
  @Column(name = "FIRST_NAME")
  protected String firstName;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "CustomerImpl_Customer_Id",
    group        = "CustomerImpl_Primary_Key",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "CUSTOMER_ID")
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "CustomerImpl_Last_Name",
    order        = 3000,
    group        = "CustomerImpl_Customer",
    prominent    = true,
    gridOrder    = 3000
  )
  @Column(name = "LAST_NAME")
  protected String lastName;

  /** DOCUMENT ME! */
  @Transient protected boolean loggedIn;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @Column(name = "PASSWORD")
  protected String password;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @Column(name = "PASSWORD_CHANGE_REQUIRED")
  protected Boolean passwordChangeRequired = false;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "CustomerImpl_Customer_Receive_Email",
    order        = 1000,
    tab          = Presentation.Tab.Name.Advanced,
    tabOrder     = Presentation.Tab.Order.Advanced
  )
  @Column(name = "RECEIVE_EMAIL")
  protected Boolean receiveEmail = true;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "CustomerImpl_Customer_Registered",
    order        = 4000,
    prominent    = true,
    gridOrder    = 4000
  )
  @Column(name = "IS_REGISTERED")
  protected Boolean registered = false;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "CustomerImpl_Customer_TaxExemptCode",
    order        = 5000,
    tab          = Presentation.Tab.Name.Advanced,
    tabOrder     = Presentation.Tab.Order.Advanced,
    visibility   = VisibilityEnum.GRID_HIDDEN
  )
  @Column(name = "TAX_EXEMPTION_CODE")
  protected String taxExemptionCode;

  /** DOCUMENT ME! */
  @Transient protected String unencodedChallengeAnswer;

  /** DOCUMENT ME! */
  @Transient protected String unencodedPassword;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "CustomerImpl_UserName",
    order        = 4000,
    group        = "CustomerImpl_Customer",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "USER_NAME")
  protected String username;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    CustomerImpl other = (CustomerImpl) obj;

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (username == null) {
      if (other.username != null) {
        return false;
      }
    } else if (!username.equals(other.username)) {
      return false;
    }

    return true;
  } // end method equals

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#getAuditable()
   */
  @Override public Auditable getAuditable() {
    return auditable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#getChallengeAnswer()
   */
  @Override public String getChallengeAnswer() {
    return challengeAnswer;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#getChallengeQuestion()
   */
  @Override public ChallengeQuestion getChallengeQuestion() {
    return challengeQuestion;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#getCustomerAddresses()
   */
  @Override public List<CustomerAddress> getCustomerAddresses() {
    return customerAddresses;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#getCustomerAttributes()
   */
  @Override public Map<String, CustomerAttribute> getCustomerAttributes() {
    return customerAttributes;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#getCustomerLocale()
   */
  @Override public Locale getCustomerLocale() {
    return customerLocale;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#getCustomerPayments()
   */
  @Override public List<CustomerPayment> getCustomerPayments() {
    return customerPayments;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#getCustomerPhones()
   */
  @Override public List<CustomerPhone> getCustomerPhones() {
    return customerPhones;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#getEmailAddress()
   */
  @Override public String getEmailAddress() {
    return emailAddress;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#getFirstName()
   */
  @Override public String getFirstName() {
    return firstName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#getLastName()
   */
  @Override public String getLastName() {
    return lastName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.admin.domain.AdminMainEntity#getMainEntityName()
   */
  @Override public String getMainEntityName() {
    if (!StringUtils.isEmpty(getFirstName()) && !StringUtils.isEmpty(getLastName())) {
      return getFirstName() + " " + getLastName();
    }

    return String.valueOf(getId());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#getPassword()
   */
  @Override public String getPassword() {
    return password;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#getTaxExemptionCode()
   */
  @Override public String getTaxExemptionCode() {
    return this.taxExemptionCode;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#getUnencodedChallengeAnswer()
   */
  @Override public String getUnencodedChallengeAnswer() {
    return unencodedChallengeAnswer;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#getUnencodedPassword()
   */
  @Override public String getUnencodedPassword() {
    return unencodedPassword;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#getUsername()
   */
  @Override public String getUsername() {
    return username;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((username == null) ? 0 : username.hashCode());

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#isAnonymous()
   */
  @Override public boolean isAnonymous() {
    return anonymous;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#isCookied()
   */
  @Override public boolean isCookied() {
    return cookied;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#isDeactivated()
   */
  @Override public boolean isDeactivated() {
    if (deactivated == null) {
      return false;
    } else {
      return deactivated.booleanValue();
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#isLoggedIn()
   */
  @Override public boolean isLoggedIn() {
    return loggedIn;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#isPasswordChangeRequired()
   */
  @Override public boolean isPasswordChangeRequired() {
    if (passwordChangeRequired == null) {
      return false;
    } else {
      return passwordChangeRequired.booleanValue();
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#isReceiveEmail()
   */
  @Override public boolean isReceiveEmail() {
    if (receiveEmail == null) {
      return false;
    } else {
      return receiveEmail.booleanValue();
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#isRegistered()
   */
  @Override public boolean isRegistered() {
    if (registered == null) {
      return true;
    } else {
      return registered.booleanValue();
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#setAnonymous(boolean)
   */
  @Override public void setAnonymous(boolean anonymous) {
    this.anonymous = anonymous;

    if (anonymous) {
      cookied  = false;
      loggedIn = false;
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#setAuditable(org.broadleafcommerce.common.audit.Auditable)
   */
  @Override public void setAuditable(Auditable auditable) {
    this.auditable = auditable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#setChallengeAnswer(java.lang.String)
   */
  @Override public void setChallengeAnswer(String challengeAnswer) {
    this.challengeAnswer = challengeAnswer;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#setChallengeQuestion(org.broadleafcommerce.profile.core.domain.ChallengeQuestion)
   */
  @Override public void setChallengeQuestion(ChallengeQuestion challengeQuestion) {
    this.challengeQuestion = challengeQuestion;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#setCookied(boolean)
   */
  @Override public void setCookied(boolean cookied) {
    this.cookied = cookied;

    if (cookied) {
      anonymous = false;
      loggedIn  = false;
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#setCustomerAddresses(java.util.List)
   */
  @Override public void setCustomerAddresses(List<CustomerAddress> customerAddresses) {
    this.customerAddresses = customerAddresses;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#setCustomerAttributes(java.util.Map)
   */
  @Override public void setCustomerAttributes(Map<String, CustomerAttribute> customerAttributes) {
    this.customerAttributes = customerAttributes;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#setCustomerLocale(org.broadleafcommerce.common.locale.domain.Locale)
   */
  @Override public void setCustomerLocale(Locale customerLocale) {
    this.customerLocale = customerLocale;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#setCustomerPayments(java.util.List)
   */
  @Override public void setCustomerPayments(List<CustomerPayment> customerPayments) {
    this.customerPayments = customerPayments;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#setCustomerPhones(java.util.List)
   */
  @Override public void setCustomerPhones(List<CustomerPhone> customerPhones) {
    this.customerPhones = customerPhones;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#setDeactivated(boolean)
   */
  @Override public void setDeactivated(boolean deactivated) {
    this.deactivated = Boolean.valueOf(deactivated);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#setEmailAddress(java.lang.String)
   */
  @Override public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#setFirstName(java.lang.String)
   */
  @Override public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#setLastName(java.lang.String)
   */
  @Override public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#setLoggedIn(boolean)
   */
  @Override public void setLoggedIn(boolean loggedIn) {
    this.loggedIn = loggedIn;

    if (loggedIn) {
      anonymous = false;
      cookied   = false;
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#setPassword(java.lang.String)
   */
  @Override public void setPassword(String password) {
    this.password = password;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#setPasswordChangeRequired(boolean)
   */
  @Override public void setPasswordChangeRequired(boolean passwordChangeRequired) {
    this.passwordChangeRequired = Boolean.valueOf(passwordChangeRequired);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#setReceiveEmail(boolean)
   */
  @Override public void setReceiveEmail(boolean receiveEmail) {
    this.receiveEmail = Boolean.valueOf(receiveEmail);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#setRegistered(boolean)
   */
  @Override public void setRegistered(boolean registered) {
    this.registered = Boolean.valueOf(registered);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#setTaxExemptionCode(java.lang.String)
   */
  @Override public void setTaxExemptionCode(String exemption) {
    this.taxExemptionCode = exemption;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#setUnencodedChallengeAnswer(java.lang.String)
   */
  @Override public void setUnencodedChallengeAnswer(String unencodedChallengeAnswer) {
    this.unencodedChallengeAnswer = unencodedChallengeAnswer;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#setUnencodedPassword(java.lang.String)
   */
  @Override public void setUnencodedPassword(String unencodedPassword) {
    this.unencodedPassword = unencodedPassword;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.domain.Customer#setUsername(java.lang.String)
   */
  @Override public void setUsername(String username) {
    this.username = username;
  }

  //~ Inner Classes ----------------------------------------------------------------------------------------------------

  public static class Presentation {
    //~ Inner Classes --------------------------------------------------------------------------------------------------

    public static class Tab {
      //~ Inner Classes ------------------------------------------------------------------------------------------------

      public static class Name {
        //~ Static fields/initializers ---------------------------------------------------------------------------------

        public static final String Contact  = "CustomerImpl_Contact_Tab";
        public static final String Advanced = "CustomerImpl_Advanced_Tab";
      }

      public static class Order {
        //~ Static fields/initializers ---------------------------------------------------------------------------------

        public static final int Contact  = 2000;
        public static final int Advanced = 3000;
      }
    }
  }
} // end class CustomerImpl
